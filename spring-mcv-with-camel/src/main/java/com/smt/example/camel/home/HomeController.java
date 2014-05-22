package com.smt.example.camel.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Principal principal) {
        return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
    }

    //    @RequestMapping("epuap")
    public String epuap() {

        return "redirect:https://hetman.epuap.gov.pl/DracoEngine2/draco.jsf";

    }
}
