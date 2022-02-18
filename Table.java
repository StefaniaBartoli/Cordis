package dashboard;

import login.Login_form;
import java.awt.Color;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import net.proteanit.sql.DbUtils;
import java.sql.Connection;
import login.Connect;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;


//Justyna Bucko

public class Table extends javax.swing.JFrame {

    //setting connection with database
    String add1;
    String add2;
    String add3;
    String add4;
    String add5;
    String add6;
    
    Connection conn = null;
    Connection conn1 = null;
    ResultSet rs = null;
    PreparedStatement pst = null;
    Charts charts;
    private String username;

    //constructor
    public Table(String username){
    this.username = username;
        initComponents();
        conn = javaconnect.ConnecrDb();
        conn1 = Connect.connect();

        charts = new Charts(conn);
        Update_Table_Project();
        Update_Table_FundScheme();
        Update_Table_Organisation();    
    }
    
    public Table() throws InterruptedException {
        this.username = username;
        initComponents();
        conn = javaconnect.ConnecrDb();

        charts = new Charts(conn);
        Update_Table_Project();
        Update_Table_FundScheme();
        Update_Table_Organisation();
        {
        }
         initComponents();
    }
    
     public Connection connect() {
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
    
     public CategoryDataset createDataset() {
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
    
     
     public JPanel createChartPanel(){
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
        chart.getPlot().setBackgroundPaint(Color.white);
        panel.getPreferredSize();
        
        setContentPane(panel);
        return panel;
     }
    
    
    

     

    //small tble project
    private void Update_Table_Project() {

        try {
            String sql = "select acronym,title,ecMaxContribution,totalCost from Project";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            Project.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
    }

    //small table organization
    private void Update_Table_Organisation() {

        try {
            String sql = "select id,shortName,city,country, activityType from  participant";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            TableParticipant.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }

    }
    
    //small table funding scheme
    
    private void Update_Table_FundScheme(){
        
        try {
            String sql = "select code, title from  Funding_Scheme";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            FundSchemeXProject.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);

        }
        
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jScrollPane5 = new javax.swing.JScrollPane();
        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane7 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableParticipant = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        organizationButton = new javax.swing.JToggleButton();
        TopFundingSchemes = new javax.swing.JButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        FundSchemeXProject = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Project = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        parameter = new javax.swing.JLabel();
        max = new javax.swing.JLabel();
        min = new javax.swing.JLabel();
        average = new javax.swing.JLabel();
        ecContribution = new javax.swing.JLabel();
        totalCost = new javax.swing.JLabel();
        countriesPart = new javax.swing.JLabel();
        signed_pro = new javax.swing.JLabel();
        logout = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        maxTC = new javax.swing.JTextField();
        minTC = new javax.swing.JTextField();
        minEC = new javax.swing.JTextField();
        avgTC = new javax.swing.JTextField();
        maxEC = new javax.swing.JTextField();
        avgEC = new javax.swing.JTextField();
        ok = new java.awt.Button();
        seemore = new java.awt.Button();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();

        jMenu1.setText("jMenu1");

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        jScrollPane5.setViewportView(jProgressBar1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(47, 48, 81));
        setMinimumSize(new java.awt.Dimension(1800, 1400));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(47, 48, 81));
        jPanel2.setForeground(new java.awt.Color(0, 0, 51));
        jPanel2.setPreferredSize(new java.awt.Dimension(2200, 4000));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableParticipant.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(TableParticipant);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 230, 580, 260));

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Project Cost Calculations");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 850, 510, 60));

