package br.com.fiap.pulsecheck.dao;

import br.com.fiap.pulsecheck.model.Checkin;

import java.util.List;

public interface CheckinDao {
    void createCheckIn(Checkin checkin);
    List<Checkin> findByUserId(int userId);
}
