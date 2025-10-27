package com.google.zxing.qrcode.decoder;

import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
final class FormatInformation {
    private static final int FORMAT_INFO_MASK_QR = 21522;
    private final byte dataMask;
    private final ErrorCorrectionLevel errorCorrectionLevel;
    private static final int[][] FORMAT_INFO_DECODE_LOOKUP = {new int[]{21522, 0}, new int[]{R2.id.yuyinImg, 1}, new int[]{R2.string.me_input_nickname, 2}, new int[]{R2.string.alivc_speed_play, 3}, new int[]{R2.id.relativeLayout1, 4}, new int[]{R2.id.ly_add_child_view_expand, 5}, new int[]{R2.id.type_describe_tv, 6}, new int[]{R2.id.tvSchoolGZ_7_name, 7}, new int[]{R2.styleable.bl_anim_bl_duration_item4, 8}, new int[]{R2.styleable.RImageView_is_circle, 9}, new int[]{32170, 10}, new int[]{30877, 11}, new int[]{R2.style.Widget_Material3_TextInputLayout_FilledBox, 12}, new int[]{R2.style.Base_Widget_AppCompat_TextView, 13}, new int[]{R2.styleable.DslTabLayout_tab_divider_height, 14}, new int[]{R2.styleable.CirclePoint_point_unselected_color, 15}, new int[]{R2.color.stolor, 16}, new int[]{R2.color.m3_dynamic_primary_text_disable_only, 17}, new int[]{R2.dimen.dp_761, 18}, new int[]{R2.dimen.dp_1160, 19}, new int[]{R2.attr.ic_select_pic_close, 20}, new int[]{R2.attr.bl_focused_gradient_type, 21}, new int[]{R2.attr.srlTextSizeTime, 22}, new int[]{2107, 23}, new int[]{R2.id.autoComplete, 24}, new int[]{R2.drawable.shape_forum_search_index, 25}, new int[]{R2.id.lineviewtype, 26}, new int[]{R2.id.fragment, 27}, new int[]{R2.drawable.ease_group_icon, 28}, new int[]{R2.drawable.authsdk_privacy_btn_normal, 29}, new int[]{R2.drawable.qq, 30}, new int[]{R2.drawable.me_bg, 31}};
    private static final int[] BITS_SET_IN_HALF_BYTE = {0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4};

    private FormatInformation(int i2) {
        this.errorCorrectionLevel = ErrorCorrectionLevel.forBits((i2 >> 3) & 3);
        this.dataMask = (byte) (i2 & 7);
    }

    public static FormatInformation decodeFormatInformation(int i2, int i3) {
        FormatInformation formatInformationDoDecodeFormatInformation = doDecodeFormatInformation(i2, i3);
        return formatInformationDoDecodeFormatInformation != null ? formatInformationDoDecodeFormatInformation : doDecodeFormatInformation(i2 ^ 21522, i3 ^ 21522);
    }

    private static FormatInformation doDecodeFormatInformation(int i2, int i3) {
        int iNumBitsDiffering;
        int i4 = Integer.MAX_VALUE;
        int i5 = 0;
        for (int[] iArr : FORMAT_INFO_DECODE_LOOKUP) {
            int i6 = iArr[0];
            if (i6 == i2 || i6 == i3) {
                return new FormatInformation(iArr[1]);
            }
            int iNumBitsDiffering2 = numBitsDiffering(i2, i6);
            if (iNumBitsDiffering2 < i4) {
                i5 = iArr[1];
                i4 = iNumBitsDiffering2;
            }
            if (i2 != i3 && (iNumBitsDiffering = numBitsDiffering(i3, i6)) < i4) {
                i5 = iArr[1];
                i4 = iNumBitsDiffering;
            }
        }
        if (i4 <= 3) {
            return new FormatInformation(i5);
        }
        return null;
    }

    public static int numBitsDiffering(int i2, int i3) {
        int i4 = i2 ^ i3;
        int[] iArr = BITS_SET_IN_HALF_BYTE;
        return iArr[i4 & 15] + iArr[(i4 >>> 4) & 15] + iArr[(i4 >>> 8) & 15] + iArr[(i4 >>> 12) & 15] + iArr[(i4 >>> 16) & 15] + iArr[(i4 >>> 20) & 15] + iArr[(i4 >>> 24) & 15] + iArr[(i4 >>> 28) & 15];
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FormatInformation)) {
            return false;
        }
        FormatInformation formatInformation = (FormatInformation) obj;
        return this.errorCorrectionLevel == formatInformation.errorCorrectionLevel && this.dataMask == formatInformation.dataMask;
    }

    public byte getDataMask() {
        return this.dataMask;
    }

    public ErrorCorrectionLevel getErrorCorrectionLevel() {
        return this.errorCorrectionLevel;
    }

    public int hashCode() {
        return (this.errorCorrectionLevel.ordinal() << 3) | this.dataMask;
    }
}
