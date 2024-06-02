/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author vitor
 */
@Entity
@Table(name = "Lista_Espera")
@NamedQueries({
    @NamedQuery(name = "ListaEspera.findAll", query = "SELECT l FROM ListaEspera l"),
    @NamedQuery(name = "ListaEspera.findByIDLista", query = "SELECT l FROM ListaEspera l WHERE l.iDLista = :iDLista"),
    @NamedQuery(name = "ListaEspera.findByPosicao", query = "SELECT l FROM ListaEspera l WHERE l.posicao = :posicao"),
    @NamedQuery(name = "ListaEspera.findByDataSolicitacao", query = "SELECT l FROM ListaEspera l WHERE l.dataSolicitacao = :dataSolicitacao")})
public class ListaEspera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Lista")
    private Integer iDLista;
    @Basic(optional = false)
    @Column(name = "Posicao")
    private int posicao;
    @Column(name = "Data_Solicitacao")
    @Temporal(TemporalType.DATE)
    private Date dataSolicitacao;
    @JoinColumn(name = "ID_Livro", referencedColumnName = "ID_Livro")
    @ManyToOne(optional = false)
    private Livro iDLivro;
    @JoinColumn(name = "ID_Usuario", referencedColumnName = "ID_Usuario")
    @ManyToOne(optional = false)
    private Usuario iDUsuario;

    public ListaEspera() {
    }

    public ListaEspera(Integer iDLista) {
        this.iDLista = iDLista;
    }

    public ListaEspera(Integer iDLista, int posicao) {
        this.iDLista = iDLista;
        this.posicao = posicao;
    }

    public Integer getIDLista() {
        return iDLista;
    }

    public void setIDLista(Integer iDLista) {
        this.iDLista = iDLista;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Livro getIDLivro() {
        return iDLivro;
    }

    public void setIDLivro(Livro iDLivro) {
        this.iDLivro = iDLivro;
    }

    public Usuario getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(Usuario iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDLista != null ? iDLista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaEspera)) {
            return false;
        }
        ListaEspera other = (ListaEspera) object;
        if ((this.iDLista == null && other.iDLista != null) || (this.iDLista != null && !this.iDLista.equals(other.iDLista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "biblioteca.entities.ListaEspera[ iDLista=" + iDLista + " ]";
    }
    
}
