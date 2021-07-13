select now();

create table customer (id bigint not null, customer_id varchar(10),first_name varchar(100),middle_name varchar(10),last_name varchar(10), primary key (id));
alter table customer drop constraint if exists UK_customer_id;
alter table customer add constraint UK_customer_id unique (customer_id);

insert into customer(id,customer_id,first_name,middle_name,last_name) values(1 ,'customer1','Mark','Junior','Watson');
insert into customer(id,customer_id,first_name,last_name) values(2 ,'customer2','Vipin','Jangid');
insert into customer(id,customer_id,first_name,middle_name,last_name) values(3 ,'customer3','Bhushan','Kumar','Singh');
insert into customer(id,customer_id,first_name,middle_name,last_name) values(4 ,'customer4','Padmesh','Kumar','Tripathi');
insert into customer(id,customer_id,first_name,last_name) values(5 ,'customer5','Hemant','Gupta');
insert into customer(id,customer_id,first_name,last_name) values(6 ,'customer6','Kishore','Rao');
insert into customer(id,customer_id,first_name,last_name) values(7 ,'customer7','Hari','Singh');
insert into customer(id,customer_id,first_name,middle_name,last_name) values(8 ,'customer8','Ashish','Pratap','Singh');
insert into customer(id,customer_id,first_name,last_name) values(9 ,'customer9','Ramit','Gupa');
insert into customer(id,customer_id,first_name,last_name) values(10 ,'customer10','Anuj','Diwedi');
insert into customer(id,customer_id,first_name,middle_name,last_name) values(11 ,'customer11','Narendra','Damodar','Modi');
insert into customer(id,customer_id,first_name,middle_name,last_name) values(12 ,'customer12','Amit','Kumar','Sah');