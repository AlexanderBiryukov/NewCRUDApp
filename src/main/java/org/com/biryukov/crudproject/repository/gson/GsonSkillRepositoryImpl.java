package org.com.biryukov.crudproject.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.com.biryukov.crudproject.model.Skill;
import org.com.biryukov.crudproject.repository.SkillRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonSkillRepositoryImpl implements SkillRepository {
    private final File SKILLS_FILE = new File("C:\\Users\\79030\\Desktop\\n\\src\\main\\resources\\skills.json");
    private final Gson gson = new Gson();


    private List<Skill> readSkillsFromFile() {
        List<Skill> skills = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SKILLS_FILE))) {
            Type targetClassType = new TypeToken<List<Skill>>() {
            }.getType();
            skills = gson.fromJson(bufferedReader, targetClassType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (skills == null) {
            return new ArrayList<>();
        }
        return skills;
    }


    private void writeSkillsToFile(List<Skill> skills) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(SKILLS_FILE))) {
            gson.toJson(skills, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long generateId(List<Skill> skills) {
        return skills.stream()
                .mapToLong(Skill::getId).max().orElse(0);
    }

    @Override
    public Skill getById(Long id) {
        return readSkillsFromFile().stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Skill> getAll() {
        return readSkillsFromFile();
    }

    @Override
    public Skill save(Skill skill) {
        List<Skill> skills = readSkillsFromFile();
        skill.setId(generateId(skills) + 1);
        skills.add(skill);
        writeSkillsToFile(skills);
        return skill;
    }

    @Override
    public Skill update(Skill updateSkill) {
        List<Skill> skills = readSkillsFromFile();

        List<Skill> updateListSkills = skills
                .stream().filter((a) -> (a.getId() != updateSkill.getId()))
                .collect(Collectors.toList());

        updateListSkills.add(updateSkill);

        writeSkillsToFile(updateListSkills);
        return updateSkill;
    }

    @Override
    public void deleteById(Long idDeletedSkill) {
        List<Skill> skills = readSkillsFromFile();
        List<Skill> updateListSkills =
                skills
                        .stream()
                        .filter(a -> a.getId() != idDeletedSkill)
                        .collect(Collectors.toList());

        writeSkillsToFile(updateListSkills);

    }

}
