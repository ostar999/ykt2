package com.google.android.exoplayer2.text.cea;

import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.text.SubtitleInputBuffer;
import com.google.android.exoplayer2.text.SubtitleOutputBuffer;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.yikaobang.yixue.R2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes3.dex */
public final class Cea608Decoder extends CeaDecoder {
    private static final int CC_FIELD_FLAG = 1;
    private static final byte CC_IMPLICIT_DATA_HEADER = -4;
    private static final int CC_MODE_PAINT_ON = 3;
    private static final int CC_MODE_POP_ON = 2;
    private static final int CC_MODE_ROLL_UP = 1;
    private static final int CC_MODE_UNKNOWN = 0;
    private static final int CC_TYPE_FLAG = 2;
    private static final int CC_VALID_FLAG = 4;
    private static final byte CTRL_BACKSPACE = 33;
    private static final byte CTRL_CARRIAGE_RETURN = 45;
    private static final byte CTRL_DELETE_TO_END_OF_ROW = 36;
    private static final byte CTRL_END_OF_CAPTION = 47;
    private static final byte CTRL_ERASE_DISPLAYED_MEMORY = 44;
    private static final byte CTRL_ERASE_NON_DISPLAYED_MEMORY = 46;
    private static final byte CTRL_RESUME_CAPTION_LOADING = 32;
    private static final byte CTRL_RESUME_DIRECT_CAPTIONING = 41;
    private static final byte CTRL_RESUME_TEXT_DISPLAY = 43;
    private static final byte CTRL_ROLL_UP_CAPTIONS_2_ROWS = 37;
    private static final byte CTRL_ROLL_UP_CAPTIONS_3_ROWS = 38;
    private static final byte CTRL_ROLL_UP_CAPTIONS_4_ROWS = 39;
    private static final byte CTRL_TEXT_RESTART = 42;
    private static final int DEFAULT_CAPTIONS_ROW_COUNT = 4;
    public static final long MIN_DATA_CHANNEL_TIMEOUT_MS = 16000;
    private static final int NTSC_CC_CHANNEL_1 = 0;
    private static final int NTSC_CC_CHANNEL_2 = 1;
    private static final int NTSC_CC_FIELD_1 = 0;
    private static final int NTSC_CC_FIELD_2 = 1;
    private static final int STYLE_ITALICS = 7;
    private static final int STYLE_UNCHANGED = 8;
    private static final String TAG = "Cea608Decoder";
    private int captionMode;
    private int captionRowCount;

    @Nullable
    private List<Cue> cues;
    private boolean isCaptionValid;
    private boolean isInCaptionService;
    private long lastCueUpdateUs;

