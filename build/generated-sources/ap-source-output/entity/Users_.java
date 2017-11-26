package entity;

import entity.Ad;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-01-12T10:48:37")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, String> login;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile ListAttribute<Users, Ad> adList;
    public static volatile SingularAttribute<Users, Integer> numberadforonepage;
    public static volatile SingularAttribute<Users, Integer> numberofstyle;

}