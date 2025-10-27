package org.bouncycastle.crypto.generators;

import com.yikaobang.yixue.R2;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.NaccacheSternKeyGenerationParameters;
import org.bouncycastle.crypto.params.NaccacheSternKeyParameters;
import org.bouncycastle.crypto.params.NaccacheSternPrivateKeyParameters;

/* loaded from: classes9.dex */
public class NaccacheSternKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private NaccacheSternKeyGenerationParameters param;
    private static int[] smallPrimes = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, R2.anim.window_bottom_out, 179, 181, R2.array.ease_file_file_suffix, 193, R2.array.ease_pdf_file_suffix, 199, 211, 223, 227, 229, 233, 239, 241, R2.attr.actionModeSelectAllDrawable, 257, 263, R2.attr.adScopeRingBgColor, R2.attr.adScopeStrokeWidth, R2.attr.ad_marker_color, R2.attr.add_study_plan, R2.attr.alertDialogButtonGroupStyle, R2.attr.all_course_live_banner_end_bg_color, 307, 311, 313, 317, R2.attr.app_bg, R2.attr.app_update_top_img, R2.attr.arcEnabledDrag, R2.attr.arcEnabledSize, R2.attr.arcLabelPaddingTop, R2.attr.arcNormalColor, R2.attr.arcStartAngle, R2.attr.arcThumbDrawable, R2.attr.arcTickSplitAngle, R2.attr.arrow_question_right, R2.attr.autoFitSize, R2.attr.auto_refresh_interval, 401, 409, 419, 421, R2.attr.badgeWithTextRadius, R2.attr.banner_indicator_gravity, R2.attr.banner_indicator_marginTop, R2.attr.banner_indicator_selected_color, R2.attr.banner_radius, R2.attr.barIsGradient, R2.attr.barStartColor, R2.attr.bar_height, R2.attr.barrierMargin, R2.attr.behavior_saveFlags, R2.attr.bg_circle_black, R2.attr.bg_home_new_style_statistics, R2.attr.bg_red, 503, 509, R2.attr.bl_anim_auto_start, R2.attr.bl_checkable_gradient_angle, R2.attr.bl_checked_gradient_endColor, R2.attr.bl_checked_stroke_color, R2.attr.bl_corners_topRightRadius};
    private static final BigInteger ONE = BigInteger.valueOf(1);

    private static Vector findFirstPrimes(int i2) {
        Vector vector = new Vector(i2);
        for (int i3 = 0; i3 != i2; i3++) {
            vector.addElement(BigInteger.valueOf(smallPrimes[i3]));
        }
        return vector;
    }

    private static BigInteger generatePrime(int i2, int i3, SecureRandom secureRandom) {
        BigInteger bigInteger = new BigInteger(i2, i3, secureRandom);
        while (bigInteger.bitLength() != i2) {
            bigInteger = new BigInteger(i2, i3, secureRandom);
        }
        return bigInteger;
    }

    private static int getInt(SecureRandom secureRandom, int i2) {
        int iNextInt;
        int i3;
        if (((-i2) & i2) == i2) {
            return (int) ((i2 * (secureRandom.nextInt() & Integer.MAX_VALUE)) >> 31);
        }
        do {
            iNextInt = secureRandom.nextInt() & Integer.MAX_VALUE;
            i3 = iNextInt % i2;
        } while ((iNextInt - i3) + (i2 - 1) < 0);
        return i3;
    }

    private static Vector permuteList(Vector vector, SecureRandom secureRandom) {
        Vector vector2 = new Vector();
        Vector vector3 = new Vector();
        for (int i2 = 0; i2 < vector.size(); i2++) {
            vector3.addElement(vector.elementAt(i2));
        }
        vector2.addElement(vector3.elementAt(0));
        while (true) {
            vector3.removeElementAt(0);
            if (vector3.size() == 0) {
                return vector2;
            }
            vector2.insertElementAt(vector3.elementAt(0), getInt(secureRandom, vector2.size() + 1));
        }
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public AsymmetricCipherKeyPair generateKeyPair() {
        long j2;
        BigInteger bigIntegerGeneratePrime;
        BigInteger bigIntegerAdd;
        BigInteger bigIntegerGeneratePrime2;
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigIntegerAdd2;
        BigInteger bigInteger3;
        BigInteger bigInteger4;
        BigInteger bigInteger5;
        BigInteger bigInteger6;
        BigInteger bigIntegerMod;
        boolean z2;
        BigInteger bigInteger7;
        BigInteger bigInteger8;
        int i2;
        PrintStream printStream;
        StringBuilder sb;
        String str;
        long j3;
        BigInteger bigInteger9;
        int i3;
        int strength = this.param.getStrength();
        SecureRandom random = this.param.getRandom();
        int certainty = this.param.getCertainty();
        boolean zIsDebug = this.param.isDebug();
        if (zIsDebug) {
            System.out.println("Fetching first " + this.param.getCntSmallPrimes() + " primes.");
        }
        Vector vectorPermuteList = permuteList(findFirstPrimes(this.param.getCntSmallPrimes()), random);
        BigInteger bigIntegerMultiply = ONE;
        BigInteger bigIntegerMultiply2 = bigIntegerMultiply;
        for (int i4 = 0; i4 < vectorPermuteList.size() / 2; i4++) {
            bigIntegerMultiply2 = bigIntegerMultiply2.multiply((BigInteger) vectorPermuteList.elementAt(i4));
        }
        for (int size = vectorPermuteList.size() / 2; size < vectorPermuteList.size(); size++) {
            bigIntegerMultiply = bigIntegerMultiply.multiply((BigInteger) vectorPermuteList.elementAt(size));
        }
        BigInteger bigIntegerMultiply3 = bigIntegerMultiply2.multiply(bigIntegerMultiply);
        int iBitLength = (((strength - bigIntegerMultiply3.bitLength()) - 48) / 2) + 1;
        BigInteger bigIntegerGeneratePrime3 = generatePrime(iBitLength, certainty, random);
        BigInteger bigIntegerGeneratePrime4 = generatePrime(iBitLength, certainty, random);
        if (zIsDebug) {
            System.out.println("generating p and q");
        }
        BigInteger bigIntegerShiftLeft = bigIntegerGeneratePrime3.multiply(bigIntegerMultiply2).shiftLeft(1);
        BigInteger bigIntegerShiftLeft2 = bigIntegerGeneratePrime4.multiply(bigIntegerMultiply).shiftLeft(1);
        long j4 = 0;
        while (true) {
            j2 = j4 + 1;
            bigIntegerGeneratePrime = generatePrime(24, certainty, random);
            bigIntegerAdd = bigIntegerGeneratePrime.multiply(bigIntegerShiftLeft).add(ONE);
            if (bigIntegerAdd.isProbablePrime(certainty)) {
                while (true) {
                    do {
                        bigIntegerGeneratePrime2 = generatePrime(24, certainty, random);
                    } while (bigIntegerGeneratePrime.equals(bigIntegerGeneratePrime2));
                    BigInteger bigIntegerMultiply4 = bigIntegerGeneratePrime2.multiply(bigIntegerShiftLeft2);
                    bigInteger = bigIntegerShiftLeft2;
                    bigInteger2 = ONE;
                    bigIntegerAdd2 = bigIntegerMultiply4.add(bigInteger2);
                    if (bigIntegerAdd2.isProbablePrime(certainty)) {
                        break;
                    }
                    bigIntegerShiftLeft2 = bigInteger;
                }
                bigInteger3 = bigIntegerShiftLeft;
                if (!bigIntegerMultiply3.gcd(bigIntegerGeneratePrime.multiply(bigIntegerGeneratePrime2)).equals(bigInteger2)) {
                    continue;
                } else {
                    if (bigIntegerAdd.multiply(bigIntegerAdd2).bitLength() >= strength) {
                        break;
                    }
                    if (zIsDebug) {
                        System.out.println("key size too small. Should be " + strength + " but is actually " + bigIntegerAdd.multiply(bigIntegerAdd2).bitLength());
                    }
                }
            } else {
                bigInteger = bigIntegerShiftLeft2;
                bigInteger3 = bigIntegerShiftLeft;
            }
            j4 = j2;
            bigIntegerShiftLeft2 = bigInteger;
            bigIntegerShiftLeft = bigInteger3;
        }
        BigInteger bigInteger10 = bigIntegerGeneratePrime4;
        if (zIsDebug) {
            bigInteger4 = bigIntegerGeneratePrime3;
            System.out.println("needed " + j2 + " tries to generate p and q.");
        } else {
            bigInteger4 = bigIntegerGeneratePrime3;
        }
        BigInteger bigIntegerMultiply5 = bigIntegerAdd.multiply(bigIntegerAdd2);
        BigInteger bigIntegerMultiply6 = bigIntegerAdd.subtract(bigInteger2).multiply(bigIntegerAdd2.subtract(bigInteger2));
        if (zIsDebug) {
            System.out.println("generating g");
        }
        long j5 = 0;
        while (true) {
            Vector vector = new Vector();
            bigInteger5 = bigIntegerAdd;
            bigInteger6 = bigIntegerAdd2;
            int i5 = 0;
            while (i5 != vectorPermuteList.size()) {
                BigInteger bigIntegerDivide = bigIntegerMultiply6.divide((BigInteger) vectorPermuteList.elementAt(i5));
                while (true) {
                    j3 = j5 + 1;
                    bigInteger9 = new BigInteger(strength, certainty, random);
                    i3 = strength;
                    if (bigInteger9.modPow(bigIntegerDivide, bigIntegerMultiply5).equals(ONE)) {
                        j5 = j3;
                        strength = i3;
                    }
                }
                vector.addElement(bigInteger9);
                i5++;
                j5 = j3;
                strength = i3;
            }
            int i6 = strength;
            bigIntegerMod = ONE;
            int i7 = 0;
            while (i7 < vectorPermuteList.size()) {
                bigIntegerMod = bigIntegerMod.multiply(((BigInteger) vector.elementAt(i7)).modPow(bigIntegerMultiply3.divide((BigInteger) vectorPermuteList.elementAt(i7)), bigIntegerMultiply5)).mod(bigIntegerMultiply5);
                i7++;
                random = random;
            }
            SecureRandom secureRandom = random;
            int i8 = 0;
            while (true) {
                if (i8 >= vectorPermuteList.size()) {
                    z2 = false;
                    break;
                }
                if (bigIntegerMod.modPow(bigIntegerMultiply6.divide((BigInteger) vectorPermuteList.elementAt(i8)), bigIntegerMultiply5).equals(ONE)) {
                    if (zIsDebug) {
                        System.out.println("g has order phi(n)/" + vectorPermuteList.elementAt(i8) + "\n g: " + bigIntegerMod);
                    }
                    z2 = true;
                } else {
                    i8++;
                }
            }
            if (z2) {
                bigInteger8 = bigInteger10;
                bigInteger7 = bigInteger4;
                i2 = certainty;
            } else {
                BigInteger bigIntegerModPow = bigIntegerMod.modPow(bigIntegerMultiply6.divide(BigInteger.valueOf(4L)), bigIntegerMultiply5);
                BigInteger bigInteger11 = ONE;
                if (bigIntegerModPow.equals(bigInteger11)) {
                    if (zIsDebug) {
                        printStream = System.out;
                        sb = new StringBuilder();
                        str = "g has order phi(n)/4\n g:";
                        sb.append(str);
                        sb.append(bigIntegerMod);
                        printStream.println(sb.toString());
                    }
                    bigInteger8 = bigInteger10;
                    bigInteger7 = bigInteger4;
                    i2 = certainty;
                } else if (bigIntegerMod.modPow(bigIntegerMultiply6.divide(bigIntegerGeneratePrime), bigIntegerMultiply5).equals(bigInteger11)) {
                    if (zIsDebug) {
                        printStream = System.out;
                        sb = new StringBuilder();
                        str = "g has order phi(n)/p'\n g: ";
                        sb.append(str);
                        sb.append(bigIntegerMod);
                        printStream.println(sb.toString());
                    }
                    bigInteger8 = bigInteger10;
                    bigInteger7 = bigInteger4;
                    i2 = certainty;
                } else if (bigIntegerMod.modPow(bigIntegerMultiply6.divide(bigIntegerGeneratePrime2), bigIntegerMultiply5).equals(bigInteger11)) {
                    if (zIsDebug) {
                        printStream = System.out;
                        sb = new StringBuilder();
                        str = "g has order phi(n)/q'\n g: ";
                        sb.append(str);
                        sb.append(bigIntegerMod);
                        printStream.println(sb.toString());
                    }
                    bigInteger8 = bigInteger10;
                    bigInteger7 = bigInteger4;
                    i2 = certainty;
                } else {
                    bigInteger7 = bigInteger4;
                    if (!bigIntegerMod.modPow(bigIntegerMultiply6.divide(bigInteger7), bigIntegerMultiply5).equals(bigInteger11)) {
                        bigInteger8 = bigInteger10;
                        if (!bigIntegerMod.modPow(bigIntegerMultiply6.divide(bigInteger8), bigIntegerMultiply5).equals(bigInteger11)) {
                            break;
                        }
                        if (zIsDebug) {
                            PrintStream printStream2 = System.out;
                            StringBuilder sb2 = new StringBuilder();
                            i2 = certainty;
                            sb2.append("g has order phi(n)/b\n g: ");
                            sb2.append(bigIntegerMod);
                            printStream2.println(sb2.toString());
                        }
                    } else {
                        if (zIsDebug) {
                            System.out.println("g has order phi(n)/a\n g: " + bigIntegerMod);
                        }
                        bigInteger8 = bigInteger10;
                    }
                    i2 = certainty;
                }
            }
            bigInteger4 = bigInteger7;
            certainty = i2;
            bigIntegerAdd2 = bigInteger6;
            bigIntegerAdd = bigInteger5;
            strength = i6;
            random = secureRandom;
            bigInteger10 = bigInteger8;
        }
        if (zIsDebug) {
            System.out.println("needed " + j5 + " tries to generate g");
            System.out.println();
            System.out.println("found new NaccacheStern cipher variables:");
            System.out.println("smallPrimes: " + vectorPermuteList);
            System.out.println("sigma:...... " + bigIntegerMultiply3 + " (" + bigIntegerMultiply3.bitLength() + " bits)");
            PrintStream printStream3 = System.out;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("a:.......... ");
            sb3.append(bigInteger7);
            printStream3.println(sb3.toString());
            System.out.println("b:.......... " + bigInteger8);
            System.out.println("p':......... " + bigIntegerGeneratePrime);
            System.out.println("q':......... " + bigIntegerGeneratePrime2);
            System.out.println("p:.......... " + bigInteger5);
            System.out.println("q:.......... " + bigInteger6);
            System.out.println("n:.......... " + bigIntegerMultiply5);
            System.out.println("phi(n):..... " + bigIntegerMultiply6);
            System.out.println("g:.......... " + bigIntegerMod);
            System.out.println();
        }
        return new AsymmetricCipherKeyPair(new NaccacheSternKeyParameters(false, bigIntegerMod, bigIntegerMultiply5, bigIntegerMultiply3.bitLength()), new NaccacheSternPrivateKeyParameters(bigIntegerMod, bigIntegerMultiply5, bigIntegerMultiply3.bitLength(), vectorPermuteList, bigIntegerMultiply6));
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.param = (NaccacheSternKeyGenerationParameters) keyGenerationParameters;
    }
}
