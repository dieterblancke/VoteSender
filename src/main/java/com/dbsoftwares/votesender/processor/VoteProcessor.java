package com.dbsoftwares.votesender.processor;

/*
 * Created by DBSoftwares on 14 september 2017
 * Developer: Dieter Blancke
 * Project: VoteSender
 */

import com.dbsoftwares.votesender.VoteSender;
import com.dbsoftwares.votesender.server.VoteServer;
import com.vexsoftware.votifier.crypto.RSA;
import com.vexsoftware.votifier.model.Vote;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class VoteProcessor extends BukkitRunnable {

    private Vote vote;

    public VoteProcessor(Vote vote) {
        this.vote = vote;

        runTaskAsynchronously(VoteSender.getInstance());
    }

    @Override
    public void run() {
        VoteSender instance = VoteSender.getInstance();

        for (VoteServer server : instance.getServers()) {
            if (server.getIP().length() == 0) {
                continue;
            }
            Socket socket = null;
            OutputStream output = null;

            try {
                if (server.getService().length() > 0) {
                    vote.setServiceName(server.getService());
                }
                String vs = "VOTE\n" + vote.getServiceName() + "\n" + vote.getUsername() + "\n" + vote.getAddress() + "\n" + vote.getTimeStamp() + "\n";

                socket = new Socket();
                socket.connect(new InetSocketAddress(server.getIP(), server.getPort()), 1000);

                output = socket.getOutputStream();
                output.write(RSA.encrypt(vs.getBytes(), server.getKey()));

                instance.getLogger().info("[VoteSend] A vote has been sent to " + server + ": " + vote.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}