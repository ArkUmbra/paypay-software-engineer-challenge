package com.arkumbra.paypay;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ImmutableFifoQueueDeepTest {

  private Queue queue;

  @Before
  public void setUp() {
    this.queue = new ImmutableFifoQueue();
  }

  @Test
  public void testQueueContainsCannotBeModifiedByAlteringObjectsInside() {
    String initialVal = "Hello";
    MyMutable myMutable = new MyMutable(initialVal);

    Queue<MyMutable> queue = new ImmutableFifoQueue<MyMutable>();
    queue = queue.enQueue(myMutable);

    String updatedValue = "World";
    myMutable.setMutValue(updatedValue);

    MyMutable head = queue.head();

    assertEquals(initialVal, head.getMutValue());
  }

}

/** A simple object which can have its contents changed.
 * Use this to check whether the contents of a queue can be changd after
 * an item is added to it.
 */
class MyMutable {
  private String mutValue;

  public MyMutable(String mutValue) {
    this.mutValue = mutValue;
  }

  public String getMutValue() {
    return mutValue;
  }

  public void setMutValue(String mutValue) {
    this.mutValue = mutValue;
  }
}
