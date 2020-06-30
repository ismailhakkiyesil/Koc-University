/*
THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES. 
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT. NAME: Ismail Hakki Yesil
*/
package supsimjava;
public class Cashier {

  public static int totalServiceTime = 0, totalCustomer = 0;
  public Customer currentCustomer;
  public boolean serviceFinished = false;
  int currentServiceTime = 0;

  public Cashier() {}
  // Assigning wait time to customer in main loop
  // We are calling it once per customer or we will get random values for each of them
  public double GetAverageServiceTime() {
    return totalServiceTime / (double) totalCustomer;
  }
  public void Serve(Customer customer) {
    currentServiceTime++;
    if (customer.serviceTime > currentServiceTime
) {
      System.out.println("Serving..." + " Customer No: " + customer.id +
            " Remaining Service Time: " + (customer.serviceTime - currentServiceTime - 1));
      serviceFinished = false;
    } else {
      // Finishing the service
      System.out.println("Serving Is Finished." + " Customer No:" + customer.id );
      serviceFinished = true;
      currentServiceTime = 0;
    }
  }
}

