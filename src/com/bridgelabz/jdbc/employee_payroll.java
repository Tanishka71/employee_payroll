package com.bridgelabz.jdbc;

/*STEPS I FOLLOWED FOR JDBC COMNNECTION ARE:
	1.IMPORT
	2.LOAD & REGISTER
	3.ESTABLISH THE CONNECTION
	4.CREATE THE STATEMENT
	5.EXECUTE THE QUERY
	6.PROCESS THE QUERY
	7.CLOSE()*/
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc: Main class for retrieving and processing employee payroll data from the database.
 */
public class employee_payroll {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/payroll_service";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "tanishka13";
    /**
     * @desc: Main method to retrieve and update employee payroll data.
     * @params: None
     * @return: None
     */
    public static void main(String[] args) {
        try {
            List<EmployeePayroll> employeePayrollList = retrieveEmployeePayroll();
            System.out.println("Employee Payroll Data Before Update:");
            displayEmployeePayroll(employeePayrollList);

            updateEmployeeSalary("tanishka", 3000000.00);

            List<EmployeePayroll> updatedEmployeePayrollList = retrieveEmployeePayroll();
            System.out.println("Employee Payroll Data After Update:");
            displayEmployeePayroll(updatedEmployeePayrollList);

        } catch (CustomDatabaseException e) {
            e.printStackTrace();
        }
    }
    /**
     * @desc: Retrieves employee payroll data from the database.
     * @params: None
     * @return: List<EmployeePayroll> - List of EmployeePayroll objects
     * @throws CustomDatabaseException if there is an error retrieving data from the database.
     */
    public static List<EmployeePayroll> retrieveEmployeePayroll() throws CustomDatabaseException {
        List<EmployeePayroll> employeePayrollList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String sqlQuery = "SELECT * FROM employee";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    EmployeePayroll employeePayroll = new EmployeePayroll();
                    employeePayroll.setEmpId(resultSet.getInt("emp_id"));
                    employeePayroll.setName(resultSet.getString("name"));
                    employeePayroll.setPhoneNumber(resultSet.getString("phoneNumber"));
                    employeePayroll.setAddress(resultSet.getString("address"));
                    employeePayroll.setGender(resultSet.getString("gender"));
                    employeePayroll.setStartDate(resultSet.getDate("startDate").toLocalDate());
                    employeePayroll.setDeptId(resultSet.getInt("dept_id"));
                    employeePayroll.setCompId(resultSet.getInt("comp_id"));
                    employeePayrollList.add(employeePayroll);
                }
            }
        } catch (SQLException e) {
            throw new CustomDatabaseException("Error retrieving employee payroll data", e);
        }
        return employeePayrollList;
    }
    /**
     * @desc: Updates the basicPay in the payroll table for the specified employee.
     * @params: employeeName - Name of the employee, newSalary - New basicPay value
     * @return: None
     * @throws CustomDatabaseException if there is an error updating data in the database.
     */
    public static void updateEmployeeSalary(String employeeName, double newSalary) throws CustomDatabaseException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            // Update basicPay in the payroll table
            String updatePayrollSalaryQuery = "UPDATE payroll SET basicPay = ? WHERE emp_id = (SELECT emp_id FROM employee WHERE name = ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updatePayrollSalaryQuery)) {
                preparedStatement.setDouble(1, newSalary);
                preparedStatement.setString(2, employeeName);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 0) {
                    throw new CustomDatabaseException("Payroll data not found for employee: " + employeeName, null);
                }

                System.out.println("Payroll salary updated successfully for employee: " + employeeName);
            }
        } catch (SQLException e) {
            throw new CustomDatabaseException("Error updating employee salary", e);
        }
    }
    /**
     * @desc: Displays employee payroll data.
     * @params: employeePayrollList - List of EmployeePayroll objects
     * @return: None
     */
    private static void displayEmployeePayroll(List<EmployeePayroll> employeePayrollList) {
        for (EmployeePayroll employeePayroll : employeePayrollList) {
            System.out.println("Employee ID: " + employeePayroll.getEmpId());
            System.out.println("Name: " + employeePayroll.getName());
            System.out.println("Phone Number: " + employeePayroll.getPhoneNumber());
            System.out.println("Address: " + employeePayroll.getAddress());
            System.out.println("Gender: " + employeePayroll.getGender());
            System.out.println("Start Date: " + employeePayroll.getStartDate());
            System.out.println("Department ID: " + employeePayroll.getDeptId());
            System.out.println("Company ID: " + employeePayroll.getCompId());
            System.out.println("------------------------");
        }
    }
}
