package cn.sonui.onlinechat.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sonui
 */
@Component
public class SessionUtils {
    private final static Map<String, WebSocketSession> LIST = new ConcurrentHashMap<>();
    private final static Map<Long, List<WebSocketSession>> ONLINE_CLIENTS = new ConcurrentHashMap<>();
    private final static Map<String, Long> SESSION_TO_USERID = new ConcurrentHashMap<>();
    private final static Map<String, List<Long>> GROUP_ONLINE_MEMBER = new ConcurrentHashMap<>();

    /**
     * 添加 session
     *
     * @param token   token
     * @param session session
     */
    public static void addClient(String token, WebSocketSession session) {
        LIST.put(token, session);
    }

    /**
     * 获得 token对应session
     *
     * @param token token
     * @return session
     */
    public static WebSocketSession getClient(String token) {
        return LIST.get(token);
    }

    /**
     * 删除 token对应session 自动关闭ws连接
     *
     * @param token token
     */
    public static void removeClient(String token) {
        try (WebSocketSession t = LIST.remove(token)) {
            if (t != null) {
                t.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否存在token对应的session
     *
     * @param token token
     * @return 结果
     */
    public static boolean containsClient(String token) {
        return LIST.containsKey(token);
    }

    /**
     * 获得 全局在线客户端数量
     *
     * @return session 数量
     */
    public static int clientSize() {
        return LIST.size();
    }

    /**
     * 获得所有 session
     *
     * @return session
     */
    public static Map<String, WebSocketSession> getClientList() {
        return LIST;
    }

    /**
     * 添加 在线客户端
     *
     * @param uid     用户id
     * @param session session
     */
    public static void addOnlineClient(Long uid, WebSocketSession session) {
        List<WebSocketSession> sessions = ONLINE_CLIENTS.computeIfAbsent(uid, k -> new java.util.LinkedList<>());
        sessions.add(session);
        ONLINE_CLIENTS.put(uid, sessions);
    }

    /**
     * 删除 在线客户端
     *
     * @param uid     用户id
     * @param session session
     */
    public static void removeOnlineClient(Long uid, WebSocketSession session) {
        List<WebSocketSession> sessions = ONLINE_CLIENTS.get(uid);
        if (sessions != null) {
            sessions.remove(session);
            ONLINE_CLIENTS.put(uid, sessions);
        }
    }

    /**
     * 获得用户在线客户端
     *
     * @param uid 用户id
     * @return session
     */
    public static List<WebSocketSession> getOnlineClients(Long uid) {
        return ONLINE_CLIENTS.get(uid);
    }

    /**
     * 获得 在线客户端数量
     *
     * @param uid 用户id
     * @return session 数量
     */
    public static int getOnlineClientSize(Long uid) {
        return ONLINE_CLIENTS.get(uid).size();
    }

    /**
     * 添加 session 到 用户id
     *
     * @param session session
     * @param uid     用户id
     */
    public static void addSessionToUserId(WebSocketSession session, Long uid) {
        SESSION_TO_USERID.put(session.getId(), uid);
    }

    /**
     * 获得 session 对应 用户id
     *
     * @param session session
     * @return 用户id
     */
    public static Long getSessionToUserId(WebSocketSession session) {
        return SESSION_TO_USERID.get(session.getId());
    }

    /**
     * 删除 session 对应 用户id
     *
     * @param session session
     */
    public static void removeSessionToUserId(WebSocketSession session) {
        SESSION_TO_USERID.remove(session.getId());
    }

    /**
     * 群增加成员
     *
     * @param groupId 群id
     * @param userId  成员id
     */
    public static void addOnlineMember(String groupId, Long userId) {
        List<Long> members = GROUP_ONLINE_MEMBER.computeIfAbsent(groupId, k -> new java.util.LinkedList<>());
        members.add(userId);
        GROUP_ONLINE_MEMBER.put(groupId, members);
    }

    /**
     * 删除群在线成员
     *
     * @param groupId 群id
     * @param userId  成员id
     */
    public static void removeOnlineMember(String groupId, Long userId) {
        List<Long> members = GROUP_ONLINE_MEMBER.get(groupId);
        if (members != null) {
            members.remove(userId);
            GROUP_ONLINE_MEMBER.put(groupId, members);
        }
    }

    /**
     * 获得群在线成员
     *
     * @param groupId 群id
     * @return 成员id列表
     */
    public static List<Long> getOnlineMembers(String groupId) {
        return GROUP_ONLINE_MEMBER.get(groupId);
    }
}
