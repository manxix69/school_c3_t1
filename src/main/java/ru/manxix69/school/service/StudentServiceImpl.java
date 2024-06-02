package ru.manxix69.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.repository.StudentRepository;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }
    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }
    @Override
    public Student editStudent(long id , Student student) {
        if (studentRepository.findById(id).isEmpty()) {
            return null;
        }
        return studentRepository.save(student);
    }
    @Override
    public Student deleteStudent(long id) {
        Student student= studentRepository.findById(id).orElse(null);
        if (student == null) {
            return null;
        }
        studentRepository.deleteById(id);
        return student;
    }
    @Override
    public Collection<Student> getStudentsByAge(int age) {
        return studentRepository.findByAgeEquals(age);
    }
}
