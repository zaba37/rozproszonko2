package entity;

import entity.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2016-01-12T10:48:37")
@StaticMetamodel(Ad.class)
public class Ad_ { 

    public static volatile SingularAttribute<Ad, Integer> id;
    public static volatile SingularAttribute<Ad, String> descryption;
    public static volatile SingularAttribute<Ad, String> title;
    public static volatile SingularAttribute<Ad, String> moderator;
    public static volatile SingularAttribute<Ad, Users> owner;
    public static volatile SingularAttribute<Ad, Integer> visit;
    public static volatile SingularAttribute<Ad, String> pathtofile;

}