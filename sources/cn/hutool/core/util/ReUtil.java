package cn.hutool.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.comparator.LengthComparator;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.mutable.Mutable;
import cn.hutool.core.lang.mutable.MutableObj;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.text.Typography;

/* loaded from: classes.dex */
public class ReUtil {
    public static final String RE_CHINESE = "[⺀-\u2eff⼀-\u2fdf㇀-\u31ef㐀-䶿一-鿿豈-\ufaff𠀀-𪛟𪜀-\u2b73f𫝀-\u2b81f𫠠-\u2ceaf丽-\u2fa1f]";
    public static final String RE_CHINESES = "[⺀-\u2eff⼀-\u2fdf㇀-\u31ef㐀-䶿一-鿿豈-\ufaff𠀀-𪛟𪜀-\u2b73f𫝀-\u2b81f𫠠-\u2ceaf丽-\u2fa1f]+";
    public static final Set<Character> RE_KEYS = CollUtil.newHashSet(Character.valueOf(Typography.dollar), '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|');

    public static boolean contains(String str, CharSequence charSequence) {
        if (str == null || charSequence == null) {
            return false;
        }
        return contains(PatternPool.get(str, 32), charSequence);
    }

    public static int count(String str, CharSequence charSequence) {
        if (str == null || charSequence == null) {
            return 0;
        }
        return count(PatternPool.get(str, 32), charSequence);
    }

    public static String delAll(String str, CharSequence charSequence) {
        return CharSequenceUtil.hasEmpty(str, charSequence) ? CharSequenceUtil.str(charSequence) : delAll(PatternPool.get(str, 32), charSequence);
    }

    public static String delFirst(String str, CharSequence charSequence) {
        return CharSequenceUtil.hasBlank(str, charSequence) ? CharSequenceUtil.str(charSequence) : delFirst(PatternPool.get(str, 32), charSequence);
    }

    public static String delLast(String str, CharSequence charSequence) {
        return CharSequenceUtil.hasBlank(str, charSequence) ? CharSequenceUtil.str(charSequence) : delLast(PatternPool.get(str, 32), charSequence);
    }

    public static String delPre(String str, CharSequence charSequence) {
        return (charSequence == null || str == null) ? CharSequenceUtil.str(charSequence) : delPre(PatternPool.get(str, 32), charSequence);
    }

    public static String escape(char c3) {
        StringBuilder sb = new StringBuilder();
        if (RE_KEYS.contains(Character.valueOf(c3))) {
            sb.append('\\');
        }
        sb.append(c3);
        return sb.toString();
    }

