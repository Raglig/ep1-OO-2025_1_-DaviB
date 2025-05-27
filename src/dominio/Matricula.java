package dominio;
import pessoas.Aluno;

public class Matricula {
    private Aluno aluno;
    private Turma turma;
    private boolean trancada;
    private float[] notas; 
    private int faltas;
    private boolean aprovado;

    public Matricula(Aluno aluno, Turma turma) {
        this.aluno = aluno;
        this.turma = turma;
        this.trancada = false;
        this.notas = new float[5];
        this.faltas = 0;
        this.aprovado = false;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public boolean isTrancada() {
        return trancada;
    }

    public void trancar() {
        this.trancada = true;
    }

    public void setNotas(float[] notas) {
        if (notas.length != 5) {
            throw new IllegalArgumentException("Devem ser 5 notas: P1, P2, P3, L, S.");
        }
        this.notas = notas;
        atualizarStatus();
    }

    public float[] getNotas() {
        return notas;
    }

    public void setNota(int index, float valor) {
        if (index < 0 || index >= notas.length) {
            throw new IndexOutOfBoundsException("Índice de nota inválido (0 a 4).");
        }
        this.notas[index] = valor;
        atualizarStatus();
    }

    public void adicionarFalta() {
        this.faltas++;
        atualizarStatus();
    }

    public int getFaltas() {
        return faltas;
    }

    public float calcularMediaFinal() {
        return turma.getTipoAvaliacao().calcularMedia(notas);
    }

    public float calcularFrequencia() {
        int totalAulas = turma.getTotalAulas();
        if (totalAulas == 0) return 0;
        int presencas = totalAulas - faltas;
        return (presencas / (float) totalAulas) * 100f;
    }

    public void atualizarStatus() {
        this.aprovado = calcularMediaFinal() >= 5.0f && 
                       calcularFrequencia() >= 75.0f;
    }

    public boolean estaAprovado() {
        return aprovado;
    }

    @Override
    public String toString() {
        return String.format("Aluno: %s | Média: %.2f | Frequência: %.1f%% | Status: %s",
                aluno.getNome(),
                calcularMediaFinal(),
                calcularFrequencia(),
                estaAprovado() ? "Aprovado" : "Reprovado"
        );
    }
}