package pessoas;

import java.util.ArrayList;
import java.util.List;

import dominio.Disciplina;
import dominio.Matricula;

public abstract class Aluno extends Pessoa {
    private String curso;
    private List<Matricula> historico = new ArrayList<>();
    public abstract boolean podeMatricular(Disciplina disciplina);


    public Aluno(String nome, int matricula, String curso){
        super(nome, matricula);
        this.curso=curso;
    }

    public List<Matricula> getHistorico() {
        return historico;
    }

    public String getCurso(){
        return curso;
    }

    public void setCurso(String curso){
        this.curso=curso;
    }


    public boolean cumpriuPreRequisitos(Disciplina disciplina) {
        List<Disciplina> cursadas = historico.stream()
            .filter(m -> m.estaAprovado())
            .map(m -> m.getTurma().getDisciplina())
            .toList();
        
        return cursadas.containsAll(disciplina.getPreRequisitos());
    }
}


