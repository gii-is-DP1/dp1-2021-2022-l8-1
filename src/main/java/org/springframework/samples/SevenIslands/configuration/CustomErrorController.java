package org.springframework.samples.SevenIslands.configuration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController{

    /* @Bean
    public ErrorProperties errorProperties(){
        return new ErrorProperties();
    }

    public ErrorAttributes errorAttributes = null;
    public ErrorProperties errorProperties = null;

    @Bean
    public BasicErrorController basicErrorController(){
        return new BasicErrorController(errorAttributes, errorProperties);
    }


    /* public CustomErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
        //TODO Auto-generated constructor stub
    } */

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // get error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            // display specific error page
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "errors/error-404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "errors/error-500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "errors/error-403";
            }
        }
        // display generic error
        return "errors/error";
    }


}