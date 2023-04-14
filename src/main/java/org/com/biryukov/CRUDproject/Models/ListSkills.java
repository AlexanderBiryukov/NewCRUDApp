package org.com.biryukov.CRUDproject.Models;

import java.util.ArrayList;
import java.util.List;

public class ListSkills {
    List<Skill> skills = new ArrayList<>();

    public ListSkills() {
        skills.add(new Skill("Programming language", 1));
        skills.add(new Skill("Data Structure and Algorithms", 2));
        skills.add(new Skill("Git and Github", 3));
        skills.add(new Skill("Database and SQL", 4));
        skills.add(new Skill("Object-Oriented Programming", 5));
        skills.add(new Skill("Linux", 6));
        skills.add(new Skill("Testing procedures", 7));
        skills.add(new Skill("Containers (Docker and Kubernetes)", 8));
        skills.add(new Skill("Software frameworks", 9));
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public Skill getById(Long id) {
        List<Skill> list = skills.stream().filter(a -> a.getId() == id).toList();
        return list.get(0);
    }
}

