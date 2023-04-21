package org.com.biryukov.crudproject.controller;

import org.com.biryukov.crudproject.model.Skill;
import org.com.biryukov.crudproject.model.Specialty;
import org.com.biryukov.crudproject.repository.SkillRepository;
import org.com.biryukov.crudproject.repository.gson.GsonSkillRepositoryImpl;

import java.util.List;

public class SkillController {
    private final SkillRepository skillRepository = new GsonSkillRepositoryImpl();

    public List<Skill> getListAllSkills() {
        return skillRepository.getAll();
    }

    public Skill createSkill (String name) {
        Skill skill = new Skill();
        skill.setName(name);
        return skillRepository.save(skill);
    }

    public Skill updateSkill(long id, String name) {
        Skill skill = new Skill();
        skill.setId(id);
        skill.setName(name);
        return skillRepository.update(skill);
    }

    public void deleteSkillById(Long id) {
        skillRepository.deleteById(id);
    }

}
