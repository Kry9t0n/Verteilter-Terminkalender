package test;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.MemoryUsage;


public class Monitoring {
    public Monitoring() {}

    /**
     * Fragt folgende Werte beim Server ab:
     * 	-> requestCount,errorCount,bytesReceived, bytesSent, processingTime
     * 
     * @return Monitoring_Data Objekt mit oben genannten Werten
     */
    public static Monitoring_Data serverInfos() {
        try {
            // Verbindung zur MBean-Server-Plattform herstellen
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

            // ObjectName für den Tomcat-GlobalRequestProcessor erstellen
            ObjectName requestProcessorName = new ObjectName("Catalina:type=GlobalRequestProcessor,name=\"http-nio-8080\"");
            
            // Metriken auslesen
            int requestCount = (int) mBeanServer.getAttribute(requestProcessorName, "requestCount");
            int errorCount = (int) mBeanServer.getAttribute(requestProcessorName, "errorCount");
            long bytesReceived = (long) mBeanServer.getAttribute(requestProcessorName, "bytesReceived");
            long bytesSent = (long) mBeanServer.getAttribute(requestProcessorName, "bytesSent");
            long processingTime = (long) mBeanServer.getAttribute(requestProcessorName, "processingTime");
            
            //Daten in Monitoring_Data Objekt speichern
            Monitoring_Data data = new Monitoring_Data(
                requestCount,
                errorCount,
                bytesReceived,
                bytesSent,
                processingTime
            );
            
            System.out.println("---- Monitoring Daten vom Server abgefragt -----");
            
            
            /*
              NICHT FUNKTIONSFÄHIG:
	            //ObjectName name = new ObjectName("Catalina:type=Memory");
	            ObjectName memoryPoolName = new ObjectName("java.lang:type=MemoryPool,name=PS Old Gen");
	    
	            // Metrik "Usage" abrufen (Speichernutzung)
	            MemoryUsage memoryUsage = (MemoryUsage) mBeanServer.getAttribute(memoryPoolName, "Usage");
	
	            // Metriken auslesen
	            long usedMemory = memoryUsage.getUsed();
	            long maxMemory = memoryUsage.getMax();
	            
	            //Ausgabe der Werte
	            System.out.println("Genutzter Speicher: " + usedMemory + " Bytes");
	            System.out.println("Maximaler Speicher: " + maxMemory + " Bytes");
            */

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}