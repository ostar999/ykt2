package com.bigkoo.pickerview.utils;

import android.util.Log;
import com.yikaobang.yixue.R2;
import java.util.GregorianCalendar;

/* loaded from: classes2.dex */
public class LunarCalendar {
    public static final int MAX_YEAR = 2099;
    public static final int MIN_YEAR = 1900;
    private static final int[] DAYS_BEFORE_MONTH = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, R2.attr.app_theme_red, R2.attr.arcShowThumb};
    private static final int[] LUNAR_INFO = {8697535, 306771, 677704, 5580477, 861776, 890180, 4631225, 354893, 634178, 2404022, 306762, 6966718, 675154, 861510, 6116026, 742478, 879171, 2714935, 613195, 7642049, 300884, 674632, 5973436, 435536, 447557, 4905656, 177741, 612162, 2398135, 300874, 6703934, 870993, 959814, 5690554, 372046, 177732, 3749688, 601675, 8165055, 824659, 870984, 7185723, 742735, 354885, 4894137, 154957, 601410, 2921910, 693578, 8080061, 445009, 742726, 5593787, 318030, 678723, 3484600, 338764, 9082175, 955730, 436808, 7001404, 701775, 308805, 4871993, 677709, 337474, 4100917, 890185, 7711422, 354897, 617798, 5549755, 306511, 675139, 5056183, 861515, 9261759, 742482, 748103, 6909244, 613200, 301893, 4869049, 674637, 11216322, 435540, 447561, 7002685, 702033, 612166, 5543867, 300879, 412484, 3581239, 959818, 8827583, 371795, 702023, 5846716, 601680, 824901, 5065400, 870988, 894273, 2468534, 354889, 8039869, 154962, 601415, 6067642, 693582, 739907, 4937015, 709962, 9788095, 309843, 678728, 6630332, 338768, 693061, 4672185, 436812, 709953, 2415286, 308810, 6969149, 675409, 861766, 6198074, 873293, 371267, 3585335, 617803, 11841215, 306515, 675144, 7153084, 861519, 873028, 6138424, 744012, 355649, 2403766, 301898, 8014782, 674641, 697670, 5984954, 447054, 711234, 3496759, 603979, 8689601, 300883, 412488, 6726972, 959823, 436804, 4896312, 699980, 601666, 3970869, 824905, 8211133, 870993, 894277, 5614266, 354894, 683331, 4533943, 339275, 9082303, 693587, 739911, 7034171, 709967, 350789, 4873528, 678732, 338754, 3838902, 430921, 7809469, 436817, 709958, 5561018, 308814, 677699, 4532024, 861770, 9343806, 873042, 895559, 6731067, 355663, 306757, 4869817, 675148, 857409, 2986677};
    private static int[] solar_1_1 = {R2.attr.ic_screen, 966732, 967231, 967733, 968265, 968766, 969297, 969798, 970298, 970829, 971330, 971830, 972362, 972863, 973395, 973896, 974397, 974928, 975428, 975929, 976461, 976962, 977462, 977994, 978494, 979026, 979526, 980026, 980558, 981059, 981559, 982091, 982593, 983124, 983624, 984124, 984656, 985157, 985656, 986189, 986690, 987191, 987722, 988222, 988753, 989254, 989754, 990286, 990788, 991288, 991819, 992319, 992851, 993352, 993851, 994383, 994885, 995385, 995917, 996418, 996918, 997450, 997949, 998481, 998982, 999483, 1000014, 1000515, 1001016, 1001548, 1002047, 1002578, 1003080, 1003580, 1004111, 1004613, 1005113, 1005645, 1006146, 1006645, 1007177, 1007678, 1008209, 1008710, 1009211, 1009743, 1010243, 1010743, 1011275, 1011775, 1012306, 1012807, 1013308, 1013840, 1014341, 1014841, 1015373, 1015874, 1016404, 1016905, 1017405, 1017937, 1018438, 1018939, 1019471, 1019972, 1020471, 1021002, 1021503, 1022035, 1022535, 1023036, 1023568, 1024069, 1024568, 1025100, 1025601, 1026102, 1026633, 1027133, 1027666, 1028167, 1028666, 1029198, 1029699, 1030199, 1030730, 1031231, 1031763, 1032264, 1032764, 1033296, 1033797, 1034297, 1034828, 1035329, 1035830, 1036362, 1036861, 1037393, 1037894, 1038394, 1038925, 1039427, 1039927, 1040459, 1040959, 1041491, 1041992, 1042492, 1043023, 1043524, 1044024, 1044556, 1045057, 1045558, 1046090, 1046590, 1047121, 1047622, 1048122, 1048654, 1049154, 1049655, 1050187, 1050689, 1051219, 1051720, 1052220, 1052751, 1053252, 1053752, 1054284, 1054786, 1055285, 1055817, 1056317, 1056849, 1057349, 1057850, 1058382, 1058883, 1059383, 1059915, 1060415, 1060947, 1061447, 1061947, 1062479, 1062981, 1063480, 1064012, 1064514, 1065014, 1065545, 1066045, 1066577, 1067078, 1067578, 1068110, 1068611, 1069112, 1069642, 1070142, 1070674, 1071175, 1071675, 1072207, 1072709, 1073209, 1073740, 1074241, 1074741, 1075273, 1075773, 1076305, 1076807, 1077308, 1077839, 1078340, 1078840, 1079372, 1079871, 1080403, 1080904};
    private static int[] lunar_month_days = {R2.attr.ic_screen, R2.color.subscribe_item_normal_bg, R2.color.svip_info_color_night, R2.id.tvUse, R2.attr.page_loading5, 50359, R2.attr.cpv_progressTextColor, R2.attr.npv_DividerColor, 46378, R2.dimen.dp_82, R2.attr.svg_raw_resource, R2.styleable.TabLayout_tabInlineLabel, R2.color.mtrl_btn_transparent_bg_color, 67949, R2.attr.main_theme_six_deep_color, R2.color.m3_sys_color_light_on_background, 43597, R2.dimen.dp_185, R2.dimen.dp_367, 36181, R2.attr.personal_add_friren, R2.color.invite_status_no_pass_wait_color_night, R2.id.textred1, R2.attr.main_theme_six_deep_color, 54427, R2.color.m3_sys_color_dynamic_light_on_secondary_container, R2.dimen.dp_183, 47781, R2.color.svip_button_txt_color_night, R2.dimen.dp_293, R2.layout.activity_replycomment, R2.color.disable_red_color_night, 59703, R2.attr.localimg, R2.color.m3_sys_color_dynamic_light_on_background, 46667, R2.attr.success_color, R2.attr.tab_border_stroke_color, 38325, R2.attr.ease_border_color, R2.color.dialog_transparent_bg, R2.id.textSpacerNoButtons, R2.attr.localimg, 52374, R2.dimen.dp_241, R2.dimen.dp_849, 44457, R2.attr.ptrHeaderBackgroundStart, R2.attr.ease_border_color, R2.styleable.RConstraintLayout_corner_radius_bottom_left, R2.color.daily_calendar_today_bg_night_color, 63789, R2.dimen.dp_1012, R2.dimen.dp_241, 56138, R2.color.svip_info_color_night, R2.attr.personal_add_friren, 38235, R2.attr.ct_clickTextBgColor, R2.color.daily_calendar_finish_night_bg_color, R2.layout.popup_home_msg, R2.color.material_dynamic_tertiary100, 63125, R2.attr.tab_badge_padding_right, R2.color.svip_info_color_night, 43701, R2.attr.mdSlideDrawable, R2.color.m3_sys_color_light_on_surface_variant, R2.styleable.Constraint_layout_goneMarginRight, R2.attr.npv_DividerColor, 70954, R2.dimen.dp_82, R2.attr.svg_raw_resource, 54698, R2.color.mtrl_btn_transparent_bg_color, R2.attr.matProg_progressIndeterminate, 38062, R2.color.m3_sys_color_light_on_background, R2.attr.np_textStrikeThru, 32038, R2.dimen.dp_367, 60245, R2.attr.personal_add_friren, R2.color.encode_view, 43357, R2.attr.main_theme_one_deep_color, R2.color.m3_sys_color_dynamic_light_on_secondary_container, 39501, R2.dimen.dp_183, 72357, R2.color.svip_button_txt_color_night, R2.color.text_color_default, 53978, R2.color.disable_red_color_night, R2.attr.login_text_color, 38039, R2.color.m3_sys_color_dynamic_light_on_background, 87627, R2.attr.success_color, R2.attr.tab_border_stroke_color, 54708, R2.color.mtrl_calendar_selected_range, R2.color.dialog_transparent_bg, 43311, R2.attr.localimg, R2.attr.spaceSize, R2.styleable.FontFamilyFont_android_font, R2.dimen.dp_849, 68965, R2.attr.ptrHeaderBackground, R2.color.mtrl_calendar_selected_range, 45677, R2.color.daily_calendar_today_bg_night_color, R2.dimen.dp_1014, 39573, R2.dimen.dp_241, R2.dimen.dp_396, R2.id.tv_browse, R2.attr.personal_add_friren, 62811, R2.attr.ct_clickTextBgColor, R2.color.daily_calendar_finish_night_bg_color, 47403, R2.color.material_dynamic_tertiary100, R2.color.subscribe_item_normal_bg, 38570, R2.color.nickanme, 76469, R2.attr.materialAlertDialogTitlePanelStyle, R2.color.m3_sys_color_light_on_surface_variant, 51799, R2.attr.npv_DividerColor, R2.color.material_dynamic_secondary95, 36501, R2.attr.svg_raw_resource, R2.color.nickanme, R2.id.toSearchView, R2.attr.matProg_progressIndeterminate, 54446, R2.color.m3_sys_color_dynamic_light_on_surface_variant, R2.dimen.dp_185, 48422, R2.dimen.dp_256, R2.attr.ptrDrawableBottom, R2.styleable.GradientColor_android_endColor, R2.color.encode_view, 92509, R2.attr.main_theme_one_deep_color, R2.color.m3_sys_color_dynamic_light_on_secondary_container, 55883, R2.dimen.dp_183, R2.dimen.dp_254, 47956, R2.color.switch_thumb_normal_material_dark, R2.attr.personal_mode_ic, R2.id.textinput_prefix_text, R2.attr.login_text_color, 62615, R2.color.m3_sys_color_dynamic_light_on_background, R2.color.material_slider_inactive_track_color, 46757, R2.attr.tab_border_item_background_width_offset, R2.color.numdefault, R2.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, R2.color.darkslateblue, 67887, R2.attr.localimg, R2.attr.spaceSize, 52554, R2.dimen.dp_849, R2.attr.switchTextAppearance, 38252, R2.color.menu_item_selected_bkg_color, R2.color.daily_calendar_today_bg_night_color, 31022, R2.dimen.dp_1014, 64149, R2.dimen.dp_241, R2.dimen.dp_396, 43861, R2.attr.personal_add_friren, R2.color.main_color_translate_end, 35421, R2.attr.npv_EmptyItemHint, 70955, R2.color.material_dynamic_tertiary100, R2.color.subscribe_item_normal_bg, 54954, R2.color.nickanme, R2.attr.page_loading3, 38074, R2.color.m3_sys_color_light_on_surface_variant, R2.attr.npv_DividerColor, R2.styleable.StyledPlayerControlView_ad_marker_width, R2.attr.statusBarBackground, 61011, R2.attr.svg_raw_resource, R2.color.nickanme, 43445, R2.attr.matProg_progressIndeterminate, R2.color.m3_sys_color_light_on_background, 35406, R2.dimen.dp_185, 72998, R2.dimen.dp_254, R2.dimen.dp_402, 52586, R2.attr.personal_mode_ic, R2.attr.main_theme_six_deep_color, 38045, R2.color.m3_sys_color_dynamic_light_on_secondary_container, R2.dimen.dp_157, R2.string.alivc_player_download_video_un_all_selected, R2.dimen.dp_254, 64338, R2.color.switch_thumb_normal_material_dark, R2.attr.paixu, 43355, R2.attr.login_text_color, R2.color.m3_sys_color_dynamic_light_on_background, 39499, R2.color.material_slider_inactive_track_color, 79525, R2.attr.tab_border_item_background_width_offset, R2.color.no_submit_color};

    private static int daysInLunarMonth(int i2, int i3) {
        return (LUNAR_INFO[i2 + (-1900)] & (1048576 >> i3)) == 0 ? 29 : 30;
    }

    private static int daysInLunarYear(int i2) {
        int i3 = leapMonth(i2) != 0 ? R2.attr.arcTickOffsetAngle : R2.attr.arcEnabledSingle;
        int i4 = LUNAR_INFO[i2 - 1900] & 1048448;
        for (int i5 = 524288; i5 > 7; i5 >>= 1) {
            if ((i4 & i5) != 0) {
                i3++;
            }
        }
        return i3;
    }

    public static final int daysInMonth(int i2, int i3) {
        return daysInMonth(i2, i3, false);
    }

    private static int getBitInt(int i2, int i3, int i4) {
        return (i2 & (((1 << i3) - 1) << i4)) >> i4;
    }

    public static int leapMonth(int i2) {
        return (LUNAR_INFO[i2 - 1900] & 15728640) >> 20;
    }

    public static final int[] lunarToSolar(int i2, int i3, int i4, boolean z2) {
        if (i2 < 1900 || i2 > 2099 || i3 < 1 || i3 > 12 || i4 < 1 || i4 > 30) {
            throw new IllegalArgumentException("Illegal lunar date, must be like that:\n\tyear : 1900~2099\n\tmonth : 1~12\n\tday : 1~30");
        }
        int i5 = i2 - 1900;
        int i6 = LUNAR_INFO[i5];
        int i7 = (i6 & 31) - 1;
        if (((i6 & 96) >> 5) == 2) {
            i7 += 31;
        }
        for (int i8 = 1; i8 < i3; i8++) {
            i7 = ((524288 >> (i8 + (-1))) & LUNAR_INFO[i5]) == 0 ? i7 + 29 : i7 + 30;
        }
        int i9 = i7 + i4;
        int i10 = LUNAR_INFO[i5];
        int i11 = (15728640 & i10) >> 20;
        if (i11 != 0 && (i3 > i11 || (i3 == i11 && z2))) {
            i9 = ((524288 >> (i3 - 1)) & i10) == 0 ? i9 + 29 : i9 + 30;
        }
        if (i9 > 366 || (i2 % 4 != 0 && i9 > 365)) {
            i2++;
            i9 = i2 % 4 == 1 ? i9 - 366 : i9 - 365;
        }
        int[] iArr = new int[3];
        int i12 = 1;
        while (true) {
            if (i12 >= 13) {
                break;
            }
            int[] iArr2 = DAYS_BEFORE_MONTH;
            int i13 = iArr2[i12];
            int i14 = i2 % 4;
            if (i14 == 0 && i12 > 2) {
                i13++;
            }
            if (i14 == 0 && i12 == 2 && i13 + 1 == i9) {
                iArr[1] = i12;
                iArr[2] = i9 - 31;
                break;
            }
            if (i13 >= i9) {
                iArr[1] = i12;
                int i15 = iArr2[i12 - 1];
                int i16 = (i14 != 0 || i12 <= 2) ? i15 : i15 + 1;
                if (i9 > i16) {
                    iArr[2] = i9 - i16;
                } else if (i9 != i16) {
                    iArr[2] = i9;
                } else if (i14 == 0 && i12 == 2) {
                    iArr[2] = (iArr2[i12] - i15) + 1;
                } else {
                    iArr[2] = iArr2[i12] - i15;
                }
            } else {
                i12++;
            }
        }
        iArr[0] = i2;
        return iArr;
    }

    private static long solarToInt(int i2, int i3, int i4) {
        int i5 = i2 - (((i3 + 9) % 12) / 10);
        return (((i5 * R2.attr.arcShowThumb) + (i5 / 4)) - (i5 / 100)) + (i5 / 400) + (((r3 * 306) + 5) / 10) + (i4 - 1);
    }

    public static final int[] solarToLunar(int i2, int i3, int i4) {
        int[] iArr = new int[4];
        int[] iArr2 = solar_1_1;
        int i5 = 0;
        int i6 = i2 - iArr2[0];
        if (iArr2[i6] > ((i2 << 9) | (i3 << 5) | i4)) {
            i6--;
        }
        int i7 = iArr2[i6];
        long jSolarToInt = solarToInt(i2, i3, i4) - solarToInt(getBitInt(i7, 12, 9), getBitInt(i7, 4, 5), getBitInt(i7, 5, 0));
        int i8 = lunar_month_days[i6];
        int bitInt = getBitInt(i8, 4, 13);
        int i9 = i6 + solar_1_1[0];
        long j2 = jSolarToInt + 1;
        int i10 = 1;
        for (int i11 = 0; i11 < 13; i11++) {
            long j3 = getBitInt(i8, 1, 12 - i11) == 1 ? 30 : 29;
            if (j2 <= j3) {
                break;
            }
            i10++;
            j2 -= j3;
        }
        int i12 = (int) j2;
        iArr[0] = i9;
        iArr[1] = i10;
        if (bitInt != 0 && i10 > bitInt) {
            iArr[1] = i10 - 1;
            if (i10 == bitInt + 1) {
                i5 = 1;
            }
        }
        iArr[2] = i12;
        iArr[3] = i5;
        return iArr;
    }

    @Deprecated
    public static final int[] solarToLunarDeprecated(int i2, int i3, int i4) {
        int[] iArr = new int[4];
        int i5 = 1900;
        int i6 = 0;
        int time = (int) ((new GregorianCalendar(i2, i3 - 1, i4).getTime().getTime() - new GregorianCalendar(1900, 0, 31).getTime().getTime()) / 86400000);
        int iDaysInLunarYear = 0;
        while (i5 <= 2099 && time > 0) {
            iDaysInLunarYear = daysInLunarYear(i5);
            time -= iDaysInLunarYear;
            i5++;
        }
        if (time < 0) {
            time += iDaysInLunarYear;
            i5--;
        }
        iArr[0] = i5;
        int iLeapMonth = leapMonth(i5);
        int iDaysInLunarMonth = 0;
        int i7 = 1;
        while (i7 <= 13 && time > 0) {
            iDaysInLunarMonth = daysInLunarMonth(i5, i7);
            time -= iDaysInLunarMonth;
            i7++;
        }
        if (iLeapMonth != 0 && i7 > iLeapMonth) {
            i7--;
            Log.i("----------->", i2 + "-" + i3 + "-" + i4 + "====>" + i7 + "-" + iLeapMonth);
            if (i7 == iLeapMonth) {
                i6 = 1;
            }
        }
        if (time < 0) {
            time += iDaysInLunarMonth;
            i7--;
        }
        iArr[1] = i7;
        iArr[2] = time + 1;
        iArr[3] = i6;
        return iArr;
    }

    public static final int daysInMonth(int i2, int i3, boolean z2) {
        int iLeapMonth = leapMonth(i2);
        int i4 = (iLeapMonth == 0 || i3 <= iLeapMonth) ? 0 : 1;
        if (!z2) {
            return daysInLunarMonth(i2, i3 + i4);
        }
        if (iLeapMonth == 0 || iLeapMonth != i3) {
            return 0;
        }
        return daysInLunarMonth(i2, i3 + 1);
    }
}
