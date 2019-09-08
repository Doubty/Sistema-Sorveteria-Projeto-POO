/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import bean.Fornecedor;
import bean.Vendedor;
import excecao.ModelValidar;

/**
 *
 * @author galva
 */
public class ModelValidarVendedor {

    public static void validarEntradaDeDados(Vendedor vendedor) throws ModelValidar {

        if (vendedor.getNome() == null || vendedor.getNome().length() == 0 || vendedor.getNome().length() > 45) {
            throw new ModelValidar("Nome Inv�lido! OBS: Campo n�o pode ser vazio e M�ximo de 45 caracteres\n");
        }

        if (vendedor.getSobrenome() == null || vendedor.getSobrenome().length() == 0 || vendedor.getSobrenome().length() > 45) {
            throw new ModelValidar("Sobrenome Inv�lido! OBS: Campo n�o pode ser vazio e M�ximo de 45 caracteres\n");
        }

        if (vendedor.getEmail() == null || vendedor.getEmail().length() == 0 || vendedor.getEmail().length() > 45) {
            throw new ModelValidar("E-mail Inv�lido! OBS: Campo n�o pode ser vazio e M�ximo de 45 caracteres\n");
        }

        if (vendedor.getTelefone() == null || vendedor.getTelefone().length() == 0 || vendedor.getTelefone().length() > 45) {
            throw new ModelValidar("Telefone Inv�lido! OBS: Campo n�o pode ser vazio e M�ximo de 20 caracteres\n");
        }

        if (vendedor.getData_nasc() == null) {
            throw new ModelValidar("Data de nascimento Inv�lida! OBS: Campo n�o pode ser vazio. \n");
        }

        if (vendedor.getSalario() + "" == null || vendedor.getSalario() + "".length() == 0 || vendedor.getSalario() < 0 || vendedor.getSalario() < 998) {
            throw new ModelValidar("Sal�rio Inv�lido! OBS: Campo n�o pode ser vazio e sal�rio >= 998.\n");
        }

        if (vendedor.getEndereco() == null || vendedor.getEndereco().length() == 0 || vendedor.getEndereco().length() > 50) {
            throw new ModelValidar("Endere�o Inv�lido! OBS: Campo n�o pode ser vazio e M�ximo de 50 caracteres\n");
        }

        if (vendedor.getCpf() == null || vendedor.getCpf().length() == 0 || vendedor.getCpf().length() > 11) {
            throw new ModelValidar("CPF Inv�lido! OBS: Campo n�o pode ser vazio e M�ximo de 11 caracteres (Digite apenas n�meros)\n");
        }

        if (vendedor.getTurno() == null || vendedor.getTurno().length() == 0) {
            throw new ModelValidar("Turno Inv�lido! OBS: Campo n�o pode ser vazio.\n");
        }

        if (vendedor.getData_inicio() == null) {
            throw new ModelValidar("Data de inic�o Inv�lida! OBS: Campo n�o pode ser vazio. \n");
        }

        if (vendedor.getCarga_horaria() + "" == null || vendedor.getCarga_horaria() + "".length() == 0 || vendedor.getCarga_horaria() < 0) {
            throw new ModelValidar("Carga Hor�ria Inv�lida! OBS: Campo n�o pode ser vazio e carga hor�ria tem que ser >= 0\n");
        }
        
        if (vendedor.getId_usuario() + "" == null || vendedor.getId_usuario() + "".length() == 0) {
            throw new ModelValidar("ID de usu�rio Inv�lido! OBS: Campo n�o pode ser vazio. \n");
        }
       
    }

}
