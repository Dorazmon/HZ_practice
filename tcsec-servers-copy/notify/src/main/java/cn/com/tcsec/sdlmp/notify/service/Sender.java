package cn.com.tcsec.sdlmp.notify.service;


public interface Sender {
	
  void send(String subject, String contact, String message);
}
