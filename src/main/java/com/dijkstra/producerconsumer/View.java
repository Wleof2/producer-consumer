
/*
 *    Copyright 2022 Wylton Leone<wleof2@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.dijkstra.producerconsumer;

import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.application.Application;

/**
 * provides the view and window for the application
 */
public class View extends Application {
  private final double WIDTH = 900;
  private final double HEIGHT = 620;

  private final Scene scene;

  /**
   * loads the template and initializes the scene
   */
  public View() {
    Parent view = (Parent) Resource.loadNode("view.fxml");
    this.scene = new Scene(view, WIDTH, HEIGHT);
  }

  /**
   * start and set settings for the stage
   *
   * @param stage
   */
  @Override
  public void start(Stage stage) {
    stage.setResizable(false);
    stage.setTitle("Producer - Consumer Problem");
    stage.setScene(scene);
    stage.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });
    stage.show();
  }

  /**
   * application entrypoint
   *
   * @param args
   */
  public static void main(String[] args) {
    launch();
  }
}