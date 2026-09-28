package com.example.Wastetracker.Service;

import com.example.Wastetracker.Entity.Schedule;
import com.example.Wastetracker.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final ScheduleRepository repository;

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    public Schedule create(Schedule schedule) {

        if (!schedule.getEndTime().isAfter(schedule.getStartTime())) {
            throw new RuntimeException(
                    "End time must be after start time");
        }

        return repository.save(schedule);
    }

    public List<Schedule> getAll() {
        return repository.findAll();
    }

    public Schedule getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }

    public Schedule update(Long id, Schedule schedule) {

        Schedule existing = getById(id);

        if (!schedule.getEndTime().isAfter(schedule.getStartTime())) {
            throw new RuntimeException(
                    "End time must be after start time");
        }

        existing.setZone(schedule.getZone());
        existing.setPickupDay(schedule.getPickupDay());
        existing.setStartTime(schedule.getStartTime());
        existing.setEndTime(schedule.getEndTime());

        return repository.save(existing);
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new RuntimeException("Schedule not found");
        }

        repository.deleteById(id);
    }
}