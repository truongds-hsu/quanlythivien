package com.library.repository;

import com.library.model.Fine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FineRepository extends JpaRepository<Fine, Long> {
    List<Fine> findByPaidFalse();
    List<Fine> findByPaid(boolean paid);
}
