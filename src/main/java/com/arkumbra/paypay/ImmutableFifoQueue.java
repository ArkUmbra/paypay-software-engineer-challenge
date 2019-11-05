package com.arkumbra.paypay;

/***
 * Was thinking about storing a collection or array of elements in the queue, then copying the
 * whole collection for each new instance of Queue, but would become prettyexpensive as queue
 * gets bigger...
 *
 * When trying to think of a way to copy efficiently when adding or removing a single element,
 * it occurred to be I might be able to just map a single 'T' element to each queue instance,
 * but pass the parent queue in to the new one. In that way you can just move a chain of queues
 * around.
 *
 * This would be fast for adding an element, but would be slow for removing an element, as you'd
 * have to navigate up to the head of the queue to remove it. Because of this, I attempted to have
 * all elements in the queue have a reference to the head, so that removal of the header element
 * can be fast.
 *
 * Ended up looking similar to singly-linked list
 *
 * @param <T> Type of item to be queued
 */
public class ImmutableFifoQueue<T> implements Queue<T> {

  private Stack<T> dequeingStack;
  private Stack<T> enqueingStack;
  private boolean isEmptyQueue;

  public ImmutableFifoQueue() {
    this.dequeingStack = new Stack<T>();
    this.enqueingStack = new Stack<T>();
    this.isEmptyQueue = true;
  }

  public ImmutableFifoQueue(Stack<T> dequeingStack, Stack<T> enqueingStack) {
    this.dequeingStack = dequeingStack;
    this.enqueingStack = enqueingStack;
    this.isEmptyQueue = false;
  }

  @Override
  public Queue<T> enQueue(T t) {
//    if (dequeingStack.element() == null) {
//    if (enqueingStack.isEmptyStack() && dequeingStack.isEmptyStack())  {

    if (this.isEmptyQueue)  {
      // so that first dequeue doesn't fail
//      return new ImmutableFifoQueue<T>(dequeingStack.push(t), dequeingStack.push(t));
      return new ImmutableFifoQueue<T>(new Stack<T>().push(t), new Stack<>());
    }

    return new ImmutableFifoQueue<T>(dequeingStack, enqueingStack.push(t));
  }

  @Override
  public Queue<T> deQueue() {
    if (this.isEmptyQueue) {
      throw new IndexOutOfBoundsException("Cant deque as no elements");
    }


    Stack<T> newDequeued = dequeingStack.pop(); // clear the top element;

    if (! newDequeued.isEmptyStack()) {
      return new ImmutableFifoQueue<T>(newDequeued, enqueingStack);
    }

    if (enqueingStack.isEmptyStack()) {
      return new ImmutableFifoQueue<T>();
    }

    return new ImmutableFifoQueue<>(enqueingStack.flip(), new Stack<>());


//    if (newDequeued == null) {
//      // this means nothing more in the queue (dequeued everything), so just
//      // make a new _empty_ queue
//      return new ImmutableFifoQueue<T>();
//
//    } else {
//      return new ImmutableFifoQueue<T>(newDequeued, enqueingStack);
//    }

  }

  @Override
  public T head() {
    if (this.isEmptyQueue) {
      throw new IndexOutOfBoundsException("Cant get head as no elements");
    }

//    if (enqueingStack.isEmptyStack()) {
//      this.
//    }

    return dequeingStack.element();
  }

  @Override
  public boolean isEmpty() {
//    return enqueingStack.isTempStack();
    return isEmptyQueue;
  }
}

class Stack<T> {
  private final Stack<T> below;

  private T element;

  private boolean isEmptyStack;

  public Stack() {
    this.below = null;
    this.element = null;
    this.isEmptyStack = true;
  }


  private Stack(Stack<T> below, T element) {
    this.below = below;
    this.element = element;
    this.isEmptyStack = false;
  }

  private Stack<T> createNewEmptyQueue() {
    return new Stack<T>();
  }

  public boolean isEmptyStack() {
    return isEmptyStack;
  }

  //
//  private Stack<T> createNewQueueFromSingleElement(T element) {
//    return new Stack<T>(null, null, element);
//  }
//
  public Stack<T> push(T newElement) {
    return new Stack<T>(this, newElement);
  }

  public Stack<T> pop() {
    return below;
  }

  /**
   * Removes the element at the beginning of the immutable queue, and returns the new queue.
   */
//  public Queue<T> deQueue() {
//    if (isEmpty()) {
//      throw new IndexOutOfBoundsException("Cannot deque an empty queue");
//
//    } else {
//      if (isHead()) {
//        // for non-empty head, just 'delete' this element
//        // i.e  create a single node queue without an element
//        return createNewEmptyQueue();
//
//      } else {
//        // get element behind the head of the queue
//        ImmutableFifoQueue<T> newHead = getElementBehindHead();
//
//
//        // replace this element itself
//        ImmutableFifoQueue<T> newEndOfQueue = new ImmutableFifoQueue<>(newHead, this.elementInfront, element);
//        //ImmutableFifoQueue<T> newEndOfQueue = new ImmutableFifoQueue<>(newHead, elementInFront, element);
//
//        return newEndOfQueue;
//      }
//    }
//  }

//  private ImmutableFifoQueue<T> getElementBehindHead() {
//    if (isElementDirectlyBehindHead()) {
//      return this;
//    } else {
//      return elementInfront.getElementBehindHead();
//    }
//  }
//
//  private boolean isElementDirectlyBehindHead() {
//    return elementInfront.isHead();
//  }


  public Stack<T> flip() {
    Stack<T> flipped = new Stack<T>();
    flipped = flipped.push(this.element);

    Stack<T> current = this.pop();
    while (! current.isEmptyStack) {
      flipped = flipped.push(current.element);
      current = current.pop();
    }

    return flipped;
  }

  //@Override
  public T element() {
    return element;
  }

//  @Override
//  public boolean isEmpty() {
//    // If this object has no reference to the head, then it IS the head
//    // therefore if there is no element here, then the whole queue is empty
//    return isHead() && (this.element == null);
//  }
//
//  private boolean isHead() {
//    return head == null;
//  }
//
//  private ImmutableFifoQueue<T> getHeadOfQueue() {
//    if (isHead()) {
//      return this;
//    } else {
//      return this.head;
//    }
//  }

}
