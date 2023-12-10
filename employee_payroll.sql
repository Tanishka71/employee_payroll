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
   
    
   #<---------USE CASE 10 USING ER DIAGRAM--------->
   -- Create EMPLOYEE table
CREATE TABLE EMPLOYEE (
    emp_id INT PRIMARY KEY,
    name VARCHAR(50),
    phoneNumber VARCHAR(15),
    address VARCHAR(100),
    gender CHAR(1),
    startDate DATE,
    dept_id INT,
    comp_id INT,
    FOREIGN KEY (dept_id) REFERENCES DEPARTMENT(dept_id),
    FOREIGN KEY (comp_id) REFERENCES COMPANY(comp_id)
);

-- Insert sample data into EMPLOYEE table
INSERT INTO EMPLOYEE (emp_id, name, phoneNumber, address, gender, startDate, dept_id, comp_id) VALUES
(1, 'tanishka', '98765430', 'lko', 'F', '2020-01-08', 201, 1),
(2, 'john', '88765430', 'uk', 'M', '2021-01-08', 301, 1),
(3, 'bob', '78765430', 'us', 'M', '2021-01-10', 401, 1),
(4, 'ria', '79965430', 'del', 'F', '2023-01-10', 501, 1);
SELECT * FROM employee;
DESC employee;

-- Create DEPARTMENT table
CREATE TABLE DEPARTMENT (
    dept_id INT PRIMARY KEY,
    dept_name VARCHAR(50),
    emp_id INT,
    FOREIGN KEY (emp_id) REFERENCES EMPLOYEE(emp_id)
);

-- Insert sample data into DEPARTMENT table
INSERT INTO DEPARTMENT (dept_id, dept_name, emp_id) VALUES
(201, 'TECH', 1),
(301, 'SALES', 2),
(401, 'CONTENT', 3),
(501, 'HR', 4);
SELECT * FROM department;
DESC department;


-- Create PAYROLL table
CREATE TABLE PAYROLL (
    payroll_id INT PRIMARY KEY,
    emp_id INT,
    basicPay DOUBLE,
    deductions DOUBLE,
    taxablePay DOUBLE,
    incomeTax DOUBLE,
    netPay DOUBLE,
    FOREIGN KEY (emp_id) REFERENCES EMPLOYEE(emp_id)
);

-- Insert sample data into PAYROLL table
INSERT INTO PAYROLL (payroll_id, emp_id, basicPay, deductions, taxablePay, incomeTax, netPay) VALUES
(1, 1, 10000.0, 100.0, 100.0, 500.0, 150.0),
(2, 2, 20000.0, 100.0, 100.0, 500.0, 150.0),
(3, 3, 30000.0, 100.0, 100.0, 500.0, 150.0),
(4, 4, 10000.0, 100.0, 100.0, 500.0, 150.0);
SELECT * FROM payroll;
DESC payroll;

-- Create COMPANY table
CREATE TABLE COMPANY (
    comp_id INT PRIMARY KEY,
    comp_name VARCHAR(100)
);

-- Insert data into COMPANY table
INSERT INTO COMPANY (comp_id, comp_name) VALUES
(1, 'GE');
SELECT * FROM company;
DESC company;
