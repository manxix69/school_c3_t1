package ru.manxix69.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.repository.FacultyRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService{

    @Autowired
    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }
    @Override
    public Faculty editFaculty(long id, Faculty faculty) {
        if (facultyRepository.findById(id).isEmpty()) {
            return null;
        }
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty deleteFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id).get();
        facultyRepository.deleteById(id);
        return faculty;
    }
    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColorEquals(color);
    }
}
