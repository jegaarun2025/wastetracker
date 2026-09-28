package com.example.Wastetracker.Repository;

import com.example.Wastetracker.Entity.PickupLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PickupLogRepository extends JpaRepository<PickupLog, Long> {

    List<PickupLog> findByHouseholdHouseholdId(Long householdId);

    List<PickupLog> findByHouseholdZoneZoneId(Long zoneId);
}