package com.iitb.cse;

import java.net.ServerSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author sanchitgarg
 * This class is used for holding information for a session.
 * 
 */
public class Session {
	@Getter @Setter Integer sessionID = 1;		//ID of the session
	@Getter @Setter String name;				//name of session
	@Getter @Setter String user;				//username of user holding that session
	@Getter @Setter boolean serverOn = true;	//no use of the variable
	//true if any experiment in the session is running, false otherwise
        
        @Getter @Setter boolean sendingControlFile = false;
        @Getter @Setter boolean sendingControlFileStatus = false;
        
	@Getter @Setter boolean experimentRunning = false;
        @Getter @Setter boolean wakeUpStatus = false;
        
        @Getter @Setter long startwakeUpDuration = 0;
        @Getter @Setter long wakeUpDuration = 0;
        
        
        
        @Getter @Setter String retryControlFileId= "";	
        @Getter @Setter String retryControlFileName= "";	
        
        
        
        @Getter @Setter boolean fetchingLogFiles = false;
        @Getter @Setter boolean fetchingLogFilesStatus = false;
	//Current Experiment
	@Getter @Setter Experiment curExperiment;
        @Getter @Setter int currentExperimentId = 0;

	//HashMap which stores registered android-clients
	//<key, value> = <macaddress of deivce, DeviceInfo>
	@Getter @Setter ConcurrentHashMap<String, DeviceInfo> connectedClients = new ConcurrentHashMap<String, DeviceInfo>();
	@Getter @Setter CopyOnWriteArrayList<DeviceInfo> filteredClients = new CopyOnWriteArrayList<DeviceInfo>();
	@Getter @Setter CopyOnWriteArrayList<DeviceInfo> actualFilteredDevices = new CopyOnWriteArrayList<DeviceInfo>();
        @Getter @Setter CopyOnWriteArrayList<DeviceInfo> apConfFilteredDevices = new CopyOnWriteArrayList<DeviceInfo>();
        @Getter @Setter CopyOnWriteArrayList<DeviceInfo> configFilteredDevices = new CopyOnWriteArrayList<DeviceInfo>();        
        @Getter @Setter ConcurrentHashMap<String, DeviceInfo> getLogFilefFilteredDevices = new ConcurrentHashMap<String, DeviceInfo>();
        
        @Getter @Setter ConcurrentHashMap<String, String> controlFileSendingFailedClients = new ConcurrentHashMap<String, String>();
        
        @Getter @Setter ConcurrentHashMap<String, String> controlFileSendingSuccessClients = new ConcurrentHashMap<String, String>();
        
        @Getter @Setter ConcurrentHashMap<String, String> controlFileSendingStatus = new ConcurrentHashMap<String, String>();
        
        @Getter @Setter ConcurrentHashMap<String,String> updateAppClients = new ConcurrentHashMap<String,String>();
        
        @Getter @Setter CopyOnWriteArrayList<DeviceInfo> wakeUpDevices = new CopyOnWriteArrayList<DeviceInfo>();
        
        @Getter @Setter ConcurrentHashMap<String, String> wakeUpDevicesStatus = new ConcurrentHashMap<String, String>();
        
        
        
	@Getter @Setter Calendar cal = Calendar.getInstance();
	//counters for thread
	@Getter @Setter Integer startExpTCounter=0;
	@Getter @Setter Integer stopExpTCounter=0;
	@Getter @Setter Integer clearRegTCounter=0;
	@Getter @Setter Integer refreshTCounter=0;	
 //       @Getter @Setter Integer logFileReceived=0;
        @Getter @Setter Integer logFileSend=0;        
        @Getter @Setter ServerSocket connectionSocket;

	/**
	* constructor
	*/
	public Session(String u){
		user=u;
	}
 
	/*
	* Decrements the refreshTCounter 
	*/
	public void DecrementRefreshTCounter(){
		refreshTCounter--;
		if(refreshTCounter<=0){
		}
	}
}
