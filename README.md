# controle-estoque

# Sobre o projeto
O objetivo geral do sistema é oferecer um controle de estoque de produtos para oficinas mecânicas. Nesse segmento é comum a utilização de peças ou outros produtos para reposição em reparos automotivos. Esses produtos geralmente são mantidos em estoque e a automação do estoque possibilita um melhor controle, em especial no que se refere à localização de produtos, a ausência de algum produto e até mesmo o lucro sobre a venda. 

Especificamente o sistema permite: (i) Manter fornecedor e seu respectivo endereço; (ii) Possibilitar o controle de estoque por meio da manutenção de entradas, saídas e realocação de produtos dentro do estoque (iii) Emitir os seguintes relatórios de estoque: relatório de entrada; movimentação – alteração de localização; relatório de saída; lucro; tempo que o produto ficou estocado. 

# Como executar o projeto
- renomear o arquivo 'src/main/resources/application.properties.example' para 'application.properties' ou criar esse arquivo;
- nele alterar as propriedades para conseguir conectar corretamente no seu database;
- abrir o terminal na pasta do projeto;
- mvn clean install;
- mvn spring-boot:run;
