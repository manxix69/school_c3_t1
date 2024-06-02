package ru.manxix69.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.manxix69.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByAgeEquals(int age);

    List<Student> findByAgeBetween(int minAge, int maxAge);

}
