package com.ballgame.servers;

import com.ballgame.data_handlers.CommandHandlersManager;
import com.ballgame.data_handlers.DataHandlerManager;
import com.ballgame.data_handlers.GameActionHandler;
import com.ballgame.game.GameManager;
import com.ballgame.objects.User;
import com.ballgame.objects.UsersMediator;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.joda.time.DateTime;

@ServerEndpoint("/server")
public class Game {
    
    private static final Map<Session,User> users = new HashMap<>();
    private static final UsersMediator usersMediator = new UsersMediator();
    private static final DataHandlerManager dataHandlerManager = new DataHandlerManager();
    private static final CommandHandlersManager commandHandlersManager = new CommandHandlersManager();
    private static final GameManager gameManager = new GameManager();
    private static final GameActionHandler gameActionHandler = new GameActionHandler();
    
    @OnOpen
    public void onOpen(Session session) {
        this.log("new connection: " + session.getId());
        User newUser = new User(Game.usersMediator, session);
        Game.users.put(session, newUser);
        Game.dataHandlerManager.handleAddUserData(newUser.getId(), newUser.getName(), session);
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        JsonReader reader = Json.createReader(new StringReader(message));
        JsonObject ob = reader.readObject();
        reader.close();
        String type = ob.getString("type");
        String data = ob.getString("data");
        if( type.equals("message") ) {
            Game.dataHandlerManager.handleMessageData(session, data);
        } else if( type.equals("command") ) {
            Game.commandHandlersManager.handle(
                Game.usersMediator.getUserBySession(session), 
                    data);
        } else if( type.equals("gameAction") ) {
            Game.gameActionHandler.handle(data, Game.usersMediator.getUserBySession(session));
        }
    }
    
    @OnClose
    public void onClose(Session session) {
        this.log("closed connecion: " + session.getId());
        User userToRemove = Game.users.get(session);
        Game.users.remove(session);
        Game.dataHandlerManager.handleRemoveUserData(userToRemove.getId());
        Game.usersMediator.removeUser(userToRemove);
        Game.gameManager.removeUser(userToRemove);
    }
    
    @OnError
    public void onError(Throwable error) {
        this.log("error: " + error.getMessage());
        error.printStackTrace();
    }
    
    private void log(String info) {
        System.out.println(Game.getCurrentTime() + info);
    }
    
    public static String getCurrentTime() {
        DateTime dateTime = new DateTime();
        String[] timeSplit = dateTime.toString().split("T");
        String date = timeSplit[0];
        String time = timeSplit[1].split("\\.")[0];
        return "[" + date + " " + time + "]";
    }

    public static UsersMediator getUsersMediator() {
        return usersMediator;
    }

    public static Map<Session, User> getUsers() {
        Map<Session, User> copy = new HashMap<>(users);
        return copy;
    }

    public static DataHandlerManager getDataHandlerManager() {
        return dataHandlerManager;
    }

    public static CommandHandlersManager getCommandHandlersManager() {
        return commandHandlersManager;
    }

    public static GameManager getGameManager() {
        return gameManager;
    }
}
