package com.example.thirdsembackend.repositories;

import com.example.thirdsembackend.models.Sailboat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SailboatRepository extends JpaRepository<Sailboat, Long> {
}
