package cn.hutool.core.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.NioUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.BiMap;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import kotlin.text.Typography;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes.dex */
public class XmlUtil {
    public static final String AMP = "&amp;";
    public static final String APOS = "&apos;";
    public static final String COMMENT_REGEX = "(?s)<!--.+?-->";
    public static final String GT = "&gt;";
    public static final int INDENT_DEFAULT = 2;
    public static final String INVALID_REGEX = "[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]";
    public static final String LT = "&lt;";
    public static final String NBSP = "&nbsp;";
    public static final String QUOTE = "&quot;";
    private static String defaultDocumentBuilderFactory = "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl";
    private static SAXParserFactory factory = null;
    private static boolean namespaceAware = true;

    public static class UniversalNamespaceCache implements NamespaceContext {
        private static final String DEFAULT_NS = "DEFAULT";
        private final BiMap<String, String> prefixUri = new BiMap<>(new HashMap());

        public UniversalNamespaceCache(Node node, boolean z2) {
            examineNode(node.getFirstChild(), z2);
        }

        private void examineNode(Node node, boolean z2) {
            NodeList childNodes;
            NamedNodeMap attributes = node.getAttributes();
            if (attributes != null) {
                int length = attributes.getLength();
                for (int i2 = 0; i2 < length; i2++) {
                    storeAttribute(attributes.item(i2));
                }
            }
            if (z2 || (childNodes = node.getChildNodes()) == null) {
                return;
            }
            int length2 = childNodes.getLength();
            for (int i3 = 0; i3 < length2; i3++) {
                Node nodeItem = childNodes.item(i3);
                if (nodeItem.getNodeType() == 1) {
                    examineNode(nodeItem, false);
                }
            }
        }

        private void storeAttribute(Node node) {
            if (node != null && "http://www.w3.org/2000/xmlns/".equals(node.getNamespaceURI())) {
                if ("xmlns".equals(node.getNodeName())) {
                    this.prefixUri.put(DEFAULT_NS, node.getNodeValue());
                } else {
                    this.prefixUri.put(node.getLocalName(), node.getNodeValue());
                }
            }
        }

        @Override // javax.xml.namespace.NamespaceContext
        public String getNamespaceURI(String str) {
            return (str == null || "".equals(str)) ? this.prefixUri.get(DEFAULT_NS) : this.prefixUri.get(str);
        }

        @Override // javax.xml.namespace.NamespaceContext
        public String getPrefix(String str) {
            return this.prefixUri.getInverse().get(str);
        }

        @Override // javax.xml.namespace.NamespaceContext
        public Iterator<String> getPrefixes(String str) {
            return null;
        }
    }

    public static void append(Node node, Object obj) throws DOMException {
        append(getOwnerDocument(node), node, obj);
    }

    public static Element appendChild(Node node, String str) {
        return appendChild(node, str, null);
    }

    private static void appendIterator(Document document, Node node, Iterator it) throws DOMException {
        Node parentNode = node.getParentNode();
        boolean z2 = true;
        while (it.hasNext()) {
            Object next = it.next();
            if (z2) {
                append(document, node, next);
                z2 = false;
            } else {
                Node nodeCloneNode = node.cloneNode(false);
                parentNode.appendChild(nodeCloneNode);
                append(document, nodeCloneNode, next);
            }
        }
    }

