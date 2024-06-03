/*
package ru.manxix69.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.repository.StudentRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @Autowired
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    private Student STUDENT_1 = new Student(1,"Name One", 34);
    private Student STUDENT_2 = new Student(2,"Name Two", 22);
    private Student STUDENT_3 = new Student(3,"Name Three", 34);

    @BeforeEach
    public void init() {
        this.studentService = new StudentServiceImpl(studentRepository);
    }


    private Student addStudent(Student student) {
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        return studentService.addStudent(student);
    }

    private void compareStudents(long exeptedId, Student exeptedStudent, Student testStudent) {
        Assertions.assertEquals(exeptedStudent.getId(), exeptedId);
        Assertions.assertEquals(exeptedStudent.getId(), testStudent.getId());
        Assertions.assertEquals(exeptedStudent.getAge(), testStudent.getAge());
        Assertions.assertEquals(exeptedStudent.getName(), testStudent.getName());
        Assertions.assertEquals(exeptedStudent.hashCode(), testStudent.hashCode());
    }

    @Test
    public void addOneStudentAndCompareThem() {
        Student student = addStudent(STUDENT_1);
        compareStudents(1, student, STUDENT_1);
    }

    @Test
    public void addTwoStudentsAndCompareThem() {
        addOneStudentAndCompareThem();
        Student student = addStudent(STUDENT_2);
        compareStudents(2, student, STUDENT_2);
    }


    @Test
    public void addThreeStudentsAndCompareThem() {
        addTwoStudentsAndCompareThem();
        Student student = addStudent(STUDENT_3);
        compareStudents(3, student, STUDENT_3);
    }



    @Test
    public void addThreeStudentsAndFindThemById() {
        addThreeStudentsAndCompareThem();
        Student student = null;

        Mockito.when(studentRepository.findById(1l)).thenReturn(Optional.ofNullable(STUDENT_1));
        student = studentService.findStudent(1);
        compareStudents(1, student, STUDENT_1);

        Mockito.when(studentRepository.findById(2l)).thenReturn(Optional.ofNullable(STUDENT_2));
        student = studentService.findStudent(2);
        compareStudents(2, student, STUDENT_2);

        Mockito.when(studentRepository.findById(3L)).thenReturn(Optional.ofNullable(STUDENT_3));
        student = studentService.findStudent(3);
        compareStudents(3, student, STUDENT_3);
    }


    @Test
    public void addThreeStudentsAndEditOneById() {
        addThreeStudentsAndCompareThem();
        Mockito.when(studentRepository.findById(1l)).thenReturn(Optional.ofNullable(STUDENT_1));
        Student student = studentService.editStudent(1, STUDENT_3);
        compareStudents(3, student, STUDENT_3);

    }

    @Test
    public void addOneStudentsAndTryEditOneByNotExistingId() {
        addOneStudentAndCompareThem();
        Student student = studentService.editStudent(2, STUDENT_3);
        Assertions.assertNull(student);
    }


    @Test
    public void addTwoStudentsAndDeleteOneOfThem() {
        addTwoStudentsAndCompareThem();

        Mockito.when(studentRepository.findById(1l)).thenReturn(Optional.ofNullable(STUDENT_1));

        Student student = studentService.deleteStudent(1);
        Assertions.assertNotNull(student);

        Mockito.when(studentRepository.findById(1l)).thenReturn(Optional.ofNullable(null));
        student = studentService.deleteStudent(1);
        Assertions.assertNull(student);
    }


    @Test
    public void addThreeStudentsAndGetCollectionByAge() {
        addThreeStudentsAndCompareThem();

        Collection<Student> students = studentService.getStudentsByAge(STUDENT_1.getAge());
        Assertions.assertNotNull(students);
//        Assertions.assertEquals(2, students.size());
//        Assertions.assertTrue(students.contains(STUDENT_1));
//        Assertions.assertTrue(students.contains(STUDENT_3));
//        Assertions.assertFalse(students.contains(STUDENT_2));

    }

    @Test
    public void addOneStudentAndCompareStrings() {
        Student student = addStudent(STUDENT_1);
        Assertions.assertTrue(student.toString().equals(STUDENT_1.toString()));
    }

    @Test
    public void getFacultyStudent() {
        Student student = addStudent(STUDENT_1);
        Faculty faculty = new Faculty("Economy", "red");
        student.setFaculty(faculty);

        Mockito.when(studentRepository.findById(STUDENT_1.getId())).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, ()-> studentService.getFacultyOfStudent(STUDENT_1.getId()));

        Mockito.when(studentRepository.findById(STUDENT_1.getId())).thenReturn(Optional.ofNullable(STUDENT_1));
        Assertions.assertEquals(faculty, studentService.getFacultyOfStudent(STUDENT_1.getId()));
    }


    @Test
    public void getStudentsBetweenAge() {
        Student student = addStudent(STUDENT_2);

        Collection<Student> students = new HashSet<>();
        students.add(student);

        Mockito.when(studentRepository.findByAgeBetween(10, 25)).thenReturn(students);
        Assertions.assertEquals(students, studentService.getStudentsBetweenAge(10,25));
    }
}
*/
