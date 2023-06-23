package com.increff.employee.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemDao extends AbstractDao{
    private String select_id = "select p from OrderItemPojo p where id=:id";
    private String select_all = "select p from OrderItemPojo p where order_id=:order_id";
    private String check_duplicate = "select p from OrderItemPojo p where product_id=:product_id and order_id=:order_id";

    @PersistenceContext
    private EntityManager em;
    @Transactional
    public void insert(OrderItemPojo pojo){em.persist(pojo);}

    public OrderItemPojo select(int id){
        TypedQuery<OrderItemPojo> query = getQuery(select_id, OrderItemPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }
    public List<OrderItemPojo> selectAll(int order_id){
        TypedQuery<OrderItemPojo> query = getQuery(select_all, OrderItemPojo.class);
        query.setParameter("order_id",order_id);
        return query.getResultList();
    }
    public OrderItemPojo checkDuplicate(int product_id, int order_id){
        TypedQuery<OrderItemPojo> query = getQuery(check_duplicate, OrderItemPojo.class);
        query.setParameter("product_id",product_id);
        query.setParameter("order_id", order_id);
        return getSingle(query);
    }

}
