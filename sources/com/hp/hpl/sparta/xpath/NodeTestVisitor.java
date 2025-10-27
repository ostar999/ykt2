package com.hp.hpl.sparta.xpath;

/* loaded from: classes4.dex */
public interface NodeTestVisitor {
    void visit(AllElementTest allElementTest);

    void visit(AttrTest attrTest);

    void visit(ElementTest elementTest);

    void visit(ParentNodeTest parentNodeTest) throws XPathException;

    void visit(TextTest textTest);

    void visit(ThisNodeTest thisNodeTest);
}
