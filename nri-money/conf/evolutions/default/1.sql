# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table ex_charge (
  id                        bigint not null,
  depends_on_amt            boolean,
  depends_on_currency       boolean,
  product_id                bigint,
  amt_currency              varchar(255),
  from_amt                  double,
  to_amt                    double,
  abs_rate                  double,
  abs_rate_currency         varchar(255),
  percent_rate              double,
  percent_currency          varchar(255),
  max_charge                double,
  min_charge                double,
  charge_type               varchar(255),
  constraint pk_ex_charge primary key (id))
;

create table ex_rate (
  id                        bigint not null,
  product_id                bigint,
  from_amt                  double,
  to_amt                    double,
  from_currency             varchar(255),
  to_currency               varchar(255),
  ex_rate                   double,
  constraint pk_ex_rate primary key (id))
;

create table institution (
  id                        bigint not null,
  logo_url                  varchar(255),
  name                      varchar(255),
  inst_type                 varchar(255),
  url                       varchar(255),
  constraint uq_institution_name unique (name),
  constraint pk_institution primary key (id))
;

create table product (
  id                        bigint not null,
  institution_id            bigint,
  name                      varchar(255),
  constraint pk_product primary key (id))
;

create sequence ex_charge_seq;

create sequence ex_rate_seq;

create sequence institution_seq;

create sequence product_seq;

alter table ex_charge add constraint fk_ex_charge_product_1 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_ex_charge_product_1 on ex_charge (product_id);
alter table ex_rate add constraint fk_ex_rate_product_2 foreign key (product_id) references product (id) on delete restrict on update restrict;
create index ix_ex_rate_product_2 on ex_rate (product_id);
alter table product add constraint fk_product_institution_3 foreign key (institution_id) references institution (id) on delete restrict on update restrict;
create index ix_product_institution_3 on product (institution_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists ex_charge;

drop table if exists ex_rate;

drop table if exists institution;

drop table if exists product;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists ex_charge_seq;

drop sequence if exists ex_rate_seq;

drop sequence if exists institution_seq;

drop sequence if exists product_seq;

