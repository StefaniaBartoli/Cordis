package dashboard;


import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


//Beatriz Vassiliadis


public class BarChart extends JFrame {

    private static final long serialVersionUID = 1L;

    static void runBarchartCountries() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BarChart(String appTitle) {
        super(appTitle);

        CategoryDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createBarChart(
                "Top 7 Participant Countries", //Chart Title  
                "Countries", // Category axis  
                "Number of Participations", // Value axis  
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setShadowVisible(true);
        renderer.setItemMargin(-4);
        renderer.setSeriesPaint(0, Color.blue);
        renderer.setSeriesPaint(1, Color.darkGray);
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesPaint(3, Color.cyan);
        renderer.setSeriesPaint(4, Color.MAGENTA);
        renderer.setSeriesPaint(5, Color.YELLOW);
        renderer.setSeriesPaint(6, Color.pink);
        ChartPanel panel = new ChartPanel(chart);
        panel.setMaximumDrawWidth(900);
        setContentPane(panel);
    }

    private CategoryDataset createDataset() {
        String sql = "select count(*) as total, a.countryName as name, a.countryID as id from Country a, Participant b where a.countryID = b.country group by 2,3 order by 1 desc limit 7;";

        Connection conn = this.connect();

        if (conn != null) {

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();

                while (rs.next()) {
                    dataset.addValue(rs.getInt("total"), rs.getString("id"), rs.getString("name"));
                }

                return dataset;
            } catch (Exception e) {
                System.err.println("Exception:" + e.getMessage());
            }
        }

        return null;
    }

    private Connection connect() {
        // SQLite connection string
        
        String url = "jdbc:sqlite:cordis2.db";
        //String url = "jdbc:sqlite:/tmp/cordis2.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return conn;
    }

    public static void main(String[] args) throws Exception {
        BarChart app = new BarChart("Top Participant Countries");
        SwingUtilities.invokeAndWait(() -> {
            BarChart chart = new BarChart("Bar Chart");
            chart.setSize(800, 400);
            chart.setLocationRelativeTo(null);
           
            chart.setVisible(true);
        });
    }
}
