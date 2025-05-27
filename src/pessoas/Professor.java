package pessoas;
public class Professor extends Pessoa {

    public Professor(String nome, int matricula) {
        super(nome, matricula);
        
    }

    public String toCSV() {
        return String.format("%s;%d", getNome(), getMatricula());
    }
    
    public static Professor fromCSV(String linha) {
        String[] dados = linha.split(";");
        return new Professor(dados[0], Integer.parseInt(dados[1]));
    }
    
    
}
