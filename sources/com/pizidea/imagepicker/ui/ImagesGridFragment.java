package com.pizidea.imagepicker.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.ImgLoader;
import com.pizidea.imagepicker.PicassoImgLoader;
import com.pizidea.imagepicker.R;
import com.pizidea.imagepicker.Util;
import com.pizidea.imagepicker.bean.ImageItem;
import com.pizidea.imagepicker.bean.ImageSet;
import com.pizidea.imagepicker.data.OnImagesLoadedListener;
import com.pizidea.imagepicker.data.impl.LocalDataSource;
import com.pizidea.imagepicker.ui.activity.ImageCropActivity;
import com.pizidea.imagepicker.widget.SuperCheckBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class ImagesGridFragment extends Fragment implements OnImagesLoadedListener, AndroidImagePicker.OnImageSelectedChangeListener, AndroidImagePicker.OnImageCropCompleteListener {
    private static final int ITEM_TYPE_CAMERA = 0;
    private static final int ITEM_TYPE_NORMAL = 1;
    private static final String TAG = "ImagesGridFragment";
    AndroidImagePicker androidImagePicker;
    Button btnDir;
    int imageGridSize;
    ImageGridAdapter mAdapter;
    Activity mContext;
    private ListPopupWindow mFolderPopupWindow;
    private View mFooterView;
    GridView mGridView;
    ImgLoader mImagePresenter;
    private ImageSetAdapter mImageSetAdapter;
    List<ImageSet> mImageSetList;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public class ImageGridAdapter extends BaseAdapter {
        List<ImageItem> images;
        Context mContext;

        public class ViewHolder {
            View cbPanel;
            SuperCheckBox cbSelected;
            ImageView ivPic;

            public ViewHolder() {
            }
        }

        public ImageGridAdapter(Context context, List<ImageItem> list) {
            this.images = list;
            this.mContext = context;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return ImagesGridFragment.this.shouldShowCamera() ? this.images.size() + 1 : this.images.size();
        }

        @Override // android.widget.Adapter
        public long getItemId(int i2) {
            return i2;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getItemViewType(int i2) {
            return (ImagesGridFragment.this.shouldShowCamera() && i2 == 0) ? 0 : 1;
        }

        @Override // android.widget.Adapter
        public View getView(final int i2, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (getItemViewType(i2) == 0) {
                View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.grid_item_camera, viewGroup, false);
                viewInflate.setTag(null);
                viewInflate.setOnClickListener(new View.OnClickListener() { // from class: com.pizidea.imagepicker.ui.ImagesGridFragment.ImageGridAdapter.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view2) {
                        try {
                            ImagesGridFragment imagesGridFragment = ImagesGridFragment.this;
                            imagesGridFragment.androidImagePicker.takePicture(imagesGridFragment, 1431);
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                });
                return viewInflate;
            }
            if (view == null) {
                view = LayoutInflater.from(this.mContext).inflate(R.layout.image_grid_item, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.ivPic = (ImageView) view.findViewById(R.id.iv_thumb);
                viewHolder.cbSelected = (SuperCheckBox) view.findViewById(R.id.iv_thumb_check);
                viewHolder.cbPanel = view.findViewById(R.id.thumb_check_panel);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            if (ImagesGridFragment.this.shouldSelectMulti()) {
                viewHolder.cbSelected.setVisibility(0);
            } else {
                viewHolder.cbSelected.setVisibility(8);
            }
            final ImageItem item = getItem(i2);
            viewHolder.cbSelected.setOnClickListener(new View.OnClickListener() { // from class: com.pizidea.imagepicker.ui.ImagesGridFragment.ImageGridAdapter.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (ImagesGridFragment.this.androidImagePicker.getSelectImageCount() <= ImagesGridFragment.this.androidImagePicker.getSelectLimit() || !viewHolder.cbSelected.isChecked()) {
                        return;
                    }
                    viewHolder.cbSelected.toggle();
                    Toast.makeText(ImageGridAdapter.this.mContext, ImagesGridFragment.this.getResources().getString(R.string.you_have_a_select_limit, Integer.valueOf(ImagesGridFragment.this.androidImagePicker.getSelectLimit())), 0).show();
                }
            });
            viewHolder.cbSelected.setOnCheckedChangeListener(null);
            if (ImagesGridFragment.this.androidImagePicker.isSelect(i2, item)) {
                viewHolder.cbSelected.setChecked(true);
                viewHolder.ivPic.setSelected(true);
            } else {
                viewHolder.cbSelected.setChecked(false);
            }
            ViewGroup.LayoutParams layoutParams = viewHolder.ivPic.getLayoutParams();
            int i3 = ImagesGridFragment.this.imageGridSize;
            layoutParams.height = i3;
            layoutParams.width = i3;
            final View viewFindViewById = view.findViewById(R.id.iv_thumb);
            viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.pizidea.imagepicker.ui.ImagesGridFragment.ImageGridAdapter.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    AdapterView.OnItemClickListener onItemClickListener = ImagesGridFragment.this.mOnItemClickListener;
                    GridView gridView = ImagesGridFragment.this.mGridView;
                    View view3 = viewFindViewById;
                    int i4 = i2;
                    onItemClickListener.onItemClick(gridView, view3, i4, i4);
                }
            });
            viewHolder.cbSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.pizidea.imagepicker.ui.ImagesGridFragment.ImageGridAdapter.4
                @Override // android.widget.CompoundButton.OnCheckedChangeListener
                public void onCheckedChanged(CompoundButton compoundButton, boolean z2) {
                    if (z2) {
                        ImagesGridFragment.this.androidImagePicker.addSelectedImageItem(i2, item);
                    } else {
                        ImagesGridFragment.this.androidImagePicker.deleteSelectedImageItem(i2, item);
                    }
                }
            });
            ImagesGridFragment.this.mImagePresenter.onPresentImage(viewHolder.ivPic, getItem(i2).path, ImagesGridFragment.this.imageGridSize);
            return view;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getViewTypeCount() {
            return 2;
        }

        public void refreshData(List<ImageItem> list) {
            if (list == null || list.size() <= 0) {
                this.images = new ArrayList();
            } else {
                this.images = list;
            }
            notifyDataSetChanged();
        }

        @Override // android.widget.Adapter
        public ImageItem getItem(int i2) {
            if (!ImagesGridFragment.this.shouldShowCamera()) {
                return this.images.get(i2);
            }
            if (i2 == 0) {
                return null;
            }
            return this.images.get(i2 - 1);
        }
    }

    public class ImageSetAdapter extends BaseAdapter {
        private Context mContext;
        int mImageSize;
        private LayoutInflater mInflater;
        private List<ImageSet> mImageSets = new ArrayList();
        int lastSelected = 0;

        public class ViewHolder {
            ImageView cover;
            ImageView indicator;
            TextView name;
            TextView size;

            public ViewHolder(View view) {
                this.cover = (ImageView) view.findViewById(R.id.cover);
                this.name = (TextView) view.findViewById(R.id.name);
                this.size = (TextView) view.findViewById(R.id.size);
                this.indicator = (ImageView) view.findViewById(R.id.indicator);
                view.setTag(this);
            }

            public void bindData(ImageSet imageSet) {
                this.name.setText(imageSet.name);
                this.size.setText(imageSet.imageItems.size() + ImageSetAdapter.this.mContext.getResources().getString(R.string.piece));
                ImagesGridFragment imagesGridFragment = ImagesGridFragment.this;
                imagesGridFragment.mImagePresenter.onPresentImage(this.cover, imageSet.cover.path, imagesGridFragment.imageGridSize);
            }
        }

        public ImageSetAdapter(Context context) {
            this.mContext = context;
            this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
            this.mImageSize = this.mContext.getResources().getDimensionPixelOffset(R.dimen.image_cover_size);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.mImageSets.size();
        }

        @Override // android.widget.Adapter
        public long getItemId(int i2) {
            return i2;
        }

        public int getSelectIndex() {
            return this.lastSelected;
        }

        @Override // android.widget.Adapter
        public View getView(int i2, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = this.mInflater.inflate(R.layout.list_item_folder, viewGroup, false);
                viewHolder = new ViewHolder(view);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.bindData(getItem(i2));
            if (this.lastSelected == i2) {
                viewHolder.indicator.setVisibility(0);
            } else {
                viewHolder.indicator.setVisibility(4);
            }
            return view;
        }

        public void refreshData(List<ImageSet> list) {
            if (list == null || list.size() <= 0) {
                this.mImageSets.clear();
            } else {
                this.mImageSets = list;
            }
            notifyDataSetChanged();
        }

        public void setSelectIndex(int i2) {
            if (this.lastSelected == i2) {
                return;
            }
            this.lastSelected = i2;
            notifyDataSetChanged();
        }

        @Override // android.widget.Adapter
        public ImageSet getItem(int i2) {
            return this.mImageSets.get(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createPopupFolderList(int i2, int i3) {
        ListPopupWindow listPopupWindow = new ListPopupWindow(this.mContext);
        this.mFolderPopupWindow = listPopupWindow;
        listPopupWindow.setAdapter(this.mImageSetAdapter);
        this.mFolderPopupWindow.setContentWidth(i2);
        this.mFolderPopupWindow.setWidth(i2);
        this.mFolderPopupWindow.setHeight((i3 * 5) / 8);
        this.mFolderPopupWindow.setAnchorView(this.mFooterView);
        this.mFolderPopupWindow.setModal(true);
        this.mFolderPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.pizidea.imagepicker.ui.ImagesGridFragment.2
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                ImagesGridFragment.this.backgroundAlpha(1.0f);
            }
        });
        this.mFolderPopupWindow.setAnimationStyle(R.style.popupwindow_anim_style);
        this.mFolderPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.pizidea.imagepicker.ui.ImagesGridFragment.3
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i4, long j2) {
                ImagesGridFragment.this.mImageSetAdapter.setSelectIndex(i4);
                ImagesGridFragment.this.androidImagePicker.setCurrentSelectedImageSetPosition(i4);
                new Handler().postDelayed(new Runnable() { // from class: com.pizidea.imagepicker.ui.ImagesGridFragment.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        ImagesGridFragment.this.mFolderPopupWindow.dismiss();
                        ImageSet imageSet = (ImageSet) adapterView.getAdapter().getItem(i4);
                        if (imageSet != null) {
                            ImagesGridFragment.this.mAdapter.refreshData(imageSet.imageItems);
                            ImagesGridFragment.this.btnDir.setText(imageSet.name);
                        }
                        ImagesGridFragment.this.mGridView.smoothScrollToPosition(0);
                    }
                }, 100L);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldSelectMulti() {
        return this.androidImagePicker.getSelectMode() == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldShowCamera() {
        return this.androidImagePicker.isShouldShowCamera();
    }

    public void backgroundAlpha(float f2) {
        WindowManager.LayoutParams attributes = this.mContext.getWindow().getAttributes();
        attributes.alpha = f2;
        this.mContext.getWindow().setAttributes(attributes);
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 == 1431 && i3 == -1) {
            if (TextUtils.isEmpty(this.androidImagePicker.getCurrentPhotoPath())) {
                Log.i(TAG, "didn't save to your path");
                return;
            }
            AndroidImagePicker.galleryAddPic(this.mContext, this.androidImagePicker.getCurrentPhotoPath());
            getActivity().finish();
            AndroidImagePicker androidImagePicker = this.androidImagePicker;
            if (androidImagePicker.cropMode) {
                Intent intent2 = new Intent();
                intent2.setClass(this.mContext, ImageCropActivity.class);
                intent2.putExtra(AndroidImagePicker.KEY_PIC_PATH, this.androidImagePicker.getCurrentPhotoPath());
                startActivityForResult(intent2, 1431);
                return;
            }
            ImageItem imageItem = new ImageItem(androidImagePicker.getCurrentPhotoPath(), "", -1L);
            this.androidImagePicker.clearSelectedImages();
            this.androidImagePicker.addSelectedImageItem(-1, imageItem);
            this.androidImagePicker.notifyOnImagePickComplete();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getActivity();
        AndroidImagePicker androidImagePicker = AndroidImagePicker.getInstance();
        this.androidImagePicker = androidImagePicker;
        androidImagePicker.addOnImageSelectedChangeListener(this);
        AndroidImagePicker androidImagePicker2 = this.androidImagePicker;
        if (androidImagePicker2.cropMode) {
            androidImagePicker2.addOnImageCropCompleteListener(this);
        }
    }

    @Override // androidx.fragment.app.Fragment
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_images_grid, (ViewGroup) null);
        this.mFooterView = viewInflate.findViewById(R.id.footer_panel);
        this.imageGridSize = (this.mContext.getWindowManager().getDefaultDisplay().getWidth() - (Util.dp2px(this.mContext, 2.0f) * 2)) / 3;
        this.btnDir = (Button) viewInflate.findViewById(R.id.btn_dir);
        this.mGridView = (GridView) viewInflate.findViewById(R.id.gridview);
        this.mImagePresenter = new PicassoImgLoader();
        new LocalDataSource(this.mContext).provideMediaItems(this);
        final int i2 = getResources().getDisplayMetrics().widthPixels;
        final int i3 = getResources().getDisplayMetrics().heightPixels;
        this.btnDir.setOnClickListener(new View.OnClickListener() { // from class: com.pizidea.imagepicker.ui.ImagesGridFragment.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ImagesGridFragment.this.mFolderPopupWindow == null) {
                    ImagesGridFragment.this.createPopupFolderList(i2, i3);
                }
                ImagesGridFragment.this.backgroundAlpha(0.3f);
                ImagesGridFragment.this.mImageSetAdapter.refreshData(ImagesGridFragment.this.mImageSetList);
                ImagesGridFragment.this.mFolderPopupWindow.setAdapter(ImagesGridFragment.this.mImageSetAdapter);
                if (ImagesGridFragment.this.mFolderPopupWindow.isShowing()) {
                    ImagesGridFragment.this.mFolderPopupWindow.dismiss();
                    return;
                }
                ImagesGridFragment.this.mFolderPopupWindow.show();
                int selectIndex = ImagesGridFragment.this.mImageSetAdapter.getSelectIndex();
                if (selectIndex != 0) {
                    selectIndex--;
                }
                ImagesGridFragment.this.mFolderPopupWindow.getListView().setSelection(selectIndex);
            }
        });
        ImageSetAdapter imageSetAdapter = new ImageSetAdapter(this.mContext);
        this.mImageSetAdapter = imageSetAdapter;
        imageSetAdapter.refreshData(this.mImageSetList);
        return viewInflate;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        this.androidImagePicker.removeOnImageItemSelectedChangeListener(this);
        AndroidImagePicker androidImagePicker = this.androidImagePicker;
        if (androidImagePicker.cropMode) {
            androidImagePicker.removeOnImageCropCompleteListener(this);
        }
        super.onDestroy();
    }

    @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImageCropCompleteListener
    public void onImageCropComplete(Bitmap bitmap, float f2) {
        getActivity().finish();
    }

    @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImageSelectedChangeListener
    public void onImageSelectChange(int i2, ImageItem imageItem, int i3, int i4) {
        this.mAdapter.refreshData(AndroidImagePicker.getInstance().getImageItemsOfCurrentImageSet());
        Log.i(TAG, "=====EVENT:onImageSelectChange");
    }

    @Override // com.pizidea.imagepicker.data.OnImagesLoadedListener
    public void onImagesLoaded(List<ImageSet> list) {
        this.mImageSetList = list;
        this.btnDir.setText(list.get(0).name);
        ImageGridAdapter imageGridAdapter = new ImageGridAdapter(this.mContext, list.get(0).imageItems);
        this.mAdapter = imageGridAdapter;
        this.mGridView.setAdapter((ListAdapter) imageGridAdapter);
    }

    public void setOnImageItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
