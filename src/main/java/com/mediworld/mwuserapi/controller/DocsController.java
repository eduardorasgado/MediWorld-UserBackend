package com.mediworld.mwuserapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador rest para el acceso a la docuementacion misma
 */
@RestController
@RequestMapping("/")
public class DocsController {
    @GetMapping
    public ModelAndView redirectToLogin(RedirectAttributes redirect){
        ModelAndView mav =new ModelAndView();
        mav.setViewName("redirect:/swagger-ui.html");
        return mav;
    }
}
