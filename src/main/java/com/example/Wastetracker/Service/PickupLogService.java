package com.example.Wastetracker.Service;

import com.example.Wastetracker.Entity.Household;
import com.example.Wastetracker.Entity.PickupLog;
import com.example.Wastetracker.Entity.Schedule;
import com.example.Wastetracker.Repository.HouseholdRepository;
import com.example.Wastetracker.Repository.PickupLogRepository;
import com.example.Wastetracker.Repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class PickupLogService {

    private final PickupLogRepository pickupRepository;
    private final HouseholdRepository householdRepository;
    private final ScheduleRepository scheduleRepository;

    private static final double MIN_SCORE = 60.0;

    public PickupLogService(
            PickupLogRepository pickupRepository,
            HouseholdRepository householdRepository,
            ScheduleRepository scheduleRepository) {

        this.pickupRepository = pickupRepository;
        this.householdRepository = householdRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public PickupLog create(PickupLog log) {

        Household household = householdRepository
                .findById(log.getHousehold().getHouseholdId())
                .orElseThrow(() ->
                        new RuntimeException("Household not found"));

        Schedule schedule = scheduleRepository
                .findById(log.getSchedule().getScheduleId())
                .orElseThrow(() ->
                        new RuntimeException("Schedule not found"));

        if (!household.getZone().getZoneId()
                .equals(schedule.getZone().getZoneId())) {

            throw new RuntimeException(
                    "Household and schedule belong to different zones");
        }

        DayOfWeek today = LocalDateTime.now().getDayOfWeek();

        if (!schedule.getPickupDay().equalsIgnoreCase(today.name())) {

            throw new RuntimeException(
                    "Pickup is not scheduled for today");
        }

        LocalTime now = LocalTime.now();

        if (now.isBefore(schedule.getStartTime())
                || now.isAfter(schedule.getEndTime())) {

            throw new RuntimeException(
                    "Pickup is outside the scheduled time window");
        }

        log.setHousehold(household);
        log.setSchedule(schedule);
        log.setPickupTime(LocalDateTime.now());

        return pickupRepository.save(log);
    }

    public List<PickupLog> getAll() {
        return pickupRepository.findAll();
    }

    public PickupLog getById(Long id) {

        return pickupRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pickup log not found"));
    }

    public PickupLog update(Long id, PickupLog log) {

        PickupLog existing = getById(id);

        existing.setSegregationScore(log.getSegregationScore());
        existing.setCollectorName(log.getCollectorName());

        return pickupRepository.save(existing);
    }

    public void delete(Long id) {

        if (!pickupRepository.existsById(id)) {
            throw new RuntimeException("Pickup log not found");
        }

        pickupRepository.deleteById(id);
    }

    public double getHouseholdAverage(Long householdId) {

        if (!householdRepository.existsById(householdId)) {
            throw new RuntimeException("Household not found");
        }

        List<PickupLog> logs =
                pickupRepository.findByHouseholdHouseholdId(householdId);

        return logs.stream()
                .mapToInt(PickupLog::getSegregationScore)
                .average()
                .orElse(0.0);
    }

    public String getReminder(Long householdId) {

        double average = getHouseholdAverage(householdId);

        if (average < MIN_SCORE) {
            return "REMINDER_REQUIRED: Please improve waste segregation.";
        }

        return "COMPLIANT: Household segregation score is acceptable.";
    }

    public double getZoneAverage(Long zoneId) {

        List<PickupLog> logs =
                pickupRepository.findByHouseholdZoneZoneId(zoneId);

        return logs.stream()
                .mapToInt(PickupLog::getSegregationScore)
                .average()
                .orElse(0.0);
    }
}