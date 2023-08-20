package ru.inno.testWork;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
 //Тесты падают на первом шаге. Не смог забороть аутентификацию.
public class EmployeeApiTests {
    private EmployeeApiTestMethods apiTestMethods;
    private final String authHeader;

    private final RestTemplate restTemplate = new RestTemplate();
    private int testEmployeeId;

    public EmployeeApiTests() {
        String username = "x_clients_db_r06g_user";
        String password = "0R1RNWXMepS7mrvcKRThRi82GtJ2Ob58";
        String authString = username + ":" + password;
        authHeader = "Basic " + Base64.getEncoder().encodeToString(authString.getBytes());
    }

    @BeforeEach
    public void setup() {
        apiTestMethods = new EmployeeApiTestMethods(restTemplate);
        ResponseEntity<String> response = apiTestMethods.createTestEmployeeInDatabase(1, authHeader);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Извлекаем id созданного сотрудника
        String responseBody = response.getBody();
        // Извлечение id и преобразование в целое число
        testEmployeeId = 80;
    }

    @AfterEach
    public void cleanupTestEmployee() {
        apiTestMethods.deleteTestEmployeeFromDatabase(testEmployeeId, authHeader);
    }

    @Test
    public void testGetEmployeeList_Positive() {
        ResponseEntity<String> response = apiTestMethods.getEmployeeList(1, authHeader);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetEmployeeById_Positive() {
        ResponseEntity<String> response = apiTestMethods.getEmployeeById(testEmployeeId, authHeader);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateEmployee_Positive() {
        apiTestMethods.updateTestEmployeeInDatabase(testEmployeeId, authHeader);

        String requestBody = "{\"last_name\":\"UpdatedLastName\"}";
        ResponseEntity<String> response = apiTestMethods.updateEmployee(testEmployeeId, authHeader, requestBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetEmployeeById_Negative() {
        ResponseEntity<String> response = apiTestMethods.getEmployeeById(999, authHeader);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateEmployee_Negative() {
        String invalidRequestBody = "{\"invalidField\":\"Value\"}";
        ResponseEntity<String> response = apiTestMethods.updateEmployee(testEmployeeId, authHeader, invalidRequestBody);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testEmployeeListContract() {
        ResponseEntity<String> response = apiTestMethods.getEmployeeList(1, authHeader);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String responseBody = response.getBody();
        assertTrue(responseBody.contains("first_name"));
        assertTrue(responseBody.contains("last_name"));
        assertTrue(responseBody.contains("company_id"));
        assertTrue(responseBody.contains("is_active"));
    }

    @Test
    public void testEmployeeByIdContract() {
        ResponseEntity<String> response = apiTestMethods.getEmployeeById(testEmployeeId, authHeader);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String responseBody = response.getBody();
        assertTrue(responseBody.contains("first_name"));
        assertTrue(responseBody.contains("last_name"));
        assertTrue(responseBody.contains("company_id"));
        assertTrue(responseBody.contains("is_active"));
    }

    @Test
    public void testEmployeeIsActiveAfterCreation() {
        ResponseEntity<String> response = apiTestMethods.getEmployeeById(testEmployeeId, authHeader);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String responseBody = response.getBody();
        assertTrue(responseBody.contains("\"is_active\":true"));
    }

    @Test
    public void testEmployeeIsInactiveAfterDeletion() {
        apiTestMethods.deleteTestEmployeeFromDatabase(testEmployeeId, authHeader);

        ResponseEntity<String> response = apiTestMethods.getEmployeeById(testEmployeeId, authHeader);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
