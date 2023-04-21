package org.com.biryukov.crudproject.controller;

import org.com.biryukov.crudproject.model.*;
import org.com.biryukov.crudproject.repository.DeveloperRepository;
import org.com.biryukov.crudproject.repository.gson.GsonDeveloperRepositoryImpl;

import java.util.List;

public class DeveloperController {
    private final DeveloperRepository developerRepository = new GsonDeveloperRepositoryImpl();

    public List<Developer> getListAllDevelopers() {
        return developerRepository.getAll();
    }


    public Developer createDeveloper(String firstName, String lastName, List<Skill> skills, Specialty specialty) {
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setSkills(skills);
        developer.setSpecialty(specialty);
        developer.setStatus(Status.ACTIVE);
        return developerRepository.save(developer);
    }

    public Developer getDevelopersById(Long id) {
        return developerRepository.getById(id);
    }

    public Developer updateDeveloper(long id, String firstName, String lastName, List<Skill> skills, Specialty specialty) {
        Developer developer = new Developer(firstName, lastName, skills, specialty, Status.ACTIVE);
        developer.setId(id);
        return developerRepository.update(developer);
    }

    public void deleteDeveloperById(Long id) {
        developerRepository.deleteById(id);
    }
}
