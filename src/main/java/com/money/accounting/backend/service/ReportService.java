package com.money.accounting.backend.service;

import com.money.accounting.backend.converter.ReportConverter;
import com.money.accounting.backend.converter.ZonedDateTimeConverter;
import com.money.accounting.backend.dto.request.ReportRequest;
import com.money.accounting.backend.exception.MoneyFinanceErrorCode;
import com.money.accounting.backend.exception.MoneyFinanceException;
import com.money.accounting.backend.model.Limit;
import com.money.accounting.backend.model.Loss;
import com.money.accounting.backend.model.Profit;
import com.money.accounting.backend.model.Report;
import com.money.accounting.backend.model.User;
import com.money.accounting.backend.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReportService extends EntityService<Report, ReportRequest> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);


    private final LossService lossService;
    private final AuditService auditService;
    private final LimitService limitService;
    private final ReportConverter converter;
    private final ProfitService profitService;
    private final ReportRepository reportRepository;
    private final ZonedDateTimeConverter dateConverter;

    public ReportService(LossService lossService,
                         AuditService auditService,
                         LimitService limitService,
                         ProfitService profitService,
                         ReportRepository reportRepository,
                         ReportConverter converter,
                         ZonedDateTimeConverter dateConverter) {
        this.lossService = lossService;
        this.auditService = auditService;
        this.limitService = limitService;
        this.profitService = profitService;
        this.reportRepository = reportRepository;
        this.converter = converter;
        this.dateConverter = dateConverter;
    }

    public Report create(ReportRequest request, User user) {
        Report report = new Report();
        report.setUser(user);
        inflateEntity(request, report);
        List<Profit> profits = profitService.getAllBetweenDates(user, report.getDateFrom(), report.getDateTo());
        List<Loss> losses = lossService.getAllBetweenDates(user, report.getDateFrom(), report.getDateTo());
        List<Limit> limits = limitService.getAllBetweenDates(user, report.getDateFrom(), report.getDateTo());
        report.setProfits(profits);
        report.setLosses(losses);
        report.setLimits(limits);
        this.save(report);
        return report;
    }

    public List<Report> getAll(User user) {
        return reportRepository.findAllByUserId(user.getId());
    }

    public Report get(User user, Integer id) throws MoneyFinanceException {
        return findByUserIdAndId(user.getId(), id);
    }

    @Override
    public Report inflateEntity(ReportRequest request, Report report) {
        String stringFrom = request.getDateFrom() + " 00:00:00";
        ZonedDateTime dateFrom = dateConverter.convert(stringFrom, ZoneId.of(report.getUser().getTimeZone()));
        String stringTo = request.getDateTo() + " 00:00:00";
        ZonedDateTime dateTo = dateConverter.convert(stringTo, ZoneId.of(report.getUser().getTimeZone()));
        report.setDateFrom(dateFrom);
        report.setDateTo(dateTo);
        return report;
    }

    @Override
    public Report findByUserIdAndId(Integer userId, Integer entityId) throws MoneyFinanceException {
        return reportRepository.findByUserIdAndId(userId, entityId)
                .orElseThrow(() -> new MoneyFinanceException(MoneyFinanceErrorCode.REPORT_NOT_EXIST));
    }

    @Override
    public void save(Report report) {
        reportRepository.save(report);
        LOGGER.info("Save report {}", report);
        auditService.save(report, converter);
    }

}
