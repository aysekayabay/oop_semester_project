package nypproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class HomePage extends JFrame {
	private static final long serialVersionUID = -4475202144940717110L;
	private static int round = 0;
	int correctCount = 0;
	int wrongCount = 0;
	private static boolean isAnswered = false;
	private static boolean isAdmin;
	private static Child childUser;
	private static Parent parentUser;
	private JFrame frame;
	private JTextField v1Start;
	private JTextField v2Start;
	private JTextField v1End;
	private JTextField v2End;
	private JTextField questionCount;
	long elapsedTime = 0;
	private static int hours = 0;
	private static int minutes = 0;
	private static int seconds = 0;
	private static int milliseconds = 0;
	private static JLabel hour;
	private static JLabel minute;
	private static JLabel second;
	private static JLabel millisecond;
	boolean isFreeExercise = false;
	boolean isStart = false;
	static int solvingTimeInSeconds;
	JPanel scoreTablePanel;
	JPanel startExercisePanel;
	JPanel createExercisePanel;
	Exercise selectedExerciseA;
	JPanel homePanel;
	JPanel reportsPanel;
	JPanel definedExercisesPanel;
	JButton freeExerciseButton;
	JButton highScoreButton;
	JButton addExerciseButton;
	JButton backButton;
	JButton reportsButton;
	JButton defaultStartButton;
	JButton exercisesButton;
	JButton startButton;
	private JLabel lblBalang_1_2;
	private JTextField nameField;
	private JTextField answerField;
	private JTable scoreTable;

	public void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage window;
					if (isAdmin) {
						window = new HomePage(isAdmin, parentUser);
					} else {
						window = new HomePage(isAdmin, childUser);
					}
					window.frame.setVisible(true);
					Toolkit kit = Toolkit.getDefaultToolkit();
					Image img = kit.getImage("child.png");
					setIconImage(img);
					setTitle("Çarpım Oyunu");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	class MenuButtonActionListener implements ActionListener { // for navigation
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			startExercisePanel.setVisible(button == freeExerciseButton);
			scoreTablePanel.setVisible(button == highScoreButton);
			createExercisePanel.setVisible(button == addExerciseButton);
			reportsPanel.setVisible(button == reportsButton);
			definedExercisesPanel.setVisible(button == exercisesButton);
			homePanel.setVisible(!(button == highScoreButton || button == addExerciseButton || button == reportsButton
					|| button == exercisesButton || button == startButton || button == freeExerciseButton));
		}
	}


	/**
	 * @wbp.parser.constructor
	 */
	public HomePage(boolean isAdmin, Parent parent) {
		HomePage.isAdmin = isAdmin;
		parentUser = parent;
		initialize();
	}

	public HomePage(boolean isAdmin, Child child) {
		HomePage.isAdmin = isAdmin;
		childUser = child;
		initialize();
	}

	public static void updateCSV() {
		String filePath = "reports.csv";
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(filePath, true), "UTF-8"))) {
			writer.write(childUser.getEmail());
			writer.write(";");
			Exercise lastExercise = childUser.getSolvedExercises().get(childUser.getSolvedExercises().size() - 1);
			writer.write(lastExercise.getExerciseName());
			writer.write(";");

			writer.write(String.format("%.2f", lastExercise.getAccuracyScore()));
			writer.write(";");

			writer.write(String.format("%.2f", lastExercise.getSpeedScore()));
			writer.write(";");

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm:ss.SSS");
			String formattedStartTime = lastExercise.getStartTime().format(formatter);
			writer.write(formattedStartTime);
			writer.write(";");

			long duration = lastExercise.getDuration();
			long hours = TimeUnit.MILLISECONDS.toHours(duration);
			long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
			long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
			long milliseconds = duration % 1000;

			String formattedDuration = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
			writer.write(formattedDuration);
			writer.write(";");

			writer.write(String.valueOf(lastExercise.getQuestions().size()));
			writer.write(";");

			writer.write(String.valueOf(lastExercise.getCorrectAnswerCount()));
			writer.write(";");

			writer.write(String.valueOf(lastExercise.getWrongAnswerCount()));
			writer.write(";");

			for (int i = 0; i < lastExercise.getQuestions().size(); i++) {
				Question question = lastExercise.getQuestions().get(i);
				writer.write(question.getA() + "x" + question.getB());
				writer.write(";");
				 milliseconds = question.getDuration();
				seconds = (int) (milliseconds / 1000);
				 minutes = seconds / 60;
				seconds %= 60;
				if (question.getIsCorrect()) {
					if (minutes >0) {
						writer.write("D - "+ minutes + " Dakika " + seconds + " Saniye");

					}
					else {
						writer.write("D - "+ seconds + " Saniye");

					}
				}
				else {
					if (minutes >0) {
						writer.write("Y - "+ minutes + " Dakika " + seconds + " Saniye");

					}
					else {
						writer.write("Y - "+ seconds + " Saniye");

					}
				}
			
				writer.write(";");
			}
			writer.newLine();

			System.out.println("CSV dosyası güncellendi: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeCSV() {
		String filePath = "reports.csv";
		String[] header = { "Email", "Exercise Name", "Accuracy Score", "Speed Score", "Start Time", "Duration",
				"Number of Questions", "Correct Answer Count", "Wrong Answer Count" };
		for (int i = 1; i <= 40; i++) {
		    header = Arrays.copyOf(header, header.length + 2);
		    header[header.length - 2] = "Q" + i;
		    header[header.length - 1] = "A" + i;
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(String.join(";", header));
			writer.newLine();
			System.out.println("CSV dosyası güncellendi: " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frame.setBounds(100, 100, 526, 436);
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
		MenuButtonActionListener menuButtonListener = new MenuButtonActionListener();
		DefaultTableModel tableModel = new DefaultTableModel(new Object[] { "Email", "Doğruluk Skoru", "Hız Skoru" }, 0);

		homePanel = new JPanel();
		homePanel.setBackground(Color.WHITE);
		homePanel.setBounds(0, 0, 512, 399);
		frame.getContentPane().add(homePanel);
		homePanel.setLayout(null);

		highScoreButton = new JButton("YÜKSEK SKOR TABLOSU");
		highScoreButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		highScoreButton.setFocusPainted(false);
		highScoreButton.setBounds(141, 130, 230, 42);
		homePanel.add(highScoreButton);

		highScoreButton.setForeground(Color.WHITE);
		highScoreButton.setBackground(new Color(0, 128, 128));

		reportsButton = new JButton("RAPORLAR");
		reportsButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		reportsButton.setFocusPainted(false);
		reportsButton.setBounds(141, 242, 230, 42);
		homePanel.add(reportsButton);
		reportsButton.setForeground(Color.WHITE);
		reportsButton.setBackground(new Color(0, 128, 128));

		addExerciseButton = new JButton("YENİ ALIŞTIRMA TANIMLA");
		addExerciseButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		addExerciseButton.setFocusPainted(false);
		addExerciseButton.setBounds(141, 186, 230, 42);
		homePanel.add(addExerciseButton);
		addExerciseButton.setForeground(Color.WHITE);
		addExerciseButton.setBackground(new Color(0, 128, 128));
		exercisesButton = new JButton("TANIMLANMIŞ ALIŞTIRMA ÇÖZ");
		exercisesButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		exercisesButton.setFocusPainted(false);
		exercisesButton.setBounds(141, 186, 230, 42);
		homePanel.add(exercisesButton);
		exercisesButton.setForeground(Color.WHITE);
		exercisesButton.setBackground(new Color(0, 128, 128));

		JButton logOutButton = new JButton("ÇIKIŞ YAP");
		logOutButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				Login loginFrame = new Login();
				loginFrame.main(null);
			}
		});
		logOutButton.setFocusPainted(false);
		logOutButton.setForeground(new Color(0, 128, 128));
		logOutButton.setBorder(new LineBorder(new Color(0, 128, 128)));
		logOutButton.setBackground(Color.WHITE);
		logOutButton.setBounds(141, 298, 230, 42);
		homePanel.add(logOutButton);

		JPanel header = new JPanel();
		header.setBackground(new Color(0, 128, 128));
		header.setBounds(0, 37, 512, 53);
		homePanel.add(header);
		header.setLayout(null);

		JLabel welcomeLabel = new JLabel("SAYFASI");
		welcomeLabel.setBounds(10, 10, 492, 33);
		header.add(welcomeLabel);
		welcomeLabel.setBackground(new Color(0, 128, 128));
		welcomeLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		welcomeLabel.setForeground(Color.WHITE);
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Roboto Condensed", Font.BOLD, 14));
		exercisesButton.setVisible(!isAdmin);
		reportsButton.setVisible(isAdmin);
		addExerciseButton.setVisible(isAdmin);

		homePanel.setVisible(true);
		reportsButton.addActionListener(menuButtonListener);
		highScoreButton.addActionListener(menuButtonListener);
		exercisesButton.addActionListener(menuButtonListener);
		addExerciseButton.addActionListener(menuButtonListener);

		freeExerciseButton = new JButton("VARSAYILAN ALIŞTIRMA MODU");
		freeExerciseButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));

		freeExerciseButton.setVisible(!isAdmin);
		freeExerciseButton.setForeground(Color.WHITE);
		freeExerciseButton.setFocusPainted(false);
		freeExerciseButton.setBackground(new Color(0, 128, 128));
		freeExerciseButton.setBounds(141, 242, 230, 42);
		homePanel.add(freeExerciseButton);

		scoreTablePanel = new JPanel();
		scoreTablePanel.setBackground(Color.WHITE);
		scoreTablePanel.setBounds(0, 0, 512, 399);
		frame.getContentPane().add(scoreTablePanel);
		scoreTablePanel.setLayout(null);

		JLabel lblNewLabel_4 = new JLabel("YÜKSEK SKOR TABLOSU");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Roboto Condensed", Font.BOLD, 14));
		lblNewLabel_4.setBounds(141, 12, 230, 22);
		scoreTablePanel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Alıştırmalardan birini seç");
		lblNewLabel_5.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(30, 42, 187, 22);
		scoreTablePanel.add(lblNewLabel_5);

		JButton showButton = new JButton("GÖRÜNTÜLE");
		showButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		showButton.setFocusPainted(false);

		showButton.setForeground(Color.WHITE);
		showButton.setBackground(new Color(0, 128, 128));
		showButton.setBounds(322, 65, 157, 33);
		scoreTablePanel.add(showButton);
		scoreTablePanel.setVisible(false);
		scoreTable = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(scoreTable);
		scrollPane.setFont(new Font("Roboto Condensed", Font.PLAIN, 10));
		scrollPane.setBackground(new Color(173, 216, 230));
		scoreTable.setDefaultEditor(Object.class, null);

		scrollPane.setBounds(30, 108, 449, 258);
		scoreTablePanel.add(scrollPane);
		DefaultComboBoxModel<Exercise> tableBoxModel = new DefaultComboBoxModel<>(); //drawer elements
		if (!isAdmin) {
			ArrayList<Exercise> allExercises = childUser.getParent().getExercises();
			for (Exercise exercise : allExercises) {
				tableBoxModel.addElement(exercise);
			}

		} else {
			ArrayList<Exercise> allExercises = parentUser.getExercises();
			for (Exercise exercise : allExercises) {
				tableBoxModel.addElement(exercise);
			}
		}

		tableBoxModel.setSelectedItem(null); // nothing selected at first

		JComboBox<Exercise> tableExercisesBox = new JComboBox<>(tableBoxModel);
		tableExercisesBox.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		tableExercisesBox.setBackground(Color.WHITE);
		tableExercisesBox.setBounds(30, 65, 273, 33);
		scoreTablePanel.add(tableExercisesBox);

		JLabel prevButton = new JLabel("<");
		prevButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModel.setRowCount(0);
				scoreTablePanel.setVisible(false);
				homePanel.setVisible(true);
				tableExercisesBox.setSelectedItem(null);
			}
		});
		prevButton.setFont(new Font("Roboto Condensed", Font.BOLD, 14));
		prevButton.setBounds(30, 17, 45, 13);
		scoreTablePanel.add(prevButton);

		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Exercise selectedEx = (Exercise) tableExercisesBox.getSelectedItem();
				tableModel.setRowCount(0);
				if (selectedEx != null) {
					if (selectedEx.getHighScoreTable() != null && selectedEx.getHighScoreTable().size() != 0) {
						List<HighScoreTableModel> highScores = selectedEx.getHighScoreTable();
						Collections.sort(highScores, new Comparator<HighScoreTableModel>() { 
							@Override
							public int compare(HighScoreTableModel o1, HighScoreTableModel o2) {  // comparison based on accuracy and speed scores
								double accuracy1 = o1.getAccuracyScore();
								double accuracy2 = o2.getAccuracyScore();
								if (accuracy1 != accuracy2) {
									return Double.compare(accuracy2, accuracy1);
								} else {
									double speed1 = o1.getSpeedScore();
									double speed2 = o2.getSpeedScore();
									return Double.compare(speed2, speed1);
								}
							}
						});

						for (HighScoreTableModel child : highScores) {
							Object[] rowData = { child.getEmail(), String.format("%.2f", child.getAccuracyScore()),
									String.format("%.2f", child.getSpeedScore()) };
							tableModel.addRow(rowData);
						}
						tableModel.fireTableDataChanged(); // update table vision
					} else {

						JOptionPane.showMessageDialog(null, "Henüz skor yok!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Lütfen bir alıştırma seçiniz!");
				}
			}
		});
		createExercisePanel = new JPanel();
		createExercisePanel.setBackground(Color.WHITE);
		createExercisePanel.setBounds(0, 0, 512, 399);
		frame.getContentPane().add(createExercisePanel);
		createExercisePanel.setLayout(null);

		backButton = new JButton("VAZGEÇ");
		backButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		backButton.setFocusPainted(false);

		backButton.setBorder(new LineBorder(new Color(0, 128, 128)));
		backButton.setForeground(new Color(0, 128, 128));
		backButton.setBackground(Color.WHITE);
		backButton.setBounds(47, 309, 193, 46);
		createExercisePanel.add(backButton);

		JButton createExerciseButton = new JButton("TANIMLA");
		createExerciseButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		createExerciseButton.setFocusPainted(false);
		createExerciseButton.setForeground(Color.WHITE);
		createExerciseButton.setBackground(new Color(0, 128, 128));
		createExerciseButton.setBounds(274, 309, 193, 46);
		createExercisePanel.add(createExerciseButton);

		v1Start = new JTextField();
		v1Start.setBounds(48, 216, 62, 30);
		createExercisePanel.add(v1Start);
		v1Start.setColumns(10);

		JLabel lblNewLabel = new JLabel("1. Çarpan Aralığı");
		lblNewLabel.setFont(new Font("Roboto Condensed", Font.BOLD, 13));
		lblNewLabel.setBounds(47, 170, 136, 22);
		createExercisePanel.add(lblNewLabel);

		JLabel lblBalang = new JLabel("Başlangıç");
		lblBalang.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblBalang.setBounds(48, 195, 71, 22);
		createExercisePanel.add(lblBalang);

		JLabel lblSon = new JLabel("Son");
		lblSon.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblSon.setBounds(120, 195, 118, 22);
		createExercisePanel.add(lblSon);

		JLabel lblarpanAral = new JLabel("2. Çarpan Aralığı");
		lblarpanAral.setFont(new Font("Roboto Condensed", Font.BOLD, 13));
		lblarpanAral.setBounds(276, 170, 136, 22);
		createExercisePanel.add(lblarpanAral);

		JLabel lblBalang_1 = new JLabel("Başlangıç");
		lblBalang_1.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblBalang_1.setBounds(277, 195, 71, 22);
		createExercisePanel.add(lblBalang_1);

		v2Start = new JTextField();
		v2Start.setColumns(10);
		v2Start.setBounds(277, 216, 62, 30);
		createExercisePanel.add(v2Start);

		JLabel lblSon_1 = new JLabel("Son");
		lblSon_1.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblSon_1.setBounds(349, 195, 118, 22);
		createExercisePanel.add(lblSon_1);

		JLabel lblBalang_1_1 = new JLabel("Soru Sayısı");
		lblBalang_1_1.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblBalang_1_1.setBounds(274, 77, 118, 22);
		createExercisePanel.add(lblBalang_1_1);

		v1End = new JTextField();
		v1End.setColumns(10);
		v1End.setBounds(121, 216, 62, 30);
		createExercisePanel.add(v1End);

		v2End = new JTextField();
		v2End.setColumns(10);
		v2End.setBounds(350, 216, 62, 30);
		createExercisePanel.add(v2End);

		questionCount = new JTextField();
		questionCount.setColumns(10);
		questionCount.setBounds(274, 100, 62, 30);
		createExercisePanel.add(questionCount);

		JLabel lblNewLabel_1 = new JLabel("YENİ ALIŞTIRMA TANIMLA");
		lblNewLabel_1.setFont(new Font("Roboto Condensed", Font.BOLD, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(154, 21, 204, 33);
		createExercisePanel.add(lblNewLabel_1);

		lblBalang_1_2 = new JLabel("Alıştırma Adı");
		lblBalang_1_2.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblBalang_1_2.setBounds(47, 77, 118, 22);
		createExercisePanel.add(lblBalang_1_2);

		definedExercisesPanel = new JPanel();
		definedExercisesPanel.setBackground(Color.WHITE);
		definedExercisesPanel.setBounds(0, 0, 512, 399);
		frame.getContentPane().add(definedExercisesPanel);
		definedExercisesPanel.setLayout(null);

		JLabel solvedExercisesCount = new JLabel("0");
		solvedExercisesCount.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		solvedExercisesCount.setBounds(442, 48, 60, 39);
		definedExercisesPanel.add(solvedExercisesCount);

		JLabel exercisesCount = new JLabel("0");
		exercisesCount.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		exercisesCount.setBounds(442, 23, 60, 33);

		startExercisePanel = new JPanel();
		startExercisePanel.setBackground(Color.WHITE);
		startExercisePanel.setBounds(0, 0, 512, 399);
		frame.getContentPane().add(startExercisePanel);
		startExercisePanel.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 148, 492, 93);
		startExercisePanel.add(panel);
		panel.setLayout(null);

		JLabel aLabel = new JLabel("A1");
		aLabel.setBounds(20, 16, 57, 50);
		panel.add(aLabel);
		aLabel.setHorizontalAlignment(SwingConstants.CENTER);
		aLabel.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));

		answerField = new JTextField();
		answerField.setBounds(290, 26, 134, 35);
		panel.add(answerField);
		answerField.setColumns(10);

		JLabel lblX = new JLabel("x");
		lblX.setBounds(89, 16, 57, 50);
		panel.add(lblX);
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));

		JLabel bLabel = new JLabel("B1");
		bLabel.setBounds(158, 16, 57, 50);
		panel.add(bLabel);
		bLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bLabel.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));

		JLabel aLabel_3 = new JLabel("=");
		aLabel_3.setBounds(223, 16, 57, 50);
		panel.add(aLabel_3);
		aLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		aLabel_3.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));

		JPanel isCorrectPanel = new JPanel();
		isCorrectPanel.setBounds(290, 60, 134, 10);
		panel.add(isCorrectPanel);
		isCorrectPanel.setBackground(Color.WHITE);

		JLabel answerLabel = new JLabel("");
		answerLabel.setBounds(434, 26, 35, 35);
		panel.add(answerLabel);
		answerLabel.setBackground(new Color(128, 128, 128));
		answerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		answerLabel.setFont(new Font("Roboto Thin", Font.BOLD, 14));

		JButton answerButton = new JButton("CEVAPLA");
		answerButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		answerButton.setFocusPainted(false);
		answerButton.setForeground(Color.WHITE);
		answerButton.setBackground(new Color(0, 128, 128));
		answerButton.setBounds(268, 311, 193, 46);
		startExercisePanel.add(answerButton);

		JLabel exerciseNameLabel = new JLabel("New label");
		exerciseNameLabel.setFont(new Font("Roboto Condensed", Font.BOLD, 14));
		exerciseNameLabel.setBounds(120, 31, 207, 22);
		startExercisePanel.add(exerciseNameLabel);

		hour = new JLabel("0");
		hour.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));
		hour.setBounds(43, 63, 45, 50);
		startExercisePanel.add(hour);

		JLabel aLabel_1_1 = new JLabel(":");
		aLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		aLabel_1_1.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));
		aLabel_1_1.setBounds(86, 63, 26, 50);
		startExercisePanel.add(aLabel_1_1);

		minute = new JLabel("0");
		minute.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));
		minute.setBounds(120, 63, 35, 50);
		startExercisePanel.add(minute);

		second = new JLabel("0");
		second.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));
		second.setBounds(187, 63, 35, 50);
		startExercisePanel.add(second);

		millisecond = new JLabel("0");
		millisecond.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));
		millisecond.setBounds(242, 63, 35, 50);
		startExercisePanel.add(millisecond);

		JLabel aLabel_1_1_1 = new JLabel(":");
		aLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		aLabel_1_1_1.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));
		aLabel_1_1_1.setBounds(161, 63, 26, 50);
		startExercisePanel.add(aLabel_1_1_1);

		JLabel aLabel_1_1_1_1 = new JLabel(":");
		aLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		aLabel_1_1_1_1.setFont(new Font("Roboto Slab ExtraBold", Font.BOLD, 16));
		aLabel_1_1_1_1.setBounds(218, 63, 26, 50);
		startExercisePanel.add(aLabel_1_1_1_1);

		JLabel exerciseNameLabel_1 = new JLabel("Alıştırma Adı:");
		exerciseNameLabel_1.setFont(new Font("Roboto Condensed", Font.PLAIN, 14));
		exerciseNameLabel_1.setBounds(43, 31, 83, 22);
		startExercisePanel.add(exerciseNameLabel_1);
		startExercisePanel.setVisible(false);

		DefaultComboBoxModel<Child> childrenBoxModel = new DefaultComboBoxModel<>(); // children drawer
		if (isAdmin) {
			ArrayList<Child> allChildren = parentUser.getChildren();
			for (Child child : allChildren) {
				childrenBoxModel.addElement(child);
			}

		}

		if (isAdmin) {
			welcomeLabel.setText("VELİ SAYFASI");
		} else {
			welcomeLabel.setText("ÇOCUK SAYFASI");

		}

		DefaultComboBoxModel<Exercise> modelBoxModel = new DefaultComboBoxModel<>();
		if (!isAdmin) {

			solvedExercisesCount.setText(String.valueOf(childUser.getSolvedExercises().size()));
			exercisesCount.setText(String.valueOf(childUser.getParent().getExercises().size()));
			ArrayList<Exercise> allExercises = childUser.getParent().getExercises();
			System.out.println("Alıştırmalar***");
			for (Exercise exercise : allExercises) {
				System.out.println(exercise.getExerciseName() + "  " + exercise.getSolvedBy().size());
				if (!exercise.isSolvedBy(childUser.getEmail())) {
					modelBoxModel.addElement(exercise);

				}
			}
			System.out.println("Alıştırmalar bitiş***");

		}
		JComboBox<Exercise> exercisesBox = new JComboBox<>(modelBoxModel);
		exercisesBox.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		exercisesBox.setBackground(Color.WHITE);
		exercisesBox.setBounds(44, 113, 317, 33);

		JLabel lblNewLabel_2 = new JLabel("Tanımlı Alıştırma Sayısı:");
		lblNewLabel_2.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(285, 23, 146, 33);

		definedExercisesPanel.add(exercisesCount);
		definedExercisesPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Tanımlanmış Alıştırmalar");
		lblNewLabel_3.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(44, 82, 175, 21);
		definedExercisesPanel.add(lblNewLabel_3);

		startButton = new JButton("BAŞLA");
		startButton.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		startButton.setFocusPainted(false);
		startButton.setForeground(Color.WHITE);
		startButton.setBackground(new Color(0, 128, 128));
		startButton.setBounds(275, 302, 193, 46);
		definedExercisesPanel.add(startButton);

		freeExerciseButton.addActionListener(menuButtonListener);
		freeExerciseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isFreeExercise = true;
				selectedExerciseA = new Exercise();
				startTimer();
				exerciseNameLabel.setText(selectedExerciseA.getExerciseName());
				aLabel.setText(String.valueOf(selectedExerciseA.getQuestions().get(0).getA()));
				bLabel.setText(String.valueOf(selectedExerciseA.getQuestions().get(0).getB()));

			}
		});
		JButton backButton_1 = new JButton("VAZGEÇ");
		backButton_1.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		backButton_1.setFocusPainted(false);
		backButton_1.setForeground(new Color(0, 128, 128));
		backButton_1.setBorder(new LineBorder(new Color(0, 128, 128)));
		backButton_1.setBackground(Color.WHITE);
		backButton_1.setBounds(44, 302, 193, 46);
		definedExercisesPanel.add(backButton_1);

		JLabel lblNewLabel_2_1 = new JLabel("Çözülen Alıştırma Sayısı");
		lblNewLabel_2_1.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(285, 48, 146, 39);
		definedExercisesPanel.add(lblNewLabel_2_1);
		definedExercisesPanel.setVisible(false);
		definedExercisesPanel.setVisible(false);

		definedExercisesPanel.add(exercisesBox);

		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (modelBoxModel.getSelectedItem() != null) {
					LocalDateTime startDateTime = LocalDateTime.now();
					isFreeExercise = false;
					startTimer();
					selectedExerciseA = (Exercise) exercisesBox.getSelectedItem();
					selectedExerciseA.setCorrectAnswerCount(0);
					selectedExerciseA.setWrongAnswerCount(0);
					selectedExerciseA.setSpeedScore(0);
					selectedExerciseA.setAccuracyScore(0);
					selectedExerciseA.setStartTime(startDateTime);
					exerciseNameLabel.setText(selectedExerciseA.getExerciseName());
					aLabel.setText(String.valueOf(selectedExerciseA.getQuestions().get(0).getA()));
					bLabel.setText(String.valueOf(selectedExerciseA.getQuestions().get(0).getB()));
					startExercisePanel.setVisible(true);
					definedExercisesPanel.setVisible(false);
				} else {
					if (modelBoxModel.getSize() == 0) {
						JOptionPane.showMessageDialog(null, "Tanımlı yeni bir alıştırma yok!");

					} else {
						JOptionPane.showMessageDialog(null, "Lütfen bir alıştırma seçiniz!");

					}
				}
			}

		});
		backButton_1.addActionListener(menuButtonListener);

		reportsPanel = new JPanel();
		reportsPanel.setBackground(Color.WHITE);

		reportsPanel.setBounds(0, 0, 512, 399);
		frame.getContentPane().add(reportsPanel);
		reportsPanel.setLayout(null);

		JLabel lblNewLabel_4_1 = new JLabel("ÇOCUK RAPORU");
		lblNewLabel_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_1.setFont(new Font("Roboto Condensed", Font.BOLD, 14));
		lblNewLabel_4_1.setBounds(141, 12, 230, 22);
		reportsPanel.add(lblNewLabel_4_1);

		JLabel prevButton_1 = new JLabel("<");
		prevButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				reportsPanel.setVisible(false);
				homePanel.setVisible(true);
			}
		});
		prevButton_1.setFont(new Font("Roboto Condensed", Font.BOLD, 14));
		prevButton_1.setBounds(30, 17, 45, 13);
		reportsPanel.add(prevButton_1);

		JLabel lblNewLabel_5_1 = new JLabel("Çocuk seç");
		lblNewLabel_5_1.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		lblNewLabel_5_1.setBounds(28, 44, 187, 22);
		reportsPanel.add(lblNewLabel_5_1);

		JButton showChild = new JButton("GÖRÜNTÜLE");
		showChild.setFont(new Font("Roboto Condensed", Font.BOLD, 12));
		answerButton.addActionListener(new ActionListener() {
			private long totalDuration = 0;
			long questionDuration;
			@Override
			public void actionPerformed(ActionEvent e) {
				String answerText = "CEVAPLA";
				String finishText = "BİTİR";
				Question question = selectedExerciseA.getQuestions().get(round);
				Boolean isCorrect;
				String nextText = "GEÇ";
				if (!isAnswered && round < selectedExerciseA.getQuestions().size()) {
					isAnswered = !isAnswered;
					if (round == selectedExerciseA.getQuestions().size() - 1) {
						answerButton.setText(finishText);
					} else {
						answerButton.setText(nextText);

					}
					answerField.setEnabled(false);

					if (!answerField.getText().isEmpty() && answerField.getText().trim().matches("\\d+") // trim: remove leading and trailing spaces
							&& Integer.parseInt(answerField.getText().trim()) == question.getAnswer()) {
						isCorrect = true;
						correctCount++;
						isCorrectPanel.setBackground(Color.GREEN);
					} else {
						isCorrect = false;
						answerLabel.setText(String.valueOf(question.getAnswer()));
						wrongCount++;
						isCorrectPanel.setBackground(Color.RED);

					}
					question.setIsCorrect(isCorrect);

				} else if (isAnswered && round < selectedExerciseA.getQuestions().size() - 1) {
					answerLabel.setText("");
					questionDuration = elapsedTime - totalDuration;
					question.setDuration(questionDuration);
					totalDuration += questionDuration;
					answerButton.setText(answerText);
					isAnswered = !isAnswered;
					answerField.setEnabled(true);
					answerField.setText("");
					round++;
					question = selectedExerciseA.getQuestions().get(round);
					aLabel.setText(String.valueOf(question.getA()));
					bLabel.setText(String.valueOf(question.getB()));
					isCorrectPanel.setBackground(Color.WHITE);
					System.out.println(questionDuration);
				} else {
					isStart = false;
					questionDuration = elapsedTime - totalDuration;
					question.setDuration(questionDuration);
					totalDuration += questionDuration;
					selectedExerciseA.setDuration(totalDuration);
					selectedExerciseA.calculateScores();
					answerLabel.setText("");
					HighScoreTableModel scoreModel = new HighScoreTableModel(childUser.getEmail(),
							selectedExerciseA.getSpeedScore(), selectedExerciseA.getAccuracyScore());

					System.out.println(questionDuration);
					System.out.println("Toplam Süre:" + totalDuration);
					JOptionPane.showMessageDialog(null,
							"Tebrikler! Doğruluk Skoru: " + String.format("%.2f", selectedExerciseA.getAccuracyScore())
									+ " Hız Skoru: " + String.format("%.2f", selectedExerciseA.getSpeedScore()));
					for (Question que : selectedExerciseA.getQuestions()) {
						System.out.println(que);
					}
					isCorrectPanel.setBackground(Color.WHITE);
					resetTimer();
					totalDuration = 0;
					questionDuration = 0;
					if (!isFreeExercise) {
						selectedExerciseA.addDataToHighScoreTable(scoreModel);
						childUser.solveAnExercise(selectedExerciseA);
						updateFileByChild();
						updateCSV();
					}

					correctCount = 0;
					wrongCount = 0;
					round = 0;
					isAnswered = !isAnswered;
					answerButton.setText(answerText);
					answerField.setText("");
					if (childUser.getParent().getExercises().contains(selectedExerciseA)) {
						definedExercisesPanel.setVisible(true);
					} else {
						homePanel.setVisible(true);
					}
					answerField.setEnabled(true);
					startExercisePanel.setVisible(false);
					solvedExercisesCount.setText(String.valueOf(childUser.getSolvedExercises().size()));
					exercisesCount.setText(String.valueOf(childUser.getParent().getExercises().size()));
					ArrayList<Exercise> allExercises = childUser.getParent().getExercises();
					modelBoxModel.removeAllElements();
					for (Exercise exercise : allExercises) {
						System.out.println(exercise.getExerciseName());
						if (!exercise.isSolvedBy(childUser.getEmail())) {
							modelBoxModel.addElement(exercise);

						}
					}

				}
			}
		});
		nameField = new JTextField();
		nameField.setColumns(10);
		nameField.setBounds(47, 100, 136, 30);
		createExercisePanel.add(nameField);
		createExercisePanel.setVisible(false);
		createExercisePanel.setVisible(false);
		backButton.addActionListener(menuButtonListener);
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				v1Start.setText("");
				v2Start.setText("");
				v1End.setText("");
				v2End.setText("");
				questionCount.setText("");
				nameField.setText("");
			}
		});
		createExerciseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!nameField.getText().isEmpty() && !v1Start.getText().isEmpty() && !v2Start.getText().isEmpty()
						&& !v1End.getText().isEmpty() && !v2End.getText().isEmpty()
						&& !questionCount.getText().isEmpty()) {
					Exercise aExercise = new Exercise(nameField.getText(),
							new Option(Integer.parseInt(v1Start.getText()), Integer.parseInt(v1End.getText()),
									Integer.parseInt(v2Start.getText()), Integer.parseInt(v2End.getText()),
									Integer.parseInt(questionCount.getText())));
					parentUser.addExercise(aExercise);

					updateFileByParent();
					for (Exercise exercise : parentUser.getExercises()) {
						System.out.println(exercise.getExerciseName());
					}
					System.out.println(" !!!" + parentUser.getChildren().get(0).getParent().getExercises().size());
					JOptionPane.showMessageDialog(null, "Yeni alıştırma eklendi!");
					nameField.setText("");
					v1Start.setText("");
					v2Start.setText("");
					v1End.setText("");
					v2End.setText("");
					questionCount.setText("");
					tableBoxModel.addElement(aExercise);
					scoreTablePanel.setVisible(false);
					reportsPanel.setVisible(false);
					definedExercisesPanel.setVisible(false);
					homePanel.setVisible(true);
					createExercisePanel.setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Lütfen eksik alan bırakmayınız!");
				}

			}
		});

		showChild.setForeground(Color.WHITE);
		showChild.setFocusPainted(false);
		showChild.setBackground(new Color(0, 128, 128));
		showChild.setBounds(320, 67, 157, 33);
		reportsPanel.add(showChild);
		reportsPanel.setVisible(false);
		JComboBox<Child> childrenBox = new JComboBox<>(childrenBoxModel);
		childrenBox.setFont(new Font("Roboto Condensed", Font.PLAIN, 12));
		childrenBox.setSelectedItem(null);
		childrenBox.setBackground(Color.WHITE);
		childrenBox.setBounds(28, 67, 273, 33);
		reportsPanel.add(childrenBox);
		showChild.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				Child selectedChild = (Child) childrenBox.getSelectedItem();
				if (selectedChild != null) {
					Chart chart;
					chart = new Chart(selectedChild.getSolvedExercises(), selectedChild.getEmail());
					Chart.main(null);
				}
				else {
					JOptionPane.showMessageDialog(null, "Lütfen bir çocuk seçiniz!");
				}
			}
		});

	}

	private void startTimer() {
		isStart = true;
		Thread thread = new Thread() {
			public void run() {
				long startTime = System.currentTimeMillis();
				while (isStart) {
					try {
						TimeUnit.MILLISECONDS.sleep(1);
						long currentTime = System.currentTimeMillis();
						elapsedTime = currentTime - startTime;

						milliseconds = (int) (elapsedTime % 1000);
						seconds = (int) (elapsedTime / 1000) % 60;
						minutes = (int) (elapsedTime / (1000 * 60)) % 60;
						hours = (int) (elapsedTime / (1000 * 60 * 60));

						hour.setText(String.format("%02d", hours));
						minute.setText(String.format("%02d", minutes));
						second.setText(String.format("%02d", seconds));
						millisecond.setText(String.format("%02d", milliseconds));
						Thread.sleep(0);
					} catch (Exception e2) {
						System.out.println("something is wrong");
					}
				}
			}
		};
		thread.start();

	}

	private static void resetTimer() {
		hours = 0;
		minutes = 0;
		seconds = 0;
		milliseconds = 0;
		updateTimerUI();
	}

	public static void updateFileByParent() { // when exercise is added
		try {
			String fileName = "info.dat";
			FileInputStream fileInputStream = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			Integer parentCount = (Integer) objectInputStream.readObject();
			ArrayList<Parent> parents = new ArrayList<>();
			for (int i = 0; i < parentCount; i++) {
				Parent parent = (Parent) objectInputStream.readObject();
				if (parent.getEmail().equals(parentUser.getEmail())) {
					parent.setExercises(parentUser.getExercises());
				}
				parents.add(parent);
			}

			Integer childrenCount = (Integer) objectInputStream.readObject();
			ArrayList<Child> children = new ArrayList<>();
			for (int i = 0; i < childrenCount; i++) {
				Child child = (Child) objectInputStream.readObject();
				children.add(child);
			}

			objectInputStream.close();

			ObjectOutputStream yazici = new ObjectOutputStream(new FileOutputStream(fileName));
			yazici.writeObject(parentCount);
			for (Parent parent : parents) {
				yazici.writeObject(parent);
			}

			yazici.writeObject(childrenCount);
			for (Child child : children) {
				yazici.writeObject(child);
			}
			yazici.close();

			System.out.println("The information has been successfully updated in file " + fileName);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateFileByChild() { // when exercise is solved	
		try {
			String fileName = "info.dat";
			FileInputStream fileInputStream = new FileInputStream(fileName);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			Integer parentCount = (Integer) objectInputStream.readObject();
			ArrayList<Parent> parents = new ArrayList<>();
			for (int i = 0; i < parentCount; i++) {
				Parent parent = (Parent) objectInputStream.readObject();
				if (childUser.getParent().getEmail().equals(parent.getEmail())) {
					parent.setExercises(childUser.getParent().getExercises());
				}
				parents.add(parent);
			}

			Integer childrenCount = (Integer) objectInputStream.readObject();
			ArrayList<Child> children = new ArrayList<>();
			for (int i = 0; i < childrenCount; i++) {
				Child child = (Child) objectInputStream.readObject();
				if (child.getEmail().equals(childUser.getEmail())) {
					child.setSolvedExercises(childUser.getSolvedExercises());
				}
				children.add(child);
			}

			objectInputStream.close();

			ObjectOutputStream yazici = new ObjectOutputStream(new FileOutputStream(fileName));
			yazici.writeObject(parentCount);
			for (Parent parent : parents) {
				yazici.writeObject(parent);
			}

			yazici.writeObject(childrenCount);
			for (Child child : children) {
				yazici.writeObject(child);
			}
			yazici.close();

			System.out.println("The information has been successfully updated in file " + fileName);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void updateTimerUI() {
		String hourText = String.format("%02d", hours);
		String minuteText = String.format("%02d", minutes);
		String secondText = String.format("%02d", seconds);
		String millisecondText = String.format("%03d", milliseconds);
		hour.setText(hourText);
		minute.setText(minuteText);
		second.setText(secondText);
		millisecond.setText(millisecondText);
	}
}
