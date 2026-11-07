# Testes do Sistema

Os testes ficam em `src/PousadaFACSI/TestesPousada.java`. Não usam JUnit nem nenhuma biblioteca externa: basta compilar o projeto e executar a classe.

## Como executar

Na raiz do projeto, depois de compilar (veja o [README.md](README.md)):

```bash
java -cp out PousadaFACSI.TestesPousada
```

Cada verificação imprime `[OK]` ou `[FALHOU]`. No final aparece o resumo e o programa termina com código 0 (tudo passou) ou 1 (houve falha):

```
RESULTADO: 181 testes passaram, 0 falharam.
```

Rode a partir da raiz do projeto: o teste do `GeradorMassa` usa a pasta `dados/` (ele cria `dados/grande.txt`, confere e apaga em seguida; se o arquivo já existia, restaura o original). Os demais testes usam arquivos temporários e não alteram `dados/pousada.txt` nem `saidas/`.

## O que é coberto

| Área | Verificações | O que é testado |
|---|---:|---|
| `Quarto` | 5 | Valores das diárias (180 / 260 / 390), tipo nulo, marca de hospedagem |
| `Consumo` | 7 | Subtotal, quantidade zero ou negativa, preço negativo, descrição em branco, data nula |
| `Hospedagem` | 13 | Checkout inválido, nº de diárias, total sem quarto, total = diárias + consumos + 10%, remoção de consumo, noites por mês (inclusive na virada de ano) |
| `Pousada` - quartos | 14 | Ordenação por número, duplicados, dados inválidos, remoção permitida e proibida |
| `Pousada` - reservas | 21 | Conflito de datas, check-in no dia do checkout, tipos diferentes, registro duplicado, restauração inválida |
| `Pousada` - lista de espera | 15 | Ordem FIFO, promoção no cancelamento, tipo diferente não é promovido, cancelamentos inválidos |
| `Pousada` - fechamento | 15 | Receita total e por tipo, ordem por checkout, mês fora do período, ocupação, mês inválido, pousada sem quartos |
| Gravador + Leitor | 15 | Ida e volta preserva tudo (inclusive totais), criação da pasta de destino |
| Leitor com arquivo ruim | 12 | Quarto repetido, tipo inválido, data inexistente, quarto inexistente, sobreposição, consumo fora do período, arquivo vazio ou inexistente |
| `GeradorMassa` | 6 | 20 quartos, 100 hospedagens e 200 consumos carregam sem nenhuma linha ignorada |
| `Menu` - fluxo completo | 29 | Cadastro, reserva, consumo, detalhes, conta (R$ 901,00), listagem, espera, cancelamento com promoção, mapa de ocupação |
| `Menu` - entradas inválidas | 20 | Letras no lugar de números, opções fora da faixa, consumo inválido, datas inválidas: nada pode fechar o programa |
| `Menu` - remoções | 9 | Remover quarto livre / usado / inexistente, remover consumo, índice inválido |
| **Total** | **181** | |

O `Menu` é testado simulando o teclado (o teste alimenta `System.in` com as linhas que o usuário digitaria e lê o que foi impresso).

## Erros encontrados e corrigidos

Na primeira execução, 9 verificações falharam, o que revelou 4 problemas reais. Todos foram corrigidos e os testes passam por completo:

1. **Consumo inválido fechava o programa.** No menu 4 (e 8 → 3), quantidade 0, preço negativo ou descrição em branco gerava uma exceção que não era tratada. Agora o sistema mostra `Não foi possível adicionar o consumo: ...` e continua. *(Menu.java)*
2. **Consumo com data fora da estadia era aceito, mas se perdia ao reabrir.** O `LeitorArquivo` descarta esses consumos ao carregar. O menu agora exige data entre check-in e checkout, igual ao leitor. *(Menu.java)*
3. **Arquivo com quarto de número inválido derrubava o carregamento.** Um quarto como `0;STANDARD;2` era recusado, mas ficava na lista interna do leitor; uma hospedagem apontando para ele causava erro na abertura do sistema. Agora o quarto só é registrado depois de aceito. *(LeitorArquivo.java)*
4. **Salvar falhava sem a pasta `dados/`.** O gravador agora cria a pasta quando necessário. *(GravadorArquivo.java)*

## Teste ponta a ponta (manual, feito uma vez)

Além dos testes automáticos, o programa foi executado de verdade em uma pasta vazia: cadastro de 2 quartos, reserva LUXO de 3 diárias, consumo de 2 × R$ 55,00, salvar (10) e sair; ao abrir de novo, os dados foram recarregados e a conta mostrou **TOTAL: R$ 901,00** (3 × 260 + 110 + 11). O fechamento mensal foi gerado e gravado em `saidas/fechamento_12_2026.txt`. Em outra execução, uma segunda reserva para o mesmo quarto e período entrou na lista de espera, e o relatório 9.2 mostrou a fila e gravou `saidas/espera.txt`.

## Roteiro manual sugerido para a apresentação

1. Compile e rode `TestesPousada` (mostra `181 passaram, 0 falharam`).
2. Rode `GeradorMassa`, renomeie `dados/grande.txt` para `dados/pousada.txt` e abra o sistema.
3. Opção 9 → 1: fechamento de agosto/2026 (mostra receita por tipo e ocupação).
4. Opção 8 → 1 duas vezes para o mesmo tipo e período até lotar: a segunda reserva vai para a lista de espera (9 → 2).
5. Opção 8 → 2: cancele a primeira reserva e mostre a promoção automática.
6. Opção 4 com quantidade `0`: o sistema recusa sem fechar.
7. Opção 10 e reabra o sistema: os dados permanecem.

## Limitações dos testes

- Não há teste automatizado para a opção 10 / 11 do menu (gravam em `dados/pousada.txt`); a gravação e a leitura são testadas diretamente nas classes `GravadorArquivo` e `LeitorArquivo` com arquivos temporários.
- Os relatórios gravados em `saidas/` pelo `Menu` (9.1 e 9.2) foram verificados nos testes ponta a ponta manuais, não no `TestesPousada` (para não criar arquivos em `saidas/`); o conteúdo do fechamento é testado diretamente em `Pousada.fechamento`.
- As limitações conhecidas do sistema estão na seção 8 do [DOCUMENTACAO.md](DOCUMENTACAO.md) (por exemplo, a lista de espera não é salva em arquivo).
