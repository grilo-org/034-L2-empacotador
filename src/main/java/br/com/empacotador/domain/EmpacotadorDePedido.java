package br.com.empacotador.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EmpacotadorDePedido {
  private final SelecionadorDeCaixa selecionadorDeCaixa;
  private static final Logger log = LoggerFactory.getLogger(EmpacotadorDePedido.class);

  public EmpacotadorDePedido(SelecionadorDeCaixa selecionadorDeCaixa) {
    this.selecionadorDeCaixa = selecionadorDeCaixa;
  }

  public PedidoEmpacotado empacotar(Pedido pedido) {
    List<String> naoEmpacotados = new ArrayList<>();

    List<ItemEmpacotado> empacotados = pedido.produtos().stream()
        .map(produto -> selecionadorDeCaixa.selecionarCaixaPara(produto)
            .map(caixa -> new ItemEmpacotado(produto.produtoId(), caixa.caixaId()))
            .orElseGet(() -> {
              naoEmpacotados.add(produto.produtoId());
              return null; // será filtrado depois
            }))
        .filter(item -> item != null)
        .toList();

    if (!naoEmpacotados.isEmpty()) {
      log.warn("Pedido {}: os seguintes produtos não foram empacotados: {}", pedido.pedidoId(), naoEmpacotados);
    }

    return new PedidoEmpacotado(pedido.pedidoId(), empacotados);
  }
}
