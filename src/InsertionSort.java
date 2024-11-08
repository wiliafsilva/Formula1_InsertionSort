import java.util.List;

public class InsertionSort {

    public static void ordenar(List<Registro> registros) {
        for (int i = 1; i < registros.size(); i++) {
            Registro chave = registros.get(i);
            int j = i - 1;

            // Move os elementos que são maiores que a chave para uma posição à frente
            while (j >= 0 && registros.get(j).compareTo(chave) > 0) {
                registros.set(j + 1, registros.get(j));
                j--;
            }
            registros.set(j + 1, chave);
        }
    }
}
