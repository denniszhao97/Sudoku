
import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import javax.swing.border.*; 

/**
 * GameBoard for Sudoku Game
 *
 * @see Sudoku
 * @see NumberPanelGroup
 * @see JMenuItems
 * @author Zhonghao Zhao & Tianjiao Pu
 */
public class SudokuGameBoard {

    public static void main(String[] args) {
	SudokuWindow Gameboard = new SudokuWindow(); //Create new SudokuWindow
    }
}

class SudokuWindow extends JFrame {

    //--------------------Buttons---------------------
    JMenuBar jmb = new JMenuBar(); 
    JMenu jmGame = new JMenu("Game Menu");
    JMenu jmTest = new JMenu("Test Menu"); 
    JMenu jmTimer = new JMenu("00:00:00"); // timer
    JMenuItem jmiGameStart = new JMenuItems(this, "Start Game"); 
    JMenuItem jmiGameEnd = new JMenuItems(this, "End Game"); 
    JMenuItem jmiGameExit = new JMenuItems(this, "Close Game"); 
    JMenuItem jmiTestClear = new JMenuItems(this, "Empty GameBoard"); 
    JMenuItem jmiTestAnswer = new JMenuItems(this, "Solve Sudoku"); 
    Container cp = getContentPane(); 
    NumberPanelGroup[] npg = new NumberPanelGroup[9];
    NumberPanel[][] np = new NumberPanel[9][9];
    JPanel jpOutter = new JPanel(); 
    JPanel jpInner = new JPanel();  
    //--------------------Button variables---------------------
    NumberPanel current_np; //the current cell where mouse is
    boolean hasHint = true; //hint or not
    boolean isPlaying; //whether the game started


    /**
     * Change time to String
     *
     * @param time: used time(ms)
     * @return (00:00:00)。
     */
    public static String getTimeString(long time) {
	int t = (int) (time / 1000); 
	int h = 0, m = 0, s; 
	while (t >= 3600) {
	    t -= 3600;
	    h++;
	}
	while (t >= 60) {
	    t -= 60;
	    m++;
	}
	s = t;
	StringBuilder sb = new StringBuilder("");
	if (h < 10) {
	    sb.append("0");
	}
	sb.append(h).append(":");
	if (m < 10) {
	    sb.append("0");
	}
	sb.append(m).append(":");
	if (s < 10) {
	    sb.append("0");
	}
	sb.append(s);
	return sb.toString();
    }
    //-------------------------Constructor-------------------------
    /**
     * Create window
     *
     */
    public SudokuWindow() {
	
	setTitle("Soduku"); //title
	setSize(600, 600); //size。
	setLocationRelativeTo(null); //Center of the Screen
	setDefaultCloseOperation(EXIT_ON_CLOSE); 
	cp.setLayout(new BorderLayout()); 

	jmb.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)); 
	setJMenuBar(jmb); 
	jmb.add(jmGame); 
	jmb.add(jmTest); 
	jmb.add(jmTimer); 
	jmGame.add(jmiGameStart); 
	jmGame.add(jmiGameEnd); 
	jmiGameEnd.setEnabled(false);
	jmGame.addSeparator(); 
	jmGame.add(jmiGameExit);
	jmTest.add(jmiTestClear);
	jmTest.add(jmiTestAnswer);
	jmTimer.setEnabled(false); 


	jpOutter.setLayout(new FlowLayout(FlowLayout.CENTER)); 
	jpInner.setLayout(new GridLayout(3, 3));
	jpInner.setPreferredSize(new Dimension(525, 525));
	jpOutter.add(jpInner);

	for (int i = 0; i < 9; i++) {
	    for (int j = 0; j < 9; j++) {
		np[i][j] = new NumberPanel(this, i, j); 
	    }
	}
	for (int i = 0; i < 9; i++) {
	    npg[i] = new NumberPanelGroup(); 
	    for (int a = 0; a < 3; a++) {
		for (int b = 0; b < 3; b++) {
		    npg[i].add(np[a + (i / 3) * 3][b + (i % 3) * 3]);
		}
	    }
	    jpInner.add(npg[i]); 
	}

	
	addKeyListener(new KeyAdapter() {

	    @Override
	    public void keyPressed(KeyEvent e) { //if pressed keyboard
		if (current_np != null) { //when the mouse is on one cell
		    switch (e.getKeyCode()) {
			case KeyEvent.VK_0:
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3:
			case KeyEvent.VK_4:
			case KeyEvent.VK_5:
			case KeyEvent.VK_6:
			case KeyEvent.VK_7:
			case KeyEvent.VK_8:
			case KeyEvent.VK_9:
			    current_np.setNumber(e.getKeyChar() - '0');
			    break;
			case KeyEvent.VK_DELETE:
			    current_np.setNumber(0);
			    break;
		    }
		}
	    }
	});


	cp.add(jpOutter, BorderLayout.CENTER); 
	
	setVisible(true);
    }

    //-------------------------Methods-------------------------
    /**
     * Get the solved array
     *
     * @return solved array
     */
    public int[][] getArray() {
	int[][] n = new int[9][9];
	for (int i = 0; i < 9; i++) {
	    for (int j = 0; j < 9; j++) {
		n[i][j] = np[i][j].getNumber();
	    }
	}
	return n;
    }
}


