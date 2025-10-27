package cn.hutool.core.date;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class StopWatch {
    private String currentTaskName;
    private final String id;
    private TaskInfo lastTaskInfo;
    private long startTimeNanos;
    private int taskCount;
    private List<TaskInfo> taskList;
    private long totalTimeNanos;

    public static final class TaskInfo {
        private final String taskName;
        private final long timeNanos;

        public TaskInfo(String str, long j2) {
            this.taskName = str;
            this.timeNanos = j2;
        }

        public String getTaskName() {
            return this.taskName;
        }

        public long getTime(TimeUnit timeUnit) {
            return timeUnit.convert(this.timeNanos, TimeUnit.NANOSECONDS);
        }

        public long getTimeMillis() {
            return getTime(TimeUnit.MILLISECONDS);
        }

        public long getTimeNanos() {
            return this.timeNanos;
        }

        public double getTimeSeconds() {
            return DateUtil.nanosToSeconds(this.timeNanos);
        }
    }

    public StopWatch() {
        this("");
    }

    public static StopWatch create(String str) {
        return new StopWatch(str);
    }

    public String currentTaskName() {
        return this.currentTaskName;
    }

    public String getId() {
        return this.id;
    }

    public TaskInfo getLastTaskInfo() throws IllegalStateException {
        TaskInfo taskInfo = this.lastTaskInfo;
        if (taskInfo != null) {
            return taskInfo;
        }
        throw new IllegalStateException("No tasks run: can't get last task info");
    }

    public String getLastTaskName() throws IllegalStateException {
        TaskInfo taskInfo = this.lastTaskInfo;
        if (taskInfo != null) {
            return taskInfo.getTaskName();
        }
        throw new IllegalStateException("No tasks run: can't get last task name");
    }

    public long getLastTaskTimeMillis() throws IllegalStateException {
        TaskInfo taskInfo = this.lastTaskInfo;
        if (taskInfo != null) {
            return taskInfo.getTimeMillis();
        }
        throw new IllegalStateException("No tasks run: can't get last task interval");
    }

    public long getLastTaskTimeNanos() throws IllegalStateException {
        TaskInfo taskInfo = this.lastTaskInfo;
        if (taskInfo != null) {
            return taskInfo.getTimeNanos();
        }
        throw new IllegalStateException("No tasks run: can't get last task interval");
    }

    public int getTaskCount() {
        return this.taskCount;
    }

    public TaskInfo[] getTaskInfo() {
        List<TaskInfo> list = this.taskList;
        if (list != null) {
            return (TaskInfo[]) list.toArray(new TaskInfo[0]);
        }
        throw new UnsupportedOperationException("Task info is not being kept!");
    }

    public long getTotal(TimeUnit timeUnit) {
        return timeUnit.convert(this.totalTimeNanos, TimeUnit.NANOSECONDS);
    }

    public long getTotalTimeMillis() {
        return getTotal(TimeUnit.MILLISECONDS);
    }

    public long getTotalTimeNanos() {
        return this.totalTimeNanos;
    }

    public double getTotalTimeSeconds() {
        return DateUtil.nanosToSeconds(this.totalTimeNanos);
    }

    public boolean isRunning() {
        return this.currentTaskName != null;
    }

    public String prettyPrint() {
        return prettyPrint(null);
    }

    public void setKeepTaskList(boolean z2) {
        if (!z2) {
            this.taskList = null;
        } else if (this.taskList == null) {
            this.taskList = new ArrayList();
        }
    }

    public String shortSummary() {
        return shortSummary(null);
    }

    public void start() throws IllegalStateException {
        start("");
    }

    public void stop() throws IllegalStateException {
        if (this.currentTaskName == null) {
            throw new IllegalStateException("Can't stop StopWatch: it's not running");
        }
        long jNanoTime = System.nanoTime() - this.startTimeNanos;
        this.totalTimeNanos += jNanoTime;
        TaskInfo taskInfo = new TaskInfo(this.currentTaskName, jNanoTime);
        this.lastTaskInfo = taskInfo;
        List<TaskInfo> list = this.taskList;
        if (list != null) {
            list.add(taskInfo);
        }
        this.taskCount++;
        this.currentTaskName = null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(shortSummary());
        List<TaskInfo> list = this.taskList;
        if (list != null) {
            for (TaskInfo taskInfo : list) {
                sb.append("; [");
                sb.append(taskInfo.getTaskName());
                sb.append("] took ");
                sb.append(taskInfo.getTimeNanos());
                sb.append(" ns");
                long jRound = Math.round((taskInfo.getTimeNanos() * 100.0d) / getTotalTimeNanos());
                sb.append(" = ");
                sb.append(jRound);
                sb.append("%");
            }
        } else {
            sb.append("; no task info kept");
        }
        return sb.toString();
    }

    public StopWatch(String str) {
        this(str, true);
    }

    public String prettyPrint(TimeUnit timeUnit) {
        if (timeUnit == null) {
            timeUnit = TimeUnit.NANOSECONDS;
        }
        StringBuilder sb = new StringBuilder(shortSummary(timeUnit));
        sb.append(FileUtil.getLineSeparator());
        if (this.taskList == null) {
            sb.append("No task info kept");
        } else {
            sb.append("---------------------------------------------");
            sb.append(FileUtil.getLineSeparator());
            sb.append(DateUtil.getShotName(timeUnit));
            sb.append("         %     Task name");
            sb.append(FileUtil.getLineSeparator());
            sb.append("---------------------------------------------");
            sb.append(FileUtil.getLineSeparator());
            NumberFormat numberInstance = NumberFormat.getNumberInstance();
            numberInstance.setMinimumIntegerDigits(9);
            numberInstance.setGroupingUsed(false);
            NumberFormat percentInstance = NumberFormat.getPercentInstance();
            percentInstance.setMinimumIntegerDigits(2);
            percentInstance.setGroupingUsed(false);
            for (TaskInfo taskInfo : getTaskInfo()) {
                sb.append(numberInstance.format(taskInfo.getTime(timeUnit)));
                sb.append("  ");
                sb.append(percentInstance.format(taskInfo.getTimeNanos() / getTotalTimeNanos()));
                sb.append("   ");
                sb.append(taskInfo.getTaskName());
                sb.append(FileUtil.getLineSeparator());
            }
        }
        return sb.toString();
    }

    public String shortSummary(TimeUnit timeUnit) {
        if (timeUnit == null) {
            timeUnit = TimeUnit.NANOSECONDS;
        }
        return CharSequenceUtil.format("StopWatch '{}': running time = {} {}", this.id, Long.valueOf(getTotal(timeUnit)), DateUtil.getShotName(timeUnit));
    }

    public void start(String str) throws IllegalStateException {
        if (this.currentTaskName != null) {
            throw new IllegalStateException("Can't start StopWatch: it's already running");
        }
        this.currentTaskName = str;
        this.startTimeNanos = System.nanoTime();
    }

    public StopWatch(String str, boolean z2) {
        this.id = str;
        if (z2) {
            this.taskList = new ArrayList();
        }
    }
}
