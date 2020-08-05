package life.pifrans.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import life.pifrans.cursomc.domain.Categoria;
import life.pifrans.cursomc.domain.Cidade;
import life.pifrans.cursomc.domain.Cliente;
import life.pifrans.cursomc.domain.Endereco;
import life.pifrans.cursomc.domain.Estado;
import life.pifrans.cursomc.domain.Produto;
import life.pifrans.cursomc.domain.enums.TipoCliente;
import life.pifrans.cursomc.repositories.CategoriaRepository;
import life.pifrans.cursomc.repositories.CidadeRepository;
import life.pifrans.cursomc.repositories.ClienteRepository;
import life.pifrans.cursomc.repositories.EnderecoRepository;
import life.pifrans.cursomc.repositories.EstadoRepository;
import life.pifrans.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		//
		Estado est1 = new Estado(null, "Santa Catarina");
		Estado est2 = new Estado(null, "Paraná");
		
		Cidade cid1 = new Cidade(null, "Indaial", est1);
		Cidade cid2 = new Cidade(null, "Blumenau", est1);
		Cidade cid3 = new Cidade(null, "Curitiba", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1, cid2));
		est2.getCidades().addAll(Arrays.asList(cid3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		//
		Cliente cli1 = new Cliente(null, "Tibío Capetei", "tibio@gmail.com", "01287254442", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("54258855", "42455599"));
		
		Endereco e1 = new Endereco(null, "Rua da lata", 44, "Casinha", "Tambor", "89025511", cli1, cid1);
		Endereco e2 = new Endereco(null, "Rua da grama", 234, "Casinha", "Tonel", "89045566", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1 ,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}

}


