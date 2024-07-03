-- liquibase formatted sql

-- changeset sergey:1
create index index_student_name on student (name);

-- changeset sergey:2
create index index_faculty_name on faculty( name, color);