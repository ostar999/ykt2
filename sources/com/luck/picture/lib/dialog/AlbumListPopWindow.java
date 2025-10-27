package com.luck.picture.lib.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.adapter.PictureAlbumAdapter;
import com.luck.picture.lib.decoration.WrapContentLinearLayoutManager;
import com.luck.picture.lib.entity.LocalMediaFolder;
import com.luck.picture.lib.interfaces.OnAlbumItemClickListener;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.utils.DensityUtil;
import com.luck.picture.lib.utils.SdkVersionUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class AlbumListPopWindow extends PopupWindow {
    private static final int ALBUM_MAX_COUNT = 8;
    private boolean isDismiss = false;
    private PictureAlbumAdapter mAdapter;
    private final Context mContext;
    private RecyclerView mRecyclerView;
    private View windMask;
    private int windowMaxHeight;
    private OnPopupWindowStatusListener windowStatusListener;

    public interface OnPopupWindowStatusListener {
        void onDismissPopupWindow();

        void onShowPopupWindow();
    }

    public AlbumListPopWindow(Context context) {
        this.mContext = context;
        setContentView(LayoutInflater.from(context).inflate(R.layout.ps_window_folder, (ViewGroup) null));
        setWidth(-1);
        setHeight(-2);
        setAnimationStyle(R.style.PictureThemeWindowStyle);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        initViews();
    }

    public static AlbumListPopWindow buildPopWindow(Context context) {
        return new AlbumListPopWindow(context);
    }

    private void initViews() {
        this.windowMaxHeight = (int) (DensityUtil.getScreenHeight(this.mContext) * 0.6d);
        this.mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.folder_list);
        this.windMask = getContentView().findViewById(R.id.rootViewBg);
        this.mRecyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this.mContext));
        PictureAlbumAdapter pictureAlbumAdapter = new PictureAlbumAdapter();
        this.mAdapter = pictureAlbumAdapter;
        this.mRecyclerView.setAdapter(pictureAlbumAdapter);
        this.windMask.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.dialog.AlbumListPopWindow.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                AlbumListPopWindow.this.dismiss();
            }
        });
        getContentView().findViewById(R.id.rootView).setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.dialog.AlbumListPopWindow.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (SdkVersionUtils.isMinM()) {
                    AlbumListPopWindow.this.dismiss();
                }
            }
        });
    }

    @SuppressLint({"NotifyDataSetChanged"})
    public void bindAlbumData(List<LocalMediaFolder> list) {
        this.mAdapter.bindAlbumData(list);
        this.mAdapter.notifyDataSetChanged();
        this.mRecyclerView.getLayoutParams().height = list.size() > 8 ? this.windowMaxHeight : -2;
    }

    public void changeSelectedAlbumStyle() {
        List<LocalMediaFolder> albumList = this.mAdapter.getAlbumList();
        for (int i2 = 0; i2 < albumList.size(); i2++) {
            LocalMediaFolder localMediaFolder = albumList.get(i2);
            localMediaFolder.setSelectTag(false);
            this.mAdapter.notifyItemChanged(i2);
            for (int i3 = 0; i3 < SelectedManager.getCount(); i3++) {
                if (TextUtils.equals(localMediaFolder.getFolderName(), SelectedManager.getSelectedResult().get(i3).getParentFolderName()) || localMediaFolder.getBucketId() == -1) {
                    localMediaFolder.setSelectTag(true);
                    this.mAdapter.notifyItemChanged(i2);
                    break;
                }
            }
        }
    }

    @Override // android.widget.PopupWindow
    public void dismiss() {
        if (this.isDismiss) {
            return;
        }
        this.windMask.animate().alpha(0.0f).setDuration(50L).start();
        OnPopupWindowStatusListener onPopupWindowStatusListener = this.windowStatusListener;
        if (onPopupWindowStatusListener != null) {
            onPopupWindowStatusListener.onDismissPopupWindow();
        }
        this.isDismiss = true;
        super.dismiss();
        this.isDismiss = false;
    }

    public List<LocalMediaFolder> getAlbumList() {
        return this.mAdapter.getAlbumList();
    }

    public int getFirstAlbumImageCount() {
        if (getFolderCount() > 0) {
            return getFolder(0).getFolderTotalNum();
        }
        return 0;
    }

    public LocalMediaFolder getFolder(int i2) {
        if (this.mAdapter.getAlbumList().size() <= 0 || i2 >= this.mAdapter.getAlbumList().size()) {
            return null;
        }
        return this.mAdapter.getAlbumList().get(i2);
    }

    public int getFolderCount() {
        return this.mAdapter.getAlbumList().size();
    }

    public void setOnIBridgeAlbumWidget(OnAlbumItemClickListener onAlbumItemClickListener) {
        this.mAdapter.setOnIBridgeAlbumWidget(onAlbumItemClickListener);
    }

    public void setOnPopupWindowStatusListener(OnPopupWindowStatusListener onPopupWindowStatusListener) {
        this.windowStatusListener = onPopupWindowStatusListener;
    }

    @Override // android.widget.PopupWindow
    public void showAsDropDown(View view) {
        if (getAlbumList() == null || getAlbumList().size() == 0) {
            return;
        }
        if (SdkVersionUtils.isN()) {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            showAtLocation(view, 0, 0, iArr[1] + view.getHeight());
        } else {
            super.showAsDropDown(view);
        }
        this.isDismiss = false;
        OnPopupWindowStatusListener onPopupWindowStatusListener = this.windowStatusListener;
        if (onPopupWindowStatusListener != null) {
            onPopupWindowStatusListener.onShowPopupWindow();
        }
        this.windMask.animate().alpha(1.0f).setDuration(250L).setStartDelay(250L).start();
        changeSelectedAlbumStyle();
    }
}
