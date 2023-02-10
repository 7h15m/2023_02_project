package main;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import main.dao.DatabaseService;
import main.dao.DatabaseServiceImpl;
import main.service.CommonService;
import main.service.CommonServiceImpl;
import main.service.join.JoinService;
import main.service.join.JoinServiceImpl;
import main.service.login.LoginService;
import main.service.login.LoginServiceImpl;

public class LoginController {

	Parent root; // 로그인(login.fxml)
	Parent join; // 회원가입(join.fxml)

	LoginService ls;
	JoinService js;
	CommonService cs;

	public LoginController() {
		// TODO Auto-generated constructor stub
		ls = new LoginServiceImpl();
		js = new JoinServiceImpl();
		cs = new CommonServiceImpl();
	}

	public void setRoot(Parent root) {
		// TODO Auto-generated method stub
		this.root = root;
	}

	public void setJoin(Parent join) {
		// TODO Auto-generated method stub
		this.join = join;
	}

	// 티켓박스의 로그인 버튼 클릭 시 실행
	public void loginProc() {
		ls.loginProc(root);
	}

	// 티켓박스의 회원가입 버튼 클릭 시 실행
	public void joinProc() {
		ls.joinProc(root);
	}

	// 회원가입의 회원가입 버튼 클릭 시 실행
	public void joinMember() {
		js.JoinMember(join);
	}

	// 회원가입의 아이디 중복확인 버튼 클릭 시 실행
	public void idDupProc() {
		js.idDupProc(join);
	}

	// 회원가입의 인증번호 발송 버튼 클릭 시 실행
	public void authSendProc() {
		js.authSendProc(join);
	}

	// 회원가입의 인증번호 확인 버튼 클릭 시 실행
	public void authOkProc() {
		js.authOkProc(join);
	}

}
