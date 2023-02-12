package main.service.join;

import java.time.LocalDate;
import java.util.Random;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.LoginController;
import main.dao.DatabaseService;
import main.dao.DatabaseServiceImpl;
import main.service.CommonService;
import main.service.CommonServiceImpl;

public class JoinServiceImpl implements JoinService{

	CommonService cs;
	DatabaseService ds;
	boolean clickId = false; // 아이디 중복확인 완료 시
	boolean clickAuth = false; // 인증번호 전송 클릭 시
	String checkNumber; // 랜덤 인증번호 문자형 변환
	Parent auth = null; // 인증번호(auth.fxml)
	boolean authFin = false; // 인증번호 확인 완료 시
	int ageCal; // 나이 계산한 변수
	
	
	public JoinServiceImpl() {
		// TODO Auto-generated constructor stub
		cs = new CommonServiceImpl();
		ds = new DatabaseServiceImpl();
	}
	
	
	@Override // 회원가입의 회원가입 버튼 클릭 시 실행
	public void JoinMember(Parent membership) {
		
		Member m = new Member();
		
		TextField id = (TextField) membership.lookup("#joinId");
		TextField name = (TextField) membership.lookup("#joinName");
		TextField age = (TextField) membership.lookup("#joinAge");
		TextField tel = (TextField) membership.lookup("#joinTel");
		TextField auth = (TextField) membership.lookup("#joinAuth");
		PasswordField pw = (PasswordField) membership.lookup("#joinPw");
		PasswordField pwOk = (PasswordField) membership.lookup("#joinPwOk");
		
		
		// 비어있을 경우 에러 메세지
		if(id.getText().isEmpty()) {
			cs.errorMsg("입력 에러", "비어 있는 항목", "아이디 항목이 비어있습니다");
			id.requestFocus();
			return;
		}
		if(pw.getText().isEmpty()) {
			cs.errorMsg("입력 에러", "비어 있는 항목", "비밀번호 항목이 비어있습니다");
			pw.requestFocus();
			return;
		}
		if(pwOk.getText().isEmpty()) {
			cs.errorMsg("입력 에러", "비어 있는 항목", "비밀번호 확인 항목이 비어있습니다");
			pwOk.requestFocus();
			return;
		}
		if(name.getText().isEmpty()) {
			cs.errorMsg("입력 에러", "비어 있는 항목", "이름 항목이 비어있습니다");
			name.requestFocus();
			return;
		}
		if(age.getText().isEmpty()) {
			cs.errorMsg("입력 에러", "비어 있는 항목", "나이 항목이 비어있습니다");
			age.requestFocus();
			return;
		}
		if(tel.getText().isEmpty()) {
			cs.errorMsg("입력 에러", "비어 있는 항목", "전화번호 항목이 비어있습니다");
			tel.requestFocus();
			return;
		}
		if(auth.getText().isEmpty()) {
			cs.errorMsg("입력 에러", "비어 있는 항목", "인증번호 항목이 비어있습니다");
			auth.requestFocus();
			return;
		}
		
		// tel에 숫자만 입력 가능하도록
		String num = "[0-9]+";
		if(tel.getText().matches(num)) {
			
		} else {
			cs.errorMsg("입력 에러", "입력 형식 불일치", "전화번호는 숫자만 입력 가능합니다");
			tel.clear();
			tel.requestFocus();
			return;
		}
		
		// 비밀번호와 비밀번호 확인이 일치할 경우
		if(pw.getText().equals(pwOk.getText())) {
			// Member 객체에 값 저장
			m.setId(id.getText());
			m.setPw(pw.getText());
			m.setName(name.getText());
			m.setAge(ageCal);
			m.setTel(tel.getText());
			
		// 불일치할 경우	
		} else {
			cs.errorMsg("암호", "암호 일치 여부", "암호가 일치하지 않습니다");
			pw.clear();
			pwOk.clear();
			pw.requestFocus();
			return;
		}
		
		// db에 가입정보 입력하기
		if(ds.insert(m, clickId, clickAuth, authFin)) {
			// 입력 성공 시 회원가입창 닫기
			Stage s = (Stage) membership.getScene().getWindow();
			s.close();
		} else {

		}
	}

	
	@Override // 회원가입의 아이디 중복확인 버튼 클릭 시 실행
	public void idDupProc(Parent membership) {
		
		TextField id = (TextField) membership.lookup("#joinId");
		
		boolean result = ds.chkId(id.getText());
		
		// 아이디 항목이 비어있을 경우
		if(id.getText().isEmpty()) {
			cs.errorMsg("중복 확인", "아이디 확인", "아이디 항목이 비어있습니다");
			id.requestFocus();
			return;
		// 아이디 항목이 입력된 경우
		} else {
			// db에 중복 아이디가 없는 경우
			if(result) {
				cs.errorMsg("중복 확인", "아이디 확인", "사용가능한 아이디입니다");
				clickId = true; // 중복확인이 완료됐다는 논리값(성공했을 때만 true)
				
				// 중복확인 버튼 숨기기
				Button joinIdDup = (Button) membership.lookup("#joinIdDup");
				joinIdDup.setVisible(false);
				// 아이디 입력창 수정불가
				id.setEditable(false);
				
			// db에 중복 아이디가 있는 경우
			} else {
				cs.errorMsg("중복 확인", "아이디 확인", "같은 아이디가 존재합니다");
				id.clear();
				id.requestFocus();
				return;
			}
		}
	}

	
	@Override // 회원가입의 인증번호 발송 버튼 클릭 시 실행
	public void authSendProc(Parent membership) {
		
		// 전화번호 입력 시에만 인증번호 발송 클릭이 가능하도록
		TextField tel = (TextField) membership.lookup("#joinTel");
		
		if(tel.getText().isEmpty()) {
			cs.errorMsg("입력 에러", "비어있는 항목", "전화번호 항목이 비어있습니다");
			tel.requestFocus();
			return;
			
		} else {
			// tel에 숫자만 입력 가능하도록
			String num = "[0-9]+";
			
			if(tel.getText().matches(num)) {
				// 전화번호 중복 아닐 때만 발송하도록
				if(ds.chkTel(tel.getText())) {
					
					clickAuth = true; // 인증번호 발송이 완료됐다는 논리값
					// 전화번호 입력창 수정불가
					tel.setEditable(false);
					
					// 랜덤으로 인증번호 생성
					Random random = new Random();
					int checkNum = random.nextInt(888888) + 111111;
					// 라벨에 setText를 넣기 위해 정수형을 문자형으로 변경한 변수 추가
					checkNumber = String.valueOf(checkNum);
					
					// 인증번호 생성 창
					Stage authStage = new Stage();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../../../auth.fxml"));
					
					try {
						auth = loader.load();

						authStage.setScene(new Scene(auth));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// 라벨에 인증번호 입력
					Label randomNum = (Label) auth.lookup("#randomNum");
					randomNum.setText(checkNumber);
					
					LoginController ctrl = loader.getController();
					ctrl.setJoin(auth);

					authStage.setTitle("인증번호");
					authStage.show();
					
				} else {
					cs.errorMsg("중복 확인", "전화번호 확인", "같은 전화번호가 존재 합니다");
					tel.clear();
					tel.requestFocus();
				}

			// tel에 숫자만 입력 안했을 경우	
			} else {
				cs.errorMsg("입력 에러", "입력 형식 불일치", "전화번호는 숫자만 입력 가능합니다");
				tel.clear();
				tel.requestFocus();
				return;
			}

		}
		
	}
	
	
	// 회원가입의 인증번호 확인 버튼 클릭 시 실행
	public boolean authOkProc(Parent membership) {
		TextField joinAuth = (TextField) membership.lookup("#joinAuth");
		// 인증번호 확인 창에 입력된 텍스트값을 문자형 변수로 새로 지정
		String authTxt = joinAuth.getText();

		// 인증번호와 확인 창에 입력된 값이 같은지 확인
		if(authTxt.equals(checkNumber)) {
			cs.errorMsg("인증번호 확인", "인증번호 일치여부", "인증번호가 일치합니다");
			
			// 인증번호 창 닫기
			Stage s = (Stage) auth.getScene().getWindow();
			s.close();
			
			// 인증번호 전송, 확인 버튼 숨기기
			Button joinAuthSend = (Button) membership.lookup("#joinAuthSend");
			Button joinAuthOk = (Button) membership.lookup("#joinAuthOk");
			TextField auth = (TextField) membership.lookup("#joinAuth");
			joinAuthSend.setVisible(false);
			joinAuthOk.setVisible(false);
			// 인증번호 확인창 수정 불가 처리
			auth.setEditable(false);
			// 인증번호 확인이 완료되었다는 논리값(완료시에만 true)
			authFin = true;
		// 인증번호 확인값이 불일치할 경우	
		} else {
			cs.errorMsg("인증번호 확인", "인증번호 일치여부", "인증번호가 불일치합니다");
			joinAuth.clear();
			joinAuth.requestFocus();
		}
		return authFin;
	}
	
	// 생년월일 선택 시 나이 계산해서 나이창에 입력되도록
	public void birthProc(Parent membership) {
		
		TextField age = (TextField) membership.lookup("#joinAge");
		
		DatePicker birth = (DatePicker) membership.lookup("#joinBirth");
		LocalDate today = LocalDate.now();
//		LocalDate clear = LocalDate.parse("1900-01-01");
		
		// 생년월일 선택 시 오늘 이전까지만 선택 가능하도록
		if(birth.getValue().getYear()>=today.getYear()&&birth.getValue().getMonthValue()>=today.getMonthValue()&&birth.getValue().getDayOfMonth()>=today.getDayOfMonth()) {
			cs.errorMsg("입력 에러", "입력 형식 불일치", "생년월일은 오늘 이전 날짜만 가능합니다");
			birth.requestFocus();
			age.clear();
//			birth.setValue(clear);
		} else {
			ageCal = (today.getYear()-birth.getValue().getYear())+1;	
			
			age.setText(String.valueOf(ageCal)+" 세");
		}

	}

}
