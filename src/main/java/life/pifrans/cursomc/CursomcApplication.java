package life.pifrans.cursomc;

import java.text.SimpleDateFormat;
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
import life.pifrans.cursomc.domain.ItemPedido;
import life.pifrans.cursomc.domain.Pagamento;
import life.pifrans.cursomc.domain.PagamentoComBoleto;
import life.pifrans.cursomc.domain.PagamentoComCartao;
import life.pifrans.cursomc.domain.Pedido;
import life.pifrans.cursomc.domain.Produto;
import life.pifrans.cursomc.domain.enums.EstadoPagamento;
import life.pifrans.cursomc.domain.enums.TipoCliente;
import life.pifrans.cursomc.repositories.CategoriaRepository;
import life.pifrans.cursomc.repositories.CidadeRepository;
import life.pifrans.cursomc.repositories.ClienteRepository;
import life.pifrans.cursomc.repositories.EnderecoRepository;
import life.pifrans.cursomc.repositories.EstadoRepository;
import life.pifrans.cursomc.repositories.ItemPedidoRepository;
import life.pifrans.cursomc.repositories.PagamentoRepository;
import life.pifrans.cursomc.repositories.PedidoRepository;
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
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Ferramentas");
		Categoria cat5 = new Categoria(null, "Brinquedos");
		Categoria cat6 = new Categoria(null, "Jardinagem");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		Categoria cat8 = new Categoria(null, "Roupas");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7, cat8));
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
		
		//
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pag1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pag1);
		
		Pagamento pag2 = new PagamentoComBoleto(null,  EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1, pag2));
		
		//
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
		
	}

}


