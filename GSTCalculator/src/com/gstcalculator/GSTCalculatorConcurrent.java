package com.gstcalculator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class GSTCalculatorConcurrent {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Invoice> invoiceQueue = new LinkedBlockingQueue<>();

        invoiceQueue.put(new Invoice(101, 50000, 18));
       
        invoiceQueue.put(new Invoice(102, 20000, 12));
        invoiceQueue.put(new Invoice(103, 15000, 18));
        invoiceQueue.put(new Invoice(104, 10000, 5));
        invoiceQueue.put(new Invoice(105, 30000, 28));

        int numProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numProcessors);

        Thread monitorThread = new Thread(() -> {
            try {
                while (true) {
                    Invoice invoice = invoiceQueue.take();  
                    executorService.submit(new InvoiceProcessor(invoice));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  
            }
        });

        monitorThread.start();


        invoiceQueue.put(new Invoice(155, 50000, 18));
        invoiceQueue.put(new Invoice(68, 50000, 18));


        // executorService.shutdown();

        System.out.println("All invoices processed. Total processed: " + InvoiceProcessor.getProcessedCount());
    }
}
