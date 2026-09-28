package com.example.Wastetracker.Repository;

import com.example.Wastetracker.Entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
}