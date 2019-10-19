create table shop (id SERIAL PRIMARY KEY, name varchar(100));
create table material (id SERIAL PRIMARY KEY, name varchar(100), price decimal, shop_id int, FOREIGN KEY (shop_id) REFERENCES shop(id));
create table jewelry (id SERIAL PRIMARY KEY, name varchar(100), price decimal, description varchar(500), imageUrl varchar(50));
create table jewelry_material (jewelry_id int not null, material_id int not null, FOREIGN KEY (jewelry_id) REFERENCES jewelry(id), FOREIGN KEY (material_id) REFERENCES material(id));

insert into jewelry(name, price, description, imageUrl) values ('Graceful shells', 0, 'Яркие ассиметричные серьги с кристальным жемчугом Swarovski, позолоченной фурнитурой и гипоаллегренными швензами. Ракушки-коннтекторы со вставками из фианитов', '1.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful flowers', 0, 'Нежные серьги со стеклянными бутонами лэмпворк', '2.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful metal', 0, 'Позолоченные серьги мятый металл с эмалью', '3.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful pearls', 0, 'Барочный жемчуг качества Grade AA, позолоченная фурнитура, бабочки с фианитами', '4.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '5.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '6.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '7.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '8.jpg');