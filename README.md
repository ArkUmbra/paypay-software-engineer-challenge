# Paypay Backend Engineer Challenge

## Immutable Queue

#### Challenge
Design an immutable queue in java based on the interface as described in 
https://github.com/ArkUmbra/paypay-software-engineer-challenge/blob/master/src/main/java/com/arkumbra/paypay/Queue.java


#### Solution

This went through many iterations before I arrived at the current solution.


For the first implementation, I was thinking about storing a collection or array of elements inside 
the queue implementation, then copying the whole collection for each new instance of Queue, but 
would become pretty expensive as queue gets bigger...


After that, I was trying to think of a way to copy the array efficiently when adding or removing a 
single element, it occurred to be I might be able to just map a single 'T' element to each queue 
instance (as you return a new queue instance for every 1 call to queue or deque), but pass the 
parent queue in to the new one. In that way you can just move a chain of queues around (as 
in commit __048b2e8__)

This would be fast for adding an element, but would be slow for removing an element, as you'd have 
to navigate up to the head of the queue to remove it. Because of this, I attempted to have all 
elements in the queue have a reference to the head, so that removal of the header element can be 
fast. To help with this, I tried to make a doubly-linked list style of implementation, so that for
deque you could jump to the head, and return the queue chain behind it. However, as evidenced in 
commit __74c6c95__ this introduced a problem where the queues weren't mutable as I was updating 
state in order to store the 'elementBehind' variable.

Finally, I realised that because I was easily able to do deque OR queue with my current 
implementation (but not both - because then I got the mutability problems), that I could pull my 
queue logic out into a stack and just use two of them - one for enque and another for deque. It took
some extra time to get this to actually work properly, and had to write some specific logic to get
the edge cases

#### Improvements

The most expensive operation in the code is probably the flip() function on the stack. I don't have
any ideas of how to improve this bit, but if there are performance gains to be found, you'd 
certainly find some by removing that.

Additionally, I had initially been mostly considering preventing changes INSIDE the queue, for 
complete immutable behaviour (as referenced by my first attempt - a relic of which can be found 
at _src/main/java/com/arkumbra/paypay/test/ImmutableFifoQueueDeep.java_ in commit __048b2e8__). I 
was thinking I could either create a json copy of the element being queued, or serialise it to a 
byte array. However, that had the problem of required a JSON parser lib, or mandating that only
objects implementing the 'Serializable' interface be allowed into the queue - both of which seemed 
to be against what the challenge was after. That being said, it's an avenue for stricter 
immutability at the cost of speed if so required.
 

## Design a Google Analytics-style system

See attached doc PayPayDesignQuestion.pdf