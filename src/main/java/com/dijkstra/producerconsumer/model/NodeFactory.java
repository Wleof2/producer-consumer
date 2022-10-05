package com.dijkstra.producerconsumer.model;

import javafx.scene.Node;
import com.dijkstra.producerconsumer.Resource;

/**
 * Provides a model for creating nodes
 */
public class NodeFactory {
  private String name;

  public NodeFactory(String name) {
    this.name = name;
  }

  /**
   * Load and instantiate a no from a fxml file
   *
   * @return a node
   */
  public Node getNode() {
    return Resource.loadNode(name);
  }
}
