package com.google.android.exoplayer2;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.audio.AudioCapabilities;
import com.google.android.exoplayer2.audio.AudioProcessor;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.audio.AudioSink;
import com.google.android.exoplayer2.audio.DefaultAudioSink;
import com.google.android.exoplayer2.audio.MediaCodecAudioRenderer;
import com.google.android.exoplayer2.mediacodec.DefaultMediaCodecAdapterFactory;
import com.google.android.exoplayer2.mediacodec.MediaCodecAdapter;
import com.google.android.exoplayer2.mediacodec.MediaCodecSelector;
import com.google.android.exoplayer2.metadata.MetadataOutput;
import com.google.android.exoplayer2.metadata.MetadataRenderer;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.text.TextRenderer;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.video.MediaCodecVideoRenderer;
import com.google.android.exoplayer2.video.VideoRendererEventListener;
import com.google.android.exoplayer2.video.spherical.CameraMotionRenderer;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class DefaultRenderersFactory implements RenderersFactory {
    public static final long DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS = 5000;
    public static final int EXTENSION_RENDERER_MODE_OFF = 0;
    public static final int EXTENSION_RENDERER_MODE_ON = 1;
    public static final int EXTENSION_RENDERER_MODE_PREFER = 2;
    public static final int MAX_DROPPED_VIDEO_FRAME_COUNT_TO_NOTIFY = 50;
    private static final String TAG = "DefaultRenderersFactory";
    private long allowedVideoJoiningTimeMs;
    private final DefaultMediaCodecAdapterFactory codecAdapterFactory;
    private final Context context;
    private boolean enableAudioTrackPlaybackParams;
    private boolean enableDecoderFallback;
    private boolean enableFloatOutput;
    private boolean enableOffload;
    private int extensionRendererMode;
    private MediaCodecSelector mediaCodecSelector;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface ExtensionRendererMode {
    }

    public DefaultRenderersFactory(Context context) {
        this.context = context;
        this.codecAdapterFactory = new DefaultMediaCodecAdapterFactory();
        this.extensionRendererMode = 0;
        this.allowedVideoJoiningTimeMs = 5000L;
        this.mediaCodecSelector = MediaCodecSelector.DEFAULT;
    }

    public void buildAudioRenderers(Context context, int i2, MediaCodecSelector mediaCodecSelector, boolean z2, AudioSink audioSink, Handler handler, AudioRendererEventListener audioRendererEventListener, ArrayList<Renderer> arrayList) {
        int i3;
        arrayList.add(new MediaCodecAudioRenderer(context, getCodecAdapterFactory(), mediaCodecSelector, z2, handler, audioRendererEventListener, audioSink));
        if (i2 == 0) {
            return;
        }
        int size = arrayList.size();
        if (i2 == 2) {
            size--;
        }
        try {
            try {
                i3 = size + 1;
            } catch (Exception e2) {
                throw new RuntimeException("Error instantiating Opus extension", e2);
            }
        } catch (ClassNotFoundException unused) {
        }
        try {
            arrayList.add(size, (Renderer) Class.forName("com.google.android.exoplayer2.ext.opus.LibopusAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
            Log.i(TAG, "Loaded LibopusAudioRenderer.");
        } catch (ClassNotFoundException unused2) {
            size = i3;
            i3 = size;
            try {
                int i4 = i3 + 1;
                arrayList.add(i3, (Renderer) Class.forName("com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                Log.i(TAG, "Loaded LibflacAudioRenderer.");
            } catch (ClassNotFoundException unused3) {
            }
            try {
                arrayList.add(i4, (Renderer) Class.forName("com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                Log.i(TAG, "Loaded FfmpegAudioRenderer.");
            } catch (ClassNotFoundException unused4) {
                return;
            } catch (Exception e3) {
                throw new RuntimeException("Error instantiating FFmpeg extension", e3);
            }
        }
        try {
            int i42 = i3 + 1;
            try {
                arrayList.add(i3, (Renderer) Class.forName("com.google.android.exoplayer2.ext.flac.LibflacAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                Log.i(TAG, "Loaded LibflacAudioRenderer.");
            } catch (ClassNotFoundException unused5) {
                i3 = i42;
                i42 = i3;
                arrayList.add(i42, (Renderer) Class.forName("com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
                Log.i(TAG, "Loaded FfmpegAudioRenderer.");
            }
            arrayList.add(i42, (Renderer) Class.forName("com.google.android.exoplayer2.ext.ffmpeg.FfmpegAudioRenderer").getConstructor(Handler.class, AudioRendererEventListener.class, AudioSink.class).newInstance(handler, audioRendererEventListener, audioSink));
            Log.i(TAG, "Loaded FfmpegAudioRenderer.");
        } catch (Exception e4) {
            throw new RuntimeException("Error instantiating FLAC extension", e4);
        }
    }

    @Nullable
    public AudioSink buildAudioSink(Context context, boolean z2, boolean z3, boolean z4) {
        return new DefaultAudioSink(AudioCapabilities.getCapabilities(context), new DefaultAudioSink.DefaultAudioProcessorChain(new AudioProcessor[0]), z2, z3, z4 ? 1 : 0);
    }

    public void buildCameraMotionRenderers(Context context, int i2, ArrayList<Renderer> arrayList) {
        arrayList.add(new CameraMotionRenderer());
    }

    public void buildMetadataRenderers(Context context, MetadataOutput metadataOutput, Looper looper, int i2, ArrayList<Renderer> arrayList) {
        arrayList.add(new MetadataRenderer(metadataOutput, looper));
    }

    public void buildMiscellaneousRenderers(Context context, Handler handler, int i2, ArrayList<Renderer> arrayList) {
    }

    public void buildTextRenderers(Context context, TextOutput textOutput, Looper looper, int i2, ArrayList<Renderer> arrayList) {
        arrayList.add(new TextRenderer(textOutput, looper));
    }

    public void buildVideoRenderers(Context context, int i2, MediaCodecSelector mediaCodecSelector, boolean z2, Handler handler, VideoRendererEventListener videoRendererEventListener, long j2, ArrayList<Renderer> arrayList) {
        int i3;
        arrayList.add(new MediaCodecVideoRenderer(context, getCodecAdapterFactory(), mediaCodecSelector, j2, z2, handler, videoRendererEventListener, 50));
        if (i2 == 0) {
            return;
        }
        int size = arrayList.size();
        if (i2 == 2) {
            size--;
        }
        try {
            try {
                i3 = size + 1;
            } catch (ClassNotFoundException unused) {
            }
            try {
                try {
                    arrayList.add(size, (Renderer) Class.forName("com.google.android.exoplayer2.ext.vp9.LibvpxVideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j2), handler, videoRendererEventListener, 50));
                    Log.i(TAG, "Loaded LibvpxVideoRenderer.");
                } catch (ClassNotFoundException unused2) {
                    size = i3;
                    i3 = size;
                    arrayList.add(i3, (Renderer) Class.forName("com.google.android.exoplayer2.ext.av1.Libgav1VideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j2), handler, videoRendererEventListener, 50));
                    Log.i(TAG, "Loaded Libgav1VideoRenderer.");
                }
                arrayList.add(i3, (Renderer) Class.forName("com.google.android.exoplayer2.ext.av1.Libgav1VideoRenderer").getConstructor(Long.TYPE, Handler.class, VideoRendererEventListener.class, Integer.TYPE).newInstance(Long.valueOf(j2), handler, videoRendererEventListener, 50));
                Log.i(TAG, "Loaded Libgav1VideoRenderer.");
            } catch (ClassNotFoundException unused3) {
            } catch (Exception e2) {
                throw new RuntimeException("Error instantiating AV1 extension", e2);
            }
        } catch (Exception e3) {
            throw new RuntimeException("Error instantiating VP9 extension", e3);
        }
    }

    @Override // com.google.android.exoplayer2.RenderersFactory
    public Renderer[] createRenderers(Handler handler, VideoRendererEventListener videoRendererEventListener, AudioRendererEventListener audioRendererEventListener, TextOutput textOutput, MetadataOutput metadataOutput) {
        ArrayList<Renderer> arrayList = new ArrayList<>();
        buildVideoRenderers(this.context, this.extensionRendererMode, this.mediaCodecSelector, this.enableDecoderFallback, handler, videoRendererEventListener, this.allowedVideoJoiningTimeMs, arrayList);
        AudioSink audioSinkBuildAudioSink = buildAudioSink(this.context, this.enableFloatOutput, this.enableAudioTrackPlaybackParams, this.enableOffload);
        if (audioSinkBuildAudioSink != null) {
            buildAudioRenderers(this.context, this.extensionRendererMode, this.mediaCodecSelector, this.enableDecoderFallback, audioSinkBuildAudioSink, handler, audioRendererEventListener, arrayList);
        }
        buildTextRenderers(this.context, textOutput, handler.getLooper(), this.extensionRendererMode, arrayList);
        buildMetadataRenderers(this.context, metadataOutput, handler.getLooper(), this.extensionRendererMode, arrayList);
        buildCameraMotionRenderers(this.context, this.extensionRendererMode, arrayList);
        buildMiscellaneousRenderers(this.context, handler, this.extensionRendererMode, arrayList);
        return (Renderer[]) arrayList.toArray(new Renderer[0]);
    }

    public DefaultRenderersFactory experimentalSetImmediateCodecStartAfterFlushEnabled(boolean z2) {
        this.codecAdapterFactory.experimentalSetImmediateCodecStartAfterFlushEnabled(z2);
        return this;
    }

    public DefaultRenderersFactory experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(boolean z2) {
        this.codecAdapterFactory.experimentalSetSynchronizeCodecInteractionsWithQueueingEnabled(z2);
        return this;
    }

    public DefaultRenderersFactory forceDisableMediaCodecAsynchronousQueueing() {
        this.codecAdapterFactory.forceDisableAsynchronous();
        return this;
    }

    public DefaultRenderersFactory forceEnableMediaCodecAsynchronousQueueing() {
        this.codecAdapterFactory.forceEnableAsynchronous();
        return this;
    }

    public MediaCodecAdapter.Factory getCodecAdapterFactory() {
        return this.codecAdapterFactory;
    }

    public DefaultRenderersFactory setAllowedVideoJoiningTimeMs(long j2) {
        this.allowedVideoJoiningTimeMs = j2;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioFloatOutput(boolean z2) {
        this.enableFloatOutput = z2;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioOffload(boolean z2) {
        this.enableOffload = z2;
        return this;
    }

    public DefaultRenderersFactory setEnableAudioTrackPlaybackParams(boolean z2) {
        this.enableAudioTrackPlaybackParams = z2;
        return this;
    }

    public DefaultRenderersFactory setEnableDecoderFallback(boolean z2) {
        this.enableDecoderFallback = z2;
        return this;
    }

    public DefaultRenderersFactory setExtensionRendererMode(int i2) {
        this.extensionRendererMode = i2;
        return this;
    }

    public DefaultRenderersFactory setMediaCodecSelector(MediaCodecSelector mediaCodecSelector) {
        this.mediaCodecSelector = mediaCodecSelector;
        return this;
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, int i2) {
        this(context, i2, 5000L);
    }

    @Deprecated
    public DefaultRenderersFactory(Context context, int i2, long j2) {
        this.context = context;
        this.extensionRendererMode = i2;
        this.allowedVideoJoiningTimeMs = j2;
        this.mediaCodecSelector = MediaCodecSelector.DEFAULT;
        this.codecAdapterFactory = new DefaultMediaCodecAdapterFactory();
    }
}
