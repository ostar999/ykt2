package org.eclipse.jetty.io;

/* loaded from: classes9.dex */
public interface ConnectedEndPoint extends EndPoint {
    Connection getConnection();

    void setConnection(Connection connection);
}
