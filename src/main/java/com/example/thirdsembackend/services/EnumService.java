package com.example.thirdsembackend.services;

import com.example.thirdsembackend.exceptions.ResourceNotFoundException;
import com.example.thirdsembackend.models.BoatType;
import com.example.thirdsembackend.models.FinishState;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnumService {

  public List<BoatType> getAllBoatTypes() {
    return BoatType.getAllBoatTypes();
  }

  public List<FinishState> getAllFinishStates() {
    return FinishState.getAllFinishStates();
  }

  public String getFinishStateDescription(String finishState) {
    List<FinishState> everyFinishState = getAllFinishStates();

    for (FinishState state : everyFinishState) {
      String stateAsString = state.toString();
      if (stateAsString.equals(finishState)) {
        return state.getDescription();
      }
    }
    throw new ResourceNotFoundException("There is no FinishState with the name of: '" + finishState + "' and therefore not able to get the Description of the FinishState.");
  }

  public String getBoatTypeDescription(String boatType) {
    List<BoatType> everyBoatType = getAllBoatTypes();

    for (BoatType type : everyBoatType) {
      String typeAsString = type.toString();
      if (typeAsString.equals(boatType)) {
        return type.getDescription();
      }
    }
    throw new ResourceNotFoundException("There is no BoatType with the name of: '" + boatType + "' and therefore not able to not get the Description of the BoatType.");
  }
}
