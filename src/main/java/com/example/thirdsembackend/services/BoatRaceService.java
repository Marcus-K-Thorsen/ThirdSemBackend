package com.example.thirdsembackend.services;

import com.example.thirdsembackend.exceptions.ResourceExistsAlreadyException;
import com.example.thirdsembackend.exceptions.ResourceNotFoundException;
import com.example.thirdsembackend.models.BoatRace;
import com.example.thirdsembackend.models.BoatType;
import com.example.thirdsembackend.repositories.BoatRaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class BoatRaceService {

  @Autowired
  BoatRaceRepository repository;


  public List<BoatRace> getAllBoatRaces() {
    return repository.findAll();
  }

  public BoatRace getBoatRace(Long id) {
    return repository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("No BoatRace with the ID: '" + id + "' within the Database, so is unable to get the BoatRace."));
  }


  public ResponseEntity<BoatRace> addBoatRace(BoatRace boatRace) {
    Long boatRaceId = boatRace.getId();
    if (boatRaceId != null) {
      boolean boatRaceExistsAlready = repository.existsById(boatRaceId);
      if (boatRaceExistsAlready) {
        throw new ResourceExistsAlreadyException("A BoatRace with the ID: '" + boatRaceId + "' already exists in the Database and could therefore not add the BoatRace.");
      }
    }
    boolean boatRaceHasNoType = boatRace.getType() == null;
    if (boatRaceHasNoType) {
      throw new ResourceNotFoundException("A BoatRace must have a Type associated with it, and therefore can't be added to the database.");
    }

    boolean boatRaceHasNoDate = boatRace.getDate() == null;
    if (boatRaceHasNoDate) {
      throw new ResourceNotFoundException("A BoatRace must have a Date associated with it, and therefore can't be added to the database.");
    }

    boolean boatRaceDateIsNotWednesday = boatRace.getDate().getDayOfWeek() != DayOfWeek.WEDNESDAY;
    if (boatRaceDateIsNotWednesday) {
      throw new ResourceNotFoundException("A BoatRace must have a date that is on a Wednesday, while the BoatRace that was tried to be posted is on the day: '" + boatRace.getDate().getDayOfWeek() + "' and could therefore not add to the Database");
    }

    boolean boatRaceWithDateAndTypeAlreadyExists = repository.existsBoatRaceByDateAndType(boatRace.getDate(), boatRace.getType());
    if (boatRaceWithDateAndTypeAlreadyExists) {
      throw new ResourceExistsAlreadyException("A BoatRace with the Date: '" + boatRace.getDate() + "' and Type: '" + boatRace.getType() + "' already exists in the Database and can therefore not add the BoatRace.");
    }

    BoatRace savedBoatRace = repository.save(boatRace);
    return new ResponseEntity<>(savedBoatRace, HttpStatus.OK);
  }

  public ResponseEntity<BoatRace> updateBoatRace(BoatRace boatRace) {

    Long boatRaceId = boatRace.getId();

    if (boatRaceId == null) {
      throw new ResourceNotFoundException("The BoatRace has an ID: 'null', so this BoatRace is not in the Database and can't be updated.");
    }
    BoatRace pastBoatRace = getBoatRace(boatRaceId);

    boolean boatRaceDoesNotExist = !repository.existsById(boatRace.getId());
    if (boatRaceDoesNotExist) {
      throw new ResourceNotFoundException("The BoatRace with the ID: '" + boatRaceId + "' is not in the Database and can't be updated.");
    }

    BoatType newRaceType = boatRace.getType();
    BoatType pastRaceType = pastBoatRace.getType();
    if (newRaceType == null) {
      boatRace.setType(pastRaceType);
      newRaceType = pastRaceType;
    }
    if (newRaceType != pastRaceType) {
      throw new ResourceExistsAlreadyException("The BoatRace already has a BoatType: '" + pastRaceType + "' and can therefore not update it to: '" + newRaceType + "' within in the Database.");
    }

    LocalDate newRaceDate = boatRace.getDate();
    LocalDate pastRaceDate = pastBoatRace.getDate();
    if (newRaceDate == null) {
      boatRace.setDate(pastRaceDate);
      newRaceDate = pastRaceDate;
    }

    if (newRaceDate.compareTo(pastRaceDate) != 0) {
      throw new ResourceExistsAlreadyException("The BoatRace already has a Date: '" + pastRaceDate + "' and can therefore not update it to: '" + newRaceDate + "' within in the Database.");
    }
    String newBoatRaceName = boatRace.getName();

    if (newBoatRaceName == null || newBoatRaceName.isBlank()) {
      boatRace.setName(pastBoatRace.getName());
    }
    BoatRace updatedBoatRace = repository.save(boatRace);
    return new ResponseEntity<>(updatedBoatRace, HttpStatus.OK);
  }


  public ResponseEntity<BoatRace> deleteBoatRace(Long id) {
    boolean boatRaceDoesNotExist = !repository.existsById(id);
    if (boatRaceDoesNotExist) {
      throw new ResourceNotFoundException("The BoatRace with the ID: '" + id + "' is not in the Database and can't be deleted.");
    }

    BoatRace deletedBoatRace = getBoatRace(id);
    repository.deleteById(id);
    return new ResponseEntity<>(deletedBoatRace, HttpStatus.OK);
  }

  public List<BoatRace> createBoatRaces() {
    if (getAllBoatRaces().size() == 0) {
      LocalDate dateOfRace = LocalDate.of(2023, 5, 3);
      List<BoatType> boatTypes = BoatType.getAllBoatTypes();
      int amountOfWednesdays = 22;
      for (int i = 1; i <= amountOfWednesdays; i++) {
        for (BoatType boatType : boatTypes) {
          String boatRaceName = boatType + " nr. " + i;
          BoatRace race = new BoatRace(dateOfRace, boatRaceName, boatType);
          addBoatRace(race);
        }
        dateOfRace = dateOfRace.plusDays(7);
      }
    }
    return getAllBoatRaces();
  }
}
