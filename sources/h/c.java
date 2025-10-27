package h;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes8.dex */
public class c extends SSLSocketFactory {

    /* renamed from: a, reason: collision with root package name */
    public final SSLSocketFactory f27025a;

    public class b extends SSLSocket {

        /* renamed from: a, reason: collision with root package name */
        public final SSLSocket f27026a;

        public b(SSLSocket sSLSocket) {
            this.f27026a = sSLSocket;
        }

        @Override // javax.net.ssl.SSLSocket
        public void addHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
            this.f27026a.addHandshakeCompletedListener(handshakeCompletedListener);
        }

        @Override // java.net.Socket
        public void bind(SocketAddress socketAddress) throws IOException {
            this.f27026a.bind(socketAddress);
        }

        @Override // java.net.Socket, java.io.Closeable, java.lang.AutoCloseable
        public synchronized void close() throws IOException {
            this.f27026a.close();
        }

        @Override // java.net.Socket
        public void connect(SocketAddress socketAddress) throws IOException {
            this.f27026a.connect(socketAddress);
        }

        public boolean equals(Object obj) {
            return this.f27026a.equals(obj);
        }

        @Override // java.net.Socket
        public SocketChannel getChannel() {
            return this.f27026a.getChannel();
        }

        @Override // javax.net.ssl.SSLSocket
        public boolean getEnableSessionCreation() {
            return this.f27026a.getEnableSessionCreation();
        }

        @Override // javax.net.ssl.SSLSocket
        public String[] getEnabledCipherSuites() {
            return this.f27026a.getEnabledCipherSuites();
        }

        @Override // javax.net.ssl.SSLSocket
        public String[] getEnabledProtocols() {
            return this.f27026a.getEnabledProtocols();
        }

        @Override // java.net.Socket
        public InetAddress getInetAddress() {
            return this.f27026a.getInetAddress();
        }

        @Override // java.net.Socket
        public InputStream getInputStream() throws IOException {
            return this.f27026a.getInputStream();
        }

        @Override // java.net.Socket
        public boolean getKeepAlive() throws SocketException {
            return this.f27026a.getKeepAlive();
        }

        @Override // java.net.Socket
        public InetAddress getLocalAddress() {
            return this.f27026a.getLocalAddress();
        }

        @Override // java.net.Socket
        public int getLocalPort() {
            return this.f27026a.getLocalPort();
        }

        @Override // java.net.Socket
        public SocketAddress getLocalSocketAddress() {
            return this.f27026a.getLocalSocketAddress();
        }

        @Override // javax.net.ssl.SSLSocket
        public boolean getNeedClientAuth() {
            return this.f27026a.getNeedClientAuth();
        }

        @Override // java.net.Socket
        public boolean getOOBInline() throws SocketException {
            return this.f27026a.getOOBInline();
        }

        @Override // java.net.Socket
        public OutputStream getOutputStream() throws IOException {
            return this.f27026a.getOutputStream();
        }

        @Override // java.net.Socket
        public int getPort() {
            return this.f27026a.getPort();
        }

        @Override // java.net.Socket
        public synchronized int getReceiveBufferSize() throws SocketException {
            return this.f27026a.getReceiveBufferSize();
        }

        @Override // java.net.Socket
        public SocketAddress getRemoteSocketAddress() {
            return this.f27026a.getRemoteSocketAddress();
        }

        @Override // java.net.Socket
        public boolean getReuseAddress() throws SocketException {
            return this.f27026a.getReuseAddress();
        }

        @Override // java.net.Socket
        public synchronized int getSendBufferSize() throws SocketException {
            return this.f27026a.getSendBufferSize();
        }

        @Override // javax.net.ssl.SSLSocket
        public SSLSession getSession() {
            return this.f27026a.getSession();
        }

        @Override // java.net.Socket
        public int getSoLinger() throws SocketException {
            return this.f27026a.getSoLinger();
        }

        @Override // java.net.Socket
        public synchronized int getSoTimeout() throws SocketException {
            return this.f27026a.getSoTimeout();
        }

        @Override // javax.net.ssl.SSLSocket
        public String[] getSupportedCipherSuites() {
            return this.f27026a.getSupportedCipherSuites();
        }

        @Override // javax.net.ssl.SSLSocket
        public String[] getSupportedProtocols() {
            return this.f27026a.getSupportedProtocols();
        }

        @Override // java.net.Socket
        public boolean getTcpNoDelay() throws SocketException {
            return this.f27026a.getTcpNoDelay();
        }

        @Override // java.net.Socket
        public int getTrafficClass() throws SocketException {
            return this.f27026a.getTrafficClass();
        }

        @Override // javax.net.ssl.SSLSocket
        public boolean getUseClientMode() {
            return this.f27026a.getUseClientMode();
        }

        @Override // javax.net.ssl.SSLSocket
        public boolean getWantClientAuth() {
            return this.f27026a.getWantClientAuth();
        }

        @Override // java.net.Socket
        public boolean isBound() {
            return this.f27026a.isBound();
        }

        @Override // java.net.Socket
        public boolean isClosed() {
            return this.f27026a.isClosed();
        }

        @Override // java.net.Socket
        public boolean isConnected() {
            return this.f27026a.isConnected();
        }

        @Override // java.net.Socket
        public boolean isInputShutdown() {
            return this.f27026a.isInputShutdown();
        }

        @Override // java.net.Socket
        public boolean isOutputShutdown() {
            return this.f27026a.isOutputShutdown();
        }

        @Override // javax.net.ssl.SSLSocket
        public void removeHandshakeCompletedListener(HandshakeCompletedListener handshakeCompletedListener) {
            this.f27026a.removeHandshakeCompletedListener(handshakeCompletedListener);
        }

