package 第六章.实例75;
import javax.sound.midi.*;
import java.io.*;
import java.net.*;

public class MidiDemo {
	private static String midiFile = "sound\\trippygaia1.mid";
	//private static String midiURI = "http://hostname/midifile";
	private Sequence sequence = null;

	public MidiDemo() {
		this.loadAndPlay();
	}

	public void loadAndPlay() {
		try {
			// 从文件中读取
			sequence = MidiSystem.getSequence(new File(midiFile));
			// 从URL中读取
			// sequence = MidiSystem.getSequence
			//                (new URL("http://hostname/midifile"));

			// 为midi序列创建一个sequencer对象
			Sequencer sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.setSequence(sequence);

			// 计算midi播放持续的时间
			double durationInSecs = sequencer.getMicrosecondLength() / 1000000.0;
			System.out.println("the duration of this audio is "
					+ durationInSecs + "secs.");

			// 计算Midi Sequencer中播放的当前位置
			double seconds = sequencer.getMicrosecondPosition() / 1000000.0;
			System.out.println("the Position of this audio is " + seconds
					+ "secs.");

			// 设置Midi声音播放的音量
			if (sequencer instanceof Synthesizer) {
				Synthesizer synthesizer = (Synthesizer) sequence;
				MidiChannel[] channels = synthesizer.getChannels();

				// gain 的值在0和1之间(loudest)
				double gain = 0.9D;
				for (int i = 0; i < channels.length; i++) {
					channels[i].controlChange(7, (int) (gain * 127.0));
				}
			}

			// 开始播放
			sequencer.start();

			// 计算当前的Midi Sequencer播放位置
			Thread.currentThread().sleep(5000);
			seconds = sequencer.getMicrosecondPosition() / 1000000.0;
			System.out.println("the Position of this audio is " + seconds
					+ "secs.");

			// 添加媒体信息的监听器
			sequencer.addMetaEventListener(new MetaEventListener() {
				public void meta(MetaMessage event) {
					if (event.getType() == 47) {
						System.out.println("Sequencer is done playing.");
					}
				}
			});
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		} catch (MidiUnavailableException e) {
		} catch (InvalidMidiDataException e) {
		} catch (InterruptedException e) {
		}
	}

	public static void main(String[] args) {
		MidiDemo midi = new MidiDemo();

	}

}
