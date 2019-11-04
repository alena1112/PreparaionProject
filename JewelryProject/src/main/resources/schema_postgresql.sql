create table shop (id SERIAL PRIMARY KEY, name varchar(100));
create table material (id SERIAL PRIMARY KEY, name varchar(100), price decimal, shop_id int, FOREIGN KEY (shop_id) REFERENCES shop(id));
create table jewelry (id SERIAL PRIMARY KEY, name varchar(100), price decimal, description varchar(500), type varchar(50), imageUrl varchar(50));
create table jewelry_material (jewelry_id int not null, material_id int not null, FOREIGN KEY (jewelry_id) REFERENCES jewelry(id), FOREIGN KEY (material_id) REFERENCES material(id));

create table promotional_code (
  id SERIAL PRIMARY KEY,
  code varchar(20) not null,
  is_active BOOLEAN not null,
  promocode_type varchar(10) not null,
  value decimal not null,
  max_uses_number int,
  current_uses_number int,
  expiration_date timestamp
);

create table user_order (
  id SERIAL PRIMARY KEY,
  promocode_id int,
  delivery_type varchar(20) not null,
  payment_type varchar(20) not null,
  first_name varchar(50),
  last_name varchar(50),
  patronymic varchar(50),
  phone varchar(50),
  email varchar(50),
  city varchar(50),
  address varchar(100),
  post_index varchar(50),
  FOREIGN KEY (promocode_id) REFERENCES promotional_code(id)
);

create table order_jewelry (
  order_id int not null,
  jewelry_id int not null,
  FOREIGN KEY (order_id) REFERENCES user_order(id),
  FOREIGN KEY (jewelry_id) REFERENCES jewelry(id)
);

insert into jewelry(name, price, description, imageUrl) values ('Graceful shells', 0, 'Яркие ассиметричные серьги с кристальным жемчугом Swarovski, позолоченной фурнитурой и гипоаллегренными швензами. Ракушки-коннтекторы со вставками из фианитов', '1.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful flowers', 0, 'Нежные серьги со стеклянными бутонами лэмпворк', '2.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful metal', 0, 'Позолоченные серьги мятый металл с эмалью', '3.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful pearls', 0, 'Барочный жемчуг качества Grade AA, позолоченная фурнитура, бабочки с фианитами', '4.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '5.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '6.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '7.jpg');
insert into jewelry(name, price, description, imageUrl) values ('Graceful ...', 0, '...', '8.jpg');