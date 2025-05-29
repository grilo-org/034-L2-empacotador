package br.com.empacotador.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record CaixaResponse(
        String caixa_id,
        List<String> produtos,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String observacao
) {
}
