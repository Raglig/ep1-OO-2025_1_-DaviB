package sistema;

import java.util.*;
import java.io.*;
import avaliacao.*;
import dominio.*;
import pessoas.*;

public class Sistema {
    private List<Aluno> alunos = new ArrayList<>();
    private List<Disciplina> disciplinas = new ArrayList<>();
    private List<Turma> turmas = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        carregarDados();
        exibirMenuPrincipal();
    }

    private void carregarDados() {
        carregarAlunos();
        carregarDisciplinas();
        carregarTurmas();
    }

    private void salvarDados() {
        salvarAlunos();
        salvarDisciplinas();
        salvarTurmas();
    }

    // ============ MENU PRINCIPAL ============
    private void exibirMenuPrincipal() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1 - Modo Aluno");
            System.out.println("2 - Modo Disciplina/Turma");
            System.out.println("3 - Modo Avaliação/Frequência");
            System.out.println("4 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> exibirModoAluno();
                case 2 -> exibirModoDisciplina();
                case 3 -> exibirModoAvaliacao();
                case 4 -> salvarDados();
            }
        } while (opcao != 4);
    }

    // ============ MODO ALUNO ============
    private void exibirModoAluno() {
        int opcao;
        do {
            System.out.println("\n=== MODO ALUNO ===");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Editar Aluno");
            System.out.println("3 - Listar Alunos");
            System.out.println("4 - Matricular Aluno em Turma");
            System.out.println("5 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarAluno();
                case 2 -> editarAluno();
                case 3 -> listarAlunos();
                case 4 -> matricularAlunoEmTurma();
            }
        } while (opcao != 5);
    }

    private void cadastrarAluno() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula (número): ");
        int matricula = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Curso: ");
        String curso = scanner.nextLine();
        System.out.print("Tipo (1-Normal / 2-Especial): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (alunoExiste(matricula)) {
            System.out.println("Matrícula já existe!");
            return;
        }

        Aluno aluno = (tipo == 1) 
            ? new AlunoNormal(nome, matricula, curso) 
            : new AlunoEspecial(nome, matricula, curso);
        
        alunos.add(aluno);
        System.out.println("Aluno cadastrado!");
    }

    private void editarAluno() {
        System.out.print("Matrícula do aluno: ");
        int matricula = scanner.nextInt();
        scanner.nextLine();

        Aluno aluno = alunos.stream()
            .filter(a -> a.getMatricula() == matricula)
            .findFirst()
            .orElse(null);

        if (aluno == null) {
            System.out.println("Aluno não encontrado!");
            return;
        }

        System.out.print("Novo nome: ");
        aluno.setNome(scanner.nextLine());
        System.out.print("Novo curso: ");
        aluno.setCurso(scanner.nextLine());
        System.out.println("Aluno atualizado!");
    }

    private void listarAlunos() {
        System.out.println("\n--- ALUNOS CADASTRADOS ---");
        for (Aluno aluno : alunos) {
            String tipo = (aluno instanceof AlunoEspecial) ? "Especial" : "Normal";
            System.out.printf("%d - %s (%s - %s)%n",
                aluno.getMatricula(), aluno.getNome(), aluno.getCurso(), tipo);
        }
    }

    private boolean alunoExiste(int matricula) {
        return alunos.stream().anyMatch(a -> a.getMatricula() == matricula);
    }

    // ============ MODO DISCIPLINA/TURMA ============
    private void exibirModoDisciplina() {
        int opcao;
        do {
            System.out.println("\n=== MODO DISCIPLINA/TURMA ===");
            System.out.println("1 - Cadastrar Disciplina");
            System.out.println("2 - Criar Turma");
            System.out.println("3 - Listar Turmas");
            System.out.println("4 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarDisciplina();
                case 2 -> criarTurma();
                case 3 -> listarTurmas();
            }
        } while (opcao != 4);
    }

    private void cadastrarDisciplina() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Carga horária: ");
        int carga = scanner.nextInt();
        scanner.nextLine();

        disciplinas.add(new Disciplina(nome, codigo, carga, new ArrayList<>()));
        System.out.println("Disciplina cadastrada!");
    }

    private void criarTurma() {
        System.out.print("Código da disciplina: ");
        String codigo = scanner.nextLine();
        
        Disciplina disciplina = disciplinas.stream()
            .filter(d -> d.getCodigo().equals(codigo))
            .findFirst()
            .orElse(null);
        
        if (disciplina == null) {
            System.out.println("Disciplina não encontrada!");
            return;
        }

        System.out.print("Nome do professor: ");
        String nomeProf = scanner.nextLine();
        System.out.print("Matrícula do professor: ");
        int matriculaProf = scanner.nextInt();
        scanner.nextLine();
        Professor professor = new Professor(nomeProf, matriculaProf);

        System.out.print("Semestre (ex: 2023.2): ");
        String semestre = scanner.nextLine();

        System.out.print("Tipo de avaliação (1-Ponderada / 2-Simples): ");
        TipoAvaliacao tipo = (scanner.nextInt() == 1) 
            ? new MediaP(new float[]{1, 2, 3, 1, 1}) 
            : new MediaS();
        scanner.nextLine();

        System.out.print("Presencial? (S/N): ");
        boolean presencial = scanner.nextLine().equalsIgnoreCase("S");
        
        String sala = "";
        if (presencial) {
            System.out.print("Sala: ");
            sala = scanner.nextLine();
        }

        System.out.print("Horário: ");
        String horario = scanner.nextLine();

        System.out.print("Capacidade máxima: ");
        int capacidade = scanner.nextInt();
        scanner.nextLine();

        Turma novaTurma = new Turma(disciplina, professor, semestre, tipo, presencial, sala, horario, capacidade);
        turmas.add(novaTurma);
        System.out.println("Turma criada: " + novaTurma);
    }

    private void matricularAlunoEmTurma() {
        System.out.print("Matrícula do aluno: ");
        int matriculaAluno = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Código da turma: ");
        String codigoTurma = scanner.nextLine();

        Aluno aluno = alunos.stream()
            .filter(a -> a.getMatricula() == matriculaAluno)
            .findFirst()
            .orElse (null);
            
        Turma turma = turmas.stream()
            .filter(t -> t.getDisciplina().getCodigo().equals(codigoTurma))
            .findFirst()
            .orElse(null);

        if (aluno == null || turma == null) {
            System.out.println("Aluno ou turma não encontrados!");
            return;
        }

        if (!aluno.cumpriuPreRequisitos(turma.getDisciplina())) {
        System.out.println("Aluno não cumpre os pré-requisitos da disciplina!");
        return;
    }

        if (turma.matricularAluno(aluno)) {
            System.out.println("Matrícula realizada!");
        } else {
            System.out.println("Falha na matrícula (vagas esgotadas ou pré-requisitos)");
        }
    }

    private void listarTurmas() {
        System.out.println("\n--- TURMAS CADASTRADAS ---");
        for (Turma turma : turmas) {
            System.out.println(turma);
        }
    }

    // ============ MODO AVALIAÇÃO ============
    private void exibirModoAvaliacao() {
        int opcao;
        do {
            System.out.println("\n=== MODO AVALIAÇÃO ===");
            System.out.println("1 - Lançar Notas");
            System.out.println("2 - Registrar Faltas");
            System.out.println("3 - Gerar Relatórios");
            System.out.println("4 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> lancarNotas();
                case 2 -> registrarFaltas();
                case 3 -> gerarRelatorios();
            }
        } while (opcao != 4);
    }

    private void lancarNotas() {
        System.out.print("Código da turma: ");
        String codigoTurma = scanner.nextLine();
        
        
        Turma turma = turmas.stream()
            .filter(t -> t.getDisciplina().getCodigo().equals(codigoTurma))
            .findFirst()
            .orElse(null);
        
        if (turma == null) {
            System.out.println("Turma não encontrada!");
            return;
        }

        System.out.println("\nAlunos matriculados:");
        turma.getMatriculas().forEach(m -> {
            System.out.println(m.getAluno().getMatricula() + " - " + m.getAluno().getNome());
        });

        System.out.print("\nMatrícula do aluno: ");
        int matriculaAluno = scanner.nextInt();
        scanner.nextLine();

        Matricula matricula = turma.getMatriculas().stream()
            .filter(m -> m.getAluno().getMatricula() == matriculaAluno)
            .findFirst()
            .orElse(null);

        if (matricula == null) {
            System.out.println("Aluno não encontrado na turma!");
            return;
        }

        System.out.println("Digite as 5 notas (P1, P2, P3, Listas, Seminário):");
        float[] notas = new float[5];
        for (int i = 0; i < 5; i++) {
            System.out.print("Nota " + (i + 1) + ": ");
            notas[i] = scanner.nextFloat();
        }
        scanner.nextLine();

        matricula.setNotas(notas);
        matricula.atualizarStatus(turma.getTotalAulas());
        System.out.printf("Média final: %.1f%n", matricula.calcularMediaFinal(),matricula.estaAprovado() ? "APROVADO" : "REPROVADO");


    }

    private void registrarFaltas() {

    }

    private void gerarRelatorios() {

    }

    // ============ PERSISTÊNCIA ============
    private void salvarAlunos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("alunos.csv"))) {
            for (Aluno aluno : alunos) {
                String tipo = (aluno instanceof AlunoEspecial) ? "ESPECIAL" : "NORMAL";
                writer.write(String.format("%s;%d;%s;%s",
                    aluno.getNome(), aluno.getMatricula(), aluno.getCurso(), tipo));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar alunos!");
        }
    }

    private void carregarAlunos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("alunos.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                String nome = dados[0];
                int matricula = Integer.parseInt(dados[1]);
                String curso = dados[2];
                String tipo = dados[3];

                Aluno aluno = tipo.equals("NORMAL") 
                    ? new AlunoNormal(nome, matricula, curso) 
                    : new AlunoEspecial(nome, matricula, curso);
                
                alunos.add(aluno);
            }
        } catch (IOException e) {
            System.out.println("Arquivo de alunos não encontrado. Criando novo...");
        }
    }

    private void salvarDisciplinas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("disciplinas.csv"))) {
            for (Disciplina disciplina : disciplinas) {
                writer.write(String.format("%s;%s;%d",
                    disciplina.getNome(), disciplina.getCodigo(), disciplina.getCargaHoraria()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas!");
        }
    }

    private void carregarDisciplinas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("disciplinas.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                disciplinas.add(new Disciplina(dados[0], dados[1], Integer.parseInt(dados[2]), new ArrayList<>()));
            }
        } catch (IOException e) {
            System.out.println("Arquivo de disciplinas não encontrado. Criando novo...");
        }
    }

    private void salvarTurmas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("turmas.csv"))) {
            for (Turma turma : turmas) {
                writer.write(String.format("%s;%s;%s;%s;%b;%s;%s;%d",
                    turma.getDisciplina().getCodigo(),
                    turma.getProfessor().getNome(),
                    turma.getSemestre(),
                    turma.getTipoAvaliacao().getClass().getSimpleName(),
                    turma.isPresencial(),
                    turma.getSala(),
                    turma.getHorario(),
                    turma.getCapacidadeMaxima()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar turmas!");
        }
    }

    private void carregarTurmas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("turmas.csv"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                final Disciplina disciplina = disciplinas.stream()
                    .filter(d -> d.getCodigo().equals(dados[0]))
                    .findFirst()
                    .orElse(null);

                if (disciplina != null) {
                    Professor professor = new Professor(dados[1], 0); // Matrícula temporária
                    TipoAvaliacao tipo = dados[3].equals("MediaP") 
                        ? new MediaP(new float[]{1, 2, 3, 1, 1}) 
                        : new MediaS();
                    
                    turmas.add(new Turma(
                        disciplina,
                        professor,
                        dados[2],
                        tipo,
                        Boolean.parseBoolean(dados[4]),
                        dados[5],
                        dados[6],
                        Integer.parseInt(dados[7])));
                }
            }
        } catch (IOException e) {
            System.out.println("Arquivo de turmas não encontrado. Criando novo...");
        }
    }

    
    private void verificarPreRequisitos() {
    System.out.print("Código da disciplina: ");
    String codigo = scanner.nextLine();
    Disciplina disciplina = disciplinas.stream()...;

    System.out.println("Pré-requisitos:");
    disciplina.getPreRequisitos().forEach(d -> 
        System.out.println("- " + d.getCodigo() + ": " + d.getNome())
    );
}

}