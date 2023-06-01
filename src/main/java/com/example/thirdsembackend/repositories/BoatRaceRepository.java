package com.example.thirdsembackend.repositories;

import com.example.thirdsembackend.models.BoatRace;
import com.example.thirdsembackend.models.BoatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BoatRaceRepository extends JpaRepository<BoatRace, Long> {

  boolean existsBoatRaceByDateAndType(LocalDate date, BoatType type);
}
