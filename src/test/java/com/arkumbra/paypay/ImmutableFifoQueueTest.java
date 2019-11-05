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

    System.out.println(queue.head());
    assertEquals(element1, queue.head());

    queue = queue.deQueue();
    System.out.println(queue.head());
    assertEquals(element2, queue.head());

    queue = queue.deQueue();
    System.out.println(queue.head());
    assertEquals(element3, queue.head());

    queue = queue.deQueue();
    System.out.println(queue.head());
    assertEquals(element4, queue.head());
  }

  @Test
  public void testForkingAQueueCantInterfereWithEachother() {
    String base = "base";
    Queue<String> baseOfQueue = new ImmutableFifoQueue<String>();
    baseOfQueue = baseOfQueue.enQueue(base);

    String fork1Val1 = "1";
    String fork1Val2 = "2";
    String fork1Val3 = "3";

    String fork2Val1 = "9";
    String fork2Val2 = "8";
    String fork2Val3 = "7";

    Queue<String> fork1 = baseOfQueue.enQueue(fork1Val1);
    Queue<String> fork2 = baseOfQueue.enQueue(fork2Val1);

    // still have the base value
    assertEquals(base, fork1.head());
    assertEquals(base, fork2.head());

    // remove base of fork1, fork2 should be unaffected
    fork1 = fork1.deQueue();
    assertEquals(fork1Val1, fork1.head());
    assertEquals(base, fork2.head());
  }

}
