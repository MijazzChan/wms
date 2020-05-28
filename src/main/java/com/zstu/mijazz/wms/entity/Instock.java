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
public class Instock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long transactionId;

    private Long itemId;

    @Min(1)
    private Long inCount;

    private Long viaId;

    private Date date;

    public Instock(Long itemId, Long inCount, Long viaId, Date date) {
        this.itemId = itemId;
        this.inCount = inCount;
        this.viaId = viaId;
        this.date = date;
    }
}

