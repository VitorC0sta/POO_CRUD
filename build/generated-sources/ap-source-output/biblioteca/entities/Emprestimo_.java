package biblioteca.entities;

import biblioteca.entities.Livro;
import biblioteca.entities.Usuario;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-01T18:43:41", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Emprestimo.class)
public class Emprestimo_ { 

    public static volatile SingularAttribute<Emprestimo, Date> dataSolicitacao;
    public static volatile SingularAttribute<Emprestimo, Integer> iDEmprestimo;
    public static volatile SingularAttribute<Emprestimo, Livro> iDLivro;
    public static volatile SingularAttribute<Emprestimo, Date> dataMaxDevolucao;
    public static volatile SingularAttribute<Emprestimo, Date> dataDevolucao;
    public static volatile SingularAttribute<Emprestimo, Usuario> iDUsuario;

}