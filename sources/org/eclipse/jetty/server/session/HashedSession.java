package org.eclipse.jetty.server.session;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.util.IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class HashedSession extends AbstractSession {
    private static final Logger LOG = Log.getLogger((Class<?>) HashedSession.class);
    private final HashSessionManager _hashSessionManager;
    private transient boolean _idled;
    private transient boolean _saveFailed;

    public HashedSession(HashSessionManager hashSessionManager, HttpServletRequest httpServletRequest) {
        super(hashSessionManager, httpServletRequest);
        this._idled = false;
        this._saveFailed = false;
        this._hashSessionManager = hashSessionManager;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSession
    public void checkValid() throws IllegalStateException {
        if (this._hashSessionManager._idleSavePeriodMs != 0) {
            deIdle();
        }
        super.checkValid();
    }

    public synchronized void deIdle() {
        FileInputStream fileInputStream;
        Exception e2;
        if (isIdled()) {
            access(System.currentTimeMillis());
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("De-idling " + super.getId(), new Object[0]);
            }
            try {
                File file = new File(this._hashSessionManager._storeDir, super.getId());
                if (!file.exists() || !file.canRead()) {
                    throw new FileNotFoundException(file.getName());
                }
                fileInputStream = new FileInputStream(file);
                try {
                    this._idled = false;
                    this._hashSessionManager.restoreSession(fileInputStream, this);
                    IO.close(fileInputStream);
                    didActivate();
                    if (this._hashSessionManager._savePeriodMs == 0) {
                        file.delete();
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    LOG.warn("Problem de-idling session " + super.getId(), e2);
                    if (fileInputStream != null) {
                        IO.close(fileInputStream);
                    }
                    invalidate();
                }
            } catch (Exception e4) {
                fileInputStream = null;
                e2 = e4;
            }
        }
    }

    @Override // org.eclipse.jetty.server.session.AbstractSession
    public void doInvalidate() throws IllegalStateException {
        super.doInvalidate();
        if (this._hashSessionManager._storeDir == null || getId() == null) {
            return;
        }
        new File(this._hashSessionManager._storeDir, getId()).delete();
    }

    public synchronized void idle() throws Exception {
        save(false);
        this._idled = true;
    }

    public synchronized boolean isIdled() {
        return this._idled;
    }

    public synchronized boolean isSaveFailed() {
        return this._saveFailed;
    }

    public synchronized void save(boolean z2) throws Exception {
        File file;
        if (!isIdled() && !this._saveFailed) {
            Logger logger = LOG;
            if (logger.isDebugEnabled()) {
                logger.debug("Saving {} {}", super.getId(), Boolean.valueOf(z2));
            }
            FileOutputStream fileOutputStream = null;
            try {
                file = new File(this._hashSessionManager._storeDir, super.getId());
                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        willPassivate();
                        save(fileOutputStream2);
                        IO.close(fileOutputStream2);
                        if (z2) {
                            didActivate();
                        } else {
                            clearAttributes();
                        }
                    } catch (Exception e2) {
                        e = e2;
                        fileOutputStream = fileOutputStream2;
                        saveFailed();
                        if (fileOutputStream != null) {
                            IO.close(fileOutputStream);
                        }
                        if (file != null) {
                            file.delete();
                        }
                        throw e;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            } catch (Exception e4) {
                e = e4;
                file = null;
            }
        }
    }

    public synchronized void saveFailed() {
        this._saveFailed = true;
    }

    @Override // org.eclipse.jetty.server.session.AbstractSession
    public void setMaxInactiveInterval(int i2) {
        super.setMaxInactiveInterval(i2);
        if (getMaxInactiveInterval() > 0) {
            long maxInactiveInterval = (getMaxInactiveInterval() * 1000) / 10;
            HashSessionManager hashSessionManager = this._hashSessionManager;
            if (maxInactiveInterval < hashSessionManager._scavengePeriodMs) {
                hashSessionManager.setScavengePeriod((i2 + 9) / 10);
            }
        }
    }

    public HashedSession(HashSessionManager hashSessionManager, long j2, long j3, String str) {
        super(hashSessionManager, j2, j3, str);
        this._idled = false;
        this._saveFailed = false;
        this._hashSessionManager = hashSessionManager;
    }

    public synchronized void save(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(getClusterId());
        dataOutputStream.writeUTF(getNodeId());
        dataOutputStream.writeLong(getCreationTime());
        dataOutputStream.writeLong(getAccessed());
        dataOutputStream.writeInt(getRequests());
        dataOutputStream.writeInt(getAttributes());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataOutputStream);
        Enumeration<String> attributeNames = getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String strNextElement = attributeNames.nextElement();
            objectOutputStream.writeUTF(strNextElement);
            objectOutputStream.writeObject(doGet(strNextElement));
        }
        objectOutputStream.close();
    }
}
