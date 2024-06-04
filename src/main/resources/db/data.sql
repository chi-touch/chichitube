truncate table users cascade;
truncate table media cascade;
insert into users(id,email,password,time_created)values
                                                     (200,'john@gmail.com','password','2024-06-04T15:03:03.792009700'),
                                                     (201,'johny@gmail.com','password','2024-06-04T15:03:03.792009700'),
                                                     (202,'johni@gmail.com','password','2024-06-04T15:03:03.792009700'),
                                                     (203,'johnp@gmail.com','password','2024-06-04T15:03:03.792009700');

insert into media(id,category,description,url,time_created) values
(1000,'ACTION','media 0','https://www.cloudinary.com/media1','2024-06-04T15:03:03.792009700'),
(t,'ACTION','media 1','https://www.cloudinary.com/media1','2024-06-04T15:03:03.792009700'),
(1002,'ACTION','media 2','https://www.cloudinary.com/media1','2024-06-04T15:03:03.792009700'),
(1003,'ACTION','media 3','https://www.cloudinary.com/media1','2024-06-04T15:03:03.792009700'),
(1004,'ACTION','media 4','https://www.cloudinary.com/media1','2024-06-04T15:03:03.792009700'),
(1005,'ACTION','media 5','https://www.cloudinary.com/media1','2024-06-04T15:03:03.792009700');
