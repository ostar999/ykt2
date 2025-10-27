package com.hp.hpl.sparta.xpath;

/* loaded from: classes4.dex */
public class AttrNotEqualsExpr extends AttrCompareExpr {
    public AttrNotEqualsExpr(String str, String str2) {
        super(str, str2);
    }

    @Override // com.hp.hpl.sparta.xpath.BooleanExpr
    public void accept(BooleanExprVisitor booleanExprVisitor) throws XPathException {
        booleanExprVisitor.visit(this);
    }

    @Override // com.hp.hpl.sparta.xpath.AttrExpr
    public String toString() {
        return toString("!=");
    }
}
