-- Inserts para a tabela fornecedor
INSERT INTO public.fornecedor (id_fornecedor, cnpj, email, nome, telefone)
VALUES 
    ('123e4567-e89b-12d3-a456-426614174001', '12345678901234', 'fornecedor1@example.com', 'Fornecedor 1', '123-456-7890'),
    ('223e4567-e89b-12d3-a456-426614174002', '56789012345678', 'fornecedor2@example.com', 'Fornecedor 2', '456-789-0123'),
    ('323e4567-e89b-12d3-a456-426614174003', '90123456789012', 'fornecedor3@example.com', 'Fornecedor 3', '789-012-3456');

-- Inserts para a tabela endereco
INSERT INTO public.endereco (id_endereco, bairro, cep, estado, logradouro, municipio, numero, pais, referencia, id_fornecedor)
VALUES 
    ('123e4567-e89b-12d3-a456-426614174101', 'Centro', 12345678, 'SP', 'Rua A', 'São Paulo', '100', 'Brasil', 'Próximo ao banco', '123e4567-e89b-12d3-a456-426614174001'),
    ('223e4567-e89b-12d3-a456-426614174102', 'Bairro 1', 87654321, 'RJ', 'Avenida B', 'Rio de Janeiro', '200', 'Brasil', 'Próximo à escola', '223e4567-e89b-12d3-a456-426614174002'),
    ('323e4567-e89b-12d3-a456-426614174103', 'Subúrbio', 56789012, 'MG', 'Travessa C', 'Belo Horizonte', '300', 'Brasil', 'Próximo à praça', '323e4567-e89b-12d3-a456-426614174003');

-- Inserts para a tabela produto
INSERT INTO public.produto (id_produto, altura, comprimento, descricao, ean, largura, marca, nome, peso)
VALUES 
    ('123e4567-e89b-12d3-a456-426614174201', 10.5, 15.2, 'Produto 1', '1234567890123', 8.2, 'Marca A', 'Produto A', 0.5),
    ('223e4567-e89b-12d3-a456-426614174202', 12.8, 18.3, 'Produto 2', '2345678901234', 9.1, 'Marca B', 'Produto B', 0.7),
    ('323e4567-e89b-12d3-a456-426614174203', 9.3, 14.6, 'Produto 3', '3456789012345', 7.5, 'Marca C', 'Produto C', 0.6);

-- Inserts para a tabela estoque
INSERT INTO public.estoque (id_estoque, andar, fila, localizacao, prateleira, rua)
VALUES 
    ('123e4567-e89b-12d3-a456-426614176001', '1', 'A', 'Prédio 1', 'A-1', 'Rua 1'),
    ('223e4567-e89b-12d3-a456-426614176002', '2', 'B', 'Prédio 2', 'B-2', 'Rua 2'),
    ('323e4567-e89b-12d3-a456-426614176003', '3', 'C', 'Prédio 3', 'C-3', 'Rua 3');

-- Inserts para a tabela usuario
INSERT INTO public.usuario (id_usuario, administrador, cpf, email, nome, senha)
VALUES 
    ('123e4567-e89b-12d3-a456-426614175001', true, '12345678900', 'usuario1@example.com', 'Usuário 1', 'senha123'),
    ('223e4567-e89b-12d3-a456-426614175002', false, '23456789001', 'usuario2@example.com', 'Usuário 2', 'senha456'),
    ('323e4567-e89b-12d3-a456-426614175003', true, '34567890012', 'usuario3@example.com', 'Usuário 3', 'senha789');

-- Inserts para a tabela saida
INSERT INTO public.saida (id_saida, data_saida, valor_venda, id_usuario)
VALUES 
    ('123e4567-e89b-12d3-a456-426614175101', '2023-04-10', 80.00, '123e4567-e89b-12d3-a456-426614175001'),
    ('223e4567-e89b-12d3-a456-426614175102', '2023-05-20', 120.50, '223e4567-e89b-12d3-a456-426614175002'),
    ('323e4567-e89b-12d3-a456-426614175103', '2023-06-25', 150.75, '323e4567-e89b-12d3-a456-426614175003');

-- Inserts para a tabela movimento
INSERT INTO public.movimento (id_movimento, data_movimentacao, id_usuario)
VALUES 
    ('123e4567-e89b-12d3-a456-426614175201', '2023-04-12', '123e4567-e89b-12d3-a456-426614175001'),
    ('223e4567-e89b-12d3-a456-426614175202', '2023-05-22', '223e4567-e89b-12d3-a456-426614175002'),
    ('323e4567-e89b-12d3-a456-426614175203', '2023-06-27', '323e4567-e89b-12d3-a456-426614175003');

-- Inserts para a tabela fornecedor_produto
INSERT INTO public.fornecedor_produto (id_fornecedor, id_produto)
VALUES 
    ('123e4567-e89b-12d3-a456-426614174001', '123e4567-e89b-12d3-a456-426614174201'),
    ('223e4567-e89b-12d3-a456-426614174002', '223e4567-e89b-12d3-a456-426614174202'),
    ('323e4567-e89b-12d3-a456-426614174003', '323e4567-e89b-12d3-a456-426614174203');

-- Inserts para a tabela entrada
INSERT INTO public.entrada (id_entrada, custo_aquisicao, data_entrada, id_usuario)
VALUES 
    ('123e4567-e89b-12d3-a456-426614175001', 50.00, '2023-01-15', '123e4567-e89b-12d3-a456-426614175001'),
    ('223e4567-e89b-12d3-a456-426614175002', 75.50, '2023-02-20', '223e4567-e89b-12d3-a456-426614175002'),
    ('323e4567-e89b-12d3-a456-426614175003', 100.75, '2023-03-25', '323e4567-e89b-12d3-a456-426614175003');

-- Inserts para a tabela item_estoque
INSERT INTO public.item_estoque (id_item_estoque, id_entrada, id_estoque, id_produto, id_saida)
VALUES 
    ('123e4567-e89b-12d3-a456-426614176101', '123e4567-e89b-12d3-a456-426614175001', '123e4567-e89b-12d3-a456-426614176001', '123e4567-e89b-12d3-a456-426614174201', '123e4567-e89b-12d3-a456-426614175101'),
    ('223e4567-e89b-12d3-a456-426614176102', '223e4567-e89b-12d3-a456-426614175002', '223e4567-e89b-12d3-a456-426614176002', '223e4567-e89b-12d3-a456-426614174202', '223e4567-e89b-12d3-a456-426614175102'),
    ('323e4567-e89b-12d3-a456-426614176103', '323e4567-e89b-12d3-a456-426614175003', '323e4567-e89b-12d3-a456-426614176003', '323e4567-e89b-12d3-a456-426614174203', '323e4567-e89b-12d3-a456-426614175103');

