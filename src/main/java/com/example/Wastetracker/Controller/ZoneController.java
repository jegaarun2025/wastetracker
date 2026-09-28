package com.example.Wastetracker.Controller;

import com.example.Wastetracker.Entity.Zone;
import com.example.Wastetracker.Service.ZoneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {

    private final ZoneService service;

    public ZoneController(ZoneService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Zone create(@Valid @RequestBody Zone zone) {
        return service.create(zone);
    }

    @GetMapping
    public List<Zone> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Zone getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public Zone update(
            @PathVariable Long id,
            @Valid @RequestBody Zone zone) {

        return service.update(id, zone);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}