package com.psychiatrygarden.activity.circleactivity;

import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.hutool.core.lang.RegexPool;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.Glide;
import com.psychiatrygarden.activity.circleactivity.PushCircleAdp;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.URLImageParser;
import com.psychiatrygarden.utils.VerticalImageSpan;
import com.yikaobang.yixue.R;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class PushCirclesListAdapter extends BaseAdapter {
    private PushCircleAdp.OnChooseItemLisenter chooseItemLisenter;
    private boolean isDetail;
    private boolean isEdit;
    private List<CirclrListBean.DataBean> mDataBeans;
    private String searchContent;

    public static class CirclesViewHolder {
        ImageView btnDel;
        TextView count;
        ImageView mImgSelected;
        ImageView mImgTips;
        TextView time;
        TextView title;

        public CirclesViewHolder(View view) {
            this.mImgSelected = (ImageView) view.findViewById(R.id.iv_select);
            this.btnDel = (ImageView) view.findViewById(R.id.btn_del);
            this.mImgTips = (ImageView) view.findViewById(R.id.img_tips);
            this.title = (TextView) view.findViewById(R.id.tv_name);
            this.time = (TextView) view.findViewById(R.id.tv_time);
            this.count = (TextView) view.findViewById(R.id.tv_count);
        }
    }

    public static abstract class OnChooseItemLisenter {
        public abstract void onDelChoosed(int pos, CirclrListBean.DataBean item);

        public abstract void onItemChoosed(int pos, CirclrListBean.DataBean item);
    }

    public PushCirclesListAdapter(List<CirclrListBean.DataBean> dataBeans, boolean isEdit, boolean isDetail) {
        this.mDataBeans = dataBeans;
        this.isEdit = isEdit;
        this.isDetail = isDetail;
    }

    private void getImageData(StringBuffer stringBuffer, TextView mTextView) {
        try {
            float textSize = mTextView.getPaint().getTextSize();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringBuffer.toString());
            Matcher matcher = Pattern.compile("\\[[^\\]]+\\]").matcher(stringBuffer.toString());
            while (matcher.find()) {
                String strGroup = matcher.group();
                if (strGroup.contains("http")) {
                    spannableStringBuilder.setSpan(new VerticalImageSpan(new URLImageParser(mTextView, mTextView.getContext(), (int) textSize).getDrawable(strGroup.substring(1, strGroup.length() - 1))), matcher.start(), matcher.end(), 33);
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(stringBuffer);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$0(int i2, CirclrListBean.DataBean dataBean, View view) {
        PushCircleAdp.OnChooseItemLisenter onChooseItemLisenter = this.chooseItemLisenter;
        if (onChooseItemLisenter != null) {
            onChooseItemLisenter.onItemChoosed(i2, dataBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getView$1(int i2, CirclrListBean.DataBean dataBean, View view) {
        PushCircleAdp.OnChooseItemLisenter onChooseItemLisenter = this.chooseItemLisenter;
        if (onChooseItemLisenter != null) {
            onChooseItemLisenter.onDelChoosed(i2, dataBean);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        List<CirclrListBean.DataBean> list = this.mDataBeans;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        List<CirclrListBean.DataBean> list = this.mDataBeans;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.mDataBeans.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        String id;
        List<CirclrListBean.DataBean> list = this.mDataBeans;
        if (list == null || list.isEmpty() || (id = this.mDataBeans.get(position).getId()) == null || !id.matches(RegexPool.NUMBERS)) {
            return 0L;
        }
        return Long.parseLong(id);
    }

    @Override // android.widget.Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        CirclesViewHolder circlesViewHolder;
        final CirclrListBean.DataBean dataBean = this.mDataBeans.get(position);
        int i2 = this.mDataBeans.size() <= 10 ? R.layout.item_push_circle : R.layout.item_push_circle_detail;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), i2, null);
            circlesViewHolder = new CirclesViewHolder(convertView);
            convertView.setTag(circlesViewHolder);
        } else {
            circlesViewHolder = (CirclesViewHolder) convertView.getTag();
        }
        if (this.mDataBeans.size() <= 10) {
            circlesViewHolder.time.setText(dataBean.getComment_time());
            circlesViewHolder.count.setText(dataBean.getComment_count() + "评论");
            StringBuffer stringBuffer = new StringBuffer();
            if (dataBean.getIcon_img() == null || dataBean.getIcon_img().size() <= 0) {
                circlesViewHolder.title.setText(dataBean.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", ""));
            } else {
                String strReplaceAll = dataBean.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", "");
                if (!(strReplaceAll + position).equals(circlesViewHolder.title.getTag())) {
                    for (int i3 = 0; i3 < dataBean.getIcon_img().size(); i3++) {
                        stringBuffer.append(StrPool.BRACKET_START);
                        stringBuffer.append(dataBean.getIcon_img().get(i3));
                        stringBuffer.append(StrPool.BRACKET_END);
                    }
                    stringBuffer.append(strReplaceAll);
                    getImageData(stringBuffer, circlesViewHolder.title);
                }
            }
            circlesViewHolder.title.setTag(dataBean.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", "") + position);
            circlesViewHolder.mImgSelected.setSelected(dataBean.isSelected());
            if (this.isDetail) {
                circlesViewHolder.mImgSelected.setVisibility(8);
                circlesViewHolder.btnDel.setVisibility(8);
            } else {
                circlesViewHolder.mImgSelected.setVisibility(this.isEdit ? 8 : 0);
                circlesViewHolder.btnDel.setVisibility(this.isEdit ? 0 : 8);
                if (dataBean.getIcon_img() != null && dataBean.getIcon_img().size() > 0) {
                    Glide.with(parent.getContext()).load((Object) GlideUtils.generateUrl(dataBean.getIcon_img().get(0))).into(circlesViewHolder.mImgTips);
                }
            }
            circlesViewHolder.mImgSelected.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.g3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11534c.lambda$getView$0(position, dataBean, view);
                }
            });
            circlesViewHolder.btnDel.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.h3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f11541c.lambda$getView$1(position, dataBean, view);
                }
            });
        } else {
            StringBuffer stringBuffer2 = new StringBuffer();
            if (dataBean.getIcon_img() == null || dataBean.getIcon_img().size() <= 0) {
                circlesViewHolder.title.setText(dataBean.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", ""));
            } else {
                String strReplaceAll2 = dataBean.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", "");
                if (!(strReplaceAll2 + position).equals(circlesViewHolder.title.getTag())) {
                    for (int i4 = 0; i4 < dataBean.getIcon_img().size(); i4++) {
                        stringBuffer2.append(StrPool.BRACKET_START);
                        stringBuffer2.append(dataBean.getIcon_img().get(i4));
                        stringBuffer2.append(StrPool.BRACKET_END);
                    }
                    stringBuffer2.append(strReplaceAll2);
                    getImageData(stringBuffer2, circlesViewHolder.title);
                }
            }
            circlesViewHolder.title.setTag(dataBean.getTitle().replaceAll("<font.*?>", "").replaceAll("</font>", "") + position);
        }
        return convertView;
    }

    public void setOnItemChoosedLisenter(PushCircleAdp.OnChooseItemLisenter lisenter) {
        this.chooseItemLisenter = lisenter;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
