package dominio;
import pessoas.Aluno;

public class Matricula {
    private Aluno aluno;
    private Turma turma;
    private boolean trancada;
    private float[] notas; 
    private int presencas;

    public Matricula(Aluno aluno, Turma turma) {
        this.aluno = aluno;
        this.turma = turma;
        this.trancada = false;
        this.notas = new float[5];
        this.presencas = 0;
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
    }

    public float[] getNotas() {
        return notas;
    }

    public void setNota(int index, float valor) {
        if (index < 0 || index >= notas.length) {
            throw new IndexOutOfBoundsException("Índice de nota inválido (0 a 4).");
        }
        this.notas[index] = valor;
    }

    public void setPresencas(int presencas) {
        this.presencas = presencas;
    }

    public void adicionarPresenca() {
        this.presencas++;
    }

    public int getPresencas() {
        return presencas;
    }

    public float calcularMediaFinal() {
        return turma.getTipoAvaliacao().calcularMedia(notas);
    }

    public float calcularFrequencia(int totalAulas) {
        if (totalAulas == 0) return 0;
        return (presencas / (float) totalAulas) * 100f;
    }

    public boolean estaAprovado(int totalAulas) {
        float media = calcularMediaFinal();
        float freq = calcularFrequencia(totalAulas);
        return media >= 5.0f && freq >= 75.0f;
    }

    @Override
    public String toString() {
        return String.format("Aluno: %s | Média: %.2f | Frequência: %.1f%% | Aprovado: %s",
                aluno.getNome(),
                calcularMediaFinal(),
                calcularFrequencia(60), // número de aulas padrão (ajustável conforme uso)
                estaAprovado(60) ? "Sim" : "Não"
        );
    }
}