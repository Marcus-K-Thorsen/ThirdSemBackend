package com.example.thirdsembackend.services;

import com.example.thirdsembackend.exceptions.ResourceExistsAlreadyException;
import com.example.thirdsembackend.exceptions.ResourceNotFoundException;
import com.example.thirdsembackend.models.BoatRace;
import com.example.thirdsembackend.models.Contestant;
import com.example.thirdsembackend.models.FinishState;
import com.example.thirdsembackend.models.Sailboat;
import com.example.thirdsembackend.repositories.ContestantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestantService {

  @Autowired
  ContestantRepository repository;


  public List<Contestant> getAllContestants() {
    return repository.findAll();
  }

  public List<Contestant> getAllContestantsByRaceId(Long raceId) {
    boolean theBoatRaceDoesntExist = repository.isTheBoatRaceNotInTheDatabase(raceId);
    if (theBoatRaceDoesntExist) {
      throw new ResourceNotFoundException("The requested Contestants, within the BoatRace with the ID: '" + raceId + "', could not be found as that BoatRace isn't within the Database.");
    }

    List<Contestant> contestantsInRace = repository.findContestantsByBoatRaceId(raceId);
    return contestantsInRace;
  }

  public Contestant getContestant(Long id) {
    return repository.findById(id).orElseThrow(
        () -> new ResourceNotFoundException("No Contestant with the ID: '" + id + "' within the Database, so is unable to get the Contestant."));
  }


  public ResponseEntity<Contestant> addContestant(Contestant contestant) {

    Long contestantId = contestant.getId();
    if (contestantId != null) {
      boolean contestantExistsAlready = repository.existsById(contestant.getId());
      if (contestantExistsAlready) {
        throw new ResourceExistsAlreadyException("A Contestant with the ID: '" + contestantId + "' already exists in the Database and could therefore not add the Contestant.");
      }
    }

    Sailboat competingBoat = contestant.getSailboat();
    BoatRace competitionRace = contestant.getBoatRace();

    if (competingBoat == null) {
      throw new ResourceNotFoundException("A Contestant must have a Sailboat, to compete with, which is set to: 'null', and could therefore not add the Contestant to the Database.");
    }
    if (competitionRace == null) {
      throw new ResourceNotFoundException("A Contestant must have a BoatRace, to compete in, which is set to: 'null', and could therefore not add the Contestant to the Database.");
    }

    Long competingBoatId = competingBoat.getId();
    Long competitionRaceId = competitionRace.getId();
    competingBoat = repository.findSailboatById(competingBoatId).orElseThrow(
        () -> new ResourceNotFoundException("The Contestant had a Sailboat with the ID: '" + competingBoatId + "' that does not exist in the Database and could therefore not add the Contestant."));
    competitionRace = repository.findBoatRaceById(competitionRaceId).orElseThrow(
        () -> new ResourceNotFoundException("The Contestant had a BoatRace with the ID: '" + competitionRaceId + "' that does not exist in the Database and could therefore not add the Contestant."));

    boolean sailboatAndBoatRaceAreDifferentTypes = competingBoat.getType() != competitionRace.getType();
    if (sailboatAndBoatRaceAreDifferentTypes) {
      throw new ResourceNotFoundException("The Contestant's Sailboat was of the Type: '"  + competingBoat.getType() + "' while the BoatRace was of the Type: '" + competitionRace.getType() + "'. The boat must be the same type as the race, so can not add the Contestant to the Database.");
    }

    boolean contestantWithThatBoatAndRaceAlreadyExists = repository.existsContestantBySailboatAndBoatRace(competingBoat, competitionRace);
    if (contestantWithThatBoatAndRaceAlreadyExists) {
      throw new ResourceExistsAlreadyException("There already exists a Contestant with a Sailboat of ID: '" + competingBoatId + "' competing in a BoatRace with the ID: '" + competitionRaceId + "' within the Database and can therefore not add the Contestant.");
    }

    contestant.setSailboat(competingBoat);
    contestant.setBoatRace(competitionRace);

    if (contestant.getPosition() < 0) {
      throw new ResourceNotFoundException("The Contestant must have a Position of higher than or equal to '0', but had Position: '" + contestant.getPosition() + "' and can therefore not add the Contestant to the Database.");
    } else if (contestant.getPosition() > 0) {
      boolean contestantWithPositionAndBoatRaceAlreadyExists = repository.existsContestantByPositionAndBoatRace(contestant.getPosition(), competitionRace);
      if (contestantWithPositionAndBoatRaceAlreadyExists) {
        throw new ResourceExistsAlreadyException("There already is a Contestant with the Position: '" + contestant.getPosition() + "' in the BoatRace with ID: '" + competitionRace.getId() + "' and can therefore not add the contestant to the Database.");
      }
      contestant.setState(FinishState.FINISHED);
    } else {
      if (contestant.getState() == null || contestant.getState() == FinishState.FINISHED) {
        throw new ResourceNotFoundException("The Contestant has not Finished the BoatRace as their position is '0', and the FinishState is either null or set to be Finished, and therefore can the Contestant not be added to the Database as long it does not have a proper FinishState.");
      }
    }


    Contestant addedContestant = repository.save(contestant);
    return new ResponseEntity<>(addedContestant, HttpStatus.OK);
  }


  public ResponseEntity<Contestant> deleteContestant(Long id) {
    boolean contestantDoesNotExist = !repository.existsById(id);
    if (contestantDoesNotExist) {
      throw new ResourceNotFoundException("The Contestant with the ID: '" + id + "' is not in the Database and can't be deleted.");
    }

    Contestant deletedContestant = getContestant(id);
    repository.deleteById(id);
    return new ResponseEntity<>(deletedContestant, HttpStatus.OK);
  }


}
