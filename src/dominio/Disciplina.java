package dominio;
import java.util.List;

public class Disciplina {  
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private List<Disciplina> preRequisitos;

    public Disciplina(String nome, String codigo, int cargaHoraria, List<Disciplina> preRequisitos) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = preRequisitos;
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public List<Disciplina> getPreRequisitos() {
        return preRequisitos;
    }

    @Override
    public String toString(){
        return codigo + " - " + nome + " (" + cargaHoraria + "h)";
    }
    
    public void adicionarPreRequisito(Disciplina prereq) {
    if (!preRequisitos.contains(prereq)) {
        preRequisitos.add(prereq);
    }
}

}