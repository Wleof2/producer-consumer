package com.dijkstra.producerconsumer.model.workers;

import com.dijkstra.producerconsumer.Controller;
import com.dijkstra.producerconsumer.model.buffer.InterestItem;

/**
 * provides a Worker implementation to the Consumer
 */
public class Consumer extends Worker {
    private Consumer(Controller controller, ThreadItem threadItem, InterestItem interestItem) {
        super(controller, threadItem, interestItem);
    }

    @Override
    public void workerRun() throws InterruptedException {
       this.controller.consume(this);
    }

    /**
     * creates a Consumer instance
     * @param controller
     * @param id
     * @return a Consumer instance
     */
    public static Consumer from(Controller controller, int id) {
        ThreadItem item = ThreadItem.from(WorkerStyle.CONSUMER, id);
        InterestItem interestItem = InterestItem.from(WorkerStyle.CONSUMER, id);
        return new Consumer(controller, item, interestItem);
    }
}
