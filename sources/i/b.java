package i;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.json.JSONObject;

/* loaded from: classes8.dex */
public class b implements InvocationHandler {

    /* renamed from: a, reason: collision with root package name */
    public h.f f27115a;

    public b(h.f fVar) {
        this.f27115a = fVar;
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        h.f fVar = this.f27115a;
        if (fVar == null || !(fVar instanceof e)) {
            return null;
        }
        Object obj2 = objArr[0];
        if (!(obj2 instanceof c)) {
            return null;
        }
        c cVar = (c) obj2;
        e eVar = (e) fVar;
        this.f27115a.d(cVar.d(), eVar.a(eVar.b(cVar), (JSONObject) method.invoke(obj, objArr)).toString());
        return null;
    }
}
