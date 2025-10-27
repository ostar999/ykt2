package com.unity3d.splash.services.core.configuration;

import android.annotation.TargetApi;
import android.os.ConditionVariable;
import com.heytap.mcssdk.constant.a;
import com.unity3d.splash.UnityAds;
import com.unity3d.splash.services.core.api.Lifecycle;
import com.unity3d.splash.services.core.connectivity.ConnectivityMonitor;
import com.unity3d.splash.services.core.connectivity.IConnectivityListener;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import com.unity3d.splash.services.core.request.WebRequest;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes6.dex */
public class InitializeThread extends Thread {
    private static InitializeThread _thread;
    private InitializeState _state;
    private boolean _stopThread = false;

    public static abstract class InitializeState {
        private InitializeState() {
        }

        public abstract InitializeState execute();
    }

    public static class InitializeStateComplete extends InitializeState {
        private Configuration _configuration;

        public InitializeStateComplete(Configuration configuration) {
            super();
            this._configuration = configuration;
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() {
            for (String str : this._configuration.getModuleConfigurationList()) {
                IModuleConfiguration moduleConfiguration = this._configuration.getModuleConfiguration(str);
                if (moduleConfiguration != null) {
                    moduleConfiguration.initCompleteState(this._configuration);
                }
            }
            return null;
        }
    }

    public static class InitializeStateConfig extends InitializeState {
        private Configuration _configuration;
        private int _maxRetries;
        private int _retries;
        private int _retryDelay;

        public InitializeStateConfig(Configuration configuration) {
            super();
            this._retries = 0;
            this._maxRetries = 6;
            this._retryDelay = 5;
            this._configuration = configuration;
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            DeviceLog.info("Unity Ads init: load configuration from " + SdkProperties.getConfigUrl());
            try {
                this._configuration.makeRequest();
                return new InitializeStateLoadCache(this._configuration);
            } catch (Exception e2) {
                int i2 = this._retries;
                if (i2 >= this._maxRetries) {
                    return new InitializeStateNetworkError(e2, this, this._configuration);
                }
                int i3 = this._retryDelay * 2;
                this._retryDelay = i3;
                this._retries = i2 + 1;
                return new InitializeStateRetry(this, i3);
            }
        }
    }

    public static class InitializeStateCreate extends InitializeState {
        private Configuration _configuration;
        private String _webViewData;

        public InitializeStateCreate(Configuration configuration, String str) {
            super();
            this._configuration = configuration;
            this._webViewData = str;
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            DeviceLog.debug("Unity Ads init: creating webapp");
            Configuration configuration = this._configuration;
            configuration.setWebViewData(this._webViewData);
            try {
                if (WebViewApp.create(configuration)) {
                    return new InitializeStateComplete(this._configuration);
                }
                DeviceLog.error("Unity Ads WebApp creation failed!");
                return new InitializeStateError("create webapp", new Exception("Creation of WebApp failed!"), this._configuration);
            } catch (IllegalThreadStateException e2) {
                DeviceLog.exception("Illegal Thread", e2);
                return new InitializeStateError("create webapp", e2, this._configuration);
            }
        }

        public Configuration getConfiguration() {
            return this._configuration;
        }

        public String getWebData() {
            return this._webViewData;
        }
    }

    public static class InitializeStateError extends InitializeState {
        protected Configuration _configuration;
        Exception _exception;
        String _state;

        public InitializeStateError(String str, Exception exc, Configuration configuration) {
            super();
            this._state = str;
            this._exception = exc;
            this._configuration = configuration;
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            DeviceLog.error("Unity Ads init: halting init in " + this._state + ": " + this._exception.getMessage());
            for (String str : this._configuration.getModuleConfigurationList()) {
                IModuleConfiguration moduleConfiguration = this._configuration.getModuleConfiguration(str);
                if (moduleConfiguration != null) {
                    moduleConfiguration.initErrorState(this._configuration, this._state, this._exception.getMessage());
                }
            }
            return null;
        }
    }

    public static class InitializeStateForceReset extends InitializeStateReset {
        public InitializeStateForceReset() {
            super(new Configuration());
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeStateReset, com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            super.execute();
            return null;
        }
    }

    public static class InitializeStateInitModules extends InitializeState {
        private Configuration _configuration;

        public InitializeStateInitModules(Configuration configuration) {
            super();
            this._configuration = configuration;
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() {
            for (String str : this._configuration.getModuleConfigurationList()) {
                IModuleConfiguration moduleConfiguration = this._configuration.getModuleConfiguration(str);
                if (moduleConfiguration != null && !moduleConfiguration.initModuleState(this._configuration)) {
                    return null;
                }
            }
            return new InitializeStateConfig(this._configuration);
        }

        public Configuration getConfiguration() {
            return this._configuration;
        }
    }

