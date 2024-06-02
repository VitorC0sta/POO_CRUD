/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author vitor
 */
@Entity
@Table(name = "Usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIDUsuario", query = "SELECT u FROM Usuario u WHERE u.iDUsuario = :iDUsuario"),
    @NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome = :nome"),
    @NamedQuery(name = "Usuario.findByRa", query = "SELECT u FROM Usuario u WHERE u.ra = :ra"),
    @NamedQuery(name = "Usuario.findByTipo", query = "SELECT u FROM Usuario u WHERE u.tipo = :tipo"),
    @NamedQuery(name = "Usuario.findByAtivo", query = "SELECT u FROM Usuario u WHERE u.ativo = :ativo")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Usuario")
    private Integer iDUsuario;
    @Basic(optional = false)
    @Column(name = "Nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "RA")
    private String ra;
    @Basic(optional = false)
    @Column(name = "Tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "Ativo")
    private boolean ativo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDUsuario")
    private List<Emprestimo> emprestimoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDUsuario")
    private List<ListaEspera> listaEsperaList;

    public Usuario() {
    }

    public Usuario(String nome, String ra, String tipo, boolean ativo) {
        this.nome = nome;
        this.ra = ra;
        this.tipo = tipo;
        this.ativo = ativo;
    }

    public Integer getIDUsuario() {
        return iDUsuario;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Emprestimo> getEmprestimoList() {
        return emprestimoList;
    }

    public void setEmprestimoList(List<Emprestimo> emprestimoList) {
        this.emprestimoList = emprestimoList;
    }

    public List<ListaEspera> getListaEsperaList() {
        return listaEsperaList;
    }

    public void setListaEsperaList(List<ListaEspera> listaEsperaList) {
        this.listaEsperaList = listaEsperaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDUsuario != null ? iDUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.iDUsuario == null && other.iDUsuario != null) || (this.iDUsuario != null && !this.iDUsuario.equals(other.iDUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "biblioteca.entities.Usuario[ iDUsuario=" + iDUsuario + " ]";
    }
    
}
