package br.com.fiap.pulsecheck.dao.impl;

import br.com.fiap.pulsecheck.dao.CheckinDao;
import br.com.fiap.pulsecheck.dto.CheckinDto;
import br.com.fiap.pulsecheck.dto.CheckinStatsDto;
import br.com.fiap.pulsecheck.dto.CheckinSupportDto;
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

    public List<Checkin> listMyCheckins(int userId) {
        return checkinMapper.listMyCheckins(userId);
    }

    public CheckinStatsDto getCheckinStatus(int userId){
        return checkinMapper.getCheckinStatus(userId);
    }

    public List<CheckinSupportDto> getLast7Days(int userId){
        return checkinMapper.getLast7Days(userId);
    }

}