class JMenuItems extends JMenuItem implements ActionListener {

    
    static String[] options = {"Easy","Medium","Hard"}; 
    
    private final SudokuWindow frame; 

    //-------------------------Constructors-------------------------
   
    public JMenuItems(final SudokuWindow sw, String name) {
	super(name); 
	frame = sw; 
	addListeners();     }


    @Override //ActionListener。
    public void actionPerformed(ActionEvent e) {
	JMenuItems jmi = (JMenuItems) e.getSource(); 
	if (jmi == frame.jmiGameStart) { 
	    start();
	} else if (jmi == frame.jmiGameExit) { 
	    exit();
	} else if (jmi == frame.jmiTestClear) {
	    clear();
	} else if (jmi == frame.jmiTestAnswer) {
	    answer();
	} else if (jmi == frame.jmiGameEnd) { 
	    end();
	}
    }

    
    private void addListeners() {
	addActionListener(this);
    }

    private void start() {

	int optionRtn = JOptionPane.showOptionDialog(frame, "Choose difficulty：", "Difficulty Level", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[1]);
	

	//forbid the use of some objects
	frame.jmiGameStart.setEnabled(false);
	frame.jmiGameEnd.setEnabled(true);
	frame.jmiTestClear.setEnabled(false);
	frame.jmiTestAnswer.setEnabled(false);

	clear();

	//randomly generate Sudoku with the chosen difficulty
	double rndPower = 3; //default
	boolean hint = true; //no hint for hard
	switch (optionRtn) {
	    case 0: //easy
		rndPower = 1.4;
		break;
	    case 1: //medium
		rndPower = 2.0;
		break;
	    case 2: //hard
		rndPower = 3.0;
		hint = false;
		break;
	}
	frame.hasHint = hint;

	//randomly generate Sudoku with the chosen difficulty
	for (int i = 0; i < 9; i++) {
	    for (int j = 0; j < 9; j++) {
		int rnd = (int) (Math.random() * rndPower); 
		if (rnd == 0) { 
		    Sudoku s = new Sudoku(frame.getArray());
		    int[][] n = s.getArray(true); 
		    int counter = 0; 
		    
		    while ((s.isCorrect(i, j) && s.solveSudoku() != null) == false) { //The generated Sudoku must be solvable
			     int value = (int) (Math.random() * 9) + 1; 
			     n[i][j] = value;
			     counter++; 
			     if (counter > 100) { 
			     n[i][j] = 0; 
			    
			     break;
			}
		    } 
		    if (n[i][j] != 0) { 
			frame.np[i][j].setNumber(n[i][j]); 
			frame.np[i][j].setEvent(false); 
		    }
		}
	    }
	}

	System.gc(); 

	//Start Game
	frame.isPlaying = true;
	new Thread(new Timer(frame)).start(); 
    }

   
    public void endGame() {
	end();
    }

    private void end() { //end game
	frame.isPlaying = false; 
	frame.jmTimer.setText("00:00:00"); //reset time
	frame.hasHint = true; 
	frame.jmiGameStart.setEnabled(true);
	frame.jmiGameEnd.setEnabled(false);
	frame.jmiTestClear.setEnabled(true);
	frame.jmiTestAnswer.setEnabled(true);
	
	for (int i = 0; i < 9; i++) {
	    for (int j = 0; j < 9; j++) {
		frame.np[i][j].setEvent(true); 
	    }
	}
    }


    private void exit() { //close window
	System.exit(0); 
    }

    private void answer() { //solve Sudoku
	Sudoku s = new Sudoku(frame.getArray());
	Sudoku ss = s.solveSudoku();
	if (ss == null) {
	    JOptionPane.showMessageDialog(frame, "This Sudoku is not solvable", "Solved", JOptionPane.WARNING_MESSAGE);
	} else {
	    int[][] n = ss.getArray();
	    for (int i = 0; i < 9; i++) {
		for (int j = 0; j < 9; j++) {
		    if (frame.np[i][j].getNumber() != n[i][j]) {
			frame.np[i][j].setNumber(n[i][j]); 
			new Thread().start(); 
		    }
		}
	    }
	    JOptionPane.showMessageDialog(frame, "Solved successfully！", "Solved", JOptionPane.INFORMATION_MESSAGE);
	}
    }

