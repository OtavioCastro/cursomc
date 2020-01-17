package com.init0.cursomc;

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
import com.init0.cursomc.domain.Produto;
import com.init0.cursomc.domain.enums.TipoCliente;
import com.init0.cursomc.repositories.CategoriaRepository;
import com.init0.cursomc.repositories.CidadeRepository;
import com.init0.cursomc.repositories.ClienteRepository;
import com.init0.cursomc.repositories.EnderecoRepository;
import com.init0.cursomc.repositories.EstadoRepository;
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
		
	}

}