    @Nullable
    private List<Cue> lastCues;
    private final int packetLength;
    private byte repeatableControlCc1;
    private byte repeatableControlCc2;
    private boolean repeatableControlSet;
    private final int selectedChannel;
    private final int selectedField;
    private final long validDataChannelTimeoutUs;
    private static final int[] ROW_INDICES = {11, 1, 3, 12, 14, 5, 7, 9};
    private static final int[] COLUMN_INDICES = {0, 4, 8, 12, 16, 20, 24, 28};
    private static final int[] STYLE_COLORS = {-1, -16711936, -16776961, -16711681, SupportMenu.CATEGORY_MASK, InputDeviceCompat.SOURCE_ANY, -65281};
    private static final int[] BASIC_CHARACTER_SET = {32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 225, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 233, 93, 237, 243, 250, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 231, R2.attr.actionModeCutDrawable, 209, 241, R2.drawable.exo_ic_pause_circle_filled};
    private static final int[] SPECIAL_CHARACTER_SET = {R2.anim.window_ios_in, 176, 189, R2.array.ease_file_file_suffix, R2.drawable.alivc_rr_bg_white, 162, 163, R2.drawable.gry_deek_rond, 224, 32, 232, 226, 234, 238, 244, R2.attr.actionModeSelectAllDrawable};
    private static final int[] SPECIAL_ES_FR_CHARACTER_SET = {193, 201, 211, 218, 220, R2.attr.actionModeShareDrawable, R2.dimen.sp_20, 161, 42, 39, 8212, 169, R2.drawable.alivc_rr_bg_orange, R2.dimen.sp_3, R2.dimen.sp_24, R2.dimen.sp_25, 192, R2.array.ease_numbers_file_suffix, 199, 200, 202, 203, 235, 206, 207, 239, 212, 217, R2.attr.actionModePasteDrawable, 219, R2.anim.widget_zoom_in, 187};
    private static final int[] SPECIAL_PT_DE_CHARACTER_SET = {R2.array.ease_other_file_suffix, 227, 205, 204, 236, 210, 242, 213, R2.attr.actionModeCloseDrawable, 123, 125, 92, 94, 95, 124, 126, R2.array.ease_pages_file_suffix, 228, 214, R2.attr.actionModeCopyDrawable, 223, 165, 164, R2.drawable.ee_20, R2.array.ease_pdf_file_suffix, 229, 216, R2.attr.actionModeFindDrawable, R2.drawable.ee_3, R2.drawable.ee_33, R2.drawable.ee_5, R2.drawable.ee_9};
    private static final boolean[] ODD_PARITY_BYTE_TABLE = {false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false, false, true, true, false, true, false, false, true, false, true, true, false, true, false, false, true, true, false, false, true, false, true, true, false};
    private final ParsableByteArray ccData = new ParsableByteArray();
    private final ArrayList<CueBuilder> cueBuilders = new ArrayList<>();
    private CueBuilder currentCueBuilder = new CueBuilder(0, 4);
    private int currentChannel = 0;

    public static final class CueBuilder {
        private static final int BASE_ROW = 15;
        private static final int SCREEN_CHARWIDTH = 32;
        private int captionMode;
        private int captionRowCount;
        private int indent;
        private int row;
        private int tabOffset;
        private final List<CueStyle> cueStyles = new ArrayList();
        private final List<SpannableString> rolledUpCaptions = new ArrayList();
        private final StringBuilder captionStringBuilder = new StringBuilder();

        public static class CueStyle {
            public int start;
            public final int style;
            public final boolean underline;

            public CueStyle(int i2, boolean z2, int i3) {
                this.style = i2;
                this.underline = z2;
                this.start = i3;
            }
        }

        public CueBuilder(int i2, int i3) {
            reset(i2);
            this.captionRowCount = i3;
        }

        private SpannableString buildCurrentLine() {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(this.captionStringBuilder);
            int length = spannableStringBuilder.length();
            int i2 = -1;
            int i3 = -1;
            int i4 = -1;
            int i5 = -1;
            int i6 = 0;
            int i7 = 0;
            boolean z2 = false;
            while (i6 < this.cueStyles.size()) {
                CueStyle cueStyle = this.cueStyles.get(i6);
                boolean z3 = cueStyle.underline;
                int i8 = cueStyle.style;
                if (i8 != 8) {
                    boolean z4 = i8 == 7;
                    if (i8 != 7) {
                        i5 = Cea608Decoder.STYLE_COLORS[i8];
                    }
                    z2 = z4;
                }
                int i9 = cueStyle.start;
                i6++;
                if (i9 != (i6 < this.cueStyles.size() ? this.cueStyles.get(i6).start : length)) {
                    if (i2 != -1 && !z3) {
                        setUnderlineSpan(spannableStringBuilder, i2, i9);
                        i2 = -1;
                    } else if (i2 == -1 && z3) {
                        i2 = i9;
                    }
                    if (i3 != -1 && !z2) {
                        setItalicSpan(spannableStringBuilder, i3, i9);
                        i3 = -1;
                    } else if (i3 == -1 && z2) {
                        i3 = i9;
                    }
                    if (i5 != i4) {
                        setColorSpan(spannableStringBuilder, i7, i9, i4);
                        i4 = i5;
                        i7 = i9;
                    }
                }
            }
            if (i2 != -1 && i2 != length) {
                setUnderlineSpan(spannableStringBuilder, i2, length);
            }
            if (i3 != -1 && i3 != length) {
                setItalicSpan(spannableStringBuilder, i3, length);
            }
            if (i7 != length) {
                setColorSpan(spannableStringBuilder, i7, length, i4);
            }
            return new SpannableString(spannableStringBuilder);
        }

