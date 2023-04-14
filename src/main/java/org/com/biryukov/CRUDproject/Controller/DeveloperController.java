package org.com.biryukov.CRUDproject.Controller;

import org.com.biryukov.CRUDproject.Models.*;
import org.com.biryukov.CRUDproject.Repository.DeveloperRepository;
import org.com.biryukov.CRUDproject.Repository.GsonDeveloperRepositoryImpl;
import org.com.biryukov.CRUDproject.Views.ConsoleView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DeveloperController {
    private final GsonDeveloperRepositoryImpl gsonDeveloperRepository = new GsonDeveloperRepositoryImpl();
    private final ConsoleView consoleView = new ConsoleView();
    final ListSkills listSkills = new ListSkills();
    final ListSpecialties listSpecialties = new ListSpecialties();

    public List<Developer> getListAllDevelopers() {
        return gsonDeveloperRepository.getAll();
    }

    public void index() {
        consoleView.showMessage("\nСписок всех действующих разработчиков: ");
        gsonDeveloperRepository.getAll().forEach(System.out::println);
    }

    public void createDeveloper() {
        consoleView.showMessage("\nВведите имя разработчика: ");
        String firstName = consoleView.readLine();
        consoleView.showMessage("Введите фамилию разработчика: ");
        String lastName = consoleView.readLine();

        consoleView.showMessage("Выберите специальность разработчика из списка(введите порядковый номер): ");
        listSpecialties.getSpecialties()
                .forEach(specialty -> System.out.println(specialty.getId() + "." + specialty.getName()));
        Specialty specialty = listSpecialties.getById(Long.parseLong(consoleView.readLine().trim()));

        consoleView.showMessage("Выберите навыки разработчика из списка(введите порядковые номера через запятую): ");
        listSkills.getSkills().forEach(skill -> System.out.println(skill.getId() + "." + skill.getName()));
        List<Long> listId = Arrays.stream(consoleView.readLine().split(","))
                .toList()
                .stream()
                .map(Long::parseLong)
                .toList();
        List<Skill> skillsNewDeveloper = new ArrayList<>();
        for (int i = 0; i < listId.size(); i++) {
            skillsNewDeveloper.add(listSkills.getById(listId.get(i)));
        }

        Developer newDeveloper = new Developer(firstName, lastName, skillsNewDeveloper, specialty, Status.ACTIVE);

        gsonDeveloperRepository.save(newDeveloper);
        consoleView.showMessage("\nРазработчик успешно создан.");
    }

    public void updateDeveloper() {
        List<Developer> listActual = gsonDeveloperRepository.getAll();

        consoleView.showMessage("Для редактирования выберите порядковый номер нужного разработчика");
        long id = Long.parseLong(consoleView.readLine().trim());
        if (id <= listActual.size()) {
            Developer developer = gsonDeveloperRepository.getById(id);
            System.out.println();
            System.out.println(developer);

            consoleView.showMessage("Введите новое имя разработчика: ");
            developer.setFirstName(consoleView.readLine().trim());
            consoleView.showMessage("Введите новую фамилию разработчика: ");
            developer.setLastName(consoleView.readLine().trim());

            consoleView.showMessage("Выберите новую специальность разработчика из списка(введите порядковый номер): ");
            listSpecialties.getSpecialties()
                    .forEach(specialty -> System.out.println(specialty.getId() + "." + specialty.getName()));
            Specialty specialty = listSpecialties.getById(Long.parseLong(consoleView.readLine().trim()));
            developer.setSpecialty(specialty);
            System.out.println();


            System.out.println(" Для добавления навыка в список введите команду \"add\"");
            System.out.println(" Для удаления навыка введите команду \"delete\"");
            System.out.println(" Для выхода из режима редактирования введите команду \"exit\"");
            System.out.println(" Для пропуска данного шага введите команду \"skip\"\n");

            List<Skill> skillsDev = developer.getSkills();  // список навыков редактируемого

            System.out.println("Список навыков выбранного разработчика: ");
            skillsDev
                    .stream()
                    .forEach(skill -> System.out.println((skillsDev.indexOf(skill) + 1) + "." + skill.getName()));

            System.out.println();
            String m = consoleView.readLine().trim();
            System.out.println();

            List<Skill> allSkillsList = listSkills.getSkills(); // список всех возможных навыков

            if (m.equals("add")) {

                List<Long> listId = new ArrayList<>();
                skillsDev.stream().forEach(s -> listId.add(s.getId()));

                // список навыков которых нет у редактируемого
                List<Skill> notHaveThisSkillList =
                        allSkillsList.stream().filter((s) -> !(listId.contains(s.getId()))).toList();


                System.out.println("\nДля добавления навыка выберите один или несколько навыков" +
                        "(введите порядковые номера через запятую)");
                notHaveThisSkillList
                        .stream()
                        .forEach(skill -> System.out.println((notHaveThisSkillList
                                .indexOf(skill) + 1) + "." + skill.getName()));
                String s = consoleView.readLine().trim();
                if (s.contains(",")) {
                    List<Integer> ordinalsNumbers = Arrays  // получение списка порядковых номеров введенных пользователем
                            .stream(s.split(","))
                            .toList().stream().map(a -> (Integer.parseInt(a) - 1)).toList();

                    // добавление новых навыков
                    ordinalsNumbers.stream().forEach(a -> skillsDev.add(notHaveThisSkillList.get(a)));
                    developer.setSkills(skillsDev);
                } else {
                    skillsDev.add(notHaveThisSkillList.get(Integer.parseInt(s) - 1));
                    developer.setSkills(skillsDev);
                }
                gsonDeveloperRepository.update(developer);
                System.out.println("\nВы успешно обновили данные о разработчике");
                System.out.println();

            } else if (m.equals("delete")) {
                System.out.println("\nДля удаления выберите один или несколько навыков" +
                        "(введите порядковые номера через запятую)");

                String s1 = consoleView.readLine().trim();
                if (s1.contains(",")) {
                    List<Integer> ordinalsNumbers1 = Arrays  // получение списка порядковых номеров введенных пользователем
                            .stream(s1.split(","))
                            .toList().stream().map(a -> (Integer.parseInt(a) - 1)).toList();

                    List<Skill> listSkillForDelete = new ArrayList<>();
                    ordinalsNumbers1.stream().forEach(a -> listSkillForDelete.add(skillsDev.get(a)));

                    List<Skill> updateListSkill = skillsDev
                            .stream().filter((a) -> (!(listSkillForDelete.contains(a)))).toList();
                    developer.setSkills(updateListSkill);

                } else {
                    skillsDev.remove(Integer.parseInt(s1) - 1);
                    developer.setSkills(skillsDev);
                }
                gsonDeveloperRepository.update(developer);
                System.out.println("\nВы успешно обновили данные о разработчике");


            } else if (m.equals("exit")) {
                System.out.println();
            } else if (m.equals("skip")) {
                gsonDeveloperRepository.update(developer);
                System.out.println("\nВы успешно обновили данные о разработчике");

            } else {
                System.out.println("\nНекорректный ввод");
            }
        } else {
            System.out.println("\n Введен некорректный номер");
        }
    }


    public void delete() {
        consoleView.showMessage("\nДля удаления выберите порядковый номер нужного разработчика");
        long id = Long.parseLong(consoleView.readLine().trim());
        gsonDeveloperRepository.deleteById(id);
    }

}
