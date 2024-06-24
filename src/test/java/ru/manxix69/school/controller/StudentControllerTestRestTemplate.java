package ru.manxix69.school.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;

import java.net.URI;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(studentController);
    }

    @Test
    public void getStudentById() throws Exception {
        ResponseEntity<Student> forEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/student/52", Student.class);
        assertNotNull(forEntity);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
    }

    @Test
    public void getFacultyByStudentId() throws Exception {
        ResponseEntity<Student> forEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/student/52/faculty", Student.class);
        assertNotNull(forEntity);
        assertEquals(HttpStatus.NOT_FOUND, forEntity.getStatusCode());

//        Student student = this.testRestTemplate.getForObject("http://localhost:" + port + "/student/52/faculty", Student.class);
//        Faculty faculty = new Faculty("TEST Faculty", "BLACK");
//        ResponseEntity<Student> forEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/student/52/faculty", Student.class);

        assertNotNull(forEntity);
        assertEquals(HttpStatus.NOT_FOUND, forEntity.getStatusCode());
    }

    @Test
    public void createPutAndDeleteStudentById() throws Exception {
        Student student = new Student();
        student.setName("TEST STUDENT");
        student.setAge(48);
        Student createdStudent = this.testRestTemplate.postForObject("http://localhost:" + port + "/student"
                , student, Student.class);
        assertNotNull(createdStudent);
        assertEquals(createdStudent.getName(), student.getName());
        assertEquals(createdStudent.getAge(), student.getAge());

        student.setName("NEW NAME FOR TEST STUDENT");
        student.setId(createdStudent.getId());
        HttpEntity<Student> studentHttpEntity = new HttpEntity<>(student);
        ResponseEntity<Student> forPutEntity =  this.testRestTemplate.exchange("http://localhost:" + port + "/student"
                , HttpMethod.PUT, studentHttpEntity, Student.class);
        assertNotNull(forPutEntity);
        assertEquals(forPutEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(forPutEntity.getBody().getId(), student.getId());
        assertEquals(forPutEntity.getBody().getName(), student.getName());
        assertEquals(forPutEntity.getBody().getAge(), student.getAge());

        ResponseEntity<Student> forDeleteEntity =  this.testRestTemplate.exchange("http://localhost:" + port + "/student" + "/" + student.getId()
                , HttpMethod.DELETE, studentHttpEntity, Student.class);
        assertNotNull(forDeleteEntity);
        assertEquals(forDeleteEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(forDeleteEntity.getBody().getId(), student.getId());
        assertEquals(forDeleteEntity.getBody().getName(), student.getName());
        assertEquals(forDeleteEntity.getBody().getAge(), student.getAge());

        assertEquals(this.testRestTemplate.exchange("http://localhost:" + port + "/student" + "/" + student.getId()
                , HttpMethod.DELETE, studentHttpEntity, Student.class).getStatusCode() , HttpStatus.BAD_REQUEST);

    }


    @Test
    public void getStudentsAllByAge() throws Exception {
        ResponseEntity<Collection> forEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/student/by-age?age=" + 33
                        , Collection.class);
        assertNotNull(forEntity);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        assertTrue(forEntity.getBody().size() > 0);
    }

    @Test
    public void getStudentsBetweenAge() throws Exception {
        ResponseEntity<Collection> forEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/student" + "/between-age"
                                + "?minAge=" + 1
                        + "&maxAge=" + 100
                        , Collection.class);
        assertNotNull(forEntity);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        assertTrue(forEntity.getBody().size() > 0);
    }


}
