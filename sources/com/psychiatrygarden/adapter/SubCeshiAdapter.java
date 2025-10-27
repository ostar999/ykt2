package com.psychiatrygarden.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.hutool.core.text.StrPool;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.MyNightUtil;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.yikaobang.yixue.R;
import java.util.List;

/* loaded from: classes5.dex */
public class SubCeshiAdapter extends BaseAdapter {
    private Context context;
    private List<ExesQuestionBean.OptionBean> list;
    private String type;

    public class ViewHoder {
        private ImageView img_select;
        private ImageView optionImg;
        private TextView tv_content;

        public ViewHoder(View view) {
            this.optionImg = (ImageView) view.findViewById(R.id.optionimg);
            this.img_select = (ImageView) view.findViewById(R.id.QuestionOptions_item_img_select);
            this.tv_content = (TextView) view.findViewById(R.id.QuestionOptions_item_tv_content);
        }
    }

    public SubCeshiAdapter(Context context, List<ExesQuestionBean.OptionBean> list) {
        this.context = context;
        this.list = list;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getView$0(View view) {
        try {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(view.getContext()).setSingleSrcView((ImageView) view, view.getTag()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.list.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder viewHoder;
        ExesQuestionBean.OptionBean optionBean = this.list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_mock_test_option, (ViewGroup) null);
            viewHoder = new ViewHoder(convertView);
            convertView.setTag(viewHoder);
        } else {
            viewHoder = (ViewHoder) convertView.getTag();
        }
        if (optionBean.getTitle() != null) {
            viewHoder.tv_content.setText(this.list.get(position).getKey() + StrPool.DOT + this.list.get(position).getTitle());
        }
        this.type = this.list.get(position).getType();
        viewHoder.optionImg.setVisibility(TextUtils.isEmpty(optionBean.getImg()) ? 8 : 0);
        if (!TextUtils.isEmpty(optionBean.getImg())) {
            GlideUtils.loadImage(convertView.getContext(), optionBean.getImg(), viewHoder.optionImg);
            viewHoder.optionImg.setTag(optionBean.getImg());
            viewHoder.optionImg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.ye
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SubCeshiAdapter.lambda$getView$0(view);
                }
            });
        }
        String str = this.type;
        if (str == null || str.equals("0")) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                viewHoder.tv_content.setTextColor(MyNightUtil.getColor(this.context, R.color.black));
                viewHoder.img_select.setImageDrawable(MyNightUtil.getDrawable(this.context, R.drawable.icon_options_select_no));
            } else {
                viewHoder.tv_content.setTextColor(MyNightUtil.getColor(this.context, R.color.question_color_night));
                viewHoder.img_select.setImageDrawable(MyNightUtil.getDrawable(this.context, R.drawable.icon_options_select_no_night));
            }
        } else if (this.type.equals("1")) {
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                viewHoder.tv_content.setTextColor(MyNightUtil.getColor(this.context, R.color.black));
                viewHoder.img_select.setImageDrawable(MyNightUtil.getDrawable(this.context, R.drawable.icon_options_select_yes));
            } else {
                viewHoder.tv_content.setTextColor(MyNightUtil.getColor(this.context, R.color.question_color_night));
                viewHoder.img_select.setImageDrawable(MyNightUtil.getDrawable(this.context, R.drawable.icon_options_select_yes_night));
            }
        }
        return convertView;
    }

    public void upData(List<ExesQuestionBean.OptionBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
