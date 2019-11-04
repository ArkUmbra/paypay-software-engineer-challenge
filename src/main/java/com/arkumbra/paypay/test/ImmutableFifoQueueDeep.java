package com.arkumbra.paypay.test;

import com.arkumbra.paypay.Queue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImmutableFifoQueueDeep<T> implements Queue<T>, Serializable {

  private final List<T> elements;

  public ImmutableFifoQueueDeep() {
    this.elements = new ArrayList<T>();
//    new ArrayDeque<>()
  }

  @Override
  public Queue<T> enQueue(T t) {
    elements.add(t);
    // temp
    return deepCopy(this);
  }

  @Override
  public Queue<T> deQueue() {
    // temp
    return deepCopy(this);
  }

  @Override
  public T head() {
    if (elements.isEmpty()) {
      throw new IndexOutOfBoundsException("No elements available to take");
    } else {
      return elements.get(0);
    }
  }

  @Override
  public boolean isEmpty() {
    return elements.isEmpty();
  }

  /**
   *
   * @param input object to be deep copied
   * @return new Object with same content as input
   */
  @SuppressWarnings("unchecked")
  private <R> R deepCopy(R input) {
    try {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      new ObjectOutputStream(byteArrayOutputStream).writeObject(input);
      ByteArrayInputStream bytesInputStream = new ByteArrayInputStream(
          byteArrayOutputStream.toByteArray());
      return (R) new ObjectInputStream(bytesInputStream).readObject();

    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Failed to make deep copy");
      throw new RuntimeException(e);
    }
  }

}
