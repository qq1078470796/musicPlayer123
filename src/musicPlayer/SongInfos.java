package musicPlayer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;

import javax.swing.JLabel;


public class SongInfos {
	private HashMap<String, String> infos=new HashMap<String, String>();//����ʱ�䡪��� ��Ӧ�б�
	private int InfosLenth=0;
	private String singer;//����
	private String album;//ר��

	public int getInfosLenth() {
		return InfosLenth;
	}

	public void setInfosLenth(int infosLenth) {
		InfosLenth = infosLenth;
	}

	public String getSinger() {
		return singer;
	}

	public void setSinger(String singer) {
		this.singer = singer;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}
	/**
	 * �ж��ļ�����
	 * @param file
	 * @return
	 */
	public static String getCharset(File file) {
        String charset = "GBK"; // Ĭ�ϱ���
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(
                  new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1)
                return charset;
            if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1]
                == (byte) 0xFF) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1]
                    == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    //��������BF���µģ�Ҳ����GBK
                    if (0x80 <= read && read <= 0xBF)
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF)// ˫�ֽ� (0xC0 - 0xDF)
                            // (0x80 -
                            // 0xBF),Ҳ������GB������
                            continue;
                        else
                            break;
                     // Ҳ�п��ܳ������Ǽ��ʽ�С
                    } else if (0xE0 <= read && read <= 0xEF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
                System.out.println(loc + " " + Integer.toHexString(read));
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
        }

	/**
	 * ��ȡ���
	 * @param file
	 */
	public void read(File file) {

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file),getCharset(file)));
			
			String line = "";
			
			while ((line = reader.readLine()) != null) {
				//�����һ��û�����ݡ���
				if(line.equals("")){
					continue;
				}
				//System.out.println(line);
				//�����ݾͰѸ���и�Ž�map��
				cutInfo(line);
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * �Ӹ�����и��������Ϣ��ר����Ϣ�Լ����
	 * @param s
	 */
	public void cutInfo(String s){
		
		String k=null;//����и�����
		int last=s.indexOf(":");//Ѱ�ҵ�һ������λ��
		k=s.substring(1, last);//�и����ʵı��ⲿ�ֺ������߲���
		if(k.equals("ti")){
			int lastIndex=s.lastIndexOf("]");
			String temp=s.substring(4,lastIndex);
			album=temp;
			//System.out.println(album);
			return;
		}
		if(k.equals("ar")){
			int lastIndex=s.lastIndexOf("]");
			String temp=s.substring(4,lastIndex);
			singer=temp;
			//System.out.println(singer);
			return;
		}
		if(k.equals("by")||k.equals("offset")||k.equals("al")){
			return;
		}
		
		String tempTime=s.substring(2, 6);
		System.out.println(tempTime);
		int xiaoshu=Integer.parseInt(s.substring(7, 8));
		//��Ϊʱ�������͵�λΪ�룬Ϊ�˾���������ʱ���ᣬ���������������
		if(xiaoshu>=5){
			//�����λС��9ֱ�Ӹ�λ+1
			if(Integer.parseInt(String.valueOf(tempTime.charAt(3)))<9){//�����λС��9ֱ��
				tempTime=tempTime.substring(0,3)+(Integer.parseInt(String.valueOf(tempTime.charAt(3)))+1);
			}
			else{
				//�����ʮλ����59 ����
				if((Integer.parseInt(tempTime.substring(2,4)))==59){
					tempTime=tempTime.substring(0,2)+(Integer.parseInt(tempTime.substring(2,4)));
					}
				//�����ʮλС��59 ��ʮλ+1
				else tempTime=tempTime.substring(0,2)+(Integer.parseInt(tempTime.substring(2,4))+1);
				}
		}
		
		String tempInfos=s.substring(10, s.length());
		infos.put(tempTime, tempInfos);
		//System.out.println(tempTime);
		//System.out.println(infos.get(tempTime));
		
	}


	public HashMap<String, String> getInfos() {
		return infos;
	}

	public void setInfos(HashMap<String, String> infos) {
		this.infos = infos;
	}
	/**
	 * ģ�����
	 * @param args
	 */
	public static void main(String [] args){
		SongInfos i=new SongInfos();
		i.read(new File("C:\\CloudMusic\\����è.lrc"));
	}
	

}
