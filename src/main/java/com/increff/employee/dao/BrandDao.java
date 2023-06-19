package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.BrandPojo;
import org.springframework.stereotype.Repository;

@Repository
public class BrandDao extends AbstractDao{
    private static String delete_id = "delete from BrandPojo p where id=:id";
    private static String select_id = "select p from BrandPojo p where id=:id";
    private static String select_all = "select p from BrandPojo p";
    private static String combination_checker = "select p from BrandPojo p where brand=:brand and category= :category";

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(BrandPojo p){ em.persist(p);}


    public BrandPojo select(int id) {
        TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<BrandPojo> selectAll(){
        TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
        return query.getResultList();
    }

    public BrandPojo checkForCombination(String brand, String category){
        TypedQuery<BrandPojo> query = getQuery(combination_checker, BrandPojo.class);
        query.setParameter("brand", brand);
        query.setParameter("category", category);
        return getSingle(query);
    }
    public void update(BrandPojo p){
    }
}
