module com.dijkstra.producerconsumer {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.dijkstra.producerconsumer to javafx.fxml;
    exports com.dijkstra.producerconsumer;
    exports com.dijkstra.producerconsumer.model.buffer;
    opens com.dijkstra.producerconsumer.model.buffer to javafx.fxml;
    exports com.dijkstra.producerconsumer.model;
    opens com.dijkstra.producerconsumer.model to javafx.fxml;
    exports com.dijkstra.producerconsumer.model.workers;
    opens com.dijkstra.producerconsumer.model.workers to javafx.fxml;
}