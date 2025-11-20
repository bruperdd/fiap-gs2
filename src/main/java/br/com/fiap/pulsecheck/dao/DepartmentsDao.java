package br.com.fiap.pulsecheck.dao;

import br.com.fiap.pulsecheck.model.Department;

import java.util.List;

public interface DepartmentsDao {

    Department listDepartmentsId(int id);

    List<Department> list();
}