    private void clear() { //clear board
	for (int i = 0; i < 9; i++) {
	    for (int j = 0; j < 9; j++) {
		frame.np[i][j].setNumber(0); 
	    }
	}
    }

}

class NumberPanelGroup extends JPanel {

    
    public NumberPanelGroup() {
	setLayout(new GridLayout(3, 3)); 
	setBackground(Color.white); 
	setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)); 
    }
}


class NumberPanel extends JPanel implements MouseListener {

    
    private static final String[] numbers = {"Clear", "1", "2", "3", "4", "5", "6", "7", "8", "9"}; //儲存有效的數字範圍(0~9)，0表示為清空待輸入。
   
    private final SudokuWindow frame; 
    private int number = 0; 
    private final JLabel jlNumber = new JLabel(); 
    private final int x, y; 
    private boolean eventEnable = true; 

    
    public NumberPanel(final SudokuWindow sw, int index_x, int index_y) {
	frame = sw; 
	x = index_x; 
	y = index_y; 
	setLayout(new FlowLayout(FlowLayout.CENTER)); 
	setBackground(Color.white); 
	setBorder(BorderFactory.createLineBorder(Color.black, 1)); 
	jlNumber.setFont(new Font(null, Font.PLAIN, 40)); 
	jlNumber.setHorizontalTextPosition(JLabel.CENTER); 
	jlNumber.setForeground(Color.black); 
	add(jlNumber); 
	addListeners(); 
    }

   
    private void addListeners() {
	addMouseListener(this);
    }

    /**
     * Set Number, n ~ [0,9]
     */
    public void setNumber(int n) {
	    number = n; 
	    showNumber(); 
    }

    /**
     * get number
     */
    public int getNumber() {
	return number; 
    }

    /**
     * Display Value on Cell
     */
    public void showNumber() {
	if (number > 0) {
	    jlNumber.setText(String.valueOf(number)); 
	    if (frame.hasHint) {
		Sudoku s = new Sudoku(frame.getArray()); 
		if (!s.isCorrect(x, y)) { 
		    jlNumber.setForeground(Color.red); // wrong number is red
		    new Thread().start(); 
		} else {
		    jlNumber.setForeground(Color.black); // number is black if no apparent error
		}
		s = null; 
	    }
	} else { // number = 0
	    jlNumber.setText(""); 
	}
    }

    /**
     * number label getter
     */
    public JLabel getLabel() {
	return jlNumber;
    }

    /**
     * Setter
     */
    public void setEvent(boolean a) {
	eventEnable = a;
    }

    /**
     * getter
     */
    public boolean getEvent() {
	return eventEnable;
    }

 
    @Override 
    public void mouseEntered(MouseEvent e) { 
	if (getEvent()) {
	    setBackground(Color.yellow); 
	    frame.current_np = (NumberPanel) e.getSource(); 
	}
    }

   
    @Override 
    public void mouseExited(MouseEvent e) {
    	setBackground(Color.white); 
	frame.current_np = null; 
    }

 
    @Override 
    public void mousePressed(MouseEvent e) {
	if (getEvent()) {
	    int optionRtn = JOptionPane.showOptionDialog(NumberPanel.this, "Choose the number you want enter：", "Enter number", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, numbers, numbers[0]);
	    if (optionRtn > -1) {
		NumberPanel.this.setNumber(optionRtn);
	    }
	}
    }


    @Override
    public void mouseClicked(MouseEvent e) { 
	
    }

    @Override 
    public void mouseReleased(MouseEvent e) {
	
    }
}

/**
 * Timer class
 */
class Timer implements Runnable {

    
    private final long time = System.currentTimeMillis(); //starting time
    private final SudokuWindow frame; 

    public Timer(SudokuWindow sw) {
	frame = sw; 
    }

 
   
    public synchronized void run() {
	long dTime; //difference in time
	while (frame.isPlaying) { 
	    dTime = System.currentTimeMillis() - time; 
	    frame.jmTimer.setText(SudokuWindow.getTimeString(dTime)); //show how long has passed
	    Sudoku s = new Sudoku(frame.getArray()); //create new Sudoku
	    if (s.isCorrect(false)) {
		JOptionPane.showMessageDialog(frame, "Solved Successfully：" + SudokuWindow.getTimeString(dTime), "Congratulations！", JOptionPane.INFORMATION_MESSAGE);
		
		new JMenuItems(frame, "").endGame();
	    }
	}
	frame.jmTimer.setText("00:00:00"); //reset time

    }
}

