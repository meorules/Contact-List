package com.company;

public class Contact {
    // base variables for the contact object used to hold pairs of names and numbers
    private String name;
    private String telephoneNumber;

    //various setters and getters used in editing and retrieving contact names or numbers or both
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }


    // base constructor used to create a contact, only asks for name and number
    //no constructor is made for any other forms of contact creation as they are not used and should not be used throughout the project.
    Contact(String n,String no){
        setName(n);
        setTelephoneNumber(no);
    }
}
