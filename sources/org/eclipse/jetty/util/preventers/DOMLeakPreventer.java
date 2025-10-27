package org.eclipse.jetty.util.preventers;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/* loaded from: classes9.dex */
public class DOMLeakPreventer extends AbstractLeakPreventer {
    @Override // org.eclipse.jetty.util.preventers.AbstractLeakPreventer
    public void prevent(ClassLoader classLoader) throws ParserConfigurationException {
        try {
            DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (Exception e2) {
            AbstractLeakPreventer.LOG.warn(e2);
        }
    }
}
