package ru.inno.testWork;

import ru.inno.model.Employee;
import ru.inno.model.UpdateEmployeeDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeController {
    private static final String DB_URL = "jdbc:postgresql://dpgcj94hf0eba7s73bdki80-a.oregon-postgres.render.com/x_clients_db_r06g";
    private static final String DB_USER = "x_clients_db_r06g_user";
    private static final String DB_PASSWORD = "0R1RNWXMepS7mrvcKRThRi82GtJ2Ob58";

    public List<Employee> findAllEmployeesByCompanyId(int company_id) {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM employee WHERE company_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, company_id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Employee employee = new Employee();
                        employee.setId(resultSet.getInt("id"));
                        employee.setFirstName(resultSet.getString("first_name"));
                        employee.setLastName(resultSet.getString("last_name"));
                        employee.setMiddleName(resultSet.getString("middle_name"));
                        employee.setCompanyId(resultSet.getInt("company_id"));
                        employee.setEmail(resultSet.getString("email"));
                        employee.setUrl(resultSet.getString("avatar_url"));
                        employee.setPhone(resultSet.getString("phone"));
                        employee.setBirthdate(resultSet.getTimestamp("birthdate").toLocalDateTime());
                        employee.setActive(resultSet.getBoolean("is_active"));
                        employees.add(employee);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public int createEmployee(Employee employee) {
        int generatedId = -1;
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO employee (first_name, last_name, middle_name, company_id, email, avatar_url, phone, birthdate, is_active) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, employee.getFirstName());
                statement.setString(2, employee.getLastName());
                statement.setString(3, employee.getMiddleName());
                statement.setInt(4, employee.getCompanyId());
                statement.setString(5, employee.getEmail());
                statement.setString(6, employee.getUrl());
                statement.setString(7, employee.getPhone());
                statement.setTimestamp(8, java.sql.Timestamp.valueOf(employee.getBirthdate()));
                statement.setBoolean(9, employee.isActive());
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    generatedId = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

    public Optional<Employee> findEmployeeById(int employeeId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM employee WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, employeeId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        Employee employee = new Employee();
                        employee.setId(resultSet.getInt("id"));
                        employee.setFirstName(resultSet.getString("first_name"));
                        employee.setLastName(resultSet.getString("last_name"));
                        employee.setMiddleName(resultSet.getString("middle_name"));
                        employee.setCompanyId(resultSet.getInt("company_id"));
                        employee.setEmail(resultSet.getString("email"));
                        employee.setUrl(resultSet.getString("avatar_url"));
                        employee.setPhone(resultSet.getString("phone"));
                        employee.setBirthdate(resultSet.getTimestamp("birthdate").toLocalDateTime());
                        employee.setActive(resultSet.getBoolean("is_active"));
                        return Optional.of(employee);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public boolean updateEmployeeById(int employeeId, UpdateEmployeeDto updatedEmployee) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE employee SET last_name = ?, email = ?, avatar_url = ?, phone = ?, is_active = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, updatedEmployee.getLastName());
                statement.setString(2, updatedEmployee.getEmail());
                statement.setString(3, updatedEmployee.getUrl());
                statement.setString(4, updatedEmployee.getPhone());
                statement.setBoolean(5, updatedEmployee.isActive());
                statement.setInt(6, employeeId);
                int rowsUpdated = statement.executeUpdate();
                return rowsUpdated > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteEmployeeById(int employeeId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM employee WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, employeeId);
                int rowsDeleted = statement.executeUpdate();
                return rowsDeleted > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
