// Adapted from Deitel How to Program C
// Fig. 12.13: fig12_13.c
// Queue Data Structure Implementation

#include <stdio.h>
#include <stdlib.h>
#include "queue.h"

// insert a node at queue tail
void enqueue(QueueNodePtr *headPtr, QueueNodePtr *tailPtr, void *value)
{
   QueueNodePtr newPtr = malloc(sizeof(QueueNode));

   if (newPtr != NULL) { // is space available
      newPtr->data = value;
      newPtr->nextPtr = NULL;

      // if empty, insert node at head
      if (isEmpty(*headPtr)) {
         *headPtr = newPtr;
      }
      else {
         (*tailPtr)->nextPtr = newPtr;
      }

      *tailPtr = newPtr;
   }
   else {
      printf("item not inserted. No memory available.\n");
   }
}

// remove node from queue head
void *dequeue(QueueNodePtr *headPtr, QueueNodePtr *tailPtr)
{
   void * value = (*headPtr)->data;
   QueueNodePtr tempPtr = *headPtr;
   *headPtr = (*headPtr)->nextPtr;

   // if queue is empty
   if (*headPtr == NULL) {
      *tailPtr = NULL;
   }

   free(tempPtr);
   return value;
}

// return 1 if the queue is empty, 0 otherwise
int isEmpty(QueueNodePtr headPtr)
{
   return headPtr == NULL;
}
