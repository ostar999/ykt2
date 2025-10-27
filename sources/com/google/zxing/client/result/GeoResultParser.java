package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class GeoResultParser extends ResultParser {
    private static final Pattern GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

    @Override // com.google.zxing.client.result.ResultParser
    public GeoParsedResult parse(Result result) throws NumberFormatException {
        Matcher matcher = GEO_URL_PATTERN.matcher(ResultParser.getMassagedText(result));
        if (!matcher.matches()) {
            return null;
        }
        String strGroup = matcher.group(4);
        try {
            double d3 = Double.parseDouble(matcher.group(1));
            if (d3 <= 90.0d && d3 >= -90.0d) {
                double d4 = Double.parseDouble(matcher.group(2));
                if (d4 <= 180.0d && d4 >= -180.0d) {
                    double d5 = 0.0d;
                    if (matcher.group(3) != null) {
                        double d6 = Double.parseDouble(matcher.group(3));
                        if (d6 < 0.0d) {
                            return null;
                        }
                        d5 = d6;
                    }
                    return new GeoParsedResult(d3, d4, d5, strGroup);
                }
            }
        } catch (NumberFormatException unused) {
        }
        return null;
    }
}
