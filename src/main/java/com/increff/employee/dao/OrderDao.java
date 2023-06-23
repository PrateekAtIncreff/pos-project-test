package com.increff.employee.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDao extends  AbstractDao{
    private String select_id = "select p from OrderPojo p where id=:id";
    private String select_all = "select p from OrderPojo p";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public OrderPojo insert(OrderPojo pojo){
        em.persist(pojo);
        return pojo;
    }

    public OrderPojo select(int id){
        TypedQuery<OrderPojo> query = getQuery(select_id, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderPojo> selectAll(){
        TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }



}
