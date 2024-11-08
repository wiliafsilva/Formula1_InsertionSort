import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private List<Registro> registros;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().criarInterface());
    }

    public Main() {
        // Carregar automaticamente o arquivo ao iniciar
        try {
            this.registros = lerArquivo("src/Formula1_InsertionSort.txt");
            // Ordenar os registros
            InsertionSort.ordenar(this.registros);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void criarInterface() {
        JFrame frame = new JFrame("Ordenação de Voltas da Fórmula 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        frame.setLayout(new BorderLayout());

        // Painel de botões
        JPanel panel = new JPanel();
        JButton btnSalvar = new JButton("Baixar em txt");

        panel.add(btnSalvar);

        // Área de texto para exibir os registros
        JTextArea areaTexto = new JTextArea(15, 50);
        areaTexto.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        // Adicionando a área de texto no centro da janela
        frame.add(scrollPane, BorderLayout.CENTER);

        // Adicionando o painel de botões na parte inferior
        frame.add(panel, BorderLayout.SOUTH);

        // Exibir os registros ordenados na área de texto
        exibirRegistros(areaTexto);

        // Ação ao clicar no botão "Salvar Arquivo Ordenado"
        btnSalvar.addActionListener(e -> salvarArquivo(areaTexto));

        frame.setVisible(true);
    }

    private void exibirRegistros(JTextArea areaTexto) {
        StringBuilder sb = new StringBuilder();
        for (Registro registro : registros) {
            sb.append(registro.toString()).append("\n");
        }
        areaTexto.setText(sb.toString());
    }

    // Função para ler o arquivo e criar a lista de registros
    private List<Registro> lerArquivo(String caminhoArquivo) throws IOException {
        List<Registro> registros = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Ler o arquivo linha por linha
        for (String linha : Files.readAllLines(Paths.get(caminhoArquivo))) {
            String[] partes = linha.split(" - ");

            String dataHoraStr = partes[0].trim();  // Remover espaços extras
            String piloto = partes[1].trim();
            String equipe = partes[2].trim();
            String tempo = partes[3].trim();

            // Tente fazer o parse da data, e em caso de erro, mostre uma mensagem
            LocalDateTime dataHora = null;
            try {
                dataHora = LocalDateTime.parse(dataHoraStr, formatter);
            } catch (Exception e) {
                System.err.println("Erro ao parsear data: " + dataHoraStr);
                e.printStackTrace();
            }

            // Se a data for válida, adicione o registro à lista
            if (dataHora != null) {
                registros.add(new Registro(dataHora, piloto, equipe, tempo));
            } else {
                // Caso queira, pode adicionar uma lógica aqui para pular o registro com erro.
                System.err.println("Registro com data inválida será ignorado: " + linha);
            }
        }

        return registros;
    }

    // Função para salvar o arquivo com os registros ordenados
    private void salvarArquivo(JTextArea areaTexto) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha o local para salvar o arquivo");

        // Define o nome padrão do arquivo
        File arquivoPadrao = new File("Formula1_Ordenado.txt");
        fileChooser.setSelectedFile(arquivoPadrao);

        // Exibe o JFileChooser
        int resultado = fileChooser.showSaveDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try (BufferedWriter writer = Files.newBufferedWriter(arquivo.toPath())) {
                writer.write(areaTexto.getText());
                JOptionPane.showMessageDialog(null, "Arquivo salvo com sucesso!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo.");
            }
        }
    }
}
