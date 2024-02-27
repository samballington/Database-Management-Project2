//@author Sam Ballington
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements Runnable {
    private JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Main());
    }

    public void run() {
        frame = new JFrame("Database Project 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton query1Button = createStyledButton("Query 1");
        JButton query2Button = createStyledButton("Query 2");
        JButton query3Button = createStyledButton("Query 3");
        JButton query4Button = createStyledButton("Query 4");
        JButton query5Button = createStyledButton("Query 5");
        JButton query6Button = createStyledButton("Query 6");

        JTextArea resultTextArea = createResultTextArea();

        // Set up the results box
        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        frame.add(scrollPane, BorderLayout.CENTER);

        // Add query buttons to the left side
        JPanel buttonsPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        buttonsPanel.add(query1Button);
        buttonsPanel.add(query2Button);
        buttonsPanel.add(query3Button);
        buttonsPanel.add(query4Button);
        buttonsPanel.add(query5Button);
        buttonsPanel.add(query6Button);

        // Add buttons panel to the west side
        frame.add(buttonsPanel, BorderLayout.WEST);

        // Action listeners for each query button
        query1Button.addActionListener(new QueryButtonActionListener(Query1::executeQuery, resultTextArea));
        query2Button.addActionListener(new QueryButtonActionListener(Query2::executeQuery, resultTextArea));
        query3Button.addActionListener(new QueryButtonActionListener(Query3::executeQuery, resultTextArea));
        query4Button.addActionListener(new QueryButtonActionListener(Query4::executeQuery, resultTextArea));
        query5Button.addActionListener(new QueryButtonActionListener(Query5::executeQuery, resultTextArea));
        query6Button.addActionListener(new QueryButtonActionListener(Query6::executeQuery, resultTextArea));

        frame.setSize(800, 600);  // Set initial size
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(135, 206, 250));  // Light Sky Blue
        button.setFocusPainted(false);
        return button;
    }

    private JTextArea createResultTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 18));
        return textArea;
    }

    private static class QueryButtonActionListener implements ActionListener {
        private final QueryExecutor queryExecutor;
        private final JTextArea resultTextArea;

        public QueryButtonActionListener(QueryExecutor queryExecutor, JTextArea resultTextArea) {
            this.queryExecutor = queryExecutor;
            this.resultTextArea = resultTextArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String result = queryExecutor.executeQuery();
            resultTextArea.setText(result);
        }
    }

    private interface QueryExecutor {
        String executeQuery();
    }
}