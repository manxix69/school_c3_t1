create table car(
	id bigint primary key,
	make text,
	model text,
	cost numeric(38,2)
);
create table man (
	id bigint primary key,
	name text not null,
	age integer check(age > 0),
	driver_s_license boolean,
	car_id bigint references car(id)
);