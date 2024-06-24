package ru.manxix69.school.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.service.FacultyService;
import ru.manxix69.school.service.FacultyServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyInfo(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.findFaculty(id));
    }

    @PutMapping()
    public ResponseEntity<Faculty> editFaculty(@RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.editFaculty(faculty));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.deleteFaculty(id));
    }

    @GetMapping("by-color/{color}")
    public ResponseEntity<Collection<Faculty>> getFacultiesByColor(@PathVariable String color) {
        return ResponseEntity.ok(facultyService.getFacultiesByColor(color));
    }

    @GetMapping("{id}/students")
    public ResponseEntity<Collection<Student>> getFacultiesByColor(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getStudentsOfFaculty(id));
    }

    @GetMapping("by-name-or-color")
    public ResponseEntity<Collection<Faculty>> getFacultiesByNameIgnoreCaseOrColorIgnoreCase(@RequestParam(required = false) String name
                                                                                            , @RequestParam(required = false) String color) {
        return ResponseEntity.ok(facultyService.getFacultiesByNameIgnoreCaseOrColorIgnoreCase(name, color));
    }
}
