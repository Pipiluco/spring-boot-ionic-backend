package life.pifrans.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import life.pifrans.cursomc.domain.Cliente;
import life.pifrans.cursomc.repositories.ClienteRepository;
import life.pifrans.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private EmailService emailService;
	private Random random = new Random();

	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("E-mail não encontrado!");
		}
		String newPass = newPassword();
		cliente.setSenha(encoder.encode(newPass));
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randonChar();
		}
		return new String(vet);
	}

	private char randonChar() {
		int opt = random.nextInt(3);
		if (opt == 0) { // Gera um número de 0 a 9
			return (char) (random.nextInt(10) + 48);
		} else if (opt == 1) { // Gera uma letra maiúscula
			return (char) (random.nextInt(26) + 65);
		} else { // Gera uma letra minúscula
			return (char) (random.nextInt(26) + 97);
		}
	}
}
