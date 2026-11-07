# Manual de Utilização

Este manual descreve cada opção do menu do sistema, com exemplos de uso. Para compilar e executar, veja o [README.md](README.md).

## Convenções

- **Datas** digitadas no sistema usam o formato `AAAA-MM-DD` (ex.: `2026-12-20`).
- **Datas no arquivo** de dados usam `dd/MM/aaaa` (ex.: `20/12/2026`).
- Sempre que o sistema pedir uma confirmação, responda `S` (sim) ou `N` (não).
- O hóspede é identificado pelo **documento** (CPF ou outro); use o mesmo documento em todas as operações.
- Valores das diárias: **STANDARD R$ 180,00 · LUXO R$ 260,00 · SUITE R$ 390,00**.
- Decimais são digitados com **ponto** (ex.: `55.00`).

## Menu principal

```
===== POUSADA FACSI =====
1 - Listar quartos e hospedagens
2 - Consultar detalhes
3 - Cadastrar quarto
4 - Adicionar elemento a hospedagem
5 - Remover quarto
6 - Remover consumo
7 - Buscar hospedagem
8 - Operações específicas
9 - Relatórios
10 - Salvar dados
11 - Sair
```

Digite o número da opção e pressione Enter. Entradas inválidas (letras, números fora da faixa) exibem uma mensagem e o menu volta a ser mostrado.

---

### 1 - Listar quartos e hospedagens

Mostra todos os quartos (em ordem crescente de número) e, em seguida, as hospedagens confirmadas.

```
===== QUARTOS CADASTRADOS =====
Número: 101 | Tipo: STANDARD | Capacidade: 2
Número: 201 | Tipo: LUXO | Capacidade: 3

===== HOSPEDAGENS CADASTRADAS =====
Hóspede: Maria Silva | Documento: 12345678900 | Check-in: 2026-12-20 | Check-out: 2026-12-23 | Quarto: 201
```

Se não houver quartos: `Nenhum quarto cadastrado.` Se não houver hospedagens: `Nenhuma hospedagem cadastrada.`

> Hóspedes na lista de espera **não** aparecem aqui; use 9 → 2 (Lista de espera).

### 2 - Consultar detalhes

Pede o documento e mostra a ficha completa da hospedagem: dados do hóspede, quarto, consumos e resumo financeiro.

```
Digite o documento do hóspede: 12345678900

===== DETALHES DA HOSPEDAGEM =====
Hóspede: Maria Silva
Documento: 12345678900
Check-in: 2026-12-20
Check-out: 2026-12-23
Situação: Quarto reservado
Quarto: 201
Tipo: LUXO
Capacidade: 3

===== CONSUMOS =====
Data: 2026-12-21 | Descrição: Jantar regional | Quantidade: 2 | Preço unitário: R$ 55.0 | Subtotal: R$ 110.0

===== RESUMO FINANCEIRO =====
Diárias: 3
Total das diárias: R$ 780,00
Total dos consumos: R$ 110,00
Taxa de serviço: R$ 11,00
TOTAL: R$ 901,00
```

Mensagens possíveis: `Sem hospedagens cadastradas.` (nenhuma hospedagem no sistema) ou `Nenhuma hospedagem encontrada para esse documento.` Sem consumos, aparece `Nenhum consumo registrado.`

### 3 - Cadastrar quarto

Pede número, tipo e capacidade.

```
Digite o número do quarto: 102
Tipos disponíveis:
1 - STANDARD
2 - LUXO
3 - SUITE
Escolha o tipo do quarto: 1
Digite a capacidade do quarto: 2
Quarto cadastrado com sucesso!
```

Erros possíveis: número repetido (`Número de quarto já cadastrado`), número ou capacidade menor ou igual a zero, tipo inválido. O quarto é inserido na posição correta para manter a lista ordenada.

### 4 - Adicionar elemento a hospedagem (lançar consumo)

Registra um consumo (item consumido) na hospedagem do hóspede. É a mesma função de 8 → 3.

