package dync.lucene.demo;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.kr.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import dync.lucene.bean.CodeBean;
import dync.lucene.bean.IssueBean;
import dync.lucene.config.IndexConstants;
import dync.lucene.util.Logs;
import dync.util.ConvertChar;



public class IndexWriterDemo {
	private static final String INDEX_LOCATION = "D:/index_lucene/Index";
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/"; 
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String dbName = "dyncdb";
	
	private Analyzer analyzer = null;
	private Directory directory = null;
	private File indexLocation = null;
	private IndexWriter iWriter = null;
	private ConvertChar convertChar = new ConvertChar("utf-8");
	private static boolean ISCREATESUCCESS = true;
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	private void connect() {
		try {
			Class.forName(DB_DRIVER);

			conn = DriverManager.getConnection((DB_URL+dbName),"root","root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void disconnect() {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public IndexWriterDemo() {
		try {
			initWriter();
		} catch (IOException e) {
			Logs.out.error(e, e);
			ISCREATESUCCESS = false;
		}
	}

	private void setIndex(String codedb_url){
		setIndexing(codedb_url);
		try {	
			iWriter.close();
			directory.close();
			analyzer.close();
		} catch (IOException e) {
			Logs.out.error(e,e);
		}
	}
	
	private void setIndexing(String codedb_url) {
		connect();
		
		String sqlIssue ="select * from ISSUE";		
		try {
			//////////////////////////////////////////
			/////////////////이슈 인덱싱//////////////////
			/////////////////////////////////////////
			IssueBean issueBean = new IssueBean();
			pstmt = conn.prepareStatement(sqlIssue);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				issueBean.setIssueId(rs.getString("ISSUE_ID"));
				issueBean.setSubject(rs.getString("SUBJECT"));
				issueBean.setContent(rs.getString("CONTENTS"));

				Document doc = new Document();
				doc.add(new TextField(IndexConstants.KEY_ISSUEID, issueBean.getIssueId(), Field.Store.YES));
				doc.add(new TextField(IndexConstants.KEY_SUBJECT, issueBean.getSubject(), Field.Store.YES));
				doc.add(new TextField(IndexConstants.KEY_CONTENTS, issueBean.getContent(), Field.Store.YES));
			
				iWriter.addDocument(doc);
			}
			
			rs.close();
			//////////////////////////////////////////
			/////////////////코드 인덱싱//////////////////
			/////////////////////////////////////////	
			CodeBean codeBean = new CodeBean();
			String sqlCode ="select * from CODE";
			pstmt = conn.prepareStatement(sqlCode);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				codeBean.setCodeId(rs.getString("CODE_ID"));
				codeBean.setCodeContents(convertChar.decode(rs.getString("CODE_CONTENTS")));
				Document doc = new Document();
				doc.add(new TextField(IndexConstants.KEY_CODEID, codeBean.getCodeId(), Field.Store.YES));
				doc.add(new TextField(IndexConstants.KEY_CODECONTENTS, codeBean.getCodeContents(), Field.Store.YES));
			
				iWriter.addDocument(doc);
			}
			rs.close();
		} catch (Exception e) {
			Logs.out.error(e, e);
		}
		finally {
			disconnect();
		}

	} 
	
	private void initWriter() throws IOException {
		ISCREATESUCCESS = true;

		if (indexLocation == null) {
			indexLocation = new File(INDEX_LOCATION);
		}
		if (!indexLocation.exists()) {
			indexLocation.mkdir();
		}
		if (directory == null) {
			directory = FSDirectory.open(indexLocation);
		}

		analyzer = new KoreanAnalyzer(Version.LUCENE_43);
		IndexWriterConfig iConf = new IndexWriterConfig(Version.LUCENE_43, analyzer);
		iConf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		iWriter = new IndexWriter(directory, iConf);
	}
	
	public static void main(String[] args) throws SQLException, Exception {
		IndexWriterDemo test = new IndexWriterDemo();
		if (ISCREATESUCCESS) {
			test.setIndex(DB_URL);
		}
	}

}
