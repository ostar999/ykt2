package com.tencent.smtt.sdk;

import android.content.Context;
import android.os.Bundle;
import com.tencent.smtt.export.external.DexLoader;

/* loaded from: classes6.dex */
public class TbsVideoCacheTask {
    public static final String KEY_VIDEO_CACHE_PARAM_FILENAME = "filename";
    public static final String KEY_VIDEO_CACHE_PARAM_FOLDERPATH = "folderPath";
    public static final String KEY_VIDEO_CACHE_PARAM_HEADER = "header";
    public static final String KEY_VIDEO_CACHE_PARAM_URL = "url";

    /* renamed from: a, reason: collision with root package name */
    Context f21027a;

    /* renamed from: b, reason: collision with root package name */
    TbsVideoCacheListener f21028b;

    /* renamed from: e, reason: collision with root package name */
    private String f21031e;

    /* renamed from: f, reason: collision with root package name */
    private String f21032f;

    /* renamed from: c, reason: collision with root package name */
    private boolean f21029c = false;

    /* renamed from: d, reason: collision with root package name */
    private q f21030d = null;

    /* renamed from: g, reason: collision with root package name */
    private Object f21033g = null;

    public TbsVideoCacheTask(Context context, Bundle bundle, TbsVideoCacheListener tbsVideoCacheListener) {
        this.f21027a = context;
        this.f21028b = tbsVideoCacheListener;
        if (bundle != null) {
            this.f21031e = bundle.getString("taskId");
            this.f21032f = bundle.getString("url");
        }
        a(bundle);
    }

    private void a(Bundle bundle) {
        TbsVideoCacheListener tbsVideoCacheListener;
        String str;
        DexLoader dexLoaderC;
        if (this.f21030d == null) {
            g.a(true).a(this.f21027a, false, false);
            u uVarA = g.a(true).a();
            if (uVarA != null) {
                dexLoaderC = uVarA.c();
            } else {
                this.f21028b.onVideoDownloadError(this, -1, "init engine error!", null);
                dexLoaderC = null;
            }
            if (dexLoaderC != null) {
                this.f21030d = new q(dexLoaderC);
            } else {
                this.f21028b.onVideoDownloadError(this, -1, "Java dexloader invalid!", null);
            }
        }
        q qVar = this.f21030d;
        if (qVar != null) {
            Object objA = qVar.a(this.f21027a, this, bundle);
            this.f21033g = objA;
            if (objA != null) {
                return;
            }
            tbsVideoCacheListener = this.f21028b;
            str = "init task error!";
        } else {
            tbsVideoCacheListener = this.f21028b;
            if (tbsVideoCacheListener == null) {
                return;
            } else {
                str = "init error!";
            }
        }
        tbsVideoCacheListener.onVideoDownloadError(this, -1, str, null);
    }

    public long getContentLength() {
        q qVar = this.f21030d;
        if (qVar != null && this.f21033g != null) {
            return qVar.d();
        }
        TbsVideoCacheListener tbsVideoCacheListener = this.f21028b;
        if (tbsVideoCacheListener == null) {
            return 0L;
        }
        tbsVideoCacheListener.onVideoDownloadError(this, -1, "getContentLength failed, init uncompleted!", null);
        return 0L;
    }

    public int getDownloadedSize() {
        q qVar = this.f21030d;
        if (qVar != null && this.f21033g != null) {
            return qVar.e();
        }
        TbsVideoCacheListener tbsVideoCacheListener = this.f21028b;
        if (tbsVideoCacheListener == null) {
            return 0;
        }
        tbsVideoCacheListener.onVideoDownloadError(this, -1, "getDownloadedSize failed, init uncompleted!", null);
        return 0;
    }

    public int getProgress() {
        q qVar = this.f21030d;
        if (qVar != null && this.f21033g != null) {
            return qVar.f();
        }
        TbsVideoCacheListener tbsVideoCacheListener = this.f21028b;
        if (tbsVideoCacheListener == null) {
            return 0;
        }
        tbsVideoCacheListener.onVideoDownloadError(this, -1, "getProgress failed, init uncompleted!", null);
        return 0;
    }

    public String getTaskID() {
        return this.f21031e;
    }

    public String getTaskUrl() {
        return this.f21032f;
    }

    public void pauseTask() {
        q qVar = this.f21030d;
        if (qVar != null && this.f21033g != null) {
            qVar.a();
            return;
        }
        TbsVideoCacheListener tbsVideoCacheListener = this.f21028b;
        if (tbsVideoCacheListener != null) {
            tbsVideoCacheListener.onVideoDownloadError(this, -1, "pauseTask failed, init uncompleted!", null);
        }
    }

    public void removeTask(boolean z2) {
        q qVar = this.f21030d;
        if (qVar != null && this.f21033g != null) {
            qVar.a(z2);
            return;
        }
        TbsVideoCacheListener tbsVideoCacheListener = this.f21028b;
        if (tbsVideoCacheListener != null) {
            tbsVideoCacheListener.onVideoDownloadError(this, -1, "removeTask failed, init uncompleted!", null);
        }
    }

    public void resumeTask() {
        q qVar = this.f21030d;
        if (qVar != null && this.f21033g != null) {
            qVar.b();
            return;
        }
        TbsVideoCacheListener tbsVideoCacheListener = this.f21028b;
        if (tbsVideoCacheListener != null) {
            tbsVideoCacheListener.onVideoDownloadError(this, -1, "resumeTask failed, init uncompleted!", null);
        }
    }

    public void stopTask() {
        q qVar = this.f21030d;
        if (qVar != null && this.f21033g != null) {
            qVar.c();
            return;
        }
        TbsVideoCacheListener tbsVideoCacheListener = this.f21028b;
        if (tbsVideoCacheListener != null) {
            tbsVideoCacheListener.onVideoDownloadError(this, -1, "stopTask failed, init uncompleted!", null);
        }
    }
}
