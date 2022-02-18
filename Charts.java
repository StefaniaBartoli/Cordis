package dashboard;

import java.awt.Color;
import java.sql.Connection;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;


//Francesco Murtas

public class Charts {

    /**
     * The ChartsDataRepo which contains the data that would be used for the
     * plot
     */
    private ChartsDataRepo dataRepo;

    public Charts(Connection conn) {
        this.dataRepo = new ChartsDataRepo(conn);
    }

    /**
     * Get a chart with projects started per month from 2019 and 2020
     */
    public void showProjectStartedByMonth() {
        JDBCCategoryDataset dataset = null;
        try {
            dataset = dataRepo.getProjectsStartedByMonthLast2Years();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        JFreeChart chart = ChartFactory.createLineChart(
                " Number of projects started per month (from 2019 to 2020).", "Months", "Projects",
                dataset, PlotOrientation.VERTICAL, false, true, false);
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.blue);

        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.blue);
        ChartFrame frame = new ChartFrame(" Number of projects started per month", chart);
        frame.setVisible(true);
        frame.setSize(400, 350);
    }

    /**
     * Shows a chart with the top 6 funding schemes by number of projects.
     */
    public void showTopFundingSchemes() {
        JDBCCategoryDataset dataset = null;
        try {
            dataset = dataRepo.getTopFundingSchemes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        JFreeChart chart = ChartFactory.createBarChart("Top 6 Funding Schemes",
                "Funding Scheme", "Projects", dataset, PlotOrientation.HORIZONTAL, false, true, false);
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.blue);

        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.blue);
        BarRenderer renderer = (BarRenderer) p.getRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setShadowVisible(true);
        renderer.setItemMargin(-4);
        renderer.setSeriesPaint(0, Color.blue);

        ChartFrame frame = new ChartFrame("Top 6 Funding Schemes", chart);
        frame.setVisible(true);
        frame.setSize(400, 350);

    }

    /**
     * Shows a chart with the project seasonality.
     */
    public void showProjectSeasonality() {
        JDBCCategoryDataset dataset = null;
        try {
            dataset = dataRepo.getProjectsStartedByMonth();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        JFreeChart chart = ChartFactory.createLineChart("Projects Seasonality ",
                "Month", "Project", dataset, PlotOrientation.VERTICAL, false, true, false);
        chart.setBackgroundPaint(Color.WHITE);
        chart.getTitle().setPaint(Color.red);
        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.blue);

        ChartFrame frame = new ChartFrame("Projects Seasonality", chart);
        frame.setVisible(true);
        frame.setSize(400, 350);
    }

    public void showProjectEndedByYear() {
        JDBCCategoryDataset dataset = null;
        try {
            dataset = dataRepo.getProjectsEndedByYear();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        JFreeChart chart = ChartFactory.createLineChart(" Number of projects ended per year (over the whole lifetime of the dataset).", "Year", "Project", dataset, PlotOrientation.VERTICAL, false, true, false);
        chart.setBackgroundPaint(Color.white);
        chart.getTitle().setPaint(Color.blue);

        CategoryPlot p = chart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.blue);
        ChartFrame frame = new ChartFrame(" Number of projects ended per month", chart);
        frame.setVisible(true);
        frame.setSize(400, 350);
    }
}
