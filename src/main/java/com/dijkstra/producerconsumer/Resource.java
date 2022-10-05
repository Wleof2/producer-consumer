package com.dijkstra.producerconsumer;

import java.net.URL;
import java.io.IOException;

import javafx.scene.Node;
import javafx.fxml.FXMLLoader;

import com.dijkstra.producerconsumer.model.NodeFactory;

/**
 * provides functions to load resources
 */
public class Resource {
  // Buffer Items
  public static NodeFactory TANGERINE = Resource.loadNodeFactory("items/1f34a.fxml");
  public static NodeFactory KIWI = Resource.loadNodeFactory("items/1f95d.fxml");
  public static NodeFactory GRAPE = Resource.loadNodeFactory("items/1f347.fxml");
  public static NodeFactory WATERMELON = Resource.loadNodeFactory("items/1f349.fxml");
  public static NodeFactory OGRE = Resource.loadNodeFactory("items/1f479.fxml");

  // Buffer and Thread Items
  public static NodeFactory INTEREST_ITEM = Resource.loadNodeFactory("interest/item.fxml");
  public static NodeFactory THREAD = Resource.loadNodeFactory("threads/thread.fxml");

  /**
   * creates an instance of a node factory from a fxml
   *
   * @param name
   * @return a NodeFactory instance
   */
  public static NodeFactory loadNodeFactory(String name) {
    return new NodeFactory(name);
  }

  /**
   * creates an instance of a node from a fxml
   *
   * @param name
   * @return a Node instance
   */
  public static Node loadNode(String name) {
    URL url = com.dijkstra.producerconsumer.View.class.getResource(name);

    try {
      FXMLLoader loader = new FXMLLoader(url);
      return loader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
