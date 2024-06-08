package ru.manxix69.school.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "avatars")
public class Avatar {
    @Id
    @GeneratedValue
    private Long id;
    private String filePath;
    private long fileSize;
    private String MediaType;
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Avatar(Long id, String filePath, long fileSize, String mediaType, Student student, byte[] data) {
        this.id = id;
        this.filePath = filePath;
        this.fileSize = fileSize;
        MediaType = mediaType;
        this.student = student;
        this.data = data;
    }

    public Avatar() {
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMediaType() {
        return MediaType;
    }

    public void setMediaType(String mediaType) {
        MediaType = mediaType;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                " id=" + id +
//                ", data=" + Arrays.toString(data) +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", MediaType='" + MediaType + '\'' +
                ", student=" + student +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return Objects.equals(id, avatar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
