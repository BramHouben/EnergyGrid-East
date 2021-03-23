package org.energygrid.east.solarparkservice.rabbit.mock;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

public class ConnectionMock implements Connection {
    @Override
    public InetAddress getAddress() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public int getChannelMax() {
        return 0;
    }

    @Override
    public int getFrameMax() {
        return 0;
    }

    @Override
    public int getHeartbeat() {
        return 0;
    }

    @Override
    public Map<String, Object> getClientProperties() {
        return null;
    }

    @Override
    public String getClientProvidedName() {
        return null;
    }

    @Override
    public Map<String, Object> getServerProperties() {
        return null;
    }

    @Override
    public Channel createChannel() throws IOException {
        return null;
    }

    @Override
    public Channel createChannel(int i) throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public void close(int i, String s) throws IOException {

    }

    @Override
    public void close(int i) throws IOException {

    }

    @Override
    public void close(int i, String s, int i1) throws IOException {

    }

    @Override
    public void abort() {

    }

    @Override
    public void abort(int i, String s) {

    }

    @Override
    public void abort(int i) {

    }

    @Override
    public void abort(int i, String s, int i1) {

    }

    @Override
    public void addBlockedListener(BlockedListener blockedListener) {

    }

    @Override
    public BlockedListener addBlockedListener(BlockedCallback blockedCallback, UnblockedCallback unblockedCallback) {
        return null;
    }

    @Override
    public boolean removeBlockedListener(BlockedListener blockedListener) {
        return false;
    }

    @Override
    public void clearBlockedListeners() {

    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return null;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public void setId(String s) {

    }

    @Override
    public void addShutdownListener(ShutdownListener shutdownListener) {

    }

    @Override
    public void removeShutdownListener(ShutdownListener shutdownListener) {

    }

    @Override
    public ShutdownSignalException getCloseReason() {
        return null;
    }

    @Override
    public void notifyListeners() {

    }

    @Override
    public boolean isOpen() {
        return false;
    }
}