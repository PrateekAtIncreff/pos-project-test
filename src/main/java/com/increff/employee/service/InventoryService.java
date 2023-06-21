package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.ProductData;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class InventoryService {
    @Autowired
    InventoryDao dao;
    @Autowired
    ProductService service;

    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo pojo) throws ApiException{
        if(pojo.getQuantity()<0){
            throw new ApiException("Quantity cannot be negative");
        }
        dao.insert(pojo);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException{
        return getCheck(id);
    }

    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, InventoryPojo pojo) throws ApiException{
        if(pojo.getQuantity()<0){
            throw new ApiException("Quantity cannot be negative");
        }
        InventoryPojo toUpdate = getCheck(id);
        toUpdate.setQuantity(pojo.getQuantity());

    }

    @Transactional
    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> pojoList =  dao.selectAll();
        List<InventoryData> dataList = new ArrayList<>();
        for(InventoryPojo pojo : pojoList){
            InventoryData data = new InventoryData();
            ProductPojo product = service.get(pojo.getId());
            data.setId(pojo.getId());
            data.setQuantity(pojo.getQuantity());
            data.setBarcode(product.getBarcode());
            dataList.add(data);
        }

        return dataList;
    }

    @Transactional
    public InventoryPojo getCheck(int id) throws ApiException{
        InventoryPojo pojo = dao.select(id);
        if (pojo == null) {
            throw new ApiException("Product Details with given id does not exist id: " + id);
        }
        return pojo;
    }
}
