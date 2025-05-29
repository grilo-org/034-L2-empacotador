package br.com.empacotador.application;

import br.com.empacotador.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpacotadorServiceImpl implements EmpacotadorService {

  private final EmpacotadorDePedido empacotadorDePedido;

  public EmpacotadorServiceImpl() {
    List<Caixa> caixasDisponiveis = List.of(
        new Caixa("Caixa 1", new Dimensoes(30, 40, 80)),
        new Caixa("Caixa 2", new Dimensoes(80, 50, 40)),
        new Caixa("Caixa 3", new Dimensoes(50, 80, 60))
    );
    SelecionadorDeCaixa selecionadorDeCaixa = new SelecionadorDeCaixa(caixasDisponiveis);
    this.empacotadorDePedido = new EmpacotadorDePedido(selecionadorDeCaixa);
  }

  @Override
  public PedidoEmpacotado empacotarPedidos(Pedido pedido) {
    return empacotadorDePedido.empacotar(pedido);
  }
}