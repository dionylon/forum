package com.dionysun.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/demo")
public class DemoController {
    @GetMapping(value = "/show")
    public String show(Model model){
        model.addAttribute("uid","12345");
        model.addAttribute("name","tom");
        return "show";
    }
}
