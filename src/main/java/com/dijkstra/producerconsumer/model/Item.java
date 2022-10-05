package com.dijkstra.producerconsumer.model;

import com.dijkstra.producerconsumer.Resource;

/**
 * Represents the model of a buffer item
 */
public enum Item {
  KIWI(Resource.KIWI), GRAPE(Resource.GRAPE), TANGERINE(Resource.TANGERINE), WATERMELON(Resource.WATERMELON), OGRE(Resource.OGRE);

  public NodeFactory node;

  Item(NodeFactory node) {
    this.node = node;
  }
}
