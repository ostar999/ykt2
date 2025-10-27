package com.aliyun.player.alivcplayerexpand.view.more;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.Nullable;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.view.quality.QualityItem;
import com.aliyun.player.nativeclass.TrackInfo;
import com.google.android.material.timepicker.TimeModel;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class TrackInfoView extends LinearLayout implements RadioGroup.OnCheckedChangeListener {
    private Context mContext;
    private OnAudioChangedListener mOnAudioChangedListener;
    private OnBitrateChangedListener mOnBitrateChangedListener;
    private OnDefinitionChangedListrener mOnDefinitionChangedListener;
    private OnSubtitleChangedListener mOnSubtitleChangedListener;
    private RadioGroup mTrackInfoRadioGroup;

    public interface OnAudioChangedListener {
        void onAudioChanged(TrackInfo trackInfo);
    }

    public interface OnBitrateChangedListener {
        void onBitrateChanged(TrackInfo trackInfo, int i2);
    }

    public interface OnDefinitionChangedListrener {
        void onDefinitionChanged(TrackInfo trackInfo);
    }

    public interface OnSubtitleChangedListener {
        void onSubtitleCancel();

        void onSubtitleChanged(TrackInfo trackInfo);
    }

    public TrackInfoView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private RadioButton createRadioButton() {
        RadioButton radioButton = new RadioButton(this.mContext);
        radioButton.setPadding(50, 50, 50, 50);
        radioButton.setGravity(17);
        radioButton.setButtonDrawable((Drawable) null);
        radioButton.setTextColor(getResources().getColorStateList(R.color.radio_track_info_color_selector));
        return radioButton;
    }

    private void findAllViews(View view) {
        this.mTrackInfoRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group_track_info);
    }

    private void init() {
        findAllViews(LayoutInflater.from(this.mContext).inflate(R.layout.alivc_dialog_trackinfo, (ViewGroup) this, true));
        initListener();
    }

    private void initListener() {
        this.mTrackInfoRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override // android.widget.RadioGroup.OnCheckedChangeListener
    public void onCheckedChanged(RadioGroup radioGroup, int i2) {
        OnSubtitleChangedListener onSubtitleChangedListener;
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(i2);
        Object tag = radioButton.getTag();
        if (!(tag instanceof TrackInfo)) {
            if (!(tag instanceof String) || (onSubtitleChangedListener = this.mOnSubtitleChangedListener) == null) {
                return;
            }
            onSubtitleChangedListener.onSubtitleCancel();
            return;
        }
        TrackInfo trackInfo = (TrackInfo) radioButton.getTag();
        if (trackInfo == null) {
            OnBitrateChangedListener onBitrateChangedListener = this.mOnBitrateChangedListener;
            if (onBitrateChangedListener != null) {
                onBitrateChangedListener.onBitrateChanged(trackInfo, i2);
                return;
            }
            return;
        }
        TrackInfo.Type type = trackInfo.getType();
        if (type == TrackInfo.Type.TYPE_AUDIO) {
            OnAudioChangedListener onAudioChangedListener = this.mOnAudioChangedListener;
            if (onAudioChangedListener != null) {
                onAudioChangedListener.onAudioChanged(trackInfo);
                return;
            }
            return;
        }
        if (type == TrackInfo.Type.TYPE_SUBTITLE) {
            OnSubtitleChangedListener onSubtitleChangedListener2 = this.mOnSubtitleChangedListener;
            if (onSubtitleChangedListener2 != null) {
                onSubtitleChangedListener2.onSubtitleChanged(trackInfo);
                return;
            }
            return;
        }
        if (type == TrackInfo.Type.TYPE_VIDEO) {
            OnBitrateChangedListener onBitrateChangedListener2 = this.mOnBitrateChangedListener;
            if (onBitrateChangedListener2 != null) {
                onBitrateChangedListener2.onBitrateChanged(trackInfo, i2);
                return;
            }
            return;
        }
        OnDefinitionChangedListrener onDefinitionChangedListrener = this.mOnDefinitionChangedListener;
        if (onDefinitionChangedListrener != null) {
            onDefinitionChangedListrener.onDefinitionChanged(trackInfo);
        }
    }

    public void setCurrentTrackInfo(TrackInfo trackInfo) {
        RadioGroup radioGroup = this.mTrackInfoRadioGroup;
        if (radioGroup == null || radioGroup.getChildCount() <= 0) {
            return;
        }
        int childCount = this.mTrackInfoRadioGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            RadioButton radioButton = (RadioButton) this.mTrackInfoRadioGroup.getChildAt(i2);
            if (radioButton != null) {
                TrackInfo trackInfo2 = (TrackInfo) radioButton.getTag();
                if (trackInfo != null && trackInfo2 != null && trackInfo.getIndex() == trackInfo2.getIndex() && (trackInfo.getType() != TrackInfo.Type.TYPE_VIDEO || i2 != 0)) {
                    this.mTrackInfoRadioGroup.check(radioButton.getId());
                    return;
                }
            }
        }
    }

    public void setOnAudioChangedListener(OnAudioChangedListener onAudioChangedListener) {
        this.mOnAudioChangedListener = onAudioChangedListener;
    }

    public void setOnBitrateChangedListener(OnBitrateChangedListener onBitrateChangedListener) {
        this.mOnBitrateChangedListener = onBitrateChangedListener;
    }

    public void setOnDefinitionChangedListener(OnDefinitionChangedListrener onDefinitionChangedListrener) {
        this.mOnDefinitionChangedListener = onDefinitionChangedListrener;
    }

    public void setOnSubtitleChangedListener(OnSubtitleChangedListener onSubtitleChangedListener) {
        this.mOnSubtitleChangedListener = onSubtitleChangedListener;
    }

    public void setTrackInfoLists(List<TrackInfo> list) {
        if (this.mTrackInfoRadioGroup == null || list == null) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            TrackInfo trackInfo = list.get(i2);
            RadioButton radioButtonCreateRadioButton = createRadioButton();
            radioButtonCreateRadioButton.setTag(trackInfo);
            if (trackInfo.getType() == TrackInfo.Type.TYPE_AUDIO) {
                radioButtonCreateRadioButton.setText(trackInfo.getAudioLang());
            } else if (trackInfo.getType() == TrackInfo.Type.TYPE_SUBTITLE) {
                radioButtonCreateRadioButton.setText(trackInfo.getSubtitleLang());
            } else if (trackInfo.getType() == TrackInfo.Type.TYPE_VOD) {
                radioButtonCreateRadioButton.setText(QualityItem.getItem(getContext(), trackInfo.getVodDefinition(), false).getName());
            } else if (i2 == 0) {
                radioButtonCreateRadioButton.setText("自动码率");
                radioButtonCreateRadioButton.setId(R.id.auto_bitrate);
            } else {
                radioButtonCreateRadioButton.setText(String.format(Locale.getDefault(), TimeModel.NUMBER_FORMAT, Integer.valueOf(trackInfo.getVideoBitrate())));
            }
            this.mTrackInfoRadioGroup.addView(radioButtonCreateRadioButton);
        }
    }

    public TrackInfoView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init();
    }

    public TrackInfoView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mContext = context;
        init();
    }
}
