package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.assetpacks.AssetPackLocation;
import com.google.android.play.core.assetpacks.AssetPackManager;
import com.google.android.play.core.assetpacks.AssetPackManagerFactory;
import com.google.android.play.core.assetpacks.AssetPackState;
import com.google.android.play.core.assetpacks.AssetPackStateUpdateListener;
import com.google.android.play.core.assetpacks.AssetPackStates;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.RuntimeExecutionException;
import com.google.android.play.core.tasks.Task;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes6.dex */
final class a implements com.unity3d.player.d {

    /* renamed from: a, reason: collision with root package name */
    private static a f24021a;

    /* renamed from: b, reason: collision with root package name */
    private AssetPackManager f24022b;

    /* renamed from: c, reason: collision with root package name */
    private HashSet f24023c;

    /* renamed from: d, reason: collision with root package name */
    private Object f24024d;

    /* renamed from: com.unity3d.player.a$a, reason: collision with other inner class name */
    public static class RunnableC0398a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Set f24025a;

        /* renamed from: b, reason: collision with root package name */
        private String f24026b;

        /* renamed from: c, reason: collision with root package name */
        private int f24027c;

        /* renamed from: d, reason: collision with root package name */
        private long f24028d;

        /* renamed from: e, reason: collision with root package name */
        private long f24029e;

        /* renamed from: f, reason: collision with root package name */
        private int f24030f;

        /* renamed from: g, reason: collision with root package name */
        private int f24031g;

        public RunnableC0398a(Set set, String str, int i2, long j2, long j3, int i3, int i4) {
            this.f24025a = set;
            this.f24026b = str;
            this.f24027c = i2;
            this.f24028d = j2;
            this.f24029e = j3;
            this.f24030f = i3;
            this.f24031g = i4;
        }

