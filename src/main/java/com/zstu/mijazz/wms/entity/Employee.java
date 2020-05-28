package com.zstu.mijazz.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {
    @Id
    private Long emId;

    private String emName;

    @Min(0)
    @Max(120)
    private int emAge;

    @Min(0)
    @Max(1)
    private int emSex;

    private String passwd;

    private boolean isAdmin;
}
