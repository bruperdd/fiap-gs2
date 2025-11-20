package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.model.Checkin;

public interface CheckinService {

    Checkin getCheckinByUserId(int department_id);
}
