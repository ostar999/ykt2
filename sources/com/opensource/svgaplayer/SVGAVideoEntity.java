package com.opensource.svgaplayer;

import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.SoundPool;
import com.alipay.sdk.authjs.a;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.aliyun.vod.log.struct.AliyunLogKey;
import com.arialyy.aria.core.inf.IOptionConstant;
import com.easefun.polyv.livecommon.module.modules.document.model.enums.PLVDocumentMarkToolType;
import com.luck.picture.lib.config.PictureMimeType;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGASoundManager;
import com.opensource.svgaplayer.bitmap.SVGABitmapByteArrayDecoder;
import com.opensource.svgaplayer.bitmap.SVGABitmapFileDecoder;
import com.opensource.svgaplayer.entities.SVGAAudioEntity;
import com.opensource.svgaplayer.entities.SVGAVideoSpriteEntity;
import com.opensource.svgaplayer.proto.AudioEntity;
import com.opensource.svgaplayer.proto.MovieEntity;
import com.opensource.svgaplayer.proto.MovieParams;
import com.opensource.svgaplayer.proto.SpriteEntity;
import com.opensource.svgaplayer.utils.SVGARect;
import com.opensource.svgaplayer.utils.log.LogUtils;
import com.tencent.connect.share.QzonePublish;
import com.tencent.smtt.sdk.TbsReaderView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.text.StringsKt__StringsJVMKt;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;

