package ru.manxix69.school.service;

import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Student addStudent(Student student);

    Student findStudent(Long id);

    Student editStudent(Student student);

    Student deleteStudent(long id);

    Faculty getFacultyOfStudent(long id);

    Collection<Student> getStudentsByAge(int age);

    Collection<Student> getStudentsBetweenAge(int minAge, int maxAge);

    Integer getCountStudents();

    Integer getAverageAgeStudents();

    Collection<Student> getLastStudents();

    Collection<String> getAllNamesOfStudents(String nameStarts);
}
