package application.view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import application.Main;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PatientController {
	@FXML
	private TextField lb_nameKS;
	@FXML
	private TextField lb_type;
	@FXML
	private TextField lb_moneyRequire;
	@FXML
	private TextField lb_moneyPay;
	@FXML
	private TextField lb_moneyReturn;
	@FXML
	private TextField lb_moneyYC;
	@FXML
	private TextField lb_nameHZ;
	@FXML
	private TextField lb_nameD;
	@FXML
	private TextField lb_code;
	@FXML
	private Button btn_register;
	@FXML
	private Button btn_clear;
	@FXML
	private Button btn_exit;
	@FXML
	private Button btn_regret;
	@FXML
	private Label lb_patientname;

	private AutoCompleteTextField autoNameKS, autoNameHZ, autoNameD, autoType;
	
	private Main main;
	
	int a;

	@FXML //���������
	private void suit() {
		changeNameD();
		changeType();
		changeNameHZ();
	}
	
	@FXML //������̼��
	private void keysuit(KeyEvent event) {//���̼������enter
		if(event.getCode() == KeyCode.ENTER  || event.getCode() == KeyCode.ALT) 
		{
			changeNameD();
			changeType();
			changeNameHZ();
		}
	}
	
	@FXML
	private void keyGH(KeyEvent event)
	{
		if(event.getCode() == KeyCode.ENTER) 
		{
			GH(null);
		}
	}
	
	@FXML
	private void keyclear(KeyEvent event)
	{
		if(event.getCode() == KeyCode.ENTER) 
		{
			clearAll(null);
		}
	}

	@FXML
	private void keyexit(KeyEvent event)
	{
		if(event.getCode() == KeyCode.ENTER) 
		{
			exitnow(null);
		}
	}
	@FXML
	private void keyback(KeyEvent event)
	{
		if(event.getCode() == KeyCode.ENTER)
		{
			mouseback(null);
		}
	}
	@FXML
	private void keyRegret(KeyEvent event)
	{
		if(event.getCode() == KeyCode.ENTER)
		{
			mouseRegret();
		}
	}
	

	/**
	 * �ı�ҽ�����Ƶĺ�ѡ��
	 */
	@FXML
	private void changeNameD() {
		try {
			if(lb_nameKS==null) return;
			if(lb_nameKS.getText()==null||lb_nameKS.getText().equals("")) {
				return;
			}
			String sqlks = "select dbo.T_KSYS.YSMC,dbo.T_KSYS.YSBH,dbo.T_KSYS.PYZS  from dbo.T_KSXX, dbo.T_KSYS"
					+ " where dbo.T_KSXX.KSMC = ? AND"
					+ "  dbo.T_KSXX.KSBH = dbo.T_KSYS.KSBH ";
			PreparedStatement stmt = Main.con.prepareStatement(sqlks);
			String[] aar=lb_nameKS.getText().split("\\s+");
			stmt.setString(1, aar[1]);// ���ÿ�������
			ResultSet rd = stmt.executeQuery();
			
			//String nameD;
			List<String> nameDlist = new ArrayList<String>();
			while (rd.next()) {
				// ��ȡҽ�����ƺͿ���
				String str1 = rd.getString("YSBH").trim();
				String str2 = rd.getString("YSMC").trim();
				String str3 = rd.getString("PYZS").trim();
				String togetherStr = str1 + " " + str2 + " " + str3;
				//searchResult.add(togetherStr);
				//nameD = rd.getString("nameD");
				nameDlist.add(togetherStr);
			}
			
			autoNameD.setCacheDataList(nameDlist);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ı�������Ƶĺ�ѡ��
	 */
	@FXML
	private void changeNameHZ() {
		try {
			if(lb_type==null || lb_nameKS==null) return;
			if(lb_type.getText()==null||lb_type.getText().equals("")) {
				return;
			}
			if(lb_nameKS.getText()==null || lb_nameKS.getText().equals("")) {
				return;
			}
//			if(lb_nameHZ.getText()!=null) return;
			
			//String nameHZ;
			List<String> nameHZlist = new ArrayList<String>();
			
			boolean isexpert = lb_type.getText().equals("ר�Һ�")? true: false;	
			int cost=0;
			
			String sqlks = "select dbo.T_HZXX.HZMC,dbo.T_HZXX.HZBH,dbo.T_HZXX.PYZS,GHFY from dbo.T_KSXX, dbo.T_HZXX"
					+ " where dbo.T_KSXX.KSMC = ?"
					+ " AND dbo.T_HZXX.KSBH = dbo.T_KSXX.KSBH"
					+ " AND dbo.T_HZXX.SFZJ = ? ";
			PreparedStatement stmt = Main.con.prepareStatement(sqlks);
			String[] aar=lb_nameKS.getText().split("\\s+");
			stmt.setString(1, aar[1]);//���ÿ�������
			stmt.setBoolean(2, isexpert);//�����Ƿ�ר��
			
			ResultSet rhz = stmt.executeQuery();
	        
			while(rhz.next()) {
				String str1 = rhz.getString("HZBH").trim();
				String str2 = rhz.getString("HZMC").trim();
				String str3 = rhz.getString("PYZS").trim();
				String togetherStr = str1 + " " + str2+" "+str3 ;
				nameHZlist.add(togetherStr);
				cost = (int)Double.parseDouble(rhz.getString("GHFY"));
			}
			lb_moneyRequire.setText(cost + "");
			
			//Ԥ�����㹻
			if(Integer.parseInt(lb_moneyYC.getText())>cost)
			{
				lb_moneyPay.setEditable(false);
			}
			else {
				lb_moneyPay.setEditable(true);
			}
			autoNameHZ.setCacheDataList(nameHZlist);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * �ı�������ĺ�ѡ��
	 */
	@FXML
	private void changeType() {
		if(lb_nameD==null) return;
		if(lb_nameD.getText()==null || lb_nameD.getText().equals("")) {
			return;
		}
//		if(lb_type.getText()!=null) return;
		List<String> typelist = new ArrayList<String>();
		typelist.clear();
		

		boolean isexpert = false;
		// ��ѯ����ҽ����
		try {
			String sqld = "select * from dbo.T_KSYS where YSMC = ? ";
			PreparedStatement psmtd = Main.con.prepareStatement(sqld);
			String[] aar=lb_nameD.getText().split("\\s+");
			psmtd.setString(1, aar[1]);// ����ҽ������
			ResultSet rd = psmtd.executeQuery();
			while (rd.next()) {
				isexpert = rd.getBoolean("SFZJ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (isexpert) {
			typelist.add("ר�Һ�");
			typelist.add("��ͨ��");
		} else {
			typelist.add("��ͨ��");
		}

		autoType.setCacheDataList(typelist);

	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		lb_patientname.setText(Main.p.getName());
		btn_regret.setFocusTraversable(false);
		autoCompleteInitialize();
		lb_moneyYC.setText("0");
		// �ȳ�ʼ��Ԥ����
		try {
			String sql = "select * from dbo.T_BRXX where BRBH = ?;";
			PreparedStatement psmt = Main.con.prepareStatement(sql);
			psmt.setString(1, Main.p.getAcount());
			ResultSet rs = psmt.executeQuery();

			while (rs.next()) {
				lb_moneyYC.setText(rs.getInt("YCJE") + "");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void autoCompleteInitialize() {
		autoNameKS = AutoCompleteTextFieldBuilder.build(lb_nameKS);
		autoNameHZ = AutoCompleteTextFieldBuilder.build(lb_nameHZ);
		autoNameD = AutoCompleteTextFieldBuilder.build(lb_nameD);
		autoType = AutoCompleteTextFieldBuilder.build(lb_type);
		try {
			String nameKS;// ������Ϣ
			List<String> nameKSlist = new ArrayList<String>();

			Statement stmt = Main.con.createStatement();
			// ��ѯ������Ϣ��
			ResultSet rks = stmt.executeQuery("select * from dbo.T_KSXX order by dbo.T_KSXX.KSBH ASC ;");

			while (rks.next()) {
				// ��ȡҽ�����ƺͿ���
				nameKS = rks.getString("KSMC").trim();
				String numKS=rks.getString("KSBH").trim();
				String res=numKS+" "+nameKS;
				nameKSlist.add(res);
			}

			autoNameKS.setCacheDataList(nameKSlist);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Event Listener on Button[#btn_register].onMouseClicked
	/**
	 * 1.��ȡ[���˱��] 2.��ҽ������,��ѯ����ҽ������ȡ[ҽ�����] 3.�������ƼӺ�����𣬲�ѯ������Ϣ����ȡ[���ֱ��],[�Һŷ���]
	 * 4.���ֱ�ţ���ѯ�Һ���Ϣ��ͳ��[�Һ��˴�]
	 */
	/**
	 * @param event
	 */
	@FXML
	public void GH(MouseEvent event) {
		try {
			String codeP, codeD = "", codeHZ = "", codeKS="";
			String nameHZ = lb_nameHZ.getText().split("\\s+")[1], nameKS="";
			int num_GH = 1, cost = 0, limit = 0;
			boolean typeHZ;
			if (lb_type.getText().equals("ר�Һ�"))
				typeHZ = true;
			else
				typeHZ = false;
			codeP = main.p.getAcount();// ��ȡ���˱��

			String sql;
			PreparedStatement psmt;
			ResultSet rs;
			
			//��ȡ������Ϣ
			sql = "select * from dbo.T_HZXX where HZMC = ? AND SFZJ = ?";
			psmt = Main.con.prepareStatement(sql);
			psmt.setString(1, nameHZ);// ���ú�������
			psmt.setBoolean(2, typeHZ);// ���ú�������
			rs = psmt.executeQuery();
			while (rs.next()) {
				// ��ȡ[���ֱ��],[�Һŷ���]
				codeHZ = rs.getString("HZBH");
				cost = rs.getInt("GHFY");
				limit = rs.getInt("GHRS");//�Һ���������
			}
			
			//���㴦��
			boolean mYcChange = false;
			int mYC = Integer.parseInt(lb_moneyYC.getText());//�϶���Ϊ��
			int mPay = lb_moneyPay.getText().equals("")? 0: Integer.parseInt(lb_moneyPay.getText());
			int mRe = lb_moneyRequire.getText().equals("")? 0: Integer.parseInt(lb_moneyRequire.getText());
			int mReturn = -1;
			
			if(cost != mRe) {
				//�������ʾ
				lb_moneyRequire.setText(cost+"");
				JOptionPane.showMessageDialog(null, 
						"����Ѿ������˱��\n" + mRe + "Ԫ->" + cost + "Ԫ\n ������ȷ��.", 
						"Sorry!", 0);
				return;
			}
			
			//1.Ԥ�����㹻������֧��
			//2.Ԥ����㣬��Ҫ֧��
			//3.û��Ԥ�����ȫ֧��
			if(mYC >= mRe) { //Ԥ�������Ҫ����
				mYC -= mRe;
				mYcChange = true;//�������ݿ�
			}
			else if(mYC != 0 && (mYC + mPay >= mRe)) {//Ԥ�����

				mYcChange = true;//�������ݿ�
				mReturn = mYC + mPay - mRe;
				mYC=0;
			}
			else if((mYC == 0) && mPay >= mRe) {//Ԥ����û�У���Ҫ֧��
				mReturn = mPay - mRe;
			}
			else { //����
				JOptionPane.showMessageDialog(null, "���㣬������֧��", "Sorry!", 0);
				return;
			}
			
			//��ѯ���չ�����ŵ��˴�
			sql = "select count(*) nums from dbo.T_GHXX where HZBH = ?"
					+ " AND cast(RQSJ as date)  = cast(? as date)";
			psmt = Main.con.prepareStatement(sql);
			psmt.setString(1, codeHZ);// ���ú��ֱ��
			psmt.setString(2, Main.dateFormat.format(new Date()));// ��������
			rs = psmt.executeQuery();
			while (rs.next()) {
				num_GH = rs.getInt("nums");
			}
			if(limit < num_GH) {//������������
				JOptionPane.showMessageDialog(null, 
						"�ú����Ѿ��ﵽ���պ�������"+limit+"\n�������ٽ��г���!", "�Բ���", 0);
				return;
			}
			num_GH++;//���ڹҺ��˴�
			
			//��ѯҽ��������Ϣ
			sql = "select dbo.T_KSYS.YSBH codeD"
					+ " from dbo.T_KSYS, dbo.T_KSXX where YSMC = ?"
					+ " AND dbo.T_KSXX.KSBH = dbo.T_KSYS.KSBH";
			psmt = Main.con.prepareStatement(sql);
			psmt.setString(1, lb_nameD.getText().split("\\s+")[1]);// ����ҽ������
			rs = psmt.executeQuery();
			while (rs.next()) {
				// ��ȡҽ����ţ���������
				codeD = rs.getString("codeD");
			}
			
			//�ܹҺű��
			sql = "select count(*) I from dbo.T_GHXX";
			psmt = Main.con.prepareStatement(sql);
			rs = psmt.executeQuery();
			int rows = 1;
			while (rs.next()) {
				rows = Integer.parseInt(rs.getString("I")) ;
			}
			rows++;
			
			sql = "insert into dbo.T_GHXX" + "(GHBH, HZBH, YSBH, BRBH, GHRC, THBZ, GHFY, RQSJ) "
					+ "values(?,?,?,?,?,?,?,?)";// ������?��ʾ���൱��ռλ��;
			psmt = Main.con.prepareStatement(sql);

			// �ȶ�ӦSQL��䣬��SQL��䴫�ݲ���
			String time = Main.dateFormat.format(new Date());
			String num=null;
			num = Integer.toString(rows);
			while(num.length()<6)
			{
				num="0"+num;
			}
			psmt.setString(1, num);
			psmt.setString(2, codeHZ);
			psmt.setString(3, codeD);
			psmt.setString(4, codeP);
			psmt.setInt(5, num_GH);
			psmt.setBoolean(6, false);
			psmt.setInt(7, cost);
			psmt.setString(8, time);
			String s = "" + rows;
			int a = 6 - s.length();
			while ((a--) != 0) {
				s = "0" + s;
			}
			// ִ��SQL���
			psmt.execute();			
			lb_code.setText(s);//���ùҺű��
			btn_regret.setFocusTraversable(true);

			String information = "��ϲ�����Һųɹ�!\n���ĺ����� " + s + " ���ιҺŷ���Ϊ " + cost + " Ԫ";
			if(mReturn>=0)//��Ҫ����
			{
				System.out.println(mReturn);
				lb_moneyReturn.setText(mReturn + "");
				information += ",\n���� " + mReturn + " Ԫ";
			}
			else
			{
				lb_moneyReturn.setText("0");
			}
			JOptionPane.showMessageDialog(null, information, "��ϲ", 1);
			
			if(mYcChange)
			{
				//����Ԥ�������
				String sqlupdate="update dbo.T_BRXX set YCJE = ? where BRBH = ?";
		        PreparedStatement psmtupdate = Main.con.prepareStatement(sqlupdate);
		        psmtupdate.setInt(1, mYC);
		        psmtupdate.setString(2, main.p.getAcount());
		        psmtupdate.execute();
		        lb_moneyYC.setText(mYC + "");//����Ǯ����
			}
			
			a = 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(a<=10){//����10�γ�ͻ
				System.out.println(e.toString() + " �ظ��Һ�");
				GH(null); //���ݿ��ظ�����,�Һ�ʧ�ܣ����³��ԹҺ�.
				a++;
			}
			else{
				e.printStackTrace();
			}
		} 
	}

	// Event Listener on Button[#btn_clear].onMouseClicked
	@FXML
	public void clearAll(MouseEvent event) {
		// TODO Autogenerated
		// Ԥ�����Ҫ����
		lb_type.clear();
		lb_nameHZ.clear();
		lb_moneyRequire.clear();
		lb_moneyPay.clear();
		lb_moneyReturn.clear();
		lb_nameKS.clear();
		lb_nameD.clear();
		lb_code.clear();
		lb_nameKS.requestFocus();
		btn_regret.setFocusTraversable(false);
	}

	// Event Listener on Button[#btn_exit].onMouseClicked
	@FXML
	public void exitnow(MouseEvent event) {
		// TODO Autogenerated
		Main.primaryStage.close();
	}
	@FXML
	public void mouseback(MouseEvent event) {
		main.showLoginview();
	}
	
	@FXML
	public void mouseRegret() {
		try {
			if(lb_code.getText() == null || lb_code.getText().equals("")) return;
			//��Ʊ
			String sql="update dbo.T_GHXX set THBZ = ?, GHFY = ? where GHBH = ?";//������?��ʾ���൱��ռλ��
			PreparedStatement psmt = Main.con.prepareStatement(sql);
			psmt.setBoolean(1, true);
			psmt.setInt(2, 0); //��������
			int index = Integer.parseInt(lb_code.getText());
			psmt.setInt(3, index);
			psmt.execute();			
			
			//�˻��Һ�Ǯ
			JOptionPane.showMessageDialog(null, "�˻��Һŷ��� " + lb_moneyRequire.getText() + " Ԫ!", "�˻��Һŷ���", 1);
			clearAll(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setMain(Main main) {
		this.main = main;
	}
}

