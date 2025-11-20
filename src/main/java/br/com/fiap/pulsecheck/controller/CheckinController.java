package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.model.Checkin;
import br.com.fiap.pulsecheck.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
@Slf4j
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @GetMapping("/user")
    public ResponseEntity<Checkin> getCheckinByUserId(@Validated @RequestParam int id) {
        log.info("New checkin for department: {}", id);
        Checkin checkin = checkinService.getCheckinByUserId(id);
        return ResponseEntity.ok(checkin);
    }
}
