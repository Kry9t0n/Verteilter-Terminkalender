package vs.terminkalender.datatypes;

import vs.terminkalender.database.DB_Funktionen;

/**
 * @Autor Niklas Baldauf, Maik Girlinger, Niklas Balke, Justin Witsch
 * @version 1.0
 * @see DB_Funktionen
 */
public class Monitoring_Data 
{
	/**
	 * Atribute
	 */
    private int requestCount;
    private int errorCount;
    private long bytesReceived;
    private long bytesSent;
    private long processingTime;

    /**
     * Konstruktor mit allen Atributen
     * @param requestCount
     * @param errorCount
     * @param bytesReceived
     * @param bytesSent
     * @param processingTime
     */
    public Monitoring_Data(int requestCount, int errorCount, long bytesReceived, long bytesSent, long processingTime) {
        this.requestCount = requestCount;
        this.errorCount = errorCount;
        this.bytesReceived = bytesReceived;
        this.bytesSent = bytesSent;
        this.processingTime = processingTime;
    }
    
    /**
     * Standart Konstruktor 
     */
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
