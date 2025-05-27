package sistema;

import java.util.*;
import java.util.stream.Collectors;
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
    File diretorioDados = new File("dados");
    if (!diretorioDados.exists()) {
        diretorioDados.mkdir();
    }
    
    carregarDados();
    exibirMenuPrincipal();
    salvarDados();
}

    private void carregarDados() {
        carregarAlunos();
        carregarDisciplinas();
        carregarProfessores();
        carregarTurmas();
    }

    private void salvarDados() {
        salvarAlunos();
        salvarDisciplinas();
        salvarProfessores();
        salvarTurmas();
    }

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

    private void exibirModoAluno() {
    int opcao = -1;
    do {
        try {
            System.out.println("\n=== MODO ALUNO ===");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Editar Aluno");
            System.out.println("3 - Listar Alunos");
            System.out.println("4 - Matricular Aluno em Turma");
            System.out.println("5 - Trancar Matrícula");
            System.out.println("6 - Voltar");
            System.out.print("Escolha: ");
            
            // Verifica se há entrada disponível
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
            } else {
                System.out.println("Por favor, digite um número válido.");
                scanner.next(); // Descarta a entrada inválida
                continue;
            }
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1 -> cadastrarAluno();
                case 2 -> editarAluno();
                case 3 -> listarAlunos();
                case 4 -> matricularAlunoEmTurma();
                case 5 -> trancarMatricula();
                case 6 -> System.out.println("Retornando ao menu principal...");
                default -> System.out.println("Opção inválida! Digite de 1 a 6.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro: Por favor, digite apenas números.");
            scanner.nextLine(); // Limpa o buffer do scanner
            opcao = -1; // Força nova iteração
        }
    } while (opcao != 6);
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
    

    private void exibirModoDisciplina() {
        int opcao;
        do {
            System.out.println("\n=== MODO DISCIPLINA/TURMA ===");
            System.out.println("1 - Cadastrar Disciplina");
            System.out.println("2 - Listar/Editar/Excluir Disciplina"); 
            System.out.println("3 - Criar Turma");
            System.out.println("4 - Listar Turmas");
            System.out.println("5 - Ver pré-requisitos");
            System.out.println("6 - Listar Alunos por Turma"); // NOVA OPÇÃO
            System.out.println("7 - Voltar");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarDisciplina();
                case 2 -> gerenciarDisciplina();
                case 3 -> criarTurma();
                case 4 -> listarTurmas();
                case 5 -> verificarPreRequisitos();
                case 6 -> listarAlunosTurma(); // CHAMADA DO MÉTODO
            }
        } while (opcao != 7);
    }

    private void cadastrarDisciplina() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Código: ");
        String codigo = scanner.nextLine();

        boolean existe = disciplinas.stream()
            .anyMatch(d -> d.getCodigo().equalsIgnoreCase(codigo));
            if (existe) {
                System.out.println("Já existe disciplina com esse código!");
                return;
            }

        System.out.print("Carga horária: ");
            int carga;
        try {
            carga = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Carga inválida.");
            return;
        }

        List<Disciplina> preRequisitos = new ArrayList<>();
        System.out.print("Quantos pré-requisitos esta disciplina possui? ");
        int numPreReq;
        try {
            numPreReq = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            numPreReq = 0;
        }

    for (int i = 0; i < numPreReq; i++) {
            System.out.print("  Código do pré-requisito " + (i+1) + ": ");
            String codPre = scanner.nextLine().trim();
            Disciplina pre = disciplinas.stream()
                .filter(d -> d.getCodigo().equalsIgnoreCase(codPre))
                .findFirst()
                .orElse(null);
            if (pre != null) {
                // REMOVA o if (!pre.equals(pre)) que estava errado e adicione diretamente:
                preRequisitos.add(pre);
            } else {
                System.out.println("  * Atenção: pré-requisito '" + codPre + "' não existe. Ignorado.");
            }
        }

    Disciplina nova = new Disciplina(nome, codigo, carga, preRequisitos);
        disciplinas.add(nova);
        salvarDisciplinas();
        System.out.println("Disciplina cadastrada!");
    }

    private void gerenciarDisciplina() {
    if (disciplinas.isEmpty()) {
        System.out.println("\nNenhuma disciplina cadastrada.");
        return;
    }

    System.out.println("\n--- LISTA DE DISCIPLINAS ---");
    for (int i = 0; i < disciplinas.size(); i++) {
        Disciplina d = disciplinas.get(i);
        System.out.printf("%d) %s%n", i + 1, d.toString());
    }

    System.out.print("Selecione o número da disciplina para editar/excluir (ou 0 para sair): ");
    int opc;
    try {
        opc = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
        System.out.println("Entrada inválida.");
        return;
    }
    
    if (opc == 0) return;
    if (opc < 1 || opc > disciplinas.size()) {
        System.out.println("Opção inválida.");
        return;
    }

    Disciplina selecionada = disciplinas.get(opc - 1);
    System.out.println("\nDisciplina selecionada: " + selecionada);
    
    System.out.println("1 - Editar pré-requisitos");
    System.out.println("2 - Excluir disciplina");
    System.out.println("0 - Voltar");
    System.out.print("Escolha: ");
    String acao = scanner.nextLine().trim();

    switch (acao) {
        case "1":
            editarPreRequisitos(selecionada);
            break;
        case "2":
            excluirDisciplina(selecionada);
            break;
        default:
            System.out.println("Opção inválida.");
        }
    }

    private void editarPreRequisitos(Disciplina disciplina) {
    System.out.println("\n--- EDITAR PRÉ-REQUISITOS ---");
    System.out.println("Pré-requisitos atuais:");
    disciplina.getPreRequisitos().forEach(d -> 
        System.out.println("- " + d.getCodigo() + ": " + d.getNome())
    );
    
    System.out.print("Deseja limpar a lista de pré-requisitos? (S/N): ");
    if (scanner.nextLine().equalsIgnoreCase("S")) {
        disciplina.getPreRequisitos().clear();
    }
    
    System.out.print("Quantos novos pré-requisitos deseja adicionar? ");
    int numPreReq;
    try {
        numPreReq = Integer.parseInt(scanner.nextLine());
    } catch (NumberFormatException e) {
        numPreReq = 0;
    }

    for (int i = 0; i < numPreReq; i++) {
        System.out.print("Código do pré-requisito " + (i+1) + ": ");
        String codPre = scanner.nextLine().trim();
        Disciplina pre = disciplinas.stream()
            .filter(d -> d.getCodigo().equalsIgnoreCase(codPre))
            .findFirst()
            .orElse(null);
            
        if (pre != null && !disciplina.getPreRequisitos().contains(pre)) {
            disciplina.adicionarPreRequisito(pre);
        } else {
            System.out.println("  * Pré-requisito inválido ou já existente");
        }
    }
    System.out.println("Pré-requisitos atualizados!");
}

    private void excluirDisciplina(Disciplina disciplina) {
    System.out.print("Tem certeza que deseja excluir " + disciplina.getCodigo() + "? (S/N): ");
    String confirmacao = scanner.nextLine();
    if (confirmacao.equalsIgnoreCase("S")) {
        turmas.removeIf(t -> t.getDisciplina().equals(disciplina));
        disciplinas.forEach(d -> d.getPreRequisitos().remove(disciplina));
        disciplinas.remove(disciplina);
        System.out.println("Disciplina excluída!");
        }
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
    if (aluno instanceof AlunoEspecial) {
        long matriculasAtivas = aluno.getHistorico().stream()
            .filter(m -> !m.isTrancada())
            .count();
    if (matriculasAtivas >= 2) {
            System.out.println("Aluno especial não pode ter mais de 2 disciplinas matriculadas!");
            return;
        }
    }

    else if (aluno instanceof AlunoNormal) {
        if (!aluno.cumpriuPreRequisitos(turma.getDisciplina())) {
            System.out.println("Aluno não cumpre os pré-requisitos da disciplina!");
            return;
        }
    }
    
    boolean temConflito = aluno.getHistorico().stream()
    .filter(m -> !m.isTrancada())
    .map(Matricula::getTurma)
    .anyMatch(t -> t.temConflito(turma));

    if (temConflito) {
        System.out.println("Conflito de horário com outra turma!");
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

        System.out.printf("%s (%d/%d vagas)\n", 
        turma.toString(), 
        turma.getMatriculas().size(), 
        turma.getCapacidadeMaxima()
);
        }
    }

    private void listarAlunosTurma() {
        System.out.print("Código da turma: ");
        String codigo = scanner.nextLine();
        turmas.stream()
            .filter(t -> t.getDisciplina().getCodigo().equals(codigo))
            .findFirst()
            .ifPresent(t -> t.getMatriculas().forEach(m -> 
                System.out.println(m.getAluno().getNome())
            ));
    }

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
        matricula.atualizarStatus();
        String status = matricula.estaAprovado() ? "APROVADO" : "REPROVADO";
        System.out.printf("Média final: %.1f - %s%n", 
            matricula.calcularMediaFinal(),
            status
        );    

        if (matricula.getAluno() instanceof AlunoEspecial) {
            System.out.println("Alunos especiais não recebem notas!");
            return;
        }

    

    }

    

    private void registrarFaltas() {
    System.out.print("Código da turma: ");
    String codigoTurma = scanner.nextLine();
    Turma turma = turmas.stream()
        .filter(t -> t.getDisciplina().getCodigo().equals(codigoTurma))
        .findFirst()
        .orElse(null);

    if (turma != null) {
        System.out.println("Registrando falta para todos os alunos...");
        turma.getMatriculas().forEach(Matricula::adicionarFalta);
        System.out.println("Faltas registradas!");
    } else {
        System.out.println("Turma não encontrada!");
    }
}

    private void gerarRelatorios() {
    System.out.println("""
        1 - Boletim por Aluno
        2 - Aprovados/Reprovados por Turma
        3 - Carga Horária Total por Aluno
        4 - Relatório por Professor
        5 - Relatório por Disciplina
        """);
    int opcao = scanner.nextInt();
    scanner.nextLine();
    
    switch (opcao) {
        case 1 -> gerarBoletimAluno();
        case 2 -> gerarStatusTurma();
        case 3 -> gerarCargaHorariaAlunos();
        case 4 -> relatorioProfessor();
        case 5 -> relatorioDisciplina();
        default -> System.out.println("Opção inválida!");
    }
}

    private void gerarBoletimAluno() {
    System.out.print("Matrícula do aluno: ");
    int matriculaAluno = scanner.nextInt();
    scanner.nextLine(); // Consumir a quebra de linha
    
    Aluno aluno = alunos.stream()
        .filter(a -> a.getMatricula() == matriculaAluno)
        .findFirst()
        .orElse(null);
    
    if (aluno != null) {
        System.out.println("\n--- BOLETIM DE " + aluno.getNome() + " ---");
        
        System.out.print("Incluir detalhes da turma? (S/N): ");
        boolean detalhesTurma = scanner.nextLine().equalsIgnoreCase("S");

        aluno.getHistorico().forEach(m -> {
            String status = m.estaAprovado() ? "APROVADO" : "REPROVADO";
            if (detalhesTurma) {
                System.out.printf("\n%s - %s\nProfessor: %s\nModalidade: %s\nCarga: %dh\nMédia: %.1f | Status: %s\n",
                    m.getTurma().getDisciplina().getCodigo(),
                    m.getTurma().getSemestre(),
                    m.getTurma().getProfessor().getNome(),
                    m.getTurma().isPresencial() ? "Presencial" : "Remoto",
                    m.getTurma().getDisciplina().getCargaHoraria(),
                    m.calcularMediaFinal(),
                    status);
            } else {
                System.out.printf("%s - %s | Média: %.1f | Status: %s\n",
                    m.getTurma().getDisciplina().getCodigo(),
                    m.getTurma().getSemestre(),
                    m.calcularMediaFinal(),
                    status);
            }
        });
    } else {
        System.out.println("Aluno não encontrado!");
    }
}


    private void salvarAlunos() {
    File arquivo = new File("alunos.csv");
    
    try {
        // Garante que o arquivo existe
        if (!arquivo.exists()) {
            arquivo.createNewFile();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Aluno aluno : alunos) {
                String tipo = (aluno instanceof AlunoEspecial) ? "ESPECIAL" : "NORMAL";
                writer.write(String.format("%s;%d;%s;%s",
                    aluno.getNome(), 
                    aluno.getMatricula(), 
                    aluno.getCurso(), 
                    tipo));
                writer.newLine();
            }
            System.out.println("Dados dos alunos salvos com sucesso.");
        }
    } catch (IOException e) {
        System.out.println("Erro grave: não foi possível salvar os alunos! " + e.getMessage());
    }
}


    private void carregarAlunos() {
    File arquivo = new File("alunos.csv");
    
    if (!arquivo.exists()) {
        try {
            arquivo.createNewFile();
            System.out.println("Arquivo de alunos criado com sucesso.");
            return; 
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de alunos: " + e.getMessage());
            return;
        }
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.trim().isEmpty()) continue;
            
            String[] dados = linha.split(";");
            if (dados.length >= 4) {  
                String nome = dados[0];
                int matricula = Integer.parseInt(dados[1]);
                String curso = dados[2];
                String tipo = dados[3];

                Aluno aluno = tipo.equals("NORMAL") 
                    ? new AlunoNormal(nome, matricula, curso) 
                    : new AlunoEspecial(nome, matricula, curso);
                
                alunos.add(aluno);
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao ler arquivo de alunos: " + e.getMessage());
    } catch (NumberFormatException e) {
        System.out.println("Formato de matrícula inválido no arquivo.");
    }
}

    private void salvarDisciplinas() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("disciplinas.csv"))) {
        for (Disciplina disciplina : disciplinas) {
            writer.write(disciplina.toCSV());
            writer.newLine();
        }
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas!");
        }
    }

    private void carregarDisciplinas() {
    disciplinas.clear();
    Map<String, Disciplina> mapa = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("disciplinas.csv"))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.trim().isEmpty()) continue;
            Disciplina d = Disciplina.fromCSV(linha, mapa); // Corrigido aqui
            disciplinas.add(d);
            mapa.put(d.getCodigo(), d);
        }
        // Linkar pré-requisitos após carregar todas
        for (Disciplina d : disciplinas) {
            d.getPreRequisitos().replaceAll(pre -> mapa.get(pre.getCodigo()));
        }
    } catch (IOException e) {
        System.out.println("Arquivo de disciplinas não encontrado. Criando novo...");
    }
    }

    private void salvarTurmas() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("turmas.csv"))) {
        for (Turma turma : turmas) {
            String tipoAvaliacao = turma.getTipoAvaliacao() instanceof MediaP ? "Ponderada" : "Simples";
            String pesos = turma.getTipoAvaliacao() instanceof MediaP ?
                Arrays.toString(((MediaP) turma.getTipoAvaliacao()).getPesos()) : "";
            
            writer.write(String.format("%s;%s;%d;%s;%s;%b;%s;%s;%d;%s",
                turma.getDisciplina().getCodigo(),
                turma.getProfessor().getNome(),
                turma.getProfessor().getMatricula(),
                turma.getSemestre(),
                tipoAvaliacao,
                turma.isPresencial(),
                turma.getSala(),
                turma.getHorario(),
                turma.getCapacidadeMaxima(),
                pesos));
            writer.newLine();
        }
        System.out.println("Dados das turmas salvos com sucesso.");
    } catch (IOException e) {
        System.out.println("Erro ao salvar turmas: " + e.getMessage());
    }
}

    private void carregarTurmas() {
    File arquivo = new File("turmas.csv");
    
    if (!arquivo.exists()) {
        try {
            arquivo.createNewFile();
            return;
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de turmas: " + e.getMessage());
            return;
        }
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (linha.trim().isEmpty()) continue;
            
            String[] dados = linha.split(";");
            if (dados.length >= 9) {
                Disciplina disciplina = disciplinas.stream()
                    .filter(d -> d.getCodigo().equals(dados[0]))
                    .findFirst()
                    .orElse(null);
                
                if (disciplina != null) {
                    Professor professor = new Professor(dados[1], Integer.parseInt(dados[2]));
                    
                    TipoAvaliacao tipoAvaliacao = dados[4].equals("Ponderada") ?
                        new MediaP(new float[]{1, 2, 3, 1, 1}) : // Ou carregar pesos do arquivo
                        new MediaS();
                    
                    Turma turma = new Turma(
                        disciplina,
                        professor,
                        dados[3],
                        tipoAvaliacao,
                        Boolean.parseBoolean(dados[5]),
                        dados[6],
                        dados[7],
                        Integer.parseInt(dados[8]));
                    
                    turmas.add(turma);
                }
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.out.println("Erro ao carregar turmas: " + e.getMessage());
    }
}

    
    private void verificarPreRequisitos() {
    System.out.print("Código da disciplina: ");
    String codigo = scanner.nextLine();
    Disciplina disciplina = disciplinas.stream()
        .filter(d -> d.getCodigo().equalsIgnoreCase(codigo))
        .findFirst()
        .orElse(null);

    if (disciplina == null) {
        System.out.println("Disciplina não encontrada!");
        return;
    }
    List<Disciplina> prereqs = disciplina.getPreRequisitos();
    if (prereqs.isEmpty()) {
        System.out.println("Esta disciplina não possui pré-requisitos.");
    } else {
        System.out.println("Pré-requisitos:");
        prereqs.forEach(d -> 
            System.out.println("- " + d.getCodigo() + ": " + d.getNome())
        );
    }
}


private void trancarMatricula() {
    System.out.print("Matrícula do aluno: ");
    int matriculaAluno = scanner.nextInt();
    scanner.nextLine();
    
    Aluno aluno = alunos.stream()
        .filter(a -> a.getMatricula() == matriculaAluno)
        .findFirst()
        .orElse(null);
    
    if (aluno == null) {
        System.out.println("Aluno não encontrado!");
        return;
    }
    
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
    
    Matricula matricula = turma.getMatriculas().stream()
        .filter(m -> m.getAluno().equals(aluno))
        .findFirst()
        .orElse(null);
    
    if (matricula != null) {
        matricula.trancar();
        System.out.println("Matrícula trancada com sucesso!");
    } else {
        System.out.println("Aluno não está matriculado nesta turma!");
    }
}

private void gerarStatusTurma() {
    System.out.println("\n--- STATUS POR TURMA ---");
    turmas.forEach(t -> {
        System.out.println("\nTurma: " + t.getDisciplina().getCodigo());
        t.getMatriculas().forEach(m -> {
            String status = m.estaAprovado() ? "APROVADO" : "REPROVADO";
            System.out.printf("- %s: %s%n", m.getAluno().getNome(), status);
        });
    });
}

private void gerarCargaHorariaAlunos() {
    System.out.println("\n--- CARGA HORÁRIA POR ALUNO ---");
    alunos.forEach(a -> {
        int totalHoras = a.getHistorico().stream()
            .filter(m -> !m.isTrancada())
            .mapToInt(m -> m.getTurma().getDisciplina().getCargaHoraria())
            .sum();
        System.out.printf("%s: %d horas%n", a.getNome(), totalHoras);
    });
}

private void relatorioProfessor() {
    Map<Professor, List<Turma>> professorTurmas = turmas.stream()
        .collect(Collectors.groupingBy(Turma::getProfessor));
    
    if (professorTurmas.isEmpty()) {
        System.out.println("Nenhum professor com turmas cadastradas!");
        return;
    }

    System.out.println("\nProfessores disponíveis:");
    professorTurmas.keySet().forEach(p -> 
        System.out.println(p.getMatricula() + " - " + p.getNome())
    );

    System.out.print("\nDigite a matrícula do professor: ");
    int matricula = scanner.nextInt();
    scanner.nextLine();

    professorTurmas.entrySet().stream()
        .filter(e -> e.getKey().getMatricula() == matricula)
        .findFirst()
        .ifPresentOrElse(
            entry -> {
                Professor prof = entry.getKey();
                List<Turma> turmasProf = entry.getValue();
                
                System.out.println("\n--- RELATÓRIO DO PROFESSOR ---");
                System.out.println("Nome: " + prof.getNome());
                System.out.println("Matrícula: " + prof.getMatricula());
                System.out.println("\nTurmas ministradas:");
                
                turmasProf.forEach(t -> {
                    long aprovados = t.getMatriculas().stream()
                        .filter(Matricula::estaAprovado)
                        .count();
                    
                    System.out.printf(
                        "- %s (%s) | %d/%d alunos | %d aprovados%n",
                        t.getDisciplina().getNome(),
                        t.getSemestre(),
                        t.getMatriculas().size(),
                        t.getCapacidadeMaxima(),
                        aprovados
                    );
                });
            },
            () -> System.out.println("Professor não encontrado!")
        );
}

private void relatorioDisciplina() {
    System.out.print("Código da disciplina: ");
    String codigo = scanner.nextLine();
    
    disciplinas.stream()
        .filter(d -> d.getCodigo().equalsIgnoreCase(codigo))
        .findFirst()
        .ifPresentOrElse(
            d -> {
                System.out.println("\n--- RELATÓRIO DA DISCIPLINA ---");
                System.out.println(d.getNome() + " (" + d.getCargaHoraria() + "h)");
                
                turmas.stream()
                    .filter(t -> t.getDisciplina().equals(d))
                    .forEach(t -> {
                        System.out.printf("\nTurma %s - %s (%s)\n",
                            t.getSemestre(),
                            t.getProfessor().getNome(),
                            t.isPresencial() ? "Presencial" : "Remoto");
                        
                        System.out.printf("Média de aprovação: %.1f%%\n",
                            t.getMatriculas().stream()
                                .filter(Matricula::estaAprovado)
                                .count() * 100.0 / 
                                Math.max(1, t.getMatriculas().size()));
                    });
            },
            () -> System.out.println("Disciplina não encontrada!")
        );
}

private void salvarProfessores() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("professores.csv"))) {
        // Extrai todos os professores únicos das turmas
        Set<Professor> professores = turmas.stream()
            .map(Turma::getProfessor)
            .collect(Collectors.toSet());
        
        for (Professor professor : professores) {
            writer.write(professor.toCSV());
            writer.newLine();
        }
        System.out.println("Dados dos professores salvos com sucesso.");
    } catch (IOException e) {
        System.out.println("Erro ao salvar professores: " + e.getMessage());
    }
}

private void carregarProfessores() {
    File arquivo = new File("professores.csv");
    
    if (!arquivo.exists()) {
        try {
            arquivo.createNewFile();
            return;
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo de professores: " + e.getMessage());
            return;
        }
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
        String linha;
        while ((linha = reader.readLine()) != null) {
            if (!linha.trim().isEmpty()) {
                Professor professor = Professor.fromCSV(linha);
                // Verifica se o professor já existe nas turmas
                boolean existe = turmas.stream()
                    .anyMatch(t -> t.getProfessor().equals(professor));
                if (!existe) {
                    // Se não existe, adiciona a uma turma fictícia ou trata conforme necessário
                }
            }
        }
    } catch (IOException e) {
        System.out.println("Erro ao carregar professores: " + e.getMessage());
    }
}


}