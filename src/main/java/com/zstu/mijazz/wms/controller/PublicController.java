package com.zstu.mijazz.wms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicController {

    @RequestMapping("/")
    public String index() {
        return dashboard();
    }

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard.html";
    }

    @RequestMapping("/instock")
    public String instock() {
        return "instock.html";
    }

    @RequestMapping("/outstock")
    public String outstock() {
        return "outstock.html";
    }

    @RequestMapping("/manage")
    public String manage() {
        return "manage.html";
    }
}
