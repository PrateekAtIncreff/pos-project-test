package com.increff.pos.service;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;

import com.increff.pos.dao.SchedulerDao;
import com.increff.pos.model.ReportsForm;
import com.increff.pos.pojo.SchedulerPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.pos.util.StringUtil;

@Service
public class SchedulerService {
    @Autowired
    SchedulerDao schedulerDao;


    @Transactional(rollbackOn = ApiException.class)
    public void add(SchedulerPojo pojo) throws ApiException{
        schedulerDao.insert(pojo);
    }
    @Transactional(rollbackOn = ApiException.class)
    public List<SchedulerPojo> getAll(){
        return schedulerDao.selectAll();
    }

    @Transactional(rollbackOn = ApiException.class)
    public List<SchedulerPojo> getByDate(ReportsForm form) throws ApiException {
        LocalDate startDate = LocalDate.parse(form.getStartDate());
        LocalDate endDate = LocalDate.parse(form.getEndDate());
        isValidDateRange(startDate,endDate);
        return schedulerDao.selectByDate(startDate,endDate);
    }

    public static void isValidDateRange(LocalDate start, LocalDate end) throws ApiException {
        if (start.isAfter(end)) {
            throw new ApiException("Start date cannot be after end date");
        }
    }
}
