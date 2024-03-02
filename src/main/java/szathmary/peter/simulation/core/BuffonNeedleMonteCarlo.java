package szathmary.peter.simulation.core;

import java.util.ArrayList;
import java.util.List;
import szathmary.peter.gui.observable.IObserver;
import szathmary.peter.randomgenerators.RandomGenerator;
import szathmary.peter.randomgenerators.continuousgenerators.ContinuousUniformGenerator;

/** Created by petos on 28/02/2024. */
public class BuffonNeedleMonteCarlo extends MonteCarloSimulationCore {
  private final double d;
  private final double l;
  private final List<IObserver> observers;
  private double pi;
  private RandomGenerator<Double> alphaRandom;
  private RandomGenerator<Double> yRandom;
  private int crossed;
  private int numberOfThrows;

  public BuffonNeedleMonteCarlo(long numberOfReplications, double d, double l) {
    super(numberOfReplications);
    this.d = d;
    this.l = l;

    observers = new ArrayList<>();
  }

  @Override
  public void beforeReplications() {
    alphaRandom = new ContinuousUniformGenerator(0, 180);
    yRandom = new ContinuousUniformGenerator(0, d);

    crossed = 0;
    numberOfThrows = 0;
    pi = 0;
  }

  @Override
  public void beforeReplication() {}

  @Override
  public void replication() {
    numberOfThrows++;
    double alpha = alphaRandom.sample();
    double y = yRandom.sample();

    double a = l * Math.sin(Math.toRadians(alpha));

    if (a + y >= d) {
      crossed++;
    }

    double p = (double) crossed / numberOfThrows;

    pi = (2 * l) / (d * p);
  }

  @Override
  public void afterReplication() {
    sendNotifications();
  }

  @Override
  public void afterReplications() {
    double p = (double) crossed / numberOfThrows;

    pi = (2 * l) / (d * p);
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
  public double getLastResult() {
    return pi;
  }
}
