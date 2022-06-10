package com.example.homefinanceweb.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest){

        ModelAndView errorPage = new ModelAndView("errorPage");
        String errorMessage = "Ups something goes wrong";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode){
            case 400 -> errorMessage = "Http Error Code: 400. Bad Request.";
            case 401 -> errorMessage = "Http Error Code: 401. Unauthorized.";
            case 404 -> errorMessage = "Http Error Code: 404. Resources not found.";
            case 500 -> errorMessage = "Http Error Code: 500. Internal Server Error.";
        }


        errorPage.addObject("errorMsg", errorMessage);

        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest){
        return (Integer)httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}
