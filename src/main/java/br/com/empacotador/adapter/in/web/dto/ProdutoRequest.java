package br.com.empacotador.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProdutoRequest(@JsonProperty("produto_id") String produtoId, DimensoesRequest dimensoes) {}
