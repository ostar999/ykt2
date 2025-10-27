package com.psychiatrygarden.activity.circleactivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.adapter.DragAdapter;
import com.psychiatrygarden.adapter.OtherAdapter;
import com.psychiatrygarden.bean.ChannelItem;
import com.psychiatrygarden.bean.ChannelItems;
import com.psychiatrygarden.bean.CircleChannelBean;
import com.psychiatrygarden.bean.HandoutPushDataBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.AsyncHandler;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.widget.DragGrid;
import com.psychiatrygarden.widget.OtherGridView;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class CircleChannelActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private boolean isyuanxiao;
    private boolean iszonghe;
    OtherAdapter otherAdapter;
    private OtherGridView otherGridView;
    DragAdapter userAdapter;
    private DragGrid userGridView;
    private TextView yuanxiao;
    private TextView zonghe;
    ArrayList<ChannelItem> otherChannelList = new ArrayList<>();
    ArrayList<ChannelItem> userChannelList = new ArrayList<>();
    boolean isMove = false;
    private final ArrayList<ChannelItem> zongheData = new ArrayList<>();
    private final ArrayList<ChannelItem> yuanxiaoData = new ArrayList<>();
    private final ArrayList<ChannelItem> otherCommData = new ArrayList<>();
    private String zongheid = "";
    private String yuanxiaoid = "";

    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final GridView clickGridView) {
        int[] iArr = new int[2];
        moveView.getLocationInWindow(iArr);
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View moveView2 = getMoveView(moveViewGroup, moveView, iArr);
        TranslateAnimation translateAnimation = new TranslateAnimation(startLocation[0], endLocation[0], startLocation[1], endLocation[1]);
        translateAnimation.setDuration(500L);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setFillAfter(false);
        animationSet.addAnimation(translateAnimation);
        moveView2.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.activity.circleactivity.CircleChannelActivity.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(moveView2);
                if (clickGridView instanceof DragGrid) {
                    CircleChannelActivity.this.otherAdapter.setVisible(true);
                    CircleChannelActivity.this.otherAdapter.notifyDataSetChanged();
                    CircleChannelActivity.this.userAdapter.remove();
                } else {
                    CircleChannelActivity.this.userAdapter.setVisible(true);
                    CircleChannelActivity.this.userAdapter.notifyDataSetChanged();
                    CircleChannelActivity.this.otherAdapter.remove();
                }
                CircleChannelActivity.this.isMove = false;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                CircleChannelActivity.this.isMove = true;
            }
        });
    }

    private View getMoveView(ViewGroup viewGroup, View view, int[] initLocation) {
        int i2 = initLocation[0];
        int i3 = initLocation[1];
        viewGroup.addView(view);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.leftMargin = i2;
        layoutParams.topMargin = i3;
        view.setLayoutParams(layoutParams);
        return view;
    }

    private ViewGroup getMoveViewGroup() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        viewGroup.setPersistentDrawingCache(1);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        viewGroup.addView(linearLayout);
        return linearLayout;
    }

    private ImageView getView(View view) {
        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(1048576);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmapCreateBitmap);
        return imageView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initData() {
        DragAdapter dragAdapter = new DragAdapter(this, this.userChannelList, true);
        this.userAdapter = dragAdapter;
        this.userGridView.setAdapter((ListAdapter) dragAdapter);
        OtherAdapter otherAdapter = new OtherAdapter(this, this.otherCommData);
        this.otherAdapter = otherAdapter;
        this.otherGridView.setAdapter((ListAdapter) otherAdapter);
        this.otherGridView.setOnItemClickListener(this);
        this.userGridView.setOnItemClickListener(this);
    }

    private void initView() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linexuexiao);
        this.userGridView = (DragGrid) findViewById(R.id.userGridView);
        this.otherGridView = (OtherGridView) findViewById(R.id.otherGridView);
        this.userGridView.setCircleTrue(true);
        this.yuanxiao = (TextView) findViewById(R.id.yuanxiao);
        this.zonghe = (TextView) findViewById(R.id.zonghe);
        linearLayout.setVisibility(0);
        this.yuanxiao.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.i
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11544c.lambda$initView$0(view);
            }
        });
        this.zonghe.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.circleactivity.j
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11548c.lambda$initView$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$0(View view) {
        isSelect(true, false);
        OtherAdapter otherAdapter = this.otherAdapter;
        if (otherAdapter != null) {
            otherAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(View view) {
        isSelect(false, true);
        OtherAdapter otherAdapter = this.otherAdapter;
        if (otherAdapter != null) {
            otherAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemClick$2(ImageView imageView, int[] iArr, int i2) {
        try {
            int[] iArr2 = new int[2];
            OtherGridView otherGridView = this.otherGridView;
            otherGridView.getChildAt(otherGridView.getLastVisiblePosition()).getLocationInWindow(iArr2);
            MoveAnim(imageView, iArr, iArr2, this.userGridView);
            this.userAdapter.setRemove(i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemClick$3(ImageView imageView, int[] iArr, int i2) {
        try {
            int[] iArr2 = new int[2];
            DragGrid dragGrid = this.userGridView;
            dragGrid.getChildAt(dragGrid.getLastVisiblePosition()).getLocationInWindow(iArr2);
            MoveAnim(imageView, iArr, iArr2, this.otherGridView);
            this.otherAdapter.setRemove(i2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void saveChannel() {
        AsyncHandler.post(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.h
            @Override // java.lang.Runnable
            public final void run() {
                this.f11537c.PushData();
            }
        });
    }

    public void PushData() {
        ArrayList<ChannelItem> arrayList = this.userChannelList;
        ArrayList<ChannelItem> arrayList2 = this.otherChannelList;
        ArrayList<ChannelItems> arrayList3 = new ArrayList<>();
        ArrayList<ChannelItems> arrayList4 = new ArrayList<>();
        if (arrayList != null && arrayList.size() > 0) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                ChannelItem channelItem = arrayList.get(i2);
                ChannelItems channelItems = new ChannelItems();
                channelItems.setId(Integer.valueOf(channelItem.getId()));
                channelItems.setTitle(channelItem.getName());
                channelItems.setPid(channelItem.getToday_topic_num());
                channelItems.setInitials(channelItem.getInitials());
                channelItems.setIs_default(channelItem.getSelected());
                if (channelItem.getU_sort() == null || !channelItem.getU_sort().equals("1")) {
                    channelItems.setU_sort("0");
                } else {
                    channelItems.setU_sort(channelItem.getU_sort());
                }
                channelItems.setSort(channelItem.getSort());
                arrayList3.add(channelItems);
            }
        }
        if (arrayList2 != null && arrayList2.size() > 0) {
            for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                ChannelItem channelItem2 = arrayList2.get(i3);
                ChannelItems channelItems2 = new ChannelItems();
                channelItems2.setId(Integer.valueOf(channelItem2.getId()));
                channelItems2.setTitle(channelItem2.getName());
                channelItems2.setIs_default(channelItem2.getSelected());
                channelItems2.setPid(channelItem2.getToday_topic_num());
                channelItems2.setInitials(channelItem2.getInitials());
                channelItems2.setSort(channelItem2.getSort());
                arrayList4.add(channelItems2);
            }
        }
        HandoutPushDataBean handoutPushDataBean = new HandoutPushDataBean();
        handoutPushDataBean.set_default(arrayList3);
        handoutPushDataBean.set_list(arrayList4);
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", new Gson().toJson(handoutPushDataBean));
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.addUserCategorySort, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleChannelActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onStart() {
                super.onStart();
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
            }
        });
    }

    public void getZongheData() {
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.getParentCate, new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.circleactivity.CircleChannelActivity.1
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) throws JSONException {
                super.onSuccess((AnonymousClass1) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        JSONArray jSONArray = jSONObject.getJSONArray("data");
                        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                            if (jSONObject2.optString("name").equals("综合版块")) {
                                CircleChannelActivity.this.zongheid = jSONObject2.optString("id");
                            } else {
                                CircleChannelActivity.this.yuanxiaoid = jSONObject2.optString("id");
                            }
                            for (int i3 = 0; i3 < CircleChannelActivity.this.otherChannelList.size(); i3++) {
                                if (CircleChannelActivity.this.otherChannelList.get(i3).getToday_topic_num().equals(jSONObject2.optString("id"))) {
                                    if (jSONObject2.optString("name").equals("综合版块")) {
                                        CircleChannelActivity.this.zongheData.add(CircleChannelActivity.this.otherChannelList.get(i3));
                                    } else {
                                        CircleChannelActivity.this.yuanxiaoData.add(CircleChannelActivity.this.otherChannelList.get(i3));
                                    }
                                }
                            }
                        }
                        CircleChannelActivity.this.initData();
                        CircleChannelActivity.this.isSelect(false, true);
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    public void isSelect(boolean yuanxiaoTrue, boolean zongheTrue) {
        this.yuanxiao.setSelected(yuanxiaoTrue);
        this.zonghe.setSelected(zongheTrue);
        if (yuanxiaoTrue) {
            this.yuanxiao.setTextColor(ContextCompat.getColor(this, R.color.app_theme_red));
        } else {
            this.yuanxiao.setTextColor(ContextCompat.getColor(this, R.color.showquanzi));
        }
        if (zongheTrue) {
            this.zonghe.setTextColor(ContextCompat.getColor(this, R.color.app_theme_red));
        } else {
            this.zonghe.setTextColor(ContextCompat.getColor(this, R.color.showquanzi));
        }
        this.iszonghe = zongheTrue;
        this.isyuanxiao = yuanxiaoTrue;
        this.otherCommData.clear();
        if (yuanxiaoTrue) {
            this.otherCommData.addAll(this.yuanxiaoData);
        }
        if (zongheTrue) {
            this.otherCommData.addAll(this.zongheData);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("帖子");
        initView();
        this.userChannelList = (ArrayList) getIntent().getSerializableExtra("mDefaultList");
        this.otherChannelList = (ArrayList) getIntent().getSerializableExtra("mUserList");
        getZongheData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    @SuppressLint({"NonConstantResourceId"})
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
        final ImageView view2;
        if (this.isMove) {
            return;
        }
        int id2 = parent.getId();
        if (id2 == R.id.otherGridView) {
            final ImageView view3 = getView(view);
            if (view3 != null) {
                final int[] iArr = new int[2];
                ((TextView) view.findViewById(R.id.text_item)).getLocationInWindow(iArr);
                ChannelItem item = ((OtherAdapter) parent.getAdapter()).getItem(position);
                if (this.iszonghe) {
                    if (!this.zongheid.equals(item.getToday_topic_num())) {
                        AlertToast("请切换至院校版块操作！");
                        return;
                    }
                    this.zongheData.remove(item);
                }
                if (this.isyuanxiao) {
                    if (!this.yuanxiaoid.equals(item.getToday_topic_num())) {
                        AlertToast("请切换至综合版块操作！");
                        return;
                    }
                    this.yuanxiaoData.remove(item);
                }
                this.userAdapter.setVisible(false);
                this.userAdapter.addItem(item);
                new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.g
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f11528c.lambda$onItemClick$3(view3, iArr, position);
                    }
                }, 50L);
                return;
            }
            return;
        }
        if (id2 == R.id.userGridView && position > SharePreferencesUtils.readLongConfig(CommonParameter.circlrListnum, this.mContext, 1L).longValue() - 1 && (view2 = getView(view)) != null) {
            final int[] iArr2 = new int[2];
            ((TextView) view.findViewById(R.id.text_item)).getLocationInWindow(iArr2);
            ChannelItem item2 = ((DragAdapter) parent.getAdapter()).getItem(position);
            if (this.iszonghe) {
                if (!this.zongheid.equals(item2.getToday_topic_num())) {
                    AlertToast("请切换至院校板块操作！");
                    return;
                }
                this.zongheData.add(item2);
            }
            if (this.isyuanxiao) {
                if (!this.yuanxiaoid.equals(item2.getToday_topic_num())) {
                    AlertToast("请切换至综合板块操作！");
                    return;
                }
                this.yuanxiaoData.add(item2);
            }
            this.otherAdapter.setVisible(false);
            this.otherAdapter.addItem(item2);
            new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.activity.circleactivity.f
                @Override // java.lang.Runnable
                public final void run() {
                    this.f11518c.lambda$onItemClick$2(view2, iArr2, position);
                }
            }, 50L);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        DragAdapter dragAdapter = this.userAdapter;
        if (dragAdapter == null || !dragAdapter.isListChanged()) {
            return;
        }
        saveChannel();
        this.otherChannelList.clear();
        this.otherChannelList.addAll(this.yuanxiaoData);
        this.otherChannelList.addAll(this.zongheData);
        EventBus.getDefault().post(new CircleChannelBean(EventBusConstant.EVENT_Circle_CHANGE, this.userAdapter.getChannnelLst(), this.otherChannelList));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.channel);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
