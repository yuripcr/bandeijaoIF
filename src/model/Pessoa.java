package model;

import service.Bandeijao;
import java.util.Random;

public class Pessoa extends Thread{
    private String pessoa;
    Bandeijao bandeijao;

    public Pessoa(Bandeijao b){
        this.pessoa = RandomPessoa();
        this.bandeijao = b;
    }

    @Override
    public void run() {
        try{
            bandeijao.acessarPista(pessoa);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String RandomPessoa() {
        int tipo = new Random().nextInt(3);
        return switch (tipo) {
            case 0 -> "Aluno do integrado";
            case 1 -> "Aluno do superior";
            case 2 -> "Servidor";
            default -> "";
        };
    }
}

