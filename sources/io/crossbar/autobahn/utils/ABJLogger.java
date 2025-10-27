package io.crossbar.autobahn.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes8.dex */
class ABJLogger implements IABLogger {

    /* renamed from: a, reason: collision with root package name */
    public final Logger f27149a;

    public ABJLogger(String str) {
        this.f27149a = Logger.getLogger(str);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void a(String str) {
        Logger logger = this.f27149a;
        logger.logp(Level.INFO, logger.getName(), (String) null, str);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void b(String str, Throwable th) {
        Logger logger = this.f27149a;
        logger.logp(Level.FINER, logger.getName(), (String) null, str, th);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void c(String str) {
        Logger logger = this.f27149a;
        logger.logp(Level.SEVERE, logger.getName(), (String) null, str);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void d(String str) {
        Logger logger = this.f27149a;
        logger.logp(Level.FINER, logger.getName(), (String) null, str);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void e(String str) {
        Logger logger = this.f27149a;
        logger.logp(Level.WARNING, logger.getName(), (String) null, str);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void a(String str, Throwable th) {
        Logger logger = this.f27149a;
        logger.logp(Level.WARNING, logger.getName(), (String) null, str, th);
    }

    @Override // io.crossbar.autobahn.utils.IABLogger
    public void b(String str) {
        Logger logger = this.f27149a;
        logger.logp(Level.FINE, logger.getName(), (String) null, str);
    }
}
