package com.zstu.mijazz.wms.repository;

import com.zstu.mijazz.wms.entity.Instock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstockRepository extends JpaRepository<Instock, Long> {
    Instock findByTransactionId(Long transaId);

    Instock findByItemId(Long itemId);

    Iterable<Instock> findByViaIdOrderByDateDesc(Long viaId);

}
