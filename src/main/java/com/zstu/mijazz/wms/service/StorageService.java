package com.zstu.mijazz.wms.service;

import com.zstu.mijazz.wms.ResultReturn;
import com.zstu.mijazz.wms.entity.Storage;
import com.zstu.mijazz.wms.repository.EmployeeRepository;
import com.zstu.mijazz.wms.repository.StorageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class StorageService {
    @Resource
    StorageRepository storageRepository;

    public ResultReturn<String> inputStorage(Long itemId, Long itemCount) {
        Storage storage = storageRepository.findByItemId(itemId);
        if (itemCount <= 0) {
            return new ResultReturn<>(300, "ERR", "count must be positive");
        }
        if (storage != null) {
            Long oldAmount = storage.getItemAmount();
            storage.setItemAmount(oldAmount + itemCount);
            storageRepository.save(storage);
            return new ResultReturn<>(200, "OK", "OK");
        }
        return new ResultReturn<>(300, "ERR", "Item not found");
    }

    public ResultReturn<String> newStorage(Long itemId, String itemName) {
        if (itemName.strip().length() == 0) {
            return new ResultReturn<>(300, "ERR", "item name cannot be null");
        }
        if (storageRepository.findByItemId(itemId) != null) {
            return new ResultReturn<>(300, "ERR", "duplicate itemId");
        }
        Storage storage = new Storage(itemId, itemName);
        storageRepository.save(storage);
        return new ResultReturn<>(200, "OK", "OK");
    }

    public ResultReturn<String> delStorage(Long itemId) {
        if (storageRepository.findByItemId(itemId) == null) {
            return new ResultReturn<>(300, "ERR", "Item did not exist");
        }
        storageRepository.deleteById(itemId);
        return new ResultReturn<>(200, "OK", "OK");
    }

    public ResultReturn<String> outputStorage(Long itemId, Long itemCount) {
        Storage storage = storageRepository.findByItemId(itemId);
        if (itemCount <= 0) {
            return new ResultReturn<>(300, "ERR", "count must be positive");
        }
        if (storage != null) {
            Long oldAmount = storage.getItemAmount();
            if (itemCount > oldAmount) {
                return new ResultReturn<>(300, "ERR", "Capacity Error");
            }else {
                storage.setItemAmount(oldAmount - itemCount);
                storageRepository.save(storage);
                return new ResultReturn<>(200, "OK", "OK");
            }
        }
        return new ResultReturn<>(300, "ERR", "Item not found");
    }

    public ResultReturn<Iterable<Storage>> findallStorage(){
        return new ResultReturn<>(200, "OK", storageRepository.findAll());
    }

    public ResultReturn<Iterable<Storage>> findallStoragedesc(){
        return new ResultReturn<>(200, "OK", storageRepository.findAllByOrderByItemAmountDesc());
    }

    public ResultReturn<Iterable<Storage>> findStorageNameLike(String name) {
        return new ResultReturn<>(200, "OK", storageRepository.findByItemNameLike(name));
    }

    public ResultReturn<Iterable<Storage>> findStorageAmountBetween(String min, String max) {
        return new ResultReturn<>(200, "OK", storageRepository.findByItemAmountBetween(Long.valueOf(min.strip()), Long.valueOf(max.strip())));
    }

    public ResultReturn<Iterable<Storage>> findById(Long itemId) {
        Storage storage = storageRepository.findByItemId(itemId);
        List<Storage> s = new ArrayList<Storage>();
        if (storage != null) {
            s.add(storage);
            return new ResultReturn<>(200, "OK", s);
        }
        s.add(new Storage(0L, "0", 0L));
        return new ResultReturn<>(200, "OK", s);
    }
}
