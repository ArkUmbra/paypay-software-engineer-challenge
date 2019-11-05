package com.arkumbra.paypay;

public interface Queue<T> {

  /**
   * Add element to end of queue
   * @param t element to append to new queue
   * @return new queue with element appended
   */
  Queue<T> enQueue(T t);

  /**
   * Removes the element at the beginning of the immutable queue, and returns the new queue.
   */
  Queue<T> deQueue();

  /**
   * Returns the head of the queue i.e. the oldest element
   * @return head of list
   */
  T head();

  boolean isEmpty();

}