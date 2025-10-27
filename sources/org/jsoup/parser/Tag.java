package org.jsoup.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.alipay.sdk.cons.c;
import com.alipay.sdk.packet.d;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.heytap.mcssdk.constant.b;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import com.meizu.cloud.pushsdk.notification.model.AppIconSetting;
import com.meizu.cloud.pushsdk.notification.model.TimeDisplaySetting;
import com.plv.foundationsdk.log.elog.logcode.linkmic.PLVErrorCodeLinkMicBase;
import com.plv.livescenes.model.PLVLiveClassDetailVO;
import com.psychiatrygarden.utils.Constants;
import com.tencent.open.SocialConstants;
import com.umeng.analytics.pro.am;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.i18n.ErrorBundle;
import org.jsoup.helper.Validate;

/* loaded from: classes9.dex */
public class Tag {
    private static final String[] blockTags;
    private static final String[] emptyTags;
    private static final String[] formListedTags;
    private static final String[] formSubmitTags;
    private static final String[] formatAsInlineTags;
    private static final String[] inlineTags;
    private static final String[] preserveWhitespaceTags;
    private static final Map<String, Tag> tags = new HashMap();
    private String tagName;
    private boolean isBlock = true;
    private boolean formatAsBlock = true;
    private boolean canContainBlock = true;
    private boolean canContainInline = true;
    private boolean empty = false;
    private boolean selfClosing = false;
    private boolean preserveWhitespace = false;
    private boolean formList = false;
    private boolean formSubmit = false;

    static {
        String[] strArr = {"html", TtmlNode.TAG_HEAD, TtmlNode.TAG_BODY, "frameset", "script", "noscript", TtmlNode.TAG_STYLE, "meta", PLVErrorCodeLinkMicBase.LINK_MODULE, "title", TypedValues.AttributesType.S_FRAME, "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol", "pre", TtmlNode.TAG_DIV, "blockquote", "hr", "address", "figure", "figcaption", c.f3228c, "fieldset", "ins", "del", "s", "dl", SocializeProtocolConstants.PROTOCOL_KEY_DT, "dd", AppIconSetting.LARGE_ICON_URL, "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th", TimeDisplaySetting.TIME_DISPLAY, "video", "audio", "canvas", ErrorBundle.DETAIL_ENTRY, "menu", "plaintext"};
        blockTags = strArr;
        inlineTags = new String[]{"object", TtmlNode.RUBY_BASE, "font", "tt", am.aC, "b", am.aG, "big", "small", "em", "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", CrashHianalyticsData.TIME, "acronym", "mark", TtmlNode.ATTR_TTS_RUBY, "rt", AliyunLogKey.KEY_RESOURCE_PATH, am.av, "img", "br", "wbr", "map", "q", "sub", "sup", "bdo", PLVLiveClassDetailVO.MENUTYPE_IFRAME, "embed", TtmlNode.TAG_SPAN, "input", "select", "textarea", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, "button", "optgroup", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_OPTION, "legend", "datalist", "keygen", "output", "progress", "meter", "area", "param", SocialConstants.PARAM_SOURCE, "track", "summary", b.f7200y, d.f3298n, "area", "basefont", "bgsound", "menuitem", "param", SocialConstants.PARAM_SOURCE, "track"};
        emptyTags = new String[]{"meta", PLVErrorCodeLinkMicBase.LINK_MODULE, TtmlNode.RUBY_BASE, TypedValues.AttributesType.S_FRAME, "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", b.f7200y, d.f3298n, "area", "basefont", "bgsound", "menuitem", "param", SocialConstants.PARAM_SOURCE, "track"};
        formatAsInlineTags = new String[]{"title", am.av, "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", AppIconSetting.LARGE_ICON_URL, "th", TimeDisplaySetting.TIME_DISPLAY, "script", TtmlNode.TAG_STYLE, "ins", "del", "s"};
        preserveWhitespaceTags = new String[]{"pre", "plaintext", "title", "textarea"};
        formListedTags = new String[]{"button", "fieldset", "input", "keygen", "object", "output", "select", "textarea"};
        formSubmitTags = new String[]{"input", "keygen", "object", "select", "textarea"};
        for (String str : strArr) {
            register(new Tag(str));
        }
        for (String str2 : inlineTags) {
            Tag tag = new Tag(str2);
            tag.isBlock = false;
            tag.canContainBlock = false;
            tag.formatAsBlock = false;
            register(tag);
        }
        for (String str3 : emptyTags) {
            Tag tag2 = tags.get(str3);
            Validate.notNull(tag2);
            tag2.canContainBlock = false;
            tag2.canContainInline = false;
            tag2.empty = true;
        }
        for (String str4 : formatAsInlineTags) {
            Tag tag3 = tags.get(str4);
            Validate.notNull(tag3);
            tag3.formatAsBlock = false;
        }
        for (String str5 : preserveWhitespaceTags) {
            Tag tag4 = tags.get(str5);
            Validate.notNull(tag4);
            tag4.preserveWhitespace = true;
        }
        for (String str6 : formListedTags) {
            Tag tag5 = tags.get(str6);
            Validate.notNull(tag5);
            tag5.formList = true;
        }
        for (String str7 : formSubmitTags) {
            Tag tag6 = tags.get(str7);
            Validate.notNull(tag6);
            tag6.formSubmit = true;
        }
    }

