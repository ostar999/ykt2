package com.psychiatrygarden.fragmenthome.knowledge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.bean.KnowledgeListType;
import com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class KnowledgeListEditActivity extends BaseActivity implements View.OnClickListener {
    private final String ALL_SELECT = "全选";
    private final String CANCEL_ALL_SELECT = "取消全选";
    private KnowledgeListEditFragment childFragment;
    private String domain_type;
    private TextView downTv;
    private String id;
    private String treeId;
    private TextView tvExportAllSelect;
    private TextView tvExportTitle;

    /* renamed from: com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditActivity$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType;

        static {
            int[] iArr = new int[KnowledgeListType.values().length];
            $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType = iArr;
            try {
                iArr[KnowledgeListType.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.CUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.ALL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.NOTE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.PRAISE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.COMMENT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.COLLECTION.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    private String getCountStr(String count) {
        switch (AnonymousClass1.$SwitchMap$com$psychiatrygarden$bean$KnowledgeListType[KnowledgeListType.INSTANCE.getKnowledgeListType(this.domain_type).ordinal()]) {
            case 1:
            case 2:
            case 3:
                return String.format("已选择%s个题", count);
            case 4:
                return String.format("已选择%s条笔记", count);
            case 5:
                return String.format("已选择%s条点赞", count);
            case 6:
                return String.format("已选择%s条评论", count);
            case 7:
                return String.format("已选择%s个收藏", count);
            default:
                return "";
        }
    }

    public static void gotToEditKnowledge(Context context, String treeId, String domain_type) {
        Intent intent = new Intent(context, (Class<?>) KnowledgeListEditActivity.class);
        intent.putExtra(KnowledgeQuestionListFragment.EXTRA_TREE_ID, treeId);
        intent.putExtra(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE, domain_type);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(int i2) {
        this.tvExportAllSelect.setText(i2 < this.childFragment.getNoteAllCount() ? "全选" : "取消全选");
        this.tvExportTitle.setText(getCountStr(i2 + ""));
        this.downTv.setEnabled(i2 > 0);
    }

    private void selectAll() {
        if (this.childFragment != null) {
            if ("全选".equals(this.tvExportAllSelect.getText().toString())) {
                this.childFragment.selectOperaAll(Boolean.TRUE);
            } else {
                this.childFragment.selectOperaAll(Boolean.FALSE);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        Intent intent = getIntent();
        if (intent != null) {
            this.domain_type = intent.getStringExtra(KnowledgeQuestionListFragment.EXTRA_DOMAIN_TYPE);
            this.treeId = intent.getStringExtra(KnowledgeQuestionListFragment.EXTRA_TREE_ID);
        }
        ImageView imageView = (ImageView) findViewById(R.id.iv_knowledge_edit_back);
        this.tvExportTitle = (TextView) findViewById(R.id.tv_export_title);
        this.downTv = (TextView) findViewById(R.id.downTv);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f15728c.lambda$init$0(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.tv_export_all_select);
        this.tvExportAllSelect = textView;
        textView.setOnClickListener(this);
        this.downTv.setOnClickListener(this);
        KnowledgeListEditFragment knowledgeListEditFragment = new KnowledgeListEditFragment();
        this.childFragment = knowledgeListEditFragment;
        this.childFragment.setArguments(knowledgeListEditFragment.getBundle(this.treeId, this.domain_type));
        if (findFragment(KnowledgeListEditFragment.class) == null) {
            loadRootFragment(R.id.fragmentKnowledgeEdit, this.childFragment);
        } else {
            replaceFragment(this.childFragment, false);
        }
        this.childFragment.setSelectNumChangeListener(new KnowledgeListEditFragment.SelectNumChangeListener() { // from class: com.psychiatrygarden.fragmenthome.knowledge.b
            @Override // com.psychiatrygarden.fragmenthome.knowledge.KnowledgeListEditFragment.SelectNumChangeListener
            public final void selectNum(int i2) {
                this.f15731a.lambda$init$1(i2);
            }
        });
        this.tvExportTitle.setText(getCountStr("0"));
        if (KnowledgeListType.CUT.getType().equals(this.domain_type)) {
            this.downTv.setText("移回题库");
        } else {
            this.downTv.setText("立即导出");
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id != R.id.downTv) {
            if (id != R.id.tv_export_all_select) {
                return;
            }
            selectAll();
        } else if (this.domain_type.equals(KnowledgeListType.CUT.getType())) {
            this.childFragment.moveZhanTi();
        } else {
            this.childFragment.getNoteDownLoad();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_knowledge_list_edit);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
