package org.jsoup.select;

import cn.hutool.core.text.StrPool;
import com.github.liuyueyi.quick.transfer.dictionary.DictionaryFactory;
import com.huawei.hms.support.hianalytics.HiAnalyticsConstant;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.parser.TokenQueue;
import org.jsoup.select.CombiningEvaluator;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.jsoup.select.StructuralEvaluator;
import org.slf4j.Marker;

/* loaded from: classes9.dex */
class QueryParser {
    private List<Evaluator> evals = new ArrayList();
    private String query;
    private TokenQueue tq;
    private static final String[] combinators = {",", ">", Marker.ANY_NON_NULL_MARKER, Constants.WAVE_SEPARATOR, " "};
    private static final String[] AttributeEvals = {"=", "!=", "^=", "$=", "*=", "~="};
    private static final Pattern NTH_AB = Pattern.compile("((\\+|-)?(\\d+)?)n(\\s*(\\+|-)?\\s*\\d+)?", 2);
    private static final Pattern NTH_B = Pattern.compile("(\\+|-)?(\\d+)");

    private QueryParser(String str) {
        this.query = str;
        this.tq = new TokenQueue(str);
    }

    private void allElements() {
        this.evals.add(new Evaluator.AllElements());
    }

    private void byAttribute() {
        TokenQueue tokenQueue = new TokenQueue(this.tq.chompBalanced('[', ']'));
        String strConsumeToAny = tokenQueue.consumeToAny(AttributeEvals);
        Validate.notEmpty(strConsumeToAny);
        tokenQueue.consumeWhitespace();
        if (tokenQueue.isEmpty()) {
            if (strConsumeToAny.startsWith("^")) {
                this.evals.add(new Evaluator.AttributeStarting(strConsumeToAny.substring(1)));
                return;
            } else {
                this.evals.add(new Evaluator.Attribute(strConsumeToAny));
                return;
            }
        }
        if (tokenQueue.matchChomp("=")) {
            this.evals.add(new Evaluator.AttributeWithValue(strConsumeToAny, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("!=")) {
            this.evals.add(new Evaluator.AttributeWithValueNot(strConsumeToAny, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("^=")) {
            this.evals.add(new Evaluator.AttributeWithValueStarting(strConsumeToAny, tokenQueue.remainder()));
            return;
        }
        if (tokenQueue.matchChomp("$=")) {
            this.evals.add(new Evaluator.AttributeWithValueEnding(strConsumeToAny, tokenQueue.remainder()));
        } else if (tokenQueue.matchChomp("*=")) {
            this.evals.add(new Evaluator.AttributeWithValueContaining(strConsumeToAny, tokenQueue.remainder()));
        } else {
            if (!tokenQueue.matchChomp("~=")) {
                throw new Selector.SelectorParseException("Could not parse attribute query '%s': unexpected token at '%s'", this.query, tokenQueue.remainder());
            }
            this.evals.add(new Evaluator.AttributeWithValueMatching(strConsumeToAny, Pattern.compile(tokenQueue.remainder())));
        }
    }

    private void byClass() {
        String strConsumeCssIdentifier = this.tq.consumeCssIdentifier();
        Validate.notEmpty(strConsumeCssIdentifier);
        this.evals.add(new Evaluator.Class(strConsumeCssIdentifier.trim().toLowerCase()));
    }

    private void byId() {
        String strConsumeCssIdentifier = this.tq.consumeCssIdentifier();
        Validate.notEmpty(strConsumeCssIdentifier);
        this.evals.add(new Evaluator.Id(strConsumeCssIdentifier));
    }

    private void byTag() {
        String strConsumeElementSelector = this.tq.consumeElementSelector();
        Validate.notEmpty(strConsumeElementSelector);
        if (strConsumeElementSelector.contains(HiAnalyticsConstant.REPORT_VAL_SEPARATOR)) {
            strConsumeElementSelector = strConsumeElementSelector.replace(HiAnalyticsConstant.REPORT_VAL_SEPARATOR, ":");
        }
        this.evals.add(new Evaluator.Tag(strConsumeElementSelector.trim().toLowerCase()));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void combinator(char r11) {
        /*
            Method dump skipped, instructions count: 215
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.select.QueryParser.combinator(char):void");
    }

    private int consumeIndex() {
        String strTrim = this.tq.chompTo(")").trim();
        Validate.isTrue(StringUtil.isNumeric(strTrim), "Index must be numeric");
        return Integer.parseInt(strTrim);
    }

    private String consumeSubQuery() {
        StringBuilder sb = new StringBuilder();
        while (!this.tq.isEmpty()) {
            if (this.tq.matches("(")) {
                sb.append("(");
                sb.append(this.tq.chompBalanced('(', ')'));
                sb.append(")");
            } else if (this.tq.matches(StrPool.BRACKET_START)) {
                sb.append(StrPool.BRACKET_START);
                sb.append(this.tq.chompBalanced('[', ']'));
                sb.append(StrPool.BRACKET_END);
            } else {
                if (this.tq.matchesAny(combinators)) {
                    break;
                }
                sb.append(this.tq.consume());
            }
        }
        return sb.toString();
    }

    private void contains(boolean z2) {
        this.tq.consume(z2 ? ":containsOwn" : ":contains");
        String strUnescape = TokenQueue.unescape(this.tq.chompBalanced('(', ')'));
        Validate.notEmpty(strUnescape, ":contains(text) query must not be empty");
        if (z2) {
            this.evals.add(new Evaluator.ContainsOwnText(strUnescape));
        } else {
            this.evals.add(new Evaluator.ContainsText(strUnescape));
        }
    }

    private void cssNthChild(boolean z2, boolean z3) throws NumberFormatException {
        String lowerCase = this.tq.chompTo(")").trim().toLowerCase();
        Matcher matcher = NTH_AB.matcher(lowerCase);
        Matcher matcher2 = NTH_B.matcher(lowerCase);
        int i2 = 2;
        int i3 = 1;
        if (!"odd".equals(lowerCase)) {
            if ("even".equals(lowerCase)) {
                i3 = 0;
            } else if (matcher.matches()) {
                int i4 = matcher.group(3) != null ? Integer.parseInt(matcher.group(1).replaceFirst("^\\+", "")) : 1;
                i3 = matcher.group(4) != null ? Integer.parseInt(matcher.group(4).replaceFirst("^\\+", "")) : 0;
                i2 = i4;
            } else {
                if (!matcher2.matches()) {
                    throw new Selector.SelectorParseException("Could not parse nth-index '%s': unexpected format", lowerCase);
                }
                i3 = Integer.parseInt(matcher2.group().replaceFirst("^\\+", ""));
                i2 = 0;
            }
        }
        if (z3) {
            if (z2) {
                this.evals.add(new Evaluator.IsNthLastOfType(i2, i3));
                return;
            } else {
                this.evals.add(new Evaluator.IsNthOfType(i2, i3));
                return;
            }
        }
        if (z2) {
            this.evals.add(new Evaluator.IsNthLastChild(i2, i3));
        } else {
            this.evals.add(new Evaluator.IsNthChild(i2, i3));
        }
    }

    private void findElements() throws NumberFormatException {
        if (this.tq.matchChomp(DictionaryFactory.SHARP)) {
            byId();
            return;
        }
        if (this.tq.matchChomp(StrPool.DOT)) {
            byClass();
            return;
        }
        if (this.tq.matchesWord()) {
            byTag();
            return;
        }
        if (this.tq.matches(StrPool.BRACKET_START)) {
            byAttribute();
            return;
        }
        if (this.tq.matchChomp("*")) {
            allElements();
            return;
        }
        if (this.tq.matchChomp(":lt(")) {
            indexLessThan();
            return;
        }
        if (this.tq.matchChomp(":gt(")) {
            indexGreaterThan();
            return;
        }
        if (this.tq.matchChomp(":eq(")) {
            indexEquals();
            return;
        }
        if (this.tq.matches(":has(")) {
            has();
            return;
        }
        if (this.tq.matches(":contains(")) {
            contains(false);
            return;
        }
        if (this.tq.matches(":containsOwn(")) {
            contains(true);
            return;
        }
        if (this.tq.matches(":matches(")) {
            matches(false);
            return;
        }
        if (this.tq.matches(":matchesOwn(")) {
            matches(true);
            return;
        }
        if (this.tq.matches(":not(")) {
            not();
            return;
        }
        if (this.tq.matchChomp(":nth-child(")) {
            cssNthChild(false, false);
            return;
        }
        if (this.tq.matchChomp(":nth-last-child(")) {
            cssNthChild(true, false);
            return;
        }
        if (this.tq.matchChomp(":nth-of-type(")) {
            cssNthChild(false, true);
            return;
        }
        if (this.tq.matchChomp(":nth-last-of-type(")) {
            cssNthChild(true, true);
            return;
        }
        if (this.tq.matchChomp(":first-child")) {
            this.evals.add(new Evaluator.IsFirstChild());
            return;
        }
        if (this.tq.matchChomp(":last-child")) {
            this.evals.add(new Evaluator.IsLastChild());
            return;
        }
        if (this.tq.matchChomp(":first-of-type")) {
            this.evals.add(new Evaluator.IsFirstOfType());
            return;
        }
        if (this.tq.matchChomp(":last-of-type")) {
            this.evals.add(new Evaluator.IsLastOfType());
            return;
        }
        if (this.tq.matchChomp(":only-child")) {
            this.evals.add(new Evaluator.IsOnlyChild());
            return;
        }
        if (this.tq.matchChomp(":only-of-type")) {
            this.evals.add(new Evaluator.IsOnlyOfType());
        } else if (this.tq.matchChomp(":empty")) {
            this.evals.add(new Evaluator.IsEmpty());
        } else {
            if (!this.tq.matchChomp(":root")) {
                throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", this.query, this.tq.remainder());
            }
            this.evals.add(new Evaluator.IsRoot());
        }
    }

    private void has() {
        this.tq.consume(":has");
        String strChompBalanced = this.tq.chompBalanced('(', ')');
        Validate.notEmpty(strChompBalanced, ":has(el) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Has(parse(strChompBalanced)));
    }

    private void indexEquals() {
        this.evals.add(new Evaluator.IndexEquals(consumeIndex()));
    }

    private void indexGreaterThan() {
        this.evals.add(new Evaluator.IndexGreaterThan(consumeIndex()));
    }

    private void indexLessThan() {
        this.evals.add(new Evaluator.IndexLessThan(consumeIndex()));
    }

    private void matches(boolean z2) {
        this.tq.consume(z2 ? ":matchesOwn" : ":matches");
        String strChompBalanced = this.tq.chompBalanced('(', ')');
        Validate.notEmpty(strChompBalanced, ":matches(regex) query must not be empty");
        if (z2) {
            this.evals.add(new Evaluator.MatchesOwn(Pattern.compile(strChompBalanced)));
        } else {
            this.evals.add(new Evaluator.Matches(Pattern.compile(strChompBalanced)));
        }
    }

    private void not() {
        this.tq.consume(":not");
        String strChompBalanced = this.tq.chompBalanced('(', ')');
        Validate.notEmpty(strChompBalanced, ":not(selector) subselect must not be empty");
        this.evals.add(new StructuralEvaluator.Not(parse(strChompBalanced)));
    }

    public static Evaluator parse(String str) {
        return new QueryParser(str).parse();
    }

    public Evaluator parse() throws NumberFormatException {
        this.tq.consumeWhitespace();
        if (this.tq.matchesAny(combinators)) {
            this.evals.add(new StructuralEvaluator.Root());
            combinator(this.tq.consume());
        } else {
            findElements();
        }
        while (!this.tq.isEmpty()) {
            boolean zConsumeWhitespace = this.tq.consumeWhitespace();
            if (this.tq.matchesAny(combinators)) {
                combinator(this.tq.consume());
            } else if (zConsumeWhitespace) {
                combinator(' ');
            } else {
                findElements();
            }
        }
        if (this.evals.size() == 1) {
            return this.evals.get(0);
        }
        return new CombiningEvaluator.And(this.evals);
    }
}
