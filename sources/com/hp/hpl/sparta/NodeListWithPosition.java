package com.hp.hpl.sparta;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes4.dex */
class NodeListWithPosition {
    private static final Integer ONE = new Integer(1);
    private static final Integer TWO = new Integer(2);
    private static final Integer THREE = new Integer(3);
    private static final Integer FOUR = new Integer(4);
    private static final Integer FIVE = new Integer(5);
    private static final Integer SIX = new Integer(6);
    private static final Integer SEVEN = new Integer(7);
    private static final Integer EIGHT = new Integer(8);
    private static final Integer NINE = new Integer(9);
    private static final Integer TEN = new Integer(10);
    private final Vector vector_ = new Vector();
    private Hashtable positions_ = new Hashtable();

    private static Integer identity(Node node) {
        return new Integer(System.identityHashCode(node));
    }

    public void add(Node node, int i2) {
        Integer num;
        this.vector_.addElement(node);
        switch (i2) {
            case 1:
                num = ONE;
                break;
            case 2:
                num = TWO;
                break;
            case 3:
                num = THREE;
                break;
            case 4:
                num = FOUR;
                break;
            case 5:
                num = FIVE;
                break;
            case 6:
                num = SIX;
                break;
            case 7:
                num = SEVEN;
                break;
            case 8:
                num = EIGHT;
                break;
            case 9:
                num = NINE;
                break;
            case 10:
                num = TEN;
                break;
            default:
                num = new Integer(i2);
                break;
        }
        this.positions_.put(identity(node), num);
    }

    public void add(String str) {
        this.vector_.addElement(str);
    }

    public Enumeration iterator() {
        return this.vector_.elements();
    }

    public int position(Node node) {
        return ((Integer) this.positions_.get(identity(node))).intValue();
    }

    public void removeAllElements() {
        this.vector_.removeAllElements();
        this.positions_.clear();
    }

    public String toString() {
        String string;
        try {
            StringBuffer stringBuffer = new StringBuffer("{ ");
            Enumeration enumerationElements = this.vector_.elements();
            while (enumerationElements.hasMoreElements()) {
                Object objNextElement = enumerationElements.nextElement();
                if (objNextElement instanceof String) {
                    StringBuffer stringBuffer2 = new StringBuffer();
                    stringBuffer2.append("String(");
                    stringBuffer2.append(objNextElement);
                    stringBuffer2.append(") ");
                    string = stringBuffer2.toString();
                } else {
                    Node node = (Node) objNextElement;
                    StringBuffer stringBuffer3 = new StringBuffer();
                    stringBuffer3.append("Node(");
                    stringBuffer3.append(node.toXml());
                    stringBuffer3.append(")[");
                    stringBuffer3.append(this.positions_.get(identity(node)));
                    stringBuffer3.append("] ");
                    string = stringBuffer3.toString();
                }
                stringBuffer.append(string);
            }
            stringBuffer.append("}");
            return stringBuffer.toString();
        } catch (IOException e2) {
            return e2.toString();
        }
    }
}
