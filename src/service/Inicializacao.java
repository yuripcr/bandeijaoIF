package service;

import interfaceGrafica.*;
import model.*;

public class Inicializacao {
    InterfaceGrafica iG;
    Bandeijao bandeijao;
    Funcionario funcionario;

    private final int nPessoas;

    public Inicializacao(int nPessoas, InterfaceGrafica i) {
        this.nPessoas = nPessoas;
        this.iG = i;
        this.bandeijao = new Bandeijao(iG);
        this.funcionario = new Funcionario(bandeijao);
    }

    public void iniciar(){
        for (int i = 1; i <= nPessoas; i++) {
            Pessoa pessoa = new Pessoa(bandeijao);
            pessoa.start();
        }

        funcionario.start();
    }
}
