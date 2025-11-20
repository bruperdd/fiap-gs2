package br.com.fiap.pulsecheck.service.impl;

import br.com.fiap.pulsecheck.dao.CheckinDao;
import br.com.fiap.pulsecheck.dao.UsersDao;
import br.com.fiap.pulsecheck.dto.CheckinDto;
import br.com.fiap.pulsecheck.dto.CheckinStatsDto;
import br.com.fiap.pulsecheck.dto.CheckinSupportDto;
import br.com.fiap.pulsecheck.model.Checkin;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.CheckinService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CheckinServiceImpl implements CheckinService {

    private final CheckinDao checkinDao;

    public CheckinServiceImpl(CheckinDao checkinDao) {
        this.checkinDao = checkinDao;
    }

    public void create(CheckinDto dto, int user_id) {

        Checkin checkin = new Checkin();
        checkin.setUser_id(user_id);
        checkin.setMood(dto.getMood());
        checkin.setNote(dto.getNote());
        checkin.setDate(new Date());

        checkinDao.createCheckIn(checkin);
    }

    public List<CheckinDto> listMyCheckins(int userId) {
        return checkinDao.listMyCheckins(userId);
    }

    public CheckinStatsDto getCheckinStatus(int userId){
        CheckinStatsDto dto = checkinDao.getCheckinStatus(userId);
        List<CheckinSupportDto> supportDtos = checkinDao.getLast7Days(userId);
        dto.setLast7Days(supportDtos);
        return dto;
    }
}
