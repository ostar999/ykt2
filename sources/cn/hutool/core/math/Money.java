package cn.hutool.core.math;

import cn.hutool.core.util.StrUtil;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/* loaded from: classes.dex */
public class Money implements Serializable, Comparable<Money> {
    public static final String DEFAULT_CURRENCY_CODE = "CNY";
    private static final long serialVersionUID = -1004117971993390293L;
    private long cent;
    private final Currency currency;
    public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private static final int[] CENT_FACTORS = {1, 10, 100, 1000};

    public Money() {
        this(0.0d);
    }

    public Money add(Money money) {
        assertSameCurrencyAs(money);
        return newMoneyWithSameCurrency(this.cent + money.cent);
    }

    public Money addTo(Money money) {
        assertSameCurrencyAs(money);
        this.cent += money.cent;
        return this;
    }

    public Money[] allocate(int i2) {
        Money[] moneyArr = new Money[i2];
        Money moneyNewMoneyWithSameCurrency = newMoneyWithSameCurrency(this.cent / i2);
        Money moneyNewMoneyWithSameCurrency2 = newMoneyWithSameCurrency(moneyNewMoneyWithSameCurrency.cent + 1);
        int i3 = ((int) this.cent) % i2;
        for (int i4 = 0; i4 < i3; i4++) {
            moneyArr[i4] = moneyNewMoneyWithSameCurrency2;
        }
        while (i3 < i2) {
            moneyArr[i3] = moneyNewMoneyWithSameCurrency;
            i3++;
        }
        return moneyArr;
    }

    public void assertSameCurrencyAs(Money money) {
        if (!this.currency.equals(money.currency)) {
            throw new IllegalArgumentException("Money math currency mismatch.");
        }
    }

    public Money divide(double d3) {
        return newMoneyWithSameCurrency(Math.round(this.cent / d3));
    }

    public Money divideBy(double d3) {
        this.cent = Math.round(this.cent / d3);
        return this;
    }

