package br.com.lazaro.estruturas;

import java.util.NoSuchElementException;

/** Fila FIFO com referencias para inicio e fim. */
public final class Fila {
    private static final class No {
        final int valor;
        No proximo;
        No(int valor) { this.valor = valor; }
    }
    private No inicio;
    private No fim;
    private int tamanho;

    public void enqueue(int valor) {
        No novo = new No(valor);
        if (fim == null) inicio = novo;
        else fim.proximo = novo;
        fim = novo;
        tamanho++;
    }
    public int dequeue() {
        int valor = front();
        inicio = inicio.proximo;
        tamanho--;
        if (inicio == null) fim = null;
        return valor;
    }
    public int front() {
        if (isEmpty()) throw new NoSuchElementException("Fila vazia");
        return inicio.valor;
    }
    public int rear() {
        if (isEmpty()) throw new NoSuchElementException("Fila vazia");
        return fim.valor;
    }
    public int size() { return tamanho; }
    public boolean isEmpty() { return tamanho == 0; }
}
