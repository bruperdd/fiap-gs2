package br.com.fiap.pulsecheck.mapper;

import br.com.fiap.pulsecheck.model.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    List<Department> list();
}
