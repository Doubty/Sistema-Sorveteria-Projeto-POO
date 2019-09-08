/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bean.Categoria;
import excecao.ModelValidar;

/**
 *
 * @author galva
 */
public class ModelValidarCategoria {
    
    public static void validarEntradaDeDados(Categoria categoria) throws ModelValidar {

        if (categoria.getNome() == null || categoria.getNome().length() == 0 || categoria.getNome().length() > 50) {
            throw new ModelValidar("Nome Inv�lido! OBS: Campo n�o pode ser vazio e M�ximo de 50 caracteres\n");
        }
    }
    
}
