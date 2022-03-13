create database if not exists web_student_tracker;
use web_student_tracker;
--
-- Table structure for table `student`
--
drop table if exists student;
create table student (
	id int not null auto_increment,
    first_name varchar(45) default null,
    last_name varchar(45) default null,
    email varchar(45) default null,
    primary key (id)
);
--
-- Dumping data for table `student`
--
lock tables student write;
insert into student values (1, 'Thang', 'Ngo','thang.nh175791@sis.hust.edu.vn'),
						     (2, 'Chi', 'Nguyen', 'chi.ntm175681@sis.hust.edu.vn'),
                             (3, 'Nga', 'Tran', 'nga.ttt175769@sis.hust.edu.vn'),
                             (4, 'Tien', 'Hoang', 'tien.hh175801@sis.hust.edu.vn'),
                             (5, 'Xuyen', 'Nguyen', 'xuyen.nt175834@sis.hust.edu.vn');
unlock tables;


