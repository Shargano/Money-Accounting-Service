package com.money.accounting.backend.service;

import com.money.accounting.backend.exception.MoneyFinanceException;

public abstract class EntityService<Entity, Request> {

    public abstract Entity inflateEntity(Request request, Entity entity);

    public abstract Entity findByUserIdAndId(Integer userId, Integer entityId) throws MoneyFinanceException;

    public abstract void save(Entity entity);

}
