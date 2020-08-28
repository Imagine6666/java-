package application.view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

import application.Main;
import application.model.Doctor;
import application.model.Patient;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class LoginController {
	@FXML
	private TextField tf_code;
	@FXML
	private PasswordField tf_password;
	@FXML
	private ChoiceBox loginType;
	String loginIdenty = "����";

	private Main main;
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize() {
		File file = new File("my.ini");
		//�Ƿ����
		if(file.exists()){
			try (FileInputStream fis = new FileInputStream(file)) {//�ļ������� �����ֽ���
			
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
				//inputstreamReader��һ���ֽ��������ֽ������ַ���ת����ʱ�򣬾���Ҫ�ƶ�һ�����뷽ʽ����Ȼ�ͻ�����
				BufferedReader br = new BufferedReader(isr);//�ַ�������
				
				tf_code.setText(br.readLine());
				tf_password.setText(br.readLine());
				if("1".equals(br.readLine())) {
					loginType.getValue().equals("ҽ��");
				}
				else loginType.getValue().equals("����");
				
				br.close();//��󽫸����̹߳ر�
				isr.close();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void changeSelected(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) 
		{
			boolean b = loginType.getValue().equals("ҽ��");
			if(b) loginIdenty="ҽ��";
		}
	}
	
	@FXML
	private void trylogin(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) 
		{
			login(null);
		}
	}

	@FXML
	public void onEnter() {
		login(null);
	}

	// Event Listener on Button.onMouseClicked
	/**
	 * ��ť���µ�ʱ�򣬴����ĺ���
	 */
	@FXML
	private void login(MouseEvent event) {
		
		String acount = tf_code.getText();

		String password = tf_password.getText();
		

		PreparedStatement psmt;
		if (loginType.getValue().equals("ҽ��"))
			{
			System.out.println("ҽ��");

			String sql = "select * from dbo.T_KSYS where YSBH = ?";
			try {

				psmt = Main.con.prepareStatement(sql);
				psmt.setString(1, acount);
				// ִ��SQL���
				ResultSet rs = psmt.executeQuery();

				while (rs.next()) {
					if (rs.getString("DLKL").equals(password)) {
						// ��¼�ɹ�
						File newfile = new File("my.ini");
					    FileOutputStream fos = new FileOutputStream(newfile);//��������ļ������ڻ��Զ������ļ�
					    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//�Ͷ�ȡһ��������ת�������ֽں��ַ���
					    BufferedWriter bw = new BufferedWriter(osw);//������д�뻺����
	
					    bw.write(tf_code.getText() + "\n");//д���ַ���
					    bw.write(tf_password.getText() + "\n");//д���ַ���
					    bw.write(1 + "\n");//д���Ƿ�ѡ��
	
					    bw.close();//������һ�� �����򿪵��ȹر� �ȴ򿪵ĺ�ر�
					    osw.close();
					    fos.close();
					    System.out.println("�ļ��Ѿ��޸�");
					    
						// �޸ĵ�¼����
						String date = Main.dateFormat.format(new Date());
						String sqlupdate="update dbo.T_KSYS set DLRQ = ? where YSBH = ?";
				        PreparedStatement psmtupdate = Main.con.prepareStatement(sqlupdate);
				        psmtupdate.setString(1, date);
				        psmtupdate.setString(2, acount);
				        psmtupdate.execute();
				        
				        
						main.d = new Doctor(acount, rs.getString("KSBH"), rs.getString("YSMC"), rs.getString("PYZS"),
								password, rs.getBoolean("SFZJ"), date);
						main.showDoctorview();
						System.out.println("����ҽ������!");
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "��Ż��ߵ�¼�������!", "��Ϣ��ʾ", 0);
				tf_code.setStyle("-fx-background-color: pink;");
				tf_password.setStyle("-fx-background-color: pink;");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else {
			System.out.println("����");

			String sql = "select * from dbo.T_BRXX where BRBH = ?";
			try {

				psmt = Main.con.prepareStatement(sql);
				psmt.setString(1, acount);
				// ִ��SQL���
				ResultSet rs = psmt.executeQuery();

				while (rs.next()) {
//					System.out.println(rs.getString("DLKL"));
					if (rs.getString("DLKL").trim().equals(password)) {
						// ��¼�ɹ�
						File newfile = new File("my.ini");
					    FileOutputStream fos = new FileOutputStream(newfile);//��������ļ������ڻ��Զ������ļ�
					    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//�Ͷ�ȡһ��������ת�������ֽں��ַ���
					    BufferedWriter bw = new BufferedWriter(osw);//������д�뻺����
						bw.write(tf_code.getText() + "\n");//д���ַ���
						bw.write(tf_password.getText() + "\n");//д���ַ���
						bw.write(0 + "\n");//д���Ƿ�ѡ��

						bw.close();//������һ�� �����򿪵��ȹر� �ȴ򿪵ĺ�ر�
						osw.close();
						fos.close();
					    System.out.println("�ļ��Ѿ��޸�");
						// �޸ĵ�¼����
						String date = Main.dateFormat.format(new Date());
						String sqlupdate="update dbo.T_BRXX set DLRQ = ? where BRBH = ?";
				        PreparedStatement psmtupdate = Main.con.prepareStatement(sqlupdate);
				        psmtupdate.setString(1, date);
				        psmtupdate.setString(2, acount);
				        psmtupdate.execute();
				        
						main.p = new Patient(acount, rs.getString("BRMC"), password, rs.getDouble("YCJE"), date);
						main.showPatientrview();
						System.out.println("���벡�˽���!");
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "��Ż��ߵ�¼�������!", "��Ϣ��ʾ", 0);
				tf_code.setStyle("-fx-background-color: pink;");
				tf_password.setStyle("-fx-background-color: pink;");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void setMain(Main main) {
		this.main = main;
	}
	
	
}
