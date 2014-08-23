package org.kbs.archiver;
/**
 * Created by kcn on 14-8-11.
 */

import org.bson.types.ObjectId;
import org.kbs.archiver.entity.ArticleEntity;
import org.kbs.archiver.entity.AttachmentEntity;
import org.kbs.archiver.entity.BoardEntity;
import org.kbs.archiver.entity.ThreadEntity;
import org.kbs.archiver.model.*;
import org.kbs.archiver.model.Thread;
import org.kbs.archiver.persistence.*;
import org.kbs.archiver.repositories.*;
import org.kbs.archiver.service.OldUrlMappingService;
import org.kbs.archiver.service.ThreadService;
import org.kbs.library.SimpleException;
import org.kbs.library.TwoObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("migrateService")
public class MigrateService {
    private static final Logger LOG = LoggerFactory
            .getLogger(MigrateService.class);
    @Autowired
    private ThreadMapper threadMapper;

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private OriginArticleInfoRepository originArticleInfoRepository;

    @Autowired
    private AttachmentDAO attachmentDAO;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ThreadService threadService;

    @Autowired
    private OldUrlMappingService oldUrlMappingService;

    private boolean force=false;

    public boolean isForce() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }

    private TwoObject<ArrayList<Article>,ArrayList<OriginArticleInfo>> getArticlesByThread(ThreadEntity oldthread,
            ObjectId boardid, ObjectId threadid) {
        ArrayList<Article> articles = new ArrayList<>();
        ArrayList<OriginArticleInfo> originArticleInfos= new ArrayList<>();

        List<ArticleEntity> oldarticles = articleMapper.getArticlesOnThread(oldthread.getThreadid());

        if (oldarticles == null)
            return null;
        for (ArticleEntity oldarticle : oldarticles) {
            String body = articleBodyMapper.get(oldarticle.getArticleid());
            if (body == null) {
                LOG.warn("Article {} havn't body", oldarticle.getArticleid());
                body = "";
            }
            ObjectId articleid=new ObjectId();
            Article article = new Article();
            article.setBoardid(boardid);
            article.setAuthor(oldarticle.getAuthor());
            article.setIsvisible(oldarticle.isIsvisible());
            article.setPosttime(oldarticle.getPosttime());
            article.setSubject(oldarticle.getSubject());
            article.setBody(body);
            article.setArticleid(articleid);
            article.setThreadid(threadid);

            OriginArticleInfo articleInfo=new OriginArticleInfo();
            articleInfo.setBoardid(boardid);
            articleInfo.setFilename(oldarticle.getFilename());
            articleInfo.setOriginid(oldarticle.getOriginid());
            articleInfo.setReplyid(oldarticle.getReplyid());
            articleInfo.setArticleid(articleid);
            articleInfo.setThreadid(threadid);

            articles.add(article);
            originArticleInfos.add(articleInfo);

            EncodingURLMapping encodingURLMapping=new EncodingURLMapping(
                    EncodingURLMapping.Type.ARTICLE,oldarticle.getEncodingurl(),articleid);

            oldUrlMappingService.batchAdd(encodingURLMapping);

            List<AttachmentEntity> oldattachments = attachmentMapper.getByArticle(oldarticle.getArticleid());
            if (oldattachments != null) {
                for (AttachmentEntity oldattachment : oldattachments) {

                    byte [] data=attachmentMapper.get(oldattachment.getAttachmentid()).getData();
                    if (data==null)
                        LOG.warn("Attachement {} data is null.",oldattachment.getAttachmentid());
                    String attachmentid=attachmentDAO.put(new ByteArrayInputStream(data));
                    Attachment attachment=new Attachment();
                    attachment.setAttachmentid(attachmentid);
                    attachment.setName(oldattachment.getName());
                    attachment.setDatasize(oldattachment.getDatasize());
                    article.addAttachment(attachment);
                    encodingURLMapping=new EncodingURLMapping(
                            EncodingURLMapping.Type.ATTACHMENT,oldattachment.getEncodingurl(),attachmentid);

                    oldUrlMappingService.batchAdd(encodingURLMapping);
                }
            }
            //Can't save article to repository,because threadid is missing.
        }
//        mongoTemplate.insert(articles,Article.class);
//        mongoTemplate.insert(originArticleInfos,OriginArticleInfo.class);
        return new TwoObject<>(articles,originArticleInfos);
    }

    private org.kbs.archiver.model.Thread Convert(ThreadEntity oldthread,ObjectId boardid) {
        Thread thread = new Thread();
        if (boardid==null) {
            Board newBoard = boardRepository.findByName(boardMapper.get(oldthread.getBoardid()).getName());
            if (newBoard == null) {
                LOG.error("Can't find board:" + boardMapper.get(oldthread.getBoardid()).getName() + ",migrate board first.");
                return null;
            }
            boardid=newBoard.getBoardid();
        }

        ObjectId threadid=new ObjectId();
        thread.setBoardid(boardid);
//        thread.set(oldthread.getArticlenumber());
        thread.setAuthor(oldthread.getAuthor());
        thread.setEncodingurl(oldthread.getEncodingurl());
        thread.setIsvisible(oldthread.isIsvisible());
        thread.setLastposttime(oldthread.getLastposttime());
        thread.setLastreply(oldthread.getLastreply());
//        thread.setOriginid(oldthread.getOriginid());
        thread.setPosttime(oldthread.getPosttime());
        thread.setSubject(oldthread.getSubject());
        thread.setThreadid(null);
        thread.setThreadid(threadid);

        TwoObject<ArrayList<Article>,ArrayList<OriginArticleInfo>> returnobj=
                getArticlesByThread(oldthread, boardid, threadid);
        ArrayList<Article> articles = returnobj.getFirst();
        ArrayList<OriginArticleInfo> originArticleInfos = returnobj.getSecond();


        EncodingURLMapping encodingURLMapping=new EncodingURLMapping(
                EncodingURLMapping.Type.THREAD,oldthread.getEncodingurl(),threadid);

        oldUrlMappingService.batchAdd(encodingURLMapping);
//        threadRepository.save(thread);
//        for (Article article : articles)
//            article.setThreadid(thread.getThreadid());
        //TODO migrate articles
        try {
            threadService.batchInsert(thread,articles,originArticleInfos);
        } catch (SimpleException e) {
            LOG.error("insert thread error:"+thread.getThreadid().toString(),e);
        }
        return thread;
    }

    public void moveThread(String boardname) {
        long starttime=System.currentTimeMillis();

        BoardEntity boardEntity = boardMapper.getByName(boardname);
        List<ThreadEntity> oldthreads = threadMapper.getThreadsOnBoard(boardEntity.getBoardid());
        Board board = boardRepository.findByName(boardname);
        if (board == null) {
            LOG.error("Can't find board:" + boardname + ",migrate board first.");
            return;
        }

        if (!mongoTemplate.collectionExists(Thread.class)) {
            mongoTemplate.createCollection(Thread.class);
        } else
            mongoTemplate.remove(new Query(Criteria.where("boardid").is(board.getBoardid())),Thread.class);

        int ticket=0;
        Date date=new Date();
        for (ThreadEntity oldthread : oldthreads) {

            Thread thread = Convert(oldthread,board.getBoardid());
            ticket++;
        }

        threadService.batchExecute();
        oldUrlMappingService.batchExecute();
        long timespent=(System.currentTimeMillis()-starttime)/1000;
        LOG.info("Convert thread for board {} end.Toal time {}:{}", boardname, timespent  / 60,
                timespent  % 60);
        LOG.info("Total threads: {} .Speed {} thread/sec", ticket, ((double)ticket)/timespent);
    }

    public void migrateBoardInfo(String boardname) throws SimpleException {
        BoardEntity boardEntity = boardMapper.getByName(boardname);
        Board board = new Board();
        board.setThreads(boardEntity.getThreads());
        board.setSection(boardEntity.getSection());
        board.setLastdeleteid(boardEntity.getLastdeleteid());
        board.setLastarticleid(boardEntity.getLastarticleid());
        board.setName(boardEntity.getName());
        board.setCname(boardEntity.getCname());
        board.setArticles(boardEntity.getArticles());
        board.setGroupid(boardEntity.getGroupid());
        board.setIgnored(boardEntity.isIgnored());
        board.setIshidden(boardEntity.isIshidden());

        Board oldboard = boardRepository.findByName(boardname);
        if (oldboard != null) {
            if (!isForce()) {
                try {
                    System.out.println("版面" + boardname + "已经存在，是否需要迁移？(Y/n)");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String str = br.readLine();
                    if ((str.charAt(0) != 'y') && (str.charAt(0) != 'Y')) {
                        System.out.println("停止迁移");
                        throw new SimpleException();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    throw new SimpleException();
                }
            }
            board.setBoardid(oldboard.getBoardid());
        }
        boardRepository.save(board);
    }

    public void migrateAll() {
        List<BoardEntity> oldboards = boardMapper.selectAll();
        for (BoardEntity oldboard : oldboards) {
            try {
                migrateBoardInfo(oldboard.getName());
                moveThread(oldboard.getName());
            } catch (SimpleException e) {
                e.printStackTrace();
            }
        }
    }
}
