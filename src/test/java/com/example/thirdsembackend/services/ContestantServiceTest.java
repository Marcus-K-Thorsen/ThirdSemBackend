package com.example.thirdsembackend.services;

import com.example.thirdsembackend.exceptions.ResourceNotFoundException;
import com.example.thirdsembackend.models.*;
import com.example.thirdsembackend.repositories.ContestantRepository;
import org.junit.jupiter.api.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.*;

class ContestantServiceTest {

  @Mock
  private ContestantRepository repository;

  @InjectMocks
  private ContestantService service;

  private List<Contestant> mockedContestantDB = new ArrayList<>();

  @BeforeEach
  void setUp() {

    BoatRace boatRace = new BoatRace(1L, LocalDate.of(2023, 5, 3), "BoatRace1", BoatType.UNDER25FEET);

    Sailboat boat1 = new Sailboat(1L, "Sankt Maria", BoatType.UNDER25FEET);
    Contestant boatCon1 = new Contestant(1L, 1, FinishState.FINISHED, boat1, boatRace);
    boat1.addContestant(boatCon1);
    boatRace.addCompetitors(boatCon1);

    Sailboat boat2 = new Sailboat(2L, "Louise", BoatType.UNDER25FEET);
    Contestant boatCon2 = new Contestant(2L, 2, FinishState.FINISHED, boat2, boatRace);
    boat2.addContestant(boatCon2);
    boatRace.addCompetitors(boatCon2);

    Sailboat boat3 = new Sailboat(3L, "Fiskebåden", BoatType.UNDER25FEET);
    Contestant boatCon3 = new Contestant(3L, 3, FinishState.FINISHED, boat3, boatRace);
    boat3.addContestant(boatCon3);
    boatRace.addCompetitors(boatCon3);

    mockedContestantDB = Arrays.asList(boatCon1, boatCon2, boatCon3);

    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllContestants() {

    // Mock the behavior of the repository
    mockingFindAll();

    // Call the service method
    List<Contestant> result = service.getAllContestants();

    // Verify the result
    Assertions.assertEquals(3, result.size());
    Assertions.assertEquals(1L, result.get(0).getId());

  }

  @Test
  void getAllContestantsByRaceId() {
    // Mock the behavior of the repository
    Long correctBoatRaceId = 1L;
    Long incorrectBoatRaceId = 0L;
    mockingIsTheBoatRaceNotInTheDatabase(correctBoatRaceId);
    mockingIsTheBoatRaceNotInTheDatabase(incorrectBoatRaceId);

    mockingFindContestantsByBoatRaceId(correctBoatRaceId);
    mockingFindContestantsByBoatRaceId(incorrectBoatRaceId);

    // Call the service method
    Assertions.assertDoesNotThrow(()-> service.getAllContestantsByRaceId(correctBoatRaceId));
    List<Contestant> result = service.getAllContestantsByRaceId(correctBoatRaceId);


    // Verify the result
    Assertions.assertThrows( ResourceNotFoundException.class,()-> service.getAllContestantsByRaceId(incorrectBoatRaceId));

    Assertions.assertEquals(3, result.size());
    Assertions.assertEquals(correctBoatRaceId, result.get(0).getBoatRace().getId());


  }

  @Test
  void getContestant() {

    // Mock the behavior of the repository
    Long correctContestantId = 2L;
    Long incorrectContestantId = 0L;
    mockingFindById(correctContestantId);
    mockingFindById(incorrectContestantId);

    // Call the service method
    Assertions.assertDoesNotThrow(()-> service.getContestant(correctContestantId));
    Contestant result = service.getContestant(correctContestantId);

    // Verify the result
    Assertions.assertThrows( ResourceNotFoundException.class,()-> service.getContestant(incorrectContestantId));
    Assertions.assertEquals(correctContestantId, result.getId());
    Assertions.assertNotEquals(incorrectContestantId, result.getId());

  }


  private void mockingFindAll() {
    when(repository.findAll()).thenReturn(mockedContestantDB);
  }


  private void mockingIsTheBoatRaceNotInTheDatabase(Long raceId) {
    boolean boatRaceIsNotInDataBase = true;
    for (Contestant contestant : mockedContestantDB) {
      if (Objects.equals(contestant.getId(), raceId)) {
        boatRaceIsNotInDataBase = false;
        break;
      }
    }
    when(repository.isTheBoatRaceNotInTheDatabase(raceId)).thenReturn(boatRaceIsNotInDataBase);
  }

  private void mockingFindContestantsByBoatRaceId(Long raceId) {
    List<Contestant> contestantsWithSameRaceId = new ArrayList<>();
    for (Contestant contestant : mockedContestantDB) {
      Long contestantRaceId = contestant.getBoatRace().getId();
      if (Objects.equals(contestantRaceId, raceId)) {
        contestantsWithSameRaceId.add(contestant);
      }
    }
    when(repository.findContestantsByBoatRaceId(raceId)).thenReturn(contestantsWithSameRaceId);
  }

  private void mockingFindById(Long id) {

    Contestant foundContestant = null;
    for (Contestant contestant : mockedContestantDB) {
      if (Objects.equals(contestant.getId(), id)) {
        foundContestant = contestant;
      }
    }
    Optional<Contestant> optionalContestant = Optional.ofNullable(foundContestant);
    when(repository.findById(id)).thenReturn(optionalContestant);
  }
}