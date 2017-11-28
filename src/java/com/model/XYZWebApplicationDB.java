package com.model;

import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XYZWebApplicationDB {
    JDBCWrapper wrapper;
    
    public XYZWebApplicationDB(JDBCWrapper w)
    {
        wrapper = w;
    }
    
    public void insertMember(Member m)
    {
        java.sql.Date sqlDOB = new java.sql.Date(m.getDOB().getTime());
        java.sql.Date sqlReg = new java.sql.Date(m.getDOB().getTime());
        wrapper.createStatement();
        try {
            wrapper.getStatement().executeUpdate("insert into members values ('" + m.getUsername() + "', '" + m.getFullName() + "', '" + m.getAddress() + "', '" + sqlDOB.toString() + "', '" + sqlReg.toString() + "', '" + m.getStatus() + "', " + m.getBalance() + ")");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertUser(User u)
    {
        wrapper.createStatement();
        try {
            wrapper.getStatement().executeUpdate("insert into users values ('" + u.getId() + "', '" + u.getPassword() + "', '" + u.getStatus() + "')");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertPayment(Payment p)
    {
        wrapper.createStatement();
        try {
            wrapper.getStatement().executeUpdate("insert into payments values ('" + p.getId() + "', '" + p.getMemid() + "', '" + p.getTypeOfPayment() + "', '" + p.getAmount() + "', '" + p.getDate() + "', '" + p.getTime() + "')");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void insertClaim(Claim c)
    {
        wrapper.createStatement();
        try {
            wrapper.getStatement().executeUpdate("insert into claims values ('" + c.getId() + "', '" + c.getMemid() + "', '" + c.getDate() + "', '" + c.getRationale() + "', '" + c.getStatus() + "', '" + c.getAmount() + "')");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public User getUser(String id)
    {
        User ret = new User();
        wrapper.createStatement();
        wrapper.findRecord("users", "id", id);
        try {
            ret.setId(wrapper.getResultSet().getString("id"));
            ret.setPassword(wrapper.getResultSet().getString("password"));
            ret.setStatus(wrapper.getResultSet().getString("status"));
        } catch (SQLException ex) {
            Logger.getLogger(XYZWebApplicationDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public ArrayList<Payment> getUserPayments(String id)
    {
        ArrayList ret = new ArrayList<Payment>();
        wrapper.findRecord("payments", "mem_id", id);
        try { 
            do
            {
               ret.add(new Payment(wrapper.getResultSet().getInt("id"), wrapper.getResultSet().getString("mem_id"), wrapper.getResultSet().getString("type_of_payment"), wrapper.getResultSet().getFloat("amount"), Time.valueOf(wrapper.getResultSet().getString("time")), makeDate(wrapper.getResultSet().getString("date"))));
            }while(wrapper.getResultSet().next());
        } catch (SQLException ex) {
            Logger.getLogger(XYZWebApplicationDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public ArrayList<Member> getAllMembers()
    {
        ArrayList ret = new ArrayList<Member>();
        wrapper.createStatement();
        wrapper.createResultSet("SELECT * FROM members");
        try { 
            wrapper.getResultSet().next();
            do
            {
               String[] addressString = wrapper.getResultSet().getString("address").split(",");
               Address a = new Address(Integer.parseInt(addressString[0]), addressString[1], addressString[2], addressString[3], addressString[4]);
               ret.add(new Member(wrapper.getResultSet().getString("id"), wrapper.getResultSet().getString("name"), a, makeDate(wrapper.getResultSet().getString("dob")), makeDate(wrapper.getResultSet().getString("dor")), wrapper.getResultSet().getString("status"), wrapper.getResultSet().getFloat("balance")));
            }while(wrapper.getResultSet().next());
        } catch (SQLException ex) {
            Logger.getLogger(XYZWebApplicationDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public ArrayList<Payment> getAllPayments()
    {
        ArrayList ret = new ArrayList<Payment>();
        wrapper.createStatement();
        wrapper.createResultSet("SELECT * FROM payments");
        try { 
            wrapper.getResultSet().next();
            do
            {
               ret.add(new Payment(wrapper.getResultSet().getInt("id"), wrapper.getResultSet().getString("mem_id"), wrapper.getResultSet().getString("type_of_payment"), wrapper.getResultSet().getFloat("amount"), wrapper.getResultSet().getTime("time"), wrapper.getResultSet().getDate("date")));
            }while(wrapper.getResultSet().next());
        } catch (SQLException ex) {
            Logger.getLogger(XYZWebApplicationDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public ArrayList<Member> getProvisionalMembers() {

        ArrayList<Member> users;
        users = getAllMembers();

        ArrayList ret = new ArrayList<Member>();

        for (Member user : users) {
            if (user.getStatus().equals("APPLIED")) {
                ret.add(user);
            }
        }
        return ret;
    }

    public ArrayList<Claim> getMemberClaims(String id)
    {
        ArrayList ret = new ArrayList<Claim>();
        wrapper.findRecord("claims", "mem_id", id);
        try { 
            do
            {
               ret.add(new Claim(wrapper.getResultSet().getInt("id"), wrapper.getResultSet().getString("mem_id"), wrapper.getResultSet().getDate("date"), wrapper.getResultSet().getString("rationale"),wrapper.getResultSet().getString("status"), wrapper.getResultSet().getFloat("amount")));
            }while(wrapper.getResultSet().next());
        } catch (SQLException ex) {
            Logger.getLogger(XYZWebApplicationDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public ArrayList<Claim> getAllClaims()
    {
        ArrayList users = getAllMembers();
        ArrayList ret = new ArrayList<Claim>();
        
        for(int i = 0; i != users.size(); ++i)
        {
            ArrayList claims = getMemberClaims(((Member)users.get(i)).getUsername());
            for(int j = 0; j != claims.size(); ++j)
            {
                ret.add(claims.get(j));
            }
        }
        
        return ret;
    }
    
    public Date makeDate(String dateParam) {
        Date dob = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        try {
            dob = df.parse(dateParam);
        } catch (ParseException ex) {
            System.out.println("Parse exception");
        }
        return dob;
    }
    
    public void changePassword(User u)
    {   
        wrapper.createStatement();
        try {
            wrapper.getStatement().executeUpdate("UPDATE users SET \"password\" = '"+ u.getPassword() +"' WHERE \"id\" = '" + u.getId() + "'");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void approveMemberApplication(User u)
    {
        wrapper.createStatement();   
        try {
            wrapper.getStatement().executeUpdate("UPDATE users SET \"status\" = 'APPROVED' WHERE \"id\" = '" + u.getId() + "'");
            wrapper.getStatement().executeUpdate("UPDATE members SET \"status\" = 'APPROVED' WHERE \"id\" = '" + u.getId() + "'");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void suspendMemberApplication(User u)
    {
        wrapper.createStatement();   
        try {
            wrapper.getStatement().executeUpdate("UPDATE users SET \"status\" = 'SUSPENDED' WHERE \"id\" = '" + u.getId() + "'");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCWrapper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
