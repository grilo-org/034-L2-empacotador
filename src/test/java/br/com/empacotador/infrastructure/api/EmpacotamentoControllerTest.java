package br.com.empacotador.infrastructure.api;

import br.com.empacotador.adapter.in.web.dto.*;
import br.com.empacotador.application.EmpacotadorService;
import br.com.empacotador.domain.ItemEmpacotado;
import br.com.empacotador.domain.Pedido;
import br.com.empacotador.domain.PedidoEmpacotado;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpacotamentoControllerTest {

  @InjectMocks
  private EmpacotamentoController controller;

  @Mock
  private EmpacotadorService empacotadorService;

  @Test
  void deveEmpacotarPedidoComEntradaEValidarSaida() {

    DimensoesRequest dimensoes = new DimensoesRequest(10, 10, 10);
    ProdutoRequest produto = new ProdutoRequest("produto-1", dimensoes);
    PedidoRequest pedidoRequest = new PedidoRequest(1, List.of(produto));
    PedidosRequest pedidosRequest = new PedidosRequest(List.of(pedidoRequest));

    ItemEmpacotado itemEmpacotado = new ItemEmpacotado("produto-1", "Caixa 1");
    PedidoEmpacotado pedidoEmpacotado = new PedidoEmpacotado(1, List.of(itemEmpacotado));

    when(empacotadorService.empacotarPedidos(any())).thenReturn(pedidoEmpacotado);

    PedidosEmpacotadosResponse response = controller.execute(pedidosRequest);

    assertNotNull(response);
    assertEquals(1, response.pedidos().size());

    PedidoEmpacotadoResponse empacotado = response.pedidos().get(0);
    assertEquals(1, empacotado.pedido_id());
    assertEquals(1, empacotado.caixas().size());

    CaixaResponse caixa = empacotado.caixas().get(0);
    assertEquals("Caixa 1", caixa.caixa_id());

    assertEquals(1, caixa.produtos().size());
    assertEquals("produto-1", caixa.produtos().get(0));

    ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
    verify(empacotadorService, times(1)).empacotarPedidos(captor.capture());

    Pedido pedidoEnviado = captor.getValue();
    assertEquals(1, pedidoEnviado.pedidoId());
    assertEquals(1, pedidoEnviado.produtos().size());
  }
}
