package com.example.Wastetracker.Controller;

import com.example.Wastetracker.Entity.PickupLog;
import com.example.Wastetracker.Service.PickupLogService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pickups")
public class PickupLogController {

    private final PickupLogService service;

    public PickupLogController(PickupLogService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PickupLog create(
            @Valid @RequestBody PickupLog log) {

        return service.create(log);
    }

    @GetMapping
    public List<PickupLog> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PickupLog getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public PickupLog update(
            @PathVariable Long id,
            @Valid @RequestBody PickupLog log) {

        return service.update(id, log);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/zone/{zoneId}/average")
    public Map<String, Object> getZoneAverage(
            @PathVariable Long zoneId) {

        double average =
                service.getZoneAverage(zoneId);

        return Map.of(
                "zoneId", zoneId,
                "averageSegregationScore", average
        );
    }
}