    private static void appendMap(final Document document, final Node node, Map map) {
        map.forEach(new BiConsumer() { // from class: cn.hutool.core.util.f0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) throws DOMException {
                XmlUtil.lambda$appendMap$0(node, document, obj, obj2);
            }
        });
    }

    public static Node appendText(Node node, CharSequence charSequence) {
        return appendText(getOwnerDocument(node), node, charSequence);
    }

    public static Document beanToXml(Object obj) {
        return beanToXml(obj, null);
    }

    public static String cleanComment(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(COMMENT_REGEX, "");
    }

    public static String cleanInvalid(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(INVALID_REGEX, "");
    }

    public static DocumentBuilder createDocumentBuilder() {
        try {
            return createDocumentBuilderFactory().newDocumentBuilder();
        } catch (Exception e2) {
            throw new UtilException(e2, "Create xml document error!", new Object[0]);
        }
    }

    public static DocumentBuilderFactory createDocumentBuilderFactory() {
        DocumentBuilderFactory documentBuilderFactoryNewInstance = CharSequenceUtil.isNotEmpty(defaultDocumentBuilderFactory) ? DocumentBuilderFactory.newInstance(defaultDocumentBuilderFactory, null) : DocumentBuilderFactory.newInstance();
        documentBuilderFactoryNewInstance.setNamespaceAware(namespaceAware);
        return disableXXE(documentBuilderFactoryNewInstance);
    }

    public static XPath createXPath() {
        return XPathFactory.newInstance().newXPath();
    }

    public static Document createXml() {
        return createDocumentBuilder().newDocument();
    }

    public static synchronized void disableDefaultDocumentBuilderFactory() {
        defaultDocumentBuilderFactory = null;
    }

    private static DocumentBuilderFactory disableXXE(DocumentBuilderFactory documentBuilderFactory) throws ParserConfigurationException {
        try {
            documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            documentBuilderFactory.setXIncludeAware(false);
            documentBuilderFactory.setExpandEntityReferences(false);
        } catch (ParserConfigurationException unused) {
        }
        return documentBuilderFactory;
    }

    public static String elementText(Element element, String str) {
        Element element2 = getElement(element, str);
        if (element2 == null) {
            return null;
        }
        return element2.getTextContent();
    }

    public static String escape(String str) {
        return EscapeUtil.escapeHtml4(str);
    }

    public static String format(Document document) {
        return toStr(document, true);
    }

    public static Object getByXPath(String str, Object obj, QName qName) {
        return getByXPath(str, obj, qName, obj instanceof Node ? new UniversalNamespaceCache((Node) obj, false) : null);
    }

    public static Element getElement(Element element, String str) {
        NodeList elementsByTagName = element.getElementsByTagName(str);
        int length = elementsByTagName.getLength();
        if (length < 1) {
            return null;
        }
        for (int i2 = 0; i2 < length; i2++) {
            Element element2 = (Element) elementsByTagName.item(i2);
            if (element2 == null || element2.getParentNode() == element) {
                return element2;
            }
        }
        return null;
    }

    public static Element getElementByXPath(String str, Object obj) {
        return (Element) getNodeByXPath(str, obj);
    }

    public static List<Element> getElements(Element element, String str) {
        return transElements(element, CharSequenceUtil.isBlank(str) ? element.getChildNodes() : element.getElementsByTagName(str));
    }

    public static Node getNodeByXPath(String str, Object obj) {
        return (Node) getByXPath(str, obj, XPathConstants.NODE);
    }

    public static NodeList getNodeListByXPath(String str, Object obj) {
        return (NodeList) getByXPath(str, obj, XPathConstants.NODESET);
    }

    public static Document getOwnerDocument(Node node) {
        return node instanceof Document ? (Document) node : node.getOwnerDocument();
    }

    public static Element getRootElement(Document document) {
        if (document == null) {
            return null;
        }
        return document.getDocumentElement();
    }

    public static boolean isElement(Node node) {
        return node != null && 1 == node.getNodeType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$appendMap$0(Node node, Document document, Object obj, Object obj2) throws DOMException {
        if (obj != null) {
            Element elementAppendChild = appendChild(node, obj.toString());
            if (obj2 != null) {
                append(document, elementAppendChild, obj2);
            }
        }
    }

    public static Document mapToXml(Map<?, ?> map, String str) {
        return mapToXml(map, str, null);
    }

    public static String mapToXmlStr(Map<?, ?> map) {
        return toStr(mapToXml(map, AliyunVodHttpCommon.Format.FORMAT_XML));
    }

    public static Document parseXml(String str) {
        if (CharSequenceUtil.isBlank(str)) {
            throw new IllegalArgumentException("XML content string is empty !");
        }
        return readXML(StrUtil.getReader(cleanInvalid(str)));
    }

    public static void readBySax(File file, ContentHandler contentHandler) throws Throwable {
        BufferedInputStream inputStream;
        try {
            inputStream = FileUtil.getInputStream(file);
        } catch (Throwable th) {
            th = th;
            inputStream = null;
        }
        try {
            readBySax(new InputSource(inputStream), contentHandler);
            IoUtil.close((Closeable) inputStream);
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) inputStream);
            throw th;
        }
    }

    public static Document readXML(File file) throws Throwable {
        BufferedInputStream inputStream;
        Assert.notNull(file, "Xml file is null !", new Object[0]);
        if (!file.exists()) {
            throw new UtilException("File [{}] not a exist!", file.getAbsolutePath());
        }
        if (!file.isFile()) {
            throw new UtilException("[{}] not a file!", file.getAbsolutePath());
        }
        try {
            file = file.getCanonicalFile();
        } catch (IOException unused) {
        }
        try {
            inputStream = FileUtil.getInputStream(file);
        } catch (Throwable th) {
            th = th;
            inputStream = null;
        }
        try {
            Document xml = readXML(inputStream);
            IoUtil.close((Closeable) inputStream);
            return xml;
        } catch (Throwable th2) {
            th = th2;
            IoUtil.close((Closeable) inputStream);
            throw th;
        }
    }

    public static synchronized void setNamespaceAware(boolean z2) {
        namespaceAware = z2;
    }

    public static void toFile(Document document, String str) throws IOException {
        toFile(document, str, null);
    }

    public static String toStr(Node node) {
        return toStr(node, false);
    }

    public static List<Element> transElements(NodeList nodeList) {
        return transElements(null, nodeList);
    }

    public static void transform(Source source, Result result, String str, int i2) throws TransformerException, IllegalArgumentException {
        transform(source, result, str, i2, false);
    }

    public static String unescape(String str) {
        return EscapeUtil.unescapeHtml4(str);
    }

    public static void write(Node node, Writer writer, String str, int i2) throws TransformerException, IllegalArgumentException {
        transform(new DOMSource(node), new StreamResult(writer), str, i2);
    }

    public static void writeObjectAsXml(File file, Object obj) throws Throwable {
        AutoCloseable xMLEncoder;
        AutoCloseable autoCloseable = null;
        try {
            xMLEncoder = new XMLEncoder(FileUtil.getOutputStream(file));
        } catch (Throwable th) {
            th = th;
        }
        try {
            xMLEncoder.writeObject(obj);
            NioUtil.close(xMLEncoder);
        } catch (Throwable th2) {
            th = th2;
            autoCloseable = xMLEncoder;
            NioUtil.close(autoCloseable);
            throw th;
        }
    }

    public static <T> T xmlToBean(Node node, Class<T> cls) {
        Map<String, Object> mapXmlToMap = xmlToMap(node);
        if (mapXmlToMap != null && mapXmlToMap.size() == 1) {
            String simpleName = cls.getSimpleName();
            String str = (String) CollUtil.getFirst(mapXmlToMap.keySet());
            if (simpleName.equalsIgnoreCase(str)) {
                return (T) BeanUtil.toBean(mapXmlToMap.get(str), cls);
            }
        }
        return (T) BeanUtil.toBean(mapXmlToMap, cls);
    }

    public static Map<String, Object> xmlToMap(String str) {
        return xmlToMap(str, new HashMap());
    }

    private static void append(Document document, Node node, Object obj) throws DOMException {
        if (obj instanceof Map) {
            appendMap(document, node, (Map) obj);
            return;
        }
        if (obj instanceof Iterator) {
            appendIterator(document, node, (Iterator) obj);
        } else if (obj instanceof Iterable) {
            appendIterator(document, node, ((Iterable) obj).iterator());
        } else {
            appendText(document, node, obj.toString());
        }
    }

    public static Element appendChild(Node node, String str, String str2) throws DOMException {
        Document ownerDocument = getOwnerDocument(node);
        Element elementCreateElement = str2 == null ? ownerDocument.createElement(str) : ownerDocument.createElementNS(str2, str);
        node.appendChild(elementCreateElement);
        return elementCreateElement;
    }

    private static Node appendText(Document document, Node node, CharSequence charSequence) {
        return node.appendChild(document.createTextNode(CharSequenceUtil.str(charSequence)));
    }

    public static Document beanToXml(Object obj, String str) {
        return beanToXml(obj, str, false);
    }

    public static Document createXml(String str) {
        return createXml(str, null);
    }

    public static String format(String str) {
        return format(parseXml(str));
    }

    public static Document mapToXml(Map<?, ?> map, String str, String str2) {
        Document documentCreateXml = createXml();
        appendMap(documentCreateXml, appendChild(documentCreateXml, str, str2), map);
        return documentCreateXml;
    }

    public static String mapToXmlStr(Map<?, ?> map, boolean z2) {
        return toStr(mapToXml(map, AliyunVodHttpCommon.Format.FORMAT_XML), "UTF-8", false, z2);
    }

    public static void toFile(Document document, String str, String str2) throws IOException {
        if (CharSequenceUtil.isBlank(str2)) {
            str2 = document.getXmlEncoding();
        }
        if (CharSequenceUtil.isBlank(str2)) {
            str2 = "UTF-8";
        }
        BufferedWriter writer = null;
        try {
            writer = FileUtil.getWriter(str, CharsetUtil.charset(str2), false);
            write(document, writer, str2, 2);
        } finally {
            IoUtil.close((Closeable) writer);
        }
    }

    public static String toStr(Document document) {
        return toStr((Node) document);
    }

    public static List<Element> transElements(Element element, NodeList nodeList) {
        int length = nodeList.getLength();
        ArrayList arrayList = new ArrayList(length);
        for (int i2 = 0; i2 < length; i2++) {
            if (1 == nodeList.item(i2).getNodeType()) {
                Element element2 = (Element) nodeList.item(i2);
                if (element == null || element2.getParentNode() == element) {
                    arrayList.add(element2);
                }
            }
        }
        return arrayList;
    }

    public static void transform(Source source, Result result, String str, int i2, boolean z2) throws TransformerException, IllegalArgumentException {
        try {
            Transformer transformerNewTransformer = TransformerFactory.newInstance().newTransformer();
            if (i2 > 0) {
                transformerNewTransformer.setOutputProperty("indent", "yes");
                transformerNewTransformer.setOutputProperty("doctype-public", "yes");
                transformerNewTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(i2));
            }
            if (CharSequenceUtil.isNotBlank(str)) {
                transformerNewTransformer.setOutputProperty("encoding", str);
            }
            if (z2) {
                transformerNewTransformer.setOutputProperty("omit-xml-declaration", "yes");
            }
            transformerNewTransformer.transform(source, result);
        } catch (Exception e2) {
            throw new UtilException(e2, "Trans xml document to string error!", new Object[0]);
        }
    }

    public static void write(Node node, Writer writer, String str, int i2, boolean z2) throws TransformerException, IllegalArgumentException {
        transform(new DOMSource(node), new StreamResult(writer), str, i2, z2);
    }

    public static Map<String, Object> xmlToMap(Node node) {
        return xmlToMap(node, new HashMap());
    }

    public static Document beanToXml(Object obj, String str, boolean z2) {
        if (obj == null) {
            return null;
        }
        return mapToXml(BeanUtil.beanToMap(obj, false, z2), obj.getClass().getSimpleName(), str);
    }

    public static Document createXml(String str, String str2) throws DOMException {
        Document documentCreateXml = createXml();
        documentCreateXml.appendChild(str2 == null ? documentCreateXml.createElement(str) : documentCreateXml.createElementNS(str2, str));
        return documentCreateXml;
    }

    public static String elementText(Element element, String str, String str2) {
        Element element2 = getElement(element, str);
        return element2 == null ? str2 : element2.getTextContent();
    }

    public static String mapToXmlStr(Map<?, ?> map, String str) {
        return toStr(mapToXml(map, str));
    }

    public static String toStr(Node node, boolean z2) {
        return toStr(node, "UTF-8", z2);
    }

    public static void write(Node node, OutputStream outputStream, String str, int i2) throws TransformerException, IllegalArgumentException {
        transform(new DOMSource(node), new StreamResult(outputStream), str, i2);
    }

    public static Map<String, Object> xmlToMap(String str, Map<String, Object> map) {
        Element rootElement = getRootElement(parseXml(str));
        rootElement.normalize();
        return xmlToMap(rootElement, map);
    }

    public static Object getByXPath(String str, Object obj, QName qName, NamespaceContext namespaceContext) {
        XPath xPathCreateXPath = createXPath();
        if (namespaceContext != null) {
            xPathCreateXPath.setNamespaceContext(namespaceContext);
        }
        try {
            if (obj instanceof InputSource) {
                return xPathCreateXPath.evaluate(str, (InputSource) obj, qName);
            }
            return xPathCreateXPath.evaluate(str, obj, qName);
        } catch (XPathExpressionException e2) {
            throw new UtilException(e2);
        }
    }

    public static String mapToXmlStr(Map<?, ?> map, String str, String str2) {
        return toStr(mapToXml(map, str, str2));
    }

    public static String toStr(Document document, boolean z2) {
        return toStr((Node) document, z2);
    }

    public static void write(Node node, OutputStream outputStream, String str, int i2, boolean z2) throws TransformerException, IllegalArgumentException {
        transform(new DOMSource(node), new StreamResult(outputStream), str, i2, z2);
    }

    public static String mapToXmlStr(Map<?, ?> map, String str, String str2, boolean z2) {
        return toStr(mapToXml(map, str, str2), "UTF-8", false, z2);
    }

    public static void readBySax(Reader reader, ContentHandler contentHandler) throws IOException {
        try {
            readBySax(new InputSource(reader), contentHandler);
        } finally {
            IoUtil.close((Closeable) reader);
        }
    }

    public static String toStr(Node node, String str, boolean z2) {
        return toStr(node, str, z2, false);
    }

    public static String mapToXmlStr(Map<?, ?> map, String str, String str2, boolean z2, boolean z3) {
        return toStr(mapToXml(map, str, str2), "UTF-8", z2, z3);
    }

    public static String toStr(Document document, String str, boolean z2) {
        return toStr((Node) document, str, z2);
    }

    public static String mapToXmlStr(Map<?, ?> map, String str, String str2, String str3, boolean z2, boolean z3) {
        return toStr(mapToXml(map, str, str2), str3, z2, z3);
    }

    public static String toStr(Node node, String str, boolean z2, boolean z3) {
        StringWriter writer = StrUtil.getWriter();
        try {
            write(node, writer, str, z2 ? 2 : 0, z3);
            return writer.toString();
        } catch (Exception e2) {
            throw new UtilException(e2, "Trans xml document to string error!", new Object[0]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.util.Map] */
    public static Map<String, Object> xmlToMap(Node node, Map<String, Object> map) throws DOMException {
        String textContent;
        Map<String, Object> map2 = map;
        if (map == null) {
            map2 = new HashMap();
        }
        NodeList childNodes = node.getChildNodes();
        int length = childNodes.getLength();
        for (int i2 = 0; i2 < length; i2++) {
            Node nodeItem = childNodes.item(i2);
            if (isElement(nodeItem)) {
                Element element = (Element) nodeItem;
                Object obj = map2.get(element.getNodeName());
                if (element.hasChildNodes()) {
                    ?? XmlToMap = xmlToMap(element);
                    boolean zIsNotEmpty = MapUtil.isNotEmpty(XmlToMap);
                    textContent = XmlToMap;
                    if (!zIsNotEmpty) {
                        textContent = element.getTextContent();
                    }
                } else {
                    textContent = element.getTextContent();
                }
                if (textContent != null) {
                    if (obj != null) {
                        if (obj instanceof List) {
                            ((List) obj).add(textContent);
                        } else {
                            map2.put(element.getNodeName(), CollUtil.newArrayList(obj, textContent));
                        }
                    } else {
                        map2.put(element.getNodeName(), textContent);
                    }
                }
            }
        }
        return map2;
    }

    public static void readBySax(InputStream inputStream, ContentHandler contentHandler) throws IOException {
        try {
            readBySax(new InputSource(inputStream), contentHandler);
        } finally {
            IoUtil.close((Closeable) inputStream);
        }
    }

    public static void readBySax(InputSource inputSource, ContentHandler contentHandler) throws ParserConfigurationException, SAXException, IOException {
        if (factory == null) {
            SAXParserFactory sAXParserFactoryNewInstance = SAXParserFactory.newInstance();
            factory = sAXParserFactoryNewInstance;
            sAXParserFactoryNewInstance.setValidating(false);
            factory.setNamespaceAware(namespaceAware);
            try {
                factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
                factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
                factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
                factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            } catch (Exception unused) {
            }
        }
        try {
            SAXParser sAXParserNewSAXParser = factory.newSAXParser();
            if (contentHandler instanceof DefaultHandler) {
                sAXParserNewSAXParser.parse(inputSource, (DefaultHandler) contentHandler);
                return;
            }
            XMLReader xMLReader = sAXParserNewSAXParser.getXMLReader();
            xMLReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            xMLReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            xMLReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            xMLReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            xMLReader.setContentHandler(contentHandler);
            xMLReader.parse(inputSource);
        } catch (IOException e2) {
            throw new IORuntimeException(e2);
        } catch (ParserConfigurationException e3) {
            e = e3;
            throw new UtilException(e);
        } catch (SAXException e4) {
            e = e4;
            throw new UtilException(e);
        }
    }

    public static Document readXML(String str) {
        if (CharSequenceUtil.startWith(str, Typography.less)) {
            return parseXml(str);
        }
        return readXML(FileUtil.file(str));
    }

    public static Document readXML(InputStream inputStream) throws UtilException {
        return readXML(new InputSource(inputStream));
    }

    public static Document readXML(Reader reader) throws UtilException {
        return readXML(new InputSource(reader));
    }

    public static Document readXML(InputSource inputSource) {
        try {
            return createDocumentBuilder().parse(inputSource);
        } catch (Exception e2) {
            throw new UtilException(e2, "Parse XML from stream error!", new Object[0]);
        }
    }
}
