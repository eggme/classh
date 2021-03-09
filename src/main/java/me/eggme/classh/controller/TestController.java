package me.eggme.classh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "test")
public class TestController {

    @RequestMapping(value = "1")
    public String tilesTest(){
        return "tiles.test";
    }


}
