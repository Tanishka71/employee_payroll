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
     * @desc: Main method to retrieve and display employee payroll data.
     * @params: None
     * @return: None
     */
    public static void main(String[] args) {
        try {
            List<EmployeePayroll> employeePayrollList = retrieveEmployeePayroll();

            // Process the retrieved employee payroll data
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

        } catch (CustomDatabaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc: Retrieves employee payroll data from the database.
     * @params: None
     * @return: List of EmployeePayroll objects containing the retrieved data.
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
}