        private static void setColorSpan(SpannableStringBuilder spannableStringBuilder, int i2, int i3, int i4) {
            if (i4 == -1) {
                return;
            }
            spannableStringBuilder.setSpan(new ForegroundColorSpan(i4), i2, i3, 33);
        }

        private static void setItalicSpan(SpannableStringBuilder spannableStringBuilder, int i2, int i3) {
            spannableStringBuilder.setSpan(new StyleSpan(2), i2, i3, 33);
        }

        private static void setUnderlineSpan(SpannableStringBuilder spannableStringBuilder, int i2, int i3) {
            spannableStringBuilder.setSpan(new UnderlineSpan(), i2, i3, 33);
        }

        public void append(char c3) {
            if (this.captionStringBuilder.length() < 32) {
                this.captionStringBuilder.append(c3);
            }
        }

        public void backspace() {
            int length = this.captionStringBuilder.length();
            if (length > 0) {
                this.captionStringBuilder.delete(length - 1, length);
                for (int size = this.cueStyles.size() - 1; size >= 0; size--) {
                    CueStyle cueStyle = this.cueStyles.get(size);
                    int i2 = cueStyle.start;
                    if (i2 != length) {
                        return;
                    }
                    cueStyle.start = i2 - 1;
                }
            }
        }

        @Nullable
        public Cue build(int i2) {
            float f2;
            int i3 = this.indent + this.tabOffset;
            int i4 = 32 - i3;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            for (int i5 = 0; i5 < this.rolledUpCaptions.size(); i5++) {
                spannableStringBuilder.append(Util.truncateAscii(this.rolledUpCaptions.get(i5), i4));
                spannableStringBuilder.append('\n');
            }
            spannableStringBuilder.append(Util.truncateAscii(buildCurrentLine(), i4));
            if (spannableStringBuilder.length() == 0) {
                return null;
            }
            int length = i4 - spannableStringBuilder.length();
            int i6 = i3 - length;
            if (i2 == Integer.MIN_VALUE) {
                i2 = (this.captionMode != 2 || (Math.abs(i6) >= 3 && length >= 0)) ? (this.captionMode != 2 || i6 <= 0) ? 0 : 2 : 1;
            }
            if (i2 != 1) {
                if (i2 == 2) {
                    i3 = 32 - length;
                }
                f2 = ((i3 / 32.0f) * 0.8f) + 0.1f;
            } else {
                f2 = 0.5f;
            }
            int i7 = this.row;
            if (i7 > 7) {
                i7 = (i7 - 15) - 2;
            } else if (this.captionMode == 1) {
                i7 -= this.captionRowCount - 1;
            }
            return new Cue.Builder().setText(spannableStringBuilder).setTextAlignment(Layout.Alignment.ALIGN_NORMAL).setLine(i7, 1).setPosition(f2).setPositionAnchor(i2).build();
        }

        public boolean isEmpty() {
            return this.cueStyles.isEmpty() && this.rolledUpCaptions.isEmpty() && this.captionStringBuilder.length() == 0;
        }

        public void reset(int i2) {
            this.captionMode = i2;
            this.cueStyles.clear();
            this.rolledUpCaptions.clear();
            this.captionStringBuilder.setLength(0);
            this.row = 15;
            this.indent = 0;
            this.tabOffset = 0;
        }

        public void rollUp() {
            this.rolledUpCaptions.add(buildCurrentLine());
            this.captionStringBuilder.setLength(0);
            this.cueStyles.clear();
            int iMin = Math.min(this.captionRowCount, this.row);
            while (this.rolledUpCaptions.size() >= iMin) {
                this.rolledUpCaptions.remove(0);
            }
        }

