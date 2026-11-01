package br.com.lazaro.estruturas;

import java.util.NoSuchElementException;

/** Lista duplamente encadeada, com indices iniciados em zero. */
public final class ListaEncadeada {
    /** No retornado por pop/remove; o valor e publico por meio do getter. */
    public static final class No {
        private final int valor;
        private No anterior;
        private No proximo;
        private No(int valor) { this.valor = valor; }
        public int getValor() { return valor; }
        @Override public String toString() { return Integer.toString(valor); }
    }
    private No inicio;
    private No fim;
    private int tamanho;

    public void push(int valor) {
        No novo = new No(valor);
        novo.anterior = fim;
        if (fim == null) inicio = novo;
        else fim.proximo = novo;
        fim = novo;
        tamanho++;
    }
    public No pop() {
        if (tamanho == 0) throw new NoSuchElementException("Lista vazia");
        return desligar(fim);
    }
    /** Insere antes do indice indicado; indice size() acrescenta ao fim. */
    public void insert(int indice, int valor) {
        if (indice < 0 || indice > tamanho) throw new IndexOutOfBoundsException(indice);
        if (indice == tamanho) { push(valor); return; }
        No atual = noEm(indice);
        No novo = new No(valor);
        novo.anterior = atual.anterior;
        novo.proximo = atual;
        if (atual.anterior == null) inicio = novo;
        else atual.anterior.proximo = novo;
        atual.anterior = novo;
        tamanho++;
    }
    public No remove(int indice) { return desligar(noEm(indice)); }
    public int elementAt(int indice) { return noEm(indice).valor; }
    public int size() { return tamanho; }
    public String printList() {
        StringBuilder texto = new StringBuilder("[");
        No atual = inicio;
        while (atual != null) {
            texto.append(atual.valor);
            atual = atual.proximo;
            if (atual != null) texto.append(", ");
        }
        return texto.append(']').toString();
    }
    private No noEm(int indice) {
        if (indice < 0 || indice >= tamanho) throw new IndexOutOfBoundsException(indice);
        if (indice < tamanho / 2) {
            No atual = inicio;
            for (int i = 0; i < indice; i++) atual = atual.proximo;
            return atual;
        }
        No atual = fim;
        for (int i = tamanho - 1; i > indice; i--) atual = atual.anterior;
        return atual;
    }
    private No desligar(No atual) {
        if (atual.anterior == null) inicio = atual.proximo;
        else atual.anterior.proximo = atual.proximo;
        if (atual.proximo == null) fim = atual.anterior;
        else atual.proximo.anterior = atual.anterior;
        // O no devolvido nao retem referencias para o restante da lista.
        atual.anterior = null;
        atual.proximo = null;
        tamanho--;
        return atual;
    }
}
