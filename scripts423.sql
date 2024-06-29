select s.name, s.age, f."name"
from student s
join faculty  f on f.id = s.faculty_id;


select s.*
from student s
where exists (select 1 from avatars a where a.student_id = s.id);
