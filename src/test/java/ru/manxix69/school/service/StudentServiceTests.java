/*
package ru.manxix69.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.manxix69.school.model.Student;

import java.util.Collection;

@SpringBootTest
public class StudentServiceTests {

    @Autowired
    private StudentService studentService;

    private Student STUDENT_1 = new Student("Name One", 34);
    private Student STUDENT_2 = new Student("Name Two", 22);
    private Student STUDENT_3 = new Student("Name Three", 34);

    @BeforeEach
    public void init() {
        this.studentService = new StudentServiceImpl();
    }


    private Student addStudent(Student student) {
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

        student = studentService.findStudent(1);
        compareStudents(1, student, STUDENT_1);

        student = studentService.findStudent(2);
        compareStudents(2, student, STUDENT_2);

        student = studentService.findStudent(3);
        compareStudents(3, student, STUDENT_3);
    }


    @Test
    public void addThreeStudentsAndEditOneById() {
        addThreeStudentsAndCompareThem();
        Student student = null;

        student = studentService.editStudent(1, STUDENT_3);
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

        Student student = studentService.deleteStudent(1);
        Assertions.assertNotNull(student);

        student = studentService.deleteStudent(1);
        Assertions.assertNull(student);
    }


    @Test
    public void addThreeStudentsAndGetCollectionByAge() {
        addThreeStudentsAndCompareThem();

        Collection<Student> students = studentService.getStudentsByAge(STUDENT_1.getAge());
        Assertions.assertNotNull(students);
        Assertions.assertEquals(2, students.size());
        Assertions.assertTrue(students.contains(STUDENT_1));
        Assertions.assertTrue(students.contains(STUDENT_3));
        Assertions.assertFalse(students.contains(STUDENT_2));

    }

    @Test
    public void addOneStudentAndCompareStrings() {
        Student student = addStudent(STUDENT_1);
        Assertions.assertTrue(student.toString().equals(STUDENT_1.toString()));
    }

}
*/
