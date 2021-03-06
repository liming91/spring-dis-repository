package com.ming.dao.impl;

import com.ming.dao.IAccountDao;
import com.ming.model.Account;
import com.ming.util.ConnectionThredUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    private QueryRunner queryRunner;

    @Autowired
    private ConnectionThredUtil conn;


    public List<Account> findAll() {
        try{
            return queryRunner.query(conn.getThredConnection(),"select * from account ",new BeanListHandler<Account>(Account.class));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Account findById(Integer id) {
        try{
            return queryRunner.query(conn.getThredConnection(),"select * from account where id = ? ",new BeanHandler<Account>(Account.class),id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void saveAccount(Account account) {
        try{
             queryRunner.update(conn.getThredConnection(),"insert into account(name,money) values(?,?)",account.getName(),account.getMoney());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(Account account) {
        try{
            queryRunner.update(conn.getThredConnection(),"update account set name=? ,money=? where id=?  ",account.getName(),account.getMoney(),account.getId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(Integer id) {
        try{
            queryRunner.update(conn.getThredConnection(),"delete account where id=?",id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account  findAccountByName(String accountName) {
        try{
            List<Account> list =  queryRunner.query(conn.getThredConnection(),"select * from account where name = ? ",new BeanListHandler<Account>(Account.class),accountName);
            if(list.size()==0 || list ==null){
                return null;
            }
            if(list.size()>1){
                throw new RuntimeException("结果集不为1");
            }
            return list.get(0);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


}
