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
            throw new ModelValidar("Nome Inválido! OBS: Campo não pode ser vazio e Máximo de 45 caracteres\n");
        }

        if (vendedor.getSobrenome() == null || vendedor.getSobrenome().length() == 0 || vendedor.getSobrenome().length() > 45) {
            throw new ModelValidar("Sobrenome Inválido! OBS: Campo não pode ser vazio e Máximo de 45 caracteres\n");
        }

        if (vendedor.getEmail() == null || vendedor.getEmail().length() == 0 || vendedor.getEmail().length() > 45) {
            throw new ModelValidar("E-mail Inválido! OBS: Campo não pode ser vazio e Máximo de 45 caracteres\n");
        }

        if (vendedor.getTelefone() == null || vendedor.getTelefone().length() == 0 || vendedor.getTelefone().length() > 45) {
            throw new ModelValidar("Telefone Inválido! OBS: Campo não pode ser vazio e Máximo de 20 caracteres\n");
        }

        if (vendedor.getData_nasc() == null) {
            throw new ModelValidar("Data de nascimento Inválida! OBS: Campo não pode ser vazio. \n");
        }

        if (vendedor.getSalario() + "" == null || vendedor.getSalario() + "".length() == 0 || vendedor.getSalario() < 0 || vendedor.getSalario() < 998) {
            throw new ModelValidar("Salário Inválido! OBS: Campo não pode ser vazio e salário >= 998.\n");
        }

        if (vendedor.getEndereco() == null || vendedor.getEndereco().length() == 0 || vendedor.getEndereco().length() > 50) {
            throw new ModelValidar("Endereço Inválido! OBS: Campo não pode ser vazio e Máximo de 50 caracteres\n");
        }

        if (vendedor.getCpf() == null || vendedor.getCpf().length() == 0 || vendedor.getCpf().length() > 11) {
            throw new ModelValidar("CPF Inválido! OBS: Campo não pode ser vazio e Máximo de 11 caracteres (Digite apenas números)\n");
        }

        if (vendedor.getTurno() == null || vendedor.getTurno().length() == 0) {
            throw new ModelValidar("Turno Inválido! OBS: Campo não pode ser vazio.\n");
        }

        if (vendedor.getData_inicio() == null) {
            throw new ModelValidar("Data de inicío Inválida! OBS: Campo não pode ser vazio. \n");
        }

        if (vendedor.getCarga_horaria() + "" == null || vendedor.getCarga_horaria() + "".length() == 0 || vendedor.getCarga_horaria() < 0) {
            throw new ModelValidar("Carga Horária Inválida! OBS: Campo não pode ser vazio e carga horária tem que ser >= 0\n");
        }
        
        if (vendedor.getId_usuario() + "" == null || vendedor.getId_usuario() + "".length() == 0) {
            throw new ModelValidar("ID de usuário Inválido! OBS: Campo não pode ser vazio. \n");
        }
       
    }

}
