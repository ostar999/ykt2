package org.eclipse.jetty.util.component;

import java.io.FileWriter;
import java.io.IOException;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class FileNoticeLifeCycleListener implements LifeCycle.Listener {
    Logger LOG = Log.getLogger((Class<?>) FileNoticeLifeCycleListener.class);
    private final String _filename;

    public FileNoticeLifeCycleListener(String str) {
        this._filename = str;
    }

    private void writeState(String str, LifeCycle lifeCycle) throws IOException {
        try {
            FileWriter fileWriter = new FileWriter(this._filename, true);
            fileWriter.append((CharSequence) str).append((CharSequence) " ").append((CharSequence) lifeCycle.toString()).append((CharSequence) "\n");
            fileWriter.close();
        } catch (Exception e2) {
            this.LOG.warn(e2);
        }
    }

    @Override // org.eclipse.jetty.util.component.LifeCycle.Listener
    public void lifeCycleFailure(LifeCycle lifeCycle, Throwable th) throws IOException {
        writeState(AbstractLifeCycle.FAILED, lifeCycle);
    }

    @Override // org.eclipse.jetty.util.component.LifeCycle.Listener
    public void lifeCycleStarted(LifeCycle lifeCycle) throws IOException {
        writeState(AbstractLifeCycle.STARTED, lifeCycle);
    }

    @Override // org.eclipse.jetty.util.component.LifeCycle.Listener
    public void lifeCycleStarting(LifeCycle lifeCycle) throws IOException {
        writeState(AbstractLifeCycle.STARTING, lifeCycle);
    }

    @Override // org.eclipse.jetty.util.component.LifeCycle.Listener
    public void lifeCycleStopped(LifeCycle lifeCycle) throws IOException {
        writeState(AbstractLifeCycle.STOPPED, lifeCycle);
    }

    @Override // org.eclipse.jetty.util.component.LifeCycle.Listener
    public void lifeCycleStopping(LifeCycle lifeCycle) throws IOException {
        writeState(AbstractLifeCycle.STOPPING, lifeCycle);
    }
}
