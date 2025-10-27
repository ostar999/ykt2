package cn.hutool.core.lang.ansi;

import androidx.core.view.MotionEventCompat;
import cn.hutool.core.img.LabColor;
import com.yikaobang.yixue.R2;
import java.awt.Color;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class AnsiColors {
    private static final int[] ANSI_8BIT_COLOR_CODE_LOOKUP;
    private static final Map<AnsiColorWrapper, LabColor> ANSI_COLOR_MAP;
    private static final int CODE_OF_4_BIT_ANSI_COLOR_BRIGHT_WHITE = 97;
    private final Map<AnsiColorWrapper, LabColor> lookup;

    public enum BitDepth {
        FOUR(4),
        EIGHT(8);

        private final int bits;

        BitDepth(int i2) {
            this.bits = i2;
        }

        public static BitDepth of(int i2) {
            for (BitDepth bitDepth : values()) {
                if (bitDepth.bits == i2) {
                    return bitDepth;
                }
            }
            throw new IllegalArgumentException("Unsupported ANSI bit depth '" + i2 + "'");
        }
    }

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap(16, 1.0f);
        int code = AnsiColor.BLACK.getCode();
        BitDepth bitDepth = BitDepth.FOUR;
        linkedHashMap.put(new AnsiColorWrapper(code, bitDepth), new LabColor((Integer) 0));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.RED.getCode(), bitDepth), new LabColor((Integer) 11141120));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.GREEN.getCode(), bitDepth), new LabColor((Integer) 43520));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.YELLOW.getCode(), bitDepth), new LabColor((Integer) 11162880));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.BLUE.getCode(), bitDepth), new LabColor(Integer.valueOf(R2.anim.welcome_loading)));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.MAGENTA.getCode(), bitDepth), new LabColor((Integer) 11141290));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.CYAN.getCode(), bitDepth), new LabColor((Integer) 43690));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.WHITE.getCode(), bitDepth), new LabColor((Integer) 11184810));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.BRIGHT_BLACK.getCode(), bitDepth), new LabColor((Integer) 5592405));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.BRIGHT_RED.getCode(), bitDepth), new LabColor((Integer) 16733525));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.BRIGHT_GREEN.getCode(), bitDepth), new LabColor((Integer) 5635840));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.BRIGHT_YELLOW.getCode(), bitDepth), new LabColor((Integer) 16777045));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.BRIGHT_BLUE.getCode(), bitDepth), new LabColor((Integer) 5592575));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.BRIGHT_MAGENTA.getCode(), bitDepth), new LabColor((Integer) 16733695));
        linkedHashMap.put(new AnsiColorWrapper(AnsiColor.BRIGHT_CYAN.getCode(), bitDepth), new LabColor((Integer) 5636095));
        linkedHashMap.put(new AnsiColorWrapper(97, bitDepth), new LabColor((Integer) 16777215));
        ANSI_COLOR_MAP = Collections.unmodifiableMap(linkedHashMap);
        ANSI_8BIT_COLOR_CODE_LOOKUP = new int[]{0, 8388608, 32768, 8421376, 128, 8388736, 32896, 12632256, 8421504, 16711680, MotionEventCompat.ACTION_POINTER_INDEX_MASK, 16776960, 255, 16711935, 65535, 16777215, 0, 95, 135, R2.anim.window_ios_out, 215, 255, R2.string.mute, R2.string.physical_shop, R2.string.plv_chat_send_img_error_tip_permission_denied, R2.string.plv_linkmic_error_tip_permission_denied, R2.string.plv_scene_login_toast_streamer_no_support_type, R2.string.pref_title_ijkplayer_audio, 34560, 34655, 34695, 34735, 34775, 34815, 44800, 44895, 44935, 44975, 45015, 45055, 55040, 55135, 55175, 55215, 55255, 55295, MotionEventCompat.ACTION_POINTER_INDEX_MASK, 65375, 65415, 65455, 65495, 65535, 6225920, 6226015, 6226055, 6226095, 6226135, 6226175, 6250240, 6250335, 6250375, 6250415, 6250455, 6250495, 6260480, 6260575, 6260615, 6260655, 6260695, 6260735, 6270720, 6270815, 6270855, 6270895, 6270935, 6270975, 6280960, 6281055, 6281095, 6281135, 6281175, 6281215, 6291200, 6291295, 6291335, 6291375, 6291415, 6291455, 8847360, 8847455, 8847495, 8847535, 8847575, 8847615, 8871680, 8871775, 8871815, 8871855, 8871895, 8871935, 8881920, 8882015, 8882055, 8882095, 8882135, 8882175, 8892160, 8892255, 8892295, 8892335, 8892375, 8892415, 8902400, 8902495, 8902535, 8902575, 8902615, 8902655, 8912640, 8912735, 8912775, 8912815, 8912855, 8912895, 11468800, 11468895, 11468935, 11468975, 11469015, 11469055, 11493120, 11493215, 11493255, 11493295, 11493335, 11493375, 11503360, 11503455, 11503495, 11503535, 11503575, 11503615, 11513600, 11513695, 11513735, 11513775, 11513815, 11513855, 11523840, 11523935, 11523975, 11524015, 11524055, 11524095, 11534080, 11534175, 11534215, 11534255, 11534295, 11534335, 14090240, 14090335, 14090375, 14090415, 14090455, 14090495, 14114560, 14114655, 14114695, 14114735, 14114775, 14114815, 14124800, 14124895, 14124935, 14124975, 14125015, 14125055, 14135040, 14135135, 14135175, 14135215, 14135255, 14135295, 14145280, 14145375, 14145415, 14145455, 14145495, 14145535, 14155520, 14155615, 14155655, 14155695, 14155735, 14155775, 16711680, 16711775, 16711815, 16711855, 16711895, 16711935, 16736000, 16736095, 16736135, 16736175, 16736215, 16736255, 16746240, 16746335, 16746375, 16746415, 16746455, 16746495, 16756480, 16756575, 16756615, 16756655, 16756695, 16756735, 16766720, 16766815, 16766855, 16766895, 16766935, 16766975, 16776960, 16777055, 16777095, 16777135, 16777175, 16777215, 526344, 1184274, 1842204, 2500134, 3158064, 3815994, 4473924, 5131854, 5789784, 6447714, 7105644, 7763574, 8421504, 9079434, 9737364, 10395294, 11053224, 11711154, 12369084, 13027014, 13684944, 14342874, 15000804, 15658734};
    }

    public AnsiColors(BitDepth bitDepth) {
        this.lookup = getLookup(bitDepth);
    }

    private Map<AnsiColorWrapper, LabColor> getLookup(BitDepth bitDepth) {
        if (bitDepth != BitDepth.EIGHT) {
            return ANSI_COLOR_MAP;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(256, 1.0f);
        int i2 = 0;
        while (true) {
            int[] iArr = ANSI_8BIT_COLOR_CODE_LOOKUP;
            if (i2 >= iArr.length) {
                return Collections.unmodifiableMap(linkedHashMap);
            }
            linkedHashMap.put(new AnsiColorWrapper(i2, BitDepth.EIGHT), new LabColor(Integer.valueOf(iArr[i2])));
            i2++;
        }
    }

    public AnsiColorWrapper findClosest(Color color) {
        return findClosest(new LabColor(color));
    }

    private AnsiColorWrapper findClosest(LabColor labColor) {
        AnsiColorWrapper key = null;
        double d3 = 3.4028234663852886E38d;
        for (Map.Entry<AnsiColorWrapper, LabColor> entry : this.lookup.entrySet()) {
            double distance = labColor.getDistance(entry.getValue());
            if (key == null || distance < d3) {
                key = entry.getKey();
                d3 = distance;
            }
        }
        return key;
    }
}
