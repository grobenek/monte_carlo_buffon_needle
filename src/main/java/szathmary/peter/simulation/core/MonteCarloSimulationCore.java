package szathmary.peter.simulation.core;

/** Created by petos on 28/02/2024. */
public abstract class MonteCarloSimulationCore {
  private final int numberOfReplications;

  public MonteCarloSimulationCore(int numberOfReplications) {
    this.numberOfReplications = numberOfReplications;
  }

  public void startSimulation() {
    beforeReplications();
    for (int i = 0; i < numberOfReplications; i++) {
      beforeReplication();
      replication();
      afterReplication();
    }
    afterReplications();
  }

  public abstract void afterReplications();

  public abstract void afterReplication();

  public abstract void replication();

  public abstract void beforeReplication();

  public abstract void beforeReplications();
}