        @Override // java.net.Socket
        public void sendUrgentData(int i2) throws IOException {
            this.f27026a.sendUrgentData(i2);
        }

        @Override // javax.net.ssl.SSLSocket
        public void setEnableSessionCreation(boolean z2) {
            this.f27026a.setEnableSessionCreation(z2);
        }

        @Override // javax.net.ssl.SSLSocket
        public void setEnabledCipherSuites(String[] strArr) {
            this.f27026a.setEnabledCipherSuites(strArr);
        }

        @Override // javax.net.ssl.SSLSocket
        public void setEnabledProtocols(String[] strArr) {
            this.f27026a.setEnabledProtocols(strArr);
        }

        @Override // java.net.Socket
        public void setKeepAlive(boolean z2) throws SocketException {
            this.f27026a.setKeepAlive(z2);
        }

        @Override // javax.net.ssl.SSLSocket
        public void setNeedClientAuth(boolean z2) {
            this.f27026a.setNeedClientAuth(z2);
        }

        @Override // java.net.Socket
        public void setOOBInline(boolean z2) throws SocketException {
            this.f27026a.setOOBInline(z2);
        }

        @Override // java.net.Socket
        public void setPerformancePreferences(int i2, int i3, int i4) {
            this.f27026a.setPerformancePreferences(i2, i3, i4);
        }

        @Override // java.net.Socket
        public synchronized void setReceiveBufferSize(int i2) throws SocketException {
            this.f27026a.setReceiveBufferSize(i2);
        }

        @Override // java.net.Socket
        public void setReuseAddress(boolean z2) throws SocketException {
            this.f27026a.setReuseAddress(z2);
        }

        @Override // java.net.Socket
        public synchronized void setSendBufferSize(int i2) throws SocketException {
            this.f27026a.setSendBufferSize(i2);
        }

        @Override // java.net.Socket
        public void setSoLinger(boolean z2, int i2) throws SocketException {
            this.f27026a.setSoLinger(z2, i2);
        }

        @Override // java.net.Socket
        public synchronized void setSoTimeout(int i2) throws SocketException {
            this.f27026a.setSoTimeout(i2);
        }

        @Override // java.net.Socket
        public void setTcpNoDelay(boolean z2) throws SocketException {
            this.f27026a.setTcpNoDelay(z2);
        }

        @Override // java.net.Socket
        public void setTrafficClass(int i2) throws SocketException {
            this.f27026a.setTrafficClass(i2);
        }

        @Override // javax.net.ssl.SSLSocket
        public void setUseClientMode(boolean z2) {
            this.f27026a.setUseClientMode(z2);
        }

        @Override // javax.net.ssl.SSLSocket
        public void setWantClientAuth(boolean z2) {
            this.f27026a.setWantClientAuth(z2);
        }

        @Override // java.net.Socket
        public void shutdownInput() throws IOException {
            this.f27026a.shutdownInput();
        }

        @Override // java.net.Socket
        public void shutdownOutput() throws IOException {
            this.f27026a.shutdownOutput();
        }

        @Override // javax.net.ssl.SSLSocket
        public void startHandshake() throws IOException {
            this.f27026a.startHandshake();
        }

        @Override // javax.net.ssl.SSLSocket, java.net.Socket
        public String toString() {
            return this.f27026a.toString();
        }

        @Override // java.net.Socket
        public void connect(SocketAddress socketAddress, int i2) throws IOException {
            this.f27026a.connect(socketAddress, i2);
        }
    }

    /* renamed from: h.c$c, reason: collision with other inner class name */
    public class C0452c extends b {
        @Override // h.c.b, javax.net.ssl.SSLSocket
        public void setEnabledProtocols(String[] strArr) {
            if (strArr != null && strArr.length == 1 && "SSLv3".equals(strArr[0])) {
                ArrayList arrayList = new ArrayList(Arrays.asList(this.f27026a.getEnabledProtocols()));
                if (arrayList.size() > 1) {
                    arrayList.remove("SSLv3");
                    System.out.println("Removed SSLv3 from enabled protocols");
                } else {
                    System.out.println("SSL stuck with protocol available for " + String.valueOf(arrayList));
                }
                strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
            super.setEnabledProtocols(strArr);
        }

        public C0452c(SSLSocket sSLSocket) {
            super(sSLSocket);
        }
    }

    public c() {
        this.f27025a = HttpsURLConnection.getDefaultSSLSocketFactory();
    }

    public final Socket a(Socket socket) {
        return socket instanceof SSLSocket ? new C0452c((SSLSocket) socket) : socket;
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public Socket createSocket(Socket socket, String str, int i2, boolean z2) throws IOException {
        return a(this.f27025a.createSocket(socket, str, i2, z2));
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getDefaultCipherSuites() {
        return this.f27025a.getDefaultCipherSuites();
    }

    @Override // javax.net.ssl.SSLSocketFactory
    public String[] getSupportedCipherSuites() {
        return this.f27025a.getSupportedCipherSuites();
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2) throws IOException {
        return a(this.f27025a.createSocket(str, i2));
    }

    public c(SSLSocketFactory sSLSocketFactory) {
        this.f27025a = sSLSocketFactory;
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(String str, int i2, InetAddress inetAddress, int i3) throws IOException {
        return a(this.f27025a.createSocket(str, i2, inetAddress, i3));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2) throws IOException {
        return a(this.f27025a.createSocket(inetAddress, i2));
    }

    @Override // javax.net.SocketFactory
    public Socket createSocket(InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) throws IOException {
        return a(this.f27025a.createSocket(inetAddress, i2, inetAddress2, i3));
    }
}
