package com.zstu.mijazz.wms.controller;

import com.zstu.mijazz.wms.Utils.Redis4TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicController {

    @Autowired
    Redis4TokenUtil redis4TokenUtil;

    @RequestMapping("/")
    public String index() {
        return dashboard();
    }

    @RequestMapping("/login")
    public String login() {
        return "login.html";
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

    @RequestMapping("/log")
    public String log() {
        return "log.html";
    }


}
