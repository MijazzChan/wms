package com.zstu.mijazz.wms.controller;

import com.zstu.mijazz.wms.ResultReturn;
import com.zstu.mijazz.wms.Utils.PasswdUtil;
import com.zstu.mijazz.wms.entity.Employee;
import com.zstu.mijazz.wms.entity.Instock;
import com.zstu.mijazz.wms.entity.Outstock;
import com.zstu.mijazz.wms.service.EmployeeService;
import com.zstu.mijazz.wms.service.InstockService;
import com.zstu.mijazz.wms.service.OutstockService;
import com.zstu.mijazz.wms.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminapi/")
public class AdminApiController {
    @Autowired
    InstockService instockService;

    @Autowired
    OutstockService outstockService;

    @Autowired
    StorageService storageService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PasswdUtil passwdUtil;

    @GetMapping(value = "/getemployee", produces = "application/json")
    public ResultReturn<Iterable<Employee>> getallemployee() {
        return employeeService.getAllEmployee();
    }

    @PostMapping(value = "/delemployee", produces = "application/json")
    public ResultReturn<String> delemployee(@RequestParam String emid) {
        Long id = Long.valueOf(emid);
        return employeeService.delEmployee(id);
    }

    @PostMapping(value = "/addemployee", produces = "application/json")
    public ResultReturn<String> addemployee(@RequestParam String emid, @RequestParam String emname, @RequestParam String emsex) {
        Long emId = Long.valueOf(emid);
        String emName = emname.trim();
        Integer emSex = Integer.valueOf(emsex);
        return employeeService.addEmployee(emId, emName, 20, emSex, passwdUtil.resetPasswdAsName(emid));
    }

    @PostMapping(value = "/resetemployee", produces = "application/json")
    public ResultReturn<String> resetemployee(@RequestParam String emid) {
        return employeeService.resetPasswd(emid);
    }

    @PostMapping(value = "/newitem", produces = "application/json")
    public ResultReturn<String> newitem(@RequestParam String itemid, @RequestParam String itemname) {
        Long itemId = Long.valueOf(itemid);
        return storageService.newStorage(itemId, itemname);
    }

    @PostMapping(value = "/delitem", produces = "application/json")
    public ResultReturn<String> delitem(@RequestParam String itemid) {
        Long itemId = Long.valueOf(itemid);
        return storageService.delStorage(itemId);
    }

    @GetMapping(value = "/getrecentout", produces = "application/json")
    public ResultReturn<Iterable<Outstock>> getrecentout() {
        return outstockService.findRecentOutstock(false);
    }

    @GetMapping(value = "/getrecentout10", produces = "application/json")
    public ResultReturn<Iterable<Outstock>> getrecentout10() {
        return outstockService.findRecentOutstock(true);
    }

    @GetMapping(value = "/getrecentin", produces = "application/json")
    public ResultReturn<Iterable<Instock>> getrecentin() {
        return instockService.findRecentInstock(false);
    }

    @GetMapping(value = "getrecentin10", produces = "application/json")
    public ResultReturn<Iterable<Instock>> getrecentin10() {
        return instockService.findRecentInstock(true);
    }
}
