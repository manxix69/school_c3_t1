package ru.manxix69.school.service;

import org.springframework.stereotype.Service;
import ru.manxix69.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private Map<Long, Faculty> faculties = new HashMap<>();

    private long idFaculty = 0L;

    public Faculty createFaculty(Faculty faculty) {
        faculty.setId(++idFaculty);
        faculties.put(idFaculty, faculty);
        return faculty;
    }

    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }

    public Collection<Faculty> getFacultysByAge(String color) {
        return faculties
                .values()
                .stream()
                .filter(faculty -> faculty.getColor() == color)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
