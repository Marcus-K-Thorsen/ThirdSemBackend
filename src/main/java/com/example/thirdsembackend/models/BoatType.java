package com.example.thirdsembackend.models;

import java.util.Arrays;
import java.util.List;

public enum BoatType {

  UNDER25FEET("Under 25 fod"),
  BETWEEN25_40FEET("Mellem 25 og 40 fod"),
  BEYOND40FEET("Over 40 fod");


  private final String description;

  // Constructor
  BoatType(String description) {
    this.description = description;
  }

  // Getter
  public String getDescription() {
    return description;
  }

  // Custom made Metoder
  public static List<BoatType> getAllBoatTypes() {
    return Arrays.asList(BoatType.class.getEnumConstants());
  }
}
