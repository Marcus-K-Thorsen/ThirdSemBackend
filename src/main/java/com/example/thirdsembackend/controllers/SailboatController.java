package com.example.thirdsembackend.controllers;

import com.example.thirdsembackend.models.Sailboat;
import com.example.thirdsembackend.services.SailboatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SailboatController {

  @Autowired
  SailboatService service;



  @GetMapping("/sailboats")
  public List<Sailboat> getSailboats() {
    return service.getAllSailboats();
  }

  @GetMapping("/sailboat/{id}")
  public Sailboat getSailboat(@PathVariable Long id) {
    return service.getSailboat(id);
  }

  @PostMapping("/sailboat")
  public ResponseEntity<Sailboat> addSailboat(@RequestBody Sailboat sailboat) {
    return service.addSailboat(sailboat);
  }

  @PutMapping("/sailboat")
  public ResponseEntity<Sailboat> updateSailboat(@RequestBody Sailboat sailboat) {
    return service.updateSailboat(sailboat);
  }

  @DeleteMapping("/sailboat/{id}")
  public ResponseEntity<Sailboat> deleteSailboat(@PathVariable Long id) {
    return service.deleteSailboat(id);
  }



}
