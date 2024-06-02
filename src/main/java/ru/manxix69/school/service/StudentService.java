package ru.manxix69.school.service;

import ru.manxix69.school.model.Student;

import java.util.Collection;

public interface StudentService {

    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(long id, Student student);

    Student deleteStudent(long id);

    Collection<Student> getStudentsByAge(int age);

    Collection<Student> getStudentsBetweenAge(int minAge, int maxAge);
}
