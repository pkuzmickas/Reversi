/**
 * A class to control the interface of the Game
 * 
 * @author Paulius Kuzmickas 
 * @version 1.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;

public class Interface implements ActionListener, MouseListener
{
    private Board board;
    private Player player1 = new Player();
    private Player player2 = new Player();
    int[][] theBoard;
    int[][] boardOfChange;
    JButton[][] gridButtons;
    JPanel grid;
    private JFrame frame, winnerScreen, optionsFrame;
    private JPanel panel, optionsPanel;
    int turn = 2, AIRow=0, AIColumn=0;
    JLabel background = new JLabel();
    JLabel optionBG = new JLabel();
    JLabel player1NameLabel, player2NameLabel, player1PiecesLabel, player2PiecesLabel, turnLabel,  player1PieceColorLabel, player2PieceColorLabel, winnerLabel, winnerLabel1, winnerLabel2;
    String player1Name, player2Name;
    int player1Pieces, player2Pieces;
    boolean possibleToMove, endGame = false, optionsOn=false;

    /**
     * The constructor for the Interface class.
     * Initializes the Frame and the game board.
     */
    public Interface() { 
        initFrame();
        initGameButtons();

    }
    
    /**
     * A method to initialize the main frame.
     */
    public void initFrame()
    {
        frame = new JFrame("Reversi");
        panel = new JPanel();
        frame.setResizable(false);
        frame.setSize(1366, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.add(panel);
        frame.addMouseListener(this);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    /**
     * A method to initialize the game board and button
     */
    public void initGameButtons()
    {
        board = new Board();
        theBoard = board.getBoard();
        boardOfChange = new int[board.getRows()][board.getColumns()];
        gridButtons = new JButton[board.getRows()][board.getColumns()];
        grid = new JPanel(new GridLayout(board.getRows(),board.getColumns()));
        for (int i=0; i<board.getRows(); i++)
        {
            for (int j=0; j<board.getColumns(); j++)
            {
                gridButtons[i][j] = new JButton();

                gridButtons[i][j].setBackground(new Color(34, 177, 76));
                gridButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                gridButtons[i][j].setActionCommand("" + i + "" + j); // String "0", "1" etc.
                gridButtons[i][j].addActionListener(this);
                gridButtons[i][j].addMouseListener(this);
                grid.add(gridButtons[i][j]);
            }
        }
    }

    /**
     * A method to open the main menu screen and control all the buttons within it.
     */
    public void mainMenu()
    {

        JButton newGame, loadGame, help, quitButton;
        panel.removeAll(); 
        panel.add(background);

        background.setIcon( new ImageIcon("images/background.png"));
        frame.pack();
        panel.setLayout(null);

        ImageIcon newGameIcon = new ImageIcon("images/New Game.png");
        ImageIcon newGameIconPressed = new ImageIcon("images/New GameP.png");
        newGame = createButton(newGameIcon);
        newGame.setBounds(130,280,550,100);
        newGame.addActionListener(new ActionListener() { // controls what happens after clicking the button
                public void actionPerformed(ActionEvent evt) {
                    newGame();
                }
            });
        newGame.addMouseListener(new MouseAdapter() { // controls the image change on click
                public void mousePressed(MouseEvent evt) {
                    newGame.setIcon(newGameIconPressed);
                }

                public void mouseReleased(MouseEvent evt) { // controls the image change on release
                    newGame.setIcon(newGameIcon);
                }
            });
        panel.add(newGame);

        ImageIcon loadGameIcon = new ImageIcon("images/Load Game.png");
        ImageIcon loadGameIconPressed = new ImageIcon("images/Load GameP.png");
        loadGame = createButton(loadGameIcon);
        loadGame.setBounds(290,380,550,100);
        loadGame.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    loadGameFromMenu();
                }
            });
        loadGame.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    loadGame.setIcon(loadGameIconPressed);
                }

                public void mouseReleased(MouseEvent evt) {
                    loadGame.setIcon(loadGameIcon);
                }
            });
        panel.add(loadGame);

        ImageIcon helpIcon = new ImageIcon("images/Help.png");
        ImageIcon helpIconPressed = new ImageIcon("images/HelpP.png");
        help = createButton(helpIcon);
        help.setBounds(450,480,550,100);
        help.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(Desktop.isDesktopSupported())
                    {
                        try {
                            Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Reversi"));
                        } catch(Exception ex) {
                            System.out.println("Problem opening the webpage.");
                        }
                    }
                }
            });
        help.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    help.setIcon(helpIconPressed);
                }

                public void mouseReleased(MouseEvent evt) {
                    help.setIcon(helpIcon);
                }
            });
        panel.add(help);

        ImageIcon quitButtonIcon = new ImageIcon("images/Exit.png");
        ImageIcon quitButtonIconPressed = new ImageIcon("images/ExitP.png");
        quitButton = createButton(quitButtonIcon);
        quitButton.setBounds(610,580,550,100);
        quitButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }
            });
        quitButton.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    quitButton.setIcon(quitButtonIconPressed);
                }

                public void mouseReleased(MouseEvent evt) {
                    quitButton.setIcon(quitButtonIcon);
                }
            });
        panel.add(quitButton);

        panel.setComponentZOrder(newGame, 0);
        panel.setComponentZOrder(loadGame, 0);
        panel.setComponentZOrder(help, 0);
        panel.setComponentZOrder(quitButton, 0);
        /*panel.setComponentZOrder(background, 1);*/
        panel.repaint();
        frame.repaint();
        frame.revalidate();
    }

    /**
     * A method to open the new game screen and control all the buttons within it.
     */
    public void newGame()
    {
        turn = 2;

        JButton player1, player2;
        panel.removeAll();

        panel.add(background);
        background.setIcon( new ImageIcon("images/playerChoose.jpg"));

        ImageIcon player1Icon = new ImageIcon("images/1player.png");
        ImageIcon player1IconPressed = new ImageIcon("images/1playerP.png");
        player1 = createButton(player1Icon);
        player1.setBounds(400,300,550,100);
        player1.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    Player1Screen();
                }
            });
        player1.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    player1.setIcon(player1IconPressed);
                }

                public void mouseReleased(MouseEvent evt) {
                    player1.setIcon(player1Icon);
                }
            });
        panel.add(player1);

        ImageIcon player2Icon = new ImageIcon("images/2player.png");
        ImageIcon player2IconPressed = new ImageIcon("images/2playerP.png");
        player2 = createButton(player2Icon);
        player2.setBounds(403,400,550,100);
        player2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    Player2Screen();
                }
            });
        player2.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    player2.setIcon(player2IconPressed);
                }

                public void mouseReleased(MouseEvent evt) {
                    player2.setIcon(player2Icon);
                }
            });
        panel.add(player2);

        panel.setComponentZOrder(player1, 0);
        panel.setComponentZOrder(player2, 0);
        /*panel.setComponentZOrder(background, 1);*/
        panel.repaint();
        frame.repaint();
    }
    /**
     * A method to open the load game screen in the main menu and control all the buttons within it.
     */
    public void loadGameFromMenu()
    {
        panel.removeAll();
        panel.add(background);
        background.setIcon(new ImageIcon("images/loadGameMenu.png"));

        JButton[] Button = new JButton[5];
        JLabel[] slotText = new JLabel[5];
        ImageIcon icon = new ImageIcon("images/slot.png");
        ImageIcon iconP = new ImageIcon("images/slotP.png");

        for(int i=0, k=230; i<5; i++, k+=70)
        { 
            slotText[i] = new JLabel();
            Button[i] = createButton(icon);

            Button[i].setBounds(400,k,550,70);
            slotText[i].setBounds(430,k,550,70);
            slotText[i].setText(FileHandling.readName(i));
            slotText[i].setFont(new Font("Arial", Font.PLAIN, 25));
            panel.add(slotText[i]);
            panel.add(Button[i]);
        }
        Button[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    
                    loadGame(0);
                }
            });
        Button[0].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[0].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[0].setIcon(icon);
                }
            });

        Button[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    
                    loadGame(1);
                }
            });
        Button[1].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[1].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[1].setIcon(icon);
                }
            });

        Button[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    
                    loadGame(2);
                }
            });
        Button[2].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[2].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[2].setIcon(icon);
                }
            });

        Button[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    
                    loadGame(3);
                }   
            });
        Button[3].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[3].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[3].setIcon(icon);
                }
            });

        Button[4].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    
                    loadGame(4);
                }
            });
        Button[4].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[4].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[4].setIcon(icon);
                }
            });

        for(int i=0; i<5; i++)
        {
            panel.setComponentZOrder(Button[i], 0);
            panel.setComponentZOrder(slotText[i], 0);
        }
    }
    
    /**
     * A method to open the player 1 menu screen and control all the buttons within it.
     */
    public void Player1Screen()
    {
        JButton arrow;
        JButton[] color = new JButton[5];
        ImageIcon[] icon = new ImageIcon[10];
        JLabel marker;
        JTextField name = new JTextField();
        panel.removeAll();

        panel.add(background);
        background.setIcon( new ImageIcon("images/1playerscreen.png"));
        name.setBounds(650,265,270,60);
        name.setFont(new Font("Arial", Font.BOLD, 40));
        name.setBackground(new Color(181, 218, 177));
        name.setBorder(null);
        panel.add(name);

        ImageIcon ring = new ImageIcon("images/ring.png");
        marker = new JLabel(ring);
        marker.setBounds(631,387,80,80);
        player1.setColor(2);
        player2.setColor(1);
        player2.setName("Computer");
        panel.add(marker);

        icon[0] = new ImageIcon("images/white.png");
        icon[1] = new ImageIcon("images/whiteP.png");
        color[0] = createButton(icon[0]);
        color[0].setBounds(525,392,70,70);
        color[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    marker.setBounds(521,387,80,80);
                    player1.setColor(1);
                    player2.setColor(2);
                }
            });
        color[0].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    color[0].setIcon(icon[1]);
                }

                public void mouseReleased(MouseEvent evt) {
                    color[0].setIcon(icon[0]);
                }
            });
        panel.add(color[0]);

        icon[2] = new ImageIcon("images/black.png");
        icon[3] = new ImageIcon("images/blackP.png");
        color[1] = createButton(icon[2]);
        color[1].setBounds(635,392,70,70);
        color[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    marker.setBounds(631,387,80,80);
                    player1.setColor(2);
                    player2.setColor(1);
                }
            });
        color[1].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    color[1].setIcon(icon[3]);
                }

                public void mouseReleased(MouseEvent evt) {
                    color[1].setIcon(icon[2]);
                }
            });
        panel.add(color[1]);

        icon[4] = new ImageIcon("images/yellow.png");
        icon[5] = new ImageIcon("images/yellowP.png");
        color[2] = createButton(icon[4]);
        color[2].setBounds(745,392,70,70);
        color[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    marker.setBounds(741,387,80,80);
                    player1.setColor(3);
                }
            });
        color[2].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    color[2].setIcon(icon[5]);
                }

                public void mouseReleased(MouseEvent evt) {
                    color[2].setIcon(icon[4]);
                }
            });
        panel.add(color[2]);

        icon[6] = new ImageIcon("images/green.png");
        icon[7] = new ImageIcon("images/greenP.png");
        color[3] = createButton(icon[6]);
        color[3].setBounds(855,392,70,70);
        color[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    marker.setBounds(851,386,80,80);
                    player1.setColor(4);
                }
            });
        color[3].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    color[3].setIcon(icon[7]);
                }

                public void mouseReleased(MouseEvent evt) {
                    color[3].setIcon(icon[6]);
                }
            });
        panel.add(color[3]);

        icon[8] = new ImageIcon("images/red.png");
        icon[9] = new ImageIcon("images/redP.png");
        color[4] = createButton(icon[8]);
        color[4].setBounds(965,392,70,70);
        color[4].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    marker.setBounds(961,387,80,80);
                    player1.setColor(5);
                }
            });
        color[4].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    color[4].setIcon(icon[9]);
                }

                public void mouseReleased(MouseEvent evt) {
                    color[4].setIcon(icon[8]);
                }
            });
        panel.add(color[4]);

        ImageIcon arrowIcon = new ImageIcon("images/arrow.png");
        ImageIcon arrowIconPressed = new ImageIcon("images/arrowP.png");
        arrow = createButton(arrowIcon);
        arrow.setBounds(950,510,270,170);
        arrow.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    player1.setName(name.getText());
                    if(name.getText().isEmpty()) player1.setName("Player1");

                    playGame(player1.getName(), "Computer");

                    player2.setIfComputer(true);
                }
            });
        arrow.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    arrow.setIcon(arrowIconPressed);
                }

                public void mouseReleased(MouseEvent evt) {
                    arrow.setIcon(arrowIcon);
                }
            });
        panel.add(arrow);

        panel.setComponentZOrder(name, 1);
        panel.setComponentZOrder(marker, 0);
        panel.setComponentZOrder(color[0], 1);
        panel.setComponentZOrder(color[1], 1);
        panel.setComponentZOrder(color[2], 1);
        panel.setComponentZOrder(color[3], 1);
        panel.setComponentZOrder(color[4], 1);
        panel.setComponentZOrder(arrow, 0);
        panel.repaint();
        frame.repaint();
    }
    /**
     * A method to open the player 2 menu screen and control all the buttons within it.
     */
    public void Player2Screen()
    {

        JButton arrow;
        JButton[] firstColor = new JButton[5];
        JButton[] secondColor = new JButton[5];
        ImageIcon[] firstIcon = new ImageIcon[10];
        JLabel marker, marker2;
        JTextField name1 = new JTextField();
        JTextField name2 = new JTextField();
        panel.removeAll();

        panel.add(background);
        background.setIcon( new ImageIcon("images/2playerscreen.png"));
        name1.setBounds(600,240,270,60);
        name1.setFont(new Font("Arial", Font.BOLD, 40));
        name1.setBackground(new Color(181, 218, 177));
        name1.setBorder(null);
        panel.add(name1);

        name2.setBounds(600,467,270,60);
        name2.setFont(new Font("Arial", Font.BOLD, 40));
        name2.setBackground(new Color(181, 218, 177));
        name2.setBorder(null);
        panel.add(name2);

        ImageIcon ring = new ImageIcon("images/ring.png");
        marker = new JLabel(ring);
        marker.setBounds(641,340,80,80);
        panel.add(marker);
        player1.setColor(2);
        marker2 = new JLabel(ring);
        marker2.setBounds(531,575,80,80);
        player2.setColor(1);
        panel.add(marker2);

        firstIcon[0] = new ImageIcon("images/white.png");
        firstIcon[1] = new ImageIcon("images/whiteP.png");
        firstColor[0] = createButton(firstIcon[0]);
        firstColor[0].setBounds(535,345,70,70);
        firstColor[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player2.getColor()!=1) {
                        marker.setBounds(531,340,80,80);
                        player1.setColor(1);
                    }
                }
            });
        firstColor[0].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    firstColor[0].setIcon(firstIcon[1]);
                }

                public void mouseReleased(MouseEvent evt) {
                    firstColor[0].setIcon(firstIcon[0]);
                }
            });
        panel.add(firstColor[0]);

        firstIcon[2] = new ImageIcon("images/black.png");
        firstIcon[3] = new ImageIcon("images/blackP.png");
        firstColor[1] = createButton(firstIcon[2]);
        firstColor[1].setBounds(645,345,70,70);
        firstColor[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player2.getColor()!=2) {
                        marker.setBounds(641,340,80,80);
                        player1.setColor(2);
                    }
                }
            });
        firstColor[1].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    firstColor[1].setIcon(firstIcon[3]);
                }

                public void mouseReleased(MouseEvent evt) {
                    firstColor[1].setIcon(firstIcon[2]);
                }
            });
        panel.add(firstColor[1]);

        firstIcon[4] = new ImageIcon("images/yellow.png");
        firstIcon[5] = new ImageIcon("images/yellowP.png");
        firstColor[2] = createButton(firstIcon[4]);
        firstColor[2].setBounds(755,345,70,70);
        firstColor[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player2.getColor()!=3) {
                        marker.setBounds(751,340,80,80);
                        player1.setColor(3);
                    }
                }
            });
        firstColor[2].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    firstColor[2].setIcon(firstIcon[5]);
                }

                public void mouseReleased(MouseEvent evt) {
                    firstColor[2].setIcon(firstIcon[4]);
                }
            });
        panel.add(firstColor[2]);

        firstIcon[6] = new ImageIcon("images/green.png");
        firstIcon[7] = new ImageIcon("images/greenP.png");
        firstColor[3] = createButton(firstIcon[6]);
        firstColor[3].setBounds(865,346,70,70);
        firstColor[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player2.getColor()!=4) {
                        marker.setBounds(861,340,80,80);
                        player1.setColor(4);
                    }
                }
            });
        firstColor[3].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    firstColor[3].setIcon(firstIcon[7]);
                }

                public void mouseReleased(MouseEvent evt) {
                    firstColor[3].setIcon(firstIcon[6]);
                }
            });
        panel.add(firstColor[3]);

        firstIcon[8] = new ImageIcon("images/red.png");
        firstIcon[9] = new ImageIcon("images/redP.png");
        firstColor[4] = createButton(firstIcon[8]);
        firstColor[4].setBounds(975,345,70,70);
        firstColor[4].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player2.getColor()!=5) {
                        marker.setBounds(971,340,80,80);
                        player1.setColor(5);
                    }
                }
            });
        firstColor[4].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    firstColor[4].setIcon(firstIcon[9]);
                }

                public void mouseReleased(MouseEvent evt) {
                    firstColor[4].setIcon(firstIcon[8]);
                }
            });
        panel.add(firstColor[4]);

        // ******* SECOND LINE ********************************************************************

        secondColor[0] = createButton(firstIcon[0]);
        secondColor[0].setBounds(535,580,70,70);
        secondColor[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player1.getColor()!=1) {
                        marker2.setBounds(531,575,80,80);
                        player2.setColor(1);
                    }
                }
            });
        secondColor[0].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    secondColor[0].setIcon(firstIcon[1]);
                }

                public void mouseReleased(MouseEvent evt) {
                    secondColor[0].setIcon(firstIcon[0]);
                }
            });
        panel.add(secondColor[0]);

        secondColor[1] = createButton(firstIcon[2]);
        secondColor[1].setBounds(645,580,70,70);
        secondColor[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player1.getColor()!=2) {
                        marker2.setBounds(643,575,80,80);
                        player2.setColor(2);
                    }
                }
            });
        secondColor[1].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    secondColor[1].setIcon(firstIcon[3]);
                }

                public void mouseReleased(MouseEvent evt) {
                    secondColor[1].setIcon(firstIcon[2]);
                }
            });
        panel.add(secondColor[1]);

        secondColor[2] = createButton(firstIcon[4]);
        secondColor[2].setBounds(755,580,70,70);
        secondColor[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player1.getColor()!=3) {
                        marker2.setBounds(751,575,80,80);
                        player2.setColor(3);
                    }
                }
            });
        secondColor[2].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    secondColor[2].setIcon(firstIcon[5]);
                }

                public void mouseReleased(MouseEvent evt) {
                    secondColor[2].setIcon(firstIcon[4]);
                }
            });
        panel.add(secondColor[2]);

        secondColor[3] = createButton(firstIcon[6]);
        secondColor[3].setBounds(865,580,70,70);
        secondColor[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player1.getColor()!=4) {
                        marker2.setBounds(861,574,80,80);
                        player2.setColor(4);
                    }
                }
            });
        secondColor[3].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    secondColor[3].setIcon(firstIcon[7]);
                }

                public void mouseReleased(MouseEvent evt) {
                    secondColor[3].setIcon(firstIcon[6]);
                }
            });
        panel.add(secondColor[3]);

        secondColor[4] = createButton(firstIcon[8]);
        secondColor[4].setBounds(975,580,70,70);
        secondColor[4].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(player1.getColor()!=5) {
                        marker2.setBounds(971,575,80,80);
                        player2.setColor(5);
                    }
                }
            });
        secondColor[4].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    secondColor[4].setIcon(firstIcon[9]);
                }

                public void mouseReleased(MouseEvent evt) {
                    secondColor[4].setIcon(firstIcon[8]);
                }
            });
        panel.add(secondColor[4]);

        ImageIcon arrowIcon = new ImageIcon("images/arrow.png");
        ImageIcon arrowIconPressed = new ImageIcon("images/arrowP.png");
        arrow = createButton(arrowIcon);
        arrow.setBounds(1075,510,270,170);
        arrow.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(name1.getText().isEmpty() && name2.getText().isEmpty()) {
                        player1.setName("Player1");
                        player2.setName("Player2");
                    }
                    else if(name1.getText().isEmpty() && !name2.getText().isEmpty()) {
                        player1.setName("Player1");
                        player2.setName(name2.getText());
                    }
                    else if(!name1.getText().isEmpty() && name2.getText().isEmpty()) {
                        player1.setName(name1.getText());
                        player2.setName("Player2");
                    }
                    else {
                        player1.setName(name1.getText());
                        player2.setName(name2.getText());
                    }
                    playGame(player1.getName(), player2.getName());

                }
            });
        arrow.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    arrow.setIcon(arrowIconPressed);
                }

                public void mouseReleased(MouseEvent evt) {
                    arrow.setIcon(arrowIcon);
                }
            });
        panel.add(arrow);

        panel.setComponentZOrder(name1, 0);
        panel.setComponentZOrder(name2, 0);
        panel.setComponentZOrder(marker, 0);
        panel.setComponentZOrder(marker2, 0);
        panel.setComponentZOrder(firstColor[0], 1);
        panel.setComponentZOrder(firstColor[1], 1);
        panel.setComponentZOrder(firstColor[2], 1);
        panel.setComponentZOrder(firstColor[3], 1);
        panel.setComponentZOrder(firstColor[4], 1);
        panel.setComponentZOrder(secondColor[0], 1);
        panel.setComponentZOrder(secondColor[1], 1);
        panel.setComponentZOrder(secondColor[2], 1);
        panel.setComponentZOrder(secondColor[3], 1);
        panel.setComponentZOrder(secondColor[4], 1);
        panel.setComponentZOrder(arrow, 0);
        panel.repaint();
        frame.repaint();
    }

    /**
     * A method to refresh/repaint the game board
     */
    public void refreshGrid()
    {
        for(int i=0; i<board.getRows(); i++)
        {
            for(int j=0; j<board.getColumns(); j++)
            {

                if(theBoard[i][j] == 1) {
                    if(player2.getColor()==1)gridButtons[i][j].setIcon(new ImageIcon("images/white.png"));
                    if(player2.getColor()==2)gridButtons[i][j].setIcon(new ImageIcon("images/black.png"));
                    if(player2.getColor()==3)gridButtons[i][j].setIcon(new ImageIcon("images/yellow.png"));
                    if(player2.getColor()==4)gridButtons[i][j].setIcon(new ImageIcon("images/green.png"));
                    if(player2.getColor()==5)gridButtons[i][j].setIcon(new ImageIcon("images/red.png"));
                    gridButtons[i][j].setBackground(new Color(34, 177, 76));
                }
                if(theBoard[i][j] == 2) {
                    if(player1.getColor()==1)gridButtons[i][j].setIcon(new ImageIcon("images/white.png"));
                    if(player1.getColor()==2)gridButtons[i][j].setIcon(new ImageIcon("images/black.png"));
                    if(player1.getColor()==3)gridButtons[i][j].setIcon(new ImageIcon("images/yellow.png"));
                    if(player1.getColor()==4)gridButtons[i][j].setIcon(new ImageIcon("images/green.png"));
                    if(player1.getColor()==5)gridButtons[i][j].setIcon(new ImageIcon("images/red.png"));
                    gridButtons[i][j].setBackground(new Color(34, 177, 76));
                }
                if(theBoard[i][j] == 3) gridButtons[i][j].setBackground(new Color(216,216,216));
                if(theBoard[i][j] == 0) gridButtons[i][j].setBackground(new Color(34, 177, 76));

            }
        }
        for(int i=0; i<board.getRows(); i++)
        {
            for(int j=0; j<board.getColumns(); j++)
            {
                if(boardOfChange[i][j] == 1)
                {
                    gridButtons[i][j].setBackground(new Color(77, 221, 120));
                }
                if(boardOfChange[i][j] == 0 && theBoard[i][j] != 3)
                {
                    gridButtons[i][j].setBackground(new Color(34, 177, 76));
                }
            }
        }

    }

    /**
     * A method to open and initialize the play game screen and set up all the labels, buttons and images in it
     */
    public void playGame(String p2name, String p1name)
    {
        panel.removeAll();
        
        panel.setBackground(Color.white);
        player1NameLabel = new JLabel(p1name);
        player2NameLabel = new JLabel(p2name);
        player1NameLabel.setFont(new Font("Arial", Font.PLAIN, 50));      
        player2NameLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        player1PiecesLabel = new JLabel(Integer.toString(board.getPlayer1Pieces()));
        player2PiecesLabel = new JLabel(Integer.toString(board.getPlayer2Pieces()));
        player1PiecesLabel.setFont(new Font("Arial", Font.PLAIN, 50));      
        player2PiecesLabel.setFont(new Font("Arial", Font.PLAIN, 50)); 
        if(turn == 2)
            turnLabel = new JLabel("" + p2name + "'s turn");
        if(turn == 1)
            turnLabel = new JLabel("" + p1name + "'s turn");
        turnLabel.setFont(new Font("Arial", Font.BOLD, 50)); 
        winnerScreen = new JFrame();

        ImageIcon cog = new ImageIcon("images/cog.png");
        ImageIcon cogPressed = new ImageIcon("images/cogP.png");
        JButton optionsButton = createButton(cog);
        optionsButton.setBounds(1150,20,130,130);
        optionsButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if(!optionsOn) {
                        options();
                        optionsOn=true;
                    }
                }
            });
        optionsButton.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    optionsButton.setIcon(cogPressed);
                }

                public void mouseReleased(MouseEvent evt) {
                    optionsButton.setIcon(cog);
                }
            });
        panel.add(optionsButton);

        if(player2.getColor()==1) player1PieceColorLabel = new JLabel(new ImageIcon("images/white.png"));
        else if(player2.getColor()==2) player1PieceColorLabel = new JLabel(new ImageIcon("images/black.png"));
        else if(player2.getColor()==3) player1PieceColorLabel = new JLabel(new ImageIcon("images/yellow.png"));
        else if(player2.getColor()==4) player1PieceColorLabel = new JLabel(new ImageIcon("images/green.png"));
        else if(player2.getColor()==5) player1PieceColorLabel = new JLabel(new ImageIcon("images/red.png"));
        else player1PieceColorLabel = new JLabel(new ImageIcon("images/black.png"));

        if(player1.getColor()==1) player2PieceColorLabel = new JLabel(new ImageIcon("images/white.png"));
        else if(player1.getColor()==2) player2PieceColorLabel = new JLabel(new ImageIcon("images/black.png"));
        else if(player1.getColor()==3) player2PieceColorLabel = new JLabel(new ImageIcon("images/yellow.png"));
        else if(player1.getColor()==4) player2PieceColorLabel = new JLabel(new ImageIcon("images/green.png"));
        else if(player1.getColor()==5) player2PieceColorLabel = new JLabel(new ImageIcon("images/red.png"));
        else player2PieceColorLabel = new JLabel(new ImageIcon("images/white.png"));

        player1PieceColorLabel.setBounds(930,435,100,100);
        player2PieceColorLabel.setBounds(930,215,100,100);
        grid.setBounds(220,90,600,600);
        winnerScreen.setBounds(450, 200, 500, 300);
        player1NameLabel.setBounds(870,350,400,70);
        player2NameLabel.setBounds(870,130,400,70);
        player1PiecesLabel.setBounds(870,450,400,70);
        player2PiecesLabel.setBounds(870,230,400,70);
        turnLabel.setBounds(870,550,400,70);
        panel.add(grid);
        //panel.add(winnerScreen);
        winnerScreen.setVisible(false);
        panel.add(player1NameLabel);
        panel.add(player2NameLabel);
        panel.add(player1PiecesLabel);
        panel.add(player2PiecesLabel);
        panel.add(turnLabel);
        panel.add(player1PieceColorLabel);
        panel.add(player2PieceColorLabel);
        //grid.setBorder(BorderFactory.createLineBorder(Color.black));

        panel.setComponentZOrder(grid, 1);
        winnerScreen.setResizable(false);
        //panel.setComponentZOrder(winnerScreen, 0);
        panel.setComponentZOrder(optionsButton, 1);
        panel.setComponentZOrder(player1NameLabel, 1);
        panel.setComponentZOrder(player2NameLabel, 1);
        panel.setComponentZOrder(player1PiecesLabel, 1);
        panel.setComponentZOrder(player2PiecesLabel, 1);
        panel.setComponentZOrder(player1PieceColorLabel, 1);
        panel.setComponentZOrder(player2PieceColorLabel, 1);
        panel.setComponentZOrder(turnLabel, 1);

        theBoard = board.getBoard();
        board.getPossibleMoves(turn);
        refreshGrid();
        panel.repaint();
        frame.repaint();
        frame.revalidate();
        
    }

    /**
     *  Returns the class name
     */
    protected String getClassName(Object o) 
    {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    /**
     * A method to control the gameplay by controling the user's input, AI movement and the rules when the game ends or what happens in specific scenarios
     */
    public void actionPerformed(ActionEvent e) 
    {
        
        String classname = getClassName(e.getSource());
        if (classname.equals("JButton") && !endGame && !optionsOn)
        {
            JButton button = (JButton)(e.getSource());
            int bnum = Integer.parseInt(button.getActionCommand());

            int row = bnum / 10;
            int col = bnum - row*10;
            theBoard = board.getBoard();       

            if(turn==1 && theBoard[row][col]==3 && !player2.isItComputer()) {

                board.addPiece(row, col, 1);
                player1PiecesLabel.setText(Integer.toString(board.getPlayer1Pieces()));
                player2PiecesLabel.setText(Integer.toString(board.getPlayer2Pieces()));
                turn = 2;
                board.getPossibleMoves(2);
                endGame = true;
                for(int i=0; i<board.getRows(); i++)
                {
                    for(int j=0; j<board.getColumns(); j++)
                    {
                        if(theBoard[i][j]==3) endGame = false;
                    }
                }

            }
            else if(turn==2 && theBoard[row][col]==3) {
                board.addPiece(row, col, 2);
                player1PiecesLabel.setText(Integer.toString(board.getPlayer1Pieces()));
                player2PiecesLabel.setText(Integer.toString(board.getPlayer2Pieces()));
                turn = 1;
                board.getPossibleMoves(1);
                endGame = true;
                for(int i=0; i<board.getRows(); i++)
                {
                    for(int j=0; j<board.getColumns(); j++)
                    {
                        if(theBoard[i][j]==3) endGame = false;
                    }
                }
                //board.test();
                if(player2.isItComputer() && !endGame) {
                    getAImove();

                    boardOfChange = board.getBoardOfChange(AIRow, AIColumn, 1);
                    if(player2.getColor()==1)gridButtons[AIRow][AIColumn].setIcon(new ImageIcon("images/white.png"));
                    if(player2.getColor()==2)gridButtons[AIRow][AIColumn].setIcon(new ImageIcon("images/black.png"));
                    if(player2.getColor()==3)gridButtons[AIRow][AIColumn].setIcon(new ImageIcon("images/yellow.png"));
                    if(player2.getColor()==4)gridButtons[AIRow][AIColumn].setIcon(new ImageIcon("images/green.png"));
                    if(player2.getColor()==5)gridButtons[AIRow][AIColumn].setIcon(new ImageIcon("images/red.png"));
                    new Thread() {

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1500);

                                board.addPiece(AIRow, AIColumn, 1);
                                player1PiecesLabel.setText(Integer.toString(board.getPlayer1Pieces()));
                                player2PiecesLabel.setText(Integer.toString(board.getPlayer2Pieces()));
                                turn = 2;
                                turnLabel.setText("" + player1.getName() + "'s turn");
                                board.getPossibleMoves(2);
                                refreshGrid();
                                endGame = true;
                                for(int i=0; i<board.getRows(); i++)
                                {
                                    for(int j=0; j<board.getColumns(); j++)
                                    {
                                        if(theBoard[i][j]==3) endGame = false;
                                    }
                                }
                                if(endGame)
                                {
                                    endTheGame();
                                }

                            } catch (InterruptedException ex) {
                                Thread.currentThread().interrupt();
                            }

                        }
                    }.start();

                }
            }
            possibleToMove = false;
            for(int i=0; i<board.getRows(); i++)
            {
                for(int j=0; j<board.getColumns(); j++)
                {
                    if(theBoard[i][j]==3) {
                        possibleToMove = true;
                        break;
                    }
                }
            }
            if(!possibleToMove) {
                if(turn == 1) {
                    turn = 2;
                    board.getPossibleMoves(2);
                }
                else if(turn == 2) {
                    turn = 1;
                    board.getPossibleMoves(1);
                }
            }
            if(turn==2) turnLabel.setText("" + player1.getName() + "'s turn");
            if(turn==1) turnLabel.setText("" + player2.getName() + "'s turn");
            endGame = true;
            for(int i=0; i<board.getRows(); i++)
            {
                for(int j=0; j<board.getColumns(); j++)
                {
                    if(/*theBoard[i][j]==0 ||*/ theBoard[i][j]==3) {
                        endGame = false;
                        break;
                    }
                }
            }
            
            if(endGame)
            {
                endTheGame();
            }

            refreshGrid();
        }
    }

    /**
     * A method to open the options frame and control all the buttons within it.
     */
    public void options()
    {
        optionsFrame = new JFrame("Options");
        optionsPanel = new JPanel();

        optionBG = new JLabel();

        optionsFrame.setResizable(false);
        optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        optionsFrame.add(optionsPanel);
        optionsPanel.add(optionBG);
        optionBG.setIcon(new ImageIcon("images/Option Window.png"));
        //background.setLayout( new BorderLayout() );
        //frame.setContentPane( background );
        optionsFrame.pack();
        //frame.setLayout(null);
        optionsPanel.setLayout(null);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        JButton[] Button = new JButton[5];
        ImageIcon[] Icon = new ImageIcon[10];

        Icon[0] = new ImageIcon("images/Resume Button.png");
        Icon[1] = new ImageIcon("images/Resume ButtonP.png");
        Button[0] = createButton(Icon[0]);
        Button[0].setBounds(80,150,550,70);
        Button[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    optionsFrame.dispose();
                }
            });
        Button[0].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[0].setIcon(Icon[1]);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[0].setIcon(Icon[0]);
                }
            });
        optionsPanel.add(Button[0]);

        Icon[2] = new ImageIcon("images/saveGame.png");
        Icon[3] = new ImageIcon("images/saveGameP.png");
        Button[1] = createButton(Icon[2]);
        Button[1].setBounds(80,220,550,70);
        Button[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    saveGameFromOptions();

                }
            });
        Button[1].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[1].setIcon(Icon[3]);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[1].setIcon(Icon[2]);
                }
            });
        optionsPanel.add(Button[1]);

        Icon[4] = new ImageIcon("images/loadGame.png");
        Icon[5] = new ImageIcon("images/loadGameP.png");
        Button[2] = createButton(Icon[4]);
        Button[2].setBounds(80,290,550,70);
        Button[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    loadGameFromOptions();
                }
            });
        Button[2].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[2].setIcon(Icon[5]);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[2].setIcon(Icon[4]);
                }
            });
        optionsPanel.add(Button[2]);

        Icon[6] = new ImageIcon("images/helpButton.png");
        Icon[7] = new ImageIcon("images/helpButtonP.png");
        Button[3] = createButton(Icon[6]);
        Button[3].setBounds(80,360,550,70);
        Button[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    try {
                        Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Reversi"));
                    } catch(Exception ex) {
                        System.out.println("Couldn't open the webpage!");
                    }
                }
            });
        Button[3].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[3].setIcon(Icon[7]);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[3].setIcon(Icon[6]);
                }
            });
        optionsPanel.add(Button[3]);

        Icon[8] = new ImageIcon("images/exitButton.png");
        Icon[9] = new ImageIcon("images/exitButtonP.png");
        Button[4] = createButton(Icon[8]);
        Button[4].setBounds(80,430,550,70);
        Button[4].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    panel.removeAll();
                    grid.removeAll();
                    frame.dispose();
                    optionsFrame.dispose();
                    initFrame();
                    initGameButtons();
                    if(player2.isItComputer()) player2.setIfComputer(false); 
                    mainMenu();

                }
            });
        Button[4].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[4].setIcon(Icon[9]);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[4].setIcon(Icon[8]);
                }
            });
        optionsPanel.add(Button[4]);

        optionsFrame.setVisible(true);
        //optionsFrame.pack();
        //optionsFrame.getContentPane().setBackground(Color.white);
        optionsFrame.setLocation(dim.width/2-optionsFrame.getSize().width/2, dim.height/2-optionsFrame.getSize().height/2);
        optionsPanel.setComponentZOrder(Button[0], 0);
        optionsPanel.setComponentZOrder(Button[1], 0);
        optionsPanel.setComponentZOrder(Button[2], 0);
        optionsPanel.setComponentZOrder(Button[3], 0);
        optionsPanel.setComponentZOrder(Button[4], 0);

    }
    /**
     * A method to open the save screen from the options menu and control all the buttons within it.
     */
    public void saveGameFromOptions()
    {
        optionsPanel.removeAll();
        optionsPanel.add(optionBG);
        optionBG.setIcon(new ImageIcon("images/Save Window.png"));

        JButton[] Button = new JButton[5];
        JLabel[] slotText = new JLabel[5];
        ImageIcon icon = new ImageIcon("images/slot.png");
        ImageIcon iconP = new ImageIcon("images/slotP.png");

        for(int i=0, k=180; i<5; i++, k+=70)
        { 
            slotText[i] = new JLabel();
            Button[i] = createButton(icon);

            Button[i].setBounds(110,k,550,70);
            slotText[i].setBounds(140,k,550,70);
            slotText[i].setText(FileHandling.readName(i));
            slotText[i].setFont(new Font("Arial", Font.PLAIN, 25));
            optionsPanel.add(slotText[i]);
            optionsPanel.add(Button[i]);
        }
        Button[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    FileHandling.writeToFile(0, theBoard, turn, player1.getName(), player2.getName(), player1.getColor(), player2.getColor(), board.getPlayer1Pieces(), board.getPlayer2Pieces());
                    optionsOn=false;
                    optionsFrame.dispose();
                }
            });
        Button[0].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[0].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[0].setIcon(icon);
                }
            });

        Button[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    FileHandling.writeToFile(1,theBoard, turn,  player1.getName(), player2.getName(), player1.getColor(), player2.getColor(), board.getPlayer1Pieces(), board.getPlayer2Pieces());
                    optionsOn=false;
                    optionsFrame.dispose();
                }
            });
        Button[1].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[1].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[1].setIcon(icon);
                }
            });

        Button[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    FileHandling.writeToFile(2,theBoard, turn,  player1.getName(), player2.getName(), player1.getColor(), player2.getColor(), board.getPlayer1Pieces(), board.getPlayer2Pieces());
                    optionsOn=false;
                    optionsFrame.dispose();
                }
            });
        Button[2].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[2].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[2].setIcon(icon);
                }
            });

        Button[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    FileHandling.writeToFile(3,theBoard, turn,  player1.getName(), player2.getName(), player1.getColor(), player2.getColor(), board.getPlayer1Pieces(), board.getPlayer2Pieces());
                    optionsOn=false;
                    optionsFrame.dispose();
                }
            });
        Button[3].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[3].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[3].setIcon(icon);
                }
            });

        Button[4].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    FileHandling.writeToFile(4,theBoard, turn,  player1.getName(), player2.getName(), player1.getColor(), player2.getColor(), board.getPlayer1Pieces(), board.getPlayer2Pieces());
                    optionsOn=false;
                    optionsFrame.dispose();
                }
            });
        Button[4].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[4].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[4].setIcon(icon);
                }
            });

        for(int i=0; i<5; i++)
        {
            optionsPanel.setComponentZOrder(Button[i], 0);
            optionsPanel.setComponentZOrder(slotText[i], 0);
        }

    }

    /**
     * A method to open the load game screen from options and control all the buttons within it.
     */
    public void loadGameFromOptions()
    {
        optionsPanel.removeAll();
        optionsPanel.add(optionBG);
        optionBG.setIcon(new ImageIcon("images/Load Window.png"));

        JButton[] Button = new JButton[5];
        JLabel[] slotText = new JLabel[5];
        ImageIcon icon = new ImageIcon("images/slot.png");
        ImageIcon iconP = new ImageIcon("images/slotP.png");

        for(int i=0, k=180; i<5; i++, k+=70)
        { 
            slotText[i] = new JLabel();
            Button[i] = createButton(icon);

            Button[i].setBounds(110,k,550,70);
            slotText[i].setBounds(140,k,550,70);
            slotText[i].setText(FileHandling.readName(i));
            slotText[i].setFont(new Font("Arial", Font.PLAIN, 25));
            optionsPanel.add(slotText[i]);
            optionsPanel.add(Button[i]);
        }
        Button[0].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    optionsFrame.dispose();
                    loadGame(0);
                }
            });
        Button[0].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[0].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[0].setIcon(icon);
                }
            });

        Button[1].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    optionsFrame.dispose();
                    loadGame(1);
                }
            });
        Button[1].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[1].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[1].setIcon(icon);
                }
            });

        Button[2].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    optionsFrame.dispose();
                    loadGame(2);
                }
            });
        Button[2].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[2].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[2].setIcon(icon);
                }
            });

        Button[3].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    optionsFrame.dispose();
                    loadGame(3);
                }   
            });
        Button[3].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[3].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[3].setIcon(icon);
                }
            });

        Button[4].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    optionsFrame.dispose();
                    loadGame(4);
                }
            });
        Button[4].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent evt) {
                    Button[4].setIcon(iconP);
                }

                public void mouseReleased(MouseEvent evt) {
                    Button[4].setIcon(icon);
                }
            });

        for(int i=0; i<5; i++)
        {
            optionsPanel.setComponentZOrder(Button[i], 0);
            optionsPanel.setComponentZOrder(slotText[i], 0);
        }
    }
    
    /**
     * A method to load the game from a specific slot.
     * 
     * @param slot from which slot does the user want to load the game.
     */
    public void loadGame(int slot)
    {
        panel.removeAll();
        grid.removeAll();
        frame.dispose();
        if(winnerScreen != null) winnerScreen.dispose();
        initFrame();
        initGameButtons();

        mainMenu();

        String nextLine="", fileName="";
        String[] split;
        if(slot==0) fileName = "slot0.txt";
        if(slot==1) fileName = "slot1.txt";
        if(slot==2) fileName = "slot2.txt";
        if(slot==3) fileName = "slot3.txt";
        if(slot==4) fileName = "slot4.txt";
        BufferedReader bufferedReader = FileHandling.openFileToRead(fileName);
        if(FileHandling.isUsable(fileName)) {
            try
            {

                optionsOn=false;
                int[][] gameBoard = new int[board.getRows()][board.getColumns()];
                for(int i=0; i<board.getRows(); i++)
                {
                    nextLine = bufferedReader.readLine();
                    split = nextLine.split(" ");
                    for(int j=0; j<board.getColumns(); j++)
                    {
                        gameBoard[i][j] = Integer.parseInt(split[j]);
                    }
                }
                board.setBoard(gameBoard);
                nextLine = bufferedReader.readLine();
                turn = Integer.parseInt(nextLine);
                nextLine = bufferedReader.readLine();
                split = nextLine.split(" ");
                player1.setName(split[0]);
                player1.setColor(Integer.parseInt(split[1]));
                board.setPlayer1Pieces(Integer.parseInt(split[2]));
                nextLine = bufferedReader.readLine();
                split = nextLine.split(" ");
                player2.setName(split[0]);
                player2.setColor(Integer.parseInt(split[1]));
                board.setPlayer2Pieces(Integer.parseInt(split[2]));

                playGame(player1.getName(), player2.getName());
            }
            catch (IOException e)
            {
                System.out.println("Sorry, there has been a problem writing to the file");
            }
            finally
            {
                if (bufferedReader != null)
                {
                    try
                    {
                        bufferedReader.close();    
                    }
                    catch (IOException e)
                    {
                        System.out.println("An error occurred when attempting to close the file");
                    }
                }  

            }
        }
        //else System.out.println("Sorry, the file does not exist or it is not readable");

    }

    
    /**
     * A method to end the game and prompt out the further options, get user input on them.
     */
    public void endTheGame()
    {
        turn = 2;
        JButton mainMenuButton = new JButton("MAIN MENU");
        JButton playAgainButton = new JButton("PLAY AGAIN");
        winnerLabel = new JLabel();
        winnerLabel1 = new JLabel();
        winnerLabel2 = new JLabel();
        JLabel colorOfPiece1 = new JLabel();
        JLabel colorOfPiece2 = new JLabel();

        if(player1.getColor()==1) colorOfPiece1 = new JLabel(new ImageIcon("images/white.png"));
        else if(player1.getColor()==2) colorOfPiece1 = new JLabel(new ImageIcon("images/black.png"));
        else if(player1.getColor()==3) colorOfPiece1 = new JLabel(new ImageIcon("images/yellow.png"));
        else if(player1.getColor()==4) colorOfPiece1 = new JLabel(new ImageIcon("images/green.png"));
        else if(player1.getColor()==5) colorOfPiece1 = new JLabel(new ImageIcon("images/red.png"));
        else colorOfPiece1 = new JLabel(new ImageIcon("images/black.png"));

        if(player2.getColor()==1) colorOfPiece2 = new JLabel(new ImageIcon("images/white.png"));
        else if(player2.getColor()==2) colorOfPiece2 = new JLabel(new ImageIcon("images/black.png"));
        else if(player2.getColor()==3) colorOfPiece2 = new JLabel(new ImageIcon("images/yellow.png"));
        else if(player2.getColor()==4) colorOfPiece2 = new JLabel(new ImageIcon("images/green.png"));
        else if(player2.getColor()==5) colorOfPiece2 = new JLabel(new ImageIcon("images/red.png"));
        else colorOfPiece2 = new JLabel(new ImageIcon("images/white.png"));

        if(board.getPlayer1Pieces()>board.getPlayer2Pieces()) winnerLabel.setText(player2.getName() + " WON");
        if(board.getPlayer2Pieces()>board.getPlayer1Pieces()) winnerLabel.setText(player1.getName() + " WON");

        winnerLabel1.setText("" + board.getPlayer2Pieces());
        winnerLabel2.setText("" + board.getPlayer1Pieces());
        winnerLabel1.setBounds(170, 95, 70, 70);
        winnerLabel2.setBounds(370, 95, 70, 70);
        colorOfPiece1.setBounds(70, 95, 70, 70);
        colorOfPiece2.setBounds(270, 95, 70, 70);
        winnerLabel.setBounds(75, 7, 400, 80); 
        if(board.getPlayer2Pieces()==board.getPlayer1Pieces()) {
            winnerLabel.setText("IT'S A TIE!");
            winnerLabel.setBounds(90, 10, 500, 50);
        }
        mainMenuButton.setBounds(40, 195, 200, 70);

        mainMenuButton.setFocusPainted(false); 
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 20));
        mainMenuButton.setBackground(Color.white);
        playAgainButton.setBounds(265, 195, 200, 70);
        playAgainButton.setFocusPainted(false); 
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 20));
        playAgainButton.setBackground(Color.white);
        winnerScreen.setBackground(Color.white);
        winnerScreen.setLayout(null);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        winnerScreen.setLocation(dim.width/2-winnerScreen.getSize().width/2, dim.height/2-winnerScreen.getSize().height/2);
        winnerScreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        winnerScreen.add(mainMenuButton);
        winnerScreen.add(playAgainButton);
        winnerScreen.add(colorOfPiece1);
        winnerScreen.add(colorOfPiece2);
        winnerScreen.add(winnerLabel);
        winnerScreen.add(winnerLabel1);
        winnerScreen.add(winnerLabel2);
        winnerLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        winnerLabel1.setFont(new Font("Arial", Font.PLAIN, 50));
        winnerLabel2.setFont(new Font("Arial", Font.PLAIN, 50));
        winnerScreen.setVisible(true);
        optionsOn=true;
        for(int i=0; i<board.getRows(); i++)
        {
            for(int j=0; j<board.getColumns(); j++)
            {
                //gridButtons[i][j].setEnabled(false);
                turnLabel.setVisible(false);

            }
        }
        endGame = false;
        mainMenuButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    panel.removeAll();
                    grid.removeAll();
                    frame.dispose();
                    winnerScreen.dispose();
                    initFrame();
                    initGameButtons();
                    if(player2.isItComputer()) player2.setIfComputer(false); 
                    turn = 2;
                    mainMenu();
                    optionsOn=false;
                }
            });

        panel.repaint();
        panel.revalidate();
        frame.repaint();
        frame.revalidate();
        winnerScreen.repaint();
        winnerScreen.revalidate();
        playAgainButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    panel.removeAll();
                    grid.removeAll();
                    frame.dispose();
                    winnerScreen.dispose();
                    initFrame();
                    initGameButtons();
                    turn = 2;
                    mainMenu();
                    playGame(player1.getName(), player2.getName());
                    optionsOn=false;
                }
            });
    }
    /**
     * A method to get the move from the AI. Set it to variables AIRow and AIColumn.
     * 
     */
    public void getAImove()
    {
        int numOfPieces=0, mostPieces=0, row=0, col=0;
        for(int i=0; i<board.getRows(); i++)
        {
            for(int j=0; j<board.getColumns(); j++)
            {
                if(theBoard[i][j]==3) {
                    numOfPieces=0;
                    boardOfChange = board.getBoardOfChange(i, j, 1);
                    for(int k=0; k<board.getRows(); k++)
                    {
                        for(int l=0; l<board.getColumns(); l++)
                        {
                            if(boardOfChange[k][l]==1) numOfPieces++;
                        }
                    }
                    if(mostPieces<numOfPieces) {
                        mostPieces = numOfPieces;
                        row = i;
                        col = j;
                    }
                }

            }
        }
        AIRow = row;
        AIColumn = col;

    }

    /**
     * A method to control what happens when the user's mouse enters the game board.
     * It controls the highlighting of pieces.
     * 
     */
    public void mouseEntered(MouseEvent e)
    {
        if(optionsFrame != null && optionsFrame.isDisplayable()==false) optionsOn = false;
        String classname = getClassName(e.getSource());
        if (classname.equals("JButton") && (!player2.isItComputer() || turn==2) && !optionsOn)
        {
            JButton button = (JButton)(e.getSource());
            int bnum = Integer.parseInt(button.getActionCommand());
            int row = bnum / 10;
            int col = bnum - row*10;
            theBoard = board.getBoard();       

            if(turn==1 && theBoard[row][col]==3) {
                boardOfChange = board.getBoardOfChange(row, col, 1);
                
                if(player2.getColor()==1)gridButtons[row][col].setIcon(new ImageIcon("images/white.png"));
                if(player2.getColor()==2)gridButtons[row][col].setIcon(new ImageIcon("images/black.png"));
                if(player2.getColor()==3)gridButtons[row][col].setIcon(new ImageIcon("images/yellow.png"));
                if(player2.getColor()==4)gridButtons[row][col].setIcon(new ImageIcon("images/green.png"));
                if(player2.getColor()==5)gridButtons[row][col].setIcon(new ImageIcon("images/red.png"));

                refreshGrid();

            }
            if(turn==2 && theBoard[row][col]==3) {
                boardOfChange = board.getBoardOfChange(row, col, 2);
                if(player1.getColor()==1)gridButtons[row][col].setIcon(new ImageIcon("images/white.png"));
                if(player1.getColor()==2)gridButtons[row][col].setIcon(new ImageIcon("images/black.png"));
                if(player1.getColor()==3)gridButtons[row][col].setIcon(new ImageIcon("images/yellow.png"));
                if(player1.getColor()==4)gridButtons[row][col].setIcon(new ImageIcon("images/green.png"));
                if(player1.getColor()==5)gridButtons[row][col].setIcon(new ImageIcon("images/red.png"));
                refreshGrid();

            }

        }
    }
    /**
     * A method to control what happens when the user's mouse exits the game board.
     * It controls the highlighting of pieces.
     * 
     */
    public void mouseExited(MouseEvent e)
    {
        String classname = getClassName(e.getSource());
        if (classname.equals("JButton") && (!player2.isItComputer() || turn==2) && !optionsOn)
        {
            JButton button = (JButton)(e.getSource());
            int bnum = Integer.parseInt(button.getActionCommand());
            int row = bnum / 10;
            int col = bnum - row*10;
            theBoard = board.getBoard();       

            if(theBoard[row][col]==3) {
                board.refreshBoardOfChange();
                gridButtons[row][col].setIcon(null);
                refreshGrid();

            }

        }
    }
    
    public void mouseClicked(MouseEvent e)
    {
        //METHODS INHERITED FROM THE MOUSELISTENER INTERFACE
    }      

    public void mouseReleased(MouseEvent e)
    {
        //METHODS INHERITED FROM THE MOUSELISTENER INTERFACE
    }

    public void mousePressed(MouseEvent e)
    {
        //METHODS INHERITED FROM THE MOUSELISTENER INTERFACE
    }

    /**
     * A method to create a basic button without it's premade Java button interfaces.
     * 
     * @param image The image you want the button to have.
     */
    public JButton createButton(ImageIcon image)
    {
        JButton button = new JButton(image);
        button.setBorderPainted(false); 
        button.setContentAreaFilled(false); 
        button.setFocusPainted(false); 
        button.setOpaque(false);
        return button;
    }

}