package com.arkumbra.paypay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ImmutableFifoQueueTest {


  @Test
  public void testEnqueueReturnsDifferentQueue() {
    String initialVal = "Hello";

    Queue<String> initialQueue = new ImmutableFifoQueue<String>();
    assertTrue(initialQueue.isEmpty());
    assertNull(initialQueue.head());

    Queue<String> updatedQueue = initialQueue.enQueue(initialVal);

    assertTrue(initialQueue.isEmpty());
    assertFalse(updatedQueue.isEmpty());
    assertEquals(initialVal, updatedQueue.head());
  }

  @Test
  public void testDequeDoesntModifyOriginalQueue() {
    String initialVal = "Hello";

    Queue<String> initialQueue = new ImmutableFifoQueue<String>();
    Queue<String> updatedQueue = initialQueue.enQueue(initialVal);
    Queue<String> dequeuedQueue = updatedQueue.deQueue();

    assertTrue(initialQueue.isEmpty());
    assertFalse(updatedQueue.isEmpty());
    assertTrue(dequeuedQueue.isEmpty());
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testCantDequeEmptyQueue() {
    Queue<String> initialQueue = new ImmutableFifoQueue<String>();
    assertTrue(initialQueue.isEmpty());

    initialQueue.deQueue();
  }

  @Test
  public void testDequesInFifoOrder() {
    String element1 = "1";
    String element2 = "2";
    String element3 = "3";
    String element4 = "4";

    Queue<String> queue = new ImmutableFifoQueue<String>();
    queue = queue.enQueue(element1);
    queue = queue.enQueue(element2);
    queue = queue.enQueue(element3);
    queue = queue.enQueue(element4);

    assertEquals(element1, queue.head());

    queue = queue.deQueue();
    assertEquals(element2, queue.head());

    queue = queue.deQueue();
    assertEquals(element3, queue.head());

    queue = queue.deQueue();
    assertEquals(element4, queue.head());
  }

}
