package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

@Repository
public class InventoryDao extends AbstractDao{
    private String select_id = "select p from InventoryPojo p where id=:id";
    private String select_all = "select p from InventoryPojo p";


    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(InventoryPojo pojo){em.persist(pojo);}

    public InventoryPojo select(int id){
        TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll(){
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }
}
