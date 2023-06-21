package net.originmobi.pdv.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import net.originmobi.pdv.model.Pessoa;
import net.originmobi.pdv.model.Usuario;
import net.originmobi.pdv.model.Venda;
import net.originmobi.pdv.singleton.Aplicacao;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VendaServiceTest {

    @Autowired
    VendaService vendaService;

    @Before
    public void init() {
        TestingAuthenticationToken testingAuth = new TestingAuthenticationToken("gerente", "123");
        
        SecurityContextHolder.getContext().setAuthentication(testingAuth);
    }

    @Test
    public void deveRetornarCodigoNaoNulo() {
        assertNotNull(vendaService.abreVenda(vendaNova()));
        assertNotNull(vendaService.abreVenda(vendaExistente()));
    }

    @Test
    public void deveRetornarCodigoDiferente_seVendaNova() {
        assertNotEquals(vendaNova().getCodigo(), vendaService.abreVenda(vendaNova()));
    }

    @Test
    public void deveRetornarCodigo_seVendaExiste() {
        assertEquals(vendaExistente().getCodigo(), vendaService.abreVenda(vendaExistente()));
    }

    private Venda vendaNova() {
        return new Venda(null, null, null, null, 
        null, null, null, null, null, null, null);
    }

    private Venda vendaExistente() {
        Pessoa pessoa = new Pessoa("Leoncio", "Leo", "11823667898", null, null, 
        null, null, null);
        Usuario usuario = new Usuario(null, Aplicacao.getInstancia().getUsuarioAtual(), null, null, pessoa, null, null);
        
        Venda venda = new Venda(null, null, null, null, null, 
        null, null, null, null, pessoa, usuario);
        venda.setCodigo(Long.valueOf(94));

        return venda;
    }
}
