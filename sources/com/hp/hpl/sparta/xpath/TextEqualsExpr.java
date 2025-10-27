package com.hp.hpl.sparta.xpath;

/* loaded from: classes4.dex */
public class TextEqualsExpr extends TextCompareExpr {
    public TextEqualsExpr(String str) {
        super(str);
    }

    @Override // com.hp.hpl.sparta.xpath.BooleanExpr
    public void accept(BooleanExprVisitor booleanExprVisitor) throws XPathException {
        booleanExprVisitor.visit(this);
    }

    public String toString() {
        return toString("=");
    }
}
