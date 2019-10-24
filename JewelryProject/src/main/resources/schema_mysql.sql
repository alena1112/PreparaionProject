create schema preparation_project;
create table preparation_project.shop (id int not null AUTO_INCREMENT, name varchar(100), PRIMARY KEY (id));
create table preparation_project.material (id int not null AUTO_INCREMENT, name varchar(100), price double, shop_id int, PRIMARY KEY (id), FOREIGN KEY (shop_id) REFERENCES shop(id));
create table preparation_project.jewelry (id int not null AUTO_INCREMENT, name varchar(100), price double, description varchar(500), type varchar(50), imageUrl varchar(50), PRIMARY KEY (id));
create table preparation_project.jewelry_material (jewelry_id int not null, material_id int not null, FOREIGN KEY (jewelry_id) REFERENCES jewelry(id), FOREIGN KEY (material_id) REFERENCES material(id));

insert into preparation_project.jewelry(name, price, description, imageUrl) values ('Graceful shells', 0, 'Яркие ассиметричные серьги с кристальным жемчугом Swarovski, позолоченной фурнитурой и гипоаллегренными швензами. Ракушки-коннтекторы со вставками из фианитов', '1.jpg');
insert into preparation_project.jewelry(name, price, description, imageUrl) values ('Graceful flowers', 0, 'Нежные серьги со стеклянными бутонами лэмпворк', '2.jpg');
insert into preparation_project.jewelry(name, price, description, imageUrl) values ('Graceful metal', 0, 'Позолоченные серьги мятый металл с эмалью', '3.jpg');
insert into preparation_project.jewelry(name, price, description, imageUrl) values ('Graceful pearls', 0, 'Барочный жемчуг качества Grade AA, позолоченная фурнитура, бабочки с фианитами', '4.jpg');
insert into preparation_project.jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '5.jpg');
insert into preparation_project.jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '6.jpg');
insert into preparation_project.jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '7.jpg');
insert into preparation_project.jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '8.jpg');

