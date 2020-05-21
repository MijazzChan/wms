package com.zstu.mijazz.wms.repository;

import com.zstu.mijazz.wms.entity.Instock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstockRepository extends JpaRepository<Instock, Long> {
    Instock findByTransactionId(Long transaId);

    Instock findByItemId(Long itemId);

    Iterable<Instock> findByViaIdOrderByDateDesc(Long viaId);

    Iterable<Instock> findAllByOrderByDateDesc();

    @Query(nativeQuery = true, value = "select * from instock order by date desc limit 10;")
    Iterable<Instock> findAllByOrOrderByDateDescL10();
}
