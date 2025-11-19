package br.com.fiap.pulsecheck.dao.impl;

import br.com.fiap.pulsecheck.dao.DepartmentsDao;
import br.com.fiap.pulsecheck.mapper.DepartmentMapper;
import br.com.fiap.pulsecheck.model.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentsDaoImpl implements DepartmentsDao {
    private final DepartmentMapper departmentMapper;

    public DepartmentsDaoImpl(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    public List<Department> list() {
        return departmentMapper.list();
    }
}
