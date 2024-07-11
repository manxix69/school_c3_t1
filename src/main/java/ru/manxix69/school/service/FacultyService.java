package ru.manxix69.school.service;

import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    Faculty deleteFaculty(long id);

    Collection<Student> getStudentsOfFaculty(long id);

    Collection<Faculty> getFacultiesByColor(String color);

    Collection<Faculty> getFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color);

    String maxLongNameOfFaculty();
}
