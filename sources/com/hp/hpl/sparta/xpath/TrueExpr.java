package com.hp.hpl.sparta.xpath;

/* loaded from: classes4.dex */
public class TrueExpr extends BooleanExpr {
    static final TrueExpr INSTANCE = new TrueExpr();

    private TrueExpr() {
    }

    @Override // com.hp.hpl.sparta.xpath.BooleanExpr
    public void accept(BooleanExprVisitor booleanExprVisitor) {
        booleanExprVisitor.visit(this);
    }

    public String toString() {
        return "";
    }
}
