package com.google.android.gms.internal.icing;

/* loaded from: classes3.dex */
final class zzgi {
    public static String zzd(zzct zzctVar) {
        zzgl zzglVar = new zzgl(zzctVar);
        StringBuilder sb = new StringBuilder(zzglVar.size());
        for (int i2 = 0; i2 < zzglVar.size(); i2++) {
            byte bZzk = zzglVar.zzk(i2);
            if (bZzk == 34) {
                sb.append("\\\"");
            } else if (bZzk == 39) {
                sb.append("\\'");
            } else if (bZzk != 92) {
                switch (bZzk) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (bZzk < 32 || bZzk > 126) {
                            sb.append('\\');
                            sb.append((char) (((bZzk >>> 6) & 3) + 48));
                            sb.append((char) (((bZzk >>> 3) & 7) + 48));
                            sb.append((char) ((bZzk & 7) + 48));
                            break;
                        } else {
                            sb.append((char) bZzk);
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }
}
