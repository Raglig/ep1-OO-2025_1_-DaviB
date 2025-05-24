import java.util.ArrayList;
import java.util.List;

public class Turma {
    private Disciplina disciplina;
    private Professor professor;
    private String semestre;
    private TipoAvaliacao tipoAvaliacao;
    private boolean presencial;
    private String sala;
    private String horario;
    private int capacidadeMaxima;
    private List<Aluno> alunosMatriculados;

    public Turma(Disciplina disciplina, Professor professor,
                 String semestre, TipoAvaliacao tipoAvaliacao,
                 boolean presencial, String sala,
                 String horario, int capacidadeMaxima) {
        this.disciplina = disciplina;
        this.professor = professor;
        this.semestre = semestre;
        this.tipoAvaliacao = tipoAvaliacao;
        this.presencial = presencial;
        this.sala = sala;
        this.horario = horario;
        this.capacidadeMaxima = capacidadeMaxima;
        this.alunosMatriculados = new ArrayList<>();
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }
    public Professor getProfessor() {
        return professor;
    }
    public String getSemestre() {
        return semestre;
    }
    public TipoAvaliacao getTipoAvaliacao() {
        return tipoAvaliacao;
    }
    public boolean isPresencial() {
        return presencial;
    }
    public String getSala() {
        return sala;
    }
    public String getHorario() {
        return horario;
    }
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
    public List<Aluno> getAlunosMatriculados() {
        return alunosMatriculados;
    }

    public boolean matricularAluno(Aluno aluno) {
        if (alunosMatriculados.size() >= capacidadeMaxima) {
            return false;
        }
        // (aqui checar pré-requisitos antes de adicionar)
        alunosMatriculados.add(aluno);
        return true;
    }

    public void removerAluno(Aluno aluno) {
        alunosMatriculados.remove(aluno);
    }

    @Override
    public String toString() {
        String modo = (presencial ? "Presencial" : "Remota");
        String infoSala = (presencial ? " Sala: " + sala : " (Remota)");
        return disciplina.getCodigo() + " [" + semestre + "] Prof: " +
               professor.getNome() + " - " + modo + infoSala +
               " Horário: " + horario + " Capacidade: " + capacidadeMaxima;
    }
}
