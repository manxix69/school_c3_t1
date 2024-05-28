package ru.manxix69.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.manxix69.school.model.Faculty;

import java.util.Collection;

@SpringBootTest
public class FacultyServiceTests {

    @Autowired
    private FacultyService facultyService;

    private Faculty FACULTY_1 = new Faculty("Name One", "red");
    private Faculty FACULTY_2 = new Faculty("Name Two", "white");
    private Faculty FACULTY_3 = new Faculty("Name Three", "red");


    @BeforeEach
    public void init() {
        this.facultyService = new FacultyServiceImpl();
    }


    private Faculty addFaculty(Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    private void compareFaculties(long exeptedId, Faculty exeptedFaculty, Faculty testFaculty) {
        Assertions.assertEquals(exeptedFaculty.getId(), exeptedId);
        Assertions.assertEquals(exeptedFaculty.getId(), testFaculty.getId());
        Assertions.assertEquals(exeptedFaculty.getColor(), testFaculty.getColor());
        Assertions.assertEquals(exeptedFaculty.getName(), testFaculty.getName());
        Assertions.assertEquals(exeptedFaculty.hashCode(), testFaculty.hashCode());
    }

    @Test
    public void addOneFacultyAndCompareThem() {
        Faculty faculty = addFaculty(FACULTY_1);
        compareFaculties(1, faculty, FACULTY_1);
    }

    @Test
    public void addTwoFacultiesAndCompareThem() {
        addOneFacultyAndCompareThem();
        Faculty faculty = addFaculty(FACULTY_2);
        compareFaculties(2, faculty, FACULTY_2);
    }


    @Test
    public void addThreeFacultiesAndCompareThem() {
        addTwoFacultiesAndCompareThem();
        Faculty faculty = addFaculty(FACULTY_3);
        compareFaculties(3, faculty, FACULTY_3);
    }



    @Test
    public void addThreeFacultiesAndFindThemById() {
        addThreeFacultiesAndCompareThem();
        Faculty faculty = null;

        faculty = facultyService.findFaculty(1);
        compareFaculties(1, faculty, FACULTY_1);

        faculty = facultyService.findFaculty(2);
        compareFaculties(2, faculty, FACULTY_2);

        faculty = facultyService.findFaculty(3);
        compareFaculties(3, faculty, FACULTY_3);
    }


    @Test
    public void addThreeFacultiesAndEditOneById() {
        addThreeFacultiesAndCompareThem();
        Faculty faculty = null;

        faculty = facultyService.editFaculty(1, FACULTY_3);
        compareFaculties(3, faculty, FACULTY_3);

    }

    @Test
    public void addOneFacultiesAndTryEditOneByNotExistingId() {
        addOneFacultyAndCompareThem();
        Faculty faculty = facultyService.editFaculty(2, FACULTY_3);
        Assertions.assertNull(faculty);
    }


    @Test
    public void addTwoFacultiesAndDeleteOneOfThem() {
        addTwoFacultiesAndCompareThem();

        Faculty faculty = facultyService.deleteFaculty(1);
        Assertions.assertNotNull(faculty);

        faculty = facultyService.deleteFaculty(1);
        Assertions.assertNull(faculty);
    }


    @Test
    public void addThreeFacultiesAndGetCollectionByColor() {
        addThreeFacultiesAndCompareThem();

        Collection<Faculty> facultys = facultyService.getFacultiesByColor(FACULTY_1.getColor());
        Assertions.assertNotNull(facultys);
        Assertions.assertEquals(2, facultys.size());
        Assertions.assertTrue(facultys.contains(FACULTY_1));
        Assertions.assertTrue(facultys.contains(FACULTY_3));
        Assertions.assertFalse(facultys.contains(FACULTY_2));

    }

    @Test
    public void addOneFacultyAndCompareStrings() {
        Faculty faculty = addFaculty(FACULTY_1);
        Assertions.assertTrue(faculty.toString().equals(FACULTY_1.toString()));
    }

}
