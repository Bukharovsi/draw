Error handling

CONSIDERATION

There are 2 basic approach for error handling
1. We introduce a common interface or class that contains user-friendly message
2. All the errors are independent, but Application knows how to handle each of them

The first approach requires us to wrap all the exceptions in user-friendly form (including 3rd party) and also 
might case a problem for internalization. But, application should not know about all the possible exceptions.
The second approach requires application to know about all the errors that must be handled.

CHOSEN APPROACH

There is no requirements for internalization, no 3rd party libraries, therefore we can take 1st approach.
But real application requires 2nd. 