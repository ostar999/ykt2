package org.eclipse.jetty.server.handler;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Locale;
import org.eclipse.jetty.util.DateCache;
import org.eclipse.jetty.util.RolloverFileOutputStream;

/* loaded from: classes9.dex */
public class DebugHandler extends HandlerWrapper {
    private DateCache _date = new DateCache("HH:mm:ss", Locale.US);
    private OutputStream _out;
    private PrintStream _print;

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStart() throws Exception {
        if (this._out == null) {
            this._out = new RolloverFileOutputStream("./logs/yyyy_mm_dd.debug.log", true);
        }
        this._print = new PrintStream(this._out);
        super.doStart();
    }

    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.handler.AbstractHandler, org.eclipse.jetty.util.component.AggregateLifeCycle, org.eclipse.jetty.util.component.AbstractLifeCycle
    public void doStop() throws Exception {
        super.doStop();
        this._print.close();
    }

    public OutputStream getOutputStream() {
        return this._out;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x025d  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0229  */
    @Override // org.eclipse.jetty.server.handler.HandlerWrapper, org.eclipse.jetty.server.Handler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handle(java.lang.String r22, org.eclipse.jetty.server.Request r23, javax.servlet.http.HttpServletRequest r24, javax.servlet.http.HttpServletResponse r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 704
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.DebugHandler.handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse):void");
    }

    public void setOutputStream(OutputStream outputStream) {
        this._out = outputStream;
    }
}
