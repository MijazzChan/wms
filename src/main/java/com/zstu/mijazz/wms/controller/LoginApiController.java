package com.zstu.mijazz.wms.controller;

import com.zstu.mijazz.wms.ResultReturn;
import com.zstu.mijazz.wms.Utils.Redis4TokenUtil;
import com.zstu.mijazz.wms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LoginApiController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    Redis4TokenUtil redis4TokenUtil;

    @RequestMapping(value = "/api/login", produces = "application/json")
    public ResultReturn<String> loginApi(@RequestParam String loginname, @RequestParam String password) {
        String emId = loginname.strip().trim();
        String emPasswd = password.strip().trim();
        return employeeService.checkPasswd(emId, emPasswd);
    }

    @RequestMapping("/logout")
    public void logout(@RequestParam String emid, HttpServletResponse response) throws IOException {
        redis4TokenUtil.killJWT(emid);
        response.sendRedirect("/login");
    }
}
