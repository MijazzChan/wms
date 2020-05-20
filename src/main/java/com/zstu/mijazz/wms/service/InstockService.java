package com.zstu.mijazz.wms.service;

import com.zstu.mijazz.wms.ResultReturn;
import com.zstu.mijazz.wms.entity.Instock;
import com.zstu.mijazz.wms.entity.Outstock;
import com.zstu.mijazz.wms.repository.InstockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class InstockService {
    @Resource
    InstockRepository instockRepository;

    @Autowired
    StorageService storageService;

    public ResultReturn<String> inputItem(Long itemId, Long viaId, Long itemCount) {
        ResultReturn<String> resultReturn = storageService.inputStorage(itemId, itemCount);
        if (resultReturn.getCode() == 200) {
            Instock instock = new Instock(itemId, itemCount, viaId, new Date());
            instockRepository.save(instock);
        }
        return resultReturn;
    }

    public ResultReturn<Iterable<Instock>> findRecentInstock() {
        Iterable<Instock> list;
        list = instockRepository.findAll();
        return new ResultReturn<>(200, "OK", list);
    }
}