        @Override // java.lang.Runnable
        public final void run() {
            Iterator it = this.f24025a.iterator();
            while (it.hasNext()) {
                ((IAssetPackManagerDownloadStatusCallback) it.next()).onStatusUpdate(this.f24026b, this.f24027c, this.f24028d, this.f24029e, this.f24030f, this.f24031g);
            }
        }
    }

    public class b implements AssetPackStateUpdateListener {

        /* renamed from: b, reason: collision with root package name */
        private HashSet f24033b;

        /* renamed from: c, reason: collision with root package name */
        private Looper f24034c;

        public b(a aVar, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
            this(iAssetPackManagerDownloadStatusCallback, Looper.myLooper());
        }

        public b(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback, Looper looper) {
            HashSet hashSet = new HashSet();
            this.f24033b = hashSet;
            hashSet.add(iAssetPackManagerDownloadStatusCallback);
            this.f24034c = looper;
        }

        private static Set a(HashSet hashSet) {
            return (Set) hashSet.clone();
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public synchronized void onStateUpdate(AssetPackState assetPackState) {
            if (assetPackState.status() == 4 || assetPackState.status() == 5 || assetPackState.status() == 0) {
                synchronized (a.f24021a) {
                    a.this.f24023c.remove(assetPackState.name());
                    if (a.this.f24023c.isEmpty()) {
                        a aVar = a.this;
                        aVar.a(aVar.f24024d);
                        a.c(a.this);
                    }
                }
            }
            if (this.f24033b.size() == 0) {
                return;
            }
            new Handler(this.f24034c).post(new RunnableC0398a(a(this.f24033b), assetPackState.name(), assetPackState.status(), assetPackState.totalBytesToDownload(), assetPackState.bytesDownloaded(), assetPackState.transferProgressPercentage(), assetPackState.errorCode()));
        }

        public final synchronized void a(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
            this.f24033b.add(iAssetPackManagerDownloadStatusCallback);
        }
    }

    public static class c implements OnSuccessListener {

        /* renamed from: a, reason: collision with root package name */
        private IAssetPackManagerMobileDataConfirmationCallback f24035a;

        /* renamed from: b, reason: collision with root package name */
        private Looper f24036b = Looper.myLooper();

        /* renamed from: com.unity3d.player.a$c$a, reason: collision with other inner class name */
        public static class RunnableC0399a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            private IAssetPackManagerMobileDataConfirmationCallback f24037a;

            /* renamed from: b, reason: collision with root package name */
            private boolean f24038b;

            public RunnableC0399a(IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback, boolean z2) {
                this.f24037a = iAssetPackManagerMobileDataConfirmationCallback;
                this.f24038b = z2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.f24037a.onMobileDataConfirmationResult(this.f24038b);
            }
        }

        public c(IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback) {
            this.f24035a = iAssetPackManagerMobileDataConfirmationCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onSuccess(Integer num) {
            if (this.f24035a != null) {
                new Handler(this.f24036b).post(new RunnableC0399a(this.f24035a, num.intValue() == -1));
            }
        }
    }

    public static class d implements OnCompleteListener {

        /* renamed from: a, reason: collision with root package name */
        private IAssetPackManagerDownloadStatusCallback f24039a;

        /* renamed from: b, reason: collision with root package name */
        private Looper f24040b = Looper.myLooper();

        /* renamed from: c, reason: collision with root package name */
        private String f24041c;

        public d(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback, String str) {
            this.f24039a = iAssetPackManagerDownloadStatusCallback;
            this.f24041c = str;
        }

        private void a(String str, int i2, int i3, long j2) {
            new Handler(this.f24040b).post(new RunnableC0398a(Collections.singleton(this.f24039a), str, i2, j2, i2 == 4 ? j2 : 0L, 0, i3));
        }

        public final void onComplete(Task task) {
            try {
                AssetPackStates assetPackStates = (AssetPackStates) task.getResult();
                Map mapPackStates = assetPackStates.packStates();
                if (mapPackStates.size() == 0) {
                    return;
                }
                for (AssetPackState assetPackState : mapPackStates.values()) {
                    if (assetPackState.errorCode() != 0 || assetPackState.status() == 4 || assetPackState.status() == 5 || assetPackState.status() == 0) {
                        a(assetPackState.name(), assetPackState.status(), assetPackState.errorCode(), assetPackStates.totalBytes());
                    } else {
                        a.f24021a.a(assetPackState.name(), this.f24039a, this.f24040b);
                    }
                }
            } catch (RuntimeExecutionException e2) {
                a(this.f24041c, 0, e2.getErrorCode(), 0L);
            }
        }
    }

    public static class e implements OnCompleteListener {

        /* renamed from: a, reason: collision with root package name */
        private IAssetPackManagerStatusQueryCallback f24042a;

        /* renamed from: b, reason: collision with root package name */
        private Looper f24043b = Looper.myLooper();

        /* renamed from: c, reason: collision with root package name */
        private String[] f24044c;

        /* renamed from: com.unity3d.player.a$e$a, reason: collision with other inner class name */
        public static class RunnableC0400a implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            private IAssetPackManagerStatusQueryCallback f24045a;

            /* renamed from: b, reason: collision with root package name */
            private long f24046b;

            /* renamed from: c, reason: collision with root package name */
            private String[] f24047c;

            /* renamed from: d, reason: collision with root package name */
            private int[] f24048d;

            /* renamed from: e, reason: collision with root package name */
            private int[] f24049e;

            public RunnableC0400a(IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback, long j2, String[] strArr, int[] iArr, int[] iArr2) {
                this.f24045a = iAssetPackManagerStatusQueryCallback;
                this.f24046b = j2;
                this.f24047c = strArr;
                this.f24048d = iArr;
                this.f24049e = iArr2;
            }

            @Override // java.lang.Runnable
            public final void run() {
                this.f24045a.onStatusResult(this.f24046b, this.f24047c, this.f24048d, this.f24049e);
            }
        }

        public e(IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback, String[] strArr) {
            this.f24042a = iAssetPackManagerStatusQueryCallback;
            this.f24044c = strArr;
        }

        public final void onComplete(Task task) {
            if (this.f24042a == null) {
                return;
            }
            int i2 = 0;
            try {
                AssetPackStates assetPackStates = (AssetPackStates) task.getResult();
                Map mapPackStates = assetPackStates.packStates();
                int size = mapPackStates.size();
                String[] strArr = new String[size];
                int[] iArr = new int[size];
                int[] iArr2 = new int[size];
                for (AssetPackState assetPackState : mapPackStates.values()) {
                    strArr[i2] = assetPackState.name();
                    iArr[i2] = assetPackState.status();
                    iArr2[i2] = assetPackState.errorCode();
                    i2++;
                }
                new Handler(this.f24043b).post(new RunnableC0400a(this.f24042a, assetPackStates.totalBytes(), strArr, iArr, iArr2));
            } catch (RuntimeExecutionException e2) {
                String message = e2.getMessage();
                for (String str : this.f24044c) {
                    if (message.contains(str)) {
                        new Handler(this.f24043b).post(new RunnableC0400a(this.f24042a, 0L, new String[]{str}, new int[]{0}, new int[]{e2.getErrorCode()}));
                        return;
                    }
                }
                String[] strArr2 = this.f24044c;
                int[] iArr3 = new int[strArr2.length];
                int[] iArr4 = new int[strArr2.length];
                for (int i3 = 0; i3 < this.f24044c.length; i3++) {
                    iArr3[i3] = 0;
                    iArr4[i3] = e2.getErrorCode();
                }
                new Handler(this.f24043b).post(new RunnableC0400a(this.f24042a, 0L, this.f24044c, iArr3, iArr4));
            }
        }
    }

    private a(Context context) {
        if (f24021a != null) {
            throw new RuntimeException("AssetPackManagerWrapper should be created only once. Use getInstance() instead.");
        }
        this.f24022b = AssetPackManagerFactory.getInstance(context);
        this.f24023c = new HashSet();
    }

    public static com.unity3d.player.d a(Context context) {
        if (f24021a == null) {
            f24021a = new a(context);
        }
        return f24021a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback, Looper looper) {
        synchronized (f24021a) {
            Object obj = this.f24024d;
            if (obj == null) {
                b bVar = new b(iAssetPackManagerDownloadStatusCallback, looper);
                this.f24022b.registerListener(bVar);
                this.f24024d = bVar;
            } else {
                ((b) obj).a(iAssetPackManagerDownloadStatusCallback);
            }
            this.f24023c.add(str);
            this.f24022b.fetch(Collections.singletonList(str));
        }
    }

    public static /* synthetic */ Object c(a aVar) {
        aVar.f24024d = null;
        return null;
    }

    @Override // com.unity3d.player.d
    public final Object a(IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        b bVar = new b(this, iAssetPackManagerDownloadStatusCallback);
        this.f24022b.registerListener(bVar);
        return bVar;
    }

    @Override // com.unity3d.player.d
    public final String a(String str) {
        AssetPackLocation packLocation = this.f24022b.getPackLocation(str);
        return packLocation == null ? "" : packLocation.assetsPath();
    }

    @Override // com.unity3d.player.d
    public final void a(Activity activity, IAssetPackManagerMobileDataConfirmationCallback iAssetPackManagerMobileDataConfirmationCallback) {
        this.f24022b.showCellularDataConfirmation(activity).addOnSuccessListener(new c(iAssetPackManagerMobileDataConfirmationCallback));
    }

    @Override // com.unity3d.player.d
    public final void a(Object obj) {
        if (obj instanceof b) {
            this.f24022b.unregisterListener((b) obj);
        }
    }

    @Override // com.unity3d.player.d
    public final void a(String[] strArr) {
        this.f24022b.cancel(Arrays.asList(strArr));
    }

    @Override // com.unity3d.player.d
    public final void a(String[] strArr, IAssetPackManagerDownloadStatusCallback iAssetPackManagerDownloadStatusCallback) {
        for (String str : strArr) {
            this.f24022b.getPackStates(Collections.singletonList(str)).addOnCompleteListener(new d(iAssetPackManagerDownloadStatusCallback, str));
        }
    }

    @Override // com.unity3d.player.d
    public final void a(String[] strArr, IAssetPackManagerStatusQueryCallback iAssetPackManagerStatusQueryCallback) {
        this.f24022b.getPackStates(Arrays.asList(strArr)).addOnCompleteListener(new e(iAssetPackManagerStatusQueryCallback, strArr));
    }

    @Override // com.unity3d.player.d
    public final void b(String str) {
        this.f24022b.removePack(str);
    }
}
