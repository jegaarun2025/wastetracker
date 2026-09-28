package com.example.Wastetracker.Repository;

import com.example.Wastetracker.Entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
}