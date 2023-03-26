package com.example.dairaapp;

public class ShowRegisteration {
    String name, email, password, ambassador, cnic, contact;

    public ShowRegisteration() { }

    public ShowRegisteration(String name, String email, String password, String ambassador, String cnic, String contact) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ambassador = ambassador;
        this.cnic = cnic;
        this.contact = contact;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getAmbassador() { return ambassador; }

    public void setAmbassador(String ambassador) { this.ambassador = ambassador; }

    public String getCnic() { return cnic; }

    public void setCnic(String cnic) { this.cnic = cnic; }

    public String getContact() { return contact; }

    public void setContact(String contact) { this.contact = contact; }

}
