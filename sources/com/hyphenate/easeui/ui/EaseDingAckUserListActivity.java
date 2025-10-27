package com.hyphenate.easeui.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.manager.EaseDingMessageHelper;
import com.hyphenate.easeui.ui.base.EaseBaseActivity;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.hyphenate.util.EMLog;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseDingAckUserListActivity extends EaseBaseActivity {
    private static final String TAG = "EaseDingAckUserListActi";
    private ListView ackUserListView;
    private EMMessage msg;
    private EaseTitleBar titleBar;
    private TextView tvNoData;
    private AckUserAdapter userAdapter;
    private List<String> userList;
    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener = new AnonymousClass2();

    /* renamed from: com.hyphenate.easeui.ui.EaseDingAckUserListActivity$2, reason: invalid class name */
    public class AnonymousClass2 implements EaseDingMessageHelper.IAckUserUpdateListener {
        public AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUpdate$0() {
            EaseDingAckUserListActivity.this.tvNoData.setVisibility(8);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUpdate$1() {
            EaseDingAckUserListActivity.this.userAdapter.notifyDataSetChanged();
        }

        @Override // com.hyphenate.easeui.manager.EaseDingMessageHelper.IAckUserUpdateListener
        public void onUpdate(List<String> list) {
            EMLog.i(EaseDingAckUserListActivity.TAG, "onUpdate: " + list.size());
            if (list.size() > 0) {
                EaseDingAckUserListActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.a
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f8742c.lambda$onUpdate$0();
                    }
                });
            }
            EaseDingAckUserListActivity.this.userList.clear();
            EaseDingAckUserListActivity.this.userList.addAll(list);
            EaseDingAckUserListActivity.this.runOnUiThread(new Runnable() { // from class: com.hyphenate.easeui.ui.b
                @Override // java.lang.Runnable
                public final void run() {
                    this.f8743c.lambda$onUpdate$1();
                }
            });
        }
    }

    public static class AckUserAdapter extends BaseAdapter {
        private Context context;
        private List<String> userList;

        public static class ViewHolder {
            public TextView nameView;

            public ViewHolder(View view) {
                this.nameView = (TextView) view.findViewById(R.id.username);
            }
        }

        public AckUserAdapter(Context context, List<String> list) {
            this.context = context;
            this.userList = list;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return this.userList.size();
        }

        @Override // android.widget.Adapter
        public Object getItem(int i2) {
            return this.userList.get(i2);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i2) {
            return i2;
        }

        @Override // android.widget.Adapter
        public View getView(int i2, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = LayoutInflater.from(this.context).inflate(R.layout.ease_row_ding_ack_user, (ViewGroup) null);
                viewHolder = new ViewHolder(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.nameView.setText(this.userList.get(i2));
            return view;
        }
    }

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_ding_ack_user_list);
        setFitSystemForTheme(true);
        this.ackUserListView = (ListView) findViewById(R.id.list_view);
        this.titleBar = (EaseTitleBar) findViewById(R.id.title_bar);
        this.tvNoData = (TextView) findViewById(R.id.tv_no_data);
        this.titleBar.setTitle(getString(R.string.title_ack_read_list));
        this.titleBar.setLeftLayoutClickListener(new View.OnClickListener() { // from class: com.hyphenate.easeui.ui.EaseDingAckUserListActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                EaseDingAckUserListActivity.this.back(view);
            }
        });
        this.msg = (EMMessage) getIntent().getParcelableExtra("msg");
        EMLog.i(TAG, "Get msg from intent, msg: " + this.msg.toString());
        this.userList = new ArrayList();
        AckUserAdapter ackUserAdapter = new AckUserAdapter(this, this.userList);
        this.userAdapter = ackUserAdapter;
        this.ackUserListView.setAdapter((ListAdapter) ackUserAdapter);
        this.msg.getMsgId();
        EaseDingMessageHelper.get().fetchGroupReadAck(this.msg);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        EaseDingMessageHelper.get().setUserUpdateListener(this.msg, null);
    }

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        EaseDingMessageHelper.get().setUserUpdateListener(this.msg, this.userUpdateListener);
    }
}
