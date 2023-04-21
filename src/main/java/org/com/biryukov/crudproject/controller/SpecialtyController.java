package org.com.biryukov.crudproject.controller;

import org.com.biryukov.crudproject.model.Developer;
import org.com.biryukov.crudproject.model.Skill;
import org.com.biryukov.crudproject.model.Specialty;
import org.com.biryukov.crudproject.model.Status;
import org.com.biryukov.crudproject.repository.SpecialityRepository;
import org.com.biryukov.crudproject.repository.gson.GsonSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyController {
    private final SpecialityRepository specialityRepository = new GsonSpecialtyRepositoryImpl();

    public List<Specialty> getListAllSpecialties() {
        return specialityRepository.getAll();
    }

    public Specialty createSpecialty (String name) {
        Specialty specialty = new Specialty();
        specialty.setName(name);
        return specialityRepository.save(specialty);
    }

    public Specialty updateSpecialty(long id, String name) {
        Specialty specialty = new Specialty();
        specialty.setId(id);
        specialty.setName(name);
        return specialityRepository.update(specialty);
    }

    public void deleteSpecialtyById(Long id) {
        specialityRepository.deleteById(id);
    }


}
