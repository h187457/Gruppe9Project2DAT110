package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

    // maps topic -> set of subscribed users
    protected ConcurrentHashMap<String, Set<String>> subscriptions;

    // maps user -> client session
    protected ConcurrentHashMap<String, ClientSession> clients;

    public Storage() {
        subscriptions = new ConcurrentHashMap<String, Set<String>>();
        clients = new ConcurrentHashMap<String, ClientSession>();
    }

    public Collection<ClientSession> getSessions() {
        return clients.values();
    }

    public Set<String> getTopics() {
        return subscriptions.keySet();
    }

    public ClientSession getSession(String user) {
        return clients.get(user);
    }

    public Set<String> getSubscribers(String topic) {
        return subscriptions.get(topic);
    }

    public void addClientSession(String user, Connection connection) {

        ClientSession session = new ClientSession(connection);

        clients.put(user, session);

        Logger.log("Client sessions: " + clients.size());
    }

    public void removeClientSession(String user) {

        ClientSession session = clients.remove(user);

        if (session != null) {
            session.disconnect();
        }

        Logger.log("Client sessions: " + clients.size());
    }

    public void createTopic(String topic) {

        subscriptions.putIfAbsent(topic, ConcurrentHashMap.newKeySet());

        Logger.log("Topic : " + subscriptions.size());
    }

    public void deleteTopic(String topic) {

        subscriptions.remove(topic);

        Logger.log("Topic removed: " + topic);
    }

    public void addSubscriber(String user, String topic) {

        Set<String> users = subscriptions.get(topic);

        if (users != null) {
            users.add(user);
            Logger.log("Subscribers : " + topic + " : " + users.size());
        }
    }

    public void removeSubscriber(String user, String topic) {

        Set<String> users = subscriptions.get(topic);

        if (users != null) {
            users.remove(user);
            Logger.log("Subscribers : " + topic + " : " + users.size());
        }
    }
}