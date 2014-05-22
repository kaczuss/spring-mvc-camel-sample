/* 
 * EpuapController.java
 *
 * 2014 SMT Software. UMK SEUP project.
 */
package com.smt.example.camel.saml.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author pkaczanowski
 * @version 22 maj 2014
 */
@Controller
public class EpuapController {

    @RequestMapping("epuap")
    public String epuap() {

        return "redirect:https://hetman.epuap.gov.pl/DracoEngine2/draco.jsf";

    }

}
