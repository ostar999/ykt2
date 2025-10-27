package e;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes8.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static i f26740a = null;

    /* renamed from: b, reason: collision with root package name */
    public static Map<Integer, l> f26741b = new HashMap();

    /* renamed from: c, reason: collision with root package name */
    public static final String f26742c = "EngineFactory";

    public static void a(int i2, c cVar, i iVar) {
        if (i2 != 0) {
            if (i2 != 1) {
                return;
            }
            if (f26741b.get(0) != null) {
                f26741b.get(0);
                if (l.f() != null) {
                    return;
                }
            }
            f26741b.put(0, h.a(cVar, iVar));
            return;
        }
        if (f26741b.get(0) != null) {
            f26741b.get(0);
            if (l.f() != null) {
                return;
            }
        }
        c.h.a(f26742c, "createLogicEngineInstance start");
        h hVarA = h.a(cVar, iVar);
        c.h.a(f26742c, "createLogicEngineInstance end");
        f26741b.put(0, hVarA);
    }

    public static h a() {
        return (h) f26741b.get(0);
    }
}