        public void setCaptionMode(int i2) {
            this.captionMode = i2;
        }

        public void setCaptionRowCount(int i2) {
            this.captionRowCount = i2;
        }

        public void setStyle(int i2, boolean z2) {
            this.cueStyles.add(new CueStyle(i2, z2, this.captionStringBuilder.length()));
        }
    }

    public Cea608Decoder(String str, int i2, long j2) {
        this.validDataChannelTimeoutUs = j2 > 0 ? j2 * 1000 : -9223372036854775807L;
        this.packetLength = MimeTypes.APPLICATION_MP4CEA608.equals(str) ? 2 : 3;
        if (i2 == 1) {
            this.selectedChannel = 0;
            this.selectedField = 0;
        } else if (i2 == 2) {
            this.selectedChannel = 1;
            this.selectedField = 0;
        } else if (i2 == 3) {
            this.selectedChannel = 0;
            this.selectedField = 1;
        } else if (i2 != 4) {
            Log.w(TAG, "Invalid channel. Defaulting to CC1.");
            this.selectedChannel = 0;
            this.selectedField = 0;
        } else {
            this.selectedChannel = 1;
            this.selectedField = 1;
        }
        setCaptionMode(0);
        resetCueBuilders();
        this.isInCaptionService = true;
        this.lastCueUpdateUs = C.TIME_UNSET;
    }

    private static char getBasicChar(byte b3) {
        return (char) BASIC_CHARACTER_SET[(b3 & 127) - 32];
    }

    private static int getChannel(byte b3) {
        return (b3 >> 3) & 1;
    }

    private List<Cue> getDisplayCues() {
        int size = this.cueBuilders.size();
        ArrayList arrayList = new ArrayList(size);
        int iMin = 2;
        for (int i2 = 0; i2 < size; i2++) {
            Cue cueBuild = this.cueBuilders.get(i2).build(Integer.MIN_VALUE);
            arrayList.add(cueBuild);
            if (cueBuild != null) {
                iMin = Math.min(iMin, cueBuild.positionAnchor);
            }
        }
        ArrayList arrayList2 = new ArrayList(size);
        for (int i3 = 0; i3 < size; i3++) {
            Cue cue = (Cue) arrayList.get(i3);
            if (cue != null) {
                if (cue.positionAnchor != iMin) {
                    cue = (Cue) Assertions.checkNotNull(this.cueBuilders.get(i3).build(iMin));
                }
                arrayList2.add(cue);
            }
        }
        return arrayList2;
    }

    private static char getExtendedEsFrChar(byte b3) {
        return (char) SPECIAL_ES_FR_CHARACTER_SET[b3 & Ascii.US];
    }

    private static char getExtendedPtDeChar(byte b3) {
        return (char) SPECIAL_PT_DE_CHARACTER_SET[b3 & Ascii.US];
    }

    private static char getExtendedWestEuropeanChar(byte b3, byte b4) {
        return (b3 & 1) == 0 ? getExtendedEsFrChar(b4) : getExtendedPtDeChar(b4);
    }

    private static char getSpecialNorthAmericanChar(byte b3) {
        return (char) SPECIAL_CHARACTER_SET[b3 & 15];
    }

    private void handleMidrowCtrl(byte b3) {
        this.currentCueBuilder.append(' ');
        this.currentCueBuilder.setStyle((b3 >> 1) & 7, (b3 & 1) == 1);
    }

