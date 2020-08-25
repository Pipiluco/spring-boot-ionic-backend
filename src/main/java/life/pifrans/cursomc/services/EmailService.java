package life.pifrans.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import life.pifrans.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
