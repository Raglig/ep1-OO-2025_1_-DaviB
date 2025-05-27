package dominio;
import java.util.ArrayList;
import java.util.List;

import avaliacao.TipoAvaliacao;
import pessoas.Aluno;
import pessoas.Professor;

public class Turma {
    private Disciplina disciplina;
    private Professor professor;
    private String semestre;
    private TipoAvaliacao tipoAvaliacao;
    private boolean presencial;
    private String sala;
    private String horario;
    private int capacidadeMaxima;
    private List<Matricula> matriculas = new ArrayList<>();

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

    public List<Matricula> getMatriculas() {
        return new ArrayList<>(matriculas);
    }

    public int getTotalAulas() {
        return disciplina.getCargaHoraria() / 4; // Exemplo: 60h = 15 aulas
    }

    public boolean matricularAluno(Aluno aluno) {
    if (matriculas.size() >= capacidadeMaxima) return false;
    matriculas.add(new Matricula(aluno, this));
    return true;
    }

    public boolean temConflito(Turma outra) {
        return this.semestre.equals(outra.semestre) && 
        this.horario.equals(outra.horario);
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
