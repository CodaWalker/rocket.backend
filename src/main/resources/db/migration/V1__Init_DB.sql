create sequence hibernate_sequence start 1 increment 1;

create table role (id varchar(36) not null, name varchar(255), primary key (id));
create table user_role (user_id varchar(36) not null, role_id varchar(36) not null, primary key (user_id, role_id));
create table usr (id varchar(36) not null, accepted boolean not null, activation_code varchar(255), email varchar(40) not null, first_name varchar(255), last_name varchar(255), locked boolean not null, login varchar(255) not null, middle_name varchar(255), password varchar(255) not null, type_user varchar(255), primary key (id));

alter table if exists usr add constraint UK_b2j2bjirhqhbg1rsexaq5qs9x unique (login);
alter table if exists user_role add constraint FKa68196081fvovjhkek5m97n3y foreign key (role_id) references role;
alter table if exists user_role add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;