package br.com.fiap.pulsecheck.mapper;

import br.com.fiap.pulsecheck.model.Checkin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CheckinMapper {

    void createCheckIn(Checkin checkin);

    List<Checkin> findByUserId(int userId);

}
