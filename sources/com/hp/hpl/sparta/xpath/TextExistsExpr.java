package com.hp.hpl.sparta.xpath;

/* loaded from: classes4.dex */
public class TextExistsExpr extends BooleanExpr {
    static final TextExistsExpr INSTANCE = new TextExistsExpr();

    private TextExistsExpr() {
    }

    @Override // com.hp.hpl.sparta.xpath.BooleanExpr
    public void accept(BooleanExprVisitor booleanExprVisitor) throws XPathException {
        booleanExprVisitor.visit(this);
    }

    public String toString() {
        return "[text()]";
    }
}
