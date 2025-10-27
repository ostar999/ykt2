package com.psychiatrygarden.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes5.dex */
public class StemMockTestAdapter extends PagerAdapter {
    private List<ExesQuestionBean> data;
    private final Handler handler;
    private Context mContext;
    private StemQuestionSwitchListener mListener;

    public class OptionsItemClickListener implements OnItemClickListener {
        private final int multiselection;
        private final int parentPosition;
        private final List<ExesQuestionBean.OptionBean> questItems;

        public OptionsItemClickListener(int multiselection, int parentPosition) {
            this.multiselection = multiselection;
            this.parentPosition = parentPosition;
            this.questItems = ((ExesQuestionBean) StemMockTestAdapter.this.data.get(parentPosition)).getOption();
        }

        @Override // com.chad.library.adapter.base.listener.OnItemClickListener
        public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
            int i2 = 0;
            if ((this.multiselection > 1 && !TextUtils.equals(((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).getType(), Constants.VIA_REPORT_TYPE_DATALINE)) || TextUtils.equals(((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).getType(), Constants.VIA_ACT_TYPE_NINETEEN) || TextUtils.equals(((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).getType(), Constants.VIA_SHARE_TYPE_PUBLISHVIDEO)) {
                this.questItems.get(position).setType(Objects.equals(this.questItems.get(position).getType(), "1") ? "0" : "1");
                StringBuilder sb = new StringBuilder();
                int i3 = 0;
                while (true) {
                    if (i3 >= this.questItems.size()) {
                        break;
                    }
                    if (this.questItems.get(i3).getType() != null && this.questItems.get(i3).getType().equals("1")) {
                        sb.append(this.questItems.get(i3).getKey().trim());
                        sb.append(i3 >= this.questItems.size() - 1 ? "" : ",");
                    }
                    i3++;
                }
                char[] charArray = ((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).getAnswer().replace(",", "").toCharArray();
                char[] charArray2 = sb.toString().replace(",", "").toCharArray();
                Arrays.sort(charArray);
                Arrays.sort(charArray2);
                String string = sb.toString();
                if (!TextUtils.isEmpty(string) && string.endsWith(",")) {
                    String strReplace = string.replace(",", "");
                    StringBuilder sb2 = new StringBuilder();
                    while (i2 < strReplace.length()) {
                        sb2.append(strReplace.charAt(i2));
                        sb2.append(i2 != strReplace.length() - 1 ? "," : "");
                        i2++;
                    }
                    string = sb2.toString();
                }
                ((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).setOwnAns(string);
                ((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).setIsRight(TextUtils.equals(new String(charArray), new String(charArray2)) ? "1" : "0");
            } else {
                int i4 = 0;
                for (int i5 = 0; i5 < this.questItems.size(); i5++) {
                    if (this.questItems.get(i5).getType().equals("1")) {
                        i4++;
                    }
                }
                if (i4 <= 0) {
                    this.questItems.get(position).setType("1");
                    ((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).setOwnAns(this.questItems.get(position).getKey());
                    ((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).setIsRight(this.questItems.get(position).getKey().equals(((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).getAnswer()) ? "1" : "0");
                    if (StemMockTestAdapter.this.mListener != null) {
                        StemMockTestAdapter.this.mListener.next(((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).getActualStemIndex());
                    }
                } else if (this.questItems.get(position).getType().equals("1")) {
                    this.questItems.get(position).setType("0");
                    ((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).setOwnAns("");
                    ((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).setIsRight("0");
                } else {
                    while (i2 < this.questItems.size()) {
                        this.questItems.get(i2).setType("0");
                        i2++;
                    }
                    this.questItems.get(position).setType("1");
                    ((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).setOwnAns(this.questItems.get(position).getKey());
                    ((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).setIsRight(this.questItems.get(position).getKey().equals(((ExesQuestionBean) StemMockTestAdapter.this.data.get(this.parentPosition)).getAnswer()) ? "1" : "0");
                }
            }
            if (StemMockTestAdapter.this.mListener != null) {
                StemMockTestAdapter.this.mListener.optionClickListener(this.parentPosition);
            }
            adapter.notifyDataSetChanged();
        }
    }

    public interface StemQuestionSwitchListener {
        void next(int current);

        void optionClickListener(int position);
    }

    public class SubmitAnswerClick implements View.OnClickListener {
        private final int position;
        private final List<ExesQuestionBean> questionBeans;

        public SubmitAnswerClick(List<ExesQuestionBean> questionBeans, int position) {
            this.questionBeans = questionBeans;
            this.position = position;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            List<ExesQuestionBean.OptionBean> option = this.questionBeans.get(this.position).getOption();
            boolean z2 = true;
            boolean z3 = TextUtils.equals(this.questionBeans.get(this.position).getType(), Constants.VIA_ACT_TYPE_NINETEEN) || TextUtils.equals(this.questionBeans.get(this.position).getType(), Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
            StringBuilder sb = new StringBuilder();
            if (option == null || option.size() <= 0) {
                return;
            }
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i2 >= option.size()) {
                    break;
                }
                if (option.get(i2).getType() != null && option.get(i2).getType().equals("1")) {
                    sb.append(option.get(i2).getKey().trim());
                    sb.append(i2 >= option.size() - 1 ? "" : ",");
                    i3++;
                }
                i2++;
            }
            if (i3 <= 0) {
                NewToast.showShort(StemMockTestAdapter.this.mContext.getApplicationContext(), "请选择答案", 0).show();
                return;
            }
            if (!z3 && i3 < 2) {
                NewToast.showShort(StemMockTestAdapter.this.mContext.getApplicationContext(), "此题为多选题", 0).show();
                return;
            }
            this.questionBeans.get(this.position).setOwnAns(sb.toString());
            String strTrim = (TextUtils.isEmpty(this.questionBeans.get(this.position).getAnswer()) ? "" : this.questionBeans.get(this.position).getAnswer()).replace(",", "").trim();
            String strTrim2 = sb.toString().replace(",", "").trim();
            char[] charArray = strTrim.toCharArray();
            char[] charArray2 = strTrim2.toCharArray();
            if (!TextUtils.isEmpty(this.questionBeans.get(this.position).getAnswer()) && !TextUtils.isEmpty(sb.toString())) {
                z2 = false;
            }
            Arrays.sort(charArray);
            Arrays.sort(charArray2);
            this.questionBeans.get(this.position).setIsRight((z2 || !TextUtils.equals(new String(charArray), new String(charArray2))) ? "0" : "1");
            if (StemMockTestAdapter.this.mListener != null) {
                StemMockTestAdapter.this.mListener.next(this.position);
            }
        }
    }

    public StemMockTestAdapter(@Nullable List<ExesQuestionBean> data, Handler handler, StemQuestionSwitchListener listener) {
        this.data = data;
        this.handler = handler;
        this.mListener = listener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$instantiateItem$0(View view) {
        try {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(view.getContext()).setSingleSrcView((ImageView) view, view.getTag()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        List<ExesQuestionBean> list = this.data;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        String str;
        List<ExesQuestionBean.OptionBean> option;
        final View viewInflate = View.inflate(container.getContext(), R.layout.item_mock_test, null);
        this.mContext = container.getContext();
        ExesQuestionBean exesQuestionBean = this.data.get(position);
        TextView textView = (TextView) viewInflate.findViewById(R.id.questiondetails_tv_childTitle);
        Locale locale = Locale.CHINA;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(exesQuestionBean.getActualStemIndex() + 1);
        if (TextUtils.isEmpty(exesQuestionBean.getShow_number()) || !exesQuestionBean.getShow_number().equals("1")) {
            str = "";
        } else {
            str = exesQuestionBean.getQuestion_num() + " ";
        }
        objArr[1] = str;
        objArr[2] = exesQuestionBean.getTitle();
        textView.setText(String.format(locale, "%d.%s%s", objArr));
        if (TextUtils.isEmpty(exesQuestionBean.getPublic_number()) && !TextUtils.isEmpty(exesQuestionBean.getPublic_title())) {
            viewInflate.findViewById(R.id.lineviewtype).setVisibility(0);
            textView.setText(exesQuestionBean.getPublic_title() + "\n" + ((Object) textView.getText()));
        }
        viewInflate.findViewById(R.id.questiondetails_btn_commentNum).setVisibility(8);
        final ImageView imageView = (ImageView) viewInflate.findViewById(R.id.show_imgView);
        RecyclerView recyclerView = (RecyclerView) viewInflate.findViewById(R.id.rvOptions);
        Button button = (Button) viewInflate.findViewById(R.id.questiondetails_btn_pushAnswer);
        ((LinearLayout) viewInflate.findViewById(R.id.questiondetails_layout_diff)).removeAllViews();
        TextView textView2 = new TextView(container.getContext());
        textView2.setTextSize(12.0f);
        textView2.setTextColor(container.getContext().getResources().getColor(R.color.gray_font));
        this.data.get(position).setDoStartDuration(System.currentTimeMillis() / 1000);
        if (TextUtils.isEmpty(exesQuestionBean.getTitle_img())) {
            imageView.setVisibility(8);
        } else {
            imageView.setVisibility(0);
            imageView.setTag(exesQuestionBean.getTitle_img());
            Glide.with(imageView.getContext()).asBitmap().placeholder(R.drawable.plugin_camera_no_pictures).error(R.drawable.plugin_camera_no_pictures).load(exesQuestionBean.getTitle_img()).into((RequestBuilder) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.adapter.StemMockTestAdapter.1
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                    onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                }

                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int width = resource.getWidth();
                    resource.getHeight();
                    int i2 = viewInflate.getContext().getResources().getDisplayMetrics().widthPixels;
                    if (width >= i2) {
                        width = i2 - CommonUtil.dip2px(viewInflate.getContext(), 30.0f);
                    }
                    int iDip2px = CommonUtil.dip2px(viewInflate.getContext(), 120.0f);
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    layoutParams.width = width;
                    layoutParams.height = iDip2px;
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageBitmap(resource);
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.se
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    StemMockTestAdapter.lambda$instantiateItem$0(view);
                }
            });
        }
        if (TextUtils.equals(Constants.VIA_REPORT_TYPE_DATALINE, exesQuestionBean.getType())) {
            option = new ArrayList<>();
            ExesQuestionBean.OptionBean optionBean = new ExesQuestionBean.OptionBean();
            ExesQuestionBean.OptionBean optionBean2 = new ExesQuestionBean.OptionBean();
            optionBean.setKey("正确");
            optionBean2.setKey("错误");
            option.add(optionBean);
            option.add(optionBean2);
            exesQuestionBean.setOption(option);
        } else {
            option = exesQuestionBean.getOption();
        }
        MockTestOptionAdapter mockTestOptionAdapter = new MockTestOptionAdapter(option);
        recyclerView.setAdapter(mockTestOptionAdapter);
        mockTestOptionAdapter.setOnItemClickListener(new OptionsItemClickListener(exesQuestionBean.getAnswer().length(), position));
        button.setVisibility((TextUtils.equals(exesQuestionBean.getType(), Constants.VIA_REPORT_TYPE_DATALINE) || !(exesQuestionBean.getAnswer().length() > 1 || TextUtils.equals(exesQuestionBean.getType(), Constants.VIA_ACT_TYPE_NINETEEN) || TextUtils.equals(exesQuestionBean.getType(), Constants.VIA_SHARE_TYPE_PUBLISHVIDEO))) ? 8 : 0);
        button.setOnClickListener(new SubmitAnswerClick(this.data, position));
        String answer = TextUtils.isEmpty(exesQuestionBean.getAnswer()) ? "" : exesQuestionBean.getAnswer();
        ((TextView) viewInflate.findViewById(R.id.questiondetails_tv_Answer)).setText(Html.fromHtml("<font  color='#38456D'>[正确答案]  </font>" + answer.trim().replace(",", "").trim()));
        String strTrim = TextUtils.isEmpty(exesQuestionBean.getRestore()) ? "" : exesQuestionBean.getRestore().trim();
        if (TextUtils.isEmpty(strTrim)) {
            viewInflate.findViewById(R.id.questiondetails_tv_content_ques).setVisibility(8);
        } else {
            viewInflate.findViewById(R.id.questiondetails_tv_content_ques).setVisibility(0);
            ((TextView) viewInflate.findViewById(R.id.questiondetails_tv_content_ques)).setText(Html.fromHtml("<font  color='#dd594a'>[考点还原]  </font>" + strTrim));
        }
        ((TextView) viewInflate.findViewById(R.id.questiondetails_tv_contents)).setText(Html.fromHtml("<font  color='#dd594a'>[答案解析]  </font>" + exesQuestionBean.getExplain()));
        container.addView(viewInflate);
        return viewInflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
