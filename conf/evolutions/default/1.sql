# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table honeypot_node_setup (
  name                      varchar(255) not null,
  client                    varchar(255),
  date                      varchar(255),
  type                      varchar(255),
  token                     varchar(255),
  constraint pk_honeypot_node_setup primary key (name))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table honeypot_node_setup;

SET FOREIGN_KEY_CHECKS=1;

