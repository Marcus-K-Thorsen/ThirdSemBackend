package com.example.thirdsembackend.models;

import java.util.Arrays;
import java.util.List;

public enum FinishState {

  NOT_COMPLETED(1, "Ikke fuldført"),
  STARTED_TOO_EARLY(1, "For tidligt startet"),
  DIDNT_START(2, "Ikke startet"),
  FINISHED(0, "Fuldført");

  private final int points;
  private final String description;

  FinishState (int points, String description) {
    this.points = points;
    this.description = description;
  }

  // Getter
  public int getPoints() {
    return points;
  }
  public String getDescription() {
    return description;
  }

  // Custom made Metoder
  public static List<FinishState> getAllFinishStates() {
    return Arrays.asList(FinishState.class.getEnumConstants());
  }
}
