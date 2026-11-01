package br.com.lazaro.estruturas;

public final class Main {
    public static void main(String[] args) {
        Pilha pilha = new Pilha();
        pilha.push(10); pilha.push(20); pilha.push(30);
        System.out.println("Pilha: topo=" + pilha.top() + ", tamanho=" + pilha.size());
        while (!pilha.isEmpty()) System.out.println("pop: " + pilha.pop());

        Fila fila = new Fila();
        fila.enqueue(10); fila.enqueue(20); fila.enqueue(30);
        System.out.println("Fila: frente=" + fila.front() + ", fim=" + fila.rear());
        while (!fila.isEmpty()) System.out.println("dequeue: " + fila.dequeue());

        ListaEncadeada lista = new ListaEncadeada();
        lista.push(10); lista.push(30); lista.insert(1, 20);
        System.out.println("Lista: " + lista.printList());
        System.out.println("elementAt(1): " + lista.elementAt(1));
        System.out.println("remove(1): " + lista.remove(1).getValor());
        System.out.println("pop: " + lista.pop().getValor());
        System.out.println("Lista final: " + lista.printList());

        MapaHash mapa = new MapaHash();
        mapa.put(1, 100); mapa.put(11, 200); mapa.put(21, 300);
        mapa.delete(11);
        System.out.println("Mapa: chave 21 apos colisao e exclusao=" + mapa.get(21));
        mapa.put(1, 999);
        System.out.println("Mapa: chave 1 atualizada=" + mapa.get(1));
        mapa.clear();
        System.out.println("Mapa apos clear: " + mapa.size());
    }
}
