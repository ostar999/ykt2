package com.hp.hpl.sparta.xpath;

import java.io.IOException;

/* loaded from: classes4.dex */
public class ExprFactory {
    public static BooleanExpr createExpr(XPath xPath, SimpleStreamTokenizer simpleStreamTokenizer) throws XPathException, IOException, NumberFormatException {
        int i2;
        int i3;
        int i4 = simpleStreamTokenizer.ttype;
        if (i4 == -3) {
            if (!simpleStreamTokenizer.sval.equals("text")) {
                throw new XPathException(xPath, "at beginning of expression", simpleStreamTokenizer, "text()");
            }
            if (simpleStreamTokenizer.nextToken() != 40) {
                throw new XPathException(xPath, "after text", simpleStreamTokenizer, "(");
            }
            if (simpleStreamTokenizer.nextToken() != 41) {
                throw new XPathException(xPath, "after text(", simpleStreamTokenizer, ")");
            }
            int iNextToken = simpleStreamTokenizer.nextToken();
            if (iNextToken != 33) {
                if (iNextToken != 61) {
                    return TextExistsExpr.INSTANCE;
                }
                simpleStreamTokenizer.nextToken();
                int i5 = simpleStreamTokenizer.ttype;
                if (i5 != 34 && i5 != 39) {
                    throw new XPathException(xPath, "right hand side of equals", simpleStreamTokenizer, "quoted string");
                }
                String str = simpleStreamTokenizer.sval;
                simpleStreamTokenizer.nextToken();
                return new TextEqualsExpr(str);
            }
            simpleStreamTokenizer.nextToken();
            if (simpleStreamTokenizer.ttype != 61) {
                throw new XPathException(xPath, "after !", simpleStreamTokenizer, "=");
            }
            simpleStreamTokenizer.nextToken();
            int i6 = simpleStreamTokenizer.ttype;
            if (i6 != 34 && i6 != 39) {
                throw new XPathException(xPath, "right hand side of !=", simpleStreamTokenizer, "quoted string");
            }
            String str2 = simpleStreamTokenizer.sval;
            simpleStreamTokenizer.nextToken();
            return new TextNotEqualsExpr(str2);
        }
        if (i4 == -2) {
            int i7 = simpleStreamTokenizer.nval;
            simpleStreamTokenizer.nextToken();
            return new PositionEqualsExpr(i7);
        }
        if (i4 != 64) {
            throw new XPathException(xPath, "at beginning of expression", simpleStreamTokenizer, "@, number, or text()");
        }
        if (simpleStreamTokenizer.nextToken() != -3) {
            throw new XPathException(xPath, "after @", simpleStreamTokenizer, "name");
        }
        String str3 = simpleStreamTokenizer.sval;
        int iNextToken2 = simpleStreamTokenizer.nextToken();
        if (iNextToken2 == 33) {
            simpleStreamTokenizer.nextToken();
            if (simpleStreamTokenizer.ttype != 61) {
                throw new XPathException(xPath, "after !", simpleStreamTokenizer, "=");
            }
            simpleStreamTokenizer.nextToken();
            int i8 = simpleStreamTokenizer.ttype;
            if (i8 != 34 && i8 != 39) {
                throw new XPathException(xPath, "right hand side of !=", simpleStreamTokenizer, "quoted string");
            }
            String str4 = simpleStreamTokenizer.sval;
            simpleStreamTokenizer.nextToken();
            return new AttrNotEqualsExpr(str3, str4);
        }
        switch (iNextToken2) {
            case 60:
                simpleStreamTokenizer.nextToken();
                int i9 = simpleStreamTokenizer.ttype;
                if (i9 == 34 || i9 == 39) {
                    i2 = Integer.parseInt(simpleStreamTokenizer.sval);
                } else {
                    if (i9 != -2) {
                        throw new XPathException(xPath, "right hand side of less-than", simpleStreamTokenizer, "quoted string or number");
                    }
                    i2 = simpleStreamTokenizer.nval;
                }
                simpleStreamTokenizer.nextToken();
                return new AttrLessExpr(str3, i2);
            case 61:
                simpleStreamTokenizer.nextToken();
                int i10 = simpleStreamTokenizer.ttype;
                if (i10 != 34 && i10 != 39) {
                    throw new XPathException(xPath, "right hand side of equals", simpleStreamTokenizer, "quoted string");
                }
                String str5 = simpleStreamTokenizer.sval;
                simpleStreamTokenizer.nextToken();
                return new AttrEqualsExpr(str3, str5);
            case 62:
                simpleStreamTokenizer.nextToken();
                int i11 = simpleStreamTokenizer.ttype;
                if (i11 == 34 || i11 == 39) {
                    i3 = Integer.parseInt(simpleStreamTokenizer.sval);
                } else {
                    if (i11 != -2) {
                        throw new XPathException(xPath, "right hand side of greater-than", simpleStreamTokenizer, "quoted string or number");
                    }
                    i3 = simpleStreamTokenizer.nval;
                }
                simpleStreamTokenizer.nextToken();
                return new AttrGreaterExpr(str3, i3);
            default:
                return new AttrExistsExpr(str3);
        }
    }
}
