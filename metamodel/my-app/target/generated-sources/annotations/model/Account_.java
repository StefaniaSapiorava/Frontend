package model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-12-15T10:38:58", comments="EclipseLink-4.0.1.v20230224-rd91fc33f4c07cccdc5ab1be2004b8690ed8e81da")
@StaticMetamodel(Account.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Account_ { 

    public static volatile SingularAttribute<Account, Double> balance;
    public static volatile SingularAttribute<Account, Long> abonentId;

}