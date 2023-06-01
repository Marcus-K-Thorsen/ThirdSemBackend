package com.example.thirdsembackend.models;

import jakarta.persistence.*;
import org.hibernate.annotations.Check;

import java.util.Set;

@Entity
@Table(name = "tbl_contestants")
public class Contestant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "contestant_id")
  private Long id;

  @Column(name = "position", nullable = false)
  @Check(constraints = "position >= 0")
  private int position;

  @Column(name = "state", nullable = false)
  @Enumerated(EnumType.STRING)
  private FinishState state;

  @ManyToOne
  @JoinColumn(name = "contestant_boat_id", referencedColumnName = "boat_id")
  private Sailboat sailboat;

  @ManyToOne
  @JoinColumn(name = "contestant_race_id", referencedColumnName = "race_id")
  private BoatRace boatRace;



  public Contestant() {

  }


  public int getPoints() {
    int calculatedPoints = position;
    if (state != FinishState.FINISHED) {
      calculatedPoints += state.getPoints();
      Set<Contestant> everyContestantInTheBoatRace = boatRace.getCompetitors();

      for (Contestant contestant : everyContestantInTheBoatRace) {
        if (contestant.state != FinishState.DIDNT_START) {
          calculatedPoints += 1;
        }
      }
    }
    return calculatedPoints;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  public FinishState getState() {
    return state;
  }

  public void setState(FinishState state) {
    this.state = state;
  }

  public Sailboat getSailboat() {
    return sailboat;
  }

  public void setSailboat(Sailboat sailboat) {
    this.sailboat = sailboat;
  }

  public BoatRace getBoatRace() {
    return boatRace;
  }

  public void setBoatRace(BoatRace boatRace) {
    this.boatRace = boatRace;
  }
}
