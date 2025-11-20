package br.com.fiap.pulsecheck.service.impl;

import br.com.fiap.pulsecheck.dao.CheckinDao;
import br.com.fiap.pulsecheck.dao.UsersDao;
import br.com.fiap.pulsecheck.dto.CheckinDto;
import br.com.fiap.pulsecheck.model.Checkin;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.CheckinService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CheckinServiceImpl implements CheckinService {

    private final CheckinDao checkinDao;
    private final UsersDao usersDao;

    public CheckinServiceImpl(CheckinDao checkinDao, UsersDao usersDao) {
        this.checkinDao = checkinDao;
        this.usersDao = usersDao;
    }

    public void create(CheckinDto dto, String emailLogado) {
        // 1. Buscar usuário logado para pegar o ID
        Users userLogado = usersDao.findByEmail(emailLogado);

        if (userLogado == null) {
            throw new RuntimeException("Usuário logado não encontrado");
        }

        // 2. Preencher o Model com dados do DTO e do Sistema
        Checkin checkin = new Checkin();
        checkin.setUser_id(userLogado.getId()); // Preenche o user_id snake_case do seu model
        checkin.setMood(dto.getMood());
        checkin.setNote(dto.getNote());
        checkin.setDate(new Date());

        checkinDao.createCheckIn(checkin);
    }

    public List<Checkin> listByAuthenticatedUser(String emailLogado) {
        Users userLogado = usersDao.findByEmail(emailLogado);

        if (userLogado == null) {
            throw new RuntimeException("Usuário logado não encontrado");
        }

        return checkinDao.findByUserId(userLogado.getId());
    }

    public List<Checkin> listByUserId(int targetUserId) {
        return List.of();
    }

    public List<Checkin> listByUserId(int targetUserId, String emailLogado) {
        // 1. Verificar quem está pedindo
        Users userLogado = usersDao.findByEmail(emailLogado);

        if (userLogado == null) {
            throw new RuntimeException("Usuário logado não encontrado");
        }

        // 2. Validação de permissão: Apenas admin pode ver dados de terceiros
        // Nota: Se quiser permitir que Gestor veja, adicione || "gestor".equals(...)
        if (!"admin".equals(userLogado.getRole())) {
            throw new RuntimeException("Apenas administradores podem acessar o histórico de outros usuários");
        }

        // 3. Verifica se o usuário alvo existe (opcional, mas boa prática)
        Users targetUser = usersDao.findById(targetUserId);
        if (targetUser == null) {
            throw new RuntimeException("Usuário alvo não encontrado");
        }

        return checkinDao.findByUserId(targetUserId);
    }
}
