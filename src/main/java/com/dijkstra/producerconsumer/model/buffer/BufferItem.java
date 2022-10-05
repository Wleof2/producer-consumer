package com.dijkstra.producerconsumer.model.buffer;

import com.dijkstra.producerconsumer.model.Item;
import com.dijkstra.producerconsumer.Resource;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;

public class BufferItem {
  private final int index;

  private FlowPane slot;

  private FlowPane interest;

  private Item currentItem;

  private InterestItem currentInterestItem;

  public BufferItem(int index, FlowPane slot, FlowPane interest) {
    this.slot = slot;
    this.index = index;
    this.interest = interest;
  }

  /**
   * set the current buffer item
   *
   * @param item
   */
  public void setSlotItem(Item item) {
    this.currentItem = item;
  }

  /**
   * set the current thread interested in the buffer
   *
   * @param interestItem
   */
  public void setInterest(InterestItem interestItem) {
    this.currentInterestItem = interestItem;
  }

  /**
   * unset the current thread interested in the buffer
   *
   * @param interestItem
   */
  public void removeInterest(InterestItem interestItem) {
    if (this.currentInterestItem.equals(interestItem)) {
      this.currentInterestItem = null;
    }
  }

  /**
   * refresh all view nodes
   */
  public void refresh() {
    this.slot.getChildren().clear();
    if (this.currentItem != null) {
      if (this.index == 0 || this.index == 5) {
        this.slot.getChildren().add(Resource.OGRE.getNode());
        throw new ArrayIndexOutOfBoundsException();
      } else {
        this.slot.getChildren().add(this.currentItem.node.getNode());
      }
    }

    this.interest.getChildren().clear();
    if (this.currentInterestItem != null) {
      this.interest.getChildren().add(currentInterestItem.get());
    }

    if (this.currentItem != null || this.currentInterestItem != null) {
      if (this.index == 0 || this.index == 5) {
        throw new ArrayIndexOutOfBoundsException();
      }
    }
  }

  /**
   * clear all view nodes
   */
  public void clear() {
    this.currentItem = null;
    this.currentInterestItem = null;
    this.slot.getChildren().clear();
    this.interest.getChildren().clear();
  }

  /**
   * create a BufferItem instance
   *
   * @param root
   * @param index
   * @return a BufferItem instance
   */
  public static BufferItem from(Pane root, int index) {
    FlowPane slot = (FlowPane) root.lookup("#buffer_slot_" + index);
    FlowPane interest = (FlowPane) root.lookup("#buffer_interest_" + index);
    return new BufferItem(index, slot, interest);
  }
}
