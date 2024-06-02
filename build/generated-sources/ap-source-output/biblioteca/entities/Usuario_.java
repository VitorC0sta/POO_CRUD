package biblioteca.entities;

import biblioteca.entities.Emprestimo;
import biblioteca.entities.ListaEspera;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-01T18:43:41", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> tipo;
    public static volatile SingularAttribute<Usuario, Boolean> ativo;
    public static volatile ListAttribute<Usuario, ListaEspera> listaEsperaList;
    public static volatile ListAttribute<Usuario, Emprestimo> emprestimoList;
    public static volatile SingularAttribute<Usuario, String> nome;
    public static volatile SingularAttribute<Usuario, Integer> iDUsuario;
    public static volatile SingularAttribute<Usuario, String> ra;

}