//package cn.shaikuba.mock.data.mybatis.typehandler;
//
//import org.apache.ibatis.type.BaseTypeHandler;
//import org.apache.ibatis.type.JdbcType;
//import org.apache.ibatis.type.MappedJdbcTypes;
//import org.apache.ibatis.type.MappedTypes;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import java.sql.CallableStatement;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//@MappedJdbcTypes(JdbcType.VARCHAR)
//@MappedTypes(RequestMethod.class)
//public class RequestMethodTypeHandler extends BaseTypeHandler<RequestMethod> {
//
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, RequestMethod parameter, JdbcType jdbcType) throws SQLException {
//
//    }
//
//    @Override
//    public RequestMethod getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        return null;
//    }
//
//    @Override
//    public RequestMethod getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        return null;
//    }
//
//    @Override
//    public RequestMethod getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        return null;
//    }
//}
