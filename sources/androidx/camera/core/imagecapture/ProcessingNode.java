package androidx.camera.core.imagecapture;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.annotation.WorkerThread;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.imagecapture.Bitmap2JpegBytes;
import androidx.camera.core.imagecapture.Image2JpegBytes;
import androidx.camera.core.imagecapture.JpegBytes2Disk;
import androidx.camera.core.imagecapture.ProcessingNode;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.processing.Edge;
import androidx.camera.core.processing.Node;
import androidx.camera.core.processing.Operation;
import androidx.camera.core.processing.Packet;
import androidx.core.util.Consumer;
import com.google.auto.value.AutoValue;
import java.util.Objects;
import java.util.concurrent.Executor;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public class ProcessingNode implements Node<In, Void> {
    private Operation<Bitmap2JpegBytes.In, Packet<byte[]>> mBitmap2JpegBytes;

    @NonNull
    private final Executor mBlockingExecutor;
    private Operation<Image2JpegBytes.In, Packet<byte[]>> mImage2JpegBytes;
    private Operation<InputPacket, Packet<ImageProxy>> mInput2Packet;
    private Operation<Packet<byte[]>, Packet<Bitmap>> mJpegBytes2CroppedBitmap;
    private Operation<JpegBytes2Disk.In, ImageCapture.OutputFileResults> mJpegBytes2Disk;
    private Operation<Packet<byte[]>, Packet<ImageProxy>> mJpegBytes2Image;
    private Operation<Packet<ImageProxy>, ImageProxy> mJpegImage2Result;

    @AutoValue
    public static abstract class In {
        public static In of(int i2) {
            return new AutoValue_ProcessingNode_In(new Edge(), i2);
        }

        public abstract Edge<InputPacket> getEdge();

        public abstract int getFormat();
    }

    @AutoValue
    public static abstract class InputPacket {
        public static InputPacket of(@NonNull ProcessingRequest processingRequest, @NonNull ImageProxy imageProxy) {
            return new AutoValue_ProcessingNode_InputPacket(processingRequest, imageProxy);
        }

        @NonNull
        public abstract ImageProxy getImageProxy();

        @NonNull
        public abstract ProcessingRequest getProcessingRequest();
    }

    public ProcessingNode(@NonNull Executor executor) {
        this.mBlockingExecutor = executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$transform$1(final InputPacket inputPacket) {
        if (inputPacket.getProcessingRequest().isAborted()) {
            return;
        }
        this.mBlockingExecutor.execute(new Runnable() { // from class: androidx.camera.core.imagecapture.e
            @Override // java.lang.Runnable
            public final void run() {
                this.f1525c.lambda$transform$0(inputPacket);
            }
        });
    }

    private static void sendError(@NonNull final ProcessingRequest processingRequest, @NonNull final ImageCaptureException imageCaptureException) {
        CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.d
            @Override // java.lang.Runnable
            public final void run() {
                processingRequest.onProcessFailure(imageCaptureException);
            }
        });
    }

    @VisibleForTesting
    public void injectJpegBytes2CroppedBitmapForTesting(@NonNull Operation<Packet<byte[]>, Packet<Bitmap>> operation) {
        this.mJpegBytes2CroppedBitmap = operation;
    }

    @NonNull
    @WorkerThread
    public ImageProxy processInMemoryCapture(@NonNull InputPacket inputPacket) throws ImageCaptureException {
        ProcessingRequest processingRequest = inputPacket.getProcessingRequest();
        Packet<ImageProxy> packetApply = this.mInput2Packet.apply(inputPacket);
        if (packetApply.getFormat() == 35) {
            packetApply = this.mJpegBytes2Image.apply(this.mImage2JpegBytes.apply(Image2JpegBytes.In.of(packetApply, processingRequest.getJpegQuality())));
        }
        return this.mJpegImage2Result.apply(packetApply);
    }

    @WorkerThread
    /* renamed from: processInputPacket, reason: merged with bridge method [inline-methods] */
    public void lambda$transform$0(@NonNull InputPacket inputPacket) {
        final ProcessingRequest processingRequest = inputPacket.getProcessingRequest();
        try {
            if (inputPacket.getProcessingRequest().isInMemoryCapture()) {
                final ImageProxy imageProxyProcessInMemoryCapture = processInMemoryCapture(inputPacket);
                CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.f
                    @Override // java.lang.Runnable
                    public final void run() {
                        processingRequest.onFinalResult(imageProxyProcessInMemoryCapture);
                    }
                });
            } else {
                final ImageCapture.OutputFileResults outputFileResultsProcessOnDiskCapture = processOnDiskCapture(inputPacket);
                CameraXExecutors.mainThreadExecutor().execute(new Runnable() { // from class: androidx.camera.core.imagecapture.g
                    @Override // java.lang.Runnable
                    public final void run() {
                        processingRequest.onFinalResult(outputFileResultsProcessOnDiskCapture);
                    }
                });
            }
        } catch (ImageCaptureException e2) {
            sendError(processingRequest, e2);
        } catch (RuntimeException e3) {
            sendError(processingRequest, new ImageCaptureException(0, "Processing failed.", e3));
        }
    }

    @NonNull
    @WorkerThread
    public ImageCapture.OutputFileResults processOnDiskCapture(@NonNull InputPacket inputPacket) throws ImageCaptureException {
        ProcessingRequest processingRequest = inputPacket.getProcessingRequest();
        Packet<byte[]> packetApply = this.mImage2JpegBytes.apply(Image2JpegBytes.In.of(this.mInput2Packet.apply(inputPacket), processingRequest.getJpegQuality()));
        if (packetApply.hasCropping()) {
            packetApply = this.mBitmap2JpegBytes.apply(Bitmap2JpegBytes.In.of(this.mJpegBytes2CroppedBitmap.apply(packetApply), processingRequest.getJpegQuality()));
        }
        Operation<JpegBytes2Disk.In, ImageCapture.OutputFileResults> operation = this.mJpegBytes2Disk;
        ImageCapture.OutputFileOptions outputFileOptions = processingRequest.getOutputFileOptions();
        Objects.requireNonNull(outputFileOptions);
        return operation.apply(JpegBytes2Disk.In.of(packetApply, outputFileOptions));
    }

    @Override // androidx.camera.core.processing.Node
    public void release() {
    }

    @Override // androidx.camera.core.processing.Node
    @NonNull
    public Void transform(@NonNull In in) {
        in.getEdge().setListener(new Consumer() { // from class: androidx.camera.core.imagecapture.c
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                this.f1522c.lambda$transform$1((ProcessingNode.InputPacket) obj);
            }
        });
        this.mInput2Packet = new ProcessingInput2Packet();
        this.mImage2JpegBytes = new Image2JpegBytes();
        this.mJpegBytes2CroppedBitmap = new JpegBytes2CroppedBitmap();
        this.mBitmap2JpegBytes = new Bitmap2JpegBytes();
        this.mJpegBytes2Disk = new JpegBytes2Disk();
        this.mJpegImage2Result = new JpegImage2Result();
        if (in.getFormat() != 35) {
            return null;
        }
        this.mJpegBytes2Image = new JpegBytes2Image();
        return null;
    }
}
