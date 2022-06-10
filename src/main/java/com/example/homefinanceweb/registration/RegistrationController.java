package com.example.homefinanceweb.registration;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationServiceInterface registrationService;
    private final EmailValidator emailValidator;

    @GetMapping
    public String registration(Model model){
        RegistrationRequest request = new RegistrationRequest();
        model.addAttribute("request", request);
        return "registration";
    }

    @PostMapping
    public ModelAndView register(@ModelAttribute("request") @Valid RegistrationRequest request, BindingResult result){

        ModelAndView mav = new ModelAndView("registration");

        if(result.hasErrors() || !emailValidator.test(request.getEmail())) {
            if (!emailValidator.test(request.getEmail())) {
                mav.addObject("emailNotValid", true);
                return mav;
            }
            return mav;
        }


        try{
            registrationService.register(request);
        }catch (IllegalStateException e){
            mav.addObject("emailAlreadyTaken", true );
            return mav;
        }

        return mav;
    }


    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token){
        if(!registrationService.confirmToken(token)){
            return "redirect:/login?confirmationError";
        }

        return "redirect:/login?confirmationSuccessful";
    }






}

