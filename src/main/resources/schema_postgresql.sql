create table shop (
  id SERIAL PRIMARY KEY,
  name varchar(100)
);

create table material_order (
  id SERIAL PRIMARY KEY,
  delivery_price decimal,
  shop_id int not null,
  purchase_date timestamp,
  FOREIGN KEY (shop_id) REFERENCES shop(id)
);

create table material (
  id SERIAL PRIMARY KEY,
  name varchar(1000),
  price decimal,
  unit_price_with_delivery decimal,
  number int,
  image_url varchar(300),
  material_order_id int not null,
  FOREIGN KEY (material_order_id) REFERENCES material_order(id)
);

create table jewelry (
  id SERIAL PRIMARY KEY,
  name varchar(100),
  price decimal,
  description varchar(500),
  type varchar(50),
  material_description varchar(500),
  size varchar(500),
  weight varchar(500),
  is_sold BOOLEAN not null default false,
  is_hide BOOLEAN not null default false,
  created_date TIMESTAMPTZ not null DEFAULT Now()
);

create table image (
  id SERIAL PRIMARY KEY,
  name varchar(100) UNIQUE,
  jewelry_id int not null,
  img_index int not null,
  FOREIGN KEY (jewelry_id) REFERENCES jewelry(id)
);

create table jewelry_material (
  jewelry_id int not null,
  material_id int not null,
  FOREIGN KEY (jewelry_id) REFERENCES jewelry(id),
  FOREIGN KEY (material_id) REFERENCES material(id)
);

create table promotional_code (
  id SERIAL PRIMARY KEY,
  code varchar(20) not null,
  is_active BOOLEAN not null,
  promocode_type varchar(10) not null,
  value decimal not null,
  max_uses_number int,
  current_uses_number int not null default 0,
  expiration_date timestamp,
  max_jewelries int
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

create table settings (
  id SERIAL PRIMARY KEY,
  s_key varchar(100) not null,
  s_value varchar(100) not null,
  description varchar(300)
);

create table email_message (
  id SERIAL PRIMARY KEY,
  message varchar(1000) not null,
  type varchar(10) not null
);