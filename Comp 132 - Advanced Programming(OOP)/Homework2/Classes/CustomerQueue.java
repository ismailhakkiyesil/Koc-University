/*
THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. NAME: Ismail Hakki Yesil
*/
package supsimjava;
import java.util.LinkedList;

public class CustomerQueue {
  public static LinkedList<Customer> customerQueue = new LinkedList<Customer>();
  public int maxWaitTime = 0, maxCustomerLine = 0, totalWaitTime = 0;

  public void enqueue(Customer customer) {
    customerQueue.add(customer);
  }
  public void dequeue() {
    maxWaitTime();
    CurrentCustomerNumber();
    if (!customerQueue.isEmpty()) {
      System.out.println(
          "Customer has been removed. ID: " + customerQueue.getFirst().id + " Queue size: " + (customerQueue.size() - 1));
      totalWaitTime += customerQueue.getFirst().waitTime;
      customerQueue.remove();
    }
  }
  public void increaseQueueWaitTime() {
    for (Customer customer : customerQueue) {
      if (customer == customerQueue.getFirst())
        continue;
      customer.increaseWaitTime();
    }
  }
  public void CurrentCustomerNumber() {
    int currentCustomerSize = customerQueue.size();
    if (currentCustomerSize > maxCustomerLine)
      maxCustomerLine = currentCustomerSize;
  }
  public void maxWaitTime() {
    for (Customer customer : customerQueue) {
      int currentWaitTime = customer.waitTime;
      if (maxWaitTime == 0)
        maxWaitTime = currentWaitTime;
      if (currentWaitTime > maxWaitTime)
        maxWaitTime = currentWaitTime;
    }
  }
  public double AverageWaitTime() {
    return totalWaitTime/(double)Cashier.totalCustomer;
  }
}