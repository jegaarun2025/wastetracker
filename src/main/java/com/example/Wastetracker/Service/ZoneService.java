package com.example.Wastetracker.Service;

import com.example.Wastetracker.Entity.Zone;
import com.example.Wastetracker.Repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

    private final ZoneRepository repository;

    public ZoneService(ZoneRepository repository) {
        this.repository = repository;
    }

    public Zone create(Zone zone) {
        return repository.save(zone);
    }

    public List<Zone> getAll() {
        return repository.findAll();
    }

    public Zone getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
    }

    public Zone update(Long id, Zone zone) {
        Zone existing = getById(id);

        existing.setZoneName(zone.getZoneName());
        existing.setLocation(zone.getLocation());

        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Zone not found");
        }

        repository.deleteById(id);
    }
}