@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0012\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B'\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\nB\u0017\b\u0016\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\rB'\b\u0016\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0002\u0010\u000eJ\u0006\u0010H\u001a\u00020.J\u001a\u0010I\u001a\u0004\u0018\u00010&2\u0006\u0010J\u001a\u00020K2\u0006\u0010L\u001a\u00020\u0014H\u0002J\u0012\u0010I\u001a\u0004\u0018\u00010&2\u0006\u0010L\u001a\u00020\u0014H\u0002J$\u0010M\u001a\u00020\u001d2\u0006\u0010N\u001a\u00020O2\u0012\u0010P\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00050%H\u0002J\u0018\u0010Q\u001a\u00020\u00052\u0006\u0010R\u001a\u00020\u00052\u0006\u0010S\u001a\u00020KH\u0002J\u001c\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u00050%2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u001c\u0010U\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020K0%2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0018\u0010V\u001a\u00020\u00142\u0006\u0010W\u001a\u00020\u00142\u0006\u0010X\u001a\u00020\u0014H\u0002J\u0012\u0010Y\u001a\u0004\u0018\u00010;2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010Z\u001a\u00020.2\u0006\u0010[\u001a\u00020\fH\u0002J\u0010\u0010Z\u001a\u00020.2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J%\u0010\\\u001a\u00020.2\f\u0010]\u001a\b\u0012\u0004\u0012\u00020.0-2\b\u0010^\u001a\u0004\u0018\u000102H\u0000¢\u0006\u0002\b_J\u0010\u0010`\u001a\u00020.2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010`\u001a\u00020.2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002J\u001e\u0010a\u001a\u00020.2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010b\u001a\b\u0012\u0004\u0012\u00020.0-H\u0002J\u0010\u0010c\u001a\u00020.2\u0006\u0010d\u001a\u00020\u0003H\u0002J\u0010\u0010e\u001a\u00020.2\u0006\u0010f\u001a\u00020gH\u0002J\u001e\u0010h\u001a\u00020.2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010b\u001a\b\u0012\u0004\u0012\u00020.0-H\u0002R\u001e\u0010\u0010\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082D¢\u0006\u0002\n\u0000R\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR \u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001cX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001e\u0010\"\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0012R&\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020&0%X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020.0-X\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u00101\u001a\u0004\u0018\u000102X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u00103\u001a\u0004\u0018\u00010\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\u0010\u00108\u001a\u0004\u0018\u000109X\u0082\u000e¢\u0006\u0002\n\u0000R\u001c\u0010:\u001a\u0004\u0018\u00010;X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R \u0010@\u001a\b\u0012\u0004\u0012\u00020A0\u001cX\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\u001f\"\u0004\bC\u0010!R\u001e\u0010E\u001a\u00020D2\u0006\u0010\u000f\u001a\u00020D@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\bF\u0010G¨\u0006i"}, d2 = {"Lcom/opensource/svgaplayer/SVGAVideoEntity;", "", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lorg/json/JSONObject;", IOptionConstant.cacheDir, "Ljava/io/File;", "(Lorg/json/JSONObject;Ljava/io/File;)V", "frameWidth", "", "frameHeight", "(Lorg/json/JSONObject;Ljava/io/File;II)V", "entity", "Lcom/opensource/svgaplayer/proto/MovieEntity;", "(Lcom/opensource/svgaplayer/proto/MovieEntity;Ljava/io/File;)V", "(Lcom/opensource/svgaplayer/proto/MovieEntity;Ljava/io/File;II)V", "<set-?>", "FPS", "getFPS", "()I", "TAG", "", "antiAlias", "", "getAntiAlias", "()Z", "setAntiAlias", "(Z)V", "audioList", "", "Lcom/opensource/svgaplayer/entities/SVGAAudioEntity;", "getAudioList$com_opensource_svgaplayer", "()Ljava/util/List;", "setAudioList$com_opensource_svgaplayer", "(Ljava/util/List;)V", "frames", "getFrames", "imageMap", "Ljava/util/HashMap;", "Landroid/graphics/Bitmap;", "getImageMap$com_opensource_svgaplayer", "()Ljava/util/HashMap;", "setImageMap$com_opensource_svgaplayer", "(Ljava/util/HashMap;)V", "mCacheDir", "mCallback", "Lkotlin/Function0;", "", "mFrameHeight", "mFrameWidth", "mPlayCallback", "Lcom/opensource/svgaplayer/SVGAParser$PlayCallback;", "movieItem", "getMovieItem", "()Lcom/opensource/svgaplayer/proto/MovieEntity;", "setMovieItem", "(Lcom/opensource/svgaplayer/proto/MovieEntity;)V", "soundCallback", "Lcom/opensource/svgaplayer/SVGASoundManager$SVGASoundCallBack;", "soundPool", "Landroid/media/SoundPool;", "getSoundPool$com_opensource_svgaplayer", "()Landroid/media/SoundPool;", "setSoundPool$com_opensource_svgaplayer", "(Landroid/media/SoundPool;)V", "spriteList", "Lcom/opensource/svgaplayer/entities/SVGAVideoSpriteEntity;", "getSpriteList$com_opensource_svgaplayer", "setSpriteList$com_opensource_svgaplayer", "Lcom/opensource/svgaplayer/utils/SVGARect;", QzonePublish.PUBLISH_TO_QZONE_VIDEO_SIZE, "getVideoSize", "()Lcom/opensource/svgaplayer/utils/SVGARect;", PLVDocumentMarkToolType.CLEAR, "createBitmap", "byteArray", "", TbsReaderView.KEY_FILE_PATH, "createSvgaAudioEntity", "audio", "Lcom/opensource/svgaplayer/proto/AudioEntity;", "audiosFileMap", "generateAudioFile", "audioCache", "value", "generateAudioFileMap", "generateAudioMap", "generateBitmapFilePath", "imgName", "imgKey", "generateSoundPool", "parserImages", "obj", "prepare", a.f3170c, "playCallback", "prepare$com_opensource_svgaplayer", "resetSprites", "setupAudios", "completionBlock", "setupByJson", "movieObject", "setupByMovie", "movieParams", "Lcom/opensource/svgaplayer/proto/MovieParams;", "setupSoundPool", BuildConfig.LIBRARY_PACKAGE_NAME}, k = 1, mv = {1, 1, 15})
/* loaded from: classes4.dex */
public final class SVGAVideoEntity {
    private int FPS;
    private final String TAG;
    private boolean antiAlias;

    @NotNull
    private List<SVGAAudioEntity> audioList;
    private int frames;

    @NotNull
    private HashMap<String, Bitmap> imageMap;
    private File mCacheDir;
    private Function0<Unit> mCallback;
    private int mFrameHeight;
    private int mFrameWidth;
    private SVGAParser.PlayCallback mPlayCallback;

    @Nullable
    private MovieEntity movieItem;
    private SVGASoundManager.SVGASoundCallBack soundCallback;

    @Nullable
    private SoundPool soundPool;

    @NotNull
    private List<SVGAVideoSpriteEntity> spriteList;

    @NotNull
    private SVGARect videoSize;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SVGAVideoEntity(@NotNull JSONObject json, @NotNull File cacheDir) {
        this(json, cacheDir, 0, 0);
        Intrinsics.checkParameterIsNotNull(json, "json");
        Intrinsics.checkParameterIsNotNull(cacheDir, "cacheDir");
    }

    public static final /* synthetic */ Function0 access$getMCallback$p(SVGAVideoEntity sVGAVideoEntity) {
        Function0<Unit> function0 = sVGAVideoEntity.mCallback;
        if (function0 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mCallback");
        }
        return function0;
    }

    private final Bitmap createBitmap(String filePath) {
        return SVGABitmapFileDecoder.INSTANCE.decodeBitmapFrom(filePath, this.mFrameWidth, this.mFrameHeight);
    }

    private final SVGAAudioEntity createSvgaAudioEntity(AudioEntity audio, HashMap<String, File> audiosFileMap) {
        SVGAAudioEntity sVGAAudioEntity = new SVGAAudioEntity(audio);
        Integer num = audio.startTime;
        double dIntValue = num != null ? num.intValue() : 0;
        Integer num2 = audio.totalTime;
        double dIntValue2 = num2 != null ? num2.intValue() : 0;
        if (((int) dIntValue2) == 0) {
            return sVGAAudioEntity;
        }
        SVGAParser.PlayCallback playCallback = this.mPlayCallback;
        if (playCallback != null) {
            ArrayList arrayList = new ArrayList();
            Iterator<Map.Entry<String, File>> it = audiosFileMap.entrySet().iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getValue());
            }
            playCallback.onPlay(arrayList);
            Function0<Unit> function0 = this.mCallback;
            if (function0 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCallback");
            }
            function0.invoke();
            return sVGAAudioEntity;
        }
        File file = audiosFileMap.get(audio.audioKey);
        if (file != null) {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                double dAvailable = fileInputStream.available();
                long j2 = (long) ((dIntValue / dIntValue2) * dAvailable);
                SVGASoundManager sVGASoundManager = SVGASoundManager.INSTANCE;
                if (sVGASoundManager.isInit$com_opensource_svgaplayer()) {
                    sVGAAudioEntity.setSoundID(Integer.valueOf(sVGASoundManager.load$com_opensource_svgaplayer(this.soundCallback, fileInputStream.getFD(), j2, (long) dAvailable, 1)));
                } else {
                    SoundPool soundPool = this.soundPool;
                    sVGAAudioEntity.setSoundID(soundPool != null ? Integer.valueOf(soundPool.load(fileInputStream.getFD(), j2, (long) dAvailable, 1)) : null);
                }
                Unit unit = Unit.INSTANCE;
                CloseableKt.closeFinally(fileInputStream, null);
            } finally {
            }
        }
        return sVGAAudioEntity;
    }

    private final File generateAudioFile(File audioCache, byte[] value) throws IOException {
        audioCache.createNewFile();
        new FileOutputStream(audioCache).write(value);
        return audioCache;
    }

    private final HashMap<String, File> generateAudioFileMap(MovieEntity entity) throws IOException {
        HashMap<String, byte[]> mapGenerateAudioMap = generateAudioMap(entity);
        HashMap<String, File> map = new HashMap<>();
        if (mapGenerateAudioMap.size() > 0) {
            for (Map.Entry<String, byte[]> entry : mapGenerateAudioMap.entrySet()) {
                File fileBuildAudioFile = SVGACache.INSTANCE.buildAudioFile(entry.getKey());
                String key = entry.getKey();
                File fileGenerateAudioFile = fileBuildAudioFile.exists() ? fileBuildAudioFile : null;
                if (fileGenerateAudioFile == null) {
                    fileGenerateAudioFile = generateAudioFile(fileBuildAudioFile, entry.getValue());
                }
                map.put(key, fileGenerateAudioFile);
            }
        }
        return map;
    }

    private final HashMap<String, byte[]> generateAudioMap(MovieEntity entity) {
        Set<Map.Entry<String, ByteString>> setEntrySet;
        HashMap<String, byte[]> map = new HashMap<>();
        Map<String, ByteString> map2 = entity.images;
        if (map2 != null && (setEntrySet = map2.entrySet()) != null) {
            Iterator<T> it = setEntrySet.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String imageKey = (String) entry.getKey();
                byte[] byteArray = ((ByteString) entry.getValue()).toByteArray();
                Intrinsics.checkExpressionValueIsNotNull(byteArray, "byteArray");
                if (byteArray.length >= 4) {
                    List listSlice = ArraysKt___ArraysKt.slice(byteArray, new IntRange(0, 3));
                    if (((Number) listSlice.get(0)).byteValue() == 73 && ((Number) listSlice.get(1)).byteValue() == 68 && ((Number) listSlice.get(2)).byteValue() == 51) {
                        Intrinsics.checkExpressionValueIsNotNull(imageKey, "imageKey");
                        map.put(imageKey, byteArray);
                    } else if (((Number) listSlice.get(0)).byteValue() == -1 && ((Number) listSlice.get(1)).byteValue() == -5 && ((Number) listSlice.get(2)).byteValue() == -108) {
                        Intrinsics.checkExpressionValueIsNotNull(imageKey, "imageKey");
                        map.put(imageKey, byteArray);
                    }
                }
            }
        }
        return map;
    }

    private final String generateBitmapFilePath(String imgName, String imgKey) {
        String str = this.mCacheDir.getAbsolutePath() + "/" + imgName;
        String str2 = str + PictureMimeType.PNG;
        String str3 = this.mCacheDir.getAbsolutePath() + "/" + imgKey + PictureMimeType.PNG;
        return new File(str).exists() ? str : new File(str2).exists() ? str2 : new File(str3).exists() ? str3 : "";
    }

    private final SoundPool generateSoundPool(MovieEntity entity) throws IllegalArgumentException {
        try {
            SoundPool.Builder audioAttributes = new SoundPool.Builder().setAudioAttributes(new AudioAttributes.Builder().setUsage(1).build());
            List<AudioEntity> list = entity.audios;
            Intrinsics.checkExpressionValueIsNotNull(list, "entity.audios");
            return audioAttributes.setMaxStreams(RangesKt___RangesKt.coerceAtMost(12, list.size())).build();
        } catch (Exception e2) {
            LogUtils.INSTANCE.error(this.TAG, e2);
            return null;
        }
    }

    private final void parserImages(JSONObject json) {
        JSONObject jSONObjectOptJSONObject = json.optJSONObject("images");
        if (jSONObjectOptJSONObject != null) {
            Iterator<String> itKeys = jSONObjectOptJSONObject.keys();
            Intrinsics.checkExpressionValueIsNotNull(itKeys, "imgJson.keys()");
            while (itKeys.hasNext()) {
                String imgKey = itKeys.next();
                String string = jSONObjectOptJSONObject.get(imgKey).toString();
                Intrinsics.checkExpressionValueIsNotNull(imgKey, "imgKey");
                String strGenerateBitmapFilePath = generateBitmapFilePath(string, imgKey);
                if (strGenerateBitmapFilePath.length() == 0) {
                    return;
                }
                String strReplace$default = StringsKt__StringsJVMKt.replace$default(imgKey, ".matte", "", false, 4, (Object) null);
                Bitmap bitmapCreateBitmap = createBitmap(strGenerateBitmapFilePath);
                if (bitmapCreateBitmap != null) {
                    this.imageMap.put(strReplace$default, bitmapCreateBitmap);
                }
            }
        }
    }

    private final void resetSprites(JSONObject json) {
        ArrayList arrayList = new ArrayList();
        JSONArray jSONArrayOptJSONArray = json.optJSONArray("sprites");
        if (jSONArrayOptJSONArray != null) {
            int length = jSONArrayOptJSONArray.length();
            for (int i2 = 0; i2 < length; i2++) {
                JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i2);
                if (jSONObjectOptJSONObject != null) {
                    arrayList.add(new SVGAVideoSpriteEntity(jSONObjectOptJSONObject));
                }
            }
        }
        this.spriteList = CollectionsKt___CollectionsKt.toList(arrayList);
    }

    private final void setupAudios(MovieEntity entity, Function0<Unit> completionBlock) throws IOException {
        List<AudioEntity> list = entity.audios;
        if (list == null || list.isEmpty()) {
            completionBlock.invoke();
            return;
        }
        setupSoundPool(entity, completionBlock);
        HashMap<String, File> mapGenerateAudioFileMap = generateAudioFileMap(entity);
        if (mapGenerateAudioFileMap.size() == 0) {
            completionBlock.invoke();
            return;
        }
        List<AudioEntity> list2 = entity.audios;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        for (AudioEntity audio : list2) {
            Intrinsics.checkExpressionValueIsNotNull(audio, "audio");
            arrayList.add(createSvgaAudioEntity(audio, mapGenerateAudioFileMap));
        }
        this.audioList = arrayList;
    }

    private final void setupByJson(JSONObject movieObject) {
        JSONObject jSONObjectOptJSONObject = movieObject.optJSONObject("viewBox");
        if (jSONObjectOptJSONObject != null) {
            this.videoSize = new SVGARect(0.0d, 0.0d, jSONObjectOptJSONObject.optDouble("width", 0.0d), jSONObjectOptJSONObject.optDouble("height", 0.0d));
        }
        this.FPS = movieObject.optInt(AliyunLogKey.KEY_FPS, 20);
        this.frames = movieObject.optInt("frames", 0);
    }

    private final void setupByMovie(MovieParams movieParams) {
        Float f2 = movieParams.viewBoxWidth;
        this.videoSize = new SVGARect(0.0d, 0.0d, f2 != null ? f2.floatValue() : 0.0f, movieParams.viewBoxHeight != null ? r0.floatValue() : 0.0f);
        Integer num = movieParams.fps;
        this.FPS = num != null ? num.intValue() : 20;
        Integer num2 = movieParams.frames;
        this.frames = num2 != null ? num2.intValue() : 0;
    }

    private final void setupSoundPool(final MovieEntity entity, final Function0<Unit> completionBlock) {
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = 0;
        if (SVGASoundManager.INSTANCE.isInit$com_opensource_svgaplayer()) {
            this.soundCallback = new SVGASoundManager.SVGASoundCallBack() { // from class: com.opensource.svgaplayer.SVGAVideoEntity.setupSoundPool.1
                @Override // com.opensource.svgaplayer.SVGASoundManager.SVGASoundCallBack
                public void onComplete() {
                    Ref.IntRef intRef2 = intRef;
                    int i2 = intRef2.element + 1;
                    intRef2.element = i2;
                    List<AudioEntity> list = entity.audios;
                    Intrinsics.checkExpressionValueIsNotNull(list, "entity.audios");
                    if (i2 >= list.size()) {
                        completionBlock.invoke();
                    }
                }

                @Override // com.opensource.svgaplayer.SVGASoundManager.SVGASoundCallBack
                public void onVolumeChange(float value) {
                    SVGASoundManager.INSTANCE.setVolume(value, SVGAVideoEntity.this);
                }
            };
            return;
        }
        this.soundPool = generateSoundPool(entity);
        LogUtils.INSTANCE.info("SVGAParser", "pool_start");
        SoundPool soundPool = this.soundPool;
        if (soundPool != null) {
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: com.opensource.svgaplayer.SVGAVideoEntity.setupSoundPool.2
                @Override // android.media.SoundPool.OnLoadCompleteListener
                public final void onLoadComplete(SoundPool soundPool2, int i2, int i3) {
                    LogUtils.INSTANCE.info("SVGAParser", "pool_complete");
                    Ref.IntRef intRef2 = intRef;
                    int i4 = intRef2.element + 1;
                    intRef2.element = i4;
                    List<AudioEntity> list = entity.audios;
                    Intrinsics.checkExpressionValueIsNotNull(list, "entity.audios");
                    if (i4 >= list.size()) {
                        completionBlock.invoke();
                    }
                }
            });
        }
    }

    public final void clear() {
        if (SVGASoundManager.INSTANCE.isInit$com_opensource_svgaplayer()) {
            Iterator<T> it = this.audioList.iterator();
            while (it.hasNext()) {
                Integer soundID = ((SVGAAudioEntity) it.next()).getSoundID();
                if (soundID != null) {
                    SVGASoundManager.INSTANCE.unload$com_opensource_svgaplayer(soundID.intValue());
                }
            }
            this.soundCallback = null;
        }
        SoundPool soundPool = this.soundPool;
        if (soundPool != null) {
            soundPool.release();
        }
        this.soundPool = null;
        this.audioList = CollectionsKt__CollectionsKt.emptyList();
        this.spriteList = CollectionsKt__CollectionsKt.emptyList();
        this.imageMap.clear();
    }

    public final boolean getAntiAlias() {
        return this.antiAlias;
    }

    @NotNull
    public final List<SVGAAudioEntity> getAudioList$com_opensource_svgaplayer() {
        return this.audioList;
    }

    public final int getFPS() {
        return this.FPS;
    }

    public final int getFrames() {
        return this.frames;
    }

    @NotNull
    public final HashMap<String, Bitmap> getImageMap$com_opensource_svgaplayer() {
        return this.imageMap;
    }

    @Nullable
    public final MovieEntity getMovieItem() {
        return this.movieItem;
    }

    @Nullable
    /* renamed from: getSoundPool$com_opensource_svgaplayer, reason: from getter */
    public final SoundPool getSoundPool() {
        return this.soundPool;
    }

    @NotNull
    public final List<SVGAVideoSpriteEntity> getSpriteList$com_opensource_svgaplayer() {
        return this.spriteList;
    }

    @NotNull
    public final SVGARect getVideoSize() {
        return this.videoSize;
    }

    public final void prepare$com_opensource_svgaplayer(@NotNull Function0<Unit> callback, @Nullable SVGAParser.PlayCallback playCallback) throws IOException {
        Intrinsics.checkParameterIsNotNull(callback, "callback");
        this.mCallback = callback;
        this.mPlayCallback = playCallback;
        MovieEntity movieEntity = this.movieItem;
        if (movieEntity == null) {
            if (callback == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCallback");
            }
            callback.invoke();
        } else {
            if (movieEntity == null) {
                Intrinsics.throwNpe();
            }
            setupAudios(movieEntity, new Function0<Unit>() { // from class: com.opensource.svgaplayer.SVGAVideoEntity$prepare$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    SVGAVideoEntity.access$getMCallback$p(this.this$0).invoke();
                }
            });
        }
    }

    public final void setAntiAlias(boolean z2) {
        this.antiAlias = z2;
    }

    public final void setAudioList$com_opensource_svgaplayer(@NotNull List<SVGAAudioEntity> list) {
        Intrinsics.checkParameterIsNotNull(list, "<set-?>");
        this.audioList = list;
    }

    public final void setImageMap$com_opensource_svgaplayer(@NotNull HashMap<String, Bitmap> map) {
        Intrinsics.checkParameterIsNotNull(map, "<set-?>");
        this.imageMap = map;
    }

    public final void setMovieItem(@Nullable MovieEntity movieEntity) {
        this.movieItem = movieEntity;
    }

    public final void setSoundPool$com_opensource_svgaplayer(@Nullable SoundPool soundPool) {
        this.soundPool = soundPool;
    }

    public final void setSpriteList$com_opensource_svgaplayer(@NotNull List<SVGAVideoSpriteEntity> list) {
        Intrinsics.checkParameterIsNotNull(list, "<set-?>");
        this.spriteList = list;
    }

    public SVGAVideoEntity(@NotNull JSONObject json, @NotNull File cacheDir, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(json, "json");
        Intrinsics.checkParameterIsNotNull(cacheDir, "cacheDir");
        this.TAG = "SVGAVideoEntity";
        this.antiAlias = true;
        this.videoSize = new SVGARect(0.0d, 0.0d, 0.0d, 0.0d);
        this.FPS = 15;
        this.spriteList = CollectionsKt__CollectionsKt.emptyList();
        this.audioList = CollectionsKt__CollectionsKt.emptyList();
        this.imageMap = new HashMap<>();
        this.mFrameWidth = i2;
        this.mFrameHeight = i3;
        this.mCacheDir = cacheDir;
        JSONObject jSONObjectOptJSONObject = json.optJSONObject("movie");
        if (jSONObjectOptJSONObject != null) {
            setupByJson(jSONObjectOptJSONObject);
            try {
                parserImages(json);
            } catch (Exception e2) {
                e2.printStackTrace();
            } catch (OutOfMemoryError e3) {
                e3.printStackTrace();
            }
            resetSprites(json);
        }
    }

    private final Bitmap createBitmap(byte[] byteArray, String filePath) {
        Bitmap bitmapDecodeBitmapFrom = SVGABitmapByteArrayDecoder.INSTANCE.decodeBitmapFrom(byteArray, this.mFrameWidth, this.mFrameHeight);
        return bitmapDecodeBitmapFrom != null ? bitmapDecodeBitmapFrom : createBitmap(filePath);
    }

    private final void resetSprites(MovieEntity entity) {
        List<SVGAVideoSpriteEntity> listEmptyList;
        List<SpriteEntity> list = entity.sprites;
        if (list != null) {
            List<SpriteEntity> list2 = list;
            listEmptyList = new ArrayList<>(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (SpriteEntity it : list2) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                listEmptyList.add(new SVGAVideoSpriteEntity(it));
            }
        } else {
            listEmptyList = CollectionsKt__CollectionsKt.emptyList();
        }
        this.spriteList = listEmptyList;
    }

    private final void parserImages(MovieEntity obj) {
        Set<Map.Entry<String, ByteString>> setEntrySet;
        Map<String, ByteString> map = obj.images;
        if (map == null || (setEntrySet = map.entrySet()) == null) {
            return;
        }
        Iterator<T> it = setEntrySet.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            byte[] byteArray = ((ByteString) entry.getValue()).toByteArray();
            Intrinsics.checkExpressionValueIsNotNull(byteArray, "byteArray");
            if (byteArray.length >= 4) {
                List listSlice = ArraysKt___ArraysKt.slice(byteArray, new IntRange(0, 3));
                if (((Number) listSlice.get(0)).byteValue() != 73 || ((Number) listSlice.get(1)).byteValue() != 68 || ((Number) listSlice.get(2)).byteValue() != 51) {
                    String strUtf8 = ((ByteString) entry.getValue()).utf8();
                    Intrinsics.checkExpressionValueIsNotNull(strUtf8, "entry.value.utf8()");
                    Object key = entry.getKey();
                    Intrinsics.checkExpressionValueIsNotNull(key, "entry.key");
                    Bitmap bitmapCreateBitmap = createBitmap(byteArray, generateBitmapFilePath(strUtf8, (String) key));
                    if (bitmapCreateBitmap != null) {
                        AbstractMap abstractMap = this.imageMap;
                        Object key2 = entry.getKey();
                        Intrinsics.checkExpressionValueIsNotNull(key2, "entry.key");
                        abstractMap.put(key2, bitmapCreateBitmap);
                    }
                }
            }
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public SVGAVideoEntity(@NotNull MovieEntity entity, @NotNull File cacheDir) {
        this(entity, cacheDir, 0, 0);
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        Intrinsics.checkParameterIsNotNull(cacheDir, "cacheDir");
    }

    public SVGAVideoEntity(@NotNull MovieEntity entity, @NotNull File cacheDir, int i2, int i3) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        Intrinsics.checkParameterIsNotNull(cacheDir, "cacheDir");
        this.TAG = "SVGAVideoEntity";
        this.antiAlias = true;
        this.videoSize = new SVGARect(0.0d, 0.0d, 0.0d, 0.0d);
        this.FPS = 15;
        this.spriteList = CollectionsKt__CollectionsKt.emptyList();
        this.audioList = CollectionsKt__CollectionsKt.emptyList();
        this.imageMap = new HashMap<>();
        this.mFrameWidth = i2;
        this.mFrameHeight = i3;
        this.mCacheDir = cacheDir;
        this.movieItem = entity;
        MovieParams movieParams = entity.params;
        if (movieParams != null) {
            setupByMovie(movieParams);
        }
        try {
            parserImages(entity);
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (OutOfMemoryError e3) {
            e3.printStackTrace();
        }
        resetSprites(entity);
    }
}
