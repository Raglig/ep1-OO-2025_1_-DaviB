public abstract class Aluno extends Pessoa {
    private String curso;

    public Aluno(String nome, int matricula, String curso){
        super(nome, matricula);
        this.curso=curso;
    }

    public String getCurso(){
        return curso;
    }

    public void setCurso(String curso){
        this.curso=curso;
    }
}

