package pessoas;

import dominio.Disciplina;

public class AlunoEspecial extends Aluno{
    public AlunoEspecial(String nome, int matricula, String curso){
        super(nome, matricula, curso);


    }
        @Override
        public boolean podeMatricular(Disciplina disciplina){
            return super .getHistorico().stream()
                .filter(m -> !m.isConcluida())
                .count() < 2;
        }    
    
}