package com.dijkstra.producerconsumer.model.workers;

/**
 * represents the style of a worker
 */
public enum WorkerStyle {
    PRODUCER("-consumer"),
    CONSUMER("-producer");

    public String value;

    WorkerStyle(String value) {
        this.value = value;
    }
}
