package ru.manxix69.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.manxix69.school.exception.NotFoundFacultyByIdException;
import ru.manxix69.school.exception.NotNullIdException;
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
        if (faculty.getId() != null) {
            throw new NotNullIdException("При создании нового фальтета не должно быть указано id в переданном запросе на сервер!");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty == null) {
            throw new NotFoundFacultyByIdException("Факультет не найден по ID!");
        }
        return faculty;
    }
    @Override
    public Faculty editFaculty(Faculty faculty) {
        findFaculty(faculty.getId());
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty deleteFaculty(long id) {
        Faculty faculty = findFaculty(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Collection<Student> getStudentsOfFaculty(long id) {
        Faculty faculty = findFaculty(id);
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
            return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        } else if (name  != null && !name.isBlank()) {
            return facultyRepository.findByNameIgnoreCase(name);
        } else if (color != null && !color.isBlank() ) {
            return facultyRepository.findByColorIgnoreCase(color);
        }
        return new HashSet<>() ;
    }
}