```
Digite o documento do hóspede:
12345678900
Digite a data do consumo (AAAA-MM-DD): 2026-12-21
Digite a descrição do consumo: Jantar regional
Digite a quantidade consumida: 2
Digite o preço unitário: 55.00
Consumo adicionado com sucesso!
```

- Quantidade deve ser maior que zero; o preço não pode ser negativo; a descrição não pode ficar em branco. Se algum valor for inválido, o sistema mostra `Não foi possível adicionar o consumo: ...` e volta ao menu.
- A data do consumo deve estar entre o check-in e o check-out (inclusive). Fora disso: `Data do consumo fora do período da hospedagem (AAAA-MM-DD a AAAA-MM-DD).`
- Só funciona para hospedagens com quarto confirmado (não para quem está na lista de espera).

### 5 - Remover quarto

```
Digite o número do quarto que deseja remover: 102
Quarto encontrado: 102
Tem certeza de que deseja removê-lo? (S/N): S
Quarto removido com sucesso!
```

**Regra:** um quarto que já teve hospedagem não pode ser removido (`Não é possível remover um quarto que já teve hospedagem`). Só é possível remover quartos nunca usados.

### 6 - Remover consumo

Lista os consumos da hospedagem numerados e remove o escolhido.

```
Digite o documento do hóspede: 12345678900

===== CONSUMOS DA HOSPEDAGEM =====
1 - Data: 2026-12-21 | Descrição: Jantar regional | Quantidade: 2 | ...

Digite o número do consumo que deseja remover: 1
Consumo selecionado: Data: 2026-12-21 | ...
Tem certeza de que deseja remover? (S/N): S
Consumo removido com sucesso!
```

### 7 - Buscar hospedagem

Consulta rápida por documento. Mostra uma versão resumida da hospedagem (sem consumos nem valores; para isso use a opção 2).

```
Digite o documento do hóspede: 12345678900

===== HOSPEDAGEM ENCONTRADA =====
Hóspede: Maria Silva
Documento: 12345678900
Check-in: 2026-12-20
Check-out: 2026-12-23
Situação: Quarto reservado
Quarto: 201
Tipo: LUXO
```

Se não houver: `Nenhuma hospedagem encontrada para esse documento.`

> Para **fazer uma reserva**, use 8 → 1.

### 8 - Operações específicas

Submenu:

```
1 - Cadastrar hospedagem (reserva)
2 - Cancelar hospedagem
3 - Lançar consumo
4 - Emitir conta
5 - Mapa de ocupação
0 - Voltar ao menu principal
```

**8.1 Cadastrar hospedagem (reserva)**

```
Digite o nome do hóspede: Maria Silva
Digite o documento do hóspede: 12345678900
Digite a data de check-in (AAAA-MM-DD): 2026-12-20
Digite a data de checkout (AAAA-MM-DD): 2026-12-23

Tipos de quarto disponíveis:
1 - STANDARD
2 - LUXO
3 - SUITE
Escolha o tipo de quarto: 2
Hospedagem registrada com sucesso! Quarto reservado.
```

Resultados possíveis:

- **Há quarto livre do tipo** no período: o sistema escolhe o de menor número disponível e confirma a reserva.
- **Não há quarto livre**: o hóspede entra na **lista de espera** (`Não há quarto disponível desse tipo no período solicitado. Hóspede adicionado à lista de espera.`).
- O check-in não pode ser anterior à data atual.
- O checkout deve ser posterior ao check-in (o sistema pede a data novamente).

**8.2 Cancelar hospedagem**: informa o documento, mostra os dados e pede confirmação.

```
Digite o documento do hóspede: 12345678900

===== HOSPEDAGEM ENCONTRADA =====
Hóspede: Maria Silva
Documento: 12345678900
Check-in: 2026-12-20
Check-out: 2026-12-23

Tem certeza de que deseja cancelar esta hospedagem? (S/N): S
Hospedagem cancelada com sucesso!

===== VAGA LIBERADA =====
O hóspede João Souza foi retirado da lista de espera.
Quarto 201 alocado com sucesso!
```

