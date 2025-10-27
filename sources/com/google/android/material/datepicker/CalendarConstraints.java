package com.google.android.material.datepicker;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.ObjectsCompat;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class CalendarConstraints implements Parcelable {
    public static final Parcelable.Creator<CalendarConstraints> CREATOR = new Parcelable.Creator<CalendarConstraints>() { // from class: com.google.android.material.datepicker.CalendarConstraints.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NonNull
        public CalendarConstraints createFromParcel(@NonNull Parcel parcel) {
            return new CalendarConstraints((Month) parcel.readParcelable(Month.class.getClassLoader()), (Month) parcel.readParcelable(Month.class.getClassLoader()), (DateValidator) parcel.readParcelable(DateValidator.class.getClassLoader()), (Month) parcel.readParcelable(Month.class.getClassLoader()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        @NonNull
        public CalendarConstraints[] newArray(int i2) {
            return new CalendarConstraints[i2];
        }
    };

    @NonNull
    private final Month end;
    private final int monthSpan;

    @Nullable
    private Month openAt;

    @NonNull
    private final Month start;

    @NonNull
    private final DateValidator validator;
    private final int yearSpan;

    public interface DateValidator extends Parcelable {
        boolean isValid(long j2);
    }

    public Month clamp(Month month) {
        return month.compareTo(this.start) < 0 ? this.start : month.compareTo(this.end) > 0 ? this.end : month;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CalendarConstraints)) {
            return false;
        }
        CalendarConstraints calendarConstraints = (CalendarConstraints) obj;
        return this.start.equals(calendarConstraints.start) && this.end.equals(calendarConstraints.end) && ObjectsCompat.equals(this.openAt, calendarConstraints.openAt) && this.validator.equals(calendarConstraints.validator);
    }

    public DateValidator getDateValidator() {
        return this.validator;
    }

    @NonNull
    public Month getEnd() {
        return this.end;
    }

    public int getMonthSpan() {
        return this.monthSpan;
    }

    @Nullable
    public Month getOpenAt() {
        return this.openAt;
    }

    @NonNull
    public Month getStart() {
        return this.start;
    }

    public int getYearSpan() {
        return this.yearSpan;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.start, this.end, this.openAt, this.validator});
    }

    public boolean isWithinBounds(long j2) {
        if (this.start.getDay(1) <= j2) {
            Month month = this.end;
            if (j2 <= month.getDay(month.daysInMonth)) {
                return true;
            }
        }
        return false;
    }

    public void setOpenAt(@Nullable Month month) {
        this.openAt = month;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i2) {
        parcel.writeParcelable(this.start, 0);
        parcel.writeParcelable(this.end, 0);
        parcel.writeParcelable(this.openAt, 0);
        parcel.writeParcelable(this.validator, 0);
    }

    private CalendarConstraints(@NonNull Month month, @NonNull Month month2, @NonNull DateValidator dateValidator, @Nullable Month month3) {
        this.start = month;
        this.end = month2;
        this.openAt = month3;
        this.validator = dateValidator;
        if (month3 != null && month.compareTo(month3) > 0) {
            throw new IllegalArgumentException("start Month cannot be after current Month");
        }
        if (month3 != null && month3.compareTo(month2) > 0) {
            throw new IllegalArgumentException("current Month cannot be after end Month");
        }
        this.monthSpan = month.monthsUntil(month2) + 1;
        this.yearSpan = (month2.year - month.year) + 1;
    }

    public static final class Builder {
        private static final String DEEP_COPY_VALIDATOR_KEY = "DEEP_COPY_VALIDATOR_KEY";
        private long end;
        private Long openAt;
        private long start;
        private DateValidator validator;
        static final long DEFAULT_START = UtcDates.canonicalYearMonthDay(Month.create(1900, 0).timeInMillis);
        static final long DEFAULT_END = UtcDates.canonicalYearMonthDay(Month.create(2100, 11).timeInMillis);

        public Builder() {
            this.start = DEFAULT_START;
            this.end = DEFAULT_END;
            this.validator = DateValidatorPointForward.from(Long.MIN_VALUE);
        }

        @NonNull
        public CalendarConstraints build() {
            Bundle bundle = new Bundle();
            bundle.putParcelable(DEEP_COPY_VALIDATOR_KEY, this.validator);
            Month monthCreate = Month.create(this.start);
            Month monthCreate2 = Month.create(this.end);
            DateValidator dateValidator = (DateValidator) bundle.getParcelable(DEEP_COPY_VALIDATOR_KEY);
            Long l2 = this.openAt;
            return new CalendarConstraints(monthCreate, monthCreate2, dateValidator, l2 == null ? null : Month.create(l2.longValue()));
        }

        @NonNull
        public Builder setEnd(long j2) {
            this.end = j2;
            return this;
        }

        @NonNull
        public Builder setOpenAt(long j2) {
            this.openAt = Long.valueOf(j2);
            return this;
        }

        @NonNull
        public Builder setStart(long j2) {
            this.start = j2;
            return this;
        }

        @NonNull
        public Builder setValidator(@NonNull DateValidator dateValidator) {
            this.validator = dateValidator;
            return this;
        }

        public Builder(@NonNull CalendarConstraints calendarConstraints) {
            this.start = DEFAULT_START;
            this.end = DEFAULT_END;
            this.validator = DateValidatorPointForward.from(Long.MIN_VALUE);
            this.start = calendarConstraints.start.timeInMillis;
            this.end = calendarConstraints.end.timeInMillis;
            this.openAt = Long.valueOf(calendarConstraints.openAt.timeInMillis);
            this.validator = calendarConstraints.validator;
        }
    }
}
