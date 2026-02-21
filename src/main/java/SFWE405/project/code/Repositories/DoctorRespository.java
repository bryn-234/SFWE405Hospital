package SFWE405.project.code.Repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import SFWE405.project.code.Entities.Doctor;

public interface DoctorRespository extends CrudRepository<Doctor, Long>{
    List<Doctor> findByName(String name);

    Doctor findById(long id);
}
