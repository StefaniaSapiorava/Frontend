package model;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-12-15T10:38:58", comments="EclipseLink-4.0.1.v20230224-rd91fc33f4c07cccdc5ab1be2004b8690ed8e81da")
@StaticMetamodel(Service.class)
@SuppressWarnings({"rawtypes", "deprecation"})
public class Service_ { 

    public static volatile SingularAttribute<Service, BigDecimal> cost;
    public static volatile SingularAttribute<Service, String> name;
    public static volatile SingularAttribute<Service, String> description;
    public static volatile SingularAttribute<Service, Long> id;

}