    public static class InitializeStateLoadCache extends InitializeState {
        private Configuration _configuration;

        public InitializeStateLoadCache(Configuration configuration) {
            super();
            this._configuration = configuration;
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() throws IllegalAccessException, NoSuchMethodException, NoSuchAlgorithmException, SecurityException, IllegalArgumentException, InvocationTargetException {
            DeviceLog.debug("Unity Ads init: check if webapp can be loaded from local cache");
            try {
                byte[] fileBytes = Utilities.readFileBytes(new File(SdkProperties.getLocalWebViewFile()));
                String strSha256 = Utilities.Sha256(fileBytes);
                if (strSha256 == null || !strSha256.equals(this._configuration.getWebViewHash())) {
                    UnityAds.setSkipLaunchScreenAds(true);
                    return new InitializeStateLoadWeb(this._configuration);
                }
                try {
                    String str = new String(fileBytes, "UTF-8");
                    DeviceLog.info("Unity Ads init: webapp loaded from local cache");
                    return new InitializeStateCreate(this._configuration, str);
                } catch (UnsupportedEncodingException e2) {
                    return new InitializeStateError("load cache", e2, this._configuration);
                }
            } catch (IOException e3) {
                DeviceLog.debug("Unity Ads init: webapp not found in local cache: " + e3.getMessage());
                return new InitializeStateLoadWeb(this._configuration);
            }
        }

        public Configuration getConfiguration() {
            return this._configuration;
        }
    }

    public static class InitializeStateLoadWeb extends InitializeState {
        private Configuration _configuration;
        private int _maxRetries;
        private int _retries;
        private int _retryDelay;

        public InitializeStateLoadWeb(Configuration configuration) {
            super();
            this._retries = 0;
            this._maxRetries = 6;
            this._retryDelay = 5;
            this._configuration = configuration;
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() throws Throwable {
            DeviceLog.info("Unity Ads init: loading webapp from " + this._configuration.getWebViewUrl());
            try {
                try {
                    String strMakeRequest = new WebRequest(this._configuration.getWebViewUrl(), "GET", null).makeRequest();
                    String webViewHash = this._configuration.getWebViewHash();
                    if (webViewHash != null && !Utilities.Sha256(strMakeRequest).equals(webViewHash)) {
                        return new InitializeStateError("load web", new Exception("Invalid webViewHash"), this._configuration);
                    }
                    if (webViewHash != null) {
                        Utilities.writeFile(new File(SdkProperties.getLocalWebViewFile()), strMakeRequest);
                    }
                    return new InitializeStateCreate(this._configuration, strMakeRequest);
                } catch (Exception e2) {
                    if (this._retries >= this._maxRetries) {
                        return new InitializeStateNetworkError(e2, this, this._configuration);
                    }
                    int i2 = this._retryDelay * 2;
                    this._retryDelay = i2;
                    this._retries++;
                    return new InitializeStateRetry(this, i2);
                }
            } catch (MalformedURLException e3) {
                DeviceLog.exception("Malformed URL", e3);
                return new InitializeStateError("make webrequest", e3, this._configuration);
            }
        }

        public Configuration getConfiguration() {
            return this._configuration;
        }
    }

    public static class InitializeStateNetworkError extends InitializeStateError implements IConnectivityListener {
        protected static final int CONNECTED_EVENT_THRESHOLD_MS = 10000;
        protected static final int MAX_CONNECTED_EVENTS = 500;
        private static long _lastConnectedEventTimeMs;
        private static int _receivedConnectedEvents;
        private ConditionVariable _conditionVariable;
        private InitializeState _erroredState;

        public InitializeStateNetworkError(Exception exc, InitializeState initializeState, Configuration configuration) {
            super("network error", exc, configuration);
            this._erroredState = initializeState;
        }

        private boolean shouldHandleConnectedEvent() {
            return System.currentTimeMillis() - _lastConnectedEventTimeMs >= a.f7153q && _receivedConnectedEvents <= 500;
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeStateError, com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            DeviceLog.error("Unity Ads init: network error, waiting for connection events");
            this._conditionVariable = new ConditionVariable();
            ConnectivityMonitor.addListener(this);
            boolean zBlock = this._conditionVariable.block(600000L);
            ConnectivityMonitor.removeListener(this);
            return zBlock ? this._erroredState : new InitializeStateError("network error", new Exception("No connected events within the timeout!"), this._configuration);
        }

