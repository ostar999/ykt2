package com.luck.picture.lib.adapter.holder;

import android.content.Context;
import android.graphics.ColorFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.luck.picture.lib.R;
import com.luck.picture.lib.adapter.PictureImageGridAdapter;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.engine.ImageEngine;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.manager.SelectedManager;
import com.luck.picture.lib.style.SelectMainStyle;
import com.luck.picture.lib.utils.StyleUtils;
import com.luck.picture.lib.utils.ValueOf;

/* loaded from: classes4.dex */
public class BaseRecyclerMediaHolder extends RecyclerView.ViewHolder {
    public View btnCheck;
    public PictureSelectionConfig config;
    private ColorFilter defaultColorFilter;
    public boolean isSelectNumberStyle;
    public ImageView ivPicture;
    private PictureImageGridAdapter.OnItemClickListener listener;
    public Context mContext;
    private ColorFilter maskWhiteColorFilter;
    private ColorFilter selectColorFilter;
    public TextView tvCheck;

    public BaseRecyclerMediaHolder(@NonNull View view) {
        super(view);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void dispatchHandleMask(com.luck.picture.lib.entity.LocalMedia r5) {
        /*
            r4 = this;
            int r0 = com.luck.picture.lib.manager.SelectedManager.getCount()
            r1 = 1
            r2 = 0
            if (r0 <= 0) goto L5d
            java.util.ArrayList r0 = com.luck.picture.lib.manager.SelectedManager.getSelectedResult()
            boolean r0 = r0.contains(r5)
            if (r0 != 0) goto L5d
            com.luck.picture.lib.config.PictureSelectionConfig r0 = r4.config
            boolean r0 = r0.isWithVideoImage
            if (r0 == 0) goto L24
            int r0 = com.luck.picture.lib.manager.SelectedManager.getCount()
            com.luck.picture.lib.config.PictureSelectionConfig r3 = r4.config
            int r3 = r3.maxSelectNum
            if (r0 != r3) goto L5d
        L22:
            r0 = r1
            goto L5e
        L24:
            java.lang.String r0 = com.luck.picture.lib.manager.SelectedManager.getTopResultMimeType()
            boolean r0 = com.luck.picture.lib.config.PictureMimeType.isHasVideo(r0)
            if (r0 == 0) goto L48
            com.luck.picture.lib.config.PictureSelectionConfig r0 = r4.config
            int r3 = r0.maxVideoSelectNum
            if (r3 <= 0) goto L35
            goto L37
        L35:
            int r3 = r0.maxSelectNum
        L37:
            int r0 = com.luck.picture.lib.manager.SelectedManager.getCount()
            if (r0 == r3) goto L22
            java.lang.String r0 = r5.getMimeType()
            boolean r0 = com.luck.picture.lib.config.PictureMimeType.isHasImage(r0)
            if (r0 == 0) goto L5d
            goto L22
        L48:
            int r0 = com.luck.picture.lib.manager.SelectedManager.getCount()
            com.luck.picture.lib.config.PictureSelectionConfig r3 = r4.config
            int r3 = r3.maxSelectNum
            if (r0 == r3) goto L22
            java.lang.String r0 = r5.getMimeType()
            boolean r0 = com.luck.picture.lib.config.PictureMimeType.isHasVideo(r0)
            if (r0 == 0) goto L5d
            goto L22
        L5d:
            r0 = r2
        L5e:
            if (r0 == 0) goto L6b
            android.widget.ImageView r0 = r4.ivPicture
            android.graphics.ColorFilter r2 = r4.maskWhiteColorFilter
            r0.setColorFilter(r2)
            r5.setMaxSelectEnabledMask(r1)
            goto L6e
        L6b:
            r5.setMaxSelectEnabledMask(r2)
        L6e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.dispatchHandleMask(com.luck.picture.lib.entity.LocalMedia):void");
    }

    public static BaseRecyclerMediaHolder generate(ViewGroup viewGroup, int i2, int i3, PictureSelectionConfig pictureSelectionConfig) {
        View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(i3, viewGroup, false);
        return i2 != 1 ? i2 != 3 ? i2 != 4 ? new ImageViewHolder(viewInflate, pictureSelectionConfig) : new AudioViewHolder(viewInflate, pictureSelectionConfig) : new VideoViewHolder(viewInflate, pictureSelectionConfig) : new CameraViewHolder(viewInflate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isSelected(LocalMedia localMedia) {
        LocalMedia compareLocalMedia;
        boolean zContains = SelectedManager.getSelectedResult().contains(localMedia);
        if (zContains && (compareLocalMedia = localMedia.getCompareLocalMedia()) != null && compareLocalMedia.isEditorImage()) {
            localMedia.setCutPath(compareLocalMedia.getCutPath());
            localMedia.setCut(!TextUtils.isEmpty(compareLocalMedia.getCutPath()));
            localMedia.setEditorImage(compareLocalMedia.isEditorImage());
        }
        return zContains;
    }

    private void notifySelectNumberStyle(LocalMedia localMedia) {
        this.tvCheck.setText("");
        for (int i2 = 0; i2 < SelectedManager.getCount(); i2++) {
            LocalMedia localMedia2 = SelectedManager.getSelectedResult().get(i2);
            if (TextUtils.equals(localMedia2.getPath(), localMedia.getPath()) || localMedia2.getId() == localMedia.getId()) {
                localMedia.setNum(localMedia2.getNum());
                localMedia2.setPosition(localMedia.getPosition());
                this.tvCheck.setText(ValueOf.toString(Integer.valueOf(localMedia.getNum())));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectedMedia(boolean z2) {
        if (this.tvCheck.isSelected() != z2) {
            this.tvCheck.setSelected(z2);
        }
        if (this.config.isDirectReturnSingle) {
            this.ivPicture.setColorFilter(this.defaultColorFilter);
        } else {
            this.ivPicture.setColorFilter(z2 ? this.selectColorFilter : this.defaultColorFilter);
        }
    }

    public void bindData(final LocalMedia localMedia, final int i2) {
        localMedia.position = getAbsoluteAdapterPosition();
        selectedMedia(isSelected(localMedia));
        if (this.isSelectNumberStyle) {
            notifySelectNumberStyle(localMedia);
        }
        PictureSelectionConfig pictureSelectionConfig = this.config;
        if (pictureSelectionConfig.isMaxSelectEnabledMask && pictureSelectionConfig.selectionMode == 2) {
            dispatchHandleMask(localMedia);
        }
        String path = localMedia.getPath();
        if (localMedia.isEditorImage()) {
            path = localMedia.getCutPath();
        }
        if (PictureMimeType.isHasAudio(localMedia.getMimeType())) {
            this.ivPicture.setImageResource(R.drawable.ps_trans_1px);
        } else {
            ImageEngine imageEngine = PictureSelectionConfig.imageEngine;
            if (imageEngine != null) {
                imageEngine.loadGridImage(this.ivPicture.getContext(), path, this.ivPicture);
            }
        }
        this.tvCheck.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                BaseRecyclerMediaHolder.this.btnCheck.performClick();
            }
        });
        this.btnCheck.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (localMedia.isMaxSelectEnabledMask() || BaseRecyclerMediaHolder.this.listener == null || BaseRecyclerMediaHolder.this.listener.onSelected(BaseRecyclerMediaHolder.this.tvCheck, i2, localMedia) == -1) {
                    return;
                }
                BaseRecyclerMediaHolder baseRecyclerMediaHolder = BaseRecyclerMediaHolder.this;
                baseRecyclerMediaHolder.selectedMedia(baseRecyclerMediaHolder.isSelected(localMedia));
            }
        });
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.3
            /* JADX WARN: Removed duplicated region for block: B:19:0x0046  */
            /* JADX WARN: Removed duplicated region for block: B:26:0x005f  */
            @Override // android.view.View.OnClickListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onClick(android.view.View r4) {
                /*
                    r3 = this;
                    com.luck.picture.lib.entity.LocalMedia r4 = r2
                    boolean r4 = r4.isMaxSelectEnabledMask()
                    if (r4 != 0) goto L7b
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.adapter.PictureImageGridAdapter$OnItemClickListener r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.access$000(r4)
                    if (r4 != 0) goto L11
                    goto L7b
                L11:
                    com.luck.picture.lib.entity.LocalMedia r4 = r2
                    java.lang.String r4 = r4.getMimeType()
                    boolean r4 = com.luck.picture.lib.config.PictureMimeType.isHasImage(r4)
                    r0 = 1
                    if (r4 == 0) goto L26
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.config.PictureSelectionConfig r4 = r4.config
                    boolean r4 = r4.isEnablePreviewImage
                    if (r4 != 0) goto L60
                L26:
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.config.PictureSelectionConfig r4 = r4.config
                    boolean r4 = r4.isDirectReturnSingle
                    if (r4 != 0) goto L60
                    com.luck.picture.lib.entity.LocalMedia r4 = r2
                    java.lang.String r4 = r4.getMimeType()
                    boolean r4 = com.luck.picture.lib.config.PictureMimeType.isHasVideo(r4)
                    if (r4 == 0) goto L46
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.config.PictureSelectionConfig r4 = r4.config
                    boolean r1 = r4.isEnablePreviewVideo
                    if (r1 != 0) goto L60
                    int r4 = r4.selectionMode
                    if (r4 == r0) goto L60
                L46:
                    com.luck.picture.lib.entity.LocalMedia r4 = r2
                    java.lang.String r4 = r4.getMimeType()
                    boolean r4 = com.luck.picture.lib.config.PictureMimeType.isHasAudio(r4)
                    if (r4 == 0) goto L5f
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.config.PictureSelectionConfig r4 = r4.config
                    boolean r1 = r4.isEnablePreviewAudio
                    if (r1 != 0) goto L60
                    int r4 = r4.selectionMode
                    if (r4 != r0) goto L5f
                    goto L60
                L5f:
                    r0 = 0
                L60:
                    if (r0 == 0) goto L74
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    com.luck.picture.lib.adapter.PictureImageGridAdapter$OnItemClickListener r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.access$000(r4)
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r0 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    android.widget.TextView r0 = r0.tvCheck
                    int r1 = r3
                    com.luck.picture.lib.entity.LocalMedia r2 = r2
                    r4.onItemClick(r0, r1, r2)
                    goto L7b
                L74:
                    com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder r4 = com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.this
                    android.view.View r4 = r4.btnCheck
                    r4.performClick()
                L7b:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.luck.picture.lib.adapter.holder.BaseRecyclerMediaHolder.AnonymousClass3.onClick(android.view.View):void");
            }
        });
    }

    public void setOnItemClickListener(PictureImageGridAdapter.OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public BaseRecyclerMediaHolder(@NonNull View view, PictureSelectionConfig pictureSelectionConfig) {
        super(view);
        this.config = pictureSelectionConfig;
        Context context = view.getContext();
        this.mContext = context;
        this.defaultColorFilter = StyleUtils.getColorFilter(context, R.color.ps_color_20);
        this.selectColorFilter = StyleUtils.getColorFilter(this.mContext, R.color.ps_color_80);
        this.maskWhiteColorFilter = StyleUtils.getColorFilter(this.mContext, R.color.ps_color_half_white);
        SelectMainStyle selectMainStyle = PictureSelectionConfig.selectorStyle.getSelectMainStyle();
        this.isSelectNumberStyle = selectMainStyle.isSelectNumberStyle();
        this.ivPicture = (ImageView) view.findViewById(R.id.ivPicture);
        this.tvCheck = (TextView) view.findViewById(R.id.tvCheck);
        this.btnCheck = view.findViewById(R.id.btnCheck);
        if (pictureSelectionConfig.selectionMode == 1 && pictureSelectionConfig.isDirectReturnSingle) {
            this.tvCheck.setVisibility(8);
            this.btnCheck.setVisibility(8);
        } else {
            this.tvCheck.setVisibility(0);
            this.btnCheck.setVisibility(0);
        }
        int adapterSelectTextSize = selectMainStyle.getAdapterSelectTextSize();
        if (StyleUtils.checkSizeValidity(adapterSelectTextSize)) {
            this.tvCheck.setTextSize(adapterSelectTextSize);
        }
        int adapterSelectTextColor = selectMainStyle.getAdapterSelectTextColor();
        if (StyleUtils.checkStyleValidity(adapterSelectTextColor)) {
            this.tvCheck.setTextColor(adapterSelectTextColor);
        }
        int selectBackground = selectMainStyle.getSelectBackground();
        if (StyleUtils.checkStyleValidity(selectBackground)) {
            this.tvCheck.setBackgroundResource(selectBackground);
        }
        int[] adapterSelectStyleGravity = selectMainStyle.getAdapterSelectStyleGravity();
        if (StyleUtils.checkArrayValidity(adapterSelectStyleGravity)) {
            if (this.tvCheck.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.tvCheck.getLayoutParams()).removeRule(21);
                for (int i2 : adapterSelectStyleGravity) {
                    ((RelativeLayout.LayoutParams) this.tvCheck.getLayoutParams()).addRule(i2);
                }
            }
            if (this.btnCheck.getLayoutParams() instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) this.btnCheck.getLayoutParams()).removeRule(21);
                for (int i3 : adapterSelectStyleGravity) {
                    ((RelativeLayout.LayoutParams) this.btnCheck.getLayoutParams()).addRule(i3);
                }
            }
            int adapterSelectClickArea = selectMainStyle.getAdapterSelectClickArea();
            if (StyleUtils.checkSizeValidity(adapterSelectClickArea)) {
                ViewGroup.LayoutParams layoutParams = this.btnCheck.getLayoutParams();
                layoutParams.width = adapterSelectClickArea;
                layoutParams.height = adapterSelectClickArea;
            }
        }
    }
}
