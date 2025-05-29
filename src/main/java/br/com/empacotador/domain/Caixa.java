package br.com.empacotador.domain;

import java.util.List;

public record Caixa(String caixaId, Dimensoes dimensoes) {

    public boolean comporta(Produto produto) {
        List<int[]> permutations = List.of(
                new int[]{dimensoes.altura(), dimensoes.largura(), dimensoes.comprimento()},
                new int[]{dimensoes.altura(), dimensoes.comprimento(), dimensoes.largura()},
                new int[]{dimensoes.largura(), dimensoes.altura(), dimensoes.comprimento()},
                new int[]{dimensoes.largura(), dimensoes.comprimento(), dimensoes.altura()},
                new int[]{dimensoes.comprimento(), dimensoes.altura(), dimensoes.largura()},
                new int[]{dimensoes.comprimento(), dimensoes.largura(), dimensoes.altura()}
        );

        return permutations.stream().anyMatch(p ->
                produto.dimensoes().altura() < p[0] &&
                        produto.dimensoes().largura() < p[1] &&
                        produto.dimensoes().comprimento() < p[2]
        );
    }
}
