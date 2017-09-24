package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

//오라클 데이터 베이스에 연결하고 select, insert, update, delete 작업을 실행해주는 클래스입니다.
public class MemberDAO {
	
	//오라클에 접속하는 소스를 작성
	String id="system";//접속 아이디
	String pass="1111";
	String url="jdbc:oracle:thin:@localhost:1521:XE"; //접속 url
	
	Connection con; // 데이터베이스에 접근할수 있도록 설정
	PreparedStatement pstmt; //데이터 베이스에 쿼리를 실행시켜주는 객체
	ResultSet rs; //데이터베이스 테이블의 결과를 리턴받아 자바에 저장해주는 객체
	
	//데이터 베이스에 접근할 수 있도록 도와주는 메소드
	public void getCon(){
		try{
			//1.해당 데이터 베이스를 사용한다고 선언 (클래스를 등록 = 오라클용 사용 )
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//2.해당 데이터 베이스에 접속
			con=DriverManager.getConnection(url, id, pass);	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//데이터 베이스에 한사람의 회원 정보를 저장해주는 메소드
	public void insertMember(MemberBean mbean){
		
		try{
			getCon();
			//3. 접속후 쿼리준비하여 쿼리를 사용하도록 설정
			String sql ="insert into member values(?, ? , ? ,? ,? , ?, ?, ?)";
			//쿼리를 사용하도록 설정
			pstmt=con.prepareStatement(sql);//jsp 에서 쿼리를 사용하도록 설정
			//?에 맞게 데이터를 맵핑
		    pstmt.setString(1, mbean.getId());
			pstmt.setString(2, mbean.getPass1());
			pstmt.setString(3, mbean.getEmail());
			pstmt.setString(4, mbean.getTel());
			pstmt.setString(5, mbean.getHobby());
			pstmt.setString(6, mbean.getJob());
			pstmt.setString(7, mbean.getAge());
			pstmt.setString(8, mbean.getInfo());
			//4. 오라클에서 쿼리를 실행하시오
			pstmt.executeUpdate(); //insert, update, delete 사용하는 메소드
		}catch(Exception e){
			
			try{
				//5.자원 반납
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
			}catch(Exception e2){
				e2.printStackTrace();
			}	
		}
		
	}
	
	//모든회원의 정보를 리턴해주는 메소드 호출
	public Vector<MemberBean> allSelectMember(){
		Vector<MemberBean> v =new Vector<>();
		
		
		//무조건 데이터 베이스는 예외처리를 반드시 해야 합니다.
		try{
			//커넥션 연결
			getCon();
			//쿼리 준비
			String sql =" select * from member ";
			//쿼리를 실행시켜주는 객체 서언
			pstmt=con.prepareStatement(sql);
			//쿼리를 실행 시간 결과를 리턴해서 받아줌(오라클 데이블의 검색된 결과를 자바객체에 저장)
			rs=pstmt.executeQuery();
			//반복문을 사용해서 rs에 저장된 데이터를 추출해놓여야함
			while(rs.next()){//저장된 데이터 만큼까지 반복문을 돌리겠다라는 뜻입니다.
			    MemberBean bean =new MemberBean();//컬럼으로 나뉘어진 데이터를 빈클래스에 저장
			    bean.setId(rs.getString("id"));
			    bean.setAge(rs.getString("age"));
			    bean.setEmail(rs.getString("email"));
			    bean.setHobby(rs.getString("hobby"));
				bean.setTel(rs.getString("tel"));
				bean.setJob(rs.getString("job"));
				bean.setPass1(rs.getString("pass1"));
			    bean.setInfo(rs.getString("info"));
				//패키징된 memberbean 클래스를 벡터에 저장
			    v.add(bean); // 0번지부터 순서대로 데이터가 저장
			}
			//자원 반납
			con.close();
		}catch(Exception e){
			
		}	
		//다 저장된 벡터를 리턴
		return v;
	}
	
	
	
}


