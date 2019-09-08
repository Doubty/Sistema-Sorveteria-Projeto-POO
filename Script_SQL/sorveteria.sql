create table usuario(
id_usuario serial primary key, 
	nome_login varchar(30) not null,
	senha_login varchar(16) not null,
     nivel int not null
);

/* setando o usuário administrador */
insert into usuario (nome_login, senha_login, nivel) values ('admin', 'admin', 1);

create table vendedor(
id_vendedor serial primary key, 
	carga_horaria int not null check (carga_horaria > 0), 
	data_inicio date not null,
	turno varchar(10) not null,
	cpf varchar(11) not null,
	salario float not null check(salario >= 998),
	data_nasc date not null,
	endereco varchar(50) not null,
	telefone varchar(20) not null, 
	email varchar(45) not null,
	nome varchar(45) not null, 
	sobrenome varchar(45) not null,
	id_usuario int references usuario(id_usuario)
);

create table categoria(
id_categoria serial primary key, 
	nome varchar(50) not null
);

/*Adicionado umas categorias padrão*/
insert into categoria (nome) values ('Picolé'),('Sorvete'), ('Dindin');

create table fornecedor(
id_fornecedor serial primary key, 
	cnpj varchar(14) not null,
	nome varchar(45) not null, 
	endereco varchar(50) not null,
	telefone varchar(20) not null
);

create table produto(
id_produto serial primary key, 
	nome varchar(50) not null,
	preco float not null check(preco > 0),
	descricao varchar(100) not null,
     quantidade int not null,
	validade date not null, 
	id_categoria int references categoria(id_categoria),
	id_fornecedor int references fornecedor(id_fornecedor)
);

create table venda(
id_venda serial primary key,
	data date not null,
	total_venda float not null,
	pagamento boolean not null,
	id_vendedor int references vendedor (id_vendedor)
);

create table item_venda(
id_item_venda serial primary key,
	quantidade int not null check(quantidade >= 1),
	total_itens float not null check(total_itens > 0),
	id_venda int references venda(id_venda),
	id_produto int references produto(id_produto)
);

/*Visão criada para produtos em estoque*/

create view relatorio_estoque as (SELECT p.id_produto as Código, p.nome as Produto, p.preco as Preço, p.validade as Validade, p.quantidade as Quantidade, c.nome as Categoria, f.nome as Fornecedor   FROM produto as p inner join categoria as c using(id_categoria) inner join fornecedor as f using (id_fornecedor));

create table ex_vendedores(
id_ex_vendedores int primary key, 
	nome varchar(45), 
	sobrenome varchar(45), 
	data_demissao date
);

 /*Regra para ex_vendedores*/

create or replace rule ex_vend as on delete to vendedor do insert into ex_vendedores values
(old.id_vendedor, old.nome, old.sobrenome, current_date);

/*Criação da função de desconto de 10%*/

create function desconto_10(float) returns float
as 'select ($1*0.90);' language 'sql';

/*Criando um log para a tabela de produtos*/

create table log_produtos(
 data date,
 usuario varchar(45),
	modificacao varchar(10)	
);

/*  create language plpgsql  pode ser necessário!!!*/

/* criação da função que será vinculada ao trigger */
create function func_log() returns trigger as $$
begin 
insert into log_produtos(data, usuario, modificacao) values
(current_date, user, TG_OP);
return new;
end;
$$ language 'plpgsql';

/*Criação do trigger*/
create trigger produto_log after insert or update or delete on produto
for each row execute procedure func_log();




