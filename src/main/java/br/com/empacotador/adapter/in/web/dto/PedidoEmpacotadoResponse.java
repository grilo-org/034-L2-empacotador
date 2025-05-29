package br.com.empacotador.adapter.in.web.dto;

import java.util.List;

public record PedidoEmpacotadoResponse(int pedido_id, List<CaixaResponse> caixas) {}
