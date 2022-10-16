package com.example.springSecurity.person.right;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RightRepository extends JpaRepository<RightEntity, Integer> {

    Optional<RightEntity> findRightByRightName (String rightName);

    RightEntity findRightByRightId(Integer rightId);
}
