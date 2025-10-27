package com.google.android.exoplayer2.util;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import cn.hutool.core.text.StrPool;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.TracksInfo;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.analytics.n1;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.decoder.DecoderCounters;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.LoadEventInfo;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.video.VideoSize;
import com.tencent.connect.share.QzonePublish;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import okhttp3.HttpUrl;
import org.eclipse.jetty.util.security.Constraint;

/* loaded from: classes3.dex */
public class EventLogger implements AnalyticsListener {
    private static final String DEFAULT_TAG = "EventLogger";
    private static final int MAX_TIMELINE_ITEM_LINES = 3;
    private static final NumberFormat TIME_FORMAT;
    private final Timeline.Period period;
    private final long startTimeMs;
    private final String tag;

    @Nullable
    private final MappingTrackSelector trackSelector;
    private final Timeline.Window window;

    static {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        TIME_FORMAT = numberFormat;
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setGroupingUsed(false);
    }

    public EventLogger(@Nullable MappingTrackSelector mappingTrackSelector) {
        this(mappingTrackSelector, DEFAULT_TAG);
    }

    private static String getAdaptiveSupportString(int i2, int i3) {
        if (i2 < 2) {
            return "N/A";
        }
        if (i3 == 0) {
            return "NO";
        }
        if (i3 == 8) {
            return "YES_NOT_SEAMLESS";
        }
        if (i3 == 16) {
            return "YES";
        }
        throw new IllegalStateException();
    }

    private static String getDiscontinuityReasonString(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? "?" : "INTERNAL" : "REMOVE" : "SKIP" : "SEEK_ADJUSTMENT" : "SEEK" : "AUTO_TRANSITION";
    }

