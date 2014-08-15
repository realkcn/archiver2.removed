package org.kbs.archiver;

import org.apache.commons.cli.*;
import org.kbs.library.SimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        Options options = new Options();
        options.addOption("h", "help", false, "print help for the command.");
        options.addOption("a", "all", false, "migrate all automatic.");
        options.addOption("f", "force", false, "force confirm.");
        options.addOption(OptionBuilder.withArgName("board")
                .hasArg()
                .withDescription("Migrate the board by name.")
                .create("b"));

        String formatstr = "migratetool [-a][-h/--help][-b boardname]";

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new PosixParser();
        CommandLine cl = null;
        try {
            // 处理Options和参数
            cl = parser.parse(options, arg);
        } catch (ParseException e) {
            formatter.printHelp(formatstr, options); // 如果发生异常，则打印出帮助信息
        }

        if (cl.hasOption('f')) {
            service.setForce(true);
        }
        if (cl.hasOption('a')) {
            service.migrateAll();
        } else if (cl.hasOption('b')) {
            String boardname = cl.getOptionValue('b');
            try {
                service.migrateBoardInfo(boardname);
                service.moveThread(boardname);
            } catch (SimpleException e) {
            }
        }
    }
}
