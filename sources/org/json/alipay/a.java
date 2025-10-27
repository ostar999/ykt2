package org.json.alipay;

import cn.hutool.core.text.StrPool;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes9.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private ArrayList f27975a;

    public a() {
        this.f27975a = new ArrayList();
    }

    public a(Object obj) throws JSONException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        this();
        if (!obj.getClass().isArray()) {
            throw new JSONException("JSONArray initial value should be a string or collection or array.");
        }
        int length = Array.getLength(obj);
        for (int i2 = 0; i2 < length; i2++) {
            this.f27975a.add(Array.get(obj, i2));
        }
    }

    public a(String str) {
        this(new c(str));
    }

    public a(Collection collection) {
        this.f27975a = collection == null ? new ArrayList() : new ArrayList(collection);
    }

    public a(c cVar) throws JSONException {
        char c3;
        ArrayList arrayList;
        Object objD;
        this();
        char c4 = cVar.c();
        if (c4 == '[') {
            c3 = ']';
        } else {
            if (c4 != '(') {
                throw cVar.a("A JSONArray text must start with '['");
            }
            c3 = ')';
        }
        if (cVar.c() == ']') {
            return;
        }
        do {
            cVar.a();
            char c5 = cVar.c();
            cVar.a();
            if (c5 == ',') {
                arrayList = this.f27975a;
                objD = null;
            } else {
                arrayList = this.f27975a;
                objD = cVar.d();
            }
            arrayList.add(objD);
            char c6 = cVar.c();
            if (c6 != ')') {
                if (c6 != ',' && c6 != ';') {
                    if (c6 != ']') {
                        throw cVar.a("Expected a ',' or ']'");
                    }
                }
            }
            if (c3 == c6) {
                return;
            }
            throw cVar.a("Expected a '" + new Character(c3) + "'");
        } while (cVar.c() != ']');
    }

    private String a(String str) {
        int size = this.f27975a.size();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < size; i2++) {
            if (i2 > 0) {
                stringBuffer.append(str);
            }
            stringBuffer.append(b.a(this.f27975a.get(i2)));
        }
        return stringBuffer.toString();
    }

    public final int a() {
        return this.f27975a.size();
    }

    public final Object a(int i2) throws JSONException {
        Object obj = (i2 < 0 || i2 >= this.f27975a.size()) ? null : this.f27975a.get(i2);
        if (obj != null) {
            return obj;
        }
        throw new JSONException("JSONArray[" + i2 + "] not found.");
    }

    public String toString() {
        try {
            return StrPool.BRACKET_START + a(",") + ']';
        } catch (Exception unused) {
            return null;
        }
    }
}
