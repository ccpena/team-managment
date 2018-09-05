package com.vividseats.teamanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vividseats.teamanagment.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

}
