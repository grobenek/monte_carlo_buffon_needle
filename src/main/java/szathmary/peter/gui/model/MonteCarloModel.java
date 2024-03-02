package szathmary.peter.gui.model;

import java.util.ArrayList;
import java.util.List;

import szathmary.peter.gui.observable.IObservable;
import szathmary.peter.gui.observable.IObserver;
import szathmary.peter.gui.observable.IReplicationObservable;
import szathmary.peter.simulation.core.BuffonNeedleMonteCarlo;
import szathmary.peter.simulation.core.MonteCarloSimulationCore;

/** Created by petos on 28/02/2024. */
public class MonteCarloModel implements IModel {
  private MonteCarloSimulationCore monteCarloSimulation;
  private final List<IObserver> observers;
  private double lastResult;
  private long currentReplication;
  private int sampleSize;

  public MonteCarloModel() {
    this.observers = new ArrayList<>();
    this.currentReplication = 0;
  }

  @Override
  public void startSimulation() {
    monteCarloSimulation.startSimulation();
  }

  @Override
  public void setParameters(BuffonsNeedleConfiguration buffonsNeedleConfiguration) {
    monteCarloSimulation =
        new BuffonNeedleMonteCarlo(
            buffonsNeedleConfiguration.numberOfReplications(),
            buffonsNeedleConfiguration.d(),
            buffonsNeedleConfiguration.l());

    monteCarloSimulation.attach(this);

    sampleSize = buffonsNeedleConfiguration.sampleSize();
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
    currentReplication++;

    if (currentReplication % sampleSize == 0) {
      sendNotifications();
    }
  }

  @Override
  public double getLastResult() {
    return lastResult;
  }
}
