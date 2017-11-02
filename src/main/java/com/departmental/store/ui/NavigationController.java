package com.departmental.store.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NavigationController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
