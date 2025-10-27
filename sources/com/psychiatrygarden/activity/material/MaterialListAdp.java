package com.psychiatrygarden.activity.material;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import cn.hutool.core.text.StrPool;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.psychiatrygarden.bean.MaterialListBean;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class MaterialListAdp extends BaseQuickAdapter<MaterialListBean.MaterialListData, BaseViewHolder> {
    private String searchContent;
    private boolean showLine;

    public MaterialListAdp(boolean showLine) {
        super(R.layout.item_material_list);
        this.showLine = showLine;
    }

    private void getImageData(String content, TextView mTextView) {
        try {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content.toString());
            String str = this.searchContent;
            if (str != null && !"".equals(str)) {
                for (String str2 : this.searchContent.split("\\s+")) {
                    String strReplace = str2.replace("\\s+", "");
                    if (!TextUtils.isEmpty(strReplace)) {
                        Matcher matcher = Pattern.compile(StrPool.BRACKET_START + strReplace + StrPool.BRACKET_END, 2).matcher(spannableStringBuilder);
                        while (matcher.find()) {
                            spannableStringBuilder.setSpan(new TextAppearanceSpan(null, 0, 0, getContext().getResources().getColorStateList(R.color.app_theme_red), null), matcher.start(0), matcher.end(0), 34);
                        }
                    }
                }
            }
            mTextView.setText(spannableStringBuilder);
        } catch (Exception e2) {
            e2.printStackTrace();
            mTextView.setText(content);
        }
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(@NonNull BaseViewHolder helper, MaterialListBean.MaterialListData item) {
        ImageView imageView = (ImageView) helper.getView(R.id.iv_file_type);
        ImageView imageView2 = (ImageView) helper.getView(R.id.ic_lock);
        TextView textView = (TextView) helper.getView(R.id.tv_file_name);
        TextView textView2 = (TextView) helper.getView(R.id.tv_author);
        TextView textView3 = (TextView) helper.getView(R.id.tv_file_size);
        TextView textView4 = (TextView) helper.getView(R.id.tv_download_count);
        helper.getView(R.id.line).setVisibility(this.showLine ? 0 : 8);
        textView4.setText(item.getDownload_count() + "次下载");
        if (item.getFile_type().equals("2")) {
            textView2.setVisibility(8);
            imageView2.setVisibility(8);
            textView3.setText(item.getFile_count() + "个资料");
        } else {
            textView3.setText(item.getSize());
            if (TextUtils.isEmpty(item.getIs_rights()) || !item.getIs_rights().equals("1")) {
                imageView2.setVisibility(0);
            } else {
                imageView2.setVisibility(8);
            }
            if (TextUtils.isEmpty(item.getNickname())) {
                textView2.setVisibility(8);
            } else {
                textView2.setVisibility(0);
                textView2.setText(item.getNickname());
            }
        }
        GlideApp.with(getContext()).load((Object) GlideUtils.generateUrl(item.getIcon())).into(imageView);
        getImageData(item.getTitle() + item.getType_name(), textView);
    }
}
