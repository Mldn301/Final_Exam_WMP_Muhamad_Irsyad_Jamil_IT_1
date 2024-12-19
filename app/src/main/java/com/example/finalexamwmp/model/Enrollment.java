package com.example.finalexamwmp.model;


public class Enrollment {

    // Private member variables to hold the subject and credit
    private String subject;
    private String credit;

    public Enrollment() {
        // Default constructor required for calls to DataSnapshot.getValue(Enrollment.class)
    }

    // Constructor to initialize the subject and credit
    public Enrollment(String subject, String credit) {
        this.subject = subject;
        this.credit = credit;
    }

    // This function in the above for get and set the subject
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}

