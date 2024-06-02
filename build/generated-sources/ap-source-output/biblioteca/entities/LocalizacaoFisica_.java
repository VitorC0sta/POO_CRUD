package biblioteca.entities;

import biblioteca.entities.Livro;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-01T18:43:41", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(LocalizacaoFisica.class)
public class LocalizacaoFisica_ { 

    public static volatile SingularAttribute<LocalizacaoFisica, Integer> fileiras;
    public static volatile ListAttribute<LocalizacaoFisica, Livro> livroList;
    public static volatile SingularAttribute<LocalizacaoFisica, Integer> iDLocalizacao;
    public static volatile SingularAttribute<LocalizacaoFisica, Character> estante;

}