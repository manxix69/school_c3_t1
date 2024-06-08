package ru.manxix69.school.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;
import ru.manxix69.school.exception.NotFoundAvatarByStudentIdException;
import ru.manxix69.school.model.Avatar;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.repository.AvatarRepository;
import ru.manxix69.school.repository.StudentRepository;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
//@SpringBootTest(properties = {"path.to.avatars.folder= /avatars"})
@TestPropertySource(locations = "classpath:application-test.properties")
//@TestPropertySource(value = "${path.to.avatars.folder}")
//@TestPropertySource(properties = {"path.to.avatars.folder= /avatarstests"})
@ExtendWith(MockitoExtension.class)
public class AvatarServiceTests {
    @Autowired
    private AvatarService avatarService;
    @MockBean
    private StudentService studentService;
    @MockBean
    private AvatarRepository avatarRepository;
    @MockBean
    private StudentRepository studentRepository;

//    @BeforeEach
//    public void init() {
//        studentService = new StudentServiceImpl(studentRepository);
//        avatarService = new AvatarServiceImpl(avatarRepository, studentService);
//    }

    @Test
    public void shouldBeNotFindAvatar() {
        Student student = new Student();
        student.setAge(21);
//        Mockito.when(studentRepository.save(student)).thenReturn(student);
        studentService.addStudent(student);
        student.setId(1L);
        Assertions.assertThrows(NotFoundAvatarByStudentIdException.class, () -> avatarService.findAvatar(student.getId()));
    }
    @Test
    public void shouldBeFindAvatar() throws IOException {
        Student student = new Student("test",33);
        Mockito.when(studentService.addStudent(student)).thenReturn(student);

        student = studentService.addStudent(student);
        student.setId(1L);

        byte[] arr = new byte[]{1,2,3,4};
        MultipartFile multipartFile = new MockMultipartFile("TEST_FILE_NAME" , arr);

        Mockito.when(studentService.findStudent(1l)).thenReturn(student);
        Mockito.when(studentRepository.findById(1l)).thenReturn(Optional.of(student));

        Mockito.when(avatarRepository.findByStudentId(student.getId())).thenReturn(null);
        avatarService.uploadAvatar(student.getId(), multipartFile);

        Avatar avatar = new Avatar();
        avatar.setData(arr);
        Mockito.when(avatarRepository.findByStudentId(student.getId())).thenReturn(avatar);

        avatar = avatarService.findAvatar(student.getId());

        Assertions.assertEquals(Arrays.toString(avatar.getData()), Arrays.toString(arr));
    }

    @Test
    public void shouldBeNotFoundAvatarByStudentIdExceptionOrReturnAvatar() {
        Assertions.assertThrows(NotFoundAvatarByStudentIdException.class, () -> avatarService.findAvatar(-1l));

        Avatar avatar = new Avatar();

        Mockito.when(avatarRepository.findByStudentId(-1l)).thenReturn(avatar);
        Assertions.assertNotNull(avatarService.findAvatar(-1l));
    }
}

