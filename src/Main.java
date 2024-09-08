
import interfaceGrafica.*;

import javax.swing.*;

public class Main {
        public static void main(String[] args) {
            JFrame frame = new InterfaceGrafica();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Bandeijão do IF");
            frame.setSize(400,400);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        }
}