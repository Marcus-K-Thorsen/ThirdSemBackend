package com.example.thirdsembackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_sailboats")
public class Sailboat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "boat_id")
  private Long id;


  @Column(name = "boat_name")
  private String name;

  @Column(name = "boat_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private BoatType type;


  @OneToMany(mappedBy = "sailboat", cascade = CascadeType.REMOVE)
  @JsonBackReference
  private Set<Contestant> competitions = new HashSet<>();

  public Sailboat(String name, BoatType type) {
    this.name = name;
    this.type = type;
  }


  public Sailboat() {
  }


  public int getAmountOfCompetitions() {
    return competitions.size();
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Set<Contestant> getCompetitions() {
    return competitions;
  }

  public void setCompetitions(Set<Contestant> competitions) {
    this.competitions = competitions;
  }
}
