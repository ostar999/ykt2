package com.easefun.polyv.mediasdk.example.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.easefun.polyv.mediasdk.player.R;

/* loaded from: classes3.dex */
public class Settings {
    public static final String POLYV_PREF_ENABLE_BACKGROUND_PLAY = "polyv.pref.enable_background_play";
    public static final String POLYV_PREF_KEY_MEDIA_CODEC_HANDLE_RESOLUTION_CHANGE = "polyv.pref.media_codec_handle_resolution_change";
    public static final String POLYV_PREF_PIXEL_FORMAT = "polyv.pref.pixel_format";
    public static final String POLYV_PREF_PLAYER = "polyv.pref.player";
    public static final int POLYV_PREF_PLAYER_DEFAULT = 2;
    public static final String POLYV_PREF_RENDER_VIEW_TYPE = "polyv.pref.render_view_type";
    public static final int POLYV_PREF_RENDER_VIEW_TYPE_DEFAULT = 2;
    public static final String POLYV_PREF_SKIP_LOOP_FILTER = "polyv.pref.skip_loop_filter";
    public static final int POLYV_PREF_SKIP_LOOP_FILTER_DEFAULT = 8;
    public static final String POLYV_PREF_USING_MEDIA_CODEC_AUTO_ROTATE_TYPE = "polyv.pref.using_media_codec_auto_rotate";
    public static final String POLYV_PREF_USING_MEDIA_CODEC_TYPE = "polyv.pref.using_media_codec";
    public static final boolean POLYV_PREF_USING_MEDIA_CODEC_TYPE_DEFAULT = false;
    public static final String POLYV_PREF_USING_OPENSL_ES = "polyv.pref.using_opensl_es";
    public static final int PV_PLAYER__AndroidMediaPlayer = 1;
    public static final int PV_PLAYER__Auto = 0;
    public static final int PV_PLAYER__IjkExoMediaPlayer = 3;
    public static final int PV_PLAYER__IjkMediaPlayer = 2;
    private Context mAppContext;
    private SharedPreferences mSharedPreferences;

    public Settings(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mAppContext = applicationContext;
        this.mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    public boolean getEnableBackgroundPlay() {
        return this.mSharedPreferences.getBoolean(POLYV_PREF_ENABLE_BACKGROUND_PLAY, false);
    }

    public boolean getEnableDetachedSurfaceTextureView() {
        return this.mSharedPreferences.getBoolean("polyv.pref.enable_detached_surface_texture", false);
    }

    public boolean getEnableNoView() {
        return this.mSharedPreferences.getBoolean("polyv.pref.enable_no_view", false);
    }

    public boolean getEnableSurfaceView() {
        return this.mSharedPreferences.getBoolean("polyv.pref.enable_surface_view", false);
    }

    public boolean getEnableTextureView() {
        return this.mSharedPreferences.getBoolean("polyv.pref.enable_texture_view", false);
    }

    public String getLastDirectory() {
        return this.mSharedPreferences.getString("polyv.pref.last_directory", "/");
    }

    public boolean getMediaCodecHandleResolutionChange() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_media_codec_handle_resolution_change), false);
    }

    public String getPixelFormat() {
        return this.mSharedPreferences.getString(POLYV_PREF_PIXEL_FORMAT, "");
    }

    public int getPlayer() {
        return this.mSharedPreferences.getInt(POLYV_PREF_PLAYER, 2);
    }

    public int getRenderViewType() {
        return this.mSharedPreferences.getInt(POLYV_PREF_RENDER_VIEW_TYPE, 2);
    }

    public int getSkipLoopFilter() {
        return this.mSharedPreferences.getInt(POLYV_PREF_SKIP_LOOP_FILTER, 8);
    }

    public boolean getUsingMediaCodec() {
        return this.mSharedPreferences.getBoolean(POLYV_PREF_USING_MEDIA_CODEC_TYPE, false);
    }

    public boolean getUsingMediaCodecAutoRotate() {
        return this.mSharedPreferences.getBoolean(POLYV_PREF_USING_MEDIA_CODEC_AUTO_ROTATE_TYPE, false);
    }

    public boolean getUsingMediaDataSource() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_using_mediadatasource), false);
    }

    public boolean getUsingOpenSLES() {
        return this.mSharedPreferences.getBoolean(this.mAppContext.getString(R.string.pref_key_using_opensl_es), false);
    }

    public void mediaCodecHandleResolutionChange(boolean z2) {
        this.mSharedPreferences.edit().putBoolean(this.mAppContext.getString(R.string.pref_key_media_codec_handle_resolution_change), z2).apply();
    }

    public void setEnableBackgroundPlay(boolean z2) {
        this.mSharedPreferences.edit().putBoolean(this.mAppContext.getString(R.string.pref_key_enable_background_play), z2).apply();
    }

    public void setLastDirectory(String str) {
        this.mSharedPreferences.edit().putString("polyv.pref.last_directory", str).apply();
    }

    public void setPixelFormat(String str) {
        this.mSharedPreferences.edit().putString(this.mAppContext.getString(R.string.pref_key_pixel_format), str).apply();
    }

    public void usingMediaCodecAutoRotate(boolean z2) {
        this.mSharedPreferences.edit().putBoolean(this.mAppContext.getString(R.string.pref_key_using_media_codec_auto_rotate), z2).apply();
    }

    public void usingOpenSLES(boolean z2) {
        this.mSharedPreferences.edit().putBoolean(this.mAppContext.getString(R.string.pref_key_using_opensl_es), z2).apply();
    }
}
