package ru.manxix69.school.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTestRestTemplate {
    @LocalServerPort
    private int port;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(facultyController);
    }

    @Test
    public void getFacultyById() throws Exception {
        ResponseEntity<Faculty> forEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/-1", Faculty.class);
        assertNotNull(forEntity);
        assertEquals(HttpStatus.BAD_REQUEST, forEntity.getStatusCode());

        forEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/2", Faculty.class);
        assertNotNull(forEntity);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
    }

    private Faculty postFaculty(Faculty faculty) {
        Faculty postedFaculty = null;
        postedFaculty = this.testRestTemplate.postForObject("http://localhost:" + port + "/faculty"
                , faculty, Faculty.class);
        return postedFaculty;
    }
    @Test
    public void createPutAndDeleteFacultyById() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("TEST FACULTY");
        faculty.setColor("BLACK");
        Faculty createdFaculty = postFaculty(faculty);
        assertNotNull(createdFaculty);
        assertEquals(createdFaculty.getName(), faculty.getName());
        assertEquals(createdFaculty.getColor(), faculty.getColor());

        faculty.setName("NEW NAME FOR TEST FACULTY");
        faculty.setId(createdFaculty.getId());
        HttpEntity<Faculty> facultyHttpEntity = new HttpEntity<>(faculty);

        ResponseEntity<Faculty> forPutEntity =  this.testRestTemplate.exchange("http://localhost:" + port + "/faculty"
                , HttpMethod.PUT, facultyHttpEntity, Faculty.class);
        assertNotNull(forPutEntity);
        assertEquals(forPutEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(forPutEntity.getBody().getId(), faculty.getId());
        assertEquals(forPutEntity.getBody().getName(), faculty.getName());
        assertEquals(forPutEntity.getBody().getColor(), faculty.getColor());

        ResponseEntity<Faculty> forDeleteEntity =  this.testRestTemplate.exchange("http://localhost:" + port + "/faculty" + "/" + faculty.getId()
                , HttpMethod.DELETE, facultyHttpEntity, Faculty.class);
        assertNotNull(forDeleteEntity);

        assertEquals(forDeleteEntity.getStatusCode(), HttpStatus.OK);
        assertEquals(forDeleteEntity.getBody().getId(), faculty.getId());
        assertEquals(forDeleteEntity.getBody().getName(), faculty.getName());
        assertEquals(forDeleteEntity.getBody().getColor(), faculty.getColor());

        assertEquals(this.testRestTemplate.exchange("http://localhost:" + port + "/faculty" + "/" + faculty.getId()
                , HttpMethod.DELETE, facultyHttpEntity, Faculty.class).getStatusCode() , HttpStatus.BAD_REQUEST);

    }

    @Test
    public void getFacultyStudents() throws Exception {
        ResponseEntity<Faculty> forEntity = this.testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/2", Faculty.class);
        assertNotNull(forEntity);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());

        assertTrue(forEntity.getBody().getStudents().size() == 0);

        Set<Student> realStudents = forEntity.getBody().getStudents();

        Set<Student> students = new HashSet<>();
        Student student = this.testRestTemplate.getForEntity("http://localhost:" + port + "/student/52", Student.class).getBody();
        students.add(student);
        forEntity.getBody().setStudents(students);

        ResponseEntity<Faculty> forPutEntity =  this.testRestTemplate.exchange("http://localhost:" + port + "/faculty"
                , HttpMethod.PUT, forEntity, Faculty.class);
        assertNotNull(forPutEntity);
        assertEquals(HttpStatus.OK, forPutEntity.getStatusCode());

        ResponseEntity<Collection> forEntityStudents = this.testRestTemplate.getForEntity("http://localhost:" + port + "/faculty/2/students", Collection.class);

        assertTrue(forPutEntity.getBody().getStudents().size() == 1);

        forEntity.getBody().setStudents(realStudents);
        this.testRestTemplate.exchange("http://localhost:" + port + "/faculty"
                , HttpMethod.PUT, forEntity, Faculty.class);

    }

    @Test
    public void getFacultiesAllByAge() throws Exception {
        String color = "red";
        ResponseEntity<Collection> forEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/faculty/by-color/" + color
                        , Collection.class);
        assertNotNull(forEntity);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        assertTrue(forEntity.getBody().size() > 0);
    }

    @Test
    public void getFacultiesBetweenAge() throws Exception {
        Faculty faculty = this.testRestTemplate.getForObject("http://localhost:" + port + "/faculty/2", Faculty.class);
        ResponseEntity<Collection> forEntity = this.testRestTemplate
                .getForEntity("http://localhost:" + port + "/faculty" + "/by-name-or-color"
                                + "?name=" + faculty.getName()
                                + "&color=" + faculty.getColor()
                        , Collection.class);
        assertNotNull(forEntity);
        assertEquals(HttpStatus.OK, forEntity.getStatusCode());
        assertTrue(forEntity.getBody().size() > 0);
    }


}
