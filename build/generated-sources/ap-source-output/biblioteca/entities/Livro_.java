package biblioteca.entities;

import biblioteca.entities.Emprestimo;
import biblioteca.entities.ListaEspera;
import biblioteca.entities.LocalizacaoFisica;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-01T18:43:41", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Livro.class)
public class Livro_ { 

    public static volatile SingularAttribute<Livro, Integer> iDLivro;
    public static volatile ListAttribute<Livro, ListaEspera> listaEsperaList;
    public static volatile SingularAttribute<Livro, Integer> anoPublicacao;
    public static volatile SingularAttribute<Livro, String> condicao;
    public static volatile ListAttribute<Livro, Emprestimo> emprestimoList;
    public static volatile SingularAttribute<Livro, String> titulo;
    public static volatile SingularAttribute<Livro, String> editora;
    public static volatile SingularAttribute<Livro, Integer> avaliacao;
    public static volatile SingularAttribute<Livro, String> autor;
    public static volatile SingularAttribute<Livro, String> sinopse;
    public static volatile SingularAttribute<Livro, String> genero;
    public static volatile SingularAttribute<Livro, LocalizacaoFisica> iDLocalizacao;
    public static volatile SingularAttribute<Livro, String> status;

}