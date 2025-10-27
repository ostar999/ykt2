package org.eclipse.jetty.io.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jetty.io.Buffer;
import org.eclipse.jetty.io.NetworkTrafficListener;
import org.eclipse.jetty.io.nio.SelectorManager;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* loaded from: classes9.dex */
public class NetworkTrafficSelectChannelEndPoint extends SelectChannelEndPoint {
    private static final Logger LOG = Log.getLogger((Class<?>) NetworkTrafficSelectChannelEndPoint.class);
    private final List<NetworkTrafficListener> listeners;

    public NetworkTrafficSelectChannelEndPoint(SocketChannel socketChannel, SelectorManager.SelectSet selectSet, SelectionKey selectionKey, int i2, List<NetworkTrafficListener> list) throws IOException {
        super(socketChannel, selectSet, selectionKey, i2);
        this.listeners = list;
    }

    @Override // org.eclipse.jetty.io.nio.SelectChannelEndPoint, org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
    public int fill(Buffer buffer) throws Throwable {
        int iFill = super.fill(buffer);
        notifyIncoming(buffer, iFill);
        return iFill;
    }

    @Override // org.eclipse.jetty.io.nio.SelectChannelEndPoint, org.eclipse.jetty.io.nio.ChannelEndPoint, org.eclipse.jetty.io.EndPoint
    public int flush(Buffer buffer) throws IOException {
        int index = buffer.getIndex();
        int iFlush = super.flush(buffer);
        notifyOutgoing(buffer, index, iFlush);
        return iFlush;
    }

    @Override // org.eclipse.jetty.io.nio.ChannelEndPoint
    public int gatheringFlush(Buffer buffer, ByteBuffer byteBuffer, Buffer buffer2, ByteBuffer byteBuffer2) throws IOException {
        int index = buffer.getIndex();
        int length = buffer.length();
        int index2 = buffer2.getIndex();
        int iGatheringFlush = super.gatheringFlush(buffer, byteBuffer, buffer2, byteBuffer2);
        notifyOutgoing(buffer, index, iGatheringFlush > length ? length : iGatheringFlush);
        notifyOutgoing(buffer2, index2, iGatheringFlush > length ? iGatheringFlush - length : 0);
        return iGatheringFlush;
    }

    public void notifyClosed() {
        List<NetworkTrafficListener> list = this.listeners;
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator<NetworkTrafficListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().closed(this._socket);
            } catch (Exception e2) {
                LOG.warn(e2);
            }
        }
    }

    public void notifyIncoming(Buffer buffer, int i2) {
        List<NetworkTrafficListener> list = this.listeners;
        if (list == null || list.isEmpty() || i2 <= 0) {
            return;
        }
        for (NetworkTrafficListener networkTrafficListener : this.listeners) {
            try {
                networkTrafficListener.incoming(this._socket, buffer.asReadOnlyBuffer());
            } catch (Exception e2) {
                LOG.warn(e2);
            }
        }
    }

    public void notifyOpened() {
        List<NetworkTrafficListener> list = this.listeners;
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator<NetworkTrafficListener> it = this.listeners.iterator();
        while (it.hasNext()) {
            try {
                it.next().opened(this._socket);
            } catch (Exception e2) {
                LOG.warn(e2);
            }
        }
    }

    public void notifyOutgoing(Buffer buffer, int i2, int i3) {
        List<NetworkTrafficListener> list = this.listeners;
        if (list == null || list.isEmpty() || i3 <= 0) {
            return;
        }
        for (NetworkTrafficListener networkTrafficListener : this.listeners) {
            try {
                Buffer bufferAsReadOnlyBuffer = buffer.asReadOnlyBuffer();
                bufferAsReadOnlyBuffer.setGetIndex(i2);
                bufferAsReadOnlyBuffer.setPutIndex(i2 + i3);
                networkTrafficListener.outgoing(this._socket, bufferAsReadOnlyBuffer);
            } catch (Exception e2) {
                LOG.warn(e2);
            }
        }
    }
}
