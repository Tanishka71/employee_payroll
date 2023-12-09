#<-----------USE CASE 1------------->
create database payroll_service;
use payroll_service;
SELECT database();
SHOW databases;

#<-----------USE CASE 2------------->
CREATE TABLE employee_payroll (
id INT unsigned NOT NULL AUTO_INCREMENT,
name VARCHAR(150) NOT NULL,
salary Double NOT NULL,
start DATE NOT NULL,
PRIMARY KEY (id)
);
