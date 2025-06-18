package comunicacao;

import pessoas.Passageiro;
import enums.ClassePassagem; // Importa o enum ClassePassagem
import javax.swing.JTextArea;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

// Assumindo que a interface 'Comunicavel' existe e tem os métodos:
// public interface Comunicavel {
//     String getNome();
//     void receberMensagem(String mensagem);
// }
// Ambas as classes Passageiro e Administrativo devem implementar esta interface.

public class CentralComunicacao {

    // Lista de todos os usuários comunicáveis atualmente "registrados" ou "ativos"
    // (Pode ser usado para quem está logado ou pode receber mensagens no chat)
    public static final List<Comunicavel> usuariosComunicaveisAtivos = new ArrayList<>();

    // Mapa para armazenar passageiros em memória para lookup eficiente por CPF
    // (Isso representa todos os passageiros conhecidos que foram carregados do arquivo ou cadastrados)
    private static final Map<String, Passageiro> passageirosCadastrados = new HashMap<>();

    // Área de texto compartilhada para o chat (mantida como você tinha)
    public static JTextArea areaMensagensCompartilhada = new JTextArea();
    
    // Caminhos dos arquivos CSV
    private static final String ARQUIVO_PASSAGEIROS = "dados/passageiros.csv";
    private static final String ARQUIVO_MENSAGENS = "dados/mensagens_chat.txt";

    // Bloco estático: é executado uma vez, quando a classe é carregada.
    // Isso garante que os passageiros e mensagens sejam carregados ao iniciar a aplicação.
    static {
        carregarPassageirosDoArquivo();
        carregarMensagensDoArquivo();
    }

    /**
     * Registra um usuário como comunicável.
     * Se o comunicável for um Passageiro, também o adiciona ao mapa de passageiros
     * e o salva no arquivo, se ainda não estiver lá.
     * Este método agora aceita um 'Comunicavel' genérico.
     * @param c O objeto Comunicavel a ser registrado.
     */
    public static void registrar(Comunicavel c) {
        // Adiciona à lista geral de usuários comunicáveis ativos, se ainda não estiver lá
        if (!usuariosComunicaveisAtivos.contains(c)) { 
            usuariosComunicaveisAtivos.add(c);
        }

        // Se o comunicável for um Passageiro, adicione-o também ao mapa de passageiros
        // para lookup eficiente por CPF e salve no arquivo (se for um novo cadastro).
        if (c instanceof Passageiro) {
            Passageiro passageiro = (Passageiro) c;
            // Se o passageiro ainda não está no mapa de passageiros cadastrados, adicione-o
            // e salve no arquivo. Isso acontece em novos cadastros.
            if (!passageirosCadastrados.containsKey(passageiro.getCPF())) {
                passageirosCadastrados.put(passageiro.getCPF(), passageiro);
                salvarPassageiroNoArquivo(passageiro, true); // Adiciona ao final do arquivo
            } else {
                // Se o passageiro já está no mapa (por exemplo, após um login de um passageiro existente),
                // apenas atualiza a referência no mapa, caso haja alguma mudança de estado,
                // mas não o salva novamente no arquivo (já está lá).
                passageirosCadastrados.put(passageiro.getCPF(), passageiro);
            }
        }
        // Futuramente, pode-se adicionar lógica específica para Administrativo aqui, se necessário.
    }

