/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitb.cse;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ratheeshkv
 */
public class Constants {

    public static String experimentDetailsDirectory = "/home/cse/Downloads/Loadgenerator";
//    public static String experimentDetailsDirectory = "/home/ratheeshkv/Downloads/Loadgenerator";

//    public static boolean experimentRunning = false;
    

    public static String DB_USER_NAME = "root";
    public static String DB_PASSWORD = "root123";//root123";
    public static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static String DB_URL = "jdbc:mysql://127.0.0.1/server";
    public static int ConnectionPORT = 8001;
    public static DBManager dbManager = null;
    public static Session currentSession = null;
    public static boolean listenOnPort = false;
    public static String configFile = "config.txt";
    public static String controlFile = "controlFile";
    public static int heartBeatAlive = 120;  // in seconds
    static final String sendControlFile = "controlFile";
    static final String stopExperiment = "stopExperiment";
    static final String getLogFiles = "getLogFiles";
    static final String refreshRegistration = "refreshRegistration";
    static final String clearRegistration = "clearRegistration";
    static final String sendApSettings = "apSettings";
    static final String rssi = "rssi";
    static final String bssid = "bssid";
    static final String bssidList = "bssidList";
    static final String ssid = "ssid";
    static final String linkSpeed = "linkSpeed";
    static final String username = "username";
    static final String password = "pwd";
    static final String processorSpeed = "processorSpeed";
    static final String numberOfCores = "numberOfCores";
    static final String memory = "memory";
    static final String storageSpace = "storageSpace";
    static final String experimentid = "exptid";
    static final String startExp = "startExperiment";
    static final String userEmail = "userEmail";
    static final String email = "email";
    static final String appversion = "appversion";
    static final String wakeup = "wakeup";
    static final String duration = "duration";
    static final String androidVersion = "androidVersion";
    static final String isInForeground = "isInForeground";
    static final String devicename = "devicename";

    static final int responseOK = 200;
    static final @Getter
    String action = "action";
    static final @Getter
    String heartBeat = "heartBeat";
    static final @Getter
    String heartBeat1 = "heartBeat1";
    static final @Getter
    String heartBeat2 = "heartBeat2";
    static final @Getter
    String ip = "ip";
    static final @Getter
    String port = "port";
    static final @Getter
    String expID = "expID";
    static final @Getter
    String macAddress = "macAddress";
    static final @Getter
    String experimentOver = "expOver";
    static final @Getter
    String controlFileacknowledgement = "ack";

    static final @Getter
    String expacknowledgement = "expack";

    static final @Getter
    String acknowledgement1 = "ack1";

    static final @Getter
    String experimentNumber = "exp";
    static final @Getter
    String fileId = "fileid";
    static final @Getter
    String serverTime = "serverTime";
    static final @Getter
    String message = "message";
    static final @Getter
    String timeout = "timeout";
    
      static final @Getter
    String timer = "timer";
      
    static final @Getter
    String textFileFollow = "textFileFollow";

}
