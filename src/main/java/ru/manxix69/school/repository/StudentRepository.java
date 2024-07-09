package ru.manxix69.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.manxix69.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> findByAgeEquals(int age);

    Collection<Student> findByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT COUNT(1) as count FROM student", nativeQuery = true)
    Integer getCountStudents();

    @Query(value = "SELECT AVG(age) as average FROM student", nativeQuery = true)
    Integer getAverageAgeStudents();

    @Query(value = "select s.* from student as s order by s.id desc limit 5", nativeQuery = true)
    Collection<Student> getLastStudents();

    List<Student> findAll();
}
