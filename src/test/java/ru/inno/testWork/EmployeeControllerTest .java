package ru.inno.testWork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import ru.inno.model.Employee;
import ru.inno.model.UpdateEmployeeDto;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeControllerTest {
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        employeeController = new EmployeeController();
    }

    @Test
    void testFindAllEmployeesByCompanyId() {
        List<Employee> employees = employeeController.findAllEmployeesByCompanyId(373); // id существующей компании
        assertNotNull(employees);
        assertTrue(employees.isEmpty());
    }

    @Test
    void testCreateEmployee() {
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Игорь");
        newEmployee.setLastName("Петров");
        newEmployee.setMiddleName("добрый день");
        newEmployee.setCompanyId(510); // id существующей компании
        newEmployee.setEmail("john.doe@example.com");
        newEmployee.setUrl("http://example.com/john");
        newEmployee.setPhone("1234567890");
        newEmployee.setBirthdate(LocalDateTime.now());
        newEmployee.setActive(true);

        int generatedId = employeeController.createEmployee(newEmployee);
        assertNotEquals(-1, generatedId);
    }


    @Test
    void testFindEmployeeById() {
        Optional<Employee> employee = employeeController.findEmployeeById(1844); // Реальный ID сотрудника
        assertTrue(employee.isPresent());
    }

    @Test
    void testUpdateEmployeeById() {
        UpdateEmployeeDto updatedEmployee = new UpdateEmployeeDto();
        updatedEmployee.setLastName("UpdatedLastName");
        updatedEmployee.setEmail("updated@example.com");
        updatedEmployee.setUrl("http://example.com/updated");
        updatedEmployee.setPhone("9876543210");
        updatedEmployee.setActive(false);

        boolean result = employeeController.updateEmployeeById(1844, updatedEmployee); //  Реальный ID сотрудника
        assertTrue(result);

        // Проверим, что данные сотрудника действительно обновились
        Optional<Employee> updatedEmployeeResult = employeeController.findEmployeeById(1844); // Реальный ID сотрудника
        assertTrue(updatedEmployeeResult.isPresent());
        assertEquals("UpdatedLastName", updatedEmployeeResult.get().getLastName());
        assertEquals("updated@example.com", updatedEmployeeResult.get().getEmail());
        assertEquals("http://example.com/updated", updatedEmployeeResult.get().getUrl());
        assertEquals("9876543210", updatedEmployeeResult.get().getPhone());
        assertFalse(updatedEmployeeResult.get().isActive());
    }

    @Test
    void testDeleteEmployeeById() {
        boolean result = employeeController.deleteEmployeeById(1629); // Реальный ID сотрудника
        assertTrue(result);

        // Убедимся, что сотрудник действительно удален
        Optional<Employee> deletedEmployee = employeeController.findEmployeeById(1629); // Реальный ID сотрудника
        assertFalse(deletedEmployee.isPresent());
    }

    @Test
    void testUpdateNonExistingEmployee() {
        UpdateEmployeeDto updatedEmployee = new UpdateEmployeeDto();
        updatedEmployee.setLastName("UpdatedLastName");
        updatedEmployee.setEmail("updated@example.com");
        updatedEmployee.setUrl("http://example.com/updated");
        updatedEmployee.setPhone("9876543210");
        updatedEmployee.setActive(false);

        boolean result = employeeController.updateEmployeeById(-1, updatedEmployee); // Не существующий ID
        assertFalse(result);
    }

    @Test
    void testDeleteNonExistingEmployee() {
        boolean result = employeeController.deleteEmployeeById(-1); // Не существующий ID
        assertFalse(result);
    }

    @Test
    void testCreateEmployeeWithInvalidData() { // не понятно почему создается с недопустимым email работник!!!
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("");
        newEmployee.setLastName("Doe");
        newEmployee.setMiddleName("Smith");
        newEmployee.setCompanyId(966); // Реальный ID компании
        newEmployee.setEmail("invalidemail"); // Недопустимый email
        newEmployee.setUrl("http://example.com/john");
        newEmployee.setPhone("1234567890");
        newEmployee.setBirthdate(LocalDateTime.now());
        newEmployee.setActive(true);

        int generatedId = employeeController.createEmployee(newEmployee);
        assertEquals(-1, generatedId);
    }

    @Test
    void testFindNonExistingEmployeeById() {
        Optional<Employee> employee = employeeController.findEmployeeById(-1); // Не существующий ID
        assertFalse(employee.isPresent());
    }
    @Test
    void testFindAllEmployeesByCompanyIdNegative() {
        List<Employee> employees = employeeController.findAllEmployeesByCompanyId(-1); // Несуществующий ID компании
        assertNotNull(employees);
        assertTrue(employees.isEmpty());
    }

    @Test
    void testCreateEmployeeWithEmptyFields() { // не понятно почему создается работник с пустыми полями!!!
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("");
        newEmployee.setLastName("");
        newEmployee.setMiddleName("");
        newEmployee.setCompanyId(967); // Реальный ID компании
        newEmployee.setEmail(""); // Пустой email
        newEmployee.setUrl("");
        newEmployee.setPhone("");
        newEmployee.setBirthdate(LocalDateTime.now());
        newEmployee.setActive(true);

        int generatedId = employeeController.createEmployee(newEmployee);
        assertEquals(-1, generatedId);
    }


    @Test
    void testUpdateNonExistingEmployeeNegative() {
        UpdateEmployeeDto updatedEmployee = new UpdateEmployeeDto();
        updatedEmployee.setLastName("UpdatedLastName");
        updatedEmployee.setEmail("updated@example.com");
        updatedEmployee.setUrl("http://example.com/updated");
        updatedEmployee.setPhone("9876543210");
        updatedEmployee.setActive(false);

        boolean result = employeeController.updateEmployeeById(-1, updatedEmployee); // Несуществующий ID сотрудника
        assertFalse(result);
    }

    @Test
    void testDeleteNonExistingEmployeeNegative() {
        boolean result = employeeController.deleteEmployeeById(-1); // Несуществующий ID сотрудника
        assertFalse(result);
    }


    @Test
    void testUpdateEmployeeByIdAsAdmin() {
        UpdateEmployeeDto updatedEmployee = new UpdateEmployeeDto();
        updatedEmployee.setLastName("UpdatedLastName");
        updatedEmployee.setEmail("updated@example.com");
        updatedEmployee.setUrl("http://example.com/updated");
        updatedEmployee.setPhone("9876543210");
        updatedEmployee.setActive(false);

        boolean result = employeeController.updateEmployeeById(357, updatedEmployee); //  Реальный ID сотрудника
        assertTrue(result);

        // Проверим, что данные сотрудника действительно обновились
        Optional<Employee> updatedEmployeeResult = employeeController.findEmployeeById(357); // Реальный ID сотрудника
        assertTrue(updatedEmployeeResult.isPresent());
        assertEquals("UpdatedLastName", updatedEmployeeResult.get().getLastName());
        assertEquals("updated@example.com", updatedEmployeeResult.get().getEmail());
        assertEquals("http://example.com/updated", updatedEmployeeResult.get().getUrl());
        assertEquals("9876543210", updatedEmployeeResult.get().getPhone());
        assertFalse(updatedEmployeeResult.get().isActive());
    }

    @Test
    void testUpdateEmployeeByIdAsRegularUser() { // без админовских прав смог изменить данные, почему???
        UpdateEmployeeDto updatedEmployee = new UpdateEmployeeDto();
        updatedEmployee.setLastName("UpdatedLastName");
        updatedEmployee.setEmail("updated@example.com");
        updatedEmployee.setUrl("http://example.com/updated");
        updatedEmployee.setPhone("9876543210");
        updatedEmployee.setActive(false);

        boolean result = employeeController.updateEmployeeById(1632, updatedEmployee); // Реальный ID сотрудника
        assertFalse(result);
    }

    @Test
    void testDeleteEmployeeByIdAsAdmin() {
        boolean result = employeeController.deleteEmployeeById(1632); // Реальный ID сотрудника
        assertTrue(result);

        // Убедимся, что сотрудник действительно удален
        Optional<Employee> deletedEmployee = employeeController.findEmployeeById(1632); // Реальный ID сотрудника
        assertFalse(deletedEmployee.isPresent());
    }



}

