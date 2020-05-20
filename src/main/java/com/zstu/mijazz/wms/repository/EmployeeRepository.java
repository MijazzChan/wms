package com.zstu.mijazz.wms.repository;

import com.zstu.mijazz.wms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmId(Long emid);

    Employee findByEmIdAndPasswd(Long emid, String passwd);

    Iterable<Employee> findByEmSex(Integer emSex);

    Iterable<Employee> findByEmNameLike(String nameLike);

}
