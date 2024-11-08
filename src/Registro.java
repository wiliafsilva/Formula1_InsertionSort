import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Registro {
    private LocalDateTime dataHora;
    private String piloto;
    private String equipe;
    private double tempo;  // O tempo é armazenado em segundos para facilitar a ordenação.

    // Construtor da classe
    public Registro(LocalDateTime dataHora, String piloto, String equipe, String tempoStr) {
        this.dataHora = dataHora;
        this.piloto = piloto;
        this.equipe = equipe;
        this.tempo = parseTempo(tempoStr);  // Converte o tempo para segundos.
    }

    // Método para converter o tempo para segundos
    private double parseTempo(String tempoStr) {
        String[] partes = tempoStr.split(":");
        double minutos = Double.parseDouble(partes[0]);
        // Convertendo segundos com ponto decimal corretamente
        double segundos = Double.parseDouble(partes[1].replace(",", "."));  // Garantir que seja ponto no lugar de vírgula
        return minutos * 60 + segundos;
    }

    // Métodos getters
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getPiloto() {
        return piloto;
    }

    public String getEquipe() {
        return equipe;
    }

    public double getTempo() {
        return tempo;
    }

    @Override
    public String toString() {
        int minutos = (int) (tempo / 60);
        double segundos = tempo % 60;
        return dataHora + " - " + piloto + " - " + equipe + " - " + String.format("%02d:%06.3f", minutos, segundos);
    }

    // Método de comparação
    public int compareTo(Registro outro) {
        if (this.tempo != outro.tempo) {
            return Double.compare(this.tempo, outro.tempo);  // Compara pelo tempo (menor é melhor)
        }
        return this.piloto.compareTo(outro.piloto);  // Se os tempos forem iguais, ordena pelo nome do piloto
    }
}
