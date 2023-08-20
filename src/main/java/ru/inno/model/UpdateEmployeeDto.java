package ru.inno.model;

import java.util.Objects;

public class UpdateEmployeeDto {
    private String last_name;
    private String email;
    private String url;
    private String phone;
    private boolean active;

    public UpdateEmployeeDto() {
    }

    public String getLastName() {
        return this.last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
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

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            UpdateEmployeeDto that = (UpdateEmployeeDto)o;
            return this.active == that.active && this.last_name.equals(that.last_name) && this.email.equals(that.email) && this.url.equals(that.url) && this.phone.equals(that.phone);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.last_name, this.email, this.url, this.phone, this.active});
    }

    public String toString() {
        return "UpdateEmployeeDto{lastName='" + this.last_name + "', email='" + this.email + "', url='" + this.url + "', phone='" + this.phone + "', active=" + this.active + "}";
    }
}
