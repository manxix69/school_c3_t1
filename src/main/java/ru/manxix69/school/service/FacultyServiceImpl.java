package ru.manxix69.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method addFaculty : faculty={}", faculty);
        if (faculty.getId() != null) {
            logger.error("An error occurred because faculty have id={}", faculty.getId());
            throw new NotNullIdException("При создании нового фальтета не должно быть указано id в переданном запросе на сервер!");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        logger.info("Was invoked method findFaculty : id={}", id);
        Faculty faculty = facultyRepository.findById(id).orElse(null);
        if (faculty == null) {
            logger.error("An error occurred because faculty not found by id={}", id);
            throw new NotFoundFacultyByIdException("Факультет не найден по ID!");
        }
        logger.debug("faculty={}", faculty);
        return faculty;
    }
    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info("Was invoked method editFaculty : faculty={}", faculty);
        findFaculty(faculty.getId());
        return facultyRepository.save(faculty);
    }
    @Override
    public Faculty deleteFaculty(long id) {
        logger.info("Was invoked method deleteFaculty : id={}", id);
        Faculty faculty = findFaculty(id);
        facultyRepository.deleteById(id);
        return faculty;
    }

    @Override
    public Collection<Student> getStudentsOfFaculty(long id) {
        logger.info("Was invoked method getStudentsOfFaculty : id={}", id);
        Faculty faculty = findFaculty(id);
        return faculty.getStudents();
    }

    @Override
    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.info("Was invoked method getFacultiesByColor : color={}", color);
        Collection<Faculty> faculties = facultyRepository.findByColorEquals(color);
        logger.debug("Number of faculties found = {}", faculties.size());
        return faculties;
    }

    @Override
    public Collection<Faculty> getFacultiesByNameIgnoreCaseOrColorIgnoreCase(String name, String color) {
        logger.info("Was invoked method getFacultiesByColor : name={},color={}", name,color);
        Collection<Faculty> foundFaculties = new HashSet<>();
        if (       name  != null && !name.isBlank()
                && color != null && !color.isBlank() ) {
            foundFaculties = facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(name, color);
        } else if (name  != null && !name.isBlank()) {
            foundFaculties = facultyRepository.findByNameIgnoreCase(name);
        } else if (color != null && !color.isBlank() ) {
            foundFaculties = facultyRepository.findByColorIgnoreCase(color);
        }
        logger.debug("Found faculties by parameters name/color: {}", foundFaculties.size());
        return foundFaculties ;
    }

    @Override
    public String maxLongNameOfFaculty() {
        logger.info("Was invoked method maxLongNameOfFaculty.");
        String maxLongName = facultyRepository.findAll()
                .stream()
                .parallel()
                .map(f -> f.getName())
                .max(Comparator.comparing(String::length))
                .get();
        logger.debug("Found longest name : {}", maxLongName);
        return maxLongName;
    }
}
