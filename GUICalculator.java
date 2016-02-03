/*
Christopher McMahon
January, 16, 2016.
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;

@SuppressWarnings("serial")
public class GUICalculator extends JFrame implements ActionListener {
	private JPanel panelA = new JPanel();
	private JPanel panelB = new JPanel();
	private JPanel panelC = new JPanel();
	
	private JTextField equaTxtBox;
	private JButton[] calcButtons = new JButton[16];
	private JButton clearButton;
	
	public GUICalculator() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		super("Calculator");
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		setResizable(false);
		setSize(230, 200);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panelA.setLayout(new FlowLayout());
		panelB.setLayout(new GridLayout(4,4));
		panelC.setLayout(new GridLayout(1,1));

		equaTxtBox = new JTextField("",17);
		equaTxtBox.setEditable(true);
		equaTxtBox.setPreferredSize(new Dimension(180,30));
		equaTxtBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	equaTxtBox.setText(equaTxtBox.getText() + " = ");	
				
				String ans = Calculation(equaTxtBox.getText());

				if(ans.equals("")) {
					equaTxtBox.setText("");
				}//if
				else {
					equaTxtBox.setText(equaTxtBox.getText() + ans);
					//disables the buttons; forces the user to clear the equation
					for(int i = 0; i <= 15; i ++) {
						calcButtons[i].setEnabled(false);
					}//for
				}//else 
		    }//actionPerformed
		});//anonymous class
		
		panelA.add(equaTxtBox);
		
		for(int i = 0; i < calcButtons.length; i ++) {
			if(i <= 9) {
				this.calcButtons[i] = new JButton(Integer.toString(i));
			}//if
			else if(i == 10) {
				this.calcButtons[i] = new JButton(" / ");
			}//else if
			else if(i == 11) {
				this.calcButtons[i] = new JButton(" * ");			
			}//else if
			else if(i == 12) {
				this.calcButtons[i] = new JButton(" - ");
			}//else if
			else if(i == 13) {
				this.calcButtons[i] = new JButton(" + ");
			}//else if
			else if(i == 14) {
				this.calcButtons[i] = new JButton(" = ");
			}//else if
			else if(i == 15) {
				this.calcButtons[i] = new JButton(".");				
			}//else if
			
			calcButtons[i].setPreferredSize(new Dimension(45,30));
			this.calcButtons[i].addActionListener(this);
			panelB.add(calcButtons[i]);
		}//for
		
		this.clearButton = new JButton("CLEAR");
		clearButton.setPreferredSize(new Dimension(180,30));
		this.clearButton.addActionListener(this);
		panelC.add(clearButton);

		add(panelA,BorderLayout.PAGE_START);
		add(panelB,BorderLayout.CENTER);
		add(panelC,BorderLayout.PAGE_END);
	}//part2 constructor
	
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == clearButton) {
			equaTxtBox.setText("");
			//re-enables the buttons after the equation has been cleared
			for(int i = 0; i <= 15; i ++) {
				calcButtons[i].setEnabled(true);
			}//for
		}//if		
		else if(evt.getSource() == calcButtons[14]){
			//calculates the answer for the user
	    	equaTxtBox.setText(equaTxtBox.getText() + " = ");	
	 
			String ans = Calculation(equaTxtBox.getText());

			if(ans.equals("")) {
				equaTxtBox.setText("");
			}//if
			else {
				equaTxtBox.setText(equaTxtBox.getText() + ans);
				//disables the buttons; forces the user to clear the equation
				for(int i = 0; i <= 15; i ++) {
					calcButtons[i].setEnabled(false);
				}//for
			}//else 
		}//else/if
		else {
			equaTxtBox.setText(equaTxtBox.getText() + ((JButton) evt.getSource()).getText());	
		}//else
	}//actionPerformed
	
	public String Calculation(String equation) {
		//removes all formatting spaces from the equation
		equation = equation.replaceAll(" ","");

		if(equation.matches("([0-9]+[\\.][0-9]+|[\\.][0-9]+|[0-9]+)((\\/|\\*|\\+|\\-)([0-9]+[\\.][0-9]+|[\\.][0-9]+|[0-9]+))+(\\=)")) { 
            //uses StringTokenizer to parse through the equation separating it into the numbers and operators 
			StringTokenizer numTok = new StringTokenizer(equation,"/*+-=");
            StringTokenizer operTok = new StringTokenizer(equation,"0123456789.");
            
            double ans = Double.parseDouble(numTok.nextToken());
			double num = 0;
            String operator;
			
            while(numTok.hasMoreTokens() && operTok.hasMoreTokens()) {
            	num = Double.parseDouble(numTok.nextToken());
            	operator = operTok.nextToken();
	           
            	//operations: division, multiplication, addition, subtraction
	            if(operator.equals("/")) {
	            	if(num == 0) { 
	        			JOptionPane.showMessageDialog(clearButton,"Cannot Divide by Zero","Error", JOptionPane.ERROR_MESSAGE);
	        			return "";            	
	            	}//if
	            	else {
	                	ans /= num;
	            	}//else
	            }//if
	            else if(operator.equals("*")) {
	            	ans *= num;
	            }//else if
	            else if(operator.equals("+")) {
	            	ans += num;
	            }//else if 
	            else if(operator.equals("-")) {
	            	ans -= num;
	            }//else if 
		}//while
            return String.format("%.2f", ans);
		}//if
		else {
			JOptionPane.showMessageDialog(clearButton,"Incorrect Equation","Error", JOptionPane.ERROR_MESSAGE);
			return "";
		}//else
	}//Calculation
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		GUICalculator Frame = new GUICalculator();
		Frame.setVisible(true);
	}//main class
}//part2 class
