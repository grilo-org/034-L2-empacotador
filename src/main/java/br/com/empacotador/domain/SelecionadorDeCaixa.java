package br.com.empacotador.domain;

import java.util.List;
import java.util.Optional;

public class SelecionadorDeCaixa {
  private final List<Caixa> caixasDisponiveis;

  public SelecionadorDeCaixa(List<Caixa> caixasDisponiveis) {
    this.caixasDisponiveis = caixasDisponiveis;
  }

  public Optional<Caixa> selecionarCaixaPara(Produto produto) {
    return caixasDisponiveis.stream()
        .filter(caixa -> caixa.comporta(produto))
        .findFirst();
  }
}

