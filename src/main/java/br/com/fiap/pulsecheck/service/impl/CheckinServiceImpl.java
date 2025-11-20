package br.com.fiap.pulsecheck.service.impl;

import br.com.fiap.pulsecheck.dao.CheckinDao;
import br.com.fiap.pulsecheck.model.Checkin;
import br.com.fiap.pulsecheck.service.CheckinService;
import org.springframework.stereotype.Service;

@Service
public class CheckinServiceImpl implements CheckinService {

    private final CheckinDao checkinDao;

    public CheckinServiceImpl(CheckinDao checkinDao) {
        this.checkinDao = checkinDao;
    }

    public Checkin getCheckinByUserId(int department_id){
        return checkinDao.getCheckinByUserId(department_id);
    }
}
