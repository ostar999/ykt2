package androidx.constraintlayout.core.parser;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class CLKey extends CLContainer {
    private static ArrayList<String> sections;

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        sections = arrayList;
        arrayList.add("ConstraintSets");
        sections.add("Variables");
        sections.add("Generate");
        sections.add("Transitions");
        sections.add("KeyFrames");
        sections.add("KeyAttributes");
        sections.add("KeyPositions");
        sections.add("KeyCycles");
    }

    public CLKey(char[] cArr) {
        super(cArr);
    }

    public static CLElement allocate(char[] cArr) {
        return new CLKey(cArr);
    }

    public String getName() {
        return content();
    }

    public CLElement getValue() {
        if (this.mElements.size() > 0) {
            return this.mElements.get(0);
        }
        return null;
    }

    public void set(CLElement cLElement) {
        if (this.mElements.size() > 0) {
            this.mElements.set(0, cLElement);
        } else {
            this.mElements.add(cLElement);
        }
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public String toFormattedJSON(int i2, int i3) {
        StringBuilder sb = new StringBuilder(getDebugName());
        addIndent(sb, i2);
        String strContent = content();
        if (this.mElements.size() <= 0) {
            return strContent + ": <> ";
        }
        sb.append(strContent);
        sb.append(": ");
        if (sections.contains(strContent)) {
            i3 = 3;
        }
        if (i3 > 0) {
            sb.append(this.mElements.get(0).toFormattedJSON(i2, i3 - 1));
        } else {
            String json = this.mElements.get(0).toJSON();
            if (json.length() + i2 < CLElement.MAX_LINE) {
                sb.append(json);
            } else {
                sb.append(this.mElements.get(0).toFormattedJSON(i2, i3 - 1));
            }
        }
        return sb.toString();
    }

    @Override // androidx.constraintlayout.core.parser.CLElement
    public String toJSON() {
        if (this.mElements.size() <= 0) {
            return getDebugName() + content() + ": <> ";
        }
        return getDebugName() + content() + ": " + this.mElements.get(0).toJSON();
    }

    public static CLElement allocate(String str, CLElement cLElement) {
        CLKey cLKey = new CLKey(str.toCharArray());
        cLKey.setStart(0L);
        cLKey.setEnd(str.length() - 1);
        cLKey.set(cLElement);
        return cLKey;
    }
}
