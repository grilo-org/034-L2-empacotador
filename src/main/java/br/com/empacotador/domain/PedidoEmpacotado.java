package br.com.empacotador.domain;

import java.util.List;

public record PedidoEmpacotado(int pedidoId, List<ItemEmpacotado> itens) {}
