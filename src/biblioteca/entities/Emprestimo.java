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
@Table(name = "Emprestimo")
@NamedQueries({
    @NamedQuery(name = "Emprestimo.findAll", query = "SELECT e FROM Emprestimo e"),
    @NamedQuery(name = "Emprestimo.findByIDEmprestimo", query = "SELECT e FROM Emprestimo e WHERE e.iDEmprestimo = :iDEmprestimo"),
    @NamedQuery(name = "Emprestimo.findByDataSolicitacao", query = "SELECT e FROM Emprestimo e WHERE e.dataSolicitacao = :dataSolicitacao"),
    @NamedQuery(name = "Emprestimo.findByDataDevolucao", query = "SELECT e FROM Emprestimo e WHERE e.dataDevolucao = :dataDevolucao"),
    @NamedQuery(name = "Emprestimo.findByDataMaxDevolucao", query = "SELECT e FROM Emprestimo e WHERE e.dataMaxDevolucao = :dataMaxDevolucao")})
public class Emprestimo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Emprestimo")
    private Integer iDEmprestimo;
    @Basic(optional = false)
    @Column(name = "Data_Solicitacao")
    @Temporal(TemporalType.DATE)
    private Date dataSolicitacao;
    @Basic(optional = false)
    @Column(name = "Data_Devolucao")
    @Temporal(TemporalType.DATE)
    private Date dataDevolucao;
    @Basic(optional = false)
    @Column(name = "Data_Max_Devolucao")
    @Temporal(TemporalType.DATE)
    private Date dataMaxDevolucao;
    @JoinColumn(name = "ID_Usuario", referencedColumnName = "ID_Usuario")
    @ManyToOne(optional = false)
    private Usuario iDUsuario;
    @JoinColumn(name = "ID_Livro", referencedColumnName = "ID_Livro")
    @ManyToOne(optional = false)
    private Livro iDLivro;

    public Emprestimo() {
    }

    public Emprestimo(Integer iDEmprestimo) {
        this.iDEmprestimo = iDEmprestimo;
    }

    public Emprestimo(Integer iDEmprestimo, Date dataSolicitacao, Date dataDevolucao, Date dataMaxDevolucao) {
        this.iDEmprestimo = iDEmprestimo;
        this.dataSolicitacao = dataSolicitacao;
        this.dataDevolucao = dataDevolucao;
        this.dataMaxDevolucao = dataMaxDevolucao;
    }

    public Integer getIDEmprestimo() {
        return iDEmprestimo;
    }

    public void setIDEmprestimo(Integer iDEmprestimo) {
        this.iDEmprestimo = iDEmprestimo;
    }

    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public Date getDataMaxDevolucao() {
        return dataMaxDevolucao;
    }

    public void setDataMaxDevolucao(Date dataMaxDevolucao) {
        this.dataMaxDevolucao = dataMaxDevolucao;
    }

    public Usuario getIDUsuario() {
        return iDUsuario;
    }

    public void setIDUsuario(Usuario iDUsuario) {
        this.iDUsuario = iDUsuario;
    }

    public Livro getIDLivro() {
        return iDLivro;
    }

    public void setIDLivro(Livro iDLivro) {
        this.iDLivro = iDLivro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDEmprestimo != null ? iDEmprestimo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Emprestimo)) {
            return false;
        }
        Emprestimo other = (Emprestimo) object;
        if ((this.iDEmprestimo == null && other.iDEmprestimo != null) || (this.iDEmprestimo != null && !this.iDEmprestimo.equals(other.iDEmprestimo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "biblioteca.entities.Emprestimo[ iDEmprestimo=" + iDEmprestimo + " ]";
    }
    
}
