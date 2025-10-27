package org.bouncycastle.util.test;

import java.io.PrintStream;
import org.bouncycastle.util.Arrays;

/* loaded from: classes9.dex */
public abstract class SimpleTest implements Test {
    public static void runTest(Test test) {
        runTest(test, System.out);
    }

    public static void runTest(Test test, PrintStream printStream) {
        TestResult testResultPerform = test.perform();
        printStream.println(testResultPerform.toString());
        if (testResultPerform.getException() != null) {
            testResultPerform.getException().printStackTrace(printStream);
        }
    }

    private TestResult success() {
        return SimpleTestResult.successful(this, "Okay");
    }

    public boolean areEqual(byte[] bArr, byte[] bArr2) {
        return Arrays.areEqual(bArr, bArr2);
    }

    public void fail(String str) {
        throw new TestFailedException(SimpleTestResult.failed(this, str));
    }

    public void fail(String str, Object obj, Object obj2) {
        throw new TestFailedException(SimpleTestResult.failed(this, str, obj, obj2));
    }

    public void fail(String str, Throwable th) {
        throw new TestFailedException(SimpleTestResult.failed(this, str, th));
    }

    @Override // org.bouncycastle.util.test.Test
    public abstract String getName();

    @Override // org.bouncycastle.util.test.Test
    public TestResult perform() {
        try {
            performTest();
            return success();
        } catch (TestFailedException e2) {
            return e2.getResult();
        } catch (Exception e3) {
            return SimpleTestResult.failed(this, "Exception: " + e3, e3);
        }
    }

    public abstract void performTest() throws Exception;
}
