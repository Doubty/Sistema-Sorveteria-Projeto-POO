/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bean.Fornecedor;
import bean.Usuario;
import excecao.ModelValidar;

/**
 *
 * @author galva
 */
public class ModelValidarUsuario {
    
   public static void validarEntradaDeDados(Usuario usuario) throws ModelValidar {

        if (usuario.getUser() == null || usuario.getUser().length() == 0 || usuario.getUser().length() > 30) {
            throw new ModelValidar("Nome de login Inv�lido! OBS: Campo n�o pode ser vazio e M�ximo de 30 caracteres\n");
        }

        if (usuario.getSenha() == null || usuario.getSenha().length() == 0 || usuario.getSenha().length() > 16) {
            throw new ModelValidar("Senha Inv�lida! OBS: Campo n�o pode ser vazio e M�ximo de 16 caracteres\n");
        }

        if (usuario.getNivel()+"" == null || usuario.getNivel()+"".length() == 0 ) {
            if(usuario.getNivel() > 1 || usuario.getNivel() < 0){
            throw new ModelValidar("N�vel de acesso Inv�lido! OBS: Campo n�o pode ser vazio e M�ximo de 1 digito, onde 0 - Vendedor e 1 - Admin\n");
        }}
    }
}
