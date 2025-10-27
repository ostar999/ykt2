package com.easefun.polyv.livecommon.module.utils.span;

import android.graphics.Bitmap;
import com.easefun.polyv.livecommon.R;
import com.plv.livescenes.model.PLVEmotionImageVO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class PLVFaceManager {
    private static PLVFaceManager instance;
    private List<PLVEmotionImageVO.EmotionImage> emotionList = new ArrayList();
    private Map<String, Integer> mFaceMap;

    private PLVFaceManager() {
        initFaceMap();
    }

    public static Bitmap eraseColor(Bitmap src, int color) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bitmapCopy = src.copy(Bitmap.Config.ARGB_8888, true);
        bitmapCopy.setHasAlpha(true);
        int i2 = width * height;
        int[] iArr = new int[i2];
        src.getPixels(iArr, 0, width, 0, 0, width, height);
        for (int i3 = 0; i3 < i2; i3++) {
            if (iArr[i3] == color) {
                iArr[i3] = 0;
            }
        }
        bitmapCopy.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmapCopy;
    }

    public static PLVFaceManager getInstance() {
        if (instance == null) {
            instance = new PLVFaceManager();
        }
        return instance;
    }

    private void initFaceMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        this.mFaceMap = linkedHashMap;
        linkedHashMap.put("[呲牙]", Integer.valueOf(R.drawable.polyv_101));
        this.mFaceMap.put("[大笑]", Integer.valueOf(R.drawable.polyv_102));
        this.mFaceMap.put("[可爱]", Integer.valueOf(R.drawable.polyv_103));
        this.mFaceMap.put("[害羞]", Integer.valueOf(R.drawable.polyv_104));
        this.mFaceMap.put("[偷笑]", Integer.valueOf(R.drawable.polyv_105));
        this.mFaceMap.put("[再见]", Integer.valueOf(R.drawable.polyv_106));
        this.mFaceMap.put("[惊讶]", Integer.valueOf(R.drawable.polyv_107));
        this.mFaceMap.put("[哭笑]", Integer.valueOf(R.drawable.polyv_108));
        this.mFaceMap.put("[酷]", Integer.valueOf(R.drawable.polyv_109));
        this.mFaceMap.put("[奸笑]", Integer.valueOf(R.drawable.polyv_110));
        this.mFaceMap.put("[鼓掌]", Integer.valueOf(R.drawable.polyv_111));
        this.mFaceMap.put("[大哭]", Integer.valueOf(R.drawable.polyv_112));
        this.mFaceMap.put("[敲打]", Integer.valueOf(R.drawable.polyv_113));
        this.mFaceMap.put("[吃瓜]", Integer.valueOf(R.drawable.polyv_114));
        this.mFaceMap.put("[让我看看]", Integer.valueOf(R.drawable.polyv_115));
        this.mFaceMap.put("[按脸哭]", Integer.valueOf(R.drawable.polyv_116));
        this.mFaceMap.put("[打哈欠]", Integer.valueOf(R.drawable.polyv_117));
        this.mFaceMap.put("[愤怒]", Integer.valueOf(R.drawable.polyv_118));
        this.mFaceMap.put("[难过]", Integer.valueOf(R.drawable.polyv_119));
        this.mFaceMap.put("[ok]", Integer.valueOf(R.drawable.polyv_120));
        this.mFaceMap.put("[爱心]", Integer.valueOf(R.drawable.polyv_121));
        this.mFaceMap.put("[加1]", Integer.valueOf(R.drawable.polyv_122));
        this.mFaceMap.put("[心碎]", Integer.valueOf(R.drawable.polyv_123));
        this.mFaceMap.put("[正确]", Integer.valueOf(R.drawable.polyv_124));
        this.mFaceMap.put("[错误]", Integer.valueOf(R.drawable.polyv_125));
        this.mFaceMap.put("[满分]", Integer.valueOf(R.drawable.polyv_126));
        this.mFaceMap.put("[笔记]", Integer.valueOf(R.drawable.polyv_127));
        this.mFaceMap.put("[胜利]", Integer.valueOf(R.drawable.polyv_128));
        this.mFaceMap.put("[比心]", Integer.valueOf(R.drawable.polyv_129));
        this.mFaceMap.put("[赞]", Integer.valueOf(R.drawable.polyv_130));
        this.mFaceMap.put("[蛋糕]", Integer.valueOf(R.drawable.polyv_131));
        this.mFaceMap.put("[礼物]", Integer.valueOf(R.drawable.polyv_132));
        this.mFaceMap.put("[红包]", Integer.valueOf(R.drawable.polyv_133));
        this.mFaceMap.put("[奶茶]", Integer.valueOf(R.drawable.polyv_134));
        this.mFaceMap.put("[时钟]", Integer.valueOf(R.drawable.polyv_135));
        this.mFaceMap.put("[晚安]", Integer.valueOf(R.drawable.polyv_136));
    }

    public List<PLVEmotionImageVO.EmotionImage> getEmotionList() {
        return this.emotionList;
    }

    public String getEmotionUrl(@NotNull String id) {
        for (PLVEmotionImageVO.EmotionImage emotionImage : this.emotionList) {
            if (id.equals(emotionImage.getId())) {
                return emotionImage.getUrl();
            }
        }
        return "";
    }

    public int getFaceId(String faceStr) {
        if (this.mFaceMap.containsKey(faceStr)) {
            return this.mFaceMap.get(faceStr).intValue();
        }
        return -1;
    }

    public Map<String, Integer> getFaceMap() {
        return this.mFaceMap;
    }

    public void initEmotionList(@NotNull List<PLVEmotionImageVO.EmotionImage> emotionImages) {
        this.emotionList.clear();
        this.emotionList.addAll(emotionImages);
    }
}
