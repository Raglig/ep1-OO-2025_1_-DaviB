package dominio;

import java.util.*;

public class Disciplina {
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private List<Disciplina> preRequisitos;
    private List<String> codigosPreRequisitos = new ArrayList<>();

    // Construtor “básico” (quando for chamado no carregarCSV)
    public Disciplina(String nome, String codigo, int cargaHoraria) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = new ArrayList<>();
    }

    // Construtor original (se quiser instanciar passando já a lista de objetos)
    public Disciplina(String nome, String codigo, int cargaHoraria, List<Disciplina> preRequisitos) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = preRequisitos;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }
    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public List<Disciplina> getPreRequisitos() {
        return preRequisitos;
    }

    public void adicionarPreRequisito(Disciplina prereq) {
        Objects.requireNonNull(prereq, "Pré-requisito não pode ser nulo");
        if (this.equals(prereq)) {
            throw new IllegalArgumentException("Uma disciplina não pode ser pré-requisito de si mesma");
        }
        if (!preRequisitos.contains(prereq)) {
            preRequisitos.add(prereq);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Disciplina)) return false;
        Disciplina that = (Disciplina) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo + " - " + nome + " (" + cargaHoraria + "h)";
    }

    public String toCSV() {
    StringBuilder sb = new StringBuilder();
    sb.append(nome).append(";")
      .append(codigo).append(";")
      .append(cargaHoraria).append(";");
    
    for (Disciplina pre : preRequisitos) {
        sb.append(pre.getCodigo()).append(",");
    }
    
    if (!preRequisitos.isEmpty()) {
        sb.setLength(sb.length() - 1); // Remove última vírgula
    }
    
    return sb.toString();
}

    public static Disciplina fromCSV(String linha, Map<String, Disciplina> mapaDisciplinas) {
    String[] dados = linha.split(";");
    List<Disciplina> preRequisitos = new ArrayList<>();

    if (dados.length >= 4 && !dados[3].isEmpty()) {
        Arrays.stream(dados[3].split(","))
            .forEach(cod -> {
                Disciplina pre = mapaDisciplinas.get(cod.trim());
                if (pre != null) preRequisitos.add(pre);
            });
    }

    return new Disciplina(
        dados[0],  // nome
        dados[1],  // código
        Integer.parseInt(dados[2]),  // carga horária
        preRequisitos
    );
}

    public void linkarPreRequisitos(Map<String, Disciplina> mapa) {
        for (String codPre : codigosPreRequisitos) {
            Disciplina prereq = mapa.get(codPre);
            if (prereq != null && !this.equals(prereq) && !preRequisitos.contains(prereq)) {
                preRequisitos.add(prereq);
            }
        }
        codigosPreRequisitos.clear();
    }
}
