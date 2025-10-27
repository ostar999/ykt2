package com.psychiatrygarden.fragmenthome;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.UserCommentInfoActivity;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.activity.rank.EntranceResultsActivity;
import com.psychiatrygarden.baseview.BaseFragment;
import com.psychiatrygarden.bean.RankingContentBean;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.onDialogShareClickListener;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.widget.CircleImageView;
import com.psychiatrygarden.widget.DialogShare;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tencent.connect.common.Constants;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class MockRankingFrag extends BaseFragment {
    private static final int PAGE_SIZE = 10;
    private View line;
    private ListView lv_rank;
    private TextView mBtnChoose;
    private TextView mBtnShare;
    private CommAdapter<RankingContentBean.DataBean.RankingBean> mCommAdapter;
    private LinearLayout mLyNoChoose;
    private RankingContentBean mRankingContentBean;
    private int position;
    private ArrayList<RankingContentBean.DataBean.RankingBean> rankingList;
    private SmartRefreshLayout smartRefreshLayout;
    private int page = 1;
    private boolean isLoadMore = false;
    List<RankingContentBean.DataBean.RankingBean> mDataBeans = new ArrayList();
    private final UMShareListener umShareListener = new UMShareListener() { // from class: com.psychiatrygarden.fragmenthome.MockRankingFrag.3
        @Override // com.umeng.socialize.UMShareListener
        public void onCancel(SHARE_MEDIA arg0) {
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onError(SHARE_MEDIA arg0, Throwable arg1) {
            MockRankingFrag.this.AlertToast("分享失败");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onResult(SHARE_MEDIA arg0) {
            MockRankingFrag.this.AlertToast("分享成功");
        }

        @Override // com.umeng.socialize.UMShareListener
        public void onStart(SHARE_MEDIA share_media) {
        }
    };

    private void initPlatforms() {
        ProjectApp.platforms.clear();
        ProjectApp.platforms.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.QQ.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.SINA.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.QZONE.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.TENCENT.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.WEIXIN_FAVORITE.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.ALIPAY.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.DINGTALK.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.RENREN.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.DOUBAN.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.SMS.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.EMAIL.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.YNOTE.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.EVERNOTE.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.LAIWANG.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.LAIWANG_DYNAMIC.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.LINKEDIN.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.YIXIN.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.YIXIN_CIRCLE.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.FACEBOOK.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.FACEBOOK_MESSAGER.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.TWITTER.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.WHATSAPP.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.GOOGLEPLUS.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.LINE.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.INSTAGRAM.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.KAKAO.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.PINTEREST.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.POCKET.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.TUMBLR.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.FLICKR.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.FOURSQUARE.toSnsPlatform());
        ProjectApp.platforms.add(SHARE_MEDIA.MORE.toSnsPlatform());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$1(int i2) {
        ((MockTypeRankingFragment) getParentFragment()).shareAppControl(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$2(View view) {
        RankingContentBean rankingContentBean = this.mRankingContentBean;
        if (rankingContentBean != null && rankingContentBean.getCode().equals("200") && this.position == 0) {
            new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.fragmenthome.l8
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i2) {
                    this.f15809a.lambda$initViews$0(i2);
                }
            }).show();
        } else {
            new DialogShare(this.mContext, new onDialogShareClickListener() { // from class: com.psychiatrygarden.fragmenthome.m8
                @Override // com.psychiatrygarden.interfaceclass.onDialogShareClickListener
                public final void onclickIntBack(int i2) {
                    this.f15835a.lambda$initViews$1(i2);
                }
            }).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initViews$3(View view) {
        toEntranceResultsActivity();
    }

    private void toEntranceResultsActivity() {
        Intent intent = new Intent(ProjectApp.instance(), (Class<?>) EntranceResultsActivity.class);
        intent.putExtra("moudle", Constants.VIA_REPORT_TYPE_SET_AVATAR);
        intent.putExtra("type", "mock");
        intent.putExtra("examId", getArguments().getString("exam_id"));
        intent.putExtra("status", "MOCK");
        startActivity(intent);
    }

    public void getGfRankData() {
        CommAdapter<RankingContentBean.DataBean.RankingBean> commAdapter = new CommAdapter<RankingContentBean.DataBean.RankingBean>(this.rankingList, this.mContext, R.layout.adapter_ranking_content) { // from class: com.psychiatrygarden.fragmenthome.MockRankingFrag.2
            @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
            public void convert(ViewHolder vHolder, final RankingContentBean.DataBean.RankingBean rankingBean, int position) {
                vHolder.setImageHeadUrl(R.id.iv_header_img, rankingBean.getAvatar()).setText(R.id.tv_ranking_grade, rankingBean.getScore() + "分").setText(R.id.tv_ranking_accuracy, rankingBean.getExam_time()).setText(R.id.tv_ranking_name, rankingBean.getNickname());
                CircleImageView circleImageView = (CircleImageView) vHolder.getView(R.id.circleimg);
                TextView textView = (TextView) vHolder.getView(R.id.tv_ranking_num);
                int i2 = position + 1;
                if (i2 == 1) {
                    circleImageView.setVisibility(0);
                    textView.setVisibility(8);
                    circleImageView.setImageResource(R.drawable.rankone);
                } else if (i2 == 2) {
                    circleImageView.setVisibility(0);
                    textView.setVisibility(8);
                    circleImageView.setImageResource(R.drawable.ranktwo);
                } else if (i2 != 3) {
                    circleImageView.setVisibility(8);
                    textView.setVisibility(0);
                    textView.setText(String.valueOf(i2));
                } else {
                    circleImageView.setVisibility(0);
                    textView.setVisibility(8);
                    circleImageView.setImageResource(R.drawable.rankthree);
                }
            }
        };
        this.mCommAdapter = commAdapter;
        this.lv_rank.setAdapter((ListAdapter) commAdapter);
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public int getLayoutId() {
        return R.layout.activity_ranking;
    }

    public void getRankData(int index) {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("exam_id", getArguments().getString("exam_id"));
        ajaxParams.put("kind", index + "");
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mRankingUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.fragmenthome.MockRankingFrag.1

            /* renamed from: com.psychiatrygarden.fragmenthome.MockRankingFrag$1$1, reason: invalid class name and collision with other inner class name */
            public class C02971 extends CommAdapter<RankingContentBean.DataBean.RankingBean> {
                public C02971(List mData, Context mcontext, int layoutId) {
                    super(mData, mcontext, layoutId);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(RankingContentBean.DataBean.RankingBean rankingBean, View view) {
                    if (CommonUtil.isFastClick()) {
                        return;
                    }
                    Intent intent = new Intent(((BaseFragment) MockRankingFrag.this).mContext, (Class<?>) UserCommentInfoActivity.class);
                    intent.putExtra("user_id", rankingBean.getUser_id());
                    intent.putExtra("jiav", "");
                    intent.addFlags(67108864);
                    MockRankingFrag.this.startActivity(intent);
                }

                @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                public void convert(ViewHolder vHolder, final RankingContentBean.DataBean.RankingBean rankingBean, int position) {
                    RelativeLayout relativeLayout = (RelativeLayout) vHolder.getView(R.id.itemrel);
                    vHolder.setImageHeadUrl(R.id.iv_header_img, rankingBean.getAvatar()).setText(R.id.tv_ranking_grade, rankingBean.getScore() + "分").setText(R.id.tv_ranking_accuracy, rankingBean.getExam_time()).setText(R.id.tv_ranking_name, rankingBean.getNickname());
                    CircleImageView circleImageView = (CircleImageView) vHolder.getView(R.id.circleimg);
                    TextView textView = (TextView) vHolder.getView(R.id.tv_ranking_num);
                    int i2 = position + 1;
                    if (i2 == 1) {
                        circleImageView.setVisibility(0);
                        textView.setVisibility(8);
                        circleImageView.setImageResource(R.drawable.rankone);
                    } else if (i2 == 2) {
                        circleImageView.setVisibility(0);
                        textView.setVisibility(8);
                        circleImageView.setImageResource(R.drawable.ranktwo);
                    } else if (i2 != 3) {
                        circleImageView.setVisibility(8);
                        textView.setVisibility(0);
                        textView.setText(String.valueOf(i2));
                    } else {
                        circleImageView.setVisibility(0);
                        textView.setVisibility(8);
                        circleImageView.setImageResource(R.drawable.rankthree);
                    }
                    relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.p8
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f15912c.lambda$convert$0(rankingBean, view);
                        }
                    });
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    MockRankingFrag.this.mRankingContentBean = (RankingContentBean) new Gson().fromJson(s2, RankingContentBean.class);
                    if (MockRankingFrag.this.mRankingContentBean.getCode().equals("200")) {
                        new SpannableStringBuilder(MockRankingFrag.this.mRankingContentBean.getData().getOwn_rank() + "/" + MockRankingFrag.this.mRankingContentBean.getData().getNumber_of_test()).setSpan(new TextAppearanceSpan(null, 0, 0, ContextCompat.getColorStateList(((BaseFragment) MockRankingFrag.this).mContext, R.color.prey_new_font), null), 0, MockRankingFrag.this.mRankingContentBean.getData().getOwn_rank().length(), 34);
                        MockRankingFrag.this.mRankingContentBean.getData().setAll(true);
                        EventBus.getDefault().post(MockRankingFrag.this.mRankingContentBean.getData());
                        MockRankingFrag mockRankingFrag = MockRankingFrag.this;
                        mockRankingFrag.mDataBeans = mockRankingFrag.mRankingContentBean.getData().getRanking();
                        MockRankingFrag mockRankingFrag2 = MockRankingFrag.this;
                        MockRankingFrag mockRankingFrag3 = MockRankingFrag.this;
                        mockRankingFrag2.mCommAdapter = new C02971(mockRankingFrag3.mDataBeans, ((BaseFragment) mockRankingFrag3).mContext, R.layout.adapter_ranking_content);
                        MockRankingFrag.this.lv_rank.setAdapter((ListAdapter) MockRankingFrag.this.mCommAdapter);
                    } else {
                        MockRankingFrag mockRankingFrag4 = MockRankingFrag.this;
                        mockRankingFrag4.AlertToast(mockRankingFrag4.mRankingContentBean.getMessage());
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void initViews(com.psychiatrygarden.baseview.ViewHolder holder, View root) {
        String string = getArguments().getString("bindSchool", "");
        this.position = getArguments().getInt("position", 0);
        this.rankingList = getArguments().getParcelableArrayList("rankingList");
        this.smartRefreshLayout = (SmartRefreshLayout) holder.get(R.id.srl_refresh);
        this.lv_rank = (ListView) holder.get(R.id.lv_rank);
        this.smartRefreshLayout.setVisibility(8);
        this.mBtnShare = (TextView) holder.get(R.id.btn_share);
        this.mLyNoChoose = (LinearLayout) holder.get(R.id.ly_no_choose);
        this.mBtnChoose = (TextView) holder.get(R.id.btn_to_choose);
        this.line = holder.get(R.id.line);
        if (this.position != 1) {
            this.mLyNoChoose.setVisibility(8);
            this.lv_rank.setVisibility(0);
            this.mBtnShare.setVisibility(0);
            this.line.setVisibility(0);
        } else if (TextUtils.isEmpty(string) || !string.equals("1")) {
            EventBus.getDefault().post("hiddenScore");
            this.mLyNoChoose.setVisibility(0);
            this.lv_rank.setVisibility(8);
            this.mBtnShare.setVisibility(8);
            this.line.setVisibility(8);
        } else {
            this.mLyNoChoose.setVisibility(8);
            this.lv_rank.setVisibility(0);
            this.mBtnShare.setVisibility(0);
            this.line.setVisibility(0);
        }
        this.mBtnShare.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.n8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15868c.lambda$initViews$2(view);
            }
        });
        this.mBtnChoose.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.o8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15890c.lambda$initViews$3(view);
            }
        });
        initPlatforms();
        if (this.position == 0) {
            getRankData(1);
        } else {
            if (TextUtils.isEmpty(string) || !string.equals("1")) {
                return;
            }
            getGfRankData();
        }
    }

    @Override // com.psychiatrygarden.baseview.BaseFragment
    public void onEventMainThread(String str) {
        if (str.equals("bindSuccess")) {
            this.mLyNoChoose.setVisibility(8);
            this.lv_rank.setVisibility(0);
            this.mBtnShare.setVisibility(0);
            this.line.setVisibility(0);
            EventBus.getDefault().post("showScore");
            if (this.position == 1) {
                getGfRankData();
            }
        }
    }

    /* renamed from: shareAppControl, reason: merged with bridge method [inline-methods] */
    public void lambda$initViews$0(int i2) {
        UMImage uMImage = new UMImage(this.mContext, R.drawable.yikaopm);
        String str = getArguments().getInt("position") == 1 ? "考研院校排" : "全部院校排";
        String str2 = this.mRankingContentBean.getData().getOwn_rank() + "/" + this.mRankingContentBean.getData().getNumber_of_test();
        StringBuilder sb = new StringBuilder();
        sb.append("得分");
        sb.append(this.mRankingContentBean.getData().getScore());
        sb.append("，用时");
        sb.append(this.mRankingContentBean.getData().getExam_time());
        String str3 = "";
        sb.append("");
        sb.append(str);
        sb.append("名");
        sb.append(str2);
        String string = sb.toString();
        try {
            str3 = NetworkRequestsURL.mockShareUrl + "title=" + getArguments().getString("title") + "&app_id=" + SharePreferencesUtils.readStrConfig(CommonParameter.App_Id, this.mContext) + "&exam_id=" + getArguments().getString("exam_id") + "&user_id=" + UserConfig.getUserId();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        String str4 = ("我的" + getArguments().getString("title")) + "排名为第" + this.mRankingContentBean.getData().getOwn_rank() + "名";
        UMWeb uMWeb = new UMWeb(str3);
        if (i2 == 3) {
            string = str4;
        }
        uMWeb.setDescription(string);
        uMWeb.setThumb(uMImage);
        if (i2 != 3) {
            uMWeb.setTitle(str4);
        }
        new ShareAction(getActivity()).withMedia(uMWeb).setPlatform(ProjectApp.platforms.get(i2).mPlatform).setCallback(this.umShareListener).share();
    }
}
