# Organização da Equipe

**Projeto:** Sistema de Gestão da Pousada FACSI
**Disciplina / Turma:** _preencher_
**Professor(a):** _preencher_
**Data de entrega:** _preencher_

## Integrantes

| # | Nome | Matrícula | Área |
|---|---|---|---|
| 1 | Maxymus Gabriel | 202640602013 | Modelagem |
| 2 | Lucio Henrique | 202640602010 | Arquivos |
| 3 | Daniel Oliveira | 202640602020 | Regras e Relatórios |
| 4 | José Augusto | 202640602006 | Interface |
| 5 | Gustavo Souza | 202640602038 | Documentos e Testes |

## Divisão de tarefas

| Área | Arquivos / entregas | Responsável |
|---|---|---|
| Modelagem: modelo de domínio (quartos, hospedagens e consumos) | `TipoQuarto.java`, `Quarto.java`, `Hospedagem.java`, `Consumo.java` | Maxymus Gabriel |
| Arquivos: persistência e massa de dados para testes | `LeitorArquivo.java`, `GravadorArquivo.java`, `GeradorMassa.java` | Lucio Henrique |
| Regras e Relatórios: regras de negócio da pousada (reservas, lista de espera, cancelamento) e fechamento mensal | `Pousada.java` | Daniel Oliveira |
| Interface: menus de console, validação de entradas e relatórios exibidos/gravados em `saidas/` | `Menu.java`, `Main.java` | José Augusto |
| Documentos: README, manual de utilização, documentação do funcionamento e organização | `README.md`, `MANUAL.md`, `DOCUMENTACAO.md`, `ORGANIZACAO.md` | Gustavo Souza |
| Testes: testes automatizados, execução dos cenários do manual e conferência dos cálculos | `TestesPousada.java`, `TESTES.md`, roteiro do `MANUAL.md` | Gustavo Souza (com apoio de todos) |

## Registro de atividades

| Data | Atividade | Responsável(is) |
|---|---|---|
| _dd/mm_ | Definição do escopo e do modelo de classes | Todos |
| _dd/mm_ | Implementação das classes de domínio | Maxymus Gabriel |
| _dd/mm_ | Implementação da `Pousada`, regras de negócio e fechamento mensal | Daniel Oliveira |
| _dd/mm_ | Implementação do `Menu` e da `Main` | José Augusto |
| _dd/mm_ | Persistência em arquivo e gerador de massa | Lucio Henrique |
| _dd/mm_ | Testes integrados | Gustavo Souza e todos |
| _dd/mm_ | Documentação (README, manual, funcionamento, organização) | Gustavo Souza |
| _dd/mm_ | Revisão final e entrega | Todos |

## Combinados da equipe

- Código e documentação revisados por pelo menos uma pessoa além de quem escreveu.
- Padrão de nomes: classes em `PascalCase`, métodos e variáveis em `camelCase`, textos do sistema em português.
- Qualquer mudança de regra de negócio é refletida em `DOCUMENTACAO.md` e `MANUAL.md`.

## Como manter os documentos organizados

| Documento | Para quem | Quando consultar |
|---|---|---|
| `README.md` | Qualquer pessoa | Instalar, compilar e executar |
| `MANUAL.md` | Usuário do sistema | Aprender cada opção do menu |
| `DOCUMENTACAO.md` | Desenvolvedores e avaliadores | Entender classes, regras e decisões técnicas |
| `ORGANIZACAO.md` | Professor e equipe | Ver quem fez o quê |