    public String dump() {
        StringBuilder sbBuilder = StrUtil.builder();
        sbBuilder.append("cent = ");
        sbBuilder.append(this.cent);
        sbBuilder.append(File.separatorChar);
        sbBuilder.append("currency = ");
        sbBuilder.append(this.currency);
        return sbBuilder.toString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof Money) && equals((Money) obj);
    }

    public BigDecimal getAmount() {
        return BigDecimal.valueOf(this.cent, this.currency.getDefaultFractionDigits());
    }

    public long getCent() {
        return this.cent;
    }

    public int getCentFactor() {
        return CENT_FACTORS[this.currency.getDefaultFractionDigits()];
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public boolean greaterThan(Money money) {
        return compareTo(money) > 0;
    }

    public int hashCode() {
        long j2 = this.cent;
        return (int) (j2 ^ (j2 >>> 32));
    }

    public Money multiply(long j2) {
        return newMoneyWithSameCurrency(this.cent * j2);
    }

    public Money multiplyBy(long j2) {
        this.cent *= j2;
        return this;
    }

    public Money newMoneyWithSameCurrency(long j2) {
        Money money = new Money(0.0d, this.currency);
        money.cent = j2;
        return money;
    }

    public long rounding(BigDecimal bigDecimal, RoundingMode roundingMode) {
        return bigDecimal.setScale(0, roundingMode).longValue();
    }

    public void setAmount(BigDecimal bigDecimal) {
        if (bigDecimal != null) {
            this.cent = rounding(bigDecimal.movePointRight(2), DEFAULT_ROUNDING_MODE);
        }
    }

    public void setCent(long j2) {
        this.cent = j2;
    }

    public Money subtract(Money money) {
        assertSameCurrencyAs(money);
        return newMoneyWithSameCurrency(this.cent - money.cent);
    }

    public Money subtractFrom(Money money) {
        assertSameCurrencyAs(money);
        this.cent -= money.cent;
        return this;
    }

    public String toString() {
        return getAmount().toString();
    }

    public Money(long j2, int i2) {
        this(j2, i2, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    @Override // java.lang.Comparable
    public int compareTo(Money money) {
        assertSameCurrencyAs(money);
        return Long.compare(this.cent, money.cent);
    }

    public Money divide(BigDecimal bigDecimal) {
        return divide(bigDecimal, DEFAULT_ROUNDING_MODE);
    }

    public Money divideBy(BigDecimal bigDecimal) {
        return divideBy(bigDecimal, DEFAULT_ROUNDING_MODE);
    }

    public boolean equals(Money money) {
        return this.currency.equals(money.currency) && this.cent == money.cent;
    }

    public Money multiply(double d3) {
        return newMoneyWithSameCurrency(Math.round(this.cent * d3));
    }

    public Money multiplyBy(double d3) {
        this.cent = Math.round(this.cent * d3);
        return this;
    }

    public Money(long j2, int i2, Currency currency) {
        this.currency = currency;
        if (0 == j2) {
            this.cent = i2;
        } else {
            this.cent = (j2 * getCentFactor()) + (i2 % getCentFactor());
        }
    }

    public Money divide(BigDecimal bigDecimal, RoundingMode roundingMode) {
        return newMoneyWithSameCurrency(BigDecimal.valueOf(this.cent).divide(bigDecimal, roundingMode).longValue());
    }

    public Money divideBy(BigDecimal bigDecimal, RoundingMode roundingMode) {
        this.cent = BigDecimal.valueOf(this.cent).divide(bigDecimal, roundingMode).longValue();
        return this;
    }

    public Money multiply(BigDecimal bigDecimal) {
        return multiply(bigDecimal, DEFAULT_ROUNDING_MODE);
    }

    public Money multiplyBy(BigDecimal bigDecimal) {
        return multiplyBy(bigDecimal, DEFAULT_ROUNDING_MODE);
    }

    public Money multiply(BigDecimal bigDecimal, RoundingMode roundingMode) {
        return newMoneyWithSameCurrency(rounding(BigDecimal.valueOf(this.cent).multiply(bigDecimal), roundingMode));
    }

    public Money multiplyBy(BigDecimal bigDecimal, RoundingMode roundingMode) {
        this.cent = rounding(BigDecimal.valueOf(this.cent).multiply(bigDecimal), roundingMode);
        return this;
    }

    public Money(String str) {
        this(str, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    public Money[] allocate(long[] jArr) {
        int length = jArr.length;
        Money[] moneyArr = new Money[length];
        long j2 = 0;
        for (long j3 : jArr) {
            j2 += j3;
        }
        long j4 = this.cent;
        for (int i2 = 0; i2 < length; i2++) {
            Money moneyNewMoneyWithSameCurrency = newMoneyWithSameCurrency((this.cent * jArr[i2]) / j2);
            moneyArr[i2] = moneyNewMoneyWithSameCurrency;
            j4 -= moneyNewMoneyWithSameCurrency.cent;
        }
        for (int i3 = 0; i3 < j4; i3++) {
            moneyArr[i3].cent++;
        }
        return moneyArr;
    }

    public Money(String str, Currency currency) {
        this(new BigDecimal(str), currency);
    }

    public Money(String str, Currency currency, RoundingMode roundingMode) {
        this(new BigDecimal(str), currency, roundingMode);
    }

    public Money(double d3) {
        this(d3, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    public Money(double d3, Currency currency) {
        this.currency = currency;
        this.cent = Math.round(d3 * getCentFactor());
    }

    public Money(BigDecimal bigDecimal) {
        this(bigDecimal, Currency.getInstance(DEFAULT_CURRENCY_CODE));
    }

    public Money(BigDecimal bigDecimal, RoundingMode roundingMode) {
        this(bigDecimal, Currency.getInstance(DEFAULT_CURRENCY_CODE), roundingMode);
    }

    public Money(BigDecimal bigDecimal, Currency currency) {
        this(bigDecimal, currency, DEFAULT_ROUNDING_MODE);
    }

    public Money(BigDecimal bigDecimal, Currency currency, RoundingMode roundingMode) {
        this.currency = currency;
        this.cent = rounding(bigDecimal.movePointRight(currency.getDefaultFractionDigits()), roundingMode);
    }
}
