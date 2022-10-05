package com.dijkstra.producerconsumer;

import com.dijkstra.producerconsumer.model.Item;
import com.dijkstra.producerconsumer.model.buffer.BufferItem;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.CheckBox;
import javafx.animation.AnimationTimer;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dijkstra.producerconsumer.model.workers.Consumer;
import com.dijkstra.producerconsumer.model.workers.Producer;
import com.dijkstra.producerconsumer.model.workers.Worker;

/**
 * provide a control structure for the view
 */
public class Controller {
    // Constants
    private final int MIN_CONSUMERS = 1;
    private final int MAX_CONSUMERS = 3;
    private final int MIN_PRODUCERS = 1;
    private final int MAX_PRODUCERS = 3;

    // View Injection
    @FXML
    private Spinner consumers;

    @FXML
    private Spinner producers;

    @FXML
    private CheckBox enableMonitors;

    @FXML
    private Button button;

    @FXML
    private Pane root;

    @FXML
    private Pane warning;

    @FXML
    private FlowPane threads;

    // Problem
    private Lock mutex;
    private Item[] items;
    private int bufferIndex;
    private Worker[] workers;
    private BufferItem[] buffer;

    // Simulation Status
    private boolean disabled = false;

    // Draw Loop
    private AnimationTimer animationTimer;

    @FXML
    private void initialize() {
        this.items = new Item[]{
                Item.KIWI,
                Item.TANGERINE,
                Item.WATERMELON,
                Item.GRAPE
        };

        this.bufferIndex = 0;

        this.mutex = new ReentrantLock(true);

        this.buffer = new BufferItem[]{
                BufferItem.from(this.root, 0),
                BufferItem.from(this.root, 1),
                BufferItem.from(this.root, 2),
                BufferItem.from(this.root, 3),
                BufferItem.from(this.root, 4),
                BufferItem.from(this.root, 5)
        };

        // Init Events

        this.button.setOnAction(event -> handleClick());
        addSpinnerValidator(producers, MIN_PRODUCERS, MAX_PRODUCERS);
        addSpinnerValidator(consumers, MIN_CONSUMERS, MAX_CONSUMERS);

        // Init Draw Loop

        this.animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (disabled) {
                    try {
                        for (BufferItem bufferItem : buffer) {
                            bufferItem.refresh();
                        }

                        for (Worker worker : workers) {
                            worker.refresh();
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throwException();
                    }
                }
            }
        };

        // Clear View

