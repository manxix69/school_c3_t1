package ru.manxix69.school.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.manxix69.school.controller.FacultyController;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.service.FacultyService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {
    @Autowired
    private MockMvc mockMvc;

    /*@MockBean
    private FacultyRepository facultyRepository;

    @SpyBean -- если ставить этот бин то не поднимаются тесты - не понимаю почему
    private FacultyService facultyService;*/

    @MockBean
    private FacultyService facultyService;
    @InjectMocks
    private FacultyController facultyController;

    private Faculty facultyTest1;
    private Student studentTest1;
    private Set<Student> students = new HashSet<>();
    @BeforeEach
    void init() {
        facultyTest1 = new Faculty("TEST_FACULTY", "BLACK");
        studentTest1 = new Student("TEST_STUDENT", 55);
        facultyTest1.setId(53l);
        studentTest1.setId(1l);
        facultyTest1.setStudents(students);
        students.add(studentTest1);
    }

    @Test
    public void getFacultyById() throws Exception {
        Mockito.when(facultyService.findFaculty(facultyTest1.getId())).thenReturn(facultyTest1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + facultyTest1.getId()) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(facultyTest1.getId()))
        ;
    }
    
    @Test
    public void postFaculty() throws Exception {
        Mockito.when(facultyService.addFaculty(facultyTest1)).thenReturn(facultyTest1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", facultyTest1.getId());
        jsonObject.put("name", facultyTest1.getName());
        jsonObject.put("color", facultyTest1.getColor());
//        jsonObject.put("faculty", facultyTest1);
        //Как тут можно положить факультете?

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
//                        .content("{\"id\":52,\"name\":\"TEST_STUDENT\",\"color\":55,\"faculty\":{\"id\":53,\"name\":\"TEST_FACULTY\",\"color\":\"BLACK\",\"faculties\":[]}}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(facultyTest1.getId()))
                .andExpect(jsonPath("$.name").value(facultyTest1.getName()))
                .andExpect(jsonPath("$.color").value(facultyTest1.getColor()))
        //можно ли както проверять сразу json объект передать туда типо просто студенда чтоб в теле проверилось?
        ;
    }
    @Test
    public void putFaculty() throws Exception {
        Mockito.when(facultyService.editFaculty(facultyTest1)).thenReturn(facultyTest1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", facultyTest1.getId());
        jsonObject.put("name", facultyTest1.getName());
        jsonObject.put("color", facultyTest1.getColor());
//        jsonObject.put("faculty", facultyTest1);
        //Как тут можно положить факультете?

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(jsonObject.toString())
//                        .content("{\"id\":52,\"name\":\"TEST_STUDENT\",\"color\":55,\"faculty\":{\"id\":53,\"name\":\"TEST_FACULTY\",\"color\":\"BLACK\",\"faculties\":[]}}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(facultyTest1.getId()))
                .andExpect(jsonPath("$.name").value(facultyTest1.getName()))
        ;
    }

    @Test
    public void deleteFacultyById() throws Exception {
        Mockito.when(facultyService.deleteFaculty(facultyTest1.getId())).thenReturn(facultyTest1);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + facultyTest1.getId() ) )
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void getFacultiesByColor() throws Exception {
        Collection<Faculty> faculties = new HashSet<>();
        faculties.add(facultyTest1);

        Mockito.when(facultyService.getFacultiesByColor(facultyTest1.getColor())).thenReturn(faculties);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/by-color/" + facultyTest1.getColor() ) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
        ;
    }

    @Test
    public void getStudentsByFaculty() throws Exception {
        Mockito.when(facultyService.getStudentsOfFaculty(facultyTest1.getId())).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + facultyTest1.getId() + "/students" ) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
        ;
    }

    @Test
    public void getFacultyByNameOrColor() throws Exception {
        Mockito.when(facultyService.getStudentsOfFaculty(facultyTest1.getId())).thenReturn(students);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/by-name-or-color?name="
                                + facultyTest1.getName()
                                + "&color=" + facultyTest1.getColor() ) )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$").isArray())
        ;
    }
}
