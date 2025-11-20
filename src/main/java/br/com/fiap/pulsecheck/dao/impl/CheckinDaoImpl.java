package br.com.fiap.pulsecheck.dao.impl;

import br.com.fiap.pulsecheck.dao.CheckinDao;
import br.com.fiap.pulsecheck.mapper.CheckinMapper;
import br.com.fiap.pulsecheck.model.Checkin;
import org.springframework.stereotype.Repository;

@Repository
public class CheckinDaoImpl implements CheckinDao {

    private final CheckinMapper checkinMapper;

    public CheckinDaoImpl(CheckinMapper checkinMapper) {
        this.checkinMapper = checkinMapper;
    }

    public Checkin getCheckinByUserId(int id) {
        return checkinMapper.getCheckinByUserId(id);
    }

}
