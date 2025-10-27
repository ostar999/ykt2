package org.slf4j.impl;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/* loaded from: classes9.dex */
public class StaticLoggerBinder implements LoggerFactoryBinder {
    private final ILoggerFactory loggerFactory = new SimpleLoggerFactory();
    private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();
    public static String REQUESTED_API_VERSION = "1.6.99";
    private static final String loggerFactoryClassStr = SimpleLoggerFactory.class.getName();

    private StaticLoggerBinder() {
    }

    public static final StaticLoggerBinder getSingleton() {
        return SINGLETON;
    }

    @Override // org.slf4j.spi.LoggerFactoryBinder
    public ILoggerFactory getLoggerFactory() {
        return this.loggerFactory;
    }

    @Override // org.slf4j.spi.LoggerFactoryBinder
    public String getLoggerFactoryClassStr() {
        return loggerFactoryClassStr;
    }
}
