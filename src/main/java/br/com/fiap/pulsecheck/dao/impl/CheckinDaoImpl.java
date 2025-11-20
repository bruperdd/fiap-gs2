package br.com.fiap.pulsecheck.dao.impl;

import br.com.fiap.pulsecheck.dao.CheckinDao;
import br.com.fiap.pulsecheck.mapper.CheckinMapper;
import br.com.fiap.pulsecheck.model.Checkin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CheckinDaoImpl implements CheckinDao {

    private final CheckinMapper checkinMapper;

    public CheckinDaoImpl(CheckinMapper checkinMapper) {
        this.checkinMapper = checkinMapper;
    }

    public void createCheckIn(Checkin checkin) {
        checkinMapper.createCheckIn(checkin);
    }

    public List<Checkin> findByUserId(int userId) {
        return checkinMapper.findByUserId(userId);
    }

}
