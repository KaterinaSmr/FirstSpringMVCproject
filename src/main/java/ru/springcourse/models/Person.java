package ru.springcourse.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 100, message = "Name should be between 1 and 100 characters")
    private String name;
    @Min(value = 1900, message = "Year should be after 1900")
    private int year;

    public Person() {
    }

    public String getName() {
        return name;
    }

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
