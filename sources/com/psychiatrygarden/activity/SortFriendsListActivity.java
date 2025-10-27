package com.psychiatrygarden.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.psychiatrygarden.adapter.EmptyAdapter;
import com.psychiatrygarden.bean.SortModel;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.LogUtils;
import com.psychiatrygarden.widget.ClearEditText;
import com.psychiatrygarden.widget.sortlist.CharacterParser;
import com.psychiatrygarden.widget.sortlist.PinyinComparator;
import com.psychiatrygarden.widget.sortlist.SideBar;
import com.psychiatrygarden.widget.sortlist.SortAdapter;
import com.psychiatrygarden.widget.swipemenu.SwipeMenu;
import com.psychiatrygarden.widget.swipemenu.SwipeMenuCreator;
import com.psychiatrygarden.widget.swipemenu.SwipeMenuItem;
import com.psychiatrygarden.widget.swipemenu.SwipeMenuListView;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class SortFriendsListActivity extends BaseActivity {
    private SortAdapter adapter;
    private CharacterParser characterParser;
    private ClearEditText mClearEditText;
    private ImageView mIvNoData;
    private PinyinComparator pinyinComparator;
    private SwipeMenuListView sortListView;
    private final List<SortModel> SourceDateList = new ArrayList();
    List<SortModel> filterDateList = new ArrayList();
    private int index = 0;

    /* renamed from: com.psychiatrygarden.activity.SortFriendsListActivity$2, reason: invalid class name */
    public class AnonymousClass2 extends AjaxCallBack<String> {
        final /* synthetic */ SortModel val$mSortModel;

        public AnonymousClass2(final SortModel val$mSortModel) {
            this.val$mSortModel = val$mSortModel;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0() {
            SortFriendsListActivity.this.adapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$1() {
            SortFriendsListActivity.this.adapter.notifyDataSetChanged();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onFailure(Throwable t2, int errorNo, String strMsg) {
            super.onFailure(t2, errorNo, strMsg);
            SortFriendsListActivity.this.hideProgressDialog();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onStart() {
            super.onStart();
        }

        @Override // net.tsz.afinal.http.AjaxCallBack
        public void onSuccess(String s2) {
            super.onSuccess((AnonymousClass2) s2);
            try {
                if (new JSONObject(s2).optString("code").equals("200")) {
                    SortFriendsListActivity.this.AlertToast("取消关注成功");
                    if (SortFriendsListActivity.this.filterDateList.size() > 0) {
                        SortFriendsListActivity.this.filterDateList.remove(this.val$mSortModel);
                        SortFriendsListActivity.this.SourceDateList.remove(this.val$mSortModel);
                        if (SortFriendsListActivity.this.filterDateList.size() <= 0) {
                            SortFriendsListActivity.this.sortListView.setAdapter((ListAdapter) new EmptyAdapter());
                        } else {
                            SortFriendsListActivity.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.qk
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f13739c.lambda$onSuccess$0();
                                }
                            });
                        }
                    } else {
                        SortFriendsListActivity.this.SourceDateList.remove(this.val$mSortModel);
                        if (SortFriendsListActivity.this.SourceDateList.size() <= 0) {
                            SortFriendsListActivity.this.sortListView.setAdapter((ListAdapter) new EmptyAdapter());
                        } else {
                            SortFriendsListActivity.this.runOnUiThread(new Runnable() { // from class: com.psychiatrygarden.activity.rk
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f13802c.lambda$onSuccess$1();
                                }
                            });
                        }
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            SortFriendsListActivity.this.hideProgressDialog();
        }
    }

    private void CancelFriends(final SortModel mSortModel) {
        showProgressDialog();
        AjaxParams ajaxParams = new AjaxParams();
        ajaxParams.put("from_user_id", UserConfig.getUserId());
        ajaxParams.put("to_user_id", mSortModel.getUser_id());
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mChatUnFollow, ajaxParams, new AnonymousClass2(mSortModel));
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, dp, getResources().getDisplayMetrics());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void filterData(String filterStr) {
        if (TextUtils.isEmpty(filterStr)) {
            this.filterDateList.clear();
            Collections.sort(this.SourceDateList, this.pinyinComparator);
            SortAdapter sortAdapter = new SortAdapter(this, this.SourceDateList);
            this.adapter = sortAdapter;
            this.sortListView.setAdapter((ListAdapter) sortAdapter);
            return;
        }
        this.filterDateList.clear();
        for (SortModel sortModel : this.SourceDateList) {
            String nickname = sortModel.getNickname();
            if (nickname.contains(filterStr) || this.characterParser.getSelling(nickname).startsWith(filterStr)) {
                this.filterDateList.add(sortModel);
            }
        }
        Collections.sort(this.filterDateList, this.pinyinComparator);
        SortAdapter sortAdapter2 = new SortAdapter(this, this.filterDateList);
        this.adapter = sortAdapter2;
        this.sortListView.setAdapter((ListAdapter) sortAdapter2);
    }

    private void getFriendsList() {
        showProgressDialog();
        YJYHttpUtils.post(this.mContext, NetworkRequestsURL.mChatFollowlist + "/from_user_id/" + UserConfig.getUserId(), new AjaxParams(), new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.SortFriendsListActivity.3
            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onFailure(Throwable t2, int errorNo, String strMsg) {
                super.onFailure(t2, errorNo, strMsg);
                SortFriendsListActivity.this.hideProgressDialog();
                SortFriendsListActivity.this.AlertToast("请求失败");
            }

            @Override // net.tsz.afinal.http.AjaxCallBack
            public void onSuccess(String s2) {
                super.onSuccess((AnonymousClass3) s2);
                try {
                    JSONObject jSONObject = new JSONObject(s2);
                    if (jSONObject.optString("code").equals("200")) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        JSONArray jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("other");
                        for (int i2 = 0; i2 < jSONArrayOptJSONArray.length(); i2++) {
                            JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray.optJSONObject(i2);
                            SortModel sortModel = new SortModel();
                            sortModel.setAvatar(jSONObjectOptJSONObject2.optString("avatar"));
                            sortModel.setUser_id(jSONObjectOptJSONObject2.optString("user_id"));
                            sortModel.setNickname(jSONObjectOptJSONObject2.optString("nickname"));
                            String upperCase = SortFriendsListActivity.this.characterParser.getSelling(jSONObjectOptJSONObject2.optString("nickname")).substring(0, 1).toUpperCase();
                            if (upperCase.matches("[A-Z]")) {
                                sortModel.setSortLetters(upperCase.toUpperCase());
                            } else {
                                sortModel.setSortLetters(DictionaryFactory.SHARP);
                            }
                            SortFriendsListActivity.this.SourceDateList.add(sortModel);
                        }
                        JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray("service");
                        for (int length = jSONArrayOptJSONArray2.length() - 1; length >= 0; length--) {
                            JSONObject jSONObjectOptJSONObject3 = jSONArrayOptJSONArray2.optJSONObject(length);
                            SortModel sortModel2 = new SortModel();
                            sortModel2.setAvatar(jSONObjectOptJSONObject3.optString("avatar"));
                            sortModel2.setUser_id(jSONObjectOptJSONObject3.optString("user_id"));
                            sortModel2.setNickname(jSONObjectOptJSONObject3.optString("nickname"));
                            sortModel2.setUser_uuid(jSONObjectOptJSONObject3.optString("user_uuid"));
                            sortModel2.setWechat_corpid(jSONObjectOptJSONObject3.optString("wechat_corpid"));
                            sortModel2.setWechat_enterprise_url(jSONObjectOptJSONObject3.optString("wechat_enterprise_url"));
                            sortModel2.setSortLetters("↑");
                            SortFriendsListActivity.this.SourceDateList.add(sortModel2);
                        }
                        Collections.sort(SortFriendsListActivity.this.SourceDateList, SortFriendsListActivity.this.pinyinComparator);
                        if (SortFriendsListActivity.this.SourceDateList.size() <= 0) {
                            SortFriendsListActivity.this.mIvNoData.setVisibility(0);
                            SortFriendsListActivity.this.mClearEditText.setVisibility(8);
                        } else {
                            SortFriendsListActivity.this.mIvNoData.setVisibility(8);
                            SortFriendsListActivity sortFriendsListActivity = SortFriendsListActivity.this;
                            SortFriendsListActivity sortFriendsListActivity2 = SortFriendsListActivity.this;
                            sortFriendsListActivity.adapter = new SortAdapter(sortFriendsListActivity2, sortFriendsListActivity2.SourceDateList);
                            SortFriendsListActivity.this.sortListView.setAdapter((ListAdapter) SortFriendsListActivity.this.adapter);
                            SortFriendsListActivity.this.mClearEditText.setVisibility(0);
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                SortFriendsListActivity.this.hideProgressDialog();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(String str) {
        try {
            int positionForSection = this.adapter.getPositionForSection(str.charAt(0));
            if (positionForSection != -1) {
                this.sortListView.setSelection(positionForSection);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(AdapterView adapterView, View view, int i2, long j2) {
        if (((SortModel) this.adapter.getItem(i2)).getSortLetters().equals("↑")) {
            CommonUtil.mWChat(this, ((SortModel) this.adapter.getItem(i2)).getWechat_corpid(), ((SortModel) this.adapter.getItem(i2)).getWechat_enterprise_url());
            return;
        }
        Intent intent = new Intent(this.mContext, (Class<?>) UserCommentInfoActivity.class);
        intent.putExtra("user_id", ((SortModel) this.adapter.getItem(i2)).getUser_id());
        intent.putExtra("jiav", "0");
        intent.addFlags(67108864);
        this.mContext.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$2(SwipeMenu swipeMenu, int i2) {
        int sectionForPosition = this.adapter.getSectionForPosition(i2);
        LogUtils.e("slide", "index===>" + i2 + ":" + sectionForPosition);
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(getApplicationContext());
        swipeMenuItem.setId(this.index);
        swipeMenuItem.setBackground(R.color.app_theme_red);
        swipeMenuItem.setShowSort(i2 == this.adapter.getPositionForSection(sectionForPosition));
        swipeMenuItem.setWidth(dp2px(90));
        swipeMenuItem.setHeight(dp2px(65));
        swipeMenuItem.setTitle("取消关注");
        swipeMenuItem.setTitleSize(14);
        swipeMenuItem.setTitleColor(-1);
        swipeMenu.addMenuItem(swipeMenuItem);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setListenerForWidget$3(int i2, SwipeMenu swipeMenu, int i3) {
        if (i3 != 0) {
            return true;
        }
        if (this.filterDateList.size() > 0) {
            CancelFriends(this.filterDateList.get(i2));
            return true;
        }
        CancelFriends(this.SourceDateList.get(i2));
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        setTitle("我的关注");
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.framelayout_friends_list);
        this.characterParser = CharacterParser.getInstance();
        this.pinyinComparator = new PinyinComparator();
        SideBar sideBar = (SideBar) findViewById(R.id.sidrbar);
        TextView textView = (TextView) findViewById(R.id.dialog);
        this.mIvNoData = (ImageView) findViewById(R.id.iv_no_data);
        sideBar.setTextView(textView);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() { // from class: com.psychiatrygarden.activity.ok
            @Override // com.psychiatrygarden.widget.sortlist.SideBar.OnTouchingLetterChangedListener
            public final void onTouchingLetterChanged(String str) {
                this.f13083a.lambda$init$0(str);
            }
        });
        SwipeMenuListView swipeMenuListView = (SwipeMenuListView) findViewById(R.id.country_lvcountry);
        this.sortListView = swipeMenuListView;
        swipeMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.pk
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f13547c.lambda$init$1(adapterView, view, i2, j2);
            }
        });
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.fragment_friends);
        ClearEditText clearEditText = (ClearEditText) findViewById(R.id.filter_edit);
        this.mClearEditText = clearEditText;
        clearEditText.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.activity.SortFriendsListActivity.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
                SortFriendsListActivity.this.filterData(s2.toString());
            }
        });
        frameLayout.setVisibility(8);
        this.mClearEditText.setVisibility(0);
        frameLayout2.setVisibility(0);
        getFriendsList();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
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
        setContentView(R.layout.activity_sort_friends_list);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        this.sortListView.setMenuCreator(new SwipeMenuCreator() { // from class: com.psychiatrygarden.activity.mk
            @Override // com.psychiatrygarden.widget.swipemenu.SwipeMenuCreator
            public final void create(SwipeMenu swipeMenu, int i2) {
                this.f13020a.lambda$setListenerForWidget$2(swipeMenu, i2);
            }
        });
        this.sortListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() { // from class: com.psychiatrygarden.activity.nk
            @Override // com.psychiatrygarden.widget.swipemenu.SwipeMenuListView.OnMenuItemClickListener
            public final boolean onMenuItemClick(int i2, SwipeMenu swipeMenu, int i3) {
                return this.f13053a.lambda$setListenerForWidget$3(i2, swipeMenu, i3);
            }
        });
        this.sortListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() { // from class: com.psychiatrygarden.activity.SortFriendsListActivity.4
            @Override // com.psychiatrygarden.widget.swipemenu.SwipeMenuListView.OnSwipeListener
            public void onSwipeEnd(int position) {
            }

            @Override // com.psychiatrygarden.widget.swipemenu.SwipeMenuListView.OnSwipeListener
            public void onSwipeStart(int position) {
            }
        });
        this.sortListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() { // from class: com.psychiatrygarden.activity.SortFriendsListActivity.5
            @Override // com.psychiatrygarden.widget.swipemenu.SwipeMenuListView.OnMenuStateChangeListener
            public void onMenuClose(int position) {
            }

            @Override // com.psychiatrygarden.widget.swipemenu.SwipeMenuListView.OnMenuStateChangeListener
            public void onMenuOpen(int position) {
            }
        });
    }
}
