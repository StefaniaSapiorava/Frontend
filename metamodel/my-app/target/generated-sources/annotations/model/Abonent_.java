package model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-12-15T10:38:58", comments="EclipseLink-4.0.1.v20230224-rd91fc33f4c07cccdc5ab1be2004b8690ed8e81da")
@StaticMetamodel(Abonent.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Abonent_ { 

    public static volatile SingularAttribute<Abonent, String> phone;
    public static volatile SingularAttribute<Abonent, String> name;
    public static volatile SingularAttribute<Abonent, Boolean> isBlocked;
    public static volatile SingularAttribute<Abonent, Long> id;
    public static volatile SingularAttribute<Abonent, String> email;

}