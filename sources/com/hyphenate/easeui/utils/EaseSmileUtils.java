package com.hyphenate.easeui.utils;

import android.content.Context;
import android.text.Spannable;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.provider.EaseEmojiconInfoProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class EaseSmileUtils {
    public static final String DELETE_KEY = "em_delete_delete_expression";
    public static final String ee_1 = "[):]";
    public static final String ee_10 = "[:(]";
    public static final String ee_11 = "[:'(]";
    public static final String ee_12 = "[:|]";
    public static final String ee_13 = "[(a)]";
    public static final String ee_14 = "[8o|]";
    public static final String ee_15 = "[8-|]";
    public static final String ee_16 = "[+o(]";
    public static final String ee_17 = "[<o)]";
    public static final String ee_18 = "[|-)]";
    public static final String ee_19 = "[*-)]";
    public static final String ee_2 = "[:D]";
    public static final String ee_20 = "[:-#]";
    public static final String ee_21 = "[:-*]";
    public static final String ee_22 = "[^o)]";
    public static final String ee_23 = "[8-)]";
    public static final String ee_24 = "[(|)]";
    public static final String ee_25 = "[(u)]";
    public static final String ee_26 = "[(S)]";
    public static final String ee_27 = "[(*)]";
    public static final String ee_28 = "[(#)]";
    public static final String ee_29 = "[(R)]";
    public static final String ee_3 = "[;)]";
    public static final String ee_30 = "[({)]";
    public static final String ee_31 = "[(})]";
    public static final String ee_32 = "[(k)]";
    public static final String ee_33 = "[(F)]";
    public static final String ee_34 = "[(W)]";
    public static final String ee_35 = "[(D)]";
    public static final String ee_4 = "[:-o]";
    public static final String ee_5 = "[:p]";
    public static final String ee_6 = "[(H)]";
    public static final String ee_7 = "[:@]";
    public static final String ee_8 = "[:s]";
    public static final String ee_9 = "[:$]";
    private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();
    private static final Map<Pattern, Object> emoticons = new HashMap();

    static {
        for (EaseEmojicon easeEmojicon : EaseDefaultEmojiconDatas.getData()) {
            addPattern(easeEmojicon.getEmojiText(), Integer.valueOf(easeEmojicon.getIcon()));
        }
        EaseEmojiconInfoProvider emojiconInfoProvider = EaseIM.getInstance().getEmojiconInfoProvider();
        if (emojiconInfoProvider == null || emojiconInfoProvider.getTextEmojiconMapping() == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : emojiconInfoProvider.getTextEmojiconMapping().entrySet()) {
            addPattern(entry.getKey(), entry.getValue());
        }
    }

    public static void addPattern(String str, Object obj) {
        emoticons.put(Pattern.compile(Pattern.quote(str)), obj);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean addSmiles(android.content.Context r12, android.text.Spannable r13) {
        /*
            java.util.Map<java.util.regex.Pattern, java.lang.Object> r0 = com.hyphenate.easeui.utils.EaseSmileUtils.emoticons
            java.util.Set r0 = r0.entrySet()
            java.util.Iterator r0 = r0.iterator()
            r1 = 0
            r2 = r1
        Lc:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto Lb4
            java.lang.Object r3 = r0.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r4 = r3.getKey()
            java.util.regex.Pattern r4 = (java.util.regex.Pattern) r4
            java.util.regex.Matcher r4 = r4.matcher(r13)
        L22:
            boolean r5 = r4.find()
            if (r5 == 0) goto Lc
            int r5 = r4.start()
            int r6 = r4.end()
            java.lang.Class<android.text.style.ImageSpan> r7 = android.text.style.ImageSpan.class
            java.lang.Object[] r5 = r13.getSpans(r5, r6, r7)
            android.text.style.ImageSpan[] r5 = (android.text.style.ImageSpan[]) r5
            int r6 = r5.length
            r7 = r1
        L3a:
            r8 = 1
            if (r7 >= r6) goto L5b
            r9 = r5[r7]
            int r10 = r13.getSpanStart(r9)
            int r11 = r4.start()
            if (r10 < r11) goto L59
            int r10 = r13.getSpanEnd(r9)
            int r11 = r4.end()
            if (r10 > r11) goto L59
            r13.removeSpan(r9)
            int r7 = r7 + 1
            goto L3a
        L59:
            r5 = r1
            goto L5c
        L5b:
            r5 = r8
        L5c:
            if (r5 == 0) goto L22
            java.lang.Object r2 = r3.getValue()
            boolean r5 = r2 instanceof java.lang.String
            r6 = 33
            if (r5 == 0) goto L9b
            r5 = r2
            java.lang.String r5 = (java.lang.String) r5
            java.lang.String r7 = "http"
            boolean r7 = r5.startsWith(r7)
            if (r7 != 0) goto L9b
            java.io.File r2 = new java.io.File
            r2.<init>(r5)
            boolean r5 = r2.exists()
            if (r5 == 0) goto L9a
            boolean r5 = r2.isDirectory()
            if (r5 == 0) goto L85
            goto L9a
        L85:
            android.text.style.ImageSpan r5 = new android.text.style.ImageSpan
            android.net.Uri r2 = android.net.Uri.fromFile(r2)
            r5.<init>(r12, r2)
            int r2 = r4.start()
            int r7 = r4.end()
            r13.setSpan(r5, r2, r7, r6)
            goto Lb1
        L9a:
            return r1
        L9b:
            android.text.style.ImageSpan r5 = new android.text.style.ImageSpan
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            r5.<init>(r12, r2)
            int r2 = r4.start()
            int r7 = r4.end()
            r13.setSpan(r5, r2, r7, r6)
        Lb1:
            r2 = r8
            goto L22
        Lb4:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.hyphenate.easeui.utils.EaseSmileUtils.addSmiles(android.content.Context, android.text.Spannable):boolean");
    }

    public static boolean containsKey(String str) {
        Iterator<Map.Entry<Pattern, Object>> it = emoticons.entrySet().iterator();
        while (it.hasNext()) {
            if (it.next().getKey().matcher(str).find()) {
                return true;
            }
        }
        return false;
    }

    public static Spannable getSmiledText(Context context, CharSequence charSequence) {
        Spannable spannableNewSpannable = spannableFactory.newSpannable(charSequence);
        addSmiles(context, spannableNewSpannable);
        return spannableNewSpannable;
    }

    public static int getSmilesSize() {
        return emoticons.size();
    }
}
