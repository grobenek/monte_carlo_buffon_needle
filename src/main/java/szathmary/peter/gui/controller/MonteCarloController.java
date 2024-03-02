package szathmary.peter.gui.controller;

import szathmary.peter.gui.observable.IObservable;
import szathmary.peter.gui.observable.IObserver;
import szathmary.peter.gui.model.BuffonsNeedleConfiguration;
import szathmary.peter.gui.model.IModel;
import szathmary.peter.gui.observable.IReplicationObservable;

import java.util.ArrayList;
import java.util.List;

/** Created by petos on 28/02/2024. */
public class MonteCarloController implements IController {
  private final IModel model;
  private final List<IObserver> observers;
  private double lastResult;

  public MonteCarloController(IModel model) {
    this.model = model;
    this.model.attach(this);
    this.observers = new ArrayList<>();
  }

  @Override
  public void startSimulation() {
    model.startSimulation();
  }

  @Override
  public void setParameters(int d, int l, long numberOfReplications, int sampleSize) {
    BuffonsNeedleConfiguration buffonsNeedleConfiguration =
        new BuffonsNeedleConfiguration(d, l, numberOfReplications, sampleSize);

    model.setParameters(buffonsNeedleConfiguration);
  }

  @Override
  public void attach(IObserver observer) {
    observers.add(observer);
  }

  @Override
  public void detach(IObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void sendNotifications() {
    for (IObserver observer : observers) {
      observer.update(this);
    }
  }

  @Override
  public void update(IObservable observable) {
    if (!(observable instanceof IReplicationObservable replicationObservable)) {
      return;
    }

    lastResult = replicationObservable.getLastResult();
    sendNotifications();
  }

  @Override
  public double getLastResult() {
    return lastResult;
  }
}
