package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controller.AES_Controller;
import Model.AES_Program;

public class AES_Views extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public AES_Program model;
	public JRadioButton rdbtn_128bit;
	public JRadioButton rdbtn_192bit;
	public JRadioButton rdbtn_256bit;
	public ButtonGroup btnGroup;
	public JPanel panelResult;
	public JPanel panelEncrypt;
	public JLabel lblBanRoEn;
	public JTextField jTextField_BanRoEn;
	public JTextField jTextField_BanMaEn;
	public JLabel lblNewLabel_1;
	public JLabel lbl_TimeEn;
	public JLabel lblNewLabel_3;
	public JPanel panelDecrypt;
	public JTextField jTextField_BanMaDe;
	public JTextField jTextField_BanRoDe;
	public JComponent lblNewLabel_4;
	public JLabel lbl_TimeDe;
	public JLabel lblNewLabel_6;
	public JPanel panelAction;
	public JButton btnEncrypt;
	public JButton btnDecrypt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AES_Views frame = new AES_Views();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AES_Views() {
		this.model = new AES_Program();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 843, 577);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("AES");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 36));
		lblNewLabel.setForeground(new Color(0, 128, 255));
		lblNewLabel.setBounds(296, 10, 235, 84);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(10, 100, 809, 424);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel panel_RadioButton = new JPanel();
		panel_RadioButton.setLayout(new GridLayout(1, 3));
		panel.add(panel_RadioButton, BorderLayout.NORTH);
		rdbtn_128bit = new JRadioButton("128 bit");
		rdbtn_128bit.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_128bit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtn_128bit.setBounds(146, 13, 103, 21);

		rdbtn_192bit = new JRadioButton("192 bit");
		rdbtn_192bit.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_192bit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtn_192bit.setBounds(362, 13, 103, 21);

		rdbtn_256bit = new JRadioButton("256 bit");
		rdbtn_256bit.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtn_256bit.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdbtn_256bit.setBounds(567, 13, 103, 21);

		btnGroup = new ButtonGroup();
		btnGroup.add(rdbtn_128bit);
		btnGroup.add(rdbtn_192bit);
		btnGroup.add(rdbtn_256bit);
		panel_RadioButton.add(rdbtn_128bit);
		panel_RadioButton.add(rdbtn_192bit);
		panel_RadioButton.add(rdbtn_256bit);

		panelResult = new JPanel();
		panelResult.setLayout(new GridLayout(1, 2));
		panel.add(panelResult, BorderLayout.CENTER);

		panelEncrypt = new JPanel();
		panelResult.add(panelEncrypt);
		panelEncrypt.setLayout(null);

		lblBanRoEn = new JLabel("Bản rõ");
		lblBanRoEn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBanRoEn.setBounds(10, 23, 74, 31);
		panelEncrypt.add(lblBanRoEn);

		jTextField_BanRoEn = new JTextField();
		jTextField_BanRoEn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		jTextField_BanRoEn.setBounds(94, 23, 300, 31);
		panelEncrypt.add(jTextField_BanRoEn);
		jTextField_BanRoEn.setColumns(10);

		JLabel lblBanMaEn = new JLabel("Bản mã");
		lblBanMaEn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBanMaEn.setBounds(10, 156, 74, 31);
		panelEncrypt.add(lblBanMaEn);

		jTextField_BanMaEn = new JTextField();
		jTextField_BanMaEn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		jTextField_BanMaEn.setColumns(10);
		jTextField_BanMaEn.setBounds(94, 156, 300, 31);
		panelEncrypt.add(jTextField_BanMaEn);

		lblNewLabel_1 = new JLabel("Thời gian: ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 260, 90, 31);
		panelEncrypt.add(lblNewLabel_1);

		lbl_TimeEn = new JLabel("Time");
		lbl_TimeEn.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_TimeEn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_TimeEn.setBounds(110, 260, 169, 31);
		panelEncrypt.add(lbl_TimeEn);

		lblNewLabel_3 = new JLabel("s");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(289, 260, 45, 31);
		panelEncrypt.add(lblNewLabel_3);

		panelDecrypt = new JPanel();
		panelResult.add(panelDecrypt);
		panelDecrypt.setLayout(null);

		JLabel lblBanMaDe = new JLabel("Bản mã");
		lblBanMaDe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBanMaDe.setBounds(10, 22, 74, 31);
		panelDecrypt.add(lblBanMaDe);

		jTextField_BanMaDe = new JTextField();
		jTextField_BanMaDe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		jTextField_BanMaDe.setColumns(10);
		jTextField_BanMaDe.setBounds(94, 22, 300, 31);
		panelDecrypt.add(jTextField_BanMaDe);

		JLabel lblBanRoDe = new JLabel("Bản rõ");
		lblBanRoDe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblBanRoDe.setBounds(10, 156, 74, 31);
		panelDecrypt.add(lblBanRoDe);

		jTextField_BanRoDe = new JTextField();
		jTextField_BanRoDe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		jTextField_BanRoDe.setColumns(10);
		jTextField_BanRoDe.setBounds(94, 156, 300, 31);
		panelDecrypt.add(jTextField_BanRoDe);

		lblNewLabel_4 = new JLabel("Thời gian: ");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(10, 258, 90, 31);
		panelDecrypt.add(lblNewLabel_4);

		lbl_TimeDe = new JLabel("Time");
		lbl_TimeDe.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl_TimeDe.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl_TimeDe.setBounds(110, 258, 169, 31);
		panelDecrypt.add(lbl_TimeDe);

		lblNewLabel_6 = new JLabel("s");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_6.setBounds(289, 258, 45, 31);
		panelDecrypt.add(lblNewLabel_6);

		panelAction = new JPanel();
		panelAction.setPreferredSize(new Dimension(10, 60));
		panelAction.setLayout(new GridLayout(1, 2));
		panel.add(panelAction, BorderLayout.SOUTH);

		ActionListener action = new AES_Controller(this);

		btnEncrypt = new JButton("Mã hóa");
		btnEncrypt.setFont(new Font("Tahoma", Font.PLAIN, 22));
		panelAction.add(btnEncrypt);
		btnEncrypt.addActionListener(action);

		btnDecrypt = new JButton("Giải mã");
		btnDecrypt.setFont(new Font("Tahoma", Font.PLAIN, 22));
		panelAction.add(btnDecrypt);
		btnDecrypt.addActionListener(action);

		this.setVisible(true);
	}

}
