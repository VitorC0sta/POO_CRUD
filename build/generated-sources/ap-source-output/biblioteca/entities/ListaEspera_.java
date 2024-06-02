package biblioteca.entities;

import biblioteca.entities.Livro;
import biblioteca.entities.Usuario;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-01T18:43:41", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(ListaEspera.class)
public class ListaEspera_ { 

    public static volatile SingularAttribute<ListaEspera, Integer> iDLista;
    public static volatile SingularAttribute<ListaEspera, Date> dataSolicitacao;
    public static volatile SingularAttribute<ListaEspera, Livro> iDLivro;
    public static volatile SingularAttribute<ListaEspera, Integer> posicao;
    public static volatile SingularAttribute<ListaEspera, Usuario> iDUsuario;

}