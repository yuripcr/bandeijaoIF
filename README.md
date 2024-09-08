# Problema 1: Pista Quente e Fria do bandejão do IF

## Descrição do Problema

O bandejão do IFSULDEMINAS - campus Poços de Caldas precisa controlar o acesso à Pista Quente e Fria, que é a área de self-service onde alunos e servidores se servem. A pista suporta até 5 pessoas simultaneamente. Se estiver cheia, as pessoas devem esperar em uma fila.

Além disso, um funcionário do restaurante deve repor os alimentos na pista de tempos em tempos. A reposição só pode ser feita quando não houver ninguém se servindo, e a pista só pode ser utilizada se houver comida disponível. A solução deve garantir que:

1. Não ocorra **starvation** (fome) - todos na fila devem eventualmente se servir.
2. Não ocorra **deadlock** - o sistema não pode travar.
3. Não ocorra **race condition** - deve haver controle adequado de concorrência.

## Regras

- Máximo de 5 pessoas podem se servir ao mesmo tempo.
- Quando uma pessoa passa pela catraca, ela deve aguardar caso a pista já esteja cheia.
- O funcionário só pode repor alimentos quando não houver ninguém na pista.
- Se a bandeja estiver vazia, as pessoas não podem se servir até que os alimentos sejam repostos.

## Proposta de Solução

### Sincronização de Processos

A solução será implementada utilizando conceitos de **sincronização de processos** para controlar o acesso à pista e à reposição de alimentos, eliminando qualquer chance de starvation, deadlock e race condition.

### Mecanismos Utilizados

- **Semáforos**: para controlar o número máximo de pessoas na pista e coordenar o acesso entre quem está se servindo e o funcionário que repõe os alimentos.
- **Mutex (exclusão mútua)**: para garantir que apenas uma ação (servir-se ou repor alimentos) ocorra de cada vez na pista.
- **Fila de espera**: as pessoas aguardam a liberação da pista quando a lotação máxima é atingida.

### Funcionalidades da Solução

1. **Controle de acesso à pista**: até 5 pessoas podem se servir simultaneamente.
2. **Fila de espera**: se a pista estiver cheia, as pessoas entram em uma fila e aguardam sua vez.
3. **Reposição de alimentos**: ocorre apenas quando a pista está vazia.
4. **Garantia de sincronização**: o sistema garante que não haja starvation (fome), deadlock ou race condition.

### Fluxo Básico do Sistema

1. **Pessoa chega à pista**:
    - Verifica se há vagas na pista (máximo 5 pessoas).
    - Se houver vagas, entra e se serve.
    - Se não houver vagas, aguarda em uma fila até que uma pessoa saia.

2. **Funcionário repõe os alimentos**:
    - Aguarda até que a pista esteja completamente vazia.
    - Reabastece as bandejas.

3. **Pessoa termina de se servir**:
    - Sai da pista, liberando espaço para a próxima pessoa da fila.

## Tecnologias

- Linguagem de programação: Java.
- Estruturas de dados e controle de processos: Semáforos, mutex, fila de espera.


