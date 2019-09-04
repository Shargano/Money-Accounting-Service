package com.money.accounting.backend.service;

import com.money.accounting.backend.converter.MoneyEntityConverter;
import com.money.accounting.backend.model.MoneyEntity;
import com.money.accounting.backend.model.audit.Audit;
import com.money.accounting.backend.repository.AuditRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void save(MoneyEntity entity, MoneyEntityConverter converter) {
        Audit audit = inflateEntity(entity, converter);
        auditRepository.save(audit);
    }

    public Audit inflateEntity(MoneyEntity entity, MoneyEntityConverter converter) {
        Audit audit = new Audit();
        audit.setObjectState(converter.from(entity).toString());
        return audit;
    }
}
