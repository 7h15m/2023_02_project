package main.service.search;

import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.dao.DatabaseService;
import main.dao.DatabaseServiceImpl;
import main.service.CommonService;
import main.service.CommonServiceImpl;

public class SearchServiceImpl implements SearchService{

	CommonService cs;
	DatabaseService ds;
	
	public SearchServiceImpl() {
		// TODO Auto-generated constructor stub
		cs = new CommonServiceImpl();
		ds = new DatabaseServiceImpl();
	}
	
	@Override // 아이디 찾기의 찾기 버튼 클릭 시 실행
	public void idSearch(Parent search) {
		// TODO Auto-generated method stub
		TextField name = (TextField) search.lookup("#idSearchName");
		TextField tel = (TextField) search.lookup("#idSearchTel");
		
		String result = ds.searchId(name.getText(), tel.getText());
		
		if(result!=null) {
			cs.errorMsg("아이디 찾기", "일치", "입력한 정보와 일치하는 아이디는 "+result+" 입니다");
			
			Stage s = (Stage) search.getScene().getWindow();
			s.close();
			
		} else {
			cs.errorMsg("아이디 찾기", "불일치", "입력한 정보와 일치하는 아이디가 없습니다");
			name.clear();
			tel.clear();
			name.requestFocus();
		}

	}

	@Override // 비밀번호 찾기의 찾기 버튼 클릭 시 실행
	public void pwSearch(Parent search) {
		// TODO Auto-generated method stub
		TextField id = (TextField) search.lookup("#pwSearchId");
		TextField name = (TextField) search.lookup("#pwSearchName");
		TextField tel = (TextField) search.lookup("#pwSearchTel");
		
		String result = ds.searchPw(id.getText(),name.getText(), tel.getText());
		
		if(result!=null) {
			cs.errorMsg("비밀번호 찾기", "일치", "입력한 정보와 일치하는 비밀번호는 " + result + " 입니다");
			
			Stage s = (Stage) search.getScene().getWindow();
			s.close();
		} else {
			cs.errorMsg("비밀번호 찾기", "불일치", "입력한 정보와 일치하는 비밀번호가 없습니다");
			id.clear();
			name.clear();
			tel.clear();
			id.requestFocus();
		}
	}

}
