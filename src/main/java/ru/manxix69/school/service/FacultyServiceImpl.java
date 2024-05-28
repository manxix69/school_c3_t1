package ru.manxix69.school.service;

import org.springframework.stereotype.Service;
import ru.manxix69.school.model.Faculty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService{
    private Map<Long, Faculty> faculties = new HashMap<>();

    private long count = 0L;
    @Override
    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++count);
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    @Override
    public Faculty findFaculty(long id) {
        return faculties.get(id);
    }
    @Override
    public Faculty editFaculty(long id, Faculty faculty) {
        if (!faculties.containsKey(id)) {
            return null;
        }
        faculties.put(id, faculty);
        return faculty;
    }
    @Override
    public Faculty deleteFaculty(long id) {
        return faculties.remove(id);
    }
    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        return faculties
                .values()
                .stream()
                .filter(faculty -> faculty.getColor() == color)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
