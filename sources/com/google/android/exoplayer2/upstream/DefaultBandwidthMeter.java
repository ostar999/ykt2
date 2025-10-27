package com.google.android.exoplayer2.upstream;

import android.content.Context;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.annotation.Nullable;
import cn.hutool.core.text.CharPool;
import com.alibaba.fastjson.parser.JSONLexer;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.NetworkTypeObserver;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.zxing.client.result.ExpandedProductParsedResult;
import com.yikaobang.yixue.R2;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.text.Typography;

/* loaded from: classes3.dex */
public final class DefaultBandwidthMeter implements BandwidthMeter, TransferListener {
    private static final int BYTES_TRANSFERRED_FOR_ESTIMATE = 524288;
    private static final int COUNTRY_GROUP_INDEX_2G = 1;
    private static final int COUNTRY_GROUP_INDEX_3G = 2;
    private static final int COUNTRY_GROUP_INDEX_4G = 3;
    private static final int COUNTRY_GROUP_INDEX_5G_NSA = 4;
    private static final int COUNTRY_GROUP_INDEX_5G_SA = 5;
    private static final int COUNTRY_GROUP_INDEX_WIFI = 0;
    public static final long DEFAULT_INITIAL_BITRATE_ESTIMATE = 1000000;
    public static final int DEFAULT_SLIDING_WINDOW_MAX_WEIGHT = 2000;
    private static final int ELAPSED_MILLIS_FOR_ESTIMATE = 2000;

