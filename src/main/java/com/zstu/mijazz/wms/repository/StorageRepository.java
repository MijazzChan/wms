package com.zstu.mijazz.wms.repository;

import com.zstu.mijazz.wms.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageRepository extends JpaRepository<Storage, Long> {
    Storage findByItemId(Long itemId);

    Storage findByItemName(String itemName);

    Iterable<Storage> findByItemNameLike(String itemName);

    Iterable<Storage> findByItemAmountBetween(Long max, Long min);

    Iterable<Storage> findAllByOrderByItemAmountDesc();
}