    private Tag(String str) {
        this.tagName = str.toLowerCase();
    }

    private static void register(Tag tag) {
        tags.put(tag.tagName, tag);
    }

    public static Tag valueOf(String str) {
        Validate.notNull(str);
        Map<String, Tag> map = tags;
        Tag tag = map.get(str);
        if (tag != null) {
            return tag;
        }
        String lowerCase = str.trim().toLowerCase();
        Validate.notEmpty(lowerCase);
        Tag tag2 = map.get(lowerCase);
        if (tag2 != null) {
            return tag2;
        }
        Tag tag3 = new Tag(lowerCase);
        tag3.isBlock = false;
        tag3.canContainBlock = true;
        return tag3;
    }

    public boolean canContainBlock() {
        return this.canContainBlock;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) obj;
        return this.canContainBlock == tag.canContainBlock && this.canContainInline == tag.canContainInline && this.empty == tag.empty && this.formatAsBlock == tag.formatAsBlock && this.isBlock == tag.isBlock && this.preserveWhitespace == tag.preserveWhitespace && this.selfClosing == tag.selfClosing && this.formList == tag.formList && this.formSubmit == tag.formSubmit && this.tagName.equals(tag.tagName);
    }

    public boolean formatAsBlock() {
        return this.formatAsBlock;
    }

    public String getName() {
        return this.tagName;
    }

    public int hashCode() {
        return (((((((((((((((((this.tagName.hashCode() * 31) + (this.isBlock ? 1 : 0)) * 31) + (this.formatAsBlock ? 1 : 0)) * 31) + (this.canContainBlock ? 1 : 0)) * 31) + (this.canContainInline ? 1 : 0)) * 31) + (this.empty ? 1 : 0)) * 31) + (this.selfClosing ? 1 : 0)) * 31) + (this.preserveWhitespace ? 1 : 0)) * 31) + (this.formList ? 1 : 0)) * 31) + (this.formSubmit ? 1 : 0);
    }

    public boolean isBlock() {
        return this.isBlock;
    }

    public boolean isData() {
        return (this.canContainInline || isEmpty()) ? false : true;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isFormListed() {
        return this.formList;
    }

    public boolean isFormSubmittable() {
        return this.formSubmit;
    }

    public boolean isInline() {
        return !this.isBlock;
    }

    public boolean isKnownTag() {
        return tags.containsKey(this.tagName);
    }

    public boolean isSelfClosing() {
        return this.empty || this.selfClosing;
    }

    public boolean preserveWhitespace() {
        return this.preserveWhitespace;
    }

    public Tag setSelfClosing() {
        this.selfClosing = true;
        return this;
    }

    public String toString() {
        return this.tagName;
    }

    public static boolean isKnownTag(String str) {
        return tags.containsKey(str);
    }
}
