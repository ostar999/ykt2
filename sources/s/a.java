package s;

import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.clone.DefaultCloneable;
import cn.hutool.core.util.ReflectUtil;

/* loaded from: classes.dex */
public final /* synthetic */ class a<T> {
    public static Object a(DefaultCloneable defaultCloneable) {
        try {
            return ReflectUtil.invoke(defaultCloneable, "clone", new Object[0]);
        } catch (Exception e2) {
            throw new CloneRuntimeException(e2);
        }
    }
}
