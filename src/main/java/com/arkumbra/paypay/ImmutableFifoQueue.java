package com.arkumbra.paypay;

/***
 * Please see readme for explanation of behaviour and changes.
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
    if (this.isEmptyQueue)  {
      // so that first dequeue doesn't fail
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

    if (newDequeued.isEmptyStack()) {
      if (enqueingStack.isEmptyStack()) {
        // return a clean empty queue
        return new ImmutableFifoQueue<T>();
      } else {
        // dequeueing stack is empty, but enqueing is not. Flip the enqueuing stack around so that
        // it looks like a dequeuing stack - takes time.
        return new ImmutableFifoQueue<>(enqueingStack.flip(), new Stack<>());
      }

    } else {
      return new ImmutableFifoQueue<T>(newDequeued, enqueingStack);
    }
  }

  @Override
  public T head() {
    if (this.isEmptyQueue) {
      throw new IndexOutOfBoundsException("Cant get head as no elements");
    }

    return dequeingStack.element();
  }

  @Override
  public boolean isEmpty() {
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

  public boolean isEmptyStack() {
    return isEmptyStack;
  }

  public Stack<T> push(T newElement) {
    return new Stack<T>(this, newElement);
  }

  public Stack<T> pop() {
    return below;
  }

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

  public T element() {
    return element;
  }

}
