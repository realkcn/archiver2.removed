package org.kbs.archiver;

import com.sohucs.auth.BasicSessionCredentials;
import com.sohucs.auth.SohuCSCredentials;
import org.kbs.archiver.persistence.ThreadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by kcn on 14-8-5.
 */
public class MigrateTool {
    private static final Logger LOG = LoggerFactory
            .getLogger(MigrateTool.class);
    public static void main(String[] arg) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("classpath:spring.xml");
        MigrateService service = (MigrateService) context.getBean("migrateService");
        service.moveThread("Test");
    }
}
