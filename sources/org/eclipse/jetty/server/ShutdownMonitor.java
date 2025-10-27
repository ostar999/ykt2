package org.eclipse.jetty.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import org.eclipse.jetty.util.thread.ShutdownThread;

/* loaded from: classes9.dex */
public class ShutdownMonitor {
    private boolean DEBUG;
    private boolean exitVm;
    private String key;
    private int port;
    private ServerSocket serverSocket;
    private ShutdownMonitorThread thread;

    public static class Holder {
        static ShutdownMonitor instance = new ShutdownMonitor();
    }

    public class ShutdownMonitorThread extends Thread {
        public ShutdownMonitorThread() {
            setDaemon(true);
            setName("ShutdownMonitor");
        }

        private void startListenSocket() {
            if (ShutdownMonitor.this.port < 0) {
                if (ShutdownMonitor.this.DEBUG) {
                    System.err.println("ShutdownMonitor not in use (port < 0): " + ShutdownMonitor.this.port);
                    return;
                }
                return;
            }
            try {
                try {
                    ShutdownMonitor.this.serverSocket = new ServerSocket(ShutdownMonitor.this.port, 1, InetAddress.getByName("127.0.0.1"));
                    if (ShutdownMonitor.this.port == 0) {
                        ShutdownMonitor shutdownMonitor = ShutdownMonitor.this;
                        shutdownMonitor.port = shutdownMonitor.serverSocket.getLocalPort();
                        System.out.printf("STOP.PORT=%d%n", Integer.valueOf(ShutdownMonitor.this.port));
                    }
                    if (ShutdownMonitor.this.key == null) {
                        ShutdownMonitor.this.key = Long.toString((long) ((Math.random() * 9.223372036854776E18d) + hashCode() + System.currentTimeMillis()), 36);
                        System.out.printf("STOP.KEY=%s%n", ShutdownMonitor.this.key);
                    }
                    ShutdownMonitor shutdownMonitor2 = ShutdownMonitor.this;
                    shutdownMonitor2.debug("STOP.PORT=%d", Integer.valueOf(shutdownMonitor2.port));
                    ShutdownMonitor shutdownMonitor3 = ShutdownMonitor.this;
                    shutdownMonitor3.debug("STOP.KEY=%s", shutdownMonitor3.key);
                    ShutdownMonitor shutdownMonitor4 = ShutdownMonitor.this;
                    shutdownMonitor4.debug("%s", shutdownMonitor4.serverSocket);
                } catch (Exception e2) {
                    ShutdownMonitor.this.debug(e2);
                    System.err.println("Error binding monitor port " + ShutdownMonitor.this.port + ": " + e2.toString());
                    ShutdownMonitor.this.serverSocket = null;
                    ShutdownMonitor shutdownMonitor5 = ShutdownMonitor.this;
                    shutdownMonitor5.debug("STOP.PORT=%d", Integer.valueOf(shutdownMonitor5.port));
                    ShutdownMonitor shutdownMonitor6 = ShutdownMonitor.this;
                    shutdownMonitor6.debug("STOP.KEY=%s", shutdownMonitor6.key);
                    ShutdownMonitor shutdownMonitor7 = ShutdownMonitor.this;
                    shutdownMonitor7.debug("%s", shutdownMonitor7.serverSocket);
                }
            } catch (Throwable th) {
                ShutdownMonitor shutdownMonitor8 = ShutdownMonitor.this;
                shutdownMonitor8.debug("STOP.PORT=%d", Integer.valueOf(shutdownMonitor8.port));
                ShutdownMonitor shutdownMonitor9 = ShutdownMonitor.this;
                shutdownMonitor9.debug("STOP.KEY=%s", shutdownMonitor9.key);
                ShutdownMonitor shutdownMonitor10 = ShutdownMonitor.this;
                shutdownMonitor10.debug("%s", shutdownMonitor10.serverSocket);
                throw th;
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws Throwable {
            Socket socketAccept;
            Throwable th;
            Exception e2;
            LineNumberReader lineNumberReader;
            if (ShutdownMonitor.this.serverSocket == null) {
                return;
            }
            while (ShutdownMonitor.this.serverSocket != null) {
                Socket socket = null;
                try {
                    socketAccept = ShutdownMonitor.this.serverSocket.accept();
                } catch (Exception e3) {
                    socketAccept = null;
                    e2 = e3;
                } catch (Throwable th2) {
                    socketAccept = null;
                    th = th2;
                }
                try {
                    try {
                        lineNumberReader = new LineNumberReader(new InputStreamReader(socketAccept.getInputStream()));
                    } catch (Exception e4) {
                        e2 = e4;
                        ShutdownMonitor.this.debug(e2);
                        System.err.println(e2.toString());
                        ShutdownMonitor.this.close(socketAccept);
                    }
                    if (ShutdownMonitor.this.key.equals(lineNumberReader.readLine())) {
                        OutputStream outputStream = socketAccept.getOutputStream();
                        String line = lineNumberReader.readLine();
                        ShutdownMonitor.this.debug("command=%s", line);
                        if ("stop".equals(line)) {
                            ShutdownMonitor.this.debug("Issuing graceful shutdown..", new Object[0]);
                            ShutdownThread.getInstance().run();
                            ShutdownMonitor.this.debug("Informing client that we are stopped.", new Object[0]);
                            outputStream.write("Stopped\r\n".getBytes("UTF-8"));
                            outputStream.flush();
                            ShutdownMonitor.this.debug("Shutting down monitor", new Object[0]);
                            ShutdownMonitor.this.close(socketAccept);
                            ShutdownMonitor shutdownMonitor = ShutdownMonitor.this;
                            shutdownMonitor.close(shutdownMonitor.serverSocket);
                            ShutdownMonitor.this.serverSocket = null;
                            if (ShutdownMonitor.this.exitVm) {
                                ShutdownMonitor.this.debug("Killing JVM", new Object[0]);
                                System.exit(0);
                            }
                        } else {
                            if ("status".equals(line)) {
                                outputStream.write("OK\r\n".getBytes("UTF-8"));
                                outputStream.flush();
                            }
                            socket = socketAccept;
                        }
                        ShutdownMonitor.this.close(socket);
                    } else {
                        System.err.println("Ignoring command with incorrect key");
                        ShutdownMonitor.this.close(socketAccept);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    ShutdownMonitor.this.close(socketAccept);
                    throw th;
                }
            }
        }

        @Override // java.lang.Thread
        public void start() {
            if (isAlive()) {
                System.err.printf("ShutdownMonitorThread already started", new Object[0]);
                return;
            }
            startListenSocket();
            if (ShutdownMonitor.this.serverSocket == null) {
                return;
            }
            if (ShutdownMonitor.this.DEBUG) {
                System.err.println("Starting ShutdownMonitorThread");
            }
            super.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void close(ServerSocket serverSocket) throws IOException {
        if (serverSocket == null) {
            return;
        }
        try {
            serverSocket.close();
        } catch (IOException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void debug(String str, Object... objArr) {
        if (this.DEBUG) {
            System.err.printf("[ShutdownMonitor] " + str + "%n", objArr);
        }
    }

    public static ShutdownMonitor getInstance() {
        return Holder.instance;
    }

    public String getKey() {
        return this.key;
    }

    public int getPort() {
        return this.port;
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public boolean isAlive() {
        boolean z2;
        synchronized (this) {
            ShutdownMonitorThread shutdownMonitorThread = this.thread;
            z2 = shutdownMonitorThread != null && shutdownMonitorThread.isAlive();
        }
        return z2;
    }

    public boolean isExitVm() {
        return this.exitVm;
    }

    public void setDebug(boolean z2) {
        this.DEBUG = z2;
    }

    public void setExitVm(boolean z2) {
        synchronized (this) {
            ShutdownMonitorThread shutdownMonitorThread = this.thread;
            if (shutdownMonitorThread != null && shutdownMonitorThread.isAlive()) {
                throw new IllegalStateException("ShutdownMonitorThread already started");
            }
            this.exitVm = z2;
        }
    }

    public void setKey(String str) {
        synchronized (this) {
            ShutdownMonitorThread shutdownMonitorThread = this.thread;
            if (shutdownMonitorThread != null && shutdownMonitorThread.isAlive()) {
                throw new IllegalStateException("ShutdownMonitorThread already started");
            }
            this.key = str;
        }
    }

    public void setPort(int i2) {
        synchronized (this) {
            ShutdownMonitorThread shutdownMonitorThread = this.thread;
            if (shutdownMonitorThread != null && shutdownMonitorThread.isAlive()) {
                throw new IllegalStateException("ShutdownMonitorThread already started");
            }
            this.port = i2;
        }
    }

    public void start() throws Exception {
        synchronized (this) {
            ShutdownMonitorThread shutdownMonitorThread = this.thread;
            if (shutdownMonitorThread != null && shutdownMonitorThread.isAlive()) {
                System.err.printf("ShutdownMonitorThread already started", new Object[0]);
                return;
            }
            ShutdownMonitorThread shutdownMonitorThread2 = new ShutdownMonitorThread();
            this.thread = shutdownMonitorThread2;
            shutdownMonitorThread2.start();
        }
    }

    public String toString() {
        return String.format("%s[port=%d]", getClass().getName(), Integer.valueOf(this.port));
    }

    private ShutdownMonitor() {
        Properties properties = System.getProperties();
        this.DEBUG = properties.containsKey("DEBUG");
        this.port = Integer.parseInt(properties.getProperty("STOP.PORT", "-1"));
        this.key = properties.getProperty("STOP.KEY", null);
        this.exitVm = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void close(Socket socket) throws IOException {
        if (socket == null) {
            return;
        }
        try {
            socket.close();
        } catch (IOException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void debug(Throwable th) {
        if (this.DEBUG) {
            th.printStackTrace(System.err);
        }
    }
}
