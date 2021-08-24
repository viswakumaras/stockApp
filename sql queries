create table products(
	product_id int primary key,
	item_name varchar(100) not null,
	category varchar (100) not null,
	price int not null,
	available int not null,
	unique(product_id)
);



create table user_details(
	user_name varchar(100)not null,
	user_pass varchar(100) not null,
	user_role varchar(10)not null,
	user_id serial primary key,
	user_status bool not null,
	unique(user_id)
);


delete from user_details;

delete from order_items;
delete from orders;


create table orders(
    order_id int primary key,
	user_id int,
	FOREIGN KEY(user_id) REFERENCES user_details(user_id),
	status int not null
);

select*from user_details;

select item_name from products where available

select max(quantity) from order_items ;

select *from orders;
select *from orders;

select max(order_id) from orders where user_id=10;

select product_id from order_items where product_id=1 and order_id=1;

select product_id,quantity,order_item from order_items where order_id=2;
select max(order_id) from orders where user_id=1;

create table order_items(
 product_id int not null,
 quantity 	int not null,
 order_item int primary key,
 order_id int,
 FOREIGN KEY (order_id) REFERENCES orders(order_id),
  FOREIGN KEY (product_id) REFERENCES products(product_id)	
);

select * from order_items;

insert into order_items(order_id,user_id,product_id,quantity,status)
values(1,1,2,5,0);


insert into product(product_id,item_name,category,price,available)
values(1,'dove','conditiner',20,100);

insert into product(product_id,item_name,category,price,available)
values(2,'lux','soap',10,100);

insert into product(product_id,item_name,category,price,available)
values(3,'maggi','food',5,100);

insert into product(product_id,item_name,category,price,available)
values(4,'maidha','food',80,100);

insert into user_details(user_name,user_pass,user_role,user_id,user_status)
values('viswa',pass,)

select * from orders;

drop table user_details cascade;
