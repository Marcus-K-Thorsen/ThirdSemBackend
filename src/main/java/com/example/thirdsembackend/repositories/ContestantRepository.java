package com.example.thirdsembackend.repositories;

import com.example.thirdsembackend.models.BoatRace;
import com.example.thirdsembackend.models.Contestant;
import com.example.thirdsembackend.models.Sailboat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContestantRepository extends JpaRepository<Contestant, Long> {

  @Query("SELECT s FROM Sailboat s WHERE s.id = :sailboatId")
  Optional<Sailboat> findSailboatById(@Param("sailboatId") Long sailboatId);

  @Query("SELECT br FROM BoatRace br WHERE br.id = :boatRaceId")
  Optional<BoatRace> findBoatRaceById(@Param("boatRaceId") Long boatRaceId);

  boolean existsContestantBySailboatAndBoatRace(Sailboat sailboat, BoatRace boatRace);

  boolean existsContestantByPositionAndBoatRace(int position, BoatRace boatRace);
  
  @Query("""
  SELECT
    CASE
      WHEN COUNT(br) > 0
      THEN false ELSE true
    END
  FROM BoatRace br
  WHERE br.id = :raceId
""")
  boolean isTheBoatRaceNotInTheDatabase(@Param("raceId") Long raceId);

  @Query("SELECT c FROM Contestant c WHERE c.boatRace.id = :boatRaceId")
  List<Contestant> findContestantsByBoatRaceId(@Param("boatRaceId") Long boatRaceId);

}
