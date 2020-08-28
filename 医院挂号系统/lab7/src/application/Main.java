package application;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import application.model.Doctor;
import application.model.Patient;
import application.view.DoctorController;
import application.view.LoginController;
import application.view.PatientController;
import application.view.TableDoctorController;
import application.view.TableRegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author DYD
 *
 */
public class Main extends Application {

	public static java.sql.Connection con;// ���ݿ⽨��������
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// ���Է�����޸����ڸ�ʽ
	public static Stage primaryStage; // ������stage
	public static Doctor d;
	public static Patient p;
	private BorderPane doctorLayout;

	/**
	 *  ���������ر����ݿ�����
	 */
	public static void main(String[] args) {
		launch(args);
		try {
			if(con==null) return;
			if (!con.isClosed()) {
				con.close();
				System.out.println("�Ͽ����ݿ�����!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ��ʾҽ��������
	 */
	public void showDoctorview() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/doctor.fxml"));
			doctorLayout = (BorderPane) loader.load();

			// Show the scene containing the login layout.
			Scene scene = new Scene(doctorLayout);
			primaryStage.close();
			primaryStage.setScene(scene);
			primaryStage.show();
			
			DoctorController controller = loader.getController();
            controller.setMain(this);
            controller.registertable(null);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʾ��¼������
	 */
	public void showLoginview() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/login.fxml"));
			AnchorPane loginLayout = (AnchorPane) loader.load();

			// Show the scene containing the login layout.
			Scene scene = new Scene(loginLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			LoginController controller = loader.getController();
            controller.setMain(this);
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʾ���˽���
	 */
	public void showPatientrview() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/patient.fxml"));
			SplitPane patientLayout = (SplitPane) loader.load();

			// Show the scene containing the login layout.
			Scene scene = new Scene(patientLayout);
			primaryStage.close();
			primaryStage.setScene(scene);
			primaryStage.show();
			
			PatientController controller = loader.getController();
			controller.setMain(this);
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ��ʾ�Һ��б����
	 */
	public void tableRegisterLoad() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/tableregister.fxml"));
			AnchorPane RELayout = (AnchorPane) loader.load();
			
			TableRegisterController controller = loader.getController();
            controller.setMain(this);
            
			doctorLayout.setCenter(RELayout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ʾ�����б����
	 */
	public TableDoctorController tableIncomeLoad() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/tabledoctor.fxml"));
			AnchorPane DocLayout = (AnchorPane) loader.load();
			
			TableDoctorController controller = loader.getController();
            
			doctorLayout.setCenter(DocLayout);
			return controller;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	/**
	 * ��ʼ�����ݿ����ӣ�Ӧ�ùرյ�ʱ���ͷ�
	 */
	public void sql_initial() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String strCon = "jdbc:sqlserver://127.0.0.1:1433;databaseName=javalab2";
			String strUserName = "sa"; // ���ݿ���û�����
			String strPWD = "820456"; // ���ݿ������
			con = java.sql.DriverManager.getConnection(strCon, strUserName, strPWD);
			if(con==null) {
				System.out.println("���ݿ�����ʧ��!");
				primaryStage.close();
			}
			System.out.println("��˳�����ӵ����ݿ⡣");
		}
		// �����쳣�����д���
		catch (java.lang.ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		} catch (java.sql.SQLException se) {
			System.out.println(se.getMessage());
		}
	}

	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage = primaryStage;
		Main.primaryStage.setTitle("ҽԺ�Һ�ϵͳ");
		sql_initial();
		showLoginview();
	}

}
