package br.com.empacotador.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PedidoRequest(@JsonProperty("pedido_id") int pedidoId, List<ProdutoRequest> produtos) {}