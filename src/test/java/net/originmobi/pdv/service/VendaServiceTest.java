package net.originmobi.pdv.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void deveRetornarCodigoNaoNulo_seVendaNova() {
        System.out.println(Aplicacao.getInstancia().getUsuarioAtual());

        System.out.println(vendaService);
        assertNotNull(vendaService.abreVenda(vendaNova()));
    }

    private Venda vendaNova() {
        return new Venda(null, null, null, null, 
        null, null, null, null, null, null, null);
    }
}
