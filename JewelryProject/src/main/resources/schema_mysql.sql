create schema preparation_project;

create table preparation_project.shop (
  id int not null AUTO_INCREMENT,
  name varchar(100),
  PRIMARY KEY (id)
);

create table preparation_project.material (
  id int not null AUTO_INCREMENT,
  name varchar(100),
  price double,
  shop_id int,
  PRIMARY KEY (id),
  FOREIGN KEY (shop_id) REFERENCES preparation_project.shop(id)
);

create table preparation_project.jewelry (
  id int not null AUTO_INCREMENT,
  name varchar(100),
  price double,
  description varchar(500),
  type varchar(50),
  imageUrl varchar(50),
  PRIMARY KEY (id)
);

create table preparation_project.jewelry_material (
  jewelry_id int not null,
  material_id int not null,
  FOREIGN KEY (jewelry_id) REFERENCES preparation_project.jewelry(id),
  FOREIGN KEY (material_id) REFERENCES preparation_project.material(id)
);

create table preparation_project.promotional_code (
  id int not null AUTO_INCREMENT,
  code varchar(20) not null,
  is_active tinyint(1) not null,
  promocode_type varchar(10) not null,
  value double not null,
  max_uses_number int,
  current_uses_number int,
  expiration_date DATETIME,
  PRIMARY KEY (id)
);

create table preparation_project.user_order (
  id int not null AUTO_INCREMENT,
  promocode_id int,
  delivery_type varchar(20) not null,
  payment_type varchar(20) not null,
  delivery_cost double,
  discount double,
  total_cost double,

  first_name varchar(50),
  last_name varchar(50),
  patronymic varchar(50),
  phone varchar(50),
  email varchar(50),
  city varchar(50),
  address varchar(100),
  post_index varchar(50),
  PRIMARY KEY (id),
  FOREIGN KEY (promocode_id) REFERENCES preparation_project.promotional_code(id)
);

create table preparation_project.order_jewelry (
  order_id int not null,
  jewelry_id int not null,
  FOREIGN KEY (order_id) REFERENCES preparation_project.user_order(id),
  FOREIGN KEY (jewelry_id) REFERENCES preparation_project.jewelry(id)
);
