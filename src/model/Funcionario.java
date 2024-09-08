package model;

import service.Bandeijao;

public class Funcionario extends Thread {
    Bandeijao bandeijao;

    public Funcionario(Bandeijao b) {
        this.bandeijao = b;
    }

    @Override
    public void run() {
        try {
            while (true) { // while (true) o funcionario sempre vai ficar tentando repor os alimentos
                bandeijao.reporAlimentos();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
