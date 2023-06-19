package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.util.StringUtil;

@Service
public class BrandService {
    @Autowired
    private BrandDao dao;

    //CREATE
    @Transactional(rollbackOn = ApiException.class)
    public void add(BrandPojo p) throws ApiException{
        normalize(p);
        if(StringUtil.isEmpty(p.getBrand())) {
            throw new ApiException("Brand cannot be empty");
        }
        if(StringUtil.isEmpty(p.getCategory())) {
            throw new ApiException("Category cannot be empty");
        }
        if(dao.checkForCombination(p.getBrand(),p.getCategory()) != null){
            throw new ApiException("Brand - Category combination already exists");
        }
        dao.insert(p);
    }

    //READ
    @Transactional(rollbackOn = ApiException.class)
    public BrandPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    //UPDATE
    @Transactional(rollbackOn  = ApiException.class)
    public void update(int id, BrandPojo p) throws ApiException{
        normalize(p);
        BrandPojo toUpdate = getCheck(id);
        toUpdate.setCategory(p.getCategory());
        toUpdate.setBrand(p.getBrand());
    }

    //Gets all brand data from BrandPojo
    @Transactional
    public List<BrandPojo> getAll(){return dao.selectAll();}

    //Check if given id exists in Database
    @Transactional
    public BrandPojo getCheck(int id) throws ApiException {
        BrandPojo p = dao.select(id);
        if (p == null) {
            throw new ApiException("Brand-Category with given id does not exist id: " + id);
        }
        return p;
    }

    //To trim and convert to lowercase
    protected static void normalize(BrandPojo p) {
        p.setCategory(p.getCategory().toLowerCase ().trim());
        p.setBrand(p.getBrand().toLowerCase ().trim());
    }
}
