import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class Main extends JDialog {
	static final long serialVersionUID = 1L;

	public Main() {
        setTitle("Welcome to MathCode");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        JLabel titleLabel = new JLabel("What operation do you want to perform?");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JButton findDivisorsOrPrimes = new JButton("Find the possible divisors of a number.");
        JButton calculateGCDandLCM = new JButton("Calculate the GCD and LCM of a number.");
        JButton numberBases = new JButton("Convert from one base to another.");

        findDivisorsOrPrimes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField input1 = new JTextField(10);

                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new GridLayout(2, 3));
                inputPanel.add(new JLabel("Please enter a number."));
                inputPanel.add(input1);

                int result = JOptionPane.showConfirmDialog(null, inputPanel, "Divisible and/or Prime.", JOptionPane.OK_CANCEL_OPTION);

                String inputText1 = input1.getText();

                try {
                    int number1 = Integer.parseInt(inputText1);

                    boolean isPrime = true;

                    if (number1 <= 0) {
                        isPrime = false;
                    } else {
                        for (int i = 2; i <= Math.sqrt(number1); i++) {
                            if (number1 % i == 0) {
                                isPrime = false;
                                break;
                            }
                        }
                    }

                    if (isPrime) {
                        JOptionPane.showMessageDialog(null, number1 + " is a prime number.");
                    } else {
                        StringBuilder divisors = new StringBuilder("Divisors of " + number1 + ": ");
                        for (int i = 1; i <= number1; i++) {
                            if (number1 % i == 0) {
                                divisors.append(i).append(" ");
                            }
                        }
                        JOptionPane.showMessageDialog(null, divisors.toString());
                    }

                } catch (NumberFormatException event) {
                    JOptionPane.showMessageDialog(null, "Please enter valid integer numbers.");
                }
            }
        });

        calculateGCDandLCM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField input1 = new JTextField(10);
                JTextField input2 = new JTextField(10);

                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new GridLayout(4, 4));
                inputPanel.add(new JLabel("Please enter a number."));
                inputPanel.add(input1);
                inputPanel.add(new JLabel("Please enter the second number."));
                inputPanel.add(input2);

                int result = JOptionPane.showConfirmDialog(null, inputPanel, "Calculate GCD and LCM", JOptionPane.OK_CANCEL_OPTION);

                String inputText1 = input1.getText();
                String inputText2 = input2.getText();

                try {
                    int number1 = Integer.parseInt(inputText1);
                    int number2 = Integer.parseInt(inputText2);
                    if (number1 % 1 == 0 && number2 % 1 == 0) {
                        int greaterNumber = Math.max(number1, number2);
                        int smallerNumber = Math.min(number1, number2);

                        while (smallerNumber != 0) {
                            int temp = smallerNumber;
                            smallerNumber = greaterNumber % smallerNumber;
                            greaterNumber = temp;
                        }

                        int gcd = greaterNumber;

                        int lcm = (number1 * number2) / gcd;

                        JOptionPane.showMessageDialog(null, "The GCD of " + number1 + " and " + number2 + " is: " + gcd);
                        JOptionPane.showMessageDialog(null, "The LCM of " + number1 + " and " + number2 + " is: " + lcm);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please enter valid integer numbers.");
                    }

                } catch (NumberFormatException event) {
                    JOptionPane.showMessageDialog(null, "Please enter valid integer numbers.");
                }
            }
        });

        numberBases.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextField inputNumber = new JTextField(20);
                JTextField inputSourceBase = new JTextField(5);
                JTextField inputTargetBase = new JTextField(5);

                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new GridLayout(4, 2));
                inputPanel.add(new JLabel("Enter a number."));
                inputPanel.add(inputNumber);
                inputPanel.add(new JLabel("Source base."));
                inputPanel.add(inputSourceBase);
                inputPanel.add(new JLabel("Target base."));
                inputPanel.add(inputTargetBase);

                int result = JOptionPane.showConfirmDialog(null, inputPanel, "Base to Base Conversion", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String number = inputNumber.getText();
                    int sourceBase = Integer.parseInt(inputSourceBase.getText());
                    int targetBase = Integer.parseInt(inputTargetBase.getText());

                    try {
                        String resultString = "";

                        if (sourceBase <= 36 && targetBase <= 36) {
                            resultString = convertBase(number, sourceBase, targetBase);
                        } else {
                            JOptionPane.showMessageDialog(null, "Bases greater than 36 are not supported.");
                            return;
                        }

                        JOptionPane.showMessageDialog(null, "The number " + number + " in base " + sourceBase + " is " + resultString + " in base " + targetBase);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter valid values.");
                    }
                }
            }

            // Function to convert numbers in bases greater than 10 to letters
            private String convertBase(String number, int sourceBase, int targetBase) {
                String digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                BigInteger decimalNumber;
                if (sourceBase == 10) {
                    decimalNumber = new BigInteger(number);
                } else {
                    decimalNumber = new BigInteger(number, sourceBase);
                }

                StringBuilder result = new StringBuilder();

                while (!decimalNumber.equals(BigInteger.ZERO)) {
                    int index = decimalNumber.mod(BigInteger.valueOf(targetBase)).intValue();
                    result.insert(0, digits.charAt(index));
                    decimalNumber = decimalNumber.divide(BigInteger.valueOf(targetBase));
                }

                return result.toString();
            }
        });

        panel.add(titleLabel);
        panel.add(findDivisorsOrPrimes);
        panel.add(calculateGCDandLCM);
        panel.add(numberBases);

        add(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main dialog = new Main();
            dialog.setVisible(true);
        });
    }
}
