package ru.manxix69.school.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.manxix69.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private Map<Long, Student> students = new HashMap<>();

    private long idStudent = 0L;

    public Student createStudent(Student student) {
        student.setId(++idStudent);
        students.put(idStudent, student);
        return student;
    }

    public Student findStudent(long id) {
        return students.get(id);
    }

    public Student updateStudent(Student student) {
        students.put(student.getId(), student);
        return student;
    }

    public Student deleteStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return students
                .values()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
