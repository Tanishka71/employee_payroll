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

#<-----------USE CASE 3------------->
INSERT INTO employee_payroll (id,name,salary,start) VALUES
(1,"Tanishka",1000, '2024-01-08');
INSERT INTO employee_payroll (name,salary,start) VALUES
("Priyanka",1000, '2024-01-08');
INSERT INTO employee_payroll (name,salary,start) VALUES
("aryaman",50, '2024-01-10');
INSERT INTO employee_payroll (name,salary,start) VALUES
("namam",50, '2018-01-10');
