package main.service;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class CommonServiceImpl implements CommonService{

	@Override // 팝업창
	public void errorMsg(String subject, String head, String content) {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(subject);
		alert.setHeaderText(head);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
