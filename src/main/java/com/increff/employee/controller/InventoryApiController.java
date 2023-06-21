package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.increff.employee.service.ApiException;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
public class InventoryApiController {
    @Autowired
    InventoryService service;
    @ApiOperation(value = "Gets by ID")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException{
        InventoryPojo pojo = service.get(id);
        return convert(pojo);
    }
    @ApiOperation(value = "Gets list of all inventory data")
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        List<InventoryData> dataList = service.getAll();
        return dataList;
    }
    @ApiOperation(value = "Updates a record")
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm form) throws ApiException{
        InventoryPojo pojo = convert(form);
        service.update(id, pojo);
    }

    private static InventoryData convert(InventoryPojo pojo){
        InventoryData data = new InventoryData();
        data.setId(pojo.getId());
        data.setQuantity(pojo.getQuantity());
        return data;
    }
    private static InventoryPojo convert(InventoryForm form){
        InventoryPojo pojo = new InventoryPojo();
        pojo.setQuantity(form.getQuantity());
        return pojo;
    }
}
