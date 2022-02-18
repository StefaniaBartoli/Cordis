package dashboard;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.SQLException;
import org.jfree.data.jdbc.JDBCCategoryDataset;

//Francesco Murtas


public class ChartsDataRepo {

    private Connection conn = null;

    public ChartsDataRepo(Connection conn) {
        this.conn = conn;
    }

    /**
     * Get projects started per month in 2019, 2020
     */
    public JDBCCategoryDataset getProjectsStartedByMonthLast2Years() throws SQLException {
        String sql = " select\n"
                + " substr(startdate, 4, 7) as month, -- extracts the month of the given year.\n"
                + "  count(*)\n"
                + "from project\n"
                + "where substr(startdate, 7, 4) IN ( '2019','2020')\n"
                + "group by 1\n"
                + "order by substr(startdate, 7, 4), substr(startdate, 4, 2)  asc -- order by year, then by month";

        JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn, sql);
        return dataset;
    }

    public JDBCCategoryDataset getTopFundingSchemes() throws SQLException {
        String sql = "SELECT fundingScheme,count(*) As c FROM project GROUP BY fundingScheme ORDER BY c DESC limit 6";

        JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn, sql);
        return dataset;
    }

    public JDBCCategoryDataset getProjectsStartedByMonth() throws SQLException {
        String sql = "select\n"
                + " substr(startdate, 4, 2) as month, "
                + "count(*)\n"
                + "from project\n"
                + "group by 1\n"
                + "order by 1 asc";

        JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn, sql);
        return dataset;
    }

    public JDBCCategoryDataset getProjectsEndedByYear() throws SQLException {
        String sql = "select\n"
                + " substr(enddate, 7, 4) as year,\n"
                + "  count(*)\n"
                + "from project\n"
                + "group by 1\n"
                + "order by 1 -- order by year";

        JDBCCategoryDataset dataset = new JDBCCategoryDataset(conn, sql);
        return dataset;
    }

}
