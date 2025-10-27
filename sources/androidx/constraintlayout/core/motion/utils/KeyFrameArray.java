package androidx.constraintlayout.core.motion.utils;

import androidx.constraintlayout.core.motion.CustomAttribute;
import androidx.constraintlayout.core.motion.CustomVariable;
import cn.hutool.core.text.StrPool;
import java.io.PrintStream;
import java.util.Arrays;

/* loaded from: classes.dex */
public class KeyFrameArray {

    public static class CustomArray {
        private static final int EMPTY = 999;
        int count;
        int[] keys = new int[101];
        CustomAttribute[] values = new CustomAttribute[101];

        public CustomArray() {
            clear();
        }

        public void append(int i2, CustomAttribute customAttribute) {
            if (this.values[i2] != null) {
                remove(i2);
            }
            this.values[i2] = customAttribute;
            int[] iArr = this.keys;
            int i3 = this.count;
            this.count = i3 + 1;
            iArr[i3] = i2;
            Arrays.sort(iArr);
        }

        public void clear() {
            Arrays.fill(this.keys, 999);
            Arrays.fill(this.values, (Object) null);
            this.count = 0;
        }

        public void dump() {
            System.out.println("V: " + Arrays.toString(Arrays.copyOf(this.keys, this.count)));
            System.out.print("K: [");
            int i2 = 0;
            while (i2 < this.count) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(i2 == 0 ? "" : ", ");
                sb.append(valueAt(i2));
                printStream.print(sb.toString());
                i2++;
            }
            System.out.println(StrPool.BRACKET_END);
        }

        public int keyAt(int i2) {
            return this.keys[i2];
        }

        public void remove(int i2) {
            this.values[i2] = null;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int i5 = this.count;
                if (i3 >= i5) {
                    this.count = i5 - 1;
                    return;
                }
                int[] iArr = this.keys;
                if (i2 == iArr[i3]) {
                    iArr[i3] = 999;
                    i4++;
                }
                if (i3 != i4) {
                    iArr[i3] = iArr[i4];
                }
                i4++;
                i3++;
            }
        }

        public int size() {
            return this.count;
        }

        public CustomAttribute valueAt(int i2) {
            return this.values[this.keys[i2]];
        }
    }

    public static class CustomVar {
        private static final int EMPTY = 999;
        int count;
        int[] keys = new int[101];
        CustomVariable[] values = new CustomVariable[101];

        public CustomVar() {
            clear();
        }

        public void append(int i2, CustomVariable customVariable) {
            if (this.values[i2] != null) {
                remove(i2);
            }
            this.values[i2] = customVariable;
            int[] iArr = this.keys;
            int i3 = this.count;
            this.count = i3 + 1;
            iArr[i3] = i2;
            Arrays.sort(iArr);
        }

        public void clear() {
            Arrays.fill(this.keys, 999);
            Arrays.fill(this.values, (Object) null);
            this.count = 0;
        }

        public void dump() {
            System.out.println("V: " + Arrays.toString(Arrays.copyOf(this.keys, this.count)));
            System.out.print("K: [");
            int i2 = 0;
            while (i2 < this.count) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(i2 == 0 ? "" : ", ");
                sb.append(valueAt(i2));
                printStream.print(sb.toString());
                i2++;
            }
            System.out.println(StrPool.BRACKET_END);
        }

        public int keyAt(int i2) {
            return this.keys[i2];
        }

        public void remove(int i2) {
            this.values[i2] = null;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int i5 = this.count;
                if (i3 >= i5) {
                    this.count = i5 - 1;
                    return;
                }
                int[] iArr = this.keys;
                if (i2 == iArr[i3]) {
                    iArr[i3] = 999;
                    i4++;
                }
                if (i3 != i4) {
                    iArr[i3] = iArr[i4];
                }
                i4++;
                i3++;
            }
        }

        public int size() {
            return this.count;
        }

        public CustomVariable valueAt(int i2) {
            return this.values[this.keys[i2]];
        }
    }

    public static class FloatArray {
        private static final int EMPTY = 999;
        int count;
        int[] keys = new int[101];
        float[][] values = new float[101][];

        public FloatArray() {
            clear();
        }

        public void append(int i2, float[] fArr) {
            if (this.values[i2] != null) {
                remove(i2);
            }
            this.values[i2] = fArr;
            int[] iArr = this.keys;
            int i3 = this.count;
            this.count = i3 + 1;
            iArr[i3] = i2;
            Arrays.sort(iArr);
        }

        public void clear() {
            Arrays.fill(this.keys, 999);
            Arrays.fill(this.values, (Object) null);
            this.count = 0;
        }

        public void dump() {
            System.out.println("V: " + Arrays.toString(Arrays.copyOf(this.keys, this.count)));
            System.out.print("K: [");
            int i2 = 0;
            while (i2 < this.count) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(i2 == 0 ? "" : ", ");
                sb.append(Arrays.toString(valueAt(i2)));
                printStream.print(sb.toString());
                i2++;
            }
            System.out.println(StrPool.BRACKET_END);
        }

        public int keyAt(int i2) {
            return this.keys[i2];
        }

        public void remove(int i2) {
            this.values[i2] = null;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                int i5 = this.count;
                if (i3 >= i5) {
                    this.count = i5 - 1;
                    return;
                }
                int[] iArr = this.keys;
                if (i2 == iArr[i3]) {
                    iArr[i3] = 999;
                    i4++;
                }
                if (i3 != i4) {
                    iArr[i3] = iArr[i4];
                }
                i4++;
                i3++;
            }
        }

        public int size() {
            return this.count;
        }

        public float[] valueAt(int i2) {
            return this.values[this.keys[i2]];
        }
    }
}
