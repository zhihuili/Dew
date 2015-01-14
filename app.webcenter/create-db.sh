#!/bin/bash
env | grep DERBY_HOME
[ $? -eq 0 ] || echo "plesase set DERBY profile var:"
 test -d ../Database && rm -rf ../Database ||mkdir ../Database 

ij << EOF 2> /dev/null
connect 'jdbc:derby:../Database/dewdb;create=true';

create table userinfo(
user_id int generated always as identity,
name varchar(10) NOT NULL,
password varchar(20) NOT NULL,
type varchar(20) NOT NULL,
primary key(user_id)
);

insert into userinfo(name,password,type) values('admin','admin','Admin');

create table application(
app_id int generated always as identity,
name varchar(100) NOT NULL,
path varchar(1000) NOT NULL,
executable varchar(100) NOT NULL,
strategy varchar(15) NOT NULL,
type varchar(15) NOT NULL,
host varchar(100) NOT NULL,
primary key(app_id)
);

create table job(
job_id int generated always as identity,
name varchar(100) NOT NULL,
defination varchar(2000),
cycle varchar(20),
user_id int,
primary key(job_id)
);

create table jobrecord(
record_id varchar(100) NOT NULL,
job_id int NOT NULL,
starttime timestamp NOT NULL,
endtime timestamp NOT NULL,
result varchar(20) NOT NULL,
primary key(record_id)
);

create table apprecord(
record_id varchar(100) NOT NULL,
app_name varchar(100) NOT NULL,
job_recordid varchar(100) NOT NULL,
engin_app_id varchar(100),
starttime timestamp NOT NULL,
endtime timestamp NOT NULL,
result varchar(20) NOT NULL,
primary key(record_id)
);

exit;
EOF

rm ./derby.log
