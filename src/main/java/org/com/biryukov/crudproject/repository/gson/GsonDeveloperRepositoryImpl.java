package org.com.biryukov.crudproject.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.com.biryukov.crudproject.model.Developer;
import org.com.biryukov.crudproject.model.Status;
import org.com.biryukov.crudproject.repository.DeveloperRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {

    private final File DEVELOPERS_FILE = new File("C:\\Users\\79030\\Desktop\\n\\src\\main\\resources\\developers.json");
    private final Gson gson = new Gson();


    private List<Developer> readDevelopersFromFile() {
        List<Developer> developers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DEVELOPERS_FILE))) {
            Type targetClassType = new TypeToken<List<Developer>>() {
            }.getType();
            developers = gson.fromJson(bufferedReader, targetClassType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (developers == null) {
            return new ArrayList<>();
        }
        return developers;
    }


    private void writeDeveloperToFile(List<Developer> developers) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DEVELOPERS_FILE))) {
            gson.toJson(developers, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long generateId(List<Developer> developers) {
        return developers.stream()
                .mapToLong(Developer::getId).max().orElse(0);
    }

    @Override
    public Developer getById(Long id) {
        return readDevelopersFromFile().stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Developer> getAll() {
        return readDevelopersFromFile();
    }

    @Override
    public Developer save(Developer developer) {
        List<Developer> developers = readDevelopersFromFile();
        developer.setId(generateId(developers) + 1);
        developers.add(developer);
        writeDeveloperToFile(developers);
        return developer;
    }

    @Override
    public Developer update(Developer updateDeveloper) {
        List<Developer> developers = readDevelopersFromFile();

        List<Developer> updateListDevelopers = developers
                .stream().filter((a) -> (a.getId() != updateDeveloper.getId())).collect(Collectors.toList());

        updateListDevelopers.add(updateDeveloper);

        writeDeveloperToFile(updateListDevelopers);
        return updateDeveloper;
    }

    @Override
    public void deleteById(Long idDeletedDev) {
        List<Developer> developers = readDevelopersFromFile();

        if (idDeletedDev <= developers.size()) {
            developers.forEach(a -> {
                if (a.getId() == idDeletedDev) {
                    a.setStatus(Status.DELETED);
                }
            });

            writeDeveloperToFile(developers);
        }
    }

}
