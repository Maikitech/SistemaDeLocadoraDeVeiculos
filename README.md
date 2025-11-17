# Sistema de Locadora de Veículos (POO2)

Este projeto é um sistema desktop completo para gestão de uma locadora de veículos, desenvolvido como trabalho final da disciplina de Programação Orientada a Objetos II. O sistema utiliza **Java Swing** para a interface gráfica e **Hibernate (JPA)** para persistência de dados em banco **H2**.

## 📋 Funcionalidades

O sistema atende aos seguintes requisitos funcionais:

* **Gestão de Usuários:** Cadastro, edição e exclusão de clientes (com validação de e-mail e telefone).
* **Gestão de Veículos:** Cadastro de frota com validação de placa (Mercosul/Antiga) e categorização por cor.
* **Controle de Aluguéis:**
    * **Abertura:** Seleção intuitiva de cliente e veículo, definição de datas (com *Date Picker*) e quilometragem inicial.
    * **Fechamento:** Atualização do aluguel para informar quilometragem final e alterar status (Aberto/Fechado).
* **Visualização:** Listagens com ordenação automática, indicadores visuais de cor do veículo e ícones de status do aluguel.

## 🛠️ Tecnologias e Requisitos Técnicos

O projeto foi construído seguindo rigorosos padrões técnicos:

* **Linguagem:** Java 17+
* **Interface Gráfica:** Java Swing (Desenhado com NetBeans/Matisse).
* **Tema:** [FlatLaf](https://www.formdev.com/flatlaf/) (Look and Feel moderno).
* **Persistência:** Hibernate ORM + JPA.
* **Banco de Dados:** H2 Database (Modo Arquivo/Embedded).
* **Componentes Extras:** [LGoodDatePicker](https://github.com/LGoodDatePicker/LGoodDatePicker) (para seleção de datas).
* **Arquitetura:** Separação em camadas (View, Entity, DAO, Util).

### Destaques da Implementação
* **Renderers Personalizados:** Células da tabela pintadas com a cor real do veículo e ícones dinâmicos para o status do aluguel.
* **Validação com Regex:** Implementada para placas de veículos e e-mails.
* **Sincronização:** Uso de interfaces (`EntidadeListener`) para atualizar as tabelas principais automaticamente após cadastros.

## 🚀 Como Executar

### Pré-requisitos
* Java JDK 17 ou superior.
* Maven.
* NetBeans IDE (recomendado).

### Passos
1.  Clone este repositório:
    ```bash
    git clone [https://github.com/Maikitech/SistemaDeLocadoraDeVeiculos.git](https://github.com/Maikitech/SistemaDeLocadoraDeVeiculos.git)
    ```
2.  Abra o projeto no NetBeans.
3.  O Maven baixará automaticamente as dependências (Hibernate, FlatLaf, DatePicker).
4.  Execute o arquivo principal:
    `com.mycompany.trabalhopoo2final.main.PontoDeEntrada`

*Nota: O banco de dados H2 será criado automaticamente na pasta `./dados/testdb` na primeira execução.*

## 📸 Screenshots

### Tela Principal (Listagem de Aluguéis)
![Tela Principal](https://github.com/user-attachments/assets/23de72ba-284a-45a3-a29b-3f43e7b83a7e)
*Exibe a listagem com ícones de status e cores dos veículos.*

### Cadastro de Aluguel
![Cadastro Aluguel](https://github.com/user-attachments/assets/b9827f78-29ff-485d-be68-656afcd76f6a)
*Demonstração dos componentes DatePicker e validação de campos.*

## 🤖 Uso de Inteligência Artificial

Conforme solicitado na especificação do trabalho, ferramentas de IA foram utilizadas para auxiliar no desenvolvimento ("Pair Programming").

* **Modelo Utilizado:** Google Gemini 1.5 Pro.
* **Aplicação:** A IA foi utilizada para refatorar o código gerado pelo NetBeans (Matisse), implementar a lógica dos *Renderers* personalizados, criar a arquitetura DAO e corrigir bugs de sincronização entre o *Design* e o *Source* do Swing.

---
**Desenvolvido por Maiki Scalvi**
Instituto Federal de Educação, Ciência e Tecnologia do Rio Grande do Sul (IFRS) Campus Veranópolis.
