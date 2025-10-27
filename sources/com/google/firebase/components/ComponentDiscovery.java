package com.google.firebase.components;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.VisibleForTesting;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class ComponentDiscovery<T> {
    private static final String COMPONENT_KEY_PREFIX = "com.google.firebase.components:";
    private static final String COMPONENT_SENTINEL_VALUE = "com.google.firebase.components.ComponentRegistrar";
    private static final String TAG = "ComponentDiscovery";
    private final T context;
    private final RegistrarNameRetriever<T> retriever;

    public static class MetadataRegistrarNameRetriever implements RegistrarNameRetriever<Context> {
        private final Class<? extends Service> discoveryService;

        private Bundle getMetadata(Context context) throws PackageManager.NameNotFoundException {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager == null) {
                    Log.w(ComponentDiscovery.TAG, "Context has no PackageManager.");
                    return null;
                }
                ServiceInfo serviceInfo = packageManager.getServiceInfo(new ComponentName(context, this.discoveryService), 128);
                if (serviceInfo != null) {
                    return serviceInfo.metaData;
                }
                Log.w(ComponentDiscovery.TAG, this.discoveryService + " has no service info.");
                return null;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w(ComponentDiscovery.TAG, "Application info not found.");
                return null;
            }
        }

        private MetadataRegistrarNameRetriever(Class<? extends Service> cls) {
            this.discoveryService = cls;
        }

        @Override // com.google.firebase.components.ComponentDiscovery.RegistrarNameRetriever
        public List<String> retrieve(Context context) throws PackageManager.NameNotFoundException {
            Bundle metadata = getMetadata(context);
            if (metadata == null) {
                Log.w(ComponentDiscovery.TAG, "Could not retrieve metadata, returning empty list of registrars.");
                return Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            for (String str : metadata.keySet()) {
                if (ComponentDiscovery.COMPONENT_SENTINEL_VALUE.equals(metadata.get(str)) && str.startsWith(ComponentDiscovery.COMPONENT_KEY_PREFIX)) {
                    arrayList.add(str.substring(31));
                }
            }
            return arrayList;
        }
    }

    @VisibleForTesting
    public interface RegistrarNameRetriever<T> {
        List<String> retrieve(T t2);
    }

    @VisibleForTesting
    public ComponentDiscovery(T t2, RegistrarNameRetriever<T> registrarNameRetriever) {
        this.context = t2;
        this.retriever = registrarNameRetriever;
    }

    public static ComponentDiscovery<Context> forContext(Context context, Class<? extends Service> cls) {
        return new ComponentDiscovery<>(context, new MetadataRegistrarNameRetriever(cls));
    }

    private static List<ComponentRegistrar> instantiate(List<String> list) throws ClassNotFoundException {
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            try {
                Class<?> cls = Class.forName(str);
                if (ComponentRegistrar.class.isAssignableFrom(cls)) {
                    arrayList.add((ComponentRegistrar) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                } else {
                    Log.w(TAG, String.format("Class %s is not an instance of %s", str, COMPONENT_SENTINEL_VALUE));
                }
            } catch (ClassNotFoundException e2) {
                Log.w(TAG, String.format("Class %s is not an found.", str), e2);
            } catch (IllegalAccessException e3) {
                Log.w(TAG, String.format("Could not instantiate %s.", str), e3);
            } catch (InstantiationException e4) {
                Log.w(TAG, String.format("Could not instantiate %s.", str), e4);
            } catch (NoSuchMethodException e5) {
                Log.w(TAG, String.format("Could not instantiate %s", str), e5);
            } catch (InvocationTargetException e6) {
                Log.w(TAG, String.format("Could not instantiate %s", str), e6);
            }
        }
        return arrayList;
    }

    public List<ComponentRegistrar> discover() {
        return instantiate(this.retriever.retrieve(this.context));
    }
}
