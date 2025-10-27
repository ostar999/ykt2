package org.apache.commons.compress.archivers.zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.compress.parallel.FileBasedScatterGatherBackingStore;
import org.apache.commons.compress.parallel.InputStreamSupplier;
import org.apache.commons.compress.parallel.ScatterGatherBackingStore;
import org.apache.commons.compress.parallel.ScatterGatherBackingStoreSupplier;

/* loaded from: classes9.dex */
public class ParallelScatterZipCreator {
    private final ScatterGatherBackingStoreSupplier backingStoreSupplier;
    private long compressionDoneAt;
    private final ExecutorService es;
    private final List<Future<Object>> futures;
    private long scatterDoneAt;
    private final long startedAt;
    private final List<ScatterZipOutputStream> streams;
    private final ThreadLocal<ScatterZipOutputStream> tlScatterStreams;

    public static class DefaultBackingStoreSupplier implements ScatterGatherBackingStoreSupplier {
        final AtomicInteger storeNum;

        private DefaultBackingStoreSupplier() {
            this.storeNum = new AtomicInteger(0);
        }

        @Override // org.apache.commons.compress.parallel.ScatterGatherBackingStoreSupplier
        public ScatterGatherBackingStore get() throws IOException {
            return new FileBasedScatterGatherBackingStore(File.createTempFile("parallelscatter", "n" + this.storeNum.incrementAndGet()));
        }
    }

    public ParallelScatterZipCreator() {
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ScatterZipOutputStream createDeferred(ScatterGatherBackingStoreSupplier scatterGatherBackingStoreSupplier) throws IOException {
        ScatterGatherBackingStore scatterGatherBackingStore = scatterGatherBackingStoreSupplier.get();
        return new ScatterZipOutputStream(scatterGatherBackingStore, StreamCompressor.create(-1, scatterGatherBackingStore));
    }

    public void addArchiveEntry(ZipArchiveEntry zipArchiveEntry, InputStreamSupplier inputStreamSupplier) {
        submit(createCallable(zipArchiveEntry, inputStreamSupplier));
    }

    public final Callable<Object> createCallable(ZipArchiveEntry zipArchiveEntry, InputStreamSupplier inputStreamSupplier) {
        if (zipArchiveEntry.getMethod() != -1) {
            final ZipArchiveEntryRequest zipArchiveEntryRequestCreateZipArchiveEntryRequest = ZipArchiveEntryRequest.createZipArchiveEntryRequest(zipArchiveEntry, inputStreamSupplier);
            return new Callable<Object>() { // from class: org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator.2
                @Override // java.util.concurrent.Callable
                public Object call() throws Exception {
                    ((ScatterZipOutputStream) ParallelScatterZipCreator.this.tlScatterStreams.get()).addArchiveEntry(zipArchiveEntryRequestCreateZipArchiveEntryRequest);
                    return null;
                }
            };
        }
        throw new IllegalArgumentException("Method must be set on zipArchiveEntry: " + zipArchiveEntry);
    }

    public ScatterStatistics getStatisticsMessage() {
        long j2 = this.compressionDoneAt;
        return new ScatterStatistics(j2 - this.startedAt, this.scatterDoneAt - j2);
    }

    public final void submit(Callable<Object> callable) {
        this.futures.add(this.es.submit(callable));
    }

    public void writeTo(ZipArchiveOutputStream zipArchiveOutputStream) throws ExecutionException, InterruptedException, IOException {
        Iterator<Future<Object>> it = this.futures.iterator();
        while (it.hasNext()) {
            it.next().get();
        }
        this.es.shutdown();
        this.es.awaitTermination(60000L, TimeUnit.SECONDS);
        this.compressionDoneAt = System.currentTimeMillis();
        for (ScatterZipOutputStream scatterZipOutputStream : this.streams) {
            scatterZipOutputStream.writeTo(zipArchiveOutputStream);
            scatterZipOutputStream.close();
        }
        this.scatterDoneAt = System.currentTimeMillis();
    }

    public ParallelScatterZipCreator(ExecutorService executorService) {
        this(executorService, new DefaultBackingStoreSupplier());
    }

    public ParallelScatterZipCreator(ExecutorService executorService, ScatterGatherBackingStoreSupplier scatterGatherBackingStoreSupplier) {
        this.streams = Collections.synchronizedList(new ArrayList());
        this.futures = new ArrayList();
        this.startedAt = System.currentTimeMillis();
        this.compressionDoneAt = 0L;
        this.tlScatterStreams = new ThreadLocal<ScatterZipOutputStream>() { // from class: org.apache.commons.compress.archivers.zip.ParallelScatterZipCreator.1
            @Override // java.lang.ThreadLocal
            public ScatterZipOutputStream initialValue() {
                try {
                    ParallelScatterZipCreator parallelScatterZipCreator = ParallelScatterZipCreator.this;
                    ScatterZipOutputStream scatterZipOutputStreamCreateDeferred = parallelScatterZipCreator.createDeferred(parallelScatterZipCreator.backingStoreSupplier);
                    ParallelScatterZipCreator.this.streams.add(scatterZipOutputStreamCreateDeferred);
                    return scatterZipOutputStreamCreateDeferred;
                } catch (IOException e2) {
                    throw new RuntimeException(e2);
                }
            }
        };
        this.backingStoreSupplier = scatterGatherBackingStoreSupplier;
        this.es = executorService;
    }
}
