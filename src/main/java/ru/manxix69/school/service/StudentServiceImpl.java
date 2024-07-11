package ru.manxix69.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.manxix69.school.exception.NotFoundStudentByIdException;
import ru.manxix69.school.exception.NotNullIdException;
import ru.manxix69.school.model.Faculty;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private final StudentRepository studentRepository;
    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Was invoked method addStudent : {}", student);
        if (student.getId() != null) {
            logger.error("An error occurred because student have id={}", student.getId());
            throw new NotNullIdException("Не должен быть указан id студента в переданном запросе на сервер!");
        }
        return studentRepository.save(student);
    }
    @Override
    public Student findStudent(Long id) {
        logger.info("Was invoked method findStudent : id={}", id);
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) {
            logger.error("Student not found by id={}", id);
            throw new NotFoundStudentByIdException("По переданному Id студент не найден в БД!");
        }
        logger.debug("Was find student : {}", student);
        return student;
    }
    @Override
    public Student editStudent(Student student) {
        logger.info("Was invoked method editStudent : {}", student);
        findStudent(student.getId());
        return studentRepository.save(student);
    }
    @Override
    public Student deleteStudent(long id) {
        logger.info("Was invoked method deleteStudent : id={}", id);
        Student student = findStudent(id);
        studentRepository.deleteById(id);
        logger.debug("Student was deleted: {}", student);
        return student;
    }

    @Override
    public Faculty getFacultyOfStudent(long id) {
        logger.info("Was invoked method getFacultyOfStudent : id={}", id);
        Student student= findStudent(id);
        return student.getFaculty();
    }

    @Override
    public Collection<Student> getStudentsByAge(int age) {
        logger.info("Was invoked method getStudentsByAge : age={}", age);
        Collection<Student> students = studentRepository.findByAgeEquals(age);
        logger.debug("Students were founded by age: {}", students.size());
        return students;
    }

    @Override
    public Collection<Student> getStudentsBetweenAge(int minAge, int maxAge) {
        logger.info("Was invoked method getStudentsBetweenAge : minAge={}, maxAge={}", minAge, maxAge);
        Collection<Student> students = studentRepository.findByAgeBetween(minAge, maxAge);
        logger.debug("Students were founded by minAge and maxAge: {}", students.size());
        return students;
    }

    @Override
    public Integer getCountStudents() {
        logger.info("Was invoked method getCountStudents.");
        Integer countStudents = studentRepository.getCountStudents();
        logger.debug("CountStudents were founded: {}", countStudents);
        return countStudents;
    }

    @Override
    public Integer getAverageAgeStudents() {
        logger.info("Was invoked method getAverageAgeStudents.");
        Integer averageAgeStudents = (int) studentRepository.findAll()
                .stream()
                .parallel()
                .flatMapToInt(student -> IntStream.of(student.getAge()))
                .average().orElse(0.0);
        logger.debug("Students have averageAge: {}", averageAgeStudents);
        return averageAgeStudents;
    }

    @Override
    public Collection<Student> getLastStudents() {
        logger.info("Was invoked method getLastStudents.");
        Collection<Student> lastStudents = studentRepository.getLastStudents();
        logger.debug("Students count lasts: lastStudents={}", lastStudents);
        return lastStudents;
    }

    @Override
    public Collection<String> getAllNamesOfStudents(String nameStarts) {
        logger.info("Was invoked method findAll.");
        List<Student> students = studentRepository.findAll();
        logger.debug("Students count: {}", students.size());
        List<String> names = students
                .stream()
                .parallel()
                .map(s -> s.getName().toUpperCase())
                .filter(name -> name.toUpperCase().startsWith(nameStarts.toUpperCase()))
                .sorted()
                .toList();
        logger.debug("Students names by nameStarts{} founded: {}", nameStarts, names.size());
        return names;
    }

    @Override
    public void printParallelStudents(int size) {
        logger.info("Was invoked method printParallelStudents :{}",size);
        List<String> namesList = getAllNamesOfStudents("").stream().limit(size).toList();

        Thread thread2 = createThreadForPrint("thread2", namesList.get(2),namesList.get(3));
        Thread thread3 = createThreadForPrint("thread3",namesList.get(4),namesList.get(5));

        thread2.start();
        thread3.start();

        System.out.println("threadMain=" + namesList.get(0));
        System.out.println("threadMain=" + namesList.get(1));
    }

    private Thread createThreadForPrint( String threadName, String name1, String name2 ) {
        logger.info("Was invoked method createThreadForPrint:{}",threadName);
        return new Thread(() -> {
            System.out.println(threadName + "=" + name1);
            System.out.println(threadName + "=" + name2);
        } );
    }

    @Override
    public void printSynchronizedStudents(int size) {
        logger.info("Was invoked method printSynchronizedStudents :{}",size);
        List<String> namesList = getAllNamesOfStudents("").stream().limit(size).toList();

        Thread thread2 = createThreadForSynchronizedPrint("thread2", namesList.get(2),namesList.get(3));
        Thread thread3 = createThreadForSynchronizedPrint("thread3",namesList.get(4),namesList.get(5));

        thread2.start();
        thread3.start();

        print("threadMain=" + namesList.get(0));
        print("threadMain=" + namesList.get(1));
    }
    private Thread createThreadForSynchronizedPrint( String threadName, String name1, String name2 ) {
        logger.info("Was invoked method createThreadForSynchronizedPrint:{}",threadName);
        return new Thread(() -> {
            print(threadName + "=" + name1);
            print(threadName + "=" + name2);
        } );
    }
    private synchronized void print(String text) {
        System.out.println(text);
    }

}
