package ru.manxix69.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.manxix69.school.model.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Avatar findByStudentId(Long id);
}
