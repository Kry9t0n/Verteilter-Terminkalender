package client;

public class Monitoring_Data 
{
    private int requestCount;
    private int errorCount;
    private long bytesReceived;
    private long bytesSent;
    private long processingTime;

    public Monitoring_Data(int requestCount, int errorCount, long bytesReceived, long bytesSent, long processingTime) {
        this.requestCount = requestCount;
        this.errorCount = errorCount;
        this.bytesReceived = bytesReceived;
        this.bytesSent = bytesSent;
        this.processingTime = processingTime;
    }
    
    public Monitoring_Data() {}

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public long getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(long bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public long getBytesSent() {
        return bytesSent;
    }

    public void setBytesSent(long bytesSent) {
        this.bytesSent = bytesSent;
    }

    public long getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(long processingTime) {
        this.processingTime = processingTime;
    }
}
