[English Version](README.en.md)

# Lista 5 - Unidade III - Chat em Java - Redes de Computadores I

Trabalho de implementação de um programa de troca de mensagens instantânea em Java da matéria Redes de Computadores I do curso de Ciência da Computação da Pontifícia Universidade Católica de Minas Gerais.

## Conceito

Para esta implementação foi concebido a seguinte ideia: dois ou mais clientes trocam mensagens entre si, por meio de um servidor.

A comunicação entre os clientes e o servidor se dá através de trocas de comandos do cliente para o servidor. Quando o servidor recebe um comando, ele retorna uma resposta. Os passos principais para a troca de mensagens entre os clientes são:

- O cliente remetente envia um comando para o servidor solicitando a entrega de uma mensagem para o outro cliente (destinatário), e nessa solicitação é enviada a mensagem. O servidor responde com uma resposta de sucesso.

- O servidor recebe a armazena as mensagens enviadas de um cliente (remetente) para outro, até que o cliente destinatário solicite o recebimento das suas mensagens.

- Cada cliente, a cada um minuto, solicita ao servidor o recebimento de suas mensagens. E o servidor responde com uma lista de mensagens endereçadas a ele. Caso não haja mensagens, a lista será vazia.

É possível realizar a comunicação entre o servidor e o cliente por meio do TCP ou do UDP. Ao inicializar tanto o cliente, como o servidor, será solicitado que o usuário escolha o protocolo de transporte (TCP ou UDP) que ele deseja utilizar.

## Código-fonte

### Como compilar

Para compilar este programa, abra o terminal na pasta dele e digite o seguinte comando:

No Terminal do Linux ou Mac:

```
./gradlew build
```

No Prompt de Comando do Windows:

```
gradlew build
```

### Como gerar os arquivos .jar

Os arquivos .jar podem ser gerados através do gradle. Para gerar os arquivos .jar, no terminal, na mesma pasta, digite os seguintes comandos:

#### Cliente

No Terminal do Linux ou Mac:

```
./gradlew jarCliente
```

No Prompt de Comando do Windows:

```
gradlew jarCliente
```

#### Servidor

No Terminal do Linux ou Mac:

```
./gradlew jarServidor
```

No Prompt de Comando do Windows:

```
gradlew jarServidor
```

####

Após executado esses comandos acima, os arquivos .jar serão gerados na pasta "[app/build/libs](app/build/libs)".

### Como executar os arquivos .jar

Para executar os arquivos .jar, na pasta onde os arquivos foram baixados ou gerados, abra o terminal e digite:

```
java -jar <nome-do-.jar>
```

Em que <nome-do-.jar> deve ser substituído pelo nome do arquivo .jar que deseja executar: cliente.jar ou servidor.jar.

## Membros do grupo

[Arthur Henrique Neves Dias](https://github.com/Arthu133)

[Henrique Mendonça Castelar Campos](https://github.com/Henriquemcc)

[João Paolinelli e Silva](https://github.com/JoaoPaolinelli)