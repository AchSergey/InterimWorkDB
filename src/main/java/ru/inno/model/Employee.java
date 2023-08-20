package ru.inno.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Employee {
    private int id;
    private String first_name;
    private String last_name;
    private String middle_name;
    private int company_id;
    private String email;
    private String url;
    private String phone;
    private LocalDateTime birthdate;
    private boolean is_active;

    public Employee() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return this.last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getMiddleName() {
        return this.middle_name;
    }

    public void setMiddleName(String middleName) {
        this.middle_name = middleName;
    }

    public int getCompanyId() {
        return this.company_id;
    }

    public void setCompanyId(int companyId) {
        this.company_id = companyId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isActive() {
        return this.is_active;
    }

    public void setActive(boolean active) {
        this.is_active = active;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Employee employee = (Employee)o;
            return this.id == employee.id && this.company_id == employee.company_id && this.is_active == employee.is_active && this.first_name.equals(employee.first_name) && this.last_name.equals(employee.last_name) && this.middle_name.equals(employee.middle_name) && this.email.equals(employee.email) && this.url.equals(employee.url) && this.phone.equals(employee.phone) && this.birthdate.equals(employee.birthdate);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id, this.first_name, this.last_name, this.middle_name, this.company_id, this.email, this.url, this.phone, this.birthdate, this.is_active});
    }

    public String toString() {
        int var10000 = this.id;
        return "Employee{id=" + var10000 + ", firstName='" + this.first_name + "', lastName='" + this.last_name + "', middleName='" + this.middle_name + "', companyId=" + this.company_id + ", email='" + this.email + "', url='" + this.url + "', phone='" + this.phone + "', birthdate=" + String.valueOf(this.birthdate) + ", isActive=" + this.is_active + "}";
    }
}

