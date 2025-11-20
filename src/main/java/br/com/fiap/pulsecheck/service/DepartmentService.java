package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.model.Department;

import java.util.List;

public interface DepartmentService {

    Department listDepartmentsId(int id);

    List<Department> list();
}
