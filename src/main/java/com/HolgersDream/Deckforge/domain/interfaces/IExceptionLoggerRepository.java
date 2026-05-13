package com.HolgersDream.Deckforge.domain.interfaces;

public interface IExceptionLoggerRepository {
    void saveExceptionMessage(Exception e, String details);
}
