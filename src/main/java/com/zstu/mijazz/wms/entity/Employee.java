package com.zstu.mijazz.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    private Long emId;

    private String emName;

    private int emAge;

    private int emSex;

    private String passwd;

    private boolean isAdmin;
}
