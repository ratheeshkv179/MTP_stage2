package com.iitb.cse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author sanchitgarg This class is used for holding information about a
 * device.
 *
 */
class NeighbourAccessPointDetails {

    @Getter
    @Setter
    String bssid = "";

    @Getter
    @Setter
    String ssid = "";

    @Getter
    @Setter
    String rssi = "";

}

public class DeviceInfo {

    @Getter
    @Setter
    int port = 0;		//port through which device is registered
    @Getter
    @Setter
    String ip = "";		//ip of the device
    @Getter
    @Setter
    String macAddress = "00:00:00:00:00:00";	//macaddress of device
    @Getter
    @Setter
    String osVersion = "1";		//Android Build Number
    @Getter
    @Setter
    String wifiVersion = "802.11";	//Wifi version number
    @Getter
    @Setter
    int numberOfCores = 1;			//number of cores
    @Getter
    @Setter
    int storageSpace = 0;			//storage space in SD card in MB
    @Getter
    @Setter
    int memory = 0;					//RAM of the device in MB
    @Getter
    @Setter
    int processorSpeed = 0;			//processor speed in MHz
    @Getter
    @Setter
    int wifiSignalStrength = 1;	// Wifi connection Strength from 1-poor to 10-full

    @Getter
    @Setter
    boolean isInForeground = false;
    @Getter
    @Setter
    String rssi = "rssi";
    @Getter
    @Setter
    String bssid = "bssid";

    @Getter
    @Setter
    String[] bssidList = null;

    @Getter
    @Setter
    String ssid = "ssid";

    @Getter
    @Setter
    String linkSpeed = "linkSpeed";

    @Getter
    @Setter
    boolean connectionStatus = false;
    @Getter
    @Setter
    Socket socket = null;
//        @Getter @Setter boolean expOver = false;
    @Getter
    @Setter
    Date lastHeartBeatTime = null;
    @Getter
    @Setter
    DataInputStream inpStream = null;
    @Getter
    @Setter
    DataOutputStream outStream = null;
    @Getter
    @Setter
    Thread thread = null;

    @Getter
    @Setter
    boolean getlogrequestsend = false;

    @Getter
    @Setter
    boolean logFileReceived = false;

    @Getter
    @Setter
    int expOver = 0;

    @Getter
    @Setter
    String details = "";

       @Getter
    @Setter
    String deviceName = "devicename";
       
            @Getter
    @Setter
    String androidVersion = "androidVersion";
       
    /**
     * constructor
     */
    public DeviceInfo() {
    }

    public void setNeighbourAccessPointDetails(String bssInfo) {
        bssInfo.split(";");
    }

    /**
     * constructor
     */
    public DeviceInfo(DeviceInfo d) {

        port = d.port;
        ip = d.ip;
        macAddress = d.macAddress;
        osVersion = d.osVersion;
        wifiVersion = d.wifiVersion;
        numberOfCores = d.numberOfCores;
        storageSpace = d.storageSpace;
        memory = d.memory;
        processorSpeed = d.processorSpeed;
        wifiSignalStrength = d.wifiSignalStrength;
        rssi = d.rssi;
        bssid = d.bssid;
        ssid = d.ssid;
        linkSpeed = d.linkSpeed;
        connectionStatus = d.connectionStatus;
        socket = d.socket;
        lastHeartBeatTime = d.lastHeartBeatTime;
        inpStream = d.inpStream;
        outStream = d.outStream;
        thread = d.thread;
        getlogrequestsend = d.getlogrequestsend;
        expOver = d.expOver;
    }

    /**
     * prints the device information
     */
    void print() {
        System.out.println("IP: " + ip);
        System.out.println("Port: " + port);
        System.out.println("MAC Address: " + macAddress);
        System.out.println("WIFI Version " + wifiVersion);
        System.out.println("No of Cores: " + numberOfCores);
        System.out.println("Storage Space: " + storageSpace);
        System.out.println("Memory: " + memory);
        System.out.println("Processor Speed: " + processorSpeed);
        System.out.println("WIFI signal Strength: " + wifiSignalStrength);

    }
}
