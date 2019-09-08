/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bean.Fornecedor;
import excecao.ModelValidar;

/**
 *
 * @author galva
 */
public class ModelValidarFornecedor {
    
     public static void validarEntradaDeDados(Fornecedor fornecedor) throws ModelValidar {

        if (fornecedor.getNome() == null || fornecedor.getNome().length() == 0 || fornecedor.getNome().length() > 45) {
            throw new ModelValidar("Nome Inválido! OBS: Campo não pode ser vazio e Máximo de 45 caracteres\n");
        }

        if (fornecedor.getCnpj() == null || fornecedor.getCnpj().length() == 0 || fornecedor.getCnpj().length() > 14) {
            throw new ModelValidar("CNPJ Inválido! OBS: Campo não pode ser vazio e Máximo de 14 caracteres.\n");
        }

        if (fornecedor.getEndereco() == null || fornecedor.getEndereco().length() == 0 || fornecedor.getEndereco().length() > 50) {
            throw new ModelValidar("Endereço Inválido! OBS: Campo não pode ser vazio e Máximo de 50 caracteres.\n");
        }

        if (fornecedor.getTelefone() == null || fornecedor.getTelefone().length() == 0) {
            throw new ModelValidar("Telefone Inválido! OBS: Campo não pode ser vazio e Máximo de 20 caracteres.\n");
        }
    }
    
}
