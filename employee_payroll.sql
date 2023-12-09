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

#<-----------USE CASE 4------------->
 SELECT * FROM employee_payroll;
 #<-----------USE CASE 5------------->
SELECT * FROM employee_payroll WHERE
salary=1000;
SELECT * FROM employee_payroll WHERE
 start BETWEEN CAST('2015-12-31' AS DATE) AND DATE (NOW());
 
 
 #<-----------USE CASE 6------------->
 ALTER TABLE employee_payroll ADD gender VARCHAR(1) AFTER name;
 SET SQL_SAFE_UPDATES = 0;
UPDATE employee_payroll SET gender='M' WHERE
 name='aryaman' OR name='namam';
   UPDATE employee_payroll SET gender='F' WHERE
 name='tanishka' OR name='priyanka';
 
 #<-----------USE CASE 7------------->
  SELECT SUM(salary) FROM employee_payroll 
  WHERE gender='F' GROUP BY gender;
SELECT MIN(salary) FROM employee_payroll 
  WHERE gender='M' GROUP BY gender;
  
  #<-----------USE CASE 8------------->
  ALTER TABLE employee_payroll ADD phoneNumber INT NOT NULL AFTER name;
  ALTER TABLE employee_payroll ADD adDress VARCHAR(500) DEFAULT "mumbai" AFTER phoneNumber;
  ALTER TABLE employee_payroll CHANGE COLUMN adDress address VARCHAR(500);
   ALTER TABLE employee_payroll ADD department VARCHAR(100) NOT NULL
   AFTER address;
   #<-----------USE CASE 9------------->
	ALTER TABLE employee_payroll ADD basicPay DOUBLE
   AFTER gender;
   ALTER TABLE employee_payroll ADD deductions DOUBLE
   AFTER basicPay;
   ALTER TABLE employee_payroll ADD taxablePay DOUBLE
   AFTER deductions;
   ALTER TABLE employee_payroll ADD incomeTax DOUBLE
   AFTER taxablePay;
  ALTER TABLE employee_payroll ADD netPay DOUBLE
   AFTER incomeTax;
   #<-----------USE CASE 10------------->
   INSERT INTO employee_payroll (name,phoneNumber,address,department,gender,basicPay,deductions,taxablePay,incomeTax,netPay,salary,start) VALUES
   ('terissa',87283727,'lko','sales','F',10000,50,220,20,8000,99999,'2020-01-30');
   ALTER TABLE employee_payroll DROP salary;