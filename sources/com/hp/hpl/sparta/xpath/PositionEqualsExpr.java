package com.hp.hpl.sparta.xpath;

import cn.hutool.core.text.StrPool;

/* loaded from: classes4.dex */
public class PositionEqualsExpr extends BooleanExpr {
    private final int position_;

    public PositionEqualsExpr(int i2) {
        this.position_ = i2;
    }

    @Override // com.hp.hpl.sparta.xpath.BooleanExpr
    public void accept(BooleanExprVisitor booleanExprVisitor) throws XPathException {
        booleanExprVisitor.visit(this);
    }

    public int getPosition() {
        return this.position_;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(StrPool.BRACKET_START);
        stringBuffer.append(this.position_);
        stringBuffer.append(StrPool.BRACKET_END);
        return stringBuffer.toString();
    }
}
