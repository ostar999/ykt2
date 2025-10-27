package com.psychiatrygarden.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import cn.webdemo.com.supporfragment.tablayout.MagicIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lxj.xpopup.core.PopupInfo;
import com.psychiatrygarden.adapter.StemMockTestAdapter;
import com.psychiatrygarden.bean.ExesQuestionBean;
import com.psychiatrygarden.bean.QuestionIndexBean;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ImageLoaderUtilsCustom;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.utils.StatusBarUtil;
import com.psychiatrygarden.widget.ImageViewerPopupViewCustom;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/* loaded from: classes5.dex */
public class MockTestAdapter extends PagerAdapter {
    private int curPos;
    private final List<ExesQuestionBean> data;
    private final SparseArray<Boolean> expandStatusArray;
    private final Handler handler;
    private int lineHeight;
    private int maxTopHeight;
    float offsetDiff;
    private ArrayMap<String, List<ExesQuestionBean>> shareStemMap;
    float startRawY;
    private final ArrayMap<Integer, View> map = new ArrayMap<>();
    private final SparseArray<Integer> titleHeightArr = new SparseArray<>();
    private final SparseArray<Integer> topHeightArr = new SparseArray<>();
    private final SparseArray<Integer> dragViewHeightArr = new SparseArray<>();
    private final SparseArray<Integer> stemViewHeightArr = new SparseArray<>();
    private final SparseArray<Boolean> singleLineTitleArr = new SparseArray<>();
    private final SparseArray<Boolean> scrollStateArr = new SparseArray<>();

    public class OptionsItemClickListener implements OnItemClickListener {
        private final int multiselection;
        private final int parentPosition;
        private final List<ExesQuestionBean.OptionBean> questItems;

        public OptionsItemClickListener(int multiselection, int parentPosition) {
            this.multiselection = multiselection;
            this.parentPosition = parentPosition;
            this.questItems = MockTestAdapter.this.getData().get(parentPosition).getOption();
        }

