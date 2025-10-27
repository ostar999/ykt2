package com.psychiatrygarden.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.adapter.DragNewAdapter;
import com.psychiatrygarden.adapter.OtherNewAdapter;
import com.psychiatrygarden.bean.ChannelItem;
import com.psychiatrygarden.bean.EveHandoutBean;
import com.psychiatrygarden.bean.HandoutChannelBean;
import com.psychiatrygarden.db.SharePreferencesUtils;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonParameter;
import com.psychiatrygarden.widget.DragGrid;
import com.psychiatrygarden.widget.DragListView;
import com.psychiatrygarden.widget.sortlist.SideBar;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/* loaded from: classes5.dex */
public class ChannelNewActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private LinearLayout idScrollLine;
    private HorizontalScrollView mColumnHorizontalScrollView;
    OtherNewAdapter otherAdapter;
    private ListView otherGridView;
    DragNewAdapter userAdapter;
    private DragListView userGridView;
    List<HandoutChannelBean.DataBean.ListBean> otherChannelList = new ArrayList();
    List<HandoutChannelBean.DataBean.DefaultBean> userChannelList = new ArrayList();
    private List<HandoutChannelBean.DataBean.ListBean.SubBean> sub = new ArrayList();
    boolean isMove = false;

    private void MoveAnim(View moveView, int[] startLocation, int[] endLocation, final ChannelItem moveChannel, final GridView clickGridView) {
        int[] iArr = new int[2];
        moveView.getLocationInWindow(iArr);
        final ViewGroup moveViewGroup = getMoveViewGroup();
        final View moveView2 = getMoveView(moveViewGroup, moveView, iArr);
        TranslateAnimation translateAnimation = new TranslateAnimation(startLocation[0], endLocation[0], startLocation[1], endLocation[1]);
        translateAnimation.setDuration(300L);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setFillAfter(false);
        animationSet.addAnimation(translateAnimation);
        moveView2.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.psychiatrygarden.activity.ChannelNewActivity.2
            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                moveViewGroup.removeView(moveView2);
                if (clickGridView instanceof DragGrid) {
                    ChannelNewActivity.this.otherAdapter.setVisible(true);
                    ChannelNewActivity.this.otherAdapter.notifyDataSetChanged();
                    ChannelNewActivity.this.userAdapter.remove();
                } else {
                    ChannelNewActivity.this.userAdapter.setVisible(true);
                    ChannelNewActivity.this.userAdapter.notifyDataSetChanged();
                    ChannelNewActivity.this.otherAdapter.remove();
                }
                ChannelNewActivity.this.isMove = false;
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationRepeat(Animation animation) {
            }

            @Override // android.view.animation.Animation.AnimationListener
            public void onAnimationStart(Animation animation) {
                ChannelNewActivity.this.isMove = true;
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
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        viewGroup.addView(linearLayout);
        return linearLayout;
    }

    private ImageView getView(View view) {
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(bitmapCreateBitmap);
        return imageView;
    }

    private void initData() {
        this.userChannelList = (List) getIntent().getSerializableExtra("defaultList");
        this.otherChannelList = (List) getIntent().getSerializableExtra("userList");
        this.idScrollLine.removeAllViews();
        if (SharePreferencesUtils.readStrConfig(CommonParameter.Student_Type, this.mContext).equals("z")) {
            this.mColumnHorizontalScrollView.setVisibility(8);
        } else {
            this.mColumnHorizontalScrollView.setVisibility(0);
        }
        List<HandoutChannelBean.DataBean.ListBean> list = this.otherChannelList;
        if (list != null && list.size() > 0) {
            for (final int i2 = 0; i2 < this.otherChannelList.size(); i2++) {
                final TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_hitem, (ViewGroup) null, false);
                textView.setText(this.otherChannelList.get(i2).getP_name());
                this.idScrollLine.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.s1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        this.f13813c.lambda$initData$0(textView, i2, view);
                    }
                });
                if (i2 == 0) {
                    textView.setSelected(true);
                    this.sub.addAll(this.otherChannelList.get(0).getSub());
                }
            }
        }
        DragNewAdapter dragNewAdapter = new DragNewAdapter(this, this.userChannelList);
        this.userAdapter = dragNewAdapter;
        this.userGridView.setAdapter((ListAdapter) dragNewAdapter);
        OtherNewAdapter otherNewAdapter = new OtherNewAdapter(this, this.sub);
        this.otherAdapter = otherNewAdapter;
        this.otherGridView.setAdapter((ListAdapter) otherNewAdapter);
        this.otherGridView.setOnItemClickListener(this);
        this.userGridView.setOnItemClickListener(this);
    }

    private void initView() {
        View viewInflate = LayoutInflater.from(this).inflate(R.layout.layout_jingyan_header, (ViewGroup) null);
        this.userGridView = (DragListView) viewInflate.findViewById(R.id.userGridView);
        this.otherGridView = (ListView) findViewById(R.id.otherGridView);
        this.idScrollLine = (LinearLayout) viewInflate.findViewById(R.id.idScrollLine);
        this.mColumnHorizontalScrollView = (HorizontalScrollView) viewInflate.findViewById(R.id.mColumnHorizontalScrollView);
        this.otherGridView.addHeaderView(viewInflate);
        SideBar sideBar = (SideBar) findViewById(R.id.sidrbar);
        sideBar.setTextView((TextView) findViewById(R.id.dialog));
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() { // from class: com.psychiatrygarden.activity.r1
            @Override // com.psychiatrygarden.widget.sortlist.SideBar.OnTouchingLetterChangedListener
            public final void onTouchingLetterChanged(String str) {
                this.f13754a.lambda$initView$1(str);
            }
        });
        this.otherGridView.setOnScrollListener(new AbsListView.OnScrollListener() { // from class: com.psychiatrygarden.activity.ChannelNewActivity.1
            @Override // android.widget.AbsListView.OnScrollListener
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                DragNewAdapter dragNewAdapter;
                if (firstVisibleItem == 0 && (dragNewAdapter = ChannelNewActivity.this.userAdapter) != null && dragNewAdapter.isListChanged()) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.addAll(ChannelNewActivity.this.userChannelList);
                    ChannelNewActivity.this.userChannelList.clear();
                    ChannelNewActivity.this.userChannelList.addAll(arrayList);
                    ChannelNewActivity.this.userAdapter.notifyDataSetChanged();
                }
            }

            @Override // android.widget.AbsListView.OnScrollListener
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initData$0(TextView textView, int i2, View view) {
        setTabView();
        textView.setSelected(true);
        this.sub.clear();
        this.sub.addAll(this.otherChannelList.get(i2).getSub());
        OtherNewAdapter otherNewAdapter = this.otherAdapter;
        if (otherNewAdapter != null) {
            otherNewAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$1(String str) {
        try {
            int positionForSection = this.otherAdapter.getPositionForSection(str.charAt(0));
            if (positionForSection != -1) {
                this.otherGridView.setSelection(positionForSection + 1);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveChannel$2(ObservableEmitter observableEmitter) throws Exception {
        PushData();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$saveChannel$3(Object obj) throws Exception {
    }

    private void saveChannel() {
        Observable.create(new ObservableOnSubscribe() { // from class: com.psychiatrygarden.activity.t1
            @Override // io.reactivex.ObservableOnSubscribe
            public final void subscribe(ObservableEmitter observableEmitter) throws Exception {
                this.f13937a.lambda$saveChannel$2(observableEmitter);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.psychiatrygarden.activity.u1
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) throws Exception {
                ChannelNewActivity.lambda$saveChannel$3(obj);
            }
        });
    }

    public void PushData() {
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("content", new Gson().toJson(this.userChannelList));
        YJYHttpUtils.post(ProjectApp.instance(), NetworkRequestsURL.mAddUsortUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.ChannelNewActivity.3
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

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("经验");
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        saveChannel();
        EventBus.getDefault().post(new EveHandoutBean("handout", this.otherChannelList, this.userChannelList));
        super.onDestroy();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
        if (this.isMove) {
            return;
        }
        int id2 = parent.getId();
        int i2 = 0;
        if (id2 != R.id.otherGridView) {
            if (id2 == R.id.userGridView && position > SharePreferencesUtils.readLongConfig(CommonParameter.Lock_NUM_TYPE, this.mContext, 1L).longValue() - 1) {
                for (int i3 = 0; i3 < this.otherChannelList.size(); i3++) {
                    if (this.userChannelList.get(position).getPid().equals(this.otherChannelList.get(i3).getP_id())) {
                        for (int i4 = 0; i4 < this.otherChannelList.get(i3).getSub().size(); i4++) {
                            if (this.userChannelList.get(position).getId().equals(this.otherChannelList.get(i3).getSub().get(i4).getId())) {
                                this.otherChannelList.get(i3).getSub().get(i4).setSelected("0");
                            }
                        }
                    }
                }
                while (i2 < this.sub.size()) {
                    if (this.sub.get(i2).getId().equals(this.userChannelList.get(position).getId())) {
                        this.sub.get(i2).setSelected("0");
                    }
                    i2++;
                }
                this.userChannelList.remove(position);
                this.userAdapter.notifyDataSetChanged();
                this.otherAdapter.notifyDataSetChanged();
                return;
            }
            return;
        }
        int i5 = position - 1;
        if (this.sub.get(i5).getSelected().equals("1")) {
            while (i2 < this.userChannelList.size()) {
                if (this.sub.get(i5).getId().equals(this.userChannelList.get(i2).getId())) {
                    this.userChannelList.remove(i2);
                }
                i2++;
            }
            this.sub.get(i5).setSelected("0");
        } else {
            while (i2 < this.userChannelList.size()) {
                if (this.userChannelList.get(i2).getId().equals(this.sub.get(i5).getId())) {
                    AlertToast("此栏目已存在默认栏目中");
                    return;
                }
                i2++;
            }
            HandoutChannelBean.DataBean.DefaultBean defaultBean = new HandoutChannelBean.DataBean.DefaultBean();
            defaultBean.setId(this.sub.get(i5).getId());
            defaultBean.setPid(this.sub.get(i5).getPid());
            defaultBean.setPname(this.sub.get(i5).getPname());
            defaultBean.setSelected("1");
            defaultBean.setTitle(this.sub.get(i5).getTitle());
            defaultBean.setSort(this.sub.get(i5).getSort());
            defaultBean.setInitials(this.sub.get(i5).getSortLetters());
            this.userAdapter.addItem(defaultBean);
            this.sub.get(i5).setSelected("1");
        }
        this.otherAdapter.notifyDataSetChanged();
        this.userAdapter.notifyDataSetChanged();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_channel_new);
        initView();
        initData();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }

    public void setTabView() {
        int childCount = this.idScrollLine.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            ((TextView) this.idScrollLine.getChildAt(i2)).setSelected(false);
        }
    }
}
