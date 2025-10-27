package com.psychiatrygarden.widget;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.psychiatrygarden.ProjectApp;
import com.tencent.smtt.sdk.TbsReaderView;
import java.io.File;

/* loaded from: classes6.dex */
public class FileTbsFragment extends FrameLayout implements TbsReaderView.ReaderCallback {
    private Context mContext;
    private OnGetFilePathListener mOnGetFilePathListener;
    private TbsReaderView mTbsReaderView;

    public interface OnGetFilePathListener {
        void onGetFilePath(FileTbsFragment mSuperFileView2);
    }

    public FileTbsFragment(Context context) {
        this(context, null);
    }

    private String getFileType(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            Log.d("filetbs", "paramString---->null");
            return "";
        }
        Log.d("filetbs", "paramString:" + paramString);
        int iLastIndexOf = paramString.lastIndexOf(46);
        if (iLastIndexOf <= -1) {
            Log.d("filetbs", "i <= -1");
            return "";
        }
        String strSubstring = paramString.substring(iLastIndexOf + 1);
        Log.d("filetbs", "paramString.substring(i + 1)------>" + strSubstring);
        return strSubstring;
    }

    private TbsReaderView getTbsReaderView(Context context) {
        return new TbsReaderView(context, this);
    }

    public void displayFile(File mFile, String article_id) {
        String str;
        if (mFile == null || TextUtils.isEmpty(mFile.toString())) {
            Log.e("filetbs", "文件路径无效！");
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            str = ProjectApp.instance().getExternalFilesDir(null).getAbsolutePath() + "/TbsReaderTemp/";
        } else {
            str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/TbsReaderTemp/";
        }
        Bundle bundle = new Bundle();
        Log.d("filetbs", mFile.toString());
        bundle.putString(TbsReaderView.KEY_FILE_PATH, mFile.toString());
        bundle.putString(TbsReaderView.KEY_TEMP_PATH, str);
        if (this.mTbsReaderView == null) {
            this.mTbsReaderView = getTbsReaderView(this.mContext);
        }
        if (this.mTbsReaderView.preOpen(getFileType(mFile.toString()), false)) {
            this.mTbsReaderView.openFile(bundle);
        }
    }

    @Override // com.tencent.smtt.sdk.TbsReaderView.ReaderCallback
    public void onCallBackAction(Integer integer, Object o2, Object o12) {
    }

    public void onStopDisplay() {
        TbsReaderView tbsReaderView = this.mTbsReaderView;
        if (tbsReaderView != null) {
            tbsReaderView.onStop();
        }
    }

    public void setOnGetFilePathListener(OnGetFilePathListener mOnGetFilePathListener) {
        this.mOnGetFilePathListener = mOnGetFilePathListener;
    }

    public void show() {
        OnGetFilePathListener onGetFilePathListener = this.mOnGetFilePathListener;
        if (onGetFilePathListener != null) {
            onGetFilePathListener.onGetFilePath(this);
        }
    }

    public FileTbsFragment(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FileTbsFragment(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        removeAllViews();
        TbsReaderView tbsReaderView = new TbsReaderView(context, this);
        this.mTbsReaderView = tbsReaderView;
        addView(tbsReaderView, new LinearLayout.LayoutParams(-1, -1));
    }
}
