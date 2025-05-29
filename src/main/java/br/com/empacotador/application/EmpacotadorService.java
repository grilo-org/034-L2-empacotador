package br.com.empacotador.application;

import br.com.empacotador.domain.Pedido;
import br.com.empacotador.domain.PedidoEmpacotado;

public interface EmpacotadorService {
    PedidoEmpacotado empacotarPedidos(Pedido pedido);
}