    public static String extractMulti(Pattern pattern, CharSequence charSequence, String str) {
        if (charSequence != null && pattern != null && str != null) {
            TreeSet treeSet = new TreeSet(new Comparator() { // from class: cn.hutool.core.util.r
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return ReUtil.lambda$extractMulti$3((Integer) obj, (Integer) obj2);
                }
            });
            Matcher matcher = PatternPool.GROUP_VAR.matcher(str);
            while (matcher.find()) {
                treeSet.add(Integer.valueOf(Integer.parseInt(matcher.group(1))));
            }
            Matcher matcher2 = pattern.matcher(charSequence);
            if (matcher2.find()) {
                Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    Integer num = (Integer) it.next();
                    str = str.replace("$" + num, matcher2.group(num.intValue()));
                }
                return str;
            }
        }
        return null;
    }

    public static String extractMultiAndDelPre(Pattern pattern, Mutable<CharSequence> mutable, String str) throws NumberFormatException {
        if (mutable != null && pattern != null && str != null) {
            HashSet hashSet = (HashSet) findAll(PatternPool.GROUP_VAR, str, 1, new HashSet());
            CharSequence charSequence = mutable.get2();
            Matcher matcher = pattern.matcher(charSequence);
            if (matcher.find()) {
                Iterator it = hashSet.iterator();
                while (it.hasNext()) {
                    String str2 = (String) it.next();
                    str = str.replace("$" + str2, matcher.group(Integer.parseInt(str2)));
                }
                mutable.set(CharSequenceUtil.sub(charSequence, matcher.end(), charSequence.length()));
                return str;
            }
        }
        return null;
    }

    public static List<String> findAll(String str, CharSequence charSequence, int i2) {
        return (List) findAll(str, charSequence, i2, new ArrayList());
    }

    public static List<String> findAllGroup0(String str, CharSequence charSequence) {
        return findAll(str, charSequence, 0);
    }

    public static List<String> findAllGroup1(String str, CharSequence charSequence) {
        return findAll(str, charSequence, 1);
    }

    public static String get(String str, CharSequence charSequence, int i2) {
        if (charSequence == null || str == null) {
            return null;
        }
        return get(PatternPool.get(str, 32), charSequence, i2);
    }

    public static Map<String, String> getAllGroupNames(Pattern pattern, CharSequence charSequence) {
        if (charSequence == null || pattern == null) {
            return null;
        }
        final Matcher matcher = pattern.matcher(charSequence);
        final HashMap mapNewHashMap = MapUtil.newHashMap(matcher.groupCount());
        if (matcher.find()) {
            ((Map) ReflectUtil.invoke(pattern, "namedGroups", new Object[0])).forEach(new BiConsumer() { // from class: cn.hutool.core.util.u
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ReUtil.lambda$getAllGroupNames$2(mapNewHashMap, matcher, (String) obj, (Integer) obj2);
                }
            });
        }
        return mapNewHashMap;
    }

    public static List<String> getAllGroups(Pattern pattern, CharSequence charSequence) {
        return getAllGroups(pattern, charSequence, true);
    }

    public static Integer getFirstNumber(CharSequence charSequence) {
        return Convert.toInt(get(PatternPool.NUMBERS, charSequence, 0), null);
    }

    public static String getGroup0(String str, CharSequence charSequence) {
        return get(str, charSequence, 0);
    }

    public static String getGroup1(String str, CharSequence charSequence) {
        return get(str, charSequence, 1);
    }

    public static MatchResult indexOf(String str, CharSequence charSequence) {
        if (str == null || charSequence == null) {
            return null;
        }
        return indexOf(PatternPool.get(str, 32), charSequence);
    }

    public static boolean isMatch(String str, CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        if (CharSequenceUtil.isEmpty(str)) {
            return true;
        }
        return isMatch(PatternPool.get(str, 32), charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$extractMulti$3(Integer num, Integer num2) {
        return ObjectUtil.compare(num2, num);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$findAll$4(Collection collection, int i2, Matcher matcher) {
        collection.add(matcher.group(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$get$0(MutableObj mutableObj, int i2, Matcher matcher) {
        mutableObj.set(matcher.group(i2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$get$1(MutableObj mutableObj, String str, Matcher matcher) {
        mutableObj.set(matcher.group(str));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$getAllGroupNames$2(Map map, Matcher matcher, String str, Integer num) {
    }

    public static MatchResult lastIndexOf(String str, CharSequence charSequence) {
        if (str == null || charSequence == null) {
            return null;
        }
        return lastIndexOf(PatternPool.get(str, 32), charSequence);
    }

    public static String replaceAll(CharSequence charSequence, String str, String str2) {
        return replaceAll(charSequence, Pattern.compile(str, 32), str2);
    }

    public static String replaceFirst(Pattern pattern, CharSequence charSequence, String str) {
        return (pattern == null || CharSequenceUtil.isEmpty(charSequence)) ? CharSequenceUtil.str(charSequence) : pattern.matcher(charSequence).replaceFirst(str);
    }

    public static <T extends Collection<String>> T findAll(String str, CharSequence charSequence, int i2, T t2) {
        return str == null ? t2 : (T) findAll(PatternPool.get(str, 32), charSequence, i2, t2);
    }

    public static List<String> findAllGroup0(Pattern pattern, CharSequence charSequence) {
        return findAll(pattern, charSequence, 0);
    }

    public static List<String> findAllGroup1(Pattern pattern, CharSequence charSequence) {
        return findAll(pattern, charSequence, 1);
    }

    public static List<String> getAllGroups(Pattern pattern, CharSequence charSequence, boolean z2) {
        return getAllGroups(pattern, charSequence, z2, false);
    }

    public static String getGroup0(Pattern pattern, CharSequence charSequence) {
        return get(pattern, charSequence, 0);
    }

    public static String getGroup1(Pattern pattern, CharSequence charSequence) {
        return get(pattern, charSequence, 1);
    }

    public static boolean contains(Pattern pattern, CharSequence charSequence) {
        if (pattern == null || charSequence == null) {
            return false;
        }
        return pattern.matcher(charSequence).find();
    }

    public static int count(Pattern pattern, CharSequence charSequence) {
        int i2 = 0;
        if (pattern != null && charSequence != null) {
            while (pattern.matcher(charSequence).find()) {
                i2++;
            }
        }
        return i2;
    }

    public static List<String> findAll(Pattern pattern, CharSequence charSequence, int i2) {
        return (List) findAll(pattern, charSequence, i2, new ArrayList());
    }

    public static String get(String str, CharSequence charSequence, String str2) {
        if (charSequence == null || str == null) {
            return null;
        }
        return get(PatternPool.get(str, 32), charSequence, str2);
    }

    public static List<String> getAllGroups(Pattern pattern, CharSequence charSequence, boolean z2, boolean z3) {
        if (charSequence == null || pattern == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        Matcher matcher = pattern.matcher(charSequence);
        while (matcher.find()) {
            int iGroupCount = matcher.groupCount();
            for (int i2 = !z2 ? 1 : 0; i2 <= iGroupCount; i2++) {
                arrayList.add(matcher.group(i2));
            }
            if (!z3) {
                break;
            }
        }
        return arrayList;
    }

    public static MatchResult indexOf(Pattern pattern, CharSequence charSequence) {
        if (pattern == null || charSequence == null) {
            return null;
        }
        Matcher matcher = pattern.matcher(charSequence);
        if (matcher.find()) {
            return matcher.toMatchResult();
        }
        return null;
    }

    public static MatchResult lastIndexOf(Pattern pattern, CharSequence charSequence) {
        MatchResult matchResult = null;
        if (pattern != null && charSequence != null) {
            Matcher matcher = pattern.matcher(charSequence);
            while (matcher.find()) {
                matchResult = matcher.toMatchResult();
            }
        }
        return matchResult;
    }

    public static String replaceAll(CharSequence charSequence, Pattern pattern, String str) throws NumberFormatException {
        if (CharSequenceUtil.isEmpty(charSequence)) {
            return CharSequenceUtil.str(charSequence);
        }
        Matcher matcher = pattern.matcher(charSequence);
        if (matcher.find()) {
            Set<String> set = (Set) findAll(PatternPool.GROUP_VAR, str, 1, new TreeSet(LengthComparator.INSTANCE.reversed()));
            StringBuffer stringBuffer = new StringBuffer();
            do {
                String strReplace = str;
                for (String str2 : set) {
                    strReplace = strReplace.replace("$" + str2, matcher.group(Integer.parseInt(str2)));
                }
                matcher.appendReplacement(stringBuffer, escape(strReplace));
            } while (matcher.find());
            matcher.appendTail(stringBuffer);
            return stringBuffer.toString();
        }
        return CharSequenceUtil.str(charSequence);
    }

    public static String delPre(Pattern pattern, CharSequence charSequence) {
        if (charSequence != null && pattern != null) {
            Matcher matcher = pattern.matcher(charSequence);
            if (matcher.find()) {
                return CharSequenceUtil.sub(charSequence, matcher.end(), charSequence.length());
            }
            return CharSequenceUtil.str(charSequence);
        }
        return CharSequenceUtil.str(charSequence);
    }

    public static <T extends Collection<String>> T findAll(Pattern pattern, CharSequence charSequence, final int i2, final T t2) throws IllegalArgumentException {
        if (pattern == null || charSequence == null) {
            return null;
        }
        Assert.notNull(t2, "Collection must be not null !", new Object[0]);
        findAll(pattern, charSequence, (Consumer<Matcher>) new Consumer() { // from class: cn.hutool.core.util.v
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ReUtil.lambda$findAll$4(t2, i2, (Matcher) obj);
            }
        });
        return t2;
    }

    public static boolean isMatch(Pattern pattern, CharSequence charSequence) {
        if (charSequence == null || pattern == null) {
            return false;
        }
        return pattern.matcher(charSequence).matches();
    }

    public static String delAll(Pattern pattern, CharSequence charSequence) {
        if (pattern != null && !CharSequenceUtil.isEmpty(charSequence)) {
            return pattern.matcher(charSequence).replaceAll("");
        }
        return CharSequenceUtil.str(charSequence);
    }

    public static String delFirst(Pattern pattern, CharSequence charSequence) {
        return replaceFirst(pattern, charSequence, "");
    }

    public static String delLast(Pattern pattern, CharSequence charSequence) {
        MatchResult matchResultLastIndexOf;
        if (pattern != null && CharSequenceUtil.isNotEmpty(charSequence) && (matchResultLastIndexOf = lastIndexOf(pattern, charSequence)) != null) {
            return CharSequenceUtil.subPre(charSequence, matchResultLastIndexOf.start()) + CharSequenceUtil.subSuf(charSequence, matchResultLastIndexOf.end());
        }
        return CharSequenceUtil.str(charSequence);
    }

    public static String get(Pattern pattern, CharSequence charSequence, final int i2) {
        if (charSequence == null || pattern == null) {
            return null;
        }
        final MutableObj mutableObj = new MutableObj();
        get(pattern, charSequence, (Consumer<Matcher>) new Consumer() { // from class: cn.hutool.core.util.t
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ReUtil.lambda$get$0(mutableObj, i2, (Matcher) obj);
            }
        });
        return (String) mutableObj.get2();
    }

    public static String escape(CharSequence charSequence) {
        if (CharSequenceUtil.isBlank(charSequence)) {
            return CharSequenceUtil.str(charSequence);
        }
        StringBuilder sb = new StringBuilder();
        int length = charSequence.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = charSequence.charAt(i2);
            if (RE_KEYS.contains(Character.valueOf(cCharAt))) {
                sb.append('\\');
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    public static void findAll(Pattern pattern, CharSequence charSequence, Consumer<Matcher> consumer) {
        if (pattern == null || charSequence == null) {
            return;
        }
        Matcher matcher = pattern.matcher(charSequence);
        while (matcher.find()) {
            consumer.accept(matcher);
        }
    }

    public static String get(Pattern pattern, CharSequence charSequence, final String str) {
        if (charSequence == null || pattern == null || str == null) {
            return null;
        }
        final MutableObj mutableObj = new MutableObj();
        get(pattern, charSequence, (Consumer<Matcher>) new Consumer() { // from class: cn.hutool.core.util.s
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ReUtil.lambda$get$1(mutableObj, str, (Matcher) obj);
            }
        });
        return (String) mutableObj.get2();
    }

    public static String extractMulti(String str, CharSequence charSequence, String str2) {
        if (charSequence == null || str == null || str2 == null) {
            return null;
        }
        return extractMulti(PatternPool.get(str, 32), charSequence, str2);
    }

    public static String extractMultiAndDelPre(String str, Mutable<CharSequence> mutable, String str2) {
        if (mutable == null || str == null || str2 == null) {
            return null;
        }
        return extractMultiAndDelPre(PatternPool.get(str, 32), mutable, str2);
    }

    public static void get(Pattern pattern, CharSequence charSequence, Consumer<Matcher> consumer) {
        if (charSequence == null || pattern == null || consumer == null) {
            return;
        }
        Matcher matcher = pattern.matcher(charSequence);
        if (matcher.find()) {
            consumer.accept(matcher);
        }
    }

    public static String replaceAll(CharSequence charSequence, String str, Func1<Matcher, String> func1) {
        return replaceAll(charSequence, Pattern.compile(str), func1);
    }

    public static String replaceAll(CharSequence charSequence, Pattern pattern, Func1<Matcher, String> func1) {
        if (CharSequenceUtil.isEmpty(charSequence)) {
            return CharSequenceUtil.str(charSequence);
        }
        Matcher matcher = pattern.matcher(charSequence);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            try {
                matcher.appendReplacement(stringBuffer, func1.call(matcher));
            } catch (Exception e2) {
                throw new UtilException(e2);
            }
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
