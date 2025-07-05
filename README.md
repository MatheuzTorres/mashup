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

## 📊 Simulações de Teste

Foram realizadas simulações com diferentes quantidades de caixas e distâncias, conforme exigido no enunciado do trabalho.

---

### ✔️ Simulação 1 – 8 caixas, 50 km

GET http://localhost:8080/api/mashup?caixas=8&distancia=50

- Veículo usado: Caminhão
- Preço: 8 × R$50,00 = R$ 400,00 (sem desconto)
- Frete: 50 × R$20 + R$200 = R$ 1.200,00
- Lucro: +55%
- Desconto por volume: não aplicável
- Imposto: +27%

🧾 **Valor final aproximado**: R$ 2.619,60

---

### ✔️ Simulação 2 – 120 caixas, 120 km

GET http://localhost:8080/api/mashup?caixas=120&distancia=120

- Veículo usado: Caminhão
- Preço com 22% de desconto: R$ 4.680,00
- Frete (com desconto nos 20km extras): R$ 2.520,00
- Subtotal: R$ 7.200,00
- Lucro +55%: R$ 11.160,00
- Desconto volume (>50): –7.5% → R$ 10.319,00
- Imposto +27%: R$ 13.110,21

🧾 **Valor final**: R$ 13.110,21 ✅

---

### ✔️ Simulação 3 – 350 caixas, 150 km

GET http://localhost:8080/api/mashup?caixas=350&distancia=150

- Veículo usado: Carreta
- Preço com 22% de desconto: R$ 13.650,00
- Frete com desconto: R$ 5.700,00
- Subtotal: R$ 19.350,00
- Lucro +55%: R$ 30.992,50
- Desconto volume (>300): –12% → R$ 27.273,40
- Imposto +27%: R$ 34.636,22

🧾 **Valor final aproximado**: R$ 34.636,22 ✅
