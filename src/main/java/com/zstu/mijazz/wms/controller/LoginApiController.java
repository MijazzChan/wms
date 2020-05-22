package com.zstu.mijazz.wms.controller;

import com.zstu.mijazz.wms.ResultReturn;
import com.zstu.mijazz.wms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginApiController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/api/login", produces = "application/json")
    public ResultReturn<String> loginApi(@RequestParam String loginname, @RequestParam String password) {
        String emId = loginname.strip().trim();
        String emPasswd = password.strip().trim();
        return employeeService.checkPasswd(emId, emPasswd);
    }

    @RequestMapping(value = "/logout")
    public String logout() {
        return "redirect:/login";
    }
}
