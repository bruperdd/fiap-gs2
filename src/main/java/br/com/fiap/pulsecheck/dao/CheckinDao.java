package br.com.fiap.pulsecheck.dao;

import br.com.fiap.pulsecheck.model.Checkin;

public interface CheckinDao {

    Checkin getCheckinByUserId(int id);
}
