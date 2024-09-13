package com.gstcalculator;
import java.util.concurrent.atomic.AtomicInteger;

class InvoiceProcessor implements Runnable {
    private Invoice invoice;
    private static AtomicInteger processedCount = new AtomicInteger(0);

    public InvoiceProcessor(Invoice invoice) {
        this.invoice = invoice;
    }

    @Override
    public void run() {

            invoice.calculateGST();  

            System.out.println(Thread.currentThread().getName() + " processed: " + invoice);

            processedCount.incrementAndGet();

    }

    public static int getProcessedCount() {
        return processedCount.get();
    }
}