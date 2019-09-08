create table usuario(
id_usuario serial primary key, 
	nome_login varchar(30) not null,
	senha_login varchar(16) not null
);

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

create table dono(
	id_dono serial primary key,
	cpf varchar(11) not null,
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

create table estoque(
id_estoque serial primary key, 
	qtd_estoque int not null
);

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
	validade date not null, 
	id_categoria int references categoria(id_categoria),
	id_estoque int references estoque(id_estoque),
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

create view relatorio_estoque as (SELECT p.id_produto as Código, p.nome as Produto, p.preco as Preço, p.validade as Validade, p.quantidade as Quantidade, c.nome as Categoria, f.nome as Fornecedor   FROM produto as p inner join categoria as c using(id_categoria) inner join fornecedor as f using (id_fornecedor))



