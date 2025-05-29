# 🧊 Loja Empacotamento

API para empacotar pedidos de produtos em caixas, otimizando o uso de espaço com base nas dimensões dos produtos.

## 📦 Objetivo

A aplicação recebe um ou mais pedidos contendo produtos com dimensões específicas, e retorna a melhor forma de empacotá-los em caixas.

## 🚀 Tecnologias

- Java 17
- Spring Boot
- Maven
- Docker
- OpenAPI / Swagger

---

## ⚙️ Como executar

### 1. Gerar o JAR com Maven

```bash
mvn clean package
docker build -t empacotador .
docker run -p 8080:8080 empacotador
```

## 📘 Documentação Swagger

http://localhost:8083/swagger-ui.html

## ⚙️ Modelo entrada

POST /api/pedidos/empacotar
Content-Type: application/json

```
{
  "pedidos": [
    {
      "pedido_id": 1,
      "produtos": [
        {
          "produto_id": "A1",
          "dimensoes": { "altura": 10, "largura": 20, "comprimento": 30 }
        },
        {
          "produto_id": "B2",
          "dimensoes": { "altura": 15, "largura": 15, "comprimento": 15 }
        }
      ]
    }
  ]
}
```

## ⚙️ Modelo saída
```
{
  "pedidos": [
    {
      "pedido_id": 1,
      "caixas": [
        {
          "caixa_id": "CX001",
          "produtos": ["A1"],
          "observacao": null
        },
        {
          "caixa_id": "CX002",
          "produtos": ["B2"],
          "observacao": null
        }
      ]
    }
  ]
}
```