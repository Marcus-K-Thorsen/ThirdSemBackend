package com.example.thirdsembackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_boat_races")
public class BoatRace {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "race_id")
  private Long id;

  @Column(name = "race_date", nullable = false)
  private LocalDate date;

  @Column(name = "race_name")
  private String name;

  @Column(name = "race_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private BoatType type;


  @OneToMany(mappedBy = "boatRace", cascade = CascadeType.REMOVE)
  @JsonBackReference
  private Set<Contestant> competitors = new HashSet<>();


  public BoatRace(LocalDate date, String name, BoatType type) {
    this.date = date;
    this.name = name;
    this.type = type;
  }

  public BoatRace() {
  }




  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BoatType getType() {
    return type;
  }

  public void setType(BoatType type) {
    this.type = type;
  }

  public Set<Contestant> getCompetitors() {
    return competitors;
  }

  public void setCompetitors(Set<Contestant> competitors) {
    this.competitors = competitors;
  }
}
