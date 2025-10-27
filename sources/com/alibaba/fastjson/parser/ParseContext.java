package com.alibaba.fastjson.parser;

import cn.hutool.core.text.StrPool;
import java.lang.reflect.Type;

/* loaded from: classes2.dex */
public class ParseContext {
    public final Object fieldName;
    public final int level;
    public Object object;
    public final ParseContext parent;
    private transient String path;
    public Type type;

    public ParseContext(ParseContext parseContext, Object obj, Object obj2) {
        this.parent = parseContext;
        this.object = obj;
        this.fieldName = obj2;
        this.level = parseContext == null ? 0 : parseContext.level + 1;
    }

    public String toString() {
        if (this.path == null) {
            if (this.parent == null) {
                this.path = "$";
            } else if (this.fieldName instanceof Integer) {
                this.path = this.parent.toString() + StrPool.BRACKET_START + this.fieldName + StrPool.BRACKET_END;
            } else {
                this.path = this.parent.toString() + StrPool.DOT + this.fieldName;
            }
        }
        return this.path;
    }
}
