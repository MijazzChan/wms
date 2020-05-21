package com.zstu.mijazz.wms.repository;

import com.zstu.mijazz.wms.entity.Instock;
import com.zstu.mijazz.wms.entity.Outstock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OutstockRepository extends JpaRepository<Outstock, Long> {
    Outstock findByTransactionId(Long transaId);

    Outstock findByItemId(Long itemId);

    Iterable<Outstock> findAllByOrderByDateDesc();

    @Query(nativeQuery = true, value = "select * from outstock order by date desc limit 10;")
    Iterable<Outstock> findAllByOrOrderByDateDescL10();
}
