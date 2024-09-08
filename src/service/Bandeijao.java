package service;

import interfaceGrafica.InterfaceGrafica;

import java.util.Random;
import java.util.concurrent.Semaphore;

//Starvation = quando um processo nunca é executado;
//Deadlock = quando dois ou mais processos estão impedidos de continuar a executar
//Race condition = quando duas threads tentam modificar o mesmo recurso simultaneamente, gerando uma inconstiencia.

public class Bandeijao {
    private boolean alimentosDisponiveis = true;
    private boolean reposicaoNecessaria = false;
    private final Semaphore vaga = new Semaphore(5); //semaforo com a contagem max de 5 pessoas, bloqueia se = 0 (decremento)
    private int pessoasNaPista = 0;
    private final Semaphore reposicao = new Semaphore(0);
    private final Random random = new Random();
    private int totalServidas = 0;
    private final InterfaceGrafica i;
    private final Semaphore mutex = new Semaphore(1); //Syncronyzed nao resolveu, entao usando sempahore pois a thread nao precisa de reentrancia

    public Bandeijao(InterfaceGrafica i) {
        this.i = i;
    }


    public void acessarPista(String pessoa) throws InterruptedException{
        try {
            vaga.acquire(); //contador semaforo para cada acesso na pista
            mutex.acquire(); //controla o acesso para uma thread por vez

            while (!alimentosDisponiveis) { //trava a pista
                mutex.release();  // Libera o mutex enquanto espera a reposição
                Thread.sleep(500);  // Simula o tempo de espera pela reposição
                mutex.acquire();  // Re-adquire o mutex para verificar novamente
            }

            pessoasNaPista++;
            i.attSaida(pessoa + " está se servindo. Pessoas na pista: " + pessoasNaPista);
            i.attPista(pessoasNaPista);

            double probAcabarComida = 0.3;
            if (random.nextDouble() < probAcabarComida) {
                esgotarAlimentos();
            }

            mutex.release();
            Thread.sleep(500); //tempo para sevir
            deixarPista(pessoa);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Reinterrompe a thread
            i.attSaida("Erro: a operação foi interrompida para " + pessoa);
        }

        //synchronized (this) { //uma thread por vez, evitando condições de corrida.
        //            while (!alimentosDisponiveis) {
        //                wait(); // Aguarda até que os alimentos estejam disponíveis
        //            }
        //            Thread.sleep(2000);
        //            pessoasNaPista++;
        //            i.attSaida(pessoa + " está se servindo. Pessoas na pista: " + pessoasNaPista);
        //            i.attPista(pessoasNaPista);
        //
        //            double probAcabarComida = 0.3;
        //            if (random.nextDouble() < probAcabarComida) {
        //                esgotarAlimentos();
        //            }
        //        } syncronyzed nao resolveu o problema
    }

    public void esgotarAlimentos(){
        if(alimentosDisponiveis) {
            alimentosDisponiveis = false;
            reposicaoNecessaria = true;
            i.attSaida("----- Alimentos indisponiveis!!");
        }

        //synchronized (this) {
        //            if(alimentosDisponiveis) {
        //                alimentosDisponiveis = false;
        //                reposicaoNecessaria = true;
        //                i.attSaida("----- Alimentos indisponiveis!!");
        //                notifyAll(); //verifica disponibilidade dos alimentos
        //            }
    }

    public void deixarPista(String pessoa) throws InterruptedException {
        try {
            mutex.acquire();
            pessoasNaPista--;
            i.attPista(pessoasNaPista);
            i.attSaida(pessoa + " deixou a pista. Pessoas na pista: " + pessoasNaPista );
            if(pessoasNaPista == 0 && reposicaoNecessaria) {
                reposicao.release();
            }
            totalServidas++;
            i.attTotal(totalServidas);
            mutex.release();
            vaga.release(1); //libera uma vaga na pista
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Reinterrompe a thread
            i.attSaida("Erro: a operação foi interrompida para " + pessoa);
        }

        //synchronized (this) {
        //            pessoasNaPista--;
        //            i.attPista(pessoasNaPista);
        //            i.attSaida(pessoa + " deixou a pista. Pessoas na pista: " + pessoasNaPista );
        //
        //            if(pessoasNaPista == 0 && reposicaoNecessaria){
        //                reposicao.release();
        //            }
        //        }

    }

    public void reporAlimentos() throws InterruptedException {
        try {
            reposicao.acquire();
            mutex.acquire();
            alimentosDisponiveis = true;
            reposicaoNecessaria = false;
            i.attSaida("----- Funcionário repondo os alimentos");
            Thread.sleep(500); // Simula o tempo de reposição
            i.attSaida("----- Alimentos disponíveis!!");
            mutex.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Reinterrompe a thread
            i.attSaida("Erro: operação de reposição foi interrompida");
        }

        //synchronized (this) {
        //            alimentosDisponiveis = true;
        //            reposicaoNecessaria = false;
        //            i.attSaida("----- Funcionário repondo os alimentos");
        //            Thread.sleep(2000); // Simula o tempo de reposição
        //            i.attSaida("----- Alimentos disponíveis!!");
        //            notifyAll(); //thread verifica disponibilidade dos alimentos
        //        }
    }
}
