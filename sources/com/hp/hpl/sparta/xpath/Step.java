package com.hp.hpl.sparta.xpath;

import cn.hutool.core.text.StrPool;
import java.io.IOException;

/* loaded from: classes4.dex */
public class Step {
    public static Step DOT = new Step(ThisNodeTest.INSTANCE, TrueExpr.INSTANCE);
    private final boolean multiLevel_;
    private final NodeTest nodeTest_;
    private final BooleanExpr predicate_;

    public Step(NodeTest nodeTest, BooleanExpr booleanExpr) {
        this.nodeTest_ = nodeTest;
        this.predicate_ = booleanExpr;
        this.multiLevel_ = false;
    }

    public Step(XPath xPath, boolean z2, SimpleStreamTokenizer simpleStreamTokenizer) throws XPathException, IOException {
        NodeTest elementTest;
        this.multiLevel_ = z2;
        int i2 = simpleStreamTokenizer.ttype;
        if (i2 != -3) {
            if (i2 == 42) {
                elementTest = AllElementTest.INSTANCE;
            } else if (i2 != 46) {
                if (i2 != 64) {
                    throw new XPathException(xPath, "at begininning of step", simpleStreamTokenizer, "'.' or '*' or name");
                }
                if (simpleStreamTokenizer.nextToken() != -3) {
                    throw new XPathException(xPath, "after @ in node test", simpleStreamTokenizer, "name");
                }
                elementTest = new AttrTest(simpleStreamTokenizer.sval);
            } else if (simpleStreamTokenizer.nextToken() == 46) {
                elementTest = ParentNodeTest.INSTANCE;
            } else {
                simpleStreamTokenizer.pushBack();
                elementTest = ThisNodeTest.INSTANCE;
            }
        } else if (!simpleStreamTokenizer.sval.equals("text")) {
            elementTest = new ElementTest(simpleStreamTokenizer.sval);
        } else {
            if (simpleStreamTokenizer.nextToken() != 40 || simpleStreamTokenizer.nextToken() != 41) {
                throw new XPathException(xPath, "after text", simpleStreamTokenizer, "()");
            }
            elementTest = TextTest.INSTANCE;
        }
        this.nodeTest_ = elementTest;
        if (simpleStreamTokenizer.nextToken() != 91) {
            this.predicate_ = TrueExpr.INSTANCE;
            return;
        }
        simpleStreamTokenizer.nextToken();
        this.predicate_ = ExprFactory.createExpr(xPath, simpleStreamTokenizer);
        if (simpleStreamTokenizer.ttype != 93) {
            throw new XPathException(xPath, "after predicate expression", simpleStreamTokenizer, StrPool.BRACKET_END);
        }
        simpleStreamTokenizer.nextToken();
    }

    public NodeTest getNodeTest() {
        return this.nodeTest_;
    }

    public BooleanExpr getPredicate() {
        return this.predicate_;
    }

    public boolean isMultiLevel() {
        return this.multiLevel_;
    }

    public boolean isStringValue() {
        return this.nodeTest_.isStringValue();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.nodeTest_.toString());
        stringBuffer.append(this.predicate_.toString());
        return stringBuffer.toString();
    }
}
