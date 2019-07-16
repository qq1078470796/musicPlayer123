package database;
import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import javax.swing.table.*;

class JDBCJTable extends Frame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AbstractTableModel tm;		//����һ����AbstractTableModel����
	JTable table;				//����һ����JTable����
	JScrollPane scrollpane;		//����һ������������
	String titles[];			//��ά������
	Vector records;				//����һ����������

	public void init(){
		records = new Vector();	//ʵ��������
		tm = new AbstractTableModel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public int getColumnCount(){
				return titles.length;	//ȡ�ñ������
			}
			public int getRowCount(){
				return records.size();		//ȡ�ñ������
			}
			public Object getValueAt(int row,int column){
				if(!records.isEmpty())		//ȡ�õ�Ԫ���е�����ֵ
					return ((Vector)records.elementAt(row)).elementAt(column);
				else
					return null;
			}
			public String getColumnName(int column){
				return titles[column];	//���ñ������
			}
			public void setValueAt(Object value,int row,int column){
					//����ģ�Ͳ��ɱ༭���÷�������Ϊ��
			}
			public Class getColumnClass(int c){
				return getValueAt(0,c).getClass();	//ȡ��������������
			}
			public boolean isCellEditable(int row,int column){
				return false;//���õ�Ԫ�񲻿ɱ༭��Ϊȱʡʵ��
			}
		};
	}

	public void start() throws SQLException, ClassNotFoundException{
		titles = new String[]{"������","����λ��","����","���λ�� ","ר��","������","�ļ���С"};

		records.removeAllElements();//��ʼ����������
		List<Music> temp = new Crud().queryAll();
		Vector<String> vtemp ;
		for(Music s:temp){
			vtemp= new Vector<String>();
			vtemp.add(s.getSong());
			vtemp.add(s.getSongDataUrl());
			vtemp.add(s.getSinger());
			vtemp.add(s.getLrcUrl());
			vtemp.add(s.getAlbum());
			vtemp.add(s.getBitRate()+"");
			vtemp.add(s.getDataSize()+"");
			records.add(vtemp);
		}
		
		
		//���Ʊ�� 
		table=new JTable(tm);	//�����Լ�������ģ��
		table.setToolTipText("��ʾȫ����ѯ���");	//���ð�����ʾ
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	//���ñ������ߴ�ģʽ
		table.setCellSelectionEnabled(false);	//���õ�Ԫ��ѡ��ʽ
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		scrollpane=new JScrollPane(table);		//�������Ϲ�����
		add( scrollpane );

		tm.fireTableStructureChanged();//���±��
	}

	public static void main( String [] args ){
		JDBCJTable f = new JDBCJTable();
		f.init();
		try{
			f.start();
		}catch( Exception e){ e.printStackTrace(); }
		f.setSize( 400,300);
		f.setTitle( "Show Database in JTable" );
		f.show();
		f.addWindowListener( new WindowAdapter(){
			public void windowClosing( WindowEvent e){System.exit(0);}
		});
	}
}