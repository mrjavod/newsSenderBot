package com.is.bot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.is.utils.ConnectionPool;
import com.is.utils.ISLogger;

public class BotService {

	public static HashMap<String, String> getSets(){

	    Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        HashMap<String, String> sets = new HashMap<>();

        try {

            c = ConnectionPool.getConnection();
            ps = c.prepareStatement("SELECT id, value FROM sets");
            rs = ps.executeQuery();

            while (rs.next()) {

                sets.put(rs.getString("id"), rs.getString("value"));
            }

        } catch (Exception e) {
            ISLogger.getLogger().error(ConnectionPool.getPstr(e));
        } finally {
            ConnectionPool.close(c);
            ConnectionPool.close(ps);
            ConnectionPool.close(rs);
        }

        return sets;
    }

    public static boolean createUser(Users user) {

	    Connection c = null;
        PreparedStatement ps = null;
        boolean isCreated = false;

        try {
            c = ConnectionPool.getConnection();
            ps = c.prepareStatement("INSERT INTO users(username, chat_id, phone_number, status, created) VALUES (?,?,?,?,NOW())", PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setLong(2, user.getChat_id());
            ps.setString(3, user.getPhone_number());
            ps.setString(4, "ACTIVE");
            ps.executeUpdate();

            int user_id = 0;
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                user_id = rs.getInt(1);
            }

            addToNewsSender(c, user_id);

            c.commit();
            isCreated = true;
        } catch (Exception e) {
            ISLogger.getLogger().error(ConnectionPool.getPstr(e));
        } finally {
            ConnectionPool.close(c);
            ConnectionPool.close(ps);
        }
        return isCreated;
    }

    private static void addToNewsSender(Connection c, int user_id){

	    PreparedStatement ps = null;

        try {
            ps = c.prepareStatement("INSERT INTO news_sender(user_id, news_id) SELECT ?, n.id FROM news n WHERE n.status=?");
            ps.setInt(1, user_id);
            ps.setString(2, "ACTIVE");
            ps.executeUpdate();

        } catch (Exception e) {
            ISLogger.getLogger().error(ConnectionPool.getPstr(e));
        } finally {
            ConnectionPool.close(ps);
        }
    }

    public static void changeUserState(long chat_id, String state) {

        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = ConnectionPool.getConnection();
            ps = c.prepareStatement("UPDATE users SET status=?, created=NOW() WHERE chat_id=?");
            ps.setString(1, state);
            ps.setLong(2, chat_id);
            ps.executeUpdate();
            c.commit();
        } catch (Exception e) {
            ISLogger.getLogger().error(ConnectionPool.getPstr(e));
        } finally {
            ConnectionPool.close(c);
            ConnectionPool.close(ps);
        }
    }

    public static List<NewsSender> sendingNews(){

        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<NewsSender> list = new ArrayList<>();

        try {

            c = ConnectionPool.getConnection();
            ps = c.prepareStatement("SELECT ns.id, u.username, u.chat_id, n.title, n.image, n.content, n.author \n" +
                    "    FROM news_sender ns\n" +
                    "    INNER JOIN news n ON n.id=ns.news_id AND n.status='ACTIVE'\n" +
                    "    INNER JOIN users u ON u.id=ns.user_id AND u.status='ACTIVE'\n" +
                    "    WHERE ns.is_send=0 AND (DATE(current_timestamp) - cast(u.created AS DATE)) > n.publish_after");
            rs = ps.executeQuery();

            while(rs.next()){
                list.add(new NewsSender(rs.getInt("id"), rs.getString("username"),
                        rs.getLong("chat_id"), rs.getString("title"),rs.getString("image"),
                        rs.getString("author"), rs.getString("content")));
            }

        }catch (SQLException e){
            ISLogger.getLogger().error(ConnectionPool.getPstr(e));
        }finally {
            ConnectionPool.close(c);
            ConnectionPool.close(ps);
            ConnectionPool.close(rs);
        }

        return list;
    }

    public static Users findUser(long chat_id) {

        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Users user = null;

        try {
            c = ConnectionPool.getConnection();
            ps = c.prepareStatement("SELECT chat_id, phone_number, status FROM users WHERE chat_id=?");
            ps.setLong(1, chat_id);
            rs = ps.executeQuery();
            while (rs.next()){
                user = new Users(rs.getLong("chat_id"), rs.getString("phone_number"),
                        rs.getString("status"));
            }
        } catch (Exception e) {
            ISLogger.getLogger().error(ConnectionPool.getPstr(e));
        } finally {
            ConnectionPool.close(c);
            ConnectionPool.close(ps);
            ConnectionPool.close(rs);
        }
        return user;
    }

    public static void successfullIsSend(long id) {

        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = ConnectionPool.getConnection();
            ps = c.prepareStatement("UPDATE news_sender SET is_send=1, send_time=NOW() WHERE id=?");
            ps.setLong(1, id);
            ps.executeUpdate();
            c.commit();
        } catch (Exception e) {
            ISLogger.getLogger().error(ConnectionPool.getPstr(e));
        } finally {
            ConnectionPool.close(c);
            ConnectionPool.close(ps);
        }
    }
}
