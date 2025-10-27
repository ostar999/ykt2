package cn.hutool.core.convert;

/* loaded from: classes.dex */
public final /* synthetic */ class a<T> {
    public static Object a(Converter converter, Object obj, Object obj2, boolean z2) throws Exception {
        try {
            return converter.convert(obj, obj2);
        } catch (Exception e2) {
            if (z2) {
                return obj2;
            }
            throw e2;
        }
    }
}
