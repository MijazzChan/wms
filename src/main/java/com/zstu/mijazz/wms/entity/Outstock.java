package com.zstu.mijazz.wms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Outstock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionId;

    private Long itemId;

    @Min(1)
    private Long outCount;

    private Long viaId;

    private Date date;

    public Outstock(Long itemId, Long outCount, Long viaId, Date date) {
        this.itemId = itemId;
        this.outCount = outCount;
        this.viaId = viaId;
        this.date = date;
    }
}
