package com.example.thirdsembackend.services;

import com.example.thirdsembackend.exceptions.ResourceExistsAlreadyException;
import com.example.thirdsembackend.exceptions.ResourceNotFoundException;
import com.example.thirdsembackend.models.BoatType;
import com.example.thirdsembackend.models.Sailboat;
import com.example.thirdsembackend.repositories.SailboatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SailboatService {

  @Autowired
  SailboatRepository repository;


  public List<Sailboat> getAllSailboats() {
    return repository.findAll();
  }


  public Sailboat getSailboat(Long id) {
    return repository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("No Sailboat with the ID: '" + id + "' within the Database, so is unable to get the Sailboat."));
  }

  public ResponseEntity<Sailboat> addSailboat(Sailboat sailboat) {

    Long sailboatId = sailboat.getId();
    if (sailboatId != null) {
      boolean sailboatExistsAlready = repository.existsById(sailboat.getId());
      if (sailboatExistsAlready) {
        throw new ResourceExistsAlreadyException("A Sailboat with the ID: '" + sailboatId + "' already exists in the Database and could therefore not add the Sailboat.");
      }
    }
    boolean sailboatHasNoType = sailboat.getType() == null;
    if (sailboatHasNoType) {
      throw new ResourceNotFoundException("A Sailboat must have a type associated with it, and therefore can't be added to the database.");
    }

    Sailboat addedSailboat = repository.save(sailboat);
    return new ResponseEntity<>(addedSailboat, HttpStatus.OK);
  }

  public ResponseEntity<Sailboat> updateSailboat(Sailboat sailboat) {
    Long sailboatId = sailboat.getId();
    if (sailboatId == null) {
      throw new ResourceNotFoundException("The Sailboat has an ID: 'null', so this Sailboat is not in the Database and can't be updated.");
    }
    Sailboat pastSailBoat = getSailboat(sailboatId);

    boolean sailboatDoesNotExist = !repository.existsById(sailboat.getId());
    if (sailboatDoesNotExist) {
      throw new ResourceNotFoundException("The Sailboat with the ID: '" + sailboatId + "' is not in the Database and can't be updated.");
    }

    BoatType newBoatType = sailboat.getType();
    BoatType pastBoatType = pastSailBoat.getType();

    if (newBoatType == null) {
      newBoatType = pastBoatType;
      sailboat.setType(pastBoatType);
    }
    if (newBoatType != pastBoatType) {
      throw new ResourceExistsAlreadyException("The Sailboat already has a BoatType: '" + pastBoatType + "' and can therefore not update it to: '" + newBoatType + "' within in the Database.");
    }

    if (sailboat.getName() == null || sailboat.getName().isBlank()) sailboat.setName(pastSailBoat.getName());
    Sailboat updatedSailboat = repository.save(sailboat);
    return new ResponseEntity<>(updatedSailboat, HttpStatus.OK);
  }


  public ResponseEntity<Sailboat> deleteSailboat(Long id) {
    boolean sailboatDoesNotExist = !repository.existsById(id);
    if (sailboatDoesNotExist) {
      throw new ResourceNotFoundException("The Sailboat with the ID: '" + id + "' is not in the Database and can't be deleted.");
    }

    Sailboat deletedSailboat = getSailboat(id);
    repository.deleteById(id);
    return new ResponseEntity<>(deletedSailboat, HttpStatus.OK);
  }
}
