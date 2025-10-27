package com.psychiatrygarden.activity.purchase;

import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.pizidea.imagepicker.AndroidImagePicker;
import com.pizidea.imagepicker.bean.ImageItem;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter;
import com.psychiatrygarden.http.NetworkRequestsURL;
import com.psychiatrygarden.http.YJYHttpUtils;
import com.psychiatrygarden.utils.FullyGridLayoutManager;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ImageFactory;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.ProperRatingBar;
import com.psychiatrygarden.widget.SkinGrakImagView;
import com.yikaobang.yixue.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import org.apache.http.cookie.ClientCookie;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes5.dex */
public class PublishProductReviewsActivity extends BaseActivity {
    private GridImageAdapter adapter;
    public EditText edit_content;
    public SkinGrakImagView imageView1;
    public ProperRatingBar lowerRatingBar;
    public RecyclerView noScrollgridview;
    public TextView tv_order;
    private List<String> imageStr = new ArrayList();
    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() { // from class: com.psychiatrygarden.activity.purchase.PublishProductReviewsActivity.1
        @Override // com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter.onAddPicClickListener
        public void deleteData(int position) {
            if (PublishProductReviewsActivity.this.imageStr == null || PublishProductReviewsActivity.this.imageStr.size() <= position) {
                return;
            }
            PublishProductReviewsActivity.this.imageStr.remove(position);
        }

        @Override // com.psychiatrygarden.activity.answer.compose.adapter.GridImageAdapter.onAddPicClickListener
        public void onAddPicClick() {
            AndroidImagePicker.getInstance().setSelectLimit(1);
            AndroidImagePicker.getInstance().pickMulti(PublishProductReviewsActivity.this, true, new AndroidImagePicker.OnImagePickCompleteListener() { // from class: com.psychiatrygarden.activity.purchase.PublishProductReviewsActivity.1.1
                @Override // com.pizidea.imagepicker.AndroidImagePicker.OnImagePickCompleteListener
                public void onImagePickComplete(List<ImageItem> items) {
                    if (items == null || items.size() <= 0) {
                        return;
                    }
                    String str = items.get(0).path;
                    String mimeTypeFromFile = ImageFactory.getMimeTypeFromFile(str);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(str, options);
                    if (mimeTypeFromFile.toUpperCase().equals("IMAGE/WEBP")) {
                        PublishProductReviewsActivity.this.AlertToast("不支持此文件格式，请选择其它图片上传！");
                    } else if (ImageFactory.getImageSize(str) > 10.0f) {
                        NewToast.showShort(PublishProductReviewsActivity.this.mContext, "请选择小于10M的图片上传", 0).show();
                    } else {
                        PublishProductReviewsActivity.this.getImageData(str);
                    }
                }
            });
        }
    };

    public void getImageData(String path) {
        AjaxParams ajaxParams = new AjaxParams();
        try {
            ajaxParams.put("file", new File(path));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        ajaxParams.put("type", ClientCookie.COMMENT_ATTR);
        YJYHttpUtils.postImage(this.mContext, NetworkRequestsURL.mPutCommentUploadUrl, ajaxParams, new AjaxCallBack<String>() { // from class: com.psychiatrygarden.activity.purchase.PublishProductReviewsActivity.2
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
                super.onSuccess((AnonymousClass2) s2);
                try {
                    JSONArray jSONArray = new JSONArray(new JSONObject(s2).optString("data"));
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        PublishProductReviewsActivity.this.imageStr.add(0, jSONArray.optString(i2));
                    }
                    ArrayList arrayList = new ArrayList();
                    for (int i3 = 0; i3 < PublishProductReviewsActivity.this.imageStr.size(); i3++) {
                        arrayList.add((String) PublishProductReviewsActivity.this.imageStr.get(i3));
                    }
                    PublishProductReviewsActivity.this.adapter.setList((List<String>) arrayList);
                    PublishProductReviewsActivity.this.adapter.notifyDataSetChanged();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        });
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
        this.tv_order = (TextView) findViewById(R.id.tv_order);
        this.imageView1 = (SkinGrakImagView) findViewById(R.id.imageView1);
        this.edit_content = (EditText) findViewById(R.id.edit_content);
        this.noScrollgridview = (RecyclerView) findViewById(R.id.noScrollgridview);
        this.lowerRatingBar = (ProperRatingBar) findViewById(R.id.lowerRatingBar);
        this.tv_order.setText("订单编号：" + getIntent().getExtras().getString("order_id", ""));
        GlideUtils.loadImage(this.imageView1.getContext(), getIntent().getExtras().getString("imgurl"), this.imageView1);
        this.noScrollgridview.setLayoutManager(new FullyGridLayoutManager(this, 3, 1, false));
        GridImageAdapter gridImageAdapter = new GridImageAdapter(this.imageStr, 3, this.onAddPicClickListener);
        this.adapter = gridImageAdapter;
        this.noScrollgridview.setAdapter(gridImageAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setContentView(R.layout.activity_publish_product_review);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
