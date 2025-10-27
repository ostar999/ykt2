package com.hp.hpl.sparta.xpath;

/* loaded from: classes4.dex */
public class AttrLessExpr extends AttrRelationalExpr {
    public AttrLessExpr(String str, int i2) {
        super(str, i2);
    }

    @Override // com.hp.hpl.sparta.xpath.BooleanExpr
    public void accept(BooleanExprVisitor booleanExprVisitor) throws XPathException {
        booleanExprVisitor.visit(this);
    }

    @Override // com.hp.hpl.sparta.xpath.AttrExpr
    public String toString() {
        return toString("<");
    }
}
