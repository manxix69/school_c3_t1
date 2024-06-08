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
//@TestPropertySource(properties = "classpath:test.properties")
//@TestPropertySource(value = "${path.to.avatars.folder}")
//@TestPropertySource(properties = {"path.to.avatars.folder= /avatarstests"})
@ExtendWith(MockitoExtension.class)
public class AvatarServiceTests {
    @Autowired
    private AvatarService avatarService;
    @Autowired
    private StudentService studentService;
    @Mock
    private AvatarRepository avatarRepository;
    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    public void init() {
        studentService = new StudentServiceImpl(studentRepository);
        avatarService = new AvatarServiceImpl(avatarRepository, studentService);
    }

    @Test
    public void shouldBeNotFindAvatar() {
        Student student = new Student();
        student.setAge(21);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        studentService.addStudent(student);
        student.setId(1L);
        Assertions.assertThrows(NotFoundAvatarByStudentIdException.class, () -> avatarService.findAvatar(student.getId()));
    }
    @Test
    public void shouldBeFindAvatar() throws IOException {
        Student student = new Student();
        student.setAge(33);
        Mockito.when(studentRepository.save(student)).thenReturn(student);
        studentService.addStudent(student);
        student.setId(1L);
        byte[] arr = new byte[]{1,2,3,4};

        MultipartFile multipartFile = new MockMultipartFile("TEST_FILE_NAME" , arr);

        Mockito.when(avatarRepository.findByStudentId(1L)).thenReturn(null);
        Mockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        // не смог найти способоа сделать по нормальному чтоб переменная из свойсв для тест бралась (всега почему то "avatarsDir = null")
        //поэтому пришлось только пользоваться тем чтоб не перезаписывать новые бины
//        Mockito.when(avatarService.)
//        Mockito.when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        avatarService.uploadAvatar(1L, multipartFile);
        Avatar avatar = avatarService.findAvatar(student.getId());

        Assertions.assertEquals(Arrays.toString(avatar.getData()), Arrays.toString(arr));

    }
}

