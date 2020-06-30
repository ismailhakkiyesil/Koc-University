/*
THIS CODE IS MY OWN WORK. I DID NOT SEARCH FOR A SOLUTION, or I DID NOT CONSULT TO ANY  PROGRAM WRITTEN BY OTHER STUDENTS or DID NOT COPY ANY PROGRAM FROM OTHER SOURCES.
I READ AND FOLLOWED THE GUIDELINE GIVEN IN THE PROGRAMMING ASSIGNMENT.
NAME: ISMAIL HAKKI YESIL
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include "../queue/queue.h"

struct customer {
    int arrivalTime;
    int serviceTime;
};


int main()
{
	time_t t;
	srand((unsigned)time(&t));

	int maxArrivalTime;
	int maxServiceTime;
	int simulationTime;

	//Taking inputs from user
	printf("Enter max simulation time, max_arrival_time, max_service_time:\nSimulation parameters: ");
	scanf("%d %d %d", &simulationTime, &maxArrivalTime, &maxServiceTime);
	printf("---------------\n");

	QueueNodePtr headPtr=NULL; // Initializing headPtr
	QueueNodePtr tailPtr=NULL; // Initializing tailPtr

	 //initializing necessary variables
	int maximumCustomersInTheLine=0;
	int customersInTheLine=0;
	double maximumCustomersWaitTime=0;
	double allCustomersWait=0;
	double allServiceTime=0;
	double allArrivalTimeInterval=0;
	int customersServed = 0;

	//Arranging arrival and service time for the next customers
	int nextIntervalOfArrival;
	int nextArrivalTime=1+rand()%maxArrivalTime;
	allArrivalTimeInterval+=nextArrivalTime;
	int nextServiceTime=1+rand()%maxServiceTime;

	int availableCashier=0;
	int customerAmount=0;
	int flag=0;

	for(int currentTime=1;currentTime<=simulationTime||
	!isEmpty(headPtr)||availableCashier>=0;currentTime++,availableCashier--){
	    if(currentTime==(simulationTime+1)){
	        printf("   %d: Max Simulation Time Reached - servicing remaining customers\n", currentTime);
	    }
		//Scenario of coinciding new customer with the current customer
		if(currentTime <= simulationTime+1 && currentTime == nextArrivalTime){
		    printf("   %d: Customer %d arrived\n",currentTime,++customerAmount);
		    //Memory allocations for customer
			struct customer *c = (struct customer *)  malloc(sizeof(struct customer));
			//Assigning next arrival and next service time data to customer
			c->arrivalTime = nextArrivalTime;
			c->serviceTime = nextServiceTime;
			//Inserting new customer into the queue
			enqueue(&headPtr, &tailPtr, c);
			//Increasing the total amount of customers in the line
			customersInTheLine++;
			/*
			If the amount of customers in the line is greater than
			the amount of maximum customers in the line
			updating the amount of maximum customers in the line
			*/
			if (maximumCustomersInTheLine<customersInTheLine){
				maximumCustomersInTheLine=customersInTheLine;
			}
			//Arranging arrival and service time for the next customers
			nextIntervalOfArrival=1+rand()%maxArrivalTime;
			nextArrivalTime=currentTime+nextIntervalOfArrival;
			nextServiceTime=1+rand()%maxServiceTime;
			allArrivalTimeInterval+=nextIntervalOfArrival;
		}
		//If cashier is available
		if(availableCashier<= 0){
		    if(customersServed!=0&&flag==0){
		        printf("   %d: Service completed for customer %d\n", currentTime, customersServed);
		        flag=1;
			}
		    //If line is not empty
			if (!isEmpty(headPtr))
			{
			    if(customersServed != 0){
			        printf("   %d: Service starts for customer %d\n", currentTime, customersServed+1);
			        flag=0;
			    }
			    //Deuquueing the customer from queue
				struct customer *c = (struct customer *) dequeue(&headPtr, &tailPtr);
				//Decreasing to the total amount of customers in the line
				customersInTheLine--;
				//Servicing to the customer
				availableCashier = c->serviceTime;
				//Calculating the waiting time of a customer
				int customerWaitTime = currentTime - c->arrivalTime;
				/*
				If customer's waiting time is greater than the customer's maximum waiting time
				updating the maximum waiting time of the customer
				*/
				if (maximumCustomersWaitTime<customerWaitTime){
					maximumCustomersWaitTime=customerWaitTime;
				}
				//Increasing waiting time of customer in all customers waiting time
				allCustomersWait+=customerWaitTime;
				//Increasing service time of customer in all serving time of customers
				allServiceTime+=c->serviceTime;
				//Increasing to the total amount of customer served
				++customersServed;
			}
		}
	}

	printf("\n-------------------------------------------\n");
	printf("           Simulation Statistics\n");
	printf("-------------------------------------------\n");

	//Calculating averages of statistics
	double averageAllCustomerWaitTime=allCustomersWait/customersServed;
	double averageAllCustomerServingTime=allServiceTime/customersServed;
	double averageAllCustomerArrivalInterval=allArrivalTimeInterval/customersServed;

	//Printing the statistics
	printf("Number of Customers: %d\n", customerAmount);
	printf("Average Wait Time: %f\n", averageAllCustomerWaitTime);
	printf("Average Service Time: %f\n", averageAllCustomerServingTime);
	printf("Maximum Wait Time: %f\n", maximumCustomersWaitTime);
	printf("Maximum Queue Length: %d\n", maximumCustomersInTheLine);
	printf("Average Arrival Interval Time of Customers: %f\n", averageAllCustomerArrivalInterval);
    printf("-------------------------------------------\n");

	return EXIT_SUCCESS;
}
