package org.jsoup.safety;

import com.caverock.androidsvg.SVGParser;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.meizu.cloud.pushsdk.notification.model.TimeDisplaySetting;
import com.tencent.connect.common.Constants;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Element;

/* loaded from: classes9.dex */
public class Whitelist {
    private Set<TagName> tagNames = new HashSet();
    private Map<TagName, Set<AttributeKey>> attributes = new HashMap();
    private Map<TagName, Map<AttributeKey, AttributeValue>> enforcedAttributes = new HashMap();
    private Map<TagName, Map<AttributeKey, Set<Protocol>>> protocols = new HashMap();
    private boolean preserveRelativeLinks = false;

    public static class AttributeKey extends TypedValue {
        public AttributeKey(String str) {
            super(str);
        }

        public static AttributeKey valueOf(String str) {
            return new AttributeKey(str);
        }
    }

    public static class AttributeValue extends TypedValue {
        public AttributeValue(String str) {
            super(str);
        }

        public static AttributeValue valueOf(String str) {
            return new AttributeValue(str);
        }
    }

    public static class Protocol extends TypedValue {
        public Protocol(String str) {
            super(str);
        }

        public static Protocol valueOf(String str) {
            return new Protocol(str);
        }
    }

    public static class TagName extends TypedValue {
        public TagName(String str) {
            super(str);
        }

        public static TagName valueOf(String str) {
            return new TagName(str);
        }
    }

    public static abstract class TypedValue {
        private String value;

        public TypedValue(String str) {
            Validate.notNull(str);
            this.value = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TypedValue typedValue = (TypedValue) obj;
            String str = this.value;
            if (str == null) {
                if (typedValue.value != null) {
                    return false;
                }
            } else if (!str.equals(typedValue.value)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            String str = this.value;
            return 31 + (str == null ? 0 : str.hashCode());
        }

        public String toString() {
            return this.value;
        }
    }

