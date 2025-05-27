package pessoas;

import dominio.Disciplina;

public class AlunoNormal extends Aluno {
    public AlunoNormal(String nome, int matricula, String curso){
        super(nome, matricula, curso);
    }
    @Override
        public boolean podeMatricular(Disciplina disciplina){
            return true;
        }  
}
