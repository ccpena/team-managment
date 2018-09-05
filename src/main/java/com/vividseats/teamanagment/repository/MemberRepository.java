package com.vividseats.teamanagment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vividseats.teamanagment.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

}
