create table shop (
  id SERIAL PRIMARY KEY,
  name varchar(100)
);

create table material (
  id SERIAL PRIMARY KEY,
  name varchar(100),
  price decimal,
  shop_id int,
  FOREIGN KEY (shop_id) REFERENCES shop(id)
);

create table image (
  id SERIAL PRIMARY KEY,
  path varchar(300)
);

create table jewelry (
  id SERIAL PRIMARY KEY,
  name varchar(100),
  price decimal,
  description varchar(500),
  main_image_id int,
  imageUrl varchar(50),
  material_description varchar(500),
  size varchar(500),
  weight varchar(500),
  is_sold BOOLEAN not null default false,
  is_hide BOOLEAN not null default false,
  FOREIGN KEY (main_image_id) REFERENCES image(id)
);

create table jewelry_material (
  jewelry_id int not null,
  material_id int not null,
  FOREIGN KEY (jewelry_id) REFERENCES jewelry(id),
  FOREIGN KEY (material_id) REFERENCES material(id)
);

create table jewelry_image (
  jewelry_id int not null,
  image_id int not null,
  FOREIGN KEY (jewelry_id) REFERENCES jewelry(id),
  FOREIGN KEY (image_id) REFERENCES image(id)
);

create table promotional_code (
  id SERIAL PRIMARY KEY,
  code varchar(20) not null,
  is_active BOOLEAN not null,
  promocode_type varchar(10) not null,
  value decimal not null,
  max_uses_number int,
  current_uses_number int not null default 0,
  expiration_date timestamp
);

create table user_order (
  id SERIAL PRIMARY KEY,
  promocode_id int,
  delivery_type varchar(20) not null,
  payment_type varchar(50) not null,
  delivery_cost decimal,
  discount decimal,
  total_cost decimal,

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