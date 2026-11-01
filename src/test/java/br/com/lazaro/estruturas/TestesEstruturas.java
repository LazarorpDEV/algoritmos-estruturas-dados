package br.com.lazaro.estruturas;

import java.util.NoSuchElementException;
import java.util.Random;

/** Testes executaveis sem dependencias externas; falhas encerram com codigo != 0. */
public final class TestesEstruturas {
    private static int verificacoes;
    private static void igual(Object esperado, Object atual) {
        verificacoes++;
        if (!esperado.equals(atual))
            throw new AssertionError("Esperado: " + esperado + "; recebido: " + atual);
    }
    private static void falha(Class<? extends Throwable> tipo, Runnable acao) {
        verificacoes++;
        try { acao.run(); }
        catch (Throwable erro) {
            if (tipo.isInstance(erro)) return;
            throw new AssertionError("Excecao inesperada", erro);
        }
        throw new AssertionError("Deveria lancar " + tipo.getSimpleName());
    }
    public static void main(String[] args) {
        testarPilha(); testarFila(); testarLista(); testarMapa();
        System.out.println("TESTES OK: " + verificacoes + " verificacoes aprovadas.");
    }
    private static void testarPilha() {
        Pilha p = new Pilha();
        igual(true, p.isEmpty()); igual(0, p.size());
        falha(NoSuchElementException.class, p::pop);
        falha(NoSuchElementException.class, p::top);
        for (int i = -100; i <= 100; i++) p.push(i);
        igual(201, p.size()); igual(100, p.top()); igual(201, p.size());
        for (int i = 100; i >= -100; i--) igual(i, p.pop());
        igual(true, p.isEmpty());
        p.push(Integer.MIN_VALUE); igual(Integer.MIN_VALUE, p.pop());
    }
    private static void testarFila() {
        Fila f = new Fila();
        igual(true, f.isEmpty());
        falha(NoSuchElementException.class, f::dequeue);
        falha(NoSuchElementException.class, f::front);
        falha(NoSuchElementException.class, f::rear);
        for (int i = -100; i <= 100; i++) f.enqueue(i);
        igual(-100, f.front()); igual(100, f.rear()); igual(201, f.size());
        for (int i = -100; i <= 100; i++) igual(i, f.dequeue());
        igual(true, f.isEmpty()); igual(0, f.size());
        // Reutilizar apos esvaziar testa a limpeza da referencia ao fim.
        f.enqueue(7); igual(7, f.front()); igual(7, f.rear()); igual(7, f.dequeue());
    }
    private static void testarLista() {
        ListaEncadeada l = new ListaEncadeada();
        igual("[]", l.printList());
        falha(NoSuchElementException.class, l::pop);
        falha(IndexOutOfBoundsException.class, () -> l.elementAt(0));
        falha(IndexOutOfBoundsException.class, () -> l.remove(0));
        falha(IndexOutOfBoundsException.class, () -> l.insert(-1, 0));
        falha(IndexOutOfBoundsException.class, () -> l.insert(1, 0));
        l.insert(0, 20); l.insert(0, 10); l.insert(2, 40); l.insert(2, 30);
        igual("[10, 20, 30, 40]", l.printList());
        igual(10, l.remove(0).getValor()); igual(30, l.remove(1).getValor());
        igual(40, l.pop().getValor()); igual(20, l.pop().getValor());
        igual("[]", l.printList()); igual(0, l.size());
        // Modelo independente em array: mistura insercoes e remocoes em 2000 passos.
        int[] modelo = new int[2000]; int n = 0;
        Random sorteio = new Random(42);
        for (int passo = 0; passo < 2000; passo++) {
            int operacao = sorteio.nextInt(4);
            if (n == 0 || operacao < 2) {
                int indice = sorteio.nextInt(n + 1), valor = sorteio.nextInt();
                if (operacao == 0) { indice = n; l.push(valor); }
                else l.insert(indice, valor);
                for (int j = n; j > indice; j--) modelo[j] = modelo[j - 1];
                modelo[indice] = valor; n++;
            } else {
                int indice = operacao == 2 ? n - 1 : sorteio.nextInt(n);
                igual(modelo[indice], operacao == 2 ? l.pop().getValor() : l.remove(indice).getValor());
                for (int j = indice; j < n - 1; j++) modelo[j] = modelo[j + 1];
                n--;
            }
            igual(n, l.size());
            StringBuilder esperado = new StringBuilder("[");
            for (int j = 0; j < n; j++) {
                igual(modelo[j], l.elementAt(j));
                if (j > 0) esperado.append(", ");
                esperado.append(modelo[j]);
            }
            igual(esperado.append(']').toString(), l.printList());
            final int limite = n;
            falha(IndexOutOfBoundsException.class, () -> l.elementAt(limite));
        }
    }
    private static void testarMapa() {
        MapaHash m = new MapaHash();
        falha(NoSuchElementException.class, () -> m.get(0));
        falha(NoSuchElementException.class, () -> m.delete(0));
        // Dez chaves que colidem, com retorno circular ao inicio do array.
        for (int i = 0; i < 10; i++) m.put(9 + 10 * i, i);
        igual(10, m.size());
        for (int i = 0; i < 10; i++) igual(i, m.get(9 + 10 * i));
        falha(IllegalStateException.class, () -> m.put(999, 1));
        m.put(99, 999); igual(999, m.get(99)); igual(10, m.size());
        igual(0, m.delete(9)); igual(999, m.get(99));
        m.put(99, 1000); igual(9, m.size()); // Nao duplica apos um removido.
        m.put(-1, -10); igual(10, m.size()); igual(-10, m.get(-1));
        for (int i = 1; i < 10; i++) m.delete(9 + 10 * i);
        m.delete(-1); igual(0, m.size());
        falha(NoSuchElementException.class, () -> m.get(123)); // Todos removidos.
        m.put(Integer.MIN_VALUE, Integer.MAX_VALUE);
        m.put(Integer.MAX_VALUE, Integer.MIN_VALUE);
        m.put(0, 0);
        igual(Integer.MAX_VALUE, m.get(Integer.MIN_VALUE));
        igual(Integer.MIN_VALUE, m.get(Integer.MAX_VALUE)); igual(0, m.get(0));
        m.clear(); m.clear(); igual(0, m.size());
        falha(NoSuchElementException.class, () -> m.get(Integer.MIN_VALUE));
        for (int i = 0; i < 10; i++) m.put(i, i);
        igual(10, m.size());
    }
}
