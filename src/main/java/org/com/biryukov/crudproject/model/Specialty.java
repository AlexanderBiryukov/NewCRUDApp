package org.com.biryukov.crudproject.model;

public class Specialty {
    private String name;
    private long id;

    public Specialty() {
    }

    public Specialty(String name, long id) {
        this.name = name;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "." + name;
    }
}
