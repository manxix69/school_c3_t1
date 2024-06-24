package ru.manxix69.school.controller;

import jakarta.inject.Inject;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.repository.StudentRepository;
import ru.manxix69.school.service.StudentService;
import ru.manxix69.school.service.StudentServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

//    @MockBean
//    private StudentRepository studentRepository;

//    @SpyBean //-- если ставить этот бин то не поднимаются тесты - не понимаю почему
//    private StudentServiceImpl studentService;

    @MockBean
    private StudentService studentService;

    private Student studentTest1;
    private Faculty facultyTest1;

    @BeforeEach
    void init() {
        studentTest1 = new Student("TEST_STUDENT",55);
        facultyTest1 = new Faculty("TEST_FACULTY", "BLACK");

        facultyTest1.setId(53l);
        studentTest1.setId(52l);
        studentTest1.setFaculty(facultyTest1);
        facultyTest1.setStudents(new HashSet<>());
    }

    @Test
    public void getStudentById() throws Exception {
        Mockito.when(studentService.findStudent(studentTest1.getId())).thenReturn(studentTest1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + studentTest1.getId()) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(studentTest1.getId()))
            ;
    }

    @Test
    public void getFacultyByStudentId() throws Exception {
        Mockito.when(studentService.getFacultyOfStudent(studentTest1.getId())).thenReturn(facultyTest1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + studentTest1.getId() + "/faculty") )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(facultyTest1.getId()))
        ;
    }

    @Test
    public void shouldNotFoundFacultyByStudentId() throws Exception {
        Mockito.when(studentService.getFacultyOfStudent(studentTest1.getId())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + studentTest1.getId() + "/faculty") )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").doesNotExist())
        ;
    }
    @Test
    public void postStudent() throws Exception {
        Mockito.when(studentService.addStudent(studentTest1)).thenReturn(studentTest1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", studentTest1.getId());
        jsonObject.put("name", studentTest1.getName());
        jsonObject.put("age", studentTest1.getAge());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(studentTest1.getId()))
                .andExpect(jsonPath("$.name").value(studentTest1.getName()))
                .andExpect(jsonPath("$.age").value(studentTest1.getAge()))
        ;
    }
    @Test
    public void putStudent() throws Exception {
        Mockito.when(studentService.editStudent(studentTest1)).thenReturn(studentTest1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", studentTest1.getId());
        jsonObject.put("name", studentTest1.getName());
        jsonObject.put("age", studentTest1.getAge());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(studentTest1.getId()))
                .andExpect(jsonPath("$.name").value(studentTest1.getName()))
        ;
    }

    @Test
    public void deleteStudentById() throws Exception {
        Mockito.when(studentService.deleteStudent(studentTest1.getId())).thenReturn(studentTest1);
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/student/" + studentTest1.getId() ) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(studentTest1.getId()))
        ;
    }

    @Test
    public void getStudentsByAge() throws Exception {
        Collection<Student> students = new HashSet<>();
        students.add(studentTest1);

        Mockito.when(studentService.getStudentsByAge(studentTest1.getAge())).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/by-age?age=" + studentTest1.getAge() ) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
        ;
    }

    @Test
    public void getStudentsBetweenAge() throws Exception {
        Collection<Student> students = new HashSet<>();
        students.add(studentTest1);

        Mockito.when(studentService.getStudentsBetweenAge(1,100)).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/between-age?minAge=" + 1
                        + "&maxAge=" + 100 ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
        ;
    }
}
