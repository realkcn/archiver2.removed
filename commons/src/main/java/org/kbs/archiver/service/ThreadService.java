package org.kbs.archiver.service;
/**
 * Created by kcn on 14-7-24.
 */

import org.bson.types.ObjectId;
import org.kbs.archiver.cache.BoardCache;
import org.kbs.archiver.model.Article;
import org.kbs.archiver.model.Board;
import org.kbs.archiver.model.OriginArticleInfo;
import org.kbs.archiver.repositories.ThreadRepository;
import org.kbs.library.SimpleException;
import org.kbs.library.TwoObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kbs.archiver.model.Thread;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
@Service
public class ThreadService {
    private static final Logger LOG = LoggerFactory.getLogger(ThreadService.class);

    @Autowired
    @Qualifier("threadRepository")
    private ThreadRepository threadRepository;

    @Autowired
    private BoardCache boardCache;

    @Autowired
    private MongoTemplate mongoTemplate;

    int maxArticleQueue=10000;

    public void setMaxArticleQueue(String maxArticleQueue) {
        if (maxArticleQueue!=null)
            this.maxArticleQueue = Integer.getInteger(maxArticleQueue);
    }

    public static Thread newThread(ObjectId threadid, Board board, Article article) {
        Thread thread = new Thread();
        thread.setThreadid(threadid);
//        thread.setArticlenumber(1);// 考虑最后再插入
        thread.setAuthor(article.getAuthor());
        thread.setBoardid(board.getBoardid());
        thread.setLastposttime(article.getPosttime());
        thread.setLastreply(article.getAuthor());
        thread.setPosttime(article.getPosttime());
        thread.setSubject(article.getSubject());
        thread.setEncodingurl(threadid.toString());
//        thread.setOriginid(article.getOriginid());
        return thread;
        // thread.setThreadid()
    }

    public List<Thread> getThreadByBoard(String boardid, int page, int size) {
        Page<Thread> threads = threadRepository.findByBoardid(boardid,
                new PageRequest(page, size, new Sort(Sort.Direction.DESC, "lastposttime")));
        return threads.getContent();
    }

    private ArrayList<Thread> threads=new ArrayList<>();
    private ArrayList<Article> articlesList=new ArrayList<>();
    private ArrayList<OriginArticleInfo> articleInfosList=new ArrayList<>();
    private ArrayList<Integer> articlepos=new ArrayList<>();

    synchronized public void batchInsert(Thread thread,List<Article> articles,List<OriginArticleInfo> articleInfos)
            throws SimpleException {
        if (articles.size()!=articleInfos.size()) {
            throw new SimpleException("Info error");
        }
        threads.add(thread);
        articlesList.addAll(articles);
        articleInfosList.addAll(articleInfos);
        int pos=articlesList.size();
        articlepos.add(pos);
        if (pos>maxArticleQueue)
            batchExecute();
    }

    synchronized public void  batchExecute() {
        int index=0;
        int lastpos=0;
        for (Thread thread:threads) {
            thread.setThreadid(new ObjectId());
            for (int i=lastpos;i<articlepos.get(index);i++) {
                articlesList.get(i).setThreadid(thread.getThreadid());
                articleInfosList.get(i).setThreadid(thread.getThreadid());
                thread.addArticle(articlesList.get(i));
            }
        }

        long start=System.currentTimeMillis();
        mongoTemplate.insert(threads,Thread.class);
        mongoTemplate.insert(articlesList,Article.class);
        mongoTemplate.insert(articleInfosList,OriginArticleInfo.class);
        for (int i=0;i<articlesList.size();i++) {
            articleInfosList.set(i,null);
            articlesList.set(i,null);
        }

        for (int i=0;i<threads.size();i++) {
            threads.set(i,null);
        }
        long spenttime=System.currentTimeMillis()-start;
        LOG.info("batch insert {} second for {} threads, {} articles.",spenttime/1000, threads.size(),articlesList.size());
        articlesList.clear();
        articleInfosList.clear();
        threads.clear();
        articlepos.clear();
    }
}
