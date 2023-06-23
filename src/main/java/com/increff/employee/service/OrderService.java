package com.increff.employee.service;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.util.StringUtil;

@Service
public class OrderService {
    @Autowired
    private OrderDao dao;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    ProductService productService;
    @Autowired
    InventoryService inventoryService;

    private int orderId;
    @Transactional(rollbackOn = ApiException.class)
    public void add(List<OrderItemForm> formList) throws ApiException{
        if(formList.size()<1){
            throw new ApiException("Frontend Validation Breach: Empty Order List Not Supported");
        }
        OrderPojo pojo = new OrderPojo();
        LocalDateTime dateTime = LocalDateTime.now();
        pojo.setDate_time(dateTime);
        orderId = dao.insert(pojo).getId();
        addItems(formList);
    }
    @Transactional(rollbackOn = ApiException.class)
    public void addItems(List<OrderItemForm> formList) throws ApiException{
        for(OrderItemForm form:formList){
            OrderItemPojo pojo = convert(form);
            orderItemService.add(pojo);

            //Updating in Inventory
            InventoryPojo inventoryPojo = inventoryService.get(pojo.getProduct_id());
            int quantity = inventoryPojo.getQuantity() - pojo.getQuantity();
            inventoryPojo.setQuantity(quantity);
            inventoryService.update(pojo.getProduct_id(),inventoryPojo);
        }
    }
    @Transactional
    public OrderPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public OrderPojo getCheck(int id) throws ApiException {
        OrderPojo pojo = dao.select(id);
        if(pojo==null)
            throw new ApiException("Order with given id not found");
        return pojo;
    }

    @Transactional
    public List<OrderPojo> getAll(){
        return dao.selectAll();
    }
    private OrderItemPojo convert(OrderItemForm form) throws ApiException {
        normalize(form);
        OrderItemPojo pojo = new OrderItemPojo();
        pojo.setOrder_id(orderId);
        pojo.setProduct_id(productService.getByBarcode(form.getBarcode()).getId());
        pojo.setQuantity(form.getQuantity());
        pojo.setSelling_price(form.getSelling_price());
        return pojo;
    }

    protected static void normalize(OrderItemForm form){
        form.setBarcode(form.getBarcode().toLowerCase ().trim());
    }
}