    private void handleMiscCode(byte b3) {
        if (b3 == 32) {
            setCaptionMode(2);
            return;
        }
        if (b3 == 41) {
            setCaptionMode(3);
            return;
        }
        switch (b3) {
            case 37:
                setCaptionMode(1);
                setCaptionRowCount(2);
                break;
            case 38:
                setCaptionMode(1);
                setCaptionRowCount(3);
                break;
            case 39:
                setCaptionMode(1);
                setCaptionRowCount(4);
                break;
            default:
                int i2 = this.captionMode;
                if (i2 != 0) {
                    if (b3 == 33) {
                        this.currentCueBuilder.backspace();
                        break;
                    } else {
                        switch (b3) {
                            case 44:
                                this.cues = Collections.emptyList();
                                int i3 = this.captionMode;
                                if (i3 == 1 || i3 == 3) {
                                    resetCueBuilders();
                                    break;
                                }
                            case 45:
                                if (i2 == 1 && !this.currentCueBuilder.isEmpty()) {
                                    this.currentCueBuilder.rollUp();
                                    break;
                                }
                                break;
                            case 46:
                                resetCueBuilders();
                                break;
                            case 47:
                                this.cues = getDisplayCues();
                                resetCueBuilders();
                                break;
                        }
                    }
                }
                break;
        }
    }

    private void handlePreambleAddressCode(byte b3, byte b4) {
        int i2 = ROW_INDICES[b3 & 7];
        if ((b4 & 32) != 0) {
            i2++;
        }
        if (i2 != this.currentCueBuilder.row) {
            if (this.captionMode != 1 && !this.currentCueBuilder.isEmpty()) {
                CueBuilder cueBuilder = new CueBuilder(this.captionMode, this.captionRowCount);
                this.currentCueBuilder = cueBuilder;
                this.cueBuilders.add(cueBuilder);
            }
            this.currentCueBuilder.row = i2;
        }
        boolean z2 = (b4 & 16) == 16;
        boolean z3 = (b4 & 1) == 1;
        int i3 = (b4 >> 1) & 7;
        this.currentCueBuilder.setStyle(z2 ? 8 : i3, z3);
        if (z2) {
            this.currentCueBuilder.indent = COLUMN_INDICES[i3];
        }
    }

    private static boolean isCtrlCode(byte b3) {
        return (b3 & 224) == 0;
    }

    private static boolean isExtendedWestEuropeanChar(byte b3, byte b4) {
        return (b3 & 246) == 18 && (b4 & 224) == 32;
    }

    private static boolean isMidrowCtrlCode(byte b3, byte b4) {
        return (b3 & 247) == 17 && (b4 & 240) == 32;
    }

    private static boolean isMiscCode(byte b3, byte b4) {
        return (b3 & 246) == 20 && (b4 & 240) == 32;
    }

    private static boolean isPreambleAddressCode(byte b3, byte b4) {
        return (b3 & 240) == 16 && (b4 & 192) == 64;
    }

    private static boolean isRepeatable(byte b3) {
        return (b3 & 240) == 16;
    }

    private boolean isRepeatedCommand(boolean z2, byte b3, byte b4) {
        if (!z2 || !isRepeatable(b3)) {
            this.repeatableControlSet = false;
        } else {
            if (this.repeatableControlSet && this.repeatableControlCc1 == b3 && this.repeatableControlCc2 == b4) {
                this.repeatableControlSet = false;
                return true;
            }
            this.repeatableControlSet = true;
            this.repeatableControlCc1 = b3;
            this.repeatableControlCc2 = b4;
        }
        return false;
    }

    private static boolean isServiceSwitchCommand(byte b3) {
        return (b3 & 247) == 20;
    }

    private static boolean isSpecialNorthAmericanChar(byte b3, byte b4) {
        return (b3 & 247) == 17 && (b4 & 240) == 48;
    }

    private static boolean isTabCtrlCode(byte b3, byte b4) {
        return (b3 & 247) == 23 && b4 >= 33 && b4 <= 35;
    }

    private static boolean isXdsControlCode(byte b3) {
        return 1 <= b3 && b3 <= 15;
    }

    private void maybeUpdateIsInCaptionService(byte b3, byte b4) {
        if (isXdsControlCode(b3)) {
            this.isInCaptionService = false;
            return;
        }
        if (isServiceSwitchCommand(b3)) {
            if (b4 != 32 && b4 != 47) {
                switch (b4) {
                    case 37:
                    case 38:
                    case 39:
                        break;
                    default:
                        switch (b4) {
                            case 42:
                            case 43:
                                this.isInCaptionService = false;
                                break;
                        }
                }
            }
            this.isInCaptionService = true;
        }
    }

