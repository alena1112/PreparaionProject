create table shop (
  id int not null AUTO_INCREMENT,
  name varchar(100),
  PRIMARY KEY (id)
);

create table material_order (
  id int not null AUTO_INCREMENT,
  delivery_price double,
  shop_id int not null,
  purchase_date DATETIME,
  PRIMARY KEY (id),
  FOREIGN KEY (shop_id) REFERENCES shop(id)
);

create table material (
  id int not null AUTO_INCREMENT,
  name varchar(1000),
  price double,
  unit_price_with_delivery double,
  number int,
  image_url varchar(300),
  material_order_id int not null,
  PRIMARY KEY (id),
  FOREIGN KEY (material_order_id) REFERENCES material_order(id)
);

create table jewelry (
  id int not null AUTO_INCREMENT,
  name varchar(100),
  price double,
  description varchar(500),
  type varchar(50),
  material_description varchar(500),
  size varchar(500),
  weight varchar(500),
  is_sold tinyint(1) not null default 0,
  is_hide tinyint(1) not null default 0,
  created_date DATETIME not null default now(),
  PRIMARY KEY (id)
);

create table image (
  id int not null AUTO_INCREMENT,
  name varchar(100) UNIQUE,
  jewelry_id int not null,
  img_index int not null,
  PRIMARY KEY (id),
  FOREIGN KEY (jewelry_id) REFERENCES jewelry(id)
);

create table jewelry_material (
  jewelry_id int not null,
  material_id int not null,
  FOREIGN KEY (jewelry_id) REFERENCES jewelry(id),
  FOREIGN KEY (material_id) REFERENCES material(id)
);

create table promotional_code (
  id int not null AUTO_INCREMENT,
  code varchar(20) not null,
  is_active tinyint(1) not null,
  promocode_type varchar(10) not null,
  value double not null,
  max_uses_number int,
  current_uses_number int not null default 0,
  expiration_date DATETIME,
  max_jewelries int,
  PRIMARY KEY (id)
);

create table user_order (
  id int not null AUTO_INCREMENT,
  created_date DATETIME not null default now(),
  promocode_id int,
  delivery_type varchar(20) not null,
  payment_type varchar(50) not null,
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
  FOREIGN KEY (promocode_id) REFERENCES promotional_code(id)
);

create table order_jewelry (
  order_id int not null,
  jewelry_id int not null,
  FOREIGN KEY (order_id) REFERENCES user_order(id),
  FOREIGN KEY (jewelry_id) REFERENCES jewelry(id)
);

create table settings (
  id int not null AUTO_INCREMENT,
  s_key varchar(100) not null,
  s_value varchar(100) not null,
  description varchar(300),
  PRIMARY KEY (id)
);

create table email_message (
  id int not null AUTO_INCREMENT,
  message varchar(1000) not null,
  type varchar(10) not null,
  PRIMARY KEY (id)
);

create table emails_log (
  id int not null AUTO_INCREMENT,
  created_date DATETIME not null default now(),
  message varchar(1000),
  from_email varchar(50) not null,
  to_email varchar(50) not null,
  PRIMARY KEY (id)
);