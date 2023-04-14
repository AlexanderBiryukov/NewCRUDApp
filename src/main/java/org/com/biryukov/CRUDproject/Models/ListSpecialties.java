package org.com.biryukov.CRUDproject.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListSpecialties {
    List<Specialty> specialties = new ArrayList<>();

    public ListSpecialties() {
        specialties.add(new Specialty("Frontend Developer", 1));
        specialties.add(new Specialty("Backend Developer", 2));
        specialties.add(new Specialty("Full stack Developer", 3));
        specialties.add(new Specialty("Mobile Developer", 4));
        specialties.add(new Specialty("Game Developer", 5));
        specialties.add(new Specialty("Big data developer", 6));
        specialties.add(new Specialty("Security developer", 7));
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    public Specialty getById(long id) {
        List<Specialty> list = specialties.stream().filter(a -> a.getId() == id).toList();
        return list.get(0);
    }
}
