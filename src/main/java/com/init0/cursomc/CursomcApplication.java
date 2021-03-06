package com.init0.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.init0.cursomc.domain.Categoria;
import com.init0.cursomc.domain.Cidade;
import com.init0.cursomc.domain.Cliente;
import com.init0.cursomc.domain.Endereco;
import com.init0.cursomc.domain.Estado;
import com.init0.cursomc.domain.ItemPedido;
import com.init0.cursomc.domain.Pagamento;
import com.init0.cursomc.domain.PagamentoComBoleto;
import com.init0.cursomc.domain.PagamentoComCartao;
import com.init0.cursomc.domain.Pedido;
import com.init0.cursomc.domain.Produto;
import com.init0.cursomc.domain.enums.EstadoPagamento;
import com.init0.cursomc.domain.enums.TipoCliente;
import com.init0.cursomc.repositories.CategoriaRepository;
import com.init0.cursomc.repositories.CidadeRepository;
import com.init0.cursomc.repositories.ClienteRepository;
import com.init0.cursomc.repositories.EnderecoRepository;
import com.init0.cursomc.repositories.EstadoRepository;
import com.init0.cursomc.repositories.ItemPedidoRepository;
import com.init0.cursomc.repositories.PagamentoRepository;
import com.init0.cursomc.repositories.PedidoRepository;
import com.init0.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

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
		
		//Populando a tabela Categoria
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		//Populando a tabela Produto
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		//Adicionando a lista de produtos as suas respectivas categorias
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Adicionando a lista de categorias aos seus respectivos produtos
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//Salvando os objetos de categoria no BD
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
		//Salvando os objetos de produto no BD
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		
		//Populando a tabela Estado
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		//Populando a tabela Cidade
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		//Adicionando a lista de cidades aos seus respectivos estados
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		//Salvando os objetos de estado no BD
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		//Salvando os objetos de cidade no BD
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		//Populando a tabela Cliente
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "36378912377", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));

		//Populando a tabela Endereco		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);
		
		//Adicionando a lista de endereços ao cliente
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		//Salvando os objetos de cliente no BD
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		//Salvando os objetos de endereco no BD
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		//Auxiliar para formatar data na instância do pedido
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//Populando a tabela Pedido
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 18:35"), cli1, e2);
		
		//Populando a tabela Pagamento
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		//Adicionando o pagamento ao pedido 1
		ped1.setPagamento(pagto1);
		
		//Populando a tabela Pagamento
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		//Adicionando o pagamento ao pedido 2
		ped2.setPagamento(pagto2);
		
		//Adicionando a lista de pedidos ao cliente
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		//salvando objetos de pedido no BD 
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		
		//salvando objetos de pagamento no BD
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		//Populando a tabela ItemPedido
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		//Adicionando a lista auxiliar itemPedido aos pedidos - variável itens
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		//Adicionando a lista auxiliar itemPedido aos produtos - variável itens
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		//Salvando objetos do tipo ItemPedido ao BD
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
