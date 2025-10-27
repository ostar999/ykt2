package com.github.liuyueyi.quick.transfer;

import com.github.liuyueyi.quick.transfer.constants.TransType;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryContainer;

/* loaded from: classes3.dex */
public class ChineseUtils {
    public static String hk2s(String str) {
        return DictionaryContainer.getInstance().getDictionary(TransType.HONGKONG_TO_SIMPLE).convert(str);
    }

    public static void preLoad(boolean z2, final TransType... transTypeArr) {
        if (!z2) {
            preLoad(transTypeArr);
            return;
        }
        Thread thread = new Thread(new Runnable() { // from class: com.github.liuyueyi.quick.transfer.ChineseUtils.1
            @Override // java.lang.Runnable
            public void run() {
                ChineseUtils.preLoad(transTypeArr);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public static String s2hk(String str) {
        return DictionaryContainer.getInstance().getDictionary(TransType.SIMPLE_TO_HONGKONG).convert(str);
    }

    public static String s2t(String str) {
        return DictionaryContainer.getInstance().getDictionary(TransType.SIMPLE_TO_TRADITIONAL).convert(str);
    }

    public static String s2tw(String str) {
        return DictionaryContainer.getInstance().getDictionary(TransType.SIMPLE_TO_TAIWAN).convert(str);
    }

    public static String t2s(String str) {
        return DictionaryContainer.getInstance().getDictionary(TransType.TRADITIONAL_TO_SIMPLE).convert(str);
    }

    public static String transfer(String str, TransType transType) {
        return DictionaryContainer.getInstance().getDictionary(transType).convert(str);
    }

    public static String tw2s(String str) {
        return DictionaryContainer.getInstance().getDictionary(TransType.TAIWAN_TO_SIMPLE).convert(str);
    }

    public static void unLoad(TransType... transTypeArr) {
        for (TransType transType : transTypeArr) {
            DictionaryContainer.getInstance().unloadDictionary(transType);
        }
    }

    public static void preLoad(TransType... transTypeArr) {
        for (TransType transType : transTypeArr) {
            transfer("", transType);
        }
    }
}
