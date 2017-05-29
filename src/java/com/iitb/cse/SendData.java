/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitb.cse;

import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ratheeshkv
 */
public class SendData extends Thread {

    final int startExp = 0;
    final int stopExp = 1;
    final int refresh = 2;
    final int apsetting = 3;
    final int getLogFile = 4;
    final int heartBeat = 5;
    final int serverConf = 6;
    final int sendControlFile = 7;
    final int sendUpdateReq = 8;
    final int wakeUpClient = 9;

    int expID;
    DeviceInfo device;
    int whatToDo;
    String jsonString;
//    String message;
    String file_id;
//    String file_name;

    public SendData(int expID, DeviceInfo device, int whatToDo, String jsonString, String file_id) {

        this.expID = expID;
        this.device = device;
        this.whatToDo = whatToDo;
        this.jsonString = jsonString;
        this.file_id = file_id;
    }

    public SendData(int expID, DeviceInfo device, int whatToDo, String jsonString) {
        this.expID = expID;
        this.device = device;
        this.whatToDo = whatToDo;
        this.jsonString = jsonString;
    }

    @Override
    public void run() {

        switch (whatToDo) {

            case apsetting:

                System.out.println("\nInside Sending APSettings...mac: " + device.macAddress + " " + device.socket);
                int status;
                try {
                    status = ClientConnection.writeToStream(device, jsonString, file_id);
                    if (status == Constants.responseOK) {
                        device.setDetails("\nAP Configuration File sent successfully");
                        System.out.println("\nAPsettings : Successfully sent AP conf file mac:" + device.getMacAddress());

                    } else {
                        device.setDetails("\nAP Configuration File sending Failed");
                        System.out.println("\nAPsettings : Unable to send AP Conf file mac:" + device.getMacAddress());
                    }

                } catch (IOException ex) {

                    device.setDetails("\nSOcket Error : Sending AP conf file failed " + ex.toString());
                    System.out.println("\nSOcket Error : Sending AP conf file failed mac:" + device.getMacAddress());
                }

                break;

            case startExp:

                try {
                    status = ClientConnection.writeToStream(device, jsonString, file_id);

                    if (status == Constants.responseOK) {
                        System.out.println("\nStart Experiment:"+expID+" Successfully sent file mac:" + device.getMacAddress() + Utils.getCurrentTimeStamp());
                        if (DBManager.addExperimentDetails(expID, device, file_id, 0, "Experiment Request Sent Successfully and Waiting for Ack")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }
                    } else {
                        System.out.println("\nStart Experiment:"+expID+" : Unable to send file mac:" + device.getMacAddress() + Utils.getCurrentTimeStamp());
                        if (DBManager.addExperimentDetails(expID, device, file_id, 2, "Start Experiment Request Failed : Socket Error")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }
                    }
                } catch (IOException ex) {
                    System.out.println("\nSOcket Error Exp:"+expID+" Sending Control file failed mac: " + device.getMacAddress() + Utils.getCurrentTimeStamp());
                    System.out.println("\nStart Experiment: : Unable to send request");
                    if (DBManager.addExperimentDetails(expID, device, file_id, 2, "Start Experiment Request Failed : Exception " + ex.toString())) {
                        System.out.println("\nStart Experiment: Insert to DataBase Success");
                    } else {
                        System.out.println("\nStart Experiment: Insert to DataBase Failed");
                    }
                }

                /*
                synchronized (session.startExpTCounter) {
                    session.startExpTCounter--;
                    System.out.println("run() " + tid + ": value of startExpTCounter = " + session.startExpTCounter);
                
                }*/
                break;

            case stopExp:

                System.out.println("\nInside Stop Experiment ...");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, file_id);

                    if (status == Constants.responseOK) {
                        System.out.println("\nStop Experiment:"+expID+" Successfully sent  file mac:" + device.getMacAddress() + " " + Utils.getCurrentTimeStamp());

                    } else {
                        System.out.println("\nStop Experiment :"+expID+" Unable to send file mac:" + device.getMacAddress() + " " + Utils.getCurrentTimeStamp());
                    }
                } catch (IOException ex) {
                    System.out.println("\nSOcket Error :"+expID+" Sending Stop Exp failed mac:" + device.getMacAddress() + " " + Utils.getCurrentTimeStamp());
                }

                /*synchronized (session.stopExpTCounter) {
                    System.out.println("run() " + tid + ": value of stopExpTCounter = " + session.stopExpTCounter);
                    session.stopExpTCounter--;
                }*/
                break;

            case refresh:

