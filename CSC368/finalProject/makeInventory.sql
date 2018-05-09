drop table inventory;

create table inventory(
	item_name	  varchar(100) not null,
	department	varchar(100) not null,
	size		    varchar(100) not null,
	price		    float        not null,
	upc		      integer      not null,
	amount		  integer      not null,
	primary key (upc)
);

insert into inventory
values ('Tomato Soup',	'Grocery', '15 oz', '2.99', '1234567890','4');
insert into inventory
values ('Paper Towels',	'Cleaning', '1 lb', '2.99', '34567324','8');
insert into inventory
values ('Beef',	'Butcher', '2 lb', '9.99', '1235324546','7');
