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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc: Main class for retrieving and processing employee payroll data from the database.
 */
public class employee_payroll {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/payroll_service";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "tanishka13";
    private static final Map<String, PreparedStatement> preparedStatements = new HashMap<>();

    private static Connection connection;

    private static employee_payroll instance;

    private employee_payroll() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * @desc: Gets the singleton instance of the EmployeePayrollDBService.
     * @params: None
     * @return: EmployeePayrollDBService - The singleton instance
     */
    public static synchronized employee_payroll getInstance() {
        if (instance == null) {
            instance = new employee_payroll();
        }
        return instance;
    }
    /**
     * @desc: Gets a PreparedStatement for the specified SQL query.
     * Caches and reuses PreparedStatement instances.
     * @params: query - The SQL query
     * @return: PreparedStatement - The prepared statement for the query
     * @throws SQLException if there is an error creating or retrieving the PreparedStatement.
     */
    public static PreparedStatement getPreparedStatement(String query) throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Connection is null or closed. Make sure to call getInstance() first.");
        }

        if (preparedStatements.containsKey(query)) {
            return preparedStatements.get(query);
        } else {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatements.put(query, preparedStatement);
            return preparedStatement;
        }
    }
    //<--------------------USE CASE 2------------------------>
    /**
     * @desc: Retrieves employee payroll data by name from the database.
     * @params: employeeName - Name of the employee
     * @return: List<EmployeePayroll> - List of EmployeePayroll objects
     * @throws CustomDatabaseException if there is an error retrieving data from the database.
     */
    public List<EmployeePayroll> retrieveEmployeePayrollByName(String employeeName) throws CustomDatabaseException {
        List<EmployeePayroll> employeePayrollList = new ArrayList<>();
        try {
            String sqlQuery = "SELECT * FROM employee WHERE name = ?";
            PreparedStatement preparedStatement = getPreparedStatement(sqlQuery);
            preparedStatement.setString(1, employeeName);
            ResultSet resultSet = preparedStatement.executeQuery();

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
        } catch (SQLException e) {
            throw new CustomDatabaseException("Error retrieving employee payroll data by name", e);
        }
        return employeePayrollList;
    }
 
  //<--------------------USE CASE 2------------------------>
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
  //<--------------------USE CASE 4------------------------>
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
    //<--------------------USE CASE 5------------------------>
    /**
     * @desc: Retrieves employees who have joined in a particular date range from the database.
     * @params: startDate - Start date of the range, endDate - End date of the range
     * @return: List<EmployeePayroll> - List of EmployeePayroll objects
     * @throws CustomDatabaseException if there is an error retrieving data from the database.
     */
