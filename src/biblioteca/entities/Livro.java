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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author vitor
 */
@Entity
@Table(name = "Livro")
@NamedQueries({
    @NamedQuery(name = "Livro.findAll", query = "SELECT l FROM Livro l"),
    @NamedQuery(name = "Livro.findByIDLivro", query = "SELECT l FROM Livro l WHERE l.iDLivro = :iDLivro"),
    @NamedQuery(name = "Livro.findByTitulo", query = "SELECT l FROM Livro l WHERE l.titulo = :titulo"),
    @NamedQuery(name = "Livro.findByAutor", query = "SELECT l FROM Livro l WHERE l.autor = :autor"),
    @NamedQuery(name = "Livro.findByEditora", query = "SELECT l FROM Livro l WHERE l.editora = :editora"),
    @NamedQuery(name = "Livro.findByAnoPublicacao", query = "SELECT l FROM Livro l WHERE l.anoPublicacao = :anoPublicacao"),
    @NamedQuery(name = "Livro.findByGenero", query = "SELECT l FROM Livro l WHERE l.genero = :genero"),
    @NamedQuery(name = "Livro.findByCondicao", query = "SELECT l FROM Livro l WHERE l.condicao = :condicao"),
    @NamedQuery(name = "Livro.findByStatus", query = "SELECT l FROM Livro l WHERE l.status = :status"),
    @NamedQuery(name = "Livro.findByAvaliacao", query = "SELECT l FROM Livro l WHERE l.avaliacao = :avaliacao")})
public class Livro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Livro")
    private Integer iDLivro;
    @Basic(optional = false)
    @Column(name = "Titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "Autor")
    private String autor;
    @Column(name = "Editora")
    private String editora;
    @Basic(optional = false)
    @Column(name = "Ano_Publicacao")
    private int anoPublicacao;
    @Basic(optional = false)
    @Column(name = "Genero")
    private String genero;
    @Lob
    @Column(name = "Sinopse")
    private String sinopse;
    @Basic(optional = false)
    @Column(name = "Condicao")
    private String condicao;
    @Basic(optional = false)
    @Column(name = "Status")
    private String status;
    @Column(name = "Avaliacao")
    private Integer avaliacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDLivro")
    private List<Emprestimo> emprestimoList;
    @JoinColumn(name = "ID_Localizacao", referencedColumnName = "ID_Localizacao")
    @ManyToOne
    private LocalizacaoFisica iDLocalizacao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDLivro")
    private List<ListaEspera> listaEsperaList;

    public Livro() {
    }

    public Livro(Integer iDLivro) {
        this.iDLivro = iDLivro;
    }

    public Livro(Integer iDLivro, String titulo, String autor, int anoPublicacao, String genero, String condicao, String status) {
        this.iDLivro = iDLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.condicao = condicao;
        this.status = status;
    }

    public Integer getIDLivro() {
        return iDLivro;
    }

    public void setIDLivro(Integer iDLivro) {
        this.iDLivro = iDLivro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getCondicao() {
        return condicao;
    }

    public void setCondicao(String condicao) {
        this.condicao = condicao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Integer avaliacao) {
        this.avaliacao = avaliacao;
    }

    public List<Emprestimo> getEmprestimoList() {
        return emprestimoList;
    }

    public void setEmprestimoList(List<Emprestimo> emprestimoList) {
        this.emprestimoList = emprestimoList;
    }

    public LocalizacaoFisica getIDLocalizacao() {
        return iDLocalizacao;
    }

    public void setIDLocalizacao(LocalizacaoFisica iDLocalizacao) {
        this.iDLocalizacao = iDLocalizacao;
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
        hash += (iDLivro != null ? iDLivro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livro)) {
            return false;
        }
        Livro other = (Livro) object;
        if ((this.iDLivro == null && other.iDLivro != null) || (this.iDLivro != null && !this.iDLivro.equals(other.iDLivro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "biblioteca.entities.Livro[ iDLivro=" + iDLivro + " ]";
    }
    
}