        organizationButton.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        organizationButton.setText("Click here to filter data");
        organizationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organizationButtonActionPerformed(evt);
            }
        });
        jPanel2.add(organizationButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 180, 540, -1));

        TopFundingSchemes.setFont(new java.awt.Font("Yu Gothic", 1, 14)); // NOI18N
        TopFundingSchemes.setText("Top Funding Schemes");
        TopFundingSchemes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TopFundingSchemesActionPerformed(evt);
            }
        });
        jPanel2.add(TopFundingSchemes, new org.netbeans.lib.awtextra.AbsoluteConstraints(1420, 190, 260, 40));

        jToggleButton3.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jToggleButton3.setText("Top participing countries");
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jToggleButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1420, 120, 260, 40));

        jButton2.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jButton2.setText("Projects Seasonality");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, 620, 260, 100));

        jButton4.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jButton4.setText("Projects ended per year along the dataset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, 490, 260, 110));

        jButton3.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jButton3.setText("Projects started  per month (2019/2020)");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1400, 380, 260, 100));

        FundSchemeXProject.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(FundSchemeXProject);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 600, 520, 160));

        Project.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(Project);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 930, 470, 390));

        jLabel9.setBackground(new java.awt.Color(0, 0, 51));
        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 51));
        jLabel9.setText("Funding Schemes");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 540, 510, 60));

        parameter.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        parameter.setForeground(new java.awt.Color(255, 255, 255));
        parameter.setText("Parameter");
        jPanel2.add(parameter, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 930, 92, -1));

        max.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        max.setForeground(new java.awt.Color(255, 255, 255));
        max.setText("Max");
        jPanel2.add(max, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 930, 49, -1));

        min.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        min.setForeground(new java.awt.Color(255, 255, 255));
        min.setText("Min");
        jPanel2.add(min, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 930, 50, 20));

        average.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        average.setForeground(new java.awt.Color(255, 255, 255));
        average.setText("Average");
        jPanel2.add(average, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 930, -1, -1));

        ecContribution.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        ecContribution.setForeground(new java.awt.Color(255, 255, 255));
        ecContribution.setText("EcContribution");
        jPanel2.add(ecContribution, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 980, -1, -1));

        totalCost.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        totalCost.setForeground(new java.awt.Color(255, 255, 255));
        totalCost.setText("total cost");
        jPanel2.add(totalCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 1050, -1, -1));

        countriesPart.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        countriesPart.setText("H2020 ");
        jPanel2.add(countriesPart, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 560, 190, 120));

        signed_pro.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        signed_pro.setText("19 666");
        signed_pro.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel2.add(signed_pro, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, 190, 100));

        logout.setText("Logout");
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
        jPanel2.add(logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 20, 250, 40));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 51));
        jLabel8.setText("framework programme");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 650, 240, -1));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 51));
        jLabel7.setText("signed projects");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 430, 190, 60));

        maxTC.setForeground(new java.awt.Color(0, 0, 0));
        jPanel2.add(maxTC, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 1040, 152, 50));
        jPanel2.add(minTC, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 1040, 148, 50));
        jPanel2.add(minEC, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 970, 148, 50));

        avgTC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avgTCActionPerformed(evt);
            }
        });
        jPanel2.add(avgTC, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 1040, 153, 50));

        maxEC.setForeground(new java.awt.Color(1, 0, 0));
        maxEC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxECActionPerformed(evt);
            }
        });
        jPanel2.add(maxEC, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 970, 152, 50));

        avgEC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                avgECActionPerformed(evt);
            }
        });
        jPanel2.add(avgEC, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 970, 153, 50));

        ok.setLabel("find out");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });
        jPanel2.add(ok, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 1120, 490, 40));

        seemore.setLabel("here");
        seemore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seemoreActionPerformed(evt);
            }
        });
        jPanel2.add(seemore, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 1390, 480, 30));

        jLabel6.setFont(new java.awt.Font("Yu Gothic", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 51));
        jLabel6.setText("get more details, search and filter data");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 1340, 470, -1));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 51));
        jLabel5.setText("Project properties");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 850, 510, 60));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 24)); // NOI18N
        jLabel4.setText("Organisation properties");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 510, 60));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dashboard/backgroundDashboard.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 1690, 1490));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 24)); // NOI18N
        jLabel3.setText("Organisation properties");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 100, 510, 60));

        jScrollPane7.setViewportView(jPanel2);

        getContentPane().add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-60, 0, 1960, 2210));
        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1570, 90, 60, 100));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void seemoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seemoreActionPerformed
        new ProjectFullTable().setVisible(true);
    }//GEN-LAST:event_seemoreActionPerformed

    
    //calculations of project costs
    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed

        try {

            String sql = "select acronym, max(ecMaxContribution), min(ecMaxContribution), avg(ecMaxContribution), max(totalCost), min(totalCost), avg(totalCost) from Project";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            //ecContribution
            add1 = rs.getString("max(ecMaxContribution)");
            maxEC.setText(add1);
            add2 = rs.getString("min(ecMaxContribution)");
            minEC.setText(add2);
            add3 = rs.getString("avg(ecMaxContribution)");
            avgEC.setText(add3);

            //total cost
            add4 = rs.getString("max(totalCost)");
            maxTC.setText(add4);
            add5 = rs.getString("min(totalCost)");
            minTC.setText(add5);
            add6 = rs.getString("avg(totalCost)");
            avgTC.setText(add6);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_okActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        charts.showProjectStartedByMonth();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        charts.showProjectEndedByYear();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        charts.showProjectSeasonality();
    }//GEN-LAST:event_jButton2ActionPerformed

    
    //showing bar chart
    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        BarChart app = new BarChart("Top Participant Countries");
        BarChart chart = new BarChart("Bar Chart");
        chart.setSize(800, 400);
        chart.setLocationRelativeTo(null);
        chart.setVisible(true);
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    
    //showing funding scheme
    private void TopFundingSchemesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TopFundingSchemesActionPerformed
        charts.showTopFundingSchemes();
    }//GEN-LAST:event_TopFundingSchemesActionPerformed

    
    //showing larger organization table
    private void organizationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organizationButtonActionPerformed
        new Organization().setVisible(true);
    }//GEN-LAST:event_organizationButtonActionPerformed

    private void avgTCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avgTCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_avgTCActionPerformed

    private void avgECActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_avgECActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_avgECActionPerformed

    private void maxECActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxECActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maxECActionPerformed

    
    //log out action
    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        // TODO add your handling code here:
    String query = " UPDATE USER set LOGOUT = datetime('now') WHERE username ='" +username+"'"; 
        try{
        pst = conn1.prepareStatement(query);
        pst.execute();

            this.dispose();
            Login_form f = new Login_form();
            f.setVisible(true);
        }
        catch(Exception ex){

            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_logoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Table.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //setting background
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Table().setVisible(true);
                    JFrame frame = new JFrame();
                    ImageIcon icon = new ImageIcon("backgroundDashboard.jpg");
                    JLabel label = new JLabel(icon);
                    frame.add(label);
                 
                    frame.pack();
                    frame.setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable FundSchemeXProject;
    private javax.swing.JTable Project;
    private javax.swing.JTable TableParticipant;
    private javax.swing.JButton TopFundingSchemes;
    private javax.swing.JLabel average;
    private javax.swing.JTextField avgEC;
    private javax.swing.JTextField avgTC;
    private javax.swing.JLabel countriesPart;
    private javax.swing.JLabel ecContribution;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JButton logout;
    private javax.swing.JLabel max;
    private javax.swing.JTextField maxEC;
    private javax.swing.JTextField maxTC;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JLabel min;
    private javax.swing.JTextField minEC;
    private javax.swing.JTextField minTC;
    private java.awt.Button ok;
    private javax.swing.JToggleButton organizationButton;
    private javax.swing.JLabel parameter;
    private java.awt.Button seemore;
    private javax.swing.JLabel signed_pro;
    private javax.swing.JLabel totalCost;
    // End of variables declaration//GEN-END:variables
}
