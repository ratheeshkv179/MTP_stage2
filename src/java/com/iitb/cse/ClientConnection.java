/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitb.cse;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ratheeshkv
 */
class ConnectionInfo {

    String ip_addr;
    int port;

    public ConnectionInfo(String ip_addr, int port) {
        this.ip_addr = ip_addr;
        this.port = port;
    }

}

public class ClientConnection {

    private static ClientConnection connObj = new ClientConnection();
    private static Object synchObj = new Object();

    static int threadNo = 0;
    static boolean acceptConnection = true;

    public static synchronized void startlistenForClients(final Session session) {
        try {

            if (session.connectionSocket != null) {
                stoplistenForClients(session);
                System.out.println("\nExisting Port closed");
                try {
                    Thread.sleep(5000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    //System.out.println(ex.toString());
                }
                startlistenForClients(session);
            }

            session.connectionSocket = new ServerSocket(Constants.ConnectionPORT);
            Constants.listenOnPort = true;
            while (true && acceptConnection) {
                System.out.println("\nListening for Client to Connect on PORT " + Constants.ConnectionPORT + "......[" + Utils.getCurrentTimeStamp() + "]");
                final Socket sock = session.connectionSocket.accept();
                System.out.println("\nClient COnnected ...... [" + Utils.getCurrentTimeStamp() + "]");
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        threadNo++;
                        ClientConnection.handleConnection(sock, session, threadNo);
                    }
                };

                Thread t = new Thread(r);

                t.start();

            }
            System.out.println("\nStopping Listening!!!!!!!1");
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                if (session.connectionSocket != null) {
                    stoplistenForClients(session);

                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
                //System.out.println("\nException" + ex.toString() + "\n");
            }
        }
    }

    public static void stoplistenForClients(Session session) {

        acceptConnection = false;
        Constants.currentSession = null;
        Constants.listenOnPort = false;

        if (Constants.dbManager != null) {
            Constants.dbManager.closeConnection();
        }
        Constants.dbManager = null;
        try {
            session.connectionSocket.close();
            session.connectionSocket = null;
            System.out.println("\nServer PORT Closed...Listening stopped!!!");
        } catch (IOException ex) {
            ex.printStackTrace();
            //System.out.println("\nIOException : " + ex.toString() + "\n");
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("\nException" + ex.toString() + "\n");
        }
    }

    public static void writeToMyLog(int expid, String macAdrress, String message) {

        String location = "";

        if (!Constants.experimentDetailsDirectory.endsWith("/")) {
            location = Constants.experimentDetailsDirectory + "/";
        }

        location += Integer.toString(expid) + macAdrress + "_log.txt";
        File file = new File(location);
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            Date date = new Date();
            System.out.println(date);
            bw.write(date.toString() + " --> " + message + "\n");
            bw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            //System.out.println("\nEXCEPTION [writeToMyLog]:" + ex.toString());
        }
    }

    public static String readFromStream(Socket socket, DataInputStream din, DataOutputStream dos) throws IOException {
//        synchronized (synchObj) {

        if (socket != null) {
            System.out.println("\nTrying to read from socket (blocking...) My Socket:" + socket + " [" + Utils.getCurrentTimeStamp() + "]");
//            synchronized (socket) {
//            System.out.println("\nRead from socket [" +Utils.getCurrentTimeStamp()+"]");
            String data = "";
            int length = din.readInt();
            System.out.println("\n[Read] Json Msg length : " + length + " Socket:" + socket + "[" + Utils.getCurrentTimeStamp() + "]");
            for (int i = 0; i < length; ++i) {
                data += (char) din.readByte();
                //     System.out.println("\nR Read: Json length : "+(i+1)*8);
                //     System.out.println("\nSuccess : Json byte");
            }
//            System.out.println("\nSocket Read Success : Json byte Complete [" + Utils.getCurrentTimeStamp() + "]");
            //         dos.writeInt(200);
            //         System.out.println("\nR Success : Json Write 200");
            //        dos.flush();
            System.out.println("\nRead from Socket " + socket + " Success!!! [" + Utils.getCurrentTimeStamp() + "]");
            /* try {
//                if (din.available() > 0) {
                    int length = din.readInt();
                    System.out.println("\nR Json length : " + length);
                    for (int i = 0; i < length; ++i) {
                        data += (char) din.readByte();
                        //     System.out.println("\nR Read: Json length : "+(i+1)*8);
                        //     System.out.println("\nSuccess : Json byte");
                    }
                    System.out.println("\nR Success : Json byte Complete");
                    //         dos.writeInt(200);
                    //         System.out.println("\nR Success : Json Write 200");
                    //        dos.flush();
                    System.out.println("\nRead from Socket Success!!!");
 //               }
            } catch (IOException ex) {
                System.out.println("\n[1] IOEx :" + ex.toString() + "-->" + socket);
            } catch (Exception ex) {
                System.out.println("\n[2] Ex :" + ex.toString() + "-->" + socket);
            }*/
            return data;
//            }
        } else {
            return null;
        }

        //}
    }

    public static int writeToStream(DeviceInfo d, String json, String message) throws IOException {

        if (d.socket != null) {
//            System.out.println("\nTrying to Write to socket [" + Utils.getCurrentTimeStamp() + "]");
//            synchronized (d.socket) {
            System.out.println("\nWriting to socket [Mac:" + d.getMacAddress() + Utils.getCurrentTimeStamp() + "]");
            int response = 200;

            d.outStream.writeInt(json.length());
//            System.out.println("\n[Write] Json Length [" + Utils.getCurrentTimeStamp() + "]");
            d.outStream.writeBytes(json);
            d.outStream.flush();
            System.out.println("\nWrite Success: Json String [Mac:" + d.getMacAddress() + Utils.getCurrentTimeStamp() + "]");

            /*   try {
//                synchronized (connObj) {
                    d.outStream.writeInt(json.length());
                    System.out.println("\nW Json Length");
                    d.outStream.writeBytes(json);
                    d.outStream.flush();
                    System.out.println("\nW Success: Json String");
  //              }
                //d.outStream.writeInt(message.length());
                //d.outStream.writeBytes(message);
                //    d.outStream.flush();
                //       response = d.inpStream.readInt();
                System.out.println("\nWrite to Socket Success!!! Res:" + response);
            } catch (IOException ex) {
                System.out.println("\n[3] IOEx :" + ex.toString() + "-->" + d.socket);
            } catch (Exception ex) {
                System.out.println("\n[4] Ex :" + ex.toString() + "-->" + d.socket);
            }*/
            return response;
//            }
        } else {
            System.out.println("\nNull Socket : Mac = " + d.macAddress);
            return 408;
        }
    }

    static void handleConnection(Socket sock, Session session, int tid) {

        System.out.println("\n\n\n---------------->>>>>>>>>[" + tid + "]");

        try {
            int count = 0;
            boolean newConnection = true;
            String ip_add = sock.getInetAddress().toString();
            String[] _ip_add = ip_add.split("/");

            String macAddress = "";
            DeviceInfo myDevice = null;
            InputStream in = sock.getInputStream();
            OutputStream out = sock.getOutputStream();
            DataInputStream dis = new DataInputStream(in);
            DataOutputStream dos = new DataOutputStream(out);

            while (true) {

                System.out.println("\n[" + tid + "] My Socket : " + sock);

                String receivedData = ClientConnection.readFromStream(sock, dis, dos).trim();
                if (receivedData.equals("") || receivedData == null) {
                    System.out.println("\n[Empty/Null Data][" + tid + "]");
                } else {

                    System.out.println("\nReceived1 : [" + Utils.getCurrentTimeStamp() + "]" + receivedData);
                    receivedData = receivedData.replace(":true,", ":\"true\",");
                    receivedData = receivedData.replace(":false,", ":\"false\",");

                    System.out.println("\nReceived2 : [" + Utils.getCurrentTimeStamp() + "]" + receivedData);

                    Map<String, String> jsonMap = null;
                    JSONParser parser = new JSONParser();

                    ContainerFactory containerFactory = new ContainerFactory() {

                        @SuppressWarnings("rawtypes")
                        @Override
                        public List creatArrayContainer() {
                            return new LinkedList();
                        }

                        @SuppressWarnings("rawtypes")
                        @Override
                        public Map createObjectContainer() {
                            return new LinkedHashMap();
                        }
                    };

                    try {
                        jsonMap = (Map<String, String>) parser.parse(receivedData, containerFactory);

                        if (jsonMap != null) {

                            String action = jsonMap.get(Constants.action);

                            if (action.compareTo(Constants.heartBeat) == 0 || action.compareTo(Constants.heartBeat1) == 0 || action.compareTo(Constants.heartBeat2) == 0) {

                                macAddress = jsonMap.get(Constants.macAddress);
                                DeviceInfo device = session.connectedClients.get(jsonMap.get(Constants.macAddress));

                                if (macAddress != null && !macAddress.equalsIgnoreCase("")) {

// ----------------------------------------------------------                                    
                                    try {
                                        long wakeUptime = (Constants.currentSession.getWakeUpDuration()) - ((System.currentTimeMillis()) - Constants.currentSession.getStartwakeUpDuration()) / 1000;
                                        if (wakeUptime > 0 && !Boolean.parseBoolean(jsonMap.get(Constants.isInForeground))) {
                                            String jsonString = Utils.getWakeUpClientsJson(Long.toString(wakeUptime));
                                            Thread sendData = new Thread(new SendData(Constants.currentSession.getCurrentExperimentId(), device, 9, jsonString));
                                            sendData.start();
                                            System.out.println("Reply to HearBeat : Sending WakeUp time : " + wakeUptime + "[ " + macAddress + " ]");

                                        } else {
                                            System.out.println("N0-Reply to HearBeat : WakeUp time : " + wakeUptime + " ForGround : " + jsonMap.get(Constants.isInForeground) + " [ " + macAddress + " ]");
                                        }
                                    } catch (Exception ex) {
                                        System.out.println("JSON parsing exception# :" + ex.toString());
                                        ex.printStackTrace();
                                    }
// ----------------------------------------------------------

                                    String appversion = jsonMap.get(Constants.appversion);
                                    String deviceName = "";
                                    try {
//                                            newDevice.setOsVersion(jsonMap.get(Constants.androidVersion));
                                        deviceName = jsonMap.get(Constants.devicename);
                                    } catch (Exception ex) {
                                        System.out.println("JSON parsing exception* : " + ex.toString());
                                        ex.printStackTrace();
                                    }

                                    String androidVersion = "";

                                    try {
//                                            newDevice.setOsVersion(jsonMap.get(Constants.androidVersion));
                                        androidVersion = jsonMap.get(Constants.androidVersion);
                                    } catch (Exception ex) {
                                        System.out.println("JSON parsing exception*# : " + ex.toString());
                                        ex.printStackTrace();
                                    }

                                    DBManager.updateAppUserHeartBeatInfo(macAddress, appversion, deviceName, androidVersion);

                                    // heartbeat    
                                    System.out.println("\n [" + tid + "] HeartBeat Received : " + macAddress + " " + (++count) + " [" + Utils.getCurrentTimeStamp() + "]");

                                    if (device == null) { // first time from this device. ie new connection

                                        System.out.println("<<<== 1 ==>>>");
                                        DeviceInfo newDevice = new DeviceInfo();
                                        newDevice.setIp(jsonMap.get(Constants.ip));
                                        newDevice.setPort(Integer.parseInt(jsonMap.get(Constants.port)));
                                        newDevice.setMacAddress(jsonMap.get(Constants.macAddress));
                                        newDevice.setBssid(jsonMap.get(Constants.bssid));
                                        newDevice.setSsid(jsonMap.get(Constants.ssid));
                                        newDevice.setProcessorSpeed(Integer.parseInt(jsonMap.get(Constants.processorSpeed)));
                                        newDevice.setLinkSpeed(jsonMap.get(Constants.linkSpeed));
                                        newDevice.setRssi(jsonMap.get(Constants.rssi));
                                        newDevice.setNumberOfCores(Integer.parseInt(jsonMap.get(Constants.numberOfCores)));
                                        newDevice.setMemory(Integer.parseInt(jsonMap.get(Constants.memory)));
                                        newDevice.setStorageSpace(Integer.parseInt(jsonMap.get(Constants.storageSpace)));
                                        newDevice.setBssidList(jsonMap.get(Constants.bssidList).split(";"));
                                        try {
//                                            newDevice.setOsVersion(jsonMap.get(Constants.androidVersion));
                                            newDevice.setInForeground(Boolean.parseBoolean(jsonMap.get(Constants.isInForeground)));
                                        } catch (Exception ex) {
                                            System.out.println("JSON parsing exception1 : " + ex.toString());
                                            ex.printStackTrace();
                                        }

                                        try {
//                                            newDevice.setOsVersion(jsonMap.get(Constants.androidVersion));
                                            newDevice.setDeviceName(jsonMap.get(Constants.devicename));
                                        } catch (Exception ex) {
                                            System.out.println("JSON parsing exception2 : " + ex.toString());
                                            ex.printStackTrace();
                                        }

                                        try {
                                            newDevice.setAndroidVersion(jsonMap.get(Constants.androidVersion));
                                        } catch (Exception ex) {
                                            System.out.println("JSON parsing exception3 : " + ex.toString());
                                            ex.printStackTrace();

                                        }

                                        // newDevice.setSsid(jsonMap.get(Constants.bssidList));
                                        /* String apInfo = jsonMap.get(Constants.bssidList);

                                    if (apInfo != null || !apInfo.equals("")) {
                                          System.out.println("\nInside Bssid List1");
                                        String[] bssidInfo = apInfo.split(";");
                                        NeighbourAccessPointDetails[] obj = new NeighbourAccessPointDetails[bssidInfo.length];

                                        for (int i = 0; i < bssidInfo.length; i++) {
                                            String[] info = bssidInfo[i].split(",");
                                            obj[i].setBssid(info[0]);
                                            obj[i].setRssi(info[1]);
                                            obj[i].setRssi(info[2]);
                                        }
                                        newDevice.setBssidList(obj);
                                    }*/
                                        Date date = Utils.getCurrentTimeStamp();
                                        newDevice.setLastHeartBeatTime(date);
                                        newDevice.setInpStream(dis);
                                        newDevice.setOutStream(dos);
                                        newDevice.setConnectionStatus(true);
                                        newDevice.setThread(Thread.currentThread());
                                        newDevice.setSocket(sock);
                                        newDevice.setGetlogrequestsend(false);

                                        /*
                                    remaining parameters needs to be added!!!
                                         */
                                        session.connectedClients.put(jsonMap.get(Constants.macAddress), newDevice);

                                    } else // subsequent heartbeats /  reconnection from same client
                                     if (newConnection) { // reconnection from same client

                                            System.out.println("<<<== 2 ==>>>");
                                            if (device.thread != null) {
                                                device.thread.interrupt();
                                                System.out.println("\n!!!!!1[" + tid + "] Interrupting old thread [" + Utils.getCurrentTimeStamp() + "]");
                                            }

                                            DeviceInfo newDevice = new DeviceInfo();
                                            newDevice.setIp(jsonMap.get(Constants.ip));
                                            newDevice.setPort(Integer.parseInt(jsonMap.get(Constants.port)));
                                            newDevice.setMacAddress(jsonMap.get(Constants.macAddress));
                                            newDevice.setBssid(jsonMap.get(Constants.bssid));
                                            newDevice.setSsid(jsonMap.get(Constants.ssid));
                                            newDevice.setProcessorSpeed(Integer.parseInt(jsonMap.get(Constants.processorSpeed)));
                                            newDevice.setLinkSpeed(jsonMap.get(Constants.linkSpeed));
                                            newDevice.setRssi(jsonMap.get(Constants.rssi));
                                            newDevice.setNumberOfCores(Integer.parseInt(jsonMap.get(Constants.numberOfCores)));
                                            newDevice.setMemory(Integer.parseInt(jsonMap.get(Constants.memory)));
                                            newDevice.setStorageSpace(Integer.parseInt(jsonMap.get(Constants.storageSpace)));
                                            newDevice.setBssidList(jsonMap.get(Constants.bssidList).split(";"));

                                            try {
//                                                newDevice.setOsVersion(jsonMap.get(Constants.androidVersion));
                                                newDevice.setInForeground(Boolean.parseBoolean(jsonMap.get(Constants.isInForeground)));
                                            } catch (Exception ex) {
                                                System.out.println("JSON parsing exception");
                                                ex.printStackTrace();
                                            }

                                            try {
//                                            newDevice.setOsVersion(jsonMap.get(Constants.androidVersion));
                                                newDevice.setDeviceName(jsonMap.get(Constants.devicename));
                                            } catch (Exception ex) {
                                                System.out.println("JSON parsing exception2 : " + ex.toString());
                                                ex.printStackTrace();
                                            }

                                            try {
                                                newDevice.setAndroidVersion(jsonMap.get(Constants.androidVersion));
                                            } catch (Exception ex) {
                                                System.out.println("JSON parsing exception3 : " + ex.toString());
                                                ex.printStackTrace();

                                            }

                                            /* String apInfo = jsonMap.get(Constants.bssidList);
                                        if (apInfo != null || !apInfo.equals("")) {
                                            System.out.println("\nInside Bssid List");
                                                    
                                            String[] bssidInfo = apInfo.split(";");
                                            NeighbourAccessPointDetails[] obj = new NeighbourAccessPointDetails[bssidInfo.length];
                                            for (int i = 0; i < bssidInfo.length; i++) {
                                                String[] info = bssidInfo[i].split(",");
                                                obj[i].setBssid(info[0]);
                                                obj[i].setRssi(info[1]);
                                                obj[i].setRssi(info[2]);
                                            }
                                            newDevice.setBssidList(obj);
                                        }*/
                                            Date date = Utils.getCurrentTimeStamp();
                                            newDevice.setLastHeartBeatTime(date);
                                            newDevice.setInpStream(dis);
                                            newDevice.setOutStream(dos);
                                            newDevice.setSocket(sock);

                                            newDevice.setThread(Thread.currentThread());
                                            newDevice.setConnectionStatus(true);
                                            newDevice.setGetlogrequestsend(false);
                                            /*
                                        remaining parameters needs to be added!!!
                                             */
                                            session.connectedClients.remove(device.macAddress);
                                            session.connectedClients.put(jsonMap.get(Constants.macAddress), newDevice);

                                            if (session.filteredClients.contains(device)) {
                                                session.filteredClients.remove(device);
                                                session.filteredClients.add(newDevice);
                                            }

                                        } else { // heartbeat

                                            System.out.println("<<<== 3 ==>>>");

                                            Date date = Utils.getCurrentTimeStamp();

                                            device.setIp(jsonMap.get(Constants.ip));
                                            device.setPort(Integer.parseInt(jsonMap.get(Constants.port)));
                                            device.setMacAddress(jsonMap.get(Constants.macAddress));
                                            device.setBssid(jsonMap.get(Constants.bssid));
                                            device.setSsid(jsonMap.get(Constants.ssid));

                                            try {
//                                                device.setOsVersion(jsonMap.get(Constants.androidVersion));
                                                device.setInForeground(Boolean.parseBoolean(jsonMap.get(Constants.isInForeground)));
                                            } catch (Exception ex) {
                                                System.out.println("JSON parsing exception");
                                                ex.printStackTrace();
                                            }

                                            try {
//                                            newDevice.setOsVersion(jsonMap.get(Constants.androidVersion));
                                                device.setDeviceName(jsonMap.get(Constants.devicename));
                                            } catch (Exception ex) {
                                                System.out.println("JSON parsing exception2 : " + ex.toString());
                                                ex.printStackTrace();
                                            }

                                            try {
                                                device.setAndroidVersion(jsonMap.get(Constants.androidVersion));
                                            } catch (Exception ex) {
                                                System.out.println("JSON parsing exception3 : " + ex.toString());
                                                ex.printStackTrace();

                                            }

                                            device.setLastHeartBeatTime(date);
                                            device.setInpStream(dis);
                                            device.setOutStream(dos);
                                            device.setSocket(sock);
                                            device.setConnectionStatus(true);
                                        }
                                }
                            } else if (action.compareTo(Constants.experimentOver) == 0) {

                                macAddress = jsonMap.get(Constants.macAddress);

                                System.out.println("\n[" + tid + "] Experiment [No:" + jsonMap.get(Constants.experimentNumber) + "] Over Mesage received [MAC: " + macAddress + " " + Utils.getCurrentTimeStamp() + "]");
                                // experiment over
                                // i need mac address from here
                                // ip and port also preferred
                                DeviceInfo device = session.connectedClients.get(jsonMap.get(Constants.macAddress));

                                if (device == null) { // new connection

                                    System.out.println("<<<== 4 ==>>>");

                                    DeviceInfo newDevice = new DeviceInfo();
                                    newDevice.setIp(jsonMap.get(Constants.ip));
                                    newDevice.setPort(Integer.parseInt(jsonMap.get(Constants.port)));
                                    newDevice.setMacAddress(jsonMap.get(Constants.macAddress));
                                    //Date date = Utils.getCurrentTimeStamp();
                                    //newDevice.setLastHeartBeatTime(date);
                                    newDevice.setInpStream(dis);
                                    newDevice.setOutStream(dos);
                                    newDevice.setSocket(sock);
                                    newDevice.setThread(Thread.currentThread());
                                    newDevice.setGetlogrequestsend(false);
                                    newDevice.setConnectionStatus(true);

                                    newDevice.setExpOver(1); //

                                    if (DBManager.updateExperimentOverStatus(Integer.parseInt(jsonMap.get(Constants.experimentNumber)), newDevice.getMacAddress())) {
                                        System.out.println("\nDB Update ExpOver Success [" + Utils.getCurrentTimeStamp() + "]");
                                    } else {
                                        System.out.println("\nDB Update ExpOver Failed [" + Utils.getCurrentTimeStamp() + "]");
                                    }

                                    /*
                                        remaining parameters needs to be added!!!
                                     */
                                    session.connectedClients.put(jsonMap.get(Constants.macAddress), newDevice);

                                } else if (newConnection) { // reconnction from the same client

                                    System.out.println("<<<== 5 ==>>>");

                                    if (device.thread != null) {
                                        device.thread.interrupt();
                                        System.out.println("\n@#2[" + tid + "] Interrupting old thread [" + Utils.getCurrentTimeStamp() + "]");
                                    }

                                    DeviceInfo newDevice = new DeviceInfo();
                                    newDevice.setIp(jsonMap.get(Constants.ip));
                                    newDevice.setPort(Integer.parseInt(jsonMap.get(Constants.port)));
                                    newDevice.setMacAddress(jsonMap.get(Constants.macAddress));
                                    //Date date = Utils.getCurrentTimeStamp();
                                    //newDevice.setLastHeartBeatTime(date);
                                    newDevice.setInpStream(dis);
                                    newDevice.setOutStream(dos);
                                    newDevice.setSocket(sock);

                                    newDevice.setThread(Thread.currentThread());
                                    newDevice.setGetlogrequestsend(false);
                                    newDevice.setConnectionStatus(true);

                                    /*
                                        remaining parameters needs to be added!!!
                                     */
                                    newDevice.setExpOver(1); //

                                    if (DBManager.updateExperimentOverStatus(Integer.parseInt(jsonMap.get(Constants.experimentNumber)), newDevice.getMacAddress())) {
                                        System.out.println("\nDB Update ExpOver Success [" + Utils.getCurrentTimeStamp() + "]");
                                    } else {
                                        System.out.println("\nDB Update ExpOver Failed [" + Utils.getCurrentTimeStamp() + "]");
                                    }

                                    session.connectedClients.remove(device.macAddress);
                                    session.connectedClients.put(jsonMap.get(Constants.macAddress), newDevice);

                                    if (session.filteredClients.contains(device)) {
                                        session.filteredClients.remove(device);
                                        session.filteredClients.add(newDevice);
                                    }

                                } else {

                                    System.out.println("<<<== 6 ==>>>");

                                    // alread connected client
                                    // device.setExpOver(jsonMap.get(Constants.macAddress))
                                    device.setConnectionStatus(true);
                                    device.setSocket(sock);
                                    device.setExpOver(1); //

                                    if (DBManager.updateExperimentOverStatus(Integer.parseInt(jsonMap.get(Constants.experimentNumber)), device.getMacAddress())) {
                                        System.out.println("\nDB Update ExpOver Success [" + Utils.getCurrentTimeStamp() + "]");
                                    } else {
                                        System.out.println("\nDB Update ExpOver Failed [" + Utils.getCurrentTimeStamp() + "]");
                                    }

                                }

                            } else if (action.compareTo(Constants.expacknowledgement) == 0) {

                                int expNumber = Integer.parseInt(jsonMap.get(Constants.experimentNumber));
//                                System.out.println("\nExperiment number : " + expNumber);
                                System.out.println("\nExperiment [No:" + expNumber + "] Acknowledgement Received --> [ MAC: " + macAddress + " " + Utils.getCurrentTimeStamp() + "]");
                                //int sessionId = Utils.getCurrentSessionID();
                                int expId = 1;
///important                                int expId =1;// Utils.getCurrentExperimentID(Integer.toString(1));
//                                System.out.println("\nExperiment number : " + expNumber + "== " + expId);

                                //            if (expNumber == expId) {
                                if (macAddress != null && !macAddress.equals("")) {
                                    DeviceInfo device = session.connectedClients.get(macAddress);
                                    session.actualFilteredDevices.add(device);
//                                    System.out.println("\n Ack : " + expNumber + " Acknowledgement Received!!! [" + Utils.getCurrentTimeStamp() + "]");

                                    if (DBManager.updateExpStartReqSendStatus(expNumber, macAddress, 1, "Successfully sent Experiment Request")) {
                                        System.out.println("\n Ack : " + expNumber + " DB updated Successfully");
                                    } else {
                                        System.out.println("\n Ack : " + expNumber + " DB updation Failed");
                                    }
///important                                        Utils.addExperimentDetails(expId, device, false);
                                }
                                //              }
                                // update the db.
                            } else if (action.compareTo(Constants.controlFileacknowledgement) == 0) {

//   {"action":"ack","fileid":"196"}
                                System.out.println("\nControl FIle Acknowledgement Received --> [MAC: " + macAddress + "  " + Utils.getCurrentTimeStamp() + "]");
                                int fileid = Integer.parseInt(jsonMap.get(Constants.fileId));
                                System.out.println("\nFileID  : " + fileid);
                                //int sessionId = Utils.getCurrentSessionID();
                                int expId = 1;
///important                                int expId =1;// Utils.getCurrentExperimentID(Integer.toString(1));
                                //      System.out.println("\nExperiment number : " + expNumber + "== " + expId);

                                //            if (expNumber == expId) {
                                if (macAddress != null && !macAddress.equals("")) {
                                    DeviceInfo device = session.connectedClients.get(macAddress);
                                    // session.actualFilteredDevices.add(device);
                                    System.out.println("\n Control File Ack  Mac : " + macAddress + " Fid: " + fileid + " Acknowledgement Received!!! [" + Utils.getCurrentTimeStamp() + "]");

                                    Constants.currentSession.getControlFileSendingSuccessClients().put(macAddress, "File sent & Ack received");

                                    if (Constants.currentSession.getControlFileSendingStatus().get(device.getMacAddress()) == null) {
                                        Constants.currentSession.getControlFileSendingStatus().put(device.getMacAddress(), "Control File Ack : Received");
                                    } else {
                                        String _status = Constants.currentSession.getControlFileSendingStatus().get(device.getMacAddress());
                                        _status += "<br>Control File Ack : Received";
                                        Constants.currentSession.getControlFileSendingStatus().put(device.getMacAddress(), _status);
                                    }

                                    if (DBManager.addControlFileSendingDetails(fileid, macAddress)) {// updateControlFileSendStatus(expNumber, macAddress, 1, "Successfully sent Control File")) {
                                        System.out.println("\n Control File Ack : MAC: " + macAddress + " Fid: " + fileid + " DB updated Successfully");
                                    } else {
                                        System.out.println("\n Control File Ack : MAC: " + macAddress + " Fid: " + fileid + " DB updation Failed");
                                    }
///important                                        Utils.addExperimentDetails(expId, device, false);
                                }
                                //              }
                                // update the db.

                            } else if (action.compareTo(Constants.userEmail) == 0) {

                                macAddress = jsonMap.get(Constants.macAddress);
                                System.out.println("\nUser Info Received --> [MAC: " + macAddress + "" + Utils.getCurrentTimeStamp() + "]");
                                if (macAddress != null && !macAddress.equalsIgnoreCase("")) {
                                    /*if (macAddress.equals("") || macAddress == null) {
                                    macAddress = jsonMap.get(Constants.macAddress);                                    
                                }*/
                                    String email = jsonMap.get(Constants.email);
//                                    String appVersion = "";
//                                    appVersion = jsonMap.get(Constants.appversion);
                                    DBManager.updateAppUserInfo(email, macAddress);
                                    //updateAppUserInfo(String email, String username, String contactinfo){
                                }

                            } else {
                                System.out.println("\n[" + tid + "] Some Other Operation...");
                            }
                            newConnection = false;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        // System.out.println("Json Ex : " + ex.toString());
                    }
                }

                try {
                    Thread.sleep(5000);  // wait for interrupt
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    //System.out.println("\n[" + tid + "] InterruptedException 1 : " + ex.toString() + "\n");

                    try {
                        sock.close();
                    } catch (IOException ex1) {
                        ex1.printStackTrace();
                        //System.out.println("\n[" + tid + "] IOException5 : " + ex1.toString() + "\n");
                    }
                    break; //
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            //System.out.println("\n [" + tid + "] IOException1 : " + ex.toString() + "\n");
            try {
                sock.close();
                //    session.connectedClients.remove(conn);
            } catch (IOException ex1) {
                ex1.printStackTrace();
                //System.out.println("\n[" + tid + "] IOException2 : " + ex1.toString() + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println("\n[" + tid + "] IOException3 : " + ex.toString() + "\n");
            try {
                sock.close();
                //    session.connectedClients.remove(conn);
            } catch (IOException ex1) {
                ex1.printStackTrace();
                //System.out.println("\n[" + tid + "] IOException4 : " + ex1.toString() + "\n");
            }
        }

    }
}
