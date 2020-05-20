package com.zstu.mijazz.wms.service;

import com.zstu.mijazz.wms.ResultReturn;
import com.zstu.mijazz.wms.entity.Instock;
import com.zstu.mijazz.wms.entity.Outstock;
import com.zstu.mijazz.wms.repository.InstockRepository;
import com.zstu.mijazz.wms.repository.OutstockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class OutstockService {
    @Resource
    OutstockRepository outstockRepository;

    @Autowired
    StorageService storageService;

    public ResultReturn<String> outputItem(Long itemId, Long viaId, Long itemCount) {
        ResultReturn<String> resultReturn = storageService.outputStorage(itemId, itemCount);
        if (resultReturn.getCode() == 200) {
            Outstock outstock = new Outstock(itemId, itemCount, viaId, new Date());
            outstockRepository.save(outstock);
        }
        return resultReturn;
    }

    public ResultReturn<Iterable<Outstock>> findRecentOutstock() {
        Iterable<Outstock> list;
        list = outstockRepository.findAll();
        return new ResultReturn<>(200, "OK", list);
    }
}
