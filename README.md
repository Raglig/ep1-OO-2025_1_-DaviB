# Sistema Acadêmico - FCTE

## Descrição do Projeto

Desenvolvimento de um sistema acadêmico para gerenciar alunos, disciplinas, professores, turmas, avaliações e frequência, utilizando os conceitos de orientação a objetos (herança, polimorfismo e encapsulamento) e persistência de dados em arquivos.

O enunciado do trabalho pode ser encontrado aqui:
- [Trabalho 1 - Sistema Acadêmico](https://github.com/lboaventura25/OO-T06_2025.1_UnB_FCTE/blob/main/trabalhos/ep1/README.md)

## Dados do Aluno

- **Nome completo:** Davi Barbosa Alves
- **Matrícula:** 241012131
- **Curso:** Engenharias
- **Turma:** 606
---

## Instruções para Compilação e Execução

1. **Compilação:**  
   import sistema.Sistema;

public class Main {
    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        sistema.iniciar();
    }
}


2. **Execução:**  
   [Descrever aqui como executar o projeto. Exemplo: `java Main` ou o script usado]

3. **Estrutura de Pastas:**  
   dados(Alunos.csv, disciplina.csv, professores.csv, turmas.csv)
   src{[avaliacao(MediaP, MediaS, TipoAvaliacao)], [dominio(Disciplina, Matricula, turma)],[pessoas(Aluno, AlunoEspecial, AlunoNormal, Pessoa, Professor)], [sistema(Sistema)], utils[(Arquivo)]
   Main.java
   aluno.cvs
   disciplina.cvs
   professor.cvs
   turmas.cvs}

3. **Versão do JAVA utilizada:**  
   Java 17

---

## Vídeo de Demonstração

- [Inserir o link para o vídeo no YouTube/Drive aqui]

---

## Prints da Execução

1. Menu Principal:  
   ![Inserir Print 1](C:\Users\davib\ep1-OO-2025_1_-DaviB\Prints)

2. Cadastro de Aluno:  
   ![Inserir Print 2](C:\Users\davib\ep1-OO-2025_1_-DaviB\Prints)

3. Relatório de Frequência/Notas:  
   ![Inserir Print 3](C:\Users\davib\ep1-OO-2025_1_-DaviB\Prints)

---

## Principais Funcionalidades Implementadas

- [X] Cadastro, listagem, matrícula e trancamento de alunos (Normais e Especiais)
- [X] Cadastro de disciplinas e criação de turmas (presenciais e remotas)
- [X] Matrícula de alunos em turmas, respeitando vagas e pré-requisitos
- [X] Lançamento de notas e controle de presença
- [X] Cálculo de média final e verificação de aprovação/reprovação
- [X] Relatórios de desempenho acadêmico por aluno, turma e disciplina
- [ ] Persistência de dados em arquivos (.txt ou .csv)
- [X] Tratamento de duplicidade de matrículas
- [X] Uso de herança, polimorfismo e encapsulamento

---

## Observações (Extras ou Dificuldades)

Tive muito problema com o Sistema.Java, no começo eu acjei que um arquivo seria o suficiente para fazer a parte visual porem as interações começaram a empilhar e eu me embolei todo com o arquivo

---

## Contato

- ddavi.barbosaalves02@gmail.com
