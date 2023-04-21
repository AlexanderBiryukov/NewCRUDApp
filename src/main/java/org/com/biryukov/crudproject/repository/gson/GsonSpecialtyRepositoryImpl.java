package org.com.biryukov.crudproject.repository.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.com.biryukov.crudproject.model.Specialty;
import org.com.biryukov.crudproject.repository.SpecialityRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GsonSpecialtyRepositoryImpl implements SpecialityRepository {
    private final File SPECIALTIES_FILE = new File("C:\\Users\\79030\\Desktop\\n\\src\\main\\resources\\specialties.json");
    private final Gson gson = new Gson();


    private List<Specialty> readSpecialtiesFromFile() {
        List<Specialty> specialties = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SPECIALTIES_FILE.getAbsolutePath()))) {
            Type targetClassType = new TypeToken<List<Specialty>>() {
            }.getType();
            specialties = gson.fromJson(bufferedReader, targetClassType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (specialties == null) {
            return new ArrayList<>();
        }
        return specialties;
    }

    private void writeSpecialtiesToFile(List<Specialty> specialties) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(SPECIALTIES_FILE))) {
            gson.toJson(specialties, bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long generateId(List<Specialty> specialties) {
        return specialties.stream()
                .mapToLong(Specialty::getId).max().orElse(0);
    }

    @Override
    public Specialty getById(Long id) {
        return readSpecialtiesFromFile().stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Specialty> getAll() {
        return readSpecialtiesFromFile();
    }

    @Override
    public Specialty save(Specialty specialty) {
        List<Specialty> specialties = readSpecialtiesFromFile();
        specialty.setId(generateId(specialties) + 1);
        specialties.add(specialty);
        writeSpecialtiesToFile(specialties);
        return specialty;
    }

    @Override
    public Specialty update(Specialty updateSpecialty) {
        List<Specialty> specialties = readSpecialtiesFromFile();

        List<Specialty> updateListSpecialties = specialties
                .stream()
                .filter((a) -> (a.getId() != updateSpecialty.getId()))
                .collect(Collectors.toList());

        updateListSpecialties.add(updateSpecialty);

        writeSpecialtiesToFile(updateListSpecialties);
        return updateSpecialty;
    }

    @Override
    public void deleteById(Long idDeletedSpec) {
        List<Specialty> specialties = readSpecialtiesFromFile();
        List<Specialty> updateListSpecialties =
                specialties
                        .stream()
                        .filter(a -> a.getId() != idDeletedSpec)
                        .collect(Collectors.toList());

        writeSpecialtiesToFile(updateListSpecialties);

    }
}
