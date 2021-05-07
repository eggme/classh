package me.eggme.classh.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(value = "/error")
    public String handlerError(){
        return "access_denied";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
