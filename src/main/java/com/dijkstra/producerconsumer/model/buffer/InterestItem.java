package com.dijkstra.producerconsumer.model.buffer;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import com.dijkstra.producerconsumer.Resource;
import com.dijkstra.producerconsumer.model.Style;
import com.dijkstra.producerconsumer.model.workers.WorkerStyle;

public class InterestItem {
  private Pane pane;

  private final int id;

  public InterestItem(Pane pane, int id) {
    this.id = id;
    this.pane = pane;
  }

  /**
   * @return the InterestItem pane
   */
  public Pane get() {
    return this.pane;
  }

  /**
   * create a InterestItem instance
   *
   * @param workerStyle
   * @param id
   * @return a InterestItem instance
   */
  public static InterestItem from(WorkerStyle workerStyle, int id) {
    Pane pane = (Pane) Resource.INTEREST_ITEM.getNode();

    Text text = (Text) pane.lookup("#id");
    Circle circle = (Circle) pane.lookup("#circle");

    text.setText(Integer.toString(id));
    circle.setStyle(Style.from("-fx-fill", workerStyle.value));

    return new InterestItem(pane, id);
  }
}
