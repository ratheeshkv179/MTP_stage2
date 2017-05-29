/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitb.cse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ratheeshkv
 */
public class DBManager {

    static Connection conn = null;

    private boolean createConnection() {

        try {
            Class.forName(Constants.JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        }

        try {
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER_NAME, Constants.DB_PASSWORD);
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void closeConnection() {

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean authenticate(String user, String pwd) {

        boolean result = false;
        //    DBManager mgr = null;

        if (Constants.dbManager != null) {
            Constants.dbManager.closeConnection();
        }

        Constants.dbManager = new DBManager();
        result = Constants.dbManager.createConnection();
        if (!result) {
            return result;
        }

        try {
            PreparedStatement p1 = conn.prepareStatement("select password from users where username=?;");
            p1.setString(1, user);
            //System.out.println("\nQuery : " + p1);
            ResultSet rs = p1.executeQuery();
            if (!rs.next()) {
                result = false;
            } else if (pwd.compareTo((String) rs.getString(1)) == 0) {

                Utils.createSession(user);
                result = true;
                System.out.println("success");
            }

        } catch (SQLException ex) {
            result = false;
            ex.printStackTrace();
        }
        //  mgr.closeConnection();

        System.out.println(result);
        return result;
    }

    public static ResultSet getAllBssids() {

        ResultSet rs = null;
        //  DBManager mgr = new DBManager();

        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("select distinct  bssid from experimentdetails  where bssid !=\"\" ;");
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //  mgr.closeConnection();
        //    }

        return rs;
    }

    public static ResultSet getAllSsids() {

        ResultSet rs = null;
        //  DBManager mgr = new DBManager();
        //  if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("select distinct  ssid from experimentdetails  where bssid !=\"\" ;");
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //  mgr.closeConnection();
        //    }

        return rs;
    }

    public static ResultSet getClientList() {

        ResultSet rs = null;
        //  DBManager mgr = new DBManager();
        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("select macaddress,ssid,bssid from experimentdetails order by bssid");
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //    }

        return rs;
    }

    public static ResultSet getAllExperimentDetails() {

        ResultSet rs = null;
        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("select id,date_format(starttime,'%d:%m:%Y %H:%i:%s'),date_format(endtime,'%d:%m:%Y %H:%i:%s'),name,location,description,fileid,filename from experiments order by id desc");
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //     }

        return rs;
    }

    public static ResultSet getExperimentDetails(String expid) {

        ResultSet rs = null;
        //  DBManager mgr = new DBManager();
        //       if (mgr.createConnection()) {

        // expid,macaddress,controlfilesend,expover,status 
        try {
            PreparedStatement p1 = conn.prepareStatement("select macaddress,controlfilesend,rssi,bssid,ssid,expover,status,filereceived,controlfilesendDate,expoverDate,fileid from experimentdetails where expid=" + expid + " order by bssid;");
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();
            //        //System.out.println("\nQuery : " + p1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //     }

        return rs;
    }

    public static ResultSet getExperimentDetails(String expid, String macAddress) {

        ResultSet rs = null;
        //  DBManager mgr = new DBManager();
        //       if (mgr.createConnection()) {

        // expid,macaddress,controlfilesend,expover,status 
        try {
            PreparedStatement p1 = conn.prepareStatement("select  ipaddress,port,bssid,ssid,rssi,linkspeed,storagespace,memory,numberofcores,processorspeed from experimentdetails where expid=" + expid + " and macaddress='" + macAddress + "' order by bssid;");
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();
            //        //System.out.println("\nQuery : " + p1);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //     }

        return rs;
    }

    public static int getNextExperimentId() {

        DBManager mgr = new DBManager();
        int expId = 0;
        ResultSet rs = null;
        //  DBManager mgr = new DBManager();
        //   if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("select max(id) as id from experiments;");
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                if (id == null) {
                    expId = 1;
                } else {
                    expId = Integer.parseInt(id) + 1;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /// ///      mgr.closeConnection();
        //      }

        Constants.currentSession.setCurrentExperimentId(expId);
        return expId;
    }

    public static boolean addExperiment(Experiment exp, String fileId, String fileName) {

        DBManager mgr = new DBManager();

        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("insert into experiments(id, name, location, description, fileid,filename,starttime) values(?,?,?,?,?,?,sysdate());");
            p1.setString(1, Integer.toString(Constants.currentSession.getCurrentExperimentId()));
            p1.setString(2, exp.getName());
            p1.setString(3, exp.getLocation());
            p1.setString(4, exp.getDescription());
            p1.setString(5, fileId);
            p1.setString(6, fileName);
            //System.out.println("\nQuery : " + p1);
            p1.execute();//executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        //      mgr.closeConnection();
        //     }
        return true;
    }

    public static boolean addExperimentDetails(int expNumber, DeviceInfo device, String file_id, int expStatus, String status) {
        //    DBManager mgr = new DBManager();

        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement(" insert into experimentdetails(expid,macaddress,osversion,wifiversion,rssi,bssid,ssid,linkspeed,numberofcores,storagespace,memory,processorspeed,wifisignalstrength,getlogrequestsend,filereceived,expover,expreqsend,status,ipaddress,port,fileid, controlfilesendDate)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate());");
            p1.setInt(1, expNumber);
            p1.setString(2, device.getMacAddress());
            p1.setInt(3, Integer.parseInt(device.getOsVersion()));
            p1.setString(4, device.getWifiVersion());
            p1.setString(5, device.getRssi());
            p1.setString(6, device.getBssid());
            p1.setString(7, device.getSsid());
            p1.setString(8, device.getLinkSpeed());
            p1.setInt(9, device.getNumberOfCores());
            p1.setInt(10, device.getStorageSpace());
            p1.setInt(11, device.getMemory());
            p1.setInt(12, device.getProcessorSpeed());
            p1.setInt(13, device.getWifiSignalStrength());
            p1.setInt(14, 0);
            p1.setBoolean(15, false);
            p1.setBoolean(16, false);
            p1.setInt(17, expStatus);
            p1.setString(18, status);
            p1.setString(19, device.getIp());
            p1.setString(20, Integer.toString(device.getPort()));
            p1.setString(21, file_id);
//            p1.setString(22, file_name);
            //System.out.println("\nQuery : " + p1);
            p1.execute();
            //        mgr.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        //      }
        return true;
    }

    public static boolean updateExperimentOverStatus(int expNumber, String macAddress) {

        //   DBManager mgr = new DBManager();
        //   if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("update experimentdetails set expover=?,expoverDate=sysdate() where expid=? and macaddress=?;");

            p1.setBoolean(1, true);
            p1.setInt(2, expNumber);
            p1.setString(3, macAddress);
            //System.out.println("\nQuery : " + p1);
            p1.executeUpdate();
            //          mgr.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        //     }
        return true;
    }

    // controlfilesend
    public static boolean updateExpStartReqSendStatus(int expNumber, String macAddress, int controlFileStatus, String status) {
        //    DBManager mgr = new DBManager();
        //     if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("update experimentdetails set expreqsend=?, status=? where expid=? and macaddress=?;");
            p1.setBoolean(1, true);
            p1.setString(2, status);
            p1.setInt(3, expNumber);
            p1.setString(4, macAddress);
            //System.out.println("\nQuery : " + p1);
            p1.executeUpdate();
            //            mgr.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        //      }
        return true;
    }

    public static ResultSet getExpStartStatus(int expid) {

        ResultSet rs = null;
        //  DBManager mgr = new DBManager();
        //      if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement(" select expreqsend, count(*) as count from experimentdetails  where expid=? group by expreqsend;");
            p1.setInt(1, expid);
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //     }
        return rs;
    }

    public static ResultSet getDetailedExperimentReqStatus(int expid) {

        ResultSet rs = null;
        //  DBManager mgr = new DBManager();
        //      if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement(" select macaddress,expreqsend,status,bssid,ssid,expover,expoverDate,controlfilesendDate from experimentdetails where expid=? ;");
            p1.setInt(1, expid);
            System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //     }
        return rs;
    }

    public static ResultSet getDetailedControlFileStatus(int expid) {

        ResultSet rs = null;
        //  DBManager mgr = new DBManager();
        //      if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement(" select macaddress,controlfilesend,status,bssid,ssid,expover,expoverDate,controlfilesendDate from experimentdetails where expid=? ;");
            p1.setInt(1, expid);
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //     }
        return rs;
    }

    public static ResultSet getLogFileRequestStatus(int expid) {
        ResultSet rs = null;

        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement(" select getlogrequestsend, count(*) as count from experimentdetails  where expid=? and expover=1 group by getlogrequestsend;");
            p1.setInt(1, expid);
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        //    }

        return rs;
    }

    public static int getLogFileReceivedCount(int expid) {
        ResultSet rs = null;
        //  DBManager mgr = new DBManager();
        int count = 0;
        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement(" select  count(*) as count from experimentdetails  where expid=? and expover=1 and filereceived = true ;");
            p1.setInt(1, expid);
            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();
            if (rs.next()) {
                count = Integer.parseInt(rs.getString("count"));
            }
            //           mgr.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            //         }

        }

        return count;
    }

    public static int getExperimentOverCount(int expid) {

        //  DBManager mgr = new DBManager();
        int count = -1;
        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("select count(*) as count from  experimentdetails where expover=true and expid=?;");
            p1.setInt(1, expid);
            //System.out.println("\nQuery : " + p1);
            ResultSet rs = p1.executeQuery();
            if (rs.next()) {
                count = Integer.parseInt(rs.getString("count"));
            }
            //            mgr.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //     } else {
        //        return count;
        //     }
        return count;
    }

    public static boolean updateGetLogFileRequestStatus(int expId, String macAddress, int status) {

        // DBManager mgr = new DBManager();
        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("update experimentdetails set getlogrequestsend=" + status + " where expid=? and macaddress=?;");
            p1.setInt(1, expId);
            p1.setString(2, macAddress);
            //System.out.println("\nQuery : " + p1);
            p1.executeUpdate();
            //          mgr.closeConnection();
            //return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        //       }
        return true;
    }

    public static boolean updateFileReceivedField(int expId, String macAddress) {

        //     DBManager mgr = new DBManager();
//        if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("update experimentdetails set filereceived = true where expid=? and macaddress=?;");
            p1.setInt(1, expId);
            p1.setString(2, macAddress);
            //System.out.println("\nQuery : " + p1);
            p1.executeUpdate();
            //return true;
            //              mgr.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        //      }
        return true;
    }

    //getPendingLogFiles.jsp
    public static ResultSet getPendingLogFileList() {

        ResultSet rs = null;
        //DBManager mgr = new DBManager();

        //      if (mgr.createConnection()) {
        try {
//                PreparedStatement p1 = conn.prepareStatement("select expid,macaddress from experimentdetails where controlfilesend=1 and expover=1 and filereceived=0 order by expid desc ;");
//                PreparedStatement p1 = conn.prepareStatement("select expid,macaddress from experimentdetails where controlfilesend=1 and  filereceived=0 order by expid desc ;");
//                  PreparedStatement p1 = conn.prepareStatement("select expid,macaddress,controlfilesend,filereceived from experimentdetails order by expid desc ;");                
            PreparedStatement p1 = conn.prepareStatement("select distinct macaddress from experimentdetails order by expid desc;");

            //System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return rs;
        }
        //    }
        return rs;
    }

    public static CopyOnWriteArrayList<String> getClientsForLogRequest(int expId) {

        //   DBManager mgr = new DBManager();
        CopyOnWriteArrayList<String> clients = new CopyOnWriteArrayList<String>();

        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("select macaddress from experimentdetails where expover=true and getlogrequestsend=0 and expid=?;");
            p1.setInt(1, expId);
            //System.out.println("\nQuery : " + p1);
            ResultSet rs = p1.executeQuery();

            while (rs.next()) {
                clients.add(rs.getString("macaddress"));
            }
            //           mgr.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //       } else {
        //          return null;
        //       }
        return clients;
    }

    public static void updateStopExperiment(int expId) {

        try {
            PreparedStatement p1 = conn.prepareStatement("update  experiments  set endtime = sysdate() where id=?;");
            p1.setInt(1, expId);
            //System.out.println("\nQuery : " + p1);
            p1.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void addControlFileInfo(String fileName, String exp_desc) {

        // ResultSet rs = null;
        try {
            PreparedStatement p1 = conn.prepareStatement("insert into control_file_info(filename, maclist,description,filedate) values(?,?,?,sysdate());");
            p1.setString(1, fileName);
            p1.setString(2, "");
            p1.setString(3, exp_desc);
            p1.execute();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static boolean addControlFileSendingDetails(int fileid, String macaddr) {//, int controlFileStatus, String status) {
        //    DBManager mgr = new DBManager();

        //    if (mgr.createConnection()) {
        try {
            PreparedStatement p1 = conn.prepareStatement("select maclist from control_file_info where fileid=? ;");
            p1.setInt(1, fileid);
            System.out.println("\nQuery : " + p1);
            ResultSet rs = p1.executeQuery();
            String maclist = "";
            ConcurrentHashMap<String, Integer> Clients = new ConcurrentHashMap<String, Integer>();

            if (rs != null) {
                while (rs.next()) {
                    maclist = rs.getString(1);
                }

                maclist = maclist.trim();

                if (maclist != null && maclist != "") {
                    String[] mac = maclist.split(" ");
                    for (int i = 0; i < mac.length; i++) {
                        Clients.put(mac[i], i);
                    }
                }

                Clients.put(macaddr, 1);

                Enumeration<String> clientMac = Clients.keys();
                maclist = "";
                while (clientMac.hasMoreElements()) {
                    maclist = maclist + " " + clientMac.nextElement();
                }

                p1 = conn.prepareStatement("update control_file_info set maclist=? where fileid=? ;");

                p1.setString(1, maclist);
                p1.setInt(2, fileid);
                System.out.println("\nQuery : " + p1);
                p1.executeUpdate();

            } else {
                System.out.println("\naddControlFileSendingDetails : NULL field");
            }

            //        mgr.closeConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        //      }
        return true;
    }

    public static ResultSet getAllControlFileNames() {
        ResultSet rs = null;
        try {
            PreparedStatement p1 = conn.prepareStatement("select distinct filename from control_file_info;");
            rs = p1.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }//select distinct filename from control_file_info

    public static boolean checkUserExists(String macaddress) {

        ResultSet rs = null;
        boolean flag = false;
        try {
            PreparedStatement p1 = conn.prepareStatement("select count(*) from androidappusers where macaddress=?;");
            p1.setString(1, macaddress);
            System.out.println("Query1 : " + p1.toString());
            rs = p1.executeQuery();

            if (rs.next()) {
                if (rs.getString(1).equalsIgnoreCase("1")) {
                    flag = true;
                } else {
                    flag = false;
                }
            } else {
                flag = false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // select count(*) from androidappusers where macaddress='40:88:05:6A:0E:01';
        return flag;
    }

    public static void updateAppUserInfo(String email, String macaddress) {

        if (checkUserExists(macaddress)) {

            if (email != null && !email.equalsIgnoreCase("")) {
                try {
                    PreparedStatement p1 = conn.prepareStatement(" update androidappusers set email=?, lastheartbeat=sysdate() where macaddress=?;");
                    p1.setString(1, email);
                    p1.setString(2, macaddress);
                    System.out.println("Query1 : " + p1.toString());
                    p1.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    PreparedStatement p1 = conn.prepareStatement(" update androidappusers set lastheartbeat=sysdate() where macaddress=?;");
                    p1.setString(1, macaddress);
                    System.out.println("Query1 : " + p1.toString());
                    p1.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            try {
                PreparedStatement p1 = conn.prepareStatement("insert into androidappusers(macaddress,email,lastheartbeat) values(?,?,sysdate());");
                p1.setString(1, macaddress);
                p1.setString(2, email);
                System.out.println("Query2 : " + p1.toString());
                p1.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //  insert into androidappusers(email,contactno,username,datainfo)values("","","",sysdate());
    }

    public static void updateAppUserHeartBeatInfo(String macaddress, String appversion, String deviceName, String androidVersion) {

        if (checkUserExists(macaddress)) {
            if (appversion != null && !appversion.equalsIgnoreCase("")) {
                try {
                    PreparedStatement p1 = conn.prepareStatement("update androidappusers set lastheartbeat=sysdate(),appversion=? where macaddress=?");
                    p1.setString(1, appversion);
                    p1.setString(2, macaddress);
                    System.out.println("Query1 : " + p1.toString());
                    p1.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                try {
                    PreparedStatement p1 = conn.prepareStatement("update androidappusers set lastheartbeat=sysdate() where macaddress=?");
                    p1.setString(1, macaddress);
                    System.out.println("Query4 : " + p1.toString());
                    p1.executeUpdate();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            try {
                PreparedStatement p1 = conn.prepareStatement("insert into androidappusers(macaddress,email,appversion,devicename,lastheartbeat) values(?,?,?,?,sysdate());");
                p1.setString(1, macaddress);
                p1.setString(2, "");
                p1.setString(3, appversion);
                p1.setString(4, deviceName);
                System.out.println("Query5 : " + p1.toString());

//                p1.setString(4, "");
//                p1.setString(5, "");
                p1.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //  insert into androidappusers(email,contactno,username,datainfo)values("","","",sysdate());

        if (deviceName != null && !deviceName.equalsIgnoreCase("")) {
            try {
                PreparedStatement p1 = conn.prepareStatement("update androidappusers set  devicename=?  where macaddress=?");
                p1.setString(1, deviceName);
                p1.setString(2, macaddress);
                System.out.println("Query3 : " + p1.toString());
                p1.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (androidVersion != null && !androidVersion.equalsIgnoreCase("")) {
            try {
                PreparedStatement p1 = conn.prepareStatement("update androidappusers set  androidVersion=?  where macaddress=?");
                p1.setString(1, androidVersion);
                p1.setString(2, macaddress);
                System.out.println("Query6 : " + p1.toString());
                p1.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public static ResultSet getAppUserInfo() {

        ResultSet rs = null;
        try {
            PreparedStatement p1 = conn.prepareStatement("select macaddress,email,appversion,lastheartbeat,devicename,androidVersion from androidappusers order by lastheartbeat desc;");
            rs = p1.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rs;
        //  insert into androidappusers(email,contactno,username,datainfo)values("","","",sysdate());
    }

    public static int nextFileId() {
        // select ifnull(max(fileid)+1,1),max(fileid) from control_file_info;
        ResultSet rs = null;
        int fileid = 1;
        try {
            PreparedStatement p1 = conn.prepareStatement("select ifnull(max(fileid)+1,1) as fileid from control_file_info;");
            rs = p1.executeQuery();
            if (rs.next()) {
                fileid = Integer.parseInt(rs.getString("fileid"));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileid;
    }

    public static ResultSet getControlFileInfo() {
        ResultSet rs = null;
        try {
            PreparedStatement p1 = conn.prepareStatement("select fileid,filename,filedate,description from control_file_info order by fileid desc;");
            rs = p1.executeQuery();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rs;
    }

    public static ConcurrentHashMap<String, Integer> getControlFileUserInfo(String fileId) {
        ResultSet rs = null;
        ConcurrentHashMap<String, Integer> Clients = new ConcurrentHashMap<String, Integer>();
        try {
            PreparedStatement p1 = conn.prepareStatement("select maclist from control_file_info where fileid=?;");
            System.out.println("\nFIle ID : " + fileId);
            p1.setInt(1, Integer.parseInt(fileId));
            System.out.println("\nQuery : " + p1);
            rs = p1.executeQuery();

            String MAC = "";
            if (rs.next()) {
                MAC = rs.getString(1);
            }

//            System.out.println(rs);
//            System.out.println(rs.getFetchSize());
//                    rs.getString("maclist");
            MAC = MAC.trim();
            String[] macLIST = MAC.split(" ");

            for (int i = 0; i < macLIST.length; i++) {
                Clients.put(macLIST[i], i);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Clients;
    }

}
