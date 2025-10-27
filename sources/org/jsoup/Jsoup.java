package org.jsoup;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.jsoup.helper.DataUtil;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;

/* loaded from: classes9.dex */
public class Jsoup {
    private Jsoup() {
    }

    public static String clean(String str, String str2, Whitelist whitelist) {
        return new Cleaner(whitelist).clean(parseBodyFragment(str, str2)).body().html();
    }

    public static Connection connect(String str) {
        return HttpConnection.connect(str);
    }

    public static boolean isValid(String str, Whitelist whitelist) {
        return new Cleaner(whitelist).isValid(parseBodyFragment(str, ""));
    }

    public static Document parse(String str, String str2) {
        return Parser.parse(str, str2);
    }

    public static Document parseBodyFragment(String str, String str2) {
        return Parser.parseBodyFragment(str, str2);
    }

    public static Document parse(String str, String str2, Parser parser) {
        return parser.parseInput(str, str2);
    }

    public static Document parseBodyFragment(String str) {
        return Parser.parseBodyFragment(str, "");
    }

    public static Document parse(String str) {
        return Parser.parse(str, "");
    }

    public static Document parse(File file, String str, String str2) throws IOException {
        return DataUtil.load(file, str, str2);
    }

    public static String clean(String str, Whitelist whitelist) {
        return clean(str, "", whitelist);
    }

    public static Document parse(File file, String str) throws IOException {
        return DataUtil.load(file, str, file.getAbsolutePath());
    }

    public static String clean(String str, String str2, Whitelist whitelist, Document.OutputSettings outputSettings) {
        Document documentClean = new Cleaner(whitelist).clean(parseBodyFragment(str, str2));
        documentClean.outputSettings(outputSettings);
        return documentClean.body().html();
    }

    public static Document parse(InputStream inputStream, String str, String str2) throws IOException {
        return DataUtil.load(inputStream, str, str2);
    }

    public static Document parse(InputStream inputStream, String str, String str2, Parser parser) throws IOException {
        return DataUtil.load(inputStream, str, str2, parser);
    }

    public static Document parse(URL url, int i2) throws IOException {
        Connection connectionConnect = HttpConnection.connect(url);
        connectionConnect.timeout(i2);
        return connectionConnect.get();
    }
}
