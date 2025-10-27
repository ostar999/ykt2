package com.hyphenate.util;

import android.text.TextUtils;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes4.dex */
public class HanziToPinyin {
    private static final boolean DEBUG = false;
    private static final String FIRST_PINYIN_UNIHAN = "阿";
    private static final String LAST_PINYIN_UNIHAN = "鿿";
    private static final String TAG = "HanziToPinyin";
    private static HanziToPinyin sInstance;
    private final boolean mHasChinaCollator;
    public static final char[] UNIHANS = {38463, 21710, 23433, 32942, 20985, 20843, 25344, 25203, 37030, 21241, 38466, 22868, 20283, 23620, 36793, 28780, 24971, 27715, 20907, 30326, 23788, 22163, 20594, 21442, 20179, 25761, 20874, 23934, 26365, 26366, 23652, 21449, 33414, 36799, 20261, 25220, 36710, 25275, 27784, 27785, 38455, 21507, 20805, 25277, 20986, 27451, 25571, 24027, 20997, 21561, 26110, 36916, 21618, 21254, 20945, 31895, 27718, 23828, 37032, 25619, 21649, 21574, 20025, 24403, 20992, 22042, 25189, 28783, 27664, 22002, 30008, 20993, 29241, 19969, 19999, 19996, 21562, 21438, 32785, 35176, 21544, 22810, 22968, 35830, 22848, 38821, 20799, 21457, 24070, 21274, 39134, 20998, 20016, 35205, 20175, 32017, 20245, 26094, 20357, 29976, 20872, 30347, 25096, 32473, 26681, 21039, 24037, 21246, 20272, 29916, 20054, 20851, 20809, 24402, 20008, 21593, 21704, 21645, 20292, 22831, 33568, 35779, 40658, 25323, 20136, 22135, 21503, 40769, 20079, 33457, 24576, 29375, 24031, 28784, 26127, 21529, 19980, 21152, 25099, 27743, 33405, 38454, 24062, 22357, 20866, 20009, 20965, 23010, 22104, 20891, 21652, 24320, 21002, 24572, 23611, 21308, 32910, 21157, 31354, 25248, 25181, 22840, 33967, 23485, 21281, 20111, 22372, 25193, 22403, 26469, 20848, 21879, 25438, 32907, 21202, 23834, 21013, 20457, 22849, 33391, 25769, 21015, 25294, 21026, 28316, 22230, 40857, 30620, 22108, 23048, 30055, 25249, 32599, 21603, 22920, 22475, 23258, 29284, 29483, 20040, 21573, 38376, 30015, 21674, 23424, 21941, 20060, 27665, 21517, 35884, 25720, 21726, 27626, 21999, 25295, 33097, 22241, 22228, 23404, 30098, 23070, 24641, 33021, 22958, 25288, 23330, 40479, 25423, 22236, 23425, 22942, 20892, 32698, 22900, 22907, 30111, 40641, 37069, 21908, 35764, 22929, 25293, 30469, 20051, 25243, 21624, 21943, 21257, 19989, 22248, 21117, 27669, 23000, 20050, 38027, 21078, 20166, 19971, 25488, 21315, 21595, 24708, 30335, 20146, 29381, 33422, 19992, 21306, 23761, 32570, 22795, 21605, 31331, 23046, 24825, 20154, 25172, 26085, 33592, 21433, 37018, 25404, 22567, 23121, 30628, 25468, 20200, 27618, 19977, 26706, 25531, 38314, 26862, 20711, 26432, 31579, 23665, 20260, 24368, 22882, 30003, 33688, 25938, 21319, 23608, 21454, 20070, 21047, 34928, 38377, 21452, 35841, 21550, 35828, 21430, 24554, 25436, 33487, 29435, 22794, 23385, 21766, 20182, 22268, 22349, 27748, 22834, 24529, 29093, 21076, 22825, 26091, 24086, 21381, 22258, 20599, 20984, 28237, 25512, 21534, 20039, 31349, 27498, 24367, 23587, 21361, 26167, 32705, 25373, 20044, 22805, 34418, 20186, 20065, 28785, 20123, 24515, 26143, 20982, 20241, 21505, 21509, 21066, 22339, 20011, 24697, 22830, 24186, 20539, 19968, 22233, 24212, 21727, 20323, 20248, 25180, 22246, 26352, 26197, 31584, 31612, 24064, 28797, 20802, 21288, 20654, 21017, 36156, 24590, 22679, 25166, 25434, 27838, 24352, 38271, 38263, 20299, 34567, 36126, 20105, 20043, 23769, 24226, 20013, 24030, 26417, 25235, 25341, 19987, 22918, 38585, 23442, 21331, 20082, 23447, 37049, 31199, 38075, 21404, 23562, 26152, 20825, 40899, 40900};
    public static final byte[][] PINYINS = {new byte[]{65, 0, 0, 0, 0, 0}, new byte[]{65, 73, 0, 0, 0, 0}, new byte[]{65, 78, 0, 0, 0, 0}, new byte[]{65, 78, 71, 0, 0, 0}, new byte[]{65, 79, 0, 0, 0, 0}, new byte[]{66, 65, 0, 0, 0, 0}, new byte[]{66, 65, 73, 0, 0, 0}, new byte[]{66, 65, 78, 0, 0, 0}, new byte[]{66, 65, 78, 71, 0, 0}, new byte[]{66, 65, 79, 0, 0, 0}, new byte[]{66, 69, 73, 0, 0, 0}, new byte[]{66, 69, 78, 0, 0, 0}, new byte[]{66, 69, 78, 71, 0, 0}, new byte[]{66, 73, 0, 0, 0, 0}, new byte[]{66, 73, 65, 78, 0, 0}, new byte[]{66, 73, 65, 79, 0, 0}, new byte[]{66, 73, 69, 0, 0, 0}, new byte[]{66, 73, 78, 0, 0, 0}, new byte[]{66, 73, 78, 71, 0, 0}, new byte[]{66, 79, 0, 0, 0, 0}, new byte[]{66, 85, 0, 0, 0, 0}, new byte[]{67, 65, 0, 0, 0, 0}, new byte[]{67, 65, 73, 0, 0, 0}, new byte[]{67, 65, 78, 0, 0, 0}, new byte[]{67, 65, 78, 71, 0, 0}, new byte[]{67, 65, 79, 0, 0, 0}, new byte[]{67, 69, 0, 0, 0, 0}, new byte[]{67, 69, 78, 0, 0, 0}, new byte[]{67, 69, 78, 71, 0, 0}, new byte[]{90, 69, 78, 71, 0, 0}, new byte[]{67, 69, 78, 71, 0, 0}, new byte[]{67, 72, 65, 0, 0, 0}, new byte[]{67, 72, 65, 73, 0, 0}, new byte[]{67, 72, 65, 78, 0, 0}, new byte[]{67, 72, 65, 78, 71, 0}, new byte[]{67, 72, 65, 79, 0, 0}, new byte[]{67, 72, 69, 0, 0, 0}, new byte[]{67, 72, 69, 78, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 69, 78, 0, 0}, new byte[]{67, 72, 69, 78, 0, 0}, new byte[]{67, 72, 69, 78, 71, 0}, new byte[]{67, 72, 73, 0, 0, 0}, new byte[]{67, 72, 79, 78, 71, 0}, new byte[]{67, 72, 79, 85, 0, 0}, new byte[]{67, 72, 85, 0, 0, 0}, new byte[]{67, 72, 85, 65, 0, 0}, new byte[]{67, 72, 85, 65, 73, 0}, new byte[]{67, 72, 85, 65, 78, 0}, new byte[]{67, 72, 85, 65, 78, 71}, new byte[]{67, 72, 85, 73, 0, 0}, new byte[]{67, 72, 85, 78, 0, 0}, new byte[]{67, 72, 85, 79, 0, 0}, new byte[]{67, 73, 0, 0, 0, 0}, new byte[]{67, 79, 78, 71, 0, 0}, new byte[]{67, 79, 85, 0, 0, 0}, new byte[]{67, 85, 0, 0, 0, 0}, new byte[]{67, 85, 65, 78, 0, 0}, new byte[]{67, 85, 73, 0, 0, 0}, new byte[]{67, 85, 78, 0, 0, 0}, new byte[]{67, 85, 79, 0, 0, 0}, new byte[]{68, 65, 0, 0, 0, 0}, new byte[]{68, 65, 73, 0, 0, 0}, new byte[]{68, 65, 78, 0, 0, 0}, new byte[]{68, 65, 78, 71, 0, 0}, new byte[]{68, 65, 79, 0, 0, 0}, new byte[]{68, 69, 0, 0, 0, 0}, new byte[]{68, 69, 78, 0, 0, 0}, new byte[]{68, 69, 78, 71, 0, 0}, new byte[]{68, 73, 0, 0, 0, 0}, new byte[]{68, 73, 65, 0, 0, 0}, new byte[]{68, 73, 65, 78, 0, 0}, new byte[]{68, 73, 65, 79, 0, 0}, new byte[]{68, 73, 69, 0, 0, 0}, new byte[]{68, 73, 78, 71, 0, 0}, new byte[]{68, 73, 85, 0, 0, 0}, new byte[]{68, 79, 78, 71, 0, 0}, new byte[]{68, 79, 85, 0, 0, 0}, new byte[]{68, 85, 0, 0, 0, 0}, new byte[]{68, 85, 65, 78, 0, 0}, new byte[]{68, 85, 73, 0, 0, 0}, new byte[]{68, 85, 78, 0, 0, 0}, new byte[]{68, 85, 79, 0, 0, 0}, new byte[]{69, 0, 0, 0, 0, 0}, new byte[]{69, 73, 0, 0, 0, 0}, new byte[]{69, 78, 0, 0, 0, 0}, new byte[]{69, 78, 71, 0, 0, 0}, new byte[]{69, 82, 0, 0, 0, 0}, new byte[]{70, 65, 0, 0, 0, 0}, new byte[]{70, 65, 78, 0, 0, 0}, new byte[]{70, 65, 78, 71, 0, 0}, new byte[]{70, 69, 73, 0, 0, 0}, new byte[]{70, 69, 78, 0, 0, 0}, new byte[]{70, 69, 78, 71, 0, 0}, new byte[]{70, 73, 65, 79, 0, 0}, new byte[]{70, 79, 0, 0, 0, 0}, new byte[]{70, 79, 85, 0, 0, 0}, new byte[]{70, 85, 0, 0, 0, 0}, new byte[]{71, 65, 0, 0, 0, 0}, new byte[]{71, 65, 73, 0, 0, 0}, new byte[]{71, 65, 78, 0, 0, 0}, new byte[]{71, 65, 78, 71, 0, 0}, new byte[]{71, 65, 79, 0, 0, 0}, new byte[]{71, 69, 0, 0, 0, 0}, new byte[]{71, 69, 73, 0, 0, 0}, new byte[]{71, 69, 78, 0, 0, 0}, new byte[]{71, 69, 78, 71, 0, 0}, new byte[]{71, 79, 78, 71, 0, 0}, new byte[]{71, 79, 85, 0, 0, 0}, new byte[]{71, 85, 0, 0, 0, 0}, new byte[]{71, 85, 65, 0, 0, 0}, new byte[]{71, 85, 65, 73, 0, 0}, new byte[]{71, 85, 65, 78, 0, 0}, new byte[]{71, 85, 65, 78, 71, 0}, new byte[]{71, 85, 73, 0, 0, 0}, new byte[]{71, 85, 78, 0, 0, 0}, new byte[]{71, 85, 79, 0, 0, 0}, new byte[]{72, 65, 0, 0, 0, 0}, new byte[]{72, 65, 73, 0, 0, 0}, new byte[]{72, 65, 78, 0, 0, 0}, new byte[]{72, 65, 78, 71, 0, 0}, new byte[]{72, 65, 79, 0, 0, 0}, new byte[]{72, 69, 0, 0, 0, 0}, new byte[]{72, 69, 73, 0, 0, 0}, new byte[]{72, 69, 78, 0, 0, 0}, new byte[]{72, 69, 78, 71, 0, 0}, new byte[]{72, 77, 0, 0, 0, 0}, new byte[]{72, 79, 78, 71, 0, 0}, new byte[]{72, 79, 85, 0, 0, 0}, new byte[]{72, 85, 0, 0, 0, 0}, new byte[]{72, 85, 65, 0, 0, 0}, new byte[]{72, 85, 65, 73, 0, 0}, new byte[]{72, 85, 65, 78, 0, 0}, new byte[]{72, 85, 65, 78, 71, 0}, new byte[]{72, 85, 73, 0, 0, 0}, new byte[]{72, 85, 78, 0, 0, 0}, new byte[]{72, 85, 79, 0, 0, 0}, new byte[]{74, 73, 0, 0, 0, 0}, new byte[]{74, 73, 65, 0, 0, 0}, new byte[]{74, 73, 65, 78, 0, 0}, new byte[]{74, 73, 65, 78, 71, 0}, new byte[]{74, 73, 65, 79, 0, 0}, new byte[]{74, 73, 69, 0, 0, 0}, new byte[]{74, 73, 78, 0, 0, 0}, new byte[]{74, 73, 78, 71, 0, 0}, new byte[]{74, 73, 79, 78, 71, 0}, new byte[]{74, 73, 85, 0, 0, 0}, new byte[]{74, 85, 0, 0, 0, 0}, new byte[]{74, 85, 65, 78, 0, 0}, new byte[]{74, 85, 69, 0, 0, 0}, new byte[]{74, 85, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 65, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 65, 73, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 65, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 65, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 65, 79, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 69, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 69, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 69, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 79, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 79, 85, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 85, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 85, 65, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 85, 65, 73, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 85, 65, 78, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 85, 65, 78, 71, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 85, 73, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 85, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGLINK, 85, 79, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 65, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 65, 73, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 65, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 65, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 65, 79, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 69, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 69, 73, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 69, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 73, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 73, 65, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 73, 65, 78, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 73, 65, 78, 71, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 73, 65, 79, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 73, 69, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 73, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 73, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 73, 85, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 79, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 79, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 79, 85, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 85, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 85, 65, 78, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 85, 69, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 85, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_LONGNAME, 85, 79, 0, 0, 0}, new byte[]{77, 0, 0, 0, 0, 0}, new byte[]{77, 65, 0, 0, 0, 0}, new byte[]{77, 65, 73, 0, 0, 0}, new byte[]{77, 65, 78, 0, 0, 0}, new byte[]{77, 65, 78, 71, 0, 0}, new byte[]{77, 65, 79, 0, 0, 0}, new byte[]{77, 69, 0, 0, 0, 0}, new byte[]{77, 69, 73, 0, 0, 0}, new byte[]{77, 69, 78, 0, 0, 0}, new byte[]{77, 69, 78, 71, 0, 0}, new byte[]{77, 73, 0, 0, 0, 0}, new byte[]{77, 73, 65, 78, 0, 0}, new byte[]{77, 73, 65, 79, 0, 0}, new byte[]{77, 73, 69, 0, 0, 0}, new byte[]{77, 73, 78, 0, 0, 0}, new byte[]{77, 73, 78, 71, 0, 0}, new byte[]{77, 73, 85, 0, 0, 0}, new byte[]{77, 79, 0, 0, 0, 0}, new byte[]{77, 79, 85, 0, 0, 0}, new byte[]{77, 85, 0, 0, 0, 0}, new byte[]{78, 0, 0, 0, 0, 0}, new byte[]{78, 65, 0, 0, 0, 0}, new byte[]{78, 65, 73, 0, 0, 0}, new byte[]{78, 65, 78, 0, 0, 0}, new byte[]{78, 65, 78, 71, 0, 0}, new byte[]{78, 65, 79, 0, 0, 0}, new byte[]{78, 69, 0, 0, 0, 0}, new byte[]{78, 69, 73, 0, 0, 0}, new byte[]{78, 69, 78, 0, 0, 0}, new byte[]{78, 69, 78, 71, 0, 0}, new byte[]{78, 73, 0, 0, 0, 0}, new byte[]{78, 73, 65, 78, 0, 0}, new byte[]{78, 73, 65, 78, 71, 0}, new byte[]{78, 73, 65, 79, 0, 0}, new byte[]{78, 73, 69, 0, 0, 0}, new byte[]{78, 73, 78, 0, 0, 0}, new byte[]{78, 73, 78, 71, 0, 0}, new byte[]{78, 73, 85, 0, 0, 0}, new byte[]{78, 79, 78, 71, 0, 0}, new byte[]{78, 79, 85, 0, 0, 0}, new byte[]{78, 85, 0, 0, 0, 0}, new byte[]{78, 85, 65, 78, 0, 0}, new byte[]{78, 85, 69, 0, 0, 0}, new byte[]{78, 85, 78, 0, 0, 0}, new byte[]{78, 85, 79, 0, 0, 0}, new byte[]{79, 0, 0, 0, 0, 0}, new byte[]{79, 85, 0, 0, 0, 0}, new byte[]{80, 65, 0, 0, 0, 0}, new byte[]{80, 65, 73, 0, 0, 0}, new byte[]{80, 65, 78, 0, 0, 0}, new byte[]{80, 65, 78, 71, 0, 0}, new byte[]{80, 65, 79, 0, 0, 0}, new byte[]{80, 69, 73, 0, 0, 0}, new byte[]{80, 69, 78, 0, 0, 0}, new byte[]{80, 69, 78, 71, 0, 0}, new byte[]{80, 73, 0, 0, 0, 0}, new byte[]{80, 73, 65, 78, 0, 0}, new byte[]{80, 73, 65, 79, 0, 0}, new byte[]{80, 73, 69, 0, 0, 0}, new byte[]{80, 73, 78, 0, 0, 0}, new byte[]{80, 73, 78, 71, 0, 0}, new byte[]{80, 79, 0, 0, 0, 0}, new byte[]{80, 79, 85, 0, 0, 0}, new byte[]{80, 85, 0, 0, 0, 0}, new byte[]{81, 73, 0, 0, 0, 0}, new byte[]{81, 73, 65, 0, 0, 0}, new byte[]{81, 73, 65, 78, 0, 0}, new byte[]{81, 73, 65, 78, 71, 0}, new byte[]{81, 73, 65, 79, 0, 0}, new byte[]{81, 73, 69, 0, 0, 0}, new byte[]{81, 73, 78, 0, 0, 0}, new byte[]{81, 73, 78, 71, 0, 0}, new byte[]{81, 73, 79, 78, 71, 0}, new byte[]{81, 73, 85, 0, 0, 0}, new byte[]{81, 85, 0, 0, 0, 0}, new byte[]{81, 85, 65, 78, 0, 0}, new byte[]{81, 85, 69, 0, 0, 0}, new byte[]{81, 85, 78, 0, 0, 0}, new byte[]{82, 65, 78, 0, 0, 0}, new byte[]{82, 65, 78, 71, 0, 0}, new byte[]{82, 65, 79, 0, 0, 0}, new byte[]{82, 69, 0, 0, 0, 0}, new byte[]{82, 69, 78, 0, 0, 0}, new byte[]{82, 69, 78, 71, 0, 0}, new byte[]{82, 73, 0, 0, 0, 0}, new byte[]{82, 79, 78, 71, 0, 0}, new byte[]{82, 79, 85, 0, 0, 0}, new byte[]{82, 85, 0, 0, 0, 0}, new byte[]{82, 85, 65, 0, 0, 0}, new byte[]{82, 85, 65, 78, 0, 0}, new byte[]{82, 85, 73, 0, 0, 0}, new byte[]{82, 85, 78, 0, 0, 0}, new byte[]{82, 85, 79, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 65, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 65, 73, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 65, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 65, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 65, 79, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 69, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 69, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 69, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 65, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 65, 73, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 65, 78, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 65, 78, 71, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 65, 79, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 69, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 69, 78, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 69, 78, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 69, 78, 71, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 73, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 79, 85, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 85, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 85, 65, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 85, 65, 73, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 85, 65, 78, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 85, 65, 78, 71}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 85, 73, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 85, 78, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 85, 79, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 73, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 79, 78, 71, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 79, 85, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 85, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 85, 65, 78, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 85, 73, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 85, 78, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 85, 79, 0, 0, 0}, new byte[]{84, 65, 0, 0, 0, 0}, new byte[]{84, 65, 73, 0, 0, 0}, new byte[]{84, 65, 78, 0, 0, 0}, new byte[]{84, 65, 78, 71, 0, 0}, new byte[]{84, 65, 79, 0, 0, 0}, new byte[]{84, 69, 0, 0, 0, 0}, new byte[]{84, 69, 78, 71, 0, 0}, new byte[]{84, 73, 0, 0, 0, 0}, new byte[]{84, 73, 65, 78, 0, 0}, new byte[]{84, 73, 65, 79, 0, 0}, new byte[]{84, 73, 69, 0, 0, 0}, new byte[]{84, 73, 78, 71, 0, 0}, new byte[]{84, 79, 78, 71, 0, 0}, new byte[]{84, 79, 85, 0, 0, 0}, new byte[]{84, 85, 0, 0, 0, 0}, new byte[]{84, 85, 65, 78, 0, 0}, new byte[]{84, 85, 73, 0, 0, 0}, new byte[]{84, 85, 78, 0, 0, 0}, new byte[]{84, 85, 79, 0, 0, 0}, new byte[]{87, 65, 0, 0, 0, 0}, new byte[]{87, 65, 73, 0, 0, 0}, new byte[]{87, 65, 78, 0, 0, 0}, new byte[]{87, 65, 78, 71, 0, 0}, new byte[]{87, 69, 73, 0, 0, 0}, new byte[]{87, 69, 78, 0, 0, 0}, new byte[]{87, 69, 78, 71, 0, 0}, new byte[]{87, 79, 0, 0, 0, 0}, new byte[]{87, 85, 0, 0, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 0, 0, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 65, 0, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 65, 78, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 65, 78, 71, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 65, 79, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 69, 0, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 78, 0, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 78, 71, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 79, 78, 71, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 73, 85, 0, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 85, 0, 0, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 85, 65, 78, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 85, 69, 0, 0, 0}, new byte[]{TarConstants.LF_PAX_EXTENDED_HEADER_UC, 85, 78, 0, 0, 0}, new byte[]{89, 65, 0, 0, 0, 0}, new byte[]{89, 65, 78, 0, 0, 0}, new byte[]{89, 65, 78, 71, 0, 0}, new byte[]{89, 65, 79, 0, 0, 0}, new byte[]{89, 69, 0, 0, 0, 0}, new byte[]{89, 73, 0, 0, 0, 0}, new byte[]{89, 73, 78, 0, 0, 0}, new byte[]{89, 73, 78, 71, 0, 0}, new byte[]{89, 79, 0, 0, 0, 0}, new byte[]{89, 79, 78, 71, 0, 0}, new byte[]{89, 79, 85, 0, 0, 0}, new byte[]{89, 85, 0, 0, 0, 0}, new byte[]{89, 85, 65, 78, 0, 0}, new byte[]{89, 85, 69, 0, 0, 0}, new byte[]{89, 85, 78, 0, 0, 0}, new byte[]{74, 85, 78, 0, 0, 0}, new byte[]{89, 85, 78, 0, 0, 0}, new byte[]{90, 65, 0, 0, 0, 0}, new byte[]{90, 65, 73, 0, 0, 0}, new byte[]{90, 65, 78, 0, 0, 0}, new byte[]{90, 65, 78, 71, 0, 0}, new byte[]{90, 65, 79, 0, 0, 0}, new byte[]{90, 69, 0, 0, 0, 0}, new byte[]{90, 69, 73, 0, 0, 0}, new byte[]{90, 69, 78, 0, 0, 0}, new byte[]{90, 69, 78, 71, 0, 0}, new byte[]{90, 72, 65, 0, 0, 0}, new byte[]{90, 72, 65, 73, 0, 0}, new byte[]{90, 72, 65, 78, 0, 0}, new byte[]{90, 72, 65, 78, 71, 0}, new byte[]{67, 72, 65, 78, 71, 0}, new byte[]{90, 72, 65, 78, 71, 0}, new byte[]{90, 72, 65, 79, 0, 0}, new byte[]{90, 72, 69, 0, 0, 0}, new byte[]{90, 72, 69, 78, 0, 0}, new byte[]{90, 72, 69, 78, 71, 0}, new byte[]{90, 72, 73, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 73, 0, 0, 0}, new byte[]{90, 72, 73, 0, 0, 0}, new byte[]{90, 72, 79, 78, 71, 0}, new byte[]{90, 72, 79, 85, 0, 0}, new byte[]{90, 72, 85, 0, 0, 0}, new byte[]{90, 72, 85, 65, 0, 0}, new byte[]{90, 72, 85, 65, 73, 0}, new byte[]{90, 72, 85, 65, 78, 0}, new byte[]{90, 72, 85, 65, 78, 71}, new byte[]{90, 72, 85, 73, 0, 0}, new byte[]{90, 72, 85, 78, 0, 0}, new byte[]{90, 72, 85, 79, 0, 0}, new byte[]{90, 73, 0, 0, 0, 0}, new byte[]{90, 79, 78, 71, 0, 0}, new byte[]{90, 79, 85, 0, 0, 0}, new byte[]{90, 85, 0, 0, 0, 0}, new byte[]{90, 85, 65, 78, 0, 0}, new byte[]{90, 85, 73, 0, 0, 0}, new byte[]{90, 85, 78, 0, 0, 0}, new byte[]{90, 85, 79, 0, 0, 0}, new byte[]{0, 0, 0, 0, 0, 0}, new byte[]{TarConstants.LF_GNUTYPE_SPARSE, 72, 65, 78, 0, 0}, new byte[]{0, 0, 0, 0, 0, 0}};
    private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);

    public static class Token {
        public static final int LATIN = 1;
        public static final int PINYIN = 2;
        public static final String SEPARATOR = " ";
        public static final int UNKNOWN = 3;
        public String source;
        public String target;
        public int type;

        public Token() {
        }

        public Token(int i2, String str, String str2) {
            this.type = i2;
            this.source = str;
            this.target = str2;
        }
    }

    public HanziToPinyin(boolean z2) {
        this.mHasChinaCollator = z2;
    }

    private void addToken(StringBuilder sb, ArrayList<Token> arrayList, int i2) {
        String string = sb.toString();
        arrayList.add(new Token(i2, string, string));
        sb.setLength(0);
    }

    private static boolean doSelfValidation() {
        char[] cArr = UNIHANS;
        char c3 = cArr[0];
        String string = Character.toString(c3);
        for (char c4 : cArr) {
            if (c3 != c4) {
                String string2 = Character.toString(c4);
                if (COLLATOR.compare(string, string2) >= 0) {
                    EMLog.e(TAG, "Internal error in Unihan table. The last string \"" + string + "\" is greater than current string \"" + string2 + "\".");
                    return false;
                }
                string = string2;
            }
        }
        return true;
    }

    public static HanziToPinyin getInstance() {
        int i2;
        synchronized (HanziToPinyin.class) {
            HanziToPinyin hanziToPinyin = sInstance;
            if (hanziToPinyin != null) {
                return hanziToPinyin;
            }
            Locale[] availableLocales = Collator.getAvailableLocales();
            int length = availableLocales.length;
            while (i2 < length) {
                Locale locale = availableLocales[i2];
                i2 = (locale.equals(Locale.CHINA) || (locale.getLanguage().equals("zh") && locale.getCountry().equals("HANS"))) ? 0 : i2 + 1;
                HanziToPinyin hanziToPinyin2 = new HanziToPinyin(true);
                sInstance = hanziToPinyin2;
                return hanziToPinyin2;
            }
            EMLog.w(TAG, "There is no Chinese collator, HanziToPinyin is disabled");
            HanziToPinyin hanziToPinyin3 = new HanziToPinyin(true);
            sInstance = hanziToPinyin3;
            return hanziToPinyin3;
        }
    }

    private Token getToken(char c3) {
        int length;
        byte b3;
        Token token = new Token();
        String string = Character.toString(c3);
        token.source = string;
        if (c3 < 256) {
            token.type = 1;
        } else {
            Collator collator = COLLATOR;
            int iCompare = collator.compare(string, FIRST_PINYIN_UNIHAN);
            if (iCompare >= 0) {
                int i2 = 0;
                if (iCompare == 0) {
                    token.type = 2;
                    length = 0;
                } else {
                    iCompare = collator.compare(string, LAST_PINYIN_UNIHAN);
                    if (iCompare <= 0) {
                        if (iCompare == 0) {
                            token.type = 2;
                            length = UNIHANS.length - 1;
                        } else {
                            length = -1;
                        }
                    }
                }
                token.type = 2;
                if (length < 0) {
                    int length2 = UNIHANS.length - 1;
                    int i3 = 0;
                    while (i3 <= length2) {
                        length = (i3 + length2) / 2;
                        iCompare = COLLATOR.compare(string, Character.toString(UNIHANS[length]));
                        if (iCompare == 0) {
                            break;
                        }
                        if (iCompare > 0) {
                            i3 = length + 1;
                        } else {
                            length2 = length - 1;
                        }
                    }
                }
                if (iCompare < 0) {
                    length--;
                }
                StringBuilder sb = new StringBuilder();
                while (true) {
                    byte[] bArr = PINYINS[length];
                    if (i2 >= bArr.length || (b3 = bArr[i2]) == 0) {
                        break;
                    }
                    sb.append((char) b3);
                    i2++;
                }
                String string2 = sb.toString();
                token.target = string2;
                if (TextUtils.isEmpty(string2)) {
                    token.type = 3;
                    token.target = token.source;
                }
                return token;
            }
            token.type = 3;
        }
        token.target = string;
        return token;
    }

    public ArrayList<Token> get(String str) {
        ArrayList<Token> arrayList = new ArrayList<>();
        if (this.mHasChinaCollator && !TextUtils.isEmpty(str)) {
            int length = str.length();
            StringBuilder sb = new StringBuilder();
            int i2 = 1;
            for (int i3 = 0; i3 < length; i3++) {
                char cCharAt = str.charAt(i3);
                if (cCharAt == ' ') {
                    if (sb.length() > 0) {
                        addToken(sb, arrayList, i2);
                    }
                } else if (cCharAt < 256) {
                    if (i2 != 1 && sb.length() > 0) {
                        addToken(sb, arrayList, i2);
                    }
                    sb.append(cCharAt);
                    i2 = 1;
                } else {
                    Token token = getToken(cCharAt);
                    int i4 = token.type;
                    if (i4 == 2) {
                        if (sb.length() > 0) {
                            addToken(sb, arrayList, i2);
                        }
                        arrayList.add(token);
                        i2 = 2;
                    } else {
                        if (i2 != i4 && sb.length() > 0) {
                            addToken(sb, arrayList, i2);
                        }
                        i2 = token.type;
                        sb.append(cCharAt);
                    }
                }
            }
            if (sb.length() > 0) {
                addToken(sb, arrayList, i2);
            }
        }
        return arrayList;
    }
}
