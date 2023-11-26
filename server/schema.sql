create database medicines_db;
use medicines_db;

create table user (
  id integer primary key auto_increment,
  firstName varchar(1000),
  lastName varchar(1000),
  email varchar(1000) UNIQUE,
  password varchar(100),
  createdTimeStamp timestamp default CURRENT_TIMESTAMP
);

create table medicines (
  id integer primary key auto_increment,
  title varchar(1000),
  company varchar(1000),
  mrp float,
  price float,
  unit varchar(1000),
  expiryDate varchar(1000),
  thumbnail varchar(1000),
  createdTimeStamp timestamp default CURRENT_TIMESTAMP
);

create table orders (
  id integer primary key auto_increment,
  userId integer,
  datePlaced TIMESTAMP,
  total float,
  status integer,
  paymentMode varchar(1000),
  paymentDate varchar(1000),
  paymentAmount varchar(1000),
  createdTimeStamp timestamp default CURRENT_TIMESTAMP
);

create table orderDetails (
  id integer primary key auto_increment,
  orderId integer,
  medicineId integer,
  price float,
  quantity integer,
  total float,
  createdTimeStamp timestamp default CURRENT_TIMESTAMP
);


create table reminders (
  id integer primary key auto_increment,
  userId integer,
  medicineId integer,
  alarmDate TIMESTAMP,
  quantity integer,
  status integer default 0,
  note varchar(200),
  createdTimeStamp timestamp default CURRENT_TIMESTAMP
);