package org.repackage.com.vivo.identifier;

import android.database.ContentObserver;
import android.util.Log;

/* loaded from: classes9.dex */
public class IdentifierIdObserver extends ContentObserver {

    /* renamed from: a, reason: collision with root package name */
    private static final String f28070a = "VMS_IDLG_SDK_Observer";

    /* renamed from: b, reason: collision with root package name */
    private String f28071b;

    /* renamed from: c, reason: collision with root package name */
    private int f28072c;

    /* renamed from: d, reason: collision with root package name */
    private IdentifierIdClient f28073d;

    public IdentifierIdObserver(IdentifierIdClient identifierIdClient, int i2, String str) {
        super(null);
        this.f28073d = identifierIdClient;
        this.f28072c = i2;
        this.f28071b = str;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2) {
        IdentifierIdClient identifierIdClient = this.f28073d;
        if (identifierIdClient != null) {
            identifierIdClient.a(this.f28072c, this.f28071b);
        } else {
            Log.e(f28070a, "mIdentifierIdClient is null");
        }
    }
}
