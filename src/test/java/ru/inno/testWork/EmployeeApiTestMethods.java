package ru.inno.testWork;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Base64;

@Component
public class EmployeeApiTestMethods {
    private final String BASE_URL = "https://x-clients-be.onrender.com";
    private final RestTemplate restTemplate;

    public EmployeeApiTestMethods(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getEmployeeList(int companyId, String authHeader) {
        String url = BASE_URL + "/employee?company=" + companyId;
        return sendRequest(url, HttpMethod.GET, authHeader, null);
    }

    public ResponseEntity<String> addEmployee(String authHeader, String requestBody) {
        String url = BASE_URL + "/employee";
        return sendRequest(url, HttpMethod.POST, authHeader, requestBody);
    }

    public ResponseEntity<String> getEmployeeById(int employeeId, String authHeader) {
        String url = BASE_URL + "/employee/" + employeeId;
        return sendRequest(url, HttpMethod.GET, authHeader, null);
    }

    public ResponseEntity<String> updateEmployee(int employeeId, String authHeader, String requestBody) {
        String url = BASE_URL + "/employee/" + employeeId;
        return sendRequest(url, HttpMethod.PATCH, authHeader, requestBody);
    }

    private ResponseEntity<String> sendRequest(String url, HttpMethod method, String authHeader, String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(authHeader.getBytes()));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        return restTemplate.exchange(url, method, entity, String.class);
    }

    public ResponseEntity<String> createTestEmployeeInDatabase(int companyId, String authHeader) {
        String requestBody = "{\"first_name\":\"Test\",\"last_name\":\"Employee\",\"company_id\":" + companyId + "}";
        return addEmployee(authHeader, requestBody);
    }

    public ResponseEntity<String> updateTestEmployeeInDatabase(int employeeId, String authHeader) {
        String requestBody = "{\"last_name\":\"UpdatedLastName\"}";
        return updateEmployee(employeeId, authHeader, requestBody);
    }

    public void deleteTestEmployeeFromDatabase(int employeeId, String authHeader) {
        String url = BASE_URL + "/employee/" + employeeId;
        sendRequest(url, HttpMethod.DELETE, authHeader, null);
    }
}
