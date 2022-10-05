package com.dijkstra.producerconsumer.model.workers;

import com.dijkstra.producerconsumer.Resource;
import com.dijkstra.producerconsumer.model.Style;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class ThreadItem {
  private Pane pane;

  private final int id;

  public ThreadItem(Pane pane, int id) {
    this.id = id;
    this.pane = pane;
  }

  public Pane getNode() {
    return this.pane;
  }

  /**
   * set a text that represents the thread state
   *
   * @param state
   */
  public void setState(Thread.State state) {
    String text;

    switch (state) {
      default:
      case RUNNABLE:
        text = "RUNNABLE";
        break;
      case TIMED_WAITING:
        text = "SLEEPING";
        break;
      case WAITING:
        text = "WAITING";
        break;
    }

    Text statusNode = (Text) pane.lookup("#status");
    statusNode.setText(text);
  }

  /**
   * creates a ThreadItem instance
   *
   * @param workerStyle
   * @param id
   * @return a ThreadItem instance
   */
  public static ThreadItem from(WorkerStyle workerStyle, int id) {
    Pane pane = (Pane) Resource.THREAD.getNode();

    Text idNode = (Text) pane.lookup("#id");
    Text statusNode = (Text) pane.lookup("#status");
    Circle circleNode = (Circle) pane.lookup("#circle");

    statusNode.setText("WAITING");
    idNode.setText(Integer.toString(id));
    circleNode.setStyle(Style.from("-fx-fill", workerStyle.value));

    return new ThreadItem(pane, id);
  }
}
