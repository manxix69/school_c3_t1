package ru.manxix69.school.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.findStudent(id));
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getStudentFaculty(@PathVariable Long id) {
        Faculty faculty = studentService.getFacultyOfStudent(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping()
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.editStudent(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.deleteStudent(id));
    }

    @GetMapping("by-age")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam Integer age) {
        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }

    @GetMapping("between-age")
    public ResponseEntity<Collection<Student>> getStudentsBetweenAge(@RequestParam Integer minAge,
                                                                     @RequestParam Integer maxAge) {
        return ResponseEntity.ok(studentService.getStudentsBetweenAge(minAge, maxAge));
    }

    @GetMapping("count")
    public ResponseEntity<Integer> getCountStudents() {
        return ResponseEntity.ok(studentService.getCountStudents());
    }

    @GetMapping("average-age")
    public ResponseEntity<Integer> getAverageAgeStudents() {
        return ResponseEntity.ok(studentService.getAverageAgeStudents());
    }

    @GetMapping("last")
    public ResponseEntity<Collection<Student>> getLastStudents() {
        return ResponseEntity.ok(studentService.getLastStudents());
    }

    @GetMapping("by-names/{nameStarts}")
    public ResponseEntity<Collection<String>> getLastStudents(@PathVariable String nameStarts) {
        return ResponseEntity.ok(studentService.getAllNamesOfStudents(nameStarts));
    }

    @GetMapping("print-parallel")
    public ResponseEntity<Void> printParallelStudents() {
        studentService.printParallelStudents(6);
        return ResponseEntity.ok().build();
    }

    @GetMapping("print-synchronized")
    public ResponseEntity<Void> printSynchronizedStudents() {
        studentService.printSynchronizedStudents(6);
        return ResponseEntity.ok().build();
    }
}
