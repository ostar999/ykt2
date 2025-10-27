package com.github.liuyueyi.quick.transfer.dictionary;

import com.github.liuyueyi.quick.transfer.constants.TransType;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class DictionaryContainer {
    private static volatile DictionaryContainer instance;
    private final Map<String, BasicDictionary> dictionaryMap = new HashMap(8, 1.0f);

    /* renamed from: com.github.liuyueyi.quick.transfer.dictionary.DictionaryContainer$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$github$liuyueyi$quick$transfer$constants$TransType;

        static {
            int[] iArr = new int[TransType.values().length];
            $SwitchMap$com$github$liuyueyi$quick$transfer$constants$TransType = iArr;
            try {
                iArr[TransType.SIMPLE_TO_TRADITIONAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$liuyueyi$quick$transfer$constants$TransType[TransType.SIMPLE_TO_HONGKONG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$github$liuyueyi$quick$transfer$constants$TransType[TransType.SIMPLE_TO_TAIWAN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$github$liuyueyi$quick$transfer$constants$TransType[TransType.TRADITIONAL_TO_SIMPLE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$github$liuyueyi$quick$transfer$constants$TransType[TransType.HONGKONG_TO_SIMPLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$github$liuyueyi$quick$transfer$constants$TransType[TransType.TAIWAN_TO_SIMPLE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private DictionaryContainer() {
    }

    public static DictionaryContainer getInstance() {
        if (instance == null) {
            synchronized (DictionaryContainer.class) {
                if (instance == null) {
                    instance = new DictionaryContainer();
                }
            }
        }
        return instance;
    }

    @Deprecated
    public BasicDictionary getDictionary(String str) {
        return getDictionary(TransType.typeOf(str));
    }

    public void unloadDictionary(TransType transType) {
        this.dictionaryMap.remove(transType.getType());
    }

    public BasicDictionary getDictionary(TransType transType) {
        BasicDictionary basicDictionaryLoadDictionary;
        BasicDictionary basicDictionary = this.dictionaryMap.get(transType.getType());
        if (basicDictionary != null) {
            return basicDictionary;
        }
        synchronized (this) {
            BasicDictionary basicDictionary2 = this.dictionaryMap.get(transType.getType());
            if (basicDictionary2 != null) {
                return basicDictionary2;
            }
            switch (AnonymousClass1.$SwitchMap$com$github$liuyueyi$quick$transfer$constants$TransType[transType.ordinal()]) {
                case 1:
                    basicDictionaryLoadDictionary = DictionaryFactory.loadDictionary("tc/s2t.txt", false);
                    break;
                case 2:
                    basicDictionaryLoadDictionary = DictionaryFactory.loadSecondDictionary(getDictionary(TransType.SIMPLE_TO_TRADITIONAL), "tc/t2hk.txt", false);
                    break;
                case 3:
                    basicDictionaryLoadDictionary = DictionaryFactory.loadSecondDictionary(getDictionary(TransType.SIMPLE_TO_TRADITIONAL), "tc/t2tw.txt", false);
                    break;
                case 4:
                    basicDictionaryLoadDictionary = DictionaryFactory.loadDictionary("tc/t2s.txt", false);
                    break;
                case 5:
                    basicDictionaryLoadDictionary = DictionaryFactory.loadSecondDictionary(getDictionary(TransType.TRADITIONAL_TO_SIMPLE), "tc/t2hk.txt", true);
                    break;
                case 6:
                    basicDictionaryLoadDictionary = DictionaryFactory.loadSecondDictionary(getDictionary(TransType.TRADITIONAL_TO_SIMPLE), "tc/t2tw.txt", true);
                    break;
                default:
                    throw new IllegalArgumentException("暂不支持转化方式" + transType);
            }
            this.dictionaryMap.put(transType.getType(), basicDictionaryLoadDictionary);
            return basicDictionaryLoadDictionary;
        }
    }
}
