# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table node (
  name                      varchar(255),
  first_seen                varchar(255),
  last_seen                 varchar(255))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table node;

SET FOREIGN_KEY_CHECKS=1;

