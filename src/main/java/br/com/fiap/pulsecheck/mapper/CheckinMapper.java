package br.com.fiap.pulsecheck.mapper;

import br.com.fiap.pulsecheck.model.Checkin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CheckinMapper {

    Checkin getCheckinByUserId(@Param("id") int id);

}
