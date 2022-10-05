package com.dijkstra.producerconsumer.model.workers;

import com.dijkstra.producerconsumer.Controller;
import com.dijkstra.producerconsumer.model.buffer.InterestItem;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker {
    protected Controller controller;

    private AtomicBoolean running;
    private AtomicBoolean interruptSignal;

    public final ThreadItem THREAD_ITEM;
    public final InterestItem INTEREST_ITEM;

    private final Thread thread;

    public Worker(Controller controller, ThreadItem threadItem, InterestItem interestItem) {
        this.THREAD_ITEM = threadItem;
        this.INTEREST_ITEM = interestItem;

        this.running = new AtomicBoolean(false);
        this.interruptSignal = new AtomicBoolean(false);

        this.controller = controller;

        this.thread = new Thread() {
            @Override
            public void run() {
                while (interruptSignal.get()) {
                    try {
                        acesssCriticalRegion();
                        workerRun();
                        leaveCriticalRegion();

                        Worker.this.sleep(2000);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        controller.throwException();
                    } catch (Exception e) {

                    }

                    yield();
                }
            }
        };
    }

    /**
     * interrupts the worker
     */
    public void interrupt() {
        this.thread.interrupt();
        this.interruptSignal.set(false);
    }

    public void workerRun() throws InterruptedException {}

    /**
     * makes the worker sleep for a time
     * @param time
     * @throws InterruptedException
     */
    public void sleep(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    /**
     * mark that the work access the critical region
     */
    public void acesssCriticalRegion(){
        this.running.set(true);
    }

    /**
     * mark that the work left the critical region
     */
    public void leaveCriticalRegion(){
        this.running.set(false);
    }

    /**
     * makes the worker sleep for a random time
     * @throws InterruptedException
     */
    public void randomSleep() throws InterruptedException {
        Random random = new Random();
        sleep(random.nextInt(500));
    }

    /**
     * @return the current worker state
     */
    public Thread.State getState(){
        Thread.State current = this.thread.getState();
        if(running.get() && current == Thread.State.TIMED_WAITING) {
            return Thread.State.RUNNABLE;
        }

        return current;
    }

    /**
     * refresh the view node with the worker state
     */
    public void refresh() {
        this.THREAD_ITEM.setState(this.getState());
    }

    /**
     * initializes the worker
     */
    public void start()  {
        this.interruptSignal.set(true);
        this.thread.start();
    }
}
