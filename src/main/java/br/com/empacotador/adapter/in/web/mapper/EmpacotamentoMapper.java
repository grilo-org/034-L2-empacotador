package br.com.empacotador.adapter.in.web.mapper;

import br.com.empacotador.adapter.in.web.dto.CaixaResponse;
import br.com.empacotador.adapter.in.web.dto.PedidoEmpacotadoResponse;
import br.com.empacotador.adapter.in.web.dto.PedidoRequest;
import br.com.empacotador.domain.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class EmpacotamentoMapper {

  private EmpacotamentoMapper() {
  }

  public static PedidoEmpacotadoResponse toResponse(PedidoEmpacotado pedido) {
    Map<String, List<String>> produtosPorCaixa = pedido.itens().stream()
        .filter(item -> item.produtoId() != null)
        .collect(Collectors.groupingBy(
            ItemEmpacotado::caixaId,
            Collectors.mapping(ItemEmpacotado::produtoId, Collectors.toList())
        ));

    List<CaixaResponse> caixas = produtosPorCaixa.entrySet().stream()
        .map(entry -> new CaixaResponse(entry.getKey(), entry.getValue(), null))
        .toList();

    return new PedidoEmpacotadoResponse(pedido.pedidoId(), caixas);
  }

  public static Pedido toDomain(PedidoRequest request) {
    List<Produto> produtos = request.produtos().stream()
        .map(produtoRequest -> new Produto(
            produtoRequest.produtoId(),
            new Dimensoes(produtoRequest.dimensoes().altura(), produtoRequest.dimensoes().largura(), produtoRequest.dimensoes().comprimento())
        ))
        .toList();
    return new Pedido(request.pedidoId(), produtos);
  }
}
