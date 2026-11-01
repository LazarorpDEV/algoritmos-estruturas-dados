package br.com.lazaro.estruturas;

import java.util.NoSuchElementException;

/** Pilha LIFO de inteiros, implementada com nos proprios. */
public final class Pilha {
    private static final class No {
        final int valor;
        final No proximo;
        No(int valor, No proximo) { this.valor = valor; this.proximo = proximo; }
    }
    private No topo;
    private int tamanho;

    public void push(int valor) {
        topo = new No(valor, topo);
        tamanho++;
    }
    public int pop() {
        int valor = top();
        topo = topo.proximo;
        tamanho--;
        return valor;
    }
    public int top() {
        if (isEmpty()) throw new NoSuchElementException("Pilha vazia");
        return topo.valor;
    }
    public boolean isEmpty() { return tamanho == 0; }
    public int size() { return tamanho; }
}
