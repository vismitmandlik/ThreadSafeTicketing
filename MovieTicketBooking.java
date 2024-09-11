import java.util.concurrent.locks.ReentrantLock;

class Movie {
    // private String name = new String("Kalki");
    public static int availableTickets = 10;
    ReentrantLock lock = new ReentrantLock();


    public void bookTickets (int numberOfTickets){
        lock.lock();
        try {
            if(availableTickets >= numberOfTickets){
                availableTickets -= numberOfTickets;
                System.out.println(numberOfTickets + " tickets booked for " + Thread.currentThread().getName() +". Remaining tickets: " + availableTickets);
            } else {
                System.out.println("Not enough tickets available for " + Thread.currentThread().getName() + " only " + availableTickets +" remainig...");
            }
        }finally {
            lock.unlock();
        }
    }
}

class BookTickets implements Runnable{
    private int tickets;
    private Movie movie;

    public BookTickets(Movie movie, int tickets){
        this.tickets = tickets;
        this.movie = movie;
    }

    @Override
    public void run() {
        movie.bookTickets(tickets);
    }
}

public class MovieTicketBooking {
    public static void main(String[] args) {
        Movie movie = new Movie();
        Thread user1 = new Thread(new BookTickets(movie,3), "Ashish");
        Thread user3 = new Thread(new BookTickets(movie, 4), "akash");
        Thread user5 = new Thread(new BookTickets(movie,3), "rohit");
        Thread user2 = new Thread(new BookTickets(movie, 2), "Vismit");

        user1.start();
        user3.start();
        user5.start();
        user2.start();
    }
}
