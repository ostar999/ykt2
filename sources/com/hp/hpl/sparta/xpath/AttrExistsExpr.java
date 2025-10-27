package com.hp.hpl.sparta.xpath;

import cn.hutool.core.text.StrPool;

/* loaded from: classes4.dex */
public class AttrExistsExpr extends AttrExpr {
    public AttrExistsExpr(String str) {
        super(str);
    }

    @Override // com.hp.hpl.sparta.xpath.BooleanExpr
    public void accept(BooleanExprVisitor booleanExprVisitor) throws XPathException {
        booleanExprVisitor.visit(this);
    }

    @Override // com.hp.hpl.sparta.xpath.AttrExpr
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(StrPool.BRACKET_START);
        stringBuffer.append(super.toString());
        stringBuffer.append(StrPool.BRACKET_END);
        return stringBuffer.toString();
    }
}
