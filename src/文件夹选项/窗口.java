package �ļ���ѡ��;

import java.awt.BorderLayout;
import javax.swing.filechooser.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.CardLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JLabel;
import java.awt.Font;

public class ���� extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	public static String s;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					���� frame = new ����();
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
	public ����() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(225, 122, 217, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("���");
		btnNewButton.setBounds(122, 122, 77, 21);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser =new JFileChooser();//�����ļ��жԻ���
				//��ʾ�ļ��жԻ���
				int i=filechooser.showOpenDialog(getContentPane());
				//�ж��û��������Ƿ�Ϊ�򿪰�ť
				if(i==JFileChooser.APPROVE_OPTION) {
					//���ѡ�е��ļ�����
					File selectedFile =filechooser.getSelectedFile();
					textField.setText(selectedFile.getAbsolutePath());
					s=textField.getText();
				}
				
			}
		});
		contentPane.add(btnNewButton);
		
		
		JLabel label = new JLabel("\u8BF7\u9009\u62E9\u5408\u9002\u7684\u6570\u636E\u5BFC\u5165\uFF1A");
		label.setFont(new Font("����", Font.PLAIN, 15));
		label.setBounds(77, 38, 180, 32);
		contentPane.add(label);
		
		JButton button = new JButton("\u9884\u5904\u7406");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				���� julei =new ����();
			    julei.show();
				julei.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				julei.setBounds(100, 100, 600, 300);
				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				contentPane.setLayout(null);
				Excel.main(null);;
				Kmeans.main(null);
			}
		});
		button.setBounds(122, 207, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u9000\u51FA");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		button_1.setBounds(349, 207, 93, 23);
		contentPane.add(button_1);
		
		
	}
}
