package org.com.biryukov.CRUDproject.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.com.biryukov.CRUDproject.Models.Developer;
import org.com.biryukov.CRUDproject.Models.Status;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class GsonDeveloperRepositoryImpl implements DeveloperRepository {
    private final File DEVELOPERS_FILE = new File("developers.json");
    private final Gson gson = new Gson();

    @Override
    public Developer getById(Long id) {
        List<Developer> developers = getAll();
        Optional<Developer> d = developers.stream().filter(a -> a.getId() == id).findFirst();

        return d.isPresent() ? d.get() : null;
    }

    @Override
    public List<Developer> getAll() {
        List<Developer> developers = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DEVELOPERS_FILE))) {
            Type targetClassType = new TypeToken<List<Developer>>() {
            }.getType();
            developers = gson.fromJson(bufferedReader, targetClassType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (developers == null) {
            return null;
        }
        return developers.stream()
                .filter(developer -> developer.getStatus().equals(Status.ACTIVE))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Developer developer) {
        List<Developer> developers = getAll();
        if (developers == null) {
            developer.setId(1);
            developers = new ArrayList<>();
        } else {
            long devId = developers.stream()
                    .mapToLong(Developer::getId).max().orElse(0);
            developer.setId(++devId);
        }

        developers.add(developer);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DEVELOPERS_FILE))) {
            gson.toJson(developers, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Developer updateDeveloper) {
        List<Developer> developers = getAll();

        List<Developer> updateListDevelopers = developers
                .stream().filter((a) -> (a.getId() != updateDeveloper.getId())).collect(Collectors.toList());

        updateListDevelopers.add(updateDeveloper);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DEVELOPERS_FILE))) {
            gson.toJson(updateListDevelopers, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long idDeletedDev) {
        List<Developer> developers = getAll();

        if (idDeletedDev <= developers.size()) {
            developers.forEach(a -> {
                        if (a.getId() == idDeletedDev) {
                            a.setStatus(Status.DELETED);
                        }
                    });

            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DEVELOPERS_FILE))) {
                gson.toJson(developers, bufferedWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("\nВы успешно удалили разработчика из списка");
        } else {
            System.out.println("\n Введен некорректый номер");
        }

    }

}
