package com.example.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoronaRepository extends JpaRepository<Corona, Long> {
}
