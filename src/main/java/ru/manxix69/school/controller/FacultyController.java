package ru.manxix69.school.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty student) {
        return facultyService.createFaculty(student);
    }

    @GetMapping("{id}")
    public Faculty getFaculty(@PathVariable long id) {
        return facultyService.findFaculty(id);
    }

    @PutMapping
    public Faculty updateFaculty(@RequestBody Faculty student) {
        return facultyService.updateFaculty(student);
    }

    @DeleteMapping("{id}")
    public Faculty deleteFaculty(@PathVariable long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping()
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(String color) {
        return ResponseEntity.ok(facultyService.getFacultysByAge(color));
    }
}
