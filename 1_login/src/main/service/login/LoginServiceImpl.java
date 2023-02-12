package main.service.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import main.LoginController;
import main.dao.DatabaseService;
import main.dao.DatabaseServiceImpl;
import main.service.CommonService;
import main.service.CommonServiceImpl;

public class LoginServiceImpl implements LoginService{

	CommonService cs;
	DatabaseService ds;

	
	public LoginServiceImpl() {
		// TODO Auto-generated constructor stub
		cs = new CommonServiceImpl();
		ds = new DatabaseServiceImpl();
	}

	
	@Override // 티켓박스의 로그인 버튼 클릭 시 실행
	public void loginProc(Parent root) {
		// TODO Auto-generated method stub
		
		TextField id = (TextField) root.lookup("#loginId");
		PasswordField pw = (PasswordField) root.lookup("#loginPw");

		if(ds.chkLogin(id.getText(), pw.getText())) {
			cs.errorMsg("로그인", "로그인 결과", "로그인에 성공하였습니다");
			
			// 로그인 창 닫기
			Stage s = (Stage) root.getScene().getWindow();
			s.close();
			
			//
			//
			//
			//
			//
			//
			//
			//
			//
			//
			//
			//
			//
			// 로그인 성공 시 영화 예매 페이지로 넘어가도록 추후 작업 필요
			
		} else {
			cs.errorMsg("로그인", "로그인 결과", "아이디/패스워드가 일치하지 않습니다");
			id.clear();
			pw.clear();
			id.requestFocus();
		}
	}


	@Override
	public Parent joinProc(Parent root) {
		// TODO Auto-generated method stub
		Stage joinForm = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../join.fxml"));

		Parent join = null;
		try {
			join = loader.load();

			joinForm.setScene(new Scene(join));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LoginController ctrl = loader.getController();
		ctrl.setJoin(join);

		joinForm.setTitle("회원 가입 창");
		joinForm.show();
		
		// 생년월일 prompt text 폰트 크기 맞춰줌
		DatePicker birth = (DatePicker) join.lookup("#joinBirth");
		birth.getEditor().setFont(new Font(14));

		return join;
	}

}
