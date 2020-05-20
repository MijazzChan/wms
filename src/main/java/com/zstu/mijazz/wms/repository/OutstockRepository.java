package com.zstu.mijazz.wms.repository;

import com.zstu.mijazz.wms.entity.Instock;
import com.zstu.mijazz.wms.entity.Outstock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutstockRepository extends JpaRepository<Outstock, Long> {
    Outstock findByTransactionId(Long transaId);

    Outstock findByItemId(Long itemId);

}
