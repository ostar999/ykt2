package com.aliyun.player.alivcplayerexpand.view.quality;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.alivcplayerexpand.listener.QualityValue;
import com.aliyun.player.alivcplayerexpand.theme.ITheme;
import com.aliyun.player.alivcplayerexpand.theme.Theme;
import com.aliyun.player.nativeclass.TrackInfo;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes2.dex */
public class QualityView extends FrameLayout implements ITheme {
    private String currentQuality;
    private boolean isMtsSource;
    private BaseAdapter mAdapter;
    private ListView mListView;
    private OnQualityClickListener mOnQualityClickListener;
    private List<TrackInfo> mQualityItems;
    private int themeColorResId;

    public interface OnQualityClickListener {
        void onQualityClick(TrackInfo trackInfo);
    }

    public class QualityAdapter extends BaseAdapter {
        private QualityAdapter() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            if (QualityView.this.mQualityItems != null) {
                return QualityView.this.mQualityItems.size();
            }
            return 0;
        }

        @Override // android.widget.Adapter
        public Object getItem(int i2) {
            return QualityView.this.mQualityItems.get(i2);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i2) {
            return i2;
        }

        @Override // android.widget.Adapter
        public View getView(int i2, View view, ViewGroup viewGroup) {
            TextView textView = (TextView) LayoutInflater.from(QualityView.this.getContext()).inflate(R.layout.ratetype_item, (ViewGroup) null);
            if (QualityView.this.mQualityItems != null) {
                String vodDefinition = ((TrackInfo) QualityView.this.mQualityItems.get(i2)).getVodDefinition();
                textView.setText(QualityItem.getItem(QualityView.this.getContext(), vodDefinition, QualityView.this.isMtsSource).getName());
                if (vodDefinition.equals(QualityView.this.currentQuality)) {
                    textView.setTextColor(ContextCompat.getColor(QualityView.this.getContext(), QualityView.this.themeColorResId));
                } else {
                    textView.setTextColor(ContextCompat.getColor(QualityView.this.getContext(), R.color.alivc_common_font_white_light));
                }
            }
            return textView;
        }
    }

    public QualityView(@NonNull Context context) {
        super(context);
        this.isMtsSource = false;
        this.themeColorResId = R.color.alivc_blue;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.alivc_view_quality, (ViewGroup) this, true);
        ListView listView = (ListView) findViewById(R.id.quality_view);
        this.mListView = listView;
        listView.setChoiceMode(1);
        this.mListView.setVerticalScrollBarEnabled(false);
        this.mListView.setHorizontalScrollBarEnabled(false);
        QualityAdapter qualityAdapter = new QualityAdapter();
        this.mAdapter = qualityAdapter;
        this.mListView.setAdapter((ListAdapter) qualityAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.quality.QualityView.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j2) {
                QualityView.this.hide();
                if (QualityView.this.mOnQualityClickListener == null || QualityView.this.mQualityItems == null) {
                    return;
                }
                QualityView.this.mOnQualityClickListener.onQualityClick((TrackInfo) QualityView.this.mQualityItems.get(i2));
            }
        });
        hide();
    }

    private List<TrackInfo> sortQuality(List<TrackInfo> list) {
        if (this.isMtsSource) {
            return list;
        }
        TrackInfo trackInfo = null;
        TrackInfo trackInfo2 = null;
        TrackInfo trackInfo3 = null;
        TrackInfo trackInfo4 = null;
        TrackInfo trackInfo5 = null;
        TrackInfo trackInfo6 = null;
        TrackInfo trackInfo7 = null;
        TrackInfo trackInfo8 = null;
        TrackInfo trackInfo9 = null;
        for (TrackInfo trackInfo10 : list) {
            if (QualityValue.QUALITY_FLUENT.equals(trackInfo10.getVodDefinition())) {
                trackInfo3 = trackInfo10;
            } else if (QualityValue.QUALITY_LOW.equals(trackInfo10.getVodDefinition())) {
                trackInfo4 = trackInfo10;
            } else if (QualityValue.QUALITY_STAND.equals(trackInfo10.getVodDefinition())) {
                trackInfo5 = trackInfo10;
            } else if (QualityValue.QUALITY_HIGH.equals(trackInfo10.getVodDefinition())) {
                trackInfo6 = trackInfo10;
            } else if (QualityValue.QUALITY_2K.equals(trackInfo10.getVodDefinition())) {
                trackInfo7 = trackInfo10;
            } else if (QualityValue.QUALITY_4K.equals(trackInfo10.getVodDefinition())) {
                trackInfo8 = trackInfo10;
            } else if (QualityValue.QUALITY_ORIGINAL.equals(trackInfo10.getVodDefinition())) {
                trackInfo9 = trackInfo10;
            } else if (QualityValue.QUALITY_SQ.equals(trackInfo10.getVodDefinition())) {
                trackInfo = trackInfo10;
            } else if (QualityValue.QUALITY_HQ.equals(trackInfo10.getVodDefinition())) {
                trackInfo2 = trackInfo10;
            }
        }
        LinkedList linkedList = new LinkedList();
        if (trackInfo != null) {
            linkedList.add(trackInfo);
        }
        if (trackInfo2 != null) {
            linkedList.add(trackInfo2);
        }
        if (trackInfo3 != null) {
            linkedList.add(trackInfo3);
        }
        if (trackInfo4 != null) {
            linkedList.add(trackInfo4);
        }
        if (trackInfo5 != null) {
            linkedList.add(trackInfo5);
        }
        if (trackInfo6 != null) {
            linkedList.add(trackInfo6);
        }
        if (trackInfo7 != null) {
            linkedList.add(trackInfo7);
        }
        if (trackInfo8 != null) {
            linkedList.add(trackInfo8);
        }
        if (trackInfo9 != null) {
            linkedList.add(trackInfo9);
        }
        return linkedList;
    }

    public void hide() {
        ListView listView = this.mListView;
        if (listView == null || listView.getVisibility() != 0) {
            return;
        }
        this.mListView.setVisibility(8);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mListView.getVisibility() != 0) {
            return super.onTouchEvent(motionEvent);
        }
        hide();
        return true;
    }

    public void setIsMtsSource(boolean z2) {
        this.isMtsSource = z2;
    }

    public void setOnQualityClickListener(OnQualityClickListener onQualityClickListener) {
        this.mOnQualityClickListener = onQualityClickListener;
    }

    public void setQuality(List<TrackInfo> list, String str) {
        this.mQualityItems = sortQuality(list);
        this.currentQuality = str;
        BaseAdapter baseAdapter = this.mAdapter;
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.aliyun.player.alivcplayerexpand.theme.ITheme
    public void setTheme(Theme theme) {
        if (theme == Theme.Blue) {
            this.themeColorResId = R.color.alivc_blue;
        } else if (theme == Theme.Green) {
            this.themeColorResId = R.color.alivc_green;
        } else if (theme == Theme.Orange) {
            this.themeColorResId = R.color.alivc_orange;
        } else if (theme == Theme.Red) {
            this.themeColorResId = R.color.alivc_red;
        } else {
            this.themeColorResId = R.color.alivc_blue;
        }
        BaseAdapter baseAdapter = this.mAdapter;
        if (baseAdapter != null) {
            baseAdapter.notifyDataSetChanged();
        }
    }

    public void showAtTop(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mListView.getLayoutParams();
        layoutParams.width = view.getWidth();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.alivc_player_rate_item_height) * this.mQualityItems.size();
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        layoutParams.leftMargin = iArr[0];
        layoutParams.topMargin = ((getHeight() - layoutParams.height) - view.getHeight()) - 20;
        this.mListView.setLayoutParams(layoutParams);
        this.mListView.setVisibility(0);
    }

    public QualityView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isMtsSource = false;
        this.themeColorResId = R.color.alivc_blue;
        init();
    }

    public QualityView(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i2) {
        super(context, attributeSet, i2);
        this.isMtsSource = false;
        this.themeColorResId = R.color.alivc_blue;
        init();
    }
}
