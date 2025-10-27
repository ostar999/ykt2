package com.google.zxing.oned;

import com.google.zxing.client.result.ExpandedProductParsedResult;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
final class EANManufacturerOrgSupport {
    private final List<int[]> ranges = new ArrayList();
    private final List<String> countryIdentifiers = new ArrayList();

    private void add(int[] iArr, String str) {
        this.ranges.add(iArr);
        this.countryIdentifiers.add(str);
    }

    private synchronized void initIfNeeded() {
        if (this.ranges.isEmpty()) {
            add(new int[]{0, 19}, "US/CA");
            add(new int[]{30, 39}, "US");
            add(new int[]{60, 139}, "US/CA");
            add(new int[]{300, R2.attr.arcTickSplitAngle}, "FR");
            add(new int[]{R2.attr.arcTickStrokeWidth}, "BG");
            add(new int[]{R2.attr.arrow_question_right}, "SI");
            add(new int[]{R2.attr.assetName}, "HR");
            add(new int[]{R2.attr.autoCompleteMode}, "BA");
            add(new int[]{400, R2.attr.banner_indicator_normal_color}, "DE");
            add(new int[]{450, R2.attr.barProgressColor}, "JP");
            add(new int[]{R2.attr.barRadius, R2.attr.bead_radius}, "RU");
            add(new int[]{R2.attr.behavior_autoShrink}, "TW");
            add(new int[]{R2.attr.behavior_fitToContents}, "EE");
            add(new int[]{R2.attr.behavior_halfExpandedRatio}, "LV");
            add(new int[]{R2.attr.behavior_hideable}, "AZ");
            add(new int[]{R2.attr.behavior_overlapTop}, "LT");
            add(new int[]{R2.attr.behavior_peekHeight}, "UZ");
            add(new int[]{R2.attr.behavior_saveFlags}, "LK");
            add(new int[]{480}, "PH");
            add(new int[]{R2.attr.beizi_bav_arrow_style}, "BY");
            add(new int[]{R2.attr.beizi_bav_color}, "UA");
            add(new int[]{R2.attr.bgColor}, "MD");
            add(new int[]{R2.attr.bg_backgroud}, "AM");
            add(new int[]{R2.attr.bg_block_color}, "GE");
            add(new int[]{R2.attr.bg_circle_black}, "KZ");
            add(new int[]{R2.attr.bg_correction_option}, "HK");
            add(new int[]{R2.attr.bg_curriculum, R2.attr.bg_red}, "JP");
            add(new int[]{500, 509}, "GB");
            add(new int[]{R2.attr.bl_active_textColor}, "GR");
            add(new int[]{R2.attr.bl_checkable_gradient_gradientRadius}, ExpandedProductParsedResult.POUND);
            add(new int[]{R2.attr.bl_checkable_gradient_startColor}, "CY");
            add(new int[]{R2.attr.bl_checkable_gradient_useLevel}, "MK");
            add(new int[]{R2.attr.bl_checked_button_drawable}, "MT");
            add(new int[]{R2.attr.bl_checked_gradient_centerX}, "IE");
            add(new int[]{R2.attr.bl_checked_gradient_centerY, R2.attr.bl_corners_bottomLeftRadius}, "BE/LU");
            add(new int[]{R2.attr.bl_duration_item1}, "PT");
            add(new int[]{R2.attr.bl_duration_item5}, "IS");
            add(new int[]{R2.attr.bl_duration_item6, R2.attr.bl_enabled_gradient_endColor}, "DK");
            add(new int[]{R2.attr.bl_focused_gradient_angle}, "PL");
            add(new int[]{R2.attr.bl_focused_gradient_endColor}, "RO");
            add(new int[]{R2.attr.bl_focused_hovered}, "HU");
            add(new int[]{600, 601}, "ZA");
            add(new int[]{603}, "GH");
            add(new int[]{608}, "BH");
            add(new int[]{609}, "MU");
            add(new int[]{611}, "MA");
            add(new int[]{R2.attr.bl_frame_drawable_item5}, "DZ");
            add(new int[]{R2.attr.bl_frame_drawable_item8}, "KE");
            add(new int[]{R2.attr.bl_function}, "CI");
            add(new int[]{R2.attr.bl_gradient_angle}, "TN");
            add(new int[]{R2.attr.bl_gradient_centerX}, "SY");
            add(new int[]{R2.attr.bl_gradient_centerY}, "EG");
            add(new int[]{R2.attr.bl_gradient_gradientRadius}, "LY");
            add(new int[]{R2.attr.bl_gradient_startColor}, "JO");
            add(new int[]{R2.attr.bl_gradient_type}, "IR");
            add(new int[]{R2.attr.bl_gradient_useLevel}, "KW");
            add(new int[]{R2.attr.bl_multi_selector1}, "SA");
            add(new int[]{R2.attr.bl_multi_selector2}, "AE");
            add(new int[]{640, R2.attr.bl_pressed_gradient_centerColor}, "FI");
            add(new int[]{R2.attr.bl_unCheckable_drawable, R2.attr.bl_unCheckable_gradient_endColor}, "CN");
            add(new int[]{700, R2.attr.bl_unChecked_gradient_endColor}, "NO");
            add(new int[]{R2.attr.bl_unEnabled_textColor}, "IL");
            add(new int[]{R2.attr.bl_unExpanded_textColor, R2.attr.bl_unFocused_gradient_startColor}, "SE");
            add(new int[]{R2.attr.bl_unFocused_gradient_type}, "GT");
            add(new int[]{R2.attr.bl_unFocused_gradient_useLevel}, "SV");
            add(new int[]{R2.attr.bl_unFocused_hovered}, "HN");
            add(new int[]{R2.attr.bl_unFocused_solid_color}, "NI");
            add(new int[]{R2.attr.bl_unFocused_stroke_color}, "CR");
            add(new int[]{R2.attr.bl_unFocused_textColor}, "PA");
            add(new int[]{R2.attr.bl_unPressed_drawable}, "DO");
            add(new int[]{R2.attr.bl_unPressed_gradient_centerY}, "MX");
            add(new int[]{R2.attr.bl_unPressed_gradient_type, R2.attr.bl_unPressed_gradient_useLevel}, "CA");
            add(new int[]{R2.attr.bl_unSelected_drawable}, "VE");
            add(new int[]{R2.attr.bl_unSelected_gradient_angle, R2.attr.bl_unSelected_solid_color}, "CH");
            add(new int[]{R2.attr.bl_unSelected_stroke_color}, "CO");
            add(new int[]{R2.attr.blendSrc}, "UY");
            add(new int[]{R2.attr.blurOverlayColor}, "PE");
            add(new int[]{R2.attr.blur_border_width}, "BO");
            add(new int[]{R2.attr.blur_corner_radius_bottom_left}, "AR");
            add(new int[]{R2.attr.blur_corner_radius_bottom_right}, "CL");
            add(new int[]{R2.attr.blur_mode}, "PY");
            add(new int[]{R2.attr.blur_overlay_color}, "PE");
            add(new int[]{R2.attr.blur_radius}, "EC");
            add(new int[]{R2.attr.bnb_scale_ratio, 790}, "BR");
            add(new int[]{800, R2.attr.buttomTextColorDefault}, "IT");
            add(new int[]{R2.attr.buttomTextColorPress, R2.attr.buttonIconDimen}, "ES");
            add(new int[]{R2.attr.buttonPanelSideLayout}, "CU");
            add(new int[]{R2.attr.button_icon_size}, "SK");
            add(new int[]{R2.attr.button_icon_stroke_width}, "CZ");
            add(new int[]{R2.attr.button_style}, "YU");
            add(new int[]{R2.attr.bvp_indicator_gravity}, "MN");
            add(new int[]{R2.attr.bvp_indicator_radius}, "KP");
            add(new int[]{R2.attr.bvp_indicator_slide_mode, R2.attr.bvp_indicator_style}, "TR");
            add(new int[]{R2.attr.bvp_indicator_visibility, R2.attr.cancelButtonBackground}, "NL");
            add(new int[]{R2.attr.cancelButtonMarginTop}, "KR");
            add(new int[]{R2.attr.cardForegroundColor}, "TH");
            add(new int[]{R2.attr.cardUseCompatPadding}, "SG");
            add(new int[]{R2.attr.carousel_backwardTransition}, "IN");
            add(new int[]{R2.attr.carousel_forwardTransition}, "VN");
            add(new int[]{R2.attr.carousel_previousState}, "PK");
            add(new int[]{R2.attr.carousel_touchUp_velocityThreshold}, "ID");
            add(new int[]{900, R2.attr.checkbox_style_single}, "AT");
            add(new int[]{R2.attr.cheshitongji, R2.attr.chipIconVisible}, "AU");
            add(new int[]{R2.attr.chipMinHeight, R2.attr.chipStyle}, "AZ");
            add(new int[]{R2.attr.ci_height}, "MY");
            add(new int[]{R2.attr.cimg2}, "MO");
        }
    }

    public String lookupCountryIdentifier(String str) throws NumberFormatException {
        int[] iArr;
        int i2;
        initIfNeeded();
        int i3 = Integer.parseInt(str.substring(0, 3));
        int size = this.ranges.size();
        for (int i4 = 0; i4 < size && i3 >= (i2 = (iArr = this.ranges.get(i4))[0]); i4++) {
            if (iArr.length != 1) {
                i2 = iArr[1];
            }
            if (i3 <= i2) {
                return this.countryIdentifiers.get(i4);
            }
        }
        return null;
    }
}
