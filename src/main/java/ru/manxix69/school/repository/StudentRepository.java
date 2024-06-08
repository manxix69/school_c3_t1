package ru.manxix69.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.manxix69.school.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeEquals(int age);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

}
