package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Document;
import com.hp.hpl.sparta.ParseException;
import com.hp.hpl.sparta.Parser;
import java.io.FileNotFoundException;
import java.io.IOException;

/* loaded from: classes9.dex */
class GwoyeuRomatzyhResource {
    private Document pinyinToGwoyeuMappingDoc;

    public static class GwoyeuRomatzyhSystemResourceHolder {
        static final GwoyeuRomatzyhResource theInstance = new GwoyeuRomatzyhResource();

        private GwoyeuRomatzyhSystemResourceHolder() {
        }
    }

    private GwoyeuRomatzyhResource() {
        initializeResource();
    }

    public static GwoyeuRomatzyhResource getInstance() {
        return GwoyeuRomatzyhSystemResourceHolder.theInstance;
    }

    private void initializeResource() {
        try {
            setPinyinToGwoyeuMappingDoc(Parser.parse("", ResourceHelper.getResourceInputStream("/pinyindb/pinyin_gwoyeu_mapping.xml")));
        } catch (ParseException | FileNotFoundException | IOException e2) {
            e2.printStackTrace();
        }
    }

    private void setPinyinToGwoyeuMappingDoc(Document document) {
        this.pinyinToGwoyeuMappingDoc = document;
    }

    public Document getPinyinToGwoyeuMappingDoc() {
        return this.pinyinToGwoyeuMappingDoc;
    }
}
