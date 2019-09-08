

package model;

import bean.Produto;
import excecao.ModelValidar;


public class ModelValidarProduto {

    public static void validarEntradaDeDados(Produto produto) throws ModelValidar {

        if (produto.getNome() == null || produto.getNome().length() == 0 || produto.getNome().length() > 45) {
            throw new ModelValidar("Nome Inválido! OBS: Campo não pode ser vazio e Máximo de 50 caracteres\n");
        }

        if (produto.getPreco() + "" == null || produto.getPreco() + "".length() == 0 || produto.getPreco() < 0) {
            throw new ModelValidar("Preco Inválido! OBS: Campo não pode ser vazio e não pode ser < 0.\n");
        }

        if (produto.getDescricao() == null || produto.getDescricao().length() == 0 || produto.getDescricao().length() > 100) {
            throw new ModelValidar("Descrição Inválida! OBS: Campo não pode ser vazio e Máximo de 100 caracteres.\n");
        }

        if (produto.getQuantidade() + "" == null || produto.getQuantidade() + "".length() == 0 || produto.getQuantidade() < 0) {
            throw new ModelValidar("Quantidade Inválida! OBS: Campo não pode ser vazio e quantidade não pode ser < 0\n");
        }

        if (produto.getValidade() + "" == null) {
            throw new ModelValidar("Validade Inválida! OBS: Campo não pode ser vazio.\n");
        }

        if (produto.getCategoria() == null) {
            throw new ModelValidar("Categoria Inválida! OBS: Campo não pode ser vazio.\n");
        }
        
         if (produto.getFornecedor() == null) {
            throw new ModelValidar("Fornecedor Inválido! OBS: Campo não pode ser vazio.\n");
        }

    }

}
