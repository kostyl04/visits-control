package itmo.visits_control.configuration;

import java.sql.Date;

import javax.persistence.TemporalType;

import org.hibernate.dialect.SQLServer2005Dialect;
import org.hibernate.dialect.function.ConvertFunction;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.DateType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import microsoft.sql.Types;


public class SuperDialect extends SQLServer2005Dialect{
	
	 public SuperDialect(){
		 super();
		 registerFunction("CONVERT",new ConvertFunction());
	 }
}