    @Nullable
    private static DefaultBandwidthMeter singletonInstance;
    private long bitrateEstimate;
    private final Clock clock;
    private final BandwidthMeter.EventListener.EventDispatcher eventDispatcher;
    private final ImmutableMap<Integer, Long> initialBitrateEstimates;
    private long lastReportedBitrateEstimate;
    private int networkType;
    private int networkTypeOverride;
    private boolean networkTypeOverrideSet;
    private final boolean resetOnNetworkTypeChange;
    private long sampleBytesTransferred;
    private long sampleStartTimeMs;
    private final SlidingPercentile slidingPercentile;
    private int streamCount;
    private long totalBytesTransferred;
    private long totalElapsedTimeMs;
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI = ImmutableList.of(5400000L, 3300000L, 2000000L, 1300000L, 760000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_2G = ImmutableList.of(1700000L, 820000L, 450000L, 180000L, 130000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_3G = ImmutableList.of(2300000L, 1300000L, 1000000L, 820000L, 570000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_4G = ImmutableList.of(3400000L, 2000000L, 1400000L, 1000000L, 620000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_NSA = ImmutableList.of(7500000L, 5200000L, 3700000L, 1800000L, 1100000L);
    public static final ImmutableList<Long> DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_SA = ImmutableList.of(3300000L, 1900000L, 1700000L, 1500000L, 1200000L);

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public static int[] getInitialBitrateCountryGroupAssignment(String str) {
        str.hashCode();
        char c3 = 65535;
        switch (str.hashCode()) {
            case R2.attr.isMaterial3Theme /* 2083 */:
                if (str.equals("AD")) {
                    c3 = 0;
                    break;
                }
                break;
            case R2.attr.isMaterialTheme /* 2084 */:
                if (str.equals("AE")) {
                    c3 = 1;
                    break;
                }
                break;
            case R2.attr.isOpaque /* 2085 */:
                if (str.equals("AF")) {
                    c3 = 2;
                    break;
                }
                break;
            case R2.attr.isOpened /* 2086 */:
                if (str.equals("AG")) {
                    c3 = 3;
                    break;
                }
                break;
            case R2.attr.isRepeat /* 2088 */:
                if (str.equals("AI")) {
                    c3 = 4;
                    break;
                }
                break;
            case R2.attr.isShowUnderLine /* 2091 */:
                if (str.equals("AL")) {
                    c3 = 5;
                    break;
                }
                break;
            case R2.attr.isSingleLine /* 2092 */:
                if (str.equals("AM")) {
                    c3 = 6;
                    break;
                }
                break;
            case R2.attr.isTextBold /* 2094 */:
                if (str.equals("AO")) {
                    c3 = 7;
                    break;
                }
                break;
            case R2.attr.is_circle /* 2096 */:
                if (str.equals("AQ")) {
                    c3 = '\b';
                    break;
                }
                break;
            case R2.attr.itemActiveIndicatorStyle /* 2097 */:
                if (str.equals("AR")) {
                    c3 = '\t';
                    break;
                }
                break;
            case R2.attr.itemBackground /* 2098 */:
                if (str.equals("AS")) {
                    c3 = '\n';
                    break;
                }
                break;
            case 2099:
                if (str.equals("AT")) {
                    c3 = 11;
                    break;
                }
                break;
            case 2100:
                if (str.equals("AU")) {
                    c3 = '\f';
                    break;
                }
                break;
            case 2102:
                if (str.equals("AW")) {
                    c3 = '\r';
                    break;
                }
                break;
            case 2103:
                if (str.equals("AX")) {
                    c3 = 14;
                    break;
                }
                break;
            case 2105:
                if (str.equals("AZ")) {
                    c3 = 15;
                    break;
                }
                break;
            case R2.attr.itemPaddingBottom /* 2111 */:
                if (str.equals("BA")) {
                    c3 = 16;
                    break;
                }
                break;
            case R2.attr.itemPaddingTop /* 2112 */:
                if (str.equals("BB")) {
                    c3 = 17;
                    break;
                }
                break;
            case R2.attr.itemShapeAppearance /* 2114 */:
                if (str.equals("BD")) {
                    c3 = 18;
                    break;
                }
                break;
            case R2.attr.itemShapeAppearanceOverlay /* 2115 */:
                if (str.equals("BE")) {
                    c3 = 19;
                    break;
                }
                break;
            case R2.attr.itemShapeFillColor /* 2116 */:
                if (str.equals("BF")) {
                    c3 = 20;
                    break;
                }
                break;
            case R2.attr.itemShapeInsetBottom /* 2117 */:
                if (str.equals("BG")) {
                    c3 = 21;
                    break;
                }
                break;
            case R2.attr.itemShapeInsetEnd /* 2118 */:
                if (str.equals("BH")) {
                    c3 = 22;
                    break;
                }
                break;
            case R2.attr.itemShapeInsetStart /* 2119 */:
                if (str.equals("BI")) {
                    c3 = 23;
                    break;
                }
                break;
            case R2.attr.itemShapeInsetTop /* 2120 */:
                if (str.equals("BJ")) {
                    c3 = 24;
                    break;
                }
                break;
            case R2.attr.itemStrokeColor /* 2122 */:
                if (str.equals("BL")) {
                    c3 = 25;
                    break;
                }
                break;
            case R2.attr.itemStrokeWidth /* 2123 */:
                if (str.equals("BM")) {
                    c3 = JSONLexer.EOI;
                    break;
                }
                break;
            case R2.attr.itemText /* 2124 */:
                if (str.equals("BN")) {
                    c3 = 27;
                    break;
                }
                break;
            case R2.attr.itemTextAppearance /* 2125 */:
                if (str.equals("BO")) {
                    c3 = 28;
                    break;
                }
                break;
            case R2.attr.itemTextAppearanceInactive /* 2127 */:
                if (str.equals("BQ")) {
                    c3 = 29;
                    break;
                }
                break;
            case R2.attr.itemTextBold /* 2128 */:
                if (str.equals("BR")) {
                    c3 = 30;
                    break;
                }
                break;
            case R2.attr.itemTextColor /* 2129 */:
                if (str.equals("BS")) {
                    c3 = 31;
                    break;
                }
                break;
            case R2.attr.itemTextSize /* 2130 */:
                if (str.equals("BT")) {
                    c3 = ' ';
                    break;
                }
                break;
            case R2.attr.item_circle_hot_2 /* 2133 */:
                if (str.equals("BW")) {
                    c3 = '!';
                    break;
                }
                break;
            case R2.attr.item_circle_hot_normal /* 2135 */:
                if (str.equals("BY")) {
                    c3 = '\"';
                    break;
                }
                break;
            case R2.attr.item_circle_hot_top_1 /* 2136 */:
                if (str.equals("BZ")) {
                    c3 = '#';
                    break;
                }
                break;
            case R2.attr.jiav /* 2142 */:
                if (str.equals("CA")) {
                    c3 = Typography.dollar;
                    break;
                }
                break;
            case R2.attr.jingyantu /* 2145 */:
                if (str.equals("CD")) {
                    c3 = '%';
                    break;
                }
                break;
            case R2.attr.jiucuo_icon /* 2147 */:
                if (str.equals("CF")) {
                    c3 = '&';
                    break;
                }
                break;
            case R2.attr.justifyContent /* 2148 */:
                if (str.equals("CG")) {
                    c3 = CharPool.SINGLE_QUOTE;
                    break;
                }
                break;
            case R2.attr.kaodiancolor /* 2149 */:
                if (str.equals("CH")) {
                    c3 = '(';
                    break;
                }
                break;
            case R2.attr.kdshoucangimg /* 2150 */:
                if (str.equals("CI")) {
                    c3 = ')';
                    break;
                }
                break;
            case R2.attr.kefu /* 2152 */:
                if (str.equals("CK")) {
                    c3 = '*';
                    break;
                }
                break;
            case R2.attr.key /* 2153 */:
                if (str.equals("CL")) {
                    c3 = '+';
                    break;
                }
                break;
            case R2.attr.keyPositionType /* 2154 */:
                if (str.equals("CM")) {
                    c3 = ',';
                    break;
                }
                break;
            case R2.attr.keyboardIcon /* 2155 */:
                if (str.equals("CN")) {
                    c3 = CharPool.DASHED;
                    break;
                }
                break;
            case R2.attr.keylines /* 2156 */:
                if (str.equals("CO")) {
                    c3 = '.';
                    break;
                }
                break;
            case R2.attr.lStar /* 2159 */:
                if (str.equals("CR")) {
                    c3 = '/';
                    break;
                }
                break;
            case R2.attr.labelGravity /* 2162 */:
                if (str.equals("CU")) {
                    c3 = '0';
                    break;
                }
                break;
            case R2.attr.labelStyle /* 2163 */:
                if (str.equals("CV")) {
                    c3 = '1';
                    break;
                }
                break;
            case R2.attr.labelTextColor /* 2164 */:
                if (str.equals("CW")) {
                    c3 = '2';
                    break;
                }
                break;
            case R2.attr.labelTextHeight /* 2165 */:
                if (str.equals("CX")) {
                    c3 = '3';
                    break;
                }
                break;
            case R2.attr.labelTextPadding /* 2166 */:
                if (str.equals("CY")) {
                    c3 = '4';
                    break;
                }
                break;
            case R2.attr.labelTextPaddingBottom /* 2167 */:
                if (str.equals("CZ")) {
                    c3 = '5';
                    break;
                }
                break;
            case R2.attr.label_unselect_text_color /* 2177 */:
                if (str.equals("DE")) {
                    c3 = '6';
                    break;
                }
                break;
            case R2.attr.layout /* 2182 */:
                if (str.equals("DJ")) {
                    c3 = '7';
                    break;
                }
                break;
            case R2.attr.layoutDescription /* 2183 */:
                if (str.equals("DK")) {
                    c3 = '8';
                    break;
                }
                break;
            case R2.attr.layoutManager /* 2185 */:
                if (str.equals("DM")) {
                    c3 = '9';
                    break;
                }
                break;
            case R2.attr.layout_anchor /* 2187 */:
                if (str.equals("DO")) {
                    c3 = ':';
                    break;
                }
                break;
            case R2.attr.layout_constraintBaseline_toBaselineOf /* 2198 */:
                if (str.equals("DZ")) {
                    c3 = ';';
                    break;
                }
                break;
            case R2.attr.layout_constraintCircleRadius /* 2206 */:
                if (str.equals("EC")) {
                    c3 = Typography.less;
                    break;
                }
                break;
            case R2.attr.layout_constraintEnd_toEndOf /* 2208 */:
                if (str.equals("EE")) {
                    c3 = '=';
                    break;
                }
                break;
            case R2.attr.layout_constraintGuide_begin /* 2210 */:
                if (str.equals("EG")) {
                    c3 = Typography.greater;
                    break;
                }
                break;
            case R2.attr.layout_constraintLeft_creator /* 2221 */:
                if (str.equals("ER")) {
                    c3 = '?';
                    break;
                }
                break;
            case R2.attr.layout_constraintLeft_toLeftOf /* 2222 */:
                if (str.equals("ES")) {
                    c3 = '@';
                    break;
                }
                break;
            case R2.attr.layout_constraintLeft_toRightOf /* 2223 */:
                if (str.equals("ET")) {
                    c3 = 'A';
                    break;
                }
                break;
            case R2.attr.layout_editor_absoluteY /* 2243 */:
                if (str.equals("FI")) {
                    c3 = 'B';
                    break;
                }
                break;
            case R2.attr.layout_flexBasisPercent /* 2244 */:
                if (str.equals("FJ")) {
                    c3 = 'C';
                    break;
                }
                break;
            case R2.attr.layout_flexGrow /* 2245 */:
                if (str.equals("FK")) {
                    c3 = 'D';
                    break;
                }
                break;
            case R2.attr.layout_goneMarginBaseline /* 2247 */:
                if (str.equals("FM")) {
                    c3 = 'E';
                    break;
                }
                break;
            case R2.attr.layout_goneMarginEnd /* 2249 */:
                if (str.equals("FO")) {
                    c3 = 'F';
                    break;
                }
                break;
            case R2.attr.layout_goneMarginStart /* 2252 */:
                if (str.equals("FR")) {
                    c3 = 'G';
                    break;
                }
                break;
            case R2.attr.layout_rowSpan /* 2266 */:
                if (str.equals("GA")) {
                    c3 = 'H';
                    break;
                }
                break;
            case R2.attr.layout_rowWeight /* 2267 */:
                if (str.equals("GB")) {
                    c3 = 'I';
                    break;
                }
                break;
            case R2.attr.layout_scrollFlags /* 2269 */:
                if (str.equals("GD")) {
                    c3 = 'J';
                    break;
                }
                break;
            case R2.attr.layout_scrollInterpolator /* 2270 */:
                if (str.equals("GE")) {
                    c3 = 'K';
                    break;
                }
                break;
            case R2.attr.layout_srlBackgroundColor /* 2271 */:
                if (str.equals("GF")) {
                    c3 = 'L';
                    break;
                }
                break;
            case R2.attr.layout_srlSpinnerStyle /* 2272 */:
                if (str.equals("GG")) {
                    c3 = 'M';
                    break;
                }
                break;
            case R2.attr.layout_tab_convex_height /* 2273 */:
                if (str.equals("GH")) {
                    c3 = 'N';
                    break;
                }
                break;
            case R2.attr.layout_tab_height /* 2274 */:
                if (str.equals("GI")) {
                    c3 = 'O';
                    break;
                }
                break;
            case R2.attr.layout_tab_indicator_content_id /* 2277 */:
                if (str.equals("GL")) {
                    c3 = 'P';
                    break;
                }
                break;
            case R2.attr.layout_tab_indicator_content_index /* 2278 */:
                if (str.equals("GM")) {
                    c3 = 'Q';
                    break;
                }
                break;
            case R2.attr.layout_tab_text_view_id /* 2279 */:
                if (str.equals("GN")) {
                    c3 = 'R';
                    break;
                }
                break;
            case R2.attr.layout_tab_weight /* 2281 */:
                if (str.equals("GP")) {
                    c3 = 'S';
                    break;
                }
                break;
            case R2.attr.layout_tab_width /* 2282 */:
                if (str.equals("GQ")) {
                    c3 = 'T';
                    break;
                }
                break;
            case R2.attr.layout_wrapBefore /* 2283 */:
                if (str.equals("GR")) {
                    c3 = 'U';
                    break;
                }
                break;
            case R2.attr.learn_center_top_bg /* 2285 */:
                if (str.equals("GT")) {
                    c3 = 'V';
                    break;
                }
                break;
            case R2.attr.learn_center_top_end_color /* 2286 */:
                if (str.equals("GU")) {
                    c3 = 'W';
                    break;
                }
                break;
            case R2.attr.learn_center_top_stroke__color /* 2288 */:
                if (str.equals("GW")) {
                    c3 = 'X';
                    break;
                }
                break;
            case R2.attr.leftIcon /* 2290 */:
                if (str.equals("GY")) {
                    c3 = 'Y';
                    break;
                }
                break;
            case R2.attr.line_all_rounds /* 2307 */:
                if (str.equals("HK")) {
                    c3 = 'Z';
                    break;
                }
                break;
            case R2.attr.line_color /* 2310 */:
                if (str.equals("HN")) {
                    c3 = '[';
                    break;
                }
                break;
            case R2.attr.line_txt_color /* 2314 */:
                if (str.equals("HR")) {
                    c3 = '\\';
                    break;
                }
                break;
            case R2.attr.linetype1 /* 2316 */:
                if (str.equals("HT")) {
                    c3 = ']';
                    break;
                }
                break;
            case R2.attr.linetype6 /* 2317 */:
                if (str.equals("HU")) {
                    c3 = '^';
                    break;
                }
                break;
            case R2.attr.listPreferredItemPaddingEnd /* 2331 */:
                if (str.equals("ID")) {
                    c3 = '_';
                    break;
                }
                break;
            case R2.attr.listPreferredItemPaddingLeft /* 2332 */:
                if (str.equals("IE")) {
                    c3 = '`';
                    break;
                }
                break;
            case R2.attr.livingimg /* 2339 */:
                if (str.equals("IL")) {
                    c3 = 'a';
                    break;
                }
                break;
            case R2.attr.liwu /* 2340 */:
                if (str.equals("IM")) {
                    c3 = 'b';
                    break;
                }
                break;
            case R2.attr.load_anim /* 2341 */:
                if (str.equals("IN")) {
                    c3 = 'c';
                    break;
                }
                break;
            case R2.attr.load_landing_page_in_background /* 2342 */:
                if (str.equals("IO")) {
                    c3 = 'd';
                    break;
                }
                break;
            case R2.attr.loading_dialog_text_color /* 2344 */:
                if (str.equals("IQ")) {
                    c3 = 'e';
                    break;
                }
                break;
            case R2.attr.loading_txt /* 2345 */:
                if (str.equals("IR")) {
                    c3 = 'f';
                    break;
                }
                break;
            case R2.attr.loading_view_color /* 2346 */:
                if (str.equals("IS")) {
                    c3 = 'g';
                    break;
                }
                break;
            case 2347:
                if (str.equals("IT")) {
                    c3 = 'h';
                    break;
                }
                break;
            case R2.attr.loopCount /* 2363 */:
                if (str.equals("JE")) {
                    c3 = 'i';
                    break;
                }
                break;
            case R2.attr.lottie_fileName /* 2371 */:
                if (str.equals("JM")) {
                    c3 = 'j';
                    break;
                }
                break;
            case R2.attr.lottie_loop /* 2373 */:
                if (str.equals("JO")) {
                    c3 = 'k';
                    break;
                }
                break;
            case R2.attr.lottie_progress /* 2374 */:
                if (str.equals("JP")) {
                    c3 = 'l';
                    break;
                }
                break;
            case R2.attr.main_theme_one_deep_color /* 2394 */:
                if (str.equals("KE")) {
                    c3 = 'm';
                    break;
                }
                break;
            case R2.attr.main_theme_six_deep_color /* 2396 */:
                if (str.equals(ExpandedProductParsedResult.KILOGRAM)) {
                    c3 = 'n';
                    break;
                }
                break;
            case R2.attr.main_theme_three_deep_color /* 2397 */:
                if (str.equals("KH")) {
                    c3 = 'o';
                    break;
                }
                break;
            case R2.attr.main_theme_two_deep_color /* 2398 */:
                if (str.equals("KI")) {
                    c3 = 'p';
                    break;
                }
                break;
            case R2.attr.marquee_count /* 2402 */:
                if (str.equals("KM")) {
                    c3 = 'q';
                    break;
                }
                break;
            case R2.attr.marquee_textSize /* 2405 */:
                if (str.equals("KP")) {
                    c3 = 'r';
                    break;
                }
                break;
            case R2.attr.matProg_barSpinCycleTime /* 2407 */:
                if (str.equals("KR")) {
                    c3 = 's';
                    break;
                }
                break;
            case R2.attr.matProg_progressIndeterminate /* 2412 */:
                if (str.equals("KW")) {
                    c3 = 't';
                    break;
                }
                break;
            case R2.attr.matProg_rimWidth /* 2414 */:
                if (str.equals("KY")) {
                    c3 = 'u';
                    break;
                }
                break;
            case R2.attr.matProg_spinSpeed /* 2415 */:
                if (str.equals("KZ")) {
                    c3 = 'v';
                    break;
                }
                break;
            case R2.attr.materialAlertDialogTitleTextStyle /* 2421 */:
                if (str.equals("LA")) {
                    c3 = 'w';
                    break;
                }
                break;
            case R2.attr.materialButtonOutlinedStyle /* 2422 */:
                if (str.equals(ExpandedProductParsedResult.POUND)) {
                    c3 = 'x';
                    break;
                }
                break;
            case R2.attr.materialButtonStyle /* 2423 */:
                if (str.equals("LC")) {
                    c3 = 'y';
                    break;
                }
                break;
            case R2.attr.materialCalendarHeaderConfirmButton /* 2429 */:
                if (str.equals("LI")) {
                    c3 = 'z';
                    break;
                }
                break;
            case R2.attr.materialCalendarHeaderLayout /* 2431 */:
                if (str.equals("LK")) {
                    c3 = '{';
                    break;
                }
                break;
            case R2.attr.materialCalendarTheme /* 2438 */:
                if (str.equals("LR")) {
                    c3 = '|';
                    break;
                }
                break;
            case R2.attr.materialCalendarYearNavigationButton /* 2439 */:
                if (str.equals("LS")) {
                    c3 = '}';
                    break;
                }
                break;
            case R2.attr.materialCardViewElevatedStyle /* 2440 */:
                if (str.equals("LT")) {
                    c3 = '~';
                    break;
                }
                break;
            case R2.attr.materialCardViewFilledStyle /* 2441 */:
                if (str.equals("LU")) {
                    c3 = Ascii.MAX;
                    break;
                }
                break;
            case R2.attr.materialCardViewOutlinedStyle /* 2442 */:
                if (str.equals("LV")) {
                    c3 = 128;
                    break;
                }
                break;
            case R2.attr.materialClockStyle /* 2445 */:
                if (str.equals("LY")) {
                    c3 = 129;
                    break;
                }
                break;
            case R2.attr.materialTimePickerTitleStyle /* 2452 */:
                if (str.equals("MA")) {
                    c3 = 130;
                    break;
                }
                break;
            case R2.attr.maxAcceleration /* 2454 */:
                if (str.equals("MC")) {
                    c3 = 131;
                    break;
                }
                break;
            case R2.attr.maxActionInlineWidth /* 2455 */:
                if (str.equals("MD")) {
                    c3 = 132;
                    break;
                }
                break;
            case R2.attr.maxButtonHeight /* 2456 */:
                if (str.equals("ME")) {
                    c3 = 133;
                    break;
                }
                break;
            case R2.attr.maxCharacterCount /* 2457 */:
                if (str.equals("MF")) {
                    c3 = 134;
                    break;
                }
                break;
            case R2.attr.maxColumns /* 2458 */:
                if (str.equals("MG")) {
                    c3 = 135;
                    break;
                }
                break;
            case R2.attr.maxExpandLines /* 2459 */:
                if (str.equals("MH")) {
                    c3 = 136;
                    break;
                }
                break;
            case R2.attr.maxLine /* 2462 */:
                if (str.equals("MK")) {
                    c3 = 137;
                    break;
                }
                break;
            case R2.attr.maxLines /* 2463 */:
                if (str.equals("ML")) {
                    c3 = 138;
                    break;
                }
                break;
            case R2.attr.maxScore /* 2464 */:
                if (str.equals("MM")) {
                    c3 = 139;
                    break;
                }
                break;
            case R2.attr.maxSelect /* 2465 */:
                if (str.equals("MN")) {
                    c3 = 140;
                    break;
                }
                break;
            case R2.attr.maxVelocity /* 2466 */:
                if (str.equals("MO")) {
                    c3 = 141;
                    break;
                }
                break;
            case R2.attr.maxWidth /* 2467 */:
                if (str.equals("MP")) {
                    c3 = 142;
                    break;
                }
                break;
            case R2.attr.max_select /* 2468 */:
                if (str.equals("MQ")) {
                    c3 = 143;
                    break;
                }
                break;
            case R2.attr.max_selects /* 2469 */:
                if (str.equals("MR")) {
                    c3 = 144;
                    break;
                }
                break;
            case R2.attr.mdActiveIndicator /* 2470 */:
                if (str.equals("MS")) {
                    c3 = 145;
                    break;
                }
                break;
            case R2.attr.mdAllowIndicatorAnimation /* 2471 */:
                if (str.equals("MT")) {
                    c3 = 146;
                    break;
                }
                break;
            case R2.attr.mdContentBackground /* 2472 */:
                if (str.equals("MU")) {
                    c3 = 147;
                    break;
                }
                break;
            case R2.attr.mdDrawOverlay /* 2473 */:
                if (str.equals("MV")) {
                    c3 = 148;
                    break;
                }
                break;
            case R2.attr.mdDrawerClosedUpContentDescription /* 2474 */:
                if (str.equals("MW")) {
                    c3 = 149;
                    break;
                }
                break;
            case R2.attr.mdDrawerOpenUpContentDescription /* 2475 */:
                if (str.equals("MX")) {
                    c3 = 150;
                    break;
                }
                break;
            case R2.attr.mdDropShadow /* 2476 */:
                if (str.equals("MY")) {
                    c3 = 151;
                    break;
                }
                break;
            case R2.attr.mdDropShadowColor /* 2477 */:
                if (str.equals("MZ")) {
                    c3 = 152;
                    break;
                }
                break;
            case R2.attr.mdPosition /* 2483 */:
                if (str.equals("NA")) {
                    c3 = 153;
                    break;
                }
                break;
            case R2.attr.mdTouchBezelSize /* 2485 */:
                if (str.equals("NC")) {
                    c3 = 154;
                    break;
                }
                break;
            case R2.attr.meau_forum /* 2487 */:
                if (str.equals("NE")) {
                    c3 = 155;
                    break;
                }
                break;
            case R2.attr.member_unlock_way_pop_item /* 2489 */:
                if (str.equals("NG")) {
                    c3 = 156;
                    break;
                }
                break;
            case R2.attr.menu /* 2491 */:
                if (str.equals("NI")) {
                    c3 = 157;
                    break;
                }
                break;
            case R2.attr.menu_question_sort_icon /* 2494 */:
                if (str.equals("NL")) {
                    c3 = 158;
                    break;
                }
                break;
            case R2.attr.methodName /* 2497 */:
                if (str.equals("NO")) {
                    c3 = 159;
                    break;
                }
                break;
            case R2.attr.mhPrimaryColor /* 2498 */:
                if (str.equals("NP")) {
                    c3 = Typography.nbsp;
                    break;
                }
                break;
            case 2500:
                if (str.equals("NR")) {
                    c3 = 161;
                    break;
                }
                break;
            case R2.attr.min /* 2503 */:
                if (str.equals("NU")) {
                    c3 = Typography.cent;
                    break;
                }
                break;
            case R2.attr.minTouchTargetSize /* 2508 */:
                if (str.equals("NZ")) {
                    c3 = Typography.pound;
                    break;
                }
                break;
            case R2.attr.motionDurationShort2 /* 2526 */:
                if (str.equals("OM")) {
                    c3 = 164;
                    break;
                }
                break;
            case R2.attr.motionTarget /* 2545 */:
                if (str.equals("PA")) {
                    c3 = 165;
                    break;
                }
                break;
            case R2.attr.msgListMyBubbleBackground /* 2549 */:
                if (str.equals("PE")) {
                    c3 = 166;
                    break;
                }
                break;
            case R2.attr.msgListOtherBubbleBackground /* 2550 */:
                if (str.equals("PF")) {
                    c3 = Typography.section;
                    break;
                }
                break;
            case R2.attr.msgListShowUserAvatar /* 2551 */:
                if (str.equals("PG")) {
                    c3 = 168;
                    break;
                }
                break;
            case R2.attr.msgListShowUserNick /* 2552 */:
                if (str.equals("PH")) {
                    c3 = Typography.copyright;
                    break;
                }
                break;
            case R2.attr.msgTextSize /* 2555 */:
                if (str.equals("PK")) {
                    c3 = 170;
                    break;
                }
                break;
            case R2.attr.multiChoiceItemLayout /* 2556 */:
                if (str.equals("PL")) {
                    c3 = 171;
                    break;
                }
                break;
            case R2.attr.mvAnimDuration /* 2557 */:
                if (str.equals("PM")) {
                    c3 = 172;
                    break;
                }
                break;
            case R2.attr.mvSingleLine /* 2562 */:
                if (str.equals("PR")) {
                    c3 = 173;
                    break;
                }
                break;
            case R2.attr.mvTextColor /* 2563 */:
                if (str.equals("PS")) {
                    c3 = Typography.registered;
                    break;
                }
                break;
            case R2.attr.mvTextSize /* 2564 */:
                if (str.equals("PT")) {
                    c3 = 175;
                    break;
                }
                break;
            case R2.attr.navigationIcon /* 2567 */:
                if (str.equals("PW")) {
                    c3 = Typography.degree;
                    break;
                }
                break;
            case R2.attr.navigationMode /* 2569 */:
                if (str.equals("PY")) {
                    c3 = Typography.plusMinus;
                    break;
                }
                break;
            case R2.attr.network_load_fail /* 2576 */:
                if (str.equals("QA")) {
                    c3 = 178;
                    break;
                }
                break;
            case R2.attr.np_dividerLength /* 2611 */:
                if (str.equals("RE")) {
                    c3 = 179;
                    break;
                }
                break;
            case R2.attr.np_max /* 2621 */:
                if (str.equals("RO")) {
                    c3 = 180;
                    break;
                }
                break;
            case R2.attr.np_orientation /* 2625 */:
                if (str.equals("RS")) {
                    c3 = 181;
                    break;
                }
                break;
            case R2.attr.np_selectedTextAlign /* 2627 */:
                if (str.equals("RU")) {
                    c3 = Typography.paragraph;
                    break;
                }
                break;
            case R2.attr.np_selectedTextSize /* 2629 */:
                if (str.equals("RW")) {
                    c3 = Typography.middleDot;
                    break;
                }
                break;
            case R2.attr.np_typeface /* 2638 */:
                if (str.equals("SA")) {
                    c3 = 184;
                    break;
                }
                break;
            case R2.attr.np_value /* 2639 */:
                if (str.equals("SB")) {
                    c3 = 185;
                    break;
                }
                break;
            case R2.attr.np_wheelItemCount /* 2640 */:
                if (str.equals("SC")) {
                    c3 = 186;
                    break;
                }
                break;
            case R2.attr.np_width /* 2641 */:
                if (str.equals(QualityValue.QUALITY_STAND)) {
                    c3 = 187;
                    break;
                }
                break;
            case R2.attr.np_wrapSelectorWheel /* 2642 */:
                if (str.equals("SE")) {
                    c3 = 188;
                    break;
                }
                break;
            case R2.attr.npv_AlternativeTextArrayWithMeasureHint /* 2644 */:
                if (str.equals("SG")) {
                    c3 = Typography.half;
                    break;
                }
                break;
            case R2.attr.npv_AlternativeTextArrayWithoutMeasureHint /* 2645 */:
                if (str.equals("SH")) {
                    c3 = 190;
                    break;
                }
                break;
            case R2.attr.npv_DividerColor /* 2646 */:
                if (str.equals("SI")) {
                    c3 = 191;
                    break;
                }
                break;
            case R2.attr.npv_DividerHeight /* 2647 */:
                if (str.equals("SJ")) {
                    c3 = 192;
                    break;
                }
                break;
            case R2.attr.npv_DividerMarginLeft /* 2648 */:
                if (str.equals("SK")) {
                    c3 = 193;
                    break;
                }
                break;
            case R2.attr.npv_DividerMarginRight /* 2649 */:
                if (str.equals("SL")) {
                    c3 = 194;
                    break;
                }
                break;
            case R2.attr.npv_EmptyItemHint /* 2650 */:
                if (str.equals("SM")) {
                    c3 = 195;
                    break;
                }
                break;
            case R2.attr.npv_HintText /* 2651 */:
                if (str.equals("SN")) {
                    c3 = 196;
                    break;
                }
                break;
            case R2.attr.npv_ItemPaddingHorizental /* 2652 */:
                if (str.equals("SO")) {
                    c3 = 197;
                    break;
                }
                break;
            case R2.attr.npv_MarginStartOfHint /* 2655 */:
                if (str.equals("SR")) {
                    c3 = 198;
                    break;
                }
                break;
            case R2.attr.npv_MaxValue /* 2656 */:
                if (str.equals("SS")) {
                    c3 = 199;
                    break;
                }
                break;
            case R2.attr.npv_MinValue /* 2657 */:
                if (str.equals("ST")) {
                    c3 = 200;
                    break;
                }
                break;
            case R2.attr.npv_ShowDivider /* 2659 */:
                if (str.equals("SV")) {
                    c3 = 201;
                    break;
                }
                break;
            case R2.attr.npv_TextColorHint /* 2661 */:
                if (str.equals("SX")) {
                    c3 = 202;
                    break;
                }
                break;
            case R2.attr.npv_TextColorNormal /* 2662 */:
                if (str.equals("SY")) {
                    c3 = 203;
                    break;
                }
                break;
            case R2.attr.npv_TextColorSelected /* 2663 */:
                if (str.equals("SZ")) {
                    c3 = 204;
                    break;
                }
                break;
            case R2.attr.nrb_starFullResource /* 2671 */:
                if (str.equals("TC")) {
                    c3 = 205;
                    break;
                }
                break;
            case R2.attr.nrb_starHalfResource /* 2672 */:
                if (str.equals("TD")) {
                    c3 = 206;
                    break;
                }
                break;
            case R2.attr.nrb_starImageWidth /* 2675 */:
                if (str.equals("TG")) {
                    c3 = 207;
                    break;
                }
                break;
            case R2.attr.nrb_starTotal /* 2676 */:
                if (str.equals("TH")) {
                    c3 = 208;
                    break;
                }
                break;
            case R2.attr.nts_animation_duration /* 2678 */:
                if (str.equals("TJ")) {
                    c3 = 209;
                    break;
                }
                break;
            case R2.attr.nts_corners_radius /* 2680 */:
                if (str.equals("TL")) {
                    c3 = 210;
                    break;
                }
                break;
            case R2.attr.nts_factor /* 2681 */:
                if (str.equals("TM")) {
                    c3 = 211;
                    break;
                }
                break;
            case R2.attr.nts_gravity /* 2682 */:
                if (str.equals("TN")) {
                    c3 = 212;
                    break;
                }
                break;
            case R2.attr.nts_inactive_color /* 2683 */:
                if (str.equals("TO")) {
                    c3 = 213;
                    break;
                }
                break;
            case R2.attr.nts_type /* 2686 */:
                if (str.equals("TR")) {
                    c3 = 214;
                    break;
                }
                break;
            case R2.attr.nts_weight /* 2688 */:
                if (str.equals("TT")) {
                    c3 = Typography.times;
                    break;
                }
                break;
            case R2.attr.numRows /* 2690 */:
                if (str.equals("TV")) {
                    c3 = 216;
                    break;
                }
                break;
            case R2.attr.number /* 2691 */:
                if (str.equals("TW")) {
                    c3 = 217;
                    break;
                }
                break;
            case R2.attr.numberPickerStyle /* 2694 */:
                if (str.equals("TZ")) {
                    c3 = 218;
                    break;
                }
                break;
            case R2.attr.onCross /* 2700 */:
                if (str.equals("UA")) {
                    c3 = 219;
                    break;
                }
                break;
            case R2.attr.onTouchUp /* 2706 */:
                if (str.equals("UG")) {
                    c3 = 220;
                    break;
                }
                break;
            case R2.attr.otherButtonSingleBackground /* 2718 */:
                if (str.equals("US")) {
                    c3 = 221;
                    break;
                }
                break;
            case R2.attr.outlineColor /* 2724 */:
                if (str.equals("UY")) {
                    c3 = 222;
                    break;
                }
                break;
            case R2.attr.outlineEnabled /* 2725 */:
                if (str.equals("UZ")) {
                    c3 = 223;
                    break;
                }
                break;
            case R2.attr.paddingRightSystemWindowInsets /* 2733 */:
                if (str.equals("VC")) {
                    c3 = 224;
                    break;
                }
                break;
            case R2.attr.paddingTopNoTitle /* 2735 */:
                if (str.equals("VE")) {
                    c3 = 225;
                    break;
                }
                break;
            case R2.attr.pageColor /* 2737 */:
                if (str.equals("VG")) {
                    c3 = 226;
                    break;
                }
                break;
            case R2.attr.page_loading2 /* 2739 */:
                if (str.equals("VI")) {
                    c3 = 227;
                    break;
                }
                break;
            case R2.attr.page_loading7 /* 2744 */:
                if (str.equals("VN")) {
                    c3 = 228;
                    break;
                }
                break;
            case R2.attr.paramName /* 2751 */:
                if (str.equals("VU")) {
                    c3 = 229;
                    break;
                }
                break;
            case R2.attr.persistent /* 2767 */:
                if (str.equals("WF")) {
                    c3 = 230;
                    break;
                }
                break;
            case R2.attr.personal_set /* 2780 */:
                if (str.equals("WS")) {
                    c3 = 231;
                    break;
                }
                break;
            case R2.attr.plvDrawShadow /* 2803 */:
                if (str.equals("XK")) {
                    c3 = 232;
                    break;
                }
                break;
            case R2.attr.pointSize /* 2828 */:
                if (str.equals("YE")) {
                    c3 = 233;
                    break;
                }
                break;
            case R2.attr.prb_tickNormalDrawable /* 2843 */:
                if (str.equals("YT")) {
                    c3 = 234;
                    break;
                }
                break;
            case R2.attr.preferenceLayoutChild /* 2855 */:
                if (str.equals("ZA")) {
                    c3 = 235;
                    break;
                }
                break;
            case R2.attr.prey_new_font1 /* 2867 */:
                if (str.equals("ZM")) {
                    c3 = 236;
                    break;
                }
                break;
            case R2.attr.progressBarStyle /* 2877 */:
                if (str.equals("ZW")) {
                    c3 = 237;
                    break;
                }
                break;
        }
        switch (c3) {
            case 0:
            case 26:
            case 'P':
            case 'y':
                return new int[]{1, 2, 0, 0, 2, 2};
            case 1:
                return new int[]{1, 4, 4, 4, 3, 2};
            case 2:
            case ']':
            case 155:
            case 187:
            case R2.array.ease_pages_file_suffix /* 196 */:
            case 206:
            case 225:
            case 233:
                return new int[]{4, 4, 4, 4, 2, 2};
            case 3:
                return new int[]{2, 3, 1, 2, 2, 2};
            case 4:
            case 25:
            case '3':
            case '9':
            case 'J':
            case 145:
            case 224:
                return new int[]{1, 2, 2, 2, 2, 2};
            case 5:
            case 16:
            case 'u':
                return new int[]{1, 2, 0, 1, 2, 2};
            case 6:
                return new int[]{2, 3, 2, 4, 2, 2};
            case 7:
            case ',':
                return new int[]{3, 4, 3, 2, 2, 2};
            case '\b':
            case '?':
            case 'd':
            case 162:
            case R2.array.ease_excel_file_suffix /* 190 */:
            case 199:
            case 216:
                return new int[]{4, 2, 2, 2, 2, 2};
            case '\t':
                return new int[]{2, 4, 1, 1, 2, 2};
            case '\n':
                return new int[]{2, 2, 2, 3, 2, 2};
            case 11:
            case '(':
            case 'g':
            case 188:
            case 193:
                return new int[]{0, 0, 0, 0, 0, 2};
            case '\f':
                return new int[]{0, 1, 0, 1, 2, 2};
            case '\r':
            case 'W':
                return new int[]{1, 2, 4, 4, 2, 2};
            case 14:
            case 'O':
            case 'z':
            case 142:
            case 172:
            case 192:
            case R2.array.ease_other_file_suffix /* 195 */:
                return new int[]{0, 2, 2, 2, 2, 2};
            case 15:
            case 154:
                return new int[]{3, 2, 4, 4, 2, 2};
            case 17:
            case 'F':
            case 'M':
                return new int[]{0, 2, 0, 0, 2, 2};
            case 18:
            case 151:
                return new int[]{2, 1, 3, 3, 2, 2};
            case 19:
                return new int[]{0, 0, 3, 3, 2, 2};
            case 20:
                return new int[]{4, 3, 4, 3, 2, 2};
            case 21:
            case '5':
            case R2.array.ease_file_file_suffix /* 191 */:
                return new int[]{0, 0, 0, 0, 1, 2};
            case 22:
                return new int[]{1, 2, 2, 4, 4, 2};
            case 23:
            case ';':
            case 203:
            case 210:
                return new int[]{4, 3, 4, 4, 2, 2};
            case 24:
                return new int[]{4, 4, 3, 4, 2, 2};
            case 27:
                return new int[]{3, 2, 1, 1, 2, 2};
            case 28:
                return new int[]{1, 3, 3, 2, 2, 2};
            case 29:
                return new int[]{1, 2, 2, 0, 2, 2};
            case 30:
            case 198:
                return new int[]{2, 3, 2, 2, 2, 2};
            case 31:
                return new int[]{4, 2, 2, 3, 2, 2};
            case ' ':
                return new int[]{3, 1, 3, 2, 2, 2};
            case '!':
            case 'Y':
                return new int[]{3, 4, 1, 0, 2, 2};
            case '\"':
                return new int[]{0, 1, 1, 3, 2, 2};
            case '#':
                return new int[]{2, 4, 2, 2, 2, 2};
            case '$':
                return new int[]{0, 2, 1, 2, 4, 1};
            case '%':
                return new int[]{4, 2, 3, 1, 2, 2};
            case '&':
                return new int[]{4, 2, 3, 2, 2, 2};
            case '\'':
            case 150:
                return new int[]{2, 4, 3, 4, 2, 2};
            case ')':
                return new int[]{3, 3, 3, 4, 2, 2};
            case '*':
                return new int[]{2, 2, 2, 1, 2, 2};
            case '+':
            case R2.anim.widget_zoom_in /* 171 */:
            case 221:
                return new int[]{1, 1, 2, 2, 3, 2};
            case '-':
                return new int[]{2, 0, 2, 2, 3, 1};
            case '.':
                return new int[]{2, 2, 4, 2, 2, 2};
            case '/':
                return new int[]{2, 2, 4, 4, 2, 2};
            case '0':
            case R2.anim.voice_from_icon /* 168 */:
                return new int[]{4, 4, 3, 2, 2, 2};
            case '1':
                return new int[]{2, 3, 1, 0, 2, 2};
            case '2':
                return new int[]{2, 2, 0, 0, 2, 2};
            case '4':
                return new int[]{1, 0, 0, 0, 1, 2};
            case '6':
                return new int[]{0, 0, 2, 2, 1, 2};
            case '7':
                return new int[]{4, 1, 4, 4, 2, 2};
            case '8':
                return new int[]{0, 0, 1, 0, 0, 2};
            case ':':
            case 'j':
                return new int[]{3, 4, 4, 4, 2, 2};
            case '<':
                return new int[]{2, 4, 2, 1, 2, 2};
            case '=':
            case '~':
            case 128:
            case 146:
                return new int[]{0, 0, 0, 0, 2, 2};
            case '>':
                return new int[]{3, 4, 2, 3, 2, 2};
            case '@':
            case '`':
                return new int[]{0, 1, 1, 1, 2, 2};
            case 'A':
                return new int[]{4, 4, 3, 1, 2, 2};
            case 'B':
                return new int[]{0, 0, 0, 1, 0, 2};
            case 'C':
                return new int[]{3, 1, 3, 3, 2, 2};
            case 'D':
            case 'r':
            case 130:
            case 152:
            case 200:
                return new int[]{3, 2, 2, 2, 2, 2};
            case 'E':
                return new int[]{3, 2, 4, 2, 2, 2};
            case 'G':
                return new int[]{1, 1, 2, 1, 1, 1};
            case 'H':
                return new int[]{2, 3, 1, 1, 2, 2};
            case 'I':
                return new int[]{0, 0, 1, 1, 2, 3};
            case 'K':
                return new int[]{1, 1, 1, 3, 2, 2};
            case 'L':
            case 'S':
            case 143:
                return new int[]{2, 1, 2, 3, 2, 2};
            case 'N':
                return new int[]{3, 2, 3, 2, 2, 2};
            case 'Q':
            case 230:
                return new int[]{4, 2, 2, 4, 2, 2};
            case 'R':
                return new int[]{4, 3, 4, 2, 2, 2};
            case 'T':
                return new int[]{4, 2, 3, 4, 2, 2};
            case 'U':
            case '\\':
            case 132:
            case 137:
            case 181:
                return new int[]{1, 0, 0, 0, 2, 2};
            case 'V':
                return new int[]{2, 3, 2, 1, 2, 2};
            case 'X':
            case 218:
                return new int[]{3, 4, 3, 3, 2, 2};
            case 'Z':
                return new int[]{0, 1, 2, 3, 2, 0};
            case '[':
            case R2.anim.welcome_loading /* 170 */:
            case R2.array.ease_pdf_file_suffix /* 197 */:
                return new int[]{3, 2, 3, 3, 2, 2};
            case '^':
                return new int[]{0, 0, 0, 1, 3, 2};
            case '_':
                return new int[]{3, 2, 3, 3, 3, 2};
            case 'a':
                return new int[]{1, 1, 2, 3, 4, 2};
            case 'b':
                return new int[]{0, 2, 0, 1, 2, 2};
            case 'c':
                return new int[]{1, 1, 3, 2, 4, 3};
            case 'e':
            case 220:
                return new int[]{3, 3, 3, 3, 2, 2};
            case 'f':
                return new int[]{3, 0, 1, 1, 3, 0};
            case 'h':
                return new int[]{0, 1, 0, 1, 1, 2};
            case 'i':
                return new int[]{3, 2, 1, 2, 2, 2};
            case 'k':
            case 133:
                return new int[]{1, 0, 0, 1, 2, 2};
            case 'l':
                return new int[]{0, 1, 0, 1, 1, 1};
            case 'm':
                return new int[]{3, 3, 2, 2, 2, 2};
            case 'n':
                return new int[]{2, 1, 1, 1, 2, 2};
            case 'o':
                return new int[]{1, 1, 4, 2, 2, 2};
            case 'p':
            case 'q':
            case 129:
            case 185:
                return new int[]{4, 2, 4, 3, 2, 2};
            case 's':
                return new int[]{0, 0, 1, 3, 4, 4};
            case 't':
                return new int[]{1, 1, 0, 0, 0, 2};
            case 'v':
                return new int[]{1, 1, 2, 2, 2, 2};
            case 'w':
            case 222:
                return new int[]{2, 2, 1, 2, 2, 2};
            case 'x':
                return new int[]{3, 2, 1, 4, 2, 2};
            case '{':
                return new int[]{3, 1, 3, 4, 4, 2};
            case '|':
                return new int[]{3, 4, 4, 3, 2, 2};
            case '}':
                return new int[]{3, 3, 4, 3, 2, 2};
            case 127:
                return new int[]{1, 0, 2, 2, 2, 2};
            case 131:
                return new int[]{0, 2, 2, 0, 2, 2};
            case 134:
                return new int[]{1, 2, 1, 0, 2, 2};
            case 135:
                return new int[]{3, 4, 2, 2, 2, 2};
            case 136:
                return new int[]{3, 2, 2, 4, 2, 2};
            case 138:
                return new int[]{4, 3, 3, 1, 2, 2};
            case 139:
                return new int[]{2, 4, 3, 3, 2, 2};
            case 140:
                return new int[]{2, 0, 1, 2, 2, 2};
            case 141:
                return new int[]{0, 2, 4, 4, 2, 2};
            case 144:
                return new int[]{4, 1, 3, 4, 2, 2};
            case 147:
                return new int[]{3, 1, 1, 2, 2, 2};
            case 148:
                return new int[]{3, 4, 1, 4, 2, 2};
            case 149:
                return new int[]{4, 2, 1, 0, 2, 2};
            case 153:
                return new int[]{4, 3, 2, 2, 2, 2};
            case 156:
                return new int[]{3, 4, 1, 1, 2, 2};
            case 157:
                return new int[]{2, 3, 4, 3, 2, 2};
            case 158:
                return new int[]{0, 0, 3, 2, 0, 4};
            case 159:
                return new int[]{0, 0, 2, 0, 0, 2};
            case 160:
                return new int[]{2, 1, 4, 3, 2, 2};
            case 161:
                return new int[]{3, 2, 2, 0, 2, 2};
            case 163:
                return new int[]{1, 0, 1, 2, 4, 2};
            case 164:
                return new int[]{2, 3, 1, 3, 4, 2};
            case 165:
                return new int[]{1, 3, 3, 3, 2, 2};
            case 166:
                return new int[]{2, 3, 4, 4, 4, 2};
            case 167:
                return new int[]{2, 3, 3, 1, 2, 2};
            case 169:
                return new int[]{2, 2, 3, 3, 3, 2};
            case R2.anim.window_bottom_out /* 173 */:
                return new int[]{2, 3, 2, 2, 3, 3};
            case R2.anim.window_ios_in /* 174 */:
                return new int[]{3, 4, 1, 2, 2, 2};
            case R2.anim.window_ios_out /* 175 */:
                return new int[]{0, 1, 0, 0, 2, 2};
            case 176:
                return new int[]{2, 2, 4, 1, 2, 2};
            case 177:
                return new int[]{2, 2, 3, 2, 2, 2};
            case 178:
                return new int[]{2, 4, 2, 4, 4, 2};
            case 179:
                return new int[]{1, 1, 1, 2, 2, 2};
            case 180:
                return new int[]{0, 0, 1, 1, 1, 2};
            case 182:
                return new int[]{0, 0, 0, 1, 2, 2};
            case 183:
                return new int[]{3, 4, 3, 0, 2, 2};
            case 184:
            case 212:
            case 226:
                return new int[]{2, 2, 1, 1, 2, 2};
            case 186:
                return new int[]{4, 3, 0, 2, 2, 2};
            case 189:
                return new int[]{1, 1, 2, 3, 1, 4};
            case R2.array.ease_numbers_file_suffix /* 194 */:
                return new int[]{4, 3, 4, 1, 2, 2};
            case 201:
                return new int[]{2, 2, 3, 3, 2, 2};
            case 202:
            case 205:
                return new int[]{2, 2, 1, 0, 2, 2};
            case 204:
                return new int[]{4, 3, 2, 4, 2, 2};
            case 207:
                return new int[]{3, 3, 2, 0, 2, 2};
            case 208:
                return new int[]{0, 3, 2, 3, 3, 0};
            case 209:
                return new int[]{4, 2, 4, 4, 2, 2};
            case 211:
                return new int[]{4, 2, 4, 2, 2, 2};
            case 213:
                return new int[]{4, 2, 3, 3, 2, 2};
            case 214:
                return new int[]{1, 1, 0, 1, 2, 2};
            case 215:
                return new int[]{1, 4, 1, 1, 2, 2};
            case 217:
                return new int[]{0, 0, 0, 0, 0, 0};
            case 219:
                return new int[]{0, 3, 1, 1, 2, 2};
            case 223:
                return new int[]{2, 2, 3, 4, 2, 2};
            case 227:
                return new int[]{1, 2, 1, 3, 2, 2};
            case 228:
                return new int[]{0, 3, 3, 4, 2, 2};
            case 229:
                return new int[]{4, 2, 2, 1, 2, 2};
            case 231:
                return new int[]{3, 1, 2, 1, 2, 2};
            case 232:
                return new int[]{1, 1, 1, 1, 2, 2};
            case 234:
                return new int[]{4, 1, 1, 1, 2, 2};
            case 235:
                return new int[]{3, 3, 1, 1, 1, 2};
            case 236:
                return new int[]{3, 3, 4, 2, 2, 2};
            case 237:
                return new int[]{3, 2, 4, 3, 2, 2};
            default:
                return new int[]{2, 2, 2, 2, 2, 2};
        }
    }

    private long getInitialBitrateEstimateForNetworkType(int i2) {
        Long l2 = this.initialBitrateEstimates.get(Integer.valueOf(i2));
        if (l2 == null) {
            l2 = this.initialBitrateEstimates.get(0);
        }
        if (l2 == null) {
            l2 = 1000000L;
        }
        return l2.longValue();
    }

    public static synchronized DefaultBandwidthMeter getSingletonInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new Builder(context).build();
        }
        return singletonInstance;
    }

    private static boolean isTransferAtFullNetworkSpeed(DataSpec dataSpec, boolean z2) {
        return z2 && !dataSpec.isFlagSet(8);
    }

    private void maybeNotifyBandwidthSample(int i2, long j2, long j3) {
        if (i2 == 0 && j2 == 0 && j3 == this.lastReportedBitrateEstimate) {
            return;
        }
        this.lastReportedBitrateEstimate = j3;
        this.eventDispatcher.bandwidthSample(i2, j2, j3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void onNetworkTypeChanged(int i2) {
        int i3 = this.networkType;
        if (i3 == 0 || this.resetOnNetworkTypeChange) {
            if (this.networkTypeOverrideSet) {
                i2 = this.networkTypeOverride;
            }
            if (i3 == i2) {
                return;
            }
            this.networkType = i2;
            if (i2 != 1 && i2 != 0 && i2 != 8) {
                this.bitrateEstimate = getInitialBitrateEstimateForNetworkType(i2);
                long jElapsedRealtime = this.clock.elapsedRealtime();
                maybeNotifyBandwidthSample(this.streamCount > 0 ? (int) (jElapsedRealtime - this.sampleStartTimeMs) : 0, this.sampleBytesTransferred, this.bitrateEstimate);
                this.sampleStartTimeMs = jElapsedRealtime;
                this.sampleBytesTransferred = 0L;
                this.totalBytesTransferred = 0L;
                this.totalElapsedTimeMs = 0L;
                this.slidingPercentile.reset();
            }
        }
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public void addEventListener(Handler handler, BandwidthMeter.EventListener eventListener) {
        Assertions.checkNotNull(handler);
        Assertions.checkNotNull(eventListener);
        this.eventDispatcher.addListener(handler, eventListener);
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public synchronized long getBitrateEstimate() {
        return this.bitrateEstimate;
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public /* synthetic */ long getTimeToFirstByteEstimateUs() {
        return a.a(this);
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public TransferListener getTransferListener() {
        return this;
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onBytesTransferred(DataSource dataSource, DataSpec dataSpec, boolean z2, int i2) {
        if (isTransferAtFullNetworkSpeed(dataSpec, z2)) {
            this.sampleBytesTransferred += i2;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onTransferEnd(DataSource dataSource, DataSpec dataSpec, boolean z2) {
        if (isTransferAtFullNetworkSpeed(dataSpec, z2)) {
            Assertions.checkState(this.streamCount > 0);
            long jElapsedRealtime = this.clock.elapsedRealtime();
            int i2 = (int) (jElapsedRealtime - this.sampleStartTimeMs);
            this.totalElapsedTimeMs += i2;
            long j2 = this.totalBytesTransferred;
            long j3 = this.sampleBytesTransferred;
            this.totalBytesTransferred = j2 + j3;
            if (i2 > 0) {
                this.slidingPercentile.addSample((int) Math.sqrt(j3), (j3 * 8000.0f) / i2);
                if (this.totalElapsedTimeMs >= ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS || this.totalBytesTransferred >= PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE_ENABLED) {
                    this.bitrateEstimate = (long) this.slidingPercentile.getPercentile(0.5f);
                }
                maybeNotifyBandwidthSample(i2, this.sampleBytesTransferred, this.bitrateEstimate);
                this.sampleStartTimeMs = jElapsedRealtime;
                this.sampleBytesTransferred = 0L;
            }
            this.streamCount--;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public void onTransferInitializing(DataSource dataSource, DataSpec dataSpec, boolean z2) {
    }

    @Override // com.google.android.exoplayer2.upstream.TransferListener
    public synchronized void onTransferStart(DataSource dataSource, DataSpec dataSpec, boolean z2) {
        if (isTransferAtFullNetworkSpeed(dataSpec, z2)) {
            if (this.streamCount == 0) {
                this.sampleStartTimeMs = this.clock.elapsedRealtime();
            }
            this.streamCount++;
        }
    }

    @Override // com.google.android.exoplayer2.upstream.BandwidthMeter
    public void removeEventListener(BandwidthMeter.EventListener eventListener) {
        this.eventDispatcher.removeListener(eventListener);
    }

    public synchronized void setNetworkTypeOverride(int i2) {
        this.networkTypeOverride = i2;
        this.networkTypeOverrideSet = true;
        onNetworkTypeChanged(i2);
    }

    public static final class Builder {
        private Clock clock;

        @Nullable
        private final Context context;
        private Map<Integer, Long> initialBitrateEstimates;
        private boolean resetOnNetworkTypeChange;
        private int slidingWindowMaxWeight;

        public Builder(Context context) {
            this.context = context == null ? null : context.getApplicationContext();
            this.initialBitrateEstimates = getInitialBitrateEstimatesForCountry(Util.getCountryCode(context));
            this.slidingWindowMaxWeight = 2000;
            this.clock = Clock.DEFAULT;
            this.resetOnNetworkTypeChange = true;
        }

        private static Map<Integer, Long> getInitialBitrateEstimatesForCountry(String str) {
            int[] initialBitrateCountryGroupAssignment = DefaultBandwidthMeter.getInitialBitrateCountryGroupAssignment(str);
            HashMap map = new HashMap(8);
            map.put(0, 1000000L);
            ImmutableList<Long> immutableList = DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_WIFI;
            map.put(2, immutableList.get(initialBitrateCountryGroupAssignment[0]));
            map.put(3, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_2G.get(initialBitrateCountryGroupAssignment[1]));
            map.put(4, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_3G.get(initialBitrateCountryGroupAssignment[2]));
            map.put(5, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_4G.get(initialBitrateCountryGroupAssignment[3]));
            map.put(10, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_NSA.get(initialBitrateCountryGroupAssignment[4]));
            map.put(9, DefaultBandwidthMeter.DEFAULT_INITIAL_BITRATE_ESTIMATES_5G_SA.get(initialBitrateCountryGroupAssignment[5]));
            map.put(7, immutableList.get(initialBitrateCountryGroupAssignment[0]));
            return map;
        }

        public DefaultBandwidthMeter build() {
            return new DefaultBandwidthMeter(this.context, this.initialBitrateEstimates, this.slidingWindowMaxWeight, this.clock, this.resetOnNetworkTypeChange);
        }

        public Builder setClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Builder setInitialBitrateEstimate(long j2) {
            Iterator<Integer> it = this.initialBitrateEstimates.keySet().iterator();
            while (it.hasNext()) {
                setInitialBitrateEstimate(it.next().intValue(), j2);
            }
            return this;
        }

        public Builder setResetOnNetworkTypeChange(boolean z2) {
            this.resetOnNetworkTypeChange = z2;
            return this;
        }

        public Builder setSlidingWindowMaxWeight(int i2) {
            this.slidingWindowMaxWeight = i2;
            return this;
        }

        public Builder setInitialBitrateEstimate(int i2, long j2) {
            this.initialBitrateEstimates.put(Integer.valueOf(i2), Long.valueOf(j2));
            return this;
        }

        public Builder setInitialBitrateEstimate(String str) {
            this.initialBitrateEstimates = getInitialBitrateEstimatesForCountry(Ascii.toUpperCase(str));
            return this;
        }
    }

    @Deprecated
    public DefaultBandwidthMeter() {
        this(null, ImmutableMap.of(), 2000, Clock.DEFAULT, false);
    }

    private DefaultBandwidthMeter(@Nullable Context context, Map<Integer, Long> map, int i2, Clock clock, boolean z2) {
        this.initialBitrateEstimates = ImmutableMap.copyOf((Map) map);
        this.eventDispatcher = new BandwidthMeter.EventListener.EventDispatcher();
        this.slidingPercentile = new SlidingPercentile(i2);
        this.clock = clock;
        this.resetOnNetworkTypeChange = z2;
        if (context != null) {
            NetworkTypeObserver networkTypeObserver = NetworkTypeObserver.getInstance(context);
            int networkType = networkTypeObserver.getNetworkType();
            this.networkType = networkType;
            this.bitrateEstimate = getInitialBitrateEstimateForNetworkType(networkType);
            networkTypeObserver.register(new NetworkTypeObserver.Listener() { // from class: com.google.android.exoplayer2.upstream.g
                @Override // com.google.android.exoplayer2.util.NetworkTypeObserver.Listener
                public final void onNetworkTypeChanged(int i3) {
                    this.f6970a.onNetworkTypeChanged(i3);
                }
            });
            return;
        }
        this.networkType = 0;
        this.bitrateEstimate = getInitialBitrateEstimateForNetworkType(0);
    }
}
