package com.example.thirdsembackend.controllers;

import com.example.thirdsembackend.models.BoatType;
import com.example.thirdsembackend.models.FinishState;
import com.example.thirdsembackend.services.EnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class EnumController {

  @Autowired
  EnumService service;


  @GetMapping("/types")
  public List<BoatType> getBoatTypes() {
    return service.getAllBoatTypes();
  }

  @GetMapping("/states")
  public List<FinishState> getFinishStates() {
    return service.getAllFinishStates();
  }

  @GetMapping("/type/description/{boatType}")
  public String getBoatTypeDescription(@PathVariable String boatType) {
    return service.getBoatTypeDescription(boatType);
  }

  @GetMapping("/state/description/{finishState}")
  public String getFinishStateDescription(@PathVariable String finishState) {
    return service.getFinishStateDescription(finishState);
  }
}
