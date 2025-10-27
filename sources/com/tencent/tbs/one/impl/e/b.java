package com.tencent.tbs.one.impl.e;

import android.os.Bundle;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneComponent;
import com.tencent.tbs.one.TBSOneEventEmitter;
import com.tencent.tbs.one.TBSOneEventReceiver;
import com.tencent.tbs.one.impl.a.o;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/* loaded from: classes6.dex */
public final class b implements TBSOneEventEmitter {

    /* renamed from: a, reason: collision with root package name */
    public WeakReference<h> f22112a;

    public b(h hVar) {
        this.f22112a = new WeakReference<>(hVar);
    }

    public static void a(String str, String str2) {
        com.tencent.tbs.one.impl.a.g.b("Failed to emit event %s, error: %s", str, str2);
    }

    public static void a(String str, String str2, String str3, String str4, Throwable th) {
        com.tencent.tbs.one.impl.a.g.b("Failed to emit event %s to %s#%s, error: %s", str3, str, str2, str4, th);
    }

    public static void b(TBSOneComponent tBSOneComponent, String str, String str2, Map<String, Object> map) throws ClassNotFoundException {
        String name;
        String str3;
        if (tBSOneComponent.getEntryClassLoader() == null) {
            a(tBSOneComponent.getName(), str, str2, "no entry class loader found", null);
        }
        try {
            Class<?> clsLoadClass = tBSOneComponent.getEntryClassLoader().loadClass(str);
            if (TBSOneEventReceiver.class.isAssignableFrom(clsLoadClass)) {
                ((TBSOneEventReceiver) clsLoadClass.getConstructor(new Class[0]).newInstance(new Object[0])).onReceive(str2, map);
            } else {
                a(tBSOneComponent.getName(), str, str2, "can't assign receiver class to com.tencent.tbs.one.TBSOneEventReceiver", null);
            }
        } catch (ClassNotFoundException e2) {
            e = e2;
            name = tBSOneComponent.getName();
            str3 = "can't load event receiver class";
            a(name, str, str2, str3, e);
        } catch (NoSuchMethodException e3) {
            e = e3;
            name = tBSOneComponent.getName();
            str3 = "can't find constructor method for event receiver class";
            a(name, str, str2, str3, e);
        } catch (Exception e4) {
            e = e4;
            name = tBSOneComponent.getName();
            str3 = "can't construct event receiver object";
            a(name, str, str2, str3, e);
        }
    }

    @Override // com.tencent.tbs.one.TBSOneEventEmitter
    public final void emit(TBSOneEventEmitter.UnloadedBehavior unloadedBehavior, final String str, final String str2, final String str3, final Map<String, Object> map) throws ClassNotFoundException {
        final h hVar = this.f22112a.get();
        if (hVar == null) {
            a(str, str2, str3, "TBSOneManager is not alive", null);
            return;
        }
        com.tencent.tbs.one.impl.c.a aVarF = hVar.f(str);
        if (aVarF != null) {
            b(aVarF, str2, str3, map);
            return;
        }
        if (unloadedBehavior == TBSOneEventEmitter.UnloadedBehavior.IGNORE) {
            a(str, str2, str3, "component is not loaded yet", null);
        } else if (unloadedBehavior == TBSOneEventEmitter.UnloadedBehavior.LOAD && hVar.b(str)) {
            a(str, str2, str3, "component is not installed yet", null);
        } else {
            o.a(new Runnable() { // from class: com.tencent.tbs.one.impl.e.b.1
                @Override // java.lang.Runnable
                public final void run() {
                    hVar.a(str, (Bundle) null, new TBSOneCallback<TBSOneComponent>() { // from class: com.tencent.tbs.one.impl.e.b.1.1
                        @Override // com.tencent.tbs.one.TBSOneCallback
                        public final /* synthetic */ void onCompleted(TBSOneComponent tBSOneComponent) throws ClassNotFoundException {
                            AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                            com.tencent.tbs.one.impl.c.a aVarF2 = hVar.f(str);
                            if (aVarF2 != null) {
                                AnonymousClass1 anonymousClass12 = AnonymousClass1.this;
                                b.b(aVarF2, str2, str3, map);
                            }
                        }
                    });
                }
            });
        }
    }

    @Override // com.tencent.tbs.one.TBSOneEventEmitter
    public final void emit(String str, Map<String, Object> map) throws ClassNotFoundException {
        String str2;
        h hVar = this.f22112a.get();
        if (hVar == null) {
            str2 = "TBSOneManager is not alive";
        } else {
            List<com.tencent.tbs.one.impl.common.g> list = hVar.f22210g.get(str);
            if (list != null) {
                ListIterator<com.tencent.tbs.one.impl.common.g> listIterator = list.listIterator();
                while (listIterator.hasNext()) {
                    com.tencent.tbs.one.impl.common.g next = listIterator.next();
                    b(hVar.f(next.f22013a), next.f22014b, str, map);
                }
                return;
            }
            str2 = "no event receiver found";
        }
        a(str, str2);
    }
}
