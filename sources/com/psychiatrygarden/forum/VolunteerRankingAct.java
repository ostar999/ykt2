package com.psychiatrygarden.forum;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.plv.livescenes.log.upload.PLVDocumentUploadELog;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.rank.EntranceResultsActivity;
import com.psychiatrygarden.activity.rank.RankEntranceActivity;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.SkinManager;
import com.psychiatrygarden.utils.ToastUtil;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class VolunteerRankingAct extends BaseActivity {
    private String activityId;
    TextView hint1;
    TextView hint3;
    private TextView mBtnOne;
    private TextView mBtnTwo;
    private ImageView mImgOne;
    private ImageView mImgTwo;
    private LinearLayout mLyNew;
    private LinearLayout mLyOld;
    TextView newTitle;
    TextView oldTitle;
    String flag = "1";
    private int mStatus = 0;
    private int mNoAccessStatus = 0;

    private void getStatus() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.volRankStatus, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.forum.VolunteerRankingAct.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
            /* JADX WARN: Removed duplicated region for block: B:22:0x0082  */
            @Override // net.tsz.afinal.http.AjaxCallBack
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void onSuccess(java.lang.String r17) {
                /*
                    Method dump skipped, instructions count: 538
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.forum.VolunteerRankingAct.AnonymousClass1.onSuccess(java.lang.String):void");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$0() {
        Intent intent = new Intent(this, (Class<?>) EntranceResultsActivity.class);
        intent.putExtra("moudle", "2".equals(this.flag) ? Constants.VIA_REPORT_TYPE_SET_AVATAR : Constants.VIA_REPORT_TYPE_WPA_STATE);
        intent.putExtra("type", "2".equals(this.flag) ? "haveimg" : "new");
        intent.putExtra("examId", getIntent().getExtras().getString("examId"));
        intent.putExtra("status", "OTHER");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$1(View view) {
        int i2 = this.mStatus;
        if (i2 == 0) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(PushConstants.INTENT_ACTIVITY_NAME, this.activityId);
            MemInterface.getInstance().setDisType(1);
            MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.forum.b2
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f15320a.lambda$setListenerForWidget$0();
                }
            });
            return;
        }
        if (i2 == 1) {
            ToastUtil.shortToast(this.mContext, "审核中");
            return;
        }
        if (i2 == 3) {
            ToastUtil.shortToast(this.mContext, "审核未通过，无法再次提交");
            return;
        }
        if ("2".equals(this.flag)) {
            Intent intent = new Intent(this, (Class<?>) RankEntranceActivity.class);
            intent.putExtra("moudle", Constants.VIA_REPORT_TYPE_SET_AVATAR);
            intent.putExtra("type", "haveimg");
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent(this, (Class<?>) RankEntranceActivity.class);
        intent2.putExtra("moudle", Constants.VIA_REPORT_TYPE_WPA_STATE);
        intent2.putExtra("type", "new");
        startActivity(intent2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2() {
        Intent intent = new Intent(this, (Class<?>) EntranceResultsActivity.class);
        intent.putExtra("moudle", "2".equals(this.flag) ? Constants.VIA_REPORT_TYPE_SET_AVATAR : Constants.VIA_REPORT_TYPE_WPA_STATE);
        intent.putExtra("type", "2".equals(this.flag) ? "noimg" : "old");
        intent.putExtra("examId", getIntent().getExtras().getString("examId"));
        intent.putExtra("status", "OTHER");
        startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$3(View view) {
        if (this.mNoAccessStatus == 0) {
            AjaxParams ajaxParams = new AjaxParams();
            ajaxParams.put(PushConstants.INTENT_ACTIVITY_NAME, this.activityId);
            MemInterface.getInstance().setDisType(1);
            MemInterface.getInstance().getMemData(this, ajaxParams, false, 0);
            MemInterface.getInstance().setmUShareListener(new MemInterface.UShareListener() { // from class: com.psychiatrygarden.forum.e2
                @Override // com.psychiatrygarden.activity.vip.Utils.MemInterface.UShareListener
                public final void mUShareListener() {
                    this.f15340a.lambda$setListenerForWidget$2();
                }
            });
            return;
        }
        if ("2".equals(this.flag)) {
            Intent intent = new Intent(this, (Class<?>) RankEntranceActivity.class);
            intent.putExtra("moudle", Constants.VIA_REPORT_TYPE_SET_AVATAR);
            intent.putExtra("type", "noimg");
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent(this, (Class<?>) RankEntranceActivity.class);
        intent2.putExtra("moudle", Constants.VIA_REPORT_TYPE_WPA_STATE);
        intent2.putExtra("type", "old");
        startActivity(intent2);
    }

    public static void startActivity(Context context, String flag) {
        Intent intent = new Intent(context, (Class<?>) VolunteerRankingAct.class);
        intent.putExtra("flag", flag);
        context.startActivity(intent);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.flag = getIntent().getStringExtra("flag");
        this.newTitle = (TextView) findViewById(R.id.newTitle);
        this.oldTitle = (TextView) findViewById(R.id.oldTitle);
        this.hint1 = (TextView) findViewById(R.id.hint1);
        this.hint3 = (TextView) findViewById(R.id.hint3);
        this.mBtnOne = (TextView) findViewById(R.id.btn_one);
        this.mBtnTwo = (TextView) findViewById(R.id.btn_two);
        this.mLyNew = (LinearLayout) findViewById(R.id.ly_new);
        this.mLyOld = (LinearLayout) findViewById(R.id.ly_old);
        this.mImgOne = (ImageView) findViewById(R.id.img_one);
        this.mImgTwo = (ImageView) findViewById(R.id.img_two);
        if ("2".equals(this.flag)) {
            setTitle("排名统计");
            this.newTitle.setText("经审核版");
            this.oldTitle.setText("未审核版");
            this.hint1.setText("提交成绩单真实截图，审核通过后进入（截图仅用于审核，不会公开）");
            this.hint3.setText("提交成绩信息即可进入\n成绩未经审核，仅供参考");
            boolean z2 = SkinManager.getCurrentSkinType(this) == 1;
            this.mImgOne.setImageResource(z2 ? R.mipmap.ic_rank_access_night : R.mipmap.ic_rank_access);
            this.mImgTwo.setImageResource(z2 ? R.mipmap.ic_rank_no_access_night : R.mipmap.ic_rank_no_access);
        } else {
            setTitle("志愿统计");
        }
        getStatus();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals(PLVDocumentUploadELog.DocumentUploadEvent.UPLOAD_SUCCESS)) {
            getStatus();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.layout_volunteer_ranking);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.mBtnOne.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.c2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15325c.lambda$setListenerForWidget$1(view);
            }
        });
        this.mBtnTwo.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.forum.d2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15331c.lambda$setListenerForWidget$3(view);
            }
        });
    }
}
