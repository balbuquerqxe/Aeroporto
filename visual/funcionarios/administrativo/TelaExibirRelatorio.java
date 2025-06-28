// O 'package' deve corresponder à estrutura de pastas do seu projeto.
package visual.funcionarios.administrativo;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Insets;


// Janela para exibir o conteúdo do novo relatório

public class TelaExibirRelatorio extends JFrame {

    /**
     * O construtor da tela que recebe o texto do relatório e monta a janela.
     * @param conteudoDoRelatorio A String completa contendo o texto a ser exibido.
     */
    public TelaExibirRelatorio(String conteudoDoRelatorio) {
                
        setTitle("Relatório Gerado"); 
        setSize(800, 600); 
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        setLocationRelativeTo(null);
        
        JTextArea areaDeTexto = new JTextArea();
        
        areaDeTexto.setFont(new Font("Monospaced", Font.PLAIN, 13)); 
        areaDeTexto.setEditable(false); 
        areaDeTexto.setLineWrap(true); 
        areaDeTexto.setWrapStyleWord(true); 
        areaDeTexto.setMargin(new Insets(10, 10, 10, 10)); 
        areaDeTexto.setText(conteudoDoRelatorio);
        
        areaDeTexto.setCaretPosition(0); 

        JScrollPane painelComRolagem = new JScrollPane(areaDeTexto);
        painelComRolagem.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        painelComRolagem.setBorder(null); 

        add(painelComRolagem);
    }
}