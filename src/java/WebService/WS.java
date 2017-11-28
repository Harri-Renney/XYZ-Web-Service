/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import com.model.Claim;
import com.model.JDBCWrapper;
import com.model.Payment;
import com.model.XYZWebApplicationDB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Harri Renney
 */
@WebService(serviceName = "WS")
public class WS {

    /**
     * This is a sample web service operation
     */
    /**
     * Web service operation
     */
    @WebMethod(operationName = "reportAnnualPayouts")
    public float reportAnnualPayouts() {
        float ret = 0;
        JDBCWrapper wrapper = new JDBCWrapper("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/XYZ Web Application DB", "root", "root");
        wrapper.createStatement();
        ArrayList<Claim> claims = new XYZWebApplicationDB(wrapper).getAllClaims();
        
        Calendar beginYear = Calendar.getInstance();
        beginYear.setTime(new Date());
        beginYear.set(Calendar.DAY_OF_YEAR, beginYear.getActualMinimum(Calendar.DAY_OF_YEAR));
        Date d = beginYear.getTime();
        
        for(int i = 0; i != claims.size(); ++i)
        {
            if(claims.get(i).getStatus().equals("APPROVED") && d.before(claims.get(i).getDate()))
                ret += ((ArrayList<Claim>)claims).get(i).getAmount();
        }
        return ret;
    }
    @WebMethod(operationName = "reportAnnualIncome")
    public float reportAnnualIncome() {
        float ret = 0;
        JDBCWrapper wrapper = new JDBCWrapper("org.apache.derby.jdbc.ClientDriver", "jdbc:derby://localhost:1527/XYZ Web Application DB", "root", "root");
        wrapper.createStatement();
        ArrayList<Payment> payments = new XYZWebApplicationDB(wrapper).getAllPayments();
        
        Calendar beginYear = Calendar.getInstance();
        beginYear.setTime(new Date());
        beginYear.set(Calendar.DAY_OF_YEAR, beginYear.getActualMinimum(Calendar.DAY_OF_YEAR));
        Date d = beginYear.getTime();
        
        for(int i = 0; i != payments.size(); ++i)
        {
            if(d.before(payments.get(i).getDate()))
                ret += ((ArrayList<Payment>)payments).get(i).getAmount();
        }
        return ret;
    }
}
