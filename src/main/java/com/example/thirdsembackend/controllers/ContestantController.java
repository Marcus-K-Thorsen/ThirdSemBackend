package com.example.thirdsembackend.controllers;

import com.example.thirdsembackend.models.Contestant;
import com.example.thirdsembackend.services.ContestantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ContestantController {

  @Autowired
  ContestantService service;


  @GetMapping("/contestants")
  public List<Contestant> getContestants() {
    return service.getAllContestants();
  }

  @GetMapping("/contestant/{id}")
  public Contestant getContestant(@PathVariable Long id) {
    return service.getContestant(id);
  }


  @PostMapping("/contestant")
  public ResponseEntity<Contestant> addContestant(@RequestBody Contestant contestant) {
    return service.addContestant(contestant);
  }


  @DeleteMapping("/contestant/{id}")
  public ResponseEntity<Contestant> deleteContestant(@PathVariable Long id) {
    return service.deleteContestant(id);
  }


}
