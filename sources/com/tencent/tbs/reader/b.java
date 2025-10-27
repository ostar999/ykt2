package com.tencent.tbs.reader;

import com.tencent.tbs.one.TBSOneComponent;

/* loaded from: classes6.dex */
public class b implements ITbsReaderCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ITbsReaderEntry f22272a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ TBSOneComponent f22273b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ a f22274c;

    public b(a aVar, ITbsReaderEntry iTbsReaderEntry, TBSOneComponent tBSOneComponent) {
        this.f22274c = aVar;
        this.f22272a = iTbsReaderEntry;
        this.f22273b = tBSOneComponent;
    }

    @Override // com.tencent.tbs.reader.ITbsReaderCallback
    public void onCallBackAction(Integer num, Object obj, Object obj2) {
        if (num.intValue() == 7003 && (obj2 instanceof Integer)) {
            int iIntValue = ((Integer) obj2).intValue();
            if (iIntValue == 0) {
                a aVar = this.f22274c;
                iIntValue = aVar.f22268e.a(aVar.f22267d, this.f22272a);
            }
            if (this.f22274c.f22266c != null) {
                int versionCode = iIntValue == 0 ? this.f22273b.getVersionCode() : -1;
                a aVar2 = this.f22274c;
                aVar2.a(aVar2.f22266c, iIntValue, versionCode);
            }
        }
    }
}