        @Override // com.unity3d.splash.services.core.connectivity.IConnectivityListener
        public void onConnected() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            _receivedConnectedEvents++;
            DeviceLog.debug("Unity Ads init got connected event");
            if (shouldHandleConnectedEvent()) {
                this._conditionVariable.open();
            }
            if (_receivedConnectedEvents > 500) {
                ConnectivityMonitor.removeListener(this);
            }
            _lastConnectedEventTimeMs = System.currentTimeMillis();
        }

        @Override // com.unity3d.splash.services.core.connectivity.IConnectivityListener
        public void onDisconnected() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            DeviceLog.debug("Unity Ads init got disconnected event");
        }
    }

    public static class InitializeStateReset extends InitializeState {
        private Configuration _configuration;

        public InitializeStateReset(Configuration configuration) {
            super();
            this._configuration = configuration;
        }

        @TargetApi(14)
        private void unregisterLifecycleCallbacks() {
            if (Lifecycle.getLifecycleListener() != null) {
                if (ClientProperties.getApplication() != null) {
                    ClientProperties.getApplication().unregisterActivityLifecycleCallbacks(Lifecycle.getLifecycleListener());
                }
                Lifecycle.setLifecycleListener(null);
            }
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            boolean zBlock;
            DeviceLog.debug("Unity Ads init: starting init");
            final ConditionVariable conditionVariable = new ConditionVariable();
            final WebViewApp currentApp = WebViewApp.getCurrentApp();
            if (currentApp != null) {
                currentApp.setWebAppLoaded(false);
                currentApp.setWebAppInitialized(false);
                if (currentApp.getWebView() != null) {
                    Utilities.runOnUiThread(new Runnable() { // from class: com.unity3d.splash.services.core.configuration.InitializeThread.InitializeStateReset.1
                        @Override // java.lang.Runnable
                        public void run() {
                            currentApp.getWebView().destroy();
                            currentApp.setWebView(null);
                            conditionVariable.open();
                        }
                    });
                    zBlock = conditionVariable.block(a.f7153q);
                } else {
                    zBlock = true;
                }
                if (!zBlock) {
                    return new InitializeStateError("reset webapp", new Exception("Reset failed on opening ConditionVariable"), this._configuration);
                }
            }
            unregisterLifecycleCallbacks();
            SdkProperties.setCacheDirectory(null);
            if (SdkProperties.getCacheDirectory() == null) {
                return new InitializeStateError("reset webapp", new Exception("Cache directory is NULL"), this._configuration);
            }
            SdkProperties.setInitialized(false);
            this._configuration.setConfigUrl(SdkProperties.getConfigUrl());
            for (String str : this._configuration.getModuleConfigurationList()) {
                IModuleConfiguration moduleConfiguration = this._configuration.getModuleConfiguration(str);
                if (moduleConfiguration != null) {
                    moduleConfiguration.resetState(this._configuration);
                }
            }
            return new InitializeStateInitModules(this._configuration);
        }
    }

    public static class InitializeStateRetry extends InitializeState {
        int _delay;
        InitializeState _state;

        public InitializeStateRetry(InitializeState initializeState, int i2) {
            super();
            this._state = initializeState;
            this._delay = i2;
        }

        @Override // com.unity3d.splash.services.core.configuration.InitializeThread.InitializeState
        public InitializeState execute() throws IllegalAccessException, InterruptedException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            DeviceLog.debug("Unity Ads init: retrying in " + this._delay + " seconds");
            try {
                Thread.sleep(this._delay * 1000);
            } catch (InterruptedException e2) {
                DeviceLog.exception("Init retry interrupted", e2);
            }
            return this._state;
        }
    }

    private InitializeThread(InitializeState initializeState) {
        this._state = initializeState;
    }

    public static synchronized void initialize(Configuration configuration) {
        if (_thread == null) {
            InitializeThread initializeThread = new InitializeThread(new InitializeStateReset(configuration));
            _thread = initializeThread;
            initializeThread.setName("UnityAdsInitializeThread");
            _thread.start();
        }
    }

    public static synchronized void reset() {
        if (_thread == null) {
            InitializeThread initializeThread = new InitializeThread(new InitializeStateForceReset());
            _thread = initializeThread;
            initializeThread.setName("UnityAdsResetThread");
            _thread.start();
        }
    }

    public void quit() {
        this._stopThread = true;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (true) {
            InitializeState initializeState = this._state;
            if (initializeState == null || (initializeState instanceof InitializeStateComplete) || this._stopThread) {
                break;
            } else {
                this._state = initializeState.execute();
            }
        }
        _thread = null;
    }
}
