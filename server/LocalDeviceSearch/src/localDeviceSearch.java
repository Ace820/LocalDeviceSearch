import java.awt.*;
import javax.swing.*;
import java.io.IOException;
import java.net.*;


public class localDeviceSearch extends JFrame{
    Container container;
    final int checkboxPostionX = 0;
    final int checkboxWidth = 20;
    final int checkboxHeight = 15;
    final int numberPostionX = 20;
    final int numberWdith = 30;
    final int textWdith = 100;
    final int textHeight = 20;
    final int macPostionX = 50;
    final int ipPostionX = 150;
    final int versionPostionX = 250;
    private static Thread listenThread;
    final int port = 53215;
    InetAddress iaddress = null;
    MulticastSocket socket = null;
    public localDeviceSearch() {
        //JFrame jf = new JFrame(title);
        setTitle(null);
        setLayout(null);
        setBounds(0,0,1000,500);
        container= getContentPane();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        listenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    iaddress = InetAddress.getLocalHost();
                    System.out.println(iaddress.toString());
                    socket = new MulticastSocket(port);
                    socket.joinGroup(iaddress);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                while(true) {
                    byte data[] = new byte[1024];
                    DatagramPacket packet = null;
                    packet = new DatagramPacket(data, data.length, iaddress, port);
                    try {
                        socket.receive(packet);
                        String msg = new String(data);
                        System.out.println(msg);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void CreateJFrame(int No,String mac,String ip,String version) {
        String itemNumber = String.valueOf(No);
        if (No == 0)
            itemNumber = "No.";
        int itemPositionY = No * 20;
        JCheckBox checkBox = new JCheckBox();
        checkBox.setBounds(checkboxPostionX,itemPositionY + 2,checkboxWidth,checkboxHeight);
        JTextField numberText = new JTextField(itemNumber);
        numberText.setEditable(false);
        numberText.setBounds(numberPostionX,itemPositionY,numberWdith,textHeight);
        JTextField macText = new JTextField(mac);
        macText.setEditable(false);
        macText.setBounds(macPostionX,itemPositionY,textWdith,textHeight);
        JTextField ipText = new JTextField(ip);
        ipText.setEditable(false);
        ipText.setBounds(ipPostionX,itemPositionY,textWdith,textHeight);
        JTextField versionText = new JTextField(version);
        versionText.setEditable(false);
        versionText.setBounds(versionPostionX,itemPositionY,textWdith,textHeight);

        container.add(checkBox);
        container.add(numberText);
        container.add(macText);
        container.add(ipText);
        container.add(versionText);
    }

    public void doSetVisible(boolean set) {
        this.setVisible(set);
    }

    public static void main(String args[]) {
        localDeviceSearch localDeviceSearchDaemon = new localDeviceSearch();
        localDeviceSearchDaemon.CreateJFrame(0,"mac","ip","version");
        localDeviceSearchDaemon.CreateJFrame(1,"mac","ip","version");
        localDeviceSearchDaemon.CreateJFrame(2,"mac","ip","version");
        localDeviceSearchDaemon.CreateJFrame(3,"mac","ip","version");

        listenThread.start();
        localDeviceSearchDaemon.doSetVisible(true);
//        new localDeviceSearch().CreateJFrame(20);
//        new localDeviceSearch().CreateJFrame(40);
//        new localDeviceSearch().CreateJFrame(60);
//        new localDeviceSearch().CreateJFrame(80);
    }
}
