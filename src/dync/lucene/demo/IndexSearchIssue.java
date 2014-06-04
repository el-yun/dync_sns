package dync.lucene.demo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import dync.lucene.config.IndexConstants;
import dync.lucene.util.Logs;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.kr.KoreanAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexSearchIssue {
	private static final String INDEX_LOCATION = "D:/index_lucene/Index";
	private static boolean ISCREATESUCCESS = true;
	private static int FINDCONDITIONAND = 0;
	private static int FINDCONDITIONOR = 1;
	private Directory directory = null;
	private File indexLocation = null;
	private IndexSearcher searcher = null;
	private Analyzer analyzer = null;
	private IndexReader ireader = null;
	
	private ArrayList<String> arrResult = new ArrayList<String>();

	public IndexSearchIssue() {
		try {
			initReader();
		} catch (IOException e) {
			Logs.out.error(e, e);
			ISCREATESUCCESS = false;
		}
	}
	//리더 초기??
	private void initReader() throws IOException {
		ISCREATESUCCESS = true;

		if (indexLocation == null) {
			indexLocation = new File(INDEX_LOCATION);
		}

		if (!indexLocation.exists()) {
			indexLocation.mkdir();
		}

		if (directory == null) {
			directory = FSDirectory.open(indexLocation);

			analyzer = new KoreanAnalyzer(Version.LUCENE_43);

			ireader = IndexReader.open(directory);
			searcher = new IndexSearcher(ireader);
		}

	}

	public ArrayList<String> getData(String keyword) {
		QueryParser parser = new QueryParser(Version.LUCENE_43,
				"<default field>", analyzer);
		String[] splitKeyword = keyword.split(" ");        
		Document selectDoc = null;
		  
		try {
			for (int j = 0; j < splitKeyword.length; j++) {
				int count = 0;
				BooleanQuery resultQuery = new BooleanQuery();
				if(j==FINDCONDITIONAND){
					for(int k = 0; k < splitKeyword.length ; k++){
						Term term = new Term(IndexConstants.KEY_SUBJECT, splitKeyword[k]);
						Query query = new TermQuery(term);
						resultQuery.add(query, Occur.MUST);
					}
					for(int k = 0; k < splitKeyword.length ; k++){
						Term term = new Term(IndexConstants.KEY_CONTENTS, splitKeyword[k]);
						Query query = new TermQuery(term);
						resultQuery.add(query, Occur.MUST);
					}
				}
				if(j==FINDCONDITIONOR){					
					for(int k = 0; k < splitKeyword.length ; k++){
						Term term = new Term(IndexConstants.KEY_SUBJECT, splitKeyword[k]);
						Query query = new TermQuery(term);
						resultQuery.add(query, Occur.SHOULD);
					}
					for(int k = 0; k < splitKeyword.length ; k++){
						Term term = new Term(IndexConstants.KEY_CONTENTS, splitKeyword[k]);
						Query query = new TermQuery(term);
						resultQuery.add(query, Occur.SHOULD);
					}
				}
				TopDocs docs = searcher.search(resultQuery, 10);
				ScoreDoc[] hits = docs.scoreDocs;
				count = hits.length;
				if (count == 0)
					arrResult.add("-1");
				else {
					for (int i = 0; i < count; i++) {
						int docId = hits[i].doc;
						selectDoc = searcher.doc(docId);
						arrResult
								.add(selectDoc.get(IndexConstants.KEY_ISSUEID));
					}
				}
			}

			ireader.close();
			directory.close();
			analyzer.close();
		} catch (Exception e) {
			Logs.out.error(e, e);
		}
		
		return arrResult;
	}
}
