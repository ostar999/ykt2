package com.psychiatrygarden.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.util.download.AliyunDownloadMediaInfo;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.bean.AliyueDownloadMediaInfoBean;
import com.psychiatrygarden.bean.QuestionCacheVideoBeanDao;
import com.squareup.picasso.Picasso;
import com.yikaobang.yixue.R;
import de.greenrobot.dao.query.WhereCondition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class DownLoadNewAdapter extends BaseAdapter {
    private List<AliyueDownloadMediaInfoBean> downloadItems;
    private Context mContext;
    private Handler mHandler;

    /* renamed from: com.psychiatrygarden.adapter.DownLoadNewAdapter$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status;

        static {
            int[] iArr = new int[AliyunDownloadMediaInfo.Status.values().length];
            $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status = iArr;
            try {
                iArr[AliyunDownloadMediaInfo.Status.Prepare.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Wait.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Start.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Stop.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Complete.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[AliyunDownloadMediaInfo.Status.Error.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public class DownloadItemHolder {
        private ImageView mCheckVideo;
        private TextView mCourseTitle;
        private ImageView mDownimg;
        private ImageView mIvStoreImg;
        private RelativeLayout mRelZhezhao;
        private TextView mTvCourseMb;
        private TextView mTvCourseStatus;

        public DownloadItemHolder(View convertView) {
            this.mIvStoreImg = (ImageView) convertView.findViewById(R.id.iv_store_img);
            this.mDownimg = (ImageView) convertView.findViewById(R.id.mdownimg);
            this.mRelZhezhao = (RelativeLayout) convertView.findViewById(R.id.rel_zhezhao);
            this.mCourseTitle = (TextView) convertView.findViewById(R.id.course_title);
            this.mTvCourseStatus = (TextView) convertView.findViewById(R.id.tv_course_status);
            this.mTvCourseMb = (TextView) convertView.findViewById(R.id.tv_course_mb);
            this.mCheckVideo = (ImageView) convertView.findViewById(R.id.check_video);
        }

        public void bindData(final AliyueDownloadMediaInfoBean info) {
            if (info.isAllSelect()) {
                this.mCheckVideo.setVisibility(0);
            } else {
                this.mCheckVideo.setVisibility(8);
            }
            if (info.isSelect()) {
                this.mCheckVideo.setImageResource(R.drawable.selectedvideo);
            } else {
                this.mCheckVideo.setImageResource(R.drawable.uncheckvideo);
            }
            this.mCheckVideo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.DownLoadNewAdapter.DownloadItemHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    if (info.isSelect()) {
                        DownloadItemHolder.this.mCheckVideo.setImageResource(R.drawable.uncheckvideo);
                        info.setSelect(false);
                    } else {
                        DownloadItemHolder.this.mCheckVideo.setImageResource(R.drawable.selectedvideo);
                        info.setSelect(true);
                    }
                    DownLoadNewAdapter.this.notifyDataSetChanged();
                    DownLoadNewAdapter.this.mHandler.sendEmptyMessage(100);
                }
            });
            String coverUrl = info.aliyunDownloadMediaInfo.getCoverUrl();
            if (!coverUrl.equals(this.mIvStoreImg.getTag()) && !TextUtils.isEmpty(coverUrl)) {
                Picasso.with(DownLoadNewAdapter.this.mContext).load(coverUrl).into(this.mIvStoreImg);
                this.mIvStoreImg.setTag(coverUrl);
            }
            try {
                ProjectApp.mDaoSession.clear();
                this.mCourseTitle.setText(ProjectApp.mDaoSession.getQuestionCacheVideoBeanDao().queryBuilder().where(QuestionCacheVideoBeanDao.Properties.Vid.eq(info.aliyunDownloadMediaInfo.getVid()), new WhereCondition[0]).list().get(0).getTitle());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            switch (AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[info.aliyunDownloadMediaInfo.getStatus().ordinal()]) {
                case 1:
                    this.mTvCourseStatus.setText("准备中");
                    break;
                case 2:
                    this.mTvCourseStatus.setText("等待中");
                    this.mDownimg.setImageResource(R.drawable.waitimg);
                    break;
                case 3:
                    this.mTvCourseStatus.setText("正在下载");
                    this.mDownimg.setImageResource(R.drawable.dimg);
                    break;
                case 4:
                    this.mTvCourseStatus.setText("暂停");
                    this.mDownimg.setImageResource(R.drawable.pimg);
                    break;
                case 5:
                    this.mTvCourseStatus.setText("下载完成");
                    break;
                case 6:
                    this.mTvCourseStatus.setText("下载出错");
                    break;
            }
            int i2 = (Integer.parseInt(info.aliyunDownloadMediaInfo.getSize() + "") / 1024) / 1024;
            this.mTvCourseMb.setText(String.format(Locale.CHINA, "%dMB / %dMB", Integer.valueOf((int) ((i2 * Float.parseFloat(info.aliyunDownloadMediaInfo.getProgress() + "")) / 100.0f)), Integer.valueOf(i2)));
            this.mRelZhezhao.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.DownLoadNewAdapter.DownloadItemHolder.2
                @Override // android.view.View.OnClickListener
                public void onClick(View v2) {
                    int i3 = AnonymousClass1.$SwitchMap$com$aliyun$player$alivcplayerexpand$util$download$AliyunDownloadMediaInfo$Status[info.aliyunDownloadMediaInfo.getStatus().ordinal()];
                    if (i3 == 3) {
                        ProjectApp.downloadManager.stopDownload(info.aliyunDownloadMediaInfo);
                    } else {
                        if (i3 != 4) {
                            return;
                        }
                        ProjectApp.downloadManager.startDownload(info.aliyunDownloadMediaInfo);
                    }
                }
            });
        }
    }

    public DownLoadNewAdapter(Context context, List<AliyueDownloadMediaInfoBean> downloadItems, Handler mHandler) {
        this.downloadItems = new ArrayList();
        this.mContext = context.getApplicationContext();
        this.downloadItems = downloadItems;
        this.mHandler = mHandler;
    }

    private boolean hasAdded(AliyueDownloadMediaInfoBean info) {
        for (AliyueDownloadMediaInfoBean aliyueDownloadMediaInfoBean : this.downloadItems) {
            if (info.aliyunDownloadMediaInfo.getFormat().equals(aliyueDownloadMediaInfoBean.aliyunDownloadMediaInfo.getFormat()) && info.aliyunDownloadMediaInfo.getQuality().equals(aliyueDownloadMediaInfoBean.aliyunDownloadMediaInfo.getQuality()) && info.aliyunDownloadMediaInfo.getVid().equals(aliyueDownloadMediaInfoBean.aliyunDownloadMediaInfo.getVid()) && info.aliyunDownloadMediaInfo.isEncripted() == aliyueDownloadMediaInfoBean.aliyunDownloadMediaInfo.isEncripted()) {
                return true;
            }
        }
        return false;
    }

    public void addAllInfos(final List<AliyueDownloadMediaInfoBean> downloadingMedias) {
        for (AliyueDownloadMediaInfoBean aliyueDownloadMediaInfoBean : downloadingMedias) {
            if (!hasAdded(aliyueDownloadMediaInfoBean)) {
                this.downloadItems.add(aliyueDownloadMediaInfoBean);
            }
        }
        notifyDataSetChanged();
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.downloadItems.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.downloadItems.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        DownloadItemHolder downloadItemHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.mContext.getApplicationContext()).inflate(R.layout.chaeitem, (ViewGroup) null);
            downloadItemHolder = new DownloadItemHolder(convertView);
            convertView.setTag(downloadItemHolder);
        } else {
            downloadItemHolder = (DownloadItemHolder) convertView.getTag();
        }
        downloadItemHolder.bindData(this.downloadItems.get(position));
        convertView.setId(position + R.id.custom_id_min);
        return convertView;
    }
}
