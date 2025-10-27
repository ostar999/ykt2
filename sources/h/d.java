package h;

/* loaded from: classes8.dex */
public enum d {
    RECON_RS_NULL,
    RECON_RS_CONFAIL,
    RECON_RS_DISCONNECT,
    RECON_RS_KEEPLIVEFAIL,
    RECON_RS_SWITCH_SIGNAL,
    RECON_RS_LOGOFF_RECON,
    RECON_RS_LOGOFF_REFRESH,
    RECON_RS_REQUEST_SIGNAL;

    public static d a(int i2) {
        for (d dVar : values()) {
            if (dVar.ordinal() == i2) {
                return dVar;
            }
        }
        return RECON_RS_NULL;
    }
}
