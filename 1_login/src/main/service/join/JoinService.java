package main.service.join;

import javafx.scene.Parent;

public interface JoinService {
	public void JoinMember(Parent membership);
	public void idDupProc(Parent membership);
	public void authSendProc(Parent membership);
	public boolean authOkProc(Parent membership);
}
