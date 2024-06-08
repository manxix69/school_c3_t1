package ru.manxix69.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.manxix69.school.exception.NotFoundStudentByIdException;
import ru.manxix69.school.exception.NotNullIdException;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        if (student.getId() != null) {
            throw new NotNullIdException("Не должен быть указан id студента в переданном запросе на сервер!");
        }
        return studentRepository.save(student);
    }
    @Override
    public Student findStudent(Long id) {
        System.out.println("StudentServiceImpl.findStudent ; id=" + id);
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            throw new NotFoundStudentByIdException("По переданному Id студент не найден в БД!");
        }
        return student;
    }
    @Override
    public Student editStudent(Student student) {
        return studentRepository.save(findStudent(student.getId()));
    }
    @Override
    public Student deleteStudent(long id) {
        Student student = findStudent(id);
        studentRepository.deleteById(id);
        return student;
    }

    @Override
    public Faculty getFacultyOfStudent(long id) {
        Student student= findStudent(id);
        return student.getFaculty();
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findByAgeEquals(age);
    }

    @Override
    public Collection<Student> getStudentsBetweenAge(int minAge, int maxAge) {
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }
}
