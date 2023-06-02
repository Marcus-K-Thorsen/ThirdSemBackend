package com.example.thirdsembackend.config;

import com.example.thirdsembackend.models.BoatType;
import com.example.thirdsembackend.models.Sailboat;
import com.example.thirdsembackend.repositories.SailboatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {

  @Autowired
  SailboatRepository sailboatRepository;

  @Override
  public void run(String... args) throws Exception {
    // Kode der skal køres, når projektet startes
    System.out.println("Starts InitData...");

    Sailboat boat1 = new Sailboat("Sankt Maria", BoatType.UNDER25FEET);
    Sailboat boat2 = new Sailboat("Louise", BoatType.BETWEEN25_40FEET);
    Sailboat boat3 = new Sailboat("Fiskebåden", BoatType.BEYOND40FEET);
    Sailboat boat4 = new Sailboat("Sankt Mario", BoatType.UNDER25FEET);
    Sailboat boat5 = new Sailboat("Lise", BoatType.BETWEEN25_40FEET);
    Sailboat boat6 = new Sailboat("Krabsbåden", BoatType.BEYOND40FEET);
    Sailboat boat7 = new Sailboat("Sankt Wario", BoatType.UNDER25FEET);
    Sailboat boat8 = new Sailboat("Tina", BoatType.BETWEEN25_40FEET);
    Sailboat boat9 = new Sailboat("Spruttebåden", BoatType.BEYOND40FEET);

    sailboatRepository.save(boat1);
    sailboatRepository.save(boat2);
    sailboatRepository.save(boat3);
    sailboatRepository.save(boat4);
    sailboatRepository.save(boat5);
    sailboatRepository.save(boat6);
    sailboatRepository.save(boat7);
    sailboatRepository.save(boat8);
    sailboatRepository.save(boat9);


    System.out.println("Ends InitData...");
  }
}
