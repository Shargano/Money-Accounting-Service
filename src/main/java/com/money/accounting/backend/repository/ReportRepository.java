package com.money.accounting.backend.repository;

import com.money.accounting.backend.model.Report;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends MoneyRepository<Report> {
    Optional<Report> findByUserIdAndId(Integer userId, Integer reportId);

    List<Report> findAllByUserId(Integer userId);
}
