/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

/**
 *
 * @author galva
 */
public abstract class Pessoa {

    protected String nome;
    protected String sobrenome;
    protected String Cpf;

    // Implementar para retonar o nome do vendedor
    public abstract String toString();

}
