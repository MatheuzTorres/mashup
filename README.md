# 🧮 Mashup – Cálculo de Venda Final

Este serviço REST integra os serviços de **cálculo do preço do tomate** e **cálculo do frete**, aplicando as regras comerciais de lucro, desconto por volume e impostos para determinar o **valor final da venda**.

Projeto desenvolvido para a disciplina **Sistemas Web 3** – UFRRJ  
Aluno: **Matheus Torres**

---

## 🧰 Tecnologias Utilizadas

- Java 17
- Spring Boot
- REST
- WebClient (REST + GraphQL)
- Maven

---

## 🎯 Objetivo

Criar um **serviço mashup** que:

1. Consome o serviço REST `precotomate`
2. Consome o serviço GraphQL `frete`
3. Integra os dois valores para:
   - Aplicar 55% de lucro
   - Aplicar desconto extra por volume
   - Adicionar 27% de imposto
4. Retorna o **valor final da venda**

---

## 📊 Regras Comerciais

### 💰 Lucro
- Aplicado sobre o custo (preço + frete): **+55%**

### 🎁 Desconto por volume (após lucro):
| Quantidade | Desconto |
|------------|----------|
| >50        | 7.5%     |
| >300       | 12%      |

### 💸 Imposto
- Aplicado no final: **+27%** sobre o valor com desconto

### 🚛 Tipo de veículo (usado no frete):
| Caixas      | Veículo  |
|-------------|----------|
| até 250     | Caminhão |
| acima de 250| Carreta  |

---

## 🚀 Como Executar o Projeto

### 1. Clonar o repositório

```
git clone https://github.com/MatheuzTorres/mashup.git
cd mashup
```
2. Rodar com Maven
```
./mvnw spring-boot:run
```
Ou:
```
mvn spring-boot:run
```
⚠️ Certifique-se de que os serviços precotomate (porta 8081) e frete (porta 8082) também estejam em execução.

📲 Como Usar a API
Endpoint:
```
GET /api/mashup?caixas={quantidade}&distancia={km}
```
caixas: número de caixas de tomate
distancia: distância da entrega em km

🔍 Exemplo de Requisição
```
http://localhost:8080/api/mashup?caixas=120&distancia=120
````
Resposta esperada:
13110.21

