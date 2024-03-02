package szathmary.peter;

import java.io.*;

import szathmary.peter.gui.controller.IController;
import szathmary.peter.gui.controller.MonteCarloController;
import szathmary.peter.gui.model.IModel;
import szathmary.peter.gui.model.MonteCarloModel;
import szathmary.peter.gui.view.IMainWindow;
import szathmary.peter.gui.view.MainWindow;
import szathmary.peter.simulation.core.MonteCarloSimulationCore;
import szathmary.peter.simulation.core.BuffonNeedleMonteCarlo;

public class Main {
  public static void main(String[] args) throws IOException {
    //    RandomGenerator<Double> continuousEmpiricRandomGenerator =
    //        new ContinuousEmpiricRandomGenerator(
    //            List.of(
    //                new EmpiricOption<>(0.1, 0.3, 0.1),
    //                new EmpiricOption<>(0.3, 0.8, 0.5),
    //                new EmpiricOption<>(0.8, 1.2, 0.2),
    //                new EmpiricOption<>(1.2, 2.5, 0.15),
    //                new EmpiricOption<>(3.8, 4.8, 0.05)));
    //
    //    RandomGenerator<Integer> discreteUniform = new DiscreteUniformRandomGenerator(0, 100);
    //
    //    File file = new File("./discrete_uniform.csv");
    //
    //    if (file.exists()) {
    //      file.delete();
    //    }
    //
    //    OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(file));
    //    for (int i = 0; i < 1000000; i++) {
    //      outputStream.write(String.valueOf(discreteUniform.sample()));
    //      outputStream.write("\n");
    //    }
    //    outputStream.flush();
//    MonteCarloSimulationCore monteCarloSimulation = new BuffonNeedleMonteCarlo(10000000, 5, 2);
//    monteCarloSimulation.startSimulation();

    IModel model = new MonteCarloModel();
    IController controller = new MonteCarloController(model);
    IMainWindow mainWindow = new MainWindow(controller);
  }
}
