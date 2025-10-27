package com.psychiatrygarden.activity.circleactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import cn.hutool.core.text.StrPool;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.WebLongSaveActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.CircleInfoBean;
import com.psychiatrygarden.interfaceclass.DomoIml;
import com.psychiatrygarden.interfaceclass.TextClick;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.StickerSpan;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.MyListView;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import com.yikaobang.yixue.R2;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class ArticeViewActivity extends BaseActivity {
    private TextView canyuren;
    private Drawable drawable;
    private CircleImageView imgheader;
    private MyListView listOption;
    private CircleInfoBean.DataBean mCircleInfoBean;
    private SpannableStringBuilder spanBuilder;
    private TextView timedata;
    private TextView title;
    private TextView titleContent;
    private TextView topicName;
    private LinearLayout toupiaolayout;
    private TextView tv_oppose;
    private TextView tv_reply;
    private TextView tv_support;
    private TextView username;
    private final int[] voteColors = {R.color.color_vote_item_0, R.color.color_vote_item_1, R.color.color_vote_item_2, R.color.color_vote_item_3, R.color.color_vote_item_4, R.color.color_vote_item_5, R.color.color_vote_item_6, R.color.color_vote_item_7, R.color.color_vote_item_8, R.color.color_vote_item_9};

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getHeaderData$1(int i2) {
        Intent intent = new Intent(this, (Class<?>) WebLongSaveActivity.class);
        intent.putExtra("web_url", this.mCircleInfoBean.getLink().get(i2).getUrl());
        intent.putExtra("title", this.mCircleInfoBean.getLink().get(i2).getTitle());
        getApplicationContext().startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getHeaderData$2(AdapterView adapterView, View view, int i2, long j2) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    public void getHeaderData() {
        this.title.setText(this.mCircleInfoBean.getTitle());
        GlideUtils.loadImage(this, this.mCircleInfoBean.getAvatar(), this.imgheader);
        this.username.setText(this.mCircleInfoBean.getNickname());
        this.timedata.setText(String.format("%s %s", this.mCircleInfoBean.getSchool_name(), this.mCircleInfoBean.getCtime()));
        if (TextUtils.isEmpty(this.mCircleInfoBean.getTopic_name())) {
            this.topicName.setVisibility(8);
        } else {
            this.topicName.setVisibility(0);
        }
        this.topicName.setText(this.mCircleInfoBean.getTopic_name());
        if (this.mCircleInfoBean.getIs_support().equals("1")) {
            this.tv_support.setText(String.format("已赞同(%s)", this.mCircleInfoBean.getSupport_count()));
        } else {
            this.tv_support.setText(String.format("赞同(%s)", this.mCircleInfoBean.getSupport_count()));
        }
        if (this.mCircleInfoBean.getIs_opposition().equals("1")) {
            this.tv_oppose.setText(String.format("已反对(%s)", this.mCircleInfoBean.getOpposition_count()));
        } else {
            this.tv_oppose.setText(String.format("反对(%s)", this.mCircleInfoBean.getOpposition_count()));
        }
        this.tv_reply.setText(String.format("回复(%s)", this.mCircleInfoBean.getComment_count()));
        if (this.mCircleInfoBean.getContent() != null) {
            String content = this.mCircleInfoBean.getContent();
            if (this.mCircleInfoBean.getImg() != null && this.mCircleInfoBean.getImg().size() > 0) {
                Matcher matcher = Pattern.compile("\\[image]").matcher(content);
                int i2 = 0;
                while (matcher.find()) {
                    try {
                        content = content.replace(matcher.group(0), StrPool.BRACKET_START + this.mCircleInfoBean.getImg().get(i2).getImage() + StrPool.BRACKET_END);
                    } catch (Exception unused) {
                        content = content.replace(matcher.group(0), StrPool.BRACKET_START + this.mCircleInfoBean.getImg().get(0).getImage() + StrPool.BRACKET_END);
                    }
                    i2++;
                }
            }
            if (this.mCircleInfoBean.getLink() != null && this.mCircleInfoBean.getLink().size() > 0) {
                Matcher matcher2 = Pattern.compile("\\[link]").matcher(content);
                int i3 = 0;
                while (matcher2.find()) {
                    int length = 0;
                    for (int i4 = 0; i4 < i3; i4++) {
                        length += ("&1000" + i4 + this.mCircleInfoBean.getLink().get(i4).getTitle()).length();
                    }
                    StringBuilder sb = new StringBuilder();
                    int i5 = i3 * 6;
                    sb.append(content.substring(0, (matcher2.start() + length) - i5));
                    sb.append("&1000");
                    sb.append(i3);
                    sb.append(this.mCircleInfoBean.getLink().get(i3).getTitle());
                    sb.append(content.substring((matcher2.end() + length) - i5));
                    content = sb.toString();
                    i3++;
                }
            }
            this.spanBuilder = new SpannableStringBuilder(content);
            if (this.mCircleInfoBean.getLink() != null && this.mCircleInfoBean.getLink().size() > 0) {
                Paint.FontMetrics fontMetrics = this.titleContent.getPaint().getFontMetrics();
                int iCeil = ((int) Math.ceil(fontMetrics.descent - fontMetrics.top)) + 2;
                this.drawable.setBounds(0, 0, (((this.drawable.getIntrinsicWidth() * iCeil) / this.drawable.getIntrinsicHeight()) / 5) * 3, (iCeil / 5) * 3);
                for (final int i6 = 0; i6 < this.mCircleInfoBean.getLink().size(); i6++) {
                    Matcher matcher3 = Pattern.compile("&1000" + i6 + this.mCircleInfoBean.getLink().get(i6).getTitle()).matcher(content);
                    if (matcher3.find()) {
                        StickerSpan stickerSpan = new StickerSpan(this.drawable, 1);
                        this.spanBuilder.setSpan(stickerSpan, matcher3.start(), matcher3.start() + (Constants.DEFAULT_UIN + i6).length() + 1, 33);
                        this.spanBuilder.setSpan(new TextClick(new DomoIml() { // from class: com.psychiatrygarden.activity.circleactivity.b
                            @Override // com.psychiatrygarden.interfaceclass.DomoIml
                            public final void clickToast() {
                                this.f11493a.lambda$getHeaderData$1(i6);
                            }
                        }, true), matcher3.start() + ("&1000" + i6).length(), matcher3.end(), 33);
                        this.titleContent.setHighlightColor(Color.parseColor("#00ffffff"));
                        this.titleContent.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                }
            }
            getImageData();
            this.titleContent.setText(this.spanBuilder);
            this.titleContent.requestLayout();
        }
        if (this.mCircleInfoBean.getOptions() != null) {
            if (this.mCircleInfoBean.getOptions().getOptions() == null || this.mCircleInfoBean.getOptions().getOptions().size() <= 0) {
                this.toupiaolayout.setVisibility(8);
                return;
            }
            this.toupiaolayout.setVisibility(0);
            this.canyuren.setText(String.format("%s人参与了投票", this.mCircleInfoBean.getOptions().getUser_count()));
            this.listOption.setAdapter((ListAdapter) new CommAdapter<CircleInfoBean.DataBean.OptionsBeanX.OptionsBean>(this.mCircleInfoBean.getOptions().getOptions(), this.mContext, R.layout.activity_weitoupiaoitem) { // from class: com.psychiatrygarden.activity.circleactivity.ArticeViewActivity.1
                @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                public void convert(ViewHolder vHolder, CircleInfoBean.DataBean.OptionsBeanX.OptionsBean optionsBean, int position) throws NumberFormatException {
                    TextView textView = (TextView) vHolder.getView(R.id.tv_background);
                    TextView textView2 = (TextView) vHolder.getView(R.id.toupiaocontent);
                    CheckBox checkBox = (CheckBox) vHolder.getView(R.id.check);
                    LinearLayout linearLayout = (LinearLayout) vHolder.getView(R.id.lineduihao);
                    TextView textView3 = (TextView) vHolder.getView(R.id.baifenbi);
                    TextView textView4 = (TextView) vHolder.getView(R.id.duigou);
                    checkBox.setChecked(optionsBean.isTrue());
                    textView2.setText(optionsBean.getOption());
                    checkBox.setVisibility(ArticeViewActivity.this.mCircleInfoBean.getOptions().getIs_vote().equals("0") ? 0 : 8);
                    checkBox.setEnabled(false);
                    if (ArticeViewActivity.this.mCircleInfoBean.getOptions().getIs_vote().equals("0")) {
                        linearLayout.setVisibility(8);
                        return;
                    }
                    if (optionsBean.getId().equals(ArticeViewActivity.this.mCircleInfoBean.getOptions().getVote_option())) {
                        textView4.setVisibility(0);
                    } else {
                        textView4.setVisibility(8);
                    }
                    linearLayout.setVisibility(0);
                    int i7 = Integer.parseInt(optionsBean.getVote_nums());
                    int i8 = Integer.parseInt(ArticeViewActivity.this.mCircleInfoBean.getOptions().getUser_count());
                    float f2 = i8 == 0 ? i7 * 100.0f : (i7 * 100.0f) / i8;
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                    layoutParams.width = (int) ((ArticeViewActivity.this.getResources().getDisplayMetrics().widthPixels * f2) / 100.0f);
                    textView.setLayoutParams(layoutParams);
                    try {
                        if (position > ArticeViewActivity.this.voteColors.length - 1) {
                            textView.setBackgroundResource(ArticeViewActivity.this.voteColors[new Random().nextInt(ArticeViewActivity.this.voteColors.length)]);
                        } else {
                            textView.setBackgroundResource(ArticeViewActivity.this.voteColors[position]);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        textView.setBackgroundResource(ArticeViewActivity.this.voteColors[0]);
                    }
                    textView3.setText(ArticeViewActivity.this.replacevalue(new DecimalFormat("#0.0").format(f2)) + "%");
                }
            });
            this.listOption.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.c
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view, int i7, long j2) {
                    ArticeViewActivity.lambda$getHeaderData$2(adapterView, view, i7, j2);
                }
            });
        }
    }

    public void getImageData() {
        try {
            if (this.mCircleInfoBean.getImg() == null || this.mCircleInfoBean.getImg().size() <= 0) {
                return;
            }
            Matcher matcher = Pattern.compile("\\[[^]]+]").matcher(this.spanBuilder.toString());
            int i2 = 0;
            while (matcher.find()) {
                if (matcher.group().contains("http")) {
                    lastPositionData(this.mCircleInfoBean.getImg().get(i2).getImage(), matcher.start(), matcher.end());
                    i2++;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.topicName = (TextView) findViewById(R.id.topicName);
        this.tv_support = (TextView) findViewById(R.id.tv_support);
        this.tv_oppose = (TextView) findViewById(R.id.tv_oppose);
        this.tv_reply = (TextView) findViewById(R.id.tv_reply);
        ((ImageView) findViewById(R.id.include_btn_left)).setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11488c.lambda$init$0(view);
            }
        });
        this.toupiaolayout = (LinearLayout) findViewById(R.id.toupiaolayout);
        this.listOption = (MyListView) findViewById(R.id.listOption);
        this.canyuren = (TextView) findViewById(R.id.canyuren);
        this.title = (TextView) findViewById(R.id.title);
        this.username = (TextView) findViewById(R.id.username);
        this.timedata = (TextView) findViewById(R.id.timedata);
        this.imgheader = (CircleImageView) findViewById(R.id.imgheader);
        TextView textView = (TextView) findViewById(R.id.titleContent);
        this.titleContent = textView;
        textView.setVisibility(0);
        this.drawable = ContextCompat.getDrawable(this, R.drawable.lianjietu);
        getHeaderData();
    }

    public void lastPositionData(String imgUrl, final int start, final int end) {
        Glide.with(getApplicationContext()).asBitmap().load((Object) GlideUtils.generateUrl(imgUrl)).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>() { // from class: com.psychiatrygarden.activity.circleactivity.ArticeViewActivity.2
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object bitmap, @Nullable Transition transition) {
                onResourceReady((Bitmap) bitmap, (Transition<? super Bitmap>) transition);
            }

            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                int screenWidth = (CommonUtil.getScreenWidth(ArticeViewActivity.this.getApplicationContext()) - ArticeViewActivity.this.titleContent.getPaddingLeft()) - ArticeViewActivity.this.titleContent.getPaddingRight();
                int height = (bitmap.getHeight() * screenWidth) / bitmap.getWidth();
                if (height > 4096) {
                    height = R2.color.N_stretchTextColor;
                }
                BitmapDrawable bitmapDrawable = new BitmapDrawable(ArticeViewActivity.this.getResources(), bitmap);
                bitmapDrawable.setBounds(0, 0, screenWidth, height);
                ArticeViewActivity.this.spanBuilder.setSpan(new ImageSpan(bitmapDrawable, 1), start, end, 33);
                ArticeViewActivity.this.spanBuilder.setSpan(new ClickableSpan() { // from class: com.psychiatrygarden.activity.circleactivity.ArticeViewActivity.2.1
                    @Override // android.text.style.ClickableSpan
                    public void onClick(View widget) {
                    }
                }, start, end, 33);
                ArticeViewActivity.this.titleContent.setMovementMethod(LinkMovementMethod.getInstance());
                ArticeViewActivity.this.titleContent.setText(ArticeViewActivity.this.spanBuilder);
                ArticeViewActivity.this.titleContent.requestLayout();
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    public String replacevalue(String s2) {
        return (s2 == null || s2.equals("")) ? "0" : s2.indexOf(StrPool.DOT) > 0 ? s2.replaceAll("0+?$", "").replaceAll("[.]$", "") : s2;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        this.mActionBar.hide();
        setContentView(R.layout.activity_artice_view);
        this.mCircleInfoBean = (CircleInfoBean.DataBean) getIntent().getSerializableExtra("data");
        init();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
