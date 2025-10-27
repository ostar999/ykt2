package com.hp.hpl.sparta;

/* loaded from: classes4.dex */
public interface ParseHandler {
    void characters(char[] cArr, int i2, int i3) throws ParseException;

    void endDocument() throws ParseException;

    void endElement(Element element) throws ParseException;

    ParseSource getParseSource();

    void setParseSource(ParseSource parseSource);

    void startDocument() throws ParseException;

    void startElement(Element element) throws ParseException;
}
