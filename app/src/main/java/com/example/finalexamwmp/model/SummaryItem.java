package com.example.finalexamwmp.model;


public class SummaryItem {
    // Private member variables to hold the subject and credit
    private String subject;
    private String credit;

    // Constructor to initialize the subject and credit
    public SummaryItem(String subject, String credit) {
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
