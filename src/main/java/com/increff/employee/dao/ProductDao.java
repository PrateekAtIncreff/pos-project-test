package com.increff.employee.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.stereotype.Repository;
@Repository
public class ProductDao extends AbstractDao{
    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_all = "select p from ProductPojo p";
    private static String brand_category_check = "select p from BrandPojo p where id=:id";
    private static String duplicate_check = "select p from ProductPojo p where barcode=:barcode";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void insert(ProductPojo pojo){em.persist(pojo);}

    public ProductPojo select(int id){
        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    public List<ProductPojo> selectAll(){
        TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }
    public BrandPojo checkIfBrandIdExists(int id){
        TypedQuery<BrandPojo> query = getQuery(brand_category_check, BrandPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    public ProductPojo checkBarcode(String barcode){
        TypedQuery<ProductPojo> query = getQuery(duplicate_check, ProductPojo.class);
        query.setParameter("barcode", barcode);
        return getSingle(query);
    }
}
