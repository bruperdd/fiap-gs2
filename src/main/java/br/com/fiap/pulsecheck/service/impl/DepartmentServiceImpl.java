package br.com.fiap.pulsecheck.service.impl;

import br.com.fiap.pulsecheck.dao.DepartmentsDao;
import br.com.fiap.pulsecheck.model.Department;
import br.com.fiap.pulsecheck.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentsDao departmentsDao;

    public DepartmentServiceImpl(DepartmentsDao departmentsDao) {
        this.departmentsDao = departmentsDao;
    }

    public List<Department> list() {
        return departmentsDao.list();
    }
}
