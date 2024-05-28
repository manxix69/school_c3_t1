package ru.manxix69.school.service;

import org.springframework.stereotype.Service;
import ru.manxix69.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    private Map<Long, Student> students = new HashMap<>();

    private long count = 0L;

    @Override
    public Student addStudent(Student student) {
        student.setId(++count);
        students.put(student.getId(), student);
        return student;
    }
    @Override
    public Student findStudent(long id) {
        return students.get(id);
    }
    @Override
    public Student editStudent(long id , Student student) {
        if (!students.containsKey(id)) {
            return null;
        }
        students.put(id, student);
        return student;
    }
    @Override
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
