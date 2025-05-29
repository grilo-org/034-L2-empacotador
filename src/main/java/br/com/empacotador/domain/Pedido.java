package br.com.empacotador.domain;

import java.util.List;

public record Pedido(int pedidoId, List<Produto> produtos) {
}