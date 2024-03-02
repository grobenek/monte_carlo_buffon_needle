package szathmary.peter.gui.controller;

import szathmary.peter.gui.observable.IObservable;
import szathmary.peter.gui.observable.IObserver;
import szathmary.peter.gui.observable.IReplicationObservable;

public interface IController extends IObserver, IReplicationObservable {
  void startSimulation();

  void setParameters(
      int d,
      int l,
      long numberOfReplications, int sampleSize);
}
