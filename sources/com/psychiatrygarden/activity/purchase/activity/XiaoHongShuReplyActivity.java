package com.psychiatrygarden.activity.purchase.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.http.PicUpLoadExecutor;
import com.psychiatrygarden.activity.purchase.util.Bimp;
import com.psychiatrygarden.activity.purchase.util.ImageItem;
import com.psychiatrygarden.activity.purchase.util.Res;
import com.psychiatrygarden.config.UserConfig;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.CameraUtil;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.EventBusConstant;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.GrapeGridView;
import com.psychiatrygarden.widget.ProperRatingBar;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.tencent.connect.common.Constants;
import com.yikaobang.yixue.R;
import de.greenrobot.event.EventBus;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class XiaoHongShuReplyActivity extends BaseActivity {
    private static final int TAKE_PICTURE = 1;
    public static Bitmap bimap;
    public static File tempFile;
    private GridAdapter adapter;
    EditText edit_content;
    private String fileName;
    public Uri imageUri;
    private LinearLayout ll_popup;
    ProperRatingBar mpro;
    private View parentView;
    TextView tv_actionbar_right;
    TextView tv_oder_time;
    TextView tv_order;
    public String sb = "";
    private PopupWindow pop = null;

    @SuppressLint({"HandlerLeak"})
    Handler mHandler = new Handler() { // from class: com.psychiatrygarden.activity.purchase.activity.XiaoHongShuReplyActivity.1
        List<String> ls = new ArrayList();

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 801) {
                try {
                    XiaoHongShuReplyActivity.this.hideProgressDialog();
                    int i2 = msg.arg1;
                    XiaoHongShuReplyActivity xiaoHongShuReplyActivity = XiaoHongShuReplyActivity.this;
                    String str = (String) msg.obj;
                    xiaoHongShuReplyActivity.sb = str;
                    if (i2 == 10000) {
                        xiaoHongShuReplyActivity.AlertToast(str);
                    } else if (i2 == 10001) {
                        xiaoHongShuReplyActivity.AlertToast("连接服务器超时");
                    } else {
                        this.ls.add(str);
                        if (this.ls.size() == i2) {
                            XiaoHongShuReplyActivity.this.sb = new Gson().toJson(this.ls);
                            XiaoHongShuReplyActivity.this.putCommpileSays();
                            this.ls.clear();
                        }
                    }
                } catch (Exception unused) {
                    XiaoHongShuReplyActivity.this.hideProgressDialog();
                    XiaoHongShuReplyActivity.this.AlertToast("发布评论失败，请稍后继续。");
                }
            }
        }
    };

    @SuppressLint({"HandlerLeak"})
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private boolean shape;
        private int selectedPosition = -1;
        Handler handler = new Handler() { // from class: com.psychiatrygarden.activity.purchase.activity.XiaoHongShuReplyActivity.GridAdapter.1
            @Override // android.os.Handler
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    XiaoHongShuReplyActivity.this.adapter.notifyDataSetChanged();
                }
                super.handleMessage(msg);
            }
        };

        public class ViewHolder {
            public ImageView image;

            public ViewHolder() {
            }
        }

        public GridAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            if (Bimp.tempSelectBitmap.size() == 3) {
                return 3;
            }
            return Bimp.tempSelectBitmap.size() + 1;
        }

        @Override // android.widget.Adapter
        public Object getItem(int arg0) {
            return Bimp.tempSelectBitmap.get(arg0);
        }

        @Override // android.widget.Adapter
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override // android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = this.inflater.inflate(R.layout.item_published_grida, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (position == Bimp.tempSelectBitmap.size()) {
                viewHolder.image.setImageBitmap(BitmapFactory.decodeResource(XiaoHongShuReplyActivity.this.getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 3) {
                    viewHolder.image.setVisibility(8);
                }
            } else if (Bimp.tempSelectBitmap.size() > 0) {
                viewHolder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
            }
            return convertView;
        }

        public boolean isShape() {
            return this.shape;
        }

        public void loading() {
            Message message = new Message();
            message.what = 1;
            this.handler.sendMessage(message);
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public void update() {
            loading();
        }
    }

    public static boolean hasSdcard() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$Init$1(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.pop.dismiss();
        this.ll_popup.clearAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$Init$2() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$Init$3(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (ContextCompat.checkSelfPermission(ProjectApp.instance(), Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.purchase.activity.t1
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f13658a.lambda$Init$2();
                }
            })).show();
            return;
        }
        photo();
        this.pop.dismiss();
        this.ll_popup.clearAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$Init$4() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$Init$5(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
        this.pop.dismiss();
        this.ll_popup.clearAnimation();
        if (ContextCompat.checkSelfPermission(ProjectApp.instance(), Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.purchase.activity.m1
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f13627a.lambda$Init$4();
                }
            })).show();
        } else {
            startActivity(new Intent(getApplicationContext(), (Class<?>) AlbumActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$Init$6(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        this.pop.dismiss();
        this.ll_popup.clearAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$Init$7(AdapterView adapterView, View view, int i2, long j2) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        hideInputMethod();
        if (i2 == Bimp.tempSelectBitmap.size()) {
            this.ll_popup.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.activity_translate_in));
            this.pop.showAtLocation(this.parentView, 80, 0, 0);
            return;
        }
        try {
            Intent intent = new Intent(getApplicationContext(), (Class<?>) GalleryActivity.class);
            intent.putExtra("position", "1");
            intent.putExtra("ID", i2);
            startActivity(intent);
        } catch (Exception unused) {
            AlertToast("请手动打开手机存储权限");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putCommpileSays$0(String str, String str2) {
        try {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.optString("code").equals("200")) {
                    AlertToast(jSONObject.optString("message"));
                    EventBus.getDefault().post(EventBusConstant.EVENT_DIALOG_INPUT_DISMISS);
                    goActivity(GanXieActivity.class);
                    finish();
                } else {
                    AlertToast(jSONObject.optString("message"));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } finally {
            hideProgressDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setListenerForWidget$8(View view) {
        if (CommonUtil.isFastClick()) {
            return;
        }
        if (this.edit_content.getText().toString().trim().equals("") && Bimp.tempSelectBitmap.size() == 0) {
            AlertToast("请添加评论内容或图片");
        } else {
            putPictice();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void putCommpileSays() {
        HashMap map = new HashMap();
        map.put("obj_id", getIntent().getExtras().getString("goods_id", ""));
        map.put("grade", this.mpro.getRating() + "");
        map.put(ActSubmitGoodsComment.EXTRA_DATA_ORDER_NO, getIntent().getExtras().getString("order_id", "") + "");
        map.put("content", this.edit_content.getText().toString().trim());
        map.put("imgs", this.sb);
        map.put("module_type", Constants.VIA_SHARE_TYPE_MINI_PROGRAM);
        map.put("comment_type", "1");
        YJYHttpUtils.postgoodsmd5(this.mContext, NetworkRequestsURL.mPutComment, map, new Response.Listener() { // from class: com.psychiatrygarden.activity.purchase.activity.l1
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj, String str) {
                this.f13624c.lambda$putCommpileSays$0((String) obj, str);
            }
        }, new Response.ErrorListener() { // from class: com.psychiatrygarden.activity.purchase.activity.XiaoHongShuReplyActivity.2
            @Override // com.android.volley.Response.ErrorListener
            public void onErrorResponse(VolleyError arg0, String arg1) {
            }
        });
    }

    private void putPictice() {
        showProgressDialog();
        Bitmap[] bitmapArr = new Bitmap[Bimp.tempSelectBitmap.size()];
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < Bimp.tempSelectBitmap.size(); i2++) {
            arrayList.add(Bimp.tempSelectBitmap.get(i2).getBitmap());
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            bitmapArr[i3] = (Bitmap) arrayList.get(i3);
        }
        if (Bimp.tempSelectBitmap.size() == 0) {
            putCommpileSays();
        } else {
            new PicUpLoadExecutor((short) 3, UserConfig.getUserId()).withUpLoadUrl(NetworkRequestsURL.mPutCommentUploadUrl).withHandler(this.mHandler).exec(bitmapArr);
        }
    }

    public void Init() {
        this.tv_oder_time = (TextView) findViewById(R.id.tv_oder_time);
        this.tv_order = (TextView) findViewById(R.id.tv_order);
        try {
            this.tv_oder_time.setText(String.format("订单时间：%s", getIntent().getExtras().getString("order_time", "")));
            this.tv_order.setText(String.format("订单编号：%s", getIntent().getExtras().getString("order_id", "")));
            GlideUtils.loadImage(findViewById(R.id.imageView1).getContext(), getIntent().getExtras().getString("imgurl"), (ImageView) findViewById(R.id.imageView1));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.edit_content = (EditText) findViewById(R.id.edit_content);
        this.mpro = (ProperRatingBar) findViewById(R.id.lowerRatingBar);
        this.pop = new PopupWindow(getApplicationContext());
        View viewInflate = getLayoutInflater().inflate(R.layout.item_popupwindows, (ViewGroup) null);
        this.ll_popup = (LinearLayout) viewInflate.findViewById(R.id.ll_popup);
        this.pop.setWidth(-1);
        this.pop.setHeight(-2);
        this.pop.setBackgroundDrawable(new BitmapDrawable());
        this.pop.setFocusable(true);
        this.pop.setOutsideTouchable(true);
        this.pop.setContentView(viewInflate);
        RelativeLayout relativeLayout = (RelativeLayout) viewInflate.findViewById(R.id.parent);
        Button button = (Button) viewInflate.findViewById(R.id.item_popupwindows_camera);
        Button button2 = (Button) viewInflate.findViewById(R.id.item_popupwindows_Photo);
        Button button3 = (Button) viewInflate.findViewById(R.id.item_popupwindows_cancel);
        relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.n1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13635c.lambda$Init$1(view);
            }
        });
        button.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.o1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13640c.lambda$Init$3(view);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.p1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13643c.lambda$Init$5(view);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.q1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13646c.lambda$Init$6(view);
            }
        });
        GrapeGridView grapeGridView = (GrapeGridView) findViewById(R.id.noScrollgridview);
        grapeGridView.setSelector(new ColorDrawable(0));
        GridAdapter gridAdapter = new GridAdapter(this);
        this.adapter = gridAdapter;
        gridAdapter.update();
        grapeGridView.setAdapter((ListAdapter) this.adapter);
        grapeGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.r1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
                this.f13648c.lambda$Init$7(adapterView, view, i2, j2);
            }
        });
    }

    public long getBitmapsize(Bitmap bitmap) {
        return bitmap.getByteCount();
    }

    public String getString(String s2) {
        if (s2 == null) {
            return "";
        }
        for (int length = s2.length() - 1; length > 0; length++) {
            s2.charAt(length);
        }
        return null;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && Bimp.tempSelectBitmap.size() < 9 && resultCode == -1) {
            try {
                Bitmap bitmapFormUri = CameraUtil.getBitmapFormUri(this, this.imageUri);
                ImageItem imageItem = new ImageItem();
                imageItem.setBitmap(bitmapFormUri);
                imageItem.setFilename(this.fileName + ".jpg");
                imageItem.setSize(getBitmapsize(bitmapFormUri) + "");
                imageItem.setImagePath(tempFile.getAbsolutePath());
                Bimp.tempSelectBitmap.add(imageItem);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                NewToast.showShort(this, "请手动打开相机权限", 0).show();
                return;
            } else {
                openCamera();
                return;
            }
        }
        if (requestCode == 2) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                NewToast.showShort(this, "请手动打开软件存储权限", 0).show();
                return;
            } else {
                startActivity(new Intent(getApplicationContext(), (Class<?>) AlbumActivity.class));
                return;
            }
        }
        if (requestCode != 3) {
            return;
        }
        if (grantResults.length <= 0 || grantResults[0] != 0) {
            NewToast.showShort(this, "请手动打开软件存储权限", 0).show();
        } else {
            photo();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.adapter.update();
    }

    public void openCamera() {
        int i2 = Build.VERSION.SDK_INT;
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (hasSdcard()) {
            this.fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CHINA).format(new Date());
            File file = new File(Environment.getExternalStorageDirectory(), this.fileName + ".jpg");
            tempFile = file;
            if (i2 < 24) {
                Uri uriFromFile = Uri.fromFile(file);
                this.imageUri = uriFromFile;
                intent.putExtra("output", uriFromFile);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put("_data", tempFile.getAbsolutePath());
                if (ContextCompat.checkSelfPermission(this, Permission.WRITE_EXTERNAL_STORAGE) != 0) {
                    NewToast.showShort(this, "请开启存储权限", 0).show();
                    return;
                } else {
                    Uri uriInsert = ProjectApp.instance.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                    this.imageUri = uriInsert;
                    intent.putExtra("output", uriInsert);
                }
            }
        }
        startActivityForResult(intent, 1);
    }

    public void photo() {
        this.fileName = String.valueOf(System.currentTimeMillis());
        if (ContextCompat.checkSelfPermission(this, Permission.CAMERA) != 0) {
            ActivityCompat.requestPermissions(this, new String[]{Permission.CAMERA}, 1);
        } else {
            openCamera();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        Res.init(this);
        bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
        View viewInflate = getLayoutInflater().inflate(R.layout.activity_reply, (ViewGroup) null);
        this.parentView = viewInflate;
        setContentView(viewInflate);
        setTitle("晒单与评价");
        Init();
        try {
            Bimp.tempSelectBitmap.clear();
            Bimp.temp.clear();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
        TextView textView = (TextView) findViewById(R.id.tv_actionbar_right);
        this.tv_actionbar_right = textView;
        textView.setText("提交");
        this.mBtnActionbarRight.setVisibility(8);
        this.tv_actionbar_right.setVisibility(0);
        this.tv_actionbar_right.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.s1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13653c.lambda$setListenerForWidget$8(view);
            }
        });
    }
}
