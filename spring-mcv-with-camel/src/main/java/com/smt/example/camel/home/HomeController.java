package com.smt.example.camel.home;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.opensaml.common.SAMLVersion;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameIDPolicy;
import org.opensaml.saml2.core.impl.AuthnRequestBuilder;
import org.opensaml.saml2.core.impl.IssuerBuilder;
import org.opensaml.saml2.core.impl.NameIDPolicyBuilder;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.util.Base64;
import org.opensaml.xml.util.XMLHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private static final String ISSUER = "http://saml20sp.abilityweb.us";
    String randId = "dsadsa";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal) {
        return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
    }

    @PostConstruct
    public void init() throws ConfigurationException {
        org.opensaml.DefaultBootstrap.bootstrap();
    }

    @RequestMapping("epuap")
    public String epuap() throws IOException, MarshallingException {

        IssuerBuilder issuerBuilder = new IssuerBuilder();
        Issuer issuer = issuerBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "samlp");
        issuer.setValue(ISSUER);

        //Create NameIDPolicy
        NameIDPolicyBuilder nameIdPolicyBuilder = new NameIDPolicyBuilder();
        NameIDPolicy nameIdPolicy = nameIdPolicyBuilder.buildObject();
        //nameIdPolicy.setSchemaLocation("urn:oasis:names:tc:SAML:2.0:protocol");
        nameIdPolicy.setFormat("urn:oasis:names:tc:SAML:2.0:nameid-format:persistent");
        nameIdPolicy.setSPNameQualifier("http://saml20sp.abilityweb.us");
        nameIdPolicy.setAllowCreate(true);

        DateTime issueInstant = new DateTime();
        AuthnRequestBuilder authRequestBuilder = new AuthnRequestBuilder();
        AuthnRequest authRequest = authRequestBuilder.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnRequest", "samlp");

        authRequest.setForceAuthn(false);
        authRequest.setIsPassive(false);
        authRequest.setIssueInstant(issueInstant);
        authRequest.setProtocolBinding("urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST");
        authRequest.setAssertionConsumerServiceURL("http://saml20sp.abilityweb.us/spdbg/sp.php");
        authRequest.setIssuer(issuer);
        authRequest.setNameIDPolicy(nameIdPolicy);
        authRequest.setID(randId);
        authRequest.setVersion(SAMLVersion.VERSION_20);
        authRequest.setDestination("https://hetmantest.epuap.gov.pl/DracoEngine2/draco.jsf");
        String stringRep = authRequest.toString();
        System.out.println("New AuthnRequestImpl: " + stringRep);
        System.out.println("Assertion Consumer Service URL: " + authRequest.getAssertionConsumerServiceURL());

        Marshaller marshaller = org.opensaml.Configuration.getMarshallerFactory().getMarshaller(authRequest);
        org.w3c.dom.Element authDOM = marshaller.marshall(authRequest);
        StringWriter rspWrt = new StringWriter();
        XMLHelper.writeNode(authDOM, rspWrt);
        String messageXML = rspWrt.toString();
        //String samlResponse = new String(Base64.encodeBytes(messageXML.getBytes(), Base64.DONT_BREAK_LINES));

        //delete this area
        //String temp = "<samlp:AuthnRequest  xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\"  ID=\"71069679271a7cf36e0e02e48084798ea844fce23f\" Version=\"2.0\" IssueInstant=\"2010-03-09T10:46:23Z\" ForceAuthn=\"false\" IsPassive=\"false\" ProtocolBinding=\"urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST\" AssertionConsumerServiceURL=\"http://saml20sp.abilityweb.us/spdbg/sp.php\"><saml:Issuer xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">http://saml20sp.abilityweb.us</saml:Issuer><samlp:NameIDPolicy  xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" Format=\"urn:oasis:names:tc:SAML:2.0:nameid-format:persistent\" SPNameQualifier=\"http://saml20sp.abilityweb.us\" AllowCreate=\"true\"></samlp:NameIDPolicy><samlp:RequestedAuthnContext xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" Comparison=\"exact\"><saml:AuthnContextClassRef xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport</saml:AuthnContextClassRef></samlp:RequestedAuthnContext></samlp:AuthnRequest>";
        Deflater deflater = new Deflater(Deflater.DEFLATED, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
        deflaterOutputStream.write(messageXML.getBytes());
        deflaterOutputStream.close();
        String samlResponse = Base64.encodeBytes(byteArrayOutputStream.toByteArray(), Base64.DONT_BREAK_LINES);
        //        String outputString = new String(byteArrayOutputStream.toByteArray());
        //System.out.println("Compressed String: " + outputString);
        samlResponse = URLEncoder.encode(samlResponse);

        String actionURL = "https://hetman.epuap.gov.pl/DracoEngine2/draco.jsf";
        System.out.println("Converted AuthRequest: " + messageXML);
        System.out.println("samlResponse: " + samlResponse);

        String url = actionURL + "?" + samlResponse;

        return "redirect:" + url;

    }
}
