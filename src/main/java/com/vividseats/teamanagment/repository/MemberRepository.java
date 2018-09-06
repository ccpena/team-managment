package com.vividseats.teamanagment.repository;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.vividseats.teamanagment.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

  Member findByMemberId(Long memberId);

  Set<Member> findByTeamId(Long teamId);

}
