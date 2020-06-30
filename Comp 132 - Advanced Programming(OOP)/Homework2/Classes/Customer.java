/*
THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. NAME: Ismail Hakki Yesil
*/
package supsimjava;

public class Customer {

    public int id;
    public static int nextId = 0;
    public int arrivalTime, waitTime, serviceTime;
    
    // Constructor for the customer
    public Customer(int arrivalTime) {
        id = ++nextId;
        this.arrivalTime = arrivalTime;
    }
    public void increaseWaitTime() {
        waitTime++;
    }
    public boolean isArrived(int currentTime) {
        return currentTime >= arrivalTime;
    }
    public String toString() {
        return " ID: " + id + " ArrivalTime: " + arrivalTime + " Service Time: " + serviceTime;
    }
}
