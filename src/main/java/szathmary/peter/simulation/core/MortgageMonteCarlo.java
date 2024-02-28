package szathmary.peter.simulation.core;

import szathmary.peter.randomgenerators.RandomGenerator;
import szathmary.peter.randomgenerators.continuousgenerators.ContinuousUniformGenerator;

/** Created by petos on 28/02/2024. */
public class MortgageMonteCarlo extends MonteCarloSimulationCore {
  private final double d;
  private final double l;
  private RandomGenerator<Double> alphaRandom;
  private RandomGenerator<Double> yRandom;
  private int crossed;
  private int numberOfThrows;

  public MortgageMonteCarlo(int numberOfReplications, double d, double l) {
    super(numberOfReplications);
    this.d = d;
    this.l = l;
  }

  @Override
  public void beforeReplications() {
    alphaRandom = new ContinuousUniformGenerator(0, 180);
    yRandom = new ContinuousUniformGenerator(0, d);

    crossed = 0;
    numberOfThrows = 0;
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
  }

  @Override
  public void afterReplication() {}

  @Override
  public void afterReplications() {
    double p = (double) crossed / numberOfThrows;

    double pi = (2 * l) / (d * p);

    System.out.println(pi);
  }
}
