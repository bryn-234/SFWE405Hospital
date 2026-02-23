package SFWE405.project.code.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SFWE405.project.code.Entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    Department findById(long id);
    Department findByName(String name);
}
