package dashboard;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


//Beatriz Vassiliadis

public class Organization extends javax.swing.JFrame {



    JComboBox<String> countriesList;
    Connection con = null;
    DefaultTableModel tableModel;
    JCheckBox participantFilter;
    JCheckBox partnerFilter;
    JCheckBox coordinatorFilter;

    public void reloadTableData() {
        Statement st = null;
        ResultSet rs = null;
        try {
            String filter = "";

            String selectedCountry = (String) countriesList.getSelectedItem();
            if (selectedCountry != null && !selectedCountry.equals("All")) {
                filter += " AND a.countryName = '" + selectedCountry + "' ";
            }

            if (!participantFilter.isSelected()) {
                filter += " AND b.role <> 'participant' ";
            }

            if (!partnerFilter.isSelected()) {
                filter += " AND b.role <> 'partner' ";
            }

            if (!coordinatorFilter.isSelected()) {
                filter += " AND b.role <> 'coordinator' ";
            }

            String sql = "SELECT a.countryName as Country, b.role as Role, b.name as Organization FROM Country a, Participant b WHERE b.country = a.countryID " + filter;
            
            System.out.println(sql);
            
            st = con.createStatement();
            rs = st.executeQuery(sql);
            ResultSetMetaData rsmt = rs.getMetaData();
            int col = rsmt.getColumnCount();

            Vector data = new Vector();
            Vector row = new Vector();

            // Remove all elements from table
            int rowCount = tableModel.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                tableModel.removeRow(i);
            }

            // Iterate over select cursor
            while (rs.next()) {
                row = new Vector(col);

                for (int i = 1; i <= col; i++) {
                    row.add(rs.getString(i));
                }

                tableModel.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                st.close();
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Organization() {
        //Connect the table to the database 
        try {
            con = DriverManager.getConnection("jdbc:sqlite:cordis2.db", "root", "");

            //Frame
            JFrame frame = new JFrame("Organization Table");
            frame.setLayout(new BorderLayout());
            frame.setSize(800, 600);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel1 = new JPanel();
            panel1.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.PAGE_END;
            gbc.fill = GridBagConstraints.PAGE_END;

            String s = "SELECT a.countryName as Country, b.role as Role, b.name as Organization FROM Country a, Participant b WHERE b.country = a.countryID limit 1";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(s);
            ResultSetMetaData rsmt = rs.getMetaData();
            int col = rsmt.getColumnCount();
            tableModel = new DefaultTableModel();


            //Defines all columns table
            for (int i = 1; i <= col; i++) {
                tableModel.addColumn(rsmt.getColumnName(i));
            }

            JTable table = new JTable(tableModel);
            table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 16));
            table.setGridColor(Color.BLACK);
            table.setForeground(Color.darkGray);
            JScrollPane jsp = new JScrollPane(table);
            panel1.setLayout(new BorderLayout());
            panel1.add(jsp, BorderLayout.CENTER);

            frame.add(panel1);
            frame.setVisible(true);
            frame.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));

            participantFilter = new JCheckBox("Participant");
            partnerFilter = new JCheckBox("Partner");
            coordinatorFilter = new JCheckBox("Coordinator");

            panel1.setSize(1300, 600);

            Font font = new Font("Verdana", Font.BOLD, 16);
            JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            frame.add(panel2, BorderLayout.NORTH);
            panel2.setSize(400, 200);

            panel2.add(partnerFilter);
            panel2.add(coordinatorFilter);
            panel2.add(participantFilter);

            //Catch the change event
            partnerFilter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    reloadTableData();
                }
            });

            //Catch the click event
            coordinatorFilter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    reloadTableData();
                }
            });

            //Catch the click event
            participantFilter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    reloadTableData();
                }
            });

            //Catch the click event
            participantFilter.setSelected(true);
            coordinatorFilter.setSelected(true);
            partnerFilter.setSelected(true);
            participantFilter.setFont(font);
            coordinatorFilter.setFont(font);
            partnerFilter.setFont(font);

            String s1 = "SELECT distinct a.countryName as Country from Country a, Participant b WHERE b.country = a.countryID;";
            rs = st.executeQuery(s1);

            ArrayList<String> items = new ArrayList<String>();

            while (rs.next()) {
                items.add(rs.getString("Country"));
            }

            String[] countries = new String[items.size() + 1];
            countries[0] = "All";
            for (int i = 0; i < items.size(); i++) {
                countries[i + 1] = items.get(i);
            }

            countriesList = new JComboBox<String>(countries);
            countriesList.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    reloadTableData();
                }
            });

            panel2.add(countriesList);
            countriesList.setFont(font);

            reloadTableData();

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR");
            e.printStackTrace();
        } finally {
            try {

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ERROR CLOSE");
                e.printStackTrace();
            }
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        organizationButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        organizationButton.setText("Click here to filter data");
        organizationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                organizationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(171, Short.MAX_VALUE)
                .addComponent(organizationButton)
                .addGap(71, 71, 71))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(organizationButton)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void organizationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_organizationButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_organizationButtonActionPerformed

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
            java.util.logging.Logger.getLogger(Organization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Organization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Organization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Organization.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton organizationButton;
    // End of variables declaration//GEN-END:variables
}
