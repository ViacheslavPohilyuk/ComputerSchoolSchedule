INSERT INTO ComputerSchoolSchedule.course_lectures (course_id,lecture_id) VALUES 
(1,3)
,(1,10)
,(1,13)
,(1,14)
,(1,15)
,(1,16)
,(1,17)
;

INSERT INTO ComputerSchoolSchedule.courses (id,title) VALUES 
(1,'java12')
,(2,'sql3')
,(3,'english6')
;

INSERT INTO ComputerSchoolSchedule.lecture_rooms (id,floor,`number`,description) VALUES 
(1,2,203,'left one')
,(2,2,203,'right one')
,(3,2,204,'left one')
,(4,2,204,'right one')
;

INSERT INTO ComputerSchoolSchedule.lectures (id,start_time,end_time,lecture_room_id,title,description) VALUES 
(3,'2017-02-25 23:40:53.000','2017-02-04 00:01:27.000',4,'title2','decription')
,(10,'2017-02-23 19:15:00.000','2017-02-23 21:15:00.000',3,'Introduction to Java','Java basics')
,(13,'2016-12-25 18:15:00.000','2016-12-25 20:15:00.000',3,'Generics','geneic classes')
,(14,'2016-12-27 20:15:00.000','2016-12-27 22:15:00.000',3,'Maven','deploy web application')
,(15,'2016-12-29 13:15:00.000','2016-12-29 15:15:00.000',3,'Testing','JUnit')
,(16,'2016-12-30 13:15:00.000','2016-12-30 15:15:00.000',3,'Web Frameworks','Spring')
,(17,'2016-12-31 12:15:00.000','2016-12-31 12:15:00.000',3,'JavaEE','Create servlet')
;

INSERT INTO ComputerSchoolSchedule.subscriptions (course_id,user_id) VALUES 
(1,60)
,(1,61)
,(2,61)
,(1,62)
,(1,63)
,(2,63)
,(1,66)
,(2,66)
,(3,66)
;

INSERT INTO ComputerSchoolSchedule.users (id,fname,surname,patronymic,user_type,email,user_password) VALUES 
(60,'Vasia','Pupkin','Vasilevich','student','vasia93@gmail.com','qwerty123')
,(61,'Petya','Durkin','Petrovich','lecturer','petya83@gmail.com','123456789')
,(66,'Kolia','Sirko','Vladimirovich','student','kolianSirko@gmail.com','wsxedcrfv')
;