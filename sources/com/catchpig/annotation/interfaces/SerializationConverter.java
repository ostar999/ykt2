package com.catchpig.annotation.interfaces;

import androidx.exifinterface.media.ExifInterface;
import com.aliyun.auth.common.AliyunVodHttpCommon;
import kotlin.Metadata;
import kotlinx.serialization.json.Json;
import org.jetbrains.annotations.NotNull;
import retrofit2.Converter;

@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u00020\u0003R\u0018\u0010\u0004\u001a\u00020\u0005X¦\u000e¢\u0006\f\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\n"}, d2 = {"Lcom/catchpig/annotation/interfaces/SerializationConverter;", "F", ExifInterface.GPS_DIRECTION_TRUE, "Lretrofit2/Converter;", AliyunVodHttpCommon.Format.FORMAT_JSON, "Lkotlinx/serialization/json/Json;", "getJson", "()Lkotlinx/serialization/json/Json;", "setJson", "(Lkotlinx/serialization/json/Json;)V", "annotation"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public interface SerializationConverter<F, T> extends Converter<F, T> {
    @NotNull
    Json getJson();

    void setJson(@NotNull Json json);
}
