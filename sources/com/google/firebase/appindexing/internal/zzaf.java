package com.google.firebase.appindexing.internal;

import androidx.annotation.NonNull;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.appindexing.FirebaseAppIndexingException;
import com.google.firebase.appindexing.FirebaseAppIndexingInvalidArgumentException;
import com.google.firebase.appindexing.FirebaseAppIndexingTooManyArgumentsException;
import com.yikaobang.yixue.R2;

/* loaded from: classes4.dex */
public final class zzaf {
    public static FirebaseAppIndexingException zza(@NonNull Status status, String str) {
        Preconditions.checkNotNull(status);
        String statusMessage = status.getStatusMessage();
        if (statusMessage != null && !statusMessage.isEmpty()) {
            str = statusMessage;
        }
        int statusCode = status.getStatusCode();
        if (statusCode == 17510) {
            return new FirebaseAppIndexingInvalidArgumentException(str);
        }
        if (statusCode == 17511) {
            return new FirebaseAppIndexingTooManyArgumentsException(str);
        }
        if (statusCode == 17602) {
            return new com.google.firebase.appindexing.zzh(str);
        }
        switch (statusCode) {
            case R2.id.plvlc_playback_controller_land_ly_time /* 17513 */:
                return new com.google.firebase.appindexing.zzb(str);
            case R2.id.plvlc_playback_controller_land_rl_root /* 17514 */:
                return new com.google.firebase.appindexing.zza(str);
            case R2.id.plvlc_playback_controller_land_sb_playprogress /* 17515 */:
                return new com.google.firebase.appindexing.zzg(str);
            case R2.id.plvlc_playback_controller_land_tv_currenttime /* 17516 */:
                return new com.google.firebase.appindexing.zze(str);
            case R2.id.plvlc_playback_controller_land_tv_start_send_message /* 17517 */:
                return new com.google.firebase.appindexing.zzf(str);
            case R2.id.plvlc_playback_controller_land_tv_totaltime /* 17518 */:
                return new com.google.firebase.appindexing.zzd(str);
            case R2.id.plvlc_playback_controller_land_tv_video_name /* 17519 */:
                return new com.google.firebase.appindexing.zzc(str);
            default:
                return new FirebaseAppIndexingException(str);
        }
    }
}
