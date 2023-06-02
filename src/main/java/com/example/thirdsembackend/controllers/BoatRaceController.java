package com.example.thirdsembackend.controllers;

import com.example.thirdsembackend.models.BoatRace;
import com.example.thirdsembackend.services.BoatRaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class BoatRaceController {

  @Autowired
  BoatRaceService service;

  @GetMapping("/create")
  public List<BoatRace> createBoatRaces() {
    return service.createBoatRaces();
  }

  @GetMapping("/boatraces")
  public List<BoatRace> getBoatRaces() {
    return service.getAllBoatRaces();
  }

  @GetMapping("/boatrace/{id}")
  public BoatRace getBoatRace(@PathVariable Long id) {
    return service.getBoatRace(id);
  }

  @PostMapping("/boatrace")
  public ResponseEntity<BoatRace> addBoatRace(@RequestBody BoatRace boatRace) {
    return service.addBoatRace(boatRace);
  }

  @PutMapping("/boatrace")
  public ResponseEntity<BoatRace> updateBoatRace(@RequestBody BoatRace boatRace) {
    return service.updateBoatRace(boatRace);
  }

  @DeleteMapping("/boatrace/{id}")
  public ResponseEntity<BoatRace> deleteBoatRace(@PathVariable Long id) {
    return service.deleteBoatRace(id);
  }
}
