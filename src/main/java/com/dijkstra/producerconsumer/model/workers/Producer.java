package com.dijkstra.producerconsumer.model.workers;

import com.dijkstra.producerconsumer.Controller;
import com.dijkstra.producerconsumer.model.buffer.InterestItem;

/**
 * provides a Worker implementation to the Producer
 */
public class Producer extends Worker {
    private Producer(Controller controller, ThreadItem threadItem, InterestItem interestItem) {
        super(controller, threadItem, interestItem);
    }

    @Override
    public void workerRun() throws InterruptedException {
        this.controller.produce(this);
    }

    /**
     * creates a Producer instance
     * @param controller
     * @param id
     * @return a Producer instance
     */
    public static Producer from(Controller controller, int id) {
        ThreadItem item = ThreadItem.from(WorkerStyle.PRODUCER, id);
        InterestItem interestItem = InterestItem.from(WorkerStyle.PRODUCER, id);
        return new Producer(controller, item, interestItem);
    }
}
