package br.com.fiap.pulsecheck.dao;

import br.com.fiap.pulsecheck.dto.CheckinDto;
import br.com.fiap.pulsecheck.dto.CheckinStatsDto;
import br.com.fiap.pulsecheck.dto.CheckinSupportDto;
import br.com.fiap.pulsecheck.model.Checkin;

import java.util.List;

public interface CheckinDao {

    void createCheckIn(Checkin checkin);

    List<Checkin> listMyCheckins(int userId);

    CheckinStatsDto getCheckinStatus(int userId);

    List<CheckinSupportDto> getLast7Days(int userId);
}
