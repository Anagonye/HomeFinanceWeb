package com.example.homefinanceweb.controller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@AllArgsConstructor
public class MainController {

    @GetMapping("/login")
        public String login(){
            return "loginPage";
        }

}
