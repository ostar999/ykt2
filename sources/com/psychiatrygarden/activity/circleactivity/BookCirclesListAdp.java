package com.psychiatrygarden.activity.circleactivity;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.hutool.core.lang.RegexPool;
import com.makeramen.roundedimageview.RoundedImageView;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class BookCirclesListAdp extends BaseAdapter {
    private OnChooseItemLisenter chooseItemLisenter;
    private boolean isDetail;
    private boolean isEdit;
    private List<PushBookData> mDataBeans;
    private String searchContent;

    public static class CirclesViewHolder {
        RoundedImageView imgPic;
        ImageView mBtnDel;
        RelativeLayout mLyItem;
        TextView mTvAuthor;
        TextView mTvGrade;
        TextView mTvGradeLabel;
        TextView mTvTitle;

        public CirclesViewHolder(View view) {
            this.imgPic = (RoundedImageView) view.findViewById(R.id.img_pic);
            this.mTvTitle = (TextView) view.findViewById(R.id.tv_title);
            this.mTvAuthor = (TextView) view.findViewById(R.id.tv_author);
            this.mTvGrade = (TextView) view.findViewById(R.id.tv_grade);
            this.mLyItem = (RelativeLayout) view.findViewById(R.id.ly_item);
            this.mBtnDel = (ImageView) view.findViewById(R.id.btn_del);
            this.mTvGradeLabel = (TextView) view.findViewById(R.id.tv_label);
        }
    }

    public static abstract class OnChooseItemLisenter {
        public abstract void onDelChoosed(int pos, PushBookData item);

        public abstract void onItemChoosed(int pos, PushBookData item);
    }

    public BookCirclesListAdp(List<PushBookData> dataBeans, boolean isEdit, boolean isDetail) {
        this.mDataBeans = dataBeans;
        this.isEdit = isEdit;
        this.isDetail = isDetail;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$0(int i2, PushBookData pushBookData, View view) {
        OnChooseItemLisenter onChooseItemLisenter = this.chooseItemLisenter;
        if (onChooseItemLisenter != null) {
            onChooseItemLisenter.onDelChoosed(i2, pushBookData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$1(int i2, PushBookData pushBookData, View view) {
        OnChooseItemLisenter onChooseItemLisenter = this.chooseItemLisenter;
        if (onChooseItemLisenter != null) {
            onChooseItemLisenter.onItemChoosed(i2, pushBookData);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<PushBookData> list = this.mDataBeans;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        List<PushBookData> list = this.mDataBeans;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.mDataBeans.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        String id;
        List<PushBookData> list = this.mDataBeans;
        if (list == null || list.isEmpty() || (id = this.mDataBeans.get(position).getId()) == null || !id.matches(RegexPool.NUMBERS)) {
            return 0L;
        }
        return Long.parseLong(id);
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        CirclesViewHolder circlesViewHolder;
        final PushBookData pushBookData = this.mDataBeans.get(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_ebook_circleinfo, null);
            circlesViewHolder = new CirclesViewHolder(convertView);
            convertView.setTag(circlesViewHolder);
        } else {
            circlesViewHolder = (CirclesViewHolder) convertView.getTag();
        }
        circlesViewHolder.mTvTitle.setText(pushBookData.getTitle());
        circlesViewHolder.mTvAuthor.setText(pushBookData.getAuthor());
        if (TextUtils.isEmpty(pushBookData.getGrade()) || pushBookData.getGrade().equals("0")) {
            circlesViewHolder.mTvGrade.setVisibility(8);
            circlesViewHolder.mTvGradeLabel.setText("暂无评分");
            TextView textView = circlesViewHolder.mTvGradeLabel;
            textView.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(textView.getContext()) == 1 ? "#606A8A" : "#7B7E83"));
        } else {
            circlesViewHolder.mTvGrade.setVisibility(0);
            circlesViewHolder.mTvGrade.setText(pushBookData.getGrade());
            circlesViewHolder.mTvGradeLabel.setText("分");
            TextView textView2 = circlesViewHolder.mTvGradeLabel;
            textView2.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(textView2.getContext()) == 1 ? "#C49231" : "#FFC100"));
        }
        GlideUtils.loadImage(circlesViewHolder.imgPic.getContext(), pushBookData.getThumbnail(), circlesViewHolder.imgPic);
        if (this.isEdit) {
            circlesViewHolder.mLyItem.setBackgroundResource(R.drawable.shape_white_corners_12);
            circlesViewHolder.mBtnDel.setVisibility(0);
        } else {
            circlesViewHolder.mLyItem.setBackgroundResource(R.drawable.circle_item_ebook_bg);
            circlesViewHolder.mBtnDel.setVisibility(8);
        }
        circlesViewHolder.mBtnDel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.d
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11503c.lambda$getView$0(position, pushBookData, view);
            }
        });
        circlesViewHolder.mLyItem.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.e
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11509c.lambda$getView$1(position, pushBookData, view);
            }
        });
        return convertView;
    }

    public void setOnItemChoosedLisenter(OnChooseItemLisenter lisenter) {
        this.chooseItemLisenter = lisenter;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
