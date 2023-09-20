package test;

import java.lang.management.ManagementFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Monitoring {
    public Monitoring() {}

    public static Monitoring_Data serverInfos() {
        try {
            // Verbindung zur MBean-Server-Plattform herstellen
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

            // ObjectName für den Tomcat-GlobalRequestProcessor erstellen
            ObjectName requestProcessorName = new ObjectName("Catalina:type=GlobalRequestProcessor,name=\"http-nio-8080\"");
            //ObjectName name = new ObjectName("Catalina:type=Memory");

         // Metriken auslesen
            int requestCount = (int) mBeanServer.getAttribute(requestProcessorName, "requestCount");
            int errorCount = (int) mBeanServer.getAttribute(requestProcessorName, "errorCount");
            long bytesReceived = (long) mBeanServer.getAttribute(requestProcessorName, "bytesReceived");
            long bytesSent = (long) mBeanServer.getAttribute(requestProcessorName, "bytesSent");
            long processingTime = (long) mBeanServer.getAttribute(requestProcessorName, "processingTime");
            
            
            System.out.println("--------TEST____01------------");
            /*
            
            long maxMemory = (long) mBeanServer.getAttribute(name, "maxMemory");
            long totalMemory = (long) mBeanServer.getAttribute(name, "totalMemory");
            long freeMemory = (long) mBeanServer.getAttribute(name, "freeMemory");

            // Ausgabe der Speicherstatistiken
            System.out.println("Max Memory: " + maxMemory / (1024 * 1024) + " MB");
            System.out.println("Total Memory: " + totalMemory / (1024 * 1024) + " MB");
            System.out.println("Free Memory: " + freeMemory / (1024 * 1024) + " MB");
            
            System.out.println("--------TEST____02------------");
            */
            
            

            Monitoring_Data data = new Monitoring_Data(
                requestCount,
                errorCount,
                bytesReceived,
                bytesSent,
                processingTime
            );

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}