import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

class Invoice {
    int invoiceId;
    double amount;
    double gstRate;
    double gst;
    double totalAmount;

    public Invoice(int invoiceId, double amount, double gstRate) {
        this.invoiceId = invoiceId;
        this.amount = amount;
        this.gstRate = gstRate;
    }

    public void calculateGST() {
        gst = (amount * gstRate) / 100;
        totalAmount = amount + gst;
    }

    @Override
    public String toString() {
        return String.format("Invoice ID: %d, Amount: %.2f, GST: %.2f, Total Amount: %.2f",
                invoiceId, amount, gst, totalAmount);
    }
}

class InvoiceProcessor implements Runnable {
    private BlockingQueue<Invoice> invoiceQueue;
    private static AtomicInteger processedCount = new AtomicInteger(0);

    public InvoiceProcessor(BlockingQueue<Invoice> invoiceQueue) {
        this.invoiceQueue = invoiceQueue;
    }

    @Override
    public void run() {
        while (!invoiceQueue.isEmpty()) {
            try {
                
                Invoice invoice = invoiceQueue.take();  
                invoice.calculateGST();  

                System.out.println(Thread.currentThread().getName() + " processed: " + invoice);

                processedCount.incrementAndGet();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  
                break;  
            }
        }
    }

    public static int getProcessedCount() {
        return processedCount.get();
    }
}

public class GSTCalculatorConcurrent {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Invoice> invoiceQueue = new LinkedBlockingQueue<>();

        invoiceQueue.add(new Invoice(101, 50000, 18));
        invoiceQueue.add(new Invoice(102, 20000, 12));
        invoiceQueue.add(new Invoice(103, 15000, 18));
        invoiceQueue.add(new Invoice(104, 10000, 5));
        invoiceQueue.add(new Invoice(105, 30000, 28));

        Thread processor1 = new Thread(new InvoiceProcessor(invoiceQueue), "Processor-1");
        Thread processor2 = new Thread(new InvoiceProcessor(invoiceQueue), "Processor-2");

        processor1.start();
        processor2.start();

        processor1.join();
        processor2.join();

        processor1.interrupt();
        processor2.interrupt();

        

        System.out.println("All invoices processed.");
    }
}
