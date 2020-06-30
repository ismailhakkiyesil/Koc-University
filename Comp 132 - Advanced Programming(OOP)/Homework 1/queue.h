// Adapted from Deitel How to Program C
// Fig. 12.13: fig12_13.c
// Queue Data Structure header file

#ifndef QUEUE_H_
#define QUEUE_H_
// self-referential structure
struct queueNode {
  void *data; // define data as a void *;
   struct queueNode *nextPtr; // queueNode pointer
};

typedef struct queueNode QueueNode;
typedef QueueNode *QueueNodePtr;

// function prototypes

int isEmpty(QueueNodePtr headPtr);
void * dequeue(QueueNodePtr *headPtr, QueueNodePtr *tailPtr);
void enqueue(QueueNodePtr *headPtr, QueueNodePtr *tailPtr, void *value);


#endif /* QUEUE_H_ */
