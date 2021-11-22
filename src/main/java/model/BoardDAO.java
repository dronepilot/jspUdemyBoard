package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDAO {
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	// �����ͺ��̽��� Ŀ�ؼ�Ǯ�� ����ϵ��� �����ϴ� �޼ҵ�
	public void getCon() {
		
		try {
			Context initctx = new InitialContext();
			
			Context envctx = (Context)initctx.lookup("java:comp/env");
			
			DataSource ds = (DataSource)envctx.lookup("jdbc/pool");
			
			// DataSource
			con = ds.getConnection();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// �ϳ��� ���ο� �Խñ��� �Ѿ�ͼ� ����Ǵ� �޼ҵ�
	public void insertBoard(BoardBean bean) {
		
		getCon();
		// ��Ŭ������ �Ѿ���� �ʾҴ� �����͵��� �ʱ�ȭ���־�� �Ѵ�.
		int ref=0;	// �۱׷��� �ǹ� = ������ ������Ѽ� ���� ū ref���� �������� +1�� �����ָ��
		int re_step = 1;	// �����̱� ������ = �θ��
		int re_level = 1;	// �����̱� ������ = �θ��
		
		try {
			// ���� ū ref���� �о���� �����غ�
			String refsql = "select max(ref) from board";
			// �������ఴü
			pstmt = con.prepareStatement(refsql);
			// ���������� ����� ����
			rs = pstmt.executeQuery();
			
			if(rs.next() ) {	// ��� ���� �ִٸ�
				ref = rs.getInt(1) +1;	// �ִ밪�� +1�� ���ؼ� �۱׷��� ����
			}
			
			// ������ �Խñ� ��ü���� ���̺� ����
			
			String sql = "insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,0,?)";
			
			pstmt = con.prepareStatement(sql);
			// ?���� ���� ����
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			
			pstmt.setInt(5, ref);
			pstmt.setInt(6, re_step);
			pstmt.setInt(7, re_level);
			pstmt.setString(8, bean.getContent());
												
			// ������ �����Ͻÿ�
			pstmt.executeUpdate();
			
			// �ڿ��ݳ�
			con.close();
		} catch(Exception e) {			
			e.printStackTrace();
		}
		
		
		
	}

	
	//��ü�Խñ��� ���� �޾��ִ� �ҽ�
	public Vector<BoardBean> getAllBoard(int start, int end) {
				
		// �����Ұ�ü ����
		Vector<BoardBean> v = new Vector<>();
		
		getCon();
		
		try {
			
			// �����غ� 
			String sql = "select * from (select A.*, rownum rnum from (select * from board order by ref desc,re_step asc)A )"
					+ "where rnum >= ? and rnum <= ?";
			
			// ������ �����Ұ�ü ����
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			// ���������� ��� ����
			rs = pstmt.executeQuery();			
			// �����Ͱ����� ����� �𸣱⿡ �ݺ����� �̿��Ͽ� �����͸� ����
			while(rs.next()) {
				// �����͸� ��Ű¡(���� = BoardBeanŬ������ �̿�)
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getString(6));
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
				// ��Ű¡�� �����͸� ���Ϳ� ����
				v.add(bean);
			}
			
			con.close();
									
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return v;
	}
	
	
	// BoardInfi �ϳ��� �Խñ��� �����ϴ� �޼ҵ�
	public BoardBean getOneBoard(int num) {
				
		// ����Ÿ�� ����
		BoardBean bean = new BoardBean();
		
		getCon();
		
		try {
			// ��ȸ�� ���� ����
			String readsql = "update board set readcount = readcount+1 where num=?";
			// �������ఴü
			pstmt = con.prepareStatement(readsql);
			pstmt.setInt(1, num);
			pstmt.executeLargeUpdate();
			
			
			String sql = "select * from board where num=?";								
			// �������ఴü
			pstmt = con.prepareStatement(sql);			
			pstmt.setInt(1, num);
			
			// ���������� ����� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getString(6));
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
			con.close();						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return bean;
		
	}
	
	
	// ����� �ۼ��ϴ� �޼ҵ�
	public void reWriteBoard(BoardBean bean) {
		
		// �θ�۱׷�� �۽���,�۷����� �о��
		int ref = bean.getRef();
		int re_step = bean.getRe_step();
		int re_level = bean.getRe_level();
		
		getCon();
		
		try {
		///////// �ٽ��ڵ� ////////////////
		// �θ�ۺ��� ū re_level�� ���� ���� 1�� ����������
			String levelsql = "update board set re_level = re_level+1 where ref=? and re_level > ?";
			// �������ఴü ����
			pstmt = con.prepareStatement(levelsql);
			pstmt.setInt(1, ref);
			pstmt.setInt(2, re_level);
			// ��������
			pstmt.executeUpdate();
			
			String sql = "insert into board values(board_seq.NEXTVAL,?,?,?,?,sysdate,?,?,?,0,?)";
			
			pstmt = con.prepareStatement(sql);
			// ?���� ���� ����
			pstmt.setString(1, bean.getWriter());
			pstmt.setString(2, bean.getEmail());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getPassword());
			
			pstmt.setInt(5, ref);		//  �θ��� ref���� �־���
			pstmt.setInt(6, re_step+1);	//  ����̱⿡ �θ�� re_step�� 1�� ������
			pstmt.setInt(7, re_level+1);
			pstmt.setString(8, bean.getContent());
												
			// ������ �����Ͻÿ�
			pstmt.executeUpdate();
			
			// �ڿ��ݳ�
			con.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	// boardUpdate�� �ϳ��� �Խñ��� ����
	public BoardBean getOneUpdateBoard(int num) {
		
		// ����Ÿ�� ����
		BoardBean bean = new BoardBean();
		
		getCon();
		
		try {				
			
			String sql = "select * from board where num=?";								
			// �������ఴü
			pstmt = con.prepareStatement(sql);			
			pstmt.setInt(1, num);
			
			// ���������� ����� ����
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getString(6));
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
			con.close();						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return bean;
		
	}
	
	// update�� delete�� �ʿ��� �н����尪�� ����
	public String getPass(int num) {
			
		// ������ ���� ��ü ����
		String pass="";
		
		getCon();
		
		try {
			String sql = "select password from board where num=?";
			
			pstmt = con.prepareStatement(sql);			
			pstmt.setInt(1, num);			
			rs = pstmt.executeQuery();
			
			// �н����尪�� ����
			if(rs.next()) {
				pass = rs.getString(1);	// �н����尪�� ����
			}
			
			// �ڿ��ݳ�
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return pass;
	}
	
	
	// �ϳ��� �Խñ� ���� �޼ҵ�
	public void updateBoard(BoardBean bean) {
		
		getCon();
		
		try {
			
			String sql = "update board set subject=?, content=? where num=?";
			pstmt = con.prepareStatement(sql);
			
			// ?���� ����
			pstmt.setString(1, bean.getSubject());
			pstmt.setString(2, bean.getContent());
			pstmt.setInt(3, bean.getNum());
			
			pstmt.executeUpdate();
			
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	// �ϳ��� �Խñ� ���� �޼ҵ�
	public void deleteBoard(int num) {			
		
		getCon();
		
		try {				
			
			String sql = "delete from board where num=?";								
			// �������ఴü
			pstmt = con.prepareStatement(sql);			
			pstmt.setInt(1, num);
			
			// ��������
			pstmt.executeUpdate();
			
		
			con.close();						
		} catch (Exception e) {
			e.printStackTrace();
		}
							
	}
	
	// ��ü���� ������ �����ϴ� �޼ҵ�
	public int getAllCount() {
		
		// �Խñ� ��ü���� �����ϴ� ����
		int count=0;
		
		getCon();
		
		try {
			// �����غ�
			String sql = "select count(*) from board";
			
			pstmt = con.prepareStatement(sql);	
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);	//��ü�Խñۼ�
			}
			
			con.close();
									
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	
	
	
	
}
 