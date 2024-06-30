select s.name, s.age, f."name"
from student s
left join faculty  f on f.id = s.faculty_id;


select s.*, a.*
  from student s
  inner join  avatars a on a.student_id = s.id;
