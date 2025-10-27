package com.mobile.auth.gatewayauth.manager;

import android.text.TextUtils;
import android.util.SparseArray;
import com.ali.security.MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad;
import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.SafeProtector;
import com.mobile.auth.gatewayauth.model.VendorConfig;
import com.mobile.auth.gatewayauth.network.DispatchInfoItem;
import com.nirvana.tools.jsoner.JsonType;
import java.util.UUID;

/* loaded from: classes4.dex */
public class VendorSdkInfoManager {

    /* renamed from: a, reason: collision with root package name */
    private SparseArray<VendorConfig> f10141a = new com.mobile.auth.gatewayauth.manager.base.a(3);

    /* renamed from: b, reason: collision with root package name */
    private SparseArray<VendorConfig> f10142b = new com.mobile.auth.gatewayauth.manager.base.a(3);

    /* renamed from: c, reason: collision with root package name */
    private String f10143c;

    /* renamed from: d, reason: collision with root package name */
    private String f10144d;

    /* renamed from: e, reason: collision with root package name */
    private d f10145e;

    /* renamed from: f, reason: collision with root package name */
    private SystemManager f10146f;

    /* renamed from: g, reason: collision with root package name */
    private com.mobile.auth.o.a f10147g;

    /* renamed from: h, reason: collision with root package name */
    private volatile DispatchInfoItem f10148h;

    /* renamed from: i, reason: collision with root package name */
    private volatile DispatchInfoItem f10149i;

    /* renamed from: j, reason: collision with root package name */
    private volatile DispatchInfoItem f10150j;

    /* renamed from: k, reason: collision with root package name */
    private volatile long f10151k;

    /* renamed from: com.mobile.auth.gatewayauth.manager.VendorSdkInfoManager$1, reason: invalid class name */
    public class AnonymousClass1 extends JsonType<VendorConfig> {
        public AnonymousClass1() {
        }
    }

    static {
        MinosSecurityLoad_bbc1bffae6ebd3190382c8ec0f7941ad.SLoad("pns-2.14.3-LogOnlineStandardCuumRelease_alijtca_plus");
    }

    public VendorSdkInfoManager(d dVar, SystemManager systemManager) {
        this.f10145e = dVar;
        this.f10147g = dVar.a();
        this.f10146f = systemManager;
    }

    private void a(String[] strArr) {
        if (strArr == null) {
            return;
        }
        try {
            if (strArr.length >= 8) {
                String str = strArr[6];
                this.f10143c = str;
                this.f10145e.a(str);
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    private void a(String[] strArr, SparseArray<VendorConfig> sparseArray) {
        if (strArr != null) {
            try {
                if (strArr.length >= 6 && sparseArray != null) {
                    for (int i2 = 0; i2 < 3; i2++) {
                        VendorConfig vendorConfig = new VendorConfig();
                        int i3 = i2 * 2;
                        vendorConfig.setVendorAccessId(strArr[i3]);
                        vendorConfig.setVendorAccessSecret(strArr[i3 + 1]);
                        if (i2 == 0) {
                            vendorConfig.setVendorKey(Constant.VENDOR_CMCC);
                            sparseArray.put(1, vendorConfig);
                        } else if (i2 == 1) {
                            vendorConfig.setVendorKey(Constant.VENDOR_CUCC);
                            sparseArray.put(2, vendorConfig);
                        } else if (i2 == 2) {
                            vendorConfig.setVendorKey(Constant.VENDOR_CTCC);
                            sparseArray.put(3, vendorConfig);
                        }
                    }
                }
            } catch (Throwable th) {
                try {
                    ExceptionProcessor.processException(th);
                } catch (Throwable th2) {
                    ExceptionProcessor.processException(th2);
                }
            }
        }
    }

    private void b(String[] strArr) {
        if (strArr == null) {
            return;
        }
        try {
            if (strArr.length >= 10) {
                this.f10144d = strArr[9];
            }
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    @SafeProtector
    private native void loadVendorConfigsBySceneCodeFromDisk(String str);

    @SafeProtector
    private native void storeVendorConfigsBySceneCodeToDisk(String str);

    public VendorConfig a(int i2) {
        try {
            VendorConfig vendorConfig = this.f10142b.get(i2);
            return vendorConfig != null ? vendorConfig : this.f10141a.get(i2);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public String a(boolean z2) {
        if (z2) {
            return "SceneCode";
        }
        try {
            String str = this.f10143c;
            return str == null ? UUID.randomUUID().toString() : str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void a(long j2) {
        try {
            this.f10151k = j2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(DispatchInfoItem dispatchInfoItem) {
        try {
            this.f10148h = dispatchInfoItem;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean a() {
        try {
            return !TextUtils.isEmpty(this.f10143c);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public String b() {
        try {
            return this.f10143c;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void b(DispatchInfoItem dispatchInfoItem) {
        try {
            this.f10149i = dispatchInfoItem;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void c(DispatchInfoItem dispatchInfoItem) {
        try {
            this.f10150j = dispatchInfoItem;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public boolean c() {
        return false;
    }

    public DispatchInfoItem d() {
        try {
            return this.f10148h;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public DispatchInfoItem e() {
        try {
            return this.f10149i;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public DispatchInfoItem f() {
        try {
            return this.f10150j;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public long g() {
        try {
            return this.f10151k;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    @SafeProtector
    public native void setLocalVendorSdkInfo(String str);
}
