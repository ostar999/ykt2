package androidx.camera.core;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.location.Location;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Pair;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.DoNotInline;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.UseCase;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.Config;
import androidx.camera.core.impl.ConfigProvider;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageOutputConfig;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.MutableConfig;
import androidx.camera.core.impl.MutableOptionsBundle;
import androidx.camera.core.impl.OptionsBundle;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.UseCaseConfig;
import androidx.camera.core.impl.UseCaseConfigFactory;
import androidx.camera.core.impl.VideoCaptureConfig;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.TargetConfig;
import androidx.camera.core.internal.ThreadConfig;
import androidx.camera.core.internal.UseCaseEventConfig;
import androidx.camera.core.internal.utils.VideoUtil;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import cn.hutool.core.text.StrPool;
import com.easefun.polyv.mediasdk.player.IjkMediaMeta;
import com.google.common.util.concurrent.ListenableFuture;
import com.hjq.permissions.Permission;
import com.yikaobang.yixue.R2;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.lingala.zip4j.util.InternalZipConstants;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
@Deprecated
/* loaded from: classes.dex */
public final class VideoCapture extends UseCase {
    private static final String AUDIO_MIME_TYPE = "audio/mp4a-latm";
    private static final int DEQUE_TIMEOUT_USEC = 10000;
    public static final int ERROR_ENCODER = 1;
    public static final int ERROR_FILE_IO = 4;
    public static final int ERROR_INVALID_CAMERA = 5;
    public static final int ERROR_MUXER = 2;
    public static final int ERROR_RECORDING_IN_PROGRESS = 3;
    public static final int ERROR_RECORDING_TOO_SHORT = 6;
    public static final int ERROR_UNKNOWN = 0;
    private static final String TAG = "VideoCapture";
    private static final String VIDEO_MIME_TYPE = "video/avc";
    private int mAudioBitRate;
    private final MediaCodec.BufferInfo mAudioBufferInfo;
    private volatile int mAudioBufferSize;
    private int mAudioChannelCount;

    @NonNull
    private MediaCodec mAudioEncoder;
    private Handler mAudioHandler;
    private HandlerThread mAudioHandlerThread;

    @Nullable
    private volatile AudioRecord mAudioRecorder;
    private int mAudioSampleRate;

    @GuardedBy("mMuxerLock")
    private int mAudioTrackIndex;
    Surface mCameraSurface;
    private DeferrableSurface mDeferrableSurface;
    private final AtomicBoolean mEndOfAudioStreamSignal;
    private final AtomicBoolean mEndOfAudioVideoSignal;
    private final AtomicBoolean mEndOfVideoStreamSignal;
    private final AtomicBoolean mIsAudioEnabled;

    @VisibleForTesting(otherwise = 2)
    public final AtomicBoolean mIsFirstAudioSampleWrite;

    @VisibleForTesting(otherwise = 2)
    public final AtomicBoolean mIsFirstVideoKeyFrameWrite;
    private volatile boolean mIsRecording;

    @GuardedBy("mMuxerLock")
    private MediaMuxer mMuxer;
    private final Object mMuxerLock;
    private final AtomicBoolean mMuxerStarted;
    private volatile ParcelFileDescriptor mParcelFileDescriptor;

    @Nullable
    private ListenableFuture<Void> mRecordingFuture;
    volatile Uri mSavedVideoUri;

    @NonNull
    private SessionConfig.Builder mSessionConfigBuilder;
    private final MediaCodec.BufferInfo mVideoBufferInfo;

    @NonNull
    MediaCodec mVideoEncoder;

    @Nullable
    private Throwable mVideoEncoderErrorMessage;
    private VideoEncoderInitStatus mVideoEncoderInitStatus;
    private Handler mVideoHandler;
    private HandlerThread mVideoHandlerThread;

    @GuardedBy("mMuxerLock")
    private int mVideoTrackIndex;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final Defaults DEFAULT_CONFIG = new Defaults();
    private static final int[] CamcorderQuality = {8, 6, 5, 4};

    @RequiresApi(23)
    public static class Api23Impl {
        private Api23Impl() {
        }

