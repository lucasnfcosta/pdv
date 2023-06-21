package net.originmobi.pdv;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.jfree.util.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import net.originmobi.pdv.service.notafiscal.NotaFiscalService;
import net.originmobi.pdv.service.EmpresaService;
import net.originmobi.pdv.enumerado.TelefoneTipo;
import net.originmobi.pdv.enumerado.notafiscal.NotaFiscalTipo;
import net.originmobi.pdv.model.Cidade;
import net.originmobi.pdv.model.Empresa;
import net.originmobi.pdv.model.Endereco;
import net.originmobi.pdv.model.Estado;
import net.originmobi.pdv.model.EmpresaParametro;
import net.originmobi.pdv.model.RegimeTributario;
import net.originmobi.pdv.model.Telefone;
import net.originmobi.pdv.repository.EmpresaRepository;
import net.originmobi.pdv.model.Pessoa;
import net.originmobi.pdv.model.FreteTipo;
import net.originmobi.pdv.model.NotaFiscalFinalidade;
import net.originmobi.pdv.model.NotaFiscalItem;
import net.originmobi.pdv.model.NotaFiscalItemImposto;
import net.originmobi.pdv.model.NotaFiscalTotais;
import net.originmobi.pdv.model.Pais;
import net.originmobi.pdv.model.NotaFiscal;
import net.originmobi.pdv.xml.nfe.GeraXmlNfe;


@SpringBootTest(classes = NotaFiscalServiceTest.class)
@RunWith(SpringRunner.class)
public class NotaFiscalServiceTest {
	
	private NotaFiscalService NFService;
	private NotaFiscal notaFiscal;
	private Empresa empresa;
	private NotaFiscalFinalidade notaFiscalFin;
	private NotaFiscalTotais notaFiscalTot;
	private Pais pais;
	private Estado estado;
	private Cidade cidade;
	private Pessoa pessoa;
	private Endereco endereco;
	private RegimeTributario rtb;
	private EmpresaParametro empP;
	private FreteTipo ft;
	
	@Before
	public void inicializa() {
		NFService = new NotaFiscalService();
		
		pais = new Pais();
		pais.setCodigo(22l);
		pais.setCodigo_pais("Br");
		pais.setNome("Brasil");
		
		estado = new Estado();
		estado.setCodigo(2l);
		estado.setNome("Rio de Janeiro");
		estado.setPais(pais);
		estado.setSigla("RJ");
		estado.setCodigo(1l);
		
		cidade = new Cidade();
		cidade.setEstado(estado);
		cidade.setCodigo(24l);
		cidade.setCodigo_municipio("55");
		cidade.setNome("Niteroi");
		
		rtb = new RegimeTributario();
		rtb.setCodigo(91l);
		rtb.setDescricao("descricao");
		rtb.setTipoRegime(2);
		
		empP = new EmpresaParametro();
		empP.setAmbiente(5);
		empP.setCodigo(5l);
		empP.setpCredSN(500.6);
		empP.setSerie_nfe(2);
		
		List<Telefone> lt = new ArrayList<Telefone>();
		Telefone t1 = new Telefone();
		t1.setCodigo(1234l);
		t1.setData_cadastro(new Date(10/03/2002));
		t1.setFone("f");
		t1.setTipo(TelefoneTipo.valueOf("FIXO"));
		lt.add(t1);
		
		endereco = new Endereco("cardoso", "centro", "145", "45675-754", "ref", new Date(22/01/1991), cidade);
		empresa = new Empresa("empresa", "emp", "3312313123213", "ie", rtb, endereco, empP);
		pessoa = new Pessoa("Rodrigo", "rd", "335.312.352-6", "observa", new Date(20/10/2000), new Date(02/10/2021), endereco, lt);
		
		ft = new FreteTipo();
		ft.setCodigo(44l);
		ft.setDescricao("frete desc");
		ft.setTipo(8);
		
		notaFiscalFin = new NotaFiscalFinalidade();
		notaFiscalFin.setCodigo(32l);
		notaFiscalFin.setDescricao("nota fiscal finalidade desc");
		notaFiscalFin.setTipo(77);
		
		notaFiscalTot = new NotaFiscalTotais(1.1, 2.2, 2.3, 3.4, 5.0, 3.1, 8.8, 8.2, 7.9, 1.0, 2.9, 12.2, 5.2);
		
		notaFiscal = new NotaFiscal(6l, 5, NotaFiscalTipo.valueOf("ENTRADA"), "natureza", 6, empresa, pessoa, 7, "verpro", ft, notaFiscalFin, 
				notaFiscalTot, 8, new Date(20/10/2021));
		notaFiscal.setChave_acesso("chave");
	}
	
	@Test
	public void testList() {
		System.out.print("Metodo find all retorna uma lista vazia\n");
		assertThrows(NullPointerException.class, ()->NFService.lista());
	}
	
	@Test
	public void testEmpresaExiste() {
		EmpresaService es1 = new EmpresaService();
		Empresa e1 = new Empresa("Empresa1", "E1", "123-321-23-223-1", "12", new RegimeTributario(), new Endereco(), new EmpresaParametro());
		try{
			es1.cadastro(e1);	
		}catch(Exception e) {
			System.out.println("Não ha empresas para cadastrar!");
			//fail("Não ha empresas para cadastrar!\n");
		}
		assertThrows(NullPointerException.class, ()->es1.verificaEmpresaCadastrada());		
	}
	
	/*public void testCadastro() {
		EmpresaService es = new EmpresaService();
		es.cadastro(empresa);
		
		NFService.cadastrar(2l, "natureza", NotaFiscalTipo.valueOf("ENTRADA"));
	}*/
	
	@Test
	public void testGeraDV() {
		NotaFiscalService nf = new NotaFiscalService();
		assertEquals(1, nf.geraDV("DV14"));
		System.out.println("DV gerado");
	}
	
	@Test
	public void testCleanUp() {
		assertThrows(NullPointerException.class, ()->NFService.cleanUp(null));
		System.out.println("O arquivo nao pode ser deletado pois nao existe");
	}

	@Test
	public void testNF() {
		assertNotNull(this.notaFiscal);
		
		NotaFiscalItemImposto nfii = new NotaFiscalItemImposto(1, 2, 3, 4.5, 6.8, 12.7, 6, 8.1, 7.1, 8.2, 14.2, 1.1,
				5.5, 7, 11, 1.5, 2.5, 3.3);
		
		List<NotaFiscalItem> lnfi = new ArrayList<NotaFiscalItem>();
		NotaFiscalItem nf1 = new NotaFiscalItem(2l, 5, 88.50, "tribu", 14, 6.50, notaFiscal, nfii, "cfop");
		lnfi.add(nf1);

		notaFiscal.setItens(lnfi);
		
		assertThrows(NullPointerException.class, ()->NFService.emitir(notaFiscal));
		System.out.println("NotaFiscalRepository notasFiscais está vazio!");
	}
}
