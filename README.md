# Atividade — Algoritmos e estruturas de dados

Projeto Java com implementação própria de pilha, fila FIFO, lista duplamente
encadeada e mapa de hash. Não utiliza Stack, Queue, Deque, LinkedList,
ArrayList ou HashMap para construir as estruturas. Os únicos arrays de
armazenamento nas implementações são os arrays primitivos do mapa.

## Requisitos

JDK 17 ou superior, com `java` e `javac` disponíveis no PATH.
Não requer Maven, banco de dados, bibliotecas externas ou acesso à internet.

## Executar no Windows

1. Extraia o ZIP.
2. Abra a pasta `Atividade_Estruturas_Dados` no VS Code.
3. Abra o terminal PowerShell nessa pasta.
4. Confira `java -version` e `javac -version`.
5. Execute:

```powershell
.\executar.bat
```

O script compila o projeto, executa os testes e apresenta uma demonstração.
Se o terminal estiver aberto fora da pasta, entre nela com `cd` primeiro.

## Executar no Linux ou macOS

```bash
bash executar.sh
```

## Executar manualmente

Crie a pasta `out` se ainda não existir. Na raiz do projeto:

```text
javac -encoding UTF-8 -d out src/main/java/br/com/lazaro/estruturas/*.java src/test/java/br/com/lazaro/estruturas/*.java
java -cp out br.com.lazaro.estruturas.TestesEstruturas
java -cp out br.com.lazaro.estruturas.Main
```

## Contratos dos métodos

| Classe | Métodos |
| --- | --- |
| Pilha | `push(int)`, `pop(): int`, `top(): int`, `isEmpty(): boolean`, `size(): int` |
| Fila | `enqueue(int)`, `dequeue(): int`, `rear(): int`, `front(): int`, `size(): int`, `isEmpty(): boolean` |
| ListaEncadeada | `push(int)`, `pop(): No`, `insert(int indice, int valor)`, `remove(int indice): No`, `elementAt(int indice): int`, `size(): int`, `printList(): String` |
| MapaHash | `put(int chave, int valor)`, `delete(int chave): int`, `get(int chave): int`, `clear()`, `size(): int` |

A lista usa índices a partir de zero. `insert` aceita índices de 0 até size();
`remove` e `elementAt` aceitam 0 até size() - 1. `pop` e `remove` devolvem o
nó desligado, cujo inteiro é acessado com `getValor()`. Os ponteiros são
privados para impedir alterações externas nas ligações.

Exemplo: `lista.insert(1, 20)` insere o valor 20 no índice 1.
`printList()` retorna um texto como `[10, 20, 30]`; a classe Main imprime esse
retorno com `System.out.println(lista.printList())`.

Pilha/fila/lista vazias nas operações de leitura/remoção sem índice lançam
`NoSuchElementException`. Índices inválidos lançam `IndexOutOfBoundsException`.
No mapa, chaves ausentes em get/delete lançam `NoSuchElementException`, e
uma nova chave em mapa cheio lança `IllegalStateException`. Isso permite
armazenar qualquer inteiro, inclusive zero e negativos, sem usar um valor
sentinela ambíguo. Atualizar uma chave existente não aumenta size().

## Testes e análise

`TestesEstruturas.java` contém testes próprios, sem JUnit. Cada comparação
lança AssertionError se falhar, independentemente da opção `-ea` da JVM.
Há testes de LIFO/FIFO, consultas sem remoção, estruturas vazias,
reutilização, índices inválidos, mudanças nas extremidades, colisões,
mapa cheio, exclusões, atualização e valores extremos. A lista passa ainda
por 2.000 operações reproduzíveis comparadas com um modelo em array.

Leia `ANALISE_COMPLEXIDADE.txt` para as justificativas de tempo, espaço,
escolha da função de hash e tratamento das colisões. O arquivo
`RESULTADO_VALIDACAO.txt` registra a execução realizada na preparação.

## Entrega

Revise o código e a análise, execute no seu computador e envie conforme o
campo da atividade na EBAC: o ZIP, se aceitar arquivo, ou o link do
repositório, se solicitar GitHub. A transcrição fornecida não exige prints.

Para publicar em um repositório novo e vazio no seu GitHub, depois de
criá-lo com o nome `algoritmos-estruturas-dados`, execute na pasta do projeto:

```bash
git init
git add .
git commit -m "Implementa estruturas de dados e analise de complexidade"
git branch -M main
git remote add origin https://github.com/LazarorpDEV/algoritmos-estruturas-dados.git
git push -u origin main
```

Esses comandos pressupõem um repositório local novo e autenticação GitHub
configurada. Não criam o repositório remoto. A pasta out não será versionada
porque está no .gitignore.
