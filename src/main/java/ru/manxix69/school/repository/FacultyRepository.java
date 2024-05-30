package ru.manxix69.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;

import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    List<Faculty> findByColorEquals(String color);
}
