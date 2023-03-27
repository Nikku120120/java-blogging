package com.example.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CoronaRepository extends JpaRepository<Corona, Long> {
    List<Corona> findByLastUpdatedBetween(LocalDateTime from, LocalDateTime to);
}
