package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.purchase.adapter.CommAdapter;
import com.psychiatrygarden.activity.purchase.adapter.ViewHolder;
import com.psychiatrygarden.bean.ReportReason;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.interfaceclass.OnClickDiogListenter;
import com.psychiatrygarden.utils.AnimUtil;
import com.psychiatrygarden.utils.weblong.SizeUtil;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONObject;

/* loaded from: classes6.dex */
public class PopupComWindow extends PopupWindow implements View.OnClickListener {
    private boolean landScape;
    private LinearLayout line_window;
    private List<String> lists;
    private ListView listview;
    private Activity mActivity;
    private CommAdapter<String> mCommAdapter;
    private OnClickDiogListenter mDiogListenter;
    private List<ReportReason> mReportReasonList;
    private TextView quxiao;
    private RelativeLayout rel_view;
    private String[] strs;
    private String[] strs1;
    private TextView tv_txt;
    private TextView tv_txt_bioaji;
    private View view;

    /* renamed from: com.psychiatrygarden.widget.PopupComWindow$1, reason: invalid class name */
    public class AnonymousClass1 extends CommAdapter<String> {
        final /* synthetic */ OnClickDiogListenter val$clickListener;
        final /* synthetic */ int val$type;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(List mData, Context mcontext, int layoutId, final int val$type, final OnClickDiogListenter val$clickListener) {
            super(mData, mcontext, layoutId);
            this.val$type = val$type;
            this.val$clickListener = val$clickListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(int i2, int i3, OnClickDiogListenter onClickDiogListenter, View view) {
            String str = "";
            if (i2 != 1) {
                str = (i3 + 1) + "";
            } else if (i3 == 0) {
                str = "-1";
            } else if (i3 == 1) {
                str = "1";
            } else if (i3 == 2) {
                str = "3";
            } else if (i3 == 3) {
                str = "7";
            } else if (i3 == 4) {
                str = "30";
            }
            onClickDiogListenter.onDiogClick(str, PopupComWindow.this);
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(ViewHolder vHolder, final String str, final int position) {
            TextView textView = (TextView) vHolder.getView(R.id.tv_filtrate1);
            ImageView imageView = (ImageView) vHolder.getView(R.id.iv_filtrate1);
            RelativeLayout relativeLayout = (RelativeLayout) vHolder.getView(R.id.rl_filtrate1);
            vHolder.getView(R.id.line).setVisibility(position == PopupComWindow.this.lists.size() + (-1) ? 8 : 0);
            imageView.setVisibility(8);
            textView.setText(str);
            final int i2 = this.val$type;
            final OnClickDiogListenter onClickDiogListenter = this.val$clickListener;
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.od
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16763c.lambda$convert$0(i2, position, onClickDiogListenter, view);
                }
            });
        }
    }

    /* renamed from: com.psychiatrygarden.widget.PopupComWindow$2, reason: invalid class name */
    public class AnonymousClass2 extends CommAdapter<String> {
        final /* synthetic */ OnClickDiogListenter val$clickListener;
        final /* synthetic */ int val$type;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(List mData, Context mcontext, int layoutId, final int val$type, final OnClickDiogListenter val$clickListener) {
            super(mData, mcontext, layoutId);
            this.val$type = val$type;
            this.val$clickListener = val$clickListener;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$convert$0(int i2, int i3, OnClickDiogListenter onClickDiogListenter, View view) {
            String str = "";
            if (i2 != 1) {
                str = (i3 + 1) + "";
            } else if (i3 == 0) {
                str = "-1";
            } else if (i3 == 1) {
                str = "1";
            } else if (i3 == 2) {
                str = "3";
            } else if (i3 == 3) {
                str = "7";
            } else if (i3 == 4) {
                str = "30";
            }
            onClickDiogListenter.onDiogClick(str, PopupComWindow.this);
        }

        @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
        public void convert(ViewHolder vHolder, final String str, final int position) {
            TextView textView = (TextView) vHolder.getView(R.id.tv_filtrate1);
            ImageView imageView = (ImageView) vHolder.getView(R.id.iv_filtrate1);
            RelativeLayout relativeLayout = (RelativeLayout) vHolder.getView(R.id.rl_filtrate1);
            vHolder.getView(R.id.line).setVisibility(position == PopupComWindow.this.lists.size() + (-1) ? 8 : 0);
            imageView.setVisibility(8);
            textView.setText(str);
            final int i2 = this.val$type;
            final OnClickDiogListenter onClickDiogListenter = this.val$clickListener;
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.pd
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f16794c.lambda$convert$0(i2, position, onClickDiogListenter, view);
                }
            });
        }
    }

    public PopupComWindow(Activity mActivity, String tv_txtstr, String tv_txt_bioajistr, final int type, final OnClickDiogListenter clickListener) {
        super(mActivity);
        this.strs = new String[]{"永久禁言", "禁言1天", "禁言3天", "禁言7天", "禁言30天"};
        this.strs1 = new String[]{"复制解析或复制评论的言论", "MARK、马克等标记性言论", "无意义言论", "广告、水军、带节奏的言论", "错误的、误导性的言论", "色情、反动、人身攻击的言论"};
        this.lists = new ArrayList();
        this.mDiogListenter = clickListener;
        this.mActivity = mActivity;
        View viewInflate = ((LayoutInflater) mActivity.getSystemService("layout_inflater")).inflate(R.layout.activity_adminuser, (ViewGroup) null);
        this.view = viewInflate;
        setContentView(viewInflate);
        this.view.measure(0, 0);
        setBackgroundDrawable(new ColorDrawable(0));
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
        setOutsideTouchable(true);
        setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.widget.ld
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                PopupComWindow.lambda$new$0();
            }
        });
        update();
        this.lists.clear();
        if (type == 1) {
            Collections.addAll(this.lists, this.strs);
        } else if (type == 2) {
            this.mReportReasonList = new ArrayList();
            getReportReasons();
        } else if (type == 3) {
            this.lists.addAll(Arrays.asList(this.strs1));
        }
        this.tv_txt = (TextView) this.view.findViewById(R.id.tv_txt);
        this.tv_txt_bioaji = (TextView) this.view.findViewById(R.id.tv_txt_bioaji);
        this.listview = (ListView) this.view.findViewById(R.id.listview);
        this.quxiao = (TextView) this.view.findViewById(R.id.quxiao);
        RelativeLayout relativeLayout = (RelativeLayout) this.view.findViewById(R.id.rel_view);
        this.rel_view = relativeLayout;
        relativeLayout.setOnClickListener(this);
        final int iDp2px = mActivity.getResources().getDisplayMetrics().heightPixels - SizeUtil.dp2px(mActivity, 96);
        LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.line_window);
        this.line_window = linearLayout;
        AnimUtil.fromBottomToTopAnim(linearLayout);
        this.quxiao.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.md
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16709c.lambda$new$1(view);
            }
        });
        this.tv_txt.setText(tv_txtstr);
        if (TextUtils.isEmpty(tv_txt_bioajistr)) {
            this.tv_txt_bioaji.setVisibility(8);
            this.tv_txt_bioaji.setText("");
        } else {
            this.tv_txt_bioaji.setVisibility(0);
            this.tv_txt_bioaji.setText(tv_txt_bioajistr);
        }
        if (type != 2) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.lists, mActivity, R.layout.activity_admimnlist, type, clickListener);
            this.mCommAdapter = anonymousClass1;
            this.listview.setAdapter((ListAdapter) anonymousClass1);
        }
        this.view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.nd
            @Override // java.lang.Runnable
            public final void run() {
                this.f16736c.lambda$new$2(iDp2px);
            }
        }, 300L);
    }

    private void getReportReasons() {
        YJYHttpUtils.get(this.mActivity, NetworkRequestsURL.reportReason, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.widget.PopupComWindow.3

            /* renamed from: com.psychiatrygarden.widget.PopupComWindow$3$2, reason: invalid class name */
            public class AnonymousClass2 extends CommAdapter<ReportReason> {
                final /* synthetic */ List val$reasonList;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass2(List mData, Context mcontext, int layoutId, final List val$reasonList) {
                    super(mData, mcontext, layoutId);
                    this.val$reasonList = val$reasonList;
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$convert$0(View view) {
                    if (PopupComWindow.this.mDiogListenter != null) {
                        PopupComWindow.this.mDiogListenter.onDiogClick(String.valueOf(view.getTag()), PopupComWindow.this);
                    }
                }

                @Override // com.psychiatrygarden.activity.purchase.adapter.CommAdapter
                public void convert(ViewHolder vHolder, ReportReason reportReason, int position) {
                    TextView textView = (TextView) vHolder.getView(R.id.tv_filtrate1);
                    ImageView imageView = (ImageView) vHolder.getView(R.id.iv_filtrate1);
                    RelativeLayout relativeLayout = (RelativeLayout) vHolder.getView(R.id.rl_filtrate1);
                    vHolder.getView(R.id.line).setVisibility(position == this.val$reasonList.size() + (-1) ? 8 : 0);
                    imageView.setVisibility(8);
                    textView.setText(reportReason.getTitle());
                    relativeLayout.setTag(reportReason.getId());
                    relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.qd
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f16827c.lambda$convert$0(view);
                        }
                    });
                }
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if ("200".equals(jSONObject.optString("code", ""))) {
                        List list = (List) new Gson().fromJson(jSONObject.optString("data", ""), new TypeToken<List<ReportReason>>() { // from class: com.psychiatrygarden.widget.PopupComWindow.3.1
                        }.getType());
                        if (list == null || list.size() <= 0) {
                            return;
                        }
                        PopupComWindow.this.mReportReasonList = new ArrayList(list);
                        PopupComWindow.this.listview.setAdapter((ListAdapter) new AnonymousClass2(list, PopupComWindow.this.mActivity, R.layout.activity_admimnlist, list));
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        dismiss();
        this.line_window.clearAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(int i2) {
        if (this.view.getMeasuredHeight() > i2) {
            try {
                ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
                layoutParams.height = i2;
                this.view.setLayoutParams(layoutParams);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$3() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$4(View view) {
        dismiss();
        this.line_window.clearAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(int i2) {
        if (this.view.getMeasuredHeight() > i2) {
            try {
                ViewGroup.LayoutParams layoutParams = this.view.getLayoutParams();
                layoutParams.height = i2;
                this.view.setLayoutParams(layoutParams);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // android.view.View.OnClickListener
    @SuppressLint({"NewApi"})
    public void onClick(View v2) {
        if (v2.getId() == R.id.rel_view) {
            dismiss();
            this.line_window.clearAnimation();
        }
    }

    public void showPopUp(View view) {
        showAtLocation(view, 80, 0, 0);
    }

    public PopupComWindow(Activity mActivity, String tv_txtstr, String tv_txt_bioajistr, final int type, final OnClickDiogListenter clickListener, boolean landScape) {
        super(mActivity);
        this.strs = new String[]{"永久禁言", "禁言1天", "禁言3天", "禁言7天", "禁言30天"};
        this.strs1 = new String[]{"复制解析或复制评论的言论", "MARK、马克等标记性言论", "无意义言论", "广告、水军、带节奏的言论", "错误的、误导性的言论", "色情、反动、人身攻击的言论"};
        this.lists = new ArrayList();
        this.landScape = landScape;
        this.mDiogListenter = clickListener;
        this.mActivity = mActivity;
        View viewInflate = ((LayoutInflater) mActivity.getSystemService("layout_inflater")).inflate(R.layout.activity_adminuser_landscape, (ViewGroup) null);
        this.view = viewInflate;
        setContentView(viewInflate);
        this.view.measure(0, 0);
        setBackgroundDrawable(new ColorDrawable(0));
        setWidth(-1);
        setHeight(-2);
        setFocusable(true);
        setOutsideTouchable(true);
        setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.psychiatrygarden.widget.id
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                PopupComWindow.lambda$new$3();
            }
        });
        update();
        this.lists.clear();
        if (type == 1) {
            Collections.addAll(this.lists, this.strs);
        } else if (type == 2) {
            this.mReportReasonList = new ArrayList();
            getReportReasons();
        } else if (type == 3) {
            this.lists.addAll(Arrays.asList(this.strs1));
        }
        this.tv_txt = (TextView) this.view.findViewById(R.id.tv_txt);
        this.tv_txt_bioaji = (TextView) this.view.findViewById(R.id.tv_txt_bioaji);
        this.listview = (ListView) this.view.findViewById(R.id.listview);
        this.quxiao = (TextView) this.view.findViewById(R.id.quxiao);
        RelativeLayout relativeLayout = (RelativeLayout) this.view.findViewById(R.id.rel_view);
        this.rel_view = relativeLayout;
        relativeLayout.setOnClickListener(this);
        final int iDp2px = mActivity.getResources().getDisplayMetrics().heightPixels - SizeUtil.dp2px(mActivity, 96);
        LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.line_window);
        this.line_window = linearLayout;
        AnimUtil.fromBottomToTopAnim(linearLayout);
        this.quxiao.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.jd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16619c.lambda$new$4(view);
            }
        });
        this.tv_txt.setText(tv_txtstr);
        if (TextUtils.isEmpty(tv_txt_bioajistr)) {
            this.tv_txt_bioaji.setVisibility(8);
            this.tv_txt_bioaji.setText("");
        } else {
            this.tv_txt_bioaji.setVisibility(0);
            this.tv_txt_bioaji.setText(tv_txt_bioajistr);
        }
        if (type != 2) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.lists, mActivity, R.layout.activity_admimnlist, type, clickListener);
            this.mCommAdapter = anonymousClass2;
            this.listview.setAdapter((ListAdapter) anonymousClass2);
        }
        this.view.postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.kd
            @Override // java.lang.Runnable
            public final void run() {
                this.f16649c.lambda$new$5(iDp2px);
            }
        }, 200L);
    }
}
