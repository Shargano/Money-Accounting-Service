package com.money.accounting.backend.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
public class Report extends MoneyEntity {
    @ManyToMany
    @JoinTable(
            name = "report_profit",
            joinColumns = {@JoinColumn(name = "report_id")},
            inverseJoinColumns = {@JoinColumn(name = "profit_id")}
    )
    private List<Profit> profits;

    @ManyToMany
    @JoinTable(
            name = "report_loss",
            joinColumns = {@JoinColumn(name = "report_id")},
            inverseJoinColumns = {@JoinColumn(name = "loss_id")}
    )
    private List<Loss> losses;

    @ManyToMany
    @JoinTable(
            name = "report_limit",
            joinColumns = {@JoinColumn(name = "report_id")},
            inverseJoinColumns = {@JoinColumn(name = "limit_id")}
    )
    private List<Limit> limits;
    private ZonedDateTime dateFrom;
    private ZonedDateTime dateTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Report() {
    }

    public List<Profit> getProfits() {
        return profits;
    }

    public void setProfits(List<Profit> profits) {
        this.profits = profits;
    }

    public List<Loss> getLosses() {
        return losses;
    }

    public void setLosses(List<Loss> losses) {
        this.losses = losses;
    }

    public List<Limit> getLimits() {
        return limits;
    }

    public void setLimits(List<Limit> limits) {
        this.limits = limits;
    }

    public ZonedDateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(ZonedDateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public ZonedDateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(ZonedDateTime dateTo) {
        this.dateTo = dateTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
