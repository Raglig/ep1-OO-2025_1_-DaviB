public class MediaS implements TipoAvaliacao {  
    @Override
    public float calcularMedia(float[] notas) {
        if (notas.length == 0) {
            return 0f;
        }
        float soma = 0;
        for (float nota : notas) {
            soma += nota;
        }
        return soma / notas.length;
    }
}