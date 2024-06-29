package ru.manxix69.school.service;

import org.springframework.web.multipart.MultipartFile;
import ru.manxix69.school.model.Avatar;

import java.io.IOException;
import java.util.Collection;

public interface AvatarService {


    Avatar findAvatar(Long studentId);

    void uploadAvatar(Long studentId, MultipartFile avatar) throws IOException;

    Collection<Avatar> findAllAvatars(Integer page, Integer size);
}
