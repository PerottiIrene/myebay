package it.prova.myebay.dto;

public class PasswordDTO {
	
	private String vecchiaPassword;
	private String nuovaPassword;
	private String confermaNuovaPassword;
	
	public String getVecchiaPassword() {
		return vecchiaPassword;
	}
	public void setVecchiaPassword(String vecchiaPassword) {
		this.vecchiaPassword = vecchiaPassword;
	}
	public String getNuovaPassword() {
		return nuovaPassword;
	}
	public void setNuovaPassword(String nuovaPassword) {
		this.nuovaPassword = nuovaPassword;
	}
	public String getConfermaNuovaPassword() {
		return confermaNuovaPassword;
	}
	public void setConfermaNuovaPassword(String confermaNuovaPassword) {
		this.confermaNuovaPassword = confermaNuovaPassword;
	}
	
	

}
