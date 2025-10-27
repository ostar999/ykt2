package com.yddmi.doctor.network.api;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0015\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0003\bÁ\u0001\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0007\"\u0004\b\u000e\u0010\tR\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00101\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00102\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00104\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00105\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00106\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010<\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010B\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010D\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010F\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010R\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010Y\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010[\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\\\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010]\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010^\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010_\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010d\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010g\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010h\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010i\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010j\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010k\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010l\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010m\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010o\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010p\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010q\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010u\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010v\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010w\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010x\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010y\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010z\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010{\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010|\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010}\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010~\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u007f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0080\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0081\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0082\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0083\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0084\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0085\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0086\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0087\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0088\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0089\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u008a\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u008b\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u008c\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u008d\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u008e\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u008f\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0090\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0091\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0092\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0093\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0094\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0095\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0096\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0097\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0098\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u0099\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u009a\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u009b\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u009c\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u009d\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u009e\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u009f\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010 \u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¡\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¢\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010£\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¤\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¥\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¦\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010§\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¨\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010©\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010ª\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010«\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¬\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010\u00ad\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010®\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¯\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010°\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010±\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010²\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010³\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010´\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010µ\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¶\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010·\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¸\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¹\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010º\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010»\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¼\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010½\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¾\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010¿\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010À\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010Á\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010Â\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010Ã\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000f\u0010Ä\u0001\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006Å\u0001"}, d2 = {"Lcom/yddmi/doctor/network/api/YddApi;", "", "()V", "authLogin", "", "baseUrl", "getBaseUrl", "()Ljava/lang/String;", "setBaseUrl", "(Ljava/lang/String;)V", "baseUrlDev", "baseUrlDevFile", "baseUrlFile", "getBaseUrlFile", "setBaseUrlFile", "baseUrlFormal", "baseUrlTest", "baseUrlTest126", "baseUrlTest126File", "baseUrlTest192", "baseUrlTest192File", "baseUrlUat", "baseUrlUat192File", "codeLogin", "commitExamInfo", "examDetailInfo", "examResult", "forgetPwd", "gateWay", "getAllNoticeRead", "getAllRead", "getAppGeneralConfig", "getAppHomeList", "getAppKnowledgeStatistics", "getAppTableList", "getAppVersion", "getAppWarn", "getAskTip", "getAssistCheckCategoryTree", "getAssistTip", "getBodyCheck", "getBodyCheckTip", "getChatGptUse", "getClinicalGuideline", "getClinicalGuidelineDetail", "getConfigWhite", "getContact", "getDepartment", "getDiagnoseClinicalGuide", "getDiagnoseTip", "getDictConfigLabel", "getEcommendedVideo", "getEcommendedVideoList", "getExamByCode", "getExamByUser", "getExamHorseLamp", "getExamQuestionList", "getFeedBackPage", "getGardeScore", "getHomeBanner", "getHomeInfoList", "getHomePatient", "getHorseLamp", "getInformationTree", "getIntegralApp", "getInviterInfo", "getKnowledgeAtlasID", "getKnowledgeDetails", "getLeaderBoard", "getMaintain", "getMedicineDirectionTree", "getMedicineSearchList", "getMessageList", "getNoticeList", "getOrderStr", "getOrganzationSearch", "getPatientAskGetByKeyword", "getPatientInfoById", "getPatientNote", "getPatientTreeRecord", "getPersonInfoApp", "getPopUserList", "getPostPersonInfo", "getPostUserBankInfo", "getPracticeRecord", "getPracticeRecordExam", "getPrepayWx", "getProductInfo", "getProductInfoSkill", "getPushCodeForgetPwd", "getPushCodeRegister", "getRandomSkill", "getReadNoticeId", "getRecommendInfo", "getRecordEquityDetail", "getRecordEquityDetailV1", "getReplyAppList", "getReportAnalyseRadar", "getReportGradeItemScore", "getReportHarmItem", "getReportHelpItem", "getReportIdentityDiagnose", "getReportIrrelevantItem", "getReportMainDiagnose", "getReportOtherDiagnose", "getReportRatioCorrect", "getReportRatioMatch", "getRulelatest", "getSicknessRelationList", "getSkillBasic", "getSkillBasicHome", "getSkillBodyHome", "getSkillHome", "getSkillHomePage", "getSkillMy", "getSkillRandom", "getSkillRecord", "getSkillTimes", "getSysTrainRecordGetKeyWord", "getTeachingCenter", "getTeachingList", "getTeachingResourceCenter", "getTeachingResourceList", "getTrainInfoFindById", "getTrainInfoStudyList", "getTrainMedical", "getTrainNoteById", "getTrainRecordListAsk", "getTreatmentCategoryTree", "getTreatmentTip", "getUnreadCount", "getUnreadNoticeCount", "getUserCoupon", "getUserExamList", "getUserSpecificCoupon", "getVeriFyMsg", "getVersionU3d", "hostFileFormal", "patientAskGetAskTree", "patientAssistGetBindData", "patientInfoGetById", "patientInfoList", "patientInfoRandom", "patientInfoSave", "postAgentLogin", "postAppFavoriteSave", "postAppRead", "postAppTableSave", "postClinicalGuideline", "postCouponRecord", "postExamCheat", "postFeedBackAdd", "postFileUploadBatch", "postIntegralAcquire", "postIntegralFreeReceive", "postIntegralUnlock", "postInviteLogin", "postInviterSave", "postKnowledgeAtlasQuary", "postKnowledgeLearnRecordSave", "postOneclickLogin", "postPatientAssistInfo", "postPatientNoteSave", "postPersonSubmit", "postPointSave", "postPopUserUpdateStatus", "postRedeemCode", "postReplySave", "postTenantInfoRegister", "postTrainMedicalSave", "postTrainNoteSave", "postUserBinding", "postUserReBinding", "postWxLogin", "readMessageList", "saveExamQuestionResult", "startExam", "sysAssistCheck", "sysAssistCheckGetTree", "sysDiseaseList", "sysTreatment", "sysTreatmentGetTree", "trainDagnoseSave", "trainDiagnoseBasisGetSavedBasis", "trainDiagnoseBasisSaveBasis", "trainDiagnoseDelete", "trainDiagnoseGetByTrainId", "trainDiagnoseUpdate", "trainInfoGetUnCompletee", "trainInfoList", "trainInfoUpdateStatus", "trainRecordBatchSave", "trainRecordListDoneDetails", "trainRecordSave", "updatePwd", "uploadImagesHeader", "userAccountCancel", "app_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class YddApi {

    @NotNull
    public static final String authLogin = "auth/login";

    @NotNull
    public static final String baseUrlDev = "http://192.168.3.113/";

    @NotNull
    public static final String baseUrlDevFile = "http://192.168.3.113/file/";

    @NotNull
    public static final String baseUrlFormal = "https://www.medmeta.com/";

    @NotNull
    public static final String baseUrlTest = "http://59.173.18.239:2336/";

    @NotNull
    public static final String baseUrlTest126 = "https://192.168.3.126/";

    @NotNull
    public static final String baseUrlTest126File = "http://192.168.3.124:9000/";

    @NotNull
    public static final String baseUrlTest192 = "https://192.168.3.123/";

    @NotNull
    public static final String baseUrlTest192File = "http://192.168.3.121:9000/";

    @NotNull
    public static final String baseUrlUat = "https://192.168.3.192/";

    @NotNull
    public static final String baseUrlUat192File = "http://192.168.3.191:9000/";

    @NotNull
    public static final String codeLogin = "auth/register";

    @NotNull
    public static final String commitExamInfo = "exam/examResult/updateExamResultStatus";

    @NotNull
    public static final String examDetailInfo = "exam/examResult/getExamResultDetail";

    @NotNull
    public static final String examResult = "exam/examResult/getExamResultVo";

    @NotNull
    public static final String forgetPwd = "system/user/profile/forgetPwd";

    @NotNull
    public static final String gateWay = "api/";

    @NotNull
    public static final String getAllNoticeRead = "msgcenter/notice/readAll";

    @NotNull
    public static final String getAllRead = "msgcenter/messagePush/read";

    @NotNull
    public static final String getAppGeneralConfig = "platform/appGeneralConfig/search";

    @NotNull
    public static final String getAppHomeList = "system/appMenu/manager/appHome/list";

    @NotNull
    public static final String getAppKnowledgeStatistics = "base/app/knowledge/statistics/";

    @NotNull
    public static final String getAppTableList = "system/appMenu/manager/appTable/list";

    @NotNull
    public static final String getAppVersion = "platform/upgrade/maintain/latest/2";

    @NotNull
    public static final String getAppWarn = "msgcenter/notice/appWarn";

    @NotNull
    public static final String getAskTip = "cmtt/patient/ask/tip";

    @NotNull
    public static final String getAssistCheckCategoryTree = "cmtt/sys/assistCheck/category/tree";

    @NotNull
    public static final String getAssistTip = "cmtt/patient/assist/tip";

    @NotNull
    public static final String getBodyCheck = "base/bodyCheck/home";

    @NotNull
    public static final String getBodyCheckTip = "cmtt/patient/bodyCheck/tip";

    @NotNull
    public static final String getChatGptUse = "system/user/chatGptUse";

    @NotNull
    public static final String getClinicalGuideline = "platform/information/list";

    @NotNull
    public static final String getClinicalGuidelineDetail = "platform/information/";

    @NotNull
    public static final String getConfigWhite = "system/config/whiteList";

    @NotNull
    public static final String getContact = "base/skill/contact/get";

    @NotNull
    public static final String getDepartment = "cmtt/tbDepartment/getTree";

    @NotNull
    public static final String getDiagnoseClinicalGuide = "cmtt/patient/diagnose/clinicalGuide";

    @NotNull
    public static final String getDiagnoseTip = "cmtt/patient/diagnose/tip";

    @NotNull
    public static final String getDictConfigLabel = "base/dictConfig/label/";

    @NotNull
    public static final String getEcommendedVideo = "cmtt/ecommendedVideo/";

    @NotNull
    public static final String getEcommendedVideoList = "cmtt/ecommendedVideo/list";

    @NotNull
    public static final String getExamByCode = "exam/examCandidate/getExamByUserKeyWord";

    @NotNull
    public static final String getExamByUser = "exam/examCandidate/getExamByUser";

    @NotNull
    public static final String getExamHorseLamp = "base/practiceRecord/horseLamp";

    @NotNull
    public static final String getExamQuestionList = "exam/examQuestion/getExamQuestions";

    @NotNull
    public static final String getFeedBackPage = "base/feedBack/app/page";

    @NotNull
    public static final String getGardeScore = "cmtt/report/grade/score";

    @NotNull
    public static final String getHomeBanner = "platform/banner/homePage/banner";

    @NotNull
    public static final String getHomeInfoList = "cmtt/recommend/homePage/display";

    @NotNull
    public static final String getHomePatient = "cmtt/recommend/homePage/patient";

    @NotNull
    public static final String getHorseLamp = "base/skill/horseLamp";

    @NotNull
    public static final String getInformationTree = "teachingCenter/information/category/getTree";

    @NotNull
    public static final String getIntegralApp = "base/integral/app";

    @NotNull
    public static final String getInviterInfo = "base/inviter/getInviterInfo";

    @NotNull
    public static final String getKnowledgeAtlasID = "literature/knowledgeAtlas/";

    @NotNull
    public static final String getKnowledgeDetails = "base/app/knowledge/list";

    @NotNull
    public static final String getLeaderBoard = "base/practiceRecord/leaderBoard";

    @NotNull
    public static final String getMaintain = "platform/agreement/maintain/latest";

    @NotNull
    public static final String getMedicineDirectionTree = "platform/informationCategory/getTree";

    @NotNull
    public static final String getMedicineSearchList = "platform/informationCategory/getList";

    @NotNull
    public static final String getMessageList = "msgcenter/messagePush/list";

    @NotNull
    public static final String getNoticeList = "msgcenter/notice/user/list";

    @NotNull
    public static final String getOrderStr = "base/product/order/getOrderStr";

    @NotNull
    public static final String getOrganzationSearch = "system/organ/getAll";

    @NotNull
    public static final String getPatientAskGetByKeyword = "cmtt/patient/ask/getByKeyword";

    @NotNull
    public static final String getPatientInfoById = "cmtt/patient/info/getById";

    @NotNull
    public static final String getPatientNote = "cmtt/train/patientNote/get";

    @NotNull
    public static final String getPatientTreeRecord = "cmtt/trainRecord/tree";

    @NotNull
    public static final String getPersonInfoApp = "system/user/profile/app";

    @NotNull
    public static final String getPopUserList = "base/popupUser/list";

    @NotNull
    public static final String getPostPersonInfo = "system/user/profile";

    @NotNull
    public static final String getPostUserBankInfo = "platform/userBankInfo";

    @NotNull
    public static final String getPracticeRecord = "base/practiceRecord";

    @NotNull
    public static final String getPracticeRecordExam = "base/practiceRecord/page";

    @NotNull
    public static final String getPrepayWx = "base/product/order/getPrepay";

    @NotNull
    public static final String getProductInfo = "base/product/info/getBySkillId";

    @NotNull
    public static final String getProductInfoSkill = "base/product/info/acquireSkill";

    @NotNull
    public static final String getPushCodeForgetPwd = "auth/msg/forgetPwd/record/v4";

    @NotNull
    public static final String getPushCodeRegister = "auth/msg/register/record/v4";

    @NotNull
    public static final String getRandomSkill = "base/practiceList/random";

    @NotNull
    public static final String getReadNoticeId = "msgcenter/notice/read";

    @NotNull
    public static final String getRecommendInfo = "cmtt/recommend/info/";

    @NotNull
    public static final String getRecordEquityDetail = "base/share/record/equityDetail";

    @NotNull
    public static final String getRecordEquityDetailV1 = "base/share/record/equityDetail/v1";

    @NotNull
    public static final String getReplyAppList = "platform/sys/reply/appList";

    @NotNull
    public static final String getReportAnalyseRadar = "cmtt/report/analyse/radar";

    @NotNull
    public static final String getReportGradeItemScore = "cmtt/report/grade/itemScore";

    @NotNull
    public static final String getReportHarmItem = "cmtt/report/grade/harmItem";

    @NotNull
    public static final String getReportHelpItem = "cmtt/report/grade/helpItem";

    @NotNull
    public static final String getReportIdentityDiagnose = "cmtt/report/grade/identityDiagnose";

    @NotNull
    public static final String getReportIrrelevantItem = "cmtt/report/grade/irrelevantItem";

    @NotNull
    public static final String getReportMainDiagnose = "cmtt/report/grade/mainDiagnose";

    @NotNull
    public static final String getReportOtherDiagnose = "cmtt/report/grade/otherDiagnose";

    @NotNull
    public static final String getReportRatioCorrect = "cmtt/report/ratio/correct";

    @NotNull
    public static final String getReportRatioMatch = "cmtt/report/ratio/match";

    @NotNull
    public static final String getRulelatest = "base/skillShare/rule/latest";

    @NotNull
    public static final String getSicknessRelationList = "platform/extent/list";

    @NotNull
    public static final String getSkillBasic = "base/skill/category/basicHome";

    @NotNull
    public static final String getSkillBasicHome = "base/skill/category/basicHome?code=JBCZ&type=2";

    @NotNull
    public static final String getSkillBodyHome = "base/skill/category/bodyHome?code=TGJC&type=2";

    @NotNull
    public static final String getSkillHome = "base/skill/home";

    @NotNull
    public static final String getSkillHomePage = "base/skill/homePage";

    @NotNull
    public static final String getSkillMy = "base/skill/my";

    @NotNull
    public static final String getSkillRandom = "base/skill/random";

    @NotNull
    public static final String getSkillRecord = "base/skill/record";

    @NotNull
    public static final String getSkillTimes = "base/skill/times";

    @NotNull
    public static final String getSysTrainRecordGetKeyWord = "cmtt/trainRecord/getByKeyWord";

    @NotNull
    public static final String getTeachingCenter = "cmtt/appHome/teachingCenter";

    @NotNull
    public static final String getTeachingList = "teachingCenter/case/teaching/list";

    @NotNull
    public static final String getTeachingResourceCenter = "teachingCenter/teaching/resource/center";

    @NotNull
    public static final String getTeachingResourceList = "teachingCenter/teaching/resource/list";

    @NotNull
    public static final String getTrainInfoFindById = "cmtt/trainInfo/findById";

    @NotNull
    public static final String getTrainInfoStudyList = "cmtt/patient/info/studyPage";

    @NotNull
    public static final String getTrainMedical = "cmtt/trainMedical/getByTrainId";

    @NotNull
    public static final String getTrainNoteById = "cmtt/trainNote/getByTrainId";

    @NotNull
    public static final String getTrainRecordListAsk = "cmtt/trainRecord/listAsk";

    @NotNull
    public static final String getTreatmentCategoryTree = "cmtt/sys/treatment/category/tree";

    @NotNull
    public static final String getTreatmentTip = "cmtt/patient/treatment/tip";

    @NotNull
    public static final String getUnreadCount = "msgcenter/messagePush/getUnreadCount";

    @NotNull
    public static final String getUnreadNoticeCount = "msgcenter/notice/unReadCount";

    @NotNull
    public static final String getUserCoupon = "base/couponRecord/userCoupon";

    @NotNull
    public static final String getUserExamList = "exam/examCandidate/listExamByCandidate";

    @NotNull
    public static final String getUserSpecificCoupon = "base/couponRecord/userSpecificCoupon";

    @NotNull
    public static final String getVeriFyMsg = "auth/msg/verify";

    @NotNull
    public static final String getVersionU3d = "base/practiceSkill/version/editU3D";

    @NotNull
    public static final String hostFileFormal = "https://file.medmeta.com/";

    @NotNull
    public static final String patientAskGetAskTree = "cmtt/patient/ask/getAskTree";

    @NotNull
    public static final String patientAssistGetBindData = "cmtt/patient/assist/getBindData";

    @NotNull
    public static final String patientInfoGetById = "cmtt/patient/info/getById";

    @NotNull
    public static final String patientInfoList = "cmtt/patient/info/list";

    @NotNull
    public static final String patientInfoRandom = "cmtt/patient/info/random";

    @NotNull
    public static final String patientInfoSave = "cmtt/trainInfo/save";

    @NotNull
    public static final String postAgentLogin = "auth/agentPublicLogin";

    @NotNull
    public static final String postAppFavoriteSave = "base/app/favorite/save";

    @NotNull
    public static final String postAppRead = "platform/sys/reply/appRead";

    @NotNull
    public static final String postAppTableSave = "system/appMenu/manager/appTable/save";

    @NotNull
    public static final String postClinicalGuideline = "platform/information/list";

    @NotNull
    public static final String postCouponRecord = "base/couponRecord/add";

    @NotNull
    public static final String postExamCheat = "exam/examCheat/add";

    @NotNull
    public static final String postFeedBackAdd = "base/feedBack/add";

    @NotNull
    public static final String postFileUploadBatch = "file/uploadBatch";

    @NotNull
    public static final String postIntegralAcquire = "base/integral/acquire";

    @NotNull
    public static final String postIntegralFreeReceive = "base/integral/freeReceive";

    @NotNull
    public static final String postIntegralUnlock = "base/integral/unlock";

    @NotNull
    public static final String postInviteLogin = "auth/invite/login";

    @NotNull
    public static final String postInviterSave = "base/inviter/save";

    @NotNull
    public static final String postKnowledgeAtlasQuary = "literature/knowledgeAtlas/query";

    @NotNull
    public static final String postKnowledgeLearnRecordSave = "base/knowledgeLearnRecord/save/";

    @NotNull
    public static final String postOneclickLogin = "auth/oneClick/Login";

    @NotNull
    public static final String postPatientAssistInfo = "cmtt/patient/assist/info";

    @NotNull
    public static final String postPatientNoteSave = "cmtt/train/patientNote/save";

    @NotNull
    public static final String postPersonSubmit = "system/user/audit/submit";

    @NotNull
    public static final String postPointSave = "base/point/location/add";

    @NotNull
    public static final String postPopUserUpdateStatus = "base/popupUser/updateStatus";

    @NotNull
    public static final String postRedeemCode = "base/channelRedeemCode/redeem";

    @NotNull
    public static final String postReplySave = "platform/sys/reply/save";

    @NotNull
    public static final String postTenantInfoRegister = "platform/tenantInfo/register";

    @NotNull
    public static final String postTrainMedicalSave = "cmtt/trainMedical/save";

    @NotNull
    public static final String postTrainNoteSave = "cmtt/trainNote/save";

    @NotNull
    public static final String postUserBinding = "system/user/platform/userBinding";

    @NotNull
    public static final String postUserReBinding = "system/user/userReBinding";

    @NotNull
    public static final String postWxLogin = "auth/platform/login";

    @NotNull
    public static final String readMessageList = "msgcenter/messagePush/update";

    @NotNull
    public static final String saveExamQuestionResult = "exam/examQuestion/updateExamQuestionResult";

    @NotNull
    public static final String startExam = "exam/examResult/startExamResult";

    @NotNull
    public static final String sysAssistCheck = "cmtt/sys/assistCheck";

    @NotNull
    public static final String sysAssistCheckGetTree = "cmtt/sys/assistCheck/getTree";

    @NotNull
    public static final String sysDiseaseList = "cmtt/sys/disease/list";

    @NotNull
    public static final String sysTreatment = "cmtt/sys/treatment";

    @NotNull
    public static final String sysTreatmentGetTree = "cmtt/sys/treatment/getTree";

    @NotNull
    public static final String trainDagnoseSave = "cmtt/trainDiagnose/save";

    @NotNull
    public static final String trainDiagnoseBasisGetSavedBasis = "cmtt/trainDiagnoseBasis/getSavedBasis";

    @NotNull
    public static final String trainDiagnoseBasisSaveBasis = "cmtt/trainDiagnoseBasis/saveBasis";

    @NotNull
    public static final String trainDiagnoseDelete = "cmtt/trainDiagnose/delete";

    @NotNull
    public static final String trainDiagnoseGetByTrainId = "cmtt/trainDiagnose/getByTrainId";

    @NotNull
    public static final String trainDiagnoseUpdate = "cmtt/trainDiagnose/update";

    @NotNull
    public static final String trainInfoGetUnCompletee = "cmtt/trainInfo/getUnComplete";

    @NotNull
    public static final String trainInfoList = "cmtt/trainInfo/list";

    @NotNull
    public static final String trainInfoUpdateStatus = "cmtt/trainInfo/updateStatus";

    @NotNull
    public static final String trainRecordBatchSave = "cmtt/trainRecord/batchSave";

    @NotNull
    public static final String trainRecordListDoneDetails = "cmtt/trainRecord/listDoneDetails";

    @NotNull
    public static final String trainRecordSave = "cmtt/trainRecord/save";

    @NotNull
    public static final String updatePwd = "system/user/profile/updatePwd";

    @NotNull
    public static final String uploadImagesHeader = "system/user/profile/avatar";

    @NotNull
    public static final String userAccountCancel = "system/user/cancel";

    @NotNull
    public static final YddApi INSTANCE = new YddApi();

    @NotNull
    private static volatile String baseUrl = "";

    @NotNull
    private static volatile String baseUrlFile = "";

    private YddApi() {
    }

    @NotNull
    public final String getBaseUrl() {
        return baseUrl;
    }

    @NotNull
    public final String getBaseUrlFile() {
        return baseUrlFile;
    }

    public final void setBaseUrl(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        baseUrl = str;
    }

    public final void setBaseUrlFile(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        baseUrlFile = str;
    }
}
