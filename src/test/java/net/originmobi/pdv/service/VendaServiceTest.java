package net.originmobi.pdv.service;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import net.originmobi.pdv.model.Venda;

public class VendaServiceTest {    
    @Test
    public void deveRetornarCodigoNaoNulo_seVendaNova() {
        VendaService vendaService = new VendaService();

        assertNotNull(vendaService.abreVenda(vendaNova()));
    }

    private Venda vendaNova() {
        return new Venda(null, null, null, null, 
        null, null, null, null, null, null, null);
    }
}
