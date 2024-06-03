/*
package ru.manxix69.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.repository.FacultyRepository;
import ru.manxix69.school.repository.StudentRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FacultyServiceTests {

    @Autowired
    private FacultyService facultyService;

    @Mock
    private FacultyRepository facultyRepository;

    private Faculty FACULTY_1 = new Faculty(1,"Name One", "red", new HashSet<>());
    private Faculty FACULTY_2 = new Faculty(2,"Name Two", "white", new HashSet<>());
    private Faculty FACULTY_3 = new Faculty(3,"Name Three", "red", new HashSet<>());


    @BeforeEach
    public void init() {
        this.facultyService = new FacultyServiceImpl(facultyRepository);
    }


    private Faculty addFaculty(Faculty faculty) {
        Mockito.when(facultyRepository.save(faculty)).thenReturn(faculty);
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

        Mockito.when(facultyRepository.findById(1l)).thenReturn(Optional.ofNullable(FACULTY_1));
        faculty = facultyService.findFaculty(1);
        compareFaculties(1, faculty, FACULTY_1);

        Mockito.when(facultyRepository.findById(2l)).thenReturn(Optional.ofNullable(FACULTY_2));
        faculty = facultyService.findFaculty(2);
        compareFaculties(2, faculty, FACULTY_2);

        Mockito.when(facultyRepository.findById(3l)).thenReturn(Optional.ofNullable(FACULTY_3));
        faculty = facultyService.findFaculty(3);
        compareFaculties(3, faculty, FACULTY_3);
    }


    @Test
    public void addThreeFacultiesAndEditOneById() {
        addThreeFacultiesAndCompareThem();
        Faculty faculty = null;

        Mockito.when(facultyRepository.findById(1l)).thenReturn(Optional.ofNullable(FACULTY_1));
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

        Mockito.when(facultyRepository.findById(1l)).thenReturn(Optional.ofNullable(FACULTY_1));
        Faculty faculty = facultyService.deleteFaculty(1);
        Assertions.assertNotNull(faculty);

        Mockito.when(facultyRepository.findById(1l)).thenReturn(Optional.ofNullable(null));
        faculty = facultyService.deleteFaculty(1);
        Assertions.assertNull(faculty);
    }


    @Test
    public void addThreeFacultiesAndGetCollectionByColor() {
        addThreeFacultiesAndCompareThem();

        Collection<Faculty> facultys = facultyService.getFacultiesByColor(FACULTY_1.getColor());
        Assertions.assertNotNull(facultys);
//        Assertions.assertEquals(2, facultys.size());
//        Assertions.assertTrue(facultys.contains(FACULTY_1));
//        Assertions.assertTrue(facultys.contains(FACULTY_3));
//        Assertions.assertFalse(facultys.contains(FACULTY_2));

    }

    @Test
    public void addOneFacultyAndCompareStrings() {
        Faculty faculty = addFaculty(FACULTY_1);
        Assertions.assertTrue(faculty.toString().equals(FACULTY_1.toString()));
    }


    @Test
    public void getStudentsOfFaculty() {
        Faculty faculty = addFaculty(FACULTY_1);

        Student student = new Student("Economy", 1);
        Collection<Student> students = new HashSet<>();
        students.add(student);
        faculty.setStudents((Set<Student>) students);

        Mockito.when(facultyRepository.findById(FACULTY_1.getId())).thenReturn(null);
        Assertions.assertThrows(NullPointerException.class, ()-> facultyService.getStudentsOfFaculty(FACULTY_1.getId()));

        Mockito.when(facultyRepository.findById(FACULTY_1.getId())).thenReturn(Optional.ofNullable(FACULTY_1));
        Assertions.assertEquals(students, facultyService.getStudentsOfFaculty(FACULTY_1.getId()));
    }


    @Test
    public void getFacultiesByNameIgnoreCaseOrColorIgnoreCase() {
        Faculty faculty = addFaculty(FACULTY_1);
        Collection<Faculty> faculties = new HashSet<>();
        faculties.add(faculty);

        Mockito.when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(FACULTY_1.getName(), FACULTY_1.getColor()) ).thenReturn(faculties);
        Assertions.assertEquals(faculties, facultyService.getFacultiesByNameIgnoreCaseOrColorIgnoreCase(FACULTY_1.getName(), FACULTY_1.getColor()));

        Mockito.when(facultyRepository.findByNameIgnoreCase(FACULTY_1.getName())).thenReturn(faculties);
        Assertions.assertEquals(faculties, facultyService.getFacultiesByNameIgnoreCaseOrColorIgnoreCase(FACULTY_1.getName(), null));

        Mockito.when(facultyRepository.findByColorIgnoreCase(FACULTY_1.getColor())).thenReturn(faculties);
        Assertions.assertEquals(faculties, facultyService.getFacultiesByNameIgnoreCaseOrColorIgnoreCase(null, FACULTY_1.getColor()));

        Assertions.assertNull(facultyService.getFacultiesByNameIgnoreCaseOrColorIgnoreCase(null, null));
    }
}
*/
