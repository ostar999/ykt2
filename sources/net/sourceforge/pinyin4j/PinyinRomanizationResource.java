package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Document;
import com.hp.hpl.sparta.ParseException;
import com.hp.hpl.sparta.Parser;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes9.dex */
class PinyinRomanizationResource {
    private Document pinyinMappingDoc;

    public static class PinyinRomanizationSystemResourceHolder {
        static final PinyinRomanizationResource theInstance = new PinyinRomanizationResource();

        private PinyinRomanizationSystemResourceHolder() {
        }
    }

    private PinyinRomanizationResource() {
        initializeResource();
    }

    public static PinyinRomanizationResource getInstance() {
        return PinyinRomanizationSystemResourceHolder.theInstance;
    }

    private void initializeResource() {
        try {
            setPinyinMappingDoc(Parser.parse("", ResourceHelper.getResourceInputStream("/pinyindb/pinyin_mapping.xml")));
        } catch (ParseException | FileNotFoundException | IOException e2) {
            e2.printStackTrace();
        }
    }

    private void setPinyinMappingDoc(Document document) {
        this.pinyinMappingDoc = document;
    }

    public Document getPinyinMappingDoc() {
        return this.pinyinMappingDoc;
    }
}