        @Override // com.chad.library.adapter.base.listener.OnItemClickListener
        public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
            int i2 = 0;
            if ((this.multiselection > 1 && !TextUtils.equals(MockTestAdapter.this.getData().get(this.parentPosition).getType(), Constants.VIA_REPORT_TYPE_DATALINE)) || TextUtils.equals(MockTestAdapter.this.getData().get(this.parentPosition).getType(), Constants.VIA_ACT_TYPE_NINETEEN) || TextUtils.equals(MockTestAdapter.this.getData().get(this.parentPosition).getType(), Constants.VIA_SHARE_TYPE_PUBLISHVIDEO)) {
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
                char[] charArray = MockTestAdapter.this.getData().get(this.parentPosition).getAnswer().replace(",", "").toCharArray();
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
                MockTestAdapter.this.getData().get(this.parentPosition).setOwnAns(string);
                MockTestAdapter.this.getData().get(this.parentPosition).setIsRight(TextUtils.equals(new String(charArray), new String(charArray2)) ? "1" : "0");
            } else {
                int i4 = 0;
                for (int i5 = 0; i5 < this.questItems.size(); i5++) {
                    if (this.questItems.get(i5).getType().equals("1")) {
                        i4++;
                    }
                }
                if (i4 <= 0) {
                    this.questItems.get(position).setType("1");
                    MockTestAdapter.this.getData().get(this.parentPosition).setOwnAns(this.questItems.get(position).getKey());
                    MockTestAdapter.this.getData().get(this.parentPosition).setIsRight(this.questItems.get(position).getKey().equals(MockTestAdapter.this.getData().get(this.parentPosition).getAnswer()) ? "1" : "0");
                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = this.parentPosition;
                    message.arg2 = MockTestAdapter.this.getData().get(this.parentPosition).getActualStemIndex();
                    MockTestAdapter.this.handler.sendMessageDelayed(message, 200L);
                } else if (this.questItems.get(position).getType().equals("1")) {
                    this.questItems.get(position).setType("0");
                    MockTestAdapter.this.getData().get(this.parentPosition).setOwnAns("");
                    MockTestAdapter.this.getData().get(this.parentPosition).setIsRight("0");
                } else {
                    while (i2 < this.questItems.size()) {
                        this.questItems.get(i2).setType("0");
                        i2++;
                    }
                    this.questItems.get(position).setType("1");
                    MockTestAdapter.this.getData().get(this.parentPosition).setOwnAns(this.questItems.get(position).getKey());
                    MockTestAdapter.this.getData().get(this.parentPosition).setIsRight(this.questItems.get(position).getKey().equals(MockTestAdapter.this.getData().get(this.parentPosition).getAnswer()) ? "1" : "0");
                }
            }
            long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) - MockTestAdapter.this.getData().get(this.parentPosition).getDoStartDuration();
            MockTestAdapter.this.getData().get(this.parentPosition).setDoDuration(jCurrentTimeMillis + "");
            adapter.notifyDataSetChanged();
        }
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
            boolean z2 = TextUtils.equals(this.questionBeans.get(this.position).getType(), Constants.VIA_ACT_TYPE_NINETEEN) || TextUtils.equals(this.questionBeans.get(this.position).getType(), Constants.VIA_SHARE_TYPE_PUBLISHVIDEO);
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
                NewToast.showShort(v2.getContext().getApplicationContext(), "请选择答案", 0).show();
                return;
            }
            if (!z2 && i3 < 2) {
                NewToast.showShort(v2.getContext().getApplicationContext(), "此题为多选题", 0).show();
                return;
            }
            this.questionBeans.get(this.position).setOwnAns(sb.toString());
            String strTrim = (TextUtils.isEmpty(this.questionBeans.get(this.position).getAnswer()) ? "" : this.questionBeans.get(this.position).getAnswer()).replace(",", "").trim();
            String strTrim2 = sb.toString().replace(",", "").trim();
            char[] charArray = strTrim.toCharArray();
            char[] charArray2 = strTrim2.toCharArray();
            boolean z3 = TextUtils.isEmpty(this.questionBeans.get(this.position).getAnswer()) || TextUtils.isEmpty(sb.toString());
            Arrays.sort(charArray);
            Arrays.sort(charArray2);
            this.questionBeans.get(this.position).setIsRight((z3 || !TextUtils.equals(new String(charArray), new String(charArray2))) ? "0" : "1");
            Message message = new Message();
            message.what = 1;
            message.arg2 = this.questionBeans.get(this.position).getActualStemIndex();
            message.arg1 = this.position;
            MockTestAdapter.this.handler.sendMessageDelayed(message, 100L);
        }
    }

    public MockTestAdapter(ArrayMap<String, List<ExesQuestionBean>> shareStemMap, List<ExesQuestionBean> data, Handler handler) {
        ArrayList arrayList = new ArrayList();
        this.data = arrayList;
        this.expandStatusArray = new SparseArray<>();
        this.handler = handler;
        if (shareStemMap != null && shareStemMap.size() > 0) {
            this.shareStemMap = shareStemMap;
        }
        if (data == null || data.size() <= 0) {
            return;
        }
        arrayList.clear();
        arrayList.addAll(data);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void execTopMarinAnimation(int... values) {
        int i2 = values[values.length - 1];
        final View view = this.map.get(Integer.valueOf(this.curPos));
        if (view == null || view.findViewById(R.id.ll_child_question).getTop() == i2) {
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(values);
        valueAnimatorOfInt.setDuration(300L);
        valueAnimatorOfInt.setInterpolator(new LinearInterpolator());
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.adapter.na
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                MockTestAdapter.lambda$execTopMarinAnimation$5(view, valueAnimator);
            }
        });
        valueAnimatorOfInt.start();
    }

    private String getTypeStr(String type, Context context) throws Resources.NotFoundException {
        int i2;
        String[] stringArray = context.getResources().getStringArray(R.array.exam_type);
        if (type == null) {
            return null;
        }
        if (type.equals(Constants.VIA_REPORT_TYPE_DATALINE)) {
            return "判断题";
        }
        if (!type.matches("-?\\d+.?\\d*") || (i2 = Integer.parseInt(type)) < 1) {
            return null;
        }
        int i3 = i2 - 1;
        if (i3 < stringArray.length) {
            return stringArray[i3];
        }
        if (i3 == stringArray.length) {
            return stringArray[stringArray.length - 1];
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$execTopMarinAnimation$5(View view, ValueAnimator valueAnimator) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.findViewById(R.id.ll_child_question).getLayoutParams();
        layoutParams.topMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        view.findViewById(R.id.ll_child_question).setLayoutParams(layoutParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$loadNormalContent$0(View view) {
        try {
            ImageViewerPopupViewCustom xPopupImageLoader = new ImageViewerPopupViewCustom(view.getContext()).setSingleSrcView((ImageView) view, view.getTag()).setXPopupImageLoader(new ImageLoaderUtilsCustom());
            xPopupImageLoader.popupInfo = new PopupInfo();
            xPopupImageLoader.show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadShareStemContent$1(View view, LinearLayout linearLayout) {
        this.dragViewHeightArr.put(((Integer) view.getTag()).intValue(), Integer.valueOf(view.findViewById(R.id.rl_drag_area).getMeasuredHeight()));
        this.stemViewHeightArr.put(((Integer) view.getTag()).intValue(), Integer.valueOf(linearLayout.getMeasuredHeight()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadShareStemContent$2(NestedScrollView nestedScrollView, int i2, int i3, int i4, int i5) {
        int iIntValue = ((Integer) nestedScrollView.getTag()).intValue();
        Boolean bool = this.scrollStateArr.get(iIntValue);
        if (bool == null || !bool.booleanValue()) {
            this.scrollStateArr.put(iIntValue, Boolean.TRUE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) nestedScrollView.getLayoutParams();
            layoutParams.height -= this.lineHeight;
            nestedScrollView.setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadShareStemContent$3(ViewGroup viewGroup, View view, TextView textView, NestedScrollView nestedScrollView, LinearLayout linearLayout) {
        int measuredHeight = viewGroup.getMeasuredHeight();
        this.topHeightArr.put(((Integer) view.getTag()).intValue(), Integer.valueOf(measuredHeight));
        int measuredHeight2 = (view.findViewById(R.id.rl_top_title).getVisibility() == 0 ? view.findViewById(R.id.rl_top_title).getMeasuredHeight() : 0) + view.findViewById(R.id.lineviewtype).getMeasuredHeight();
        if (textView.getLineCount() > 3) {
            measuredHeight2 += (textView.getLineHeight() * 3) + CommonUtil.dip2px(view.getContext(), 10.0f);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.findViewById(R.id.nsl).getLayoutParams();
            layoutParams.height = measuredHeight2;
            nestedScrollView.setLayoutParams(layoutParams);
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams2.topMargin = measuredHeight2;
            linearLayout.setLayoutParams(layoutParams2);
        } else {
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams3.topMargin = measuredHeight + CommonUtil.dip2px(view.getContext(), 10.0f);
            linearLayout.setLayoutParams(layoutParams3);
        }
        this.stemViewHeightArr.put(((Integer) view.getTag()).intValue(), Integer.valueOf(view.findViewById(R.id.ll_stem_content).getMeasuredHeight()));
        boolean z2 = textView.getLineCount() <= 3;
        this.lineHeight = textView.getLineHeight();
        this.titleHeightArr.put(((Integer) view.getTag()).intValue(), Integer.valueOf(measuredHeight2));
        this.singleLineTitleArr.put(((Integer) view.getTag()).intValue(), Boolean.valueOf(z2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$loadShareStemContent$4(View view, NestedScrollView nestedScrollView, View view2, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.startRawY = motionEvent.getRawY();
            return true;
        }
        if (action != 2 || this.singleLineTitleArr.get(((Integer) view.getTag()).intValue()).booleanValue()) {
            return false;
        }
        this.offsetDiff = motionEvent.getRawY() - this.startRawY;
        int iIntValue = this.topHeightArr.get(((Integer) view.getTag()).intValue()).intValue();
        int iIntValue2 = this.stemViewHeightArr.get(((Integer) view.getTag()).intValue()).intValue();
        int iIntValue3 = this.titleHeightArr.get(((Integer) view.getTag()).intValue()).intValue();
        if (motionEvent.getRawY() - this.startRawY > 0.0f) {
            if (view.findViewById(R.id.ll_child_question).getTop() > this.maxTopHeight) {
                return false;
            }
            if (view.findViewById(R.id.nsl).getHeight() < iIntValue2) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.findViewById(R.id.nsl).getLayoutParams();
                layoutParams.height = (int) (layoutParams.height + Math.abs(this.offsetDiff));
                view.findViewById(R.id.nsl).setLayoutParams(layoutParams);
            }
            if (view.findViewById(R.id.ll_child_question).getTop() >= iIntValue + this.lineHeight) {
                view.findViewById(R.id.nsl).setScrollbarFadingEnabled(true);
                this.offsetDiff = 0.0f;
                return false;
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view.findViewById(R.id.ll_child_question).getLayoutParams();
            layoutParams2.topMargin = (int) (layoutParams2.topMargin + this.offsetDiff);
            view.findViewById(R.id.ll_child_question).setLayoutParams(layoutParams2);
            this.startRawY = motionEvent.getRawY();
            return true;
        }
        if (motionEvent.getRawY() - this.startRawY < 0.0f) {
            if (view.findViewById(R.id.ll_child_question).getTop() > iIntValue3) {
                FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) view.findViewById(R.id.ll_child_question).getLayoutParams();
                layoutParams3.topMargin = (int) (layoutParams3.topMargin - Math.abs(this.offsetDiff));
                view.findViewById(R.id.ll_child_question).setLayoutParams(layoutParams3);
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) nestedScrollView.getLayoutParams();
                layoutParams4.height = layoutParams3.topMargin - this.lineHeight;
                nestedScrollView.setLayoutParams(layoutParams4);
                this.startRawY = motionEvent.getRawY();
                return true;
            }
            nestedScrollView.setScrollbarFadingEnabled(false);
            LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) view.findViewById(R.id.nsl).getLayoutParams();
            layoutParams5.height = iIntValue3 - this.lineHeight;
            nestedScrollView.setLayoutParams(layoutParams5);
        }
        return false;
    }

    private void loadNormalContent(@NonNull final View view, ExesQuestionBean item, int position) {
        String str;
        List<ExesQuestionBean.OptionBean> option;
        view.findViewById(R.id.questiondetails_btn_commentNum).setVisibility(8);
        final ImageView imageView = (ImageView) view.findViewById(R.id.show_imgView);
        TextView textView = (TextView) view.findViewById(R.id.questiondetails_tv_childTitle);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvOptions);
        Button button = (Button) view.findViewById(R.id.questiondetails_btn_pushAnswer);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.questiondetails_layout_diff);
        Locale locale = Locale.CHINA;
        Object[] objArr = new Object[3];
        objArr[0] = Integer.valueOf(item.getActualStemIndex() + 1);
        if (TextUtils.isEmpty(item.getShow_number()) || !item.getShow_number().equals("1")) {
            str = "";
        } else {
            str = item.getQuestion_num() + " ";
        }
        objArr[1] = str;
        objArr[2] = item.getTitle();
        textView.setText(String.format(locale, "%d.%s%s", objArr));
        if (TextUtils.isEmpty(item.getPublic_number()) && !TextUtils.isEmpty(item.getPublic_title())) {
            view.findViewById(R.id.lineviewtype).setVisibility(0);
            textView.setText(item.getPublic_title() + "\n" + ((Object) textView.getText()));
        }
        linearLayout.removeAllViews();
        TextView textView2 = new TextView(view.getContext());
        textView2.setTextSize(12.0f);
        textView2.setTextColor(view.getContext().getResources().getColor(R.color.gray_font));
        if (TextUtils.isEmpty(item.getTitle_img())) {
            imageView.setVisibility(8);
        } else {
            imageView.setTag(item.getTitle_img());
            imageView.setVisibility(0);
            Glide.with(imageView.getContext()).asBitmap().placeholder(R.drawable.plugin_camera_no_pictures).error(R.drawable.plugin_camera_no_pictures).load(item.getTitle_img()).into((RequestBuilder) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.adapter.MockTestAdapter.1
                @Override // com.bumptech.glide.request.target.Target
                public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object resource, @Nullable Transition transition) {
                    onResourceReady((Bitmap) resource, (Transition<? super Bitmap>) transition);
                }

                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int width = resource.getWidth();
                    resource.getHeight();
                    int i2 = view.getContext().getResources().getDisplayMetrics().widthPixels;
                    if (width >= i2) {
                        width = i2 - CommonUtil.dip2px(view.getContext(), 30.0f);
                    }
                    int iDip2px = CommonUtil.dip2px(view.getContext(), 120.0f);
                    ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                    layoutParams.width = width;
                    layoutParams.height = iDip2px;
                    imageView.setLayoutParams(layoutParams);
                    imageView.setImageBitmap(resource);
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.adapter.oa
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    MockTestAdapter.lambda$loadNormalContent$0(view2);
                }
            });
        }
        getData().get(position).setDoStartDuration(System.currentTimeMillis() / 1000);
        if (TextUtils.equals(Constants.VIA_REPORT_TYPE_DATALINE, item.getType())) {
            option = new ArrayList<>();
            ExesQuestionBean.OptionBean optionBean = new ExesQuestionBean.OptionBean();
            ExesQuestionBean.OptionBean optionBean2 = new ExesQuestionBean.OptionBean();
            optionBean.setKey("正确");
            optionBean2.setKey("错误");
            option.add(optionBean);
            option.add(optionBean2);
            item.setOption(option);
        } else {
            option = item.getOption();
        }
        MockTestOptionAdapter mockTestOptionAdapter = new MockTestOptionAdapter(option);
        recyclerView.setAdapter(mockTestOptionAdapter);
        mockTestOptionAdapter.setOnItemClickListener(new OptionsItemClickListener(item.getAnswer().length(), position));
        button.setVisibility((TextUtils.equals(item.getType(), Constants.VIA_REPORT_TYPE_DATALINE) || !(item.getAnswer().length() > 1 || TextUtils.equals(item.getType(), Constants.VIA_ACT_TYPE_NINETEEN) || TextUtils.equals(item.getType(), Constants.VIA_SHARE_TYPE_PUBLISHVIDEO))) ? 8 : 0);
        button.setOnClickListener(new SubmitAnswerClick(getData(), position));
        String answer = TextUtils.isEmpty(item.getAnswer()) ? "" : item.getAnswer();
        ((TextView) view.findViewById(R.id.questiondetails_tv_Answer)).setText(Html.fromHtml("<font  color='#38456D'>[正确答案]  </font>" + answer.trim().replace(",", "").trim()));
        String strTrim = TextUtils.isEmpty(item.getRestore()) ? "" : item.getRestore().trim();
        if (TextUtils.isEmpty(strTrim)) {
            view.findViewById(R.id.questiondetails_tv_content_ques).setVisibility(8);
        } else {
            TextView textView3 = (TextView) view.findViewById(R.id.questiondetails_tv_content_ques);
            view.findViewById(R.id.questiondetails_tv_content_ques).setVisibility(0);
            textView3.setText(Html.fromHtml("<font  color='#dd594a'>[考点还原]  </font>" + strTrim));
        }
        ((TextView) view.findViewById(R.id.questiondetails_tv_contents)).setText(Html.fromHtml("<font  color='#dd594a'>[答案解析]  </font>" + item.getExplain()));
    }

    private void loadShareStemContent(@NonNull final View view, ExesQuestionBean item, final int pos) throws Resources.NotFoundException {
        view.findViewById(R.id.pagenumtv).setVisibility(8);
        final TextView textView = (TextView) view.findViewById(R.id.typeStr);
        this.curPos = pos;
        final ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.ll_top_content);
        final List<ExesQuestionBean> list = this.shareStemMap.get(item.getPublic_number());
        if (list == null) {
            return;
        }
        if (list.size() >= 5) {
            view.findViewById(R.id.iv_shadow_left).setVisibility(0);
            view.findViewById(R.id.iv_shadow_right).setVisibility(0);
        }
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        while (i2 < list.size()) {
            StringBuilder sb = new StringBuilder();
            sb.append("第");
            int i3 = i2 + 1;
            sb.append(i3);
            sb.append("问");
            arrayList.add(new QuestionIndexBean(sb.toString(), String.valueOf(i3), i2 == 0));
            i2 = i3;
        }
        final TextView textView2 = (TextView) view.findViewById(R.id.titletv);
        textView2.setText(item.getPublic_title());
        textView.setText(list.get(0).getType_str());
        TextView textView3 = (TextView) view.findViewById(R.id.questiondetails_tv_title);
        textView3.setText(item.getQuestion_type());
        textView3.setGravity(8388627);
        textView3.setTextSize(12.0f);
        textView3.setPadding(CommonUtil.dip2px(view.getContext(), 20.0f), CommonUtil.dip2px(view.getContext(), 7.0f), CommonUtil.dip2px(view.getContext(), 20.0f), CommonUtil.dip2px(view.getContext(), 7.0f));
        final MagicIndicator magicIndicator = (MagicIndicator) view.findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(view.getContext());
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        commonNavigator.setSkimOver(false);
        commonNavigator.setAdapter(new ShareStemQuestionAdapter(arrayList, viewPager));
        magicIndicator.setNavigator(commonNavigator);
        viewPager.setTag(Integer.valueOf(pos));
        viewPager.setAdapter(new StemMockTestAdapter(list, this.handler, new StemMockTestAdapter.StemQuestionSwitchListener() { // from class: com.psychiatrygarden.adapter.MockTestAdapter.2
            @Override // com.psychiatrygarden.adapter.StemMockTestAdapter.StemQuestionSwitchListener
            public void next(int current) throws Resources.NotFoundException {
                if (viewPager.getCurrentItem() == list.size() - 1) {
                    Message message = new Message();
                    message.what = 1;
                    message.arg1 = pos;
                    message.arg2 = current;
                    MockTestAdapter.this.handler.sendMessageDelayed(message, 100L);
                    return;
                }
                if (viewPager.getCurrentItem() < list.size() - 1) {
                    ViewPager viewPager2 = viewPager;
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1, true);
                    magicIndicator.onPageSelected(viewPager.getCurrentItem());
                }
            }

            @Override // com.psychiatrygarden.adapter.StemMockTestAdapter.StemQuestionSwitchListener
            public void optionClickListener(int position) {
                long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) - ((ExesQuestionBean) list.get(position)).getDoStartDuration();
                LogUtils.e("do_question_time", "共用题干答题时间=>" + jCurrentTimeMillis);
                ((ExesQuestionBean) list.get(position)).setDoDuration(String.valueOf(jCurrentTimeMillis));
            }
        }));
        this.data.get(pos).setCurrentStemIndex(list.get(0).getActualStemIndex());
        int jumpPosition = item.getJumpPosition();
        if (jumpPosition != -1) {
            int i4 = 0;
            while (true) {
                if (i4 >= list.size()) {
                    break;
                }
                if (list.get(i4).getActualStemIndex() == jumpPosition) {
                    viewPager.setCurrentItem(i4, false);
                    magicIndicator.onPageSelected(i4);
                    break;
                }
                i4++;
            }
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.adapter.MockTestAdapter.3
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int position) {
                LogUtils.e("do_question_time", "页面选中可见：" + ((ExesQuestionBean) list.get(position)).getTitle());
                boolean zBooleanValue = ((Boolean) MockTestAdapter.this.singleLineTitleArr.get(((Integer) viewPager.getTag()).intValue())).booleanValue();
                int iIntValue = ((Integer) MockTestAdapter.this.topHeightArr.get(((Integer) viewPager.getTag()).intValue())).intValue();
                int iIntValue2 = ((Integer) MockTestAdapter.this.dragViewHeightArr.get(((Integer) viewPager.getTag()).intValue())).intValue();
                int iIntValue3 = ((Integer) MockTestAdapter.this.titleHeightArr.get(((Integer) viewPager.getTag()).intValue())).intValue();
                MockTestAdapter.this.getData().get(pos).setCurrentStemIndex(((ExesQuestionBean) list.get(position)).getActualStemIndex());
                ((ExesQuestionBean) list.get(position)).setDoStartDuration(System.currentTimeMillis() / 1000);
                magicIndicator.onPageSelected(position);
                Boolean bool = (Boolean) MockTestAdapter.this.expandStatusArray.get(position);
                if (bool == null) {
                    bool = Boolean.TRUE;
                }
                if (!zBooleanValue) {
                    iIntValue = iIntValue3;
                }
                MockTestAdapter mockTestAdapter = MockTestAdapter.this;
                int[] iArr = new int[2];
                iArr[0] = !bool.booleanValue() ? iIntValue : -iIntValue2;
                if (!bool.booleanValue()) {
                    iIntValue = -iIntValue2;
                }
                iArr[1] = iIntValue;
                mockTestAdapter.execTopMarinAnimation(iArr);
                MockTestAdapter.this.expandStatusArray.put(position, bool);
                Message messageObtainMessage = MockTestAdapter.this.handler.obtainMessage();
                messageObtainMessage.what = 999;
                messageObtainMessage.obj = Integer.valueOf(((ExesQuestionBean) list.get(position)).getActualStemIndex() + 1);
                MockTestAdapter.this.handler.sendMessage(messageObtainMessage);
                textView.setText(((ExesQuestionBean) list.get(0)).getType_str());
            }
        });
        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_child_question);
        this.expandStatusArray.put(item.getActualStemIndex(), Boolean.TRUE);
        linearLayout.post(new Runnable() { // from class: com.psychiatrygarden.adapter.ja
            @Override // java.lang.Runnable
            public final void run() {
                this.f14648c.lambda$loadShareStemContent$1(view, linearLayout);
            }
        });
        final NestedScrollView nestedScrollView = (NestedScrollView) view.findViewById(R.id.nsl);
        nestedScrollView.setTag(Integer.valueOf(pos));
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() { // from class: com.psychiatrygarden.adapter.ka
            @Override // androidx.core.widget.NestedScrollView.OnScrollChangeListener
            public final void onScrollChange(NestedScrollView nestedScrollView2, int i5, int i6, int i7, int i8) {
                this.f14679a.lambda$loadShareStemContent$2(nestedScrollView2, i5, i6, i7, i8);
            }
        });
        viewGroup.post(new Runnable() { // from class: com.psychiatrygarden.adapter.la
            @Override // java.lang.Runnable
            public final void run() {
                this.f14720c.lambda$loadShareStemContent$3(viewGroup, view, textView2, nestedScrollView, linearLayout);
            }
        });
        view.findViewById(R.id.rl_drag_area).setOnTouchListener(new View.OnTouchListener() { // from class: com.psychiatrygarden.adapter.ma
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.f14762c.lambda$loadShareStemContent$4(view, nestedScrollView, view2, motionEvent);
            }
        });
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        this.map.remove(Integer.valueOf(position));
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    /* renamed from: getCount */
    public int getSize() {
        return this.data.size();
    }

    public List<ExesQuestionBean> getData() {
        return this.data;
    }

    public View getPageView(int position) {
        return this.map.get(Integer.valueOf(position));
    }

    public ArrayMap<String, List<ExesQuestionBean>> getShareStemMap() {
        return this.shareStemMap;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup container, int position) throws Resources.NotFoundException {
        ExesQuestionBean exesQuestionBean = this.data.get(position);
        View viewInflate = View.inflate(container.getContext(), exesQuestionBean.getItemType() == 2 ? R.layout.item_mock_test : R.layout.item_share_stem_question, null);
        viewInflate.setTag(Integer.valueOf(position));
        LogUtils.d("position", position + "");
        this.maxTopHeight = 0;
        int statusBarHeight = 0 + StatusBarUtil.getStatusBarHeight(container.getContext());
        this.maxTopHeight = statusBarHeight;
        this.maxTopHeight = statusBarHeight + CommonUtil.dip2px(container.getContext(), 494.0f);
        if (exesQuestionBean.getItemType() == 2) {
            loadNormalContent(viewInflate, exesQuestionBean, position);
        } else {
            loadShareStemContent(viewInflate, exesQuestionBean, position);
        }
        container.addView(viewInflate);
        this.map.put(Integer.valueOf(position), viewInflate);
        return viewInflate;
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
