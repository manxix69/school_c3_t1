package ru.manxix69.school.service;

import ru.manxix69.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(long id, Faculty faculty);

    Faculty deleteFaculty(long id);

    Collection<Faculty> getFacultiesByAge(String color);
}