        this.clear();
    }

    /**
     * initializes the producers/consumers workers
     */
    private void startWorkers() {
        int producers = this.getProducers();
        int consumers = this.getConsumers();

        this.workers = new Worker[producers + consumers];

        for (int i = 0; i < producers; i++) {
            workers[i] = Producer.from(this, i);
            linkWorker(workers[i]);
            workers[i].start();
        }

        for (int i = producers; i < producers + consumers; i++) {
            workers[i] = Consumer.from(this, i);
            linkWorker(workers[i]);
            workers[i].start();
        }
    }

    /**
     * Adds the worker node to the thread list
     *
     * @param worker
     */
    private void linkWorker(Worker worker) {
        threads.getChildren().add(worker.THREAD_ITEM.getNode());
    }

    /**
     * Handle an event click
     */
    private synchronized void handleClick() {
        if (this.disabled) {
            this.stop();
        } else {
            this.start();
        }
    }

    /**
     * produces an item from the buffer
     *
     * @param worker
     * @throws InterruptedException
     */
    private void executeProduce(Worker worker) throws InterruptedException {
        if (bufferIndex != buffer.length - 2) {
            Item item = getRandomItem();

            // forces a race condition
            worker.randomSleep();
            int index = ++bufferIndex;
            worker.randomSleep();

            buffer[index].setInterest(worker.INTEREST_ITEM);
            buffer[index].setSlotItem(item);
            worker.sleep(1000);
            buffer[index].removeInterest(worker.INTEREST_ITEM);
        }
    }

    /**
     * wrappers the produce function
     *
     * @param worker
     * @throws InterruptedException
     */
    public void produce(Worker worker) throws InterruptedException {
        if (this.getEnableMonitors()) {
            mutex.lock();
            executeProduce(worker);
            mutex.unlock();
        } else {
            executeProduce(worker);
        }
    }

    /**
     * wrappers the consume function
     *
     * @param worker
     * @throws InterruptedException
     */
    private void executeConsume(Worker worker) throws InterruptedException {
        if (bufferIndex >= 1) {
            // forces a race condition
            worker.randomSleep();
            int index = bufferIndex--;
            worker.randomSleep();

            buffer[index].setInterest(worker.INTEREST_ITEM);
            buffer[index].setSlotItem(null);
            worker.sleep(1000);
            buffer[index].removeInterest(worker.INTEREST_ITEM);
        }
    }

    /**
     * consumes an item from the buffer
     *
     * @param worker
     * @throws InterruptedException
     */
    public void consume(Worker worker) throws InterruptedException {
        if (this.getEnableMonitors()) {
            mutex.lock();
            executeConsume(worker);
            mutex.unlock();
        } else {
            executeConsume(worker);
        }
    }

    /**
     * @return a random producer item
     */
    public Item getRandomItem() {
        Random random = new Random();
        return items[random.nextInt(items.length - 1)];
    }

    /**
     * @return the number of consumers
     */
    public synchronized int getConsumers() {
        return (Integer) consumers.getValue();
    }

    /**
     * @return the number of producers
     */
    public synchronized int getProducers() {
        return (Integer) producers.getValue();
    }

    /**
     * @return the current value of the checkbox (EnableMonitors)
     */
    public synchronized boolean getEnableMonitors() {
        return enableMonitors.isSelected();
    }

    /**
     * set disable property of nodes
     *
     * @param value
     */
    private synchronized void setDisableProperty(boolean value) {
        this.consumers.disableProperty().set(value);
        this.producers.disableProperty().set(value);
        this.enableMonitors.disableProperty().set(value);
    }

    /**
     * displays the start button
     */
    public void showStartButton() {
        this.button.textProperty().set("START");
        this.button.getStyleClass().set(1, "start");
    }

    /**
     * displays the stop button
     */
    public void showStopButton() {
        this.button.textProperty().set("STOP");
        this.button.getStyleClass().set(1, "stop");
    }

    /**
     * displays the alert with the error
     */
    private void showWarning() {
        this.warning.visibleProperty().set(true);
    }

    /**
     * hides the alert with the error
     */
    private void hiddenWarning() {
        this.warning.visibleProperty().set(false);
    }

    /**
     * displays the warning and stops the execution
     */
    public synchronized void throwException() {
        Platform.runLater(() -> {
            showWarning();
            stop();
        });
    }

    /**
     * initializes the execution
     */
    private synchronized void start() {
        this.disabled = true;

        this.clear();

        // Threads
        this.mutex = new ReentrantLock(true);
        this.startWorkers();
        this.animationTimer.start();

        // View
        this.showStopButton();
        this.setDisableProperty(true);
    }

    /**
     * stops the execution
     */
    private synchronized void stop() {
        this.disabled = false;
        this.setDisableProperty(false);

        if (this.workers != null) {
            animationTimer.stop();

            for (Worker worker : workers) {
                if (worker != null) {
                    worker.interrupt();
                }
            }

            this.workers = null;
        }

        this.showStartButton();
    }

    /**
     * clear all view nodes
     */
    private void clear() {
        this.bufferIndex = 0;
        this.hiddenWarning();

        for (BufferItem bufferItem : buffer) {
            bufferItem.clear();
        }

        threads.getChildren().clear();
    }

    /**
     * initializes the spinner validator
     *
     * @param spinner
     * @param min
     * @param max
     */
    private void addSpinnerValidator(Spinner spinner, int min, int max) {
        spinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (this.disabled) {
                spinner.getEditor().setText(oldValue);
                return;
            }

            if (newValue.matches("\\d*")) {
                int value = Integer.parseInt(newValue);
                if (value < min || value > max) {
                    spinner.getEditor().setText(oldValue);
                }
            } else {
                spinner.getEditor().setText(oldValue);
            }
        });
    }
}
