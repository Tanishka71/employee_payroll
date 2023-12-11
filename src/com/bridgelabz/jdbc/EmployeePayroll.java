package com.bridgelabz.jdbc;

import java.time.LocalDate;

/**
 * @desc: Represents an employee's payroll information.
 */
public class EmployeePayroll {
    private int empId;
    private String name;
    private String phoneNumber;
    private String address;
    private String gender;
    private LocalDate startDate;
    private int deptId;
    private int compId;

    /**
     * @desc: Gets the employee ID.
     * @return: The employee ID.
     */
    public int getEmpId() {
        return empId;
    }

    /**
     * @desc: Sets the employee ID.
     * @params: The new employee ID.
     */
    public void setEmpId(int empId) {
        this.empId = empId;
    }

    /**
     * @desc: Gets the employee name.
     * @return: The employee name.
     */
    public String getName() {
        return name;
    }

    /**
     * @desc: Sets the employee name.
     * @params: The new employee name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @desc: Gets the employee phone number.
     * @return: The employee phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @desc: Sets the employee phone number.
     * @params: The new employee phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @desc: Gets the employee address.
     * @return: The employee address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * @desc: Sets the employee address.
     * @params: The new employee address.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @desc: Gets the employee gender.
     * @return: The employee gender.
     */
    public String getGender() {
        return gender;
    }

    /**
     * @desc: Sets the employee gender.
     * @params: The new employee gender.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @desc: Gets the employee start date.
     * @return: The employee start date.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @desc: Sets the employee start date.
     * @params: The new employee start date.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * @desc: Gets the employee department ID.
     * @return: The employee department ID.
     */
    public int getDeptId() {
        return deptId;
    }

    /**
     * @desc: Sets the employee department ID.
     * @params: The new employee department ID.
     */
    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    /**
     * @desc: Gets the employee company ID.
     * @return: The employee company ID.
     */
    public int getCompId() {
        return compId;
    }

    /**
     * @desc: Sets the employee company ID.
     * @params: The new employee company ID.
     */
    public void setCompId(int compId) {
        this.compId = compId;
    }
    /**
     * @desc: Gets the employee salary
     * @return: The employee salary
     */
    public double getSalary() {
        // Implement logic to calculate salary based on basicPay or other factors if needed
        return 0.0;
    }
}
