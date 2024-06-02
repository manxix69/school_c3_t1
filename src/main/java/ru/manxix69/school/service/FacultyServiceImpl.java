package ru.manxix69.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.repository.FacultyRepository;

import java.util.*;
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
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty == null) {
            return null;
        }
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Collection<Student> getStudentsOfFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(()-> new UnsupportedOperationException());
        return faculty.getStudents();
    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        return facultyRepository.findByColorEquals(color);
    }

    @Override
    public Collection<Faculty> getFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        if (       name  != null && !name.isBlank()
                && color != null && !color.isBlank() ) {
            facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        } else if (name  != null && !name.isBlank()) {
            facultyRepository.findByNameIgnoreCase(name);
        } else if (color != null && !color.isBlank() ) {
            facultyRepository.findByColorIgnoreCase(color);
        }
        return null;
    }
}