    public static Whitelist basic() {
        return new Whitelist().addTags(am.av, "b", "blockquote", "br", "cite", "code", "dd", "dl", SocializeProtocolConstants.PROTOCOL_KEY_DT, "em", am.aC, AppIconSetting.LARGE_ICON_URL, "ol", "p", "pre", "q", "small", TtmlNode.TAG_SPAN, "strike", "strong", "sub", "sup", am.aG, "ul").addAttributes(am.av, SVGParser.XML_STYLESHEET_ATTR_HREF).addAttributes("blockquote", "cite").addAttributes("q", "cite").addProtocols(am.av, SVGParser.XML_STYLESHEET_ATTR_HREF, "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addEnforcedAttribute(am.av, "rel", "nofollow");
    }

    public static Whitelist basicWithImages() {
        return basic().addTags("img").addAttributes("img", "align", "alt", "height", "src", "title", "width").addProtocols("img", "src", "http", "https");
    }

    public static Whitelist none() {
        return new Whitelist();
    }

    public static Whitelist relaxed() {
        return new Whitelist().addTags(am.av, "b", "blockquote", "br", "caption", "cite", "code", "col", "colgroup", "dd", TtmlNode.TAG_DIV, "dl", SocializeProtocolConstants.PROTOCOL_KEY_DT, "em", "h1", "h2", "h3", "h4", "h5", "h6", am.aC, "img", AppIconSetting.LARGE_ICON_URL, "ol", "p", "pre", "q", "small", TtmlNode.TAG_SPAN, "strike", "strong", "sub", "sup", "table", "tbody", TimeDisplaySetting.TIME_DISPLAY, "tfoot", "th", "thead", "tr", am.aG, "ul").addAttributes(am.av, SVGParser.XML_STYLESHEET_ATTR_HREF, "title").addAttributes("blockquote", "cite").addAttributes("col", TtmlNode.TAG_SPAN, "width").addAttributes("colgroup", TtmlNode.TAG_SPAN, "width").addAttributes("img", "align", "alt", "height", "src", "title", "width").addAttributes("ol", "start", "type").addAttributes("q", "cite").addAttributes("table", "summary", "width").addAttributes(TimeDisplaySetting.TIME_DISPLAY, "abbr", "axis", "colspan", "rowspan", "width").addAttributes("th", "abbr", "axis", "colspan", "rowspan", Constants.PARAM_SCOPE, "width").addAttributes("ul", "type").addProtocols(am.av, SVGParser.XML_STYLESHEET_ATTR_HREF, "ftp", "http", "https", "mailto").addProtocols("blockquote", "cite", "http", "https").addProtocols("cite", "cite", "http", "https").addProtocols("img", "src", "http", "https").addProtocols("q", "cite", "http", "https");
    }

    public static Whitelist simpleText() {
        return new Whitelist().addTags("b", "em", am.aC, "strong", am.aG);
    }

    private boolean testValidProtocol(Element element, Attribute attribute, Set<Protocol> set) {
        String strAbsUrl = element.absUrl(attribute.getKey());
        if (strAbsUrl.length() == 0) {
            strAbsUrl = attribute.getValue();
        }
        if (!this.preserveRelativeLinks) {
            attribute.setValue(strAbsUrl);
        }
        Iterator<Protocol> it = set.iterator();
        while (it.hasNext()) {
            if (strAbsUrl.toLowerCase().startsWith(it.next().toString() + ":")) {
                return true;
            }
        }
        return false;
    }

    public Whitelist addAttributes(String str, String... strArr) {
        Validate.notEmpty(str);
        Validate.notNull(strArr);
        Validate.isTrue(strArr.length > 0, "No attributes supplied.");
        TagName tagNameValueOf = TagName.valueOf(str);
        if (!this.tagNames.contains(tagNameValueOf)) {
            this.tagNames.add(tagNameValueOf);
        }
        HashSet hashSet = new HashSet();
        for (String str2 : strArr) {
            Validate.notEmpty(str2);
            hashSet.add(AttributeKey.valueOf(str2));
        }
        if (this.attributes.containsKey(tagNameValueOf)) {
            this.attributes.get(tagNameValueOf).addAll(hashSet);
        } else {
            this.attributes.put(tagNameValueOf, hashSet);
        }
        return this;
    }

    public Whitelist addEnforcedAttribute(String str, String str2, String str3) {
        Validate.notEmpty(str);
        Validate.notEmpty(str2);
        Validate.notEmpty(str3);
        TagName tagNameValueOf = TagName.valueOf(str);
        if (!this.tagNames.contains(tagNameValueOf)) {
            this.tagNames.add(tagNameValueOf);
        }
        AttributeKey attributeKeyValueOf = AttributeKey.valueOf(str2);
        AttributeValue attributeValueValueOf = AttributeValue.valueOf(str3);
        if (this.enforcedAttributes.containsKey(tagNameValueOf)) {
            this.enforcedAttributes.get(tagNameValueOf).put(attributeKeyValueOf, attributeValueValueOf);
        } else {
            HashMap map = new HashMap();
            map.put(attributeKeyValueOf, attributeValueValueOf);
            this.enforcedAttributes.put(tagNameValueOf, map);
        }
        return this;
    }

    public Whitelist addProtocols(String str, String str2, String... strArr) {
        Map<AttributeKey, Set<Protocol>> map;
        Set<Protocol> set;
        Validate.notEmpty(str);
        Validate.notEmpty(str2);
        Validate.notNull(strArr);
        TagName tagNameValueOf = TagName.valueOf(str);
        AttributeKey attributeKeyValueOf = AttributeKey.valueOf(str2);
        if (this.protocols.containsKey(tagNameValueOf)) {
            map = this.protocols.get(tagNameValueOf);
        } else {
            HashMap map2 = new HashMap();
            this.protocols.put(tagNameValueOf, map2);
            map = map2;
        }
        if (map.containsKey(attributeKeyValueOf)) {
            set = map.get(attributeKeyValueOf);
        } else {
            HashSet hashSet = new HashSet();
            map.put(attributeKeyValueOf, hashSet);
            set = hashSet;
        }
        for (String str3 : strArr) {
            Validate.notEmpty(str3);
            set.add(Protocol.valueOf(str3));
        }
        return this;
    }

    public Whitelist addTags(String... strArr) {
        Validate.notNull(strArr);
        for (String str : strArr) {
            Validate.notEmpty(str);
            this.tagNames.add(TagName.valueOf(str));
        }
        return this;
    }

    public Attributes getEnforcedAttributes(String str) {
        Attributes attributes = new Attributes();
        TagName tagNameValueOf = TagName.valueOf(str);
        if (this.enforcedAttributes.containsKey(tagNameValueOf)) {
            for (Map.Entry<AttributeKey, AttributeValue> entry : this.enforcedAttributes.get(tagNameValueOf).entrySet()) {
                attributes.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return attributes;
    }

    public boolean isSafeAttribute(String str, Element element, Attribute attribute) {
        TagName tagNameValueOf = TagName.valueOf(str);
        AttributeKey attributeKeyValueOf = AttributeKey.valueOf(attribute.getKey());
        if (!this.attributes.containsKey(tagNameValueOf) || !this.attributes.get(tagNameValueOf).contains(attributeKeyValueOf)) {
            return !str.equals(":all") && isSafeAttribute(":all", element, attribute);
        }
        if (!this.protocols.containsKey(tagNameValueOf)) {
            return true;
        }
        Map<AttributeKey, Set<Protocol>> map = this.protocols.get(tagNameValueOf);
        return !map.containsKey(attributeKeyValueOf) || testValidProtocol(element, attribute, map.get(attributeKeyValueOf));
    }

    public boolean isSafeTag(String str) {
        return this.tagNames.contains(TagName.valueOf(str));
    }

    public Whitelist preserveRelativeLinks(boolean z2) {
        this.preserveRelativeLinks = z2;
        return this;
    }
}
