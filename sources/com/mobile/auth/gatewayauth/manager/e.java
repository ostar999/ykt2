package com.mobile.auth.gatewayauth.manager;

import com.mobile.auth.gatewayauth.Constant;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.model.VendorConfig;
import com.mobile.auth.gatewayauth.network.DispatchInfoItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private SystemManager f10238a;

    /* renamed from: b, reason: collision with root package name */
    private a f10239b;

    /* renamed from: c, reason: collision with root package name */
    private a f10240c;

    /* renamed from: d, reason: collision with root package name */
    private a f10241d;

    /* renamed from: e, reason: collision with root package name */
    private a f10242e;

    /* renamed from: f, reason: collision with root package name */
    private d f10243f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f10244g = false;

    public e(SystemManager systemManager, d dVar) {
        this.f10238a = systemManager;
        this.f10243f = dVar;
        this.f10239b = new com.mobile.auth.q.d(systemManager.e(), this.f10243f);
        this.f10240c = new com.mobile.auth.s.a(this.f10238a.e(), this.f10243f);
        this.f10241d = new com.mobile.auth.r.a(this.f10238a.e(), this.f10243f);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.mobile.auth.gatewayauth.manager.a a(java.lang.String r6) {
        /*
            r5 = this;
            r0 = 0
            int r1 = r6.hashCode()     // Catch: java.lang.Throwable -> L4a
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r1) {
                case -1350608857: goto L2a;
                case 95009260: goto L20;
                case 880255494: goto L16;
                case 880617272: goto Lc;
                default: goto Lb;
            }     // Catch: java.lang.Throwable -> L4a
        Lb:
            goto L34
        Lc:
            java.lang.String r1 = "cm_zyhl"
            boolean r6 = r6.equals(r1)     // Catch: java.lang.Throwable -> L4a
            if (r6 == 0) goto L34
            r6 = 0
            goto L35
        L16:
            java.lang.String r1 = "cm_ntyd"
            boolean r6 = r6.equals(r1)     // Catch: java.lang.Throwable -> L4a
            if (r6 == 0) goto L34
            r6 = r2
            goto L35
        L20:
            java.lang.String r1 = "cu_xw"
            boolean r6 = r6.equals(r1)     // Catch: java.lang.Throwable -> L4a
            if (r6 == 0) goto L34
            r6 = r4
            goto L35
        L2a:
            java.lang.String r1 = "ct_sjl"
            boolean r6 = r6.equals(r1)     // Catch: java.lang.Throwable -> L4a
            if (r6 == 0) goto L34
            r6 = r3
            goto L35
        L34:
            r6 = -1
        L35:
            if (r6 == 0) goto L47
            if (r6 == r4) goto L44
            if (r6 == r3) goto L41
            if (r6 == r2) goto L3e
            return r0
        L3e:
            com.mobile.auth.gatewayauth.manager.a r6 = r5.f10242e     // Catch: java.lang.Throwable -> L4a
            return r6
        L41:
            com.mobile.auth.gatewayauth.manager.a r6 = r5.f10241d     // Catch: java.lang.Throwable -> L4a
            return r6
        L44:
            com.mobile.auth.gatewayauth.manager.a r6 = r5.f10240c     // Catch: java.lang.Throwable -> L4a
            return r6
        L47:
            com.mobile.auth.gatewayauth.manager.a r6 = r5.f10239b     // Catch: java.lang.Throwable -> L4a
            return r6
        L4a:
            r6 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r6)     // Catch: java.lang.Throwable -> L4f
            return r0
        L4f:
            r6 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.gatewayauth.manager.e.a(java.lang.String):com.mobile.auth.gatewayauth.manager.a");
    }

    public boolean a() {
        try {
            return this.f10244g;
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

    public boolean a(VendorSdkInfoManager vendorSdkInfoManager, boolean z2, boolean z3) {
        int i2;
        a aVar;
        String vendorAccessId;
        String vendorAccessSecret;
        com.mobile.auth.r.a aVar2;
        String channelCode;
        a aVar3;
        String vendorAccessId2;
        String vendorAccessSecret2;
        com.mobile.auth.s.a aVar4;
        String channelCode2;
        try {
            VendorConfig vendorConfigA = vendorSdkInfoManager.a(1);
            VendorConfig vendorConfigA2 = vendorSdkInfoManager.a(2);
            VendorConfig vendorConfigA3 = vendorSdkInfoManager.a(3);
            if (vendorConfigA != null) {
                if (vendorSdkInfoManager.d() != null) {
                    this.f10239b.c();
                    if (Constant.VENDOR_CMCC_TYPE1.equals(vendorSdkInfoManager.d().getSupplierSdkType())) {
                        this.f10244g = false;
                        ((com.mobile.auth.q.d) this.f10239b).a(vendorSdkInfoManager.d().getChannelCode());
                        if (vendorSdkInfoManager.d().getAppId().equals(((com.mobile.auth.q.d) this.f10239b).d())) {
                            vendorSdkInfoManager.a((DispatchInfoItem) null);
                        } else {
                            this.f10239b.a(vendorSdkInfoManager.d().getAppId(), vendorSdkInfoManager.d().getAppSecret());
                        }
                        com.mobile.auth.gatewayauth.utils.d.a().f(false);
                    } else if (Constant.VENDOR_CMCC_BJYD.equals(vendorSdkInfoManager.d().getSupplierSdkType())) {
                        this.f10239b.c();
                        com.mobile.auth.q.d dVar = new com.mobile.auth.q.d(this.f10238a.e(), this.f10243f);
                        this.f10239b = dVar;
                        dVar.a(vendorSdkInfoManager.d().getAppId(), vendorSdkInfoManager.d().getAppSecret());
                        ((com.mobile.auth.q.d) this.f10239b).a(vendorSdkInfoManager.d().getChannelCode());
                    }
                } else {
                    this.f10239b.a(vendorConfigA.getVendorAccessId(), vendorConfigA.getVendorAccessSecret());
                    this.f10244g = false;
                }
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (vendorConfigA2 != null) {
                if (vendorSdkInfoManager.e() != null) {
                    if (Constant.VENDOR_CUCC_TYPE1.equals(vendorSdkInfoManager.e().getSupplierSdkType())) {
                        ((com.mobile.auth.s.a) this.f10240c).a(vendorSdkInfoManager.e().getChannelCode());
                        if (vendorSdkInfoManager.e().getAppId().equals(vendorConfigA2.getVendorAccessId())) {
                            vendorSdkInfoManager.b((DispatchInfoItem) null);
                            i2++;
                        } else {
                            this.f10240c.a(vendorSdkInfoManager.e().getAppId(), vendorSdkInfoManager.e().getAppSecret());
                            aVar4 = (com.mobile.auth.s.a) this.f10240c;
                            channelCode2 = vendorSdkInfoManager.e().getChannelCode();
                        }
                    } else if (Constant.VENDOR_CUCC_TYPE2.equals(vendorSdkInfoManager.e().getSupplierSdkType())) {
                        this.f10240c.a(vendorSdkInfoManager.e().getAppId(), vendorSdkInfoManager.e().getAppSecret());
                        aVar4 = (com.mobile.auth.s.a) this.f10240c;
                        channelCode2 = vendorSdkInfoManager.e().getChannelCode();
                    } else {
                        aVar3 = this.f10240c;
                        vendorAccessId2 = vendorConfigA2.getVendorAccessId();
                        vendorAccessSecret2 = vendorConfigA2.getVendorAccessSecret();
                    }
                    aVar4.a(channelCode2);
                    i2++;
                } else {
                    aVar3 = this.f10240c;
                    vendorAccessId2 = vendorConfigA2.getVendorAccessId();
                    vendorAccessSecret2 = vendorConfigA2.getVendorAccessSecret();
                }
                aVar3.a(vendorAccessId2, vendorAccessSecret2);
                i2++;
            }
            if (vendorConfigA3 != null) {
                if (vendorSdkInfoManager.f() != null) {
                    if (Constant.VENDOR_CTCC_TYPE1.equals(vendorSdkInfoManager.f().getSupplierSdkType())) {
                        ((com.mobile.auth.r.a) this.f10241d).a(vendorSdkInfoManager.f().getChannelCode());
                        if (vendorSdkInfoManager.f().getAppId().equals(vendorConfigA3.getVendorAccessId())) {
                            vendorSdkInfoManager.c(null);
                            i2++;
                        } else {
                            this.f10241d.a(vendorSdkInfoManager.f().getAppId(), vendorSdkInfoManager.f().getAppSecret());
                            aVar2 = (com.mobile.auth.r.a) this.f10241d;
                            channelCode = vendorSdkInfoManager.f().getChannelCode();
                        }
                    } else if (Constant.VENDOR_CTCC_TYPE2.equals(vendorSdkInfoManager.f().getSupplierSdkType())) {
                        this.f10241d.a(vendorSdkInfoManager.f().getAppId(), vendorSdkInfoManager.f().getAppSecret());
                        aVar2 = (com.mobile.auth.r.a) this.f10241d;
                        channelCode = vendorSdkInfoManager.f().getChannelCode();
                    } else {
                        aVar = this.f10241d;
                        vendorAccessId = vendorConfigA3.getVendorAccessId();
                        vendorAccessSecret = vendorConfigA3.getVendorAccessSecret();
                    }
                    aVar2.a(channelCode);
                    i2++;
                } else {
                    aVar = this.f10241d;
                    vendorAccessId = vendorConfigA3.getVendorAccessId();
                    vendorAccessSecret = vendorConfigA3.getVendorAccessSecret();
                }
                aVar.a(vendorAccessId, vendorAccessSecret);
                i2++;
            }
            if (z2) {
                if (z3) {
                    com.mobile.auth.gatewayauth.utils.d.a().b(true);
                    com.mobile.auth.gatewayauth.utils.d.a().c(false);
                } else {
                    com.mobile.auth.gatewayauth.utils.d.a().e(true);
                    com.mobile.auth.gatewayauth.utils.d.a().d(false);
                }
            }
            return i2 >= 3;
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

    public List<a> b() {
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.f10239b);
            arrayList.add(this.f10240c);
            arrayList.add(this.f10241d);
            arrayList.add(this.f10242e);
            return arrayList;
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
}
