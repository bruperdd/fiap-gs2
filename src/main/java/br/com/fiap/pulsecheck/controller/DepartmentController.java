package br.com.fiap.pulsecheck.controller;

import br.com.fiap.pulsecheck.model.Department;
import br.com.fiap.pulsecheck.model.Users;
import br.com.fiap.pulsecheck.service.CheckinService;
import br.com.fiap.pulsecheck.service.DepartmentService;
import br.com.fiap.pulsecheck.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Department>> list() {
        log.info("List departments: ");
        List<Department> departments = departmentService.list();
        return ResponseEntity.ok(departments);
    }
}