    /**
     * Carrega os dados dos passageiros do arquivo CSV para a memória.
     * Este método é chamado automaticamente ao iniciar a aplicação.
     */
    private static void carregarPassageirosDoArquivo() {
        File arquivo = new File(ARQUIVO_PASSAGEIROS);
        // Cria o diretório 'dados' se não existir
        File diretorioDados = new File("dados");
        if (!diretorioDados.exists()) {
            diretorioDados.mkdirs();
        }

        // Se o arquivo de passageiros não existir, cria-o com um cabeçalho
        if (!arquivo.exists()) {
            System.out.println("Arquivo de passageiros não encontrado. Criando um novo com cabeçalho.");
            try (FileWriter fw = new FileWriter(arquivo, false)) { // false para sobrescrever/criar
                fw.write("id,nome,cpf,dataNascimento,senha,classePassagem\n");
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo de passageiros: " + e.getMessage());
            }
            return; // Não há dados para carregar se o arquivo acabou de ser criado
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_PASSAGEIROS))) {
            String linha;
            boolean primeiraLinha = true;
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    // Ignora o cabeçalho se a linha começar com 'id,nome,cpf...'
                    if (linha.startsWith("id,nome,cpf")) {
                        continue;
                    }
                }
                String[] partes = linha.split(",");
                // Espera 6 partes: id, nome, cpf, dataNascimento, senha, classePassagem
                if (partes.length == 6) {
                    String id = partes[0].trim();
                    String nome = partes[1].trim();
                    String cpf = partes[2].trim();
                    String dataNascimento = partes[3].trim();
                    String senha = partes[4].trim();
                    ClassePassagem classe = null;
                    try {
                        classe = ClassePassagem.valueOf(partes[5].trim().toUpperCase());
                    } catch (IllegalArgumentException e) {
                        System.err.println("Classe de passagem inválida para o passageiro " + nome + ": " + partes[5] + ". Usando INDEFINIDA.");
                        classe = ClassePassagem.INDEFINIDA; // Define como indefinida se houver erro
                    }
                    Passageiro p = new Passageiro(id, nome, cpf, dataNascimento, senha, classe);
                    passageirosCadastrados.put(cpf, p); // Adiciona o passageiro ao mapa em memória
                } else {
                    System.err.println("Linha inválida no arquivo de passageiros (formato incorreto): " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar passageiros do arquivo: " + e.getMessage());
        }
    }

    /**
     * Salva um único passageiro no arquivo CSV.
     * Pode adicionar ao final ou sobrescrever.
     * @param passageiro O objeto Passageiro a ser salvo.
     * @param append Se true, adiciona ao final do arquivo; se false, sobrescreve.
     */
    private static void salvarPassageiroNoArquivo(Passageiro passageiro, boolean append) {
        // Verifica se o arquivo está vazio e adiciona o cabeçalho se for a primeira escrita e estiver vazio
        File arquivo = new File(ARQUIVO_PASSAGEIROS);
        boolean precisaAdicionarCabecalho = !arquivo.exists() || (arquivo.length() == 0 && append);

        try (FileWriter fw = new FileWriter(ARQUIVO_PASSAGEIROS, append);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            if (precisaAdicionarCabecalho) {
                 bw.write("id,nome,cpf,dataNascimento,senha,classePassagem\n");
            }
            bw.write(passageiro.getId() + "," +
                     passageiro.getNome() + "," +
                     passageiro.getCPF() + "," +
                     passageiro.getDataNascimento() + "," +
                     passageiro.getSenha() + "," +
                     passageiro.getClassePassagem().name() + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao salvar passageiro no arquivo: " + e.getMessage());
        }
    }

    /**
     * Retorna um objeto Passageiro a partir do CPF.
     * @param cpf O CPF do passageiro a ser buscado.
     * @return O objeto Passageiro correspondente ao CPF, ou null se não encontrado.
     */
    public static Passageiro getPassageiroPorCpf(String cpf) {
        return passageirosCadastrados.get(cpf);
    }

    /**
     * Envia mensagem entre comunicáveis.
     * @param remetente O Comunicavel que envia a mensagem.
     * @param destinatario O Comunicavel que recebe a mensagem.
     * @param mensagem O conteúdo da mensagem.
     */
    public static void enviarMensagem(Comunicavel remetente, Comunicavel destinatario, String mensagem) {
        // Formata a mensagem.
        String formatada = remetente.getNome() + " -> " + destinatario.getNome() + ": " + mensagem;

        // Quem irá receber a mensagem formatada.
        destinatario.receberMensagem(formatada);
        if (areaMensagensCompartilhada != null) {
            // Registra a mensagem na área.
            areaMensagensCompartilhada.append(formatada + "\n");
        }
        // Salva a mensagem.
        salvarMensagem(remetente.getNome(), destinatario.getNome(), mensagem); // Usa o método sobrecarregado
    }

    /**
     * Salva uma mensagem no arquivo de chat.
     * @param remetente O nome do remetente.
     * @param destinatario O nome do destinatário.
     * @param mensagem O conteúdo da mensagem.
     */
    public static void salvarMensagem(String remetente, String destinatario, String mensagem) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_MENSAGENS, true))) {
            writer.write(remetente + " -> " + destinatario + " | Mensagem: " + mensagem);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao salvar mensagem no histórico: " + e.getMessage());
        }
    }

    /**
     * Carrega as mensagens existentes do arquivo para a área de chat compartilhada.
     */
    private static void carregarMensagensDoArquivo() {
        File arquivo = new File(ARQUIVO_MENSAGENS);
        File diretorioDados = new File("dados");
        if (!diretorioDados.exists()) {
            diretorioDados.mkdirs();
        }
        
        if (!arquivo.exists()) {
            try {
                arquivo.createNewFile(); // Cria o arquivo se ele não existir
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de mensagens: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO_MENSAGENS))) {
            String linha;
            if (areaMensagensCompartilhada == null) {
                areaMensagensCompartilhada = new JTextArea();
            }
            while ((linha = br.readLine()) != null) {
                areaMensagensCompartilhada.append(linha + "\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar mensagens anteriores: " + e.getMessage());
        }
    }

    // Método para obter todos os passageiros cadastrados (opcional, para outras telas)
    public static Map<String, Passageiro> getTodosPassageirosCadastrados() {
        return new HashMap<>(passageirosCadastrados); // Retorna uma cópia para evitar modificações externas
    }
}
