package com.dbsoftwares.votesender.server;

/*
 * Created by DBSoftwares on 14 september 2017
 * Developer: Dieter Blancke
 * Project: VoteSender
 */

import lombok.AllArgsConstructor;
import lombok.Data;

import java.security.PublicKey;

@Data @AllArgsConstructor
public class VoteServer {

    private String name;
    private PublicKey key;
    private String IP;
    private int port;
    private String service;
}