package 第六章.实例74;
// SoundApplet.java
import javax.swing.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class SoundApplet extends JApplet
                         implements ActionListener,
                                    ItemListener {
    AppletSoundList soundList;
    String auFile = "sound\\spacemusic.au";
    String aiffFile = "sound\\flute+hrn+mrmba.aif";
    String midiFile = "sound\\trippygaia1.mid";
    String rmfFile = "sound\\jungle.rmf";
    String wavFile = "sound\\bottle-open.wav";
    String chosenFile;
    AudioClip onceClip, loopClip;
    JComboBox formats;
    JButton playButton, loopButton, stopButton;
    boolean looping = false;

    public void init() {
        String [] fileTypes = {auFile,
                               aiffFile,
                               midiFile,        
                               rmfFile,
                               wavFile};
        formats = new JComboBox(fileTypes);
        formats.setSelectedIndex(0);
        // 获得选中的声音文件名
        chosenFile = (String)formats.getSelectedItem();
        formats.addItemListener(this);

        playButton = new JButton("Play");
        playButton.addActionListener(this);

        loopButton = new JButton("Loop");
        loopButton.addActionListener(this);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(this);
        stopButton.setEnabled(false);
                
        JPanel controlPanel = new JPanel();
        controlPanel.add(formats);
        controlPanel.add(playButton);
        controlPanel.add(loopButton);
        controlPanel.add(stopButton);
        getContentPane().add(controlPanel);

        startLoadingSounds();   
    }
       
    public void itemStateChanged(ItemEvent e) {
        chosenFile = (String)formats.getSelectedItem();
        soundList.startLoading(chosenFile);
    }

    void startLoadingSounds() {
        // 开始装载声音文件
        soundList = new AppletSoundList(this, getCodeBase());
        soundList.startLoading(auFile);
        soundList.startLoading(aiffFile);
        soundList.startLoading(midiFile);
        soundList.startLoading(rmfFile);
        soundList.startLoading(wavFile);
   }

    public void stop() {
        onceClip.stop();        //停止一次播放
        if (looping) {
            loopClip.stop();    //停止循环播放
        }
    }    

    public void start() {
        if (looping) {
            loopClip.loop();    //重新启动循环播放
        }
    }    

    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == playButton) {
            //Try to get the AudioClip.
            onceClip = soundList.getClip(chosenFile);
            onceClip.play();     //Play it once.
            stopButton.setEnabled(true); 
            showStatus("Playing sound " + chosenFile + ".");
            if (onceClip == null) {
                showStatus("Sound " + chosenFile + " not loaded yet.");
            }
            return;
        }

        //如果按下循环按钮
        if (source == loopButton) {
            loopClip = soundList.getClip(chosenFile);
    
            looping = true;
            loopClip.loop();     //开始循环播放
            loopButton.setEnabled(false); 
            stopButton.setEnabled(true); 
            showStatus("Playing sound " + chosenFile + " continuously.");
            if (loopClip == null) {
                showStatus("Sound " + chosenFile + " not loaded yet.");
            }
            return;
        }

        //如果按下停止按钮
        if (source == stopButton) {
            if (looping) {
                looping = false;
                loopClip.stop();    //停止循环播放
                loopButton.setEnabled(true); //使开始按钮可用
            }
            else if (onceClip != null) {
                onceClip.stop();
            }
            stopButton.setEnabled(false); 
            showStatus("Stopped playing " + chosenFile + ".");
            return;
        }
    }
}

