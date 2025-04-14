import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordleUI {
    private JFrame frame;
    private JPanel contentPanel;
    private JTextField[][] letterGrid;
    private String currentWord = "";
    private String targetWord = "";
    private int row = 0;
    private JButton[] keyboardGrid = new JButton[26];
   
    public WordleUI() {
        frame = new JFrame("Wordle Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 1050);
        frame.setLayout(null);
        frame.setResizable(false);
               
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBounds(10, 380, 380,15);
        String keys = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] letters = keys.toCharArray();
        for (int i = 0 ; i < letters.length; i++)
        {
            char key = letters[i]; 
            System.out.println(String.valueOf(key));
            int yCord;
            int xCord;
            int curSection; 
            if (i < 9) {
                yCord = 700;
                xCord = 30;
                curSection = 0;
            } else if (i < 18) {
                yCord = 800;
                xCord = 30;
                curSection = 9;
            } else {
                yCord = 900;
                xCord = 30;
                curSection = 18;
            }
            
            JButton button = new JButton(String.valueOf(key));
            button.setBounds(xCord + (i - curSection) * 80, yCord, 75,75);
            button.setFont(new Font("Arial", Font.BOLD, 10));
            button.setBackground(Color.GRAY);
            button.setForeground(Color.PINK);
            button.addActionListener(e -> addLetter(key));
            contentPanel.add(button);
            keyboardGrid[i] = button;
        }
        
        JButton enterButton = new JButton("ENTER");
        enterButton.setFont(new Font("Arial", Font.BOLD, 10));
        enterButton.setBounds(670,900, 75, 75);
        enterButton.addActionListener(e -> submitWord());
        contentPanel.add(enterButton);
       
        JButton backspaceButton = new JButton("DELETE");
        backspaceButton.setFont(new Font("Arial", Font.BOLD, 10));
        backspaceButton.setFocusPainted(false);
        backspaceButton.setBounds(750,900, 75, 75);
        backspaceButton.addActionListener(e -> removeLetter());
        contentPanel.add(backspaceButton);
         
        letterGrid = new JTextField[6][5];
        int yGridCord;
        int xGridCord;
        for (int i = 0 ; i < letterGrid[0].length; i++) {
            for (int k = 0; k < letterGrid.length; k++) {
                letterGrid[k][i] = new JTextField();
                letterGrid[k][i].setBounds(140 + i * 110, 20 + k * 110, 90, 90);
                letterGrid[k][i].setFont(new Font("Arial", Font.BOLD, 50));
                letterGrid[k][i].setEditable(false);  
                letterGrid[k][i].setFocusable(false);  
                contentPanel.add(letterGrid[k][i]);
            }
        }
                  
        frame.setContentPane(contentPanel);
        frame.setVisible(true);
        
        targetWord = Words.getTargetWord(); 
    }
   
    private void addLetter(char letter) {
        if (currentWord.length() < 5) {
            letterGrid[row][currentWord.length()].setText(String.valueOf(letter));
            currentWord += letter;
        }
    }
   
    private void removeLetter() {
        if (!currentWord.isEmpty()) {
            int index = currentWord.length() - 1;
            letterGrid[row][index].setText("");
            currentWord = currentWord.substring(0, index);
        }
    }
   
    private void submitWord() {
        System.out.println("TARGET WORD " + targetWord);
        if (currentWord.length() == 5) {
            if(currentWord.equals(targetWord)) {
                JOptionPane.showMessageDialog(frame, "Congrats. You won in " + (row + 1) + " guesses");
                return; 
            }
            if (Words.isRealWord(currentWord)) {
                updateLetters(currentWord, row);
                row++;
                currentWord = "";
                if (row >= 6) {
                    JOptionPane.showMessageDialog(frame, "Game Over!");
                }   
            } else {
                JOptionPane.showMessageDialog(frame, "Not Real Word Try Again");
                return;
            }
        }
    }
    
    private void updateLetters(String guessedWord, int row) {
        for (int i = 0 ; i < guessedWord.length(); i++) {
            for (int k = 0 ; k < keyboardGrid.length; k++) {
                if(guessedWord.substring(i, i + 1).equals(keyboardGrid[k].getText()) &&
                keyboardGrid[k].getBackground().equals(Color.GRAY)) {
                    keyboardGrid[k].setBackground(Color.RED);
                }
            }
        }
        //green and yellow iterations

        Object[][] letterStatus = new Object[guessedWord.length()][2];  

        for (int i = 0; i < guessedWord.length(); i++) {  
            letterStatus[i][0] = guessedWord.substring(i, i+1); 
            letterStatus[i][1] = false;   
        }  
        
        for (int i = 0; i < guessedWord.length(); i++) {
            if (guessedWord.substring(i, i+1).equals(targetWord.substring(i, i+1))) {
                letterGrid[row][i].setBackground(Color.GREEN);
                keyboardGrid[i].setBackground(Color.GREEN);
                letterStatus[i][1] = true;
            }
        }
        
        
        for (int i = 0 ; i < guessedWord.length(); i++) {
            if (!(Boolean) letterStatus[i][1] && targetWord.contains(guessedWord.substring(i, i+1))) {
                letterGrid[row][i].setBackground(Color.YELLOW);
                keyboardGrid[i].setBackground(Color.YELLOW);
            }        
        }
    }
   
    public static void main() {
        SwingUtilities.invokeLater(WordleUI::new);
    }
}