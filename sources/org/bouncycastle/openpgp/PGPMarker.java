package org.bouncycastle.openpgp;

import java.io.IOException;
import org.bouncycastle.bcpg.BCPGInputStream;
import org.bouncycastle.bcpg.MarkerPacket;

/* loaded from: classes9.dex */
public class PGPMarker {

    /* renamed from: p, reason: collision with root package name */
    private MarkerPacket f27946p;

    public PGPMarker(BCPGInputStream bCPGInputStream) throws IOException {
        this.f27946p = (MarkerPacket) bCPGInputStream.readPacket();
    }
}
