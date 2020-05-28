package com.zstu.mijazz.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage implements Serializable {
    @Id
    private Long itemId;

    private String itemName;

    @Min(0)
    private Long itemAmount;

    public Storage(Long itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemAmount = 0L;
    }
}
