package szathmary.peter.gui.view;

import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import szathmary.peter.gui.controller.IController;
import szathmary.peter.gui.observable.IObservable;
import szathmary.peter.gui.observable.IReplicationObservable;

/** Created by petos on 28/02/2024. */
public class MainWindow extends JFrame implements IMainWindow {
  private final IController controller;
  private SwingWorker<Void, Void> simulationWorker;
  private JFreeChart chart;
  private XYSeriesCollection dataset;
  private JTextField dTextField;
  private JTextField lTextField;
  private JTextField numberOfReplicationsTextField;
  private JTextField sampleSizeTextField;
  private JButton startButton;
  private JButton stopButton;
  private ChartPanel chartPanel;
  private JPanel mainPanel;

  public MainWindow(IController controller) {
    this.controller = controller;
    this.controller.attach(this);

    setContentPane(mainPanel);
    setTitle("Buffon's needle");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(1400, 900);
    setLocationRelativeTo(null);
    setVisible(true);

    startButton.addActionListener(e -> startSimulation());

    stopButton.addActionListener(e -> stopSimulation());
  }

  public void createUIComponents() {
    dataset = new XYSeriesCollection();
    XYSeries newSeries = new XYSeries("replications");
    dataset.addSeries(newSeries);

    chart =
        ChartFactory.createXYLineChart(
            "Pi", "Replication", "Pi", dataset, PlotOrientation.VERTICAL, false, false, false);

    chartPanel = new ChartPanel(chart);
    chartPanel.setMouseZoomable(true);
  }

  @Override
  public void startSimulation() {
    setParameters();
    resetChart();

    simulationWorker =
        new SwingWorker<>() {
          @Override
          protected Void doInBackground() {
            controller.startSimulation();
            return null;
          }
        };

    simulationWorker.execute();
  }

  private void resetChart() {
    dataset.getSeries(0).clear();
  }

  @Override
  public void setParameters() {
    controller.setParameters(
        Integer.parseInt(dTextField.getText()),
        Integer.parseInt(lTextField.getText()),
        Long.parseLong(numberOfReplicationsTextField.getText()),
        Integer.parseInt(sampleSizeTextField.getText()));
  }

  @Override
  public void stopSimulation() {
    if (simulationWorker == null) {
      return;
    }

    System.out.println(simulationWorker.cancel(true));
  }

  @Override
  public void update(IObservable observable) {
    if (!(observable instanceof IReplicationObservable replicationObservable)) {
      return;
    }

    if (simulationWorker != null && !simulationWorker.isDone()) {
      double lastResult = replicationObservable.getLastResult();

      if (Double.isInfinite(lastResult) || Double.isNaN(lastResult)) {
        return;
      }

      SwingUtilities.invokeLater(
          () -> {
            XYSeries series = dataset.getSeries(0);

            int sampleSize = Integer.parseInt(sampleSizeTextField.getText());

            series.add(
                series.getItemCount() == 0
                    ? series.getItemCount() + sampleSize
                    : series.getMaxX() + sampleSize,
                lastResult);

            double lowestLow = series.getMinY();
            double highestHigh = series.getMaxY();

            chart.getXYPlot().getRangeAxis().setRange(lowestLow * 0.999, highestHigh * 1.001);

            chart.fireChartChanged();
          });
    }
  }
}
