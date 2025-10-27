package com.psychiatrygarden.bean;

import com.aliyun.auth.common.AliyunVodHttpCommon;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.bean.RedEnvelopeCouponsBean;
import com.psychiatrygarden.utils.Constants;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0081\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0003\b\u008d\u0001\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B«\u0006\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003\u0012\b\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003\u0012\u001a\u0010\u0014\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u0003\u0018\u0001`\u0016\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u001b\u001a\u0004\u0018\u00010\u0003\u0012\u001c\b\u0002\u0010\u001c\u001a\u0016\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001d\u0018\u0001`\u0016\u0012\u001a\u0010\u001e\u001a\u0016\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001f\u0018\u0001`\u0016\u0012\b\u0010 \u001a\u0004\u0018\u00010\u0003\u0012\b\u0010!\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\"\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010#\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010$\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010%\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010&\u001a\u00020\u0003\u0012\b\u0010'\u001a\u0004\u0018\u00010(\u0012\b\u0010)\u001a\u0004\u0018\u00010(\u0012\b\u0010*\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010+\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010,\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010-\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010.\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010/\u001a\u0004\u0018\u00010\u0003\u0012\b\u00100\u001a\u0004\u0018\u00010\u0003\u0012\b\u00101\u001a\u0004\u0018\u00010\u0003\u0012\b\u00102\u001a\u0004\u0018\u00010\u0003\u0012\b\u00103\u001a\u0004\u0018\u00010\u0003\u0012\b\u00104\u001a\u0004\u0018\u00010\u0003\u0012\b\u00105\u001a\u0004\u0018\u00010\u0003\u0012\b\u00106\u001a\u0004\u0018\u00010\u0003\u0012\b\u00107\u001a\u0004\u0018\u00010\u0003\u0012\b\u00108\u001a\u0004\u0018\u00010\u0003\u0012\b\u00109\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010:\u001a\u0004\u0018\u00010\u0003\u0012\u001a\u0010;\u001a\u0016\u0012\u0004\u0012\u00020<\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020<\u0018\u0001`\u0016\u0012\u001a\u0010=\u001a\u0016\u0012\u0004\u0012\u00020>\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020>\u0018\u0001`\u0016\u0012\b\u0010?\u001a\u0004\u0018\u00010\u0003\u0012\u001a\u0010@\u001a\u0016\u0012\u0004\u0012\u00020A\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020A\u0018\u0001`\u0016\u0012\b\u0010B\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010C\u001a\u0004\u0018\u00010\u0003\u0012\f\u0010D\u001a\b\u0012\u0004\u0012\u00020F0E\u0012\b\b\u0002\u0010G\u001a\u00020\u0003\u0012\u001a\u0010H\u001a\u0016\u0012\u0004\u0012\u00020I\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020I\u0018\u0001`\u0016\u0012\u001a\u0010J\u001a\u0016\u0012\u0004\u0012\u00020K\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020K\u0018\u0001`\u0016\u0012\u001a\u0010L\u001a\u0016\u0012\u0004\u0012\u00020M\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020M\u0018\u0001`\u0016\u0012\b\u0010N\u001a\u0004\u0018\u00010O\u0012\b\u0010P\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010Q\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010R\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010SJ\n\u0010\u009a\u0001\u001a\u00020\u0003HÆ\u0003J\f\u0010\u009b\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010\u009c\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\n\u0010\u009d\u0001\u001a\u00020\u0003HÆ\u0003J\f\u0010\u009e\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010\u009f\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010 \u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¡\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001e\u0010¢\u0001\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u0003\u0018\u0001`\u0016HÆ\u0003J\f\u0010£\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¤\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\n\u0010¥\u0001\u001a\u00020\u0003HÆ\u0003J\f\u0010¦\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010§\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¨\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001e\u0010©\u0001\u001a\u0016\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001d\u0018\u0001`\u0016HÆ\u0003J\u001e\u0010ª\u0001\u001a\u0016\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001f\u0018\u0001`\u0016HÆ\u0003J\f\u0010«\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¬\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010\u00ad\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010®\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¯\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\n\u0010°\u0001\u001a\u00020\u0006HÆ\u0003J\f\u0010±\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\n\u0010²\u0001\u001a\u00020\u0003HÆ\u0003J\f\u0010³\u0001\u001a\u0004\u0018\u00010(HÆ\u0003J\f\u0010´\u0001\u001a\u0004\u0018\u00010(HÆ\u0003J\f\u0010µ\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¶\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010·\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¸\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¹\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010º\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\n\u0010»\u0001\u001a\u00020\u0003HÆ\u0003J\f\u0010¼\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010½\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¾\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010¿\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010À\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Á\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Â\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Ã\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Ä\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Å\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Æ\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Ç\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001e\u0010È\u0001\u001a\u0016\u0012\u0004\u0012\u00020<\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020<\u0018\u0001`\u0016HÆ\u0003J\u001e\u0010É\u0001\u001a\u0016\u0012\u0004\u0012\u00020>\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020>\u0018\u0001`\u0016HÆ\u0003J\f\u0010Ê\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001e\u0010Ë\u0001\u001a\u0016\u0012\u0004\u0012\u00020A\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020A\u0018\u0001`\u0016HÆ\u0003J\f\u0010Ì\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Í\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u0010\u0010Î\u0001\u001a\b\u0012\u0004\u0012\u00020F0EHÆ\u0003J\n\u0010Ï\u0001\u001a\u00020\u0003HÆ\u0003J\u001e\u0010Ð\u0001\u001a\u0016\u0012\u0004\u0012\u00020I\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020I\u0018\u0001`\u0016HÆ\u0003J\f\u0010Ñ\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001e\u0010Ò\u0001\u001a\u0016\u0012\u0004\u0012\u00020K\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020K\u0018\u0001`\u0016HÆ\u0003J\u001e\u0010Ó\u0001\u001a\u0016\u0012\u0004\u0012\u00020M\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020M\u0018\u0001`\u0016HÆ\u0003J\f\u0010Ô\u0001\u001a\u0004\u0018\u00010OHÆ\u0003J\f\u0010Õ\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Ö\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010×\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Ø\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\f\u0010Ù\u0001\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\n\u0010Ú\u0001\u001a\u00020\u0003HÆ\u0003J¬\u0007\u0010Û\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\f\u001a\u00020\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00032\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00032\u001c\b\u0002\u0010\u0014\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u0003\u0018\u0001`\u00162\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0018\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u00032\u001c\b\u0002\u0010\u001c\u001a\u0016\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001d\u0018\u0001`\u00162\u001c\b\u0002\u0010\u001e\u001a\u0016\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001f\u0018\u0001`\u00162\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010!\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010$\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010%\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010&\u001a\u00020\u00032\n\b\u0002\u0010'\u001a\u0004\u0018\u00010(2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010(2\n\b\u0002\u0010*\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010,\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010-\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010.\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010/\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00100\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00101\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00102\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00103\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00104\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00105\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00106\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00107\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00108\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u00109\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010:\u001a\u0004\u0018\u00010\u00032\u001c\b\u0002\u0010;\u001a\u0016\u0012\u0004\u0012\u00020<\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020<\u0018\u0001`\u00162\u001c\b\u0002\u0010=\u001a\u0016\u0012\u0004\u0012\u00020>\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020>\u0018\u0001`\u00162\n\b\u0002\u0010?\u001a\u0004\u0018\u00010\u00032\u001c\b\u0002\u0010@\u001a\u0016\u0012\u0004\u0012\u00020A\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020A\u0018\u0001`\u00162\n\b\u0002\u0010B\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010C\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010D\u001a\b\u0012\u0004\u0012\u00020F0E2\b\b\u0002\u0010G\u001a\u00020\u00032\u001c\b\u0002\u0010H\u001a\u0016\u0012\u0004\u0012\u00020I\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020I\u0018\u0001`\u00162\u001c\b\u0002\u0010J\u001a\u0016\u0012\u0004\u0012\u00020K\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020K\u0018\u0001`\u00162\u001c\b\u0002\u0010L\u001a\u0016\u0012\u0004\u0012\u00020M\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020M\u0018\u0001`\u00162\n\b\u0002\u0010N\u001a\u0004\u0018\u00010O2\n\b\u0002\u0010P\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010Q\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010R\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0016\u0010Ü\u0001\u001a\u00030Ý\u00012\t\u0010Þ\u0001\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\b\u0010ß\u0001\u001a\u00030Ý\u0001J\b\u0010à\u0001\u001a\u00030Ý\u0001J\n\u0010á\u0001\u001a\u00020\u0006HÖ\u0001J\b\u0010â\u0001\u001a\u00030Ý\u0001J\u0007\u0010/\u001a\u00030Ý\u0001J\n\u0010ã\u0001\u001a\u00020\u0003HÖ\u0001R\u0018\u0010)\u001a\u0004\u0018\u00010(8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bT\u0010UR\u0016\u0010\f\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bV\u0010WR\u0018\u0010'\u001a\u0004\u0018\u00010(8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bX\u0010UR\u0018\u0010\u000b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bY\u0010WR*\u0010;\u001a\u0016\u0012\u0004\u0012\u00020<\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020<\u0018\u0001`\u00168\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bZ\u0010[R\u0018\u0010,\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\\\u0010WR*\u0010@\u001a\u0016\u0012\u0004\u0012\u00020A\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020A\u0018\u0001`\u00168\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b]\u0010[R\u0013\u0010Q\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b^\u0010WR\u0016\u0010\u000f\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b_\u0010WR*\u0010=\u001a\u0016\u0012\u0004\u0012\u00020>\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020>\u0018\u0001`\u00168\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b`\u0010[R%\u0010L\u001a\u0016\u0012\u0004\u0012\u00020M\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020M\u0018\u0001`\u0016¢\u0006\b\n\u0000\u001a\u0004\ba\u0010[R\u0018\u0010?\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bb\u0010WR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bc\u0010WR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bd\u0010e\"\u0004\bf\u0010gR\u0013\u0010\u001a\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bh\u0010WR\u0018\u0010\u001b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bi\u0010WR%\u0010J\u001a\u0016\u0012\u0004\u0012\u00020K\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020K\u0018\u0001`\u0016¢\u0006\b\n\u0000\u001a\u0004\bj\u0010[R\u0018\u0010C\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bk\u0010WR\u0018\u0010\t\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bl\u0010WR\u0013\u0010P\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bm\u0010WR\u0013\u0010\"\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\bn\u0010WR\u0013\u0010N\u001a\u0004\u0018\u00010O¢\u0006\b\n\u0000\u001a\u0004\bo\u0010pR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bq\u0010WR2\u0010\u0014\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u0003\u0018\u0001`\u00168\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\br\u0010[\"\u0004\bs\u0010tR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bu\u0010W\"\u0004\bv\u0010wR\u0018\u0010.\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010WR\u0018\u00101\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b1\u0010WR\u0018\u0010 \u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010WR\u0018\u0010\u0010\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010WR\u0018\u00102\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b2\u0010WR\u0018\u00103\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b3\u0010WR\u0018\u00104\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b4\u0010WR\u0018\u00108\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b8\u0010WR\u0016\u0010&\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b&\u0010WR\u0018\u0010/\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u0010WR\u0018\u00100\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b0\u0010WR.\u0010\u001e\u001a\u0016\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001f\u0018\u0001`\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bx\u0010[\"\u0004\by\u0010tR\u0018\u0010:\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\bz\u0010WR\u0018\u0010+\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b{\u0010WR\u0018\u00109\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b|\u0010WR\u0018\u0010!\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b}\u0010WR\u0018\u0010#\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b~\u0010WR\u001d\u0010D\u001a\b\u0012\u0004\u0012\u00020F0E8\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u007f\u0010\u0080\u0001R\u001e\u0010\u0011\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0081\u0001\u0010W\"\u0005\b\u0082\u0001\u0010wR\u0019\u0010\n\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0083\u0001\u0010WR\u0019\u0010\b\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0084\u0001\u0010WR\"\u0010%\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u0085\u0001\u0010W\"\u0005\b\u0086\u0001\u0010wR\u0019\u0010$\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0087\u0001\u0010WR\u0019\u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0088\u0001\u0010WR\u0019\u0010*\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0089\u0001\u0010WR\u0017\u0010G\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008a\u0001\u0010WR0\u0010H\u001a\u0016\u0012\u0004\u0012\u00020I\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020I\u0018\u0001`\u0016X\u0086\u000e¢\u0006\u0010\n\u0000\u001a\u0005\b\u008b\u0001\u0010[\"\u0005\b\u008c\u0001\u0010tR\u0019\u0010B\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u008d\u0001\u0010WR&\u0010\u001c\u001a\u0016\u0012\u0004\u0012\u00020\u001d\u0018\u00010\u0015j\n\u0012\u0004\u0012\u00020\u001d\u0018\u0001`\u0016¢\u0006\t\n\u0000\u001a\u0005\b\u008e\u0001\u0010[R\u0014\u0010R\u001a\u0004\u0018\u00010\u0003¢\u0006\t\n\u0000\u001a\u0005\b\u008f\u0001\u0010WR\u0017\u0010\u0004\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0090\u0001\u0010WR\u0012\u0010\u0007\u001a\u00020\u0003¢\u0006\t\n\u0000\u001a\u0005\b\u0091\u0001\u0010WR\u0014\u0010-\u001a\u0004\u0018\u00010\u0003¢\u0006\t\n\u0000\u001a\u0005\b\u0092\u0001\u0010WR\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0093\u0001\u0010WR\u0019\u0010\u0018\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0094\u0001\u0010WR\u0019\u0010\u0019\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0095\u0001\u0010WR\u0019\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0096\u0001\u0010WR\u0019\u00105\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0097\u0001\u0010WR\u0019\u00107\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0098\u0001\u0010WR\u0019\u00106\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\t\n\u0000\u001a\u0005\b\u0099\u0001\u0010W¨\u0006ä\u0001"}, d2 = {"Lcom/psychiatrygarden/bean/CourseDetailBean;", "", "id", "", "title", "dataType", "", "type", "priceLabel", "goodsType", "priceGray", "btnGray", "appId", "vipFree", "sVipFree", "categoryId", "isCoupon", "price", "inventory", AliyunVodHttpCommon.ImageType.IMAGETYPE_COVER, "imgList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "vid", "video", "videoCoverImg", "description", "detail", "teacher", "Lcom/psychiatrygarden/bean/CourseTeacher;", Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL, "Lcom/psychiatrygarden/bean/CourseLabel;", "isBuy", "nowPrice", "goods_thumbnail", "originalPrice", "promotionPrice", "promotionCountDown", "isPromotion", "beforeCs", "Lcom/psychiatrygarden/bean/OnlineServiceBean;", "afterCs", "salesVolume", "memberCount", "btnText", "validDate", "isAloneSale", "isSale", "isStopSale", "isBestSellers", "isEnd", "isHideTeacher", "isOpenQrCode", "weChatCode", "weChatTips", "weChatNumber", "isPermission", "minPrice", "maxPrice", "btnList", "Lcom/psychiatrygarden/bean/BottomBtn;", "commonProblem", "Lcom/psychiatrygarden/bean/CommonProblem;", "courseCount", "buyRecord", "Lcom/psychiatrygarden/bean/BuyRecord;", "tbBuy", "giveWayType", "parentCourse", "", "Lcom/psychiatrygarden/bean/ParentCourseBean;", "showMode", "sku", "Lcom/psychiatrygarden/bean/SkuItem;", "gift", "Lcom/psychiatrygarden/bean/CourseGiftItem;", "coupon", "Lcom/psychiatrygarden/bean/RedEnvelopeCouponsBean$RedEnvelopeCouponsDataItem;", "goods_video", "Lcom/psychiatrygarden/bean/GoodsVideo;", "goods_sale_type", "buy_quantity", "times_limit", "(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/psychiatrygarden/bean/OnlineServiceBean;Lcom/psychiatrygarden/bean/OnlineServiceBean;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/lang/String;Ljava/util/ArrayList;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;Lcom/psychiatrygarden/bean/GoodsVideo;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAfterCs", "()Lcom/psychiatrygarden/bean/OnlineServiceBean;", "getAppId", "()Ljava/lang/String;", "getBeforeCs", "getBtnGray", "getBtnList", "()Ljava/util/ArrayList;", "getBtnText", "getBuyRecord", "getBuy_quantity", "getCategoryId", "getCommonProblem", "getCoupon", "getCourseCount", "getCover", "getDataType", "()I", "setDataType", "(I)V", "getDescription", "getDetail", "getGift", "getGiveWayType", "getGoodsType", "getGoods_sale_type", "getGoods_thumbnail", "getGoods_video", "()Lcom/psychiatrygarden/bean/GoodsVideo;", "getId", "getImgList", "setImgList", "(Ljava/util/ArrayList;)V", "getInventory", "setInventory", "(Ljava/lang/String;)V", "getLabel", "setLabel", "getMaxPrice", "getMemberCount", "getMinPrice", "getNowPrice", "getOriginalPrice", "getParentCourse", "()Ljava/util/List;", "getPrice", "setPrice", "getPriceGray", "getPriceLabel", "getPromotionCountDown", "setPromotionCountDown", "getPromotionPrice", "getSVipFree", "getSalesVolume", "getShowMode", "getSku", "setSku", "getTbBuy", "getTeacher", "getTimes_limit", "getTitle", "getType", "getValidDate", "getVid", "getVideo", "getVideoCoverImg", "getVipFree", "getWeChatCode", "getWeChatNumber", "getWeChatTips", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component44", "component45", "component46", "component47", "component48", "component49", "component5", "component50", "component51", "component52", "component53", "component54", "component55", "component56", "component57", "component58", "component59", "component6", "component60", "component61", "component62", "component63", "component64", "component65", "component7", "component8", "component9", "copy", "equals", "", "other", "hasBuy", "hasPermission", "hashCode", "isAloneSle", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CourseDetailBean {

    @SerializedName("after_cs")
    @Nullable
    private final OnlineServiceBean afterCs;

    @SerializedName("app_id")
    @NotNull
    private final String appId;

    @SerializedName(alternate = {"cs"}, value = "before_cs")
    @Nullable
    private final OnlineServiceBean beforeCs;

    @SerializedName("button_gray")
    @Nullable
    private final String btnGray;

    @SerializedName("btn_list")
    @Nullable
    private final ArrayList<BottomBtn> btnList;

    @SerializedName("button_text")
    @Nullable
    private final String btnText;

    @SerializedName(alternate = {"shop_users"}, value = "course_record")
    @Nullable
    private final ArrayList<BuyRecord> buyRecord;

    @Nullable
    private final String buy_quantity;

    @SerializedName("category_id")
    @NotNull
    private final String categoryId;

    @SerializedName("common_problem")
    @Nullable
    private final ArrayList<CommonProblem> commonProblem;

    @Nullable
    private final ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> coupon;

    @SerializedName("package_count")
    @Nullable
    private final String courseCount;

    @Nullable
    private final String cover;
    private int dataType;

    @Nullable
    private final String description;

    @SerializedName(alternate = {"detail_html"}, value = "detail")
    @Nullable
    private final String detail;

    @Nullable
    private final ArrayList<CourseGiftItem> gift;

    @SerializedName("give_away_type")
    @Nullable
    private final String giveWayType;

    @SerializedName("goods_type")
    @Nullable
    private final String goodsType;

    @Nullable
    private final String goods_sale_type;

    @Nullable
    private final String goods_thumbnail;

    @Nullable
    private final GoodsVideo goods_video;

    @SerializedName(alternate = {"goods_id"}, value = "id")
    @NotNull
    private final String id;

    @SerializedName(alternate = {"goods_img"}, value = "publicity_img")
    @Nullable
    private ArrayList<String> imgList;

    @Nullable
    private String inventory;

    @SerializedName("is_alone_sale")
    @Nullable
    private final String isAloneSale;

    @SerializedName("is_best_sellers")
    @Nullable
    private final String isBestSellers;

    @SerializedName("is_buy")
    @Nullable
    private final String isBuy;

    @SerializedName("is_coupon")
    @Nullable
    private final String isCoupon;

    @SerializedName("is_end")
    @Nullable
    private final String isEnd;

    @SerializedName("is_hide_teacher")
    @Nullable
    private final String isHideTeacher;

    @SerializedName("is_open_qrcode")
    @Nullable
    private final String isOpenQrCode;

    @SerializedName("is_permission")
    @Nullable
    private final String isPermission;

    @SerializedName("is_promotion")
    @NotNull
    private final String isPromotion;

    @SerializedName("is_sale")
    @Nullable
    private final String isSale;

    @SerializedName("is_stop")
    @Nullable
    private final String isStopSale;

    @Nullable
    private ArrayList<CourseLabel> label;

    @SerializedName("max_price")
    @Nullable
    private final String maxPrice;

    @SerializedName("member_count")
    @Nullable
    private final String memberCount;

    @SerializedName("min_price")
    @Nullable
    private final String minPrice;

    @SerializedName("now_price")
    @Nullable
    private final String nowPrice;

    @SerializedName("original_price")
    @Nullable
    private final String originalPrice;

    @SerializedName("parent_course")
    @NotNull
    private final List<ParentCourseBean> parentCourse;

    @Nullable
    private String price;

    @SerializedName("price_gray")
    @Nullable
    private final String priceGray;

    @SerializedName("price_label")
    @Nullable
    private final String priceLabel;

    @SerializedName("promotion_countdown")
    @Nullable
    private String promotionCountDown;

    @SerializedName("promotion_price")
    @Nullable
    private final String promotionPrice;

    @SerializedName("svip_free")
    @Nullable
    private final String sVipFree;

    @SerializedName("sales_volume")
    @Nullable
    private final String salesVolume;

    @SerializedName("display_type")
    @NotNull
    private final String showMode;

    @Nullable
    private ArrayList<SkuItem> sku;

    @SerializedName("tb_link_status")
    @Nullable
    private final String tbBuy;

    @Nullable
    private final ArrayList<CourseTeacher> teacher;

    @Nullable
    private final String times_limit;

    @SerializedName(alternate = {"goods_name"}, value = "title")
    @NotNull
    private final String title;

    @NotNull
    private final String type;

    @Nullable
    private final String validDate;

    @SerializedName("publicity_vid")
    @Nullable
    private final String vid;

    @SerializedName("publicity_video")
    @Nullable
    private final String video;

    @SerializedName("publicity_video_img")
    @Nullable
    private final String videoCoverImg;

    @SerializedName("vip_free")
    @Nullable
    private final String vipFree;

    @SerializedName("wechat_qrcode")
    @Nullable
    private final String weChatCode;

    @SerializedName("wechat_number")
    @Nullable
    private final String weChatNumber;

    @SerializedName("wechat_tips")
    @Nullable
    private final String weChatTips;

    public CourseDetailBean(@NotNull String id, @NotNull String title, int i2, @NotNull String type, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @NotNull String appId, @Nullable String str5, @Nullable String str6, @NotNull String categoryId, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable String str10, @Nullable ArrayList<String> arrayList, @Nullable String str11, @Nullable String str12, @Nullable String str13, @Nullable String str14, @Nullable String str15, @Nullable ArrayList<CourseTeacher> arrayList2, @Nullable ArrayList<CourseLabel> arrayList3, @Nullable String str16, @Nullable String str17, @Nullable String str18, @Nullable String str19, @Nullable String str20, @Nullable String str21, @NotNull String isPromotion, @Nullable OnlineServiceBean onlineServiceBean, @Nullable OnlineServiceBean onlineServiceBean2, @Nullable String str22, @Nullable String str23, @Nullable String str24, @Nullable String str25, @Nullable String str26, @Nullable String str27, @Nullable String str28, @Nullable String str29, @Nullable String str30, @Nullable String str31, @Nullable String str32, @Nullable String str33, @Nullable String str34, @Nullable String str35, @Nullable String str36, @Nullable String str37, @Nullable String str38, @Nullable ArrayList<BottomBtn> arrayList4, @Nullable ArrayList<CommonProblem> arrayList5, @Nullable String str39, @Nullable ArrayList<BuyRecord> arrayList6, @Nullable String str40, @Nullable String str41, @NotNull List<ParentCourseBean> parentCourse, @NotNull String showMode, @Nullable ArrayList<SkuItem> arrayList7, @Nullable ArrayList<CourseGiftItem> arrayList8, @Nullable ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> arrayList9, @Nullable GoodsVideo goodsVideo, @Nullable String str42, @Nullable String str43, @Nullable String str44) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(appId, "appId");
        Intrinsics.checkNotNullParameter(categoryId, "categoryId");
        Intrinsics.checkNotNullParameter(isPromotion, "isPromotion");
        Intrinsics.checkNotNullParameter(parentCourse, "parentCourse");
        Intrinsics.checkNotNullParameter(showMode, "showMode");
        this.id = id;
        this.title = title;
        this.dataType = i2;
        this.type = type;
        this.priceLabel = str;
        this.goodsType = str2;
        this.priceGray = str3;
        this.btnGray = str4;
        this.appId = appId;
        this.vipFree = str5;
        this.sVipFree = str6;
        this.categoryId = categoryId;
        this.isCoupon = str7;
        this.price = str8;
        this.inventory = str9;
        this.cover = str10;
        this.imgList = arrayList;
        this.vid = str11;
        this.video = str12;
        this.videoCoverImg = str13;
        this.description = str14;
        this.detail = str15;
        this.teacher = arrayList2;
        this.label = arrayList3;
        this.isBuy = str16;
        this.nowPrice = str17;
        this.goods_thumbnail = str18;
        this.originalPrice = str19;
        this.promotionPrice = str20;
        this.promotionCountDown = str21;
        this.isPromotion = isPromotion;
        this.beforeCs = onlineServiceBean;
        this.afterCs = onlineServiceBean2;
        this.salesVolume = str22;
        this.memberCount = str23;
        this.btnText = str24;
        this.validDate = str25;
        this.isAloneSale = str26;
        this.isSale = str27;
        this.isStopSale = str28;
        this.isBestSellers = str29;
        this.isEnd = str30;
        this.isHideTeacher = str31;
        this.isOpenQrCode = str32;
        this.weChatCode = str33;
        this.weChatTips = str34;
        this.weChatNumber = str35;
        this.isPermission = str36;
        this.minPrice = str37;
        this.maxPrice = str38;
        this.btnList = arrayList4;
        this.commonProblem = arrayList5;
        this.courseCount = str39;
        this.buyRecord = arrayList6;
        this.tbBuy = str40;
        this.giveWayType = str41;
        this.parentCourse = parentCourse;
        this.showMode = showMode;
        this.sku = arrayList7;
        this.gift = arrayList8;
        this.coupon = arrayList9;
        this.goods_video = goodsVideo;
        this.goods_sale_type = str42;
        this.buy_quantity = str43;
        this.times_limit = str44;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @Nullable
    /* renamed from: component10, reason: from getter */
    public final String getVipFree() {
        return this.vipFree;
    }

    @Nullable
    /* renamed from: component11, reason: from getter */
    public final String getSVipFree() {
        return this.sVipFree;
    }

    @NotNull
    /* renamed from: component12, reason: from getter */
    public final String getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    /* renamed from: component13, reason: from getter */
    public final String getIsCoupon() {
        return this.isCoupon;
    }

    @Nullable
    /* renamed from: component14, reason: from getter */
    public final String getPrice() {
        return this.price;
    }

    @Nullable
    /* renamed from: component15, reason: from getter */
    public final String getInventory() {
        return this.inventory;
    }

    @Nullable
    /* renamed from: component16, reason: from getter */
    public final String getCover() {
        return this.cover;
    }

    @Nullable
    public final ArrayList<String> component17() {
        return this.imgList;
    }

    @Nullable
    /* renamed from: component18, reason: from getter */
    public final String getVid() {
        return this.vid;
    }

    @Nullable
    /* renamed from: component19, reason: from getter */
    public final String getVideo() {
        return this.video;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component20, reason: from getter */
    public final String getVideoCoverImg() {
        return this.videoCoverImg;
    }

    @Nullable
    /* renamed from: component21, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    /* renamed from: component22, reason: from getter */
    public final String getDetail() {
        return this.detail;
    }

    @Nullable
    public final ArrayList<CourseTeacher> component23() {
        return this.teacher;
    }

    @Nullable
    public final ArrayList<CourseLabel> component24() {
        return this.label;
    }

    @Nullable
    /* renamed from: component25, reason: from getter */
    public final String getIsBuy() {
        return this.isBuy;
    }

    @Nullable
    /* renamed from: component26, reason: from getter */
    public final String getNowPrice() {
        return this.nowPrice;
    }

    @Nullable
    /* renamed from: component27, reason: from getter */
    public final String getGoods_thumbnail() {
        return this.goods_thumbnail;
    }

    @Nullable
    /* renamed from: component28, reason: from getter */
    public final String getOriginalPrice() {
        return this.originalPrice;
    }

    @Nullable
    /* renamed from: component29, reason: from getter */
    public final String getPromotionPrice() {
        return this.promotionPrice;
    }

    /* renamed from: component3, reason: from getter */
    public final int getDataType() {
        return this.dataType;
    }

    @Nullable
    /* renamed from: component30, reason: from getter */
    public final String getPromotionCountDown() {
        return this.promotionCountDown;
    }

    @NotNull
    /* renamed from: component31, reason: from getter */
    public final String getIsPromotion() {
        return this.isPromotion;
    }

    @Nullable
    /* renamed from: component32, reason: from getter */
    public final OnlineServiceBean getBeforeCs() {
        return this.beforeCs;
    }

    @Nullable
    /* renamed from: component33, reason: from getter */
    public final OnlineServiceBean getAfterCs() {
        return this.afterCs;
    }

    @Nullable
    /* renamed from: component34, reason: from getter */
    public final String getSalesVolume() {
        return this.salesVolume;
    }

    @Nullable
    /* renamed from: component35, reason: from getter */
    public final String getMemberCount() {
        return this.memberCount;
    }

    @Nullable
    /* renamed from: component36, reason: from getter */
    public final String getBtnText() {
        return this.btnText;
    }

    @Nullable
    /* renamed from: component37, reason: from getter */
    public final String getValidDate() {
        return this.validDate;
    }

    @Nullable
    /* renamed from: component38, reason: from getter */
    public final String getIsAloneSale() {
        return this.isAloneSale;
    }

    @Nullable
    /* renamed from: component39, reason: from getter */
    public final String getIsSale() {
        return this.isSale;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component40, reason: from getter */
    public final String getIsStopSale() {
        return this.isStopSale;
    }

    @Nullable
    /* renamed from: component41, reason: from getter */
    public final String getIsBestSellers() {
        return this.isBestSellers;
    }

    @Nullable
    /* renamed from: component42, reason: from getter */
    public final String getIsEnd() {
        return this.isEnd;
    }

    @Nullable
    /* renamed from: component43, reason: from getter */
    public final String getIsHideTeacher() {
        return this.isHideTeacher;
    }

    @Nullable
    /* renamed from: component44, reason: from getter */
    public final String getIsOpenQrCode() {
        return this.isOpenQrCode;
    }

    @Nullable
    /* renamed from: component45, reason: from getter */
    public final String getWeChatCode() {
        return this.weChatCode;
    }

    @Nullable
    /* renamed from: component46, reason: from getter */
    public final String getWeChatTips() {
        return this.weChatTips;
    }

    @Nullable
    /* renamed from: component47, reason: from getter */
    public final String getWeChatNumber() {
        return this.weChatNumber;
    }

    @Nullable
    /* renamed from: component48, reason: from getter */
    public final String getIsPermission() {
        return this.isPermission;
    }

    @Nullable
    /* renamed from: component49, reason: from getter */
    public final String getMinPrice() {
        return this.minPrice;
    }

    @Nullable
    /* renamed from: component5, reason: from getter */
    public final String getPriceLabel() {
        return this.priceLabel;
    }

    @Nullable
    /* renamed from: component50, reason: from getter */
    public final String getMaxPrice() {
        return this.maxPrice;
    }

    @Nullable
    public final ArrayList<BottomBtn> component51() {
        return this.btnList;
    }

    @Nullable
    public final ArrayList<CommonProblem> component52() {
        return this.commonProblem;
    }

    @Nullable
    /* renamed from: component53, reason: from getter */
    public final String getCourseCount() {
        return this.courseCount;
    }

    @Nullable
    public final ArrayList<BuyRecord> component54() {
        return this.buyRecord;
    }

    @Nullable
    /* renamed from: component55, reason: from getter */
    public final String getTbBuy() {
        return this.tbBuy;
    }

    @Nullable
    /* renamed from: component56, reason: from getter */
    public final String getGiveWayType() {
        return this.giveWayType;
    }

    @NotNull
    public final List<ParentCourseBean> component57() {
        return this.parentCourse;
    }

    @NotNull
    /* renamed from: component58, reason: from getter */
    public final String getShowMode() {
        return this.showMode;
    }

    @Nullable
    public final ArrayList<SkuItem> component59() {
        return this.sku;
    }

    @Nullable
    /* renamed from: component6, reason: from getter */
    public final String getGoodsType() {
        return this.goodsType;
    }

    @Nullable
    public final ArrayList<CourseGiftItem> component60() {
        return this.gift;
    }

    @Nullable
    public final ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> component61() {
        return this.coupon;
    }

    @Nullable
    /* renamed from: component62, reason: from getter */
    public final GoodsVideo getGoods_video() {
        return this.goods_video;
    }

    @Nullable
    /* renamed from: component63, reason: from getter */
    public final String getGoods_sale_type() {
        return this.goods_sale_type;
    }

    @Nullable
    /* renamed from: component64, reason: from getter */
    public final String getBuy_quantity() {
        return this.buy_quantity;
    }

    @Nullable
    /* renamed from: component65, reason: from getter */
    public final String getTimes_limit() {
        return this.times_limit;
    }

    @Nullable
    /* renamed from: component7, reason: from getter */
    public final String getPriceGray() {
        return this.priceGray;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getBtnGray() {
        return this.btnGray;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getAppId() {
        return this.appId;
    }

    @NotNull
    public final CourseDetailBean copy(@NotNull String id, @NotNull String title, int dataType, @NotNull String type, @Nullable String priceLabel, @Nullable String goodsType, @Nullable String priceGray, @Nullable String btnGray, @NotNull String appId, @Nullable String vipFree, @Nullable String sVipFree, @NotNull String categoryId, @Nullable String isCoupon, @Nullable String price, @Nullable String inventory, @Nullable String cover, @Nullable ArrayList<String> imgList, @Nullable String vid, @Nullable String video, @Nullable String videoCoverImg, @Nullable String description, @Nullable String detail, @Nullable ArrayList<CourseTeacher> teacher, @Nullable ArrayList<CourseLabel> label, @Nullable String isBuy, @Nullable String nowPrice, @Nullable String goods_thumbnail, @Nullable String originalPrice, @Nullable String promotionPrice, @Nullable String promotionCountDown, @NotNull String isPromotion, @Nullable OnlineServiceBean beforeCs, @Nullable OnlineServiceBean afterCs, @Nullable String salesVolume, @Nullable String memberCount, @Nullable String btnText, @Nullable String validDate, @Nullable String isAloneSale, @Nullable String isSale, @Nullable String isStopSale, @Nullable String isBestSellers, @Nullable String isEnd, @Nullable String isHideTeacher, @Nullable String isOpenQrCode, @Nullable String weChatCode, @Nullable String weChatTips, @Nullable String weChatNumber, @Nullable String isPermission, @Nullable String minPrice, @Nullable String maxPrice, @Nullable ArrayList<BottomBtn> btnList, @Nullable ArrayList<CommonProblem> commonProblem, @Nullable String courseCount, @Nullable ArrayList<BuyRecord> buyRecord, @Nullable String tbBuy, @Nullable String giveWayType, @NotNull List<ParentCourseBean> parentCourse, @NotNull String showMode, @Nullable ArrayList<SkuItem> sku, @Nullable ArrayList<CourseGiftItem> gift, @Nullable ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> coupon, @Nullable GoodsVideo goods_video, @Nullable String goods_sale_type, @Nullable String buy_quantity, @Nullable String times_limit) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(appId, "appId");
        Intrinsics.checkNotNullParameter(categoryId, "categoryId");
        Intrinsics.checkNotNullParameter(isPromotion, "isPromotion");
        Intrinsics.checkNotNullParameter(parentCourse, "parentCourse");
        Intrinsics.checkNotNullParameter(showMode, "showMode");
        return new CourseDetailBean(id, title, dataType, type, priceLabel, goodsType, priceGray, btnGray, appId, vipFree, sVipFree, categoryId, isCoupon, price, inventory, cover, imgList, vid, video, videoCoverImg, description, detail, teacher, label, isBuy, nowPrice, goods_thumbnail, originalPrice, promotionPrice, promotionCountDown, isPromotion, beforeCs, afterCs, salesVolume, memberCount, btnText, validDate, isAloneSale, isSale, isStopSale, isBestSellers, isEnd, isHideTeacher, isOpenQrCode, weChatCode, weChatTips, weChatNumber, isPermission, minPrice, maxPrice, btnList, commonProblem, courseCount, buyRecord, tbBuy, giveWayType, parentCourse, showMode, sku, gift, coupon, goods_video, goods_sale_type, buy_quantity, times_limit);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CourseDetailBean)) {
            return false;
        }
        CourseDetailBean courseDetailBean = (CourseDetailBean) other;
        return Intrinsics.areEqual(this.id, courseDetailBean.id) && Intrinsics.areEqual(this.title, courseDetailBean.title) && this.dataType == courseDetailBean.dataType && Intrinsics.areEqual(this.type, courseDetailBean.type) && Intrinsics.areEqual(this.priceLabel, courseDetailBean.priceLabel) && Intrinsics.areEqual(this.goodsType, courseDetailBean.goodsType) && Intrinsics.areEqual(this.priceGray, courseDetailBean.priceGray) && Intrinsics.areEqual(this.btnGray, courseDetailBean.btnGray) && Intrinsics.areEqual(this.appId, courseDetailBean.appId) && Intrinsics.areEqual(this.vipFree, courseDetailBean.vipFree) && Intrinsics.areEqual(this.sVipFree, courseDetailBean.sVipFree) && Intrinsics.areEqual(this.categoryId, courseDetailBean.categoryId) && Intrinsics.areEqual(this.isCoupon, courseDetailBean.isCoupon) && Intrinsics.areEqual(this.price, courseDetailBean.price) && Intrinsics.areEqual(this.inventory, courseDetailBean.inventory) && Intrinsics.areEqual(this.cover, courseDetailBean.cover) && Intrinsics.areEqual(this.imgList, courseDetailBean.imgList) && Intrinsics.areEqual(this.vid, courseDetailBean.vid) && Intrinsics.areEqual(this.video, courseDetailBean.video) && Intrinsics.areEqual(this.videoCoverImg, courseDetailBean.videoCoverImg) && Intrinsics.areEqual(this.description, courseDetailBean.description) && Intrinsics.areEqual(this.detail, courseDetailBean.detail) && Intrinsics.areEqual(this.teacher, courseDetailBean.teacher) && Intrinsics.areEqual(this.label, courseDetailBean.label) && Intrinsics.areEqual(this.isBuy, courseDetailBean.isBuy) && Intrinsics.areEqual(this.nowPrice, courseDetailBean.nowPrice) && Intrinsics.areEqual(this.goods_thumbnail, courseDetailBean.goods_thumbnail) && Intrinsics.areEqual(this.originalPrice, courseDetailBean.originalPrice) && Intrinsics.areEqual(this.promotionPrice, courseDetailBean.promotionPrice) && Intrinsics.areEqual(this.promotionCountDown, courseDetailBean.promotionCountDown) && Intrinsics.areEqual(this.isPromotion, courseDetailBean.isPromotion) && Intrinsics.areEqual(this.beforeCs, courseDetailBean.beforeCs) && Intrinsics.areEqual(this.afterCs, courseDetailBean.afterCs) && Intrinsics.areEqual(this.salesVolume, courseDetailBean.salesVolume) && Intrinsics.areEqual(this.memberCount, courseDetailBean.memberCount) && Intrinsics.areEqual(this.btnText, courseDetailBean.btnText) && Intrinsics.areEqual(this.validDate, courseDetailBean.validDate) && Intrinsics.areEqual(this.isAloneSale, courseDetailBean.isAloneSale) && Intrinsics.areEqual(this.isSale, courseDetailBean.isSale) && Intrinsics.areEqual(this.isStopSale, courseDetailBean.isStopSale) && Intrinsics.areEqual(this.isBestSellers, courseDetailBean.isBestSellers) && Intrinsics.areEqual(this.isEnd, courseDetailBean.isEnd) && Intrinsics.areEqual(this.isHideTeacher, courseDetailBean.isHideTeacher) && Intrinsics.areEqual(this.isOpenQrCode, courseDetailBean.isOpenQrCode) && Intrinsics.areEqual(this.weChatCode, courseDetailBean.weChatCode) && Intrinsics.areEqual(this.weChatTips, courseDetailBean.weChatTips) && Intrinsics.areEqual(this.weChatNumber, courseDetailBean.weChatNumber) && Intrinsics.areEqual(this.isPermission, courseDetailBean.isPermission) && Intrinsics.areEqual(this.minPrice, courseDetailBean.minPrice) && Intrinsics.areEqual(this.maxPrice, courseDetailBean.maxPrice) && Intrinsics.areEqual(this.btnList, courseDetailBean.btnList) && Intrinsics.areEqual(this.commonProblem, courseDetailBean.commonProblem) && Intrinsics.areEqual(this.courseCount, courseDetailBean.courseCount) && Intrinsics.areEqual(this.buyRecord, courseDetailBean.buyRecord) && Intrinsics.areEqual(this.tbBuy, courseDetailBean.tbBuy) && Intrinsics.areEqual(this.giveWayType, courseDetailBean.giveWayType) && Intrinsics.areEqual(this.parentCourse, courseDetailBean.parentCourse) && Intrinsics.areEqual(this.showMode, courseDetailBean.showMode) && Intrinsics.areEqual(this.sku, courseDetailBean.sku) && Intrinsics.areEqual(this.gift, courseDetailBean.gift) && Intrinsics.areEqual(this.coupon, courseDetailBean.coupon) && Intrinsics.areEqual(this.goods_video, courseDetailBean.goods_video) && Intrinsics.areEqual(this.goods_sale_type, courseDetailBean.goods_sale_type) && Intrinsics.areEqual(this.buy_quantity, courseDetailBean.buy_quantity) && Intrinsics.areEqual(this.times_limit, courseDetailBean.times_limit);
    }

    @Nullable
    public final OnlineServiceBean getAfterCs() {
        return this.afterCs;
    }

    @NotNull
    public final String getAppId() {
        return this.appId;
    }

    @Nullable
    public final OnlineServiceBean getBeforeCs() {
        return this.beforeCs;
    }

    @Nullable
    public final String getBtnGray() {
        return this.btnGray;
    }

    @Nullable
    public final ArrayList<BottomBtn> getBtnList() {
        return this.btnList;
    }

    @Nullable
    public final String getBtnText() {
        return this.btnText;
    }

    @Nullable
    public final ArrayList<BuyRecord> getBuyRecord() {
        return this.buyRecord;
    }

    @Nullable
    public final String getBuy_quantity() {
        return this.buy_quantity;
    }

    @NotNull
    public final String getCategoryId() {
        return this.categoryId;
    }

    @Nullable
    public final ArrayList<CommonProblem> getCommonProblem() {
        return this.commonProblem;
    }

    @Nullable
    public final ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> getCoupon() {
        return this.coupon;
    }

    @Nullable
    public final String getCourseCount() {
        return this.courseCount;
    }

    @Nullable
    public final String getCover() {
        return this.cover;
    }

    public final int getDataType() {
        return this.dataType;
    }

    @Nullable
    public final String getDescription() {
        return this.description;
    }

    @Nullable
    public final String getDetail() {
        return this.detail;
    }

    @Nullable
    public final ArrayList<CourseGiftItem> getGift() {
        return this.gift;
    }

    @Nullable
    public final String getGiveWayType() {
        return this.giveWayType;
    }

    @Nullable
    public final String getGoodsType() {
        return this.goodsType;
    }

    @Nullable
    public final String getGoods_sale_type() {
        return this.goods_sale_type;
    }

    @Nullable
    public final String getGoods_thumbnail() {
        return this.goods_thumbnail;
    }

    @Nullable
    public final GoodsVideo getGoods_video() {
        return this.goods_video;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @Nullable
    public final ArrayList<String> getImgList() {
        return this.imgList;
    }

    @Nullable
    public final String getInventory() {
        return this.inventory;
    }

    @Nullable
    public final ArrayList<CourseLabel> getLabel() {
        return this.label;
    }

    @Nullable
    public final String getMaxPrice() {
        return this.maxPrice;
    }

    @Nullable
    public final String getMemberCount() {
        return this.memberCount;
    }

    @Nullable
    public final String getMinPrice() {
        return this.minPrice;
    }

    @Nullable
    public final String getNowPrice() {
        return this.nowPrice;
    }

    @Nullable
    public final String getOriginalPrice() {
        return this.originalPrice;
    }

    @NotNull
    public final List<ParentCourseBean> getParentCourse() {
        return this.parentCourse;
    }

    @Nullable
    public final String getPrice() {
        return this.price;
    }

    @Nullable
    public final String getPriceGray() {
        return this.priceGray;
    }

    @Nullable
    public final String getPriceLabel() {
        return this.priceLabel;
    }

    @Nullable
    public final String getPromotionCountDown() {
        return this.promotionCountDown;
    }

    @Nullable
    public final String getPromotionPrice() {
        return this.promotionPrice;
    }

    @Nullable
    public final String getSVipFree() {
        return this.sVipFree;
    }

    @Nullable
    public final String getSalesVolume() {
        return this.salesVolume;
    }

    @NotNull
    public final String getShowMode() {
        return this.showMode;
    }

    @Nullable
    public final ArrayList<SkuItem> getSku() {
        return this.sku;
    }

    @Nullable
    public final String getTbBuy() {
        return this.tbBuy;
    }

    @Nullable
    public final ArrayList<CourseTeacher> getTeacher() {
        return this.teacher;
    }

    @Nullable
    public final String getTimes_limit() {
        return this.times_limit;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getType() {
        return this.type;
    }

    @Nullable
    public final String getValidDate() {
        return this.validDate;
    }

    @Nullable
    public final String getVid() {
        return this.vid;
    }

    @Nullable
    public final String getVideo() {
        return this.video;
    }

    @Nullable
    public final String getVideoCoverImg() {
        return this.videoCoverImg;
    }

    @Nullable
    public final String getVipFree() {
        return this.vipFree;
    }

    @Nullable
    public final String getWeChatCode() {
        return this.weChatCode;
    }

    @Nullable
    public final String getWeChatNumber() {
        return this.weChatNumber;
    }

    @Nullable
    public final String getWeChatTips() {
        return this.weChatTips;
    }

    public final boolean hasBuy() {
        return Intrinsics.areEqual("1", this.isBuy);
    }

    public final boolean hasPermission() {
        return Intrinsics.areEqual("1", this.isPermission);
    }

    public int hashCode() {
        int iHashCode = ((((((this.id.hashCode() * 31) + this.title.hashCode()) * 31) + this.dataType) * 31) + this.type.hashCode()) * 31;
        String str = this.priceLabel;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.goodsType;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.priceGray;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.btnGray;
        int iHashCode5 = (((iHashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31) + this.appId.hashCode()) * 31;
        String str5 = this.vipFree;
        int iHashCode6 = (iHashCode5 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.sVipFree;
        int iHashCode7 = (((iHashCode6 + (str6 == null ? 0 : str6.hashCode())) * 31) + this.categoryId.hashCode()) * 31;
        String str7 = this.isCoupon;
        int iHashCode8 = (iHashCode7 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.price;
        int iHashCode9 = (iHashCode8 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.inventory;
        int iHashCode10 = (iHashCode9 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.cover;
        int iHashCode11 = (iHashCode10 + (str10 == null ? 0 : str10.hashCode())) * 31;
        ArrayList<String> arrayList = this.imgList;
        int iHashCode12 = (iHashCode11 + (arrayList == null ? 0 : arrayList.hashCode())) * 31;
        String str11 = this.vid;
        int iHashCode13 = (iHashCode12 + (str11 == null ? 0 : str11.hashCode())) * 31;
        String str12 = this.video;
        int iHashCode14 = (iHashCode13 + (str12 == null ? 0 : str12.hashCode())) * 31;
        String str13 = this.videoCoverImg;
        int iHashCode15 = (iHashCode14 + (str13 == null ? 0 : str13.hashCode())) * 31;
        String str14 = this.description;
        int iHashCode16 = (iHashCode15 + (str14 == null ? 0 : str14.hashCode())) * 31;
        String str15 = this.detail;
        int iHashCode17 = (iHashCode16 + (str15 == null ? 0 : str15.hashCode())) * 31;
        ArrayList<CourseTeacher> arrayList2 = this.teacher;
        int iHashCode18 = (iHashCode17 + (arrayList2 == null ? 0 : arrayList2.hashCode())) * 31;
        ArrayList<CourseLabel> arrayList3 = this.label;
        int iHashCode19 = (iHashCode18 + (arrayList3 == null ? 0 : arrayList3.hashCode())) * 31;
        String str16 = this.isBuy;
        int iHashCode20 = (iHashCode19 + (str16 == null ? 0 : str16.hashCode())) * 31;
        String str17 = this.nowPrice;
        int iHashCode21 = (iHashCode20 + (str17 == null ? 0 : str17.hashCode())) * 31;
        String str18 = this.goods_thumbnail;
        int iHashCode22 = (iHashCode21 + (str18 == null ? 0 : str18.hashCode())) * 31;
        String str19 = this.originalPrice;
        int iHashCode23 = (iHashCode22 + (str19 == null ? 0 : str19.hashCode())) * 31;
        String str20 = this.promotionPrice;
        int iHashCode24 = (iHashCode23 + (str20 == null ? 0 : str20.hashCode())) * 31;
        String str21 = this.promotionCountDown;
        int iHashCode25 = (((iHashCode24 + (str21 == null ? 0 : str21.hashCode())) * 31) + this.isPromotion.hashCode()) * 31;
        OnlineServiceBean onlineServiceBean = this.beforeCs;
        int iHashCode26 = (iHashCode25 + (onlineServiceBean == null ? 0 : onlineServiceBean.hashCode())) * 31;
        OnlineServiceBean onlineServiceBean2 = this.afterCs;
        int iHashCode27 = (iHashCode26 + (onlineServiceBean2 == null ? 0 : onlineServiceBean2.hashCode())) * 31;
        String str22 = this.salesVolume;
        int iHashCode28 = (iHashCode27 + (str22 == null ? 0 : str22.hashCode())) * 31;
        String str23 = this.memberCount;
        int iHashCode29 = (iHashCode28 + (str23 == null ? 0 : str23.hashCode())) * 31;
        String str24 = this.btnText;
        int iHashCode30 = (iHashCode29 + (str24 == null ? 0 : str24.hashCode())) * 31;
        String str25 = this.validDate;
        int iHashCode31 = (iHashCode30 + (str25 == null ? 0 : str25.hashCode())) * 31;
        String str26 = this.isAloneSale;
        int iHashCode32 = (iHashCode31 + (str26 == null ? 0 : str26.hashCode())) * 31;
        String str27 = this.isSale;
        int iHashCode33 = (iHashCode32 + (str27 == null ? 0 : str27.hashCode())) * 31;
        String str28 = this.isStopSale;
        int iHashCode34 = (iHashCode33 + (str28 == null ? 0 : str28.hashCode())) * 31;
        String str29 = this.isBestSellers;
        int iHashCode35 = (iHashCode34 + (str29 == null ? 0 : str29.hashCode())) * 31;
        String str30 = this.isEnd;
        int iHashCode36 = (iHashCode35 + (str30 == null ? 0 : str30.hashCode())) * 31;
        String str31 = this.isHideTeacher;
        int iHashCode37 = (iHashCode36 + (str31 == null ? 0 : str31.hashCode())) * 31;
        String str32 = this.isOpenQrCode;
        int iHashCode38 = (iHashCode37 + (str32 == null ? 0 : str32.hashCode())) * 31;
        String str33 = this.weChatCode;
        int iHashCode39 = (iHashCode38 + (str33 == null ? 0 : str33.hashCode())) * 31;
        String str34 = this.weChatTips;
        int iHashCode40 = (iHashCode39 + (str34 == null ? 0 : str34.hashCode())) * 31;
        String str35 = this.weChatNumber;
        int iHashCode41 = (iHashCode40 + (str35 == null ? 0 : str35.hashCode())) * 31;
        String str36 = this.isPermission;
        int iHashCode42 = (iHashCode41 + (str36 == null ? 0 : str36.hashCode())) * 31;
        String str37 = this.minPrice;
        int iHashCode43 = (iHashCode42 + (str37 == null ? 0 : str37.hashCode())) * 31;
        String str38 = this.maxPrice;
        int iHashCode44 = (iHashCode43 + (str38 == null ? 0 : str38.hashCode())) * 31;
        ArrayList<BottomBtn> arrayList4 = this.btnList;
        int iHashCode45 = (iHashCode44 + (arrayList4 == null ? 0 : arrayList4.hashCode())) * 31;
        ArrayList<CommonProblem> arrayList5 = this.commonProblem;
        int iHashCode46 = (iHashCode45 + (arrayList5 == null ? 0 : arrayList5.hashCode())) * 31;
        String str39 = this.courseCount;
        int iHashCode47 = (iHashCode46 + (str39 == null ? 0 : str39.hashCode())) * 31;
        ArrayList<BuyRecord> arrayList6 = this.buyRecord;
        int iHashCode48 = (iHashCode47 + (arrayList6 == null ? 0 : arrayList6.hashCode())) * 31;
        String str40 = this.tbBuy;
        int iHashCode49 = (iHashCode48 + (str40 == null ? 0 : str40.hashCode())) * 31;
        String str41 = this.giveWayType;
        int iHashCode50 = (((((iHashCode49 + (str41 == null ? 0 : str41.hashCode())) * 31) + this.parentCourse.hashCode()) * 31) + this.showMode.hashCode()) * 31;
        ArrayList<SkuItem> arrayList7 = this.sku;
        int iHashCode51 = (iHashCode50 + (arrayList7 == null ? 0 : arrayList7.hashCode())) * 31;
        ArrayList<CourseGiftItem> arrayList8 = this.gift;
        int iHashCode52 = (iHashCode51 + (arrayList8 == null ? 0 : arrayList8.hashCode())) * 31;
        ArrayList<RedEnvelopeCouponsBean.RedEnvelopeCouponsDataItem> arrayList9 = this.coupon;
        int iHashCode53 = (iHashCode52 + (arrayList9 == null ? 0 : arrayList9.hashCode())) * 31;
        GoodsVideo goodsVideo = this.goods_video;
        int iHashCode54 = (iHashCode53 + (goodsVideo == null ? 0 : goodsVideo.hashCode())) * 31;
        String str42 = this.goods_sale_type;
        int iHashCode55 = (iHashCode54 + (str42 == null ? 0 : str42.hashCode())) * 31;
        String str43 = this.buy_quantity;
        int iHashCode56 = (iHashCode55 + (str43 == null ? 0 : str43.hashCode())) * 31;
        String str44 = this.times_limit;
        return iHashCode56 + (str44 != null ? str44.hashCode() : 0);
    }

    @Nullable
    public final String isAloneSale() {
        return this.isAloneSale;
    }

    public final boolean isAloneSle() {
        return Intrinsics.areEqual("1", this.isAloneSale);
    }

    @Nullable
    public final String isBestSellers() {
        return this.isBestSellers;
    }

    @Nullable
    public final String isBuy() {
        return this.isBuy;
    }

    @Nullable
    public final String isCoupon() {
        return this.isCoupon;
    }

    @Nullable
    public final String isEnd() {
        return this.isEnd;
    }

    @Nullable
    public final String isHideTeacher() {
        return this.isHideTeacher;
    }

    @Nullable
    public final String isOpenQrCode() {
        return this.isOpenQrCode;
    }

    @Nullable
    public final String isPermission() {
        return this.isPermission;
    }

    @NotNull
    public final String isPromotion() {
        return this.isPromotion;
    }

    @Nullable
    public final String isSale() {
        return this.isSale;
    }

    @Nullable
    public final String isStopSale() {
        return this.isStopSale;
    }

    public final void setDataType(int i2) {
        this.dataType = i2;
    }

    public final void setImgList(@Nullable ArrayList<String> arrayList) {
        this.imgList = arrayList;
    }

    public final void setInventory(@Nullable String str) {
        this.inventory = str;
    }

    public final void setLabel(@Nullable ArrayList<CourseLabel> arrayList) {
        this.label = arrayList;
    }

    public final void setPrice(@Nullable String str) {
        this.price = str;
    }

    public final void setPromotionCountDown(@Nullable String str) {
        this.promotionCountDown = str;
    }

    public final void setSku(@Nullable ArrayList<SkuItem> arrayList) {
        this.sku = arrayList;
    }

    @NotNull
    public String toString() {
        return "CourseDetailBean(id=" + this.id + ", title=" + this.title + ", dataType=" + this.dataType + ", type=" + this.type + ", priceLabel=" + this.priceLabel + ", goodsType=" + this.goodsType + ", priceGray=" + this.priceGray + ", btnGray=" + this.btnGray + ", appId=" + this.appId + ", vipFree=" + this.vipFree + ", sVipFree=" + this.sVipFree + ", categoryId=" + this.categoryId + ", isCoupon=" + this.isCoupon + ", price=" + this.price + ", inventory=" + this.inventory + ", cover=" + this.cover + ", imgList=" + this.imgList + ", vid=" + this.vid + ", video=" + this.video + ", videoCoverImg=" + this.videoCoverImg + ", description=" + this.description + ", detail=" + this.detail + ", teacher=" + this.teacher + ", label=" + this.label + ", isBuy=" + this.isBuy + ", nowPrice=" + this.nowPrice + ", goods_thumbnail=" + this.goods_thumbnail + ", originalPrice=" + this.originalPrice + ", promotionPrice=" + this.promotionPrice + ", promotionCountDown=" + this.promotionCountDown + ", isPromotion=" + this.isPromotion + ", beforeCs=" + this.beforeCs + ", afterCs=" + this.afterCs + ", salesVolume=" + this.salesVolume + ", memberCount=" + this.memberCount + ", btnText=" + this.btnText + ", validDate=" + this.validDate + ", isAloneSale=" + this.isAloneSale + ", isSale=" + this.isSale + ", isStopSale=" + this.isStopSale + ", isBestSellers=" + this.isBestSellers + ", isEnd=" + this.isEnd + ", isHideTeacher=" + this.isHideTeacher + ", isOpenQrCode=" + this.isOpenQrCode + ", weChatCode=" + this.weChatCode + ", weChatTips=" + this.weChatTips + ", weChatNumber=" + this.weChatNumber + ", isPermission=" + this.isPermission + ", minPrice=" + this.minPrice + ", maxPrice=" + this.maxPrice + ", btnList=" + this.btnList + ", commonProblem=" + this.commonProblem + ", courseCount=" + this.courseCount + ", buyRecord=" + this.buyRecord + ", tbBuy=" + this.tbBuy + ", giveWayType=" + this.giveWayType + ", parentCourse=" + this.parentCourse + ", showMode=" + this.showMode + ", sku=" + this.sku + ", gift=" + this.gift + ", coupon=" + this.coupon + ", goods_video=" + this.goods_video + ", goods_sale_type=" + this.goods_sale_type + ", buy_quantity=" + this.buy_quantity + ", times_limit=" + this.times_limit + ')';
    }

    /* renamed from: isSale, reason: collision with other method in class */
    public final boolean m99isSale() {
        return Intrinsics.areEqual("1", this.isSale);
    }

    public /* synthetic */ CourseDetailBean(String str, String str2, int i2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, String str13, String str14, String str15, ArrayList arrayList, String str16, String str17, String str18, String str19, String str20, ArrayList arrayList2, ArrayList arrayList3, String str21, String str22, String str23, String str24, String str25, String str26, String str27, OnlineServiceBean onlineServiceBean, OnlineServiceBean onlineServiceBean2, String str28, String str29, String str30, String str31, String str32, String str33, String str34, String str35, String str36, String str37, String str38, String str39, String str40, String str41, String str42, String str43, String str44, ArrayList arrayList4, ArrayList arrayList5, String str45, ArrayList arrayList6, String str46, String str47, List list, String str48, ArrayList arrayList7, ArrayList arrayList8, ArrayList arrayList9, GoodsVideo goodsVideo, String str49, String str50, String str51, int i3, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i3 & 4) != 0 ? 1 : i2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14, str15, arrayList, str16, str17, str18, str19, str20, (i3 & 4194304) != 0 ? new ArrayList() : arrayList2, arrayList3, str21, str22, str23, str24, str25, str26, str27, onlineServiceBean, onlineServiceBean2, str28, str29, str30, str31, str32, str33, str34, str35, str36, str37, str38, str39, str40, str41, str42, str43, str44, arrayList4, arrayList5, str45, arrayList6, str46, str47, list, (i4 & 33554432) != 0 ? "0" : str48, arrayList7, arrayList8, arrayList9, goodsVideo, str49, str50, str51);
    }
}
