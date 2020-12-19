package org.example;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.InputStream;

public class GtAwsDirectory {

    private JSch jsch = new JSch();
    private Session session = null;
    private int port = 22;
    private String host;
    private String folder;
    private String user = "ubuntu";
    private String command = "ls -la /%s";

    GtAwsDirectory(String host, String folder){
        this.folder = folder;
        this.host = host;
    }
    private void setConfiguration() {
        try {
            jsch.addIdentity(getPrivateKey(this.host));
            session = jsch.getSession(user, host, port);
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
        } catch (
                  JSchException e) {
            throw new RuntimeException("Failed to create Jsch Session object.", e);
        }
    }

    private String getPrivateKey(String hostName){
            ClassLoader classLoader = getClass().getClassLoader();


        switch (hostName){
            case "18.203.171.233":
            case "ec2-18-203-171-233.eu-west-1.compute.amazonaws.com": return "./lib/MJ";
            case "54.77.250.188":
            case "ec2-54-77-250-188.eu-west-1.compute.amazonaws.com": return "./lib/slave";//classLoader.getResource("slave").getPath(); for stream
            default: return "";
        }
    }
    public void executeCommand(){
        setConfiguration();
        try {
            session.connect();
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(String.format(command,this.folder));
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
                while(in.available()>0){
                    int i=in.read(tmp, 0, 1024);
                    if(i<0)break;
                    System.out.print(new String(tmp, 0, i));
                }
                if(channel.isClosed()){
                    System.out.println("exit-status: "+channel.getExitStatus());
                    break;
                }

            }
            channel.disconnect();
            session.disconnect();
        } catch (JSchException e) {
            throw new RuntimeException("Error durring SSH command execution. Command: " + command);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }




}
