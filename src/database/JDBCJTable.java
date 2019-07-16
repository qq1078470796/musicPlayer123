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
	AbstractTableModel tm;		//声明一个类AbstractTableModel对象
	JTable table;				//声明一个类JTable对象
	JScrollPane scrollpane;		//声明一个滚动面板对象
	String titles[];			//二维表列名
	Vector records;				//声明一个向量对象

	public void init(){
		records = new Vector();	//实例化向量
		tm = new AbstractTableModel(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public int getColumnCount(){
				return titles.length;	//取得表格列数
			}
			public int getRowCount(){
				return records.size();		//取得表格行数
			}
			public Object getValueAt(int row,int column){
				if(!records.isEmpty())		//取得单元格中的属性值
					return ((Vector)records.elementAt(row)).elementAt(column);
				else
					return null;
			}
			public String getColumnName(int column){
				return titles[column];	//设置表格列名
			}
			public void setValueAt(Object value,int row,int column){
					//数据模型不可编辑，该方法设置为空
			}
			public Class getColumnClass(int c){
				return getValueAt(0,c).getClass();	//取得列所属对象类
			}
			public boolean isCellEditable(int row,int column){
				return false;//设置单元格不可编辑，为缺省实现
			}
		};
	}

	public void start() throws SQLException, ClassNotFoundException{
		titles = new String[]{"歌曲名","歌曲位置","歌手","歌词位置 ","专辑","比特率","文件大小"};

		records.removeAllElements();//初始化向量对象
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
		
		
		//定制表格： 
		table=new JTable(tm);	//生成自己的数据模型
		table.setToolTipText("显示全部查询结果");	//设置帮助提示
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);	//设置表格调整尺寸模式
		table.setCellSelectionEnabled(false);	//设置单元格选择方式
		table.setShowVerticalLines(true);
		table.setShowHorizontalLines(true);
		scrollpane=new JScrollPane(table);		//给表格加上滚动条
		add( scrollpane );

		tm.fireTableStructureChanged();//更新表格
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