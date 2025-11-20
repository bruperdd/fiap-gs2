package br.com.fiap.pulsecheck.mapper;

import br.com.fiap.pulsecheck.dto.CheckinDto;
import br.com.fiap.pulsecheck.dto.CheckinStatsDto;
import br.com.fiap.pulsecheck.dto.CheckinSupportDto;
import br.com.fiap.pulsecheck.model.Checkin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckinMapper {

    void createCheckIn(Checkin checkin);

    List<CheckinDto> listMyCheckins(@Param("id") int id);

    CheckinStatsDto getCheckinStatus(@Param("id") int id);

    List<CheckinSupportDto> getLast7Days(@Param("id") int id);
}