//    public static List<EmployeePayroll> retrieveEmployeesByDateRange(LocalDate startDate, LocalDate endDate) throws CustomDatabaseException {
//        List<EmployeePayroll> employeePayrollList = new ArrayList<>();
//        try {
//            String sqlQuery = "SELECT * FROM employee WHERE startDate BETWEEN ? AND ?";
//            PreparedStatement preparedStatement = getPreparedStatement(sqlQuery);
//            preparedStatement.setDate(1, Date.valueOf(startDate));
//            preparedStatement.setDate(2, Date.valueOf(endDate));
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                EmployeePayroll employeePayroll = new EmployeePayroll();
//                employeePayroll.setEmpId(resultSet.getInt("emp_id"));
//                employeePayroll.setName(resultSet.getString("name"));
//                employeePayroll.setPhoneNumber(resultSet.getString("phoneNumber"));
//                employeePayroll.setAddress(resultSet.getString("address"));
//                employeePayroll.setGender(resultSet.getString("gender"));
//                employeePayroll.setStartDate(resultSet.getDate("startDate").toLocalDate());
//                employeePayroll.setDeptId(resultSet.getInt("dept_id"));
//                employeePayroll.setCompId(resultSet.getInt("comp_id"));
//                employeePayrollList.add(employeePayroll);
//            }
//        } catch (SQLException e) {
//            throw new CustomDatabaseException("Error retrieving employee payroll data by date range", e);
//        }
//        return employeePayrollList;
//    }
   
    //<----------------------USE CASE 6-------------------->
    /**
     * @desc: Retrieves the sum, average, min, max, and count of basicPay for male and female employees.
     * @return: Map<String, Double> - A map containing results for each gender
     * @throws CustomDatabaseException if there is an error retrieving data from the database.
     */
    public static Map<String, Double> analyzeEmployeePayroll() throws CustomDatabaseException {
        Map<String, Double> analysisResults = new HashMap<>();

        try {
            String sqlQuery = "SELECT gender, SUM(salary) AS totalSalary, AVG(salary) AS avgSalary, MIN(salary) AS minSalary, MAX(salary) AS maxSalary, COUNT(*) AS employeeCount FROM employee GROUP BY gender";
            PreparedStatement preparedStatement = getPreparedStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String gender = resultSet.getString("gender");
                double totalSalary = resultSet.getDouble("totalSalary");
                double avgSalary = resultSet.getDouble("avgSalary");
                double minSalary = resultSet.getDouble("minSalary");
                double maxSalary = resultSet.getDouble("maxSalary");
                int employeeCount = resultSet.getInt("employeeCount");

                System.out.println("Gender: " + gender);
                System.out.println("Total Salary: " + totalSalary);
                System.out.println("Average Salary: " + avgSalary);
                System.out.println("Min Salary: " + minSalary);
                System.out.println("Max Salary: " + maxSalary);
                System.out.println("Employee Count: " + employeeCount);
                System.out.println("------------------------");

                // Store results in the map
                analysisResults.put(gender, totalSalary);
                analysisResults.put("avg_" + gender, avgSalary);
                analysisResults.put("min_" + gender, minSalary);
                analysisResults.put("max_" + gender, maxSalary);
                analysisResults.put("count_" + gender, (double) employeeCount);
            }
        } catch (SQLException e) {
            throw new CustomDatabaseException("Error analyzing employee payroll data", e);
        }

        return analysisResults;
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
    /**
     * @desc: Main method to retrieve and update employee payroll data.
     * @params: None
     * @return: None
     */
    public static void main(String[] args) {
    	 try {
    	        // Retrieve and display all employee payroll data
    	        List<EmployeePayroll> allEmployeePayroll = retrieveEmployeePayroll();
    	        System.out.println("All Employee Payroll Data:");
    	        displayEmployeePayroll(allEmployeePayroll);

    	        // Update salary for an employee
    	        updateEmployeeSalary("tanishka", 3000000.00);
    	        System.out.println("Employee Salary Updated for Tanishka");

    	        // Retrieve and display updated employee payroll data
    	        List<EmployeePayroll> updatedEmployeePayroll = retrieveEmployeePayroll();
    	        System.out.println("Updated Employee Payroll Data:");
    	        displayEmployeePayroll(updatedEmployeePayroll);

//    	        // Retrieve and display employees by date range
//    	        LocalDate startDate = LocalDate.of(2023, 1, 1);
//    	        LocalDate endDate = LocalDate.of(2023, 12, 31);
//    	        List<EmployeePayroll> employeesByDateRange = retrieveEmployeesByDateRange(startDate, endDate);
//    	        System.out.println("Employee Payroll Data for Date Range (2020-01-01 to 2023-12-31):");
//    	        displayEmployeePayroll(employeesByDateRange);
    	     // Analyze employee payroll data
    	        Map<String, Double> analysisResults = analyzeEmployeePayroll();

    	        // Display analysis results
    	        System.out.println("Analysis Results:");
    	        for (Map.Entry<String, Double> entry : analysisResults.entrySet()) {
    	            System.out.println(entry.getKey() + ": " + entry.getValue());
    	        }
    	    } catch (CustomDatabaseException e) {
    	        e.printStackTrace();
    	    }
}}