    private void resetCueBuilders() {
        this.currentCueBuilder.reset(this.captionMode);
        this.cueBuilders.clear();
        this.cueBuilders.add(this.currentCueBuilder);
    }

    private void setCaptionMode(int i2) {
        int i3 = this.captionMode;
        if (i3 == i2) {
            return;
        }
        this.captionMode = i2;
        if (i2 == 3) {
            for (int i4 = 0; i4 < this.cueBuilders.size(); i4++) {
                this.cueBuilders.get(i4).setCaptionMode(i2);
            }
            return;
        }
        resetCueBuilders();
        if (i3 == 3 || i2 == 1 || i2 == 0) {
            this.cues = Collections.emptyList();
        }
    }

    private void setCaptionRowCount(int i2) {
        this.captionRowCount = i2;
        this.currentCueBuilder.setCaptionRowCount(i2);
    }

    private boolean shouldClearStuckCaptions() {
        return (this.validDataChannelTimeoutUs == C.TIME_UNSET || this.lastCueUpdateUs == C.TIME_UNSET || getPositionUs() - this.lastCueUpdateUs < this.validDataChannelTimeoutUs) ? false : true;
    }

    private boolean updateAndVerifyCurrentChannel(byte b3) {
        if (isCtrlCode(b3)) {
            this.currentChannel = getChannel(b3);
        }
        return this.currentChannel == this.selectedChannel;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    public Subtitle createSubtitle() {
        List<Cue> list = this.cues;
        this.lastCues = list;
        return new CeaSubtitle((List) Assertions.checkNotNull(list));
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0064  */
    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void decode(com.google.android.exoplayer2.text.SubtitleInputBuffer r10) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.cea.Cea608Decoder.decode(com.google.android.exoplayer2.text.SubtitleInputBuffer):void");
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public /* bridge */ /* synthetic */ SubtitleInputBuffer dequeueInputBuffer() throws SubtitleDecoderException {
        return super.dequeueInputBuffer();
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public void flush() {
        super.flush();
        this.cues = null;
        this.lastCues = null;
        setCaptionMode(0);
        setCaptionRowCount(4);
        resetCueBuilders();
        this.isCaptionValid = false;
        this.repeatableControlSet = false;
        this.repeatableControlCc1 = (byte) 0;
        this.repeatableControlCc2 = (byte) 0;
        this.currentChannel = 0;
        this.isInCaptionService = true;
        this.lastCueUpdateUs = C.TIME_UNSET;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public String getName() {
        return TAG;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    public boolean isNewSubtitleDataAvailable() {
        return this.cues != this.lastCues;
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder
    public /* bridge */ /* synthetic */ void queueInputBuffer(SubtitleInputBuffer subtitleInputBuffer) throws SubtitleDecoderException {
        super.queueInputBuffer(subtitleInputBuffer);
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    public void release() {
    }

    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.text.SubtitleDecoder
    public /* bridge */ /* synthetic */ void setPositionUs(long j2) {
        super.setPositionUs(j2);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.exoplayer2.text.cea.CeaDecoder, com.google.android.exoplayer2.decoder.Decoder
    @Nullable
    public SubtitleOutputBuffer dequeueOutputBuffer() throws SubtitleDecoderException {
        SubtitleOutputBuffer availableOutputBuffer;
        SubtitleOutputBuffer subtitleOutputBufferDequeueOutputBuffer = super.dequeueOutputBuffer();
        if (subtitleOutputBufferDequeueOutputBuffer != null) {
            return subtitleOutputBufferDequeueOutputBuffer;
        }
        if (!shouldClearStuckCaptions() || (availableOutputBuffer = getAvailableOutputBuffer()) == null) {
            return null;
        }
        this.cues = Collections.emptyList();
        this.lastCueUpdateUs = C.TIME_UNSET;
        availableOutputBuffer.setContent(getPositionUs(), createSubtitle(), Long.MAX_VALUE);
        return availableOutputBuffer;
    }
}
