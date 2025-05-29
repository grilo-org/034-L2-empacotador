package br.com.empacotador.infrastructure.api;

import br.com.empacotador.adapter.in.web.dto.PedidoEmpacotadoResponse;
import br.com.empacotador.adapter.in.web.dto.PedidosEmpacotadosResponse;
import br.com.empacotador.adapter.in.web.dto.PedidosRequest;
import br.com.empacotador.adapter.in.web.mapper.EmpacotamentoMapper;
import br.com.empacotador.application.EmpacotadorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class EmpacotamentoController {

  private final EmpacotadorService empacotadorService;

  public EmpacotamentoController(EmpacotadorService empacotadorService) {
    this.empacotadorService = empacotadorService;
  }

  @PostMapping("/empacotar")
  public PedidosEmpacotadosResponse execute(@RequestBody PedidosRequest pedidosRequest) {
    List<PedidoEmpacotadoResponse> pedidosEmpacotados =
        pedidosRequest.pedidos().stream().map(EmpacotamentoMapper::toDomain)
            .map(empacotadorService::empacotarPedidos)
            .map(EmpacotamentoMapper::toResponse)
            .toList();

    return new PedidosEmpacotadosResponse(pedidosEmpacotados);
  }

}
