public class mediaP implements tipoAvaliacao{
    public float[] pesos;
    @Override
    public float calcular(float[] notas) {
        float nota=0, somaPesos=0;
        for (int i = 0; i < notas.length; i++) {
            nota += notas[i] * pesos[i];
            somaPesos += pesos[i];
        }
        return nota/somaPesos;
    }

    
}