                System.out.println("\nInside Refresh Registration ...");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, file_id);

                    if (status == Constants.responseOK) {
                        System.out.println("\nRefresh Registration: Successfully sent file " + " " + Utils.getCurrentTimeStamp());
                    } else {
                        System.out.println("\nRefresh Registration : Unable to send file " + " " + Utils.getCurrentTimeStamp());
                    }
                } catch (IOException ex) {
                    System.out.println("\nSOcket Error : Sending Refresh Exp failed " + " " + Utils.getCurrentTimeStamp());
                }

                /*synchronized (session.refreshTCounter) {
                    session.DecrementRefreshTCounter();
                    System.out.println("value of refreshTCounter=" + session.refreshTCounter);
                }*/
                break;

            case getLogFile:
                System.out.println("\nInside Get Log FIle Request..");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, file_id);
                    if (status == Constants.responseOK) {
                        device.setGetlogrequestsend(true);
                        device.setDetails("\nLog File request sent" + device.macAddress);
                        System.out.println("\nGet Log File request Successfully sent " + device.macAddress + " " + Utils.getCurrentTimeStamp());
                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 1);
                    } else {
                        device.setGetlogrequestsend(false);
                        device.setDetails("\nLog File request Sending Failed" + device.macAddress);
                        System.out.println("\nGet Log File request Sending Failed" + device.macAddress + " " + Utils.getCurrentTimeStamp());
                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 2);
                    }
                } catch (IOException ex) {
                    device.setGetlogrequestsend(false);
                    device.setDetails("\nSOcket Error : Sending GetLog Request Exp failed " + ex.toString());
                    System.out.println("\nSOcket Error : Sending GetLog Request Exp failed " + " " + Utils.getCurrentTimeStamp());
                }
                break;

            case heartBeat:
                System.out.println("\nInside Sending HEARTBEAT duration..");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, file_id);
                    if (status == Constants.responseOK) {
                        device.setGetlogrequestsend(true);
                        device.setDetails("\nNew HeartBeat duration sent");
                        System.out.println("\nNew HeartBeat duration Successfully sent ");
//                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 1);
                    } else {
                        device.setGetlogrequestsend(false);
                        device.setDetails("\nNew HeartBeat duration Sending Failed");
                        System.out.println("\nNew HeartBeat duration Sending Failed");
//                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 2);
                    }
                } catch (IOException ex) {
                    device.setGetlogrequestsend(false);
                    device.setDetails("\nSOcket Error : New HeartBeat duration Sending Exp failed " + ex.toString());
                    System.out.println("\nSOcket Error : New HeartBeat duration Sending Exp failed ");
                }
                break;
            case serverConf:
                System.out.println("\nInside Sending ServerConfiguration ..");
                try {
                    status = ClientConnection.writeToStream(device, jsonString, file_id);
                    if (status == Constants.responseOK) {
                        device.setGetlogrequestsend(true);
                        device.setDetails("\nServerConfiguration request sent");
                        System.out.println("\nServerConfiguration request Successfully sent ");
//                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 1);
                    } else {
                        device.setGetlogrequestsend(false);
                        device.setDetails("\nServerConfiguration request Sending Failed");
                        System.out.println("\nServerConfiguration request Sending Failed");
//                        DBManager.updateGetLogFileRequestStatus(expID, device.macAddress, 2);
                    }
                } catch (IOException ex) {
                    device.setGetlogrequestsend(false);
                    device.setDetails("\nSOcket Error : Sending ServerConfiguration Request Exp failed " + ex.toString());
                    System.out.println("\nSOcket Error : Sending ServerConfiguration Request Exp failed ");
                }

                /* System.out.println("\nInside GetLogFile Request ...");
                status = ClientConnection.writeToStream(device, jsonString, message);
                if (status == Constants.responseOK) {

                    System.out.println("\nGetLogFile : Successfully sent Json file ");
                    Utils.addSendLogRequestDetails(session.getCurrentExperiment(), device.getMacAddress(), "SUCCESS");
                    Experiment e = Main.getRunningExperimentMap().get(session.getCurrentExperiment());
                    e.SFIncrement();//
                    device.setGetlogrequestsend("SUCCESS");
                } else {

                    System.out.println("\nGetLogFile : Unable to send Json file ");
                    Utils.addSendLogRequestDetails(session.getCurrentExperiment(), device.getMacAddress(), "ERROR Code : " + status + "");
                    device.setGetlogrequestsend("ERROR Code : " + status);
                }
                 */
                break;

            case sendControlFile:
                try {
//                  controlFileReceviedClients
                    status = ClientConnection.writeToStream(device, jsonString, file_id);
                    if (status == Constants.responseOK) {
                        System.out.println("\nControl File :"+expID+" File Successfully sent [mac:" + device.getMacAddress() + "]" + " " + Utils.getCurrentTimeStamp());

                        if (Constants.currentSession.getControlFileSendingStatus().get(device.getMacAddress()) == null) {
                            Constants.currentSession.getControlFileSendingStatus().put(device.getMacAddress(), "Request Sent : Success");
                        } else {
                            String _status = Constants.currentSession.getControlFileSendingStatus().get(device.getMacAddress());
                            _status += "<br>Request Sent : Success";
                            Constants.currentSession.getControlFileSendingStatus().put(device.getMacAddress(), _status);
                        }

                        /*if (DBManager.addControlFileSendingDetails(expID, device, 0, "")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }*/
                    } else {
                        System.out.println("\nControl File :"+expID+" Unable to send file [mac:" + device.getMacAddress() + "]");

                        Constants.currentSession.getControlFileSendingFailedClients().put(device.macAddress, "Error:Sending Failed" + " " + Utils.getCurrentTimeStamp());

                        if (Constants.currentSession.getControlFileSendingStatus().get(device.getMacAddress()) == null) {
                            Constants.currentSession.getControlFileSendingStatus().put(device.getMacAddress(), "Request Sent : Failed");
                        } else {
                            String _status = Constants.currentSession.getControlFileSendingStatus().get(device.getMacAddress());
                            _status += "<br>Request Sent : Failed";
                            Constants.currentSession.getControlFileSendingStatus().put(device.getMacAddress(), _status);
                        }

//                        controlFileSendingFailedClients
                        /*
                        if (DBManager.addExperimentDetails(expID, device, 2, "Sending control file failed")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }
                         */
                    }
                } catch (IOException ex) {
                    System.out.println("\nSOcket Error : Sending Control file:"+expID+" failed MAC:  " + device.macAddress);
                    Constants.currentSession.getControlFileSendingFailedClients().put(device.macAddress, "Socket Exception :Sending Failed" + " " + Utils.getCurrentTimeStamp());

                    if (Constants.currentSession.getControlFileSendingStatus().get(device.getMacAddress()) == null) {
                        Constants.currentSession.getControlFileSendingStatus().put(device.getMacAddress(), "Request Sent : Socket err " + ex.toString());
                    } else {
                        String _status = Constants.currentSession.getControlFileSendingStatus().get(device.getMacAddress());
                        _status += "<br>Request Sent : Socket err " + ex.toString();
                        Constants.currentSession.getControlFileSendingStatus().put(device.getMacAddress(), _status);
                    }

                }

                break;

            case sendUpdateReq:

                try {
                    String macAddr = device.getMacAddress();
                    try {

                        status = ClientConnection.writeToStream(device, jsonString, file_id);

                        if (status == Constants.responseOK) {
                            System.out.println("\nSend Update : SUCCESS [mac:" + macAddr + "]");
                            Constants.currentSession.getUpdateAppClients().put(macAddr, "Success");
                            /*if (DBManager.addControlFileSendingDetails(expID, device, 0, "")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }*/
                        } else {
                            System.out.println("\nSend Update : FAILED [mac:" + macAddr + "]");
                            Constants.currentSession.getUpdateAppClients().put(macAddr, "Failed");

//                        controlFileSendingFailedClients
                            /*
                        if (DBManager.addExperimentDetails(expID, device, 2, "Sending control file failed")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }
                             */
                        }
                    } catch (IOException ex) {
                        System.out.println("\nSend Update : FAILED [mac:" + macAddr + "]");
                        Constants.currentSession.getUpdateAppClients().put(macAddr, "Failed Exception: " + ex.toString());

                    }
                } catch (Exception ex) {
                    System.out.println("\nSend Update : FAILED [mac:" + device.getMacAddress() + "]");
                    Constants.currentSession.getUpdateAppClients().put("", "Failed : Client is Not active " + ex.toString());
                }

                break;

            case wakeUpClient:
                try {
//                  controlFileReceviedClients
                    status = ClientConnection.writeToStream(device, jsonString, file_id);
                    if (status == Constants.responseOK) {
                        System.out.println("\nWakeUp Request : Sent Successfully [mac:" + device.getMacAddress() + "]");

                        Constants.currentSession.getWakeUpDevicesStatus().put(device.getMacAddress(), "Success");

                        /*if (DBManager.addControlFileSendingDetails(expID, device, 0, "")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }*/
                    } else {
                        System.out.println("\nWakeUp Request : Unable to send [mac:" + device.getMacAddress() + "]");

                        Constants.currentSession.getControlFileSendingFailedClients().put(device.macAddress, "Error:Sending Failed");
                        Constants.currentSession.getWakeUpDevicesStatus().put(device.getMacAddress(), "Sock Err: Sending failed");
//                        controlFileSendingFailedClients
                        /*
                        if (DBManager.addExperimentDetails(expID, device, 2, "Sending control file failed")) {
                            System.out.println("\nStart Experiment: Insert to DataBase Success");
                        } else {
                            System.out.println("\nStart Experiment: Insert to DataBase Failed");
                        }
                         */
                    }
                } catch (IOException ex) {
                    System.out.println("\nSOcket Error : Sending WakeUp request failed mac:" + device.macAddress);
                    Constants.currentSession.getControlFileSendingFailedClients().put(device.macAddress, "Socket Exception :Sending Failed");
                    Constants.currentSession.getWakeUpDevicesStatus().put(device.getMacAddress(), ex.toString());
                }

                break;

        }
    }
}
