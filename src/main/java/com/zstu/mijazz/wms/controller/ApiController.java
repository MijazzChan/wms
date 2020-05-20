package com.zstu.mijazz.wms.controller;

import com.zstu.mijazz.wms.ResultReturn;
import com.zstu.mijazz.wms.entity.Employee;
import com.zstu.mijazz.wms.entity.Instock;
import com.zstu.mijazz.wms.entity.Outstock;
import com.zstu.mijazz.wms.entity.Storage;
import com.zstu.mijazz.wms.repository.EmployeeRepository;
import com.zstu.mijazz.wms.service.EmployeeService;
import com.zstu.mijazz.wms.service.InstockService;
import com.zstu.mijazz.wms.service.OutstockService;
import com.zstu.mijazz.wms.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ApiController {
    @Autowired
    InstockService instockService;

    @Autowired
    OutstockService outstockService;

    @Autowired
    StorageService storageService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping(value = "/instock", produces = "application/json")
    public ResultReturn<String> instock(@RequestParam String id, @RequestParam String viaid, @RequestParam String count) {
        Long itemId = Long.valueOf(id);
        Long viaId = Long.valueOf(viaid);
        Long itemcount = Long.valueOf(count);

        return instockService.inputItem(itemId, viaId, itemcount);
    }

    @PostMapping(value = "/outstock", produces = "application/json")
    public ResultReturn<String> outstock(@RequestParam String id, @RequestParam String viaid, @RequestParam String count) {
        Long itemId = Long.valueOf(id);
        Long viaId = Long.valueOf(viaid);
        Long itemcount = Long.valueOf(count);

        return outstockService.outputItem(itemId, viaId, itemcount);
    }

    @GetMapping(value = "/getstorage", produces = "application/json")
    public ResultReturn<Iterable<Storage>> getstorage() {
        return storageService.findallStorage();
    }

    @GetMapping(value = "/getstoragedesc", produces = "application/json")
    public ResultReturn<Iterable<Storage>> getstoragedesc() {
        return storageService.findallStoragedesc();
    }

    @GetMapping(value = "/getrecentout", produces = "application/json")
    public ResultReturn<Iterable<Outstock>> getrecentout() {
        return outstockService.findRecentOutstock();
    }

    @GetMapping(value = "/getrecentin", produces = "application/json")
    public ResultReturn<Iterable<Instock>> getrecentin() {
        return instockService.findRecentInstock();
    }

    @GetMapping(value = "/getemployee", produces = "application/json")
    public ResultReturn<Iterable<Employee>> getallemployee() {
        return employeeService.getAllEmployee();
    }

    @PostMapping(value = "/delemployee", produces = "application/json")
    public ResultReturn<String> delemployee(@RequestParam String emid) {
        Long id = Long.valueOf(emid);
        return employeeService.delEmployee(id);
    }

    @PostMapping(value = "/inputitem", produces = "application/json")
    public ResultReturn<String> inputitem(@RequestParam String itemid, @RequestParam String viaid, @RequestParam String itemcount) {
        Long itemId = Long.valueOf(itemid);
        Long viaId = Long.valueOf(viaid);
        Long itemCount = Long.valueOf(itemcount);
        return instockService.inputItem(itemId, viaId, itemCount);
    }

    @PostMapping(value = "/newitem", produces = "application/json")
    public ResultReturn<String> newitem(@RequestParam String itemid, @RequestParam String itemname) {
        Long itemId = Long.valueOf(itemid);
        return storageService.newStorage(itemId, itemname);
    }

    @PostMapping(value = "/outputitem", produces = "application/json")
    public ResultReturn<String> outputitem(@RequestParam String itemid, @RequestParam String viaid, @RequestParam String itemcount) {
        Long itemId = Long.valueOf(itemid);
        Long viaId = Long.valueOf(viaid);
        Long itemCount = Long.valueOf(itemcount);
        return outstockService.outputItem(itemId, viaId, itemCount);
    }

    @PostMapping(value = "/delitem", produces = "application/json")
    public ResultReturn<String> delitem(@RequestParam String itemid) {
        Long itemId = Long.valueOf(itemid);
        return storageService.delStorage(itemId);
    }

    @PostMapping(value = "/findstoragebyid", produces = "application/json")
    public ResultReturn<Iterable<Storage>> findstoragebyid(@RequestParam String itemid) {
        Long itemId = Long.valueOf(itemid);
        return storageService.findById(itemId);
    }

    @PostMapping(value = "/findstoragebyname", produces = "application/json")
    public ResultReturn<Iterable<Storage>> findstoragebyname(@RequestParam String itemname) {
        return storageService.findStorageNameLike(itemname);
    }

    @PostMapping(value = "/findstoragebetween", produces = "application/json")
    public ResultReturn<Iterable<Storage>> findstoragebetween(@RequestParam String between) {
        String[] section = between.replace("，", ",").replace(" ", "").strip().split(",");
        System.out.println(between);
        if (section.length == 2) {
            return storageService.findStorageAmountBetween(section[0], section[1]);
        }
        // TODO: change to legit
        return null;
    }
}
