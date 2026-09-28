package com.example.Wastetracker.Repository;

import com.example.Wastetracker.Entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}