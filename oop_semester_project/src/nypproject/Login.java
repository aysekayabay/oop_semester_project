package nypproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField emailTextField;
	private JPasswordField passwordField;
	static ArrayList<Parent> parents = new ArrayList<>();
	static ArrayList<Child> children = new ArrayList<>();
	private UserType userType;
	public void main(String[] args) {
		readFromFile();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		initialize();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("child.png");
		setIconImage(img);
		setTitle("Çarpım Oyunu");
	}

	public static int readFromFile() {
		String fileName = "info.dat";
		try {
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(fileName));
			Integer parentCount = (Integer) reader.readObject();
			for (int i = 0; i < parentCount; i++) {
				Parent parent = (Parent) reader.readObject();
				parents.add(parent);
				System.out.print(parent.getEmail() + " 'in ");

				System.out.println("Oluşturduğu Egzersizler:");
				for (Exercise ex : parent.getExercises()) {
					System.out.println(ex.getExerciseName());
					System.out.println("egzersinizi çözenler:");

					for (Child child : ex.getSolvedBy()) {
						System.out.println(child.getEmail());
					}
					System.out.println("****");

				}
			}
			Integer childrenCount = (Integer) reader.readObject();
			for (int i = 0; i < childrenCount; i++) {
				Child child = (Child) reader.readObject();
				System.out.println(child.getEmail());
				System.out.println("Çözdüğü Egzersizler: ");
				for (Exercise exercise : child.getSolvedExercises()) {
					System.out.println(exercise.getExerciseName() + "Egzersiz Sonuçları:");
					System.out.println("DOĞRULUK SKORU : " + exercise.getAccuracyScore() + "  HIZ SKORU: "
							+ exercise.getSpeedScore());
					for (Question que : exercise.getQuestions()) {
						System.out.println(que);
					}

				}
				children.add(child);
			}

			System.out.println(children.get(0).getParent().getExercises().size());

			reader.close();
			return 1;
		} catch (IOException e) {
			System.out.println("An exception has occured during file reading.");
			JOptionPane.showMessageDialog(null, "Login dosyası bulunamadı");
			e.printStackTrace();
			return 0;

		} catch (ClassNotFoundException e) {
			System.out.println("An exception has occured while processing read records.");
			e.printStackTrace();
			return 0;

		}
	}
	public static Child findChild(String email, String password, ArrayList<Child> childrenList) {
		for (Child child : childrenList) {
			if (child.getEmail().equals(email) && child.getPassword().equals(password)) {
				return child;
			}
		}
		return null;
	}
	public static Parent findParent(String email, String password, ArrayList<Parent> parentsList) {
		for (Parent parent: parentsList) {
			if (parent.getEmail().equals(email) && parent.getPassword().equals(password)) {
				return parent;
			}
		}
		return null;
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 14));
		frame.setBounds(100, 100, 460, 486);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		int frameWidth = frame.getWidth();
		int frameHeight = frame.getHeight();
		int x = (screenWidth - frameWidth) / 2;
		int y = (screenHeight - frameHeight) / 2;
		frame.setLocation(x, y);

		JPanel loginBody = new JPanel();
		loginBody.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		loginBody.setBackground(Color.WHITE);
		loginBody.setBounds(48, 142, 349, 139);
		frame.getContentPane().add(loginBody);
		loginBody.setLayout(null);

		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(34, 27, 89, 13);
		loginBody.add(emailLabel);
		emailLabel.setFont(new Font("Roboto Condensed", Font.PLAIN, 11));

		emailTextField = new JTextField();
		emailTextField.setBounds(101, 20, 146, 27);
		loginBody.add(emailTextField);
		emailTextField.setColumns(10);

		JLabel passwordLabel = new JLabel("Şifre:");
		passwordLabel.setBounds(34, 64, 89, 13);
		loginBody.add(passwordLabel);
		passwordLabel.setFont(new Font("Roboto Condensed", Font.PLAIN, 11));

		JCheckBox passCheckBox = new JCheckBox("Göster");
		passCheckBox.setFont(new Font("Roboto Condensed", Font.PLAIN, 11));
		passCheckBox.setBounds(252, 60, 91, 21);
		loginBody.add(passCheckBox);
		passCheckBox.setBackground(Color.WHITE);

		passwordField = new JPasswordField();
		passwordField.setBounds(101, 57, 146, 27);
		loginBody.add(passwordField);

		JRadioButton parentButton = new JRadioButton("Veli");
		parentButton.setBounds(101, 97, 63, 21);
		loginBody.add(parentButton);
		parentButton.setFont(new Font("Roboto Condensed", Font.PLAIN, 11));
		parentButton.setBackground(Color.WHITE);

		JRadioButton childButton = new JRadioButton("Çocuk");
		childButton.setBounds(174, 97, 73, 21);
		loginBody.add(childButton);
		childButton.setFont(new Font("Roboto Condensed", Font.PLAIN, 11));
		childButton.setBackground(Color.WHITE);

		JLabel profileLabel = new JLabel("Profil:");
		profileLabel.setBounds(34, 101, 89, 13);
		loginBody.add(profileLabel);
		profileLabel.setFont(new Font("Roboto Condensed", Font.PLAIN, 11));

		childButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				parentButton.setSelected(false);
				userType = UserType.CHILD;
				
			}
		});
		parentButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				childButton.setSelected(false);
				userType = UserType.PARENT;
			}
		});
		passCheckBox.addActionListener(new ActionListener() {
			boolean passwordVisible = false;

			public void actionPerformed(ActionEvent e) {
				passwordVisible = !passwordVisible;
				if (passwordVisible) {
					passwordField.setEchoChar((char) 0); // show password

				} else {
					passwordField.setEchoChar('*'); // hide password

				}
			}
		});

		JButton loginButton = new JButton("GİRİŞ YAP");
		loginButton.setForeground(new Color(255, 255, 255));
		loginButton.setBorder(UIManager.getBorder("Button.border"));
		loginButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		loginButton.setBackground(new Color(0, 128, 128));
		loginButton.setBounds(130, 333, 186, 42);
		frame.getContentPane().add(loginButton);

		JPanel loginHeader = new JPanel();
		loginHeader.setBorder(null);
		loginHeader.setBackground(new Color(0, 128, 128));
		loginHeader.setBounds(0, 37, 446, 53);
		frame.getContentPane().add(loginHeader);
		loginHeader.setLayout(null);

		
		
		JLabel lblNewLabel = new JLabel("SİSTEM GİRİŞİ");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 10, 426, 33);
		loginHeader.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Roboto Condensed", Font.BOLD, 14));
		loginButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				Child childUser = null;
				Parent parentUser = null;
				if (!emailTextField.getText().isEmpty() && !passwordField.getText().isEmpty()
						&& (parentButton.isSelected() || childButton.isSelected())) {
					
					if (userType == UserType.PARENT) {
						 parentUser = findParent(emailTextField.getText(), passwordField.getText(), parents);
						
					}
					else {
						 childUser = findChild(emailTextField.getText(), passwordField.getText(),children);

					}
					
					if (parentUser != null || childUser != null) { // success
						JOptionPane.showMessageDialog(null, "Giriş Başarılı");
						frame.setVisible(false);
						HomePage homePage;
						if (userType == UserType.PARENT) {
							homePage = new HomePage(true, parentUser);
						} else {
							homePage = new HomePage(false, childUser);
						}
						homePage.main();
					}
					else {
						JOptionPane.showMessageDialog(null, "Email veya şifre yanlış...");

					}
				
				} else {
					JOptionPane.showMessageDialog(null, "Eksik alan bırakmayınız!");

				}

			}
		});
	}
}
