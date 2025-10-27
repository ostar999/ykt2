package com.psychiatrygarden.activity.courselist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryExportNoteFragment;
import com.psychiatrygarden.activity.courselist.fragment.CurriculumFragment;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class CourseNoteExportActivity extends BaseActivity implements View.OnClickListener {
    private final String ALL_SELECT = "全选";
    private final String CANCEL_ALL_SELECT = "取消全选";
    private String courseId;
    private TextView downTv;
    private CourseDirectoryExportNoteFragment noteFragment;
    private TextView tvExportAllSelect;
    private TextView tvExportTitle;

    public static void gotToExportNote(Context context, String courseId) {
        Intent intent = new Intent(context, (Class<?>) CourseNoteExportActivity.class);
        intent.putExtra("courseId", courseId);
        context.startActivity(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(int i2) {
        this.tvExportAllSelect.setText(i2 < this.noteFragment.getNoteAllCount() ? "全选" : "取消全选");
        this.tvExportTitle.setText("已选择 " + i2 + " 条笔记");
        this.downTv.setEnabled(i2 > 0);
    }

    private void selectAll() {
        if (this.noteFragment != null) {
            if ("全选".equals(this.tvExportAllSelect.getText().toString())) {
                this.noteFragment.selectOperaAll(Boolean.TRUE);
            } else {
                this.noteFragment.selectOperaAll(Boolean.FALSE);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.courseId = getIntent().getStringExtra("courseId");
        ImageView imageView = (ImageView) findViewById(R.id.iv_export_back);
        this.tvExportTitle = (TextView) findViewById(R.id.tv_export_title);
        this.downTv = (TextView) findViewById(R.id.downTv);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.courselist.d1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f11901c.lambda$init$0(view);
            }
        });
        TextView textView = (TextView) findViewById(R.id.tv_export_all_select);
        this.tvExportAllSelect = textView;
        textView.setOnClickListener(this);
        this.downTv.setOnClickListener(this);
        this.noteFragment = new CourseDirectoryExportNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putString("course_id", this.courseId);
        bundle.putBoolean("edit_mode", true);
        this.noteFragment.setArguments(bundle);
        if (findFragment(CurriculumFragment.class) == null) {
            loadRootFragment(R.id.fragmentExport, this.noteFragment);
        } else {
            replaceFragment(this.noteFragment, false);
        }
        this.noteFragment.setSelectNumChangeListener(new CourseDirectoryExportNoteFragment.SelectNumChangeListener() { // from class: com.psychiatrygarden.activity.courselist.e1
            @Override // com.psychiatrygarden.activity.courselist.fragment.CourseDirectoryExportNoteFragment.SelectNumChangeListener
            public final void selectNum(int i2) {
                this.f11906a.lambda$init$1(i2);
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.downTv) {
            this.noteFragment.getNoteDownLoad();
        } else {
            if (id != R.id.tv_export_all_select) {
                return;
            }
            selectAll();
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
        setContentView(R.layout.activity_course_note_export);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
