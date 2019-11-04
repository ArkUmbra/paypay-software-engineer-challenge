package com.arkumbra.paypay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/***
 * Was thinking about copying the whole collection for each new array, but would become pretty
 * expensive as queue gets bigger...
 *
 * When trying to think of a way to copy efficiently when adding or removing a single element,
 * it occurred to be I might be able to just map a single 'T' element to each queue instance,
 * but pass the parent queue in to the new one. In that way you can just move a chain of queues
 * around.
 *
 * This would be fast for adding an element, but would be slow for removing an element, as you'd
 * have to navigate up to the head of the queue to remove it. Because of this, I attempted to have
 * all elements in the queue have a reference to the head, so that removal of the header element can be fast.
 *
 * @param <T> Type of item to be queued
 */
public class ImmutableFifoQueue<T> implements Queue<T> {

  // TODO make final where possible


  private final ImmutableFifoQueue<T> head;
  private final ImmutableFifoQueue<T> elementInFront;
  private ImmutableFifoQueue<T> elementBehind;

  private T element;

  public ImmutableFifoQueue() {
    this.head = null;
    this.elementInFront = null;
  }

  private ImmutableFifoQueue(ImmutableFifoQueue<T> head, ImmutableFifoQueue<T> queueInFront, T element) {
    this.head = head;
    this.elementInFront = queueInFront;
    this.element = element;
  }

//  private ImmutableFifoQueue(ImmutableFifoQueue<T> head, ImmutableFifoQueue<T> queueInFront, T element) {
//    this.head = head;
//    this.elementInFront = queue;
//    this.element = element;
//  }

  private ImmutableFifoQueue<T> createNewEmptyQueue() {
    return new ImmutableFifoQueue<T>(null, null, null);
  }

  private ImmutableFifoQueue<T> createNewQueueFromSingleElement(T element) {
    return new ImmutableFifoQueue<T>(null, null, element);
  }


  /**
   * Add the element at the end of the immutable queue, and returns the new queue.
   */
  @Override
  public Queue<T> enQueue(T newElement) {

    if (element == null) {
      // If this is an empty head node, then just make a new queue without reference to this
      return createNewQueueFromSingleElement(newElement);

    } else {
      ImmutableFifoQueue<T> backOfQueue = new ImmutableFifoQueue<T>(getHeadOfQueue(), this, newElement);
      this.elementBehind = backOfQueue;
      return backOfQueue;
    }
  }

  /**
   * Removes the element at the beginning of the immutable queue, and returns the new queue.
   */
  @Override
  public Queue<T> deQueue() {
    if (isEmpty()) {
      throw new IndexOutOfBoundsException("Cannot deque an empty queue");

    } else {
      if (isHead()) {
        // for non-empty head, just create a single node queue without an element
        return createNewEmptyQueue();

      } else {
        // get element behind the head of the queue
        ImmutableFifoQueue<T> newHead = getHeadOfQueue().elementBehind;

        // TODO double check this works as expected
        ImmutableFifoQueue<T> newEndOfQueue = new ImmutableFifoQueue<>(newHead, elementInFront, element);

        return newEndOfQueue;
      }
    }

  }

  @Override
  public T head() {
    return getHeadOfQueue().element;
  }

  @Override
  public boolean isEmpty() {
    // If this object has no reference to the head, then it IS the head
    // therefore if there is no element here, then the whole queue is empty
    //return elementInFront == null && elementBehind == null;
    return isHead() && (this.element == null);
  }

  private boolean isHead() {
    return head == null;
  }

  private ImmutableFifoQueue<T> getHeadOfQueue() {
    if (isHead()) {
      return this;
    } else {
      return this.head;
    }
  }

}
