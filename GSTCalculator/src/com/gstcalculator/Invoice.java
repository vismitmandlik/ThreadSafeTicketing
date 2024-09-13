package com.gstcalculator;

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

