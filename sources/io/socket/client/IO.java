package io.socket.client;

import io.socket.client.Manager;
import io.socket.engineio.client.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.Call;
import okhttp3.WebSocket;

/* loaded from: classes8.dex */
public class IO {
    private static final Logger logger = Logger.getLogger(IO.class.getName());
    private static final ConcurrentHashMap<String, Manager> managers = new ConcurrentHashMap<>();
    public static int protocol = 4;

    public static class Options extends Manager.Options {
        public boolean forceNew;
        public boolean multiplex = true;
    }

    private IO() {
    }

    public static void setDefaultOkHttpCallFactory(Call.Factory factory) {
        Manager.defaultCallFactory = factory;
    }

    public static void setDefaultOkHttpWebSocketFactory(WebSocket.Factory factory) {
        Manager.defaultWebSocketFactory = factory;
    }

    public static Socket socket(String str) throws URISyntaxException {
        return socket(str, (Options) null);
    }

    public static Socket socket(String str, Options options) throws URISyntaxException {
        return socket(new URI(str), options);
    }

    public static Socket socket(URI uri) {
        return socket(uri, (Options) null);
    }

    public static Socket socket(URI uri, Options options) throws URISyntaxException {
        Manager manager;
        String str;
        if (options == null) {
            options = new Options();
        }
        URL url = Url.parse(uri);
        try {
            URI uri2 = url.toURI();
            String strExtractId = Url.extractId(url);
            String path = url.getPath();
            ConcurrentHashMap<String, Manager> concurrentHashMap = managers;
            if (options.forceNew || !options.multiplex || (concurrentHashMap.containsKey(strExtractId) && concurrentHashMap.get(strExtractId).nsps.containsKey(path))) {
                Logger logger2 = logger;
                if (logger2.isLoggable(Level.FINE)) {
                    logger2.fine(String.format("ignoring socket cache for %s", uri2));
                }
                manager = new Manager(uri2, options);
            } else {
                if (!concurrentHashMap.containsKey(strExtractId)) {
                    Logger logger3 = logger;
                    if (logger3.isLoggable(Level.FINE)) {
                        logger3.fine(String.format("new io instance for %s", uri2));
                    }
                    concurrentHashMap.putIfAbsent(strExtractId, new Manager(uri2, options));
                }
                manager = concurrentHashMap.get(strExtractId);
            }
            String query = url.getQuery();
            if (query != null && ((str = ((Socket.Options) options).query) == null || str.isEmpty())) {
                ((Socket.Options) options).query = query;
            }
            return manager.socket(url.getPath(), options);
        } catch (URISyntaxException e2) {
            throw new RuntimeException(e2);
        }
    }
}