Ao cancelar, o quarto liberado é oferecido à **lista de espera**: a primeira hospedagem da fila (por ordem de chegada) que seja do mesmo tipo e caiba no período é promovida automaticamente e o sistema informa quem foi alocado. Se ninguém puder ocupar a vaga: `Nenhum hóspede da lista de espera foi alocado.`

**8.3 Lançar consumo**: igual à opção 4 do menu principal.

**8.4 Emitir conta**: mostra o resumo financeiro.

```
========== CONTA DA HOSPEDAGEM ==========
Hóspede: Maria Silva
Documento: 12345678900
Check-in: 2026-12-20
Check-out: 2026-12-23
Quarto: 201
----------------------------------------
Diárias: 3
Total das diárias: R$ 780,00
Total dos consumos: R$ 110,00
Taxa de serviço (10%): R$ 11,00
----------------------------------------
TOTAL A PAGAR: R$ 901,00
```

Cálculo: `diárias × valor da diária + consumos + 10% dos consumos`. A taxa de serviço incide **somente sobre os consumos**, não sobre as diárias.

**8.5 Mapa de ocupação**: informa uma data e mostra cada quarto como OCUPADO ou LIVRE naquele dia.

```
Digite a data para consultar a ocupação (AAAA-MM-DD): 2026-12-21

MAPA DE OCUPAÇÃO - 2026-12-21
--------------------------------
Quarto 101: LIVRE
Quarto 201: OCUPADO
```

O dia do check-out é considerado livre (o hóspede sai nesse dia).

### 9 - Relatórios

```
1 - Fechamento mensal
2 - Lista de espera
0 - Voltar ao menu principal
```

**9.1 Fechamento mensal**: pede mês (1 a 12) e ano (de 2000 até o ano atual).

```
Digite o mês do fechamento (1 a 12): 8
Digite o ano do fechamento: 2026
```

Saída (resumida):

```
FECHAMENTO MENSAL
08/2026

Ana Silva | Quarto 101 | Checkout: 2026-08-05 | Total: R$ 765,00
...
RECEITA TOTAL: R$ 12345,00

RECEITA POR TIPO:
STANDARD: R$ ...
LUXO: R$ ...
SUITE: R$ ...

OCUPAÇÃO: 38,50%
```

Entram no mês as hospedagens cujo **checkout** ocorre nele, em ordem de data de checkout. A ocupação é `noites ocupadas no mês ÷ (quartos × dias do mês)`. O relatório também é salvo em `saidas/fechamento_MM_AAAA.txt`.

**9.2 Lista de espera**: mostra a fila em ordem de chegada (posição, hóspede, documento, datas) e salva em `saidas/espera.txt`. Se estiver vazia: `Não existem hospedagens na lista de espera.`

### 10 - Salvar dados

Grava quartos, hospedagens e consumos em `dados/pousada.txt` (a pasta `dados/` é criada se não existir).

```
Dados salvos em dados/pousada.txt
```

> A **lista de espera não é gravada** no arquivo; ao reiniciar o sistema, ela volta vazia.

### 11 - Sair

Pergunta se deseja salvar antes de encerrar (`S` ou `N`; qualquer outra resposta repete a pergunta) e finaliza o programa.

---

## Fluxo de exemplo (do zero)

1. Opção **3**: cadastre o quarto 101 (STANDARD, capacidade 2).
2. Opção **8 → 1**: reserve para "Maria Silva", documento `123`, de `2026-12-20` a `2026-12-23`, tipo 1. Resultado: reservada no quarto 101.
3. Opção **8 → 1** de novo: "João Souza", documento `456`, mesmas datas, tipo 1. Resultado: sem quarto livre, vai para a **lista de espera** (veja em 9 → 2).
4. Opção **4**: lance um consumo para o documento `123`.
5. Opção **2**: consulte os detalhes do documento `123` ou emita a conta em **8 → 4**.
6. Opção **8 → 2**: cancele a hospedagem `123`. O sistema informa que João foi alocado ao quarto 101.
7. Opção **10**: salve os dados.
