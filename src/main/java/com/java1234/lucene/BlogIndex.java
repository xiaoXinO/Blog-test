package com.java1234.lucene;

import com.java1234.entity.Blog;
import com.java1234.util.DateUtil;
import com.java1234.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 博客索引类
 * Created by xin on 2017/2/10.
 */
public class BlogIndex {

    private Directory dir;

    /**
     * 获取IndexWriter实例
     *
     * @return
     * @throws IOException
     */
    private IndexWriter getWriter() throws Exception {
        dir = FSDirectory.open(Paths.get("/usr/local/lucene"));
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 添加索引
     *
     * @param blog
     * @throws IOException
     */
    public void addIndex(Blog blog) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("content", String.valueOf(blog.getContentNotag()), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("title", String.valueOf(blog.getTitle()), Field.Store.YES));
        writer.addDocument(doc);
        writer.commit();
        writer.close();
    }

    /**
     * 修改Blog索引
     *
     * @param blog
     * @throws IOException
     */
    public void updateIndex(Blog blog) throws Exception {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new TextField("content", String.valueOf(blog.getContentNotag()), Field.Store.YES));
        doc.add(new StringField("releaseDate", DateUtil.formatDate(new Date(), "yyyy-MM-dd"), Field.Store.YES));
        doc.add(new TextField("title", String.valueOf(blog.getTitle()), Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
        writer.commit();
        writer.close();
    }

    /**
     * 删除索引
     *
     * @param blogId
     */
    public void deleteIndex(String blogId) throws Exception {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term("id", blogId));
        writer.forceMergeDeletes();
        writer.commit();
        writer.close();
    }

    /**
     * 查询博客信息
     *
     * @param q
     * @return
     * @throws Exception
     */
    public List<Blog> searchBlog(String q) throws Exception {
        //打开目录
        dir = FSDirectory.open(Paths.get("C://lucene"));
        IndexReader reader = DirectoryReader.open(dir);
        //初始化IndexSearcher
        IndexSearcher is = new IndexSearcher(reader);

        //添加Search条件
        BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

        QueryParser parser = new QueryParser("title", analyzer);
        Query query = parser.parse(q);

        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser2.parse(q);

        booleanQuery.add(query, BooleanClause.Occur.SHOULD);
        booleanQuery.add(query2, BooleanClause.Occur.SHOULD);

        //获取查询出来的文档
        TopDocs hits = is.search(booleanQuery.build(), 100);
        QueryScorer scorer = new QueryScorer(query);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color='red'>", "</font></b>");
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);

        List<Blog> blogList = new LinkedList<Blog>();
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = is.doc(scoreDoc.doc);
            Blog blog = new Blog();
            blog.setId(Integer.parseInt(doc.get("id")));
            blog.setReleaseDateStr(doc.get("releaseDate"));
            String title = doc.get("title");
            // 除去Content中的 HTML 标签
            String content = StringEscapeUtils.escapeHtml(doc.get("content"));
            if (title != null) {
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if (StringUtil.isNotEmpty(hTitle)) {
                    blog.setTitle(hTitle);
                } else {
                    blog.setTitle(title);
                }
            }
            if (content != null) {
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if (StringUtil.isNotEmpty(hContent)) {
                    blog.setContent(hContent);
                } else {
                    if (content.length() > 100) {
                        blog.setContent(content.substring(0, 100));
                    } else {
                        blog.setContent(content);
                    }

                }
            }
            blogList.add(blog);
        }
        return blogList;
    }
}














