package com.example.Nov.entity;

import jakarta.persistence.*;

@Entity

public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long stdid;

    private String stdname;

    public Student(){}

    @ManyToOne
    @JoinColumn
    private Department department;

    public Long getStdid() {
        return stdid;
    }

    public void setStdid(Long stdid) {
        this.stdid = stdid;
    }

    public String getStdname() {
        return stdname;
    }

    public void setStdname(String stdname) {
        this.stdname = stdname;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
