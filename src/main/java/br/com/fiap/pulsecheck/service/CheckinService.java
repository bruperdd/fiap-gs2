package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.dto.CheckinDto;
import br.com.fiap.pulsecheck.dto.CheckinStatsDto;
import br.com.fiap.pulsecheck.model.Checkin;

import java.util.List;

public interface CheckinService {

    void create(CheckinDto dto, int user_id);

    List<CheckinDto> listMyCheckins(int userId);

    CheckinStatsDto getCheckinStatus(int userId);
}
