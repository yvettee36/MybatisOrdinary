package com.eurasia.test;

import com.eurasia.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by yvettee on 2017/11/23.
 */
public class MybatisFirst {
    //根据id查询用户信息，得到一条记录
    @Test
    public void findUserByIdTest() throws IOException {

        //Mybatis配置文件
        String resource = "SqlMapConfig.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);


        //创建会话工厂,传入Mybatis的配置文件信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //通过工厂得到SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //通过SqlSession操作数据库
        //第一个参数：映射文件中statement的id，等于namespace+statement的id
        //指定和映射文件中匹配的parameterType类型的参数
        User user = sqlSession.selectOne("test.findUserById", 1);
        System.out.println(user);

        //释放资源
        sqlSession.close();
    }

    //根据名称模糊查询
    @Test
    public void findUserByNameTest() throws IOException {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> list = sqlSession.selectList("test.findUserByName", "小明");
        System.out.println(list);
        sqlSession.close();
    }

    //添加用户信息
    @Test
    public void insertUserTest() throws IOException {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

       //插入用户对象
        User user = new User();
        user.setUsername("小山竹");
        user.setBirthday(new Date());
        user.setSex("2");
        user.setAddress("台湾高雄");

        sqlSession.insert("test.insertUser",user);
        //提交事务
        System.out.println(user.getId());
        sqlSession.commit();
        sqlSession.close();
    }

    //删除用户
    @Test
    public void deleteUserTest() throws IOException {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.delete("test.deleteUser",26);
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    //更新用户
    @Test
    public void updateUserTest() throws IOException {
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(16);
        user.setUsername("嗯哼");
        user.setBirthday(new Date());
        user.setSex("1");
        user.setAddress("北京");
        sqlSession.update("test.updateUser",user);
        //提交事务
        sqlSession.commit();
        sqlSession.close();
    }
}