    private String getEventString(AnalyticsListener.EventTime eventTime, String str, @Nullable String str2, @Nullable Throwable th) {
        String eventTimeString = getEventTimeString(eventTime);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(eventTimeString).length());
        sb.append(str);
        sb.append(" [");
        sb.append(eventTimeString);
        String string = sb.toString();
        if (th instanceof PlaybackException) {
            String strValueOf = String.valueOf(string);
            String errorCodeName = ((PlaybackException) th).getErrorCodeName();
            StringBuilder sb2 = new StringBuilder(strValueOf.length() + 12 + String.valueOf(errorCodeName).length());
            sb2.append(strValueOf);
            sb2.append(", errorCode=");
            sb2.append(errorCodeName);
            string = sb2.toString();
        }
        if (str2 != null) {
            String strValueOf2 = String.valueOf(string);
            StringBuilder sb3 = new StringBuilder(strValueOf2.length() + 2 + str2.length());
            sb3.append(strValueOf2);
            sb3.append(", ");
            sb3.append(str2);
            string = sb3.toString();
        }
        String throwableString = Log.getThrowableString(th);
        if (!TextUtils.isEmpty(throwableString)) {
            String strValueOf3 = String.valueOf(string);
            String strReplace = throwableString.replace("\n", "\n  ");
            StringBuilder sb4 = new StringBuilder(strValueOf3.length() + 4 + String.valueOf(strReplace).length());
            sb4.append(strValueOf3);
            sb4.append("\n  ");
            sb4.append(strReplace);
            sb4.append('\n');
            string = sb4.toString();
        }
        return String.valueOf(string).concat(StrPool.BRACKET_END);
    }

    private String getEventTimeString(AnalyticsListener.EventTime eventTime) {
        int i2 = eventTime.windowIndex;
        StringBuilder sb = new StringBuilder(18);
        sb.append("window=");
        sb.append(i2);
        String string = sb.toString();
        if (eventTime.mediaPeriodId != null) {
            String strValueOf = String.valueOf(string);
            int indexOfPeriod = eventTime.timeline.getIndexOfPeriod(eventTime.mediaPeriodId.periodUid);
            StringBuilder sb2 = new StringBuilder(strValueOf.length() + 20);
            sb2.append(strValueOf);
            sb2.append(", period=");
            sb2.append(indexOfPeriod);
            string = sb2.toString();
            if (eventTime.mediaPeriodId.isAd()) {
                String strValueOf2 = String.valueOf(string);
                int i3 = eventTime.mediaPeriodId.adGroupIndex;
                StringBuilder sb3 = new StringBuilder(strValueOf2.length() + 21);
                sb3.append(strValueOf2);
                sb3.append(", adGroup=");
                sb3.append(i3);
                String strValueOf3 = String.valueOf(sb3.toString());
                int i4 = eventTime.mediaPeriodId.adIndexInAdGroup;
                StringBuilder sb4 = new StringBuilder(strValueOf3.length() + 16);
                sb4.append(strValueOf3);
                sb4.append(", ad=");
                sb4.append(i4);
                string = sb4.toString();
            }
        }
        String timeString = getTimeString(eventTime.realtimeMs - this.startTimeMs);
        String timeString2 = getTimeString(eventTime.eventPlaybackPositionMs);
        StringBuilder sb5 = new StringBuilder(String.valueOf(timeString).length() + 23 + String.valueOf(timeString2).length() + String.valueOf(string).length());
        sb5.append("eventTime=");
        sb5.append(timeString);
        sb5.append(", mediaPos=");
        sb5.append(timeString2);
        sb5.append(", ");
        sb5.append(string);
        return sb5.toString();
    }

    private static String getMediaItemTransitionReasonString(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? "?" : "PLAYLIST_CHANGED" : "SEEK" : "AUTO" : "REPEAT";
    }

    private static String getPlayWhenReadyChangeReasonString(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? "?" : "END_OF_MEDIA_ITEM" : "REMOTE" : "AUDIO_BECOMING_NOISY" : "AUDIO_FOCUS_LOSS" : "USER_REQUEST";
    }

    private static String getPlaybackSuppressionReasonString(int i2) {
        return i2 != 0 ? i2 != 1 ? "?" : "TRANSIENT_AUDIO_FOCUS_LOSS" : Constraint.NONE;
    }

    private static String getRepeatModeString(int i2) {
        return i2 != 0 ? i2 != 1 ? i2 != 2 ? "?" : "ALL" : "ONE" : "OFF";
    }

    private static String getStateString(int i2) {
        return i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? "?" : "ENDED" : "READY" : "BUFFERING" : "IDLE";
    }

    private static String getTimeString(long j2) {
        return j2 == C.TIME_UNSET ? "?" : TIME_FORMAT.format(j2 / 1000.0f);
    }

    private static String getTimelineChangeReasonString(int i2) {
        return i2 != 0 ? i2 != 1 ? "?" : "SOURCE_UPDATE" : "PLAYLIST_CHANGED";
    }

    private static String getTrackStatusString(@Nullable TrackSelection trackSelection, TrackGroup trackGroup, int i2) {
        return getTrackStatusString((trackSelection == null || trackSelection.getTrackGroup() != trackGroup || trackSelection.indexOf(i2) == -1) ? false : true);
    }

    private static String getTrackStatusString(boolean z2) {
        return z2 ? "[X]" : "[ ]";
    }

    private void printInternalError(AnalyticsListener.EventTime eventTime, String str, Exception exc) {
        loge(eventTime, "internalError", str, exc);
    }

    private void printMetadata(Metadata metadata, String str) {
        for (int i2 = 0; i2 < metadata.length(); i2++) {
            String strValueOf = String.valueOf(metadata.get(i2));
            StringBuilder sb = new StringBuilder(String.valueOf(str).length() + strValueOf.length());
            sb.append(str);
            sb.append(strValueOf);
            logd(sb.toString());
        }
    }

    public void logd(String str) {
        Log.d(this.tag, str);
    }

    public void loge(String str) {
        Log.e(this.tag, str);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioAttributesChanged(AnalyticsListener.EventTime eventTime, AudioAttributes audioAttributes) {
        int i2 = audioAttributes.contentType;
        int i3 = audioAttributes.flags;
        int i4 = audioAttributes.usage;
        int i5 = audioAttributes.allowedCapturePolicy;
        StringBuilder sb = new StringBuilder(47);
        sb.append(i2);
        sb.append(",");
        sb.append(i3);
        sb.append(",");
        sb.append(i4);
        sb.append(",");
        sb.append(i5);
        logd(eventTime, "audioAttributes", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onAudioCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
        n1.b(this, eventTime, exc);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2) {
        logd(eventTime, "audioDecoderInitialized", str);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onAudioDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2, long j3) {
        n1.d(this, eventTime, str, j2, j3);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        logd(eventTime, "audioDecoderReleased", str);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        logd(eventTime, "audioDisabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        logd(eventTime, "audioEnabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format) {
        n1.h(this, eventTime, format);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        logd(eventTime, "audioInputFormat", Format.toLogString(format));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onAudioPositionAdvancing(AnalyticsListener.EventTime eventTime, long j2) {
        n1.j(this, eventTime, j2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioSessionIdChanged(AnalyticsListener.EventTime eventTime, int i2) {
        logd(eventTime, "audioSessionId", Integer.toString(i2));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onAudioSinkError(AnalyticsListener.EventTime eventTime, Exception exc) {
        n1.l(this, eventTime, exc);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onAudioUnderrun(AnalyticsListener.EventTime eventTime, int i2, long j2, long j3) {
        StringBuilder sb = new StringBuilder(55);
        sb.append(i2);
        sb.append(", ");
        sb.append(j2);
        sb.append(", ");
        sb.append(j3);
        loge(eventTime, "audioTrackUnderrun", sb.toString(), null);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onAvailableCommandsChanged(AnalyticsListener.EventTime eventTime, Player.Commands commands) {
        n1.n(this, eventTime, commands);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onBandwidthEstimate(AnalyticsListener.EventTime eventTime, int i2, long j2, long j3) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onDecoderDisabled(AnalyticsListener.EventTime eventTime, int i2, DecoderCounters decoderCounters) {
        n1.p(this, eventTime, i2, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onDecoderEnabled(AnalyticsListener.EventTime eventTime, int i2, DecoderCounters decoderCounters) {
        n1.q(this, eventTime, i2, decoderCounters);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onDecoderInitialized(AnalyticsListener.EventTime eventTime, int i2, String str, long j2) {
        n1.r(this, eventTime, i2, str, j2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onDecoderInputFormatChanged(AnalyticsListener.EventTime eventTime, int i2, Format format) {
        n1.s(this, eventTime, i2, format);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDownstreamFormatChanged(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        logd(eventTime, "downstreamFormat", Format.toLogString(mediaLoadData.trackFormat));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmKeysLoaded(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmKeysLoaded");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmKeysRemoved(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmKeysRemoved");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmKeysRestored(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmKeysRestored");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime) {
        n1.x(this, eventTime);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionAcquired(AnalyticsListener.EventTime eventTime, int i2) {
        StringBuilder sb = new StringBuilder(17);
        sb.append("state=");
        sb.append(i2);
        logd(eventTime, "drmSessionAcquired", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionManagerError(AnalyticsListener.EventTime eventTime, Exception exc) {
        printInternalError(eventTime, "drmSessionManagerError", exc);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDrmSessionReleased(AnalyticsListener.EventTime eventTime) {
        logd(eventTime, "drmSessionReleased");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onDroppedVideoFrames(AnalyticsListener.EventTime eventTime, int i2, long j2) {
        logd(eventTime, "droppedFrames", Integer.toString(i2));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onEvents(Player player, AnalyticsListener.Events events) {
        n1.C(this, player, events);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onIsLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        logd(eventTime, "loading", Boolean.toString(z2));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onIsPlayingChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        logd(eventTime, "isPlaying", Boolean.toString(z2));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadCanceled(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadCompleted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadError(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData, IOException iOException, boolean z2) {
        printInternalError(eventTime, "loadError", iOException);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onLoadStarted(AnalyticsListener.EventTime eventTime, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onLoadingChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        n1.J(this, eventTime, z2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onMaxSeekToPreviousPositionChanged(AnalyticsListener.EventTime eventTime, long j2) {
        n1.K(this, eventTime, j2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onMediaItemTransition(AnalyticsListener.EventTime eventTime, @Nullable MediaItem mediaItem, int i2) {
        String eventTimeString = getEventTimeString(eventTime);
        String mediaItemTransitionReasonString = getMediaItemTransitionReasonString(i2);
        StringBuilder sb = new StringBuilder(String.valueOf(eventTimeString).length() + 21 + String.valueOf(mediaItemTransitionReasonString).length());
        sb.append("mediaItem [");
        sb.append(eventTimeString);
        sb.append(", reason=");
        sb.append(mediaItemTransitionReasonString);
        sb.append(StrPool.BRACKET_END);
        logd(sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onMediaMetadataChanged(AnalyticsListener.EventTime eventTime, MediaMetadata mediaMetadata) {
        n1.M(this, eventTime, mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onMetadata(AnalyticsListener.EventTime eventTime, Metadata metadata) {
        String strValueOf = String.valueOf(getEventTimeString(eventTime));
        logd(strValueOf.length() != 0 ? "metadata [".concat(strValueOf) : new String("metadata ["));
        printMetadata(metadata, "  ");
        logd(StrPool.BRACKET_END);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlayWhenReadyChanged(AnalyticsListener.EventTime eventTime, boolean z2, int i2) {
        String playWhenReadyChangeReasonString = getPlayWhenReadyChangeReasonString(i2);
        StringBuilder sb = new StringBuilder(String.valueOf(playWhenReadyChangeReasonString).length() + 7);
        sb.append(z2);
        sb.append(", ");
        sb.append(playWhenReadyChangeReasonString);
        logd(eventTime, "playWhenReady", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlaybackParametersChanged(AnalyticsListener.EventTime eventTime, PlaybackParameters playbackParameters) {
        logd(eventTime, "playbackParameters", playbackParameters.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlaybackStateChanged(AnalyticsListener.EventTime eventTime, int i2) {
        logd(eventTime, "state", getStateString(i2));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlaybackSuppressionReasonChanged(AnalyticsListener.EventTime eventTime, int i2) {
        logd(eventTime, "playbackSuppressionReason", getPlaybackSuppressionReasonString(i2));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPlayerError(AnalyticsListener.EventTime eventTime, PlaybackException playbackException) {
        loge(eventTime, "playerFailed", playbackException);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onPlayerReleased(AnalyticsListener.EventTime eventTime) {
        n1.T(this, eventTime);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onPlayerStateChanged(AnalyticsListener.EventTime eventTime, boolean z2, int i2) {
        n1.U(this, eventTime, z2, i2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onPlaylistMetadataChanged(AnalyticsListener.EventTime eventTime, MediaMetadata mediaMetadata) {
        n1.V(this, eventTime, mediaMetadata);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, int i2) {
        n1.W(this, eventTime, i2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onPositionDiscontinuity(AnalyticsListener.EventTime eventTime, Player.PositionInfo positionInfo, Player.PositionInfo positionInfo2, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("reason=");
        sb.append(getDiscontinuityReasonString(i2));
        sb.append(", PositionInfo:old [");
        sb.append("mediaItem=");
        sb.append(positionInfo.mediaItemIndex);
        sb.append(", period=");
        sb.append(positionInfo.periodIndex);
        sb.append(", pos=");
        sb.append(positionInfo.positionMs);
        if (positionInfo.adGroupIndex != -1) {
            sb.append(", contentPos=");
            sb.append(positionInfo.contentPositionMs);
            sb.append(", adGroup=");
            sb.append(positionInfo.adGroupIndex);
            sb.append(", ad=");
            sb.append(positionInfo.adIndexInAdGroup);
        }
        sb.append("], PositionInfo:new [");
        sb.append("mediaItem=");
        sb.append(positionInfo2.mediaItemIndex);
        sb.append(", period=");
        sb.append(positionInfo2.periodIndex);
        sb.append(", pos=");
        sb.append(positionInfo2.positionMs);
        if (positionInfo2.adGroupIndex != -1) {
            sb.append(", contentPos=");
            sb.append(positionInfo2.contentPositionMs);
            sb.append(", adGroup=");
            sb.append(positionInfo2.adGroupIndex);
            sb.append(", ad=");
            sb.append(positionInfo2.adIndexInAdGroup);
        }
        sb.append(StrPool.BRACKET_END);
        logd(eventTime, "positionDiscontinuity", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime, Object obj, long j2) {
        logd(eventTime, "renderedFirstFrame", String.valueOf(obj));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onRepeatModeChanged(AnalyticsListener.EventTime eventTime, int i2) {
        logd(eventTime, "repeatMode", getRepeatModeString(i2));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onSeekBackIncrementChanged(AnalyticsListener.EventTime eventTime, long j2) {
        n1.a0(this, eventTime, j2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onSeekForwardIncrementChanged(AnalyticsListener.EventTime eventTime, long j2) {
        n1.b0(this, eventTime, j2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onSeekProcessed(AnalyticsListener.EventTime eventTime) {
        n1.c0(this, eventTime);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
        n1.d0(this, eventTime);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onShuffleModeChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        logd(eventTime, "shuffleModeEnabled", Boolean.toString(z2));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onSkipSilenceEnabledChanged(AnalyticsListener.EventTime eventTime, boolean z2) {
        logd(eventTime, "skipSilenceEnabled", Boolean.toString(z2));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onSurfaceSizeChanged(AnalyticsListener.EventTime eventTime, int i2, int i3) {
        StringBuilder sb = new StringBuilder(24);
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        logd(eventTime, "surfaceSize", sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onTimelineChanged(AnalyticsListener.EventTime eventTime, int i2) {
        int periodCount = eventTime.timeline.getPeriodCount();
        int windowCount = eventTime.timeline.getWindowCount();
        String eventTimeString = getEventTimeString(eventTime);
        String timelineChangeReasonString = getTimelineChangeReasonString(i2);
        StringBuilder sb = new StringBuilder(String.valueOf(eventTimeString).length() + 69 + String.valueOf(timelineChangeReasonString).length());
        sb.append("timeline [");
        sb.append(eventTimeString);
        sb.append(", periodCount=");
        sb.append(periodCount);
        sb.append(", windowCount=");
        sb.append(windowCount);
        sb.append(", reason=");
        sb.append(timelineChangeReasonString);
        logd(sb.toString());
        for (int i3 = 0; i3 < Math.min(periodCount, 3); i3++) {
            eventTime.timeline.getPeriod(i3, this.period);
            String timeString = getTimeString(this.period.getDurationMs());
            StringBuilder sb2 = new StringBuilder(String.valueOf(timeString).length() + 11);
            sb2.append("  period [");
            sb2.append(timeString);
            sb2.append(StrPool.BRACKET_END);
            logd(sb2.toString());
        }
        if (periodCount > 3) {
            logd("  ...");
        }
        for (int i4 = 0; i4 < Math.min(windowCount, 3); i4++) {
            eventTime.timeline.getWindow(i4, this.window);
            String timeString2 = getTimeString(this.window.getDurationMs());
            Timeline.Window window = this.window;
            boolean z2 = window.isSeekable;
            boolean z3 = window.isDynamic;
            StringBuilder sb3 = new StringBuilder(String.valueOf(timeString2).length() + 42);
            sb3.append("  window [");
            sb3.append(timeString2);
            sb3.append(", seekable=");
            sb3.append(z2);
            sb3.append(", dynamic=");
            sb3.append(z3);
            sb3.append(StrPool.BRACKET_END);
            logd(sb3.toString());
        }
        if (windowCount > 3) {
            logd("  ...");
        }
        logd(StrPool.BRACKET_END);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onTracksChanged(AnalyticsListener.EventTime eventTime, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        MappingTrackSelector mappingTrackSelector = this.trackSelector;
        MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = mappingTrackSelector != null ? mappingTrackSelector.getCurrentMappedTrackInfo() : null;
        if (currentMappedTrackInfo == null) {
            logd(eventTime, "tracks", HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
            return;
        }
        String strValueOf = String.valueOf(getEventTimeString(eventTime));
        logd(strValueOf.length() != 0 ? "tracks [".concat(strValueOf) : new String("tracks ["));
        int rendererCount = currentMappedTrackInfo.getRendererCount();
        int i2 = 0;
        while (true) {
            String str = "    Group:";
            String str2 = " [";
            if (i2 >= rendererCount) {
                break;
            }
            TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(i2);
            TrackSelection trackSelection = trackSelectionArray.get(i2);
            int i3 = rendererCount;
            if (trackGroups.length == 0) {
                String rendererName = currentMappedTrackInfo.getRendererName(i2);
                StringBuilder sb = new StringBuilder(String.valueOf(rendererName).length() + 5);
                sb.append("  ");
                sb.append(rendererName);
                sb.append(" []");
                logd(sb.toString());
            } else {
                String rendererName2 = currentMappedTrackInfo.getRendererName(i2);
                StringBuilder sb2 = new StringBuilder(String.valueOf(rendererName2).length() + 4);
                sb2.append("  ");
                sb2.append(rendererName2);
                sb2.append(" [");
                logd(sb2.toString());
                int i4 = 0;
                while (i4 < trackGroups.length) {
                    TrackGroup trackGroup = trackGroups.get(i4);
                    TrackGroupArray trackGroupArray2 = trackGroups;
                    String adaptiveSupportString = getAdaptiveSupportString(trackGroup.length, currentMappedTrackInfo.getAdaptiveSupport(i2, i4, false));
                    StringBuilder sb3 = new StringBuilder(String.valueOf(adaptiveSupportString).length() + 44);
                    sb3.append(str);
                    sb3.append(i4);
                    sb3.append(", adaptive_supported=");
                    sb3.append(adaptiveSupportString);
                    sb3.append(str2);
                    logd(sb3.toString());
                    int i5 = 0;
                    while (i5 < trackGroup.length) {
                        String trackStatusString = getTrackStatusString(trackSelection, trackGroup, i5);
                        String formatSupportString = Util.getFormatSupportString(currentMappedTrackInfo.getTrackSupport(i2, i4, i5));
                        TrackGroup trackGroup2 = trackGroup;
                        String logString = Format.toLogString(trackGroup.getFormat(i5));
                        String str3 = str;
                        StringBuilder sb4 = new StringBuilder(String.valueOf(trackStatusString).length() + 38 + String.valueOf(logString).length() + String.valueOf(formatSupportString).length());
                        sb4.append("      ");
                        sb4.append(trackStatusString);
                        sb4.append(" Track:");
                        sb4.append(i5);
                        sb4.append(", ");
                        sb4.append(logString);
                        sb4.append(", supported=");
                        sb4.append(formatSupportString);
                        logd(sb4.toString());
                        i5++;
                        str = str3;
                        trackGroup = trackGroup2;
                        str2 = str2;
                    }
                    logd("    ]");
                    i4++;
                    trackGroups = trackGroupArray2;
                }
                if (trackSelection != null) {
                    int i6 = 0;
                    while (true) {
                        if (i6 >= trackSelection.length()) {
                            break;
                        }
                        Metadata metadata = trackSelection.getFormat(i6).metadata;
                        if (metadata != null) {
                            logd("    Metadata [");
                            printMetadata(metadata, "      ");
                            logd("    ]");
                            break;
                        }
                        i6++;
                    }
                }
                logd("  ]");
            }
            i2++;
            rendererCount = i3;
        }
        String str4 = "    Group:";
        String str5 = " [";
        TrackGroupArray unmappedTrackGroups = currentMappedTrackInfo.getUnmappedTrackGroups();
        if (unmappedTrackGroups.length > 0) {
            logd("  Unmapped [");
            int i7 = 0;
            while (i7 < unmappedTrackGroups.length) {
                StringBuilder sb5 = new StringBuilder(23);
                String str6 = str4;
                sb5.append(str6);
                sb5.append(i7);
                String str7 = str5;
                sb5.append(str7);
                logd(sb5.toString());
                TrackGroup trackGroup3 = unmappedTrackGroups.get(i7);
                int i8 = 0;
                while (i8 < trackGroup3.length) {
                    String trackStatusString2 = getTrackStatusString(false);
                    String formatSupportString2 = Util.getFormatSupportString(0);
                    String logString2 = Format.toLogString(trackGroup3.getFormat(i8));
                    TrackGroup trackGroup4 = trackGroup3;
                    StringBuilder sb6 = new StringBuilder(String.valueOf(trackStatusString2).length() + 38 + String.valueOf(logString2).length() + String.valueOf(formatSupportString2).length());
                    sb6.append("      ");
                    sb6.append(trackStatusString2);
                    sb6.append(" Track:");
                    sb6.append(i8);
                    sb6.append(", ");
                    sb6.append(logString2);
                    sb6.append(", supported=");
                    sb6.append(formatSupportString2);
                    logd(sb6.toString());
                    i8++;
                    unmappedTrackGroups = unmappedTrackGroups;
                    trackGroup3 = trackGroup4;
                }
                logd("    ]");
                i7++;
                str4 = str6;
                str5 = str7;
            }
            logd("  ]");
        }
        logd(StrPool.BRACKET_END);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onTracksInfoChanged(AnalyticsListener.EventTime eventTime, TracksInfo tracksInfo) {
        n1.j0(this, eventTime, tracksInfo);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onUpstreamDiscarded(AnalyticsListener.EventTime eventTime, MediaLoadData mediaLoadData) {
        logd(eventTime, "upstreamDiscarded", Format.toLogString(mediaLoadData.trackFormat));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onVideoCodecError(AnalyticsListener.EventTime eventTime, Exception exc) {
        n1.l0(this, eventTime, exc);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2) {
        logd(eventTime, "videoDecoderInitialized", str);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onVideoDecoderInitialized(AnalyticsListener.EventTime eventTime, String str, long j2, long j3) {
        n1.n0(this, eventTime, str, j2, j3);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoDecoderReleased(AnalyticsListener.EventTime eventTime, String str) {
        logd(eventTime, "videoDecoderReleased", str);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoDisabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        logd(eventTime, "videoDisabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoEnabled(AnalyticsListener.EventTime eventTime, DecoderCounters decoderCounters) {
        logd(eventTime, "videoEnabled");
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onVideoFrameProcessingOffset(AnalyticsListener.EventTime eventTime, long j2, int i2) {
        n1.r0(this, eventTime, j2, i2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format) {
        n1.s0(this, eventTime, format);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoInputFormatChanged(AnalyticsListener.EventTime eventTime, Format format, @Nullable DecoderReuseEvaluation decoderReuseEvaluation) {
        logd(eventTime, "videoInputFormat", Format.toLogString(format));
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public /* synthetic */ void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, int i2, int i3, int i4, float f2) {
        n1.u0(this, eventTime, i2, i3, i4, f2);
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVideoSizeChanged(AnalyticsListener.EventTime eventTime, VideoSize videoSize) {
        int i2 = videoSize.width;
        int i3 = videoSize.height;
        StringBuilder sb = new StringBuilder(24);
        sb.append(i2);
        sb.append(", ");
        sb.append(i3);
        logd(eventTime, QzonePublish.PUBLISH_TO_QZONE_VIDEO_SIZE, sb.toString());
    }

    @Override // com.google.android.exoplayer2.analytics.AnalyticsListener
    public void onVolumeChanged(AnalyticsListener.EventTime eventTime, float f2) {
        logd(eventTime, "volume", Float.toString(f2));
    }

    public EventLogger(@Nullable MappingTrackSelector mappingTrackSelector, String str) {
        this.trackSelector = mappingTrackSelector;
        this.tag = str;
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        this.startTimeMs = android.os.SystemClock.elapsedRealtime();
    }

    private void logd(AnalyticsListener.EventTime eventTime, String str) {
        logd(getEventString(eventTime, str, null, null));
    }

    private void loge(AnalyticsListener.EventTime eventTime, String str, @Nullable Throwable th) {
        loge(getEventString(eventTime, str, null, th));
    }

    private void logd(AnalyticsListener.EventTime eventTime, String str, String str2) {
        logd(getEventString(eventTime, str, str2, null));
    }

    private void loge(AnalyticsListener.EventTime eventTime, String str, String str2, @Nullable Throwable th) {
        loge(getEventString(eventTime, str, str2, th));
    }
}
