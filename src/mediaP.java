public class MediaP implements TipoAvaliacao {
    private float[] pesos;

    public MediaP(float[] pesos) {
        this.pesos = pesos;
    }

    @Override
    public float calcularMedia(float[] notas) {
        if (notas.length != pesos.length) {
            throw new IllegalArgumentException("Quantidade de notas e pesos incompatível.");
        }
        float somaNota = 0, somaPesos = 0;
        for (int i = 0; i < notas.length; i++) {
            somaNota += notas[i] * pesos[i];
            somaPesos += pesos[i];
        }
        return somaNota / somaPesos;
    }
} 