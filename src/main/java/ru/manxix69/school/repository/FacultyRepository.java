package ru.manxix69.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColorEquals(String color);

    Collection<Faculty> findByNameIgnoreCaseOrColorIgnoreCase(String name, String color);
    Collection<Faculty> findByNameIgnoreCase(String name);
    Collection<Faculty> findByColorIgnoreCase(String color);
}
