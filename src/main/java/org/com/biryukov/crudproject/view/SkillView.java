package org.com.biryukov.crudproject.view;

import org.com.biryukov.crudproject.controller.SkillController;
import org.com.biryukov.crudproject.model.Skill;
import org.com.biryukov.crudproject.model.Specialty;

import java.util.List;
import java.util.Scanner;

public class SkillView {
    private final SkillController skillController = new SkillController();
    private final Scanner scanner = new Scanner(System.in);

    public void showToSkills() {
        List<Skill> skillList = skillController.getListAllSkills();
        if (skillList.isEmpty()) {
            System.out.println("\nNone skills\n");
        } else {
            skillController.getListAllSkills().forEach(System.out::println);
        }
    }

    public List<Skill> outAllSkills() {
        return skillController.getListAllSkills();
    }

    public void createSkill() {
        System.out.println("Enter the name of the skill: ");
        skillController.createSkill(scanner.nextLine().trim());
        System.out.println("Skill created");
    }

    public void editSkill() {
        String newName;
        long id;
        List<Skill> skillList = skillController.getListAllSkills();
        if (skillList.isEmpty()) {
            System.out.println("List of skills is empty");
        } else {
            System.out.println("Select a skill to update from the list" +
                    "(Enter a ordinal number): \n");
            showToSkills();
            id = Long.parseLong(scanner.nextLine().trim());
            System.out.println("Enter new name skill: ");
            newName = scanner.nextLine().trim();
            skillController.updateSkill(id, newName);
            System.out.println("Skill updated");
        }
    }

    public void deleteSkill() {
        long id;
        List<Skill> skillList = skillController.getListAllSkills();
        if (skillList.isEmpty()) {
            System.out.println("List of skills is empty");
        } else {
            showToSkills();
            System.out.println("\nSelect a skill to delete from the list" +
                    "(Enter a ordinal number): \n");
            id = Long.parseLong(scanner.nextLine().trim());
            skillController.deleteSkillById(id);
            System.out.println("Skill deleted");
        }
    }


}
