package com.example.Wastetracker.Controller;

import com.example.Wastetracker.Entity.Household;
import com.example.Wastetracker.Service.HouseholdService;
import com.example.Wastetracker.Service.PickupLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final HouseholdService service;
    private final PickupLogService pickupService;

    public HouseholdController(
            HouseholdService service,
            PickupLogService pickupService) {

        this.service = service;
        this.pickupService = pickupService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Household create(
            @Valid @RequestBody Household household) {

        return service.create(household);
    }

    @GetMapping
    public List<Household> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Household getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Household update(
            @PathVariable Long id,
            @Valid @RequestBody Household household) {

        return service.update(id, household);
    }

    @GetMapping("/{id}/average")
    public Map<String, Object> getAverage(
            @PathVariable Long id) {

        double average =
                pickupService.getHouseholdAverage(id);

        return Map.of(
                "householdId", id,
                "averageScore", average
        );
    }

    @GetMapping("/{id}/reminder")
    public Map<String, Object> getReminder(
            @PathVariable Long id) {

        double average =
                pickupService.getHouseholdAverage(id);

        String status =
                average < 60
                        ? "REMINDER_REQUIRED"
                        : "COMPLIANT";

        return Map.of(
                "householdId", id,
                "averageScore", average,
                "status", status
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}