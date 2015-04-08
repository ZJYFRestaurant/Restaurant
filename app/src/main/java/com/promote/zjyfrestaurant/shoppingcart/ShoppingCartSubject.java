package com.promote.zjyfrestaurant.shoppingcart;

import java.util.ArrayList;

/**
 * 点餐车监听对象。
 */
public class ShoppingCartSubject {

    private ArrayList<ShoppingCartObserver> observers = new ArrayList<ShoppingCartObserver>();
    private boolean changed = false;

    /**
     * 注册监听者。
     *
     * @param observer
     */
    public void addObserver(ShoppingCartObserver observer) {

        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!observers.contains(observer))
                observers.add(observer);
        }
    }

    /**
     * Removes the specified observer from the list of observers. Passing null
     * won't do anything.
     *
     * @param observer the observer to remove.
     */
    public synchronized void deleteObserver(ShoppingCartObserver observer) {
        observers.remove(observer);
    }

    /**
     * Removes all observers from the list of observers.
     */
    public synchronized void deleteObservers() {
        observers.clear();
    }

    /**
     * Clears the changed flag for this {@code Observable}. After calling
     * {@code clearChanged()}, {@code hasChanged()} will return {@code false}.
     */
    protected void clearChanged() {
        changed = false;
    }

    /**
     * Returns the changed flag for this {@code Observable}.
     *
     * @return {@code true} when the changed flag for this {@code Observable} is
     * set, {@code false} otherwise.
     */
    public boolean hasChanged() {
        return changed;
    }

    /**
     * If {@code hasChanged()} returns {@code true}, calls the {@code update()}
     * method for every Observer in the list of observers using the specified
     * argument. Afterwards calls {@code clearChanged()}.
     *
     * @param data the argument passed to {@code update()}.
     */
    @SuppressWarnings("unchecked")
    public void notifyObservers(int data) {
        int size = 0;
        ShoppingCartObserver[] arrays = null;
        synchronized (this) {
            if (hasChanged()) {
                clearChanged();
                size = observers.size();
                arrays = new ShoppingCartObserver[size];
                observers.toArray(arrays);
            }
        }
        if (arrays != null) {
            for (ShoppingCartObserver observer : arrays) {
                observer.update(this, data);
            }
        }
    }

    /**
     * Sets the changed flag for this {@code Observable}. After calling
     * {@code setChanged()}, {@code hasChanged()} will return {@code true}.
     */
    protected void setChanged() {
        changed = true;
    }

}
