select now();

create table IF NOT EXISTS customer (id bigint not null, customer_id varchar(10),first_name varchar(100),middle_name varchar(100),last_name varchar(100), primary key (id));
alter table customer drop constraint if exists UK_customer_id;
alter table customer add constraint UK_customer_id unique (customer_id);

insert into customer(id,customer_id,first_name,middle_name,last_name) values(1 ,'customer1','firstName1','MiddleName1','Lastname1');
insert into customer(id,customer_id,first_name,last_name) values(2 ,'customer2','firstName2','Jangid');
insert into customer(id,customer_id,first_name,middle_name,last_name) values(3 ,'customer3','firstName3','MiddleName1','Lastname2');
insert into customer(id,customer_id,first_name,middle_name,last_name) values(4 ,'customer4','firstName4','MiddleName1','Lastname3');
insert into customer(id,customer_id,first_name,last_name) values(5 ,'customer5','firstName5','Lastname3');
insert into customer(id,customer_id,first_name,last_name) values(6 ,'customer6','firstName6','Lastname1');
insert into customer(id,customer_id,first_name,last_name) values(7 ,'customer7','firstName1','Lastname2');
