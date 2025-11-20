package br.com.fiap.pulsecheck.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CheckinStatsDto {

    private int averageMood;
    private int totalCheckins;
    List<CheckinSupportDto> last7Days;
}
