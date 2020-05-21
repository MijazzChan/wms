package com.zstu.mijazz.wms.service;

import com.zstu.mijazz.wms.ResultReturn;
import com.zstu.mijazz.wms.config.TokenUtil;
import com.zstu.mijazz.wms.entity.Employee;
import com.zstu.mijazz.wms.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

@Service
@Transactional
public class EmployeeService {
    @Resource
    EmployeeRepository employeeRepository;

    public ResultReturn<Iterable<Employee>> getAllEmployee() {
        return new ResultReturn<>(200, "OK", employeeRepository.findAll());
    }

    public ResultReturn<Iterable<Employee>> getEmployeebySex(Integer sex) {
        return new ResultReturn<>(200, "OK", employeeRepository.findByEmSex(sex));
    }

    public ResultReturn<Iterable<Employee>> getEmployeebyNameLike(String emName) {
        if (emName.strip().length() == 0) {
            return new ResultReturn<>(300, "Bad Query String", null);
        }
        return new ResultReturn<>(200, "OK", employeeRepository.findByEmNameLike(emName));
    }

    public ResultReturn<String> getEmployeePassword(Long emid) {
        Employee employee = employeeRepository.findByEmId(emid);
        String empasswd = "";
        if (employee != null && employee.getPasswd().length() > 0) {
            empasswd = employee.getPasswd();
            return new ResultReturn<>(200, "OK", empasswd);
        }
        return new ResultReturn<>(300, "ERR", "BAD CREDENTIAL");
    }

    public ResultReturn<Employee> getEmployee(Long emid) {
        Employee employee = employeeRepository.findByEmId(emid);
        if (employee != null) {
            return new ResultReturn<>(200, "OK", employee);
        }
        return new ResultReturn<>(300, "ERR", employee);
    }

    public ResultReturn<String> addEmployee(Long emId, String emName, int emAge, int emSex, String passwd) {
        Employee employee = new Employee(emId, emName, emAge, emSex, passwd, false);
        employeeRepository.save(employee);
        return new ResultReturn<>(200, "OK", emName);
    }

    public ResultReturn<String> delEmployee(Long emId){
        Employee employee = employeeRepository.findByEmId(emId);
        if (employee == null) {
            return new ResultReturn<>(300, "ERR", "No employee has been found");
        }
        employeeRepository.deleteById(emId);
        return new ResultReturn<>(200, "OK", "OK");
    }

    public ResultReturn<String> checkPasswd(String emId, String emPasswd) {
        Employee employee = employeeRepository.findByEmId(Long.valueOf(emId));
        if (employee != null && emPasswd.equals(employee.getPasswd())) {
            return new ResultReturn<>(200, "OK", TokenUtil.sign(employee));
        }
        return new ResultReturn<>(300, "ERR", "NO matching credential");
    }
}
