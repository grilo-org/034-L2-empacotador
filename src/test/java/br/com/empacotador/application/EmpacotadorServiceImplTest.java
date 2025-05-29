package br.com.empacotador.application;

import br.com.empacotador.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmpacotadorServiceImplTest {

  private EmpacotadorServiceImpl service;

  @BeforeEach
  void setUp() {
    service = new EmpacotadorServiceImpl();
  }

  @Test
  @DisplayName("Deve empacotar pedido com múltiplos itens com sucesso")
  void deveEmpacotarPedidoComSucesso() {
    Pedido pedido = new Pedido(1, List.of(
        new Produto("Item A", new Dimensoes(10, 10, 10)),
        new Produto("Item B", new Dimensoes(20, 20, 5))
    ));

    PedidoEmpacotado resultado = service.empacotarPedidos(pedido);

    assertNotNull(resultado);
    assertEquals(1, resultado.pedidoId());
    assertFalse(resultado.itens().isEmpty());
  }

  @Test
  @DisplayName("Deve empacotar item que cabe exatamente na Caixa 1 (30x40x80)")
  void deveEmpacotarItemNaCaixa1() {
    Pedido pedido = new Pedido(2, List.of(
        new Produto("Produto A", new Dimensoes(25, 35, 70)) // cabe na Caixa 1
    ));

    PedidoEmpacotado resultado = service.empacotarPedidos(pedido);

    assertNotNull(resultado);
    assertEquals(2, resultado.pedidoId());
    assertFalse(resultado.itens().isEmpty());
    assertEquals("Caixa 1", resultado.itens().get(0).caixaId());
  }

  @Test
  @DisplayName("Deve empacotar item que cabe exatamente na Caixa 2 (80x50x40)")
  void deveEmpacotarItemNaCaixa2() {
    Pedido pedido = new Pedido(3, List.of(
        new Produto("Produto B", new Dimensoes(70, 40, 30)) // cabe na Caixa 2
    ));

    PedidoEmpacotado resultado = service.empacotarPedidos(pedido);

    assertNotNull(resultado);
    assertEquals(3, resultado.pedidoId());
    assertFalse(resultado.itens().isEmpty());
    assertEquals("Caixa 2", resultado.itens().get(0).caixaId());
  }

  @Test
  @DisplayName("Deve empacotar item que cabe exatamente na Caixa 3 (50x80x60)")
  void deveEmpacotarItemNaCaixa3() {
    Pedido pedido = new Pedido(4, List.of(
        new Produto("Produto C", new Dimensoes(45, 70, 50)) // cabe na Caixa 3
    ));

    PedidoEmpacotado resultado = service.empacotarPedidos(pedido);

    assertNotNull(resultado);
    assertEquals(4, resultado.pedidoId());
    assertFalse(resultado.itens().isEmpty());
    assertEquals("Caixa 3", resultado.itens().get(0).caixaId());
  }

  @Test
  @DisplayName("Deve empacotar 3 produtos em 2 caixas: dois em uma, um em outra")
  void deveEmpacotarTresItensEmDuasCaixas() {
    Pedido pedido = new Pedido(5, List.of(
        new Produto("Produto A", new Dimensoes(20, 20, 20)), // cabe com B
        new Produto("Produto B", new Dimensoes(25, 20, 20)), // cabe com A
        new Produto("Produto C", new Dimensoes(50, 50, 40))  // precisa de outra caixa
    ));

    PedidoEmpacotado resultado = service.empacotarPedidos(pedido);

    assertNotNull(resultado);
    assertEquals(5, resultado.pedidoId());
    assertEquals(3, resultado.itens().size());

    var itemA = resultado.itens().stream().filter(i -> i.produtoId().equals("Produto A")).findFirst().orElseThrow();
    var itemB = resultado.itens().stream().filter(i -> i.produtoId().equals("Produto B")).findFirst().orElseThrow();
    var itemC = resultado.itens().stream().filter(i -> i.produtoId().equals("Produto C")).findFirst().orElseThrow();

    assertEquals(itemA.caixaId(), itemB.caixaId(), "Produtos A e B devem estar na mesma caixa");

    assertNotEquals(itemA.caixaId(), itemC.caixaId(), "Produto C deve estar em uma caixa diferente");
  }

  @Test
  @DisplayName("Deve retornar pedido empacotado vazio quando item não cabe em nenhuma caixa")
  void deveFalharEmpacotarItemMaiorQueTodasAsCaixas() {
    Pedido pedido = new Pedido(6, List.of(
        new Produto("Produto Gigante", new Dimensoes(1000, 1000, 1000)) // muito maior que qualquer caixa
    ));

    PedidoEmpacotado resultado = service.empacotarPedidos(pedido);

    assertNotNull(resultado);
    assertEquals(6, resultado.pedidoId());
    assertTrue(resultado.itens().isEmpty(), "Nenhum item deve ser empacotado");
  }
}