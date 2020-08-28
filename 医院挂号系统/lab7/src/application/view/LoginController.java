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
	String loginIdenty = "患者";

	private Main main;
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
	@FXML
	private void initialize() {
		File file = new File("my.ini");
		//是否存在
		if(file.exists()){
			try (FileInputStream fis = new FileInputStream(file)) {//文件输入流 这是字节流
			
				InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
				//inputstreamReader是一个字节流，将字节流和字符流转化的时候，就需要制定一个编码方式，不然就会乱码
				BufferedReader br = new BufferedReader(isr);//字符缓冲区
				
				tf_code.setText(br.readLine());
				tf_password.setText(br.readLine());
				if("1".equals(br.readLine())) {
					loginType.getValue().equals("医生");
				}
				else loginType.getValue().equals("患者");
				
				br.close();//最后将各个线程关闭
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
			boolean b = loginType.getValue().equals("医生");
			if(b) loginIdenty="医生";
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
	 * 按钮按下的时候，触发的函数
	 */
	@FXML
	private void login(MouseEvent event) {
		
		String acount = tf_code.getText();

		String password = tf_password.getText();
		

		PreparedStatement psmt;
		if (loginType.getValue().equals("医生"))
			{
			System.out.println("医生");

			String sql = "select * from dbo.T_KSYS where YSBH = ?";
			try {

				psmt = Main.con.prepareStatement(sql);
				psmt.setString(1, acount);
				// 执行SQL语句
				ResultSet rs = psmt.executeQuery();

				while (rs.next()) {
					if (rs.getString("DLKL").equals(password)) {
						// 登录成功
						File newfile = new File("my.ini");
					    FileOutputStream fos = new FileOutputStream(newfile);//这里如果文件不存在会自动创建文件
					    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//和读取一样这里是转化的是字节和字符流
					    BufferedWriter bw = new BufferedWriter(osw);//这里是写入缓冲区
	
					    bw.write(tf_code.getText() + "\n");//写入字符串
					    bw.write(tf_password.getText() + "\n");//写入字符串
					    bw.write(1 + "\n");//写入是否选中
	
					    bw.close();//和上面一样 这里后打开的先关闭 先打开的后关闭
					    osw.close();
					    fos.close();
					    System.out.println("文件已经修改");
					    
						// 修改登录日期
						String date = Main.dateFormat.format(new Date());
						String sqlupdate="update dbo.T_KSYS set DLRQ = ? where YSBH = ?";
				        PreparedStatement psmtupdate = Main.con.prepareStatement(sqlupdate);
				        psmtupdate.setString(1, date);
				        psmtupdate.setString(2, acount);
				        psmtupdate.execute();
				        
				        
						main.d = new Doctor(acount, rs.getString("KSBH"), rs.getString("YSMC"), rs.getString("PYZS"),
								password, rs.getBoolean("SFZJ"), date);
						main.showDoctorview();
						System.out.println("进入医生界面!");
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "编号或者登录口令错误!", "消息提示", 0);
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
			System.out.println("病人");

			String sql = "select * from dbo.T_BRXX where BRBH = ?";
			try {

				psmt = Main.con.prepareStatement(sql);
				psmt.setString(1, acount);
				// 执行SQL语句
				ResultSet rs = psmt.executeQuery();

				while (rs.next()) {
//					System.out.println(rs.getString("DLKL"));
					if (rs.getString("DLKL").trim().equals(password)) {
						// 登录成功
						File newfile = new File("my.ini");
					    FileOutputStream fos = new FileOutputStream(newfile);//这里如果文件不存在会自动创建文件
					    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");//和读取一样这里是转化的是字节和字符流
					    BufferedWriter bw = new BufferedWriter(osw);//这里是写入缓冲区
						bw.write(tf_code.getText() + "\n");//写入字符串
						bw.write(tf_password.getText() + "\n");//写入字符串
						bw.write(0 + "\n");//写入是否选中

						bw.close();//和上面一样 这里后打开的先关闭 先打开的后关闭
						osw.close();
						fos.close();
					    System.out.println("文件已经修改");
						// 修改登录日期
						String date = Main.dateFormat.format(new Date());
						String sqlupdate="update dbo.T_BRXX set DLRQ = ? where BRBH = ?";
				        PreparedStatement psmtupdate = Main.con.prepareStatement(sqlupdate);
				        psmtupdate.setString(1, date);
				        psmtupdate.setString(2, acount);
				        psmtupdate.execute();
				        
						main.p = new Patient(acount, rs.getString("BRMC"), password, rs.getDouble("YCJE"), date);
						main.showPatientrview();
						System.out.println("进入病人界面!");
						return;
					}
				}
				JOptionPane.showMessageDialog(null, "编号或者登录口令错误!", "消息提示", 0);
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
