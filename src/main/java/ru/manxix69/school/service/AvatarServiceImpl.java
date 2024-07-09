package ru.manxix69.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.manxix69.school.exception.NotFoundAvatarByStudentIdException;
import ru.manxix69.school.exception.PageArgumentException;
import ru.manxix69.school.exception.SizeArgumentException;
import ru.manxix69.school.model.Avatar;
import ru.manxix69.school.model.Student;
import ru.manxix69.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService{

    private final AvatarRepository avatarRepository;
    private final StudentService studentService;

    private Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    public AvatarServiceImpl(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private Avatar findOrCreateAvatar(Long studentId) {
        logger.info("Was invoked method findOrCreateAvatar student : {}", studentId);
        Avatar avatar = avatarRepository.findByStudentId(studentId);
        if (avatar == null) {
            logger.debug("Avatar not exists. A new Avatar will be returned.");
            return new Avatar();
        } else {
            logger.debug("Avatar, with studentId = {}, {}", studentId, avatar);
            return avatar;
        }
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method findAvatar student : {}", studentId);
        Avatar avatar = avatarRepository.findByStudentId(studentId);
        if (avatar == null){
            logger.error("Avatar not exists for student : {}", studentId);
            throw new NotFoundAvatarByStudentIdException("Аватар по id студента не найден в БД!");
        }
        logger.debug("Avatar, with studentId = {}, {}", studentId, avatar);
        return avatar;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method uploadAvatar : studentId={} avatarFile.getName()={}", studentId,avatarFile.getName());

        Student student = studentService.findStudent(studentId);
        String filName = student + "." + getExtensions(avatarFile.getOriginalFilename());
        Path filePath = Path.of(avatarsDir, filName);
        logger.debug("filePath={}",filePath);
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        } catch (IOException e) {
            logger.error("An error occurred while transferring the file. {}{} , message", avatarsDir,filName,e.getMessage());
            throw e;
        }

        Avatar avatar = findOrCreateAvatar(studentId);

        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
        logger.info("Avatar was saved.");
    }

    @Override
    public Collection<Avatar> findAllAvatars(Integer page, Integer size) {
        logger.info("Was invoked method uploadAvatar : page={} size={}", size);
        if (page < 1 ) {
            logger.error("An error occurred because page less than 1.");
            throw new PageArgumentException("Номер страницы не может быть меньше 1!");
        } else if (size < 1) {
            logger.error("An error occurred because size less than 1.");
            throw new SizeArgumentException("количество автарок не может быть меньше 1!");
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        logger.debug("pageRequest={}", pageRequest);
        return avatarRepository.findAll(pageRequest).getContent();
    }

    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
