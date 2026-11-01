package br.com.lazaro.estruturas;

import java.util.NoSuchElementException;

/** Mapa int -> int: dez posicoes, sondagem linear e marcacao de removidos. */
public final class MapaHash {
    public static final int CAPACIDADE = 10;
    private static final byte LIVRE = 0, OCUPADO = 1, REMOVIDO = 2;
    private final int[] chaves = new int[CAPACIDADE];
    private final int[] valores = new int[CAPACIDADE];
    private final byte[] estados = new byte[CAPACIDADE];
    private int tamanho;

    private int hash(int chave) {
        // long evita overflow; floorMod aceita inclusive Integer.MIN_VALUE.
        return (int) Math.floorMod(31L * chave, (long) CAPACIDADE);
    }
    public void put(int chave, int valor) {
        int primeiroRemovido = -1;
        int inicio = hash(chave);
        for (int passo = 0; passo < CAPACIDADE; passo++) {
            int i = (inicio + passo) % CAPACIDADE;
            if (estados[i] == OCUPADO && chaves[i] == chave) {
                valores[i] = valor;
                return;
            }
            if (estados[i] == REMOVIDO && primeiroRemovido == -1) primeiroRemovido = i;
            if (estados[i] == LIVRE) {
                gravar(primeiroRemovido == -1 ? i : primeiroRemovido, chave, valor);
                return;
            }
        }
        if (primeiroRemovido != -1) { gravar(primeiroRemovido, chave, valor); return; }
        throw new IllegalStateException("Mapa cheio: limite de 10 chaves distintas");
    }
    private void gravar(int indice, int chave, int valor) {
        chaves[indice] = chave;
        valores[indice] = valor;
        estados[indice] = OCUPADO;
        tamanho++;
    }
    public int get(int chave) { return valores[localizar(chave)]; }
    /** Retorna o valor removido. Chave ausente gera excecao. */
    public int delete(int chave) {
        int indice = localizar(chave);
        int valor = valores[indice];
        estados[indice] = REMOVIDO;
        chaves[indice] = 0;
        valores[indice] = 0;
        tamanho--;
        return valor;
    }
    private int localizar(int chave) {
        int inicio = hash(chave);
        for (int passo = 0; passo < CAPACIDADE; passo++) {
            int i = (inicio + passo) % CAPACIDADE;
            if (estados[i] == LIVRE) break;
            if (estados[i] == OCUPADO && chaves[i] == chave) return i;
        }
        throw new NoSuchElementException("Chave nao encontrada: " + chave);
    }
    public void clear() {
        for (int i = 0; i < CAPACIDADE; i++) {
            estados[i] = LIVRE;
            chaves[i] = 0;
            valores[i] = 0;
        }
        tamanho = 0;
    }
    public int size() { return tamanho; }
}
