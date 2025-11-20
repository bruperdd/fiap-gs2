package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.dto.CheckinDto;
import br.com.fiap.pulsecheck.model.Checkin;

import java.util.List;

public interface CheckinService {
    void create(CheckinDto dto, String emailLogado);

    List<Checkin> listByAuthenticatedUser(String emailLogado);

    // A versão correta precisa do emailLogado para validar se é Admin
    List<Checkin> listByUserId(int targetUserId, String emailLogado);
}
