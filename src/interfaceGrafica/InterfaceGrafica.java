package interfaceGrafica;

import service.Inicializacao;
import javax.swing.*;

public class InterfaceGrafica extends JFrame {
    private JSpinner spinner1;
    private JTextArea textArea1;
    private JLabel totalPessoas;
    private JLabel naPista;
    private JButton iniciarButton;
    private JPanel mainPanel;

    public InterfaceGrafica() {
        App();
    }


    public void App() {
        setContentPane(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        textArea1.setVisible(true);
        textArea1.setEditable(false);


        iniciarButton.addActionListener(_ -> {
            textArea1.setText("");
            naPista.setText("Pessoas na pista: 0");
            totalPessoas.setText("Total de pessoas servidas: 0");
            Inicializacao i = new Inicializacao((Integer) spinner1.getValue(), this);
            i.iniciar();
        });
    }

    public void attSaida(String saida){
        SwingUtilities.invokeLater(() -> {
            textArea1.append(saida + "\n");
            textArea1.setCaretPosition(textArea1.getDocument().getLength());
        });
    }

    public void attPista(int n) {
        SwingUtilities.invokeLater(() -> naPista.setText("Pessoas na pista: " + n));
    }

    public void attTotal(int n) {
        SwingUtilities.invokeLater(() -> totalPessoas.setText("Total de pessoas servidas: " + n));
    }
}
