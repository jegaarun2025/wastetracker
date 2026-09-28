package com.example.Wastetracker.Service;

import com.example.Wastetracker.Entity.Household;
import com.example.Wastetracker.Repository.HouseholdRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseholdService {

    private final HouseholdRepository repository;

    public HouseholdService(HouseholdRepository repository) {
        this.repository = repository;
    }

    public Household create(Household household) {
        return repository.save(household);
    }

    public List<Household> getAll() {
        return repository.findAll();
    }

    public Household getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Household not found"));
    }

    public Household update(Long id, Household household) {

        Household existing = getById(id);

        existing.setOwnerName(household.getOwnerName());
        existing.setAddress(household.getAddress());
        existing.setZone(household.getZone());

        return repository.save(existing);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Household not found");
        }

        repository.deleteById(id);
    }
}