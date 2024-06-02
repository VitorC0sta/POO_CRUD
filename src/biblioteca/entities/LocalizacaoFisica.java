/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "Localizacao_Fisica")
@NamedQueries({
    @NamedQuery(name = "LocalizacaoFisica.findAll", query = "SELECT l FROM LocalizacaoFisica l"),
    @NamedQuery(name = "LocalizacaoFisica.findByIDLocalizacao", query = "SELECT l FROM LocalizacaoFisica l WHERE l.iDLocalizacao = :iDLocalizacao"),
    @NamedQuery(name = "LocalizacaoFisica.findByEstante", query = "SELECT l FROM LocalizacaoFisica l WHERE l.estante = :estante"),
    @NamedQuery(name = "LocalizacaoFisica.findByFileiras", query = "SELECT l FROM LocalizacaoFisica l WHERE l.fileiras = :fileiras")})
public class LocalizacaoFisica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Localizacao")
    private Integer iDLocalizacao;
    @Column(name = "Estante")
    private Character estante;
    @Column(name = "Fileiras")
    private Integer fileiras;
    @OneToMany(mappedBy = "iDLocalizacao")
    private List<Livro> livroList;

    public LocalizacaoFisica() {
    }

    public LocalizacaoFisica(Integer iDLocalizacao) {
        this.iDLocalizacao = iDLocalizacao;
    }

    public Integer getIDLocalizacao() {
        return iDLocalizacao;
    }

    public void setIDLocalizacao(Integer iDLocalizacao) {
        this.iDLocalizacao = iDLocalizacao;
    }

    public Character getEstante() {
        return estante;
    }

    public void setEstante(Character estante) {
        this.estante = estante;
    }

    public Integer getFileiras() {
        return fileiras;
    }

    public void setFileiras(Integer fileiras) {
        this.fileiras = fileiras;
    }

    public List<Livro> getLivroList() {
        return livroList;
    }

    public void setLivroList(List<Livro> livroList) {
        this.livroList = livroList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDLocalizacao != null ? iDLocalizacao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LocalizacaoFisica)) {
            return false;
        }
        LocalizacaoFisica other = (LocalizacaoFisica) object;
        if ((this.iDLocalizacao == null && other.iDLocalizacao != null) || (this.iDLocalizacao != null && !this.iDLocalizacao.equals(other.iDLocalizacao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "biblioteca.entities.LocalizacaoFisica[ iDLocalizacao=" + iDLocalizacao + " ]";
    }
    
}
