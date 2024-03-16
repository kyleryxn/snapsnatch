package com.github.kyleryxn.snapsnatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String welcome() {
        return "index";
    }

}