        @DoNotInline
        public static int getCodecExceptionErrorCode(MediaCodec.CodecException codecException) {
            return codecException.getErrorCode();
        }
    }

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @NonNull
        @DoNotInline
        public static MediaMuxer createMediaMuxer(@NonNull FileDescriptor fileDescriptor, int i2) throws IOException {
            return new MediaMuxer(fileDescriptor, i2);
        }
    }

    public static final class Builder implements UseCaseConfig.Builder<VideoCapture, VideoCaptureConfig, Builder>, ImageOutputConfig.Builder<Builder>, ThreadConfig.Builder<Builder> {
        private final MutableOptionsBundle mMutableConfig;

        public Builder() {
            this(MutableOptionsBundle.create());
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static Builder fromConfig(@NonNull Config config) {
            return new Builder(MutableOptionsBundle.from(config));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public MutableConfig getMutableConfig() {
            return this.mMutableConfig;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAudioBitRate(int i2) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_BIT_RATE, Integer.valueOf(i2));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAudioChannelCount(int i2) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_CHANNEL_COUNT, Integer.valueOf(i2));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAudioMinBufferSize(int i2) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_MIN_BUFFER_SIZE, Integer.valueOf(i2));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setAudioSampleRate(int i2) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_AUDIO_SAMPLE_RATE, Integer.valueOf(i2));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setBitRate(int i2) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_BIT_RATE, Integer.valueOf(i2));
            return this;
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setIFrameInterval(int i2) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_INTRA_FRAME_INTERVAL, Integer.valueOf(i2));
            return this;
        }

        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public /* bridge */ /* synthetic */ Builder setSupportedResolutions(@NonNull List list) {
            return setSupportedResolutions((List<Pair<Integer, Size[]>>) list);
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public /* bridge */ /* synthetic */ Object setTargetClass(@NonNull Class cls) {
            return setTargetClass((Class<VideoCapture>) cls);
        }

        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setVideoFrameRate(int i2) {
            getMutableConfig().insertOption(VideoCaptureConfig.OPTION_VIDEO_FRAME_RATE, Integer.valueOf(i2));
            return this;
        }

        private Builder(@NonNull MutableOptionsBundle mutableOptionsBundle) {
            this.mMutableConfig = mutableOptionsBundle;
            Class cls = (Class) mutableOptionsBundle.retrieveOption(TargetConfig.OPTION_TARGET_CLASS, null);
            if (cls == null || cls.equals(VideoCapture.class)) {
                setTargetClass(VideoCapture.class);
                return;
            }
            throw new IllegalArgumentException("Invalid target class configuration for " + this + ": " + cls);
        }

        @NonNull
        public static Builder fromConfig(@NonNull VideoCaptureConfig videoCaptureConfig) {
            return new Builder(MutableOptionsBundle.from((Config) videoCaptureConfig));
        }

        @Override // androidx.camera.core.ExtendableBuilder
        @NonNull
        public VideoCapture build() {
            if (getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, null) == null || getMutableConfig().retrieveOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, null) == null) {
                return new VideoCapture(getUseCaseConfig());
            }
            throw new IllegalArgumentException("Cannot use both setTargetResolution and setTargetAspectRatio on the same config.");
        }

        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public VideoCaptureConfig getUseCaseConfig() {
            return new VideoCaptureConfig(OptionsBundle.from(this.mMutableConfig));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.internal.ThreadConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setBackgroundExecutor(@NonNull Executor executor) {
            getMutableConfig().insertOption(ThreadConfig.OPTION_BACKGROUND_EXECUTOR, executor);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY})
        public Builder setCameraSelector(@NonNull CameraSelector cameraSelector) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAMERA_SELECTOR, cameraSelector);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setCaptureOptionUnpacker(@NonNull CaptureConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_CAPTURE_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultCaptureConfig(@NonNull CaptureConfig captureConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_CAPTURE_CONFIG, captureConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultResolution(@NonNull Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_DEFAULT_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setDefaultSessionConfig(@NonNull SessionConfig sessionConfig) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_DEFAULT_SESSION_CONFIG, sessionConfig);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setMaxResolution(@NonNull Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_MAX_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSessionOptionUnpacker(@NonNull SessionConfig.OptionUnpacker optionUnpacker) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SESSION_CONFIG_UNPACKER, optionUnpacker);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSupportedResolutions(@NonNull List<Pair<Integer, Size[]>> list) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_SUPPORTED_RESOLUTIONS, list);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setSurfaceOccupancyPriority(int i2) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_SURFACE_OCCUPANCY_PRIORITY, Integer.valueOf(i2));
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetAspectRatio(int i2) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ASPECT_RATIO, Integer.valueOf(i2));
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetClass(@NonNull Class<VideoCapture> cls) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_CLASS, cls);
            if (getMutableConfig().retrieveOption(TargetConfig.OPTION_TARGET_NAME, null) == null) {
                setTargetName(cls.getCanonicalName() + "-" + UUID.randomUUID());
            }
            return this;
        }

        @Override // androidx.camera.core.internal.TargetConfig.Builder
        @NonNull
        public Builder setTargetName(@NonNull String str) {
            getMutableConfig().insertOption(TargetConfig.OPTION_TARGET_NAME, str);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetResolution(@NonNull Size size) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_RESOLUTION, size);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.ImageOutputConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setTargetRotation(int i2) {
            getMutableConfig().insertOption(ImageOutputConfig.OPTION_TARGET_ROTATION, Integer.valueOf(i2));
            return this;
        }

        @Override // androidx.camera.core.internal.UseCaseEventConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setUseCaseEventCallback(@NonNull UseCase.EventCallback eventCallback) {
            getMutableConfig().insertOption(UseCaseEventConfig.OPTION_USE_CASE_EVENT_CALLBACK, eventCallback);
            return this;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // androidx.camera.core.impl.UseCaseConfig.Builder
        @NonNull
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder setZslDisabled(boolean z2) {
            getMutableConfig().insertOption(UseCaseConfig.OPTION_ZSL_DISABLED, Boolean.valueOf(z2));
            return this;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class Defaults implements ConfigProvider<VideoCaptureConfig> {
        private static final int DEFAULT_ASPECT_RATIO = 1;
        private static final int DEFAULT_AUDIO_BIT_RATE = 64000;
        private static final int DEFAULT_AUDIO_CHANNEL_COUNT = 1;
        private static final int DEFAULT_AUDIO_MIN_BUFFER_SIZE = 1024;
        private static final int DEFAULT_AUDIO_SAMPLE_RATE = 8000;
        private static final int DEFAULT_BIT_RATE = 8388608;
        private static final VideoCaptureConfig DEFAULT_CONFIG;
        private static final int DEFAULT_INTRA_FRAME_INTERVAL = 1;
        private static final Size DEFAULT_MAX_RESOLUTION;
        private static final int DEFAULT_SURFACE_OCCUPANCY_PRIORITY = 3;
        private static final int DEFAULT_VIDEO_FRAME_RATE = 30;

        static {
            Size size = new Size(R2.attr.iconTint, R2.attr.color_hot_circle_one_end);
            DEFAULT_MAX_RESOLUTION = size;
            DEFAULT_CONFIG = new Builder().setVideoFrameRate(30).setBitRate(8388608).setIFrameInterval(1).setAudioBitRate(DEFAULT_AUDIO_BIT_RATE).setAudioSampleRate(8000).setAudioChannelCount(1).setAudioMinBufferSize(1024).setMaxResolution(size).setSurfaceOccupancyPriority(3).setTargetAspectRatio(1).getUseCaseConfig();
        }

        @Override // androidx.camera.core.impl.ConfigProvider
        @NonNull
        public VideoCaptureConfig getConfig() {
            return DEFAULT_CONFIG;
        }
    }

    public static final class Metadata {

        @Nullable
        public Location location;
    }

    public interface OnVideoSavedCallback {
        void onError(int i2, @NonNull String str, @Nullable Throwable th);

        void onVideoSaved(@NonNull OutputFileResults outputFileResults);
    }

    public static final class OutputFileOptions {
        private static final Metadata EMPTY_METADATA = new Metadata();

        @Nullable
        private final ContentResolver mContentResolver;

        @Nullable
        private final ContentValues mContentValues;

        @Nullable
        private final File mFile;

        @Nullable
        private final FileDescriptor mFileDescriptor;

        @Nullable
        private final Metadata mMetadata;

        @Nullable
        private final Uri mSaveCollection;

        public OutputFileOptions(@Nullable File file, @Nullable FileDescriptor fileDescriptor, @Nullable ContentResolver contentResolver, @Nullable Uri uri, @Nullable ContentValues contentValues, @Nullable Metadata metadata) {
            this.mFile = file;
            this.mFileDescriptor = fileDescriptor;
            this.mContentResolver = contentResolver;
            this.mSaveCollection = uri;
            this.mContentValues = contentValues;
            this.mMetadata = metadata == null ? EMPTY_METADATA : metadata;
        }

        @Nullable
        public ContentResolver getContentResolver() {
            return this.mContentResolver;
        }

        @Nullable
        public ContentValues getContentValues() {
            return this.mContentValues;
        }

        @Nullable
        public File getFile() {
            return this.mFile;
        }

        @Nullable
        public FileDescriptor getFileDescriptor() {
            return this.mFileDescriptor;
        }

        @Nullable
        public Metadata getMetadata() {
            return this.mMetadata;
        }

        @Nullable
        public Uri getSaveCollection() {
            return this.mSaveCollection;
        }

        public boolean isSavingToFile() {
            return getFile() != null;
        }

        public boolean isSavingToFileDescriptor() {
            return getFileDescriptor() != null;
        }

        public boolean isSavingToMediaStore() {
            return (getSaveCollection() == null || getContentResolver() == null || getContentValues() == null) ? false : true;
        }

        public static final class Builder {

            @Nullable
            private ContentResolver mContentResolver;

            @Nullable
            private ContentValues mContentValues;

            @Nullable
            private File mFile;

            @Nullable
            private FileDescriptor mFileDescriptor;

            @Nullable
            private Metadata mMetadata;

            @Nullable
            private Uri mSaveCollection;

            public Builder(@NonNull File file) {
                this.mFile = file;
            }

            @NonNull
            public OutputFileOptions build() {
                return new OutputFileOptions(this.mFile, this.mFileDescriptor, this.mContentResolver, this.mSaveCollection, this.mContentValues, this.mMetadata);
            }

            @NonNull
            public Builder setMetadata(@NonNull Metadata metadata) {
                this.mMetadata = metadata;
                return this;
            }

            public Builder(@NonNull FileDescriptor fileDescriptor) {
                Preconditions.checkArgument(Build.VERSION.SDK_INT >= 26, "Using a FileDescriptor to record a video is only supported for Android 8.0 or above.");
                this.mFileDescriptor = fileDescriptor;
            }

            public Builder(@NonNull ContentResolver contentResolver, @NonNull Uri uri, @NonNull ContentValues contentValues) {
                this.mContentResolver = contentResolver;
                this.mSaveCollection = uri;
                this.mContentValues = contentValues;
            }
        }
    }

    public static class OutputFileResults {

        @Nullable
        private Uri mSavedUri;

        public OutputFileResults(@Nullable Uri uri) {
            this.mSavedUri = uri;
        }

        @Nullable
        public Uri getSavedUri() {
            return this.mSavedUri;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface VideoCaptureError {
    }

    public enum VideoEncoderInitStatus {
        VIDEO_ENCODER_INIT_STATUS_UNINITIALIZED,
        VIDEO_ENCODER_INIT_STATUS_INITIALIZED_FAILED,
        VIDEO_ENCODER_INIT_STATUS_INSUFFICIENT_RESOURCE,
        VIDEO_ENCODER_INIT_STATUS_RESOURCE_RECLAIMED
    }

    public static final class VideoSavedListenerWrapper implements OnVideoSavedCallback {

        @NonNull
        Executor mExecutor;

        @NonNull
        OnVideoSavedCallback mOnVideoSavedCallback;

        public VideoSavedListenerWrapper(@NonNull Executor executor, @NonNull OnVideoSavedCallback onVideoSavedCallback) {
            this.mExecutor = executor;
            this.mOnVideoSavedCallback = onVideoSavedCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i2, String str, Throwable th) {
            this.mOnVideoSavedCallback.onError(i2, str, th);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVideoSaved$0(OutputFileResults outputFileResults) {
            this.mOnVideoSavedCallback.onVideoSaved(outputFileResults);
        }

        @Override // androidx.camera.core.VideoCapture.OnVideoSavedCallback
        public void onError(final int i2, @NonNull final String str, @Nullable final Throwable th) {
            try {
                this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.e2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f1493c.lambda$onError$1(i2, str, th);
                    }
                });
            } catch (RejectedExecutionException unused) {
                Logger.e(VideoCapture.TAG, "Unable to post to the supplied executor.");
            }
        }

        @Override // androidx.camera.core.VideoCapture.OnVideoSavedCallback
        public void onVideoSaved(@NonNull final OutputFileResults outputFileResults) {
            try {
                this.mExecutor.execute(new Runnable() { // from class: androidx.camera.core.d2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f1486c.lambda$onVideoSaved$0(outputFileResults);
                    }
                });
            } catch (RejectedExecutionException unused) {
                Logger.e(VideoCapture.TAG, "Unable to post to the supplied executor.");
            }
        }
    }

    public VideoCapture(@NonNull VideoCaptureConfig videoCaptureConfig) {
        super(videoCaptureConfig);
        this.mVideoBufferInfo = new MediaCodec.BufferInfo();
        this.mMuxerLock = new Object();
        this.mEndOfVideoStreamSignal = new AtomicBoolean(true);
        this.mEndOfAudioStreamSignal = new AtomicBoolean(true);
        this.mEndOfAudioVideoSignal = new AtomicBoolean(true);
        this.mAudioBufferInfo = new MediaCodec.BufferInfo();
        this.mIsFirstVideoKeyFrameWrite = new AtomicBoolean(false);
        this.mIsFirstAudioSampleWrite = new AtomicBoolean(false);
        this.mRecordingFuture = null;
        this.mSessionConfigBuilder = new SessionConfig.Builder();
        this.mMuxerStarted = new AtomicBoolean(false);
        this.mIsRecording = false;
        this.mIsAudioEnabled = new AtomicBoolean(true);
        this.mVideoEncoderInitStatus = VideoEncoderInitStatus.VIDEO_ENCODER_INIT_STATUS_UNINITIALIZED;
    }

    @RequiresPermission(Permission.RECORD_AUDIO)
    private AudioRecord autoConfigAudioRecordSource(VideoCaptureConfig videoCaptureConfig) {
        int i2 = this.mAudioChannelCount == 1 ? 16 : 12;
        try {
            int minBufferSize = AudioRecord.getMinBufferSize(this.mAudioSampleRate, i2, 2);
            if (minBufferSize <= 0) {
                minBufferSize = videoCaptureConfig.getAudioMinBufferSize();
            }
            int i3 = minBufferSize;
            AudioRecord audioRecord = new AudioRecord(5, this.mAudioSampleRate, i2, 2, i3 * 2);
            if (audioRecord.getState() != 1) {
                return null;
            }
            this.mAudioBufferSize = i3;
            Logger.i(TAG, "source: 5 audioSampleRate: " + this.mAudioSampleRate + " channelConfig: " + i2 + " audioFormat: 2 bufferSize: " + i3);
            return audioRecord;
        } catch (Exception e2) {
            Logger.e(TAG, "Exception, keep trying.", e2);
            return null;
        }
    }

    private MediaFormat createAudioMediaFormat() {
        MediaFormat mediaFormatCreateAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", this.mAudioSampleRate, this.mAudioChannelCount);
        mediaFormatCreateAudioFormat.setInteger("aac-profile", 2);
        mediaFormatCreateAudioFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, this.mAudioBitRate);
        return mediaFormatCreateAudioFormat;
    }

    private static MediaFormat createVideoMediaFormat(VideoCaptureConfig videoCaptureConfig, Size size) {
        MediaFormat mediaFormatCreateVideoFormat = MediaFormat.createVideoFormat("video/avc", size.getWidth(), size.getHeight());
        mediaFormatCreateVideoFormat.setInteger("color-format", 2130708361);
        mediaFormatCreateVideoFormat.setInteger(IjkMediaMeta.IJKM_KEY_BITRATE, videoCaptureConfig.getBitRate());
        mediaFormatCreateVideoFormat.setInteger("frame-rate", videoCaptureConfig.getVideoFrameRate());
        mediaFormatCreateVideoFormat.setInteger("i-frame-interval", videoCaptureConfig.getIFrameInterval());
        return mediaFormatCreateVideoFormat;
    }

    private ByteBuffer getInputBuffer(MediaCodec mediaCodec, int i2) {
        return mediaCodec.getInputBuffer(i2);
    }

    private ByteBuffer getOutputBuffer(MediaCodec mediaCodec, int i2) {
        return mediaCodec.getOutputBuffer(i2);
    }

    @NonNull
    private MediaMuxer initMediaMuxer(@NonNull OutputFileOptions outputFileOptions) throws Throwable {
        MediaMuxer mediaMuxerCreateMediaMuxer;
        if (outputFileOptions.isSavingToFile()) {
            File file = outputFileOptions.getFile();
            this.mSavedVideoUri = Uri.fromFile(outputFileOptions.getFile());
            return new MediaMuxer(file.getAbsolutePath(), 0);
        }
        if (outputFileOptions.isSavingToFileDescriptor()) {
            if (Build.VERSION.SDK_INT >= 26) {
                return Api26Impl.createMediaMuxer(outputFileOptions.getFileDescriptor(), 0);
            }
            throw new IllegalArgumentException("Using a FileDescriptor to record a video is only supported for Android 8.0 or above.");
        }
        if (!outputFileOptions.isSavingToMediaStore()) {
            throw new IllegalArgumentException("The OutputFileOptions should assign before recording");
        }
        this.mSavedVideoUri = outputFileOptions.getContentResolver().insert(outputFileOptions.getSaveCollection(), outputFileOptions.getContentValues() != null ? new ContentValues(outputFileOptions.getContentValues()) : new ContentValues());
        if (this.mSavedVideoUri == null) {
            throw new IOException("Invalid Uri!");
        }
        try {
            if (Build.VERSION.SDK_INT < 26) {
                String absolutePathFromUri = VideoUtil.getAbsolutePathFromUri(outputFileOptions.getContentResolver(), this.mSavedVideoUri);
                Logger.i(TAG, "Saved Location Path: " + absolutePathFromUri);
                mediaMuxerCreateMediaMuxer = new MediaMuxer(absolutePathFromUri, 0);
            } else {
                this.mParcelFileDescriptor = outputFileOptions.getContentResolver().openFileDescriptor(this.mSavedVideoUri, InternalZipConstants.WRITE_MODE);
                mediaMuxerCreateMediaMuxer = Api26Impl.createMediaMuxer(this.mParcelFileDescriptor.getFileDescriptor(), 0);
            }
            return mediaMuxerCreateMediaMuxer;
        } catch (IOException e2) {
            this.mSavedVideoUri = null;
            throw e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$releaseCameraSurface$7(boolean z2, MediaCodec mediaCodec) {
        if (!z2 || mediaCodec == null) {
            return;
        }
        mediaCodec.release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Object lambda$startRecording$1(AtomicReference atomicReference, CallbackToFutureAdapter.Completer completer) throws Exception {
        atomicReference.set(completer);
        return "startRecording";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecording$2() {
        this.mRecordingFuture = null;
        if (getCamera() != null) {
            setupEncoder(getCameraId(), getAttachedSurfaceResolution());
            notifyReset();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRecording$4(OnVideoSavedCallback onVideoSavedCallback, String str, Size size, OutputFileOptions outputFileOptions, CallbackToFutureAdapter.Completer completer) {
        if (!videoEncode(onVideoSavedCallback, str, size, outputFileOptions)) {
            onVideoSavedCallback.onVideoSaved(new OutputFileResults(this.mSavedVideoUri));
            this.mSavedVideoUri = null;
        }
        completer.set(null);
    }

    private void releaseAudioInputResource() {
        this.mAudioHandlerThread.quitSafely();
        MediaCodec mediaCodec = this.mAudioEncoder;
        if (mediaCodec != null) {
            mediaCodec.release();
            this.mAudioEncoder = null;
        }
        if (this.mAudioRecorder != null) {
            this.mAudioRecorder.release();
            this.mAudioRecorder = null;
        }
    }

    @UiThread
    private void releaseCameraSurface(final boolean z2) {
        DeferrableSurface deferrableSurface = this.mDeferrableSurface;
        if (deferrableSurface == null) {
            return;
        }
        final MediaCodec mediaCodec = this.mVideoEncoder;
        deferrableSurface.close();
        this.mDeferrableSurface.getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.core.a2
            @Override // java.lang.Runnable
            public final void run() {
                VideoCapture.lambda$releaseCameraSurface$7(z2, mediaCodec);
            }
        }, CameraXExecutors.mainThreadExecutor());
        if (z2) {
            this.mVideoEncoder = null;
        }
        this.mCameraSurface = null;
        this.mDeferrableSurface = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: releaseResources, reason: merged with bridge method [inline-methods] */
    public void lambda$onDetached$6() {
        this.mVideoHandlerThread.quitSafely();
        releaseAudioInputResource();
        if (this.mCameraSurface != null) {
            releaseCameraSurface(true);
        }
    }

    private boolean removeRecordingResultIfNoVideoKeyFrameArrived(@NonNull OutputFileOptions outputFileOptions) {
        boolean z2;
        Logger.i(TAG, "check Recording Result First Video Key Frame Write: " + this.mIsFirstVideoKeyFrameWrite.get());
        if (this.mIsFirstVideoKeyFrameWrite.get()) {
            z2 = true;
        } else {
            Logger.i(TAG, "The recording result has no key frame.");
            z2 = false;
        }
        if (outputFileOptions.isSavingToFile()) {
            File file = outputFileOptions.getFile();
            if (!z2) {
                Logger.i(TAG, "Delete file.");
                file.delete();
            }
        } else if (outputFileOptions.isSavingToMediaStore() && !z2) {
            Logger.i(TAG, "Delete file.");
            if (this.mSavedVideoUri != null) {
                outputFileOptions.getContentResolver().delete(this.mSavedVideoUri, null, null);
            }
        }
        return z2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x002b, code lost:
    
        r7.mAudioChannelCount = r4.audioChannels;
        r7.mAudioSampleRate = r4.audioSampleRate;
        r7.mAudioBitRate = r4.audioBitRate;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0037, code lost:
    
        r0 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setAudioParametersByCamcorderProfile(android.util.Size r8, java.lang.String r9) {
        /*
            r7 = this;
            r0 = 0
            int[] r1 = androidx.camera.core.VideoCapture.CamcorderQuality     // Catch: java.lang.NumberFormatException -> L3d
            int r2 = r1.length     // Catch: java.lang.NumberFormatException -> L3d
            r3 = r0
        L5:
            if (r3 >= r2) goto L44
            r4 = r1[r3]     // Catch: java.lang.NumberFormatException -> L3d
            int r5 = java.lang.Integer.parseInt(r9)     // Catch: java.lang.NumberFormatException -> L3d
            boolean r5 = android.media.CamcorderProfile.hasProfile(r5, r4)     // Catch: java.lang.NumberFormatException -> L3d
            if (r5 == 0) goto L3a
            int r5 = java.lang.Integer.parseInt(r9)     // Catch: java.lang.NumberFormatException -> L3d
            android.media.CamcorderProfile r4 = android.media.CamcorderProfile.get(r5, r4)     // Catch: java.lang.NumberFormatException -> L3d
            int r5 = r8.getWidth()     // Catch: java.lang.NumberFormatException -> L3d
            int r6 = r4.videoFrameWidth     // Catch: java.lang.NumberFormatException -> L3d
            if (r5 != r6) goto L3a
            int r5 = r8.getHeight()     // Catch: java.lang.NumberFormatException -> L3d
            int r6 = r4.videoFrameHeight     // Catch: java.lang.NumberFormatException -> L3d
            if (r5 != r6) goto L3a
            int r8 = r4.audioChannels     // Catch: java.lang.NumberFormatException -> L3d
            r7.mAudioChannelCount = r8     // Catch: java.lang.NumberFormatException -> L3d
            int r8 = r4.audioSampleRate     // Catch: java.lang.NumberFormatException -> L3d
            r7.mAudioSampleRate = r8     // Catch: java.lang.NumberFormatException -> L3d
            int r8 = r4.audioBitRate     // Catch: java.lang.NumberFormatException -> L3d
            r7.mAudioBitRate = r8     // Catch: java.lang.NumberFormatException -> L3d
            r8 = 1
            r0 = r8
            goto L44
        L3a:
            int r3 = r3 + 1
            goto L5
        L3d:
            java.lang.String r8 = "VideoCapture"
            java.lang.String r9 = "The camera Id is not an integer because the camera may be a removable device. Use the default values for the audio related settings."
            androidx.camera.core.Logger.i(r8, r9)
        L44:
            if (r0 != 0) goto L5e
            androidx.camera.core.impl.UseCaseConfig r8 = r7.getCurrentConfig()
            androidx.camera.core.impl.VideoCaptureConfig r8 = (androidx.camera.core.impl.VideoCaptureConfig) r8
            int r9 = r8.getAudioChannelCount()
            r7.mAudioChannelCount = r9
            int r9 = r8.getAudioSampleRate()
            r7.mAudioSampleRate = r9
            int r8 = r8.getAudioBitRate()
            r7.mAudioBitRate = r8
        L5e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.VideoCapture.setAudioParametersByCamcorderProfile(android.util.Size, java.lang.String):void");
    }

    private boolean writeAudioEncodedBuffer(int i2) {
        ByteBuffer outputBuffer = getOutputBuffer(this.mAudioEncoder, i2);
        outputBuffer.position(this.mAudioBufferInfo.offset);
        if (this.mMuxerStarted.get()) {
            try {
                MediaCodec.BufferInfo bufferInfo = this.mAudioBufferInfo;
                if (bufferInfo.size <= 0 || bufferInfo.presentationTimeUs <= 0) {
                    Logger.i(TAG, "mAudioBufferInfo size: " + this.mAudioBufferInfo.size + " presentationTimeUs: " + this.mAudioBufferInfo.presentationTimeUs);
                } else {
                    synchronized (this.mMuxerLock) {
                        if (!this.mIsFirstAudioSampleWrite.get()) {
                            Logger.i(TAG, "First audio sample written.");
                            this.mIsFirstAudioSampleWrite.set(true);
                        }
                        this.mMuxer.writeSampleData(this.mAudioTrackIndex, outputBuffer, this.mAudioBufferInfo);
                    }
                }
            } catch (Exception e2) {
                Logger.e(TAG, "audio error:size=" + this.mAudioBufferInfo.size + "/offset=" + this.mAudioBufferInfo.offset + "/timeUs=" + this.mAudioBufferInfo.presentationTimeUs);
                e2.printStackTrace();
            }
        }
        this.mAudioEncoder.releaseOutputBuffer(i2, false);
        return (this.mAudioBufferInfo.flags & 4) != 0;
    }

    private boolean writeVideoEncodedBuffer(int i2) {
        if (i2 < 0) {
            Logger.e(TAG, "Output buffer should not have negative index: " + i2);
            return false;
        }
        ByteBuffer outputBuffer = this.mVideoEncoder.getOutputBuffer(i2);
        if (outputBuffer == null) {
            Logger.d(TAG, "OutputBuffer was null.");
            return false;
        }
        if (this.mMuxerStarted.get()) {
            MediaCodec.BufferInfo bufferInfo = this.mVideoBufferInfo;
            if (bufferInfo.size > 0) {
                outputBuffer.position(bufferInfo.offset);
                MediaCodec.BufferInfo bufferInfo2 = this.mVideoBufferInfo;
                outputBuffer.limit(bufferInfo2.offset + bufferInfo2.size);
                this.mVideoBufferInfo.presentationTimeUs = System.nanoTime() / 1000;
                synchronized (this.mMuxerLock) {
                    if (!this.mIsFirstVideoKeyFrameWrite.get()) {
                        if ((this.mVideoBufferInfo.flags & 1) != 0) {
                            Logger.i(TAG, "First video key frame written.");
                            this.mIsFirstVideoKeyFrameWrite.set(true);
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putInt("request-sync", 0);
                            this.mVideoEncoder.setParameters(bundle);
                        }
                    }
                    this.mMuxer.writeSampleData(this.mVideoTrackIndex, outputBuffer, this.mVideoBufferInfo);
                }
            } else {
                Logger.i(TAG, "mVideoBufferInfo.size <= 0, index " + i2);
            }
        }
        this.mVideoEncoder.releaseOutputBuffer(i2, false);
        return (this.mVideoBufferInfo.flags & 4) != 0;
    }

    /* renamed from: audioEncode, reason: merged with bridge method [inline-methods] */
    public boolean lambda$startRecording$3(OnVideoSavedCallback onVideoSavedCallback) throws IllegalStateException, MediaCodec.CryptoException {
        boolean zWriteAudioEncodedBuffer = false;
        long j2 = 0;
        while (!zWriteAudioEncodedBuffer && this.mIsRecording) {
            if (this.mEndOfAudioStreamSignal.get()) {
                this.mEndOfAudioStreamSignal.set(false);
                this.mIsRecording = false;
            }
            if (this.mAudioEncoder != null && this.mAudioRecorder != null) {
                try {
                    int iDequeueInputBuffer = this.mAudioEncoder.dequeueInputBuffer(-1L);
                    if (iDequeueInputBuffer >= 0) {
                        ByteBuffer inputBuffer = getInputBuffer(this.mAudioEncoder, iDequeueInputBuffer);
                        inputBuffer.clear();
                        int i2 = this.mAudioRecorder.read(inputBuffer, this.mAudioBufferSize);
                        if (i2 > 0) {
                            this.mAudioEncoder.queueInputBuffer(iDequeueInputBuffer, 0, i2, System.nanoTime() / 1000, this.mIsRecording ? 0 : 4);
                        }
                    }
                } catch (MediaCodec.CodecException e2) {
                    Logger.i(TAG, "audio dequeueInputBuffer CodecException " + e2.getMessage());
                } catch (IllegalStateException e3) {
                    Logger.i(TAG, "audio dequeueInputBuffer IllegalStateException " + e3.getMessage());
                }
                do {
                    int iDequeueOutputBuffer = this.mAudioEncoder.dequeueOutputBuffer(this.mAudioBufferInfo, 0L);
                    if (iDequeueOutputBuffer == -2) {
                        synchronized (this.mMuxerLock) {
                            int iAddTrack = this.mMuxer.addTrack(this.mAudioEncoder.getOutputFormat());
                            this.mAudioTrackIndex = iAddTrack;
                            if (iAddTrack >= 0 && this.mVideoTrackIndex >= 0) {
                                Logger.i(TAG, "MediaMuxer start on audio encoder thread.");
                                this.mMuxer.start();
                                this.mMuxerStarted.set(true);
                            }
                        }
                    } else if (iDequeueOutputBuffer != -1) {
                        if (this.mAudioBufferInfo.presentationTimeUs > j2) {
                            zWriteAudioEncodedBuffer = writeAudioEncodedBuffer(iDequeueOutputBuffer);
                            j2 = this.mAudioBufferInfo.presentationTimeUs;
                        } else {
                            Logger.w(TAG, "Drops frame, current frame's timestamp " + this.mAudioBufferInfo.presentationTimeUs + " is earlier that last frame " + j2);
                            this.mAudioEncoder.releaseOutputBuffer(iDequeueOutputBuffer, false);
                        }
                    }
                    if (iDequeueOutputBuffer >= 0) {
                    }
                } while (!zWriteAudioEncodedBuffer);
            }
        }
        try {
            Logger.i(TAG, "audioRecorder stop");
            this.mAudioRecorder.stop();
        } catch (IllegalStateException e4) {
            onVideoSavedCallback.onError(1, "Audio recorder stop failed!", e4);
        }
        try {
            this.mAudioEncoder.stop();
        } catch (IllegalStateException e5) {
            onVideoSavedCallback.onError(1, "Audio encoder stop failed!", e5);
        }
        Logger.i(TAG, "Audio encode thread end");
        this.mEndOfVideoStreamSignal.set(true);
        return false;
    }

    @Override // androidx.camera.core.UseCase
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig<?> getDefaultConfig(boolean z2, @NonNull UseCaseConfigFactory useCaseConfigFactory) {
        Config config = useCaseConfigFactory.getConfig(UseCaseConfigFactory.CaptureType.VIDEO_CAPTURE, 1);
        if (z2) {
            config = androidx.camera.core.impl.n.b(config, DEFAULT_CONFIG.getConfig());
        }
        if (config == null) {
            return null;
        }
        return getUseCaseConfigBuilder(config).getUseCaseConfig();
    }

    @Override // androidx.camera.core.UseCase
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public UseCaseConfig.Builder<?, ?, ?> getUseCaseConfigBuilder(@NonNull Config config) {
        return Builder.fromConfig(config);
    }

    @Override // androidx.camera.core.UseCase
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onAttached() {
        this.mVideoHandlerThread = new HandlerThread("CameraX-video encoding thread");
        this.mAudioHandlerThread = new HandlerThread("CameraX-audio encoding thread");
        this.mVideoHandlerThread.start();
        this.mVideoHandler = new Handler(this.mVideoHandlerThread.getLooper());
        this.mAudioHandlerThread.start();
        this.mAudioHandler = new Handler(this.mAudioHandlerThread.getLooper());
    }

    @Override // androidx.camera.core.UseCase
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void onDetached() {
        lambda$stopRecording$5();
        ListenableFuture<Void> listenableFuture = this.mRecordingFuture;
        if (listenableFuture != null) {
            listenableFuture.addListener(new Runnable() { // from class: androidx.camera.core.b2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1480c.lambda$onDetached$6();
                }
            }, CameraXExecutors.mainThreadExecutor());
        } else {
            lambda$onDetached$6();
        }
    }

    @Override // androidx.camera.core.UseCase
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    @UiThread
    public void onStateDetached() {
        lambda$stopRecording$5();
    }

    @Override // androidx.camera.core.UseCase
    @NonNull
    @RequiresPermission(Permission.RECORD_AUDIO)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public Size onSuggestedResolutionUpdated(@NonNull Size size) {
        if (this.mCameraSurface != null) {
            this.mVideoEncoder.stop();
            this.mVideoEncoder.release();
            this.mAudioEncoder.stop();
            this.mAudioEncoder.release();
            releaseCameraSurface(false);
        }
        try {
            this.mVideoEncoder = MediaCodec.createEncoderByType("video/avc");
            this.mAudioEncoder = MediaCodec.createEncoderByType("audio/mp4a-latm");
            setupEncoder(getCameraId(), size);
            notifyActive();
            return size;
        } catch (IOException e2) {
            throw new IllegalStateException("Unable to create MediaCodec due to: " + e2.getCause());
        }
    }

    public void setTargetRotation(int i2) {
        setTargetRotationInternal(i2);
    }

    @RequiresPermission(Permission.RECORD_AUDIO)
    @UiThread
    public void setupEncoder(@NonNull final String str, @NonNull final Size size) {
        VideoCaptureConfig videoCaptureConfig = (VideoCaptureConfig) getCurrentConfig();
        this.mVideoEncoder.reset();
        this.mVideoEncoderInitStatus = VideoEncoderInitStatus.VIDEO_ENCODER_INIT_STATUS_UNINITIALIZED;
        try {
            this.mVideoEncoder.configure(createVideoMediaFormat(videoCaptureConfig, size), (Surface) null, (MediaCrypto) null, 1);
            if (this.mCameraSurface != null) {
                releaseCameraSurface(false);
            }
            final Surface surfaceCreateInputSurface = this.mVideoEncoder.createInputSurface();
            this.mCameraSurface = surfaceCreateInputSurface;
            this.mSessionConfigBuilder = SessionConfig.Builder.createFrom(videoCaptureConfig);
            DeferrableSurface deferrableSurface = this.mDeferrableSurface;
            if (deferrableSurface != null) {
                deferrableSurface.close();
            }
            ImmediateSurface immediateSurface = new ImmediateSurface(this.mCameraSurface, size, getImageFormat());
            this.mDeferrableSurface = immediateSurface;
            ListenableFuture<Void> terminationFuture = immediateSurface.getTerminationFuture();
            Objects.requireNonNull(surfaceCreateInputSurface);
            terminationFuture.addListener(new Runnable() { // from class: androidx.camera.core.c2
                @Override // java.lang.Runnable
                public final void run() {
                    surfaceCreateInputSurface.release();
                }
            }, CameraXExecutors.mainThreadExecutor());
            this.mSessionConfigBuilder.addNonRepeatingSurface(this.mDeferrableSurface);
            this.mSessionConfigBuilder.addErrorListener(new SessionConfig.ErrorListener() { // from class: androidx.camera.core.VideoCapture.1
                @Override // androidx.camera.core.impl.SessionConfig.ErrorListener
                @RequiresPermission(Permission.RECORD_AUDIO)
                public void onError(@NonNull SessionConfig sessionConfig, @NonNull SessionConfig.SessionError sessionError) {
                    if (VideoCapture.this.isCurrentCamera(str)) {
                        VideoCapture.this.setupEncoder(str, size);
                        VideoCapture.this.notifyReset();
                    }
                }
            });
            updateSessionConfig(this.mSessionConfigBuilder.build());
            this.mIsAudioEnabled.set(true);
            setAudioParametersByCamcorderProfile(size, str);
            this.mAudioEncoder.reset();
            this.mAudioEncoder.configure(createAudioMediaFormat(), (Surface) null, (MediaCrypto) null, 1);
            if (this.mAudioRecorder != null) {
                this.mAudioRecorder.release();
            }
            this.mAudioRecorder = autoConfigAudioRecordSource(videoCaptureConfig);
            if (this.mAudioRecorder == null) {
                Logger.e(TAG, "AudioRecord object cannot initialized correctly!");
                this.mIsAudioEnabled.set(false);
            }
            synchronized (this.mMuxerLock) {
                this.mVideoTrackIndex = -1;
                this.mAudioTrackIndex = -1;
            }
            this.mIsRecording = false;
        } catch (MediaCodec.CodecException e2) {
            int codecExceptionErrorCode = Api23Impl.getCodecExceptionErrorCode(e2);
            String diagnosticInfo = e2.getDiagnosticInfo();
            if (codecExceptionErrorCode == 1100) {
                Logger.i(TAG, "CodecException: code: " + codecExceptionErrorCode + " diagnostic: " + diagnosticInfo);
                this.mVideoEncoderInitStatus = VideoEncoderInitStatus.VIDEO_ENCODER_INIT_STATUS_INSUFFICIENT_RESOURCE;
            } else if (codecExceptionErrorCode == 1101) {
                Logger.i(TAG, "CodecException: code: " + codecExceptionErrorCode + " diagnostic: " + diagnosticInfo);
                this.mVideoEncoderInitStatus = VideoEncoderInitStatus.VIDEO_ENCODER_INIT_STATUS_RESOURCE_RECLAIMED;
            }
            this.mVideoEncoderErrorMessage = e2;
        } catch (IllegalArgumentException e3) {
            e = e3;
            this.mVideoEncoderInitStatus = VideoEncoderInitStatus.VIDEO_ENCODER_INIT_STATUS_INITIALIZED_FAILED;
            this.mVideoEncoderErrorMessage = e;
        } catch (IllegalStateException e4) {
            e = e4;
            this.mVideoEncoderInitStatus = VideoEncoderInitStatus.VIDEO_ENCODER_INIT_STATUS_INITIALIZED_FAILED;
            this.mVideoEncoderErrorMessage = e;
        }
    }

    @RequiresPermission(Permission.RECORD_AUDIO)
    /* renamed from: startRecording, reason: merged with bridge method [inline-methods] */
    public void lambda$startRecording$0(@NonNull final OutputFileOptions outputFileOptions, @NonNull final Executor executor, @NonNull final OnVideoSavedCallback onVideoSavedCallback) throws IllegalStateException {
        Location location;
        if (Looper.getMainLooper() != Looper.myLooper()) {
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.u1
                @Override // java.lang.Runnable
                public final void run() throws IllegalStateException {
                    this.f1681c.lambda$startRecording$0(outputFileOptions, executor, onVideoSavedCallback);
                }
            });
            return;
        }
        Logger.i(TAG, "startRecording");
        this.mIsFirstVideoKeyFrameWrite.set(false);
        this.mIsFirstAudioSampleWrite.set(false);
        final VideoSavedListenerWrapper videoSavedListenerWrapper = new VideoSavedListenerWrapper(executor, onVideoSavedCallback);
        CameraInternal camera = getCamera();
        if (camera == null) {
            videoSavedListenerWrapper.onError(5, "Not bound to a Camera [" + this + StrPool.BRACKET_END, null);
            return;
        }
        VideoEncoderInitStatus videoEncoderInitStatus = this.mVideoEncoderInitStatus;
        if (videoEncoderInitStatus == VideoEncoderInitStatus.VIDEO_ENCODER_INIT_STATUS_INSUFFICIENT_RESOURCE || videoEncoderInitStatus == VideoEncoderInitStatus.VIDEO_ENCODER_INIT_STATUS_INITIALIZED_FAILED || videoEncoderInitStatus == VideoEncoderInitStatus.VIDEO_ENCODER_INIT_STATUS_RESOURCE_RECLAIMED) {
            videoSavedListenerWrapper.onError(1, "Video encoder initialization failed before start recording ", this.mVideoEncoderErrorMessage);
            return;
        }
        if (!this.mEndOfAudioVideoSignal.get()) {
            videoSavedListenerWrapper.onError(3, "It is still in video recording!", null);
            return;
        }
        if (this.mIsAudioEnabled.get()) {
            try {
                if (this.mAudioRecorder.getState() == 1) {
                    this.mAudioRecorder.startRecording();
                }
            } catch (IllegalStateException e2) {
                Logger.i(TAG, "AudioRecorder cannot start recording, disable audio." + e2.getMessage());
                this.mIsAudioEnabled.set(false);
                releaseAudioInputResource();
            }
            if (this.mAudioRecorder.getRecordingState() != 3) {
                Logger.i(TAG, "AudioRecorder startRecording failed - incorrect state: " + this.mAudioRecorder.getRecordingState());
                this.mIsAudioEnabled.set(false);
                releaseAudioInputResource();
            }
        }
        final AtomicReference atomicReference = new AtomicReference();
        this.mRecordingFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.v1
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return VideoCapture.lambda$startRecording$1(atomicReference, completer);
            }
        });
        final CallbackToFutureAdapter.Completer completer = (CallbackToFutureAdapter.Completer) Preconditions.checkNotNull((CallbackToFutureAdapter.Completer) atomicReference.get());
        this.mRecordingFuture.addListener(new Runnable() { // from class: androidx.camera.core.w1
            @Override // java.lang.Runnable
            public final void run() {
                this.f1706c.lambda$startRecording$2();
            }
        }, CameraXExecutors.mainThreadExecutor());
        try {
            Logger.i(TAG, "videoEncoder start");
            this.mVideoEncoder.start();
            if (this.mIsAudioEnabled.get()) {
                Logger.i(TAG, "audioEncoder start");
                this.mAudioEncoder.start();
            }
            try {
                synchronized (this.mMuxerLock) {
                    MediaMuxer mediaMuxerInitMediaMuxer = initMediaMuxer(outputFileOptions);
                    this.mMuxer = mediaMuxerInitMediaMuxer;
                    Preconditions.checkNotNull(mediaMuxerInitMediaMuxer);
                    this.mMuxer.setOrientationHint(getRelativeRotation(camera));
                    Metadata metadata = outputFileOptions.getMetadata();
                    if (metadata != null && (location = metadata.location) != null) {
                        this.mMuxer.setLocation((float) location.getLatitude(), (float) metadata.location.getLongitude());
                    }
                }
                this.mEndOfVideoStreamSignal.set(false);
                this.mEndOfAudioStreamSignal.set(false);
                this.mEndOfAudioVideoSignal.set(false);
                this.mIsRecording = true;
                this.mSessionConfigBuilder.clearSurfaces();
                this.mSessionConfigBuilder.addSurface(this.mDeferrableSurface);
                updateSessionConfig(this.mSessionConfigBuilder.build());
                notifyUpdated();
                if (this.mIsAudioEnabled.get()) {
                    this.mAudioHandler.post(new Runnable() { // from class: androidx.camera.core.x1
                        @Override // java.lang.Runnable
                        public final void run() throws IllegalStateException, MediaCodec.CryptoException {
                            this.f1710c.lambda$startRecording$3(videoSavedListenerWrapper);
                        }
                    });
                }
                final String cameraId = getCameraId();
                final Size attachedSurfaceResolution = getAttachedSurfaceResolution();
                this.mVideoHandler.post(new Runnable() { // from class: androidx.camera.core.y1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f1714c.lambda$startRecording$4(videoSavedListenerWrapper, cameraId, attachedSurfaceResolution, outputFileOptions, completer);
                    }
                });
            } catch (IOException e3) {
                completer.set(null);
                videoSavedListenerWrapper.onError(2, "MediaMuxer creation failed!", e3);
            }
        } catch (IllegalStateException e4) {
            completer.set(null);
            videoSavedListenerWrapper.onError(1, "Audio/Video encoder start fail", e4);
        }
    }

    /* renamed from: stopRecording, reason: merged with bridge method [inline-methods] */
    public void lambda$stopRecording$5() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.z1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1722c.lambda$stopRecording$5();
                }
            });
            return;
        }
        Logger.i(TAG, "stopRecording");
        this.mSessionConfigBuilder.clearSurfaces();
        this.mSessionConfigBuilder.addNonRepeatingSurface(this.mDeferrableSurface);
        updateSessionConfig(this.mSessionConfigBuilder.build());
        notifyUpdated();
        if (this.mIsRecording) {
            if (this.mIsAudioEnabled.get()) {
                this.mEndOfAudioStreamSignal.set(true);
            } else {
                this.mEndOfVideoStreamSignal.set(true);
            }
        }
    }

    public boolean videoEncode(@NonNull OnVideoSavedCallback onVideoSavedCallback, @NonNull String str, @NonNull Size size, @NonNull OutputFileOptions outputFileOptions) throws IOException {
        boolean zWriteVideoEncodedBuffer = false;
        boolean z2 = false;
        while (!zWriteVideoEncodedBuffer && !z2) {
            if (this.mEndOfVideoStreamSignal.get()) {
                this.mVideoEncoder.signalEndOfInputStream();
                this.mEndOfVideoStreamSignal.set(false);
            }
            int iDequeueOutputBuffer = this.mVideoEncoder.dequeueOutputBuffer(this.mVideoBufferInfo, com.heytap.mcssdk.constant.a.f7153q);
            if (iDequeueOutputBuffer == -2) {
                if (this.mMuxerStarted.get()) {
                    onVideoSavedCallback.onError(1, "Unexpected change in video encoding format.", null);
                    z2 = true;
                }
                synchronized (this.mMuxerLock) {
                    this.mVideoTrackIndex = this.mMuxer.addTrack(this.mVideoEncoder.getOutputFormat());
                    if ((this.mIsAudioEnabled.get() && this.mAudioTrackIndex >= 0 && this.mVideoTrackIndex >= 0) || (!this.mIsAudioEnabled.get() && this.mVideoTrackIndex >= 0)) {
                        Logger.i(TAG, "MediaMuxer started on video encode thread and audio enabled: " + this.mIsAudioEnabled);
                        this.mMuxer.start();
                        this.mMuxerStarted.set(true);
                    }
                }
            } else if (iDequeueOutputBuffer != -1) {
                zWriteVideoEncodedBuffer = writeVideoEncodedBuffer(iDequeueOutputBuffer);
            }
        }
        try {
            Logger.i(TAG, "videoEncoder stop");
            this.mVideoEncoder.stop();
        } catch (IllegalStateException e2) {
            onVideoSavedCallback.onError(1, "Video encoder stop failed!", e2);
            z2 = true;
        }
        try {
            synchronized (this.mMuxerLock) {
                if (this.mMuxer != null) {
                    if (this.mMuxerStarted.get()) {
                        Logger.i(TAG, "Muxer already started");
                        this.mMuxer.stop();
                    }
                    this.mMuxer.release();
                    this.mMuxer = null;
                }
            }
        } catch (IllegalStateException e3) {
            Logger.i(TAG, "muxer stop IllegalStateException: " + System.currentTimeMillis());
            Logger.i(TAG, "muxer stop exception, mIsFirstVideoKeyFrameWrite: " + this.mIsFirstVideoKeyFrameWrite.get());
            if (this.mIsFirstVideoKeyFrameWrite.get()) {
                onVideoSavedCallback.onError(2, "Muxer stop failed!", e3);
            } else {
                onVideoSavedCallback.onError(6, "The file has no video key frame.", null);
            }
        }
        if (!removeRecordingResultIfNoVideoKeyFrameArrived(outputFileOptions)) {
            onVideoSavedCallback.onError(6, "The file has no video key frame.", null);
            z2 = true;
        }
        if (this.mParcelFileDescriptor != null) {
            try {
                this.mParcelFileDescriptor.close();
                this.mParcelFileDescriptor = null;
            } catch (IOException e4) {
                onVideoSavedCallback.onError(2, "File descriptor close failed!", e4);
                z2 = true;
            }
        }
        this.mMuxerStarted.set(false);
        this.mEndOfAudioVideoSignal.set(true);
        this.mIsFirstVideoKeyFrameWrite.set(false);
        Logger.i(TAG, "Video encode thread end.");
        return z2;
    }
}
